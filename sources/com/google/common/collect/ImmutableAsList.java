package com.google.common.collect;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

abstract class ImmutableAsList<E> extends ImmutableList<E> {

    public static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        public final ImmutableCollection<?> c;

        public SerializedForm(ImmutableCollection<?> immutableCollection) {
            this.c = immutableCollection;
        }

        public Object readResolve() {
            return this.c.asList();
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    public boolean contains(Object obj) {
        return j().contains(obj);
    }

    public boolean isEmpty() {
        return j().isEmpty();
    }

    public final boolean isPartialView() {
        return j().isPartialView();
    }

    public abstract ImmutableCollection<E> j();

    public int size() {
        return j().size();
    }

    public Object writeReplace() {
        return new SerializedForm(j());
    }
}
