package defpackage;

import androidx.core.internal.view.SupportMenu;
import j$.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cg  reason: default package */
public final class cg implements ILoggerFactory, d0 {
    public static final byte[] a = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

    public static final int b(int i) {
        return ((i & SupportMenu.USER_MASK) << 8) | 7;
    }

    public static String c(String str, String str2) {
        if (!str2.trim().isEmpty()) {
            String lowerCase = str.trim().toLowerCase();
            String trim = str2.trim();
            try {
                if ("c".equals(lowerCase)) {
                    int parseInt = Integer.parseInt(trim);
                    if (parseInt <= 0 || parseInt > 30) {
                        return "https://gemini.truongblack.me/v1/chat/completions/search?q=news";
                    }
                    return "https://gemini.truongblack.me/v1/chat/completions/search?c=" + parseInt;
                } else if ("chuyensau".equals(lowerCase)) {
                    String encode = URLEncoder.encode(trim, "UTF-8");
                    return "https://gemini.truongblack.me/v1/chat/completions/search?chuyensau=" + encode;
                } else if ("q".equals(lowerCase)) {
                    String encode2 = URLEncoder.encode(trim, "UTF-8");
                    return "https://gemini.truongblack.me/v1/chat/completions/search?q=" + encode2;
                }
            } catch (UnsupportedEncodingException | NumberFormatException unused) {
            }
        }
        return "https://gemini.truongblack.me/v1/chat/completions/search?q=news";
    }

    public static void d(boolean z, String str, int i, int i2) {
        if (!z) {
            throw new ArithmeticException("overflow: " + str + "(" + i + ", " + i2 + ")");
        }
    }

    public static void e(int i, String str) {
        if (i < 0) {
            throw new IllegalArgumentException(str + " (" + i + ") must be >= 0");
        }
    }

