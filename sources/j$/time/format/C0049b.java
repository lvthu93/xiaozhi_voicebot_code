package j$.time.format;

import j$.time.chrono.m;
import j$.time.temporal.q;
import java.util.Iterator;
import java.util.Locale;

/* renamed from: j$.time.format.b  reason: case insensitive filesystem */
final class C0049b extends B {
    final /* synthetic */ A e;

    C0049b(A a) {
        this.e = a;
    }

    public final String e(m mVar, q qVar, long j, G g, Locale locale) {
        return this.e.a(j, g);
    }

    public final String f(q qVar, long j, G g, Locale locale) {
        return this.e.a(j, g);
    }

    public final Iterator g(m mVar, q qVar, G g, Locale locale) {
        return this.e.b(g);
    }

    public final Iterator h(q qVar, G g, Locale locale) {
        return this.e.b(g);
    }
}
