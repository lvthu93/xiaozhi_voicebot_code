package org.mozilla.javascript.regexp;

import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.TopLevel;
import org.mozilla.javascript.Undefined;

class NativeRegExpCtor extends BaseFunction {
    private static final int DOLLAR_ID_BASE = 12;
    private static final int Id_AMPERSAND = 6;
    private static final int Id_BACK_QUOTE = 10;
    private static final int Id_DOLLAR_1 = 13;
    private static final int Id_DOLLAR_2 = 14;
    private static final int Id_DOLLAR_3 = 15;
    private static final int Id_DOLLAR_4 = 16;
    private static final int Id_DOLLAR_5 = 17;
    private static final int Id_DOLLAR_6 = 18;
    private static final int Id_DOLLAR_7 = 19;
    private static final int Id_DOLLAR_8 = 20;
    private static final int Id_DOLLAR_9 = 21;
    private static final int Id_PLUS = 8;
    private static final int Id_QUOTE = 12;
    private static final int Id_STAR = 2;
    private static final int Id_UNDERSCORE = 4;
    private static final int Id_input = 3;
    private static final int Id_lastMatch = 5;
    private static final int Id_lastParen = 7;
    private static final int Id_leftContext = 9;
    private static final int Id_multiline = 1;
    private static final int Id_rightContext = 11;
    private static final int MAX_INSTANCE_ID = 21;
    private static final long serialVersionUID = -5733330028285400526L;
    private int inputAttr = 4;
    private int multilineAttr = 4;
    private int starAttr = 4;
    private int underscoreAttr = 4;

