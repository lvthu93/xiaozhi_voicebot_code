package j$.util.stream;

import j$.lang.a;
import j$.util.Objects;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* renamed from: j$.util.stream.p2  reason: case insensitive filesystem */
public abstract class C0153p2 implements C0172t2 {
    protected final C0182v2 a;

    public C0153p2(C0182v2 v2Var) {
        this.a = (C0182v2) Objects.requireNonNull(v2Var);
    }

    public final /* synthetic */ void accept(double d) {
        D0.z();
        throw null;
    }

    public final /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        d((Integer) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a.c(this, intConsumer);
    }

    public final /* synthetic */ void d(Integer num) {
        D0.C(this, num);
    }

    public boolean e() {
        return this.a.e();
    }

    public void end() {
        this.a.end();
    }
}
