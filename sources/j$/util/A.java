package j$.util;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

public final /* synthetic */ class A implements C, Iterator {
    public final /* synthetic */ PrimitiveIterator.OfLong a;

    private /* synthetic */ A(PrimitiveIterator.OfLong ofLong) {
        this.a = ofLong;
    }

    public static /* synthetic */ C a(PrimitiveIterator.OfLong ofLong) {
        if (ofLong == null) {
            return null;
        }
        return ofLong instanceof B ? ((B) ofLong).a : new A(ofLong);
    }

    public final /* synthetic */ boolean equals(Object obj) {
        if (obj instanceof A) {
            obj = ((A) obj).a;
        }
        return this.a.equals(obj);
    }

    public final /* synthetic */ void forEachRemaining(Object obj) {
        this.a.forEachRemaining(obj);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.a.forEachRemaining(consumer);
    }

    public final /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
        this.a.forEachRemaining(longConsumer);
    }

    public final /* synthetic */ boolean hasNext() {
        return this.a.hasNext();
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ long nextLong() {
        return this.a.nextLong();
    }

    public final /* synthetic */ void remove() {
        this.a.remove();
    }
}