    public static JSONObject f(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("success", false);
            jSONObject.put("message", str);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    public static String g(cp cpVar) {
        StringBuilder sb = new StringBuilder(cpVar.size());
        for (int i = 0; i < cpVar.size(); i++) {
            byte a2 = cpVar.a(i);
            if (a2 == 34) {
                sb.append("\\\"");
            } else if (a2 == 39) {
                sb.append("\\'");
            } else if (a2 != 92) {
                switch (a2) {
                    case 7:
                        sb.append("\\a");
                        break;
                    case 8:
                        sb.append("\\b");
                        break;
                    case 9:
                        sb.append("\\t");
                        break;
                    case 10:
                        sb.append("\\n");
                        break;
                    case 11:
                        sb.append("\\v");
                        break;
                    case 12:
                        sb.append("\\f");
                        break;
                    case 13:
                        sb.append("\\r");
                        break;
                    default:
                        if (a2 >= 32 && a2 <= 126) {
                            sb.append((char) a2);
                            break;
                        } else {
                            sb.append('\\');
                            sb.append((char) (((a2 >>> 6) & 3) + 48));
                            sb.append((char) (((a2 >>> 3) & 7) + 48));
                            sb.append((char) ((a2 & 7) + 48));
                            break;
                        }
                        break;
                }
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }

    public static String h(String str) {
        int indexOf;
        try {
            for (String trim : str.split("\n")) {
                String trim2 = trim.trim();
                if (trim2.matches("^\\d+\\.\\s*\\{.*") && (indexOf = trim2.indexOf("{")) >= 0) {
                    try {
                        String optString = new JSONObject(trim2.substring(indexOf)).optString("text", "");
                        if (!optString.isEmpty()) {
                            return optString;
                        }
                    } catch (JSONException e) {
                        e.getMessage();
                    }
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static final int i(String str, s0 s0Var) {
        if (str.length() != 1) {
            return b(s0Var.a(str));
        }
        char charAt = str.charAt(0);
        if (charAt == 'F') {
            return 2;
        }
        if (!(charAt == 'S' || charAt == 'Z' || charAt == 'I')) {
            if (charAt == 'J') {
                return 4;
            }
            switch (charAt) {
                case 'B':
                case 'C':
                    break;
                case 'D':
                    return 3;
                default:
                    throw new IllegalArgumentException("bad type");
            }
        }
        return 1;
    }

    public static final String j(int i, s0 s0Var) {
        if ((i & 255) == 7) {
            return (String) s0Var.e(i >>> 8);
        }
        throw new IllegalArgumentException("expecting object type");
    }

    public static boolean k(int i) {
        return i == 3 || i == 4;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x0065 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONObject l(java.lang.String r7, boolean r8) {
        /*
            java.lang.String r0 = "Lỗi parse: "
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
            java.lang.String r2 = "message"
            java.lang.String r3 = "success"
            r4 = 0
            boolean r5 = r7.isEmpty()     // Catch:{ JSONException -> 0x004e }
            if (r5 != 0) goto L_0x0065
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x004e }
            r5.<init>(r7)     // Catch:{ JSONException -> 0x004e }
            java.lang.String r7 = "text"
            java.lang.String r6 = ""
            java.lang.String r7 = r5.optString(r7, r6)     // Catch:{ JSONException -> 0x004e }
            boolean r5 = r7.isEmpty()     // Catch:{ JSONException -> 0x004e }
            if (r5 == 0) goto L_0x002e
            r1.put(r3, r4)     // Catch:{ JSONException -> 0x004e }
            java.lang.String r7 = "Không có nội dung"
            r1.put(r2, r7)     // Catch:{ JSONException -> 0x004e }
            return r1
        L_0x002e:
            java.lang.String r5 = "content"
            r6 = 1
            if (r8 == 0) goto L_0x0047
            java.lang.String r8 = h(r7)     // Catch:{ JSONException -> 0x004e }
            if (r8 == 0) goto L_0x0040
            r1.put(r3, r6)     // Catch:{ JSONException -> 0x004e }
            r1.put(r5, r8)     // Catch:{ JSONException -> 0x004e }
            goto L_0x004d
        L_0x0040:
            r1.put(r3, r6)     // Catch:{ JSONException -> 0x004e }
            r1.put(r5, r7)     // Catch:{ JSONException -> 0x004e }
            goto L_0x004d
        L_0x0047:
            r1.put(r3, r6)     // Catch:{ JSONException -> 0x004e }
            r1.put(r5, r7)     // Catch:{ JSONException -> 0x004e }
        L_0x004d:
            return r1
        L_0x004e:
            r7 = move-exception
            r1.put(r3, r4)     // Catch:{ JSONException -> 0x0065 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0065 }
            r8.<init>(r0)     // Catch:{ JSONException -> 0x0065 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ JSONException -> 0x0065 }
            r8.append(r7)     // Catch:{ JSONException -> 0x0065 }
            java.lang.String r7 = r8.toString()     // Catch:{ JSONException -> 0x0065 }
            r1.put(r2, r7)     // Catch:{ JSONException -> 0x0065 }
        L_0x0065:
            r1.put(r3, r4)     // Catch:{ JSONException -> 0x006d }
            java.lang.String r7 = "Không có dữ liệu"
            r1.put(r2, r7)     // Catch:{ JSONException -> 0x006d }
        L_0x006d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cg.l(java.lang.String, boolean):org.json.JSONObject");
    }

    public static String m(int i, s0 s0Var) {
        int i2 = i & 255;
        switch (i2) {
            case 0:
                return "top";
            case 1:
                return "int";
            case 2:
                return "float";
            case 3:
                return "double";
            case 4:
                return "long";
            case 5:
                return "null";
            case 6:
                return "uninitialized_this";
            default:
                if (i2 == 7) {
                    return j(i, s0Var);
                }
                if (i2 == 8) {
                    return "uninitialized";
                }
                throw new IllegalArgumentException("bad type");
        }
    }

    public k4 a(String str) {
        return d7.c;
    }

    public void onError(Exception exc) {
    }

    public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
        Boolean bool = (Boolean) obj;
    }
}
