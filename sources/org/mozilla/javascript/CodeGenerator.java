package org.mozilla.javascript;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import org.mozilla.javascript.ObjToIntMap;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.Block;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.Jump;
import org.mozilla.javascript.ast.Scope;
import org.mozilla.javascript.ast.ScriptNode;
import org.mozilla.javascript.ast.VariableInitializer;

class CodeGenerator extends Icode {
    private static final int ECF_TAIL = 1;
    private static final int MIN_FIXUP_TABLE_SIZE = 40;
    private static final int MIN_LABEL_TABLE_SIZE = 32;
    private CompilerEnvirons compilerEnv;
    private int doubleTableTop;
    private int exceptionTableTop;
    private long[] fixupTable;
    private int fixupTableTop;
    private int iCodeTop;
    private InterpreterData itsData;
    private boolean itsInFunctionFlag;
    private boolean itsInTryFlag;
    private int[] labelTable;
    private int labelTableTop;
    private int lineNumber;
    private ObjArray literalIds = new ObjArray();
    private int localTop;
    private ScriptNode scriptOrFn;
    private int stackDepth;
    private ObjToIntMap strings = new ObjToIntMap(20);

    private void addBackwardGoto(int i, int i2) {
        int i3 = this.iCodeTop;
        if (i3 > i2) {
            addGotoOp(i);
            resolveGoto(i3, i2);
            return;
        }
        throw Kit.codeBug();
    }

