package j$.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.BitSet;

public class URLDecoder {
    static {
        BitSet bitSet = URLEncoder.a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00f4, code lost:
        r3.append(new java.lang.String(r5, 0, r7, r1));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r16, java.nio.charset.Charset r17) {
        /*
            r0 = r16
            r1 = r17
            java.lang.String r2 = "Charset"
            j$.util.Objects.requireNonNull(r1, r2)
            int r2 = r16.length()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r4 = 500(0x1f4, float:7.0E-43)
            if (r2 <= r4) goto L_0x0016
            int r4 = r2 / 2
            goto L_0x0017
        L_0x0016:
            r4 = r2
        L_0x0017:
            r3.<init>(r4)
            r5 = 0
            r6 = 0
            r7 = 0
        L_0x001d:
            if (r6 >= r2) goto L_0x0118
            char r8 = r0.charAt(r6)
            r10 = 37
            if (r8 == r10) goto L_0x003b
            r10 = 43
            if (r8 == r10) goto L_0x0031
            r3.append(r8)
            int r6 = r6 + 1
            goto L_0x001d
        L_0x0031:
            r7 = 32
            r3.append(r7)
            int r6 = r6 + 1
            r8 = 0
            goto L_0x00fd
        L_0x003b:
            if (r5 != 0) goto L_0x0047
            int r5 = r2 - r6
            int r5 = r5 / 3
            byte[] r5 = new byte[r5]     // Catch:{ NumberFormatException -> 0x0044 }
            goto L_0x0047
        L_0x0044:
            r0 = move-exception
            goto L_0x0100
        L_0x0047:
            r7 = 0
        L_0x0048:
            int r11 = r6 + 2
            if (r11 >= r2) goto L_0x00e5
            if (r8 != r10) goto L_0x00e5
            int r12 = r6 + 1
            char r13 = r0.charAt(r12)     // Catch:{ NumberFormatException -> 0x0044 }
            r15 = 102(0x66, float:1.43E-43)
            r9 = 65
            r4 = 57
            r10 = 97
            r14 = 48
            if (r14 > r13) goto L_0x0062
            if (r13 <= r4) goto L_0x006c
        L_0x0062:
            if (r10 > r13) goto L_0x0066
            if (r13 <= r15) goto L_0x006c
        L_0x0066:
            if (r9 > r13) goto L_0x006e
            r9 = 70
            if (r13 > r9) goto L_0x006e
        L_0x006c:
            r9 = 1
            goto L_0x006f
        L_0x006e:
            r9 = 0
        L_0x006f:
            if (r9 == 0) goto L_0x00c8
            char r9 = r0.charAt(r11)     // Catch:{ NumberFormatException -> 0x0044 }
            if (r14 > r9) goto L_0x0079
            if (r9 <= r4) goto L_0x0085
        L_0x0079:
            if (r10 > r9) goto L_0x007d
            if (r9 <= r15) goto L_0x0085
        L_0x007d:
            r4 = 65
            if (r4 > r9) goto L_0x0087
            r4 = 70
            if (r9 > r4) goto L_0x0087
        L_0x0085:
            r4 = 1
            goto L_0x0088
        L_0x0087:
            r4 = 0
        L_0x0088:
            if (r4 == 0) goto L_0x00c8
            int r4 = r6 + 3
            java.lang.CharSequence r9 = r0.subSequence(r12, r4)     // Catch:{ NumberFormatException -> 0x0044 }
            java.lang.String r9 = r9.toString()     // Catch:{ NumberFormatException -> 0x0044 }
            r10 = 16
            int r9 = java.lang.Integer.parseInt(r9, r10)     // Catch:{ NumberFormatException -> 0x0044 }
            if (r9 < 0) goto L_0x00ad
            int r6 = r7 + 1
            byte r9 = (byte) r9     // Catch:{ NumberFormatException -> 0x0044 }
            r5[r7] = r9     // Catch:{ NumberFormatException -> 0x0044 }
            if (r4 >= r2) goto L_0x00a8
            char r7 = r0.charAt(r4)     // Catch:{ NumberFormatException -> 0x0044 }
            r8 = r7
        L_0x00a8:
            r7 = r6
            r10 = 37
            r6 = r4
            goto L_0x0048
        L_0x00ad:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException     // Catch:{ NumberFormatException -> 0x0044 }
            java.lang.String r0 = r0.substring(r6, r4)     // Catch:{ NumberFormatException -> 0x0044 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x0044 }
            r2.<init>()     // Catch:{ NumberFormatException -> 0x0044 }
            java.lang.String r3 = "URLDecoder: Illegal hex characters in escape (%) pattern - negative value : "
            r2.append(r3)     // Catch:{ NumberFormatException -> 0x0044 }
            r2.append(r0)     // Catch:{ NumberFormatException -> 0x0044 }
            java.lang.String r0 = r2.toString()     // Catch:{ NumberFormatException -> 0x0044 }
            r1.<init>(r0)     // Catch:{ NumberFormatException -> 0x0044 }
            throw r1     // Catch:{ NumberFormatException -> 0x0044 }
        L_0x00c8:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException     // Catch:{ NumberFormatException -> 0x0044 }
            int r2 = r6 + 3
            java.lang.String r0 = r0.substring(r6, r2)     // Catch:{ NumberFormatException -> 0x0044 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x0044 }
            r2.<init>()     // Catch:{ NumberFormatException -> 0x0044 }
            java.lang.String r3 = "URLDecoder: Illegal hex characters in escape (%) pattern : "
            r2.append(r3)     // Catch:{ NumberFormatException -> 0x0044 }
            r2.append(r0)     // Catch:{ NumberFormatException -> 0x0044 }
            java.lang.String r0 = r2.toString()     // Catch:{ NumberFormatException -> 0x0044 }
            r1.<init>(r0)     // Catch:{ NumberFormatException -> 0x0044 }
            throw r1     // Catch:{ NumberFormatException -> 0x0044 }
        L_0x00e5:
            if (r6 >= r2) goto L_0x00f4
            r4 = 37
            if (r8 == r4) goto L_0x00ec
            goto L_0x00f4
        L_0x00ec:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ NumberFormatException -> 0x0044 }
            java.lang.String r1 = "URLDecoder: Incomplete trailing escape (%) pattern"
            r0.<init>(r1)     // Catch:{ NumberFormatException -> 0x0044 }
            throw r0     // Catch:{ NumberFormatException -> 0x0044 }
        L_0x00f4:
            java.lang.String r4 = new java.lang.String     // Catch:{ NumberFormatException -> 0x0044 }
            r8 = 0
            r4.<init>(r5, r8, r7, r1)     // Catch:{ NumberFormatException -> 0x0044 }
            r3.append(r4)     // Catch:{ NumberFormatException -> 0x0044 }
        L_0x00fd:
            r7 = 1
            goto L_0x001d
        L_0x0100:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "URLDecoder: Illegal hex characters in escape (%) pattern - "
            r2.<init>(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L_0x0118:
            if (r7 == 0) goto L_0x011e
            java.lang.String r0 = r3.toString()
        L_0x011e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.net.URLDecoder.a(java.lang.String, java.nio.charset.Charset):java.lang.String");
    }

    public static String decode(String str, String str2) {
        if (!str2.isEmpty()) {
            try {
                return a(str, Charset.forName(str2));
            } catch (IllegalCharsetNameException | UnsupportedCharsetException unused) {
                throw new UnsupportedEncodingException(str2);
            }
        } else {
            throw new UnsupportedEncodingException("URLDecoder: empty string enc parameter");
        }
    }
}
