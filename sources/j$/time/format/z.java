package j$.time.format;

import j$.time.DateTimeException;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.q;
import j$.time.temporal.s;
import java.util.Locale;

final class z {
    private TemporalAccessor a;
    private DateTimeFormatter b;
    private int c;

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0072  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    z(j$.time.temporal.TemporalAccessor r10, j$.time.format.DateTimeFormatter r11) {
        /*
            r9 = this;
            r9.<init>()
            j$.time.chrono.m r0 = r11.a()
            j$.time.ZoneId r1 = r11.d()
            if (r0 != 0) goto L_0x0011
            if (r1 != 0) goto L_0x0011
            goto L_0x00ff
        L_0x0011:
            j$.time.temporal.s r2 = j$.time.temporal.p.e()
            java.lang.Object r2 = r10.H(r2)
            j$.time.chrono.m r2 = (j$.time.chrono.m) r2
            j$.time.temporal.s r3 = j$.time.temporal.p.l()
            java.lang.Object r3 = r10.H(r3)
            j$.time.ZoneId r3 = (j$.time.ZoneId) r3
            boolean r4 = j$.util.Objects.equals(r0, r2)
            r5 = 0
            if (r4 == 0) goto L_0x002d
            r0 = r5
        L_0x002d:
            boolean r4 = j$.util.Objects.equals(r1, r3)
            if (r4 == 0) goto L_0x0034
            r1 = r5
        L_0x0034:
            if (r0 != 0) goto L_0x003a
            if (r1 != 0) goto L_0x003a
            goto L_0x00ff
        L_0x003a:
            if (r0 == 0) goto L_0x003e
            r4 = r0
            goto L_0x003f
        L_0x003e:
            r4 = r2
        L_0x003f:
            if (r1 == 0) goto L_0x00ab
            j$.time.temporal.a r6 = j$.time.temporal.a.INSTANT_SECONDS
            boolean r6 = r10.e(r6)
            if (r6 == 0) goto L_0x005b
            j$.time.chrono.t r0 = j$.time.chrono.t.d
            java.lang.Object r0 = j$.util.Objects.requireNonNullElse(r4, r0)
            j$.time.chrono.m r0 = (j$.time.chrono.m) r0
            j$.time.Instant r10 = j$.time.Instant.R(r10)
            j$.time.chrono.ChronoZonedDateTime r10 = r0.L(r10, r1)
            goto L_0x00ff
        L_0x005b:
            j$.time.zone.f r6 = r1.R()     // Catch:{ g -> 0x006c }
            boolean r7 = r6.i()     // Catch:{ g -> 0x006c }
            if (r7 == 0) goto L_0x006d
            j$.time.Instant r7 = j$.time.Instant.c     // Catch:{ g -> 0x006c }
            j$.time.ZoneOffset r6 = r6.d(r7)     // Catch:{ g -> 0x006c }
            goto L_0x006e
        L_0x006c:
        L_0x006d:
            r6 = r1
        L_0x006e:
            boolean r6 = r6 instanceof j$.time.ZoneOffset
            if (r6 == 0) goto L_0x00ab
            j$.time.temporal.a r6 = j$.time.temporal.a.OFFSET_SECONDS
            boolean r7 = r10.e(r6)
            if (r7 == 0) goto L_0x00ab
            int r6 = r10.k(r6)
            j$.time.zone.f r7 = r1.R()
            j$.time.Instant r8 = j$.time.Instant.c
            j$.time.ZoneOffset r7 = r7.d(r8)
            int r7 = r7.a0()
            if (r6 != r7) goto L_0x008f
            goto L_0x00ab
        L_0x008f:
            j$.time.DateTimeException r11 = new j$.time.DateTimeException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "Unable to apply override zone '"
            r0.<init>(r2)
            r0.append(r1)
            java.lang.String r1 = "' because the temporal object being formatted has a different offset but does not represent an instant: "
            r0.append(r1)
            r0.append(r10)
            java.lang.String r10 = r0.toString()
            r11.<init>(r10)
            throw r11
        L_0x00ab:
            if (r1 == 0) goto L_0x00ae
            r3 = r1
        L_0x00ae:
            if (r0 == 0) goto L_0x00f9
            j$.time.temporal.a r1 = j$.time.temporal.a.EPOCH_DAY
            boolean r1 = r10.e(r1)
            if (r1 == 0) goto L_0x00bd
            j$.time.chrono.c r5 = r4.B(r10)
            goto L_0x00f9
        L_0x00bd:
            j$.time.chrono.t r1 = j$.time.chrono.t.d
            if (r0 != r1) goto L_0x00c3
            if (r2 == 0) goto L_0x00f9
        L_0x00c3:
            j$.time.temporal.a[] r1 = j$.time.temporal.a.values()
            int r2 = r1.length
            r6 = 0
        L_0x00c9:
            if (r6 >= r2) goto L_0x00f9
            r7 = r1[r6]
            boolean r8 = r7.isDateBased()
            if (r8 == 0) goto L_0x00f6
            boolean r7 = r10.e(r7)
            if (r7 != 0) goto L_0x00da
            goto L_0x00f6
        L_0x00da:
            j$.time.DateTimeException r11 = new j$.time.DateTimeException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Unable to apply override chronology '"
            r1.<init>(r2)
            r1.append(r0)
            java.lang.String r0 = "' because the temporal object being formatted contains date fields but does not represent a whole date: "
            r1.append(r0)
            r1.append(r10)
            java.lang.String r10 = r1.toString()
            r11.<init>(r10)
            throw r11
        L_0x00f6:
            int r6 = r6 + 1
            goto L_0x00c9
        L_0x00f9:
            j$.time.format.y r0 = new j$.time.format.y
            r0.<init>(r5, r10, r4, r3)
            r10 = r0
        L_0x00ff:
            r9.a = r10
            r9.b = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.z.<init>(j$.time.temporal.TemporalAccessor, j$.time.format.DateTimeFormatter):void");
    }

    /* access modifiers changed from: package-private */
    public final void a() {
        this.c--;
    }

    /* access modifiers changed from: package-private */
    public final C b() {
        return this.b.b();
    }

    /* access modifiers changed from: package-private */
    public final Locale c() {
        return this.b.c();
    }

    /* access modifiers changed from: package-private */
    public final TemporalAccessor d() {
        return this.a;
    }

    /* access modifiers changed from: package-private */
    public final Long e(q qVar) {
        if (this.c <= 0 || this.a.e(qVar)) {
            return Long.valueOf(this.a.E(qVar));
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public final Object f(s sVar) {
        Object H = this.a.H(sVar);
        if (H != null || this.c != 0) {
            return H;
        }
        TemporalAccessor temporalAccessor = this.a;
        throw new DateTimeException("Unable to extract " + sVar + " from temporal " + temporalAccessor);
    }

    /* access modifiers changed from: package-private */
    public final void g() {
        this.c++;
    }

    public final String toString() {
        return this.a.toString();
    }
}
