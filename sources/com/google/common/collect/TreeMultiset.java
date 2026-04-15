package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Serialization;
import j$.util.Iterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

public final class TreeMultiset<E> extends AbstractSortedMultiset<E> implements Serializable {
    public static final /* synthetic */ int l = 0;
    private static final long serialVersionUID = 1;
    public final transient Reference<AvlNode<E>> i;
    public final transient GeneralRange<E> j;
    public final transient AvlNode<E> k;

    /* renamed from: com.google.common.collect.TreeMultiset$4  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] a;

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x000d */
        static {
            /*
                com.google.common.collect.BoundType[] r0 = com.google.common.collect.BoundType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                r1 = 1
                r2 = 0
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x000d }
            L_0x000d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.TreeMultiset.AnonymousClass4.<clinit>():void");
        }
    }

    public enum Aggregate {
        ;

        /* access modifiers changed from: public */
        Aggregate() {
            throw null;
        }

        public abstract int b(AvlNode<?> avlNode);

        public abstract long d(AvlNode<?> avlNode);
    }

    public static final class AvlNode<E> {
        public final E a;
        public int b;
        public int c;
        public long d;
        public int e;
        public AvlNode<E> f;
        public AvlNode<E> g;
        public AvlNode<E> h;
        public AvlNode<E> i;

        public AvlNode(int i2, Object obj) {
            boolean z;
            if (i2 > 0) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z);
            this.a = obj;
            this.b = i2;
            this.d = (long) i2;
            this.c = 1;
            this.e = 1;
            this.f = null;
            this.g = null;
        }

        public final AvlNode<E> a(Comparator<? super E> comparator, E e2, int i2, int[] iArr) {
            int compare = comparator.compare(e2, this.a);
            boolean z = true;
            if (compare < 0) {
                AvlNode<E> avlNode = this.f;
                if (avlNode == null) {
                    iArr[0] = 0;
                    b(i2, e2);
                    return this;
                }
                int i3 = avlNode.e;
                AvlNode<E> a2 = avlNode.a(comparator, e2, i2, iArr);
                this.f = a2;
                if (iArr[0] == 0) {
                    this.c++;
                }
                this.d += (long) i2;
                if (a2.e == i3) {
                    return this;
                }
                return g();
            } else if (compare > 0) {
                AvlNode<E> avlNode2 = this.g;
                if (avlNode2 == null) {
                    iArr[0] = 0;
                    c(i2, e2);
                    return this;
                }
                int i4 = avlNode2.e;
                AvlNode<E> a3 = avlNode2.a(comparator, e2, i2, iArr);
                this.g = a3;
                if (iArr[0] == 0) {
                    this.c++;
                }
                this.d += (long) i2;
                if (a3.e == i4) {
                    return this;
                }
                return g();
            } else {
                int i5 = this.b;
                iArr[0] = i5;
                long j = (long) i2;
                if (((long) i5) + j > 2147483647L) {
                    z = false;
                }
                Preconditions.checkArgument(z);
                this.b += i2;
                this.d += j;
                return this;
            }
        }

        public final void b(int i2, Object obj) {
            AvlNode<E> avlNode = new AvlNode<>(i2, obj);
            this.f = avlNode;
            AvlNode<E> avlNode2 = this.h;
            int i3 = TreeMultiset.l;
            avlNode2.i = avlNode;
            avlNode.h = avlNode2;
            avlNode.i = this;
            this.h = avlNode;
            this.e = Math.max(2, this.e);
            this.c++;
            this.d += (long) i2;
        }

        public final void c(int i2, Object obj) {
            AvlNode<E> avlNode = new AvlNode<>(i2, obj);
            this.g = avlNode;
            AvlNode<E> avlNode2 = this.i;
            int i3 = TreeMultiset.l;
            this.i = avlNode;
            avlNode.h = this;
            avlNode.i = avlNode2;
            avlNode2.h = avlNode;
            this.e = Math.max(2, this.e);
            this.c++;
            this.d += (long) i2;
        }

        public int count(Comparator<? super E> comparator, E e2) {
            int compare = comparator.compare(e2, this.a);
            if (compare < 0) {
                AvlNode<E> avlNode = this.f;
                if (avlNode == null) {
                    return 0;
                }
                return avlNode.count(comparator, e2);
            } else if (compare <= 0) {
                return this.b;
            } else {
                AvlNode<E> avlNode2 = this.g;
                if (avlNode2 == null) {
                    return 0;
                }
                return avlNode2.count(comparator, e2);
            }
        }

