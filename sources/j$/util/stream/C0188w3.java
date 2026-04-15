package j$.util.stream;

import j$.lang.a;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* renamed from: j$.util.stream.w3  reason: case insensitive filesystem */
public final /* synthetic */ class C0188w3 implements C0172t2 {
    public final /* synthetic */ int a;
    public final /* synthetic */ IntConsumer b;

    public /* synthetic */ C0188w3(IntConsumer intConsumer, int i) {
        this.a = i;
        this.b = intConsumer;
    }

    public final /* synthetic */ IntConsumer a(IntConsumer intConsumer) {
        switch (this.a) {
            case 0:
                return a.c(this, intConsumer);
            default:
                return a.c(this, intConsumer);
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

    public final void accept(int i) {
        int i2 = this.a;
        IntConsumer intConsumer = this.b;
        switch (i2) {
            case 0:
                ((C0079a3) intConsumer).accept(i);
                return;
            default:
                intConsumer.accept(i);
                return;
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
                d((Integer) obj);
                return;
            default:
                d((Integer) obj);
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

    public final /* synthetic */ void d(Integer num) {
        switch (this.a) {
            case 0:
                D0.C(this, num);
                return;
            default:
                D0.C(this, num);
                return;
        }
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }
}
