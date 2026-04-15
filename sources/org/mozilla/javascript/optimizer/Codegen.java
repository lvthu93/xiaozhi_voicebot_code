package org.mozilla.javascript.optimizer;

import androidx.core.app.NotificationCompat;
import java.util.HashMap;
import java.util.List;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Evaluator;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.GeneratedClassLoader;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.ObjArray;
import org.mozilla.javascript.ObjToIntMap;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.SecurityController;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.ScriptNode;

public class Codegen implements Evaluator {
    static final String DEFAULT_MAIN_METHOD_CLASS = "org.mozilla.javascript.optimizer.OptRuntime";
    static final String FUNCTION_CONSTRUCTOR_SIGNATURE = "(Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;I)V";
    static final String FUNCTION_INIT_SIGNATURE = "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)V";
    static final String ID_FIELD_NAME = "_id";
    static final String REGEXP_INIT_METHOD_NAME = "_reInit";
    static final String REGEXP_INIT_METHOD_SIGNATURE = "(Lorg/mozilla/javascript/Context;)V";
    private static final String SUPER_CLASS_NAME = "org.mozilla.javascript.NativeFunction";
    private static final Object globalLock = new Object();
    private static int globalSerialClassCounter;
    private CompilerEnvirons compilerEnv;
    private ObjArray directCallTargets;
    private double[] itsConstantList;
    private int itsConstantListSize;
    String mainClassName;
    String mainClassSignature;
    private String mainMethodClass = DEFAULT_MAIN_METHOD_CLASS;
    private ObjToIntMap scriptOrFnIndexes;
    ScriptNode[] scriptOrFnNodes;

    private static void addDoubleWrap(l0 l0Var) {
        l0Var.m("org/mozilla/javascript/optimizer/OptRuntime", 184, "wrapDouble", "(D)Ljava/lang/Double;");
    }

    public static RuntimeException badTree() {
        throw new RuntimeException("Bad tree in codegen");
    }

    private static void collectScriptNodes_r(ScriptNode scriptNode, ObjArray objArray) {
        objArray.add(scriptNode);
        int functionCount = scriptNode.getFunctionCount();
        for (int i = 0; i != functionCount; i++) {
            collectScriptNodes_r(scriptNode.getFunctionNode(i), objArray);
        }
    }

    private Class<?> defineClass(Object obj, Object obj2) {
        Object[] objArr = (Object[]) obj;
        String str = (String) objArr[0];
        byte[] bArr = (byte[]) objArr[1];
        GeneratedClassLoader createLoader = SecurityController.createLoader(getClass().getClassLoader(), obj2);
        try {
            Class<?> defineClass = createLoader.defineClass(str, bArr);
            createLoader.linkClass(defineClass);
            return defineClass;
        } catch (IllegalArgumentException | SecurityException e) {
            throw new RuntimeException("Malformed optimizer package " + e);
        }
    }

    private void emitConstantDudeInitializers(l0 l0Var) {
        int i = this.itsConstantListSize;
        if (i != 0) {
            l0Var.ap("<clinit>", "()V", 24);
            double[] dArr = this.itsConstantList;
            for (int i2 = 0; i2 != i; i2++) {
                double d = dArr[i2];
                String f = y2.f("_k", i2);
                String staticConstantWrapperType = getStaticConstantWrapperType(d);
                l0Var.k(f, staticConstantWrapperType, 10);
                int i3 = (int) d;
                if (((double) i3) == d) {
                    l0Var.s(i3);
                    l0Var.m("java/lang/Integer", 184, "valueOf", "(I)Ljava/lang/Integer;");
                } else {
                    l0Var.r(d);
                    addDoubleWrap(l0Var);
                }
                l0Var.f(this.mainClassName, 179, f, staticConstantWrapperType);
            }
            l0Var.c(177);
            l0Var.aq(0);
        }
    }

