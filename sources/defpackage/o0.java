package defpackage;

import com.google.protobuf.a;
import com.google.protobuf.ac;
import com.google.protobuf.p;
import com.google.protobuf.x;
import java.io.IOException;
import java.nio.charset.Charset;

/* renamed from: o0  reason: default package */
public final class o0 implements jf {
    public final n0 a;

    public o0(n0 n0Var) {
        Charset charset = p.a;
        if (n0Var != null) {
            this.a = n0Var;
            n0Var.c = this;
            return;
        }
        throw new NullPointerException("output");
    }

    public final void a(int i, boolean z) throws IOException {
        this.a.z(i, z);
    }

    public final void b(int i, cp cpVar) throws IOException {
        this.a.aa(i, cpVar);
    }

    public final void c(int i, double d) throws IOException {
        n0 n0Var = this.a;
        n0Var.getClass();
        n0Var.ad(i, Double.doubleToRawLongBits(d));
    }

    @Deprecated
    public final void d(int i) throws IOException {
        this.a.ak(i, 4);
    }

    public final void e(int i, int i2) throws IOException {
        this.a.af(i, i2);
    }

    public final void f(int i, int i2) throws IOException {
        this.a.ab(i, i2);
    }

    public final void g(int i, long j) throws IOException {
        this.a.ad(i, j);
    }

    public final void h(float f, int i) throws IOException {
        n0 n0Var = this.a;
        n0Var.getClass();
        n0Var.ab(i, Float.floatToRawIntBits(f));
    }

    public final void i(int i, ac acVar, Object obj) throws IOException {
        n0 n0Var = this.a;
        n0Var.ak(i, 3);
        acVar.g((a) obj, this);
        n0Var.ak(i, 4);
    }

    public final void j(int i, int i2) throws IOException {
        this.a.af(i, i2);
    }

    public final void k(int i, long j) throws IOException {
        this.a.an(i, j);
    }

    public final void l(int i, ac acVar, Object obj) throws IOException {
        a aVar = (a) obj;
        n0 n0Var = this.a;
        n0Var.ak(i, 2);
        n0Var.am(aVar.getSerializedSize(acVar));
        acVar.g(aVar, this);
    }

    public final void m(int i, Object obj) throws IOException {
        boolean z = obj instanceof cp;
        n0 n0Var = this.a;
        if (z) {
            n0Var.ai(i, (cp) obj);
        } else {
            n0Var.ah(i, (x) obj);
        }
    }

    public final void n(int i, int i2) throws IOException {
        this.a.ab(i, i2);
    }

    public final void o(int i, long j) throws IOException {
        this.a.ad(i, j);
    }

    public final void p(int i, int i2) throws IOException {
        this.a.al(i, (i2 >> 31) ^ (i2 << 1));
    }

    public final void q(int i, long j) throws IOException {
        this.a.an(i, (j >> 63) ^ (j << 1));
    }

    @Deprecated
    public final void r(int i) throws IOException {
        this.a.ak(i, 3);
    }

    public final void s(int i, int i2) throws IOException {
        this.a.al(i, i2);
    }

    public final void t(int i, long j) throws IOException {
        this.a.an(i, j);
    }
}
