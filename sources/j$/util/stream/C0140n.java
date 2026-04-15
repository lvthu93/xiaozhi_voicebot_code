package j$.util.stream;

import j$.lang.a;
import j$.util.Map;
import j$.util.j0;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/* renamed from: j$.util.stream.n  reason: case insensitive filesystem */
public final /* synthetic */ class C0140n implements BiConsumer, Supplier {
    public final /* synthetic */ Object a;
    public final /* synthetic */ Object b;
    public final /* synthetic */ Object c;

    public /* synthetic */ C0140n(Object obj, Object obj2, Object obj3) {
        this.a = obj;
        this.b = obj2;
        this.c = obj3;
    }

    public final void accept(Object obj, Object obj2) {
        Set set = Collectors.a;
        Map.EL.a((java.util.Map) obj, ((Function) this.a).apply(obj2), ((Function) this.b).apply(obj2), (BinaryOperator) this.c);
    }

    public final /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
        return a.a(this, biConsumer);
    }

    public final Object get() {
        Set set = Collectors.a;
        return new j0((CharSequence) this.a, (CharSequence) this.b, (CharSequence) this.c);
    }
}
