package com.google.common.collect;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.Iterator;
import java.util.Set;

public abstract class ForwardingMultiset<E> extends ForwardingCollection<E> implements Multiset<E> {

    public class StandardElementSet extends Multisets.ElementSet<E> {
        public StandardElementSet() {
        }

        public final Multiset<E> a() {
            return ForwardingMultiset.this;
        }

        public Iterator<E> iterator() {
            return new TransformedIterator<Multiset.Entry<Object>, Object>(ForwardingMultiset.this.entrySet().iterator()) {
                public final Object a(Object obj) {
                    return ((Multiset.Entry) obj).getElement();
                }
            };
        }
    }

    public int add(E e, int i) {
        return delegate().add(e, i);
    }

    public int count(Object obj) {
        return delegate().count(obj);
    }

    public Set<E> elementSet() {
        return delegate().elementSet();
    }

    public Set<Multiset.Entry<E>> entrySet() {
        return delegate().entrySet();
    }

    public boolean equals(Object obj) {
        return obj == this || delegate().equals(obj);
    }

    /* renamed from: g */
    public abstract Multiset<E> delegate();

    public int hashCode() {
        return delegate().hashCode();
    }

    public int remove(Object obj, int i) {
        return delegate().remove(obj, i);
    }

    public int setCount(E e, int i) {
        return delegate().setCount(e, i);
    }

    public boolean setCount(E e, int i, int i2) {
        return delegate().setCount(e, i, i2);
    }
}
