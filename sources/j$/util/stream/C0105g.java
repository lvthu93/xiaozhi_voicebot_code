package j$.util.stream;

import j$.util.S;
import j$.util.U;
import java.util.Iterator;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/* renamed from: j$.util.stream.g  reason: case insensitive filesystem */
public final /* synthetic */ class C0105g implements C0115i {
    public final /* synthetic */ BaseStream a;

    private /* synthetic */ C0105g(BaseStream baseStream) {
        this.a = baseStream;
    }

    public static /* synthetic */ C0115i k(BaseStream baseStream) {
        if (baseStream == null) {
            return null;
        }
        return baseStream instanceof C0110h ? ((C0110h) baseStream).a : baseStream instanceof DoubleStream ? G.k((DoubleStream) baseStream) : baseStream instanceof IntStream ? C0116i0.k((IntStream) baseStream) : baseStream instanceof LongStream ? C0165s0.k((LongStream) baseStream) : baseStream instanceof Stream ? C0109g3.k((Stream) baseStream) : new C0105g(baseStream);
    }

    public final /* synthetic */ void close() {
        this.a.close();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        if (obj instanceof C0105g) {
            obj = ((C0105g) obj).a;
        }
        return this.a.equals(obj);
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    public final /* synthetic */ Iterator iterator() {
        return this.a.iterator();
    }

    public final /* synthetic */ C0115i onClose(Runnable runnable) {
        return k(this.a.onClose(runnable));
    }

    public final /* synthetic */ C0115i parallel() {
        return k(this.a.parallel());
    }

    public final /* synthetic */ C0115i sequential() {
        return k(this.a.sequential());
    }

    public final /* synthetic */ U spliterator() {
        return S.a(this.a.spliterator());
    }

    public final /* synthetic */ C0115i unordered() {
        return k(this.a.unordered());
    }
}
