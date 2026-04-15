package org.mozilla.javascript.optimizer;

import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.Jump;
import org.mozilla.javascript.ast.ScriptNode;

class BodyCodegen {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ECMAERROR_EXCEPTION = 2;
    private static final int EVALUATOR_EXCEPTION = 1;
    private static final int EXCEPTION_MAX = 5;
    private static final int FINALLY_EXCEPTION = 4;
    static final int GENERATOR_START = 0;
    static final int GENERATOR_TERMINATE = -1;
    static final int GENERATOR_YIELD_START = 1;
    private static final int JAVASCRIPT_EXCEPTION = 0;
    private static final int MAX_LOCALS = 1024;
    private static final int THROWABLE_EXCEPTION = 3;
    private short argsLocal;
    l0 cfw;
    Codegen codegen;
    CompilerEnvirons compilerEnv;
    private short contextLocal;
    private int enterAreaStartLabel;
    private int epilogueLabel;
    private ExceptionManager exceptionManager = new ExceptionManager();
    private Map<Node, FinallyReturnPoint> finallys;
    private short firstFreeLocal;
    private OptFunctionNode fnCurrent;
    private short funObjLocal;
    private short generatorStateLocal;
    private int generatorSwitch;
    private boolean hasVarsInRegs;
    private boolean inDirectCallFunction;
    private boolean inLocalBlock;
    private boolean isGenerator;
    private boolean itsForcedObjectParameters;
    private int itsLineNumber;
    private short itsOneArgArray;
    private short itsZeroArgArray;
    private List<Node> literals;
    private int[] locals;
    private short localsMax;
    private int maxLocals = 0;
    private int maxStack = 0;
    private short operationLocal;
    private short popvLocal;
    private int savedCodeOffset;
    ScriptNode scriptOrFn;
    public int scriptOrFnIndex;
    private short thisObjLocal;
    private int unnestedYieldCount = 0;
    private IdentityHashMap<Node, String> unnestedYields = new IdentityHashMap<>();
    private short[] varRegisters;
    private short variableObjectLocal;

    public class ExceptionManager {
        private LinkedList<ExceptionInfo> exceptionInfo = new LinkedList<>();

        public class ExceptionInfo {
            Node currentFinally = null;
            int[] exceptionStarts = new int[5];
            Node finallyBlock;
            int[] handlerLabels = new int[5];

            public ExceptionInfo(Jump jump, Node node) {
                this.finallyBlock = node;
            }
        }

        public ExceptionManager() {
        }

        private void endCatch(ExceptionInfo exceptionInfo2, int i, int i2) {
            int i3 = exceptionInfo2.exceptionStarts[i];
            if (i3 == 0) {
                throw new IllegalStateException("bad exception start");
            } else if (BodyCodegen.this.cfw.ae(i3) != BodyCodegen.this.cfw.ae(i2)) {
                BodyCodegen.this.cfw.j(exceptionInfo2.exceptionStarts[i], i2, exceptionInfo2.handlerLabels[i], BodyCodegen.exceptionTypeToName(i));
            }
        }

        private ExceptionInfo getTop() {
            return this.exceptionInfo.getLast();
        }

        public void addHandler(int i, int i2, int i3) {
            ExceptionInfo top = getTop();
            top.handlerLabels[i] = i2;
            top.exceptionStarts[i] = i3;
        }

        public void markInlineFinallyEnd(Node node, int i) {
            LinkedList<ExceptionInfo> linkedList = this.exceptionInfo;
            ListIterator<ExceptionInfo> listIterator = linkedList.listIterator(linkedList.size());
            while (listIterator.hasPrevious()) {
                ExceptionInfo previous = listIterator.previous();
                for (int i2 = 0; i2 < 5; i2++) {
                    if (previous.handlerLabels[i2] != 0 && previous.currentFinally == node) {
                        previous.exceptionStarts[i2] = i;
                        previous.currentFinally = null;
                    }
                }
                if (previous.finallyBlock == node) {
                    return;
                }
            }
        }

        public void markInlineFinallyStart(Node node, int i) {
            LinkedList<ExceptionInfo> linkedList = this.exceptionInfo;
            ListIterator<ExceptionInfo> listIterator = linkedList.listIterator(linkedList.size());
            while (listIterator.hasPrevious()) {
                ExceptionInfo previous = listIterator.previous();
                for (int i2 = 0; i2 < 5; i2++) {
                    if (previous.handlerLabels[i2] != 0 && previous.currentFinally == null) {
                        endCatch(previous, i2, i);
                        previous.exceptionStarts[i2] = 0;
                        previous.currentFinally = node;
                    }
                }
                if (previous.finallyBlock == node) {
                    return;
                }
            }
        }

        public void popExceptionInfo() {
            this.exceptionInfo.removeLast();
        }

        public void pushExceptionInfo(Jump jump) {
            this.exceptionInfo.add(new ExceptionInfo(jump, BodyCodegen.getFinallyAtTarget(jump.getFinally())));
        }

        public int removeHandler(int i, int i2) {
            ExceptionInfo top = getTop();
            int i3 = top.handlerLabels[i];
            if (i3 == 0) {
                return 0;
            }
            endCatch(top, i, i2);
            top.handlerLabels[i] = 0;
            return i3;
        }

        public void setHandlers(int[] iArr, int i) {
            for (int i2 = 0; i2 < iArr.length; i2++) {
                int i3 = iArr[i2];
                if (i3 != 0) {
                    addHandler(i2, i3, i);
                }
            }
        }
    }

    public static class FinallyReturnPoint {
        public List<Integer> jsrPoints = new ArrayList();
        public int tableLabel = 0;
    }

    private void addDoubleWrap() {
        addOptRuntimeInvoke("wrapDouble", "(D)Ljava/lang/Double;");
    }

    private void addGoto(Node node, int i) {
        this.cfw.d(i, getTargetLabel(node));
    }

    private void addGotoWithReturn(Node node) {
        FinallyReturnPoint finallyReturnPoint = this.finallys.get(node);
        this.cfw.o(finallyReturnPoint.jsrPoints.size());
        addGoto(node, Token.LAST_TOKEN);
        this.cfw.c(87);
        int b = this.cfw.b();
        this.cfw.af(b);
        finallyReturnPoint.jsrPoints.add(Integer.valueOf(b));
    }

    private void addInstructionCount() {
        addInstructionCount(Math.max(this.cfw.j - this.savedCodeOffset, 1));
    }

    private void addJumpedBooleanWrap(int i, int i2) {
        this.cfw.af(i2);
        int b = this.cfw.b();
        this.cfw.f("java/lang/Boolean", 178, "FALSE", "Ljava/lang/Boolean;");
        this.cfw.d(Token.LAST_TOKEN, b);
        this.cfw.af(i);
        this.cfw.f("java/lang/Boolean", 178, "TRUE", "Ljava/lang/Boolean;");
        this.cfw.af(b);
        this.cfw.ab();
    }

    private void addLoadPropertyIds(Object[] objArr, int i) {
        addNewObjectArray(i);
        for (int i2 = 0; i2 != i; i2++) {
            this.cfw.c(89);
            this.cfw.s(i2);
            String str = objArr[i2];
            if (str instanceof String) {
                this.cfw.t(str);
            } else {
                this.cfw.s(((Integer) str).intValue());
                addScriptRuntimeInvoke("wrapInt", "(I)Ljava/lang/Integer;");
            }
            this.cfw.c(83);
        }
    }

    private void addLoadPropertyValues(Node node, Node node2, int i) {
        int i2 = 0;
        if (this.isGenerator) {
            for (int i3 = 0; i3 != i; i3++) {
                int type = node2.getType();
                if (type == 152 || type == 153 || type == 164) {
                    generateExpression(node2.getFirstChild(), node);
                } else {
                    generateExpression(node2, node);
                }
                node2 = node2.getNext();
            }
            addNewObjectArray(i);
            while (i2 != i) {
                this.cfw.c(90);
                this.cfw.c(95);
                this.cfw.s((i - i2) - 1);
                this.cfw.c(95);
                this.cfw.c(83);
                i2++;
            }
            return;
        }
        addNewObjectArray(i);
        while (i2 != i) {
            this.cfw.c(89);
            this.cfw.s(i2);
            int type2 = node2.getType();
            if (type2 == 152 || type2 == 153 || type2 == 164) {
                generateExpression(node2.getFirstChild(), node);
            } else {
                generateExpression(node2, node);
            }
            this.cfw.c(83);
            node2 = node2.getNext();
            i2++;
        }
    }

    private void addNewObjectArray(int i) {
        if (i == 0) {
            short s = this.itsZeroArgArray;
            if (s >= 0) {
                this.cfw.g(s);
            } else {
                this.cfw.f("org/mozilla/javascript/ScriptRuntime", 178, "emptyArgs", "[Ljava/lang/Object;");
            }
        } else {
            this.cfw.s(i);
            this.cfw.e(189, "java/lang/Object");
        }
    }

    private void addObjectToDouble() {
        addScriptRuntimeInvoke("toNumber", "(Ljava/lang/Object;)D");
    }

    private void addOptRuntimeInvoke(String str, String str2) {
        this.cfw.m("org/mozilla/javascript/optimizer/OptRuntime", 184, str, str2);
    }

    private void addScriptRuntimeInvoke(String str, String str2) {
        this.cfw.m("org.mozilla.javascript.ScriptRuntime", 184, str, str2);
    }

    private void dcpLoadAsNumber(int i) {
        this.cfw.g(i);
        this.cfw.f("java/lang/Void", 178, "TYPE", "Ljava/lang/Class;");
        int b = this.cfw.b();
        this.cfw.d(Token.ARROW, b);
        l0 l0Var = this.cfw;
        short s = l0Var.m;
        l0Var.g(i);
        addObjectToDouble();
        int b2 = this.cfw.b();
        this.cfw.d(Token.LAST_TOKEN, b2);
        this.cfw.ag(b, s);
        this.cfw.i(i + 1);
        this.cfw.af(b2);
    }

    private void dcpLoadAsObject(int i) {
        this.cfw.g(i);
        this.cfw.f("java/lang/Void", 178, "TYPE", "Ljava/lang/Class;");
        int b = this.cfw.b();
        this.cfw.d(Token.ARROW, b);
        l0 l0Var = this.cfw;
        short s = l0Var.m;
        l0Var.g(i);
        int b2 = this.cfw.b();
        this.cfw.d(Token.LAST_TOKEN, b2);
        this.cfw.ag(b, s);
        this.cfw.i(i + 1);
        addDoubleWrap();
        this.cfw.af(b2);
    }

    private void decReferenceWordLocal(short s) {
        int[] iArr = this.locals;
        iArr[s] = iArr[s] - 1;
    }

    /* access modifiers changed from: private */
    public static String exceptionTypeToName(int i) {
        if (i == 0) {
            return "org/mozilla/javascript/JavaScriptException";
        }
        if (i == 1) {
            return "org/mozilla/javascript/EvaluatorException";
        }
        if (i == 2) {
            return "org/mozilla/javascript/EcmaError";
        }
        if (i == 3) {
            return "java/lang/Throwable";
        }
        if (i == 4) {
            return null;
        }
        throw Kit.codeBug();
    }

