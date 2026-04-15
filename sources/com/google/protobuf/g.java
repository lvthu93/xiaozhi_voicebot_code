package com.google.protobuf;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.protobuf.q;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public final class g implements y9 {
    public final f a;
    public int b;
    public int c;
    public int d = 0;

    public g(f fVar) {
        Charset charset = p.a;
        if (fVar != null) {
            this.a = fVar;
            fVar.e = this;
            return;
        }
        throw new NullPointerException("input");
    }

    public static void ac(int i) throws IOException {
        if ((i & 3) != 0) {
            throw q.g();
        }
    }

    public static void ad(int i) throws IOException {
        if ((i & 7) != 0) {
            throw q.g();
        }
    }

    public final int a() throws IOException {
        int i = this.d;
        if (i != 0) {
            this.b = i;
            this.d = 0;
        } else {
            this.b = this.a.aa();
        }
        int i2 = this.b;
        if (i2 == 0 || i2 == this.c) {
            return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }
        return i2 >>> 3;
    }

    public final void aa(int i) throws IOException {
        if ((this.b & 7) != i) {
            throw q.d();
        }
    }

    public final boolean ab() throws IOException {
        int i;
        f fVar = this.a;
        if (fVar.e() || (i = this.b) == this.c) {
            return false;
        }
        return fVar.ad(i);
    }

    public final <T> void b(T t, ac<T> acVar, i iVar) throws IOException {
        int i = this.c;
        this.c = ((this.b >>> 3) << 3) | 4;
        try {
            acVar.e(t, this, iVar);
            if (this.b != this.c) {
                throw q.g();
            }
        } finally {
            this.c = i;
        }
    }

    public final <T> void c(T t, ac<T> acVar, i iVar) throws IOException {
        f fVar = this.a;
        int ab = fVar.ab();
        if (fVar.a + fVar.b < fVar.c) {
            int j = fVar.j(ab);
            fVar.a++;
            acVar.e(t, this, iVar);
            fVar.a(0);
            fVar.a--;
            fVar.i(j);
            return;
        }
        throw new q("Protocol message had too many levels of nesting.  May be malicious.  Use setRecursionLimit() to increase the recursion depth limit.");
    }

    public final void d(List<Boolean> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof e;
        f fVar = this.a;
        if (z) {
            e eVar = (e) list;
            int i = this.b & 7;
            if (i == 0) {
                do {
                    eVar.b(fVar.k());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else if (i == 2) {
                int d2 = fVar.d() + fVar.ab();
                do {
                    eVar.b(fVar.k());
                } while (fVar.d() < d2);
                z(d2);
            } else {
                throw q.d();
            }
        } else {
            int i2 = this.b & 7;
            if (i2 == 0) {
                do {
                    list.add(Boolean.valueOf(fVar.k()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else if (i2 == 2) {
                int d3 = fVar.d() + fVar.ab();
                do {
                    list.add(Boolean.valueOf(fVar.k()));
                } while (fVar.d() < d3);
                z(d3);
            } else {
                throw q.d();
            }
        }
    }

    public final cp e() throws IOException {
        aa(2);
        return this.a.l();
    }

    public final void f(List<cp> list) throws IOException {
        int aa;
        if ((this.b & 7) == 2) {
            do {
                list.add(e());
                f fVar = this.a;
                if (!fVar.e()) {
                    aa = fVar.aa();
                } else {
                    return;
                }
            } while (aa == this.b);
            this.d = aa;
            return;
        }
        throw q.d();
    }

    public final void g(List<Double> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof h;
        f fVar = this.a;
        if (z) {
            h hVar = (h) list;
            int i = this.b & 7;
            if (i == 1) {
                do {
                    hVar.b(fVar.m());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else if (i == 2) {
                int ab = fVar.ab();
                ad(ab);
                int d2 = fVar.d() + ab;
                do {
                    hVar.b(fVar.m());
                } while (fVar.d() < d2);
            } else {
                int i2 = q.f;
                throw new q.a();
            }
        } else {
            int i3 = this.b & 7;
            if (i3 == 1) {
                do {
                    list.add(Double.valueOf(fVar.m()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else if (i3 == 2) {
                int ab2 = fVar.ab();
                ad(ab2);
                int d3 = fVar.d() + ab2;
                do {
                    list.add(Double.valueOf(fVar.m()));
                } while (fVar.d() < d3);
            } else {
                int i4 = q.f;
                throw new q.a();
            }
        }
    }

    public final void h(List<Integer> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof o;
        f fVar = this.a;
        if (z) {
            o oVar = (o) list;
            int i = this.b & 7;
            if (i == 0) {
                do {
                    oVar.b(fVar.n());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else if (i == 2) {
                int d2 = fVar.d() + fVar.ab();
                do {
                    oVar.b(fVar.n());
                } while (fVar.d() < d2);
                z(d2);
            } else {
                throw q.d();
            }
        } else {
            int i2 = this.b & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(fVar.n()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else if (i2 == 2) {
                int d3 = fVar.d() + fVar.ab();
                do {
                    list.add(Integer.valueOf(fVar.n()));
                } while (fVar.d() < d3);
                z(d3);
            } else {
                throw q.d();
            }
        }
    }

    public final int i() throws IOException {
        aa(5);
        return this.a.o();
    }

    public final void j(List<Integer> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof o;
        f fVar = this.a;
        if (z) {
            o oVar = (o) list;
            int i = this.b & 7;
            if (i == 2) {
                int ab = fVar.ab();
                ac(ab);
                int d2 = fVar.d() + ab;
                do {
                    oVar.b(fVar.o());
                } while (fVar.d() < d2);
            } else if (i == 5) {
                do {
                    oVar.b(fVar.o());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else {
                int i2 = q.f;
                throw new q.a();
            }
        } else {
            int i3 = this.b & 7;
            if (i3 == 2) {
                int ab2 = fVar.ab();
                ac(ab2);
                int d3 = fVar.d() + ab2;
                do {
                    list.add(Integer.valueOf(fVar.o()));
                } while (fVar.d() < d3);
            } else if (i3 == 5) {
                do {
                    list.add(Integer.valueOf(fVar.o()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else {
                int i4 = q.f;
                throw new q.a();
            }
        }
    }

    public final long k() throws IOException {
        aa(1);
        return this.a.p();
    }

    public final void l(List<Long> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof u;
        f fVar = this.a;
        if (z) {
            u uVar = (u) list;
            int i = this.b & 7;
            if (i == 1) {
                do {
                    uVar.b(fVar.p());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else if (i == 2) {
                int ab = fVar.ab();
                ad(ab);
                int d2 = fVar.d() + ab;
                do {
                    uVar.b(fVar.p());
                } while (fVar.d() < d2);
            } else {
                int i2 = q.f;
                throw new q.a();
            }
        } else {
            int i3 = this.b & 7;
            if (i3 == 1) {
                do {
                    list.add(Long.valueOf(fVar.p()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else if (i3 == 2) {
                int ab2 = fVar.ab();
                ad(ab2);
                int d3 = fVar.d() + ab2;
                do {
                    list.add(Long.valueOf(fVar.p()));
                } while (fVar.d() < d3);
            } else {
                int i4 = q.f;
                throw new q.a();
            }
        }
    }

    public final void m(List<Float> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof m;
        f fVar = this.a;
        if (z) {
            m mVar = (m) list;
            int i = this.b & 7;
            if (i == 2) {
                int ab = fVar.ab();
                ac(ab);
                int d2 = fVar.d() + ab;
                do {
                    mVar.b(fVar.q());
                } while (fVar.d() < d2);
            } else if (i == 5) {
                do {
                    mVar.b(fVar.q());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else {
                int i2 = q.f;
                throw new q.a();
            }
        } else {
            int i3 = this.b & 7;
            if (i3 == 2) {
                int ab2 = fVar.ab();
                ac(ab2);
                int d3 = fVar.d() + ab2;
                do {
                    list.add(Float.valueOf(fVar.q()));
                } while (fVar.d() < d3);
            } else if (i3 == 5) {
                do {
                    list.add(Float.valueOf(fVar.q()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else {
                int i4 = q.f;
                throw new q.a();
            }
        }
    }

    public final int n() throws IOException {
        aa(0);
        return this.a.r();
    }

    public final void o(List<Integer> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof o;
        f fVar = this.a;
        if (z) {
            o oVar = (o) list;
            int i = this.b & 7;
            if (i == 0) {
                do {
                    oVar.b(fVar.r());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else if (i == 2) {
                int d2 = fVar.d() + fVar.ab();
                do {
                    oVar.b(fVar.r());
                } while (fVar.d() < d2);
                z(d2);
            } else {
                throw q.d();
            }
        } else {
            int i2 = this.b & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(fVar.r()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else if (i2 == 2) {
                int d3 = fVar.d() + fVar.ab();
                do {
                    list.add(Integer.valueOf(fVar.r()));
                } while (fVar.d() < d3);
                z(d3);
            } else {
                throw q.d();
            }
        }
    }

    public final long p() throws IOException {
        aa(0);
        return this.a.s();
    }

    public final void q(List<Long> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof u;
        f fVar = this.a;
        if (z) {
            u uVar = (u) list;
            int i = this.b & 7;
            if (i == 0) {
                do {
                    uVar.b(fVar.s());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else if (i == 2) {
                int d2 = fVar.d() + fVar.ab();
                do {
                    uVar.b(fVar.s());
                } while (fVar.d() < d2);
                z(d2);
            } else {
                throw q.d();
            }
        } else {
            int i2 = this.b & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(fVar.s()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else if (i2 == 2) {
                int d3 = fVar.d() + fVar.ab();
                do {
                    list.add(Long.valueOf(fVar.s()));
                } while (fVar.d() < d3);
                z(d3);
            } else {
                throw q.d();
            }
        }
    }

    public final void r(List<Integer> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof o;
        f fVar = this.a;
        if (z) {
            o oVar = (o) list;
            int i = this.b & 7;
            if (i == 2) {
                int ab = fVar.ab();
                ac(ab);
                int d2 = fVar.d() + ab;
                do {
                    oVar.b(fVar.u());
                } while (fVar.d() < d2);
            } else if (i == 5) {
                do {
                    oVar.b(fVar.u());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else {
                int i2 = q.f;
                throw new q.a();
            }
        } else {
            int i3 = this.b & 7;
            if (i3 == 2) {
                int ab2 = fVar.ab();
                ac(ab2);
                int d3 = fVar.d() + ab2;
                do {
                    list.add(Integer.valueOf(fVar.u()));
                } while (fVar.d() < d3);
            } else if (i3 == 5) {
                do {
                    list.add(Integer.valueOf(fVar.u()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else {
                int i4 = q.f;
                throw new q.a();
            }
        }
    }

    public final void s(List<Long> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof u;
        f fVar = this.a;
        if (z) {
            u uVar = (u) list;
            int i = this.b & 7;
            if (i == 1) {
                do {
                    uVar.b(fVar.v());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else if (i == 2) {
                int ab = fVar.ab();
                ad(ab);
                int d2 = fVar.d() + ab;
                do {
                    uVar.b(fVar.v());
                } while (fVar.d() < d2);
            } else {
                int i2 = q.f;
                throw new q.a();
            }
        } else {
            int i3 = this.b & 7;
            if (i3 == 1) {
                do {
                    list.add(Long.valueOf(fVar.v()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else if (i3 == 2) {
                int ab2 = fVar.ab();
                ad(ab2);
                int d3 = fVar.d() + ab2;
                do {
                    list.add(Long.valueOf(fVar.v()));
                } while (fVar.d() < d3);
            } else {
                int i4 = q.f;
                throw new q.a();
            }
        }
    }

    public final void t(List<Integer> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof o;
        f fVar = this.a;
        if (z) {
            o oVar = (o) list;
            int i = this.b & 7;
            if (i == 0) {
                do {
                    oVar.b(fVar.w());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else if (i == 2) {
                int d2 = fVar.d() + fVar.ab();
                do {
                    oVar.b(fVar.w());
                } while (fVar.d() < d2);
                z(d2);
            } else {
                throw q.d();
            }
        } else {
            int i2 = this.b & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(fVar.w()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else if (i2 == 2) {
                int d3 = fVar.d() + fVar.ab();
                do {
                    list.add(Integer.valueOf(fVar.w()));
                } while (fVar.d() < d3);
                z(d3);
            } else {
                throw q.d();
            }
        }
    }

    public final void u(List<Long> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof u;
        f fVar = this.a;
        if (z) {
            u uVar = (u) list;
            int i = this.b & 7;
            if (i == 0) {
                do {
                    uVar.b(fVar.x());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else if (i == 2) {
                int d2 = fVar.d() + fVar.ab();
                do {
                    uVar.b(fVar.x());
                } while (fVar.d() < d2);
                z(d2);
            } else {
                throw q.d();
            }
        } else {
            int i2 = this.b & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(fVar.x()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else if (i2 == 2) {
                int d3 = fVar.d() + fVar.ab();
                do {
                    list.add(Long.valueOf(fVar.x()));
                } while (fVar.d() < d3);
                z(d3);
            } else {
                throw q.d();
            }
        }
    }

    public final void v(List<String> list, boolean z) throws IOException {
        String str;
        int aa;
        int aa2;
        if ((this.b & 7) == 2) {
            boolean z2 = list instanceof e4;
            f fVar = this.a;
            if (!z2 || z) {
                do {
                    if (z) {
                        aa(2);
                        str = fVar.z();
                    } else {
                        aa(2);
                        str = fVar.y();
                    }
                    list.add(str);
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
                return;
            }
            e4 e4Var = (e4) list;
            do {
                e();
                e4Var.i();
                if (!fVar.e()) {
                    aa2 = fVar.aa();
                } else {
                    return;
                }
            } while (aa2 == this.b);
            this.d = aa2;
            return;
        }
        int i = q.f;
        throw new q.a();
    }

    public final int w() throws IOException {
        aa(0);
        return this.a.ab();
    }

    public final void x(List<Integer> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof o;
        f fVar = this.a;
        if (z) {
            o oVar = (o) list;
            int i = this.b & 7;
            if (i == 0) {
                do {
                    oVar.b(fVar.ab());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else if (i == 2) {
                int d2 = fVar.d() + fVar.ab();
                do {
                    oVar.b(fVar.ab());
                } while (fVar.d() < d2);
                z(d2);
            } else {
                throw q.d();
            }
        } else {
            int i2 = this.b & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(fVar.ab()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else if (i2 == 2) {
                int d3 = fVar.d() + fVar.ab();
                do {
                    list.add(Integer.valueOf(fVar.ab()));
                } while (fVar.d() < d3);
                z(d3);
            } else {
                throw q.d();
            }
        }
    }

    public final void y(List<Long> list) throws IOException {
        int aa;
        int aa2;
        boolean z = list instanceof u;
        f fVar = this.a;
        if (z) {
            u uVar = (u) list;
            int i = this.b & 7;
            if (i == 0) {
                do {
                    uVar.b(fVar.ac());
                    if (!fVar.e()) {
                        aa2 = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa2 == this.b);
                this.d = aa2;
            } else if (i == 2) {
                int d2 = fVar.d() + fVar.ab();
                do {
                    uVar.b(fVar.ac());
                } while (fVar.d() < d2);
                z(d2);
            } else {
                throw q.d();
            }
        } else {
            int i2 = this.b & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(fVar.ac()));
                    if (!fVar.e()) {
                        aa = fVar.aa();
                    } else {
                        return;
                    }
                } while (aa == this.b);
                this.d = aa;
            } else if (i2 == 2) {
                int d3 = fVar.d() + fVar.ab();
                do {
                    list.add(Long.valueOf(fVar.ac()));
                } while (fVar.d() < d3);
                z(d3);
            } else {
                throw q.d();
            }
        }
    }

    public final void z(int i) throws IOException {
        if (this.a.d() != i) {
            throw q.h();
        }
    }
}