        public final AvlNode<E> d(Comparator<? super E> comparator, E e2) {
            int compare = comparator.compare(e2, this.a);
            if (compare < 0) {
                AvlNode<E> avlNode = this.f;
                if (avlNode == null) {
                    return this;
                }
                return (AvlNode) MoreObjects.firstNonNull(avlNode.d(comparator, e2), this);
            } else if (compare == 0) {
                return this;
            } else {
                AvlNode<E> avlNode2 = this.g;
                if (avlNode2 == null) {
                    return null;
                }
                return avlNode2.d(comparator, e2);
            }
        }

        public final AvlNode<E> e() {
            int i2 = this.b;
            this.b = 0;
            AvlNode<E> avlNode = this.h;
            AvlNode<E> avlNode2 = this.i;
            int i3 = TreeMultiset.l;
            avlNode.i = avlNode2;
            avlNode2.h = avlNode;
            AvlNode<E> avlNode3 = this.f;
            if (avlNode3 == null) {
                return this.g;
            }
            AvlNode<E> avlNode4 = this.g;
            if (avlNode4 == null) {
                return avlNode3;
            }
            if (avlNode3.e >= avlNode4.e) {
                AvlNode<E> avlNode5 = this.h;
                avlNode5.f = avlNode3.k(avlNode5);
                avlNode5.g = this.g;
                avlNode5.c = this.c - 1;
                avlNode5.d = this.d - ((long) i2);
                return avlNode5.g();
            }
            AvlNode<E> avlNode6 = this.i;
            avlNode6.g = avlNode4.l(avlNode6);
            avlNode6.f = this.f;
            avlNode6.c = this.c - 1;
            avlNode6.d = this.d - ((long) i2);
            return avlNode6.g();
        }

        public final AvlNode<E> f(Comparator<? super E> comparator, E e2) {
            int compare = comparator.compare(e2, this.a);
            if (compare > 0) {
                AvlNode<E> avlNode = this.g;
                if (avlNode == null) {
                    return this;
                }
                return (AvlNode) MoreObjects.firstNonNull(avlNode.f(comparator, e2), this);
            } else if (compare == 0) {
                return this;
            } else {
                AvlNode<E> avlNode2 = this.f;
                if (avlNode2 == null) {
                    return null;
                }
                return avlNode2.f(comparator, e2);
            }
        }

        public final AvlNode<E> g() {
            int i2;
            int i3;
            int i4;
            int i5;
            AvlNode<E> avlNode = this.f;
            int i6 = 0;
            if (avlNode == null) {
                i2 = 0;
            } else {
                i2 = avlNode.e;
            }
            AvlNode<E> avlNode2 = this.g;
            if (avlNode2 == null) {
                i3 = 0;
            } else {
                i3 = avlNode2.e;
            }
            int i7 = i2 - i3;
            if (i7 == -2) {
                AvlNode<E> avlNode3 = avlNode2.f;
                if (avlNode3 == null) {
                    i4 = 0;
                } else {
                    i4 = avlNode3.e;
                }
                AvlNode<E> avlNode4 = avlNode2.g;
                if (avlNode4 != null) {
                    i6 = avlNode4.e;
                }
                if (i4 - i6 > 0) {
                    this.g = avlNode2.n();
                }
                return m();
            } else if (i7 != 2) {
                i();
                return this;
            } else {
                AvlNode<E> avlNode5 = avlNode.f;
                if (avlNode5 == null) {
                    i5 = 0;
                } else {
                    i5 = avlNode5.e;
                }
                AvlNode<E> avlNode6 = avlNode.g;
                if (avlNode6 != null) {
                    i6 = avlNode6.e;
                }
                if (i5 - i6 < 0) {
                    this.f = avlNode.m();
                }
                return n();
            }
        }

        public final void h() {
            int i2;
            long j;
            AvlNode<E> avlNode = this.f;
            int i3 = TreeMultiset.l;
            int i4 = 0;
            if (avlNode == null) {
                i2 = 0;
            } else {
                i2 = avlNode.c;
            }
            int i5 = i2 + 1;
            AvlNode<E> avlNode2 = this.g;
            if (avlNode2 != null) {
                i4 = avlNode2.c;
            }
            this.c = i4 + i5;
            long j2 = (long) this.b;
            long j3 = 0;
            if (avlNode == null) {
                j = 0;
            } else {
                j = avlNode.d;
            }
            long j4 = j2 + j;
            if (avlNode2 != null) {
                j3 = avlNode2.d;
            }
            this.d = j4 + j3;
            i();
        }

        public final void i() {
            int i2;
            AvlNode<E> avlNode = this.f;
            int i3 = 0;
            if (avlNode == null) {
                i2 = 0;
            } else {
                i2 = avlNode.e;
            }
            AvlNode<E> avlNode2 = this.g;
            if (avlNode2 != null) {
                i3 = avlNode2.e;
            }
            this.e = Math.max(i2, i3) + 1;
        }

