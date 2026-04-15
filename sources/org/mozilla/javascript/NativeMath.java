package org.mozilla.javascript;

final class NativeMath extends IdScriptableObject {
    private static final Double Double32 = Double.valueOf(32.0d);
    private static final int Id_E = 37;
    private static final int Id_LN10 = 39;
    private static final int Id_LN2 = 40;
    private static final int Id_LOG10E = 42;
    private static final int Id_LOG2E = 41;
    private static final int Id_PI = 38;
    private static final int Id_SQRT1_2 = 43;
    private static final int Id_SQRT2 = 44;
    private static final int Id_abs = 2;
    private static final int Id_acos = 3;
    private static final int Id_acosh = 30;
    private static final int Id_asin = 4;
    private static final int Id_asinh = 31;
    private static final int Id_atan = 5;
    private static final int Id_atan2 = 6;
    private static final int Id_atanh = 32;
    private static final int Id_cbrt = 20;
    private static final int Id_ceil = 7;
    private static final int Id_clz32 = 36;
    private static final int Id_cos = 8;
    private static final int Id_cosh = 21;
    private static final int Id_exp = 9;
    private static final int Id_expm1 = 22;
    private static final int Id_floor = 10;
    private static final int Id_fround = 35;
    private static final int Id_hypot = 23;
    private static final int Id_imul = 28;
    private static final int Id_log = 11;
    private static final int Id_log10 = 25;
    private static final int Id_log1p = 24;
    private static final int Id_log2 = 34;
    private static final int Id_max = 12;
    private static final int Id_min = 13;
    private static final int Id_pow = 14;
    private static final int Id_random = 15;
    private static final int Id_round = 16;
    private static final int Id_sign = 33;
    private static final int Id_sin = 17;
    private static final int Id_sinh = 26;
    private static final int Id_sqrt = 18;
    private static final int Id_tan = 19;
    private static final int Id_tanh = 27;
    private static final int Id_toSource = 1;
    private static final int Id_trunc = 29;
    private static final int LAST_METHOD_ID = 36;
    private static final double LOG2E = 1.4426950408889634d;
    private static final Object MATH_TAG = "Math";
    private static final int MAX_ID = 44;
    private static final long serialVersionUID = -8838847185801131569L;

    private NativeMath() {
    }

    public static void init(Scriptable scriptable, boolean z) {
        NativeMath nativeMath = new NativeMath();
        nativeMath.activatePrototypeMap(44);
        nativeMath.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        nativeMath.setParentScope(scriptable);
        if (z) {
            nativeMath.sealObject();
        }
        ScriptableObject.defineProperty(scriptable, "Math", nativeMath, 2);
    }

    private static double js_hypot(Object[] objArr) {
        double d = 0.0d;
        if (objArr == null) {
            return 0.0d;
        }
        boolean z = false;
        boolean z2 = false;
        for (Object number : objArr) {
            double number2 = ScriptRuntime.toNumber(number);
            if (Double.isNaN(number2)) {
                z2 = true;
            } else if (Double.isInfinite(number2)) {
                z = true;
            } else {
                d = (number2 * number2) + d;
            }
        }
        if (z) {
            return Double.POSITIVE_INFINITY;
        }
        if (z2) {
            return Double.NaN;
        }
        return Math.sqrt(d);
    }

