package j$.time.format;

import j$.time.temporal.a;
import org.eclipse.paho.client.mqttv3.MqttTopic;

final class j implements C0054g {
    public final /* synthetic */ int a;
    private final Object b;

    public /* synthetic */ j(Object obj, int i) {
        this.a = i;
        this.b = obj;
    }

    private static void a(StringBuilder sb, int i) {
        sb.append((char) ((i / 10) + 48));
        sb.append((char) ((i % 10) + 48));
    }

    static int b(CharSequence charSequence, int i) {
        char charAt = charSequence.charAt(i);
        if (charAt < '0' || charAt > '9') {
            return -1;
        }
        return charAt - '0';
    }

    public final boolean k(z zVar, StringBuilder sb) {
        int i = this.a;
        Object obj = this.b;
        switch (i) {
            case 0:
                Long e = zVar.e(a.OFFSET_SECONDS);
                if (e == null) {
                    return false;
                }
                sb.append("GMT");
                int h = j$.lang.a.h(e.longValue());
                if (h == 0) {
                    return true;
                }
                int abs = Math.abs((h / 3600) % 100);
                int abs2 = Math.abs((h / 60) % 60);
                int abs3 = Math.abs(h % 60);
                sb.append(h < 0 ? "-" : MqttTopic.SINGLE_LEVEL_WILDCARD);
                if (((G) obj) == G.FULL) {
                    a(sb, abs);
                    sb.append(':');
                    a(sb, abs2);
                    if (abs3 == 0) {
                        return true;
                    }
                } else {
                    if (abs >= 10) {
                        sb.append((char) ((abs / 10) + 48));
                    }
                    sb.append((char) ((abs % 10) + 48));
                    if (abs2 == 0 && abs3 == 0) {
                        return true;
                    }
                    sb.append(':');
                    a(sb, abs2);
                    if (abs3 == 0) {
                        return true;
                    }
                }
                sb.append(':');
                a(sb, abs3);
                return true;
            default:
                sb.append((String) obj);
                return true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0087, code lost:
        if (r2 >= 0) goto L_0x00e3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int l(j$.time.format.x r14, java.lang.CharSequence r15, int r16) {
        /*
            r13 = this;
            r0 = r13
            r7 = r15
            r8 = r16
            int r1 = r0.a
            java.lang.Object r9 = r0.b
            switch(r1) {
                case 0: goto L_0x000d;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x0110
        L_0x000d:
            int r10 = r15.length()
            java.lang.String r4 = "GMT"
            r5 = 0
            r6 = 3
            r1 = r14
            r2 = r15
            r3 = r16
            boolean r1 = r1.s(r2, r3, r4, r5, r6)
            if (r1 != 0) goto L_0x0021
            goto L_0x0092
        L_0x0021:
            int r6 = r8 + 3
            if (r6 != r10) goto L_0x0027
            goto L_0x0104
        L_0x0027:
            char r1 = r15.charAt(r6)
            r2 = 43
            r3 = 1
            if (r1 != r2) goto L_0x0032
            r1 = 1
            goto L_0x0037
        L_0x0032:
            r2 = 45
            if (r1 != r2) goto L_0x0104
            r1 = -1
        L_0x0037:
            int r6 = r6 + r3
            j$.time.format.G r9 = (j$.time.format.G) r9
            j$.time.format.G r2 = j$.time.format.G.FULL
            r3 = 0
            r4 = 58
            if (r9 != r2) goto L_0x008a
            int r2 = r6 + 1
            int r5 = b(r15, r6)
            int r6 = r2 + 1
            int r2 = b(r15, r2)
            if (r5 < 0) goto L_0x0092
            if (r2 < 0) goto L_0x0092
            int r9 = r6 + 1
            char r6 = r15.charAt(r6)
            if (r6 == r4) goto L_0x005a
            goto L_0x0092
        L_0x005a:
            int r5 = r5 * 10
            int r5 = r5 + r2
            int r2 = r9 + 1
            int r6 = b(r15, r9)
            int r9 = r2 + 1
            int r2 = b(r15, r2)
            if (r6 < 0) goto L_0x0092
            if (r2 >= 0) goto L_0x006e
            goto L_0x0092
        L_0x006e:
            int r6 = r6 * 10
            int r6 = r6 + r2
            int r2 = r9 + 2
            if (r2 >= r10) goto L_0x00ed
            char r10 = r15.charAt(r9)
            if (r10 != r4) goto L_0x00ed
            int r4 = r9 + 1
            int r4 = b(r15, r4)
            int r2 = b(r15, r2)
            if (r4 < 0) goto L_0x00ed
            if (r2 < 0) goto L_0x00ed
            goto L_0x00e3
        L_0x008a:
            int r2 = r6 + 1
            int r5 = b(r15, r6)
            if (r5 >= 0) goto L_0x0095
        L_0x0092:
            int r1 = ~r8
            goto L_0x010f
        L_0x0095:
            if (r2 >= r10) goto L_0x00ea
            int r6 = b(r15, r2)
            if (r6 < 0) goto L_0x00a2
            int r5 = r5 * 10
            int r5 = r5 + r6
            int r2 = r2 + 1
        L_0x00a2:
            int r6 = r2 + 2
            if (r6 >= r10) goto L_0x00ea
            char r9 = r15.charAt(r2)
            if (r9 != r4) goto L_0x00ea
            if (r6 >= r10) goto L_0x00ea
            char r9 = r15.charAt(r2)
            if (r9 != r4) goto L_0x00ea
            int r9 = r2 + 1
            int r9 = b(r15, r9)
            int r6 = b(r15, r6)
            if (r9 < 0) goto L_0x00ea
            if (r6 < 0) goto L_0x00ea
            int r9 = r9 * 10
            int r9 = r9 + r6
            int r2 = r2 + 3
            int r6 = r2 + 2
            if (r6 >= r10) goto L_0x00eb
            char r10 = r15.charAt(r2)
            if (r10 != r4) goto L_0x00eb
            int r4 = r2 + 1
            int r4 = b(r15, r4)
            int r6 = b(r15, r6)
            if (r4 < 0) goto L_0x00eb
            if (r6 < 0) goto L_0x00eb
            r12 = r9
            r9 = r2
            r2 = r6
            r6 = r12
        L_0x00e3:
            int r4 = r4 * 10
            int r3 = r4 + r2
            int r9 = r9 + 3
            goto L_0x00ed
        L_0x00ea:
            r9 = 0
        L_0x00eb:
            r6 = r9
            r9 = r2
        L_0x00ed:
            long r1 = (long) r1
            long r4 = (long) r5
            r10 = 3600(0xe10, double:1.7786E-320)
            long r4 = r4 * r10
            long r6 = (long) r6
            r10 = 60
            long r6 = r6 * r10
            long r6 = r6 + r4
            long r3 = (long) r3
            long r6 = r6 + r3
            long r3 = r6 * r1
            j$.time.temporal.a r2 = j$.time.temporal.a.OFFSET_SECONDS
            r1 = r14
            r5 = r16
            r6 = r9
            goto L_0x010b
        L_0x0104:
            j$.time.temporal.a r2 = j$.time.temporal.a.OFFSET_SECONDS
            r3 = 0
            r1 = r14
            r5 = r16
        L_0x010b:
            int r1 = r1.o(r2, r3, r5, r6)
        L_0x010f:
            return r1
        L_0x0110:
            int r1 = r15.length()
            if (r8 > r1) goto L_0x0132
            if (r8 < 0) goto L_0x0132
            java.lang.String r9 = (java.lang.String) r9
            r5 = 0
            int r6 = r9.length()
            r1 = r14
            r2 = r15
            r3 = r16
            r4 = r9
            boolean r1 = r1.s(r2, r3, r4, r5, r6)
            if (r1 != 0) goto L_0x012c
            int r1 = ~r8
            goto L_0x0131
        L_0x012c:
            int r1 = r9.length()
            int r1 = r1 + r8
        L_0x0131:
            return r1
        L_0x0132:
            java.lang.IndexOutOfBoundsException r1 = new java.lang.IndexOutOfBoundsException
            r1.<init>()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.j.l(j$.time.format.x, java.lang.CharSequence, int):int");
    }

    public final String toString() {
        int i = this.a;
        Object obj = this.b;
        switch (i) {
            case 0:
                return "LocalizedOffset(" + ((G) obj) + ")";
            default:
                String replace = ((String) obj).replace("'", "''");
                return "'" + replace + "'";
        }
    }
}