    private void emitDirectConstructor(l0 l0Var, OptFunctionNode optFunctionNode) {
        l0Var.ap(getDirectCtorName(optFunctionNode.fnode), getBodyMethodSignature(optFunctionNode.fnode), 10);
        int paramCount = optFunctionNode.fnode.getParamCount();
        int i = (paramCount * 3) + 4;
        int i2 = i + 1;
        l0Var.g(0);
        l0Var.g(1);
        l0Var.g(2);
        l0Var.m("org/mozilla/javascript/BaseFunction", 182, "createObject", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
        l0Var.h(i2);
        l0Var.g(0);
        l0Var.g(1);
        l0Var.g(2);
        l0Var.g(i2);
        for (int i3 = 0; i3 < paramCount; i3++) {
            int i4 = i3 * 3;
            l0Var.g(i4 + 4);
            l0Var.i(i4 + 5);
        }
        l0Var.g(i);
        l0Var.m(this.mainClassName, 184, getBodyMethodName(optFunctionNode.fnode), getBodyMethodSignature(optFunctionNode.fnode));
        int b = l0Var.b();
        l0Var.c(89);
        l0Var.e(193, "org/mozilla/javascript/Scriptable");
        l0Var.d(Token.SET, b);
        l0Var.e(192, "org/mozilla/javascript/Scriptable");
        l0Var.c(176);
        l0Var.af(b);
        l0Var.g(i2);
        l0Var.c(176);
        l0Var.aq((short) (i2 + 1));
    }

    private void emitRegExpInit(l0 l0Var) {
        l0 l0Var2 = l0Var;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            ScriptNode[] scriptNodeArr = this.scriptOrFnNodes;
            if (i2 == scriptNodeArr.length) {
                break;
            }
            i3 += scriptNodeArr[i2].getRegexpCount();
            i2++;
        }
        if (i3 != 0) {
            short s = 10;
            l0Var2.ap(REGEXP_INIT_METHOD_NAME, REGEXP_INIT_METHOD_SIGNATURE, 10);
            l0Var2.k("_reInitDone", "Z", 74);
            l0Var2.f(this.mainClassName, 178, "_reInitDone", "Z");
            int b = l0Var.b();
            l0Var2.d(Token.SET, b);
            l0Var2.c(177);
            l0Var2.af(b);
            l0Var2.g(0);
            l0Var2.m("org/mozilla/javascript/ScriptRuntime", 184, "checkRegExpProxy", "(Lorg/mozilla/javascript/Context;)Lorg/mozilla/javascript/RegExpProxy;");
            l0Var2.h(1);
            int i4 = 0;
            while (true) {
                ScriptNode[] scriptNodeArr2 = this.scriptOrFnNodes;
                if (i4 != scriptNodeArr2.length) {
                    ScriptNode scriptNode = scriptNodeArr2[i4];
                    int regexpCount = scriptNode.getRegexpCount();
                    int i5 = 0;
                    while (i5 != regexpCount) {
                        String compiledRegexpName = getCompiledRegexpName(scriptNode, i5);
                        String regexpString = scriptNode.getRegexpString(i5);
                        String regexpFlags = scriptNode.getRegexpFlags(i5);
                        l0Var2.k(compiledRegexpName, "Ljava/lang/Object;", s);
                        l0Var2.g(1);
                        l0Var2.g(i);
                        l0Var2.t(regexpString);
                        if (regexpFlags == null) {
                            l0Var2.c(1);
                        } else {
                            l0Var2.t(regexpFlags);
                        }
                        l0Var2.m("org/mozilla/javascript/RegExpProxy", 185, "compileRegExp", "(Lorg/mozilla/javascript/Context;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");
                        l0Var2.f(this.mainClassName, 179, compiledRegexpName, "Ljava/lang/Object;");
                        i5++;
                        i = 0;
                        s = 10;
                    }
                    i4++;
                    i = 0;
                    s = 10;
                } else {
                    l0Var2.s(1);
                    l0Var2.f(this.mainClassName, 179, "_reInitDone", "Z");
                    l0Var2.c(177);
                    l0Var2.aq(2);
                    return;
                }
            }
        }
    }

