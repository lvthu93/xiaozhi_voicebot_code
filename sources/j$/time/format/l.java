package j$.time.format;

import j$.time.temporal.a;
import j$.util.Objects;
import org.eclipse.paho.client.mqttv3.MqttTopic;

final class l implements C0054g {
    static final String[] d = {"+HH", "+HHmm", "+HH:mm", "+HHMM", "+HH:MM", "+HHMMss", "+HH:MM:ss", "+HHMMSS", "+HH:MM:SS", "+HHmmss", "+HH:mm:ss", "+H", "+Hmm", "+H:mm", "+HMM", "+H:MM", "+HMMss", "+H:MM:ss", "+HMMSS", "+H:MM:SS", "+Hmmss", "+H:mm:ss"};
    static final l e = new l("+HH:MM:ss", "Z");
    static final l f = new l("+HH:MM:ss", "0");
    private final String a;
    private final int b;
    private final int c;

    l(String str, String str2) {
        Objects.requireNonNull(str, "pattern");
        Objects.requireNonNull(str2, "noOffsetText");
        int i = 0;
        while (true) {
            String[] strArr = d;
            if (i >= 22) {
                throw new IllegalArgumentException("Invalid zone offset pattern: " + str);
            } else if (strArr[i].equals(str)) {
                this.b = i;
                this.c = i % 11;
                this.a = str2;
                return;
            } else {
                i++;
            }
        }
    }

    private static void a(boolean z, int i, StringBuilder sb) {
        sb.append(z ? ":" : "");
        sb.append((char) ((i / 10) + 48));
        sb.append((char) ((i % 10) + 48));
    }

    private static boolean b(CharSequence charSequence, boolean z, int i, int[] iArr) {
        int i2;
        int i3 = iArr[0];
        if (i3 < 0) {
            return true;
        }
        if (z && i != 1) {
            int i4 = i3 + 1;
            if (i4 > charSequence.length() || charSequence.charAt(i3) != ':') {
                return false;
            }
            i3 = i4;
        }
        if (i3 + 2 > charSequence.length()) {
            return false;
        }
        int i5 = i3 + 1;
        char charAt = charSequence.charAt(i3);
        int i6 = i5 + 1;
        char charAt2 = charSequence.charAt(i5);
        if (charAt < '0' || charAt > '9' || charAt2 < '0' || charAt2 > '9' || (i2 = (charAt2 - '0') + ((charAt - '0') * 10)) < 0 || i2 > 59) {
            return false;
        }
        iArr[i] = i2;
        iArr[0] = i6;
        return true;
    }

    private static void c(CharSequence charSequence, boolean z, int[] iArr) {
        if (!z) {
            e(charSequence, 1, 2, iArr);
        } else if (!b(charSequence, false, 1, iArr)) {
            iArr[0] = ~iArr[0];
        }
    }

    private static void d(CharSequence charSequence, boolean z, boolean z2, int[] iArr) {
        if (!b(charSequence, z, 2, iArr) && z2) {
            iArr[0] = ~iArr[0];
        }
    }

    private static void e(CharSequence charSequence, int i, int i2, int[] iArr) {
        int i3;
        int i4 = iArr[0];
        char[] cArr = new char[i2];
        int i5 = 0;
        int i6 = 0;
        while (true) {
            if (i5 >= i2 || (i3 = i4 + 1) > charSequence.length()) {
                break;
            }
            char charAt = charSequence.charAt(i4);
            if (charAt < '0' || charAt > '9') {
                i4 = i3 - 1;
            } else {
                cArr[i5] = charAt;
                i6++;
                i5++;
                i4 = i3;
            }
        }
        i4 = i3 - 1;
        if (i6 < i) {
            iArr[0] = ~iArr[0];
            return;
        }
        switch (i6) {
            case 1:
                iArr[1] = cArr[0] - '0';
                break;
            case 2:
                iArr[1] = (cArr[1] - '0') + ((cArr[0] - '0') * 10);
                break;
            case 3:
                iArr[1] = cArr[0] - '0';
                iArr[2] = (cArr[2] - '0') + ((cArr[1] - '0') * 10);
                break;
            case 4:
                iArr[1] = (cArr[1] - '0') + ((cArr[0] - '0') * 10);
                iArr[2] = (cArr[3] - '0') + ((cArr[2] - '0') * 10);
                break;
            case 5:
                iArr[1] = cArr[0] - '0';
                iArr[2] = (cArr[2] - '0') + ((cArr[1] - '0') * 10);
                iArr[3] = (cArr[4] - '0') + ((cArr[3] - '0') * 10);
                break;
            case 6:
                iArr[1] = (cArr[1] - '0') + ((cArr[0] - '0') * 10);
                iArr[2] = (cArr[3] - '0') + ((cArr[2] - '0') * 10);
                iArr[3] = (cArr[5] - '0') + ((cArr[4] - '0') * 10);
                break;
        }
        iArr[0] = i4;
    }

