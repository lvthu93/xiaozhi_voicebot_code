package org.mozilla.javascript;

import java.io.Serializable;
import java.lang.reflect.Method;

final class NativeError extends IdScriptableObject {
    private static final int ConstructorId_captureStackTrace = -1;
    public static final int DEFAULT_STACK_LIMIT = -1;
    private static final Method ERROR_DELEGATE_GET_STACK;
    private static final Method ERROR_DELEGATE_SET_STACK;
    private static final Object ERROR_TAG = "Error";
    private static final int Id_constructor = 1;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    private static final int MAX_PROTOTYPE_ID = 3;
    private static final String STACK_HIDE_KEY = "_stackHide";
    private static final long serialVersionUID = -5338413581437645187L;
    private RhinoException stackProvider;

    static {
        Class<Scriptable> cls = Scriptable.class;
        Class<NativeError> cls2 = NativeError.class;
        try {
            ERROR_DELEGATE_GET_STACK = cls2.getMethod("getStackDelegated", new Class[]{cls});
            ERROR_DELEGATE_SET_STACK = cls2.getMethod("setStackDelegated", new Class[]{cls, Object.class});
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Object callPrepareStack(Function function, ScriptStackElement[] scriptStackElementArr) {
        Context currentContext = Context.getCurrentContext();
        Object[] objArr = new Object[scriptStackElementArr.length];
        for (int i = 0; i < scriptStackElementArr.length; i++) {
            NativeCallSite nativeCallSite = (NativeCallSite) currentContext.newObject(this, "CallSite");
            nativeCallSite.setElement(scriptStackElementArr[i]);
            objArr[i] = nativeCallSite;
        }
        return function.call(currentContext, function, this, new Object[]{this, currentContext.newArray((Scriptable) this, objArr)});
    }

    public static void init(Scriptable scriptable, boolean z) {
        NativeError nativeError = new NativeError();
        ScriptableObject.putProperty((Scriptable) nativeError, "name", (Object) "Error");
        ScriptableObject.putProperty((Scriptable) nativeError, "message", (Object) "");
        ScriptableObject.putProperty((Scriptable) nativeError, "fileName", (Object) "");
        ScriptableObject.putProperty((Scriptable) nativeError, "lineNumber", (Object) 0);
        nativeError.setAttributes("name", 2);
        nativeError.setAttributes("message", 2);
        nativeError.exportAsJSClass(3, scriptable, z);
        NativeCallSite.init(nativeError, z);
    }

    private static void js_captureStackTrace(Context context, Scriptable scriptable, Object[] objArr) {
        Function function;
        Object obj;
        ScriptableObject scriptableObject = (ScriptableObject) ScriptRuntime.toObjectOrNull(context, objArr[0], scriptable);
        if (objArr.length > 1) {
            function = (Function) ScriptRuntime.toObjectOrNull(context, objArr[1], scriptable);
        } else {
            function = null;
        }
        NativeError nativeError = (NativeError) context.newObject(scriptable, "Error");
        nativeError.setStackProvider(new EvaluatorException("[object Object]"));
        if (!(function == null || (obj = function.get("name", (Scriptable) function)) == null || Undefined.instance.equals(obj))) {
            nativeError.associateValue(STACK_HIDE_KEY, Context.toString(obj));
        }
        scriptableObject.defineProperty("stack", nativeError, ERROR_DELEGATE_GET_STACK, ERROR_DELEGATE_SET_STACK, 0);
    }

    private static String js_toSource(Context context, Scriptable scriptable, Scriptable scriptable2) {
        int int32;
        Object property = ScriptableObject.getProperty(scriptable2, "name");
        Object property2 = ScriptableObject.getProperty(scriptable2, "message");
        Object property3 = ScriptableObject.getProperty(scriptable2, "fileName");
        Object property4 = ScriptableObject.getProperty(scriptable2, "lineNumber");
        StringBuilder sb = new StringBuilder("(new ");
        Object obj = Scriptable.NOT_FOUND;
        if (property == obj) {
            property = Undefined.instance;
        }
        sb.append(ScriptRuntime.toString(property));
        sb.append("(");
        if (!(property2 == obj && property3 == obj && property4 == obj)) {
            if (property2 == obj) {
                property2 = "";
            }
            sb.append(ScriptRuntime.uneval(context, scriptable, property2));
            if (!(property3 == obj && property4 == obj)) {
                sb.append(", ");
                if (property3 == obj) {
                    property3 = "";
                }
                sb.append(ScriptRuntime.uneval(context, scriptable, property3));
                if (!(property4 == obj || (int32 = ScriptRuntime.toInt32(property4)) == 0)) {
                    sb.append(", ");
                    sb.append(ScriptRuntime.toString((double) int32));
                }
            }
        }
        sb.append("))");
        return sb.toString();
    }

    private static Object js_toString(Scriptable scriptable) {
        String str;
        String str2;
        Object property = ScriptableObject.getProperty(scriptable, "name");
        Object obj = Scriptable.NOT_FOUND;
        if (property == obj || property == Undefined.instance) {
            str = "Error";
        } else {
            str = ScriptRuntime.toString(property);
        }
        Object property2 = ScriptableObject.getProperty(scriptable, "message");
        if (property2 == obj || property2 == Undefined.instance) {
            str2 = "";
        } else {
            str2 = ScriptRuntime.toString(property2);
        }
        if (str.toString().length() == 0) {
            return str2;
        }
        if (str2.toString().length() == 0) {
            return str;
        }
        return str + ": " + str2;
    }

    public static NativeError make(Context context, Scriptable scriptable, IdFunctionObject idFunctionObject, Object[] objArr) {
        NativeError nativeError = new NativeError();
        nativeError.setPrototype((Scriptable) idFunctionObject.get("prototype", (Scriptable) idFunctionObject));
        nativeError.setParentScope(scriptable);
        int length = objArr.length;
        if (length >= 1) {
            Object obj = objArr[0];
            if (obj != Undefined.instance) {
                ScriptableObject.putProperty((Scriptable) nativeError, "message", (Object) ScriptRuntime.toString(obj));
            }
            if (length >= 2) {
                ScriptableObject.putProperty((Scriptable) nativeError, "fileName", objArr[1]);
                if (length >= 3) {
                    ScriptableObject.putProperty((Scriptable) nativeError, "lineNumber", (Object) Integer.valueOf(ScriptRuntime.toInt32(objArr[2])));
                }
            }
        }
        return nativeError;
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(ERROR_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        if (methodId == -1) {
            js_captureStackTrace(context, scriptable2, objArr);
            return Undefined.instance;
        } else if (methodId == 1) {
            return make(context, scriptable, idFunctionObject, objArr);
        } else {
            if (methodId == 2) {
                return js_toString(scriptable2);
            }
            if (methodId == 3) {
                return js_toSource(context, scriptable, scriptable2);
            }
            throw new IllegalArgumentException(String.valueOf(methodId));
        }
    }

    public void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        addIdFunctionProperty(idFunctionObject, ERROR_TAG, -1, "captureStackTrace", 2);
        ProtoProps protoProps = new ProtoProps();
        associateValue("_ErrorPrototypeProps", protoProps);
        IdFunctionObject idFunctionObject2 = idFunctionObject;
        ProtoProps protoProps2 = protoProps;
        idFunctionObject2.defineProperty("stackTraceLimit", protoProps2, ProtoProps.GET_STACK_LIMIT, ProtoProps.SET_STACK_LIMIT, 0);
        idFunctionObject2.defineProperty("prepareStackTrace", protoProps2, ProtoProps.GET_PREPARE_STACK, ProtoProps.SET_PREPARE_STACK, 0);
        super.fillConstructorProperties(idFunctionObject);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0029 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r5) {
        /*
            r4 = this;
            int r0 = r5.length()
            r1 = 8
            r2 = 0
            if (r0 != r1) goto L_0x001d
            r0 = 3
            char r1 = r5.charAt(r0)
            r3 = 111(0x6f, float:1.56E-43)
            if (r1 != r3) goto L_0x0015
            java.lang.String r1 = "toSource"
            goto L_0x0027
        L_0x0015:
            r0 = 116(0x74, float:1.63E-43)
            if (r1 != r0) goto L_0x0025
            java.lang.String r1 = "toString"
            r0 = 2
            goto L_0x0027
        L_0x001d:
            r1 = 11
            if (r0 != r1) goto L_0x0025
            java.lang.String r1 = "constructor"
            r0 = 1
            goto L_0x0027
        L_0x0025:
            r1 = 0
            r0 = 0
        L_0x0027:
            if (r1 == 0) goto L_0x0032
            if (r1 == r5) goto L_0x0032
            boolean r5 = r1.equals(r5)
            if (r5 != 0) goto L_0x0032
            goto L_0x0033
        L_0x0032:
            r2 = r0
        L_0x0033:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeError.findPrototypeId(java.lang.String):int");
    }

    public String getClassName() {
        return "Error";
    }

    public Object getStackDelegated(Scriptable scriptable) {
        int i;
        Function function;
        Object obj;
        if (this.stackProvider == null) {
            return Scriptable.NOT_FOUND;
        }
        ProtoProps protoProps = (ProtoProps) ((NativeError) getPrototype()).getAssociatedValue("_ErrorPrototypeProps");
        if (protoProps != null) {
            i = protoProps.getStackTraceLimit();
            function = protoProps.getPrepareStackTrace();
        } else {
            i = -1;
            function = null;
        }
        ScriptStackElement[] scriptStack = this.stackProvider.getScriptStack(i, (String) getAssociatedValue(STACK_HIDE_KEY));
        if (function == null) {
            obj = RhinoException.formatStackTrace(scriptStack, this.stackProvider.details());
        } else {
            obj = callPrepareStack(function, scriptStack);
        }
        setStackDelegated(scriptable, obj);
        return obj;
    }

    public void initPrototypeId(int i) {
        int i2;
        String str;
        if (i != 1) {
            i2 = 0;
            if (i == 2) {
                str = "toString";
            } else if (i == 3) {
                str = "toSource";
            } else {
                throw new IllegalArgumentException(String.valueOf(i));
            }
        } else {
            str = "constructor";
            i2 = 1;
        }
        initPrototypeMethod(ERROR_TAG, i, str, i2);
    }

    public void setStackDelegated(Scriptable scriptable, Object obj) {
        scriptable.delete("stack");
        this.stackProvider = null;
        scriptable.put("stack", scriptable, obj);
    }

    public void setStackProvider(RhinoException rhinoException) {
        if (this.stackProvider == null) {
            this.stackProvider = rhinoException;
            defineProperty("stack", this, ERROR_DELEGATE_GET_STACK, ERROR_DELEGATE_SET_STACK, 2);
        }
    }

    public String toString() {
        Object js_toString = js_toString(this);
        if (js_toString instanceof String) {
            return (String) js_toString;
        }
        return super.toString();
    }

    public static final class ProtoProps implements Serializable {
        static final Method GET_PREPARE_STACK;
        static final Method GET_STACK_LIMIT;
        static final String KEY = "_ErrorPrototypeProps";
        static final Method SET_PREPARE_STACK;
        static final Method SET_STACK_LIMIT;
        private static final long serialVersionUID = 1907180507775337939L;
        private Function prepareStackTrace;
        private int stackTraceLimit;

        static {
            Class<Object> cls = Object.class;
            Class<Scriptable> cls2 = Scriptable.class;
            Class<ProtoProps> cls3 = ProtoProps.class;
            try {
                GET_STACK_LIMIT = cls3.getMethod("getStackTraceLimit", new Class[]{cls2});
                SET_STACK_LIMIT = cls3.getMethod("setStackTraceLimit", new Class[]{cls2, cls});
                GET_PREPARE_STACK = cls3.getMethod("getPrepareStackTrace", new Class[]{cls2});
                SET_PREPARE_STACK = cls3.getMethod("setPrepareStackTrace", new Class[]{cls2, cls});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        private ProtoProps() {
            this.stackTraceLimit = -1;
        }

        public Object getPrepareStackTrace(Scriptable scriptable) {
            Function prepareStackTrace2 = getPrepareStackTrace();
            return prepareStackTrace2 == null ? Undefined.instance : prepareStackTrace2;
        }

        public Object getStackTraceLimit(Scriptable scriptable) {
            int i = this.stackTraceLimit;
            if (i >= 0) {
                return Integer.valueOf(i);
            }
            return Double.valueOf(Double.POSITIVE_INFINITY);
        }

        public void setPrepareStackTrace(Scriptable scriptable, Object obj) {
            if (obj == null || Undefined.instance.equals(obj)) {
                this.prepareStackTrace = null;
            } else if (obj instanceof Function) {
                this.prepareStackTrace = (Function) obj;
            }
        }

        public void setStackTraceLimit(Scriptable scriptable, Object obj) {
            double number = Context.toNumber(obj);
            if (Double.isNaN(number) || Double.isInfinite(number)) {
                this.stackTraceLimit = -1;
            } else {
                this.stackTraceLimit = (int) number;
            }
        }

        public Function getPrepareStackTrace() {
            return this.prepareStackTrace;
        }

        public int getStackTraceLimit() {
            return this.stackTraceLimit;
        }
    }
}
