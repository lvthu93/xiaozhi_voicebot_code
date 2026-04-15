package j$.time.format;

import j$.time.zone.j;
import j$.util.concurrent.ConcurrentHashMap;
import java.lang.ref.SoftReference;
import java.text.DateFormatSymbols;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

final class v extends u {
    private static final ConcurrentHashMap i = new ConcurrentHashMap();
    private final G e;
    private final boolean f;
    private final HashMap g = new HashMap();
    private final HashMap h = new HashMap();

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    v(j$.time.format.G r4, boolean r5) {
        /*
            r3 = this;
            j$.time.temporal.s r0 = j$.time.temporal.p.k()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "ZoneText("
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r2 = ")"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r3.<init>(r0, r1)
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r3.g = r0
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r3.h = r0
            java.lang.String r0 = "textStyle"
            java.lang.Object r4 = j$.util.Objects.requireNonNull(r4, r0)
            j$.time.format.G r4 = (j$.time.format.G) r4
            r3.e = r4
            r3.f = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.v.<init>(j$.time.format.G, boolean):void");
    }

    /* access modifiers changed from: protected */
    public final o a(x xVar) {
        o oVar;
        G g2 = G.NARROW;
        G g3 = this.e;
        if (g3 == g2) {
            return super.a(xVar);
        }
        Locale i2 = xVar.i();
        boolean k = xVar.k();
        Set a = j.a();
        int size = a.size();
        HashMap hashMap = k ? this.g : this.h;
        Map.Entry entry = (Map.Entry) hashMap.get(i2);
        if (entry == null || ((Integer) entry.getKey()).intValue() != size || (oVar = (o) ((SoftReference) entry.getValue()).get()) == null) {
            oVar = o.f(xVar);
            String[][] zoneStrings = DateFormatSymbols.getInstance(i2).getZoneStrings();
            int length = zoneStrings.length;
            int i3 = 0;
            while (true) {
                int i4 = 2;
                if (i3 >= length) {
                    break;
                }
                String[] strArr = zoneStrings[i3];
                String str = strArr[0];
                if (a.contains(str)) {
                    oVar.a(str, str);
                    String a2 = H.a(str, i2);
                    if (g3 == G.FULL) {
                        i4 = 1;
                    }
                    while (i4 < strArr.length) {
                        oVar.a(strArr[i4], a2);
                        i4 += 2;
                    }
                }
                i3++;
            }
            hashMap.put(i2, new AbstractMap.SimpleImmutableEntry(Integer.valueOf(size), new SoftReference(oVar)));
        }
        return oVar;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: j$.util.concurrent.ConcurrentHashMap} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: j$.util.concurrent.ConcurrentHashMap} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: java.util.Map} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v18, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v19, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v20, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v23, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v24, resolved type: java.lang.String} */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x009e, code lost:
        if (r8 == null) goto L_0x00a0;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ef  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean k(j$.time.format.z r14, java.lang.StringBuilder r15) {
        /*
            r13 = this;
            j$.time.temporal.s r0 = j$.time.temporal.p.l()
            java.lang.Object r0 = r14.f(r0)
            j$.time.ZoneId r0 = (j$.time.ZoneId) r0
            r1 = 0
            if (r0 != 0) goto L_0x000e
            return r1
        L_0x000e:
            java.lang.String r2 = r0.j()
            boolean r3 = r0 instanceof j$.time.ZoneOffset
            r4 = 1
            if (r3 != 0) goto L_0x00f0
            j$.time.temporal.TemporalAccessor r3 = r14.d()
            boolean r5 = r13.f
            r6 = 2
            if (r5 != 0) goto L_0x0077
            j$.time.temporal.a r5 = j$.time.temporal.a.INSTANT_SECONDS
            boolean r5 = r3.e(r5)
            if (r5 == 0) goto L_0x0035
            j$.time.zone.f r0 = r0.R()
            j$.time.Instant r3 = j$.time.Instant.R(r3)
            boolean r0 = r0.h(r3)
            goto L_0x0078
        L_0x0035:
            j$.time.temporal.a r5 = j$.time.temporal.a.EPOCH_DAY
            boolean r7 = r3.e(r5)
            if (r7 == 0) goto L_0x0077
            j$.time.temporal.a r7 = j$.time.temporal.a.NANO_OF_DAY
            boolean r8 = r3.e(r7)
            if (r8 == 0) goto L_0x0077
            long r8 = r3.E(r5)
            j$.time.LocalDate r5 = j$.time.LocalDate.d0(r8)
            long r7 = r3.E(r7)
            j$.time.j r3 = j$.time.j.Z(r7)
            j$.time.LocalDateTime r3 = j$.time.LocalDateTime.Z(r5, r3)
            j$.time.zone.f r5 = r0.R()
            j$.time.zone.b r5 = r5.f(r3)
            if (r5 != 0) goto L_0x0077
            j$.time.zone.f r5 = r0.R()
            j$.time.ZonedDateTime r0 = r3.A(r0)
            r0.getClass()
            j$.time.Instant r0 = j$.time.chrono.C0038b.r(r0)
            boolean r0 = r5.h(r0)
            goto L_0x0078
        L_0x0077:
            r0 = 2
        L_0x0078:
            java.util.Locale r14 = r14.c()
            j$.time.format.G r3 = j$.time.format.G.NARROW
            r5 = 0
            j$.time.format.G r7 = r13.e
            if (r7 != r3) goto L_0x0084
            goto L_0x00ed
        L_0x0084:
            j$.util.concurrent.ConcurrentHashMap r3 = i
            java.lang.Object r8 = r3.get(r2)
            java.lang.ref.SoftReference r8 = (java.lang.ref.SoftReference) r8
            r9 = 5
            r10 = 3
            if (r8 == 0) goto L_0x00a0
            java.lang.Object r5 = r8.get()
            java.util.Map r5 = (java.util.Map) r5
            if (r5 == 0) goto L_0x00a0
            java.lang.Object r8 = r5.get(r14)
            java.lang.String[] r8 = (java.lang.String[]) r8
            if (r8 != 0) goto L_0x00da
        L_0x00a0:
            java.util.TimeZone r8 = java.util.TimeZone.getTimeZone(r2)
            r11 = 7
            java.lang.String[] r11 = new java.lang.String[r11]
            r11[r1] = r2
            java.lang.String r12 = r8.getDisplayName(r1, r4, r14)
            r11[r4] = r12
            java.lang.String r12 = r8.getDisplayName(r1, r1, r14)
            r11[r6] = r12
            java.lang.String r6 = r8.getDisplayName(r4, r4, r14)
            r11[r10] = r6
            r6 = 4
            java.lang.String r1 = r8.getDisplayName(r4, r1, r14)
            r11[r6] = r1
            r11[r9] = r2
            r1 = 6
            r11[r1] = r2
            if (r5 != 0) goto L_0x00ce
            j$.util.concurrent.ConcurrentHashMap r5 = new j$.util.concurrent.ConcurrentHashMap
            r5.<init>()
        L_0x00ce:
            r5.put(r14, r11)
            java.lang.ref.SoftReference r14 = new java.lang.ref.SoftReference
            r14.<init>(r5)
            r3.put(r2, r14)
            r8 = r11
        L_0x00da:
            int r14 = r7.k()
            if (r0 == 0) goto L_0x00ea
            if (r0 == r4) goto L_0x00e6
            int r14 = r14 + r9
            r5 = r8[r14]
            goto L_0x00ed
        L_0x00e6:
            int r14 = r14 + r10
            r5 = r8[r14]
            goto L_0x00ed
        L_0x00ea:
            int r14 = r14 + r4
            r5 = r8[r14]
        L_0x00ed:
            if (r5 == 0) goto L_0x00f0
            r2 = r5
        L_0x00f0:
            r15.append(r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.v.k(j$.time.format.z, java.lang.StringBuilder):boolean");
    }
}
