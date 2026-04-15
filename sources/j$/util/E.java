package j$.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;

final class E extends g0 {
    final /* synthetic */ SortedSet f;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    E(SortedSet sortedSet, Collection collection) {
        super(collection, 21);
        this.f = sortedSet;
    }

    public final Comparator getComparator() {
        return this.f.comparator();
    }
}
