package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

final class FilteredMultimapValues<K, V> extends AbstractCollection<V> {
    public final FilteredMultimap<K, V> c;

    public FilteredMultimapValues(FilteredMultimap<K, V> filteredMultimap) {
        this.c = (FilteredMultimap) Preconditions.checkNotNull(filteredMultimap);
    }

    public void clear() {
        this.c.clear();
    }

    public boolean contains(Object obj) {
        return this.c.containsValue(obj);
    }

    public Iterator<V> iterator() {
        return new TransformedIterator<Map.Entry<Object, Object>, Object>(this.c.entries().iterator()) {
            public final Object a(Object obj) {
                return ((Map.Entry) obj).getValue();
            }
        };
    }

    public boolean remove(Object obj) {
        FilteredMultimap<K, V> filteredMultimap = this.c;
        Predicate<? super Map.Entry<K, V>> entryPredicate = filteredMultimap.entryPredicate();
        Iterator<Map.Entry<K, V>> it = filteredMultimap.unfiltered().entries().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            if (entryPredicate.apply(next) && Objects.equal(next.getValue(), obj)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public boolean removeAll(Collection<?> collection) {
        FilteredMultimap<K, V> filteredMultimap = this.c;
        return Iterables.removeIf(filteredMultimap.unfiltered().entries(), Predicates.and(filteredMultimap.entryPredicate(), Maps.k(Predicates.in(collection))));
    }

    public boolean retainAll(Collection<?> collection) {
        FilteredMultimap<K, V> filteredMultimap = this.c;
        return Iterables.removeIf(filteredMultimap.unfiltered().entries(), Predicates.and(filteredMultimap.entryPredicate(), Maps.k(Predicates.not(Predicates.in(collection)))));
    }

    public int size() {
        return this.c.size();
    }
}
