package com.google.common.collect;

import com.google.common.base.Preconditions;
import defpackage.w3;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

final class TopKSelector<T> {
    public final int a;
    public final Comparator<? super T> b;
    public final T[] c;
    public int d;
    public T e;

    public TopKSelector(int i, Comparator comparator) {
        boolean z;
        this.b = (Comparator) Preconditions.checkNotNull(comparator, "comparator");
        this.a = i;
        if (i >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "k must be nonnegative, was %s", i);
        this.c = new Object[(i * 2)];
        this.d = 0;
        this.e = null;
    }

    public static <T extends Comparable<? super T>> TopKSelector<T> greatest(int i) {
        return greatest(i, Ordering.natural());
    }

    public static <T extends Comparable<? super T>> TopKSelector<T> least(int i) {
        return least(i, Ordering.natural());
    }

    public void offer(T t) {
        int i;
        boolean z;
        boolean z2;
        int i2 = this.a;
        if (i2 != 0) {
            int i3 = this.d;
            int i4 = 0;
            T[] tArr = this.c;
            if (i3 == 0) {
                tArr[0] = t;
                this.e = t;
                this.d = 1;
                return;
            }
            Comparator<? super T> comparator = this.b;
            if (i3 < i2) {
                this.d = i3 + 1;
                tArr[i3] = t;
                if (comparator.compare(t, this.e) > 0) {
                    this.e = t;
                }
            } else if (comparator.compare(t, this.e) < 0) {
                int i5 = this.d;
                int i6 = i5 + 1;
                this.d = i6;
                tArr[i5] = t;
                int i7 = i2 * 2;
                if (i6 == i7) {
                    int i8 = i7 - 1;
                    int i9 = i8 + 0;
                    RoundingMode roundingMode = RoundingMode.CEILING;
                    if (i9 > 0) {
                        switch (w3.a.a[roundingMode.ordinal()]) {
                            case 1:
                                if (i9 > 0) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                                if (((i9 - 1) & i9) == 0) {
                                    z2 = true;
                                } else {
                                    z2 = false;
                                }
                                if (!z || !z2) {
                                    throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
                                }
                                break;
                            case 2:
                            case 3:
                                break;
                            case 4:
                            case 5:
                                i = 32 - Integer.numberOfLeadingZeros(i9 - 1);
                                break;
                            case 6:
                            case 7:
                            case 8:
                                int numberOfLeadingZeros = Integer.numberOfLeadingZeros(i9);
                                i = (31 - numberOfLeadingZeros) + ((~(~((-1257966797 >>> numberOfLeadingZeros) - i9))) >>> 31);
                                break;
                            default:
                                throw new AssertionError();
                        }
                        i = 31 - Integer.numberOfLeadingZeros(i9);
                        int i10 = i * 3;
                        int i11 = 0;
                        int i12 = 0;
                        while (true) {
                            if (i4 < i8) {
                                int i13 = ((i4 + i8) + 1) >>> 1;
                                T t2 = tArr[i13];
                                tArr[i13] = tArr[i8];
                                int i14 = i4;
                                int i15 = i14;
                                while (i14 < i8) {
                                    if (comparator.compare(tArr[i14], t2) < 0) {
                                        T t3 = tArr[i15];
                                        tArr[i15] = tArr[i14];
                                        tArr[i14] = t3;
                                        i15++;
                                    }
                                    i14++;
                                }
                                tArr[i8] = tArr[i15];
                                tArr[i15] = t2;
                                if (i15 > i2) {
                                    i8 = i15 - 1;
                                } else if (i15 < i2) {
                                    i4 = Math.max(i15, i4 + 1);
                                    i12 = i15;
                                }
                                i11++;
                                if (i11 >= i10) {
                                    Arrays.sort(tArr, i4, i8, comparator);
                                }
                            }
                        }
                        this.d = i2;
                        this.e = tArr[i12];
                        for (int i16 = i12 + 1; i16 < i2; i16++) {
                            if (comparator.compare(tArr[i16], this.e) > 0) {
                                this.e = tArr[i16];
                            }
                        }
                        return;
                    }
                    throw new IllegalArgumentException("x (" + i9 + ") must be > 0");
                }
            }
        }
    }

    public void offerAll(Iterable<? extends T> iterable) {
        offerAll(iterable.iterator());
    }

    public List<T> topK() {
        int i = this.d;
        Comparator<? super T> comparator = this.b;
        T[] tArr = this.c;
        Arrays.sort(tArr, 0, i, comparator);
        int i2 = this.d;
        int i3 = this.a;
        if (i2 > i3) {
            Arrays.fill(tArr, i3, tArr.length, (Object) null);
            this.d = i3;
            this.e = tArr[i3 - 1];
        }
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(tArr, this.d)));
    }

    public static <T> TopKSelector<T> greatest(int i, Comparator<? super T> comparator) {
        return new TopKSelector<>(i, Ordering.from(comparator).reverse());
    }

    public static <T> TopKSelector<T> least(int i, Comparator<? super T> comparator) {
        return new TopKSelector<>(i, comparator);
    }

    public void offerAll(Iterator<? extends T> it) {
        while (it.hasNext()) {
            offer(it.next());
        }
    }
}
