package j$.util.stream;

import j$.lang.a;
import j$.util.K;
import j$.util.i0;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* renamed from: j$.util.stream.a3  reason: case insensitive filesystem */
class C0079a3 extends C0099e3 implements IntConsumer {
    C0079a3() {
    }

    C0079a3(int i) {
        super(i);
    }

    public void accept(int i) {
        t();
        int i2 = this.b;
        this.b = i2 + 1;
        ((int[]) this.e)[i2] = i;
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a.c(this, intConsumer);
    }

    public final void forEach(Consumer consumer) {
        if (consumer instanceof IntConsumer) {
            g((IntConsumer) consumer);
        } else if (!X3.a) {
            spliterator().forEachRemaining(consumer);
        } else {
            X3.a(getClass(), "{0} calling SpinedBuffer.OfInt.forEach(Consumer)");
            throw null;
        }
    }

    public final Iterator iterator() {
        return i0.g(spliterator());
    }

    public final Object newArray(int i) {
        return new int[i];
    }

    /* access modifiers changed from: protected */
    public final void o(Object obj, int i, int i2, Object obj2) {
        int[] iArr = (int[]) obj;
        IntConsumer intConsumer = (IntConsumer) obj2;
        while (i < i2) {
            intConsumer.accept(iArr[i]);
            i++;
        }
    }

    /* access modifiers changed from: protected */
    public final int p(Object obj) {
        return ((int[]) obj).length;
    }

    /* access modifiers changed from: protected */
    public final Object[] s() {
        return new int[8][];
    }

    public final String toString() {
        int[] iArr = (int[]) b();
        if (iArr.length < 200) {
            return String.format("%s[length=%d, chunks=%d]%s", new Object[]{getClass().getSimpleName(), Integer.valueOf(iArr.length), Integer.valueOf(this.c), Arrays.toString(iArr)});
        }
        return String.format("%s[length=%d, chunks=%d]%s...", new Object[]{getClass().getSimpleName(), Integer.valueOf(iArr.length), Integer.valueOf(this.c), Arrays.toString(Arrays.copyOf(iArr, 200))});
    }

    /* renamed from: u */
    public K spliterator() {
        return new Z2(this, 0, this.c, 0, this.b);
    }
}