    public final boolean k(z zVar, StringBuilder sb) {
        Long e2 = zVar.e(a.OFFSET_SECONDS);
        boolean z = false;
        if (e2 == null) {
            return false;
        }
        int h = j$.lang.a.h(e2.longValue());
        if (h != 0) {
            int abs = Math.abs((h / 3600) % 100);
            int abs2 = Math.abs((h / 60) % 60);
            int abs3 = Math.abs(h % 60);
            int length = sb.length();
            sb.append(h < 0 ? "-" : MqttTopic.SINGLE_LEVEL_WILDCARD);
            if ((this.b < 11) || abs >= 10) {
                a(false, abs, sb);
            } else {
                sb.append((char) (abs + 48));
            }
            int i = this.c;
            if ((i >= 3 && i <= 8) || ((i >= 9 && abs3 > 0) || (i >= 1 && abs2 > 0))) {
                a(i > 0 && i % 2 == 0, abs2, sb);
                abs += abs2;
                if (i == 7 || i == 8 || (i >= 5 && abs3 > 0)) {
                    if (i > 0 && i % 2 == 0) {
                        z = true;
                    }
                    a(z, abs3, sb);
                    abs += abs3;
                }
            }
            if (abs == 0) {
                sb.setLength(length);
            }
            return true;
        }
        sb.append(this.a);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0012, code lost:
        if (r8 == r9) goto L_0x012c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int l(j$.time.format.x r17, java.lang.CharSequence r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r7 = r18
            r8 = r19
            int r9 = r18.length()
            java.lang.String r1 = r0.a
            int r10 = r1.length()
            if (r10 != 0) goto L_0x0016
            if (r8 != r9) goto L_0x0034
            goto L_0x012c
        L_0x0016:
            if (r8 != r9) goto L_0x001a
            int r1 = ~r8
            return r1
        L_0x001a:
            java.lang.String r4 = r0.a
            r5 = 0
            r1 = r17
            r2 = r18
            r3 = r19
            r6 = r10
            boolean r1 = r1.s(r2, r3, r4, r5, r6)
            if (r1 == 0) goto L_0x0034
            j$.time.temporal.a r1 = j$.time.temporal.a.OFFSET_SECONDS
            int r2 = r8 + r10
            r6 = r2
            r3 = 0
            r2 = r1
            goto L_0x0132
        L_0x0034:
            char r1 = r18.charAt(r19)
            r2 = 43
            r3 = 45
            if (r1 == r2) goto L_0x0040
            if (r1 != r3) goto L_0x012a
        L_0x0040:
            r2 = 1
            if (r1 != r3) goto L_0x0045
            r1 = -1
            goto L_0x0046
        L_0x0045:
            r1 = 1
        L_0x0046:
            r3 = 2
            r4 = 0
            int r5 = r0.c
            if (r5 <= 0) goto L_0x0051
            int r5 = r5 % r3
            if (r5 != 0) goto L_0x0051
            r5 = 1
            goto L_0x0052
        L_0x0051:
            r5 = 0
        L_0x0052:
            r6 = 11
            int r13 = r0.b
            if (r13 >= r6) goto L_0x005a
            r14 = 1
            goto L_0x005b
        L_0x005a:
            r14 = 0
        L_0x005b:
            r15 = 4
            int[] r11 = new int[r15]
            int r12 = r8 + 1
            r11[r4] = r12
            boolean r12 = r17.l()
            if (r12 != 0) goto L_0x00a2
            r12 = 58
            if (r14 == 0) goto L_0x0083
            if (r5 != 0) goto L_0x007e
            if (r13 != 0) goto L_0x007b
            int r6 = r8 + 3
            if (r9 <= r6) goto L_0x007b
            char r6 = r7.charAt(r6)
            if (r6 != r12) goto L_0x007b
            goto L_0x007e
        L_0x007b:
            r13 = 9
            goto L_0x00a2
        L_0x007e:
            r5 = 10
            r13 = 10
            goto L_0x00a1
        L_0x0083:
            if (r5 != 0) goto L_0x009d
            if (r13 != r6) goto L_0x009a
            int r6 = r8 + 3
            if (r9 <= r6) goto L_0x009a
            int r9 = r8 + 2
            char r9 = r7.charAt(r9)
            if (r9 == r12) goto L_0x009d
            char r6 = r7.charAt(r6)
            if (r6 != r12) goto L_0x009a
            goto L_0x009d
        L_0x009a:
            r13 = 20
            goto L_0x00a2
        L_0x009d:
            r5 = 21
            r13 = 21
        L_0x00a1:
            r5 = 1
        L_0x00a2:
            r6 = 3
            r9 = 6
            switch(r13) {
                case 0: goto L_0x00f5;
                case 1: goto L_0x00ee;
                case 2: goto L_0x00ee;
                case 3: goto L_0x00e7;
                case 4: goto L_0x00e7;
                case 5: goto L_0x00dc;
                case 6: goto L_0x00dc;
                case 7: goto L_0x00ca;
                case 8: goto L_0x00ca;
                case 9: goto L_0x00bd;
                case 10: goto L_0x00bd;
                case 11: goto L_0x00f5;
                case 12: goto L_0x00b9;
                case 13: goto L_0x00ee;
                case 14: goto L_0x00b5;
                case 15: goto L_0x00e7;
                case 16: goto L_0x00b1;
                case 17: goto L_0x00dc;
                case 18: goto L_0x00ac;
                case 19: goto L_0x00ca;
                case 20: goto L_0x00a8;
                case 21: goto L_0x00bd;
                default: goto L_0x00a7;
            }
        L_0x00a7:
            goto L_0x00f8
        L_0x00a8:
            e(r7, r2, r9, r11)
            goto L_0x00f8
        L_0x00ac:
            r5 = 5
            e(r7, r5, r9, r11)
            goto L_0x00f8
        L_0x00b1:
            e(r7, r6, r9, r11)
            goto L_0x00f8
        L_0x00b5:
            e(r7, r6, r15, r11)
            goto L_0x00f8
        L_0x00b9:
            e(r7, r2, r15, r11)
            goto L_0x00f8
        L_0x00bd:
            c(r7, r14, r11)
            boolean r9 = b(r7, r5, r3, r11)
            if (r9 == 0) goto L_0x00f8
            b(r7, r5, r6, r11)
            goto L_0x00f8
        L_0x00ca:
            c(r7, r14, r11)
            d(r7, r5, r2, r11)
            boolean r5 = b(r7, r5, r6, r11)
            if (r5 != 0) goto L_0x00f8
            r5 = r11[r4]
            int r5 = ~r5
            r11[r4] = r5
            goto L_0x00f8
        L_0x00dc:
            c(r7, r14, r11)
            d(r7, r5, r2, r11)
            boolean r5 = b(r7, r5, r6, r11)
            goto L_0x00f8
        L_0x00e7:
            c(r7, r14, r11)
            d(r7, r5, r2, r11)
            goto L_0x00f8
        L_0x00ee:
            c(r7, r14, r11)
            d(r7, r5, r4, r11)
            goto L_0x00f8
        L_0x00f5:
            c(r7, r14, r11)
        L_0x00f8:
            r4 = r11[r4]
            if (r4 <= 0) goto L_0x012a
            r2 = r11[r2]
            r5 = 23
            if (r2 > r5) goto L_0x0122
            r3 = r11[r3]
            r5 = 59
            if (r3 > r5) goto L_0x0122
            r6 = r11[r6]
            if (r6 > r5) goto L_0x0122
            long r9 = (long) r1
            long r1 = (long) r2
            r11 = 3600(0xe10, double:1.7786E-320)
            long r1 = r1 * r11
            long r11 = (long) r3
            r13 = 60
            long r11 = r11 * r13
            long r11 = r11 + r1
            long r1 = (long) r6
            long r11 = r11 + r1
            long r11 = r11 * r9
            j$.time.temporal.a r1 = j$.time.temporal.a.OFFSET_SECONDS
            r2 = r1
            r6 = r4
            r3 = r11
            goto L_0x0132
        L_0x0122:
            j$.time.DateTimeException r1 = new j$.time.DateTimeException
            java.lang.String r2 = "Value out of range: Hour[0-23], Minute[0-59], Second[0-59]"
            r1.<init>(r2)
            throw r1
        L_0x012a:
            if (r10 != 0) goto L_0x013b
        L_0x012c:
            j$.time.temporal.a r1 = j$.time.temporal.a.OFFSET_SECONDS
            r2 = r1
            r6 = r8
            r3 = 0
        L_0x0132:
            r1 = r17
            r5 = r19
            int r1 = r1.o(r2, r3, r5, r6)
            return r1
        L_0x013b:
            int r1 = ~r8
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.l.l(j$.time.format.x, java.lang.CharSequence, int):int");
    }

    public final String toString() {
        String replace = this.a.replace("'", "''");
        String str = d[this.b];
        return "Offset(" + str + ",'" + replace + "')";
    }
}
