package j$.util;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* renamed from: j$.util.w  reason: case insensitive filesystem */
public final /* synthetic */ class C0205w implements C0207y, Iterator {
    public final /* synthetic */ PrimitiveIterator.OfInt a;

    private /* synthetic */ C0205w(PrimitiveIterator.OfInt ofInt) {
        this.a = ofInt;
    }

    public static /* synthetic */ C0207y a(PrimitiveIterator.OfInt ofInt) {
        if (ofInt == null) {
            return null;
        }
        return ofInt instanceof C0206x ? ((C0206x) ofInt).a : new C0205w(ofInt);
    }

    public final /* synthetic */ boolean equals(Object obj) {
        if (obj instanceof C0205w) {
            obj = ((C0205w) obj).a;
        }
        return this.a.equals(obj);
    }

    public final /* synthetic */ void forEachRemaining(Object obj) {
        this.a.forEachRemaining(obj);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.a.forEachRemaining(consumer);
    }

    public final /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
        this.a.forEachRemaining(intConsumer);
    }

    public final /* synthetic */ boolean hasNext() {
        return this.a.hasNext();
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ int nextInt() {
        return this.a.nextInt();
    }

    public final /* synthetic */ void remove() {
        this.a.remove();
    }
}
