package org.mozilla.javascript;

import java.text.Collator;
import java.text.Normalizer;
import java.util.Locale;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.TopLevel;
import org.mozilla.javascript.regexp.NativeRegExp;

final class NativeString extends IdScriptableObject {
    private static final int ConstructorId_charAt = -5;
    private static final int ConstructorId_charCodeAt = -6;
    private static final int ConstructorId_concat = -14;
    private static final int ConstructorId_equalsIgnoreCase = -30;
    private static final int ConstructorId_fromCharCode = -1;
    private static final int ConstructorId_fromCodePoint = -2;
    private static final int ConstructorId_indexOf = -7;
    private static final int ConstructorId_lastIndexOf = -8;
    private static final int ConstructorId_localeCompare = -34;
    private static final int ConstructorId_match = -31;
    private static final int ConstructorId_replace = -33;
    private static final int ConstructorId_search = -32;
    private static final int ConstructorId_slice = -15;
    private static final int ConstructorId_split = -9;
    private static final int ConstructorId_substr = -13;
    private static final int ConstructorId_substring = -10;
    private static final int ConstructorId_toLocaleLowerCase = -35;
    private static final int ConstructorId_toLowerCase = -11;
    private static final int ConstructorId_toUpperCase = -12;
    private static final int Id_anchor = 28;
    private static final int Id_big = 21;
    private static final int Id_blink = 22;
    private static final int Id_bold = 16;
    private static final int Id_charAt = 5;
    private static final int Id_charCodeAt = 6;
    private static final int Id_codePointAt = 45;
    private static final int Id_concat = 14;
    private static final int Id_constructor = 1;
    private static final int Id_endsWith = 42;
    private static final int Id_equals = 29;
    private static final int Id_equalsIgnoreCase = 30;
    private static final int Id_fixed = 18;
    private static final int Id_fontcolor = 26;
    private static final int Id_fontsize = 25;
    private static final int Id_includes = 40;
    private static final int Id_indexOf = 7;
    private static final int Id_italics = 17;
    private static final int Id_lastIndexOf = 8;
    private static final int Id_length = 1;
    private static final int Id_link = 27;
    private static final int Id_localeCompare = 34;
    private static final int Id_match = 31;
    private static final int Id_normalize = 43;
    private static final int Id_padEnd = 47;
    private static final int Id_padStart = 46;
    private static final int Id_repeat = 44;
    private static final int Id_replace = 33;
    private static final int Id_search = 32;
    private static final int Id_slice = 15;
    private static final int Id_small = 20;
    private static final int Id_split = 9;
    private static final int Id_startsWith = 41;
    private static final int Id_strike = 19;
    private static final int Id_sub = 24;
    private static final int Id_substr = 13;
    private static final int Id_substring = 10;
    private static final int Id_sup = 23;
    private static final int Id_toLocaleLowerCase = 35;
    private static final int Id_toLocaleUpperCase = 36;
    private static final int Id_toLowerCase = 11;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    private static final int Id_toUpperCase = 12;
    private static final int Id_trim = 37;
    private static final int Id_trimEnd = 50;
    private static final int Id_trimLeft = 38;
    private static final int Id_trimRight = 39;
    private static final int Id_trimStart = 49;
    private static final int Id_valueOf = 4;
    private static final int MAX_INSTANCE_ID = 1;
    private static final int MAX_PROTOTYPE_ID = 50;
    private static final Object STRING_TAG = "String";
    private static final int SymbolId_iterator = 48;
    private static final long serialVersionUID = 920268368584188687L;
    private CharSequence string;

    public NativeString(CharSequence charSequence) {
        this.string = charSequence;
    }

    private ScriptableObject defaultIndexPropertyDescriptor(Object obj) {
        Scriptable parentScope = getParentScope();
        if (parentScope == null) {
            parentScope = this;
        }
        NativeObject nativeObject = new NativeObject();
        ScriptRuntime.setBuiltinProtoAndParent(nativeObject, parentScope, TopLevel.Builtins.Object);
        nativeObject.defineProperty(ES6Iterator.VALUE_PROPERTY, obj, 0);
        Boolean bool = Boolean.FALSE;
        nativeObject.defineProperty("writable", (Object) bool, 0);
        nativeObject.defineProperty("enumerable", (Object) Boolean.TRUE, 0);
        nativeObject.defineProperty("configurable", (Object) bool, 0);
        return nativeObject;
    }

    public static void init(Scriptable scriptable, boolean z) {
        new NativeString("").exportAsJSClass(50, scriptable, z);
    }

    private static String js_concat(String str, Object[] objArr) {
        int length = objArr.length;
        if (length == 0) {
            return str;
        }
        if (length == 1) {
            return str.concat(ScriptRuntime.toString(objArr[0]));
        }
        int length2 = str.length();
        String[] strArr = new String[length];
        for (int i = 0; i != length; i++) {
            String scriptRuntime = ScriptRuntime.toString(objArr[i]);
            strArr[i] = scriptRuntime;
            length2 += scriptRuntime.length();
        }
        StringBuilder sb = new StringBuilder(length2);
        sb.append(str);
        for (int i2 = 0; i2 != length; i2++) {
            sb.append(strArr[i2]);
        }
        return sb.toString();
    }

    private static int js_indexOf(int i, String str, Object[] objArr) {
        int length;
        String scriptRuntime = ScriptRuntime.toString(objArr, 0);
        double integer = ScriptRuntime.toInteger(objArr, 1);
        if (i == 41 || i == 42 || scriptRuntime.length() != 0) {
            if (i != 41 && i != 42 && integer > ((double) str.length())) {
                return -1;
            }
            if (integer < 0.0d) {
                integer = 0.0d;
            } else {
                if (integer > ((double) str.length())) {
                    length = str.length();
                } else if (i == 42 && (Double.isNaN(integer) || integer > ((double) str.length()))) {
                    length = str.length();
                }
                integer = (double) length;
            }
            if (42 == i) {
                if (objArr.length == 0 || objArr.length == 1 || (objArr.length == 2 && objArr[1] == Undefined.instance)) {
                    integer = (double) str.length();
                }
                if (str.substring(0, (int) integer).endsWith(scriptRuntime)) {
                    return 0;
                }
                return -1;
            } else if (i != 41) {
                return str.indexOf(scriptRuntime, (int) integer);
            } else {
                if (str.startsWith(scriptRuntime, (int) integer)) {
                    return 0;
                }
                return -1;
            }
        } else if (integer > ((double) str.length())) {
            return str.length();
        } else {
            return (int) integer;
        }
    }

