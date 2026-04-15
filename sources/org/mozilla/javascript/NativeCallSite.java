package org.mozilla.javascript;

public class NativeCallSite extends IdScriptableObject {
    private static final String CALLSITE_TAG = "CallSite";
    private static final int Id_constructor = 1;
    private static final int Id_getColumnNumber = 9;
    private static final int Id_getEvalOrigin = 10;
    private static final int Id_getFileName = 7;
    private static final int Id_getFunction = 4;
    private static final int Id_getFunctionName = 5;
    private static final int Id_getLineNumber = 8;
    private static final int Id_getMethodName = 6;
    private static final int Id_getThis = 2;
    private static final int Id_getTypeName = 3;
    private static final int Id_isConstructor = 14;
    private static final int Id_isEval = 12;
    private static final int Id_isNative = 13;
    private static final int Id_isToplevel = 11;
    private static final int Id_toString = 15;
    private static final int MAX_PROTOTYPE_ID = 15;
    private static final long serialVersionUID = 2688372752566593594L;
    private ScriptStackElement element;

    private NativeCallSite() {
    }

    private static Object getFileName(Scriptable scriptable) {
        while (scriptable != null && !(scriptable instanceof NativeCallSite)) {
            scriptable = scriptable.getPrototype();
        }
        if (scriptable == null) {
            return Scriptable.NOT_FOUND;
        }
        ScriptStackElement scriptStackElement = ((NativeCallSite) scriptable).element;
        if (scriptStackElement == null) {
            return null;
        }
        return scriptStackElement.fileName;
    }

    private static Object getFunctionName(Scriptable scriptable) {
        while (scriptable != null && !(scriptable instanceof NativeCallSite)) {
            scriptable = scriptable.getPrototype();
        }
        if (scriptable == null) {
            return Scriptable.NOT_FOUND;
        }
        ScriptStackElement scriptStackElement = ((NativeCallSite) scriptable).element;
        if (scriptStackElement == null) {
            return null;
        }
        return scriptStackElement.functionName;
    }

    private static Object getLineNumber(Scriptable scriptable) {
        int i;
        while (scriptable != null && !(scriptable instanceof NativeCallSite)) {
            scriptable = scriptable.getPrototype();
        }
        if (scriptable == null) {
            return Scriptable.NOT_FOUND;
        }
        ScriptStackElement scriptStackElement = ((NativeCallSite) scriptable).element;
        if (scriptStackElement == null || (i = scriptStackElement.lineNumber) < 0) {
            return Undefined.instance;
        }
        return Integer.valueOf(i);
    }

    public static void init(Scriptable scriptable, boolean z) {
        new NativeCallSite().exportAsJSClass(15, scriptable, z);
    }

    private static Object js_toString(Scriptable scriptable) {
        while (scriptable != null && !(scriptable instanceof NativeCallSite)) {
            scriptable = scriptable.getPrototype();
        }
        if (scriptable == null) {
            return Scriptable.NOT_FOUND;
        }
        StringBuilder sb = new StringBuilder();
        ((NativeCallSite) scriptable).element.renderJavaStyle(sb);
        return sb.toString();
    }

