package j$.util.stream;

import j$.lang.a;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.stream.u3  reason: case insensitive filesystem */
public final /* synthetic */ class C0178u3 implements C0167s2 {
    public final /* synthetic */ int a;
    public final /* synthetic */ DoubleConsumer b;

    public /* synthetic */ C0178u3(DoubleConsumer doubleConsumer, int i) {
        this.a = i;
        this.b = doubleConsumer;
    }

    public final /* synthetic */ DoubleConsumer a(DoubleConsumer doubleConsumer) {
        switch (this.a) {
            case 0:
                return a.b(this, doubleConsumer);
            default:
                return a.b(this, doubleConsumer);
        }
    }

    public final void accept(double d) {
        int i = this.a;
        DoubleConsumer doubleConsumer = this.b;
        switch (i) {
            case 0:
                ((Y2) doubleConsumer).accept(d);
                return;
            default:
                doubleConsumer.accept(d);
                return;
        }
    }

    public final /* synthetic */ void accept(int i) {
        switch (this.a) {
            case 0:
                D0.G();
                throw null;
            default:
                D0.G();
                throw null;
        }
    }

    public final /* synthetic */ void accept(long j) {
        switch (this.a) {
            case 0:
                D0.H();
                throw null;
            default:
                D0.H();
                throw null;
        }
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        switch (this.a) {
            case 0:
                m((Double) obj);
                return;
            default:
                m((Double) obj);
                return;
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
            case 0:
                return Consumer$CC.$default$andThen(this, consumer);
            default:
                return Consumer$CC.$default$andThen(this, consumer);
        }
    }

    public final /* synthetic */ void c(long j) {
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }

    public final /* synthetic */ void m(Double d) {
        switch (this.a) {
            case 0:
                D0.A(this, d);
                return;
            default:
                D0.A(this, d);
                return;
        }
    }
}
