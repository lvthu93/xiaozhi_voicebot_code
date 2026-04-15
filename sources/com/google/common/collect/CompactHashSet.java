package com.google.common.collect;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import j$.util.Iterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

class CompactHashSet<E> extends AbstractSet<E> implements Serializable {
    public transient int[] c;
    public transient long[] f;
    public transient Object[] g;
    public transient float h;
    public transient int i;
    public transient int j;
    public transient int k;

    public CompactHashSet() {
        f(3);
    }

    public static <E> CompactHashSet<E> create() {
        return new CompactHashSet<>();
    }

    public static <E> CompactHashSet<E> createWithExpectedSize(int i2) {
        return new CompactHashSet<>(i2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        f(3);
        int readInt = objectInputStream.readInt();
        while (true) {
            readInt--;
            if (readInt >= 0) {
                add(objectInputStream.readObject());
            } else {
                return;
            }
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.k);
        Iterator it = iterator();
        while (it.hasNext()) {
            objectOutputStream.writeObject(it.next());
        }
    }

    public int a(int i2, int i3) {
        return i2 - 1;
    }

    public boolean add(E e) {
        long[] jArr = this.f;
        Object[] objArr = this.g;
        int c2 = Hashing.c(e);
        int[] iArr = this.c;
        int length = (iArr.length - 1) & c2;
        int i2 = this.k;
        int i3 = iArr[length];
        if (i3 == -1) {
            iArr[length] = i2;
        } else {
            while (true) {
                long j2 = jArr[i3];
                if (((int) (j2 >>> 32)) == c2 && Objects.equal(e, objArr[i3])) {
                    return false;
                }
                int i4 = (int) j2;
                if (i4 == -1) {
                    jArr[i3] = (j2 & -4294967296L) | (4294967295L & ((long) i2));
                    break;
                }
                i3 = i4;
            }
        }
        int i5 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        if (i2 != Integer.MAX_VALUE) {
            int i6 = i2 + 1;
            int length2 = this.f.length;
            if (i6 > length2) {
                int max = Math.max(1, length2 >>> 1) + length2;
                if (max >= 0) {
                    i5 = max;
                }
                if (i5 != length2) {
                    l(i5);
                }
            }
            g(i2, c2, e);
            this.k = i6;
            if (i2 >= this.j) {
                m(this.c.length * 2);
            }
            this.i++;
            return true;
        }
        throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
    }

    public int b() {
        return isEmpty() ? -1 : 0;
    }

    public int c(int i2) {
        int i3 = i2 + 1;
        if (i3 < this.k) {
            return i3;
        }
        return -1;
    }

    public void clear() {
        this.i++;
        Arrays.fill(this.g, 0, this.k, (Object) null);
        Arrays.fill(this.c, -1);
        Arrays.fill(this.f, -1);
        this.k = 0;
    }

    public boolean contains(Object obj) {
        int c2 = Hashing.c(obj);
        int[] iArr = this.c;
        int i2 = iArr[(iArr.length - 1) & c2];
        while (i2 != -1) {
            long j2 = this.f[i2];
            if (((int) (j2 >>> 32)) == c2 && Objects.equal(obj, this.g[i2])) {
                return true;
            }
            i2 = (int) j2;
        }
        return false;
    }

    public void f(int i2) {
        boolean z;
        if (i2 >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Initial capacity must be non-negative");
        Preconditions.checkArgument(true, "Illegal load factor");
        int a = Hashing.a(i2, (double) 1.0f);
        int[] iArr = new int[a];
        Arrays.fill(iArr, -1);
        this.c = iArr;
        this.h = 1.0f;
        this.g = new Object[i2];
        long[] jArr = new long[i2];
        Arrays.fill(jArr, -1);
        this.f = jArr;
        this.j = Math.max(1, (int) (((float) a) * 1.0f));
    }

    public void g(int i2, int i3, Object obj) {
        this.f[i2] = (((long) i3) << 32) | 4294967295L;
        this.g[i2] = obj;
    }

    public boolean isEmpty() {
        return this.k == 0;
    }

    public Iterator<E> iterator() {
        return new Object() {
            public int c;
            public int f;
            public int g = -1;

            {
                this.c = CompactHashSet.this.i;
                this.f = CompactHashSet.this.b();
            }

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                return this.f >= 0;
            }

            public E next() {
                CompactHashSet compactHashSet = CompactHashSet.this;
                if (compactHashSet.i != this.c) {
                    throw new ConcurrentModificationException();
                } else if (hasNext()) {
                    int i = this.f;
                    this.g = i;
                    E e = compactHashSet.g[i];
                    this.f = compactHashSet.c(i);
                    return e;
                } else {
                    throw new NoSuchElementException();
                }
            }

            public void remove() {
                boolean z;
                CompactHashSet compactHashSet = CompactHashSet.this;
                if (compactHashSet.i == this.c) {
                    if (this.g >= 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    CollectPreconditions.e(z);
                    this.c++;
                    Object[] objArr = compactHashSet.g;
                    int i = this.g;
                    compactHashSet.k((int) (compactHashSet.f[i] >>> 32), objArr[i]);
                    this.f = compactHashSet.a(this.f, this.g);
                    this.g = -1;
                    return;
                }
                throw new ConcurrentModificationException();
            }
        };
    }

    public void j(int i2) {
        int size = size() - 1;
        if (i2 < size) {
            Object[] objArr = this.g;
            objArr[i2] = objArr[size];
            objArr[size] = null;
            long[] jArr = this.f;
            long j2 = jArr[size];
            jArr[i2] = j2;
            jArr[size] = -1;
            int[] iArr = this.c;
            int length = ((int) (j2 >>> 32)) & (iArr.length - 1);
            int i3 = iArr[length];
            if (i3 == size) {
                iArr[length] = i2;
                return;
            }
            while (true) {
                long[] jArr2 = this.f;
                long j3 = jArr2[i3];
                int i4 = (int) j3;
                if (i4 == size) {
                    jArr2[i3] = (j3 & -4294967296L) | (4294967295L & ((long) i2));
                    return;
                }
                i3 = i4;
            }
        } else {
            this.g[i2] = null;
            this.f[i2] = -1;
        }
    }

    public final boolean k(int i2, Object obj) {
        int[] iArr = this.c;
        int length = (iArr.length - 1) & i2;
        int i3 = iArr[length];
        if (i3 == -1) {
            return false;
        }
        int i4 = -1;
        while (true) {
            if (((int) (this.f[i3] >>> 32)) != i2 || !Objects.equal(obj, this.g[i3])) {
                int i5 = (int) this.f[i3];
                if (i5 == -1) {
                    return false;
                }
                int i6 = i5;
                i4 = i3;
                i3 = i6;
            } else {
                if (i4 == -1) {
                    this.c[length] = (int) this.f[i3];
                } else {
                    long[] jArr = this.f;
                    jArr[i4] = (jArr[i4] & -4294967296L) | (4294967295L & ((long) ((int) jArr[i3])));
                }
                j(i3);
                this.k--;
                this.i++;
                return true;
            }
        }
    }

    public void l(int i2) {
        this.g = Arrays.copyOf(this.g, i2);
        long[] jArr = this.f;
        int length = jArr.length;
        long[] copyOf = Arrays.copyOf(jArr, i2);
        if (i2 > length) {
            Arrays.fill(copyOf, length, i2, -1);
        }
        this.f = copyOf;
    }

    public final void m(int i2) {
        if (this.c.length >= 1073741824) {
            this.j = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            return;
        }
        int i3 = ((int) (((float) i2) * this.h)) + 1;
        int[] iArr = new int[i2];
        Arrays.fill(iArr, -1);
        long[] jArr = this.f;
        int i4 = i2 - 1;
        for (int i5 = 0; i5 < this.k; i5++) {
            int i6 = (int) (jArr[i5] >>> 32);
            int i7 = i6 & i4;
            int i8 = iArr[i7];
            iArr[i7] = i5;
            jArr[i5] = (((long) i6) << 32) | (((long) i8) & 4294967295L);
        }
        this.j = i3;
        this.c = iArr;
    }

    public boolean remove(Object obj) {
        return k(Hashing.c(obj), obj);
    }

    public int size() {
        return this.k;
    }

    public Object[] toArray() {
        return Arrays.copyOf(this.g, this.k);
    }

    public void trimToSize() {
        int i2 = this.k;
        if (i2 < this.f.length) {
            l(i2);
        }
        int max = Math.max(1, Integer.highestOneBit((int) (((float) i2) / this.h)));
        if (max < 1073741824 && ((double) i2) / ((double) max) > ((double) this.h)) {
            max <<= 1;
        }
        if (max < this.c.length) {
            m(max);
        }
    }

    public static <E> CompactHashSet<E> create(Collection<? extends E> collection) {
        CompactHashSet<E> createWithExpectedSize = createWithExpectedSize(collection.size());
        createWithExpectedSize.addAll(collection);
        return createWithExpectedSize;
    }

    public <T> T[] toArray(T[] tArr) {
        Object[] objArr = this.g;
        int i2 = this.k;
        Preconditions.checkPositionIndexes(0, 0 + i2, objArr.length);
        if (tArr.length < i2) {
            tArr = ObjectArrays.newArray(tArr, i2);
        } else if (tArr.length > i2) {
            tArr[i2] = null;
        }
        System.arraycopy(objArr, 0, tArr, 0, i2);
        return tArr;
    }

    public CompactHashSet(int i2) {
        f(i2);
    }

    public static <E> CompactHashSet<E> create(E... eArr) {
        CompactHashSet<E> createWithExpectedSize = createWithExpectedSize(eArr.length);
        Collections.addAll(createWithExpectedSize, eArr);
        return createWithExpectedSize;
    }
}