    public static NativeCallSite make(Scriptable scriptable, Scriptable scriptable2) {
        NativeCallSite nativeCallSite = new NativeCallSite();
        nativeCallSite.setParentScope(scriptable);
        nativeCallSite.setPrototype((Scriptable) scriptable2.get("prototype", scriptable2));
        return nativeCallSite;
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(CALLSITE_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        switch (methodId) {
            case 1:
                return make(scriptable, idFunctionObject);
            case 2:
            case 3:
            case 4:
            case 9:
                return Undefined.instance;
            case 5:
                return getFunctionName(scriptable2);
            case 6:
                return null;
            case 7:
                return getFileName(scriptable2);
            case 8:
                return getLineNumber(scriptable2);
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                return Boolean.FALSE;
            case 15:
                return js_toString(scriptable2);
            default:
                throw new IllegalArgumentException(String.valueOf(methodId));
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r7) {
        /*
            r6 = this;
            int r0 = r7.length()
            r1 = 116(0x74, float:1.63E-43)
            r2 = 105(0x69, float:1.47E-43)
            r3 = 3
            r4 = 0
            switch(r0) {
                case 6: goto L_0x008b;
                case 7: goto L_0x0087;
                case 8: goto L_0x0075;
                case 9: goto L_0x000d;
                case 10: goto L_0x0070;
                case 11: goto L_0x004e;
                case 12: goto L_0x000d;
                case 13: goto L_0x0026;
                case 14: goto L_0x000d;
                case 15: goto L_0x000f;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x0090
        L_0x000f:
            char r0 = r7.charAt(r3)
            r1 = 67
            if (r0 != r1) goto L_0x001d
            java.lang.String r0 = "getColumnNumber"
            r3 = 9
            goto L_0x0092
        L_0x001d:
            r1 = 70
            if (r0 != r1) goto L_0x0090
            java.lang.String r0 = "getFunctionName"
            r3 = 5
            goto L_0x0092
        L_0x0026:
            char r0 = r7.charAt(r3)
            r1 = 69
            if (r0 == r1) goto L_0x0049
            r1 = 111(0x6f, float:1.56E-43)
            if (r0 == r1) goto L_0x0044
            r1 = 76
            if (r0 == r1) goto L_0x003f
            r1 = 77
            if (r0 == r1) goto L_0x003b
            goto L_0x0090
        L_0x003b:
            java.lang.String r0 = "getMethodName"
            r3 = 6
            goto L_0x0092
        L_0x003f:
            java.lang.String r0 = "getLineNumber"
            r3 = 8
            goto L_0x0092
        L_0x0044:
            java.lang.String r0 = "isConstructor"
            r3 = 14
            goto L_0x0092
        L_0x0049:
            java.lang.String r0 = "getEvalOrigin"
            r3 = 10
            goto L_0x0092
        L_0x004e:
            r0 = 4
            char r5 = r7.charAt(r0)
            if (r5 == r2) goto L_0x006c
            r2 = 121(0x79, float:1.7E-43)
            if (r5 == r2) goto L_0x0069
            if (r5 == r1) goto L_0x0065
            r1 = 117(0x75, float:1.64E-43)
            if (r5 == r1) goto L_0x0060
            goto L_0x0090
        L_0x0060:
            java.lang.String r1 = "getFunction"
            r0 = r1
            r3 = 4
            goto L_0x0092
        L_0x0065:
            java.lang.String r0 = "constructor"
            r3 = 1
            goto L_0x0092
        L_0x0069:
            java.lang.String r0 = "getTypeName"
            goto L_0x0092
        L_0x006c:
            java.lang.String r0 = "getFileName"
            r3 = 7
            goto L_0x0092
        L_0x0070:
            java.lang.String r0 = "isToplevel"
            r3 = 11
            goto L_0x0092
        L_0x0075:
            char r0 = r7.charAt(r4)
            if (r0 != r2) goto L_0x0080
            java.lang.String r0 = "isNative"
            r3 = 13
            goto L_0x0092
        L_0x0080:
            if (r0 != r1) goto L_0x0090
            java.lang.String r0 = "toString"
            r3 = 15
            goto L_0x0092
        L_0x0087:
            java.lang.String r0 = "getThis"
            r3 = 2
            goto L_0x0092
        L_0x008b:
            java.lang.String r0 = "isEval"
            r3 = 12
            goto L_0x0092
        L_0x0090:
            r0 = 0
            r3 = 0
        L_0x0092:
            if (r0 == 0) goto L_0x009d
            if (r0 == r7) goto L_0x009d
            boolean r7 = r0.equals(r7)
            if (r7 != 0) goto L_0x009d
            goto L_0x009e
        L_0x009d:
            r4 = r3
        L_0x009e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeCallSite.findPrototypeId(java.lang.String):int");
    }

    public String getClassName() {
        return CALLSITE_TAG;
    }

    public void initPrototypeId(int i) {
        String str;
        switch (i) {
            case 1:
                str = "constructor";
                break;
            case 2:
                str = "getThis";
                break;
            case 3:
                str = "getTypeName";
                break;
            case 4:
                str = "getFunction";
                break;
            case 5:
                str = "getFunctionName";
                break;
            case 6:
                str = "getMethodName";
                break;
            case 7:
                str = "getFileName";
                break;
            case 8:
                str = "getLineNumber";
                break;
            case 9:
                str = "getColumnNumber";
                break;
            case 10:
                str = "getEvalOrigin";
                break;
            case 11:
                str = "isToplevel";
                break;
            case 12:
                str = "isEval";
                break;
            case 13:
                str = "isNative";
                break;
            case 14:
                str = "isConstructor";
                break;
            case 15:
                str = "toString";
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(i));
        }
        initPrototypeMethod(CALLSITE_TAG, i, str, 0);
    }

    public void setElement(ScriptStackElement scriptStackElement) {
        this.element = scriptStackElement;
    }

    public String toString() {
        ScriptStackElement scriptStackElement = this.element;
        if (scriptStackElement == null) {
            return "";
        }
        return scriptStackElement.toString();
    }
}
