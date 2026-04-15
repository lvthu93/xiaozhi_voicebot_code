package j$.util;

import j$.util.stream.C0080b;
import java.io.Serializable;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* renamed from: j$.util.d  reason: case insensitive filesystem */
public final /* synthetic */ class C0060d implements Comparator, Serializable {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0060d(Object obj, int i) {
        this.a = i;
        this.b = obj;
    }

    public final int compare(Object obj, Object obj2) {
        int i = this.a;
        Object obj3 = this.b;
        switch (i) {
            case 0:
                C0080b bVar = (C0080b) ((ToDoubleFunction) obj3);
                return Double.compare(bVar.applyAsDouble(obj), bVar.applyAsDouble(obj2));
            case 1:
                ToIntFunction toIntFunction = (ToIntFunction) obj3;
                return Integer.compare(toIntFunction.applyAsInt(obj), toIntFunction.applyAsInt(obj2));
            case 2:
                C0080b bVar2 = (C0080b) ((ToLongFunction) obj3);
                return Long.compare(bVar2.applyAsLong(obj), bVar2.applyAsLong(obj2));
            default:
                Function function = (Function) obj3;
                return ((Comparable) function.apply(obj)).compareTo(function.apply(obj2));
        }
    }
}
