package j$.util.stream;

import j$.lang.a;
import j$.util.function.Consumer$CC;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.t1  reason: case insensitive filesystem */
final class C0171t1 extends C0166s1 implements G0 {
    C0171t1(long j) {
        super(j);
    }

    public final /* synthetic */ void accept(double d) {
        D0.z();
        throw null;
    }

    public final /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public final void accept(long j) {
        int i = this.b;
        long[] jArr = this.a;
        if (i < jArr.length) {
            this.b = i + 1;
            jArr[i] = j;
            return;
        }
        throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", new Object[]{Integer.valueOf(jArr.length)}));
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

    public final K0 build() {
        int i = this.b;
        long[] jArr = this.a;
        if (i >= jArr.length) {
            return this;
        }
        throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", new Object[]{Integer.valueOf(this.b), Integer.valueOf(jArr.length)}));
    }

    public final void c(long j) {
        long[] jArr = this.a;
        if (j == ((long) jArr.length)) {
            this.b = 0;
        } else {
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", new Object[]{Long.valueOf(j), Integer.valueOf(jArr.length)}));
        }
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final void end() {
        int i = this.b;
        long[] jArr = this.a;
        if (i < jArr.length) {
            throw new IllegalStateException(String.format("End size %d is less than fixed size %d", new Object[]{Integer.valueOf(this.b), Integer.valueOf(jArr.length)}));
        }
    }

    public final /* synthetic */ void l(Long l) {
        D0.E(this, l);
    }

    public final String toString() {
        long[] jArr = this.a;
        return String.format("LongFixedNodeBuilder[%d][%s]", new Object[]{Integer.valueOf(jArr.length - this.b), Arrays.toString(jArr)});
    }
}