    private void generateCallMethod(l0 l0Var, boolean z) {
        boolean z2;
        int i;
        int paramCount;
        l0 l0Var2 = l0Var;
        l0Var2.ap(NotificationCompat.CATEGORY_CALL, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;", 17);
        int b = l0Var.b();
        l0Var2.g(1);
        l0Var2.m("org/mozilla/javascript/ScriptRuntime", 184, "hasTopCall", "(Lorg/mozilla/javascript/Context;)Z");
        l0Var2.d(Token.LET, b);
        l0Var2.g(0);
        l0Var2.g(1);
        l0Var2.g(2);
        l0Var2.g(3);
        l0Var2.g(4);
        l0Var.u(z);
        l0Var2.m("org/mozilla/javascript/ScriptRuntime", 184, "doTopCall", "(Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;Z)Ljava/lang/Object;");
        l0Var2.c(176);
        l0Var2.af(b);
        l0Var2.g(0);
        l0Var2.g(1);
        l0Var2.g(2);
        l0Var2.g(3);
        l0Var2.g(4);
        int length = this.scriptOrFnNodes.length;
        if (2 <= length) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            l0Var.q();
            l0Var2.f(l0Var2.d, Context.VERSION_1_8, ID_FIELD_NAME, "I");
            i = l0Var2.x(1, length - 1);
        } else {
            i = 0;
        }
        short s = 0;
        for (int i2 = 0; i2 != length; i2++) {
            ScriptNode scriptNode = this.scriptOrFnNodes[i2];
            if (z2) {
                if (i2 == 0) {
                    l0Var2.aj(i);
                    s = l0Var2.m;
                } else {
                    l0Var2.ai(i, i2 - 1, s);
                }
            }
            if (scriptNode.getType() == 110) {
                OptFunctionNode optFunctionNode = OptFunctionNode.get(scriptNode);
                if (optFunctionNode.isTargetOfDirectCall() && (paramCount = optFunctionNode.fnode.getParamCount()) != 0) {
                    for (int i3 = 0; i3 != paramCount; i3++) {
                        l0Var2.c(190);
                        l0Var2.s(i3);
                        int b2 = l0Var.b();
                        int b3 = l0Var.b();
                        l0Var2.d(Token.METHOD, b2);
                        l0Var2.g(4);
                        l0Var2.s(i3);
                        l0Var2.c(50);
                        l0Var2.d(Token.LAST_TOKEN, b3);
                        l0Var2.af(b2);
                        pushUndefined(l0Var);
                        l0Var2.af(b3);
                        l0Var.ab();
                        l0Var2.r(0.0d);
                        l0Var2.g(4);
                    }
                }
            }
            l0Var2.m(this.mainClassName, 184, getBodyMethodName(scriptNode), getBodyMethodSignature(scriptNode));
            l0Var2.c(176);
        }
        l0Var2.aq(5);
    }

    private byte[] generateCode(String str) {
        boolean z;
        String str2;
        boolean z2 = true;
        if (this.scriptOrFnNodes[0].getType() == 137) {
            z = true;
        } else {
            z = false;
        }
        ScriptNode[] scriptNodeArr = this.scriptOrFnNodes;
        if (scriptNodeArr.length <= 1 && z) {
            z2 = false;
        }
        boolean isInStrictMode = scriptNodeArr[0].isInStrictMode();
        if (this.compilerEnv.isGenerateDebugInfo()) {
            str2 = this.scriptOrFnNodes[0].getSourceName();
        } else {
            str2 = null;
        }
        l0 l0Var = new l0(this.mainClassName, SUPER_CLASS_NAME, str2);
        l0Var.k(ID_FIELD_NAME, "I", 2);
        if (z2) {
            generateFunctionConstructor(l0Var);
        }
        if (z) {
            l0Var.r.add(Short.valueOf(l0Var.k.a("org/mozilla/javascript/Script")));
            generateScriptCtor(l0Var);
            generateMain(l0Var);
            generateExecute(l0Var);
        }
        generateCallMethod(l0Var, isInStrictMode);
        generateResumeGenerator(l0Var);
        generateNativeFunctionOverrides(l0Var, str);
        int length = this.scriptOrFnNodes.length;
        for (int i = 0; i != length; i++) {
            ScriptNode scriptNode = this.scriptOrFnNodes[i];
            BodyCodegen bodyCodegen = new BodyCodegen();
            bodyCodegen.cfw = l0Var;
            bodyCodegen.codegen = this;
            bodyCodegen.compilerEnv = this.compilerEnv;
            bodyCodegen.scriptOrFn = scriptNode;
            bodyCodegen.scriptOrFnIndex = i;
            bodyCodegen.generateBodyCode();
            if (scriptNode.getType() == 110) {
                OptFunctionNode optFunctionNode = OptFunctionNode.get(scriptNode);
                generateFunctionInit(l0Var, optFunctionNode);
                if (optFunctionNode.isTargetOfDirectCall()) {
                    emitDirectConstructor(l0Var, optFunctionNode);
                }
            }
        }
        emitRegExpInit(l0Var);
        emitConstantDudeInitializers(l0Var);
        return l0Var.ar();
    }

