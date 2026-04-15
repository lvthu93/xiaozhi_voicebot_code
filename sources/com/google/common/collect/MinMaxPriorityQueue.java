package com.google.common.collect;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.common.base.Preconditions;
import j$.util.Iterator;
import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public final class MinMaxPriorityQueue<E> extends AbstractQueue<E> {
    public final MinMaxPriorityQueue<E>.Heap c;
    public final MinMaxPriorityQueue<E>.Heap f;
    public final int g;
    public Object[] h;
    public int i;
    public int j;

    public static final class Builder<B> {
        public final Comparator<B> a;
        public int b;
        public int c;

        public Builder() {
            throw null;
        }

        public Builder(Comparator comparator) {
            this.b = -1;
            this.c = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            this.a = (Comparator) Preconditions.checkNotNull(comparator);
        }

        public <T extends B> MinMaxPriorityQueue<T> create() {
            return create(Collections.emptySet());
        }

        public Builder<B> expectedSize(int i) {
            boolean z;
            if (i >= 0) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z);
            this.b = i;
            return this;
        }

        public Builder<B> maximumSize(int i) {
            boolean z;
            if (i > 0) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z);
            this.c = i;
            return this;
        }

        public <T extends B> MinMaxPriorityQueue<T> create(Iterable<? extends T> iterable) {
            int i = this.b;
            int i2 = this.c;
            if (i == -1) {
                i = 11;
            }
            if (iterable instanceof Collection) {
                i = Math.max(i, ((Collection) iterable).size());
            }
            MinMaxPriorityQueue<T> minMaxPriorityQueue = new MinMaxPriorityQueue<>(this, Math.min(i - 1, i2) + 1);
            for (Object offer : iterable) {
                minMaxPriorityQueue.offer(offer);
            }
            return minMaxPriorityQueue;
        }
    }

    public class Heap {
        public final Ordering<E> a;
        public MinMaxPriorityQueue<E>.Heap b;

        public Heap(Ordering<E> ordering) {
            this.a = ordering;
        }

        public final int a(int i, E e) {
            MinMaxPriorityQueue minMaxPriorityQueue;
            while (true) {
                minMaxPriorityQueue = MinMaxPriorityQueue.this;
                if (i <= 2) {
                    break;
                }
                int i2 = (((i - 1) / 2) - 1) / 2;
                Object obj = minMaxPriorityQueue.h[i2];
                if (this.a.compare(obj, e) <= 0) {
                    break;
                }
                minMaxPriorityQueue.h[i] = obj;
                i = i2;
            }
            minMaxPriorityQueue.h[i] = e;
            return i;
        }

        public final int b(int i, E e) {
            int i2;
            MinMaxPriorityQueue minMaxPriorityQueue = MinMaxPriorityQueue.this;
            if (i == 0) {
                minMaxPriorityQueue.h[0] = e;
                return 0;
            }
            int i3 = (i - 1) / 2;
            Object[] objArr = minMaxPriorityQueue.h;
            Object obj = objArr[i3];
            Ordering<E> ordering = this.a;
            if (!(i3 == 0 || (i2 = (((i3 - 1) / 2) * 2) + 2) == i3 || (i2 * 2) + 1 < minMaxPriorityQueue.i)) {
                Object obj2 = objArr[i2];
                if (ordering.compare(obj2, obj) < 0) {
                    obj = obj2;
                    i3 = i2;
                }
            }
            if (ordering.compare(obj, e) < 0) {
                Object[] objArr2 = minMaxPriorityQueue.h;
                objArr2[i] = obj;
                objArr2[i3] = e;
                return i3;
            }
            minMaxPriorityQueue.h[i] = e;
            return i;
        }

        public final int c(int i, int i2) {
            boolean z;
            MinMaxPriorityQueue minMaxPriorityQueue = MinMaxPriorityQueue.this;
            if (i >= minMaxPriorityQueue.i) {
                return -1;
            }
            if (i > 0) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkState(z);
            int min = Math.min(i, minMaxPriorityQueue.i - i2) + i2;
            for (int i3 = i + 1; i3 < min; i3++) {
                Object[] objArr = minMaxPriorityQueue.h;
                if (this.a.compare(objArr[i3], objArr[i]) < 0) {
                    i = i3;
                }
            }
            return i;
        }
    }

    public static class MoveDesc<E> {
        public final E a;
        public final E b;

        public MoveDesc(E e, E e2) {
            this.a = e;
            this.b = e2;
        }
    }

    public class QueueIterator implements Iterator<E>, j$.util.Iterator {
        public int c = -1;
        public int f = -1;
        public int g;
        public ArrayDeque h;
        public ArrayList i;
        public E j;
        public boolean k;

        public QueueIterator() {
            this.g = MinMaxPriorityQueue.this.j;
        }

        public static boolean a(Collection collection, Object obj) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (it.next() == obj) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }

        public final void c(int i2) {
            if (this.f < i2) {
                if (this.i != null) {
                    while (true) {
                        MinMaxPriorityQueue minMaxPriorityQueue = MinMaxPriorityQueue.this;
                        if (i2 >= minMaxPriorityQueue.size() || !a(this.i, minMaxPriorityQueue.h[i2])) {
                            break;
                        }
                        i2++;
                    }
                }
                this.f = i2;
            }
        }

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            MinMaxPriorityQueue minMaxPriorityQueue = MinMaxPriorityQueue.this;
            if (minMaxPriorityQueue.j == this.g) {
                c(this.c + 1);
                if (this.f < minMaxPriorityQueue.size()) {
                    return true;
                }
                ArrayDeque arrayDeque = this.h;
                if (arrayDeque == null || arrayDeque.isEmpty()) {
                    return false;
                }
                return true;
            }
            throw new ConcurrentModificationException();
        }

        public E next() {
            MinMaxPriorityQueue minMaxPriorityQueue = MinMaxPriorityQueue.this;
            if (minMaxPriorityQueue.j == this.g) {
                c(this.c + 1);
                if (this.f < minMaxPriorityQueue.size()) {
                    int i2 = this.f;
                    this.c = i2;
                    this.k = true;
                    return minMaxPriorityQueue.h[i2];
                }
                if (this.h != null) {
                    this.c = minMaxPriorityQueue.size();
                    E poll = this.h.poll();
                    this.j = poll;
                    if (poll != null) {
                        this.k = true;
                        return poll;
                    }
                }
                throw new NoSuchElementException("iterator moved past last element in queue.");
            }
            throw new ConcurrentModificationException();
        }

        public void remove() {
            CollectPreconditions.e(this.k);
            MinMaxPriorityQueue minMaxPriorityQueue = MinMaxPriorityQueue.this;
            int i2 = minMaxPriorityQueue.j;
            int i3 = this.g;
            if (i2 == i3) {
                boolean z = false;
                this.k = false;
                this.g = i3 + 1;
                if (this.c < minMaxPriorityQueue.size()) {
                    MoveDesc c2 = minMaxPriorityQueue.c(this.c);
                    if (c2 != null) {
                        if (this.h == null) {
                            this.h = new ArrayDeque();
                            this.i = new ArrayList(3);
                        }
                        ArrayList arrayList = this.i;
                        E e = c2.a;
                        if (!a(arrayList, e)) {
                            this.h.add(e);
                        }
                        ArrayDeque arrayDeque = this.h;
                        E e2 = c2.b;
                        if (!a(arrayDeque, e2)) {
                            this.i.add(e2);
                        }
                    }
                    this.c--;
                    this.f--;
                    return;
                }
                E e3 = this.j;
                int i4 = 0;
                while (true) {
                    if (i4 >= minMaxPriorityQueue.i) {
                        break;
                    } else if (minMaxPriorityQueue.h[i4] == e3) {
                        minMaxPriorityQueue.c(i4);
                        z = true;
                        break;
                    } else {
                        i4++;
                    }
                }
                Preconditions.checkState(z);
                this.j = null;
                return;
            }
            throw new ConcurrentModificationException();
        }
    }

    public MinMaxPriorityQueue(Builder<? super E> builder, int i2) {
        Ordering<B> from = Ordering.from(builder.a);
        MinMaxPriorityQueue<E>.Heap heap = new Heap(from);
        this.c = heap;
        MinMaxPriorityQueue<E>.Heap heap2 = new Heap(from.reverse());
        this.f = heap2;
        heap.b = heap2;
        heap2.b = heap;
        this.g = builder.c;
        this.h = new Object[i2];
    }

    public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create() {
        return new Builder(Ordering.natural()).create();
    }

    public static Builder<Comparable> expectedSize(int i2) {
        return new Builder(Ordering.natural()).expectedSize(i2);
    }

    public static Builder<Comparable> maximumSize(int i2) {
        return new Builder(Ordering.natural()).maximumSize(i2);
    }

    public static <B> Builder<B> orderedBy(Comparator<B> comparator) {
        return new Builder<>(comparator);
    }

    public final int a() {
        int i2 = this.i;
        if (i2 == 1) {
            return 0;
        }
        if (i2 == 2) {
            return 1;
        }
        MinMaxPriorityQueue<E>.Heap heap = this.f;
        Object[] objArr = MinMaxPriorityQueue.this.h;
        if (heap.a.compare(objArr[1], objArr[2]) <= 0) {
            return 1;
        }
        return 2;
    }

    public boolean add(E e) {
        offer(e);
        return true;
    }

    public boolean addAll(Collection<? extends E> collection) {
        boolean z = false;
        for (Object offer : collection) {
            offer(offer);
            z = true;
        }
        return z;
    }

    public final MinMaxPriorityQueue<E>.Heap b(int i2) {
        boolean z;
        boolean z2 = true;
        int i3 = ~(~(i2 + 1));
        if (i3 > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "negative index");
        if ((1431655765 & i3) <= (i3 & -1431655766)) {
            z2 = false;
        }
        if (z2) {
            return this.c;
        }
        return this.f;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.common.collect.MinMaxPriorityQueue.MoveDesc<E> c(int r12) {
        /*
            r11 = this;
            int r0 = r11.i
            com.google.common.base.Preconditions.checkPositionIndex(r12, r0)
            int r0 = r11.j
            int r0 = r0 + 1
            r11.j = r0
            int r0 = r11.i
            int r0 = r0 + -1
            r11.i = r0
            r1 = 0
            if (r0 != r12) goto L_0x0019
            java.lang.Object[] r12 = r11.h
            r12[r0] = r1
            return r1
        L_0x0019:
            java.lang.Object[] r2 = r11.h
            r2 = r2[r0]
            com.google.common.collect.MinMaxPriorityQueue$Heap r0 = r11.b(r0)
            com.google.common.collect.MinMaxPriorityQueue r3 = com.google.common.collect.MinMaxPriorityQueue.this
            int r4 = r3.i
            int r5 = r4 + -1
            r6 = 2
            int r5 = r5 / r6
            if (r5 == 0) goto L_0x004e
            int r7 = r5 + -1
            int r7 = r7 / r6
            int r7 = r7 * 2
            int r7 = r7 + r6
            if (r7 == r5) goto L_0x004e
            int r5 = r7 * 2
            int r5 = r5 + 1
            if (r5 < r4) goto L_0x004e
            java.lang.Object[] r4 = r3.h
            r4 = r4[r7]
            com.google.common.collect.Ordering<E> r0 = r0.a
            int r0 = r0.compare(r4, r2)
            if (r0 >= 0) goto L_0x004e
            java.lang.Object[] r0 = r3.h
            r0[r7] = r2
            int r3 = r3.i
            r0[r3] = r4
            goto L_0x0050
        L_0x004e:
            int r7 = r3.i
        L_0x0050:
            if (r7 != r12) goto L_0x0059
            java.lang.Object[] r12 = r11.h
            int r0 = r11.i
            r12[r0] = r1
            return r1
        L_0x0059:
            int r0 = r11.i
            java.lang.Object[] r3 = r11.h
            r4 = r3[r0]
            r3[r0] = r1
            com.google.common.collect.MinMaxPriorityQueue$Heap r0 = r11.b(r12)
            r3 = r12
        L_0x0066:
            int r5 = r3 * 2
            int r5 = r5 + 1
            if (r5 >= 0) goto L_0x006e
            r8 = -1
            goto L_0x0077
        L_0x006e:
            int r8 = r5 * 2
            int r8 = r8 + 1
            r9 = 4
            int r8 = r0.c(r8, r9)
        L_0x0077:
            if (r8 <= 0) goto L_0x0083
            com.google.common.collect.MinMaxPriorityQueue r5 = com.google.common.collect.MinMaxPriorityQueue.this
            java.lang.Object[] r5 = r5.h
            r9 = r5[r8]
            r5[r3] = r9
            r3 = r8
            goto L_0x0066
        L_0x0083:
            r0.getClass()
            int r8 = r0.a(r3, r4)
            if (r8 != r3) goto L_0x00cc
            int r5 = r0.c(r5, r6)
            com.google.common.collect.MinMaxPriorityQueue r8 = com.google.common.collect.MinMaxPriorityQueue.this
            if (r5 <= 0) goto L_0x00a9
            java.lang.Object[] r9 = r8.h
            r9 = r9[r5]
            com.google.common.collect.Ordering<E> r10 = r0.a
            int r9 = r10.compare(r9, r4)
            if (r9 >= 0) goto L_0x00a9
            java.lang.Object[] r9 = r8.h
            r10 = r9[r5]
            r9[r3] = r10
            r9[r5] = r4
            goto L_0x00ad
        L_0x00a9:
            int r5 = r0.b(r3, r4)
        L_0x00ad:
            if (r5 != r3) goto L_0x00b0
            goto L_0x00d7
        L_0x00b0:
            if (r5 >= r12) goto L_0x00b7
            java.lang.Object[] r3 = r8.h
            r3 = r3[r12]
            goto L_0x00be
        L_0x00b7:
            int r3 = r12 + -1
            int r3 = r3 / r6
            java.lang.Object[] r6 = r8.h
            r3 = r6[r3]
        L_0x00be:
            com.google.common.collect.MinMaxPriorityQueue<E>$Heap r0 = r0.b
            int r0 = r0.a(r5, r4)
            if (r0 >= r12) goto L_0x00d7
            com.google.common.collect.MinMaxPriorityQueue$MoveDesc r1 = new com.google.common.collect.MinMaxPriorityQueue$MoveDesc
            r1.<init>(r4, r3)
            goto L_0x00d7
        L_0x00cc:
            if (r8 >= r12) goto L_0x00d7
            com.google.common.collect.MinMaxPriorityQueue$MoveDesc r1 = new com.google.common.collect.MinMaxPriorityQueue$MoveDesc
            java.lang.Object[] r0 = r11.h
            r0 = r0[r12]
            r1.<init>(r4, r0)
        L_0x00d7:
            if (r7 >= r12) goto L_0x00e9
            if (r1 != 0) goto L_0x00e1
            com.google.common.collect.MinMaxPriorityQueue$MoveDesc r12 = new com.google.common.collect.MinMaxPriorityQueue$MoveDesc
            r12.<init>(r2, r4)
            return r12
        L_0x00e1:
            com.google.common.collect.MinMaxPriorityQueue$MoveDesc r12 = new com.google.common.collect.MinMaxPriorityQueue$MoveDesc
            E r0 = r1.b
            r12.<init>(r2, r0)
            return r12
        L_0x00e9:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.MinMaxPriorityQueue.c(int):com.google.common.collect.MinMaxPriorityQueue$MoveDesc");
    }

    public void clear() {
        for (int i2 = 0; i2 < this.i; i2++) {
            this.h[i2] = null;
        }
        this.i = 0;
    }

    public Comparator<? super E> comparator() {
        return this.c.a;
    }

    public java.util.Iterator<E> iterator() {
        return new QueueIterator();
    }

    public boolean offer(E e) {
        int i2;
        boolean z;
        Preconditions.checkNotNull(e);
        this.j++;
        int i3 = this.i;
        int i4 = i3 + 1;
        this.i = i4;
        Object[] objArr = this.h;
        int length = objArr.length;
        int i5 = this.g;
        if (i4 > length) {
            int length2 = objArr.length;
            if (length2 < 64) {
                i2 = (length2 + 1) * 2;
            } else {
                int i6 = length2 / 2;
                long j2 = ((long) i6) * ((long) 3);
                int i7 = (int) j2;
                if (j2 == ((long) i7)) {
                    z = true;
                } else {
                    z = false;
                }
                cg.d(z, "checkedMultiply", i6, 3);
                i2 = i7;
            }
            Object[] objArr2 = new Object[(Math.min(i2 - 1, i5) + 1)];
            Object[] objArr3 = this.h;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.h = objArr2;
        }
        MinMaxPriorityQueue<E>.Heap b = b(i3);
        int b2 = b.b(i3, e);
        if (b2 != i3) {
            b = b.b;
            i3 = b2;
        }
        b.a(i3, e);
        if (this.i <= i5 || pollLast() != e) {
            return true;
        }
        return false;
    }

    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return this.h[0];
    }

    public E peekFirst() {
        return peek();
    }

    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return this.h[a()];
    }

    public E poll() {
        if (isEmpty()) {
            return null;
        }
        E e = this.h[0];
        c(0);
        return e;
    }

    public E pollFirst() {
        return poll();
    }

    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        int a = a();
        E e = this.h[a];
        c(a);
        return e;
    }

    public E removeFirst() {
        return remove();
    }

    public E removeLast() {
        if (!isEmpty()) {
            int a = a();
            E e = this.h[a];
            c(a);
            return e;
        }
        throw new NoSuchElementException();
    }

    public int size() {
        return this.i;
    }

    public Object[] toArray() {
        int i2 = this.i;
        Object[] objArr = new Object[i2];
        System.arraycopy(this.h, 0, objArr, 0, i2);
        return objArr;
    }

    public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create(Iterable<? extends E> iterable) {
        return new Builder(Ordering.natural()).create(iterable);
    }
}
