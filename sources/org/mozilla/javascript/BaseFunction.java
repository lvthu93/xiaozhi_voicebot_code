package org.mozilla.javascript;

import androidx.core.app.NotificationCompat;

public class BaseFunction extends IdScriptableObject implements Function {
    private static final String FUNCTION_CLASS = "Function";
    private static final Object FUNCTION_TAG = FUNCTION_CLASS;
    static final String GENERATOR_FUNCTION_CLASS = "__GeneratorFunction";
    private static final int Id_apply = 4;
    private static final int Id_arguments = 5;
    private static final int Id_arity = 2;
    private static final int Id_bind = 6;
    private static final int Id_call = 5;
    private static final int Id_constructor = 1;
    private static final int Id_length = 1;
    private static final int Id_name = 3;
    private static final int Id_prototype = 4;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    private static final int MAX_INSTANCE_ID = 5;
    private static final int MAX_PROTOTYPE_ID = 6;
    private static final long serialVersionUID = 5311394446546053859L;
    private int argumentsAttributes;
    private Object argumentsObj;
    private boolean isGeneratorFunction;
    private Object prototypeProperty;
    private int prototypePropertyAttributes;

    public BaseFunction() {
        this.argumentsObj = Scriptable.NOT_FOUND;
        this.isGeneratorFunction = false;
        this.prototypePropertyAttributes = 6;
        this.argumentsAttributes = 6;
    }

    private Object getArguments() {
        Object obj;
        if (defaultHas("arguments")) {
            obj = defaultGet("arguments");
        } else {
            obj = this.argumentsObj;
        }
        if (obj != Scriptable.NOT_FOUND) {
            return obj;
        }
        NativeCall findFunctionActivation = ScriptRuntime.findFunctionActivation(Context.getContext(), this);
        if (findFunctionActivation == null) {
            return null;
        }
        return findFunctionActivation.get("arguments", (Scriptable) findFunctionActivation);
    }

    public static void init(Scriptable scriptable, boolean z) {
        BaseFunction baseFunction = new BaseFunction();
        baseFunction.prototypePropertyAttributes = 7;
        baseFunction.exportAsJSClass(6, scriptable, z);
    }

    public static Object initAsGeneratorFunction(Scriptable scriptable, boolean z) {
        BaseFunction baseFunction = new BaseFunction(true);
        baseFunction.prototypePropertyAttributes = 5;
        baseFunction.exportAsJSClass(6, scriptable, z);
        return ScriptableObject.getProperty(scriptable, GENERATOR_FUNCTION_CLASS);
    }

    public static boolean isApply(IdFunctionObject idFunctionObject) {
        return idFunctionObject.hasTag(FUNCTION_TAG) && idFunctionObject.methodId() == 4;
    }

    public static boolean isApplyOrCall(IdFunctionObject idFunctionObject) {
        if (!idFunctionObject.hasTag(FUNCTION_TAG)) {
            return false;
        }
        int methodId = idFunctionObject.methodId();
        if (methodId == 4 || methodId == 5) {
            return true;
        }
        return false;
    }

    private Object jsConstructor(Context context, Scriptable scriptable, Object[] objArr) {
        int i;
        int length = objArr.length;
        StringBuilder sb = new StringBuilder("function ");
        if (isGeneratorFunction()) {
            sb.append("* ");
        }
        if (context.getLanguageVersion() != 120) {
            sb.append("anonymous");
        }
        sb.append('(');
        int i2 = 0;
        while (true) {
            i = length - 1;
            if (i2 >= i) {
                break;
            }
            if (i2 > 0) {
                sb.append(',');
            }
            sb.append(ScriptRuntime.toString(objArr[i2]));
            i2++;
        }
        sb.append(") {");
        if (length != 0) {
            sb.append(ScriptRuntime.toString(objArr[i]));
        }
        sb.append("\n}");
        String sb2 = sb.toString();
        int[] iArr = new int[1];
        String sourcePositionFromStack = Context.getSourcePositionFromStack(iArr);
        if (sourcePositionFromStack == null) {
            iArr[0] = 1;
            sourcePositionFromStack = "<eval'ed string>";
        }
        String makeUrlForGeneratedScript = ScriptRuntime.makeUrlForGeneratedScript(false, sourcePositionFromStack, iArr[0]);
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        ErrorReporter forEval = DefaultErrorReporter.forEval(context.getErrorReporter());
        Evaluator createInterpreter = Context.createInterpreter();
        if (createInterpreter != null) {
            return context.compileFunction(topLevelScope, sb2, createInterpreter, forEval, makeUrlForGeneratedScript, 1, (Object) null);
        }
        throw new JavaScriptException("Interpreter not present", sourcePositionFromStack, iArr[0]);
    }

