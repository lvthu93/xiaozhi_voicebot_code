package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;

public final /* synthetic */ class Q3 implements C0182v2 {
    public final /* synthetic */ int a;
    public final /* synthetic */ Consumer b;

    public /* synthetic */ Q3(Consumer consumer, int i) {
        this.a = i;
        this.b = consumer;
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

    public final void accept(Object obj) {
        int i = this.a;
        Consumer consumer = this.b;
        switch (i) {
            case 0:
                ((C0104f3) consumer).accept(obj);
                return;
            default:
                consumer.accept(obj);
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
}