        public final AvlNode<E> j(Comparator<? super E> comparator, E e2, int i2, int[] iArr) {
            int compare = comparator.compare(e2, this.a);
            if (compare < 0) {
                AvlNode<E> avlNode = this.f;
                if (avlNode == null) {
                    iArr[0] = 0;
                    return this;
                }
                this.f = avlNode.j(comparator, e2, i2, iArr);
                int i3 = iArr[0];
                if (i3 > 0) {
                    if (i2 >= i3) {
                        this.c--;
                        this.d -= (long) i3;
                    } else {
                        this.d -= (long) i2;
                    }
                }
                if (i3 == 0) {
                    return this;
                }
                return g();
            } else if (compare > 0) {
                AvlNode<E> avlNode2 = this.g;
                if (avlNode2 == null) {
                    iArr[0] = 0;
                    return this;
                }
                this.g = avlNode2.j(comparator, e2, i2, iArr);
                int i4 = iArr[0];
                if (i4 > 0) {
                    if (i2 >= i4) {
                        this.c--;
                        this.d -= (long) i4;
                    } else {
                        this.d -= (long) i2;
                    }
                }
                return g();
            } else {
                int i5 = this.b;
                iArr[0] = i5;
                if (i2 >= i5) {
                    return e();
                }
                this.b = i5 - i2;
                this.d -= (long) i2;
                return this;
            }
        }

        public final AvlNode<E> k(AvlNode<E> avlNode) {
            AvlNode<E> avlNode2 = this.g;
            if (avlNode2 == null) {
                return this.f;
            }
            this.g = avlNode2.k(avlNode);
            this.c--;
            this.d -= (long) avlNode.b;
            return g();
        }

        public final AvlNode<E> l(AvlNode<E> avlNode) {
            AvlNode<E> avlNode2 = this.f;
            if (avlNode2 == null) {
                return this.g;
            }
            this.f = avlNode2.l(avlNode);
            this.c--;
            this.d -= (long) avlNode.b;
            return g();
        }

        public final AvlNode<E> m() {
            boolean z;
            if (this.g != null) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkState(z);
            AvlNode<E> avlNode = this.g;
            this.g = avlNode.f;
            avlNode.f = this;
            avlNode.d = this.d;
            avlNode.c = this.c;
            h();
            avlNode.i();
            return avlNode;
        }

        public final AvlNode<E> n() {
            boolean z;
            if (this.f != null) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkState(z);
            AvlNode<E> avlNode = this.f;
            this.f = avlNode.g;
            avlNode.g = this;
            avlNode.d = this.d;
            avlNode.c = this.c;
            h();
            avlNode.i();
            return avlNode;
        }

        public final AvlNode<E> o(Comparator<? super E> comparator, E e2, int i2, int i3, int[] iArr) {
            int compare = comparator.compare(e2, this.a);
            if (compare < 0) {
                AvlNode<E> avlNode = this.f;
                if (avlNode == null) {
                    iArr[0] = 0;
                    if (i2 == 0 && i3 > 0) {
                        b(i3, e2);
                    }
                    return this;
                }
                this.f = avlNode.o(comparator, e2, i2, i3, iArr);
                int i4 = iArr[0];
                if (i4 == i2) {
                    if (i3 == 0 && i4 != 0) {
                        this.c--;
                    } else if (i3 > 0 && i4 == 0) {
                        this.c++;
                    }
                    this.d += (long) (i3 - i4);
                }
                return g();
            } else if (compare > 0) {
                AvlNode<E> avlNode2 = this.g;
                if (avlNode2 == null) {
                    iArr[0] = 0;
                    if (i2 == 0 && i3 > 0) {
                        c(i3, e2);
                    }
                    return this;
                }
                this.g = avlNode2.o(comparator, e2, i2, i3, iArr);
                int i5 = iArr[0];
                if (i5 == i2) {
                    if (i3 == 0 && i5 != 0) {
                        this.c--;
                    } else if (i3 > 0 && i5 == 0) {
                        this.c++;
                    }
                    this.d += (long) (i3 - i5);
                }
                return g();
            } else {
                int i6 = this.b;
                iArr[0] = i6;
                if (i2 == i6) {
                    if (i3 == 0) {
                        return e();
                    }
                    this.d += (long) (i3 - i6);
                    this.b = i3;
                }
                return this;
            }
        }

