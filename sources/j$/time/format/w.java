package j$.time.format;

import j$.time.chrono.t;
import j$.time.temporal.a;
import j$.time.temporal.i;
import j$.time.temporal.k;
import j$.time.temporal.q;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public final class w {
    private static final C0048a h = new C0048a();
    private static final HashMap i;
    public static final /* synthetic */ int j = 0;
    private w a;
    private final w b;
    private final ArrayList c;
    private final boolean d;
    private int e;
    private char f;
    private int g;

    static {
        HashMap hashMap = new HashMap();
        i = hashMap;
        hashMap.put('G', a.ERA);
        hashMap.put('y', a.YEAR_OF_ERA);
        hashMap.put('u', a.YEAR);
        q qVar = i.a;
        hashMap.put('Q', qVar);
        hashMap.put('q', qVar);
        a aVar = a.MONTH_OF_YEAR;
        hashMap.put('M', aVar);
        hashMap.put('L', aVar);
        hashMap.put('D', a.DAY_OF_YEAR);
        hashMap.put('d', a.DAY_OF_MONTH);
        hashMap.put('F', a.ALIGNED_DAY_OF_WEEK_IN_MONTH);
        a aVar2 = a.DAY_OF_WEEK;
        hashMap.put('E', aVar2);
        hashMap.put('c', aVar2);
        hashMap.put('e', aVar2);
        hashMap.put('a', a.AMPM_OF_DAY);
        hashMap.put('H', a.HOUR_OF_DAY);
        hashMap.put('k', a.CLOCK_HOUR_OF_DAY);
        hashMap.put('K', a.HOUR_OF_AMPM);
        hashMap.put('h', a.CLOCK_HOUR_OF_AMPM);
        hashMap.put('m', a.MINUTE_OF_HOUR);
        hashMap.put('s', a.SECOND_OF_MINUTE);
        a aVar3 = a.NANO_OF_SECOND;
        hashMap.put('S', aVar3);
        hashMap.put('A', a.MILLI_OF_DAY);
        hashMap.put('n', aVar3);
        hashMap.put('N', a.NANO_OF_DAY);
        hashMap.put('g', k.a);
    }

    public w() {
        this.a = this;
        this.c = new ArrayList();
        this.g = -1;
        this.b = null;
        this.d = false;
    }

    private w(w wVar) {
        this.a = this;
        this.c = new ArrayList();
        this.g = -1;
        this.b = wVar;
        this.d = true;
    }

    private int d(C0054g gVar) {
        Objects.requireNonNull(gVar, "pp");
        w wVar = this.a;
        int i2 = wVar.e;
        if (i2 > 0) {
            if (gVar != null) {
                gVar = new m(gVar, i2, wVar.f);
            }
            wVar.e = 0;
            wVar.f = 0;
        }
        wVar.c.add(gVar);
        w wVar2 = this.a;
        wVar2.g = -1;
        return wVar2.c.size() - 1;
    }

    private void l(k kVar) {
        k kVar2;
        w wVar = this.a;
        int i2 = wVar.g;
        if (i2 >= 0) {
            k kVar3 = (k) wVar.c.get(i2);
            int i3 = kVar.b;
            int i4 = kVar.c;
            if (i3 == i4 && kVar.d == F.NOT_NEGATIVE) {
                kVar2 = kVar3.f(i4);
                d(kVar.e());
                this.a.g = i2;
            } else {
                kVar2 = kVar3.e();
                this.a.g = d(kVar);
            }
            this.a.c.set(i2, kVar2);
            return;
        }
        wVar.g = d(kVar);
    }

    private DateTimeFormatter w(Locale locale, E e2, t tVar) {
        Objects.requireNonNull(locale, "locale");
        while (this.a.b != null) {
            o();
        }
        C0053f fVar = new C0053f(this.c, false);
        C c2 = C.a;
        return new DateTimeFormatter(fVar, locale, e2, tVar);
    }

    public final void a(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        d(dateTimeFormatter.g());
    }

    public final void b(a aVar, int i2, int i3, boolean z) {
        if (i2 != i3 || z) {
            d(new C0055h(aVar, i2, i3, z));
        } else {
            l(new C0055h(aVar, i2, i3, z));
        }
    }

    public final void c() {
        d(new i());
    }

    public final void e(char c2) {
        d(new C0052e(c2));
    }

    public final void f(String str) {
        Objects.requireNonNull(str, "literal");
        if (!str.isEmpty()) {
            d(str.length() == 1 ? new C0052e(str.charAt(0)) : new j(str, 1));
        }
    }

    public final void g(String str, String str2) {
        d(new l(str, str2));
    }

    public final void h() {
        d(l.e);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00e7, code lost:
        if (r3 == 1) goto L_0x020d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x01e7  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x01f0  */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x02ed  */
    /* JADX WARNING: Removed duplicated region for block: B:246:0x03fc  */
    /* JADX WARNING: Removed duplicated region for block: B:286:0x02e5 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:299:0x0415 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void i(java.lang.String r17) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            java.lang.String r2 = "pattern"
            j$.util.Objects.requireNonNull(r1, r2)
            r2 = 0
            r3 = 0
        L_0x000b:
            int r4 = r17.length()
            if (r3 >= r4) goto L_0x0468
            char r4 = r1.charAt(r3)
            r5 = 90
            r6 = 1
            r7 = 122(0x7a, float:1.71E-43)
            r8 = 97
            r9 = 65
            if (r4 < r9) goto L_0x0022
            if (r4 <= r5) goto L_0x0026
        L_0x0022:
            if (r4 < r8) goto L_0x03d0
            if (r4 > r7) goto L_0x03d0
        L_0x0026:
            int r10 = r3 + 1
        L_0x0028:
            int r11 = r17.length()
            if (r10 >= r11) goto L_0x0037
            char r11 = r1.charAt(r10)
            if (r11 != r4) goto L_0x0037
            int r10 = r10 + 1
            goto L_0x0028
        L_0x0037:
            int r3 = r10 - r3
            r11 = 112(0x70, float:1.57E-43)
            r12 = -1
            if (r4 != r11) goto L_0x0098
            int r11 = r17.length()
            if (r10 >= r11) goto L_0x0064
            char r4 = r1.charAt(r10)
            if (r4 < r9) goto L_0x004c
            if (r4 <= r5) goto L_0x0050
        L_0x004c:
            if (r4 < r8) goto L_0x0064
            if (r4 > r7) goto L_0x0064
        L_0x0050:
            int r11 = r10 + 1
        L_0x0052:
            int r13 = r17.length()
            if (r11 >= r13) goto L_0x0061
            char r13 = r1.charAt(r11)
            if (r13 != r4) goto L_0x0061
            int r11 = r11 + 1
            goto L_0x0052
        L_0x0061:
            int r10 = r11 - r10
            goto L_0x0067
        L_0x0064:
            r11 = r10
            r10 = r3
            r3 = 0
        L_0x0067:
            if (r3 == 0) goto L_0x008c
            if (r3 < r6) goto L_0x0078
            j$.time.format.w r13 = r0.a
            r13.e = r3
            r3 = 32
            r13.f = r3
            r13.g = r12
            r3 = r10
            r10 = r11
            goto L_0x0098
        L_0x0078:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "The pad width must be at least one but was "
            r2.<init>(r4)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x008c:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Pad letter 'p' must be followed by valid pad pattern: "
            java.lang.String r1 = r3.concat(r1)
            r2.<init>(r1)
            throw r2
        L_0x0098:
            java.util.HashMap r11 = i
            java.lang.Character r13 = java.lang.Character.valueOf(r4)
            java.lang.Object r11 = r11.get(r13)
            j$.time.temporal.q r11 = (j$.time.temporal.q) r11
            r14 = 2
            r15 = 5
            java.lang.String r12 = "Too many pattern letters: "
            r5 = 4
            if (r11 == 0) goto L_0x022b
            if (r4 == r9) goto L_0x0221
            java.lang.String r7 = "field"
            r9 = 81
            r13 = 3
            if (r4 == r9) goto L_0x01b1
            r9 = 83
            if (r4 == r9) goto L_0x01aa
            if (r4 == r8) goto L_0x0195
            r8 = 107(0x6b, float:1.5E-43)
            if (r4 == r8) goto L_0x0178
            r8 = 113(0x71, float:1.58E-43)
            if (r4 == r8) goto L_0x0176
            r8 = 115(0x73, float:1.61E-43)
            if (r4 == r8) goto L_0x0178
            r8 = 117(0x75, float:1.64E-43)
            if (r4 == r8) goto L_0x0155
            r8 = 121(0x79, float:1.7E-43)
            if (r4 == r8) goto L_0x0155
            r8 = 103(0x67, float:1.44E-43)
            if (r4 == r8) goto L_0x016d
            r8 = 104(0x68, float:1.46E-43)
            if (r4 == r8) goto L_0x0178
            r8 = 109(0x6d, float:1.53E-43)
            if (r4 == r8) goto L_0x0178
            r8 = 110(0x6e, float:1.54E-43)
            if (r4 == r8) goto L_0x0221
            switch(r4) {
                case 68: goto L_0x011f;
                case 69: goto L_0x01b1;
                case 70: goto L_0x0109;
                case 71: goto L_0x00eb;
                case 72: goto L_0x0178;
                default: goto L_0x00e1;
            }
        L_0x00e1:
            switch(r4) {
                case 75: goto L_0x0178;
                case 76: goto L_0x0176;
                case 77: goto L_0x01b1;
                case 78: goto L_0x0221;
                default: goto L_0x00e4;
            }
        L_0x00e4:
            switch(r4) {
                case 99: goto L_0x0141;
                case 100: goto L_0x0178;
                case 101: goto L_0x01b1;
                default: goto L_0x00e7;
            }
        L_0x00e7:
            if (r3 != r6) goto L_0x017e
            goto L_0x020d
        L_0x00eb:
            if (r3 == r6) goto L_0x01f4
            if (r3 == r14) goto L_0x01f4
            if (r3 == r13) goto L_0x01f4
            if (r3 == r5) goto L_0x01db
            if (r3 != r15) goto L_0x00f7
            goto L_0x01c1
        L_0x00f7:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r12)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0109:
            if (r3 != r6) goto L_0x010d
            goto L_0x020d
        L_0x010d:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r12)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x011f:
            if (r3 != r6) goto L_0x0123
            goto L_0x020d
        L_0x0123:
            if (r3 == r14) goto L_0x013a
            if (r3 != r13) goto L_0x0128
            goto L_0x013a
        L_0x0128:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r12)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x013a:
            j$.time.format.F r4 = j$.time.format.F.NOT_NEGATIVE
            r0.k(r11, r3, r13, r4)
            goto L_0x0228
        L_0x0141:
            if (r3 != r6) goto L_0x014a
            j$.time.format.t r5 = new j$.time.format.t
            r5.<init>(r4, r3, r3, r3)
            goto L_0x01ec
        L_0x014a:
            if (r3 == r14) goto L_0x014d
            goto L_0x0176
        L_0x014d:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Invalid pattern \"cc\""
            r1.<init>(r2)
            throw r1
        L_0x0155:
            if (r3 != r14) goto L_0x016b
            j$.time.LocalDate r3 = j$.time.format.q.i
            j$.util.Objects.requireNonNull(r11, r7)
            java.lang.String r4 = "baseDate"
            j$.util.Objects.requireNonNull(r3, r4)
            j$.time.format.q r4 = new j$.time.format.q
            r4.<init>(r11, r3)
            r0.l(r4)
            goto L_0x0228
        L_0x016b:
            if (r3 >= r5) goto L_0x0173
        L_0x016d:
            j$.time.format.F r4 = j$.time.format.F.NORMAL
        L_0x016f:
            r5 = 19
            goto L_0x0225
        L_0x0173:
            j$.time.format.F r4 = j$.time.format.F.EXCEEDS_PAD
            goto L_0x016f
        L_0x0176:
            r8 = 1
            goto L_0x01b2
        L_0x0178:
            if (r3 != r6) goto L_0x017c
            goto L_0x020d
        L_0x017c:
            if (r3 != r14) goto L_0x0183
        L_0x017e:
            r0.m(r11, r3)
            goto L_0x0228
        L_0x0183:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r12)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0195:
            if (r3 != r6) goto L_0x0198
            goto L_0x01f4
        L_0x0198:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r12)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x01aa:
            j$.time.temporal.a r4 = j$.time.temporal.a.NANO_OF_SECOND
            r0.b(r4, r3, r3, r2)
            goto L_0x0228
        L_0x01b1:
            r8 = 0
        L_0x01b2:
            if (r3 == r6) goto L_0x01e3
            if (r3 == r14) goto L_0x01e3
            if (r3 == r13) goto L_0x01de
            if (r3 == r5) goto L_0x01d6
            if (r3 != r15) goto L_0x01c4
            if (r8 == 0) goto L_0x01c1
            j$.time.format.G r3 = j$.time.format.G.NARROW_STANDALONE
            goto L_0x01f6
        L_0x01c1:
            j$.time.format.G r3 = j$.time.format.G.NARROW
            goto L_0x01f6
        L_0x01c4:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r12)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x01d6:
            if (r8 == 0) goto L_0x01db
            j$.time.format.G r3 = j$.time.format.G.FULL_STANDALONE
            goto L_0x01f6
        L_0x01db:
            j$.time.format.G r3 = j$.time.format.G.FULL
            goto L_0x01f6
        L_0x01de:
            if (r8 == 0) goto L_0x01f4
            j$.time.format.G r3 = j$.time.format.G.SHORT_STANDALONE
            goto L_0x01f6
        L_0x01e3:
            r5 = 101(0x65, float:1.42E-43)
            if (r4 != r5) goto L_0x01f0
            j$.time.format.t r5 = new j$.time.format.t
            r5.<init>(r4, r3, r3, r3)
        L_0x01ec:
            r0.l(r5)
            goto L_0x0228
        L_0x01f0:
            r5 = 69
            if (r4 != r5) goto L_0x020b
        L_0x01f4:
            j$.time.format.G r3 = j$.time.format.G.SHORT
        L_0x01f6:
            j$.util.Objects.requireNonNull(r11, r7)
            java.lang.String r4 = "textStyle"
            j$.util.Objects.requireNonNull(r3, r4)
            j$.time.format.s r4 = new j$.time.format.s
            j$.time.format.B r5 = j$.time.format.B.d()
            r4.<init>(r11, r3, r5)
            r0.d(r4)
            goto L_0x0228
        L_0x020b:
            if (r3 != r6) goto L_0x021d
        L_0x020d:
            j$.util.Objects.requireNonNull(r11, r7)
            j$.time.format.k r3 = new j$.time.format.k
            j$.time.format.F r4 = j$.time.format.F.NORMAL
            r5 = 19
            r3.<init>(r11, r6, r5, r4)
            r0.l(r3)
            goto L_0x0228
        L_0x021d:
            r0.m(r11, r14)
            goto L_0x0228
        L_0x0221:
            r5 = 19
            j$.time.format.F r4 = j$.time.format.F.NOT_NEGATIVE
        L_0x0225:
            r0.k(r11, r3, r5, r4)
        L_0x0228:
            r3 = -1
            goto L_0x03b9
        L_0x022b:
            if (r4 != r7) goto L_0x0251
            if (r3 > r5) goto L_0x023f
            if (r3 != r5) goto L_0x0234
            j$.time.format.G r3 = j$.time.format.G.FULL
            goto L_0x0236
        L_0x0234:
            j$.time.format.G r3 = j$.time.format.G.SHORT
        L_0x0236:
            j$.time.format.v r4 = new j$.time.format.v
            r4.<init>(r3, r2)
            r0.d(r4)
            goto L_0x0228
        L_0x023f:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r12)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0251:
            r7 = 86
            if (r4 != r7) goto L_0x027a
            if (r3 != r14) goto L_0x0266
            j$.time.format.u r3 = new j$.time.format.u
            j$.time.temporal.s r4 = j$.time.temporal.p.l()
            java.lang.String r5 = "ZoneId()"
            r3.<init>(r4, r5)
            r0.d(r3)
            goto L_0x0228
        L_0x0266:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Pattern letter count must be 2: "
            r2.<init>(r3)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x027a:
            r7 = 118(0x76, float:1.65E-43)
            if (r4 != r7) goto L_0x02a4
            if (r3 != r6) goto L_0x0283
            j$.time.format.G r3 = j$.time.format.G.SHORT
            goto L_0x0287
        L_0x0283:
            if (r3 != r5) goto L_0x0290
            j$.time.format.G r3 = j$.time.format.G.FULL
        L_0x0287:
            j$.time.format.v r4 = new j$.time.format.v
            r4.<init>(r3, r6)
            r0.d(r4)
            goto L_0x0228
        L_0x0290:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Wrong number of  pattern letters: "
            r2.<init>(r3)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x02a4:
            java.lang.String r7 = "+0000"
            r8 = 90
            if (r4 != r8) goto L_0x02ca
            if (r3 >= r5) goto L_0x02b0
            java.lang.String r3 = "+HHMM"
            goto L_0x0350
        L_0x02b0:
            if (r3 != r5) goto L_0x02b3
            goto L_0x02d5
        L_0x02b3:
            if (r3 != r15) goto L_0x02b8
            java.lang.String r3 = "+HH:MM:ss"
            goto L_0x031b
        L_0x02b8:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r12)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x02ca:
            r8 = 79
            if (r4 != r8) goto L_0x030b
            if (r3 != r6) goto L_0x02d3
            j$.time.format.G r3 = j$.time.format.G.SHORT
            goto L_0x02d7
        L_0x02d3:
            if (r3 != r5) goto L_0x02f7
        L_0x02d5:
            j$.time.format.G r3 = j$.time.format.G.FULL
        L_0x02d7:
            java.lang.String r4 = "style"
            j$.util.Objects.requireNonNull(r3, r4)
            j$.time.format.G r4 = j$.time.format.G.FULL
            if (r3 == r4) goto L_0x02ed
            j$.time.format.G r4 = j$.time.format.G.SHORT
            if (r3 != r4) goto L_0x02e5
            goto L_0x02ed
        L_0x02e5:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Style must be either full or short"
            r1.<init>(r2)
            throw r1
        L_0x02ed:
            j$.time.format.j r4 = new j$.time.format.j
            r4.<init>(r3, r2)
            r0.d(r4)
            goto L_0x0228
        L_0x02f7:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Pattern letter count must be 1 or 4: "
            r2.<init>(r3)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x030b:
            r5 = 88
            if (r4 != r5) goto L_0x0334
            if (r3 > r15) goto L_0x0322
            java.lang.String[] r4 = j$.time.format.l.d
            if (r3 != r6) goto L_0x0317
            r5 = 0
            goto L_0x0318
        L_0x0317:
            r5 = 1
        L_0x0318:
            int r3 = r3 + r5
            r3 = r4[r3]
        L_0x031b:
            java.lang.String r4 = "Z"
            r0.g(r3, r4)
            goto L_0x0228
        L_0x0322:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r12)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0334:
            r5 = 120(0x78, float:1.68E-43)
            if (r4 != r5) goto L_0x0367
            if (r3 > r15) goto L_0x0355
            if (r3 != r6) goto L_0x033f
            java.lang.String r7 = "+00"
            goto L_0x0346
        L_0x033f:
            int r4 = r3 % 2
            if (r4 != 0) goto L_0x0344
            goto L_0x0346
        L_0x0344:
            java.lang.String r7 = "+00:00"
        L_0x0346:
            java.lang.String[] r4 = j$.time.format.l.d
            if (r3 != r6) goto L_0x034c
            r5 = 0
            goto L_0x034d
        L_0x034c:
            r5 = 1
        L_0x034d:
            int r3 = r3 + r5
            r3 = r4[r3]
        L_0x0350:
            r0.g(r3, r7)
            goto L_0x0228
        L_0x0355:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r12)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0367:
            r5 = 87
            if (r4 != r5) goto L_0x0385
            if (r3 > r6) goto L_0x0373
            j$.time.format.t r5 = new j$.time.format.t
            r5.<init>(r4, r3, r3, r3)
            goto L_0x03b4
        L_0x0373:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r12)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0385:
            r5 = 119(0x77, float:1.67E-43)
            if (r4 != r5) goto L_0x03a3
            if (r3 > r14) goto L_0x0391
            j$.time.format.t r5 = new j$.time.format.t
            r5.<init>(r4, r3, r3, r14)
            goto L_0x03b4
        L_0x0391:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r12)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x03a3:
            r5 = 89
            if (r4 != r5) goto L_0x03bc
            j$.time.format.t r5 = new j$.time.format.t
            if (r3 != r14) goto L_0x03af
            r5.<init>(r4, r3, r3, r14)
            goto L_0x03b4
        L_0x03af:
            r7 = 19
            r5.<init>(r4, r3, r3, r7)
        L_0x03b4:
            r0.l(r5)
            goto L_0x0228
        L_0x03b9:
            int r3 = r3 + r10
            goto L_0x044e
        L_0x03bc:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Unknown pattern letter: "
            r2.<init>(r3)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x03d0:
            java.lang.String r5 = "'"
            r7 = 39
            if (r4 != r7) goto L_0x0421
            int r3 = r3 + 1
            r4 = r3
        L_0x03d9:
            int r8 = r17.length()
            if (r4 >= r8) goto L_0x03f6
            char r8 = r1.charAt(r4)
            if (r8 != r7) goto L_0x03f4
            int r8 = r4 + 1
            int r9 = r17.length()
            if (r8 >= r9) goto L_0x03f6
            char r9 = r1.charAt(r8)
            if (r9 != r7) goto L_0x03f6
            r4 = r8
        L_0x03f4:
            int r4 = r4 + r6
            goto L_0x03d9
        L_0x03f6:
            int r8 = r17.length()
            if (r4 >= r8) goto L_0x0415
            java.lang.String r3 = r1.substring(r3, r4)
            boolean r8 = r3.isEmpty()
            if (r8 == 0) goto L_0x040a
            r0.e(r7)
            goto L_0x0413
        L_0x040a:
            java.lang.String r7 = "''"
            java.lang.String r3 = r3.replace(r7, r5)
            r0.f(r3)
        L_0x0413:
            r3 = r4
            goto L_0x044e
        L_0x0415:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Pattern ends with an incomplete string literal: "
            java.lang.String r1 = r3.concat(r1)
            r2.<init>(r1)
            throw r2
        L_0x0421:
            r7 = 91
            if (r4 != r7) goto L_0x0429
            r16.p()
            goto L_0x044e
        L_0x0429:
            r7 = 93
            if (r4 != r7) goto L_0x043f
            j$.time.format.w r4 = r0.a
            j$.time.format.w r4 = r4.b
            if (r4 == 0) goto L_0x0437
            r16.o()
            goto L_0x044e
        L_0x0437:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Pattern invalid as it contains ] without previous ["
            r1.<init>(r2)
            throw r1
        L_0x043f:
            r7 = 123(0x7b, float:1.72E-43)
            if (r4 == r7) goto L_0x0451
            r7 = 125(0x7d, float:1.75E-43)
            if (r4 == r7) goto L_0x0451
            r7 = 35
            if (r4 == r7) goto L_0x0451
            r0.e(r4)
        L_0x044e:
            int r3 = r3 + r6
            goto L_0x000b
        L_0x0451:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Pattern includes reserved character: '"
            r2.<init>(r3)
            r2.append(r4)
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0468:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.w.i(java.lang.String):void");
    }

    public final void j(a aVar, HashMap hashMap) {
        Objects.requireNonNull(aVar, "field");
        Objects.requireNonNull(hashMap, "textLookup");
        LinkedHashMap linkedHashMap = new LinkedHashMap(hashMap);
        G g2 = G.FULL;
        d(new s(aVar, g2, new C0049b(new A(Collections.singletonMap(g2, linkedHashMap)))));
    }

    public final w k(q qVar, int i2, int i3, F f2) {
        if (i2 == i3 && f2 == F.NOT_NEGATIVE) {
            m(qVar, i3);
            return this;
        }
        Objects.requireNonNull(qVar, "field");
        Objects.requireNonNull(f2, "signStyle");
        if (i2 < 1 || i2 > 19) {
            throw new IllegalArgumentException("The minimum width must be from 1 to 19 inclusive but was " + i2);
        } else if (i3 < 1 || i3 > 19) {
            throw new IllegalArgumentException("The maximum width must be from 1 to 19 inclusive but was " + i3);
        } else if (i3 >= i2) {
            l(new k(qVar, i2, i3, f2));
            return this;
        } else {
            throw new IllegalArgumentException("The maximum width must exceed or equal the minimum width but " + i3 + " < " + i2);
        }
    }

    public final void m(q qVar, int i2) {
        Objects.requireNonNull(qVar, "field");
        if (i2 < 1 || i2 > 19) {
            throw new IllegalArgumentException("The width must be from 1 to 19 inclusive but was " + i2);
        }
        l(new k(qVar, i2, i2, F.NOT_NEGATIVE));
    }

    public final void n() {
        d(new u(h, "ZoneRegionId()"));
    }

    public final void o() {
        w wVar = this.a;
        if (wVar.b == null) {
            throw new IllegalStateException("Cannot call optionalEnd() as there was no previous call to optionalStart()");
        } else if (wVar.c.size() > 0) {
            w wVar2 = this.a;
            C0053f fVar = new C0053f(wVar2.c, wVar2.d);
            this.a = this.a.b;
            d(fVar);
        } else {
            this.a = this.a.b;
        }
    }

    public final void p() {
        w wVar = this.a;
        wVar.g = -1;
        this.a = new w(wVar);
    }

    public final void q() {
        d(r.INSENSITIVE);
    }

    public final void r() {
        d(r.SENSITIVE);
    }

    public final void s() {
        d(r.LENIENT);
    }

    public final void t() {
        d(r.STRICT);
    }

    /* access modifiers changed from: package-private */
    public final DateTimeFormatter u(E e2, t tVar) {
        return w(Locale.getDefault(), e2, tVar);
    }

    public final DateTimeFormatter v(Locale locale) {
        return w(locale, E.SMART, (t) null);
    }
}
