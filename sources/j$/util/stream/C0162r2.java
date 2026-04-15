package j$.util.stream;

import j$.util.Objects;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;

/* renamed from: j$.util.stream.r2  reason: case insensitive filesystem */
public abstract class C0162r2 implements C0182v2 {
    protected final C0182v2 a;

    public C0162r2(C0182v2 v2Var) {
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

    public final /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public boolean e() {
        return this.a.e();
    }

    public void end() {
        this.a.end();
    }
}