        public final AvlNode<E> p(Comparator<? super E> comparator, E e2, int i2, int[] iArr) {
            int compare = comparator.compare(e2, this.a);
            if (compare < 0) {
                AvlNode<E> avlNode = this.f;
                if (avlNode == null) {
                    iArr[0] = 0;
                    if (i2 > 0) {
                        b(i2, e2);
                    }
                    return this;
                }
                this.f = avlNode.p(comparator, e2, i2, iArr);
                if (i2 == 0 && iArr[0] != 0) {
                    this.c--;
                } else if (i2 > 0 && iArr[0] == 0) {
                    this.c++;
                }
                this.d += (long) (i2 - iArr[0]);
                return g();
            } else if (compare > 0) {
                AvlNode<E> avlNode2 = this.g;
                if (avlNode2 == null) {
                    iArr[0] = 0;
                    if (i2 > 0) {
                        c(i2, e2);
                    }
                    return this;
                }
                this.g = avlNode2.p(comparator, e2, i2, iArr);
                if (i2 == 0 && iArr[0] != 0) {
                    this.c--;
                } else if (i2 > 0 && iArr[0] == 0) {
                    this.c++;
                }
                this.d += (long) (i2 - iArr[0]);
                return g();
            } else {
                int i3 = this.b;
                iArr[0] = i3;
                if (i2 == 0) {
                    return e();
                }
                this.d += (long) (i2 - i3);
                this.b = i2;
                return this;
            }
        }

