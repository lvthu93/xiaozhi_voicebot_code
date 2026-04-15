package org.mozilla.javascript;

final class NativeNumber extends IdScriptableObject {
    private static final int ConstructorId_isFinite = -1;
    private static final int ConstructorId_isInteger = -3;
    private static final int ConstructorId_isNaN = -2;
    private static final int ConstructorId_isSafeInteger = -4;
    private static final int ConstructorId_parseFloat = -5;
    private static final int ConstructorId_parseInt = -6;
    private static final int Id_constructor = 1;
    private static final int Id_toExponential = 7;
    private static final int Id_toFixed = 6;
    private static final int Id_toLocaleString = 3;
    private static final int Id_toPrecision = 8;
    private static final int Id_toSource = 4;
    private static final int Id_toString = 2;
    private static final int Id_valueOf = 5;
    private static final int MAX_PRECISION = 100;
    private static final int MAX_PROTOTYPE_ID = 8;
    public static final double MAX_SAFE_INTEGER = 9.007199254740991E15d;
    private static final double MIN_SAFE_INTEGER = -9.007199254740991E15d;
    private static final Object NUMBER_TAG = "Number";
    private static final long serialVersionUID = 3504516769741512101L;
    private double doubleValue;

    public NativeNumber(double d) {
        this.doubleValue = d;
    }

    private static Object execConstructorCall(int i, Object[] objArr) {
        Number number;
        Number number2;
        Number number3;
        Object obj;
        switch (i) {
            case ConstructorId_parseInt /*-6*/:
                return NativeGlobal.js_parseInt(objArr);
            case ConstructorId_parseFloat /*-5*/:
                return NativeGlobal.js_parseFloat(objArr);
            case -4:
                if (objArr.length == 0 || Undefined.instance == (number = objArr[0])) {
                    return Boolean.FALSE;
                }
                if (number instanceof Number) {
                    return Boolean.valueOf(isSafeInteger(number));
                }
                return Boolean.FALSE;
            case -3:
                if (objArr.length == 0 || Undefined.instance == (number2 = objArr[0])) {
                    return Boolean.FALSE;
                }
                if (number2 instanceof Number) {
                    return Boolean.valueOf(isInteger(number2));
                }
                return Boolean.FALSE;
            case -2:
                if (objArr.length == 0 || Undefined.instance == (number3 = objArr[0])) {
                    return Boolean.FALSE;
                }
                if (number3 instanceof Number) {
                    return isNaN(number3);
                }
                return Boolean.FALSE;
            case -1:
                if (objArr.length == 0 || Undefined.instance == (obj = objArr[0])) {
                    return Boolean.FALSE;
                }
                if (obj instanceof Number) {
                    return isFinite(obj);
                }
                return Boolean.FALSE;
            default:
                throw new IllegalArgumentException(String.valueOf(i));
        }
    }

    public static void init(Scriptable scriptable, boolean z) {
        new NativeNumber(0.0d).exportAsJSClass(8, scriptable, z);
    }

    private static boolean isDoubleInteger(Double d) {
        return !d.isInfinite() && !d.isNaN() && Math.floor(d.doubleValue()) == d.doubleValue();
    }

    private static boolean isDoubleSafeInteger(Double d) {
        return isDoubleInteger(d) && d.doubleValue() <= 9.007199254740991E15d && d.doubleValue() >= MIN_SAFE_INTEGER;
    }

    public static Object isFinite(Object obj) {
        boolean z;
        Double valueOf = Double.valueOf(ScriptRuntime.toNumber(obj));
        if (valueOf.isInfinite() || valueOf.isNaN()) {
            z = false;
        } else {
            z = true;
        }
        return ScriptRuntime.wrapBoolean(z);
    }

    private static boolean isInteger(Number number) {
        if (number instanceof Double) {
            return isDoubleInteger((Double) number);
        }
        return isDoubleInteger(number.doubleValue());
    }

    private static Boolean isNaN(Number number) {
        if (number instanceof Double) {
            return Boolean.valueOf(((Double) number).isNaN());
        }
        return Boolean.valueOf(Double.isNaN(number.doubleValue()));
    }

    private static boolean isSafeInteger(Number number) {
        if (number instanceof Double) {
            return isDoubleSafeInteger((Double) number);
        }
        return isDoubleSafeInteger(number.doubleValue());
    }