    private static int js_imul(Object[] objArr) {
        if (objArr == null) {
            return 0;
        }
        return ScriptRuntime.toInt32(objArr, 1) * ScriptRuntime.toInt32(objArr, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0069, code lost:
        if (r21 < 1.0d) goto L_0x0082;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static double js_pow(double r21, double r23) {
        /*
            r0 = r23
            boolean r2 = java.lang.Double.isNaN(r23)
            if (r2 == 0) goto L_0x000b
            r15 = r0
            goto L_0x00a6
        L_0x000b:
            r2 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r4 = 0
            int r6 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x0016
            r15 = r2
            goto L_0x00a6
        L_0x0016:
            r7 = -9223372036854775808
            r9 = 0
            r11 = 1
            r13 = -4503599627370496(0xfff0000000000000, double:-Infinity)
            r15 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            int r17 = (r21 > r4 ? 1 : (r21 == r4 ? 0 : -1))
            if (r17 != 0) goto L_0x004a
            double r2 = r2 / r21
            int r17 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r17 <= 0) goto L_0x002e
            if (r6 <= 0) goto L_0x00a6
            goto L_0x00a5
        L_0x002e:
            long r2 = (long) r0
            double r4 = (double) r2
            int r18 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r18 != 0) goto L_0x0041
            long r0 = r2 & r11
            int r2 = (r0 > r9 ? 1 : (r0 == r9 ? 0 : -1))
            if (r2 == 0) goto L_0x0041
            if (r6 <= 0) goto L_0x003d
            goto L_0x003e
        L_0x003d:
            r7 = r13
        L_0x003e:
            r4 = r7
            goto L_0x00a5
        L_0x0041:
            if (r6 <= 0) goto L_0x0047
            r4 = 0
            goto L_0x00a5
        L_0x0047:
            r4 = r15
            goto L_0x00a5
        L_0x004a:
            double r4 = java.lang.Math.pow(r21, r23)
            boolean r18 = java.lang.Double.isNaN(r4)
            if (r18 == 0) goto L_0x00a5
            r18 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            int r20 = (r0 > r15 ? 1 : (r0 == r15 ? 0 : -1))
            if (r20 != 0) goto L_0x006c
            int r0 = (r21 > r18 ? 1 : (r21 == r18 ? 0 : -1))
            if (r0 < 0) goto L_0x00a6
            int r0 = (r2 > r21 ? 1 : (r2 == r21 ? 0 : -1))
            if (r0 >= 0) goto L_0x0063
            goto L_0x00a6
        L_0x0063:
            int r0 = (r18 > r21 ? 1 : (r18 == r21 ? 0 : -1))
            if (r0 >= 0) goto L_0x00a5
            int r0 = (r21 > r2 ? 1 : (r21 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x00a5
            goto L_0x0082
        L_0x006c:
            int r20 = (r0 > r13 ? 1 : (r0 == r13 ? 0 : -1))
            if (r20 != 0) goto L_0x0085
            int r0 = (r21 > r18 ? 1 : (r21 == r18 ? 0 : -1))
            if (r0 < 0) goto L_0x0082
            int r0 = (r2 > r21 ? 1 : (r2 == r21 ? 0 : -1))
            if (r0 >= 0) goto L_0x0079
            goto L_0x0082
        L_0x0079:
            int r0 = (r18 > r21 ? 1 : (r18 == r21 ? 0 : -1))
            if (r0 >= 0) goto L_0x00a5
            int r0 = (r21 > r2 ? 1 : (r21 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x00a5
            goto L_0x00a6
        L_0x0082:
            r15 = 0
            goto L_0x00a6
        L_0x0085:
            int r2 = (r21 > r15 ? 1 : (r21 == r15 ? 0 : -1))
            if (r2 != 0) goto L_0x008c
            if (r6 <= 0) goto L_0x0082
            goto L_0x00a6
        L_0x008c:
            int r2 = (r21 > r13 ? 1 : (r21 == r13 ? 0 : -1))
            if (r2 != 0) goto L_0x00a5
            long r2 = (long) r0
            double r4 = (double) r2
            int r18 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r18 != 0) goto L_0x00a2
            long r0 = r2 & r11
            int r2 = (r0 > r9 ? 1 : (r0 == r9 ? 0 : -1))
            if (r2 == 0) goto L_0x00a2
            if (r6 <= 0) goto L_0x009f
            goto L_0x00a0
        L_0x009f:
            r13 = r7
        L_0x00a0:
            r15 = r13
            goto L_0x00a6
        L_0x00a2:
            if (r6 <= 0) goto L_0x0082
            goto L_0x00a6
        L_0x00a5:
            r15 = r4
        L_0x00a6:
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeMath.js_pow(double, double):double");
    }

    private static double js_trunc(double d) {
        return d < 0.0d ? Math.ceil(d) : Math.floor(d);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:109:0x01df, code lost:
        if (r13 != 0.0d) goto L_0x01e1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01e1, code lost:
        r13 = 0.0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x02a3, code lost:
        r13 = Double.NaN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x02bb, code lost:
        return org.mozilla.javascript.ScriptRuntime.wrapNumber(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0084, code lost:
        r13 = r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object execIdCall(org.mozilla.javascript.IdFunctionObject r20, org.mozilla.javascript.Context r21, org.mozilla.javascript.Scriptable r22, org.mozilla.javascript.Scriptable r23, java.lang.Object[] r24) {
        /*
            r19 = this;
            r0 = r24
            java.lang.Object r1 = MATH_TAG
            r2 = r20
            boolean r1 = r2.hasTag(r1)
            if (r1 != 0) goto L_0x0011
            java.lang.Object r0 = super.execIdCall(r20, r21, r22, r23, r24)
            return r0
        L_0x0011:
            int r1 = r20.methodId()
            r2 = 0
            r4 = 4609176140021203710(0x3ff71547652b82fe, double:1.4426950408889634)
            r6 = -4503599627370496(0xfff0000000000000, double:-Infinity)
            r8 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            r10 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            r12 = 1
            r15 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r17 = 0
            r13 = 0
            switch(r1) {
                case 1: goto L_0x02bc;
                case 2: goto L_0x02a6;
                case 3: goto L_0x0282;
                case 4: goto L_0x0282;
                case 5: goto L_0x0279;
                case 6: goto L_0x026c;
                case 7: goto L_0x0263;
                case 8: goto L_0x0252;
                case 9: goto L_0x023d;
                case 10: goto L_0x0233;
                case 11: goto L_0x0223;
                case 12: goto L_0x01f9;
                case 13: goto L_0x01f9;
                case 14: goto L_0x01eb;
                case 15: goto L_0x01e5;
                case 16: goto L_0x01ba;
                case 17: goto L_0x01a8;
                case 18: goto L_0x019e;
                case 19: goto L_0x0194;
                case 20: goto L_0x018a;
                case 21: goto L_0x0180;
                case 22: goto L_0x0176;
                case 23: goto L_0x0170;
                case 24: goto L_0x0166;
                case 25: goto L_0x015c;
                case 26: goto L_0x0152;
                case 27: goto L_0x0148;
                case 28: goto L_0x0141;
                case 29: goto L_0x0137;
                case 30: goto L_0x0119;
                case 31: goto L_0x00e1;
                case 32: goto L_0x00ac;
                case 33: goto L_0x0087;
                case 34: goto L_0x0074;
                case 35: goto L_0x006c;
                case 36: goto L_0x0035;
                default: goto L_0x002b;
            }
        L_0x002b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.<init>(r1)
            throw r0
        L_0x0035:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            int r6 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
            if (r6 == 0) goto L_0x0069
            boolean r6 = java.lang.Double.isNaN(r0)
            if (r6 != 0) goto L_0x0069
            boolean r6 = java.lang.Double.isInfinite(r0)
            if (r6 == 0) goto L_0x004a
            goto L_0x0069
        L_0x004a:
            long r0 = org.mozilla.javascript.ScriptRuntime.toUint32((double) r0)
            int r6 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r6 != 0) goto L_0x0055
            java.lang.Double r0 = Double32
            return r0
        L_0x0055:
            long r0 = r0 >>> r13
            double r0 = (double) r0
            double r0 = java.lang.Math.log(r0)
            double r0 = r0 * r4
            double r0 = java.lang.Math.floor(r0)
            r2 = 4629418941960159232(0x403f000000000000, double:31.0)
            double r2 = r2 - r0
            java.lang.Double r0 = java.lang.Double.valueOf(r2)
            return r0
        L_0x0069:
            java.lang.Double r0 = Double32
            return r0
        L_0x006c:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            float r0 = (float) r0
            double r13 = (double) r0
            goto L_0x02b7
        L_0x0074:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            int r2 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
            if (r2 >= 0) goto L_0x007e
            goto L_0x02a3
        L_0x007e:
            double r0 = java.lang.Math.log(r0)
            double r0 = r0 * r4
        L_0x0084:
            r13 = r0
            goto L_0x02b7
        L_0x0087:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            boolean r2 = java.lang.Double.isNaN(r0)
            if (r2 != 0) goto L_0x00a9
            int r2 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
            if (r2 != 0) goto L_0x00a0
            double r15 = r15 / r0
            int r0 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r0 <= 0) goto L_0x009d
            java.lang.Double r0 = org.mozilla.javascript.ScriptRuntime.zeroObj
            return r0
        L_0x009d:
            java.lang.Double r0 = org.mozilla.javascript.ScriptRuntime.negativeZeroObj
            return r0
        L_0x00a0:
            double r0 = java.lang.Math.signum(r0)
            java.lang.Double r0 = java.lang.Double.valueOf(r0)
            return r0
        L_0x00a9:
            java.lang.Double r0 = org.mozilla.javascript.ScriptRuntime.NaNobj
            return r0
        L_0x00ac:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            boolean r2 = java.lang.Double.isNaN(r0)
            if (r2 != 0) goto L_0x00de
            int r2 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r2 > 0) goto L_0x00de
            int r2 = (r0 > r15 ? 1 : (r0 == r15 ? 0 : -1))
            if (r2 > 0) goto L_0x00de
            int r2 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
            if (r2 != 0) goto L_0x00cd
            double r15 = r15 / r0
            int r0 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r0 <= 0) goto L_0x00ca
            java.lang.Double r0 = org.mozilla.javascript.ScriptRuntime.zeroObj
            return r0
        L_0x00ca:
            java.lang.Double r0 = org.mozilla.javascript.ScriptRuntime.negativeZeroObj
            return r0
        L_0x00cd:
            double r2 = r0 + r15
            double r0 = r0 - r15
            double r2 = r2 / r0
            double r0 = java.lang.Math.log(r2)
            r2 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            double r0 = r0 * r2
            java.lang.Double r0 = java.lang.Double.valueOf(r0)
            return r0
        L_0x00de:
            java.lang.Double r0 = org.mozilla.javascript.ScriptRuntime.NaNobj
            return r0
        L_0x00e1:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            boolean r2 = java.lang.Double.isInfinite(r0)
            if (r2 == 0) goto L_0x00f0
            java.lang.Double r0 = java.lang.Double.valueOf(r0)
            return r0
        L_0x00f0:
            boolean r2 = java.lang.Double.isNaN(r0)
            if (r2 != 0) goto L_0x0116
            int r2 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
            if (r2 != 0) goto L_0x0105
            double r15 = r15 / r0
            int r0 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r0 <= 0) goto L_0x0102
            java.lang.Double r0 = org.mozilla.javascript.ScriptRuntime.zeroObj
            return r0
        L_0x0102:
            java.lang.Double r0 = org.mozilla.javascript.ScriptRuntime.negativeZeroObj
            return r0
        L_0x0105:
            double r2 = r0 * r0
            double r2 = r2 + r15
            double r2 = java.lang.Math.sqrt(r2)
            double r2 = r2 + r0
            double r0 = java.lang.Math.log(r2)
            java.lang.Double r0 = java.lang.Double.valueOf(r0)
            return r0
        L_0x0116:
            java.lang.Double r0 = org.mozilla.javascript.ScriptRuntime.NaNobj
            return r0
        L_0x0119:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            boolean r2 = java.lang.Double.isNaN(r0)
            if (r2 != 0) goto L_0x0134
            double r2 = r0 * r0
            double r2 = r2 - r15
            double r2 = java.lang.Math.sqrt(r2)
            double r2 = r2 + r0
            double r0 = java.lang.Math.log(r2)
            java.lang.Double r0 = java.lang.Double.valueOf(r0)
            return r0
        L_0x0134:
            java.lang.Double r0 = org.mozilla.javascript.ScriptRuntime.NaNobj
            return r0
        L_0x0137:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = js_trunc(r0)
            goto L_0x02b7
        L_0x0141:
            int r0 = js_imul(r24)
            double r13 = (double) r0
            goto L_0x02b7
        L_0x0148:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = java.lang.Math.tanh(r0)
            goto L_0x02b7
        L_0x0152:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = java.lang.Math.sinh(r0)
            goto L_0x02b7
        L_0x015c:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = java.lang.Math.log10(r0)
            goto L_0x02b7
        L_0x0166:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = java.lang.Math.log1p(r0)
            goto L_0x02b7
        L_0x0170:
            double r13 = js_hypot(r24)
            goto L_0x02b7
        L_0x0176:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = java.lang.Math.expm1(r0)
            goto L_0x02b7
        L_0x0180:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = java.lang.Math.cosh(r0)
            goto L_0x02b7
        L_0x018a:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = java.lang.Math.cbrt(r0)
            goto L_0x02b7
        L_0x0194:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = java.lang.Math.tan(r0)
            goto L_0x02b7
        L_0x019e:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = java.lang.Math.sqrt(r0)
            goto L_0x02b7
        L_0x01a8:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            boolean r2 = java.lang.Double.isInfinite(r0)
            if (r2 == 0) goto L_0x01b4
            goto L_0x02a3
        L_0x01b4:
            double r0 = java.lang.Math.sin(r0)
            goto L_0x0084
        L_0x01ba:
            double r13 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            boolean r0 = java.lang.Double.isNaN(r13)
            if (r0 != 0) goto L_0x02b7
            boolean r0 = java.lang.Double.isInfinite(r13)
            if (r0 != 0) goto L_0x02b7
            long r0 = java.lang.Math.round(r13)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x01d5
            double r0 = (double) r0
            goto L_0x0084
        L_0x01d5:
            int r0 = (r13 > r17 ? 1 : (r13 == r17 ? 0 : -1))
            if (r0 >= 0) goto L_0x01dd
            double r0 = org.mozilla.javascript.ScriptRuntime.negativeZero
            goto L_0x0084
        L_0x01dd:
            int r0 = (r13 > r17 ? 1 : (r13 == r17 ? 0 : -1))
            if (r0 == 0) goto L_0x02b7
        L_0x01e1:
            r13 = r17
            goto L_0x02b7
        L_0x01e5:
            double r13 = java.lang.Math.random()
            goto L_0x02b7
        L_0x01eb:
            double r1 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r3 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r12)
            double r13 = js_pow(r1, r3)
            goto L_0x02b7
        L_0x01f9:
            r2 = 12
            if (r1 != r2) goto L_0x01fe
            goto L_0x01ff
        L_0x01fe:
            r6 = r10
        L_0x01ff:
            int r3 = r0.length
            if (r13 == r3) goto L_0x0220
            r3 = r0[r13]
            double r3 = org.mozilla.javascript.ScriptRuntime.toNumber((java.lang.Object) r3)
            boolean r5 = java.lang.Double.isNaN(r3)
            if (r5 == 0) goto L_0x0211
            r13 = r3
            goto L_0x02b7
        L_0x0211:
            if (r1 != r2) goto L_0x0218
            double r3 = java.lang.Math.max(r6, r3)
            goto L_0x021c
        L_0x0218:
            double r3 = java.lang.Math.min(r6, r3)
        L_0x021c:
            r6 = r3
            int r13 = r13 + 1
            goto L_0x01ff
        L_0x0220:
            r13 = r6
            goto L_0x02b7
        L_0x0223:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            int r2 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
            if (r2 >= 0) goto L_0x022d
            goto L_0x02a3
        L_0x022d:
            double r0 = java.lang.Math.log(r0)
            goto L_0x0084
        L_0x0233:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = java.lang.Math.floor(r0)
            goto L_0x02b7
        L_0x023d:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            int r2 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r2 != 0) goto L_0x0247
            goto L_0x0084
        L_0x0247:
            int r2 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r2 != 0) goto L_0x024c
            goto L_0x01e1
        L_0x024c:
            double r0 = java.lang.Math.exp(r0)
            goto L_0x0084
        L_0x0252:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            boolean r2 = java.lang.Double.isInfinite(r0)
            if (r2 == 0) goto L_0x025d
            goto L_0x02a3
        L_0x025d:
            double r0 = java.lang.Math.cos(r0)
            goto L_0x0084
        L_0x0263:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = java.lang.Math.ceil(r0)
            goto L_0x02b7
        L_0x026c:
            double r1 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r3 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r12)
            double r13 = java.lang.Math.atan2(r1, r3)
            goto L_0x02b7
        L_0x0279:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            double r13 = java.lang.Math.atan(r0)
            goto L_0x02b7
        L_0x0282:
            double r2 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            boolean r0 = java.lang.Double.isNaN(r2)
            if (r0 != 0) goto L_0x02a3
            int r0 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x02a3
            int r0 = (r2 > r15 ? 1 : (r2 == r15 ? 0 : -1))
            if (r0 > 0) goto L_0x02a3
            r0 = 3
            if (r1 != r0) goto L_0x029d
            double r0 = java.lang.Math.acos(r2)
            goto L_0x0084
        L_0x029d:
            double r0 = java.lang.Math.asin(r2)
            goto L_0x0084
        L_0x02a3:
            r13 = 9221120237041090560(0x7ff8000000000000, double:NaN)
            goto L_0x02b7
        L_0x02a6:
            double r0 = org.mozilla.javascript.ScriptRuntime.toNumber(r0, r13)
            int r2 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
            if (r2 != 0) goto L_0x02b0
            goto L_0x01e1
        L_0x02b0:
            int r2 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
            if (r2 >= 0) goto L_0x0084
            double r0 = -r0
            goto L_0x0084
        L_0x02b7:
            java.lang.Number r0 = org.mozilla.javascript.ScriptRuntime.wrapNumber(r13)
            return r0
        L_0x02bc:
            java.lang.String r0 = "Math"
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeMath.execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r17) {
        /*
            r16 = this;
            r0 = r17
            int r1 = r17.length()
            r2 = 114(0x72, float:1.6E-43)
            r5 = 4
            r7 = 50
            r9 = 108(0x6c, float:1.51E-43)
            r10 = 112(0x70, float:1.57E-43)
            r11 = 101(0x65, float:1.42E-43)
            r12 = 76
            r13 = 104(0x68, float:1.46E-43)
            r15 = 116(0x74, float:1.63E-43)
            r6 = 99
            r4 = 97
            r3 = 115(0x73, float:1.61E-43)
            r8 = 1
            r14 = 0
            switch(r1) {
                case 1: goto L_0x0292;
                case 2: goto L_0x027f;
                case 3: goto L_0x01a9;
                case 4: goto L_0x00f6;
                case 5: goto L_0x004d;
                case 6: goto L_0x002f;
                case 7: goto L_0x0029;
                case 8: goto L_0x0024;
                default: goto L_0x0022;
            }
        L_0x0022:
            goto L_0x029d
        L_0x0024:
            java.lang.String r1 = "toSource"
            r5 = 1
            goto L_0x029f
        L_0x0029:
            java.lang.String r1 = "SQRT1_2"
            r5 = 43
            goto L_0x029f
        L_0x002f:
            char r1 = r0.charAt(r14)
            if (r1 != r12) goto L_0x003b
            java.lang.String r1 = "LOG10E"
            r5 = 42
            goto L_0x029f
        L_0x003b:
            r3 = 102(0x66, float:1.43E-43)
            if (r1 != r3) goto L_0x0045
            java.lang.String r1 = "fround"
            r5 = 35
            goto L_0x029f
        L_0x0045:
            if (r1 != r2) goto L_0x029d
            java.lang.String r1 = "random"
            r5 = 15
            goto L_0x029f
        L_0x004d:
            char r1 = r0.charAt(r14)
            if (r1 == r12) goto L_0x00f0
            r12 = 83
            if (r1 == r12) goto L_0x00ea
            if (r1 == r4) goto L_0x00a5
            if (r1 == r6) goto L_0x009f
            if (r1 == r13) goto L_0x0099
            if (r1 == r9) goto L_0x0083
            if (r1 == r2) goto L_0x007d
            if (r1 == r15) goto L_0x0077
            if (r1 == r11) goto L_0x0071
            r2 = 102(0x66, float:1.43E-43)
            if (r1 == r2) goto L_0x006b
            goto L_0x029d
        L_0x006b:
            java.lang.String r1 = "floor"
            r5 = 10
            goto L_0x029f
        L_0x0071:
            java.lang.String r1 = "expm1"
            r5 = 22
            goto L_0x029f
        L_0x0077:
            java.lang.String r1 = "trunc"
            r5 = 29
            goto L_0x029f
        L_0x007d:
            java.lang.String r1 = "round"
            r5 = 16
            goto L_0x029f
        L_0x0083:
            char r1 = r0.charAt(r5)
            r2 = 48
            if (r1 != r2) goto L_0x0091
            java.lang.String r1 = "log10"
            r5 = 25
            goto L_0x029f
        L_0x0091:
            if (r1 != r10) goto L_0x029d
            java.lang.String r1 = "log1p"
            r5 = 24
            goto L_0x029f
        L_0x0099:
            java.lang.String r1 = "hypot"
            r5 = 23
            goto L_0x029f
        L_0x009f:
            java.lang.String r1 = "clz32"
            r5 = 36
            goto L_0x029f
        L_0x00a5:
            char r1 = r0.charAt(r8)
            if (r1 != r6) goto L_0x00b1
            java.lang.String r1 = "acosh"
            r5 = 30
            goto L_0x029f
        L_0x00b1:
            if (r1 != r3) goto L_0x00b9
            java.lang.String r1 = "asinh"
            r5 = 31
            goto L_0x029f
        L_0x00b9:
            if (r1 != r15) goto L_0x029d
            char r1 = r0.charAt(r5)
            if (r1 != r7) goto L_0x00d4
            r2 = 2
            char r1 = r0.charAt(r2)
            if (r1 != r4) goto L_0x029d
            r3 = 3
            char r1 = r0.charAt(r3)
            r5 = 110(0x6e, float:1.54E-43)
            if (r1 != r5) goto L_0x029d
            r14 = 6
            goto L_0x02ab
        L_0x00d4:
            r2 = 2
            r3 = 3
            r5 = 110(0x6e, float:1.54E-43)
            if (r1 != r13) goto L_0x029d
            char r1 = r0.charAt(r2)
            if (r1 != r4) goto L_0x029d
            char r1 = r0.charAt(r3)
            if (r1 != r5) goto L_0x029d
            r14 = 32
            goto L_0x02ab
        L_0x00ea:
            java.lang.String r1 = "SQRT2"
            r5 = 44
            goto L_0x029f
        L_0x00f0:
            java.lang.String r1 = "LOG2E"
            r5 = 41
            goto L_0x029f
        L_0x00f6:
            char r1 = r0.charAt(r8)
            r2 = 78
            if (r1 == r2) goto L_0x01a3
            if (r1 == r11) goto L_0x019e
            r2 = 105(0x69, float:1.47E-43)
            if (r1 == r2) goto L_0x016d
            r2 = 109(0x6d, float:1.53E-43)
            if (r1 == r2) goto L_0x0167
            r2 = 111(0x6f, float:1.56E-43)
            if (r1 == r2) goto L_0x0139
            r2 = 113(0x71, float:1.58E-43)
            if (r1 == r2) goto L_0x0133
            if (r1 == r3) goto L_0x012f
            if (r1 == r15) goto L_0x012a
            switch(r1) {
                case 97: goto L_0x0124;
                case 98: goto L_0x011e;
                case 99: goto L_0x0119;
                default: goto L_0x0117;
            }
        L_0x0117:
            goto L_0x029d
        L_0x0119:
            java.lang.String r1 = "acos"
            r5 = 3
            goto L_0x029f
        L_0x011e:
            java.lang.String r1 = "cbrt"
            r5 = 20
            goto L_0x029f
        L_0x0124:
            java.lang.String r1 = "tanh"
            r5 = 27
            goto L_0x029f
        L_0x012a:
            java.lang.String r1 = "atan"
            r5 = 5
            goto L_0x029f
        L_0x012f:
            java.lang.String r1 = "asin"
            goto L_0x029f
        L_0x0133:
            java.lang.String r1 = "sqrt"
            r5 = 18
            goto L_0x029f
        L_0x0139:
            char r1 = r0.charAt(r14)
            if (r1 != r6) goto L_0x0151
            r2 = 2
            char r1 = r0.charAt(r2)
            if (r1 != r3) goto L_0x029d
            r4 = 3
            char r1 = r0.charAt(r4)
            if (r1 != r13) goto L_0x029d
            r14 = 21
            goto L_0x02ab
        L_0x0151:
            r2 = 2
            r4 = 3
            if (r1 != r9) goto L_0x029d
            char r1 = r0.charAt(r2)
            r2 = 103(0x67, float:1.44E-43)
            if (r1 != r2) goto L_0x029d
            char r1 = r0.charAt(r4)
            if (r1 != r7) goto L_0x029d
            r14 = 34
            goto L_0x02ab
        L_0x0167:
            java.lang.String r1 = "imul"
            r5 = 28
            goto L_0x029f
        L_0x016d:
            r4 = 3
            char r1 = r0.charAt(r4)
            if (r1 != r13) goto L_0x0187
            char r1 = r0.charAt(r14)
            if (r1 != r3) goto L_0x029d
            r2 = 2
            char r1 = r0.charAt(r2)
            r4 = 110(0x6e, float:1.54E-43)
            if (r1 != r4) goto L_0x029d
            r14 = 26
            goto L_0x02ab
        L_0x0187:
            r2 = 2
            r4 = 110(0x6e, float:1.54E-43)
            if (r1 != r4) goto L_0x029d
            char r1 = r0.charAt(r14)
            if (r1 != r3) goto L_0x029d
            char r1 = r0.charAt(r2)
            r2 = 103(0x67, float:1.44E-43)
            if (r1 != r2) goto L_0x029d
            r14 = 33
            goto L_0x02ab
        L_0x019e:
            java.lang.String r1 = "ceil"
            r5 = 7
            goto L_0x029f
        L_0x01a3:
            java.lang.String r1 = "LN10"
            r5 = 39
            goto L_0x029f
        L_0x01a9:
            char r1 = r0.charAt(r14)
            if (r1 == r12) goto L_0x026d
            if (r1 == r4) goto L_0x025c
            if (r1 == r6) goto L_0x024a
            if (r1 == r11) goto L_0x0238
            if (r1 == r10) goto L_0x0223
            if (r1 == r9) goto L_0x020e
            r2 = 109(0x6d, float:1.53E-43)
            if (r1 == r2) goto L_0x01eb
            if (r1 == r3) goto L_0x01d6
            if (r1 == r15) goto L_0x01c3
            goto L_0x029d
        L_0x01c3:
            r1 = 2
            char r1 = r0.charAt(r1)
            r2 = 110(0x6e, float:1.54E-43)
            if (r1 != r2) goto L_0x029d
            char r1 = r0.charAt(r8)
            if (r1 != r4) goto L_0x029d
            r14 = 19
            goto L_0x02ab
        L_0x01d6:
            r1 = 2
            r2 = 110(0x6e, float:1.54E-43)
            char r1 = r0.charAt(r1)
            if (r1 != r2) goto L_0x029d
            char r1 = r0.charAt(r8)
            r3 = 105(0x69, float:1.47E-43)
            if (r1 != r3) goto L_0x029d
            r14 = 17
            goto L_0x02ab
        L_0x01eb:
            r1 = 2
            r2 = 110(0x6e, float:1.54E-43)
            r3 = 105(0x69, float:1.47E-43)
            char r1 = r0.charAt(r1)
            if (r1 != r2) goto L_0x0200
            char r1 = r0.charAt(r8)
            if (r1 != r3) goto L_0x029d
            r14 = 13
            goto L_0x02ab
        L_0x0200:
            r2 = 120(0x78, float:1.68E-43)
            if (r1 != r2) goto L_0x029d
            char r1 = r0.charAt(r8)
            if (r1 != r4) goto L_0x029d
            r14 = 12
            goto L_0x02ab
        L_0x020e:
            r1 = 2
            char r1 = r0.charAt(r1)
            r2 = 103(0x67, float:1.44E-43)
            if (r1 != r2) goto L_0x029d
            char r1 = r0.charAt(r8)
            r2 = 111(0x6f, float:1.56E-43)
            if (r1 != r2) goto L_0x029d
            r14 = 11
            goto L_0x02ab
        L_0x0223:
            r1 = 2
            r2 = 111(0x6f, float:1.56E-43)
            char r1 = r0.charAt(r1)
            r3 = 119(0x77, float:1.67E-43)
            if (r1 != r3) goto L_0x029d
            char r1 = r0.charAt(r8)
            if (r1 != r2) goto L_0x029d
            r14 = 14
            goto L_0x02ab
        L_0x0238:
            r1 = 2
            char r1 = r0.charAt(r1)
            if (r1 != r10) goto L_0x029d
            char r1 = r0.charAt(r8)
            r2 = 120(0x78, float:1.68E-43)
            if (r1 != r2) goto L_0x029d
            r14 = 9
            goto L_0x02ab
        L_0x024a:
            r1 = 2
            char r1 = r0.charAt(r1)
            if (r1 != r3) goto L_0x029d
            char r1 = r0.charAt(r8)
            r2 = 111(0x6f, float:1.56E-43)
            if (r1 != r2) goto L_0x029d
            r14 = 8
            goto L_0x02ab
        L_0x025c:
            r1 = 2
            char r2 = r0.charAt(r1)
            if (r2 != r3) goto L_0x029d
            char r2 = r0.charAt(r8)
            r3 = 98
            if (r2 != r3) goto L_0x029d
            r14 = 2
            goto L_0x02ab
        L_0x026d:
            r1 = 2
            char r1 = r0.charAt(r1)
            if (r1 != r7) goto L_0x029d
            char r1 = r0.charAt(r8)
            r2 = 78
            if (r1 != r2) goto L_0x029d
            r14 = 40
            goto L_0x02ab
        L_0x027f:
            char r1 = r0.charAt(r14)
            r2 = 80
            if (r1 != r2) goto L_0x029d
            char r1 = r0.charAt(r8)
            r2 = 73
            if (r1 != r2) goto L_0x029d
            r14 = 38
            goto L_0x02ab
        L_0x0292:
            char r1 = r0.charAt(r14)
            r2 = 69
            if (r1 != r2) goto L_0x029d
            r14 = 37
            goto L_0x02ab
        L_0x029d:
            r1 = 0
            r5 = 0
        L_0x029f:
            if (r1 == 0) goto L_0x02aa
            if (r1 == r0) goto L_0x02aa
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x02aa
            goto L_0x02ab
        L_0x02aa:
            r14 = r5
        L_0x02ab:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeMath.findPrototypeId(java.lang.String):int");
    }

    public String getClassName() {
        return "Math";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x007a, code lost:
        r2 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x008a, code lost:
        r0 = r1;
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x008c, code lost:
        initPrototypeMethod(MATH_TAG, r4, r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initPrototypeId(int r4) {
        /*
            r3 = this;
            r0 = 36
            if (r4 > r0) goto L_0x0092
            r0 = 0
            r1 = 2
            r2 = 1
            switch(r4) {
                case 1: goto L_0x0088;
                case 2: goto L_0x0085;
                case 3: goto L_0x0082;
                case 4: goto L_0x007f;
                case 5: goto L_0x007c;
                case 6: goto L_0x0078;
                case 7: goto L_0x0075;
                case 8: goto L_0x0072;
                case 9: goto L_0x006f;
                case 10: goto L_0x006c;
                case 11: goto L_0x0069;
                case 12: goto L_0x0066;
                case 13: goto L_0x0063;
                case 14: goto L_0x0060;
                case 15: goto L_0x005d;
                case 16: goto L_0x005a;
                case 17: goto L_0x0057;
                case 18: goto L_0x0054;
                case 19: goto L_0x0051;
                case 20: goto L_0x004e;
                case 21: goto L_0x004b;
                case 22: goto L_0x0048;
                case 23: goto L_0x0045;
                case 24: goto L_0x0042;
                case 25: goto L_0x003f;
                case 26: goto L_0x003b;
                case 27: goto L_0x0037;
                case 28: goto L_0x0034;
                case 29: goto L_0x0030;
                case 30: goto L_0x002c;
                case 31: goto L_0x0028;
                case 32: goto L_0x0024;
                case 33: goto L_0x0020;
                case 34: goto L_0x001c;
                case 35: goto L_0x0018;
                case 36: goto L_0x0014;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r4 = java.lang.String.valueOf(r4)
            r0.<init>(r4)
            throw r0
        L_0x0014:
            java.lang.String r0 = "clz32"
            goto L_0x008c
        L_0x0018:
            java.lang.String r0 = "fround"
            goto L_0x008c
        L_0x001c:
            java.lang.String r0 = "log2"
            goto L_0x008c
        L_0x0020:
            java.lang.String r0 = "sign"
            goto L_0x008c
        L_0x0024:
            java.lang.String r0 = "atanh"
            goto L_0x008c
        L_0x0028:
            java.lang.String r0 = "asinh"
            goto L_0x008c
        L_0x002c:
            java.lang.String r0 = "acosh"
            goto L_0x008c
        L_0x0030:
            java.lang.String r0 = "trunc"
            goto L_0x008c
        L_0x0034:
            java.lang.String r0 = "imul"
            goto L_0x007a
        L_0x0037:
            java.lang.String r0 = "tanh"
            goto L_0x008c
        L_0x003b:
            java.lang.String r0 = "sinh"
            goto L_0x008c
        L_0x003f:
            java.lang.String r0 = "log10"
            goto L_0x008c
        L_0x0042:
            java.lang.String r0 = "log1p"
            goto L_0x008c
        L_0x0045:
            java.lang.String r0 = "hypot"
            goto L_0x007a
        L_0x0048:
            java.lang.String r0 = "expm1"
            goto L_0x008c
        L_0x004b:
            java.lang.String r0 = "cosh"
            goto L_0x008c
        L_0x004e:
            java.lang.String r0 = "cbrt"
            goto L_0x008c
        L_0x0051:
            java.lang.String r0 = "tan"
            goto L_0x008c
        L_0x0054:
            java.lang.String r0 = "sqrt"
            goto L_0x008c
        L_0x0057:
            java.lang.String r0 = "sin"
            goto L_0x008c
        L_0x005a:
            java.lang.String r0 = "round"
            goto L_0x008c
        L_0x005d:
            java.lang.String r1 = "random"
            goto L_0x008a
        L_0x0060:
            java.lang.String r0 = "pow"
            goto L_0x007a
        L_0x0063:
            java.lang.String r0 = "min"
            goto L_0x007a
        L_0x0066:
            java.lang.String r0 = "max"
            goto L_0x007a
        L_0x0069:
            java.lang.String r0 = "log"
            goto L_0x008c
        L_0x006c:
            java.lang.String r0 = "floor"
            goto L_0x008c
        L_0x006f:
            java.lang.String r0 = "exp"
            goto L_0x008c
        L_0x0072:
            java.lang.String r0 = "cos"
            goto L_0x008c
        L_0x0075:
            java.lang.String r0 = "ceil"
            goto L_0x008c
        L_0x0078:
            java.lang.String r0 = "atan2"
        L_0x007a:
            r2 = 2
            goto L_0x008c
        L_0x007c:
            java.lang.String r0 = "atan"
            goto L_0x008c
        L_0x007f:
            java.lang.String r0 = "asin"
            goto L_0x008c
        L_0x0082:
            java.lang.String r0 = "acos"
            goto L_0x008c
        L_0x0085:
            java.lang.String r0 = "abs"
            goto L_0x008c
        L_0x0088:
            java.lang.String r1 = "toSource"
        L_0x008a:
            r0 = r1
            r2 = 0
        L_0x008c:
            java.lang.Object r1 = MATH_TAG
            r3.initPrototypeMethod(r1, r4, r0, r2)
            goto L_0x00e6
        L_0x0092:
            switch(r4) {
                case 37: goto L_0x00d7;
                case 38: goto L_0x00cf;
                case 39: goto L_0x00c7;
                case 40: goto L_0x00bf;
                case 41: goto L_0x00b7;
                case 42: goto L_0x00af;
                case 43: goto L_0x00a7;
                case 44: goto L_0x009f;
                default: goto L_0x0095;
            }
        L_0x0095:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r4 = java.lang.String.valueOf(r4)
            r0.<init>(r4)
            throw r0
        L_0x009f:
            r0 = 4609047870845172685(0x3ff6a09e667f3bcd, double:1.4142135623730951)
            java.lang.String r2 = "SQRT2"
            goto L_0x00de
        L_0x00a7:
            r0 = 4604544271217802189(0x3fe6a09e667f3bcd, double:0.7071067811865476)
            java.lang.String r2 = "SQRT1_2"
            goto L_0x00de
        L_0x00af:
            r0 = 4601495173785380110(0x3fdbcb7b1526e50e, double:0.4342944819032518)
            java.lang.String r2 = "LOG10E"
            goto L_0x00de
        L_0x00b7:
            r0 = 4609176140021203710(0x3ff71547652b82fe, double:1.4426950408889634)
            java.lang.String r2 = "LOG2E"
            goto L_0x00de
        L_0x00bf:
            r0 = 4604418534313441775(0x3fe62e42fefa39ef, double:0.6931471805599453)
            java.lang.String r2 = "LN2"
            goto L_0x00de
        L_0x00c7:
            r0 = 4612367379483415830(0x40026bb1bbb55516, double:2.302585092994046)
            java.lang.String r2 = "LN10"
            goto L_0x00de
        L_0x00cf:
            r0 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
            java.lang.String r2 = "PI"
            goto L_0x00de
        L_0x00d7:
            r0 = 4613303445314885481(0x4005bf0a8b145769, double:2.718281828459045)
            java.lang.String r2 = "E"
        L_0x00de:
            java.lang.Number r0 = org.mozilla.javascript.ScriptRuntime.wrapNumber(r0)
            r1 = 7
            r3.initPrototypeValue((int) r4, (java.lang.String) r2, (java.lang.Object) r0, (int) r1)
        L_0x00e6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeMath.initPrototypeId(int):void");
    }
}
