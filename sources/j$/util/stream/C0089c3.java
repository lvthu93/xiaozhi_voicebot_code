package j$.util.stream;

import j$.lang.a;
import j$.util.N;
import j$.util.i0;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.c3  reason: case insensitive filesystem */
class C0089c3 extends C0099e3 implements LongConsumer {
    C0089c3() {
    }

    C0089c3(int i) {
        super(i);
    }

    public void accept(long j) {
        t();
        int i = this.b;
        this.b = i + 1;
        ((long[]) this.e)[i] = j;
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
    }

    public final void forEach(Consumer consumer) {
        if (consumer instanceof LongConsumer) {
            g((LongConsumer) consumer);
        } else if (!X3.a) {
            spliterator().forEachRemaining(consumer);
        } else {
            X3.a(getClass(), "{0} calling SpinedBuffer.OfLong.forEach(Consumer)");
            throw null;
        }
    }

    public final Iterator iterator() {
        return i0.h(spliterator());
    }

    public final Object newArray(int i) {
        return new long[i];
    }

    /* access modifiers changed from: protected */
    public final void o(Object obj, int i, int i2, Object obj2) {
        long[] jArr = (long[]) obj;
        LongConsumer longConsumer = (LongConsumer) obj2;
        while (i < i2) {
            longConsumer.accept(jArr[i]);
            i++;
        }
    }

    /* access modifiers changed from: protected */
    public final int p(Object obj) {
        return ((long[]) obj).length;
    }

    /* access modifiers changed from: protected */
    public final Object[] s() {
        return new long[8][];
    }

    public final String toString() {
        long[] jArr = (long[]) b();
        if (jArr.length < 200) {
            return String.format("%s[length=%d, chunks=%d]%s", new Object[]{getClass().getSimpleName(), Integer.valueOf(jArr.length), Integer.valueOf(this.c), Arrays.toString(jArr)});
        }
        return String.format("%s[length=%d, chunks=%d]%s...", new Object[]{getClass().getSimpleName(), Integer.valueOf(jArr.length), Integer.valueOf(this.c), Arrays.toString(Arrays.copyOf(jArr, 200))});
    }

    /* renamed from: u */
    public N spliterator() {
        return new C0084b3(this, 0, this.c, 0, this.b);
    }
}