    private static BaseFunction realFunction(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        Object defaultValue = scriptable.getDefaultValue(ScriptRuntime.FunctionClass);
        if (defaultValue instanceof Delegator) {
            defaultValue = ((Delegator) defaultValue).getDelegee();
        }
        if (defaultValue instanceof BaseFunction) {
            return (BaseFunction) defaultValue;
        }
        throw ScriptRuntime.typeError1("msg.incompat.call", idFunctionObject.getFunctionName());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.lang.Object setupDefaultPrototype() {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.Object r0 = r3.prototypeProperty     // Catch:{ all -> 0x001f }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r3)
            return r0
        L_0x0007:
            org.mozilla.javascript.NativeObject r0 = new org.mozilla.javascript.NativeObject     // Catch:{ all -> 0x001f }
            r0.<init>()     // Catch:{ all -> 0x001f }
            java.lang.String r1 = "constructor"
            r2 = 2
            r0.defineProperty((java.lang.String) r1, (java.lang.Object) r3, (int) r2)     // Catch:{ all -> 0x001f }
            r3.prototypeProperty = r0     // Catch:{ all -> 0x001f }
            org.mozilla.javascript.Scriptable r1 = org.mozilla.javascript.ScriptableObject.getObjectPrototype(r3)     // Catch:{ all -> 0x001f }
            if (r1 == r0) goto L_0x001d
            r0.setPrototype(r1)     // Catch:{ all -> 0x001f }
        L_0x001d:
            monitor-exit(r3)
            return r0
        L_0x001f:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.BaseFunction.setupDefaultPrototype():java.lang.Object");
    }

    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        return Undefined.instance;
    }

    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        Scriptable parentScope;
        Scriptable classPrototype;
        Scriptable createObject = createObject(context, scriptable);
        if (createObject != null) {
            Object call = call(context, scriptable, createObject, objArr);
            if (call instanceof Scriptable) {
                return (Scriptable) call;
            }
            return createObject;
        }
        Object call2 = call(context, scriptable, (Scriptable) null, objArr);
        if (call2 instanceof Scriptable) {
            Scriptable scriptable2 = (Scriptable) call2;
            if (scriptable2.getPrototype() == null && scriptable2 != (classPrototype = getClassPrototype())) {
                scriptable2.setPrototype(classPrototype);
            }
            if (scriptable2.getParentScope() != null || scriptable2 == (parentScope = getParentScope())) {
                return scriptable2;
            }
            scriptable2.setParentScope(parentScope);
            return scriptable2;
        }
        throw new IllegalStateException("Bad implementaion of call as constructor, name=" + getFunctionName() + " in " + getClass().getName());
    }

    public Scriptable createObject(Context context, Scriptable scriptable) {
        NativeObject nativeObject = new NativeObject();
        nativeObject.setPrototype(getClassPrototype());
        nativeObject.setParentScope(getParentScope());
        return nativeObject;
    }

    public String decompile(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        if ((i2 & 1) == 0) {
            z = false;
        }
        if (!z) {
            sb.append("function ");
            sb.append(getFunctionName());
            sb.append("() {\n\t");
        }
        sb.append("[native code, arity=");
        sb.append(getArity());
        sb.append("]\n");
        if (!z) {
            sb.append("}\n");
        }
        return sb.toString();
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        int int32;
        Object[] objArr2;
        Scriptable scriptable3;
        if (!idFunctionObject.hasTag(FUNCTION_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        boolean z = true;
        int i = 0;
        switch (methodId) {
            case 1:
                return jsConstructor(context, scriptable, objArr);
            case 2:
                return realFunction(scriptable2, idFunctionObject).decompile(ScriptRuntime.toInt32(objArr, 0), 0);
            case 3:
                BaseFunction realFunction = realFunction(scriptable2, idFunctionObject);
                int i2 = 2;
                if (objArr.length != 0 && (int32 = ScriptRuntime.toInt32(objArr[0])) >= 0) {
                    i = int32;
                    i2 = 0;
                }
                return realFunction.decompile(i, i2);
            case 4:
            case 5:
                if (methodId != 4) {
                    z = false;
                }
                return ScriptRuntime.applyOrCall(z, context, scriptable, scriptable2, objArr);
            case 6:
                if (scriptable2 instanceof Callable) {
                    Callable callable = (Callable) scriptable2;
                    int length = objArr.length;
                    if (length > 0) {
                        Scriptable objectOrNull = ScriptRuntime.toObjectOrNull(context, objArr[0], scriptable);
                        int i3 = length - 1;
                        Object[] objArr3 = new Object[i3];
                        System.arraycopy(objArr, 1, objArr3, 0, i3);
                        scriptable3 = objectOrNull;
                        objArr2 = objArr3;
                    } else {
                        objArr2 = ScriptRuntime.emptyArgs;
                        scriptable3 = null;
                    }
                    return new BoundFunction(context, scriptable, callable, scriptable3, objArr2);
                }
                throw ScriptRuntime.notFunctionError(scriptable2);
            default:
                throw new IllegalArgumentException(String.valueOf(methodId));
        }
    }

    public void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        idFunctionObject.setPrototype(this);
        super.fillConstructorProperties(idFunctionObject);
    }

    public int findInstanceIdInfo(String str) {
        int i;
        String str2;
        int i2;
        int length = str.length();
        if (length == 4) {
            str2 = "name";
            i = 3;
        } else if (length == 5) {
            str2 = "arity";
            i = 2;
        } else if (length != 6) {
            if (length == 9) {
                char charAt = str.charAt(0);
                if (charAt == 'a') {
                    str2 = "arguments";
                    i = 5;
                } else if (charAt == 'p') {
                    str2 = "prototype";
                    i = 4;
                }
            }
            str2 = null;
            i = 0;
        } else {
            str2 = "length";
            i = 1;
        }
        if (!(str2 == null || str2 == str || str2.equals(str))) {
            i = 0;
        }
        if (i == 0) {
            return super.findInstanceIdInfo(str);
        }
        if (i == 1 || i == 2 || i == 3) {
            i2 = 7;
        } else if (i != 4) {
            if (i == 5) {
                i2 = this.argumentsAttributes;
            } else {
                throw new IllegalStateException();
            }
        } else if (!hasPrototypeProperty()) {
            return 0;
        } else {
            i2 = this.prototypePropertyAttributes;
        }
        return IdScriptableObject.instanceIdInfo(i2, i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0047 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r5) {
        /*
            r4 = this;
            int r0 = r5.length()
            r1 = 5
            r2 = 4
            r3 = 0
            if (r0 == r2) goto L_0x0030
            if (r0 == r1) goto L_0x002c
            r1 = 8
            if (r0 == r1) goto L_0x0018
            r1 = 11
            if (r0 == r1) goto L_0x0014
            goto L_0x0043
        L_0x0014:
            java.lang.String r0 = "constructor"
            r1 = 1
            goto L_0x0045
        L_0x0018:
            r1 = 3
            char r0 = r5.charAt(r1)
            r2 = 111(0x6f, float:1.56E-43)
            if (r0 != r2) goto L_0x0024
            java.lang.String r0 = "toSource"
            goto L_0x0045
        L_0x0024:
            r1 = 116(0x74, float:1.63E-43)
            if (r0 != r1) goto L_0x0043
            java.lang.String r0 = "toString"
            r1 = 2
            goto L_0x0045
        L_0x002c:
            java.lang.String r0 = "apply"
            r1 = 4
            goto L_0x0045
        L_0x0030:
            char r0 = r5.charAt(r3)
            r2 = 98
            if (r0 != r2) goto L_0x003c
            java.lang.String r0 = "bind"
            r1 = 6
            goto L_0x0045
        L_0x003c:
            r2 = 99
            if (r0 != r2) goto L_0x0043
            java.lang.String r0 = "call"
            goto L_0x0045
        L_0x0043:
            r0 = 0
            r1 = 0
        L_0x0045:
            if (r0 == 0) goto L_0x0050
            if (r0 == r5) goto L_0x0050
            boolean r5 = r0.equals(r5)
            if (r5 != 0) goto L_0x0050
            goto L_0x0051
        L_0x0050:
            r3 = r1
        L_0x0051:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.BaseFunction.findPrototypeId(java.lang.String):int");
    }

    public int getArity() {
        return 0;
    }

    public String getClassName() {
        return isGeneratorFunction() ? GENERATOR_FUNCTION_CLASS : FUNCTION_CLASS;
    }

    public Scriptable getClassPrototype() {
        Object prototypeProperty2 = getPrototypeProperty();
        if (prototypeProperty2 instanceof Scriptable) {
            return (Scriptable) prototypeProperty2;
        }
        return ScriptableObject.getObjectPrototype(this);
    }

    public String getFunctionName() {
        return "";
    }

    public String getInstanceIdName(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? super.getInstanceIdName(i) : "arguments" : "prototype" : "name" : "arity" : "length";
    }

    public Object getInstanceIdValue(int i) {
        if (i == 1) {
            return ScriptRuntime.wrapInt(getLength());
        }
        if (i == 2) {
            return ScriptRuntime.wrapInt(getArity());
        }
        if (i == 3) {
            return getFunctionName();
        }
        if (i == 4) {
            return getPrototypeProperty();
        }
        if (i != 5) {
            return super.getInstanceIdValue(i);
        }
        return getArguments();
    }

    public int getLength() {
        return 0;
    }

    public int getMaxInstanceId() {
        return 5;
    }

    public Object getPrototypeProperty() {
        Object obj = this.prototypeProperty;
        if (obj == null) {
            if (this instanceof NativeFunction) {
                return setupDefaultPrototype();
            }
            return Undefined.instance;
        } else if (obj == UniqueTag.NULL_VALUE) {
            return null;
        } else {
            return obj;
        }
    }

    public String getTypeOf() {
        return avoidObjectDetection() ? "undefined" : "function";
    }

    public boolean hasInstance(Scriptable scriptable) {
        Object property = ScriptableObject.getProperty((Scriptable) this, "prototype");
        if (property instanceof Scriptable) {
            return ScriptRuntime.jsDelegatesTo(scriptable, (Scriptable) property);
        }
        throw ScriptRuntime.typeError1("msg.instanceof.bad.prototype", getFunctionName());
    }

    public boolean hasPrototypeProperty() {
        return this.prototypeProperty != null || (this instanceof NativeFunction);
    }

    public void initPrototypeId(int i) {
        String str;
        int i2 = 1;
        switch (i) {
            case 1:
                str = "constructor";
                break;
            case 2:
                i2 = 0;
                str = "toString";
                break;
            case 3:
                str = "toSource";
                break;
            case 4:
                i2 = 2;
                str = "apply";
                break;
            case 5:
                str = NotificationCompat.CATEGORY_CALL;
                break;
            case 6:
                str = "bind";
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(i));
        }
        initPrototypeMethod(FUNCTION_TAG, i, str, i2);
    }

    public boolean isGeneratorFunction() {
        return this.isGeneratorFunction;
    }

    public void setImmunePrototypeProperty(Object obj) {
        if ((this.prototypePropertyAttributes & 1) == 0) {
            if (obj == null) {
                obj = UniqueTag.NULL_VALUE;
            }
            this.prototypeProperty = obj;
            this.prototypePropertyAttributes = 7;
            return;
        }
        throw new IllegalStateException();
    }

    public void setInstanceIdAttributes(int i, int i2) {
        if (i == 4) {
            this.prototypePropertyAttributes = i2;
        } else if (i != 5) {
            super.setInstanceIdAttributes(i, i2);
        } else {
            this.argumentsAttributes = i2;
        }
    }

    public void setInstanceIdValue(int i, Object obj) {
        if (i != 1 && i != 2 && i != 3) {
            if (i != 4) {
                if (i != 5) {
                    super.setInstanceIdValue(i, obj);
                    return;
                }
                if (obj == Scriptable.NOT_FOUND) {
                    Kit.codeBug();
                }
                if (defaultHas("arguments")) {
                    defaultPut("arguments", obj);
                } else if ((this.argumentsAttributes & 1) == 0) {
                    this.argumentsObj = obj;
                }
            } else if ((this.prototypePropertyAttributes & 1) == 0) {
                if (obj == null) {
                    obj = UniqueTag.NULL_VALUE;
                }
                this.prototypeProperty = obj;
            }
        }
    }

    public BaseFunction(boolean z) {
        this.argumentsObj = Scriptable.NOT_FOUND;
        this.prototypePropertyAttributes = 6;
        this.argumentsAttributes = 6;
        this.isGeneratorFunction = z;
    }

    public BaseFunction(Scriptable scriptable, Scriptable scriptable2) {
        super(scriptable, scriptable2);
        this.argumentsObj = Scriptable.NOT_FOUND;
        this.isGeneratorFunction = false;
        this.prototypePropertyAttributes = 6;
        this.argumentsAttributes = 6;
    }
}
