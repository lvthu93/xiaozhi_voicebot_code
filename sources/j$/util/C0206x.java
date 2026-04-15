package j$.util;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* renamed from: j$.util.x  reason: case insensitive filesystem */
public final /* synthetic */ class C0206x implements PrimitiveIterator.OfInt {
    public final /* synthetic */ C0207y a;

    private /* synthetic */ C0206x(C0207y yVar) {
        this.a = yVar;
    }

    public static /* synthetic */ PrimitiveIterator.OfInt a(C0207y yVar) {
        if (yVar == null) {
            return null;
        }
        return yVar instanceof C0205w ? ((C0205w) yVar).a : new C0206x(yVar);
    }

    public final /* synthetic */ boolean equals(Object obj) {
        C0207y yVar = this.a;
        if (obj instanceof C0206x) {
            obj = ((C0206x) obj).a;
        }
        return yVar.equals(obj);
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
