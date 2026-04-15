package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.ObjectCountHashMap;
import j$.util.Iterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

abstract class AbstractMapBasedMultiset<E> extends AbstractMultiset<E> implements Serializable {
    private static final long serialVersionUID = 0;
    public transient ObjectCountHashMap<E> g;
    public transient long h;

    public abstract class Itr<T> implements Iterator<T>, j$.util.Iterator {
        public int c;
        public int f = -1;
        public int g;

        public Itr() {
            this.c = AbstractMapBasedMultiset.this.g.b();
            this.g = AbstractMapBasedMultiset.this.g.d;
        }

        public abstract T a(int i);

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            if (AbstractMapBasedMultiset.this.g.d != this.g) {
                throw new ConcurrentModificationException();
            } else if (this.c >= 0) {
                return true;
            } else {
                return false;
            }
        }

        public T next() {
            if (hasNext()) {
                T a = a(this.c);
                int i = this.c;
                this.f = i;
                this.c = AbstractMapBasedMultiset.this.g.i(i);
                return a;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            boolean z;
            AbstractMapBasedMultiset abstractMapBasedMultiset = AbstractMapBasedMultiset.this;
            if (abstractMapBasedMultiset.g.d == this.g) {
                if (this.f != -1) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.e(z);
                abstractMapBasedMultiset.h -= (long) abstractMapBasedMultiset.g.l(this.f);
                this.c = abstractMapBasedMultiset.g.j(this.c, this.f);
                this.f = -1;
                this.g = abstractMapBasedMultiset.g.d;
                return;
            }
            throw new ConcurrentModificationException();
        }
    }

    public AbstractMapBasedMultiset(int i) {
        g(i);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        g(3);
        Serialization.d(this, objectInputStream, readInt);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.g(this, objectOutputStream);
    }

    public final int add(E e, int i) {
        boolean z;
        if (i == 0) {
            return count(e);
        }
        boolean z2 = true;
        if (i > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "occurrences cannot be negative: %s", i);
        int e2 = this.g.e(e);
        if (e2 == -1) {
            this.g.put(e, i);
            this.h += (long) i;
            return 0;
        }
        int d = this.g.d(e2);
        long j = (long) i;
        long j2 = ((long) d) + j;
        if (j2 > 2147483647L) {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "too many occurrences: %s", j2);
        ObjectCountHashMap<E> objectCountHashMap = this.g;
        Preconditions.checkElementIndex(e2, objectCountHashMap.c);
        objectCountHashMap.b[e2] = (int) j2;
        this.h += j;
        return d;
    }

    public final int b() {
        return this.g.c;
    }

    public final java.util.Iterator<E> c() {
        return new AbstractMapBasedMultiset<E>.Itr<E>() {
            public final E a(int i2) {
                return AbstractMapBasedMultiset.this.g.c(i2);
            }
        };
    }

    public final void clear() {
        this.g.clear();
        this.h = 0;
    }

    public final int count(Object obj) {
        return this.g.get(obj);
    }

    public final java.util.Iterator<Multiset.Entry<E>> f() {
        return new AbstractMapBasedMultiset<E>.Itr<Multiset.Entry<E>>() {
            public final Object a(int i2) {
                ObjectCountHashMap<E> objectCountHashMap = AbstractMapBasedMultiset.this.g;
                Preconditions.checkElementIndex(i2, objectCountHashMap.c);
                return new ObjectCountHashMap.MapEntry(i2);
            }
        };
    }

    public abstract void g(int i);

    public final java.util.Iterator<E> iterator() {
        return Multisets.c(this);
    }

    public final int remove(Object obj, int i) {
        boolean z;
        if (i == 0) {
            return count(obj);
        }
        if (i > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "occurrences cannot be negative: %s", i);
        int e = this.g.e(obj);
        if (e == -1) {
            return 0;
        }
        int d = this.g.d(e);
        if (d > i) {
            ObjectCountHashMap<E> objectCountHashMap = this.g;
            Preconditions.checkElementIndex(e, objectCountHashMap.c);
            objectCountHashMap.b[e] = d - i;
        } else {
            this.g.l(e);
            i = d;
        }
        this.h -= (long) i;
        return d;
    }

    public final int setCount(E e, int i) {
        CollectPreconditions.b(i, "count");
        ObjectCountHashMap<E> objectCountHashMap = this.g;
        int remove = i == 0 ? objectCountHashMap.remove(e) : objectCountHashMap.put(e, i);
        this.h += (long) (i - remove);
        return remove;
    }

    public final int size() {
        return y3.b(this.h);
    }

    public final boolean setCount(E e, int i, int i2) {
        CollectPreconditions.b(i, "oldCount");
        CollectPreconditions.b(i2, "newCount");
        int e2 = this.g.e(e);
        if (e2 == -1) {
            if (i != 0) {
                return false;
            }
            if (i2 > 0) {
                this.g.put(e, i2);
                this.h += (long) i2;
            }
            return true;
        } else if (this.g.d(e2) != i) {
            return false;
        } else {
            if (i2 == 0) {
                this.g.l(e2);
                this.h -= (long) i;
            } else {
                ObjectCountHashMap<E> objectCountHashMap = this.g;
                Preconditions.checkElementIndex(e2, objectCountHashMap.c);
                objectCountHashMap.b[e2] = i2;
                this.h += (long) (i2 - i);
            }
            return true;
        }
    }
}