    private static RegExpImpl getImpl() {
        return (RegExpImpl) ScriptRuntime.getRegExpProxy(Context.getCurrentContext());
    }

    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (objArr.length > 0) {
            Object obj = objArr[0];
            if ((obj instanceof NativeRegExp) && (objArr.length == 1 || objArr[1] == Undefined.instance)) {
                return obj;
            }
        }
        return construct(context, scriptable, objArr);
    }

    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        NativeRegExp nativeRegExp = new NativeRegExp();
        nativeRegExp.compile(context, scriptable, objArr);
        ScriptRuntime.setBuiltinProtoAndParent(nativeRegExp, scriptable, TopLevel.Builtins.RegExp);
        return nativeRegExp;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00eb, code lost:
        if (r11.charAt(0) == '$') goto L_0x0103;
     */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x010a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findInstanceIdInfo(java.lang.String r11) {
        /*
            r10 = this;
            int r0 = r11.length()
            r1 = 3
            r2 = 12
            r3 = 4
            r4 = 1
            r5 = 5
            r6 = 2
            r7 = 0
            if (r0 == r6) goto L_0x004c
            if (r0 == r5) goto L_0x0047
            r8 = 9
            if (r0 == r8) goto L_0x0028
            r9 = 11
            if (r0 == r9) goto L_0x0022
            if (r0 == r2) goto L_0x001c
            goto L_0x00f6
        L_0x001c:
            java.lang.String r0 = "rightContext"
            r2 = 11
            goto L_0x00f8
        L_0x0022:
            java.lang.String r0 = "leftContext"
            r2 = 9
            goto L_0x00f8
        L_0x0028:
            char r0 = r11.charAt(r3)
            r2 = 77
            if (r0 != r2) goto L_0x0035
            java.lang.String r0 = "lastMatch"
            r2 = 5
            goto L_0x00f8
        L_0x0035:
            r2 = 80
            if (r0 != r2) goto L_0x003e
            java.lang.String r0 = "lastParen"
            r2 = 7
            goto L_0x00f8
        L_0x003e:
            r2 = 105(0x69, float:1.47E-43)
            if (r0 != r2) goto L_0x00f6
            java.lang.String r0 = "multiline"
            r2 = 1
            goto L_0x00f8
        L_0x0047:
            java.lang.String r0 = "input"
            r2 = 3
            goto L_0x00f8
        L_0x004c:
            char r0 = r11.charAt(r4)
            r8 = 38
            r9 = 36
            if (r0 == r8) goto L_0x00ee
            r8 = 39
            if (r0 == r8) goto L_0x00e7
            r2 = 42
            if (r0 == r2) goto L_0x00df
            r2 = 43
            if (r0 == r2) goto L_0x00d6
            r2 = 95
            if (r0 == r2) goto L_0x00ce
            r2 = 96
            if (r0 == r2) goto L_0x00c5
            switch(r0) {
                case 49: goto L_0x00bc;
                case 50: goto L_0x00b3;
                case 51: goto L_0x00aa;
                case 52: goto L_0x00a1;
                case 53: goto L_0x0097;
                case 54: goto L_0x008d;
                case 55: goto L_0x0083;
                case 56: goto L_0x0079;
                case 57: goto L_0x006f;
                default: goto L_0x006d;
            }
        L_0x006d:
            goto L_0x00f6
        L_0x006f:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 21
            goto L_0x0103
        L_0x0079:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 20
            goto L_0x0103
        L_0x0083:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 19
            goto L_0x0103
        L_0x008d:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 18
            goto L_0x0103
        L_0x0097:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 17
            goto L_0x0103
        L_0x00a1:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 16
            goto L_0x0103
        L_0x00aa:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 15
            goto L_0x0103
        L_0x00b3:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 14
            goto L_0x0103
        L_0x00bc:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 13
            goto L_0x0103
        L_0x00c5:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 10
            goto L_0x0103
        L_0x00ce:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 4
            goto L_0x0103
        L_0x00d6:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 8
            goto L_0x0103
        L_0x00df:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 2
            goto L_0x0103
        L_0x00e7:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            goto L_0x0103
        L_0x00ee:
            char r0 = r11.charAt(r7)
            if (r0 != r9) goto L_0x00f6
            r2 = 6
            goto L_0x0103
        L_0x00f6:
            r0 = 0
            r2 = 0
        L_0x00f8:
            if (r0 == 0) goto L_0x0103
            if (r0 == r11) goto L_0x0103
            boolean r0 = r0.equals(r11)
            if (r0 != 0) goto L_0x0103
            r2 = 0
        L_0x0103:
            if (r2 != 0) goto L_0x010a
            int r11 = super.findInstanceIdInfo(r11)
            return r11
        L_0x010a:
            if (r2 == r4) goto L_0x011c
            if (r2 == r6) goto L_0x0119
            if (r2 == r1) goto L_0x0116
            if (r2 == r3) goto L_0x0113
            goto L_0x011e
        L_0x0113:
            int r5 = r10.underscoreAttr
            goto L_0x011e
        L_0x0116:
            int r5 = r10.inputAttr
            goto L_0x011e
        L_0x0119:
            int r5 = r10.starAttr
            goto L_0x011e
        L_0x011c:
            int r5 = r10.multilineAttr
        L_0x011e:
            int r11 = super.getMaxInstanceId()
            int r11 = r11 + r2
            int r11 = org.mozilla.javascript.IdScriptableObject.instanceIdInfo(r5, r11)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExpCtor.findInstanceIdInfo(java.lang.String):int");
    }

    public int getArity() {
        return 2;
    }

    public String getFunctionName() {
        return "RegExp";
    }

    public String getInstanceIdName(int i) {
        int maxInstanceId = i - super.getMaxInstanceId();
        if (1 > maxInstanceId || maxInstanceId > 21) {
            return super.getInstanceIdName(i);
        }
        switch (maxInstanceId) {
            case 1:
                return "multiline";
            case 2:
                return "$*";
            case 3:
                return "input";
            case 4:
                return "$_";
            case 5:
                return "lastMatch";
            case 6:
                return "$&";
            case 7:
                return "lastParen";
            case 8:
                return "$+";
            case 9:
                return "leftContext";
            case 10:
                return "$`";
            case 11:
                return "rightContext";
            case 12:
                return "$'";
            default:
                return new String(new char[]{'$', (char) (((maxInstanceId - 12) - 1) + 49)});
        }
    }

    public Object getInstanceIdValue(int i) {
        Object obj;
        int maxInstanceId = i - super.getMaxInstanceId();
        if (1 > maxInstanceId || maxInstanceId > 21) {
            return super.getInstanceIdValue(i);
        }
        RegExpImpl impl = getImpl();
        switch (maxInstanceId) {
            case 1:
            case 2:
                return ScriptRuntime.wrapBoolean(impl.multiline);
            case 3:
            case 4:
                obj = impl.input;
                break;
            case 5:
            case 6:
                obj = impl.lastMatch;
                break;
            case 7:
            case 8:
                obj = impl.lastParen;
                break;
            case 9:
            case 10:
                obj = impl.leftContext;
                break;
            case 11:
            case 12:
                obj = impl.rightContext;
                break;
            default:
                obj = impl.getParenSubString((maxInstanceId - 12) - 1);
                break;
        }
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    public int getLength() {
        return 2;
    }

    public int getMaxInstanceId() {
        return super.getMaxInstanceId() + 21;
    }

    public void setInstanceIdAttributes(int i, int i2) {
        int maxInstanceId = i - super.getMaxInstanceId();
        switch (maxInstanceId) {
            case 1:
                this.multilineAttr = i2;
                return;
            case 2:
                this.starAttr = i2;
                return;
            case 3:
                this.inputAttr = i2;
                return;
            case 4:
                this.underscoreAttr = i2;
                return;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return;
            default:
                int i3 = (maxInstanceId - 12) - 1;
                if (i3 < 0 || i3 > 8) {
                    super.setInstanceIdAttributes(i, i2);
                    return;
                }
                return;
        }
    }

    public void setInstanceIdValue(int i, Object obj) {
        int maxInstanceId = i - super.getMaxInstanceId();
        switch (maxInstanceId) {
            case 1:
            case 2:
                getImpl().multiline = ScriptRuntime.toBoolean(obj);
                return;
            case 3:
            case 4:
                getImpl().input = ScriptRuntime.toString(obj);
                return;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return;
            default:
                int i2 = (maxInstanceId - 12) - 1;
                if (i2 < 0 || i2 > 8) {
                    super.setInstanceIdValue(i, obj);
                    return;
                }
                return;
        }
    }
}
