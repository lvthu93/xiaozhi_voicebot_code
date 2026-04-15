package j$.util.stream;

import j$.lang.a;
import j$.util.H;
import j$.util.i0;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

class Y2 extends C0099e3 implements DoubleConsumer {
    Y2() {
    }

    Y2(int i) {
        super(i);
    }

    public void accept(double d) {
        t();
        int i = this.b;
        this.b = i + 1;
        ((double[]) this.e)[i] = d;
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }

    public final void forEach(Consumer consumer) {
        if (consumer instanceof DoubleConsumer) {
            g((DoubleConsumer) consumer);
        } else if (!X3.a) {
            spliterator().forEachRemaining(consumer);
        } else {
            X3.a(getClass(), "{0} calling SpinedBuffer.OfDouble.forEach(Consumer)");
            throw null;
        }
    }

    public final Iterator iterator() {
        return i0.f(spliterator());
    }

    public final Object newArray(int i) {
        return new double[i];
    }

    /* access modifiers changed from: protected */
    public final void o(Object obj, int i, int i2, Object obj2) {
        double[] dArr = (double[]) obj;
        DoubleConsumer doubleConsumer = (DoubleConsumer) obj2;
        while (i < i2) {
            doubleConsumer.accept(dArr[i]);
            i++;
        }
    }

    /* access modifiers changed from: protected */
    public final int p(Object obj) {
        return ((double[]) obj).length;
    }

    /* access modifiers changed from: protected */
    public final Object[] s() {
        return new double[8][];
    }

    public final String toString() {
        double[] dArr = (double[]) b();
        if (dArr.length < 200) {
            return String.format("%s[length=%d, chunks=%d]%s", new Object[]{getClass().getSimpleName(), Integer.valueOf(dArr.length), Integer.valueOf(this.c), Arrays.toString(dArr)});
        }
        return String.format("%s[length=%d, chunks=%d]%s...", new Object[]{getClass().getSimpleName(), Integer.valueOf(dArr.length), Integer.valueOf(this.c), Arrays.toString(Arrays.copyOf(dArr, 200))});
    }

    /* renamed from: u */
    public H spliterator() {
        return new X2(this, 0, this.c, 0, this.b);
    }
}