    private Node findNestedYield(Node node) {
        for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNext()) {
            if (firstChild.getType() == 73 || firstChild.getType() == 166) {
                return firstChild;
            }
            Node findNestedYield = findNestedYield(firstChild);
            if (findNestedYield != null) {
                return findNestedYield;
            }
        }
        return null;
    }

    private void genSimpleCompare(int i, int i2, int i3) {
        if (i2 != -1) {
            switch (i) {
                case 14:
                    this.cfw.c(Token.GET);
                    this.cfw.d(Token.CONST, i2);
                    break;
                case 15:
                    this.cfw.c(Token.GET);
                    this.cfw.d(Token.ARRAYCOMP, i2);
                    break;
                case 16:
                    this.cfw.c(Token.TO_DOUBLE);
                    this.cfw.d(Token.SETCONSTVAR, i2);
                    break;
                case 17:
                    this.cfw.c(Token.TO_DOUBLE);
                    this.cfw.d(Token.SETCONST, i2);
                    break;
                default:
                    throw Codegen.badTree();
            }
            if (i3 != -1) {
                this.cfw.d(Token.LAST_TOKEN, i3);
                return;
            }
            return;
        }
        throw Codegen.badTree();
    }

    private void generateActivationExit() {
        if (this.fnCurrent == null || this.hasVarsInRegs) {
            throw Kit.codeBug();
        }
        this.cfw.g(this.contextLocal);
        addScriptRuntimeInvoke("exitActivationFunction", "(Lorg/mozilla/javascript/Context;)V");
    }

    private void generateArrayLiteralFactory(Node node, int i) {
        initBodyGeneration();
        short s = this.firstFreeLocal;
        short s2 = (short) (s + 1);
        this.firstFreeLocal = s2;
        this.argsLocal = s;
        this.localsMax = s2;
        this.cfw.ap(this.codegen.getBodyMethodName(this.scriptOrFn) + "_literal" + i, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;", 2);
        visitArrayLiteral(node, node.getFirstChild(), true);
        this.cfw.c(176);
        this.cfw.aq((short) (this.localsMax + 1));
    }

    private void generateCallArgArray(Node node, Node node2, boolean z) {
        short s;
        int i = 0;
        for (Node node3 = node2; node3 != null; node3 = node3.getNext()) {
            i++;
        }
        if (i != 1 || (s = this.itsOneArgArray) < 0) {
            addNewObjectArray(i);
        } else {
            this.cfw.g(s);
        }
        for (int i2 = 0; i2 != i; i2++) {
            if (!this.isGenerator) {
                this.cfw.c(89);
                this.cfw.s(i2);
            }
            if (!z) {
                generateExpression(node2, node);
            } else {
                int nodeIsDirectCallParameter = nodeIsDirectCallParameter(node2);
                if (nodeIsDirectCallParameter >= 0) {
                    dcpLoadAsObject(nodeIsDirectCallParameter);
                } else {
                    generateExpression(node2, node);
                    if (node2.getIntProp(8, -1) == 0) {
                        addDoubleWrap();
                    }
                }
            }
            if (this.isGenerator) {
                short newWordLocal = getNewWordLocal();
                this.cfw.h(newWordLocal);
                this.cfw.e(192, "[Ljava/lang/Object;");
                this.cfw.c(89);
                this.cfw.s(i2);
                this.cfw.g(newWordLocal);
                releaseWordLocal(newWordLocal);
            }
            this.cfw.c(83);
            node2 = node2.getNext();
        }
    }

    private void generateCatchBlock(int i, short s, int i2, int i3, int i4) {
        if (i4 == 0) {
            i4 = this.cfw.b();
        }
        l0 l0Var = this.cfw;
        l0Var.m = 1;
        l0Var.af(i4);
        this.cfw.h(i3);
        this.cfw.g(s);
        this.cfw.h(this.variableObjectLocal);
        this.cfw.d(Token.LAST_TOKEN, i2);
    }

    private void generateCheckForThrowOrClose(int i, boolean z, int i2) {
        int b = this.cfw.b();
        int b2 = this.cfw.b();
        this.cfw.af(b);
        this.cfw.g(this.argsLocal);
        generateThrowJavaScriptException();
        this.cfw.af(b2);
        this.cfw.g(this.argsLocal);
        this.cfw.e(192, "java/lang/Throwable");
        this.cfw.c(191);
        if (i != -1) {
            this.cfw.af(i);
        }
        if (!z) {
            this.cfw.ah(this.generatorSwitch, i2);
        }
        this.cfw.l(this.operationLocal);
        this.cfw.o(2);
        this.cfw.d(Token.LETEXPR, b2);
        this.cfw.l(this.operationLocal);
        this.cfw.o(1);
        this.cfw.d(Token.LETEXPR, b);
    }

    private void generateEpilogue() {
        if (this.compilerEnv.isGenerateObserverCount()) {
            addInstructionCount();
        }
        if (this.isGenerator) {
            Map<Node, int[]> liveLocals = ((FunctionNode) this.scriptOrFn).getLiveLocals();
            if (liveLocals != null) {
                List<Node> resumptionPoints = ((FunctionNode) this.scriptOrFn).getResumptionPoints();
                for (int i = 0; i < resumptionPoints.size(); i++) {
                    Node node = resumptionPoints.get(i);
                    int[] iArr = liveLocals.get(node);
                    if (iArr != null) {
                        this.cfw.ah(this.generatorSwitch, getNextGeneratorState(node));
                        generateGetGeneratorLocalsState();
                        for (int i2 = 0; i2 < iArr.length; i2++) {
                            this.cfw.c(89);
                            this.cfw.o(i2);
                            this.cfw.c(50);
                            this.cfw.h(iArr[i2]);
                        }
                        this.cfw.c(87);
                        this.cfw.d(Token.LAST_TOKEN, getTargetLabel(node));
                    }
                }
            }
            Map<Node, FinallyReturnPoint> map = this.finallys;
            if (map != null) {
                for (Map.Entry next : map.entrySet()) {
                    if (((Node) next.getKey()).getType() == 126) {
                        FinallyReturnPoint finallyReturnPoint = (FinallyReturnPoint) next.getValue();
                        this.cfw.ag(finallyReturnPoint.tableLabel, 1);
                        int x = this.cfw.x(0, finallyReturnPoint.jsrPoints.size() - 1);
                        this.cfw.aj(x);
                        int i3 = 0;
                        for (int i4 = 0; i4 < finallyReturnPoint.jsrPoints.size(); i4++) {
                            this.cfw.ah(x, i3);
                            this.cfw.d(Token.LAST_TOKEN, finallyReturnPoint.jsrPoints.get(i4).intValue());
                            i3++;
                        }
                    }
                }
            }
        }
        int i5 = this.epilogueLabel;
        if (i5 != -1) {
            this.cfw.af(i5);
        }
        if (this.isGenerator) {
            if (((FunctionNode) this.scriptOrFn).getResumptionPoints() != null) {
                this.cfw.aj(this.generatorSwitch);
            }
            generateSetGeneratorResumptionPoint(-1);
            this.cfw.g(this.variableObjectLocal);
            this.cfw.g(this.generatorStateLocal);
            addOptRuntimeInvoke("throwStopIteration", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            Codegen.pushUndefined(this.cfw);
            this.cfw.c(176);
        } else if (this.hasVarsInRegs) {
            this.cfw.c(176);
        } else if (this.fnCurrent == null) {
            this.cfw.g(this.popvLocal);
            this.cfw.c(176);
        } else {
            generateActivationExit();
            this.cfw.c(176);
            int b = this.cfw.b();
            l0 l0Var = this.cfw;
            l0Var.m = 1;
            l0Var.af(b);
            short newWordLocal = getNewWordLocal();
            this.cfw.h(newWordLocal);
            generateActivationExit();
            this.cfw.g(newWordLocal);
            releaseWordLocal(newWordLocal);
            this.cfw.c(191);
            this.cfw.j(this.enterAreaStartLabel, this.epilogueLabel, b, (String) null);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x02ec, code lost:
        r2 = r1.cfw.b();
        r3 = r1.cfw.b();
        visitIfJumpRelOp(r0, r4, r2, r3);
        addJumpedBooleanWrap(r2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x045b, code lost:
        visitSetElem(r3, r0, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x0460, code lost:
        visitSetProp(r3, r0, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:236:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006f, code lost:
        if (r4 == null) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0071, code lost:
        generateExpression(r4, r0);
        r4 = r4.getNext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0079, code lost:
        r1.cfw.g(r1.contextLocal);
        r1.cfw.g(r1.variableObjectLocal);
        r1.cfw.t(r18.getString());
        addScriptRuntimeInvoke("bind", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)Lorg/mozilla/javascript/Scriptable;");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void generateExpression(org.mozilla.javascript.Node r18, org.mozilla.javascript.Node r19) {
        /*
            r17 = this;
            r1 = r17
            r0 = r18
            r2 = r19
            int r3 = r18.getType()
            org.mozilla.javascript.Node r4 = r18.getFirstChild()
            r5 = 90
            if (r3 == r5) goto L_0x0581
            r5 = 103(0x67, float:1.44E-43)
            java.lang.String r6 = "(Ljava/lang/Object;)Z"
            java.lang.String r7 = "toBoolean"
            if (r3 == r5) goto L_0x0544
            r5 = 4
            r8 = 110(0x6e, float:1.54E-43)
            r9 = 1
            if (r3 == r8) goto L_0x0519
            r8 = 127(0x7f, float:1.78E-43)
            if (r3 == r8) goto L_0x0508
            r8 = 143(0x8f, float:2.0E-43)
            java.lang.String r10 = "refGet"
            java.lang.String r11 = "(Lorg/mozilla/javascript/Ref;Lorg/mozilla/javascript/Context;)Ljava/lang/Object;"
            if (r3 == r8) goto L_0x04d2
            r8 = 147(0x93, float:2.06E-43)
            if (r3 == r8) goto L_0x04cd
            r8 = 160(0xa0, float:2.24E-43)
            if (r3 == r8) goto L_0x04b6
            r8 = 166(0xa6, float:2.33E-43)
            if (r3 == r8) goto L_0x04b1
            r8 = 150(0x96, float:2.1E-43)
            if (r3 == r8) goto L_0x0488
            r8 = 151(0x97, float:2.12E-43)
            if (r3 == r8) goto L_0x0480
            r8 = 178(0xb2, float:2.5E-43)
            java.lang.String r12 = "TRUE"
            java.lang.String r13 = "FALSE"
            java.lang.String r14 = "Ljava/lang/Boolean;"
            java.lang.String r15 = "java/lang/Boolean"
            switch(r3) {
                case 8: goto L_0x02d2;
                case 9: goto L_0x02cd;
                case 10: goto L_0x02cd;
                case 11: goto L_0x02cd;
                case 12: goto L_0x02b9;
                case 13: goto L_0x02b9;
                case 14: goto L_0x02ec;
                case 15: goto L_0x02ec;
                case 16: goto L_0x02ec;
                case 17: goto L_0x02ec;
                case 18: goto L_0x02cd;
                case 19: goto L_0x02cd;
                case 20: goto L_0x02cd;
                case 21: goto L_0x025a;
                case 22: goto L_0x0253;
                case 23: goto L_0x024c;
                case 24: goto L_0x023e;
                case 25: goto L_0x023e;
                case 26: goto L_0x0202;
                case 27: goto L_0x01df;
                case 28: goto L_0x01c9;
                case 29: goto L_0x01c9;
                case 30: goto L_0x019e;
                case 31: goto L_0x0175;
                case 32: goto L_0x0169;
                case 33: goto L_0x0164;
                case 34: goto L_0x0164;
                case 35: goto L_0x0460;
                case 36: goto L_0x0131;
                case 37: goto L_0x045b;
                case 38: goto L_0x019e;
                case 39: goto L_0x0111;
                case 40: goto L_0x00f4;
                case 41: goto L_0x00e9;
                case 42: goto L_0x00e2;
                case 43: goto L_0x00d9;
                case 44: goto L_0x00d2;
                case 45: goto L_0x00cb;
                case 46: goto L_0x02b9;
                case 47: goto L_0x02b9;
                case 48: goto L_0x0099;
                case 49: goto L_0x006f;
                default: goto L_0x004d;
            }
        L_0x004d:
            switch(r3) {
                case 52: goto L_0x02ec;
                case 53: goto L_0x02ec;
                case 54: goto L_0x02e1;
                case 55: goto L_0x02dc;
                case 56: goto L_0x02d7;
                default: goto L_0x0050;
            }
        L_0x0050:
            java.lang.String r2 = "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;)Ljava/lang/Object;"
            switch(r3) {
                case 62: goto L_0x0309;
                case 63: goto L_0x0309;
                case 64: goto L_0x0300;
                default: goto L_0x0055;
            }
        L_0x0055:
            java.lang.String r5 = "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;)Ljava/lang/String;"
            switch(r3) {
                case 66: goto L_0x0415;
                case 67: goto L_0x040f;
                case 68: goto L_0x0400;
                case 69: goto L_0x04d2;
                case 70: goto L_0x03ef;
                case 71: goto L_0x03d4;
                case 72: goto L_0x03ad;
                case 73: goto L_0x04b1;
                case 74: goto L_0x03a8;
                case 75: goto L_0x0397;
                case 76: goto L_0x0386;
                case 77: goto L_0x0375;
                case 78: goto L_0x032d;
                case 79: goto L_0x032d;
                case 80: goto L_0x032d;
                case 81: goto L_0x032d;
                default: goto L_0x005a;
            }
        L_0x005a:
            switch(r3) {
                case 105: goto L_0x0420;
                case 106: goto L_0x0420;
                case 107: goto L_0x041b;
                case 108: goto L_0x041b;
                default: goto L_0x005d;
            }
        L_0x005d:
            switch(r3) {
                case 138: goto L_0x0465;
                case 139: goto L_0x059e;
                case 140: goto L_0x0460;
                case 141: goto L_0x045b;
                default: goto L_0x0060;
            }
        L_0x0060:
            switch(r3) {
                case 156: goto L_0x047b;
                case 157: goto L_0x0476;
                case 158: goto L_0x046a;
                default: goto L_0x0063;
            }
        L_0x0063:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r2 = "Unexpected node type "
            java.lang.String r2 = defpackage.y2.f(r2, r3)
            r0.<init>(r2)
            throw r0
        L_0x006f:
            if (r4 == 0) goto L_0x0079
            r1.generateExpression(r4, r0)
            org.mozilla.javascript.Node r4 = r4.getNext()
            goto L_0x006f
        L_0x0079:
            l0 r2 = r1.cfw
            short r3 = r1.contextLocal
            r2.g(r3)
            l0 r2 = r1.cfw
            short r3 = r1.variableObjectLocal
            r2.g(r3)
            l0 r2 = r1.cfw
            java.lang.String r0 = r18.getString()
            r2.t(r0)
            java.lang.String r0 = "bind"
            java.lang.String r2 = "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)Lorg/mozilla/javascript/Scriptable;"
            r1.addScriptRuntimeInvoke(r0, r2)
            goto L_0x059e
        L_0x0099:
            l0 r2 = r1.cfw
            short r3 = r1.contextLocal
            r2.g(r3)
            l0 r2 = r1.cfw
            short r3 = r1.variableObjectLocal
            r2.g(r3)
            int r0 = r0.getExistingIntProp(r5)
            l0 r2 = r1.cfw
            org.mozilla.javascript.optimizer.Codegen r3 = r1.codegen
            java.lang.String r4 = r3.mainClassName
            org.mozilla.javascript.ast.ScriptNode r5 = r1.scriptOrFn
            java.lang.String r0 = r3.getCompiledRegexpName(r5, r0)
            java.lang.String r3 = "Ljava/lang/Object;"
            r2.f(r4, r8, r0, r3)
            l0 r0 = r1.cfw
            java.lang.String r2 = "org/mozilla/javascript/ScriptRuntime"
            r3 = 184(0xb8, float:2.58E-43)
            java.lang.String r4 = "wrapRegExp"
            java.lang.String r5 = "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;"
            r0.m(r2, r3, r4, r5)
            goto L_0x059e
        L_0x00cb:
            l0 r0 = r1.cfw
            r0.f(r15, r8, r12, r14)
            goto L_0x059e
        L_0x00d2:
            l0 r0 = r1.cfw
            r0.f(r15, r8, r13, r14)
            goto L_0x059e
        L_0x00d9:
            l0 r0 = r1.cfw
            short r2 = r1.thisObjLocal
            r0.g(r2)
            goto L_0x059e
        L_0x00e2:
            l0 r0 = r1.cfw
            r0.c(r9)
            goto L_0x059e
        L_0x00e9:
            l0 r2 = r1.cfw
            java.lang.String r0 = r18.getString()
            r2.t(r0)
            goto L_0x059e
        L_0x00f4:
            double r2 = r18.getDouble()
            r4 = 8
            r5 = -1
            int r0 = r0.getIntProp(r4, r5)
            if (r0 == r5) goto L_0x0108
            l0 r0 = r1.cfw
            r0.r(r2)
            goto L_0x059e
        L_0x0108:
            org.mozilla.javascript.optimizer.Codegen r0 = r1.codegen
            l0 r4 = r1.cfw
            r0.pushNumberAsObject(r4, r2)
            goto L_0x059e
        L_0x0111:
            l0 r2 = r1.cfw
            short r3 = r1.contextLocal
            r2.g(r3)
            l0 r2 = r1.cfw
            short r3 = r1.variableObjectLocal
            r2.g(r3)
            l0 r2 = r1.cfw
            java.lang.String r0 = r18.getString()
            r2.t(r0)
            java.lang.String r0 = "name"
            java.lang.String r2 = "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)Ljava/lang/Object;"
            r1.addScriptRuntimeInvoke(r0, r2)
            goto L_0x059e
        L_0x0131:
            r1.generateExpression(r4, r0)
            org.mozilla.javascript.Node r2 = r4.getNext()
            r1.generateExpression(r2, r0)
            l0 r2 = r1.cfw
            short r3 = r1.contextLocal
            r2.g(r3)
            r2 = 8
            r3 = -1
            int r0 = r0.getIntProp(r2, r3)
            if (r0 == r3) goto L_0x0154
            java.lang.String r0 = "getObjectIndex"
            java.lang.String r2 = "(Ljava/lang/Object;DLorg/mozilla/javascript/Context;)Ljava/lang/Object;"
            r1.addScriptRuntimeInvoke(r0, r2)
            goto L_0x059e
        L_0x0154:
            l0 r0 = r1.cfw
            short r2 = r1.variableObjectLocal
            r0.g(r2)
            java.lang.String r0 = "getObjectElem"
            java.lang.String r2 = "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;"
            r1.addScriptRuntimeInvoke(r0, r2)
            goto L_0x059e
        L_0x0164:
            r1.visitGetProp(r0, r4)
            goto L_0x059e
        L_0x0169:
            r1.generateExpression(r4, r0)
            java.lang.String r0 = "typeof"
            java.lang.String r2 = "(Ljava/lang/Object;)Ljava/lang/String;"
            r1.addScriptRuntimeInvoke(r0, r2)
            goto L_0x059e
        L_0x0175:
            int r2 = r4.getType()
            r3 = 49
            if (r2 != r3) goto L_0x017e
            goto L_0x017f
        L_0x017e:
            r9 = 0
        L_0x017f:
            r1.generateExpression(r4, r0)
            org.mozilla.javascript.Node r2 = r4.getNext()
            r1.generateExpression(r2, r0)
            l0 r0 = r1.cfw
            short r2 = r1.contextLocal
            r0.g(r2)
            l0 r0 = r1.cfw
            r0.u(r9)
            java.lang.String r0 = "delete"
            java.lang.String r2 = "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Z)Ljava/lang/Object;"
            r1.addScriptRuntimeInvoke(r0, r2)
            goto L_0x059e
        L_0x019e:
            r2 = 10
            r5 = 0
            int r2 = r0.getIntProp(r2, r5)
            if (r2 != 0) goto L_0x01c4
            r2 = 9
            java.lang.Object r2 = r0.getProp(r2)
            org.mozilla.javascript.optimizer.OptFunctionNode r2 = (org.mozilla.javascript.optimizer.OptFunctionNode) r2
            if (r2 == 0) goto L_0x01b6
            r1.visitOptimizedCall(r0, r2, r3, r4)
            goto L_0x059e
        L_0x01b6:
            r2 = 38
            if (r3 != r2) goto L_0x01bf
            r1.visitStandardCall(r0, r4)
            goto L_0x059e
        L_0x01bf:
            r1.visitStandardNew(r0, r4)
            goto L_0x059e
        L_0x01c4:
            r1.visitSpecialCall(r0, r3, r2, r4)
            goto L_0x059e
        L_0x01c9:
            r1.generateExpression(r4, r0)
            r17.addObjectToDouble()
            r0 = 29
            if (r3 != r0) goto L_0x01da
            l0 r0 = r1.cfw
            r2 = 119(0x77, float:1.67E-43)
            r0.c(r2)
        L_0x01da:
            r17.addDoubleWrap()
            goto L_0x059e
        L_0x01df:
            r1.generateExpression(r4, r0)
            java.lang.String r0 = "toInt32"
            java.lang.String r2 = "(Ljava/lang/Object;)I"
            r1.addScriptRuntimeInvoke(r0, r2)
            l0 r0 = r1.cfw
            r2 = -1
            r0.s(r2)
            l0 r0 = r1.cfw
            r2 = 130(0x82, float:1.82E-43)
            r0.c(r2)
            l0 r0 = r1.cfw
            r2 = 135(0x87, float:1.89E-43)
            r0.c(r2)
            r17.addDoubleWrap()
            goto L_0x059e
        L_0x0202:
            l0 r2 = r1.cfw
            int r2 = r2.b()
            l0 r3 = r1.cfw
            int r3 = r3.b()
            l0 r5 = r1.cfw
            int r5 = r5.b()
            r1.generateIfJump(r4, r0, r2, r3)
            l0 r0 = r1.cfw
            r0.af(r2)
            l0 r0 = r1.cfw
            r0.f(r15, r8, r13, r14)
            l0 r0 = r1.cfw
            r2 = 167(0xa7, float:2.34E-43)
            r0.d(r2, r5)
            l0 r0 = r1.cfw
            r0.af(r3)
            l0 r0 = r1.cfw
            r0.f(r15, r8, r12, r14)
            l0 r0 = r1.cfw
            r0.af(r5)
            l0 r0 = r1.cfw
            r0.ab()
            goto L_0x059e
        L_0x023e:
            r5 = 24
            if (r3 != r5) goto L_0x0245
            r3 = 111(0x6f, float:1.56E-43)
            goto L_0x0247
        L_0x0245:
            r3 = 115(0x73, float:1.61E-43)
        L_0x0247:
            r1.visitArithmetic(r0, r3, r4, r2)
            goto L_0x059e
        L_0x024c:
            r3 = 107(0x6b, float:1.5E-43)
            r1.visitArithmetic(r0, r3, r4, r2)
            goto L_0x059e
        L_0x0253:
            r3 = 103(0x67, float:1.44E-43)
            r1.visitArithmetic(r0, r3, r4, r2)
            goto L_0x059e
        L_0x025a:
            r1.generateExpression(r4, r0)
            org.mozilla.javascript.Node r2 = r4.getNext()
            r1.generateExpression(r2, r0)
            r2 = 8
            r3 = -1
            int r0 = r0.getIntProp(r2, r3)
            if (r0 == 0) goto L_0x02b0
            java.lang.String r2 = "add"
            if (r0 == r9) goto L_0x02a9
            r3 = 2
            if (r0 == r3) goto L_0x02a2
            int r0 = r4.getType()
            r3 = 41
            if (r0 != r3) goto L_0x0283
            java.lang.String r0 = "(Ljava/lang/CharSequence;Ljava/lang/Object;)Ljava/lang/CharSequence;"
            r1.addScriptRuntimeInvoke(r2, r0)
            goto L_0x059e
        L_0x0283:
            org.mozilla.javascript.Node r0 = r4.getNext()
            int r0 = r0.getType()
            if (r0 != r3) goto L_0x0294
            java.lang.String r0 = "(Ljava/lang/Object;Ljava/lang/CharSequence;)Ljava/lang/CharSequence;"
            r1.addScriptRuntimeInvoke(r2, r0)
            goto L_0x059e
        L_0x0294:
            l0 r0 = r1.cfw
            short r3 = r1.contextLocal
            r0.g(r3)
            java.lang.String r0 = "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;)Ljava/lang/Object;"
            r1.addScriptRuntimeInvoke(r2, r0)
            goto L_0x059e
        L_0x02a2:
            java.lang.String r0 = "(Ljava/lang/Object;D)Ljava/lang/Object;"
            r1.addOptRuntimeInvoke(r2, r0)
            goto L_0x059e
        L_0x02a9:
            java.lang.String r0 = "(DLjava/lang/Object;)Ljava/lang/Object;"
            r1.addOptRuntimeInvoke(r2, r0)
            goto L_0x059e
        L_0x02b0:
            l0 r0 = r1.cfw
            r2 = 99
            r0.c(r2)
            goto L_0x059e
        L_0x02b9:
            l0 r2 = r1.cfw
            int r2 = r2.b()
            l0 r3 = r1.cfw
            int r3 = r3.b()
            r1.visitIfJumpEqOp(r0, r4, r2, r3)
            r1.addJumpedBooleanWrap(r2, r3)
            goto L_0x059e
        L_0x02cd:
            r1.visitBitOp(r0, r3, r4)
            goto L_0x059e
        L_0x02d2:
            r1.visitSetName(r0, r4)
            goto L_0x059e
        L_0x02d7:
            r1.visitSetVar(r0, r4, r9)
            goto L_0x059e
        L_0x02dc:
            r17.visitGetVar(r18)
            goto L_0x059e
        L_0x02e1:
            l0 r2 = r1.cfw
            int r0 = getLocalBlockRegister(r18)
            r2.g(r0)
            goto L_0x059e
        L_0x02ec:
            l0 r2 = r1.cfw
            int r2 = r2.b()
            l0 r3 = r1.cfw
            int r3 = r3.b()
            r1.visitIfJumpRelOp(r0, r4, r2, r3)
            r1.addJumpedBooleanWrap(r2, r3)
            goto L_0x059e
        L_0x0300:
            l0 r0 = r1.cfw
            r2 = 42
            r0.c(r2)
            goto L_0x059e
        L_0x0309:
            int r0 = getLocalBlockRegister(r18)
            l0 r4 = r1.cfw
            r4.g(r0)
            r0 = 62
            if (r3 != r0) goto L_0x031f
            java.lang.String r0 = "enumNext"
            java.lang.String r2 = "(Ljava/lang/Object;)Ljava/lang/Boolean;"
            r1.addScriptRuntimeInvoke(r0, r2)
            goto L_0x059e
        L_0x031f:
            l0 r0 = r1.cfw
            short r3 = r1.contextLocal
            r0.g(r3)
            java.lang.String r0 = "enumId"
            r1.addScriptRuntimeInvoke(r0, r2)
            goto L_0x059e
        L_0x032d:
            r2 = 16
            r5 = 0
            int r2 = r0.getIntProp(r2, r5)
        L_0x0334:
            r1.generateExpression(r4, r0)
            org.mozilla.javascript.Node r4 = r4.getNext()
            if (r4 != 0) goto L_0x0334
            l0 r0 = r1.cfw
            short r4 = r1.contextLocal
            r0.g(r4)
            java.lang.String r0 = "nameRef"
            java.lang.String r4 = "memberRef"
            switch(r3) {
                case 78: goto L_0x0367;
                case 79: goto L_0x0364;
                case 80: goto L_0x035a;
                case 81: goto L_0x0350;
                default: goto L_0x034b;
            }
        L_0x034b:
            java.lang.RuntimeException r0 = org.mozilla.javascript.Kit.codeBug()
            throw r0
        L_0x0350:
            l0 r3 = r1.cfw
            short r4 = r1.variableObjectLocal
            r3.g(r4)
            java.lang.String r3 = "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;I)Lorg/mozilla/javascript/Ref;"
            goto L_0x036b
        L_0x035a:
            l0 r3 = r1.cfw
            short r4 = r1.variableObjectLocal
            r3.g(r4)
            java.lang.String r3 = "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;I)Lorg/mozilla/javascript/Ref;"
            goto L_0x036b
        L_0x0364:
            java.lang.String r0 = "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;I)Lorg/mozilla/javascript/Ref;"
            goto L_0x0369
        L_0x0367:
            java.lang.String r0 = "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;I)Lorg/mozilla/javascript/Ref;"
        L_0x0369:
            r3 = r0
            r0 = r4
        L_0x036b:
            l0 r4 = r1.cfw
            r4.s(r2)
            r1.addScriptRuntimeInvoke(r0, r3)
            goto L_0x059e
        L_0x0375:
            r1.generateExpression(r4, r0)
            l0 r0 = r1.cfw
            short r2 = r1.contextLocal
            r0.g(r2)
            java.lang.String r0 = "escapeTextValue"
            r1.addScriptRuntimeInvoke(r0, r5)
            goto L_0x059e
        L_0x0386:
            r1.generateExpression(r4, r0)
            l0 r0 = r1.cfw
            short r2 = r1.contextLocal
            r0.g(r2)
            java.lang.String r0 = "escapeAttributeValue"
            r1.addScriptRuntimeInvoke(r0, r5)
            goto L_0x059e
        L_0x0397:
            r1.generateExpression(r4, r0)
            l0 r0 = r1.cfw
            short r3 = r1.contextLocal
            r0.g(r3)
            java.lang.String r0 = "setDefaultNamespace"
            r1.addScriptRuntimeInvoke(r0, r2)
            goto L_0x059e
        L_0x03a8:
            r1.visitStrictSetName(r0, r4)
            goto L_0x059e
        L_0x03ad:
            r2 = 17
            java.lang.Object r2 = r0.getProp(r2)
            java.lang.String r2 = (java.lang.String) r2
            r1.generateExpression(r4, r0)
            l0 r0 = r1.cfw
            r0.t(r2)
            l0 r0 = r1.cfw
            short r2 = r1.contextLocal
            r0.g(r2)
            l0 r0 = r1.cfw
            short r2 = r1.variableObjectLocal
            r0.g(r2)
            java.lang.String r0 = "specialRef"
            java.lang.String r2 = "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Ref;"
            r1.addScriptRuntimeInvoke(r0, r2)
            goto L_0x059e
        L_0x03d4:
            r1.generateFunctionAndThisObj(r4, r0)
            org.mozilla.javascript.Node r2 = r4.getNext()
            r3 = 0
            r1.generateCallArgArray(r0, r2, r3)
            l0 r0 = r1.cfw
            short r2 = r1.contextLocal
            r0.g(r2)
            java.lang.String r0 = "callRef"
            java.lang.String r2 = "(Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;Lorg/mozilla/javascript/Context;)Lorg/mozilla/javascript/Ref;"
            r1.addScriptRuntimeInvoke(r0, r2)
            goto L_0x059e
        L_0x03ef:
            r1.generateExpression(r4, r0)
            l0 r0 = r1.cfw
            short r2 = r1.contextLocal
            r0.g(r2)
            java.lang.String r0 = "refDel"
            r1.addScriptRuntimeInvoke(r0, r11)
            goto L_0x059e
        L_0x0400:
            r1.generateExpression(r4, r0)
            l0 r0 = r1.cfw
            short r2 = r1.contextLocal
            r0.g(r2)
            r1.addScriptRuntimeInvoke(r10, r11)
            goto L_0x059e
        L_0x040f:
            r2 = 0
            r1.visitObjectLiteral(r0, r4, r2)
            goto L_0x059e
        L_0x0415:
            r2 = 0
            r1.visitArrayLiteral(r0, r4, r2)
            goto L_0x059e
        L_0x041b:
            r17.visitIncDec(r18)
            goto L_0x059e
        L_0x0420:
            r1.generateExpression(r4, r0)
            l0 r2 = r1.cfw
            r5 = 89
            r2.c(r5)
            r1.addScriptRuntimeInvoke(r7, r6)
            l0 r2 = r1.cfw
            int r2 = r2.b()
            r5 = 106(0x6a, float:1.49E-43)
            if (r3 != r5) goto L_0x043f
            l0 r3 = r1.cfw
            r5 = 153(0x99, float:2.14E-43)
            r3.d(r5, r2)
            goto L_0x0446
        L_0x043f:
            l0 r3 = r1.cfw
            r5 = 154(0x9a, float:2.16E-43)
            r3.d(r5, r2)
        L_0x0446:
            l0 r3 = r1.cfw
            r5 = 87
            r3.c(r5)
            org.mozilla.javascript.Node r3 = r4.getNext()
            r1.generateExpression(r3, r0)
            l0 r0 = r1.cfw
            r0.af(r2)
            goto L_0x059e
        L_0x045b:
            r1.visitSetElem(r3, r0, r4)
            goto L_0x059e
        L_0x0460:
            r1.visitSetProp(r3, r0, r4)
            goto L_0x059e
        L_0x0465:
            r17.visitTypeofname(r18)
            goto L_0x059e
        L_0x046a:
            org.mozilla.javascript.Node r2 = r4.getNext()
            r1.generateStatement(r4)
            r1.generateExpression(r2, r0)
            goto L_0x059e
        L_0x0476:
            r1.visitSetConstVar(r0, r4, r9)
            goto L_0x059e
        L_0x047b:
            r1.visitSetConst(r0, r4)
            goto L_0x059e
        L_0x0480:
            r1.generateExpression(r4, r0)
            r17.addObjectToDouble()
            goto L_0x059e
        L_0x0488:
            int r2 = r4.getType()
            r3 = 40
            if (r2 != r3) goto L_0x0498
            r2 = 8
            r3 = -1
            int r5 = r4.getIntProp(r2, r3)
            goto L_0x049c
        L_0x0498:
            r2 = 8
            r3 = -1
            r5 = -1
        L_0x049c:
            if (r5 == r3) goto L_0x04a9
            r4.removeProp(r2)
            r1.generateExpression(r4, r0)
            r4.putIntProp(r2, r5)
            goto L_0x059e
        L_0x04a9:
            r1.generateExpression(r4, r0)
            r17.addDoubleWrap()
            goto L_0x059e
        L_0x04b1:
            r1.generateYieldPoint(r0, r9)
            goto L_0x059e
        L_0x04b6:
            org.mozilla.javascript.Node r0 = r4.getNext()
            org.mozilla.javascript.Node r2 = r0.getNext()
            r1.generateStatement(r4)
            org.mozilla.javascript.Node r3 = r0.getFirstChild()
            r1.generateExpression(r3, r0)
            r1.generateStatement(r2)
            goto L_0x059e
        L_0x04cd:
            r1.visitDotQuery(r0, r4)
            goto L_0x059e
        L_0x04d2:
            r1.generateExpression(r4, r0)
            org.mozilla.javascript.Node r2 = r4.getNext()
            r4 = 143(0x8f, float:2.0E-43)
            if (r3 != r4) goto L_0x04ee
            l0 r3 = r1.cfw
            r4 = 89
            r3.c(r4)
            l0 r3 = r1.cfw
            short r4 = r1.contextLocal
            r3.g(r4)
            r1.addScriptRuntimeInvoke(r10, r11)
        L_0x04ee:
            r1.generateExpression(r2, r0)
            l0 r0 = r1.cfw
            short r2 = r1.contextLocal
            r0.g(r2)
            l0 r0 = r1.cfw
            short r2 = r1.variableObjectLocal
            r0.g(r2)
            java.lang.String r0 = "refSet"
            java.lang.String r2 = "(Lorg/mozilla/javascript/Ref;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;"
            r1.addScriptRuntimeInvoke(r0, r2)
            goto L_0x059e
        L_0x0508:
            r1.generateExpression(r4, r0)
            l0 r0 = r1.cfw
            r2 = 87
            r0.c(r2)
            l0 r0 = r1.cfw
            org.mozilla.javascript.optimizer.Codegen.pushUndefined(r0)
            goto L_0x059e
        L_0x0519:
            org.mozilla.javascript.optimizer.OptFunctionNode r3 = r1.fnCurrent
            if (r3 != 0) goto L_0x0525
            int r2 = r19.getType()
            r3 = 137(0x89, float:1.92E-43)
            if (r2 == r3) goto L_0x059e
        L_0x0525:
            int r0 = r0.getExistingIntProp(r9)
            org.mozilla.javascript.ast.ScriptNode r2 = r1.scriptOrFn
            org.mozilla.javascript.optimizer.OptFunctionNode r0 = org.mozilla.javascript.optimizer.OptFunctionNode.get(r2, r0)
            org.mozilla.javascript.ast.FunctionNode r2 = r0.fnode
            int r2 = r2.getFunctionType()
            r3 = 2
            if (r2 == r3) goto L_0x0540
            if (r2 != r5) goto L_0x053b
            goto L_0x0540
        L_0x053b:
            java.lang.RuntimeException r0 = org.mozilla.javascript.optimizer.Codegen.badTree()
            throw r0
        L_0x0540:
            r1.visitFunction(r0, r2)
            goto L_0x059e
        L_0x0544:
            org.mozilla.javascript.Node r2 = r4.getNext()
            org.mozilla.javascript.Node r3 = r2.getNext()
            r1.generateExpression(r4, r0)
            r1.addScriptRuntimeInvoke(r7, r6)
            l0 r4 = r1.cfw
            int r4 = r4.b()
            l0 r5 = r1.cfw
            r6 = 153(0x99, float:2.14E-43)
            r5.d(r6, r4)
            l0 r5 = r1.cfw
            short r5 = r5.m
            r1.generateExpression(r2, r0)
            l0 r2 = r1.cfw
            int r2 = r2.b()
            l0 r6 = r1.cfw
            r7 = 167(0xa7, float:2.34E-43)
            r6.d(r7, r2)
            l0 r6 = r1.cfw
            r6.ag(r4, r5)
            r1.generateExpression(r3, r0)
            l0 r0 = r1.cfw
            r0.af(r2)
            goto L_0x059e
        L_0x0581:
            org.mozilla.javascript.Node r2 = r4.getNext()
        L_0x0585:
            r16 = r4
            r4 = r2
            r2 = r16
            if (r4 == 0) goto L_0x059b
            r1.generateExpression(r2, r0)
            l0 r2 = r1.cfw
            r3 = 87
            r2.c(r3)
            org.mozilla.javascript.Node r2 = r4.getNext()
            goto L_0x0585
        L_0x059b:
            r1.generateExpression(r2, r0)     // Catch:{ all -> 0x059f }
        L_0x059e:
            return
        L_0x059f:
            r0 = move-exception
            r2 = r0
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.optimizer.BodyCodegen.generateExpression(org.mozilla.javascript.Node, org.mozilla.javascript.Node):void");
    }

    private void generateFunctionAndThisObj(Node node, Node node2) {
        int type = node.getType();
        int type2 = node.getType();
        if (type2 != 33) {
            if (type2 == 34) {
                throw Kit.codeBug();
            } else if (type2 != 36) {
                if (type2 != 39) {
                    generateExpression(node, node2);
                    this.cfw.g(this.contextLocal);
                    addScriptRuntimeInvoke("getValueFunctionAndThis", "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;)Lorg/mozilla/javascript/Callable;");
                } else {
                    this.cfw.t(node.getString());
                    this.cfw.g(this.contextLocal);
                    this.cfw.g(this.variableObjectLocal);
                    addScriptRuntimeInvoke("getNameFunctionAndThis", "(Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Callable;");
                }
                this.cfw.g(this.contextLocal);
                addScriptRuntimeInvoke("lastStoredScriptable", "(Lorg/mozilla/javascript/Context;)Lorg/mozilla/javascript/Scriptable;");
            }
        }
        Node firstChild = node.getFirstChild();
        generateExpression(firstChild, node);
        Node next = firstChild.getNext();
        if (type == 33) {
            this.cfw.t(next.getString());
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            addScriptRuntimeInvoke("getPropFunctionAndThis", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Callable;");
        } else {
            generateExpression(next, node);
            if (node.getIntProp(8, -1) != -1) {
                addDoubleWrap();
            }
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            addScriptRuntimeInvoke("getElemFunctionAndThis", "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Callable;");
        }
        this.cfw.g(this.contextLocal);
        addScriptRuntimeInvoke("lastStoredScriptable", "(Lorg/mozilla/javascript/Context;)Lorg/mozilla/javascript/Scriptable;");
    }

    private void generateGenerator() {
        this.cfw.ap(this.codegen.getBodyMethodName(this.scriptOrFn), this.codegen.getBodyMethodSignature(this.scriptOrFn), 10);
        initBodyGeneration();
        short s = this.firstFreeLocal;
        short s2 = (short) (s + 1);
        this.firstFreeLocal = s2;
        this.argsLocal = s;
        this.localsMax = s2;
        if (this.fnCurrent != null) {
            this.cfw.g(this.funObjLocal);
            this.cfw.m("org/mozilla/javascript/Scriptable", 185, "getParentScope", "()Lorg/mozilla/javascript/Scriptable;");
            this.cfw.h(this.variableObjectLocal);
        }
        this.cfw.g(this.funObjLocal);
        this.cfw.g(this.variableObjectLocal);
        this.cfw.g(this.argsLocal);
        this.cfw.u(this.scriptOrFn.isInStrictMode());
        addScriptRuntimeInvoke("createFunctionActivation", "(Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;Z)Lorg/mozilla/javascript/Scriptable;");
        this.cfw.h(this.variableObjectLocal);
        this.cfw.e(187, this.codegen.mainClassName);
        this.cfw.c(89);
        this.cfw.g(this.variableObjectLocal);
        this.cfw.g(this.contextLocal);
        this.cfw.s(this.scriptOrFnIndex);
        this.cfw.m(this.codegen.mainClassName, 183, "<init>", "(Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;I)V");
        generateNestedFunctionInits();
        this.cfw.g(this.variableObjectLocal);
        this.cfw.g(this.thisObjLocal);
        this.cfw.o(this.maxLocals);
        this.cfw.o(this.maxStack);
        addOptRuntimeInvoke("createNativeGenerator", "(Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;II)Lorg/mozilla/javascript/Scriptable;");
        this.cfw.c(176);
        this.cfw.aq((short) (this.localsMax + 1));
    }

    private void generateGetGeneratorLocalsState() {
        this.cfw.g(this.generatorStateLocal);
        addOptRuntimeInvoke("getGeneratorLocalsState", "(Ljava/lang/Object;)[Ljava/lang/Object;");
    }

    private void generateGetGeneratorResumptionPoint() {
        this.cfw.g(this.generatorStateLocal);
        this.cfw.f("org/mozilla/javascript/optimizer/OptRuntime$GeneratorState", Context.VERSION_1_8, "resumptionPoint", "I");
    }

    private void generateGetGeneratorStackState() {
        this.cfw.g(this.generatorStateLocal);
        addOptRuntimeInvoke("getGeneratorStackState", "(Ljava/lang/Object;)[Ljava/lang/Object;");
    }

    private void generateIfJump(Node node, Node node2, int i, int i2) {
        int type = node.getType();
        Node firstChild = node.getFirstChild();
        if (type != 26) {
            if (!(type == 46 || type == 47)) {
                if (!(type == 52 || type == 53)) {
                    if (type == 105 || type == 106) {
                        int b = this.cfw.b();
                        if (type == 106) {
                            generateIfJump(firstChild, node, b, i2);
                        } else {
                            generateIfJump(firstChild, node, i, b);
                        }
                        this.cfw.af(b);
                        generateIfJump(firstChild.getNext(), node, i, i2);
                        return;
                    }
                    switch (type) {
                        case 12:
                        case 13:
                            break;
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                            break;
                        default:
                            generateExpression(node, node2);
                            addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)Z");
                            this.cfw.d(Token.LET, i);
                            this.cfw.d(Token.LAST_TOKEN, i2);
                            return;
                    }
                }
                visitIfJumpRelOp(node, firstChild, i, i2);
                return;
            }
            visitIfJumpEqOp(node, firstChild, i, i2);
            return;
        }
        generateIfJump(firstChild, node, i2, i);
    }

    private void generateIntegerUnwrap() {
        this.cfw.m("java/lang/Integer", 182, "intValue", "()I");
    }

    private void generateIntegerWrap() {
        this.cfw.m("java/lang/Integer", 184, "valueOf", "(I)Ljava/lang/Integer;");
    }

    private void generateLocalYieldPoint(Node node, boolean z) {
        short s = this.cfw.m;
        int i = this.maxStack;
        if (i <= s) {
            i = s;
        }
        this.maxStack = i;
        if (s != 0) {
            generateGetGeneratorStackState();
            for (int i2 = 0; i2 < s; i2++) {
                this.cfw.c(90);
                this.cfw.c(95);
                this.cfw.o(i2);
                this.cfw.c(95);
                this.cfw.c(83);
            }
            this.cfw.c(87);
        }
        Node firstChild = node.getFirstChild();
        if (firstChild != null) {
            generateExpression(firstChild, node);
        } else {
            Codegen.pushUndefined(this.cfw);
        }
        if (node.getType() == 166) {
            this.cfw.e(187, "org/mozilla/javascript/ES6Generator$YieldStarResult");
            this.cfw.c(90);
            this.cfw.c(95);
            this.cfw.m("org/mozilla/javascript/ES6Generator$YieldStarResult", 183, "<init>", "(Ljava/lang/Object;)V");
        }
        int nextGeneratorState = getNextGeneratorState(node);
        generateSetGeneratorResumptionPoint(nextGeneratorState);
        boolean generateSaveLocals = generateSaveLocals(node);
        this.cfw.c(176);
        generateCheckForThrowOrClose(getTargetLabel(node), generateSaveLocals, nextGeneratorState);
        if (s != 0) {
            generateGetGeneratorStackState();
            for (int i3 = s - 1; i3 >= 0; i3--) {
                this.cfw.c(89);
                this.cfw.o(i3);
                this.cfw.c(50);
                this.cfw.c(95);
            }
            this.cfw.c(87);
        }
        if (z) {
            this.cfw.g(this.argsLocal);
        }
    }

    private void generateNestedFunctionInits() {
        int functionCount = this.scriptOrFn.getFunctionCount();
        for (int i = 0; i != functionCount; i++) {
            OptFunctionNode optFunctionNode = OptFunctionNode.get(this.scriptOrFn, i);
            if (optFunctionNode.fnode.getFunctionType() == 1) {
                visitFunction(optFunctionNode, 1);
            }
        }
    }

    private void generateObjectLiteralFactory(Node node, int i) {
        initBodyGeneration();
        short s = this.firstFreeLocal;
        short s2 = (short) (s + 1);
        this.firstFreeLocal = s2;
        this.argsLocal = s;
        this.localsMax = s2;
        this.cfw.ap(this.codegen.getBodyMethodName(this.scriptOrFn) + "_literal" + i, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;", 2);
        visitObjectLiteral(node, node.getFirstChild(), true);
        this.cfw.c(176);
        this.cfw.aq((short) (this.localsMax + 1));
    }

    private void generatePrologue() {
        boolean z;
        String str;
        String str2;
        short s;
        String str3;
        short s2;
        if (this.inDirectCallFunction) {
            int paramCount = this.scriptOrFn.getParamCount();
            if (this.firstFreeLocal != 4) {
                Kit.codeBug();
            }
            for (int i = 0; i != paramCount; i++) {
                short[] sArr = this.varRegisters;
                short s3 = this.firstFreeLocal;
                sArr[i] = s3;
                this.firstFreeLocal = (short) (s3 + 3);
            }
            if (!this.fnCurrent.getParameterNumberContext()) {
                this.itsForcedObjectParameters = true;
                for (int i2 = 0; i2 != paramCount; i2++) {
                    short s4 = this.varRegisters[i2];
                    this.cfw.g(s4);
                    this.cfw.f("java/lang/Void", 178, "TYPE", "Ljava/lang/Class;");
                    int b = this.cfw.b();
                    this.cfw.d(Token.YIELD_STAR, b);
                    this.cfw.i(s4 + 1);
                    addDoubleWrap();
                    this.cfw.h(s4);
                    this.cfw.af(b);
                }
            }
        }
        if (this.fnCurrent != null) {
            this.cfw.g(this.funObjLocal);
            this.cfw.m("org/mozilla/javascript/Scriptable", 185, "getParentScope", "()Lorg/mozilla/javascript/Scriptable;");
            this.cfw.h(this.variableObjectLocal);
        }
        short s5 = this.firstFreeLocal;
        short s6 = (short) (s5 + 1);
        this.firstFreeLocal = s6;
        this.argsLocal = s5;
        this.localsMax = s6;
        if (this.isGenerator) {
            short s7 = (short) (s6 + 1);
            this.firstFreeLocal = s7;
            this.operationLocal = s6;
            this.localsMax = s7;
            this.cfw.g(this.thisObjLocal);
            short s8 = this.firstFreeLocal;
            short s9 = (short) (s8 + 1);
            this.firstFreeLocal = s9;
            this.generatorStateLocal = s8;
            this.localsMax = s9;
            this.cfw.e(192, "org/mozilla/javascript/optimizer/OptRuntime$GeneratorState");
            this.cfw.c(89);
            this.cfw.h(this.generatorStateLocal);
            this.cfw.f("org/mozilla/javascript/optimizer/OptRuntime$GeneratorState", Context.VERSION_1_8, "thisObj", "Lorg/mozilla/javascript/Scriptable;");
            this.cfw.h(this.thisObjLocal);
            if (this.epilogueLabel == -1) {
                this.epilogueLabel = this.cfw.b();
            }
            List<Node> resumptionPoints = ((FunctionNode) this.scriptOrFn).getResumptionPoints();
            if (resumptionPoints != null) {
                generateGetGeneratorResumptionPoint();
                this.generatorSwitch = this.cfw.x(0, resumptionPoints.size() + 0);
                generateCheckForThrowOrClose(-1, false, 0);
            }
        }
        if (this.fnCurrent == null && this.scriptOrFn.getRegexpCount() != 0) {
            this.cfw.g(this.contextLocal);
            this.cfw.m(this.codegen.mainClassName, 184, "_reInit", "(Lorg/mozilla/javascript/Context;)V");
        }
        if (this.compilerEnv.isGenerateObserverCount()) {
            saveCurrentCodeOffset();
        }
        if (!this.isGenerator) {
            if (this.hasVarsInRegs) {
                int paramCount2 = this.scriptOrFn.getParamCount();
                if (paramCount2 > 0 && !this.inDirectCallFunction) {
                    this.cfw.g(this.argsLocal);
                    this.cfw.c(190);
                    this.cfw.s(paramCount2);
                    int b2 = this.cfw.b();
                    this.cfw.d(Token.COMMENT, b2);
                    this.cfw.g(this.argsLocal);
                    this.cfw.s(paramCount2);
                    addScriptRuntimeInvoke("padArguments", "([Ljava/lang/Object;I)[Ljava/lang/Object;");
                    this.cfw.h(this.argsLocal);
                    this.cfw.af(b2);
                }
                int paramCount3 = this.fnCurrent.fnode.getParamCount();
                int paramAndVarCount = this.fnCurrent.fnode.getParamAndVarCount();
                boolean[] paramAndVarConst = this.fnCurrent.fnode.getParamAndVarConst();
                short s10 = -1;
                for (int i3 = 0; i3 != paramAndVarCount; i3++) {
                    if (i3 < paramCount3) {
                        if (!this.inDirectCallFunction) {
                            s = getNewWordLocal();
                            this.cfw.g(this.argsLocal);
                            this.cfw.s(i3);
                            this.cfw.c(50);
                            this.cfw.h(s);
                        } else {
                            s = -1;
                        }
                    } else if (this.fnCurrent.isNumberVar(i3)) {
                        s = getNewWordPairLocal(paramAndVarConst[i3]);
                        this.cfw.r(0.0d);
                        this.cfw.as(71, 57, s);
                    } else {
                        s = getNewWordLocal(paramAndVarConst[i3]);
                        if (s10 == -1) {
                            Codegen.pushUndefined(this.cfw);
                            s10 = s;
                        } else {
                            this.cfw.g(s10);
                        }
                        this.cfw.h(s);
                    }
                    if (s >= 0) {
                        if (paramAndVarConst[i3]) {
                            this.cfw.s(0);
                            l0 l0Var = this.cfw;
                            if (this.fnCurrent.isNumberVar(i3)) {
                                s2 = 2;
                            } else {
                                s2 = 1;
                            }
                            l0Var.as(59, 54, s2 + s);
                        }
                        this.varRegisters[i3] = s;
                    }
                    if (this.compilerEnv.isGenerateDebugInfo()) {
                        String paramOrVarName = this.fnCurrent.fnode.getParamOrVarName(i3);
                        if (this.fnCurrent.isNumberVar(i3)) {
                            str3 = "D";
                        } else {
                            str3 = "Ljava/lang/Object;";
                        }
                        l0 l0Var2 = this.cfw;
                        int i4 = l0Var2.j;
                        if (s < 0) {
                            s = this.varRegisters[i3];
                        }
                        l0Var2.aa(paramOrVarName, i4, s, str3);
                    }
                }
                return;
            }
            ScriptNode scriptNode = this.scriptOrFn;
            if (!(scriptNode instanceof FunctionNode) || ((FunctionNode) scriptNode).getFunctionType() != 4) {
                z = false;
            } else {
                z = true;
            }
            if (this.fnCurrent != null) {
                this.cfw.g(this.funObjLocal);
                this.cfw.g(this.variableObjectLocal);
                this.cfw.g(this.argsLocal);
                if (z) {
                    str2 = "createArrowFunctionActivation";
                } else {
                    str2 = "createFunctionActivation";
                }
                this.cfw.u(this.scriptOrFn.isInStrictMode());
                addScriptRuntimeInvoke(str2, "(Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;Z)Lorg/mozilla/javascript/Scriptable;");
                this.cfw.h(this.variableObjectLocal);
                this.cfw.g(this.contextLocal);
                this.cfw.g(this.variableObjectLocal);
                addScriptRuntimeInvoke("enterActivationFunction", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)V");
                str = "activation";
            } else {
                this.cfw.g(this.funObjLocal);
                this.cfw.g(this.thisObjLocal);
                this.cfw.g(this.contextLocal);
                this.cfw.g(this.variableObjectLocal);
                this.cfw.s(0);
                addScriptRuntimeInvoke("initScript", "(Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Z)V");
                str = "global";
            }
            this.enterAreaStartLabel = this.cfw.b();
            this.epilogueLabel = this.cfw.b();
            this.cfw.af(this.enterAreaStartLabel);
            generateNestedFunctionInits();
            if (this.compilerEnv.isGenerateDebugInfo()) {
                l0 l0Var3 = this.cfw;
                l0Var3.aa(str, l0Var3.j, this.variableObjectLocal, "Lorg/mozilla/javascript/Scriptable;");
            }
            OptFunctionNode optFunctionNode = this.fnCurrent;
            if (optFunctionNode == null) {
                this.popvLocal = getNewWordLocal();
                Codegen.pushUndefined(this.cfw);
                this.cfw.h(this.popvLocal);
                int endLineno = this.scriptOrFn.getEndLineno();
                if (endLineno != -1) {
                    this.cfw.n((short) endLineno);
                    return;
                }
                return;
            }
            if (optFunctionNode.itsContainsCalls0) {
                this.itsZeroArgArray = getNewWordLocal();
                this.cfw.f("org/mozilla/javascript/ScriptRuntime", 178, "emptyArgs", "[Ljava/lang/Object;");
                this.cfw.h(this.itsZeroArgArray);
            }
            if (this.fnCurrent.itsContainsCalls1) {
                this.itsOneArgArray = getNewWordLocal();
                this.cfw.s(1);
                this.cfw.e(189, "java/lang/Object");
                this.cfw.h(this.itsOneArgArray);
            }
        }
    }

    private boolean generateSaveLocals(Node node) {
        int i = 0;
        for (int i2 = 0; i2 < this.firstFreeLocal; i2++) {
            if (this.locals[i2] != 0) {
                i++;
            }
        }
        if (i == 0) {
            ((FunctionNode) this.scriptOrFn).addLiveLocals(node, (int[]) null);
            return false;
        }
        int i3 = this.maxLocals;
        if (i3 <= i) {
            i3 = i;
        }
        this.maxLocals = i3;
        int[] iArr = new int[i];
        int i4 = 0;
        for (int i5 = 0; i5 < this.firstFreeLocal; i5++) {
            if (this.locals[i5] != 0) {
                iArr[i4] = i5;
                i4++;
            }
        }
        ((FunctionNode) this.scriptOrFn).addLiveLocals(node, iArr);
        generateGetGeneratorLocalsState();
        for (int i6 = 0; i6 < i; i6++) {
            this.cfw.c(89);
            this.cfw.o(i6);
            this.cfw.g(iArr[i6]);
            this.cfw.c(83);
        }
        this.cfw.c(87);
        return true;
    }

    private void generateSetGeneratorResumptionPoint(int i) {
        this.cfw.g(this.generatorStateLocal);
        this.cfw.o(i);
        this.cfw.f("org/mozilla/javascript/optimizer/OptRuntime$GeneratorState", 181, "resumptionPoint", "I");
    }

    private void generateSetGeneratorReturnValue() {
        this.cfw.g(this.generatorStateLocal);
        this.cfw.c(95);
        addOptRuntimeInvoke("setGeneratorReturnValue", "(Ljava/lang/Object;Ljava/lang/Object;)V");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:151:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x015a, code lost:
        if (r8.compilerEnv.isGenerateObserverCount() == false) goto L_0x015f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x015c, code lost:
        addInstructionCount();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x015f, code lost:
        visitGoto((org.mozilla.javascript.ast.Jump) r9, r0, r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void generateStatement(org.mozilla.javascript.Node r9) {
        /*
            r8 = this;
            r8.updateLineNumber(r9)
            int r0 = r9.getType()
            org.mozilla.javascript.Node r1 = r9.getFirstChild()
            r2 = 50
            if (r0 == r2) goto L_0x02ff
            r2 = 51
            if (r0 == r2) goto L_0x02e3
            r2 = 65
            r3 = 167(0xa7, float:2.34E-43)
            r4 = -1
            if (r0 == r2) goto L_0x0296
            r2 = 82
            if (r0 == r2) goto L_0x028f
            r2 = 110(0x6e, float:1.54E-43)
            r5 = 1
            if (r0 == r2) goto L_0x026e
            r2 = 115(0x73, float:1.61E-43)
            if (r0 == r2) goto L_0x025c
            r2 = 124(0x7c, float:1.74E-43)
            if (r0 == r2) goto L_0x0247
            r2 = 126(0x7e, float:1.77E-43)
            r6 = 0
            if (r0 == r2) goto L_0x01d6
            r2 = 142(0x8e, float:1.99E-43)
            r7 = 2
            if (r0 == r2) goto L_0x01a8
            r2 = 161(0xa1, float:2.26E-43)
            if (r0 == r2) goto L_0x0310
            switch(r0) {
                case 2: goto L_0x0182;
                case 3: goto L_0x0166;
                case 4: goto L_0x0296;
                case 5: goto L_0x0154;
                case 6: goto L_0x0154;
                case 7: goto L_0x0154;
                default: goto L_0x003c;
            }
        L_0x003c:
            switch(r0) {
                case 57: goto L_0x010d;
                case 58: goto L_0x00d3;
                case 59: goto L_0x00d3;
                case 60: goto L_0x00d3;
                case 61: goto L_0x00d3;
                default: goto L_0x003f;
            }
        L_0x003f:
            switch(r0) {
                case 129: goto L_0x0247;
                case 130: goto L_0x0247;
                case 131: goto L_0x0247;
                case 132: goto L_0x00b2;
                case 133: goto L_0x0247;
                case 134: goto L_0x005d;
                case 135: goto L_0x0047;
                case 136: goto L_0x0154;
                case 137: goto L_0x0247;
                default: goto L_0x0042;
            }
        L_0x0042:
            java.lang.RuntimeException r9 = org.mozilla.javascript.optimizer.Codegen.badTree()
            throw r9
        L_0x0047:
            r8.generateExpression(r1, r9)
            short r9 = r8.popvLocal
            if (r9 >= 0) goto L_0x0054
            short r9 = r8.getNewWordLocal()
            r8.popvLocal = r9
        L_0x0054:
            l0 r9 = r8.cfw
            short r0 = r8.popvLocal
            r9.h(r0)
            goto L_0x0310
        L_0x005d:
            int r0 = r1.getType()
            r2 = 56
            if (r0 != r2) goto L_0x006e
            org.mozilla.javascript.Node r9 = r1.getFirstChild()
            r8.visitSetVar(r1, r9, r6)
            goto L_0x0310
        L_0x006e:
            int r0 = r1.getType()
            r2 = 157(0x9d, float:2.2E-43)
            if (r0 != r2) goto L_0x007f
            org.mozilla.javascript.Node r9 = r1.getFirstChild()
            r8.visitSetConstVar(r1, r9, r6)
            goto L_0x0310
        L_0x007f:
            int r0 = r1.getType()
            r2 = 73
            if (r0 == r2) goto L_0x00ad
            int r0 = r1.getType()
            r2 = 166(0xa6, float:2.33E-43)
            if (r0 != r2) goto L_0x0090
            goto L_0x00ad
        L_0x0090:
            r8.generateExpression(r1, r9)
            r0 = 8
            int r9 = r9.getIntProp(r0, r4)
            if (r9 == r4) goto L_0x00a4
            l0 r9 = r8.cfw
            r0 = 88
            r9.c(r0)
            goto L_0x0310
        L_0x00a4:
            l0 r9 = r8.cfw
            r0 = 87
            r9.c(r0)
            goto L_0x0310
        L_0x00ad:
            r8.generateYieldPoint(r1, r6)
            goto L_0x0310
        L_0x00b2:
            org.mozilla.javascript.CompilerEnvirons r0 = r8.compilerEnv
            boolean r0 = r0.isGenerateObserverCount()
            if (r0 == 0) goto L_0x00bd
            r8.addInstructionCount()
        L_0x00bd:
            int r9 = r8.getTargetLabel(r9)
            l0 r0 = r8.cfw
            r0.af(r9)
            org.mozilla.javascript.CompilerEnvirons r9 = r8.compilerEnv
            boolean r9 = r9.isGenerateObserverCount()
            if (r9 == 0) goto L_0x0310
            r8.saveCurrentCodeOffset()
            goto L_0x0310
        L_0x00d3:
            r8.generateExpression(r1, r9)
            l0 r1 = r8.cfw
            short r2 = r8.contextLocal
            r1.g(r2)
            l0 r1 = r8.cfw
            short r2 = r8.variableObjectLocal
            r1.g(r2)
            r1 = 58
            if (r0 != r1) goto L_0x00ea
            r5 = 0
            goto L_0x00f6
        L_0x00ea:
            r1 = 59
            if (r0 != r1) goto L_0x00ef
            goto L_0x00f6
        L_0x00ef:
            r1 = 61
            if (r0 != r1) goto L_0x00f5
            r5 = 6
            goto L_0x00f6
        L_0x00f5:
            r5 = 2
        L_0x00f6:
            l0 r0 = r8.cfw
            r0.s(r5)
            java.lang.String r0 = "enumInit"
            java.lang.String r1 = "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;I)Ljava/lang/Object;"
            r8.addScriptRuntimeInvoke(r0, r1)
            l0 r0 = r8.cfw
            int r9 = getLocalBlockRegister(r9)
            r0.h(r9)
            goto L_0x0310
        L_0x010d:
            l0 r0 = r8.cfw
            r0.m = r6
            int r0 = getLocalBlockRegister(r9)
            r2 = 14
            int r2 = r9.getExistingIntProp(r2)
            java.lang.String r3 = r1.getString()
            org.mozilla.javascript.Node r1 = r1.getNext()
            r8.generateExpression(r1, r9)
            if (r2 != 0) goto L_0x012e
            l0 r9 = r8.cfw
            r9.c(r5)
            goto L_0x0133
        L_0x012e:
            l0 r9 = r8.cfw
            r9.g(r0)
        L_0x0133:
            l0 r9 = r8.cfw
            r9.t(r3)
            l0 r9 = r8.cfw
            short r1 = r8.contextLocal
            r9.g(r1)
            l0 r9 = r8.cfw
            short r1 = r8.variableObjectLocal
            r9.g(r1)
            java.lang.String r9 = "newCatchScope"
            java.lang.String r1 = "(Ljava/lang/Throwable;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;"
            r8.addScriptRuntimeInvoke(r9, r1)
            l0 r9 = r8.cfw
            r9.h(r0)
            goto L_0x0310
        L_0x0154:
            org.mozilla.javascript.CompilerEnvirons r2 = r8.compilerEnv
            boolean r2 = r2.isGenerateObserverCount()
            if (r2 == 0) goto L_0x015f
            r8.addInstructionCount()
        L_0x015f:
            org.mozilla.javascript.ast.Jump r9 = (org.mozilla.javascript.ast.Jump) r9
            r8.visitGoto(r9, r0, r1)
            goto L_0x0310
        L_0x0166:
            l0 r9 = r8.cfw
            short r0 = r8.variableObjectLocal
            r9.g(r0)
            java.lang.String r9 = "leaveWith"
            java.lang.String r0 = "(Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;"
            r8.addScriptRuntimeInvoke(r9, r0)
            l0 r9 = r8.cfw
            short r0 = r8.variableObjectLocal
            r9.h(r0)
            short r9 = r8.variableObjectLocal
            r8.decReferenceWordLocal(r9)
            goto L_0x0310
        L_0x0182:
            r8.generateExpression(r1, r9)
            l0 r9 = r8.cfw
            short r0 = r8.contextLocal
            r9.g(r0)
            l0 r9 = r8.cfw
            short r0 = r8.variableObjectLocal
            r9.g(r0)
            java.lang.String r9 = "enterWith"
            java.lang.String r0 = "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;"
            r8.addScriptRuntimeInvoke(r9, r0)
            l0 r9 = r8.cfw
            short r0 = r8.variableObjectLocal
            r9.h(r0)
            short r9 = r8.variableObjectLocal
            r8.incReferenceWordLocal(r9)
            goto L_0x0310
        L_0x01a8:
            boolean r0 = r8.inLocalBlock
            r8.inLocalBlock = r5
            short r2 = r8.getNewWordLocal()
            boolean r3 = r8.isGenerator
            if (r3 == 0) goto L_0x01be
            l0 r3 = r8.cfw
            r3.c(r5)
            l0 r3 = r8.cfw
            r3.h(r2)
        L_0x01be:
            r9.putIntProp(r7, r2)
        L_0x01c1:
            if (r1 == 0) goto L_0x01cb
            r8.generateStatement(r1)
            org.mozilla.javascript.Node r1 = r1.getNext()
            goto L_0x01c1
        L_0x01cb:
            short r1 = (short) r2
            r8.releaseWordLocal(r1)
            r9.removeProp(r7)
            r8.inLocalBlock = r0
            goto L_0x0310
        L_0x01d6:
            boolean r0 = r8.isGenerator
            if (r0 != 0) goto L_0x01dc
            goto L_0x0310
        L_0x01dc:
            org.mozilla.javascript.CompilerEnvirons r0 = r8.compilerEnv
            boolean r0 = r0.isGenerateObserverCount()
            if (r0 == 0) goto L_0x01e7
            r8.saveCurrentCodeOffset()
        L_0x01e7:
            l0 r0 = r8.cfw
            r0.m = r5
            short r0 = r8.getNewWordLocal()
            l0 r2 = r8.cfw
            int r2 = r2.b()
            l0 r4 = r8.cfw
            int r4 = r4.b()
            l0 r5 = r8.cfw
            r5.af(r2)
            r8.generateIntegerWrap()
            l0 r2 = r8.cfw
            r2.h(r0)
        L_0x0208:
            if (r1 == 0) goto L_0x0212
            r8.generateStatement(r1)
            org.mozilla.javascript.Node r1 = r1.getNext()
            goto L_0x0208
        L_0x0212:
            l0 r1 = r8.cfw
            r1.g(r0)
            l0 r1 = r8.cfw
            r2 = 192(0xc0, float:2.69E-43)
            java.lang.String r5 = "java/lang/Integer"
            r1.e(r2, r5)
            r8.generateIntegerUnwrap()
            java.util.Map<org.mozilla.javascript.Node, org.mozilla.javascript.optimizer.BodyCodegen$FinallyReturnPoint> r1 = r8.finallys
            java.lang.Object r9 = r1.get(r9)
            org.mozilla.javascript.optimizer.BodyCodegen$FinallyReturnPoint r9 = (org.mozilla.javascript.optimizer.BodyCodegen.FinallyReturnPoint) r9
            l0 r1 = r8.cfw
            int r1 = r1.b()
            r9.tableLabel = r1
            l0 r9 = r8.cfw
            r9.d(r3, r1)
            l0 r9 = r8.cfw
            r9.m = r6
            short r9 = (short) r0
            r8.releaseWordLocal(r9)
            l0 r9 = r8.cfw
            r9.af(r4)
            goto L_0x0310
        L_0x0247:
            org.mozilla.javascript.CompilerEnvirons r9 = r8.compilerEnv
            boolean r9 = r9.isGenerateObserverCount()
            if (r9 == 0) goto L_0x0252
            r8.addInstructionCount(r5)
        L_0x0252:
            if (r1 == 0) goto L_0x0310
            r8.generateStatement(r1)
            org.mozilla.javascript.Node r1 = r1.getNext()
            goto L_0x0252
        L_0x025c:
            org.mozilla.javascript.CompilerEnvirons r0 = r8.compilerEnv
            boolean r0 = r0.isGenerateObserverCount()
            if (r0 == 0) goto L_0x0267
            r8.addInstructionCount()
        L_0x0267:
            org.mozilla.javascript.ast.Jump r9 = (org.mozilla.javascript.ast.Jump) r9
            r8.visitSwitch(r9, r1)
            goto L_0x0310
        L_0x026e:
            int r9 = r9.getExistingIntProp(r5)
            org.mozilla.javascript.ast.ScriptNode r0 = r8.scriptOrFn
            org.mozilla.javascript.optimizer.OptFunctionNode r9 = org.mozilla.javascript.optimizer.OptFunctionNode.get(r0, r9)
            org.mozilla.javascript.ast.FunctionNode r0 = r9.fnode
            int r0 = r0.getFunctionType()
            r1 = 3
            if (r0 != r1) goto L_0x0286
            r8.visitFunction(r9, r0)
            goto L_0x0310
        L_0x0286:
            if (r0 != r5) goto L_0x028a
            goto L_0x0310
        L_0x028a:
            java.lang.RuntimeException r9 = org.mozilla.javascript.optimizer.Codegen.badTree()
            throw r9
        L_0x028f:
            org.mozilla.javascript.ast.Jump r9 = (org.mozilla.javascript.ast.Jump) r9
            r8.visitTryCatchFinally(r9, r1)
            goto L_0x0310
        L_0x0296:
            if (r1 == 0) goto L_0x029c
            r8.generateExpression(r1, r9)
            goto L_0x02ae
        L_0x029c:
            r9 = 4
            if (r0 != r9) goto L_0x02a5
            l0 r9 = r8.cfw
            org.mozilla.javascript.optimizer.Codegen.pushUndefined(r9)
            goto L_0x02ae
        L_0x02a5:
            short r9 = r8.popvLocal
            if (r9 < 0) goto L_0x02de
            l0 r0 = r8.cfw
            r0.g(r9)
        L_0x02ae:
            boolean r9 = r8.isGenerator
            if (r9 == 0) goto L_0x02b5
            r8.generateSetGeneratorReturnValue()
        L_0x02b5:
            org.mozilla.javascript.CompilerEnvirons r9 = r8.compilerEnv
            boolean r9 = r9.isGenerateObserverCount()
            if (r9 == 0) goto L_0x02c0
            r8.addInstructionCount()
        L_0x02c0:
            int r9 = r8.epilogueLabel
            if (r9 != r4) goto L_0x02d6
            boolean r9 = r8.hasVarsInRegs
            if (r9 == 0) goto L_0x02d1
            l0 r9 = r8.cfw
            int r9 = r9.b()
            r8.epilogueLabel = r9
            goto L_0x02d6
        L_0x02d1:
            java.lang.RuntimeException r9 = org.mozilla.javascript.optimizer.Codegen.badTree()
            throw r9
        L_0x02d6:
            l0 r9 = r8.cfw
            int r0 = r8.epilogueLabel
            r9.d(r3, r0)
            goto L_0x0310
        L_0x02de:
            java.lang.RuntimeException r9 = org.mozilla.javascript.optimizer.Codegen.badTree()
            throw r9
        L_0x02e3:
            org.mozilla.javascript.CompilerEnvirons r0 = r8.compilerEnv
            boolean r0 = r0.isGenerateObserverCount()
            if (r0 == 0) goto L_0x02ee
            r8.addInstructionCount()
        L_0x02ee:
            l0 r0 = r8.cfw
            int r9 = getLocalBlockRegister(r9)
            r0.g(r9)
            l0 r9 = r8.cfw
            r0 = 191(0xbf, float:2.68E-43)
            r9.c(r0)
            goto L_0x0310
        L_0x02ff:
            r8.generateExpression(r1, r9)
            org.mozilla.javascript.CompilerEnvirons r9 = r8.compilerEnv
            boolean r9 = r9.isGenerateObserverCount()
            if (r9 == 0) goto L_0x030d
            r8.addInstructionCount()
        L_0x030d:
            r8.generateThrowJavaScriptException()
        L_0x0310:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.optimizer.BodyCodegen.generateStatement(org.mozilla.javascript.Node):void");
    }

    private void generateThrowJavaScriptException() {
        this.cfw.e(187, "org/mozilla/javascript/JavaScriptException");
        this.cfw.c(90);
        this.cfw.c(95);
        this.cfw.t(this.scriptOrFn.getSourceName());
        this.cfw.s(this.itsLineNumber);
        this.cfw.m("org/mozilla/javascript/JavaScriptException", 183, "<init>", "(Ljava/lang/Object;Ljava/lang/String;I)V");
        this.cfw.c(191);
    }

    private void generateYieldPoint(Node node, boolean z) {
        if (!this.unnestedYields.containsKey(node)) {
            Node findNestedYield = findNestedYield(node);
            if (findNestedYield != null) {
                generateYieldPoint(findNestedYield, true);
                String str = "__nested__yield__" + this.unnestedYieldCount;
                this.unnestedYieldCount++;
                this.cfw.g(this.variableObjectLocal);
                this.cfw.c(95);
                this.cfw.p(str);
                this.cfw.c(95);
                this.cfw.g(this.contextLocal);
                addScriptRuntimeInvoke("setObjectProp", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;Ljava/lang/Object;Lorg/mozilla/javascript/Context;)Ljava/lang/Object;");
                this.cfw.c(87);
                this.unnestedYields.put(findNestedYield, str);
            }
            generateLocalYieldPoint(node, z);
        } else if (z) {
            this.cfw.g(this.variableObjectLocal);
            this.cfw.p(this.unnestedYields.get(node));
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            addScriptRuntimeInvoke("getObjectPropNoWarn", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
        }
    }

    /* access modifiers changed from: private */
    public static Node getFinallyAtTarget(Node node) {
        Node next;
        if (node == null) {
            return null;
        }
        if (node.getType() == 126) {
            return node;
        }
        if (node.getType() == 132 && (next = node.getNext()) != null && next.getType() == 126) {
            return next;
        }
        throw Kit.codeBug("bad finally target");
    }

    private static int getLocalBlockRegister(Node node) {
        return ((Node) node.getProp(3)).getExistingIntProp(2);
    }

    private short getNewWordIntern(int i) {
        short s;
        int[] iArr = this.locals;
        if (i > 1) {
            s = this.firstFreeLocal;
            loop0:
            while (true) {
                if (s + i > 1024) {
                    s = -1;
                    break;
                }
                int i2 = 0;
                while (i2 < i) {
                    if (iArr[s + i2] != 0) {
                        s += i2 + 1;
                    } else {
                        i2++;
                    }
                }
                break loop0;
            }
        }
        s = this.firstFreeLocal;
        if (s != -1) {
            iArr[s] = 1;
            if (i > 1) {
                iArr[s + 1] = 1;
            }
            if (i > 2) {
                iArr[s + 2] = 1;
            }
            if (s != this.firstFreeLocal) {
                return (short) s;
            }
            for (int i3 = i + s; i3 < 1024; i3++) {
                if (iArr[i3] == 0) {
                    short s2 = (short) i3;
                    this.firstFreeLocal = s2;
                    if (this.localsMax < s2) {
                        this.localsMax = s2;
                    }
                    return (short) s;
                }
            }
        }
        throw Context.reportRuntimeError("Program too complex (out of locals)");
    }

    private short getNewWordLocal(boolean z) {
        return getNewWordIntern(z ? 2 : 1);
    }

    private short getNewWordPairLocal(boolean z) {
        return getNewWordIntern(z ? 3 : 2);
    }

    private int getNextGeneratorState(Node node) {
        return ((FunctionNode) this.scriptOrFn).getResumptionPoints().indexOf(node) + 1;
    }

    private int getTargetLabel(Node node) {
        int labelId = node.labelId();
        if (labelId != -1) {
            return labelId;
        }
        int b = this.cfw.b();
        node.labelId(b);
        return b;
    }

    private void incReferenceWordLocal(short s) {
        int[] iArr = this.locals;
        iArr[s] = iArr[s] + 1;
    }

    private void initBodyGeneration() {
        int paramAndVarCount;
        this.varRegisters = null;
        if (this.scriptOrFn.getType() == 110) {
            OptFunctionNode optFunctionNode = OptFunctionNode.get(this.scriptOrFn);
            this.fnCurrent = optFunctionNode;
            boolean z = !optFunctionNode.fnode.requiresActivation();
            this.hasVarsInRegs = z;
            if (z && (paramAndVarCount = this.fnCurrent.fnode.getParamAndVarCount()) != 0) {
                this.varRegisters = new short[paramAndVarCount];
            }
            boolean isTargetOfDirectCall = this.fnCurrent.isTargetOfDirectCall();
            this.inDirectCallFunction = isTargetOfDirectCall;
            if (isTargetOfDirectCall && !this.hasVarsInRegs) {
                Codegen.badTree();
            }
        } else {
            this.fnCurrent = null;
            this.hasVarsInRegs = false;
            this.inDirectCallFunction = false;
        }
        this.locals = new int[1024];
        this.funObjLocal = 0;
        this.contextLocal = 1;
        this.variableObjectLocal = 2;
        this.thisObjLocal = 3;
        this.localsMax = 4;
        this.firstFreeLocal = 4;
        this.popvLocal = -1;
        this.argsLocal = -1;
        this.itsZeroArgArray = -1;
        this.itsOneArgArray = -1;
        this.epilogueLabel = -1;
        this.enterAreaStartLabel = -1;
        this.generatorStateLocal = -1;
    }

    private void inlineFinally(Node node, int i, int i2) {
        Node finallyAtTarget = getFinallyAtTarget(node);
        finallyAtTarget.resetTargets();
        this.exceptionManager.markInlineFinallyStart(finallyAtTarget, i);
        for (Node firstChild = finallyAtTarget.getFirstChild(); firstChild != null; firstChild = firstChild.getNext()) {
            generateStatement(firstChild);
        }
        this.exceptionManager.markInlineFinallyEnd(finallyAtTarget, i2);
    }

    private static boolean isArithmeticNode(Node node) {
        int type = node.getType();
        return type == 22 || type == 25 || type == 24 || type == 23;
    }

    private int nodeIsDirectCallParameter(Node node) {
        if (node.getType() != 55 || !this.inDirectCallFunction || this.itsForcedObjectParameters) {
            return -1;
        }
        int varIndex = this.fnCurrent.getVarIndex(node);
        if (this.fnCurrent.isParameter(varIndex)) {
            return this.varRegisters[varIndex];
        }
        return -1;
    }

    private void releaseWordLocal(short s) {
        if (s < this.firstFreeLocal) {
            this.firstFreeLocal = s;
        }
        this.locals[s] = 0;
    }

    private void saveCurrentCodeOffset() {
        this.savedCodeOffset = this.cfw.j;
    }

    private void updateLineNumber(Node node) {
        int lineno = node.getLineno();
        this.itsLineNumber = lineno;
        if (lineno != -1) {
            this.cfw.n((short) lineno);
        }
    }

    private boolean varIsDirectCallParameter(int i) {
        return this.fnCurrent.isParameter(i) && this.inDirectCallFunction && !this.itsForcedObjectParameters;
    }

    private void visitArithmetic(Node node, int i, Node node2, Node node3) {
        if (node.getIntProp(8, -1) != -1) {
            generateExpression(node2, node);
            generateExpression(node2.getNext(), node);
            this.cfw.c(i);
            return;
        }
        boolean isArithmeticNode = isArithmeticNode(node3);
        generateExpression(node2, node);
        if (!isArithmeticNode(node2)) {
            addObjectToDouble();
        }
        generateExpression(node2.getNext(), node);
        if (!isArithmeticNode(node2.getNext())) {
            addObjectToDouble();
        }
        this.cfw.c(i);
        if (!isArithmeticNode) {
            addDoubleWrap();
        }
    }

    private void visitArrayLiteral(Node node, Node node2, boolean z) {
        int i = 0;
        int i2 = 0;
        for (Node node3 = node2; node3 != null; node3 = node3.getNext()) {
            i2++;
        }
        if (z || ((i2 <= 10 && this.cfw.j <= 30000) || this.hasVarsInRegs || this.isGenerator || this.inLocalBlock)) {
            if (this.isGenerator) {
                for (int i3 = 0; i3 != i2; i3++) {
                    generateExpression(node2, node);
                    node2 = node2.getNext();
                }
                addNewObjectArray(i2);
                while (i != i2) {
                    this.cfw.c(90);
                    this.cfw.c(95);
                    this.cfw.s((i2 - i) - 1);
                    this.cfw.c(95);
                    this.cfw.c(83);
                    i++;
                }
            } else {
                addNewObjectArray(i2);
                while (i != i2) {
                    this.cfw.c(89);
                    this.cfw.s(i);
                    generateExpression(node2, node);
                    this.cfw.c(83);
                    node2 = node2.getNext();
                    i++;
                }
            }
            int[] iArr = (int[]) node.getProp(11);
            if (iArr == null) {
                this.cfw.c(1);
                this.cfw.c(3);
            } else {
                this.cfw.t(OptRuntime.encodeIntArray(iArr));
                this.cfw.s(iArr.length);
            }
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            addOptRuntimeInvoke("newArrayLiteral", "([Ljava/lang/Object;Ljava/lang/String;ILorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
            return;
        }
        if (this.literals == null) {
            this.literals = new LinkedList();
        }
        this.literals.add(node);
        this.cfw.g(this.funObjLocal);
        this.cfw.g(this.contextLocal);
        this.cfw.g(this.variableObjectLocal);
        this.cfw.g(this.thisObjLocal);
        this.cfw.g(this.argsLocal);
        this.cfw.m(this.codegen.mainClassName, 182, this.codegen.getBodyMethodName(this.scriptOrFn) + "_literal" + this.literals.size(), "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;");
    }

    private void visitBitOp(Node node, int i, Node node2) {
        int intProp = node.getIntProp(8, -1);
        generateExpression(node2, node);
        if (i == 20) {
            addScriptRuntimeInvoke("toUint32", "(Ljava/lang/Object;)J");
            generateExpression(node2.getNext(), node);
            addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)I");
            this.cfw.s(31);
            this.cfw.c(Token.FINALLY);
            this.cfw.c(Token.CATCH);
            this.cfw.c(Token.TYPEOFNAME);
            addDoubleWrap();
            return;
        }
        if (intProp == -1) {
            addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)I");
            generateExpression(node2.getNext(), node);
            addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)I");
        } else {
            addScriptRuntimeInvoke("toInt32", "(D)I");
            generateExpression(node2.getNext(), node);
            addScriptRuntimeInvoke("toInt32", "(D)I");
        }
        if (i == 18) {
            this.cfw.c(120);
        } else if (i != 19) {
            switch (i) {
                case 9:
                    this.cfw.c(128);
                    break;
                case 10:
                    this.cfw.c(130);
                    break;
                case 11:
                    this.cfw.c(Token.FINALLY);
                    break;
                default:
                    throw Codegen.badTree();
            }
        } else {
            this.cfw.c(122);
        }
        this.cfw.c(Token.EXPR_RESULT);
        if (intProp == -1) {
            addDoubleWrap();
        }
    }

    private void visitDotQuery(Node node, Node node2) {
        updateLineNumber(node);
        generateExpression(node2, node);
        this.cfw.g(this.variableObjectLocal);
        addScriptRuntimeInvoke("enterDotQuery", "(Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
        this.cfw.h(this.variableObjectLocal);
        this.cfw.c(1);
        int b = this.cfw.b();
        this.cfw.af(b);
        this.cfw.c(87);
        generateExpression(node2.getNext(), node);
        addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)Z");
        this.cfw.g(this.variableObjectLocal);
        addScriptRuntimeInvoke("updateDotQuery", "(ZLorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
        this.cfw.c(89);
        this.cfw.d(198, b);
        this.cfw.g(this.variableObjectLocal);
        addScriptRuntimeInvoke("leaveDotQuery", "(Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
        this.cfw.h(this.variableObjectLocal);
    }

    private void visitFunction(OptFunctionNode optFunctionNode, int i) {
        int index = this.codegen.getIndex(optFunctionNode.fnode);
        this.cfw.e(187, this.codegen.mainClassName);
        this.cfw.c(89);
        this.cfw.g(this.variableObjectLocal);
        this.cfw.g(this.contextLocal);
        this.cfw.s(index);
        this.cfw.m(this.codegen.mainClassName, 183, "<init>", "(Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;I)V");
        if (i == 4) {
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            this.cfw.g(this.thisObjLocal);
            addOptRuntimeInvoke("bindThis", "(Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Function;");
        }
        if (i != 2 && i != 4) {
            this.cfw.s(i);
            this.cfw.g(this.variableObjectLocal);
            this.cfw.g(this.contextLocal);
            addOptRuntimeInvoke("initFunction", "(Lorg/mozilla/javascript/NativeFunction;ILorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;)V");
        }
    }

    private void visitGetProp(Node node, Node node2) {
        generateExpression(node2, node);
        Node next = node2.getNext();
        generateExpression(next, node);
        if (node.getType() == 34) {
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            addScriptRuntimeInvoke("getObjectPropNoWarn", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
        } else if (node2.getType() == 43 && next.getType() == 41) {
            this.cfw.g(this.contextLocal);
            addScriptRuntimeInvoke("getObjectProp", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;Lorg/mozilla/javascript/Context;)Ljava/lang/Object;");
        } else {
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            addScriptRuntimeInvoke("getObjectProp", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
        }
    }

    private void visitGetVar(Node node) {
        if (!this.hasVarsInRegs) {
            Kit.codeBug();
        }
        int varIndex = this.fnCurrent.getVarIndex(node);
        short s = this.varRegisters[varIndex];
        if (varIsDirectCallParameter(varIndex)) {
            if (node.getIntProp(8, -1) != -1) {
                dcpLoadAsNumber(s);
            } else {
                dcpLoadAsObject(s);
            }
        } else if (this.fnCurrent.isNumberVar(varIndex)) {
            this.cfw.i(s);
        } else {
            this.cfw.g(s);
        }
    }

    private void visitGoto(Jump jump, int i, Node node) {
        Node node2 = jump.target;
        if (i == 6 || i == 7) {
            if (node != null) {
                int targetLabel = getTargetLabel(node2);
                int b = this.cfw.b();
                if (i == 6) {
                    generateIfJump(node, jump, targetLabel, b);
                } else {
                    generateIfJump(node, jump, b, targetLabel);
                }
                this.cfw.af(b);
                return;
            }
            throw Codegen.badTree();
        } else if (i != 136) {
            addGoto(node2, Token.LAST_TOKEN);
        } else if (this.isGenerator) {
            addGotoWithReturn(node2);
        } else {
            inlineFinally(node2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00ad  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void visitIfJumpEqOp(org.mozilla.javascript.Node r18, org.mozilla.javascript.Node r19, int r20, int r21) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r5 = -1
            if (r3 == r5) goto L_0x0151
            if (r4 == r5) goto L_0x0151
            l0 r6 = r0.cfw
            short r6 = r6.m
            int r7 = r18.getType()
            org.mozilla.javascript.Node r8 = r19.getNext()
            int r9 = r19.getType()
            r12 = 12
            r14 = 42
            if (r9 == r14) goto L_0x00d9
            int r9 = r8.getType()
            if (r9 != r14) goto L_0x002d
            goto L_0x00d9
        L_0x002d:
            int r9 = r0.nodeIsDirectCallParameter(r2)
            if (r9 == r5) goto L_0x009f
            int r5 = r8.getType()
            r13 = 150(0x96, float:2.1E-43)
            if (r5 != r13) goto L_0x009f
            org.mozilla.javascript.Node r5 = r8.getFirstChild()
            int r13 = r5.getType()
            r10 = 40
            if (r13 != r10) goto L_0x009f
            l0 r10 = r0.cfw
            r10.g(r9)
            l0 r10 = r0.cfw
            java.lang.String r13 = "TYPE"
            java.lang.String r15 = "Ljava/lang/Class;"
            java.lang.String r11 = "java/lang/Void"
            r14 = 178(0xb2, float:2.5E-43)
            r10.f(r11, r14, r13, r15)
            l0 r10 = r0.cfw
            int r10 = r10.b()
            l0 r11 = r0.cfw
            r13 = 166(0xa6, float:2.33E-43)
            r11.d(r13, r10)
            l0 r11 = r0.cfw
            int r9 = r9 + 1
            r11.i(r9)
            l0 r9 = r0.cfw
            double r13 = r5.getDouble()
            r9.r(r13)
            l0 r5 = r0.cfw
            r9 = 151(0x97, float:2.12E-43)
            r5.c(r9)
            if (r7 != r12) goto L_0x0089
            l0 r5 = r0.cfw
            r9 = 153(0x99, float:2.14E-43)
            r5.d(r9, r3)
            r11 = 154(0x9a, float:2.16E-43)
            goto L_0x0092
        L_0x0089:
            r9 = 153(0x99, float:2.14E-43)
            l0 r5 = r0.cfw
            r11 = 154(0x9a, float:2.16E-43)
            r5.d(r11, r3)
        L_0x0092:
            l0 r5 = r0.cfw
            r13 = 167(0xa7, float:2.34E-43)
            r5.d(r13, r4)
            l0 r5 = r0.cfw
            r5.af(r10)
            goto L_0x00a3
        L_0x009f:
            r9 = 153(0x99, float:2.14E-43)
            r11 = 154(0x9a, float:2.16E-43)
        L_0x00a3:
            r0.generateExpression(r2, r1)
            r0.generateExpression(r8, r1)
            java.lang.String r1 = "eq"
            if (r7 == r12) goto L_0x00c4
            r2 = 13
            if (r7 == r2) goto L_0x00c1
            java.lang.String r1 = "shallowEq"
            r2 = 46
            if (r7 == r2) goto L_0x00c4
            r2 = 47
            if (r7 != r2) goto L_0x00bc
            goto L_0x00c1
        L_0x00bc:
            java.lang.RuntimeException r1 = org.mozilla.javascript.optimizer.Codegen.badTree()
            throw r1
        L_0x00c1:
            r14 = 153(0x99, float:2.14E-43)
            goto L_0x00c6
        L_0x00c4:
            r14 = 154(0x9a, float:2.16E-43)
        L_0x00c6:
            java.lang.String r2 = "(Ljava/lang/Object;Ljava/lang/Object;)Z"
            r0.addScriptRuntimeInvoke(r1, r2)
            l0 r1 = r0.cfw
            r1.d(r14, r3)
            l0 r1 = r0.cfw
            r2 = 167(0xa7, float:2.34E-43)
            r1.d(r2, r4)
            goto L_0x0145
        L_0x00d9:
            int r5 = r19.getType()
            if (r5 != r14) goto L_0x00e0
            r2 = r8
        L_0x00e0:
            r0.generateExpression(r2, r1)
            r1 = 199(0xc7, float:2.79E-43)
            r2 = 46
            if (r7 == r2) goto L_0x0135
            r2 = 47
            if (r7 != r2) goto L_0x00f0
            r2 = 46
            goto L_0x0135
        L_0x00f0:
            if (r7 == r12) goto L_0x0101
            r2 = 13
            if (r7 != r2) goto L_0x00fc
            r16 = r4
            r4 = r3
            r3 = r16
            goto L_0x0101
        L_0x00fc:
            java.lang.RuntimeException r1 = org.mozilla.javascript.optimizer.Codegen.badTree()
            throw r1
        L_0x0101:
            l0 r2 = r0.cfw
            r5 = 89
            r2.c(r5)
            l0 r2 = r0.cfw
            int r2 = r2.b()
            l0 r5 = r0.cfw
            r5.d(r1, r2)
            l0 r1 = r0.cfw
            short r5 = r1.m
            r7 = 87
            r1.c(r7)
            l0 r1 = r0.cfw
            r7 = 167(0xa7, float:2.34E-43)
            r1.d(r7, r3)
            l0 r1 = r0.cfw
            r1.ag(r2, r5)
            l0 r1 = r0.cfw
            org.mozilla.javascript.optimizer.Codegen.pushUndefined(r1)
            l0 r1 = r0.cfw
            r2 = 165(0xa5, float:2.31E-43)
            r1.d(r2, r3)
            goto L_0x013e
        L_0x0135:
            if (r7 != r2) goto L_0x0139
            r1 = 198(0xc6, float:2.77E-43)
        L_0x0139:
            l0 r2 = r0.cfw
            r2.d(r1, r3)
        L_0x013e:
            l0 r1 = r0.cfw
            r2 = 167(0xa7, float:2.34E-43)
            r1.d(r2, r4)
        L_0x0145:
            l0 r1 = r0.cfw
            short r1 = r1.m
            if (r6 != r1) goto L_0x014c
            return
        L_0x014c:
            java.lang.RuntimeException r1 = org.mozilla.javascript.optimizer.Codegen.badTree()
            throw r1
        L_0x0151:
            java.lang.RuntimeException r1 = org.mozilla.javascript.optimizer.Codegen.badTree()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.optimizer.BodyCodegen.visitIfJumpEqOp(org.mozilla.javascript.Node, org.mozilla.javascript.Node, int, int):void");
    }

    private void visitIfJumpRelOp(Node node, Node node2, int i, int i2) {
        String str;
        String str2;
        Node node3 = node;
        Node node4 = node2;
        int i3 = i;
        int i4 = i2;
        if (i3 == -1 || i4 == -1) {
            throw Codegen.badTree();
        }
        int type = node.getType();
        Node next = node2.getNext();
        if (type == 53 || type == 52) {
            generateExpression(node4, node3);
            generateExpression(next, node3);
            this.cfw.g(this.contextLocal);
            if (type == 53) {
                str = "instanceOf";
            } else {
                str = "in";
            }
            addScriptRuntimeInvoke(str, "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;)Z");
            this.cfw.d(Token.LET, i3);
            this.cfw.d(Token.LAST_TOKEN, i4);
            return;
        }
        int intProp = node3.getIntProp(8, -1);
        int nodeIsDirectCallParameter = nodeIsDirectCallParameter(node4);
        int nodeIsDirectCallParameter2 = nodeIsDirectCallParameter(next);
        if (intProp != -1) {
            if (intProp != 2) {
                generateExpression(node4, node3);
            } else if (nodeIsDirectCallParameter != -1) {
                dcpLoadAsNumber(nodeIsDirectCallParameter);
            } else {
                generateExpression(node4, node3);
                addObjectToDouble();
            }
            if (intProp != 1) {
                generateExpression(next, node3);
            } else if (nodeIsDirectCallParameter2 != -1) {
                dcpLoadAsNumber(nodeIsDirectCallParameter2);
            } else {
                generateExpression(next, node3);
                addObjectToDouble();
            }
            genSimpleCompare(type, i3, i4);
            return;
        }
        if (nodeIsDirectCallParameter == -1 || nodeIsDirectCallParameter2 == -1) {
            generateExpression(node4, node3);
            generateExpression(next, node3);
        } else {
            l0 l0Var = this.cfw;
            short s = l0Var.m;
            int b = l0Var.b();
            this.cfw.g(nodeIsDirectCallParameter);
            this.cfw.f("java/lang/Void", 178, "TYPE", "Ljava/lang/Class;");
            this.cfw.d(Token.YIELD_STAR, b);
            this.cfw.i(nodeIsDirectCallParameter + 1);
            dcpLoadAsNumber(nodeIsDirectCallParameter2);
            genSimpleCompare(type, i3, i4);
            l0 l0Var2 = this.cfw;
            if (s == l0Var2.m) {
                l0Var2.af(b);
                int b2 = this.cfw.b();
                this.cfw.g(nodeIsDirectCallParameter2);
                this.cfw.f("java/lang/Void", 178, "TYPE", "Ljava/lang/Class;");
                this.cfw.d(Token.YIELD_STAR, b2);
                this.cfw.g(nodeIsDirectCallParameter);
                addObjectToDouble();
                this.cfw.i(nodeIsDirectCallParameter2 + 1);
                genSimpleCompare(type, i3, i4);
                l0 l0Var3 = this.cfw;
                if (s == l0Var3.m) {
                    l0Var3.af(b2);
                    this.cfw.g(nodeIsDirectCallParameter);
                    this.cfw.g(nodeIsDirectCallParameter2);
                } else {
                    throw Codegen.badTree();
                }
            } else {
                throw Codegen.badTree();
            }
        }
        if (type == 17 || type == 16) {
            this.cfw.c(95);
        }
        if (type == 14 || type == 16) {
            str2 = "cmp_LT";
        } else {
            str2 = "cmp_LE";
        }
        addScriptRuntimeInvoke(str2, "(Ljava/lang/Object;Ljava/lang/Object;)Z");
        this.cfw.d(Token.LET, i3);
        this.cfw.d(Token.LAST_TOKEN, i4);
    }

    private void visitIncDec(Node node) {
        boolean z;
        int existingIntProp = node.getExistingIntProp(13);
        Node firstChild = node.getFirstChild();
        int type = firstChild.getType();
        if (type == 33) {
            Node firstChild2 = firstChild.getFirstChild();
            generateExpression(firstChild2, node);
            generateExpression(firstChild2.getNext(), node);
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            this.cfw.s(existingIntProp);
            addScriptRuntimeInvoke("propIncrDecr", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;I)Ljava/lang/Object;");
        } else if (type == 34) {
            throw Kit.codeBug();
        } else if (type == 36) {
            Node firstChild3 = firstChild.getFirstChild();
            generateExpression(firstChild3, node);
            generateExpression(firstChild3.getNext(), node);
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            this.cfw.s(existingIntProp);
            if (firstChild3.getNext().getIntProp(8, -1) != -1) {
                addOptRuntimeInvoke("elemIncrDecr", "(Ljava/lang/Object;DLorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;I)Ljava/lang/Object;");
            } else {
                addScriptRuntimeInvoke("elemIncrDecr", "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;I)Ljava/lang/Object;");
            }
        } else if (type == 39) {
            this.cfw.g(this.variableObjectLocal);
            this.cfw.t(firstChild.getString());
            this.cfw.g(this.contextLocal);
            this.cfw.s(existingIntProp);
            addScriptRuntimeInvoke("nameIncrDecr", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;Lorg/mozilla/javascript/Context;I)Ljava/lang/Object;");
        } else if (type == 55) {
            if (!this.hasVarsInRegs) {
                Kit.codeBug();
            }
            if ((existingIntProp & 2) != 0) {
                z = true;
            } else {
                z = false;
            }
            int varIndex = this.fnCurrent.getVarIndex(firstChild);
            short s = this.varRegisters[varIndex];
            if (this.fnCurrent.fnode.getParamAndVarConst()[varIndex]) {
                if (node.getIntProp(8, -1) != -1) {
                    this.cfw.i(s + (varIsDirectCallParameter(varIndex) ? 1 : 0));
                    if (!z) {
                        this.cfw.r(1.0d);
                        if ((existingIntProp & 1) == 0) {
                            this.cfw.c(99);
                        } else {
                            this.cfw.c(103);
                        }
                    }
                } else {
                    if (varIsDirectCallParameter(varIndex)) {
                        dcpLoadAsObject(s);
                    } else {
                        this.cfw.g(s);
                    }
                    if (z) {
                        this.cfw.c(89);
                        addObjectToDouble();
                        this.cfw.c(88);
                        return;
                    }
                    addObjectToDouble();
                    this.cfw.r(1.0d);
                    if ((existingIntProp & 1) == 0) {
                        this.cfw.c(99);
                    } else {
                        this.cfw.c(103);
                    }
                    addDoubleWrap();
                }
            } else if (node.getIntProp(8, -1) != -1) {
                boolean varIsDirectCallParameter = varIsDirectCallParameter(varIndex);
                l0 l0Var = this.cfw;
                int i = s + (varIsDirectCallParameter ? 1 : 0);
                l0Var.i(i);
                if (z) {
                    this.cfw.c(92);
                }
                this.cfw.r(1.0d);
                if ((existingIntProp & 1) == 0) {
                    this.cfw.c(99);
                } else {
                    this.cfw.c(103);
                }
                if (!z) {
                    this.cfw.c(92);
                }
                this.cfw.as(71, 57, i);
            } else {
                if (varIsDirectCallParameter(varIndex)) {
                    dcpLoadAsObject(s);
                } else {
                    this.cfw.g(s);
                }
                addObjectToDouble();
                if (z) {
                    this.cfw.c(92);
                }
                this.cfw.r(1.0d);
                if ((existingIntProp & 1) == 0) {
                    this.cfw.c(99);
                } else {
                    this.cfw.c(103);
                }
                addDoubleWrap();
                if (!z) {
                    this.cfw.c(89);
                }
                this.cfw.h(s);
                if (z) {
                    addDoubleWrap();
                }
            }
        } else if (type != 68) {
            Codegen.badTree();
        } else {
            generateExpression(firstChild.getFirstChild(), node);
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            this.cfw.s(existingIntProp);
            addScriptRuntimeInvoke("refIncrDecr", "(Lorg/mozilla/javascript/Ref;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;I)Ljava/lang/Object;");
        }
    }

    private void visitObjectLiteral(Node node, Node node2, boolean z) {
        boolean z2;
        Object[] objArr = (Object[]) node.getProp(12);
        int length = objArr.length;
        if (z || ((length <= 10 && this.cfw.j <= 30000) || this.hasVarsInRegs || this.isGenerator || this.inLocalBlock)) {
            if (this.isGenerator) {
                addLoadPropertyValues(node, node2, length);
                addLoadPropertyIds(objArr, length);
                this.cfw.c(95);
            } else {
                addLoadPropertyIds(objArr, length);
                addLoadPropertyValues(node, node2, length);
            }
            Node node3 = node2;
            int i = 0;
            while (true) {
                if (i == length) {
                    z2 = false;
                    break;
                }
                int type = node3.getType();
                if (type == 152 || type == 153) {
                    z2 = true;
                } else {
                    node3 = node3.getNext();
                    i++;
                }
            }
            z2 = true;
            if (z2) {
                this.cfw.s(length);
                this.cfw.d(188, 10);
                for (int i2 = 0; i2 != length; i2++) {
                    this.cfw.c(89);
                    this.cfw.s(i2);
                    int type2 = node2.getType();
                    if (type2 == 152) {
                        this.cfw.c(2);
                    } else if (type2 == 153) {
                        this.cfw.c(4);
                    } else {
                        this.cfw.c(3);
                    }
                    this.cfw.c(79);
                    node2 = node2.getNext();
                }
            } else {
                this.cfw.c(1);
            }
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            addScriptRuntimeInvoke("newObjectLiteral", "([Ljava/lang/Object;[Ljava/lang/Object;[ILorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
            return;
        }
        if (this.literals == null) {
            this.literals = new LinkedList();
        }
        this.literals.add(node);
        this.cfw.g(this.funObjLocal);
        this.cfw.g(this.contextLocal);
        this.cfw.g(this.variableObjectLocal);
        this.cfw.g(this.thisObjLocal);
        this.cfw.g(this.argsLocal);
        this.cfw.m(this.codegen.mainClassName, 182, this.codegen.getBodyMethodName(this.scriptOrFn) + "_literal" + this.literals.size(), "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;");
    }

    private void visitOptimizedCall(Node node, OptFunctionNode optFunctionNode, int i, Node node2) {
        short s;
        String str;
        Node next = node2.getNext();
        String str2 = this.codegen.mainClassName;
        if (i == 30) {
            generateExpression(node2, node);
            s = 0;
        } else {
            generateFunctionAndThisObj(node2, node);
            s = getNewWordLocal();
            this.cfw.h(s);
        }
        int b = this.cfw.b();
        int b2 = this.cfw.b();
        this.cfw.c(89);
        this.cfw.e(193, str2);
        this.cfw.d(Token.SET, b2);
        this.cfw.e(192, str2);
        this.cfw.c(89);
        this.cfw.f(str2, Context.VERSION_1_8, "_id", "I");
        this.cfw.s(this.codegen.getIndex(optFunctionNode.fnode));
        this.cfw.d(160, b2);
        this.cfw.g(this.contextLocal);
        this.cfw.g(this.variableObjectLocal);
        if (i == 30) {
            this.cfw.c(1);
        } else {
            this.cfw.g(s);
        }
        for (Node node3 = next; node3 != null; node3 = node3.getNext()) {
            int nodeIsDirectCallParameter = nodeIsDirectCallParameter(node3);
            if (nodeIsDirectCallParameter >= 0) {
                this.cfw.g(nodeIsDirectCallParameter);
                this.cfw.i(nodeIsDirectCallParameter + 1);
            } else if (node3.getIntProp(8, -1) == 0) {
                this.cfw.f("java/lang/Void", 178, "TYPE", "Ljava/lang/Class;");
                generateExpression(node3, node);
            } else {
                generateExpression(node3, node);
                this.cfw.r(0.0d);
            }
        }
        this.cfw.f("org/mozilla/javascript/ScriptRuntime", 178, "emptyArgs", "[Ljava/lang/Object;");
        l0 l0Var = this.cfw;
        Codegen codegen2 = this.codegen;
        String str3 = codegen2.mainClassName;
        if (i == 30) {
            str = codegen2.getDirectCtorName(optFunctionNode.fnode);
        } else {
            str = codegen2.getBodyMethodName(optFunctionNode.fnode);
        }
        l0Var.m(str3, 184, str, this.codegen.getBodyMethodSignature(optFunctionNode.fnode));
        this.cfw.d(Token.LAST_TOKEN, b);
        this.cfw.af(b2);
        this.cfw.g(this.contextLocal);
        this.cfw.g(this.variableObjectLocal);
        if (i != 30) {
            this.cfw.g(s);
            releaseWordLocal(s);
        }
        generateCallArgArray(node, next, true);
        if (i == 30) {
            addScriptRuntimeInvoke("newObject", "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;");
        } else {
            this.cfw.m("org/mozilla/javascript/Callable", 185, NotificationCompat.CATEGORY_CALL, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;");
        }
        this.cfw.af(b);
    }

    private void visitSetConst(Node node, Node node2) {
        String string = node.getFirstChild().getString();
        while (node2 != null) {
            generateExpression(node2, node);
            node2 = node2.getNext();
        }
        this.cfw.g(this.contextLocal);
        this.cfw.t(string);
        addScriptRuntimeInvoke("setConst", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Ljava/lang/String;)Ljava/lang/Object;");
    }

    private void visitSetConstVar(Node node, Node node2, boolean z) {
        boolean z2;
        if (!this.hasVarsInRegs) {
            Kit.codeBug();
        }
        int varIndex = this.fnCurrent.getVarIndex(node);
        generateExpression(node2.getNext(), node);
        if (node.getIntProp(8, -1) != -1) {
            z2 = true;
        } else {
            z2 = false;
        }
        short s = this.varRegisters[varIndex];
        int b = this.cfw.b();
        int b2 = this.cfw.b();
        if (z2) {
            int i = s + 2;
            this.cfw.l(i);
            this.cfw.d(Token.LET, b2);
            l0 l0Var = this.cfw;
            short s2 = l0Var.m;
            l0Var.s(1);
            this.cfw.as(59, 54, i);
            this.cfw.as(71, 57, s);
            if (z) {
                this.cfw.i(s);
                this.cfw.ag(b2, s2);
            } else {
                this.cfw.d(Token.LAST_TOKEN, b);
                this.cfw.ag(b2, s2);
                this.cfw.c(88);
            }
        } else {
            int i2 = s + 1;
            this.cfw.l(i2);
            this.cfw.d(Token.LET, b2);
            l0 l0Var2 = this.cfw;
            short s3 = l0Var2.m;
            l0Var2.s(1);
            this.cfw.as(59, 54, i2);
            this.cfw.h(s);
            if (z) {
                this.cfw.g(s);
                this.cfw.ag(b2, s3);
            } else {
                this.cfw.d(Token.LAST_TOKEN, b);
                this.cfw.ag(b2, s3);
                this.cfw.c(87);
            }
        }
        this.cfw.af(b);
    }

    private void visitSetElem(int i, Node node, Node node2) {
        boolean z;
        generateExpression(node2, node);
        Node next = node2.getNext();
        if (i == 141) {
            this.cfw.c(89);
        }
        generateExpression(next, node);
        Node next2 = next.getNext();
        if (node.getIntProp(8, -1) != -1) {
            z = true;
        } else {
            z = false;
        }
        if (i == 141) {
            if (z) {
                this.cfw.c(93);
                this.cfw.g(this.contextLocal);
                this.cfw.g(this.variableObjectLocal);
                addScriptRuntimeInvoke("getObjectIndex", "(Ljava/lang/Object;DLorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
            } else {
                this.cfw.c(90);
                this.cfw.g(this.contextLocal);
                this.cfw.g(this.variableObjectLocal);
                addScriptRuntimeInvoke("getObjectElem", "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
            }
        }
        generateExpression(next2, node);
        this.cfw.g(this.contextLocal);
        this.cfw.g(this.variableObjectLocal);
        if (z) {
            addScriptRuntimeInvoke("setObjectIndex", "(Ljava/lang/Object;DLjava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
        } else {
            addScriptRuntimeInvoke("setObjectElem", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
        }
    }

    private void visitSetName(Node node, Node node2) {
        String string = node.getFirstChild().getString();
        while (node2 != null) {
            generateExpression(node2, node);
            node2 = node2.getNext();
        }
        this.cfw.g(this.contextLocal);
        this.cfw.g(this.variableObjectLocal);
        this.cfw.t(string);
        addScriptRuntimeInvoke("setName", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)Ljava/lang/Object;");
    }

    private void visitSetProp(int i, Node node, Node node2) {
        generateExpression(node2, node);
        Node next = node2.getNext();
        if (i == 140) {
            this.cfw.c(89);
        }
        generateExpression(next, node);
        Node next2 = next.getNext();
        if (i == 140) {
            this.cfw.c(90);
            if (node2.getType() == 43 && next.getType() == 41) {
                this.cfw.g(this.contextLocal);
                addScriptRuntimeInvoke("getObjectProp", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;Lorg/mozilla/javascript/Context;)Ljava/lang/Object;");
            } else {
                this.cfw.g(this.contextLocal);
                this.cfw.g(this.variableObjectLocal);
                addScriptRuntimeInvoke("getObjectProp", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
            }
        }
        generateExpression(next2, node);
        this.cfw.g(this.contextLocal);
        this.cfw.g(this.variableObjectLocal);
        addScriptRuntimeInvoke("setObjectProp", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;");
    }

    private void visitSetVar(Node node, Node node2, boolean z) {
        boolean z2;
        if (!this.hasVarsInRegs) {
            Kit.codeBug();
        }
        int varIndex = this.fnCurrent.getVarIndex(node);
        generateExpression(node2.getNext(), node);
        if (node.getIntProp(8, -1) != -1) {
            z2 = true;
        } else {
            z2 = false;
        }
        short s = this.varRegisters[varIndex];
        if (this.fnCurrent.fnode.getParamAndVarConst()[varIndex]) {
            if (z) {
                return;
            }
            if (z2) {
                this.cfw.c(88);
            } else {
                this.cfw.c(87);
            }
        } else if (!varIsDirectCallParameter(varIndex)) {
            boolean isNumberVar = this.fnCurrent.isNumberVar(varIndex);
            if (!z2) {
                if (isNumberVar) {
                    Kit.codeBug();
                }
                this.cfw.h(s);
                if (z) {
                    this.cfw.g(s);
                }
            } else if (isNumberVar) {
                this.cfw.as(71, 57, s);
                if (z) {
                    this.cfw.i(s);
                }
            } else {
                if (z) {
                    this.cfw.c(92);
                }
                addDoubleWrap();
                this.cfw.h(s);
            }
        } else if (z2) {
            if (z) {
                this.cfw.c(92);
            }
            this.cfw.g(s);
            this.cfw.f("java/lang/Void", 178, "TYPE", "Ljava/lang/Class;");
            int b = this.cfw.b();
            int b2 = this.cfw.b();
            this.cfw.d(Token.ARROW, b);
            short s2 = this.cfw.m;
            addDoubleWrap();
            this.cfw.h(s);
            this.cfw.d(Token.LAST_TOKEN, b2);
            this.cfw.ag(b, s2);
            this.cfw.as(71, 57, s + 1);
            this.cfw.af(b2);
        } else {
            if (z) {
                this.cfw.c(89);
            }
            this.cfw.h(s);
        }
    }

    private void visitSpecialCall(Node node, int i, int i2, Node node2) {
        String str;
        String str2;
        this.cfw.g(this.contextLocal);
        if (i == 30) {
            generateExpression(node2, node);
        } else {
            generateFunctionAndThisObj(node2, node);
        }
        generateCallArgArray(node, node2.getNext(), false);
        if (i == 30) {
            this.cfw.g(this.variableObjectLocal);
            this.cfw.g(this.thisObjLocal);
            this.cfw.s(i2);
            str2 = "newObjectSpecial";
            str = "(Lorg/mozilla/javascript/Context;Ljava/lang/Object;[Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;I)Ljava/lang/Object;";
        } else {
            this.cfw.g(this.variableObjectLocal);
            this.cfw.g(this.thisObjLocal);
            this.cfw.s(i2);
            String sourceName = this.scriptOrFn.getSourceName();
            l0 l0Var = this.cfw;
            if (sourceName == null) {
                sourceName = "";
            }
            l0Var.t(sourceName);
            this.cfw.s(this.itsLineNumber);
            str2 = "callSpecial";
            str = "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;ILjava/lang/String;I)Ljava/lang/Object;";
        }
        addOptRuntimeInvoke(str2, str);
    }

    private void visitStandardCall(Node node, Node node2) {
        String str;
        String str2;
        if (node.getType() == 38) {
            Node next = node2.getNext();
            int type = node2.getType();
            if (next == null) {
                if (type == 39) {
                    this.cfw.t(node2.getString());
                    str2 = "callName0";
                    str = "(Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
                } else if (type == 33) {
                    Node firstChild = node2.getFirstChild();
                    generateExpression(firstChild, node);
                    this.cfw.t(firstChild.getNext().getString());
                    str2 = "callProp0";
                    str = "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
                } else if (type != 34) {
                    generateFunctionAndThisObj(node2, node);
                    str2 = "call0";
                    str = "(Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
                } else {
                    throw Kit.codeBug();
                }
            } else if (type == 39) {
                String string = node2.getString();
                generateCallArgArray(node, next, false);
                this.cfw.t(string);
                str2 = "callName";
                str = "([Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
            } else {
                int i = 0;
                for (Node node3 = next; node3 != null; node3 = node3.getNext()) {
                    i++;
                }
                generateFunctionAndThisObj(node2, node);
                if (i == 1) {
                    generateExpression(next, node);
                    str2 = "call1";
                    str = "(Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
                } else if (i == 2) {
                    generateExpression(next, node);
                    generateExpression(next.getNext(), node);
                    str2 = "call2";
                    str = "(Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
                } else {
                    generateCallArgArray(node, next, false);
                    str2 = "callN";
                    str = "(Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
                }
            }
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            addOptRuntimeInvoke(str2, str);
            return;
        }
        throw Codegen.badTree();
    }

    private void visitStandardNew(Node node, Node node2) {
        if (node.getType() == 30) {
            Node next = node2.getNext();
            generateExpression(node2, node);
            this.cfw.g(this.contextLocal);
            this.cfw.g(this.variableObjectLocal);
            generateCallArgArray(node, next, false);
            addScriptRuntimeInvoke("newObject", "(Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Lorg/mozilla/javascript/Scriptable;");
            return;
        }
        throw Codegen.badTree();
    }

    private void visitStrictSetName(Node node, Node node2) {
        String string = node.getFirstChild().getString();
        while (node2 != null) {
            generateExpression(node2, node);
            node2 = node2.getNext();
        }
        this.cfw.g(this.contextLocal);
        this.cfw.g(this.variableObjectLocal);
        this.cfw.t(string);
        addScriptRuntimeInvoke("strictSetName", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)Ljava/lang/Object;");
    }

    private void visitSwitch(Jump jump, Node node) {
        generateExpression(node, jump);
        short newWordLocal = getNewWordLocal();
        this.cfw.h(newWordLocal);
        Jump jump2 = (Jump) node.getNext();
        while (jump2 != null) {
            if (jump2.getType() == 116) {
                generateExpression(jump2.getFirstChild(), jump2);
                this.cfw.g(newWordLocal);
                addScriptRuntimeInvoke("shallowEq", "(Ljava/lang/Object;Ljava/lang/Object;)Z");
                addGoto(jump2.target, Token.LET);
                jump2 = (Jump) jump2.getNext();
            } else {
                throw Codegen.badTree();
            }
        }
        releaseWordLocal(newWordLocal);
    }

    private void visitTryCatchFinally(Jump jump, Node node) {
        int i;
        int i2;
        Jump jump2 = jump;
        short newWordLocal = getNewWordLocal();
        this.cfw.g(this.variableObjectLocal);
        this.cfw.h(newWordLocal);
        int b = this.cfw.b();
        this.cfw.ag(b, 0);
        Node node2 = jump2.target;
        Node node3 = jump.getFinally();
        int[] iArr = new int[5];
        this.exceptionManager.pushExceptionInfo(jump2);
        if (node2 != null) {
            iArr[0] = this.cfw.b();
            iArr[1] = this.cfw.b();
            iArr[2] = this.cfw.b();
            Context currentContext = Context.getCurrentContext();
            if (currentContext != null && currentContext.hasFeature(13)) {
                iArr[3] = this.cfw.b();
            }
        }
        if (node3 != null) {
            iArr[4] = this.cfw.b();
        }
        this.exceptionManager.setHandlers(iArr, b);
        if (this.isGenerator && node3 != null) {
            FinallyReturnPoint finallyReturnPoint = new FinallyReturnPoint();
            if (this.finallys == null) {
                this.finallys = new HashMap();
            }
            this.finallys.put(node3, finallyReturnPoint);
            this.finallys.put(node3.getNext(), finallyReturnPoint);
        }
        for (Node node4 = node; node4 != null; node4 = node4.getNext()) {
            if (node4 == node2) {
                int targetLabel = getTargetLabel(node2);
                this.exceptionManager.removeHandler(0, targetLabel);
                this.exceptionManager.removeHandler(1, targetLabel);
                this.exceptionManager.removeHandler(2, targetLabel);
                this.exceptionManager.removeHandler(3, targetLabel);
            }
            generateStatement(node4);
        }
        int b2 = this.cfw.b();
        this.cfw.d(Token.LAST_TOKEN, b2);
        int localBlockRegister = getLocalBlockRegister(jump);
        if (node2 != null) {
            int labelId = node2.labelId();
            short s = newWordLocal;
            int i3 = labelId;
            i = localBlockRegister;
            i2 = b2;
            generateCatchBlock(0, s, i3, localBlockRegister, iArr[0]);
            generateCatchBlock(1, s, i3, localBlockRegister, iArr[1]);
            generateCatchBlock(2, s, i3, localBlockRegister, iArr[2]);
            Context currentContext2 = Context.getCurrentContext();
            if (currentContext2 != null && currentContext2.hasFeature(13)) {
                generateCatchBlock(3, newWordLocal, labelId, i, iArr[3]);
            }
        } else {
            i = localBlockRegister;
            i2 = b2;
        }
        if (node3 != null) {
            int b3 = this.cfw.b();
            int b4 = this.cfw.b();
            l0 l0Var = this.cfw;
            l0Var.m = 1;
            l0Var.af(b3);
            if (!this.isGenerator) {
                this.cfw.af(iArr[4]);
            }
            int i4 = i;
            this.cfw.h(i4);
            this.cfw.g(newWordLocal);
            this.cfw.h(this.variableObjectLocal);
            int labelId2 = node3.labelId();
            if (this.isGenerator) {
                addGotoWithReturn(node3);
            } else {
                inlineFinally(node3, iArr[4], b4);
            }
            this.cfw.g(i4);
            if (this.isGenerator) {
                this.cfw.e(192, "java/lang/Throwable");
            }
            this.cfw.c(191);
            this.cfw.af(b4);
            if (this.isGenerator) {
                this.cfw.j(b, labelId2, b3, (String) null);
            }
        }
        releaseWordLocal(newWordLocal);
        this.cfw.af(i2);
        if (!this.isGenerator) {
            this.exceptionManager.popExceptionInfo();
        }
    }

    private void visitTypeofname(Node node) {
        int indexForNameNode;
        if (!this.hasVarsInRegs || (indexForNameNode = this.fnCurrent.fnode.getIndexForNameNode(node)) < 0) {
            this.cfw.g(this.variableObjectLocal);
            this.cfw.t(node.getString());
            addScriptRuntimeInvoke("typeofName", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)Ljava/lang/String;");
        } else if (this.fnCurrent.isNumberVar(indexForNameNode)) {
            this.cfw.t("number");
        } else if (varIsDirectCallParameter(indexForNameNode)) {
            short s = this.varRegisters[indexForNameNode];
            this.cfw.g(s);
            this.cfw.f("java/lang/Void", 178, "TYPE", "Ljava/lang/Class;");
            int b = this.cfw.b();
            this.cfw.d(Token.ARROW, b);
            l0 l0Var = this.cfw;
            short s2 = l0Var.m;
            l0Var.g(s);
            addScriptRuntimeInvoke("typeof", "(Ljava/lang/Object;)Ljava/lang/String;");
            int b2 = this.cfw.b();
            this.cfw.d(Token.LAST_TOKEN, b2);
            this.cfw.ag(b, s2);
            this.cfw.t("number");
            this.cfw.af(b2);
        } else {
            this.cfw.g(this.varRegisters[indexForNameNode]);
            addScriptRuntimeInvoke("typeof", "(Ljava/lang/Object;)Ljava/lang/String;");
        }
    }

    public void generateBodyCode() {
        Node node;
        this.isGenerator = Codegen.isGenerator(this.scriptOrFn);
        initBodyGeneration();
        if (this.isGenerator) {
            String k = y2.k(new StringBuilder("("), this.codegen.mainClassSignature, "Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Ljava/lang/Object;I)Ljava/lang/Object;");
            l0 l0Var = this.cfw;
            l0Var.ap(this.codegen.getBodyMethodName(this.scriptOrFn) + "_gen", k, 10);
        } else {
            this.cfw.ap(this.codegen.getBodyMethodName(this.scriptOrFn), this.codegen.getBodyMethodSignature(this.scriptOrFn), 10);
        }
        generatePrologue();
        if (this.fnCurrent != null) {
            node = this.scriptOrFn.getLastChild();
        } else {
            node = this.scriptOrFn;
        }
        generateStatement(node);
        generateEpilogue();
        this.cfw.aq((short) (this.localsMax + 1));
        if (this.isGenerator) {
            generateGenerator();
        }
        if (this.literals != null) {
            for (int i = 0; i < this.literals.size(); i++) {
                Node node2 = this.literals.get(i);
                int type = node2.getType();
                if (type == 66) {
                    generateArrayLiteralFactory(node2, i + 1);
                } else if (type != 67) {
                    Kit.codeBug(Token.typeToName(type));
                } else {
                    generateObjectLiteralFactory(node2, i + 1);
                }
            }
        }
    }

    private short getNewWordLocal() {
        return getNewWordIntern(1);
    }

    private void addInstructionCount(int i) {
        this.cfw.g(this.contextLocal);
        this.cfw.s(i);
        addScriptRuntimeInvoke("addInstructionCount", "(Lorg/mozilla/javascript/Context;I)V");
    }

    private void inlineFinally(Node node) {
        int b = this.cfw.b();
        int b2 = this.cfw.b();
        this.cfw.af(b);
        inlineFinally(node, b, b2);
        this.cfw.af(b2);
    }
}
