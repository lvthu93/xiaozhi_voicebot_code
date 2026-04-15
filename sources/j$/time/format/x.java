package j$.time.format;

import j$.time.ZoneId;
import j$.time.chrono.m;
import j$.time.chrono.t;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import j$.time.temporal.q;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

final class x {
    private DateTimeFormatter a;
    private boolean b = true;
    private boolean c = true;
    private final ArrayList d;
    private ArrayList e;

    x(DateTimeFormatter dateTimeFormatter) {
        ArrayList arrayList = new ArrayList();
        this.d = arrayList;
        this.e = null;
        this.a = dateTimeFormatter;
        arrayList.add(new D());
    }

    static boolean c(char c2, char c3) {
        return c2 == c3 || Character.toUpperCase(c2) == Character.toUpperCase(c3) || Character.toLowerCase(c2) == Character.toLowerCase(c3);
    }

    private D e() {
        ArrayList arrayList = this.d;
        return (D) arrayList.get(arrayList.size() - 1);
    }

    /* access modifiers changed from: package-private */
    public final void a(p pVar) {
        if (this.e == null) {
            this.e = new ArrayList();
        }
        this.e.add(pVar);
    }

    /* access modifiers changed from: package-private */
    public final boolean b(char c2, char c3) {
        return this.b ? c2 == c3 : c(c2, c3);
    }

    /* access modifiers changed from: package-private */
    public final x d() {
        x xVar = new x(this.a);
        xVar.b = this.b;
        xVar.c = this.c;
        return xVar;
    }

    /* access modifiers changed from: package-private */
    public final void f(boolean z) {
        ArrayList arrayList = this.d;
        arrayList.remove(z ? arrayList.size() - 2 : arrayList.size() - 1);
    }

    /* access modifiers changed from: package-private */
    public final C g() {
        return this.a.b();
    }

    /* access modifiers changed from: package-private */
    public final m h() {
        m mVar = e().c;
        if (mVar != null) {
            return mVar;
        }
        m a2 = this.a.a();
        return a2 == null ? t.d : a2;
    }

    /* access modifiers changed from: package-private */
    public final Locale i() {
        return this.a.c();
    }

    /* access modifiers changed from: package-private */
    public final Long j(a aVar) {
        return (Long) e().a.get(aVar);
    }

    /* access modifiers changed from: package-private */
    public final boolean k() {
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public final boolean l() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public final void m(boolean z) {
        this.b = z;
    }

    /* access modifiers changed from: package-private */
    public final void n(ZoneId zoneId) {
        Objects.requireNonNull(zoneId, "zone");
        e().b = zoneId;
    }

    /* access modifiers changed from: package-private */
    public final int o(q qVar, long j, int i, int i2) {
        Objects.requireNonNull(qVar, "field");
        Long l = (Long) e().a.put(qVar, Long.valueOf(j));
        return (l == null || l.longValue() == j) ? i2 : ~i;
    }

    /* access modifiers changed from: package-private */
    public final void p() {
        e().getClass();
    }

    /* access modifiers changed from: package-private */
    public final void q(boolean z) {
        this.c = z;
    }

    /* access modifiers changed from: package-private */
    public final void r() {
        ArrayList arrayList = this.d;
        D e2 = e();
        e2.getClass();
        D d2 = new D();
        d2.a.putAll(e2.a);
        d2.b = e2.b;
        d2.c = e2.c;
        arrayList.add(d2);
    }

    /* access modifiers changed from: package-private */
    public final boolean s(CharSequence charSequence, int i, CharSequence charSequence2, int i2, int i3) {
        if (i + i3 > charSequence.length() || i2 + i3 > charSequence2.length()) {
            return false;
        }
        if (this.b) {
            for (int i4 = 0; i4 < i3; i4++) {
                if (charSequence.charAt(i + i4) != charSequence2.charAt(i2 + i4)) {
                    return false;
                }
            }
            return true;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            char charAt = charSequence.charAt(i + i5);
            char charAt2 = charSequence2.charAt(i2 + i5);
            if (charAt != charAt2 && Character.toUpperCase(charAt) != Character.toUpperCase(charAt2) && Character.toLowerCase(charAt) != Character.toLowerCase(charAt2)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public final TemporalAccessor t(E e2, Set set) {
        D e3 = e();
        e3.c = h();
        ZoneId zoneId = e3.b;
        if (zoneId == null) {
            zoneId = this.a.d();
        }
        e3.b = zoneId;
        e3.j(e2, set);
        return e3;
    }

    public final String toString() {
        return e().toString();
    }
}
