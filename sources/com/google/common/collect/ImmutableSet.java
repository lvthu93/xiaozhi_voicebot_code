package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

public abstract class ImmutableSet<E> extends ImmutableCollection<E> implements Set<E> {
    public transient ImmutableList<E> f;

    public static class Builder<E> extends ImmutableCollection.ArrayBasedBuilder<E> {
        public Object[] d;
        public int e;

        public Builder() {
            super(4);
        }

        public Builder(int i) {
            super(i);
            this.d = new Object[ImmutableSet.g(i)];
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: com.google.common.collect.ImmutableSet} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: com.google.common.collect.RegularImmutableSet} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: com.google.common.collect.RegularImmutableSet} */
        /* JADX WARNING: type inference failed for: r0v5, types: [com.google.common.collect.ImmutableSet<E>] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.google.common.collect.ImmutableSet<E> build() {
            /*
                r10 = this;
                int r0 = r10.b
                if (r0 == 0) goto L_0x0056
                r1 = 0
                r2 = 1
                if (r0 == r2) goto L_0x004d
                java.lang.Object[] r3 = r10.d
                if (r3 == 0) goto L_0x0039
                int r0 = com.google.common.collect.ImmutableSet.g(r0)
                java.lang.Object[] r3 = r10.d
                int r3 = r3.length
                if (r0 != r3) goto L_0x0039
                int r0 = r10.b
                java.lang.Object[] r3 = r10.a
                int r4 = r3.length
                int r5 = r4 >> 1
                int r4 = r4 >> 2
                int r5 = r5 + r4
                if (r0 >= r5) goto L_0x0022
                r1 = 1
            L_0x0022:
                if (r1 == 0) goto L_0x0028
                java.lang.Object[] r3 = java.util.Arrays.copyOf(r3, r0)
            L_0x0028:
                r5 = r3
                com.google.common.collect.RegularImmutableSet r0 = new com.google.common.collect.RegularImmutableSet
                int r6 = r10.e
                java.lang.Object[] r7 = r10.d
                int r1 = r7.length
                int r8 = r1 + -1
                int r9 = r10.b
                r4 = r0
                r4.<init>(r5, r6, r7, r8, r9)
                goto L_0x0047
            L_0x0039:
                int r0 = r10.b
                java.lang.Object[] r1 = r10.a
                com.google.common.collect.ImmutableSet r0 = com.google.common.collect.ImmutableSet.j(r0, r1)
                int r1 = r0.size()
                r10.b = r1
            L_0x0047:
                r10.c = r2
                r1 = 0
                r10.d = r1
                return r0
            L_0x004d:
                java.lang.Object[] r0 = r10.a
                r0 = r0[r1]
                com.google.common.collect.ImmutableSet r0 = com.google.common.collect.ImmutableSet.of(r0)
                return r0
            L_0x0056:
                com.google.common.collect.ImmutableSet r0 = com.google.common.collect.ImmutableSet.of()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ImmutableSet.Builder.build():com.google.common.collect.ImmutableSet");
        }

        public Builder<E> addAll(Iterable<? extends E> iterable) {
            Preconditions.checkNotNull(iterable);
            if (this.d != null) {
                for (Object add : iterable) {
                    add((Object) add);
                }
            } else {
                super.addAll(iterable);
            }
            return this;
        }

        public Builder<E> add(E e2) {
            Preconditions.checkNotNull(e2);
            if (this.d != null) {
                int g = ImmutableSet.g(this.b);
                Object[] objArr = this.d;
                if (g <= objArr.length) {
                    int length = objArr.length - 1;
                    int hashCode = e2.hashCode();
                    int b = Hashing.b(hashCode);
                    while (true) {
                        int i = b & length;
                        Object[] objArr2 = this.d;
                        Object obj = objArr2[i];
                        if (obj == null) {
                            objArr2[i] = e2;
                            this.e += hashCode;
                            super.add(e2);
                            break;
                        } else if (obj.equals(e2)) {
                            break;
                        } else {
                            b = i + 1;
                        }
                    }
                    return this;
                }
            }
            this.d = null;
            super.add(e2);
            return this;
        }

        public Builder<E> addAll(Iterator<? extends E> it) {
            Preconditions.checkNotNull(it);
            while (it.hasNext()) {
                add((Object) it.next());
            }
            return this;
        }

        public Builder<E> add(E... eArr) {
            if (this.d != null) {
                for (E add : eArr) {
                    add((Object) add);
                }
            } else {
                super.add(eArr);
            }
            return this;
        }
    }

    public static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        public final Object[] c;

        public SerializedForm(Object[] objArr) {
            this.c = objArr;
        }

        public Object readResolve() {
            return ImmutableSet.copyOf((E[]) this.c);
        }
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    public static <E> Builder<E> builderWithExpectedSize(int i) {
        CollectPreconditions.b(i, "expectedSize");
        return new Builder<>(i);
    }

    public static <E> ImmutableSet<E> copyOf(Collection<? extends E> collection) {
        if ((collection instanceof ImmutableSet) && !(collection instanceof SortedSet)) {
            ImmutableSet<E> immutableSet = (ImmutableSet) collection;
            if (!immutableSet.isPartialView()) {
                return immutableSet;
            }
        }
        Object[] array = collection.toArray();
        return j(array.length, array);
    }

    static int g(int i) {
        int max = Math.max(i, 2);
        boolean z = true;
        if (max < 751619276) {
            int highestOneBit = Integer.highestOneBit(max - 1) << 1;
            while (((double) highestOneBit) * 0.7d < ((double) max)) {
                highestOneBit <<= 1;
            }
            return highestOneBit;
        }
        if (max >= 1073741824) {
            z = false;
        }
        Preconditions.checkArgument(z, "collection too large");
        return 1073741824;
    }

    public static <E> ImmutableSet<E> j(int i, Object... objArr) {
        if (i == 0) {
            return of();
        }
        boolean z = false;
        if (i == 1) {
            return of(objArr[0]);
        }
        int g = g(i);
        Object[] objArr2 = new Object[g];
        int i2 = g - 1;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < i) {
            Object obj = objArr[i3];
            if (obj != null) {
                int hashCode = obj.hashCode();
                int b = Hashing.b(hashCode);
                while (true) {
                    int i6 = b & i2;
                    Object obj2 = objArr2[i6];
                    if (obj2 == null) {
                        objArr[i5] = obj;
                        objArr2[i6] = obj;
                        i4 += hashCode;
                        i5++;
                        break;
                    } else if (obj2.equals(obj)) {
                        break;
                    } else {
                        b++;
                    }
                }
                i3++;
            } else {
                throw new NullPointerException(y2.f("at index ", i3));
            }
        }
        Arrays.fill(objArr, i5, i, (Object) null);
        if (i5 == 1) {
            return new SingletonImmutableSet(i4, objArr[0]);
        }
        if (g(i5) < g / 2) {
            return j(i5, objArr);
        }
        int length = objArr.length;
        if (i5 < (length >> 1) + (length >> 2)) {
            z = true;
        }
        if (z) {
            objArr = Arrays.copyOf(objArr, i5);
        }
        return new RegularImmutableSet(objArr, i4, objArr2, i2, i5);
    }

    public static <E> ImmutableSet<E> of() {
        return RegularImmutableSet.l;
    }

    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList = this.f;
        if (immutableList != null) {
            return immutableList;
        }
        ImmutableList<E> k = k();
        this.f = k;
        return k;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ImmutableSet) || !l() || !((ImmutableSet) obj).l() || hashCode() == obj.hashCode()) {
            return Sets.a(this, obj);
        }
        return false;
    }

    public int hashCode() {
        return Sets.b(this);
    }

    public abstract UnmodifiableIterator<E> iterator();

    public ImmutableList<E> k() {
        Object[] array = toArray();
        UnmodifiableListIterator<Object> unmodifiableListIterator = ImmutableList.f;
        return ImmutableList.g(array.length, array);
    }

    public boolean l() {
        return this instanceof EmptyContiguousSet;
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(toArray());
    }

    public static <E> ImmutableSet<E> of(E e) {
        return new SingletonImmutableSet(e);
    }

    public static <E> ImmutableSet<E> of(E e, E e2) {
        return j(2, e, e2);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3) {
        return j(3, e, e2, e3);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4) {
        return j(4, e, e2, e3, e4);
    }

    public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return copyOf((Collection) iterable);
        }
        return copyOf(iterable.iterator());
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5) {
        return j(5, e, e2, e3, e4, e5);
    }

    @SafeVarargs
    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        Preconditions.checkArgument(eArr.length <= 2147483641, "the total number of elements must fit in an int");
        int length = eArr.length + 6;
        Object[] objArr = new Object[length];
        objArr[0] = e;
        objArr[1] = e2;
        objArr[2] = e3;
        objArr[3] = e4;
        objArr[4] = e5;
        objArr[5] = e6;
        System.arraycopy(eArr, 0, objArr, 6, eArr.length);
        return j(length, objArr);
    }

    public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> it) {
        if (!it.hasNext()) {
            return of();
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return of(next);
        }
        return new Builder().add((Object) next).addAll((Iterator) it).build();
    }

    public static <E> ImmutableSet<E> copyOf(E[] eArr) {
        int length = eArr.length;
        if (length == 0) {
            return of();
        }
        if (length != 1) {
            return j(eArr.length, (Object[]) eArr.clone());
        }
        return of(eArr[0]);
    }
}
