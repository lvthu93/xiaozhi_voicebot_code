package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import j$.util.Iterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.Enum;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

public final class EnumMultiset<E extends Enum<E>> extends AbstractMultiset<E> implements Serializable {
    private static final long serialVersionUID = 0;
    public transient Class<E> g;
    public transient E[] h;
    public transient int[] i;
    public transient int j;
    public transient long k;

    public abstract class Itr<T> implements Iterator<T>, j$.util.Iterator {
        public int c = 0;
        public int f = -1;

        public Itr() {
        }

        public abstract T a(int i);

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            while (true) {
                int i = this.c;
                EnumMultiset enumMultiset = EnumMultiset.this;
                if (i >= enumMultiset.h.length) {
                    return false;
                }
                if (enumMultiset.i[i] > 0) {
                    return true;
                }
                this.c = i + 1;
            }
        }

        public T next() {
            if (hasNext()) {
                T a = a(this.c);
                int i = this.c;
                this.f = i;
                this.c = i + 1;
                return a;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            boolean z;
            if (this.f >= 0) {
                z = true;
            } else {
                z = false;
            }
            CollectPreconditions.e(z);
            EnumMultiset enumMultiset = EnumMultiset.this;
            int[] iArr = enumMultiset.i;
            int i = this.f;
            int i2 = iArr[i];
            if (i2 > 0) {
                enumMultiset.j--;
                enumMultiset.k -= (long) i2;
                iArr[i] = 0;
            }
            this.f = -1;
        }
    }

    public EnumMultiset(Class<E> cls) {
        this.g = cls;
        Preconditions.checkArgument(cls.isEnum());
        E[] eArr = (Enum[]) cls.getEnumConstants();
        this.h = eArr;
        this.i = new int[eArr.length];
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Class<E> cls) {
        return new EnumMultiset<>(cls);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Class<E> cls = (Class) objectInputStream.readObject();
        this.g = cls;
        E[] eArr = (Enum[]) cls.getEnumConstants();
        this.h = eArr;
        this.i = new int[eArr.length];
        Serialization.d(this, objectInputStream, objectInputStream.readInt());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.g);
        Serialization.g(this, objectOutputStream);
    }

    public final int b() {
        return this.j;
    }

    public final java.util.Iterator<E> c() {
        return new EnumMultiset<E>.Itr<E>() {
            public final Object a(int i) {
                return EnumMultiset.this.h[i];
            }
        };
    }

    public void clear() {
        Arrays.fill(this.i, 0);
        this.k = 0;
        this.j = 0;
    }

    public /* bridge */ /* synthetic */ boolean contains(Object obj) {
        return super.contains(obj);
    }

    public int count(Object obj) {
        if (obj == null || !j(obj)) {
            return 0;
        }
        return this.i[((Enum) obj).ordinal()];
    }

    public /* bridge */ /* synthetic */ Set elementSet() {
        return super.elementSet();
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public final java.util.Iterator<Multiset.Entry<E>> f() {
        return new EnumMultiset<E>.Itr<Multiset.Entry<E>>() {
            public final Object a(final int i) {
                return new Multisets.AbstractEntry<Enum<Object>>() {
                    public int getCount() {
                        return EnumMultiset.this.i[i];
                    }

                    public Enum<Object> getElement() {
                        return EnumMultiset.this.h[i];
                    }
                };
            }
        };
    }

    public final void g(Object obj) {
        Preconditions.checkNotNull(obj);
        if (!j(obj)) {
            throw new ClassCastException("Expected an " + this.g + " but got " + obj);
        }
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public java.util.Iterator<E> iterator() {
        return Multisets.c(this);
    }

    public final boolean j(Object obj) {
        if (!(obj instanceof Enum)) {
            return false;
        }
        E e = (Enum) obj;
        int ordinal = e.ordinal();
        E[] eArr = this.h;
        if (ordinal >= eArr.length || eArr[ordinal] != e) {
            return false;
        }
        return true;
    }

    public int remove(Object obj, int i2) {
        if (obj == null || !j(obj)) {
            return 0;
        }
        Enum enumR = (Enum) obj;
        CollectPreconditions.b(i2, "occurrences");
        if (i2 == 0) {
            return count(obj);
        }
        int ordinal = enumR.ordinal();
        int[] iArr = this.i;
        int i3 = iArr[ordinal];
        if (i3 == 0) {
            return 0;
        }
        if (i3 <= i2) {
            iArr[ordinal] = 0;
            this.j--;
            this.k -= (long) i3;
        } else {
            iArr[ordinal] = i3 - i2;
            this.k -= (long) i2;
        }
        return i3;
    }

    public int size() {
        return y3.b(this.k);
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> iterable) {
        java.util.Iterator<E> it = iterable.iterator();
        Preconditions.checkArgument(it.hasNext(), "EnumMultiset constructor passed empty Iterable");
        EnumMultiset<E> enumMultiset = new EnumMultiset<>(((Enum) it.next()).getDeclaringClass());
        Iterables.addAll(enumMultiset, iterable);
        return enumMultiset;
    }

    public int add(E e, int i2) {
        g(e);
        CollectPreconditions.b(i2, "occurrences");
        if (i2 == 0) {
            return count(e);
        }
        int ordinal = e.ordinal();
        int i3 = this.i[ordinal];
        long j2 = (long) i2;
        long j3 = ((long) i3) + j2;
        Preconditions.checkArgument(j3 <= 2147483647L, "too many occurrences: %s", j3);
        this.i[ordinal] = (int) j3;
        if (i3 == 0) {
            this.j++;
        }
        this.k += j2;
        return i3;
    }

    public int setCount(E e, int i2) {
        g(e);
        CollectPreconditions.b(i2, "count");
        int ordinal = e.ordinal();
        int[] iArr = this.i;
        int i3 = iArr[ordinal];
        iArr[ordinal] = i2;
        this.k += (long) (i2 - i3);
        if (i3 == 0 && i2 > 0) {
            this.j++;
        } else if (i3 > 0 && i2 == 0) {
            this.j--;
        }
        return i3;
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> iterable, Class<E> cls) {
        EnumMultiset<E> create = create(cls);
        Iterables.addAll(create, iterable);
        return create;
    }
}
