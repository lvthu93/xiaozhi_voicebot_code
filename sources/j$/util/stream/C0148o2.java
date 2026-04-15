package j$.util.stream;

import j$.lang.a;
import j$.util.Objects;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.stream.o2  reason: case insensitive filesystem */
public abstract class C0148o2 implements C0167s2 {
    protected final C0182v2 a;

    public C0148o2(C0182v2 v2Var) {
        this.a = (C0182v2) Objects.requireNonNull(v2Var);
    }

    public final /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public final /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        m((Double) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }

    public boolean e() {
        return this.a.e();
    }

    public void end() {
        this.a.end();
    }

    public final /* synthetic */ void m(Double d) {
        D0.A(this, d);
    }
}
