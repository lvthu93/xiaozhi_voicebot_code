package j$.util;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.t  reason: case insensitive filesystem */
public final /* synthetic */ class C0202t implements PrimitiveIterator.OfDouble {
    public final /* synthetic */ C0203u a;

    private /* synthetic */ C0202t(C0203u uVar) {
        this.a = uVar;
    }

    public static /* synthetic */ PrimitiveIterator.OfDouble a(C0203u uVar) {
        if (uVar == null) {
            return null;
        }
        return uVar instanceof C0074s ? ((C0074s) uVar).a : new C0202t(uVar);
    }

    public final /* synthetic */ boolean equals(Object obj) {
        C0203u uVar = this.a;
        if (obj instanceof C0202t) {
            obj = ((C0202t) obj).a;
        }
        return uVar.equals(obj);
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