    private static int js_lastIndexOf(String str, Object[] objArr) {
        String scriptRuntime = ScriptRuntime.toString(objArr, 0);
        double number = ScriptRuntime.toNumber(objArr, 1);
        if (Double.isNaN(number) || number > ((double) str.length())) {
            number = (double) str.length();
        } else if (number < 0.0d) {
            number = 0.0d;
        }
        return str.lastIndexOf(scriptRuntime, (int) number);
    }

    private static String js_pad(Context context, Scriptable scriptable, IdFunctionObject idFunctionObject, Object[] objArr, boolean z) {
        String str;
        String scriptRuntime = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, scriptable, idFunctionObject));
        long length = ScriptRuntime.toLength(objArr, 0);
        if (length <= ((long) scriptRuntime.length())) {
            return scriptRuntime;
        }
        if (objArr.length < 2 || Undefined.isUndefined(objArr[1])) {
            str = " ";
        } else {
            str = ScriptRuntime.toString(objArr[1]);
            if (str.length() < 1) {
                return scriptRuntime;
            }
        }
        int length2 = (int) (length - ((long) scriptRuntime.length()));
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(str);
        } while (sb.length() < length2);
        sb.setLength(length2);
        if (!z) {
            return sb.insert(0, scriptRuntime).toString();
        }
        sb.append(scriptRuntime);
        return sb.toString();
    }

    private static String js_repeat(Context context, Scriptable scriptable, IdFunctionObject idFunctionObject, Object[] objArr) {
        String scriptRuntime = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context, scriptable, idFunctionObject));
        double integer = ScriptRuntime.toInteger(objArr, 0);
        if (integer < 0.0d || integer == Double.POSITIVE_INFINITY) {
            throw ScriptRuntime.rangeError("Invalid count value");
        } else if (integer == 0.0d || scriptRuntime.length() == 0) {
            return "";
        } else {
            long length = ((long) scriptRuntime.length()) * ((long) integer);
            if (integer > 2.147483647E9d || length > 2147483647L) {
                throw ScriptRuntime.rangeError("Invalid size or count value");
            }
            StringBuilder sb = new StringBuilder((int) length);
            sb.append(scriptRuntime);
            int i = (int) integer;
            int i2 = 1;
            while (i2 <= i / 2) {
                sb.append(sb);
                i2 *= 2;
            }
            if (i2 < i) {
                sb.append(sb.substring(0, (i - i2) * scriptRuntime.length()));
            }
            return sb.toString();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003c, code lost:
        if (r6 < 0.0d) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0042, code lost:
        if (r6 > r1) goto L_0x0046;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.CharSequence js_slice(java.lang.CharSequence r10, java.lang.Object[] r11) {
        /*
            int r0 = r11.length
            r1 = 0
            r3 = 1
            if (r0 >= r3) goto L_0x0008
            r4 = r1
            goto L_0x000f
        L_0x0008:
            r0 = 0
            r0 = r11[r0]
            double r4 = org.mozilla.javascript.ScriptRuntime.toInteger((java.lang.Object) r0)
        L_0x000f:
            int r0 = r10.length()
            int r6 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r6 >= 0) goto L_0x001f
            double r6 = (double) r0
            double r4 = r4 + r6
            int r6 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r6 >= 0) goto L_0x0025
            r4 = r1
            goto L_0x0025
        L_0x001f:
            double r6 = (double) r0
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 <= 0) goto L_0x0025
            r4 = r6
        L_0x0025:
            int r6 = r11.length
            r7 = 2
            if (r6 < r7) goto L_0x004c
            r11 = r11[r3]
            java.lang.Object r3 = org.mozilla.javascript.Undefined.instance
            if (r11 != r3) goto L_0x0030
            goto L_0x004c
        L_0x0030:
            double r6 = org.mozilla.javascript.ScriptRuntime.toInteger((java.lang.Object) r11)
            int r11 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r11 >= 0) goto L_0x003f
            double r8 = (double) r0
            double r6 = r6 + r8
            int r11 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r11 >= 0) goto L_0x0045
            goto L_0x0046
        L_0x003f:
            double r1 = (double) r0
            int r11 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r11 <= 0) goto L_0x0045
            goto L_0x0046
        L_0x0045:
            r1 = r6
        L_0x0046:
            int r11 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r11 >= 0) goto L_0x004d
            r1 = r4
            goto L_0x004d
        L_0x004c:
            double r1 = (double) r0
        L_0x004d:
            int r11 = (int) r4
            int r0 = (int) r1
            java.lang.CharSequence r10 = r10.subSequence(r11, r0)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeString.js_slice(java.lang.CharSequence, java.lang.Object[]):java.lang.CharSequence");
    }

    private static CharSequence js_substr(CharSequence charSequence, Object[] objArr) {
        if (objArr.length < 1) {
            return charSequence;
        }
        double integer = ScriptRuntime.toInteger(objArr[0]);
        int length = charSequence.length();
        double d = 0.0d;
        if (integer < 0.0d) {
            integer += (double) length;
            if (integer < 0.0d) {
                integer = 0.0d;
            }
        } else {
            double d2 = (double) length;
            if (integer > d2) {
                integer = d2;
            }
        }
        double d3 = (double) length;
        if (objArr.length > 1) {
            Object obj = objArr[1];
            if (!Undefined.isUndefined(obj)) {
                double integer2 = ScriptRuntime.toInteger(obj);
                if (integer2 >= 0.0d) {
                    d = integer2;
                }
                double d4 = d + integer;
                if (d4 <= d3) {
                    d3 = d4;
                }
            }
        }
        return charSequence.subSequence((int) integer, (int) d3);
    }

    private static CharSequence js_substring(Context context, CharSequence charSequence, Object[] objArr) {
        Object obj;
        int length = charSequence.length();
        double integer = ScriptRuntime.toInteger(objArr, 0);
        double d = 0.0d;
        if (integer < 0.0d) {
            integer = 0.0d;
        } else {
            double d2 = (double) length;
            if (integer > d2) {
                integer = d2;
            }
        }
        if (objArr.length <= 1 || (obj = objArr[1]) == Undefined.instance) {
            d = (double) length;
        } else {
            double integer2 = ScriptRuntime.toInteger(obj);
            if (integer2 >= 0.0d) {
                d = (double) length;
                if (integer2 <= d) {
                    d = integer2;
                }
            }
            if (d < integer) {
                if (context.getLanguageVersion() != 120) {
                    double d3 = integer;
                    integer = d;
                    d = d3;
                } else {
                    d = integer;
                }
            }
        }
        return charSequence.subSequence((int) integer, (int) d);
    }

    private static NativeString realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof NativeString) {
            return (NativeString) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    private static String tagify(Scriptable scriptable, String str, String str2, Object[] objArr) {
        String scriptRuntime = ScriptRuntime.toString((Object) scriptable);
        StringBuilder sb = new StringBuilder("<");
        sb.append(str);
        if (str2 != null) {
            sb.append(' ');
            sb.append(str2);
            sb.append("=\"");
            sb.append(ScriptRuntime.toString(objArr, 0));
            sb.append('\"');
        }
        sb.append('>');
        sb.append(scriptRuntime);
        sb.append("</");
        sb.append(str);
        sb.append('>');
        return sb.toString();
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        boolean z;
        Normalizer.Form form;
        IdFunctionObject idFunctionObject2 = idFunctionObject;
        Context context2 = context;
        Scriptable scriptable3 = scriptable;
        if (!idFunctionObject2.hasTag(STRING_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        Scriptable scriptable4 = scriptable2;
        Object[] objArr2 = objArr;
        while (true) {
            CharSequence charSequence = "";
            boolean z2 = true;
            int i = 0;
            if (methodId == -2) {
                int length = objArr2.length;
                if (length < 1) {
                    return charSequence;
                }
                int[] iArr = new int[length];
                for (int i2 = 0; i2 != length; i2++) {
                    Object obj = objArr2[i2];
                    int int32 = ScriptRuntime.toInt32(obj);
                    if (!ScriptRuntime.eqNumber(ScriptRuntime.toNumber(obj), Integer.valueOf(int32)) || !Character.isValidCodePoint(int32)) {
                        throw ScriptRuntime.rangeError("Invalid code point " + ScriptRuntime.toString(obj));
                    }
                    iArr[i2] = int32;
                }
                return new String(iArr, 0, length);
            } else if (methodId != -1) {
                switch (methodId) {
                    case ConstructorId_toLocaleLowerCase /*-35*/:
                    case ConstructorId_localeCompare /*-34*/:
                    case ConstructorId_replace /*-33*/:
                    case ConstructorId_search /*-32*/:
                    case ConstructorId_match /*-31*/:
                    case ConstructorId_equalsIgnoreCase /*-30*/:
                        break;
                    default:
                        switch (methodId) {
                            case ConstructorId_slice /*-15*/:
                            case ConstructorId_concat /*-14*/:
                            case ConstructorId_substr /*-13*/:
                            case ConstructorId_toUpperCase /*-12*/:
                            case ConstructorId_toLowerCase /*-11*/:
                            case ConstructorId_substring /*-10*/:
                            case ConstructorId_split /*-9*/:
                            case ConstructorId_lastIndexOf /*-8*/:
                            case ConstructorId_indexOf /*-7*/:
                            case ConstructorId_charCodeAt /*-6*/:
                            case ConstructorId_charAt /*-5*/:
                                break;
                            default:
                                int i3 = 3;
                                switch (methodId) {
                                    case 1:
                                        if (objArr2.length != 0) {
                                            if (!ScriptRuntime.isSymbol(objArr2[0]) || scriptable4 == null) {
                                                charSequence = ScriptRuntime.toCharSequence(objArr2[0]);
                                            } else {
                                                charSequence = objArr2[0].toString();
                                            }
                                        }
                                        if (scriptable4 == null) {
                                            return new NativeString(charSequence);
                                        }
                                        if (charSequence instanceof String) {
                                            return charSequence;
                                        }
                                        return charSequence.toString();
                                    case 2:
                                    case 4:
                                        CharSequence charSequence2 = realThis(scriptable4, idFunctionObject2).string;
                                        if (charSequence2 instanceof String) {
                                            return charSequence2;
                                        }
                                        return charSequence2.toString();
                                    case 3:
                                        CharSequence charSequence3 = realThis(scriptable4, idFunctionObject2).string;
                                        return "(new String(\"" + ScriptRuntime.escapeString(charSequence3.toString()) + "\"))";
                                    case 5:
                                    case 6:
                                        CharSequence charSequence4 = ScriptRuntime.toCharSequence(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2));
                                        double integer = ScriptRuntime.toInteger(objArr2, 0);
                                        if (integer >= 0.0d && integer < ((double) charSequence4.length())) {
                                            char charAt = charSequence4.charAt((int) integer);
                                            if (methodId == 5) {
                                                return String.valueOf(charAt);
                                            }
                                            return ScriptRuntime.wrapInt(charAt);
                                        } else if (methodId == 5) {
                                            return charSequence;
                                        } else {
                                            return ScriptRuntime.NaNobj;
                                        }
                                    case 7:
                                        return ScriptRuntime.wrapInt(js_indexOf(7, ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)), objArr2));
                                    case 8:
                                        return ScriptRuntime.wrapInt(js_lastIndexOf(ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)), objArr2));
                                    case 9:
                                        return ScriptRuntime.checkRegExpProxy(context).js_split(context2, scriptable3, ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)), objArr2);
                                    case 10:
                                        return js_substring(context2, ScriptRuntime.toCharSequence(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)), objArr2);
                                    case 11:
                                        return ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)).toLowerCase(Locale.ROOT);
                                    case 12:
                                        return ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)).toUpperCase(Locale.ROOT);
                                    case 13:
                                        return js_substr(ScriptRuntime.toCharSequence(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)), objArr2);
                                    case 14:
                                        return js_concat(ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)), objArr2);
                                    case 15:
                                        return js_slice(ScriptRuntime.toCharSequence(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)), objArr2);
                                    case 16:
                                        return tagify(scriptable4, "b", (String) null, (Object[]) null);
                                    case 17:
                                        return tagify(scriptable4, "i", (String) null, (Object[]) null);
                                    case 18:
                                        return tagify(scriptable4, "tt", (String) null, (Object[]) null);
                                    case 19:
                                        return tagify(scriptable4, "strike", (String) null, (Object[]) null);
                                    case 20:
                                        return tagify(scriptable4, "small", (String) null, (Object[]) null);
                                    case 21:
                                        return tagify(scriptable4, "big", (String) null, (Object[]) null);
                                    case 22:
                                        return tagify(scriptable4, "blink", (String) null, (Object[]) null);
                                    case 23:
                                        return tagify(scriptable4, "sup", (String) null, (Object[]) null);
                                    case 24:
                                        return tagify(scriptable4, "sub", (String) null, (Object[]) null);
                                    case 25:
                                        return tagify(scriptable4, "font", "size", objArr2);
                                    case 26:
                                        return tagify(scriptable4, "font", "color", objArr2);
                                    case 27:
                                        return tagify(scriptable4, "a", "href", objArr2);
                                    case 28:
                                        return tagify(scriptable4, "a", "name", objArr2);
                                    case 29:
                                    case 30:
                                        String scriptRuntime = ScriptRuntime.toString((Object) scriptable4);
                                        String scriptRuntime2 = ScriptRuntime.toString(objArr2, 0);
                                        if (methodId == 29) {
                                            z = scriptRuntime.equals(scriptRuntime2);
                                        } else {
                                            z = scriptRuntime.equalsIgnoreCase(scriptRuntime2);
                                        }
                                        return ScriptRuntime.wrapBoolean(z);
                                    case 31:
                                    case 32:
                                    case 33:
                                        if (methodId == 31) {
                                            i3 = 1;
                                        } else if (methodId != 32) {
                                            i3 = 2;
                                        }
                                        ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2);
                                        return ScriptRuntime.checkRegExpProxy(context).action(context, scriptable, scriptable4, objArr2, i3);
                                    case 34:
                                        String scriptRuntime3 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2));
                                        Collator instance = Collator.getInstance(context.getLocale());
                                        instance.setStrength(3);
                                        instance.setDecomposition(1);
                                        return ScriptRuntime.wrapNumber((double) instance.compare(scriptRuntime3, ScriptRuntime.toString(objArr2, 0)));
                                    case 35:
                                        return ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)).toLowerCase(context.getLocale());
                                    case 36:
                                        return ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)).toUpperCase(context.getLocale());
                                    case 37:
                                        String scriptRuntime4 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2));
                                        char[] charArray = scriptRuntime4.toCharArray();
                                        while (i < charArray.length && ScriptRuntime.isJSWhitespaceOrLineTerminator(charArray[i])) {
                                            i++;
                                        }
                                        int length2 = charArray.length;
                                        while (length2 > i && ScriptRuntime.isJSWhitespaceOrLineTerminator(charArray[length2 - 1])) {
                                            length2--;
                                        }
                                        return scriptRuntime4.substring(i, length2);
                                    case 38:
                                    case 49:
                                        String scriptRuntime5 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2));
                                        char[] charArray2 = scriptRuntime5.toCharArray();
                                        while (i < charArray2.length && ScriptRuntime.isJSWhitespaceOrLineTerminator(charArray2[i])) {
                                            i++;
                                        }
                                        return scriptRuntime5.substring(i, charArray2.length);
                                    case 39:
                                    case 50:
                                        String scriptRuntime6 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2));
                                        char[] charArray3 = scriptRuntime6.toCharArray();
                                        int length3 = charArray3.length;
                                        while (length3 > 0 && ScriptRuntime.isJSWhitespaceOrLineTerminator(charArray3[length3 - 1])) {
                                            length3--;
                                        }
                                        return scriptRuntime6.substring(0, length3);
                                    case 40:
                                    case 41:
                                    case 42:
                                        String scriptRuntime7 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2));
                                        if (objArr2.length <= 0 || !(objArr2[0] instanceof NativeRegExp)) {
                                            int js_indexOf = js_indexOf(methodId, scriptRuntime7, objArr2);
                                            if (methodId == 40) {
                                                if (js_indexOf == -1) {
                                                    z2 = false;
                                                }
                                                return Boolean.valueOf(z2);
                                            } else if (methodId == 41) {
                                                if (js_indexOf != 0) {
                                                    z2 = false;
                                                }
                                                return Boolean.valueOf(z2);
                                            } else if (methodId == 42) {
                                                if (js_indexOf == -1) {
                                                    z2 = false;
                                                }
                                                return Boolean.valueOf(z2);
                                            }
                                        } else {
                                            throw ScriptRuntime.typeError2("msg.first.arg.not.regexp", String.class.getSimpleName(), idFunctionObject.getFunctionName());
                                        }
                                        break;
                                    case 43:
                                        if (objArr2.length == 0 || Undefined.isUndefined(objArr2[0])) {
                                            return Normalizer.normalize(ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)), Normalizer.Form.NFC);
                                        }
                                        String scriptRuntime8 = ScriptRuntime.toString(objArr2, 0);
                                        if (Normalizer.Form.NFD.name().equals(scriptRuntime8)) {
                                            form = Normalizer.Form.NFD;
                                        } else if (Normalizer.Form.NFKC.name().equals(scriptRuntime8)) {
                                            form = Normalizer.Form.NFKC;
                                        } else if (Normalizer.Form.NFKD.name().equals(scriptRuntime8)) {
                                            form = Normalizer.Form.NFKD;
                                        } else if (Normalizer.Form.NFC.name().equals(scriptRuntime8)) {
                                            form = Normalizer.Form.NFC;
                                        } else {
                                            throw ScriptRuntime.rangeError("The normalization form should be one of 'NFC', 'NFD', 'NFKC', 'NFKD'.");
                                        }
                                        return Normalizer.normalize(ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2)), form);
                                    case 44:
                                        return js_repeat(context2, scriptable4, idFunctionObject2, objArr2);
                                    case 45:
                                        String scriptRuntime9 = ScriptRuntime.toString(ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2));
                                        double integer2 = ScriptRuntime.toInteger(objArr2, 0);
                                        if (integer2 < 0.0d || integer2 >= ((double) scriptRuntime9.length())) {
                                            return Undefined.instance;
                                        }
                                        return Integer.valueOf(scriptRuntime9.codePointAt((int) integer2));
                                    case 46:
                                    case 47:
                                        break;
                                    case 48:
                                        return new NativeStringIterator(scriptable3, ScriptRuntimeES6.requireObjectCoercible(context2, scriptable4, idFunctionObject2));
                                    default:
                                        throw new IllegalArgumentException("String.prototype has no method: " + idFunctionObject.getFunctionName());
                                }
                                if (methodId != 46) {
                                    z2 = false;
                                }
                                return js_pad(context2, scriptable4, idFunctionObject2, objArr2, z2);
                        }
                }
                if (objArr2.length > 0) {
                    scriptable4 = ScriptRuntime.toObject(context2, scriptable3, (Object) ScriptRuntime.toCharSequence(objArr2[0]));
                    int length4 = objArr2.length - 1;
                    Object[] objArr3 = new Object[length4];
                    while (i < length4) {
                        int i4 = i + 1;
                        objArr3[i] = objArr2[i4];
                        i = i4;
                    }
                    objArr2 = objArr3;
                } else {
                    scriptable4 = ScriptRuntime.toObject(context2, scriptable3, (Object) ScriptRuntime.toCharSequence(scriptable4));
                }
                methodId = -methodId;
            } else {
                int length5 = objArr2.length;
                if (length5 < 1) {
                    return charSequence;
                }
                char[] cArr = new char[length5];
                while (i != length5) {
                    cArr[i] = ScriptRuntime.toUint16(objArr2[i]);
                    i++;
                }
                return new String(cArr);
            }
        }
    }

    public void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        IdFunctionObject idFunctionObject2 = idFunctionObject;
        Object obj = STRING_TAG;
        addIdFunctionProperty(idFunctionObject2, obj, -1, "fromCharCode", 1);
        addIdFunctionProperty(idFunctionObject2, obj, -2, "fromCodePoint", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_charAt, "charAt", 2);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_charCodeAt, "charCodeAt", 2);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_indexOf, "indexOf", 2);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_lastIndexOf, "lastIndexOf", 2);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_split, "split", 3);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_substring, "substring", 3);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_toLowerCase, "toLowerCase", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_toUpperCase, "toUpperCase", 1);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_substr, "substr", 3);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_concat, "concat", 2);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_slice, "slice", 3);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_equalsIgnoreCase, "equalsIgnoreCase", 2);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_match, "match", 2);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_search, "search", 2);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_replace, "replace", 2);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_localeCompare, "localeCompare", 2);
        addIdFunctionProperty(idFunctionObject2, obj, ConstructorId_toLocaleLowerCase, "toLocaleLowerCase", 1);
        super.fillConstructorProperties(idFunctionObject);
    }

    public int findInstanceIdInfo(String str) {
        if (str.equals("length")) {
            return IdScriptableObject.instanceIdInfo(7, 1);
        }
        return super.findInstanceIdInfo(str);
    }

    public int findPrototypeId(Symbol symbol) {
        return SymbolKey.ITERATOR.equals(symbol) ? 48 : 0;
    }

    public Object get(int i, Scriptable scriptable) {
        if (i < 0 || i >= this.string.length()) {
            return super.get(i, scriptable);
        }
        return String.valueOf(this.string.charAt(i));
    }

    public int getAttributes(int i) {
        if (i < 0 || i >= this.string.length()) {
            return super.getAttributes(i);
        }
        if (Context.getContext().getLanguageVersion() < 200) {
            return 7;
        }
        return 5;
    }

    public String getClassName() {
        return "String";
    }

    public Object[] getIds(boolean z, boolean z2) {
        Context currentContext = Context.getCurrentContext();
        if (currentContext == null || currentContext.getLanguageVersion() < 200) {
            return super.getIds(z, z2);
        }
        Object[] ids = super.getIds(z, z2);
        Object[] objArr = new Object[(this.string.length() + ids.length)];
        int i = 0;
        while (i < this.string.length()) {
            objArr[i] = Integer.valueOf(i);
            i++;
        }
        System.arraycopy(ids, 0, objArr, i, ids.length);
        return objArr;
    }

    public String getInstanceIdName(int i) {
        if (i == 1) {
            return "length";
        }
        return super.getInstanceIdName(i);
    }

    public Object getInstanceIdValue(int i) {
        if (i == 1) {
            return ScriptRuntime.wrapInt(this.string.length());
        }
        return super.getInstanceIdValue(i);
    }

    public int getLength() {
        return this.string.length();
    }

    public int getMaxInstanceId() {
        return 1;
    }

    public ScriptableObject getOwnPropertyDescriptor(Context context, Object obj) {
        int i;
        if (!(obj instanceof Symbol) && context != null && context.getLanguageVersion() >= 200) {
            ScriptRuntime.StringIdOrIndex stringIdOrIndex = ScriptRuntime.toStringIdOrIndex(context, obj);
            if (stringIdOrIndex.stringId == null && (i = stringIdOrIndex.index) >= 0 && i < this.string.length()) {
                return defaultIndexPropertyDescriptor(String.valueOf(this.string.charAt(stringIdOrIndex.index)));
            }
        }
        return super.getOwnPropertyDescriptor(context, obj);
    }

    public boolean has(int i, Scriptable scriptable) {
        if (i < 0 || i >= this.string.length()) {
            return super.has(i, scriptable);
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b2, code lost:
        r10 = r1;
        r12 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00c9, code lost:
        r10 = r0;
        r12 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00ce, code lost:
        r10 = r0;
        r12 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00d0, code lost:
        initPrototypeMethod(STRING_TAG, r14, r10, (java.lang.String) null, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00d7, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initPrototypeId(int r14) {
        /*
            r13 = this;
            r0 = 48
            if (r14 != r0) goto L_0x0011
            java.lang.Object r2 = STRING_TAG
            org.mozilla.javascript.SymbolKey r4 = org.mozilla.javascript.SymbolKey.ITERATOR
            java.lang.String r5 = "[Symbol.iterator]"
            r6 = 0
            r1 = r13
            r3 = r14
            r1.initPrototypeMethod((java.lang.Object) r2, (int) r3, (org.mozilla.javascript.Symbol) r4, (java.lang.String) r5, (int) r6)
            return
        L_0x0011:
            r11 = 0
            r0 = 2
            r1 = 1
            r2 = 0
            switch(r14) {
                case 1: goto L_0x00cc;
                case 2: goto L_0x00c7;
                case 3: goto L_0x00c4;
                case 4: goto L_0x00c1;
                case 5: goto L_0x00be;
                case 6: goto L_0x00bb;
                case 7: goto L_0x00b8;
                case 8: goto L_0x00b5;
                case 9: goto L_0x00b0;
                case 10: goto L_0x00ad;
                case 11: goto L_0x00aa;
                case 12: goto L_0x00a7;
                case 13: goto L_0x00a4;
                case 14: goto L_0x00a1;
                case 15: goto L_0x009e;
                case 16: goto L_0x009b;
                case 17: goto L_0x0098;
                case 18: goto L_0x0095;
                case 19: goto L_0x0092;
                case 20: goto L_0x008f;
                case 21: goto L_0x008c;
                case 22: goto L_0x0089;
                case 23: goto L_0x0086;
                case 24: goto L_0x0083;
                case 25: goto L_0x0080;
                case 26: goto L_0x007d;
                case 27: goto L_0x0079;
                case 28: goto L_0x0075;
                case 29: goto L_0x0071;
                case 30: goto L_0x006d;
                case 31: goto L_0x0069;
                case 32: goto L_0x0065;
                case 33: goto L_0x0062;
                case 34: goto L_0x005e;
                case 35: goto L_0x005a;
                case 36: goto L_0x0056;
                case 37: goto L_0x0052;
                case 38: goto L_0x004e;
                case 39: goto L_0x004a;
                case 40: goto L_0x0046;
                case 41: goto L_0x0042;
                case 42: goto L_0x003e;
                case 43: goto L_0x003a;
                case 44: goto L_0x0036;
                case 45: goto L_0x0032;
                case 46: goto L_0x002e;
                case 47: goto L_0x002a;
                case 48: goto L_0x0018;
                case 49: goto L_0x0026;
                case 50: goto L_0x0022;
                default: goto L_0x0018;
            }
        L_0x0018:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r14 = java.lang.String.valueOf(r14)
            r0.<init>(r14)
            throw r0
        L_0x0022:
            java.lang.String r0 = "trimEnd"
            goto L_0x00c9
        L_0x0026:
            java.lang.String r0 = "trimStart"
            goto L_0x00c9
        L_0x002a:
            java.lang.String r0 = "padEnd"
            goto L_0x00ce
        L_0x002e:
            java.lang.String r0 = "padStart"
            goto L_0x00ce
        L_0x0032:
            java.lang.String r0 = "codePointAt"
            goto L_0x00ce
        L_0x0036:
            java.lang.String r0 = "repeat"
            goto L_0x00ce
        L_0x003a:
            java.lang.String r0 = "normalize"
            goto L_0x00c9
        L_0x003e:
            java.lang.String r0 = "endsWith"
            goto L_0x00ce
        L_0x0042:
            java.lang.String r0 = "startsWith"
            goto L_0x00ce
        L_0x0046:
            java.lang.String r0 = "includes"
            goto L_0x00ce
        L_0x004a:
            java.lang.String r0 = "trimRight"
            goto L_0x00c9
        L_0x004e:
            java.lang.String r0 = "trimLeft"
            goto L_0x00c9
        L_0x0052:
            java.lang.String r0 = "trim"
            goto L_0x00c9
        L_0x0056:
            java.lang.String r0 = "toLocaleUpperCase"
            goto L_0x00c9
        L_0x005a:
            java.lang.String r0 = "toLocaleLowerCase"
            goto L_0x00c9
        L_0x005e:
            java.lang.String r0 = "localeCompare"
            goto L_0x00ce
        L_0x0062:
            java.lang.String r1 = "replace"
            goto L_0x00b2
        L_0x0065:
            java.lang.String r0 = "search"
            goto L_0x00ce
        L_0x0069:
            java.lang.String r0 = "match"
            goto L_0x00ce
        L_0x006d:
            java.lang.String r0 = "equalsIgnoreCase"
            goto L_0x00ce
        L_0x0071:
            java.lang.String r0 = "equals"
            goto L_0x00ce
        L_0x0075:
            java.lang.String r0 = "anchor"
            goto L_0x00c9
        L_0x0079:
            java.lang.String r0 = "link"
            goto L_0x00c9
        L_0x007d:
            java.lang.String r0 = "fontcolor"
            goto L_0x00c9
        L_0x0080:
            java.lang.String r0 = "fontsize"
            goto L_0x00c9
        L_0x0083:
            java.lang.String r0 = "sub"
            goto L_0x00c9
        L_0x0086:
            java.lang.String r0 = "sup"
            goto L_0x00c9
        L_0x0089:
            java.lang.String r0 = "blink"
            goto L_0x00c9
        L_0x008c:
            java.lang.String r0 = "big"
            goto L_0x00c9
        L_0x008f:
            java.lang.String r0 = "small"
            goto L_0x00c9
        L_0x0092:
            java.lang.String r0 = "strike"
            goto L_0x00c9
        L_0x0095:
            java.lang.String r0 = "fixed"
            goto L_0x00c9
        L_0x0098:
            java.lang.String r0 = "italics"
            goto L_0x00c9
        L_0x009b:
            java.lang.String r0 = "bold"
            goto L_0x00c9
        L_0x009e:
            java.lang.String r1 = "slice"
            goto L_0x00b2
        L_0x00a1:
            java.lang.String r0 = "concat"
            goto L_0x00ce
        L_0x00a4:
            java.lang.String r1 = "substr"
            goto L_0x00b2
        L_0x00a7:
            java.lang.String r0 = "toUpperCase"
            goto L_0x00c9
        L_0x00aa:
            java.lang.String r0 = "toLowerCase"
            goto L_0x00c9
        L_0x00ad:
            java.lang.String r1 = "substring"
            goto L_0x00b2
        L_0x00b0:
            java.lang.String r1 = "split"
        L_0x00b2:
            r10 = r1
            r12 = 2
            goto L_0x00d0
        L_0x00b5:
            java.lang.String r0 = "lastIndexOf"
            goto L_0x00ce
        L_0x00b8:
            java.lang.String r0 = "indexOf"
            goto L_0x00ce
        L_0x00bb:
            java.lang.String r0 = "charCodeAt"
            goto L_0x00ce
        L_0x00be:
            java.lang.String r0 = "charAt"
            goto L_0x00ce
        L_0x00c1:
            java.lang.String r0 = "valueOf"
            goto L_0x00c9
        L_0x00c4:
            java.lang.String r0 = "toSource"
            goto L_0x00c9
        L_0x00c7:
            java.lang.String r0 = "toString"
        L_0x00c9:
            r10 = r0
            r12 = 0
            goto L_0x00d0
        L_0x00cc:
            java.lang.String r0 = "constructor"
        L_0x00ce:
            r10 = r0
            r12 = 1
        L_0x00d0:
            java.lang.Object r8 = STRING_TAG
            r7 = r13
            r9 = r14
            r7.initPrototypeMethod((java.lang.Object) r8, (int) r9, (java.lang.String) r10, (java.lang.String) r11, (int) r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeString.initPrototypeId(int):void");
    }

    public void put(int i, Scriptable scriptable, Object obj) {
        if (i < 0 || i >= this.string.length()) {
            super.put(i, scriptable, obj);
        }
    }

    public CharSequence toCharSequence() {
        return this.string;
    }

    public String toString() {
        CharSequence charSequence = this.string;
        return charSequence instanceof String ? (String) charSequence : charSequence.toString();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r17) {
        /*
            r16 = this;
            r0 = r17
            int r1 = r17.length()
            r2 = 8
            r3 = 85
            r4 = 76
            r5 = 6
            r8 = 100
            r9 = 99
            r10 = 114(0x72, float:1.6E-43)
            r12 = 97
            r13 = 4
            r15 = 2
            r14 = 110(0x6e, float:1.54E-43)
            r11 = 115(0x73, float:1.61E-43)
            r7 = 116(0x74, float:1.63E-43)
            r6 = 0
            switch(r1) {
                case 3: goto L_0x01dd;
                case 4: goto L_0x01c0;
                case 5: goto L_0x0182;
                case 6: goto L_0x0124;
                case 7: goto L_0x00f5;
                case 8: goto L_0x00b3;
                case 9: goto L_0x0081;
                case 10: goto L_0x006e;
                case 11: goto L_0x0043;
                case 12: goto L_0x0021;
                case 13: goto L_0x003d;
                case 14: goto L_0x0021;
                case 15: goto L_0x0021;
                case 16: goto L_0x0037;
                case 17: goto L_0x0023;
                default: goto L_0x0021;
            }
        L_0x0021:
            goto L_0x0224
        L_0x0023:
            char r1 = r0.charAt(r2)
            if (r1 != r4) goto L_0x002f
            java.lang.String r1 = "toLocaleLowerCase"
            r2 = 35
            goto L_0x0226
        L_0x002f:
            if (r1 != r3) goto L_0x0224
            java.lang.String r1 = "toLocaleUpperCase"
            r2 = 36
            goto L_0x0226
        L_0x0037:
            java.lang.String r1 = "equalsIgnoreCase"
            r2 = 30
            goto L_0x0226
        L_0x003d:
            java.lang.String r1 = "localeCompare"
            r2 = 34
            goto L_0x0226
        L_0x0043:
            char r1 = r0.charAt(r15)
            if (r1 == r4) goto L_0x0068
            if (r1 == r3) goto L_0x0062
            if (r1 == r8) goto L_0x005c
            if (r1 == r14) goto L_0x0057
            if (r1 == r11) goto L_0x0053
            goto L_0x0224
        L_0x0053:
            java.lang.String r1 = "lastIndexOf"
            goto L_0x0226
        L_0x0057:
            java.lang.String r1 = "constructor"
            r2 = 1
            goto L_0x0226
        L_0x005c:
            java.lang.String r1 = "codePointAt"
            r2 = 45
            goto L_0x0226
        L_0x0062:
            java.lang.String r1 = "toUpperCase"
            r2 = 12
            goto L_0x0226
        L_0x0068:
            java.lang.String r1 = "toLowerCase"
            r2 = 11
            goto L_0x0226
        L_0x006e:
            char r1 = r0.charAt(r6)
            if (r1 != r9) goto L_0x0079
            java.lang.String r1 = "charCodeAt"
            r2 = 6
            goto L_0x0226
        L_0x0079:
            if (r1 != r11) goto L_0x0224
            java.lang.String r1 = "startsWith"
            r2 = 41
            goto L_0x0226
        L_0x0081:
            char r1 = r0.charAt(r13)
            r2 = 82
            if (r1 == r2) goto L_0x00ad
            r2 = 83
            if (r1 == r2) goto L_0x00a7
            if (r1 == r12) goto L_0x00a1
            if (r1 == r9) goto L_0x009b
            if (r1 == r7) goto L_0x0095
            goto L_0x0224
        L_0x0095:
            java.lang.String r1 = "substring"
            r2 = 10
            goto L_0x0226
        L_0x009b:
            java.lang.String r1 = "fontcolor"
            r2 = 26
            goto L_0x0226
        L_0x00a1:
            java.lang.String r1 = "normalize"
            r2 = 43
            goto L_0x0226
        L_0x00a7:
            java.lang.String r1 = "trimStart"
            r2 = 49
            goto L_0x0226
        L_0x00ad:
            java.lang.String r1 = "trimRight"
            r2 = 39
            goto L_0x0226
        L_0x00b3:
            char r1 = r0.charAt(r5)
            if (r1 == r9) goto L_0x00f0
            if (r1 == r14) goto L_0x00eb
            if (r1 == r10) goto L_0x00e5
            if (r1 == r7) goto L_0x00df
            r2 = 122(0x7a, float:1.71E-43)
            if (r1 == r2) goto L_0x00d9
            r2 = 101(0x65, float:1.42E-43)
            if (r1 == r2) goto L_0x00d3
            r2 = 102(0x66, float:1.43E-43)
            if (r1 == r2) goto L_0x00cd
            goto L_0x0224
        L_0x00cd:
            java.lang.String r1 = "trimLeft"
            r2 = 38
            goto L_0x0226
        L_0x00d3:
            java.lang.String r1 = "includes"
            r2 = 40
            goto L_0x0226
        L_0x00d9:
            java.lang.String r1 = "fontsize"
            r2 = 25
            goto L_0x0226
        L_0x00df:
            java.lang.String r1 = "endsWith"
            r2 = 42
            goto L_0x0226
        L_0x00e5:
            java.lang.String r1 = "padStart"
            r2 = 46
            goto L_0x0226
        L_0x00eb:
            java.lang.String r1 = "toString"
            r2 = 2
            goto L_0x0226
        L_0x00f0:
            java.lang.String r1 = "toSource"
            r2 = 3
            goto L_0x0226
        L_0x00f5:
            r1 = 1
            char r1 = r0.charAt(r1)
            if (r1 == r12) goto L_0x011f
            r2 = 101(0x65, float:1.42E-43)
            if (r1 == r2) goto L_0x0119
            if (r1 == r14) goto L_0x0114
            if (r1 == r10) goto L_0x010e
            if (r1 == r7) goto L_0x0108
            goto L_0x0224
        L_0x0108:
            java.lang.String r1 = "italics"
            r2 = 17
            goto L_0x0226
        L_0x010e:
            java.lang.String r1 = "trimEnd"
            r2 = 50
            goto L_0x0226
        L_0x0114:
            java.lang.String r1 = "indexOf"
            r2 = 7
            goto L_0x0226
        L_0x0119:
            java.lang.String r1 = "replace"
            r2 = 33
            goto L_0x0226
        L_0x011f:
            java.lang.String r1 = "valueOf"
            r2 = 4
            goto L_0x0226
        L_0x0124:
            r1 = 1
            char r1 = r0.charAt(r1)
            if (r1 == r12) goto L_0x017c
            r2 = 101(0x65, float:1.42E-43)
            if (r1 == r2) goto L_0x0168
            r2 = 104(0x68, float:1.46E-43)
            if (r1 == r2) goto L_0x0163
            r2 = 113(0x71, float:1.58E-43)
            if (r1 == r2) goto L_0x015d
            if (r1 == r14) goto L_0x0157
            r2 = 111(0x6f, float:1.56E-43)
            if (r1 == r2) goto L_0x0151
            if (r1 == r7) goto L_0x014b
            r2 = 117(0x75, float:1.64E-43)
            if (r1 == r2) goto L_0x0145
            goto L_0x0224
        L_0x0145:
            java.lang.String r1 = "substr"
            r2 = 13
            goto L_0x0226
        L_0x014b:
            java.lang.String r1 = "strike"
            r2 = 19
            goto L_0x0226
        L_0x0151:
            java.lang.String r1 = "concat"
            r2 = 14
            goto L_0x0226
        L_0x0157:
            java.lang.String r1 = "anchor"
            r2 = 28
            goto L_0x0226
        L_0x015d:
            java.lang.String r1 = "equals"
            r2 = 29
            goto L_0x0226
        L_0x0163:
            java.lang.String r1 = "charAt"
            r2 = 5
            goto L_0x0226
        L_0x0168:
            char r1 = r0.charAt(r6)
            if (r1 != r10) goto L_0x0174
            java.lang.String r1 = "repeat"
            r2 = 44
            goto L_0x0226
        L_0x0174:
            if (r1 != r11) goto L_0x0224
            java.lang.String r1 = "search"
            r2 = 32
            goto L_0x0226
        L_0x017c:
            java.lang.String r1 = "padEnd"
            r2 = 47
            goto L_0x0226
        L_0x0182:
            char r1 = r0.charAt(r13)
            if (r1 == r8) goto L_0x01ba
            r2 = 101(0x65, float:1.42E-43)
            if (r1 == r2) goto L_0x01b4
            r2 = 104(0x68, float:1.46E-43)
            if (r1 == r2) goto L_0x01ae
            if (r1 == r7) goto L_0x01a8
            r2 = 107(0x6b, float:1.5E-43)
            if (r1 == r2) goto L_0x01a2
            r2 = 108(0x6c, float:1.51E-43)
            if (r1 == r2) goto L_0x019c
            goto L_0x0224
        L_0x019c:
            java.lang.String r1 = "small"
            r2 = 20
            goto L_0x0226
        L_0x01a2:
            java.lang.String r1 = "blink"
            r2 = 22
            goto L_0x0226
        L_0x01a8:
            java.lang.String r1 = "split"
            r2 = 9
            goto L_0x0226
        L_0x01ae:
            java.lang.String r1 = "match"
            r2 = 31
            goto L_0x0226
        L_0x01b4:
            java.lang.String r1 = "slice"
            r2 = 15
            goto L_0x0226
        L_0x01ba:
            java.lang.String r1 = "fixed"
            r2 = 18
            goto L_0x0226
        L_0x01c0:
            char r1 = r0.charAt(r6)
            r2 = 98
            if (r1 != r2) goto L_0x01cd
            java.lang.String r1 = "bold"
            r2 = 16
            goto L_0x0226
        L_0x01cd:
            r2 = 108(0x6c, float:1.51E-43)
            if (r1 != r2) goto L_0x01d6
            java.lang.String r1 = "link"
            r2 = 27
            goto L_0x0226
        L_0x01d6:
            if (r1 != r7) goto L_0x0224
            java.lang.String r1 = "trim"
            r2 = 37
            goto L_0x0226
        L_0x01dd:
            char r1 = r0.charAt(r15)
            r2 = 98
            if (r1 != r2) goto L_0x01f7
            char r1 = r0.charAt(r6)
            if (r1 != r11) goto L_0x0224
            r2 = 1
            char r1 = r0.charAt(r2)
            r2 = 117(0x75, float:1.64E-43)
            if (r1 != r2) goto L_0x0224
            r6 = 24
            goto L_0x0232
        L_0x01f7:
            r2 = 1
            r3 = 103(0x67, float:1.44E-43)
            if (r1 != r3) goto L_0x020f
            char r1 = r0.charAt(r6)
            r3 = 98
            if (r1 != r3) goto L_0x0224
            char r1 = r0.charAt(r2)
            r2 = 105(0x69, float:1.47E-43)
            if (r1 != r2) goto L_0x0224
            r6 = 21
            goto L_0x0232
        L_0x020f:
            r3 = 112(0x70, float:1.57E-43)
            if (r1 != r3) goto L_0x0224
            char r1 = r0.charAt(r6)
            if (r1 != r11) goto L_0x0224
            char r1 = r0.charAt(r2)
            r2 = 117(0x75, float:1.64E-43)
            if (r1 != r2) goto L_0x0224
            r6 = 23
            goto L_0x0232
        L_0x0224:
            r1 = 0
            r2 = 0
        L_0x0226:
            if (r1 == 0) goto L_0x0231
            if (r1 == r0) goto L_0x0231
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x0231
            goto L_0x0232
        L_0x0231:
            r6 = r2
        L_0x0232:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeString.findPrototypeId(java.lang.String):int");
    }
}
