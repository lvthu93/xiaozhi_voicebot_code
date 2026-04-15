package j$.util;

import j$.util.stream.Stream;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Predicate;

/* renamed from: j$.util.Collection$-EL  reason: invalid class name */
public final /* synthetic */ class Collection$EL {
    public static U a(Collection collection) {
        if (collection instanceof C0058c) {
            return ((C0058c) collection).spliterator();
        }
        if (collection instanceof LinkedHashSet) {
            return new g0((Collection) Objects.requireNonNull((LinkedHashSet) collection), 17);
        }
        if (collection instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet) collection;
            return new E(sortedSet, sortedSet);
        } else if (collection instanceof Set) {
            return new g0((Collection) Objects.requireNonNull((Set) collection), 1);
        } else {
            if (!(collection instanceof List)) {
                return new g0((Collection) Objects.requireNonNull(collection), 0);
            }
            List list = (List) collection;
            return list instanceof RandomAccess ? new C0056a(list) : new g0((Collection) Objects.requireNonNull(list), 16);
        }
    }

    public static /* synthetic */ boolean removeIf(Collection collection, Predicate predicate) {
        return collection instanceof C0058c ? ((C0058c) collection).removeIf(predicate) : C0057b.f(collection, predicate);
    }

    public static /* synthetic */ Stream stream(Collection collection) {
        return collection instanceof C0058c ? ((C0058c) collection).stream() : C0057b.g(collection);
    }
}
