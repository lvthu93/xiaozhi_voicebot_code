package com.google.protobuf;

import com.google.protobuf.p;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

public abstract class c<E> extends AbstractList<E> implements p.i<E> {
    public boolean c;

    public c(boolean z) {
        this.c = z;
    }

    public final void a() {
        if (!this.c) {
            throw new UnsupportedOperationException();
        }
    }

    public boolean addAll(Collection<? extends E> collection) {
        a();
        return super.addAll(collection);
    }

    public final void clear() {
        a();
        super.clear();
    }

    public final void e() {
        if (this.c) {
            this.c = false;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        if (!(obj instanceof RandomAccess)) {
            return super.equals(obj);
        }
        List list = (List) obj;
        int size = size();
        if (size != list.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!get(i).equals(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int size = size();
        int i = 1;
        for (int i2 = 0; i2 < size; i2++) {
            i = (i * 31) + get(i2).hashCode();
        }
        return i;
    }

    public abstract E remove(int i);

    public final boolean remove(Object obj) {
        a();
        int indexOf = indexOf(obj);
        if (indexOf == -1) {
            return false;
        }
        remove(indexOf);
        return true;
    }

    public final boolean removeAll(Collection<?> collection) {
        a();
        return super.removeAll(collection);
    }

    public final boolean retainAll(Collection<?> collection) {
        a();
        return super.retainAll(collection);
    }

    public final boolean y() {
        return this.c;
    }

    public final boolean addAll(int i, Collection<? extends E> collection) {
        a();
        return super.addAll(i, collection);
    }
}
