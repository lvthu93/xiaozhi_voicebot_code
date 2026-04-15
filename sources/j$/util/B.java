package j$.util;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

public final /* synthetic */ class B implements PrimitiveIterator.OfLong {
    public final /* synthetic */ C a;

    private /* synthetic */ B(C c) {
        this.a = c;
    }

    public static /* synthetic */ PrimitiveIterator.OfLong a(C c) {
        if (c == null) {
            return null;
        }
        return c instanceof A ? ((A) c).a : new B(c);
    }

    public final /* synthetic */ boolean equals(Object obj) {
        C c = this.a;
        if (obj instanceof B) {
            obj = ((B) obj).a;
        }
        return c.equals(obj);
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