        public String toString() {
            return Multisets.immutableEntry(this.a, this.b).toString();
        }
    }

    public static final class Reference<T> {
        public T a;

        public void checkAndSet(T t, T t2) {
            if (this.a == t) {
                this.a = t2;
                return;
            }
            throw new ConcurrentModificationException();
        }

        public T get() {
            return this.a;
        }
    }

    public TreeMultiset(Comparator<? super E> comparator) {
        super(comparator);
        BoundType boundType = BoundType.OPEN;
        this.j = new GeneralRange(comparator, false, null, boundType, false, null, boundType);
        AvlNode<E> avlNode = new AvlNode<>(1, (Object) null);
        this.k = avlNode;
        avlNode.i = avlNode;
        avlNode.h = avlNode;
        this.i = new Reference<>();
    }

    public static <E extends Comparable> TreeMultiset<E> create() {
        return new TreeMultiset<>(Ordering.natural());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Comparator comparator = (Comparator) objectInputStream.readObject();
        Serialization.a(AbstractSortedMultiset.class, "comparator").a(this, comparator);
        Class<TreeMultiset> cls = TreeMultiset.class;
        Serialization.FieldSetter<TreeMultiset> a = Serialization.a(cls, "range");
        BoundType boundType = BoundType.OPEN;
        a.a(this, new GeneralRange(comparator, false, null, boundType, false, null, boundType));
        Serialization.a(cls, "rootReference").a(this, new Reference());
        AvlNode<E> avlNode = new AvlNode<>(1, (Object) null);
        Serialization.a(cls, "header").a(this, avlNode);
        avlNode.i = avlNode;
        avlNode.h = avlNode;
        Serialization.d(this, objectInputStream, objectInputStream.readInt());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(elementSet().comparator());
        Serialization.g(this, objectOutputStream);
    }

    public int add(E e, int i2) {
        CollectPreconditions.b(i2, "occurrences");
        if (i2 == 0) {
            return count(e);
        }
        Preconditions.checkArgument(this.j.a(e));
        Reference<AvlNode<E>> reference = this.i;
        AvlNode avlNode = reference.get();
        if (avlNode == null) {
            comparator().compare(e, e);
            AvlNode<E> avlNode2 = new AvlNode<>(i2, e);
            AvlNode<E> avlNode3 = this.k;
            avlNode3.i = avlNode2;
            avlNode2.h = avlNode3;
            avlNode2.i = avlNode3;
            avlNode3.h = avlNode2;
            reference.checkAndSet(avlNode, avlNode2);
            return 0;
        }
        int[] iArr = new int[1];
        reference.checkAndSet(avlNode, avlNode.a(comparator(), e, i2, iArr));
        return iArr[0];
    }

    public final int b() {
        return y3.b(l(Aggregate.f));
    }

    public final Iterator<E> c() {
        return new TransformedIterator<Multiset.Entry<Object>, Object>(new Object() {
            public AvlNode<E> c;
            public Multiset.Entry<E> f;

            /* JADX WARNING: Code restructure failed: missing block: B:14:0x004a, code lost:
                if (r1.a(r0.a) != false) goto L_0x004d;
             */
            {
                /*
                    r6 = this;
                    com.google.common.collect.TreeMultiset.this = r7
                    r6.<init>()
                    com.google.common.collect.TreeMultiset$Reference<com.google.common.collect.TreeMultiset$AvlNode<E>> r0 = r7.i
                    java.lang.Object r1 = r0.get()
                    com.google.common.collect.TreeMultiset$AvlNode r1 = (com.google.common.collect.TreeMultiset.AvlNode) r1
                    if (r1 != 0) goto L_0x0010
                    goto L_0x004c
                L_0x0010:
                    com.google.common.collect.GeneralRange<E> r1 = r7.j
                    boolean r2 = r1.f
                    com.google.common.collect.TreeMultiset$AvlNode<E> r3 = r7.k
                    if (r2 == 0) goto L_0x0040
                    java.lang.Object r0 = r0.get()
                    com.google.common.collect.TreeMultiset$AvlNode r0 = (com.google.common.collect.TreeMultiset.AvlNode) r0
                    java.util.Comparator r2 = r7.comparator()
                    T r4 = r1.g
                    com.google.common.collect.TreeMultiset$AvlNode r0 = r0.d(r2, r4)
                    if (r0 != 0) goto L_0x002b
                    goto L_0x004c
                L_0x002b:
                    com.google.common.collect.BoundType r2 = com.google.common.collect.BoundType.OPEN
                    com.google.common.collect.BoundType r5 = r1.h
                    if (r5 != r2) goto L_0x0042
                    java.util.Comparator r7 = r7.comparator()
                    E r2 = r0.a
                    int r7 = r7.compare(r4, r2)
                    if (r7 != 0) goto L_0x0042
                    com.google.common.collect.TreeMultiset$AvlNode<E> r0 = r0.i
                    goto L_0x0042
                L_0x0040:
                    com.google.common.collect.TreeMultiset$AvlNode<E> r0 = r3.i
                L_0x0042:
                    if (r0 == r3) goto L_0x004c
                    E r7 = r0.a
                    boolean r7 = r1.a(r7)
                    if (r7 != 0) goto L_0x004d
                L_0x004c:
                    r0 = 0
                L_0x004d:
                    r6.c = r0
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.TreeMultiset.AnonymousClass2.<init>(com.google.common.collect.TreeMultiset):void");
            }

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                AvlNode<E> avlNode = this.c;
                if (avlNode == null) {
                    return false;
                }
                if (!TreeMultiset.this.j.c(avlNode.a)) {
                    return true;
                }
                this.c = null;
                return false;
            }

            public void remove() {
                boolean z;
                if (this.f != null) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.e(z);
                TreeMultiset.this.setCount(this.f.getElement(), 0);
                this.f = null;
            }

            public Multiset.Entry<E> next() {
                if (hasNext()) {
                    AvlNode<E> avlNode = this.c;
                    int i = TreeMultiset.l;
                    TreeMultiset treeMultiset = TreeMultiset.this;
                    treeMultiset.getClass();
                    AnonymousClass1 r2 = new Multisets.AbstractEntry<Object>(avlNode) {
                        public final /* synthetic */ AvlNode c;

                        {
                            this.c = r2;
                        }

                        public int getCount() {
                            int i = this.c.b;
                            if (i == 0) {
                                return TreeMultiset.this.count(getElement());
                            }
                            return i;
                        }

                        public Object getElement() {
                            return this.c.a;
                        }
                    };
                    this.f = r2;
                    AvlNode<E> avlNode2 = this.c.i;
                    if (avlNode2 == treeMultiset.k) {
                        this.c = null;
                    } else {
                        this.c = avlNode2;
                    }
                    return r2;
                }
                throw new NoSuchElementException();
            }
        }) {
            /*  JADX ERROR: Method generation error
                jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.google.common.collect.Multisets.5.a(java.lang.Object):java.lang.Object, class status: UNLOADED
                	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:510)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:314)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:510)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:249)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:238)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:510)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                */
            /*  JADX ERROR: Method generation error: Method args not loaded: com.google.common.collect.Multisets.5.a(java.lang.Object):java.lang.Object, class status: UNLOADED
                jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.google.common.collect.Multisets.5.a(java.lang.Object):java.lang.Object, class status: UNLOADED
                	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:510)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:314)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:510)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:249)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:238)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:510)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                */
            public final java.lang.Object a(
/*
Method generation error in method: com.google.common.collect.Multisets.5.a(java.lang.Object):java.lang.Object, dex: classes.dex
            jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.google.common.collect.Multisets.5.a(java.lang.Object):java.lang.Object, class status: UNLOADED
            	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:510)
            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596)
            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:314)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:510)
            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596)
            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
            
*/
        };
    }

    public void clear() {
        GeneralRange<E> generalRange = this.j;
        if (generalRange.f || generalRange.i) {
            Iterators.b(new Object() {
                public AvlNode<E> c;
                public Multiset.Entry<E> f;

                public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                    Iterator.CC.$default$forEachRemaining(this, consumer);
                }

                public boolean hasNext() {
                    AvlNode<E> avlNode = this.c;
                    if (avlNode == null) {
                        return false;
                    }
                    if (!TreeMultiset.this.j.c(avlNode.a)) {
                        return true;
                    }
                    this.c = null;
                    return false;
                }

                public void remove() {
                    boolean z;
                    if (this.f != null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    CollectPreconditions.e(z);
                    TreeMultiset.this.setCount(this.f.getElement(), 0);
                    this.f = null;
                }

                public Multiset.Entry<E> next() {
                    if (hasNext()) {
                        AvlNode<E> avlNode = this.c;
                        int i = TreeMultiset.l;
                        TreeMultiset treeMultiset = TreeMultiset.this;
                        treeMultiset.getClass();
                        AnonymousClass1 r2 = new Multisets.AbstractEntry<Object>(avlNode) {
                            public final /* synthetic */ AvlNode c;

                            {
                                this.c = r2;
                            }

                            public int getCount() {
                                int i = this.c.b;
                                if (i == 0) {
                                    return TreeMultiset.this.count(getElement());
                                }
                                return i;
                            }

                            public Object getElement() {
                                return this.c.a;
                            }
                        };
                        this.f = r2;
                        AvlNode<E> avlNode2 = this.c.i;
                        if (avlNode2 == treeMultiset.k) {
                            this.c = null;
                        } else {
                            this.c = avlNode2;
                        }
                        return r2;
                    }
                    throw new NoSuchElementException();
                }
            });
            return;
        }
        AvlNode<E> avlNode = this.k;
        AvlNode<E> avlNode2 = avlNode.i;
        while (avlNode2 != avlNode) {
            AvlNode<E> avlNode3 = avlNode2.i;
            avlNode2.b = 0;
            avlNode2.f = null;
            avlNode2.g = null;
            avlNode2.h = null;
            avlNode2.i = null;
            avlNode2 = avlNode3;
        }
        avlNode.i = avlNode;
        avlNode.h = avlNode;
        this.i.a = null;
    }

    public /* bridge */ /* synthetic */ Comparator comparator() {
        return super.comparator();
    }

    public /* bridge */ /* synthetic */ boolean contains(Object obj) {
        return super.contains(obj);
    }

    public int count(Object obj) {
        try {
            AvlNode avlNode = this.i.get();
            if (this.j.a(obj)) {
                if (avlNode != null) {
                    return avlNode.count(comparator(), obj);
                }
            }
        } catch (ClassCastException | NullPointerException unused) {
        }
        return 0;
    }

    public /* bridge */ /* synthetic */ SortedMultiset descendingMultiset() {
        return super.descendingMultiset();
    }

    public /* bridge */ /* synthetic */ NavigableSet elementSet() {
        return super.elementSet();
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public final java.util.Iterator<Multiset.Entry<E>> f() {
        return new Object() {
            public AvlNode<E> c;
            public Multiset.Entry<E> f;

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                AvlNode<E> avlNode = this.c;
                if (avlNode == null) {
                    return false;
                }
                if (!TreeMultiset.this.j.c(avlNode.a)) {
                    return true;
                }
                this.c = null;
                return false;
            }

            public void remove() {
                boolean z;
                if (this.f != null) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.e(z);
                TreeMultiset.this.setCount(this.f.getElement(), 0);
                this.f = null;
            }

            public Multiset.Entry<E> next() {
                if (hasNext()) {
                    AvlNode<E> avlNode = this.c;
                    int i = TreeMultiset.l;
                    TreeMultiset treeMultiset = TreeMultiset.this;
                    treeMultiset.getClass();
                    AnonymousClass1 r2 = new Multisets.AbstractEntry<Object>(avlNode) {
                        public final /* synthetic */ AvlNode c;

                        {
                            this.c = r2;
                        }

                        public int getCount() {
                            int i = this.c.b;
                            if (i == 0) {
                                return TreeMultiset.this.count(getElement());
                            }
                            return i;
                        }

                        public Object getElement() {
                            return this.c.a;
                        }
                    };
                    this.f = r2;
                    AvlNode<E> avlNode2 = this.c.i;
                    if (avlNode2 == treeMultiset.k) {
                        this.c = null;
                    } else {
                        this.c = avlNode2;
                    }
                    return r2;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public /* bridge */ /* synthetic */ Multiset.Entry firstEntry() {
        return super.firstEntry();
    }

    public final java.util.Iterator<Multiset.Entry<E>> g() {
        return new Object() {
            public AvlNode<E> c;
            public Multiset.Entry<E> f;

            /* JADX WARNING: Code restructure failed: missing block: B:14:0x004b, code lost:
                if (r1.a(r0.a) != false) goto L_0x004e;
             */
            {
                /*
                    r7 = this;
                    com.google.common.collect.TreeMultiset.this = r8
                    r7.<init>()
                    com.google.common.collect.TreeMultiset$Reference<com.google.common.collect.TreeMultiset$AvlNode<E>> r0 = r8.i
                    java.lang.Object r1 = r0.get()
                    com.google.common.collect.TreeMultiset$AvlNode r1 = (com.google.common.collect.TreeMultiset.AvlNode) r1
                    r2 = 0
                    if (r1 != 0) goto L_0x0011
                    goto L_0x004d
                L_0x0011:
                    com.google.common.collect.GeneralRange<E> r1 = r8.j
                    boolean r3 = r1.i
                    com.google.common.collect.TreeMultiset$AvlNode<E> r4 = r8.k
                    if (r3 == 0) goto L_0x0041
                    java.lang.Object r0 = r0.get()
                    com.google.common.collect.TreeMultiset$AvlNode r0 = (com.google.common.collect.TreeMultiset.AvlNode) r0
                    java.util.Comparator r3 = r8.comparator()
                    T r5 = r1.j
                    com.google.common.collect.TreeMultiset$AvlNode r0 = r0.f(r3, r5)
                    if (r0 != 0) goto L_0x002c
                    goto L_0x004d
                L_0x002c:
                    com.google.common.collect.BoundType r3 = com.google.common.collect.BoundType.OPEN
                    com.google.common.collect.BoundType r6 = r1.k
                    if (r6 != r3) goto L_0x0043
                    java.util.Comparator r8 = r8.comparator()
                    E r3 = r0.a
                    int r8 = r8.compare(r5, r3)
                    if (r8 != 0) goto L_0x0043
                    com.google.common.collect.TreeMultiset$AvlNode<E> r0 = r0.h
                    goto L_0x0043
                L_0x0041:
                    com.google.common.collect.TreeMultiset$AvlNode<E> r0 = r4.h
                L_0x0043:
                    if (r0 == r4) goto L_0x004d
                    E r8 = r0.a
                    boolean r8 = r1.a(r8)
                    if (r8 != 0) goto L_0x004e
                L_0x004d:
                    r0 = r2
                L_0x004e:
                    r7.c = r0
                    r7.f = r2
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.TreeMultiset.AnonymousClass3.<init>(com.google.common.collect.TreeMultiset):void");
            }

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                AvlNode<E> avlNode = this.c;
                if (avlNode == null) {
                    return false;
                }
                if (!TreeMultiset.this.j.d(avlNode.a)) {
                    return true;
                }
                this.c = null;
                return false;
            }

            public void remove() {
                boolean z;
                if (this.f != null) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.e(z);
                TreeMultiset.this.setCount(this.f.getElement(), 0);
                this.f = null;
            }

            public Multiset.Entry<E> next() {
                if (hasNext()) {
                    AvlNode<E> avlNode = this.c;
                    int i = TreeMultiset.l;
                    TreeMultiset treeMultiset = TreeMultiset.this;
                    treeMultiset.getClass();
                    AnonymousClass1 r2 = new Multisets.AbstractEntry<Object>(avlNode) {
                        public final /* synthetic */ AvlNode c;

                        {
                            this.c = r2;
                        }

                        public int getCount() {
                            int i = this.c.b;
                            if (i == 0) {
                                return TreeMultiset.this.count(getElement());
                            }
                            return i;
                        }

                        public Object getElement() {
                            return this.c.a;
                        }
                    };
                    this.f = r2;
                    AvlNode<E> avlNode2 = this.c.h;
                    if (avlNode2 == treeMultiset.k) {
                        this.c = null;
                    } else {
                        this.c = avlNode2;
                    }
                    return r2;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public SortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return new TreeMultiset(this.i, this.j.b(new GeneralRange(comparator(), false, null, BoundType.OPEN, true, e, boundType)), this.k);
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public java.util.Iterator<E> iterator() {
        return Multisets.c(this);
    }

    public final long j(Aggregate aggregate, AvlNode<E> avlNode) {
        if (avlNode == null) {
            return 0;
        }
        Comparator comparator = comparator();
        GeneralRange<E> generalRange = this.j;
        int compare = comparator.compare(generalRange.j, avlNode.a);
        if (compare > 0) {
            return j(aggregate, avlNode.g);
        }
        if (compare == 0) {
            int ordinal = generalRange.k.ordinal();
            if (ordinal == 0) {
                return ((long) aggregate.b(avlNode)) + aggregate.d(avlNode.g);
            }
            if (ordinal == 1) {
                return aggregate.d(avlNode.g);
            }
            throw new AssertionError();
        }
        return j(aggregate, avlNode.f) + aggregate.d(avlNode.g) + ((long) aggregate.b(avlNode));
    }

    public final long k(Aggregate aggregate, AvlNode<E> avlNode) {
        if (avlNode == null) {
            return 0;
        }
        Comparator comparator = comparator();
        GeneralRange<E> generalRange = this.j;
        int compare = comparator.compare(generalRange.g, avlNode.a);
        if (compare < 0) {
            return k(aggregate, avlNode.f);
        }
        if (compare == 0) {
            int ordinal = generalRange.h.ordinal();
            if (ordinal == 0) {
                return ((long) aggregate.b(avlNode)) + aggregate.d(avlNode.f);
            }
            if (ordinal == 1) {
                return aggregate.d(avlNode.f);
            }
            throw new AssertionError();
        }
        return k(aggregate, avlNode.g) + aggregate.d(avlNode.f) + ((long) aggregate.b(avlNode));
    }

    public final long l(Aggregate aggregate) {
        AvlNode avlNode = this.i.get();
        long d = aggregate.d(avlNode);
        GeneralRange<E> generalRange = this.j;
        if (generalRange.f) {
            d -= k(aggregate, avlNode);
        }
        if (generalRange.i) {
            return d - j(aggregate, avlNode);
        }
        return d;
    }

    public /* bridge */ /* synthetic */ Multiset.Entry lastEntry() {
        return super.lastEntry();
    }

    public /* bridge */ /* synthetic */ Multiset.Entry pollFirstEntry() {
        return super.pollFirstEntry();
    }

    public /* bridge */ /* synthetic */ Multiset.Entry pollLastEntry() {
        return super.pollLastEntry();
    }

    public int remove(Object obj, int i2) {
        CollectPreconditions.b(i2, "occurrences");
        if (i2 == 0) {
            return count(obj);
        }
        Reference<AvlNode<E>> reference = this.i;
        AvlNode avlNode = reference.get();
        int[] iArr = new int[1];
        try {
            if (this.j.a(obj)) {
                if (avlNode != null) {
                    reference.checkAndSet(avlNode, avlNode.j(comparator(), obj, i2, iArr));
                    return iArr[0];
                }
            }
        } catch (ClassCastException | NullPointerException unused) {
        }
        return 0;
    }

    public int setCount(E e, int i2) {
        CollectPreconditions.b(i2, "count");
        boolean z = true;
        if (!this.j.a(e)) {
            if (i2 != 0) {
                z = false;
            }
            Preconditions.checkArgument(z);
            return 0;
        }
        Reference<AvlNode<E>> reference = this.i;
        AvlNode avlNode = reference.get();
        if (avlNode == null) {
            if (i2 > 0) {
                add(e, i2);
            }
            return 0;
        }
        int[] iArr = new int[1];
        reference.checkAndSet(avlNode, avlNode.p(comparator(), e, i2, iArr));
        return iArr[0];
    }

    public int size() {
        return y3.b(l(Aggregate.c));
    }

    public /* bridge */ /* synthetic */ SortedMultiset subMultiset(Object obj, BoundType boundType, Object obj2, BoundType boundType2) {
        return super.subMultiset(obj, boundType, obj2, boundType2);
    }

    public SortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return new TreeMultiset(this.i, this.j.b(new GeneralRange(comparator(), true, e, boundType, false, null, BoundType.OPEN)), this.k);
    }

    public static <E> TreeMultiset<E> create(Comparator<? super E> comparator) {
        return comparator == null ? new TreeMultiset<>(Ordering.natural()) : new TreeMultiset<>(comparator);
    }

    public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> iterable) {
        TreeMultiset<E> create = create();
        Iterables.addAll(create, iterable);
        return create;
    }

    public boolean setCount(E e, int i2, int i3) {
        CollectPreconditions.b(i3, "newCount");
        CollectPreconditions.b(i2, "oldCount");
        Preconditions.checkArgument(this.j.a(e));
        Reference<AvlNode<E>> reference = this.i;
        AvlNode avlNode = reference.get();
        if (avlNode != null) {
            int[] iArr = new int[1];
            reference.checkAndSet(avlNode, avlNode.o(comparator(), e, i2, i3, iArr));
            if (iArr[0] == i2) {
                return true;
            }
            return false;
        } else if (i2 != 0) {
            return false;
        } else {
            if (i3 > 0) {
                add(e, i3);
            }
            return true;
        }
    }

    public TreeMultiset(Reference<AvlNode<E>> reference, GeneralRange<E> generalRange, AvlNode<E> avlNode) {
        super(generalRange.c);
        this.i = reference;
        this.j = generalRange;
        this.k = avlNode;
    }
}