    private static void generateExecute(l0 l0Var) {
        l0Var.ap("exec", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;", 17);
        l0Var.q();
        l0Var.g(1);
        l0Var.g(2);
        l0Var.c(89);
        l0Var.c(1);
        l0Var.m(l0Var.d, 182, NotificationCompat.CATEGORY_CALL, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;");
        l0Var.c(176);
        l0Var.aq(3);
    }

    private void generateFunctionConstructor(l0 l0Var) {
        int i;
        int i2;
        boolean z = true;
        l0Var.ap("<init>", FUNCTION_CONSTRUCTOR_SIGNATURE, 1);
        short s = 0;
        l0Var.g(0);
        l0Var.m(SUPER_CLASS_NAME, 183, "<init>", "()V");
        l0Var.q();
        l0Var.l(3);
        l0Var.f(l0Var.d, 181, ID_FIELD_NAME, "I");
        l0Var.q();
        l0Var.g(2);
        l0Var.g(1);
        if (this.scriptOrFnNodes[0].getType() == 137) {
            i = 1;
        } else {
            i = 0;
        }
        int length = this.scriptOrFnNodes.length;
        if (i != length) {
            if (2 > length - i) {
                z = false;
            }
            if (z) {
                l0Var.l(3);
                i2 = l0Var.x(i + 1, length - 1);
            } else {
                i2 = 0;
            }
            for (int i3 = i; i3 != length; i3++) {
                if (z) {
                    if (i3 == i) {
                        l0Var.aj(i2);
                        s = l0Var.m;
                    } else {
                        l0Var.ai(i2, (i3 - 1) - i, s);
                    }
                }
                l0Var.m(this.mainClassName, 183, getFunctionInitMethodName(OptFunctionNode.get(this.scriptOrFnNodes[i3])), FUNCTION_INIT_SIGNATURE);
                l0Var.c(177);
            }
            l0Var.aq(4);
            return;
        }
        throw badTree();
    }

    private void generateFunctionInit(l0 l0Var, OptFunctionNode optFunctionNode) {
        l0Var.ap(getFunctionInitMethodName(optFunctionNode), FUNCTION_INIT_SIGNATURE, 18);
        l0Var.q();
        l0Var.g(1);
        l0Var.g(2);
        l0Var.m("org/mozilla/javascript/NativeFunction", 182, "initScriptFunction", FUNCTION_INIT_SIGNATURE);
        if (optFunctionNode.fnode.getRegexpCount() != 0) {
            l0Var.g(1);
            l0Var.m(this.mainClassName, 184, REGEXP_INIT_METHOD_NAME, REGEXP_INIT_METHOD_SIGNATURE);
        }
        l0Var.c(177);
        l0Var.aq(3);
    }

    private void generateMain(l0 l0Var) {
        l0Var.ap("main", "([Ljava/lang/String;)V", 9);
        String str = l0Var.d;
        l0Var.e(187, str);
        l0Var.c(89);
        l0Var.m(str, 183, "<init>", "()V");
        l0Var.c(42);
        l0Var.m(this.mainMethodClass, 184, "main", "(Lorg/mozilla/javascript/Script;[Ljava/lang/String;)V");
        l0Var.c(177);
        l0Var.aq(1);
    }

    private void generateNativeFunctionOverrides(l0 l0Var, String str) {
        short s;
        int i;
        l0 l0Var2 = l0Var;
        short s2 = 1;
        l0Var2.ap("getLanguageVersion", "()I", 1);
        l0Var2.s(this.compilerEnv.getLanguageVersion());
        int i2 = 172;
        l0Var2.c(172);
        l0Var2.aq(1);
        int i3 = 0;
        while (i3 != 7) {
            if (i3 != 4 || str != null) {
                int i4 = 3;
                switch (i3) {
                    case 0:
                        l0Var2.ap("getFunctionName", "()Ljava/lang/String;", s2);
                        break;
                    case 1:
                        l0Var2.ap("getParamCount", "()I", s2);
                        break;
                    case 2:
                        l0Var2.ap("getParamAndVarCount", "()I", s2);
                        break;
                    case 3:
                        l0Var2.ap("getParamOrVarName", "(I)Ljava/lang/String;", s2);
                        s = 2;
                        break;
                    case 4:
                        l0Var2.ap("getEncodedSource", "()Ljava/lang/String;", s2);
                        l0Var.t(str);
                        break;
                    case 5:
                        l0Var2.ap("getParamOrVarConst", "(I)Z", s2);
                        s = 3;
                        break;
                    case 6:
                        l0Var2.ap("isGeneratorFunction", "()Z", 4);
                        break;
                    default:
                        throw Kit.codeBug();
                }
                s = 1;
                int length = this.scriptOrFnNodes.length;
                if (length > s2) {
                    l0Var.q();
                    l0Var2.f(l0Var2.d, Context.VERSION_1_8, ID_FIELD_NAME, "I");
                    i = l0Var2.x(s2, length - 1);
                } else {
                    i = 0;
                }
                int i5 = 0;
                short s3 = 0;
                while (i5 != length) {
                    ScriptNode scriptNode = this.scriptOrFnNodes[i5];
                    if (i5 != 0) {
                        l0Var2.ai(i, i5 - 1, s3);
                    } else if (length > s2) {
                        l0Var2.aj(i);
                        s3 = l0Var2.m;
                    }
                    switch (i3) {
                        case 0:
                            if (scriptNode.getType() == 137) {
                                l0Var2.t("");
                            } else {
                                l0Var2.t(((FunctionNode) scriptNode).getName());
                            }
                            l0Var2.c(176);
                            break;
                        case 1:
                            l0Var2.s(scriptNode.getParamCount());
                            l0Var2.c(i2);
                            break;
                        case 2:
                            l0Var2.s(scriptNode.getParamAndVarCount());
                            i2 = 172;
                            l0Var2.c(172);
                            break;
                        case 3:
                            int paramAndVarCount = scriptNode.getParamAndVarCount();
                            if (paramAndVarCount != 0) {
                                if (paramAndVarCount != 1) {
                                    l0Var2.l(1);
                                    int x = l0Var2.x(1, paramAndVarCount - 1);
                                    int i6 = 0;
                                    while (i6 != paramAndVarCount) {
                                        if (l0Var2.m != 0) {
                                            Kit.codeBug();
                                        }
                                        String paramOrVarName = scriptNode.getParamOrVarName(i6);
                                        if (i6 == 0) {
                                            l0Var2.aj(x);
                                        } else {
                                            l0Var2.ai(x, i6 - 1, 0);
                                        }
                                        l0Var2.t(paramOrVarName);
                                        l0Var2.c(176);
                                        i6++;
                                        i2 = 172;
                                    }
                                    break;
                                } else {
                                    l0Var2.t(scriptNode.getParamOrVarName(0));
                                    l0Var2.c(176);
                                    break;
                                }
                            } else {
                                l0Var2.c(1);
                                l0Var2.c(176);
                                break;
                            }
                        case 4:
                            l0Var2.s(scriptNode.getEncodedSourceStart());
                            l0Var2.s(scriptNode.getEncodedSourceEnd());
                            l0Var2.m("java/lang/String", 182, "substring", "(II)Ljava/lang/String;");
                            l0Var2.c(176);
                            break;
                        case 5:
                            int paramAndVarCount2 = scriptNode.getParamAndVarCount();
                            boolean[] paramAndVarConst = scriptNode.getParamAndVarConst();
                            if (paramAndVarCount2 != 0) {
                                if (paramAndVarCount2 != s2) {
                                    l0Var2.l(s2);
                                    int x2 = l0Var2.x(s2, paramAndVarCount2 - 1);
                                    for (int i7 = 0; i7 != paramAndVarCount2; i7++) {
                                        if (l0Var2.m != 0) {
                                            Kit.codeBug();
                                        }
                                        if (i7 == 0) {
                                            l0Var2.aj(x2);
                                        } else {
                                            l0Var2.ai(x2, i7 - 1, 0);
                                        }
                                        l0Var2.u(paramAndVarConst[i7]);
                                        l0Var2.c(i2);
                                    }
                                    break;
                                } else {
                                    l0Var2.u(paramAndVarConst[0]);
                                    l0Var2.c(i2);
                                    break;
                                }
                            } else {
                                l0Var2.c(i4);
                                l0Var2.c(i2);
                                break;
                            }
                        case 6:
                            if (scriptNode instanceof FunctionNode) {
                                l0Var2.u(((FunctionNode) scriptNode).isES6Generator());
                            } else {
                                l0Var2.u(false);
                            }
                            l0Var2.c(i2);
                            break;
                        default:
                            throw Kit.codeBug();
                    }
                    i5++;
                    s2 = 1;
                    i4 = 3;
                }
                l0Var2.aq(s);
            }
            i3++;
            s2 = 1;
        }
    }

    private void generateResumeGenerator(l0 l0Var) {
        int i = 0;
        int i2 = 0;
        boolean z = false;
        while (true) {
            ScriptNode[] scriptNodeArr = this.scriptOrFnNodes;
            if (i2 >= scriptNodeArr.length) {
                break;
            }
            if (isGenerator(scriptNodeArr[i2])) {
                z = true;
            }
            i2++;
        }
        if (z) {
            l0Var.ap("resumeGenerator", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;ILjava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", 17);
            l0Var.g(0);
            l0Var.g(1);
            l0Var.g(2);
            l0Var.g(4);
            l0Var.g(5);
            l0Var.l(3);
            l0Var.q();
            l0Var.f(l0Var.d, Context.VERSION_1_8, ID_FIELD_NAME, "I");
            int x = l0Var.x(0, this.scriptOrFnNodes.length - 1);
            l0Var.aj(x);
            int b = l0Var.b();
            while (true) {
                ScriptNode[] scriptNodeArr2 = this.scriptOrFnNodes;
                if (i < scriptNodeArr2.length) {
                    ScriptNode scriptNode = scriptNodeArr2[i];
                    l0Var.ai(x, i, 6);
                    if (isGenerator(scriptNode)) {
                        String k = y2.k(new StringBuilder("("), this.mainClassSignature, "Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Ljava/lang/Object;I)Ljava/lang/Object;");
                        String str = this.mainClassName;
                        l0Var.m(str, 184, getBodyMethodName(scriptNode) + "_gen", k);
                        l0Var.c(176);
                    } else {
                        l0Var.d(Token.LAST_TOKEN, b);
                    }
                    i++;
                } else {
                    l0Var.af(b);
                    pushUndefined(l0Var);
                    l0Var.c(176);
                    l0Var.aq(6);
                    return;
                }
            }
        }
    }

    private static void generateScriptCtor(l0 l0Var) {
        l0Var.ap("<init>", "()V", 1);
        l0Var.q();
        l0Var.m(SUPER_CLASS_NAME, 183, "<init>", "()V");
        l0Var.q();
        l0Var.s(0);
        l0Var.f(l0Var.d, 181, ID_FIELD_NAME, "I");
        l0Var.c(177);
        l0Var.aq(1);
    }

    private static String getStaticConstantWrapperType(double d) {
        return ((double) ((int) d)) == d ? "Ljava/lang/Integer;" : "Ljava/lang/Double;";
    }

    private static void initOptFunctions_r(ScriptNode scriptNode) {
        int functionCount = scriptNode.getFunctionCount();
        for (int i = 0; i != functionCount; i++) {
            FunctionNode functionNode = scriptNode.getFunctionNode(i);
            new OptFunctionNode(functionNode);
            initOptFunctions_r(functionNode);
        }
    }

    private void initScriptNodesData(ScriptNode scriptNode) {
        ObjArray objArray = new ObjArray();
        collectScriptNodes_r(scriptNode, objArray);
        int size = objArray.size();
        ScriptNode[] scriptNodeArr = new ScriptNode[size];
        this.scriptOrFnNodes = scriptNodeArr;
        objArray.toArray(scriptNodeArr);
        this.scriptOrFnIndexes = new ObjToIntMap(size);
        for (int i = 0; i != size; i++) {
            this.scriptOrFnIndexes.put(this.scriptOrFnNodes[i], i);
        }
    }

    public static boolean isGenerator(ScriptNode scriptNode) {
        if (scriptNode.getType() != 110 || !((FunctionNode) scriptNode).isGenerator()) {
            return false;
        }
        return true;
    }

    public static void pushUndefined(l0 l0Var) {
        l0Var.f("org/mozilla/javascript/Undefined", 178, "instance", "Ljava/lang/Object;");
    }

    private void transform(ScriptNode scriptNode) {
        initOptFunctions_r(scriptNode);
        int optimizationLevel = this.compilerEnv.getOptimizationLevel();
        HashMap hashMap = null;
        if (optimizationLevel > 0 && scriptNode.getType() == 137) {
            int functionCount = scriptNode.getFunctionCount();
            for (int i = 0; i != functionCount; i++) {
                OptFunctionNode optFunctionNode = OptFunctionNode.get(scriptNode, i);
                if (optFunctionNode.fnode.getFunctionType() == 1) {
                    String name = optFunctionNode.fnode.getName();
                    if (name.length() != 0) {
                        if (hashMap == null) {
                            hashMap = new HashMap();
                        }
                        hashMap.put(name, optFunctionNode);
                    }
                }
            }
        }
        if (hashMap != null) {
            this.directCallTargets = new ObjArray();
        }
        new OptTransformer(hashMap, this.directCallTargets).transform(scriptNode, this.compilerEnv);
        if (optimizationLevel > 0) {
            new Optimizer().optimize(scriptNode);
        }
    }

    public void captureStackInfo(RhinoException rhinoException) {
        throw new UnsupportedOperationException();
    }

    public String cleanName(ScriptNode scriptNode) {
        if (!(scriptNode instanceof FunctionNode)) {
            return "script";
        }
        Name functionName = ((FunctionNode) scriptNode).getFunctionName();
        if (functionName == null) {
            return "anonymous";
        }
        return functionName.getIdentifier();
    }

    public Object compile(CompilerEnvirons compilerEnvirons, ScriptNode scriptNode, String str, boolean z) {
        int i;
        synchronized (globalLock) {
            i = globalSerialClassCounter + 1;
            globalSerialClassCounter = i;
        }
        String str2 = "c";
        if (scriptNode.getSourceName().length() > 0) {
            str2 = scriptNode.getSourceName().replaceAll("\\W", "_");
            if (!Character.isJavaIdentifierStart(str2.charAt(0))) {
                str2 = "_".concat(str2);
            }
        }
        Object obj = "org.mozilla.javascript.gen." + str2 + "_" + i;
        return new Object[]{obj, compileToClassFile(compilerEnvirons, obj, scriptNode, str, z)};
    }

    public byte[] compileToClassFile(CompilerEnvirons compilerEnvirons, String str, ScriptNode scriptNode, String str2, boolean z) {
        this.compilerEnv = compilerEnvirons;
        transform(scriptNode);
        if (z) {
            scriptNode = scriptNode.getFunctionNode(0);
        }
        initScriptNodesData(scriptNode);
        this.mainClassName = str;
        int i = l0.ac;
        int length = str.length();
        int i2 = length + 1;
        int i3 = i2 + 1;
        char[] cArr = new char[i3];
        cArr[0] = 'L';
        cArr[i2] = ';';
        str.getChars(0, length, cArr, 1);
        for (int i4 = 1; i4 != i2; i4++) {
            if (cArr[i4] == '.') {
                cArr[i4] = '/';
            }
        }
        this.mainClassSignature = new String(cArr, 0, i3);
        return generateCode(str2);
    }

    public Function createFunctionObject(Context context, Scriptable scriptable, Object obj, Object obj2) {
        try {
            return (NativeFunction) defineClass(obj, obj2).getConstructors()[0].newInstance(new Object[]{scriptable, context, 0});
        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate compiled class:" + e.toString());
        }
    }

    public Script createScriptObject(Object obj, Object obj2) {
        try {
            return (Script) defineClass(obj, obj2).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate compiled class:" + e.toString());
        }
    }

    public String getBodyMethodName(ScriptNode scriptNode) {
        return "_c_" + cleanName(scriptNode) + "_" + getIndex(scriptNode);
    }

    public String getBodyMethodSignature(ScriptNode scriptNode) {
        StringBuilder sb = new StringBuilder("(");
        sb.append(this.mainClassSignature);
        sb.append("Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;");
        if (scriptNode.getType() == 110) {
            OptFunctionNode optFunctionNode = OptFunctionNode.get(scriptNode);
            if (optFunctionNode.isTargetOfDirectCall()) {
                int paramCount = optFunctionNode.fnode.getParamCount();
                for (int i = 0; i != paramCount; i++) {
                    sb.append("Ljava/lang/Object;D");
                }
            }
        }
        sb.append("[Ljava/lang/Object;)Ljava/lang/Object;");
        return sb.toString();
    }

    public String getCompiledRegexpName(ScriptNode scriptNode, int i) {
        return "_re" + getIndex(scriptNode) + "_" + i;
    }

    public String getDirectCtorName(ScriptNode scriptNode) {
        return "_n" + getIndex(scriptNode);
    }

    public String getFunctionInitMethodName(OptFunctionNode optFunctionNode) {
        return "_i" + getIndex(optFunctionNode.fnode);
    }

    public int getIndex(ScriptNode scriptNode) {
        return this.scriptOrFnIndexes.getExisting(scriptNode);
    }

    public String getPatchedStack(RhinoException rhinoException, String str) {
        throw new UnsupportedOperationException();
    }

    public List<String> getScriptStack(RhinoException rhinoException) {
        throw new UnsupportedOperationException();
    }

    public String getSourcePositionFromStack(Context context, int[] iArr) {
        throw new UnsupportedOperationException();
    }

    public void pushNumberAsObject(l0 l0Var, double d) {
        if (d == 0.0d) {
            if (1.0d / d > 0.0d) {
                l0Var.f("org/mozilla/javascript/ScriptRuntime", 178, "zeroObj", "Ljava/lang/Double;");
                return;
            }
            l0Var.r(d);
            addDoubleWrap(l0Var);
        } else if (d == 1.0d) {
            l0Var.f("org/mozilla/javascript/optimizer/OptRuntime", 178, "oneObj", "Ljava/lang/Double;");
        } else if (d == -1.0d) {
            l0Var.f("org/mozilla/javascript/optimizer/OptRuntime", 178, "minusOneObj", "Ljava/lang/Double;");
        } else if (Double.isNaN(d)) {
            l0Var.f("org/mozilla/javascript/ScriptRuntime", 178, "NaNobj", "Ljava/lang/Double;");
        } else {
            int i = this.itsConstantListSize;
            if (i >= 2000) {
                l0Var.r(d);
                addDoubleWrap(l0Var);
                return;
            }
            int i2 = 0;
            if (i == 0) {
                this.itsConstantList = new double[64];
            } else {
                double[] dArr = this.itsConstantList;
                int i3 = 0;
                while (i3 != i && dArr[i3] != d) {
                    i3++;
                }
                if (i == dArr.length) {
                    double[] dArr2 = new double[(i * 2)];
                    System.arraycopy(this.itsConstantList, 0, dArr2, 0, i);
                    this.itsConstantList = dArr2;
                }
                i2 = i3;
            }
            if (i2 == i) {
                this.itsConstantList[i] = d;
                this.itsConstantListSize = i + 1;
            }
            l0Var.f(this.mainClassName, 178, y2.f("_k", i2), getStaticConstantWrapperType(d));
        }
    }

    public void setEvalScriptFlag(Script script) {
        throw new UnsupportedOperationException();
    }

    public void setMainMethodClass(String str) {
        this.mainMethodClass = str;
    }
}
