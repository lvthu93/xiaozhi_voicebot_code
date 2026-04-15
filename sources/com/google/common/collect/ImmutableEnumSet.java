package com.google.common.collect;

import java.io.Serializable;
import java.lang.Enum;
import java.util.Collection;
import java.util.EnumSet;

final class ImmutableEnumSet<E extends Enum<E>> extends ImmutableSet<E> {
    public final transient EnumSet<E> g;
    public transient int h;

    public static class EnumSerializedForm<E extends Enum<E>> implements Serializable {
        private static final long serialVersionUID = 0;
        public final EnumSet<E> c;

        public EnumSerializedForm(EnumSet<E> enumSet) {
            this.c = enumSet;
        }

        public Object readResolve() {
            return new ImmutableEnumSet(this.c.clone());
        }
    }

    public ImmutableEnumSet(EnumSet<E> enumSet) {
        this.g = enumSet;
    }

    public static ImmutableSet m(EnumSet enumSet) {
        int size = enumSet.size();
        if (size == 0) {
            return ImmutableSet.of();
        }
        if (size != 1) {
            return new ImmutableEnumSet(enumSet);
        }
        return ImmutableSet.of(Iterables.getOnlyElement(enumSet));
    }

    public boolean contains(Object obj) {
        return this.g.contains(obj);
    }

    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof ImmutableEnumSet) {
            collection = ((ImmutableEnumSet) collection).g;
        }
        return this.g.containsAll(collection);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ImmutableEnumSet) {
            obj = ((ImmutableEnumSet) obj).g;
        }
        return this.g.equals(obj);
    }

    public int hashCode() {
        int i = this.h;
        if (i != 0) {
            return i;
        }
        int hashCode = this.g.hashCode();
        this.h = hashCode;
        return hashCode;
    }

    public boolean isEmpty() {
        return this.g.isEmpty();
    }

    public final boolean isPartialView() {
        return false;
    }

    public final boolean l() {
        return true;
    }

    public int size() {
        return this.g.size();
    }

    public String toString() {
        return this.g.toString();
    }

    public Object writeReplace() {
        return new EnumSerializedForm(this.g);
    }

    public UnmodifiableIterator<E> iterator() {
        return Iterators.unmodifiableIterator(this.g.iterator());
    }
}