    private static String num_to(double d, Object[] objArr, int i, int i2, int i3, int i4) {
        int i5 = 0;
        if (objArr.length != 0) {
            double integer = ScriptRuntime.toInteger(objArr[0]);
            if (integer < ((double) i3) || integer > 100.0d) {
                throw ScriptRuntime.rangeError(ScriptRuntime.getMessage1("msg.bad.precision", ScriptRuntime.toString(objArr[0])));
            }
            i5 = ScriptRuntime.toInt32(integer);
            i = i2;
        }
        StringBuilder sb = new StringBuilder();
        DToA.JS_dtostr(sb, i, i5 + i4, d);
        return sb.toString();
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        int i;
        Scriptable scriptable3 = scriptable2;
        Object[] objArr2 = objArr;
        IdFunctionObject idFunctionObject2 = idFunctionObject;
        if (!idFunctionObject.hasTag(NUMBER_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        double d = 0.0d;
        if (methodId == 1) {
            if (objArr2.length >= 1) {
                d = ScriptRuntime.toNumber(objArr2[0]);
            }
            if (scriptable3 == null) {
                return new NativeNumber(d);
            }
            return ScriptRuntime.wrapNumber(d);
        } else if (methodId < 1) {
            return execConstructorCall(methodId, objArr2);
        } else {
            if (scriptable3 instanceof NativeNumber) {
                double d2 = ((NativeNumber) scriptable3).doubleValue;
                int i2 = 10;
                switch (methodId) {
                    case 2:
                    case 3:
                        if (!(objArr2.length == 0 || (obj = objArr2[0]) == Undefined.instance)) {
                            i2 = ScriptRuntime.toInt32(obj);
                        }
                        return ScriptRuntime.numberToString(d2, i2);
                    case 4:
                        return "(new Number(" + ScriptRuntime.toString(d2) + "))";
                    case 5:
                        return ScriptRuntime.wrapNumber(d2);
                    case 6:
                        if (context.version < 200) {
                            i = -20;
                        } else {
                            i = 0;
                        }
                        return num_to(d2, objArr, 2, 2, i, 0);
                    case 7:
                        if (Double.isNaN(d2)) {
                            return "NaN";
                        }
                        if (!Double.isInfinite(d2)) {
                            return num_to(d2, objArr, 1, 3, 0, 1);
                        }
                        if (d2 >= 0.0d) {
                            return "Infinity";
                        }
                        return "-Infinity";
                    case 8:
                        if (objArr2.length == 0 || objArr2[0] == Undefined.instance) {
                            return ScriptRuntime.numberToString(d2, 10);
                        }
                        if (Double.isNaN(d2)) {
                            return "NaN";
                        }
                        if (!Double.isInfinite(d2)) {
                            return num_to(d2, objArr, 0, 4, 1, 0);
                        }
                        if (d2 >= 0.0d) {
                            return "Infinity";
                        }
                        return "-Infinity";
                    default:
                        throw new IllegalArgumentException(String.valueOf(methodId));
                }
            } else {
                throw IdScriptableObject.incompatibleCallError(idFunctionObject);
            }
        }
    }

    public void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        idFunctionObject.defineProperty("NaN", (Object) ScriptRuntime.NaNobj, 7);
        idFunctionObject.defineProperty("POSITIVE_INFINITY", (Object) ScriptRuntime.wrapNumber(Double.POSITIVE_INFINITY), 7);
        idFunctionObject.defineProperty("NEGATIVE_INFINITY", (Object) ScriptRuntime.wrapNumber(Double.NEGATIVE_INFINITY), 7);
        idFunctionObject.defineProperty("MAX_VALUE", (Object) ScriptRuntime.wrapNumber(Double.MAX_VALUE), 7);
        idFunctionObject.defineProperty("MIN_VALUE", (Object) ScriptRuntime.wrapNumber(Double.MIN_VALUE), 7);
        idFunctionObject.defineProperty("MAX_SAFE_INTEGER", (Object) ScriptRuntime.wrapNumber(9.007199254740991E15d), 7);
        idFunctionObject.defineProperty("MIN_SAFE_INTEGER", (Object) ScriptRuntime.wrapNumber(MIN_SAFE_INTEGER), 7);
        IdFunctionObject idFunctionObject2 = idFunctionObject;
        Object obj = NUMBER_TAG;
        addIdFunctionProperty(idFunctionObject2, obj, -1, "isFinite", 1);
        addIdFunctionProperty(idFunctionObject2, obj, -2, "isNaN", 1);
        addIdFunctionProperty(idFunctionObject2, obj, -3, "isInteger", 1);
        addIdFunctionProperty(idFunctionObject2, obj, -4, "isSafeInteger", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_parseFloat, "parseFloat", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_parseInt, "parseInt", 1);
        super.fillConstructorProperties(idFunctionObject);
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x005e A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r8) {
        /*
            r7 = this;
            int r0 = r8.length()
            r1 = 7
            r2 = 116(0x74, float:1.63E-43)
            r3 = 0
            if (r0 == r1) goto L_0x0048
            r4 = 3
            r5 = 8
            if (r0 == r5) goto L_0x0036
            r6 = 11
            if (r0 == r6) goto L_0x0023
            r2 = 13
            if (r0 == r2) goto L_0x0020
            r1 = 14
            if (r0 == r1) goto L_0x001c
            goto L_0x005a
        L_0x001c:
            java.lang.String r0 = "toLocaleString"
            r1 = 3
            goto L_0x005c
        L_0x0020:
            java.lang.String r0 = "toExponential"
            goto L_0x005c
        L_0x0023:
            char r0 = r8.charAt(r3)
            r1 = 99
            if (r0 != r1) goto L_0x002f
            java.lang.String r0 = "constructor"
            r1 = 1
            goto L_0x005c
        L_0x002f:
            if (r0 != r2) goto L_0x005a
            java.lang.String r0 = "toPrecision"
            r1 = 8
            goto L_0x005c
        L_0x0036:
            char r0 = r8.charAt(r4)
            r1 = 111(0x6f, float:1.56E-43)
            if (r0 != r1) goto L_0x0042
            java.lang.String r0 = "toSource"
            r1 = 4
            goto L_0x005c
        L_0x0042:
            if (r0 != r2) goto L_0x005a
            java.lang.String r0 = "toString"
            r1 = 2
            goto L_0x005c
        L_0x0048:
            char r0 = r8.charAt(r3)
            if (r0 != r2) goto L_0x0052
            java.lang.String r0 = "toFixed"
            r1 = 6
            goto L_0x005c
        L_0x0052:
            r1 = 118(0x76, float:1.65E-43)
            if (r0 != r1) goto L_0x005a
            java.lang.String r0 = "valueOf"
            r1 = 5
            goto L_0x005c
        L_0x005a:
            r0 = 0
            r1 = 0
        L_0x005c:
            if (r0 == 0) goto L_0x0067
            if (r0 == r8) goto L_0x0067
            boolean r8 = r0.equals(r8)
            if (r8 != 0) goto L_0x0067
            goto L_0x0068
        L_0x0067:
            r3 = r1
        L_0x0068:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeNumber.findPrototypeId(java.lang.String):int");
    }

    public String getClassName() {
        return "Number";
    }

    public void initPrototypeId(int i) {
        String str;
        String str2;
        int i2 = 1;
        switch (i) {
            case 1:
                str = "constructor";
                break;
            case 2:
                str = "toString";
                break;
            case 3:
                str = "toLocaleString";
                break;
            case 4:
                str2 = "toSource";
                break;
            case 5:
                str2 = "valueOf";
                break;
            case 6:
                str = "toFixed";
                break;
            case 7:
                str = "toExponential";
                break;
            case 8:
                str = "toPrecision";
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(i));
        }
        str = str2;
        i2 = 0;
        initPrototypeMethod(NUMBER_TAG, i, str, i2);
    }

    public String toString() {
        return ScriptRuntime.numberToString(this.doubleValue, 10);
    }

    private static boolean isDoubleInteger(double d) {
        return !Double.isInfinite(d) && !Double.isNaN(d) && Math.floor(d) == d;
    }

    private static boolean isDoubleSafeInteger(double d) {
        return isDoubleInteger(d) && d <= 9.007199254740991E15d && d >= MIN_SAFE_INTEGER;
    }
}
