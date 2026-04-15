package org.mozilla.javascript;

final class NativeBoolean extends IdScriptableObject {
    private static final Object BOOLEAN_TAG = "Boolean";
    private static final int Id_constructor = 1;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    private static final int Id_valueOf = 4;
    private static final int MAX_PROTOTYPE_ID = 4;
    private static final long serialVersionUID = -3716996899943880933L;
    private boolean booleanValue;

    public NativeBoolean(boolean z) {
        this.booleanValue = z;
    }

    public static void init(Scriptable scriptable, boolean z) {
        new NativeBoolean(false).exportAsJSClass(4, scriptable, z);
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(BOOLEAN_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        if (methodId == 1) {
            boolean z = false;
            if (objArr.length != 0) {
                ScriptableObject scriptableObject = objArr[0];
                if (!(scriptableObject instanceof ScriptableObject) || !scriptableObject.avoidObjectDetection()) {
                    z = ScriptRuntime.toBoolean(objArr[0]);
                }
            }
            if (scriptable2 == null) {
                return new NativeBoolean(z);
            }
            return ScriptRuntime.wrapBoolean(z);
        } else if (scriptable2 instanceof NativeBoolean) {
            boolean z2 = ((NativeBoolean) scriptable2).booleanValue;
            if (methodId != 2) {
                if (methodId != 3) {
                    if (methodId == 4) {
                        return ScriptRuntime.wrapBoolean(z2);
                    }
                    throw new IllegalArgumentException(String.valueOf(methodId));
                } else if (z2) {
                    return "(new Boolean(true))";
                } else {
                    return "(new Boolean(false))";
                }
            } else if (z2) {
                return "true";
            } else {
                return "false";
            }
        } else {
            throw IdScriptableObject.incompatibleCallError(idFunctionObject);
        }
    }

    public int findPrototypeId(String str) {
        int i;
        String str2;
        int length = str.length();
        if (length == 7) {
            str2 = "valueOf";
            i = 4;
        } else {
            if (length == 8) {
                i = 3;
                char charAt = str.charAt(3);
                if (charAt == 'o') {
                    str2 = "toSource";
                } else if (charAt == 't') {
                    str2 = "toString";
                    i = 2;
                }
            } else if (length == 11) {
                str2 = "constructor";
                i = 1;
            }
            str2 = null;
            i = 0;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i;
        }
        return 0;
    }

    public String getClassName() {
        return "Boolean";
    }

    public Object getDefaultValue(Class<?> cls) {
        if (cls == ScriptRuntime.BooleanClass) {
            return ScriptRuntime.wrapBoolean(this.booleanValue);
        }
        return super.getDefaultValue(cls);
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
            } else if (i == 4) {
                str = "valueOf";
            } else {
                throw new IllegalArgumentException(String.valueOf(i));
            }
        } else {
            str = "constructor";
            i2 = 1;
        }
        initPrototypeMethod(BOOLEAN_TAG, i, str, i2);
    }
}
