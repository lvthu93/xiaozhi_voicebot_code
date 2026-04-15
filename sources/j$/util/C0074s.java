package j$.util;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.s  reason: case insensitive filesystem */
public final /* synthetic */ class C0074s implements C0203u, Iterator {
    public final /* synthetic */ PrimitiveIterator.OfDouble a;

    private /* synthetic */ C0074s(PrimitiveIterator.OfDouble ofDouble) {
        this.a = ofDouble;
    }

    public static /* synthetic */ C0203u a(PrimitiveIterator.OfDouble ofDouble) {
        if (ofDouble == null) {
            return null;
        }
        return ofDouble instanceof C0202t ? ((C0202t) ofDouble).a : new C0074s(ofDouble);
    }

    public final /* synthetic */ boolean equals(Object obj) {
        if (obj instanceof C0074s) {
            obj = ((C0074s) obj).a;
        }
        return this.a.equals(obj);
    }

    public final /* synthetic */ void forEachRemaining(Object obj) {
        this.a.forEachRemaining(obj);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.a.forEachRemaining(consumer);
    }

    public final /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
        this.a.forEachRemaining(doubleConsumer);
    }

    public final /* synthetic */ boolean hasNext() {
        return this.a.hasNext();
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ double nextDouble() {
        return this.a.nextDouble();
    }

    public final /* synthetic */ void remove() {
        this.a.remove();
    }
}
