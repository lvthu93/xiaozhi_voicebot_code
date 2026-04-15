package j$.util.stream;

import j$.lang.a;
import j$.util.Objects;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.q2  reason: case insensitive filesystem */
public abstract class C0158q2 implements C0177u2 {
    protected final C0182v2 a;

    public C0158q2(C0182v2 v2Var) {
        this.a = (C0182v2) Objects.requireNonNull(v2Var);
    }

    public final /* synthetic */ void accept(double d) {
        D0.z();
        throw null;
    }

    public final /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        l((Long) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
    }

    public boolean e() {
        return this.a.e();
    }

    public void end() {
        this.a.end();
    }

    public final /* synthetic */ void l(Long l) {
        D0.E(this, l);
    }
}
