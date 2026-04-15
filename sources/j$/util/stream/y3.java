package j$.util.stream;

import j$.lang.a;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

public final /* synthetic */ class y3 implements C0177u2 {
    public final /* synthetic */ int a;
    public final /* synthetic */ LongConsumer b;

    public /* synthetic */ y3(LongConsumer longConsumer, int i) {
        this.a = i;
        this.b = longConsumer;
    }

    public final /* synthetic */ LongConsumer a(LongConsumer longConsumer) {
        switch (this.a) {
            case 0:
                return a.d(this, longConsumer);
            default:
                return a.d(this, longConsumer);
        }
    }

    public final /* synthetic */ void accept(double d) {
        switch (this.a) {
            case 0:
                D0.z();
                throw null;
            default:
                D0.z();
                throw null;
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

    public final void accept(long j) {
        int i = this.a;
        LongConsumer longConsumer = this.b;
        switch (i) {
            case 0:
                ((C0089c3) longConsumer).accept(j);
                return;
            default:
                longConsumer.accept(j);
                return;
        }
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        switch (this.a) {
            case 0:
                l((Long) obj);
                return;
            default:
                l((Long) obj);
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

    public final /* synthetic */ void l(Long l) {
        switch (this.a) {
            case 0:
                D0.E(this, l);
                return;
            default:
                D0.E(this, l);
                return;
        }
    }
}