    /* JADX WARNING: type inference failed for: r8v0, types: [boolean] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void addExceptionHandler(int r5, int r6, int r7, boolean r8, int r9, int r10) {
        /*
            r4 = this;
            int r0 = r4.exceptionTableTop
            org.mozilla.javascript.InterpreterData r1 = r4.itsData
            int[] r1 = r1.itsExceptionTable
            if (r1 != 0) goto L_0x0016
            if (r0 == 0) goto L_0x000d
            org.mozilla.javascript.Kit.codeBug()
        L_0x000d:
            r1 = 12
            int[] r1 = new int[r1]
            org.mozilla.javascript.InterpreterData r2 = r4.itsData
            r2.itsExceptionTable = r1
            goto L_0x0027
        L_0x0016:
            int r2 = r1.length
            if (r2 != r0) goto L_0x0027
            int r2 = r1.length
            int r2 = r2 * 2
            int[] r2 = new int[r2]
            r3 = 0
            java.lang.System.arraycopy(r1, r3, r2, r3, r0)
            org.mozilla.javascript.InterpreterData r1 = r4.itsData
            r1.itsExceptionTable = r2
            r1 = r2
        L_0x0027:
            int r2 = r0 + 0
            r1[r2] = r5
            int r5 = r0 + 1
            r1[r5] = r6
            int r5 = r0 + 2
            r1[r5] = r7
            int r5 = r0 + 3
            r1[r5] = r8
            int r5 = r0 + 4
            r1[r5] = r9
            int r5 = r0 + 5
            r1[r5] = r10
            int r0 = r0 + 6
            r4.exceptionTableTop = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.CodeGenerator.addExceptionHandler(int, int, int, boolean, int, int):void");
    }

    private void addGoto(Node node, int i) {
        int targetLabel = getTargetLabel(node);
        if (targetLabel >= this.labelTableTop) {
            Kit.codeBug();
        }
        int i2 = this.labelTable[targetLabel];
        if (i2 != -1) {
            addBackwardGoto(i, i2);
            return;
        }
        int i3 = this.iCodeTop;
        addGotoOp(i);
        int i4 = this.fixupTableTop;
        long[] jArr = this.fixupTable;
        if (jArr == null || i4 == jArr.length) {
            if (jArr == null) {
                this.fixupTable = new long[40];
            } else {
                long[] jArr2 = new long[(jArr.length * 2)];
                System.arraycopy(jArr, 0, jArr2, 0, i4);
                this.fixupTable = jArr2;
            }
        }
        this.fixupTableTop = i4 + 1;
        this.fixupTable[i4] = (((long) targetLabel) << 32) | ((long) i3);
    }

    private void addGotoOp(int i) {
        byte[] bArr = this.itsData.itsICode;
        int i2 = this.iCodeTop;
        if (i2 + 3 > bArr.length) {
            bArr = increaseICodeCapacity(3);
        }
        bArr[i2] = (byte) i;
        this.iCodeTop = i2 + 1 + 2;
    }

    private void addIcode(int i) {
        if (Icode.validIcode(i)) {
            addUint8(i & 255);
            return;
        }
        throw Kit.codeBug();
    }

    private void addIndexOp(int i, int i2) {
        addIndexPrefix(i2);
        if (Icode.validIcode(i)) {
            addIcode(i);
        } else {
            addToken(i);
        }
    }

    private void addIndexPrefix(int i) {
        if (i < 0) {
            Kit.codeBug();
        }
        if (i < 6) {
            addIcode(-32 - i);
        } else if (i <= 255) {
            addIcode(-38);
            addUint8(i);
        } else if (i <= 65535) {
            addIcode(-39);
            addUint16(i);
        } else {
            addIcode(-40);
            addInt(i);
        }
    }

    private void addInt(int i) {
        byte[] bArr = this.itsData.itsICode;
        int i2 = this.iCodeTop;
        int i3 = i2 + 4;
        if (i3 > bArr.length) {
            bArr = increaseICodeCapacity(4);
        }
        bArr[i2] = (byte) (i >>> 24);
        bArr[i2 + 1] = (byte) (i >>> 16);
        bArr[i2 + 2] = (byte) (i >>> 8);
        bArr[i2 + 3] = (byte) i;
        this.iCodeTop = i3;
    }

    private void addStringOp(int i, String str) {
        addStringPrefix(str);
        if (Icode.validIcode(i)) {
            addIcode(i);
        } else {
            addToken(i);
        }
    }

    private void addStringPrefix(String str) {
        int i = this.strings.get(str, -1);
        if (i == -1) {
            i = this.strings.size();
            this.strings.put(str, i);
        }
        if (i < 4) {
            addIcode(-41 - i);
        } else if (i <= 255) {
            addIcode(-45);
            addUint8(i);
        } else if (i <= 65535) {
            addIcode(-46);
            addUint16(i);
        } else {
            addIcode(-47);
            addInt(i);
        }
    }

    private void addToken(int i) {
        if (Icode.validTokenCode(i)) {
            addUint8(i);
            return;
        }
        throw Kit.codeBug();
    }

    private void addUint16(int i) {
        if ((-65536 & i) == 0) {
            byte[] bArr = this.itsData.itsICode;
            int i2 = this.iCodeTop;
            int i3 = i2 + 2;
            if (i3 > bArr.length) {
                bArr = increaseICodeCapacity(2);
            }
            bArr[i2] = (byte) (i >>> 8);
            bArr[i2 + 1] = (byte) i;
            this.iCodeTop = i3;
            return;
        }
        throw Kit.codeBug();
    }

    private void addUint8(int i) {
        if ((i & InputDeviceCompat.SOURCE_ANY) == 0) {
            byte[] bArr = this.itsData.itsICode;
            int i2 = this.iCodeTop;
            if (i2 == bArr.length) {
                bArr = increaseICodeCapacity(1);
            }
            bArr[i2] = (byte) i;
            this.iCodeTop = i2 + 1;
            return;
        }
        throw Kit.codeBug();
    }

    private void addVarOp(int i, int i2) {
        int i3;
        if (i != -7) {
            if (i != 157) {
                if (i != 55 && i != 56) {
                    throw Kit.codeBug();
                } else if (i2 < 128) {
                    if (i == 55) {
                        i3 = -48;
                    } else {
                        i3 = -49;
                    }
                    addIcode(i3);
                    addUint8(i2);
                    return;
                }
            } else if (i2 < 128) {
                addIcode(-61);
                addUint8(i2);
                return;
            } else {
                addIndexOp(-60, i2);
                return;
            }
        }
        addIndexOp(i, i2);
    }

    private int allocLocal() {
        int i = this.localTop;
        int i2 = i + 1;
        this.localTop = i2;
        InterpreterData interpreterData = this.itsData;
        if (i2 > interpreterData.itsMaxLocals) {
            interpreterData.itsMaxLocals = i2;
        }
        return i;
    }

    private static RuntimeException badTree(Node node) {
        throw new RuntimeException(node.toString());
    }

    private void fixLabelGotos() {
        int i = 0;
        while (i < this.fixupTableTop) {
            long j = this.fixupTable[i];
            int i2 = (int) j;
            int i3 = this.labelTable[(int) (j >> 32)];
            if (i3 != -1) {
                resolveGoto(i2, i3);
                i++;
            } else {
                throw Kit.codeBug();
            }
        }
        this.fixupTableTop = 0;
    }

    private void generateCallFunAndThis(Node node) {
        int type = node.getType();
        if (type == 33 || type == 36) {
            Node firstChild = node.getFirstChild();
            visitExpression(firstChild, 0);
            Node next = firstChild.getNext();
            if (type == 33) {
                addStringOp(-16, next.getString());
                stackChange(1);
                return;
            }
            visitExpression(next, 0);
            addIcode(-17);
        } else if (type != 39) {
            visitExpression(node, 0);
            addIcode(-18);
            stackChange(1);
        } else {
            addStringOp(-15, node.getString());
            stackChange(2);
        }
    }

    private void generateFunctionICode() {
        this.itsInFunctionFlag = true;
        FunctionNode functionNode = (FunctionNode) this.scriptOrFn;
        this.itsData.itsFunctionType = functionNode.getFunctionType();
        this.itsData.itsNeedsActivation = functionNode.requiresActivation();
        if (functionNode.getFunctionName() != null) {
            this.itsData.itsName = functionNode.getName();
        }
        if (functionNode.isGenerator()) {
            addIcode(-62);
            addUint16(functionNode.getBaseLineno() & SupportMenu.USER_MASK);
        }
        if (functionNode.isInStrictMode()) {
            this.itsData.isStrict = true;
        }
        if (functionNode.isES6Generator()) {
            this.itsData.isES6Generator = true;
        }
        this.itsData.declaredAsVar = functionNode.getParent() instanceof VariableInitializer;
        generateICodeFromTree(functionNode.getLastChild());
    }

    private void generateICodeFromTree(Node node) {
        generateNestedFunctions();
        generateRegExpLiterals();
        visitStatement(node, 0);
        fixLabelGotos();
        if (this.itsData.itsFunctionType == 0) {
            addToken(65);
        }
        byte[] bArr = this.itsData.itsICode;
        int length = bArr.length;
        int i = this.iCodeTop;
        if (length != i) {
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, i);
            this.itsData.itsICode = bArr2;
        }
        if (this.strings.size() == 0) {
            this.itsData.itsStringTable = null;
        } else {
            this.itsData.itsStringTable = new String[this.strings.size()];
            ObjToIntMap.Iterator newIterator = this.strings.newIterator();
            newIterator.start();
            while (!newIterator.done()) {
                String str = (String) newIterator.getKey();
                int value = newIterator.getValue();
                if (this.itsData.itsStringTable[value] != null) {
                    Kit.codeBug();
                }
                this.itsData.itsStringTable[value] = str;
                newIterator.next();
            }
        }
        int i2 = this.doubleTableTop;
        if (i2 == 0) {
            this.itsData.itsDoubleTable = null;
        } else {
            double[] dArr = this.itsData.itsDoubleTable;
            if (dArr.length != i2) {
                double[] dArr2 = new double[i2];
                System.arraycopy(dArr, 0, dArr2, 0, i2);
                this.itsData.itsDoubleTable = dArr2;
            }
        }
        int i3 = this.exceptionTableTop;
        if (i3 != 0) {
            int[] iArr = this.itsData.itsExceptionTable;
            if (iArr.length != i3) {
                int[] iArr2 = new int[i3];
                System.arraycopy(iArr, 0, iArr2, 0, i3);
                this.itsData.itsExceptionTable = iArr2;
            }
        }
        this.itsData.itsMaxVars = this.scriptOrFn.getParamAndVarCount();
        InterpreterData interpreterData = this.itsData;
        interpreterData.itsMaxFrameArray = interpreterData.itsMaxVars + interpreterData.itsMaxLocals + interpreterData.itsMaxStack;
        interpreterData.argNames = this.scriptOrFn.getParamAndVarNames();
        this.itsData.argIsConst = this.scriptOrFn.getParamAndVarConst();
        this.itsData.argCount = this.scriptOrFn.getParamCount();
        this.itsData.encodedSourceStart = this.scriptOrFn.getEncodedSourceStart();
        this.itsData.encodedSourceEnd = this.scriptOrFn.getEncodedSourceEnd();
        if (this.literalIds.size() != 0) {
            this.itsData.literalIds = this.literalIds.toArray();
        }
    }

    private void generateNestedFunctions() {
        int functionCount = this.scriptOrFn.getFunctionCount();
        if (functionCount != 0) {
            InterpreterData[] interpreterDataArr = new InterpreterData[functionCount];
            for (int i = 0; i != functionCount; i++) {
                FunctionNode functionNode = this.scriptOrFn.getFunctionNode(i);
                CodeGenerator codeGenerator = new CodeGenerator();
                codeGenerator.compilerEnv = this.compilerEnv;
                codeGenerator.scriptOrFn = functionNode;
                codeGenerator.itsData = new InterpreterData(this.itsData);
                codeGenerator.generateFunctionICode();
                interpreterDataArr[i] = codeGenerator.itsData;
                AstNode parent = functionNode.getParent();
                if (!(parent instanceof AstRoot) && !(parent instanceof Scope) && !(parent instanceof Block)) {
                    codeGenerator.itsData.declaredAsFunctionExpression = true;
                }
            }
            this.itsData.itsNestedFunctions = interpreterDataArr;
        }
    }

    private void generateRegExpLiterals() {
        int regexpCount = this.scriptOrFn.getRegexpCount();
        if (regexpCount != 0) {
            Context context = Context.getContext();
            RegExpProxy checkRegExpProxy = ScriptRuntime.checkRegExpProxy(context);
            Object[] objArr = new Object[regexpCount];
            for (int i = 0; i != regexpCount; i++) {
                objArr[i] = checkRegExpProxy.compileRegExp(context, this.scriptOrFn.getRegexpString(i), this.scriptOrFn.getRegexpFlags(i));
            }
            this.itsData.itsRegExpLiterals = objArr;
        }
    }

    private int getDoubleIndex(double d) {
        int i = this.doubleTableTop;
        if (i == 0) {
            this.itsData.itsDoubleTable = new double[64];
        } else {
            double[] dArr = this.itsData.itsDoubleTable;
            if (dArr.length == i) {
                double[] dArr2 = new double[(i * 2)];
                System.arraycopy(dArr, 0, dArr2, 0, i);
                this.itsData.itsDoubleTable = dArr2;
            }
        }
        this.itsData.itsDoubleTable[i] = d;
        this.doubleTableTop = i + 1;
        return i;
    }

    private static int getLocalBlockRef(Node node) {
        return ((Node) node.getProp(3)).getExistingIntProp(2);
    }

    private int getTargetLabel(Node node) {
        int labelId = node.labelId();
        if (labelId != -1) {
            return labelId;
        }
        int i = this.labelTableTop;
        int[] iArr = this.labelTable;
        if (iArr == null || i == iArr.length) {
            if (iArr == null) {
                this.labelTable = new int[32];
            } else {
                int[] iArr2 = new int[(iArr.length * 2)];
                System.arraycopy(iArr, 0, iArr2, 0, i);
                this.labelTable = iArr2;
            }
        }
        this.labelTableTop = i + 1;
        this.labelTable[i] = -1;
        node.labelId(i);
        return i;
    }

    private byte[] increaseICodeCapacity(int i) {
        byte[] bArr = this.itsData.itsICode;
        int length = bArr.length;
        int i2 = this.iCodeTop;
        int i3 = i + i2;
        if (i3 > length) {
            int i4 = length * 2;
            if (i3 <= i4) {
                i3 = i4;
            }
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, 0, bArr2, 0, i2);
            this.itsData.itsICode = bArr2;
            return bArr2;
        }
        throw Kit.codeBug();
    }

    private void markTargetLabel(Node node) {
        int targetLabel = getTargetLabel(node);
        if (this.labelTable[targetLabel] != -1) {
            Kit.codeBug();
        }
        this.labelTable[targetLabel] = this.iCodeTop;
    }

    private void releaseLocal(int i) {
        int i2 = this.localTop - 1;
        this.localTop = i2;
        if (i != i2) {
            Kit.codeBug();
        }
    }

    private void resolveForwardGoto(int i) {
        int i2 = this.iCodeTop;
        if (i2 >= i + 3) {
            resolveGoto(i, i2);
            return;
        }
        throw Kit.codeBug();
    }

    private void resolveGoto(int i, int i2) {
        int i3 = i2 - i;
        if (i3 < 0 || i3 > 2) {
            int i4 = i + 1;
            if (i3 != ((short) i3)) {
                InterpreterData interpreterData = this.itsData;
                if (interpreterData.longJumps == null) {
                    interpreterData.longJumps = new UintMap();
                }
                this.itsData.longJumps.put(i4, i2);
                i3 = 0;
            }
            byte[] bArr = this.itsData.itsICode;
            bArr[i4] = (byte) (i3 >> 8);
            bArr[i4 + 1] = (byte) i3;
            return;
        }
        throw Kit.codeBug();
    }

    private void stackChange(int i) {
        if (i <= 0) {
            this.stackDepth += i;
            return;
        }
        int i2 = this.stackDepth + i;
        InterpreterData interpreterData = this.itsData;
        if (i2 > interpreterData.itsMaxStack) {
            interpreterData.itsMaxStack = i2;
        }
        this.stackDepth = i2;
    }

    private void updateLineNumber(Node node) {
        int lineno = node.getLineno();
        if (lineno != this.lineNumber && lineno >= 0) {
            InterpreterData interpreterData = this.itsData;
            if (interpreterData.firstLinePC < 0) {
                interpreterData.firstLinePC = lineno;
            }
            this.lineNumber = lineno;
            addIcode(-26);
            addUint16(lineno & SupportMenu.USER_MASK);
        }
    }

    private void visitArrayComprehension(Node node, Node node2, Node node3) {
        visitStatement(node2, this.stackDepth);
        visitExpression(node3, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x028b, code lost:
        if (r2 != 30) goto L_0x0291;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x028d, code lost:
        visitExpression(r3, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0291, code lost:
        generateCallFunAndThis(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0294, code lost:
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0295, code lost:
        r3 = r3.getNext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x0299, code lost:
        if (r3 == null) goto L_0x02a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x029b, code lost:
        visitExpression(r3, 0);
        r6 = r6 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x02a1, code lost:
        r0 = r0.getIntProp(10, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x02a9, code lost:
        if (r2 == 71) goto L_0x02c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x02ab, code lost:
        if (r0 == 0) goto L_0x02c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x02ad, code lost:
        addIndexOp(-21, r6);
        addUint8(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x02b5, code lost:
        if (r2 != 30) goto L_0x02b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x02b7, code lost:
        r9 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x02b8, code lost:
        addUint8(r9);
        addUint16(r1.lineNumber & androidx.core.internal.view.SupportMenu.USER_MASK);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x02c5, code lost:
        if (r2 != 38) goto L_0x02db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x02c9, code lost:
        if ((r19 & 1) == 0) goto L_0x02db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x02d1, code lost:
        if (r1.compilerEnv.isGenerateDebugInfo() != false) goto L_0x02db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x02d5, code lost:
        if (r1.itsInTryFlag != false) goto L_0x02db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x02d7, code lost:
        r2 = -55;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x02db, code lost:
        addIndexOp(r2, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x02de, code lost:
        if (r2 != 30) goto L_0x02e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x02e0, code lost:
        stackChange(-r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x02e5, code lost:
        stackChange(-1 - r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x02ea, code lost:
        r0 = r1.itsData;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x02ee, code lost:
        if (r6 <= r0.itsMaxCalleeArgs) goto L_0x041c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x02f0, code lost:
        r0.itsMaxCalleeArgs = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x02f4, code lost:
        visitExpression(r3, 0);
        visitExpression(r3.getNext(), 0);
        addToken(r2);
        stackChange(-1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x0306, code lost:
        r0 = r3.getString();
        visitExpression(r3, 0);
        visitExpression(r3.getNext(), 0);
        addStringOp(r2, r0);
        stackChange(-1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0194, code lost:
        addToken(r2);
        stackChange(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01f5, code lost:
        visitExpression(r3, 0);
        r0 = r3.getNext();
        visitExpression(r0, 0);
        r0 = r0.getNext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0206, code lost:
        if (r2 != 141) goto L_0x0219;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0208, code lost:
        addIcode(-2);
        stackChange(2);
        addToken(36);
        stackChange(-1);
        stackChange(-1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0219, code lost:
        visitExpression(r0, 0);
        addToken(37);
        stackChange(-2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0226, code lost:
        visitExpression(r3, 0);
        r0 = r3.getNext();
        r3 = r0.getString();
        r0 = r0.getNext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0237, code lost:
        if (r2 != 140) goto L_0x0247;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0239, code lost:
        addIcode(-1);
        stackChange(1);
        addStringOp(33, r3);
        stackChange(-1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0247, code lost:
        visitExpression(r0, 0);
        addStringOp(35, r3);
        stackChange(-1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void visitExpression(org.mozilla.javascript.Node r18, int r19) {
        /*
            r17 = this;
            r1 = r17
            r0 = r18
            int r2 = r18.getType()
            org.mozilla.javascript.Node r3 = r18.getFirstChild()
            int r4 = r1.stackDepth
            r5 = 90
            r6 = -4
            r7 = -1
            r8 = 1
            r9 = 0
            if (r2 == r5) goto L_0x0403
            r5 = 103(0x67, float:1.44E-43)
            r10 = 7
            if (r2 == r5) goto L_0x03d9
            r5 = 110(0x6e, float:1.54E-43)
            r11 = 4
            r12 = 2
            if (r2 == r5) goto L_0x03b4
            r5 = -50
            r13 = 127(0x7f, float:1.78E-43)
            if (r2 == r13) goto L_0x03a4
            r14 = 143(0x8f, float:2.0E-43)
            if (r2 == r14) goto L_0x0380
            r15 = 147(0x93, float:2.06E-43)
            if (r2 == r15) goto L_0x0362
            r15 = 160(0xa0, float:2.24E-43)
            if (r2 == r15) goto L_0x0340
            r15 = 166(0xa6, float:2.33E-43)
            r16 = 65535(0xffff, float:9.1834E-41)
            if (r2 == r15) goto L_0x031c
            switch(r2) {
                case 8: goto L_0x0306;
                case 9: goto L_0x02f4;
                case 10: goto L_0x02f4;
                case 11: goto L_0x02f4;
                case 12: goto L_0x02f4;
                case 13: goto L_0x02f4;
                case 14: goto L_0x02f4;
                case 15: goto L_0x02f4;
                case 16: goto L_0x02f4;
                case 17: goto L_0x02f4;
                case 18: goto L_0x02f4;
                case 19: goto L_0x02f4;
                case 20: goto L_0x02f4;
                case 21: goto L_0x02f4;
                case 22: goto L_0x02f4;
                case 23: goto L_0x02f4;
                case 24: goto L_0x02f4;
                case 25: goto L_0x02f4;
                case 26: goto L_0x03a4;
                case 27: goto L_0x03a4;
                case 28: goto L_0x03a4;
                case 29: goto L_0x03a4;
                case 30: goto L_0x0289;
                case 31: goto L_0x0264;
                case 32: goto L_0x03a4;
                case 33: goto L_0x0254;
                case 34: goto L_0x0254;
                case 35: goto L_0x0226;
                case 36: goto L_0x02f4;
                case 37: goto L_0x01f5;
                case 38: goto L_0x0289;
                case 39: goto L_0x01e9;
                case 40: goto L_0x019c;
                case 41: goto L_0x01e9;
                case 42: goto L_0x0194;
                case 43: goto L_0x0194;
                case 44: goto L_0x0194;
                case 45: goto L_0x0194;
                case 46: goto L_0x02f4;
                case 47: goto L_0x02f4;
                case 48: goto L_0x0186;
                case 49: goto L_0x01e9;
                default: goto L_0x003d;
            }
        L_0x003d:
            r11 = 55
            switch(r2) {
                case 52: goto L_0x02f4;
                case 53: goto L_0x02f4;
                case 54: goto L_0x0178;
                case 55: goto L_0x0161;
                case 56: goto L_0x0144;
                default: goto L_0x0042;
            }
        L_0x0042:
            switch(r2) {
                case 62: goto L_0x0138;
                case 63: goto L_0x0138;
                case 64: goto L_0x0194;
                default: goto L_0x0045;
            }
        L_0x0045:
            switch(r2) {
                case 66: goto L_0x0133;
                case 67: goto L_0x0133;
                case 68: goto L_0x012b;
                case 69: goto L_0x0380;
                case 70: goto L_0x012b;
                case 71: goto L_0x0289;
                case 72: goto L_0x011b;
                case 73: goto L_0x031c;
                case 74: goto L_0x0306;
                case 75: goto L_0x0113;
                case 76: goto L_0x0113;
                case 77: goto L_0x0113;
                case 78: goto L_0x00f8;
                case 79: goto L_0x00f8;
                case 80: goto L_0x00f8;
                case 81: goto L_0x00f8;
                default: goto L_0x0048;
            }
        L_0x0048:
            switch(r2) {
                case 105: goto L_0x00cd;
                case 106: goto L_0x00cd;
                case 107: goto L_0x00c8;
                case 108: goto L_0x00c8;
                default: goto L_0x004b;
            }
        L_0x004b:
            switch(r2) {
                case 138: goto L_0x0099;
                case 139: goto L_0x0094;
                case 140: goto L_0x0226;
                case 141: goto L_0x01f5;
                default: goto L_0x004e;
            }
        L_0x004e:
            switch(r2) {
                case 156: goto L_0x007c;
                case 157: goto L_0x005f;
                case 158: goto L_0x0056;
                default: goto L_0x0051;
            }
        L_0x0051:
            java.lang.RuntimeException r0 = badTree(r18)
            throw r0
        L_0x0056:
            org.mozilla.javascript.Node r2 = r3.getNext()
            r1.visitArrayComprehension(r0, r3, r2)
            goto L_0x041c
        L_0x005f:
            org.mozilla.javascript.InterpreterData r0 = r1.itsData
            boolean r0 = r0.itsNeedsActivation
            if (r0 == 0) goto L_0x0068
            org.mozilla.javascript.Kit.codeBug()
        L_0x0068:
            org.mozilla.javascript.ast.ScriptNode r0 = r1.scriptOrFn
            int r0 = r0.getIndexForNameNode(r3)
            org.mozilla.javascript.Node r2 = r3.getNext()
            r1.visitExpression(r2, r9)
            r2 = 157(0x9d, float:2.2E-43)
            r1.addVarOp(r2, r0)
            goto L_0x041c
        L_0x007c:
            java.lang.String r0 = r3.getString()
            r1.visitExpression(r3, r9)
            org.mozilla.javascript.Node r2 = r3.getNext()
            r1.visitExpression(r2, r9)
            r2 = -59
            r1.addStringOp(r2, r0)
            r1.stackChange(r7)
            goto L_0x041c
        L_0x0094:
            r1.stackChange(r8)
            goto L_0x041c
        L_0x0099:
            boolean r2 = r1.itsInFunctionFlag
            if (r2 == 0) goto L_0x00aa
            org.mozilla.javascript.InterpreterData r2 = r1.itsData
            boolean r2 = r2.itsNeedsActivation
            if (r2 != 0) goto L_0x00aa
            org.mozilla.javascript.ast.ScriptNode r2 = r1.scriptOrFn
            int r2 = r2.getIndexForNameNode(r0)
            goto L_0x00ab
        L_0x00aa:
            r2 = -1
        L_0x00ab:
            if (r2 != r7) goto L_0x00bb
            r2 = -14
            java.lang.String r0 = r18.getString()
            r1.addStringOp(r2, r0)
            r1.stackChange(r8)
            goto L_0x041c
        L_0x00bb:
            r1.addVarOp(r11, r2)
            r1.stackChange(r8)
            r0 = 32
            r1.addToken(r0)
            goto L_0x041c
        L_0x00c8:
            r1.visitIncDec(r0, r3)
            goto L_0x041c
        L_0x00cd:
            r1.visitExpression(r3, r9)
            r1.addIcode(r7)
            r1.stackChange(r8)
            int r0 = r1.iCodeTop
            r5 = 106(0x6a, float:1.49E-43)
            if (r2 != r5) goto L_0x00dd
            goto L_0x00de
        L_0x00dd:
            r10 = 6
        L_0x00de:
            r1.addGotoOp(r10)
            r1.stackChange(r7)
            r1.addIcode(r6)
            r1.stackChange(r7)
            org.mozilla.javascript.Node r2 = r3.getNext()
            r3 = r19 & 1
            r1.visitExpression(r2, r3)
            r1.resolveForwardGoto(r0)
            goto L_0x041c
        L_0x00f8:
            r5 = 16
            int r0 = r0.getIntProp(r5, r9)
            r5 = 0
        L_0x00ff:
            r1.visitExpression(r3, r9)
            int r5 = r5 + r8
            org.mozilla.javascript.Node r3 = r3.getNext()
            if (r3 != 0) goto L_0x00ff
            r1.addIndexOp(r2, r0)
            int r0 = 1 - r5
            r1.stackChange(r0)
            goto L_0x041c
        L_0x0113:
            r1.visitExpression(r3, r9)
            r1.addToken(r2)
            goto L_0x041c
        L_0x011b:
            r1.visitExpression(r3, r9)
            r3 = 17
            java.lang.Object r0 = r0.getProp(r3)
            java.lang.String r0 = (java.lang.String) r0
            r1.addStringOp(r2, r0)
            goto L_0x041c
        L_0x012b:
            r1.visitExpression(r3, r9)
            r1.addToken(r2)
            goto L_0x041c
        L_0x0133:
            r1.visitLiteral(r0, r3)
            goto L_0x041c
        L_0x0138:
            int r0 = getLocalBlockRef(r18)
            r1.addIndexOp(r2, r0)
            r1.stackChange(r8)
            goto L_0x041c
        L_0x0144:
            org.mozilla.javascript.InterpreterData r0 = r1.itsData
            boolean r0 = r0.itsNeedsActivation
            if (r0 == 0) goto L_0x014d
            org.mozilla.javascript.Kit.codeBug()
        L_0x014d:
            org.mozilla.javascript.ast.ScriptNode r0 = r1.scriptOrFn
            int r0 = r0.getIndexForNameNode(r3)
            org.mozilla.javascript.Node r2 = r3.getNext()
            r1.visitExpression(r2, r9)
            r2 = 56
            r1.addVarOp(r2, r0)
            goto L_0x041c
        L_0x0161:
            org.mozilla.javascript.InterpreterData r2 = r1.itsData
            boolean r2 = r2.itsNeedsActivation
            if (r2 == 0) goto L_0x016a
            org.mozilla.javascript.Kit.codeBug()
        L_0x016a:
            org.mozilla.javascript.ast.ScriptNode r2 = r1.scriptOrFn
            int r0 = r2.getIndexForNameNode(r0)
            r1.addVarOp(r11, r0)
            r1.stackChange(r8)
            goto L_0x041c
        L_0x0178:
            int r0 = getLocalBlockRef(r18)
            r2 = 54
            r1.addIndexOp(r2, r0)
            r1.stackChange(r8)
            goto L_0x041c
        L_0x0186:
            int r0 = r0.getExistingIntProp(r11)
            r2 = 48
            r1.addIndexOp(r2, r0)
            r1.stackChange(r8)
            goto L_0x041c
        L_0x0194:
            r1.addToken(r2)
            r1.stackChange(r8)
            goto L_0x041c
        L_0x019c:
            double r2 = r18.getDouble()
            int r0 = (int) r2
            double r5 = (double) r0
            int r7 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r7 != 0) goto L_0x01db
            if (r0 != 0) goto L_0x01bc
            r0 = -51
            r1.addIcode(r0)
            r5 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r5 = r5 / r2
            r2 = 0
            int r0 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x01e4
            r0 = 29
            r1.addToken(r0)
            goto L_0x01e4
        L_0x01bc:
            if (r0 != r8) goto L_0x01c4
            r0 = -52
            r1.addIcode(r0)
            goto L_0x01e4
        L_0x01c4:
            short r2 = (short) r0
            if (r2 != r0) goto L_0x01d2
            r2 = -27
            r1.addIcode(r2)
            r0 = r0 & r16
            r1.addUint16(r0)
            goto L_0x01e4
        L_0x01d2:
            r2 = -28
            r1.addIcode(r2)
            r1.addInt(r0)
            goto L_0x01e4
        L_0x01db:
            int r0 = r1.getDoubleIndex(r2)
            r2 = 40
            r1.addIndexOp(r2, r0)
        L_0x01e4:
            r1.stackChange(r8)
            goto L_0x041c
        L_0x01e9:
            java.lang.String r0 = r18.getString()
            r1.addStringOp(r2, r0)
            r1.stackChange(r8)
            goto L_0x041c
        L_0x01f5:
            r1.visitExpression(r3, r9)
            org.mozilla.javascript.Node r0 = r3.getNext()
            r1.visitExpression(r0, r9)
            org.mozilla.javascript.Node r0 = r0.getNext()
            r3 = 141(0x8d, float:1.98E-43)
            r5 = -2
            if (r2 != r3) goto L_0x0219
            r1.addIcode(r5)
            r1.stackChange(r12)
            r2 = 36
            r1.addToken(r2)
            r1.stackChange(r7)
            r1.stackChange(r7)
        L_0x0219:
            r1.visitExpression(r0, r9)
            r0 = 37
            r1.addToken(r0)
            r1.stackChange(r5)
            goto L_0x041c
        L_0x0226:
            r1.visitExpression(r3, r9)
            org.mozilla.javascript.Node r0 = r3.getNext()
            java.lang.String r3 = r0.getString()
            org.mozilla.javascript.Node r0 = r0.getNext()
            r5 = 140(0x8c, float:1.96E-43)
            if (r2 != r5) goto L_0x0247
            r1.addIcode(r7)
            r1.stackChange(r8)
            r2 = 33
            r1.addStringOp(r2, r3)
            r1.stackChange(r7)
        L_0x0247:
            r1.visitExpression(r0, r9)
            r0 = 35
            r1.addStringOp(r0, r3)
            r1.stackChange(r7)
            goto L_0x041c
        L_0x0254:
            r1.visitExpression(r3, r9)
            org.mozilla.javascript.Node r0 = r3.getNext()
            java.lang.String r0 = r0.getString()
            r1.addStringOp(r2, r0)
            goto L_0x041c
        L_0x0264:
            int r0 = r3.getType()
            r2 = 49
            if (r0 != r2) goto L_0x026e
            r0 = 1
            goto L_0x026f
        L_0x026e:
            r0 = 0
        L_0x026f:
            r1.visitExpression(r3, r9)
            org.mozilla.javascript.Node r2 = r3.getNext()
            r1.visitExpression(r2, r9)
            if (r0 == 0) goto L_0x027f
            r1.addIcode(r9)
            goto L_0x0284
        L_0x027f:
            r0 = 31
            r1.addToken(r0)
        L_0x0284:
            r1.stackChange(r7)
            goto L_0x041c
        L_0x0289:
            r5 = 30
            if (r2 != r5) goto L_0x0291
            r1.visitExpression(r3, r9)
            goto L_0x0294
        L_0x0291:
            r1.generateCallFunAndThis(r3)
        L_0x0294:
            r6 = 0
        L_0x0295:
            org.mozilla.javascript.Node r3 = r3.getNext()
            if (r3 == 0) goto L_0x02a1
            r1.visitExpression(r3, r9)
            int r6 = r6 + 1
            goto L_0x0295
        L_0x02a1:
            r3 = 10
            int r0 = r0.getIntProp(r3, r9)
            r3 = 71
            if (r2 == r3) goto L_0x02c3
            if (r0 == 0) goto L_0x02c3
            r3 = -21
            r1.addIndexOp(r3, r6)
            r1.addUint8(r0)
            if (r2 != r5) goto L_0x02b8
            r9 = 1
        L_0x02b8:
            r1.addUint8(r9)
            int r0 = r1.lineNumber
            r0 = r0 & r16
            r1.addUint16(r0)
            goto L_0x02de
        L_0x02c3:
            r0 = 38
            if (r2 != r0) goto L_0x02db
            r0 = r19 & 1
            if (r0 == 0) goto L_0x02db
            org.mozilla.javascript.CompilerEnvirons r0 = r1.compilerEnv
            boolean r0 = r0.isGenerateDebugInfo()
            if (r0 != 0) goto L_0x02db
            boolean r0 = r1.itsInTryFlag
            if (r0 != 0) goto L_0x02db
            r0 = -55
            r2 = -55
        L_0x02db:
            r1.addIndexOp(r2, r6)
        L_0x02de:
            if (r2 != r5) goto L_0x02e5
            int r0 = -r6
            r1.stackChange(r0)
            goto L_0x02ea
        L_0x02e5:
            int r0 = -1 - r6
            r1.stackChange(r0)
        L_0x02ea:
            org.mozilla.javascript.InterpreterData r0 = r1.itsData
            int r2 = r0.itsMaxCalleeArgs
            if (r6 <= r2) goto L_0x041c
            r0.itsMaxCalleeArgs = r6
            goto L_0x041c
        L_0x02f4:
            r1.visitExpression(r3, r9)
            org.mozilla.javascript.Node r0 = r3.getNext()
            r1.visitExpression(r0, r9)
            r1.addToken(r2)
            r1.stackChange(r7)
            goto L_0x041c
        L_0x0306:
            java.lang.String r0 = r3.getString()
            r1.visitExpression(r3, r9)
            org.mozilla.javascript.Node r3 = r3.getNext()
            r1.visitExpression(r3, r9)
            r1.addStringOp(r2, r0)
            r1.stackChange(r7)
            goto L_0x041c
        L_0x031c:
            if (r3 == 0) goto L_0x0322
            r1.visitExpression(r3, r9)
            goto L_0x0328
        L_0x0322:
            r1.addIcode(r5)
            r1.stackChange(r8)
        L_0x0328:
            r3 = 73
            if (r2 != r3) goto L_0x0330
            r1.addToken(r3)
            goto L_0x0335
        L_0x0330:
            r2 = -66
            r1.addIcode(r2)
        L_0x0335:
            int r0 = r18.getLineno()
            r0 = r0 & r16
            r1.addUint16(r0)
            goto L_0x041c
        L_0x0340:
            org.mozilla.javascript.Node r0 = r18.getFirstChild()
            org.mozilla.javascript.Node r2 = r0.getNext()
            org.mozilla.javascript.Node r0 = r0.getFirstChild()
            r1.visitExpression(r0, r9)
            r1.addToken(r12)
            r1.stackChange(r7)
            org.mozilla.javascript.Node r0 = r2.getFirstChild()
            r1.visitExpression(r0, r9)
            r0 = 3
            r1.addToken(r0)
            goto L_0x041c
        L_0x0362:
            r17.updateLineNumber(r18)
            r1.visitExpression(r3, r9)
            r0 = -53
            r1.addIcode(r0)
            r1.stackChange(r7)
            int r0 = r1.iCodeTop
            org.mozilla.javascript.Node r2 = r3.getNext()
            r1.visitExpression(r2, r9)
            r2 = -54
            r1.addBackwardGoto(r2, r0)
            goto L_0x041c
        L_0x0380:
            r1.visitExpression(r3, r9)
            org.mozilla.javascript.Node r0 = r3.getNext()
            if (r2 != r14) goto L_0x0397
            r1.addIcode(r7)
            r1.stackChange(r8)
            r2 = 68
            r1.addToken(r2)
            r1.stackChange(r7)
        L_0x0397:
            r1.visitExpression(r0, r9)
            r0 = 69
            r1.addToken(r0)
            r1.stackChange(r7)
            goto L_0x041c
        L_0x03a4:
            r1.visitExpression(r3, r9)
            if (r2 != r13) goto L_0x03b0
            r1.addIcode(r6)
            r1.addIcode(r5)
            goto L_0x041c
        L_0x03b0:
            r1.addToken(r2)
            goto L_0x041c
        L_0x03b4:
            int r0 = r0.getExistingIntProp(r8)
            org.mozilla.javascript.ast.ScriptNode r2 = r1.scriptOrFn
            org.mozilla.javascript.ast.FunctionNode r2 = r2.getFunctionNode(r0)
            int r3 = r2.getFunctionType()
            if (r3 == r12) goto L_0x03d0
            int r2 = r2.getFunctionType()
            if (r2 != r11) goto L_0x03cb
            goto L_0x03d0
        L_0x03cb:
            java.lang.RuntimeException r0 = org.mozilla.javascript.Kit.codeBug()
            throw r0
        L_0x03d0:
            r2 = -19
            r1.addIndexOp(r2, r0)
            r1.stackChange(r8)
            goto L_0x041c
        L_0x03d9:
            org.mozilla.javascript.Node r0 = r3.getNext()
            org.mozilla.javascript.Node r2 = r0.getNext()
            r1.visitExpression(r3, r9)
            int r3 = r1.iCodeTop
            r1.addGotoOp(r10)
            r1.stackChange(r7)
            r5 = r19 & 1
            r1.visitExpression(r0, r5)
            int r0 = r1.iCodeTop
            r6 = 5
            r1.addGotoOp(r6)
            r1.resolveForwardGoto(r3)
            r1.stackDepth = r4
            r1.visitExpression(r2, r5)
            r1.resolveForwardGoto(r0)
            goto L_0x041c
        L_0x0403:
            org.mozilla.javascript.Node r0 = r18.getLastChild()
        L_0x0407:
            if (r3 == r0) goto L_0x0417
            r1.visitExpression(r3, r9)
            r1.addIcode(r6)
            r1.stackChange(r7)
            org.mozilla.javascript.Node r3 = r3.getNext()
            goto L_0x0407
        L_0x0417:
            r0 = r19 & 1
            r1.visitExpression(r3, r0)     // Catch:{ all -> 0x0425 }
        L_0x041c:
            int r4 = r4 + r8
            int r0 = r1.stackDepth
            if (r4 == r0) goto L_0x0424
            org.mozilla.javascript.Kit.codeBug()
        L_0x0424:
            return
        L_0x0425:
            r0 = move-exception
            r2 = r0
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.CodeGenerator.visitExpression(org.mozilla.javascript.Node, int):void");
    }

    private void visitIncDec(Node node, Node node2) {
        int existingIntProp = node.getExistingIntProp(13);
        int type = node2.getType();
        if (type == 33) {
            Node firstChild = node2.getFirstChild();
            visitExpression(firstChild, 0);
            addStringOp(-9, firstChild.getNext().getString());
            addUint8(existingIntProp);
        } else if (type == 36) {
            Node firstChild2 = node2.getFirstChild();
            visitExpression(firstChild2, 0);
            visitExpression(firstChild2.getNext(), 0);
            addIcode(-10);
            addUint8(existingIntProp);
            stackChange(-1);
        } else if (type == 39) {
            addStringOp(-8, node2.getString());
            addUint8(existingIntProp);
            stackChange(1);
        } else if (type == 55) {
            if (this.itsData.itsNeedsActivation) {
                Kit.codeBug();
            }
            addVarOp(-7, this.scriptOrFn.getIndexForNameNode(node2));
            addUint8(existingIntProp);
            stackChange(1);
        } else if (type == 68) {
            visitExpression(node2.getFirstChild(), 0);
            addIcode(-11);
            addUint8(existingIntProp);
        } else {
            throw badTree(node);
        }
    }

    private void visitLiteral(Node node, Node node2) {
        int i;
        Object[] objArr;
        int type = node.getType();
        if (type == 66) {
            i = 0;
            for (Node node3 = node2; node3 != null; node3 = node3.getNext()) {
                i++;
            }
            objArr = null;
        } else if (type == 67) {
            objArr = (Object[]) node.getProp(12);
            i = objArr.length;
        } else {
            throw badTree(node);
        }
        addIndexOp(-29, i);
        stackChange(2);
        while (node2 != null) {
            int type2 = node2.getType();
            if (type2 == 152) {
                visitExpression(node2.getFirstChild(), 0);
                addIcode(-57);
            } else if (type2 == 153) {
                visitExpression(node2.getFirstChild(), 0);
                addIcode(-58);
            } else if (type2 == 164) {
                visitExpression(node2.getFirstChild(), 0);
                addIcode(-30);
            } else {
                visitExpression(node2, 0);
                addIcode(-30);
            }
            stackChange(-1);
            node2 = node2.getNext();
        }
        if (type == 66) {
            int[] iArr = (int[]) node.getProp(11);
            if (iArr == null) {
                addToken(66);
            } else {
                int size = this.literalIds.size();
                this.literalIds.add(iArr);
                addIndexOp(-31, size);
            }
        } else {
            int size2 = this.literalIds.size();
            this.literalIds.add(objArr);
            addIndexOp(67, size2);
        }
        stackChange(-1);
    }

    private void visitStatement(Node node, int i) {
        int type = node.getType();
        Node firstChild = node.getFirstChild();
        if (type != -62) {
            if (type != 65) {
                int i2 = 1;
                if (type != 82) {
                    int i3 = -5;
                    if (type == 110) {
                        int existingIntProp = node.getExistingIntProp(1);
                        int functionType = this.scriptOrFn.getFunctionNode(existingIntProp).getFunctionType();
                        if (functionType == 3) {
                            addIndexOp(-20, existingIntProp);
                        } else if (functionType != 1) {
                            throw Kit.codeBug();
                        }
                        if (!this.itsInFunctionFlag) {
                            addIndexOp(-19, existingIntProp);
                            stackChange(1);
                            addIcode(-5);
                            stackChange(-1);
                        }
                    } else if (type != 115) {
                        if (type != 124) {
                            if (type == 126) {
                                stackChange(1);
                                int localBlockRef = getLocalBlockRef(node);
                                addIndexOp(-24, localBlockRef);
                                stackChange(-1);
                                while (firstChild != null) {
                                    visitStatement(firstChild, i);
                                    firstChild = firstChild.getNext();
                                }
                                addIndexOp(-25, localBlockRef);
                            } else if (type == 142) {
                                int allocLocal = allocLocal();
                                node.putIntProp(2, allocLocal);
                                updateLineNumber(node);
                                while (firstChild != null) {
                                    visitStatement(firstChild, i);
                                    firstChild = firstChild.getNext();
                                }
                                addIndexOp(-56, allocLocal);
                                releaseLocal(allocLocal);
                            } else if (type == 161) {
                                addIcode(-64);
                            } else if (type == 50) {
                                updateLineNumber(node);
                                visitExpression(firstChild, 0);
                                addToken(50);
                                addUint16(this.lineNumber & SupportMenu.USER_MASK);
                                stackChange(-1);
                            } else if (type != 51) {
                                switch (type) {
                                    case 2:
                                        visitExpression(firstChild, 0);
                                        addToken(2);
                                        stackChange(-1);
                                        break;
                                    case 3:
                                        addToken(3);
                                        break;
                                    case 4:
                                        updateLineNumber(node);
                                        if (node.getIntProp(20, 0) == 0) {
                                            if (firstChild != null) {
                                                visitExpression(firstChild, 1);
                                                addToken(4);
                                                stackChange(-1);
                                                break;
                                            } else {
                                                addIcode(-22);
                                                break;
                                            }
                                        } else if (firstChild != null && this.compilerEnv.getLanguageVersion() >= 200) {
                                            visitExpression(firstChild, 1);
                                            addIcode(-65);
                                            addUint16(this.lineNumber & SupportMenu.USER_MASK);
                                            stackChange(-1);
                                            break;
                                        } else {
                                            addIcode(-63);
                                            addUint16(this.lineNumber & SupportMenu.USER_MASK);
                                            break;
                                        }
                                        break;
                                    case 5:
                                        addGoto(((Jump) node).target, type);
                                        break;
                                    case 6:
                                    case 7:
                                        Node node2 = ((Jump) node).target;
                                        visitExpression(firstChild, 0);
                                        addGoto(node2, type);
                                        stackChange(-1);
                                        break;
                                    default:
                                        switch (type) {
                                            case 57:
                                                int localBlockRef2 = getLocalBlockRef(node);
                                                int existingIntProp2 = node.getExistingIntProp(14);
                                                String string = firstChild.getString();
                                                visitExpression(firstChild.getNext(), 0);
                                                addStringPrefix(string);
                                                addIndexPrefix(localBlockRef2);
                                                addToken(57);
                                                if (existingIntProp2 == 0) {
                                                    i2 = 0;
                                                }
                                                addUint8(i2);
                                                stackChange(-1);
                                                break;
                                            case 58:
                                            case 59:
                                            case 60:
                                            case 61:
                                                visitExpression(firstChild, 0);
                                                addIndexOp(type, getLocalBlockRef(node));
                                                stackChange(-1);
                                                break;
                                            default:
                                                switch (type) {
                                                    case Token.EMPTY /*129*/:
                                                    case 130:
                                                    case Token.LABEL /*131*/:
                                                    case Token.LOOP /*133*/:
                                                        break;
                                                    case Token.TARGET /*132*/:
                                                        markTargetLabel(node);
                                                        break;
                                                    case Token.EXPR_VOID /*134*/:
                                                    case Token.EXPR_RESULT /*135*/:
                                                        updateLineNumber(node);
                                                        visitExpression(firstChild, 0);
                                                        if (type == 134) {
                                                            i3 = -4;
                                                        }
                                                        addIcode(i3);
                                                        stackChange(-1);
                                                        break;
                                                    case Token.JSR /*136*/:
                                                        addGoto(((Jump) node).target, -23);
                                                        break;
                                                    case Token.SCRIPT /*137*/:
                                                        break;
                                                    default:
                                                        throw badTree(node);
                                                }
                                        }
                                }
                            } else {
                                updateLineNumber(node);
                                addIndexOp(51, getLocalBlockRef(node));
                            }
                        }
                        updateLineNumber(node);
                        while (firstChild != null) {
                            visitStatement(firstChild, i);
                            firstChild = firstChild.getNext();
                        }
                    } else {
                        updateLineNumber(node);
                        visitExpression(firstChild, 0);
                        Jump jump = (Jump) firstChild.getNext();
                        while (jump != null) {
                            if (jump.getType() == 116) {
                                Node firstChild2 = jump.getFirstChild();
                                addIcode(-1);
                                stackChange(1);
                                visitExpression(firstChild2, 0);
                                addToken(46);
                                stackChange(-1);
                                addGoto(jump.target, -6);
                                stackChange(-1);
                                jump = (Jump) jump.getNext();
                            } else {
                                throw badTree(jump);
                            }
                        }
                        addIcode(-4);
                        stackChange(-1);
                    }
                } else {
                    Jump jump2 = (Jump) node;
                    int localBlockRef3 = getLocalBlockRef(jump2);
                    int allocLocal2 = allocLocal();
                    addIndexOp(-13, allocLocal2);
                    int i4 = this.iCodeTop;
                    boolean z = this.itsInTryFlag;
                    this.itsInTryFlag = true;
                    while (firstChild != null) {
                        visitStatement(firstChild, i);
                        firstChild = firstChild.getNext();
                    }
                    this.itsInTryFlag = z;
                    Node node3 = jump2.target;
                    if (node3 != null) {
                        int i5 = this.labelTable[getTargetLabel(node3)];
                        addExceptionHandler(i4, i5, i5, false, localBlockRef3, allocLocal2);
                    }
                    Node node4 = jump2.getFinally();
                    if (node4 != null) {
                        int i6 = this.labelTable[getTargetLabel(node4)];
                        addExceptionHandler(i4, i6, i6, true, localBlockRef3, allocLocal2);
                    }
                    addIndexOp(-56, allocLocal2);
                    releaseLocal(allocLocal2);
                }
            } else {
                updateLineNumber(node);
                addToken(65);
            }
        }
        if (this.stackDepth != i) {
            throw Kit.codeBug();
        }
    }

    public InterpreterData compile(CompilerEnvirons compilerEnvirons, ScriptNode scriptNode, String str, boolean z) {
        this.compilerEnv = compilerEnvirons;
        new NodeTransformer().transform(scriptNode, compilerEnvirons);
        if (z) {
            this.scriptOrFn = scriptNode.getFunctionNode(0);
        } else {
            this.scriptOrFn = scriptNode;
        }
        InterpreterData interpreterData = new InterpreterData(compilerEnvirons.getLanguageVersion(), this.scriptOrFn.getSourceName(), str, this.scriptOrFn.isInStrictMode());
        this.itsData = interpreterData;
        interpreterData.topLevel = true;
        if (z) {
            generateFunctionICode();
        } else {
            generateICodeFromTree(this.scriptOrFn);
        }
        return this.itsData;
    }
}
