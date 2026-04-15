package j$.util.stream;

import j$.util.C0057b;
import j$.util.Objects;
import j$.util.U;
import j$.util.function.Consumer$CC;
import java.util.Comparator;
import java.util.function.Consumer;

final class T3 implements Consumer, U {
    int a = -2;
    Object b;
    C0104f3 c;

    T3(Object obj) {
        this.b = obj;
    }

    public final void accept(Object obj) {
        int i = this.a;
        if (i == 0) {
            this.b = obj;
            this.a = i + 1;
        } else if (i > 0) {
            if (this.c == null) {
                C0104f3 f3Var = new C0104f3();
                this.c = f3Var;
                f3Var.accept(this.b);
                this.a++;
            }
            this.c.accept(obj);
        } else {
            throw new IllegalStateException();
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* bridge */ /* synthetic */ int characteristics() {
        return 17488;
    }

    public final long estimateSize() {
        return (long) ((-this.a) - 1);
    }

    public final void forEachRemaining(Consumer consumer) {
        Objects.requireNonNull(consumer);
        if (this.a == -2) {
            consumer.accept(this.b);
            this.a = -1;
        }
    }

    public final Comparator getComparator() {
        throw new IllegalStateException();
    }

    public final /* synthetic */ long getExactSizeIfKnown() {
        return C0057b.d(this);
    }

    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return C0057b.e(this, i);
    }

    public final boolean tryAdvance(Consumer consumer) {
        Objects.requireNonNull(consumer);
        if (this.a != -2) {
            return false;
        }
        consumer.accept(this.b);
        this.a = -1;
        return true;
    }

    public final /* bridge */ /* synthetic */ U trySplit() {
        return null;
    }
}
