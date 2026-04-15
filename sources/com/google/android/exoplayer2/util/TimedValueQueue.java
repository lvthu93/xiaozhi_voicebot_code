package com.google.android.exoplayer2.util;

import androidx.annotation.Nullable;
import java.util.Arrays;

public final class TimedValueQueue<V> {
    public long[] a;
    public V[] b;
    public int c;
    public int d;

    public TimedValueQueue() {
        this(10);
    }

    public final void a() {
        int length = this.b.length;
        if (this.d >= length) {
            int i = length * 2;
            long[] jArr = new long[i];
            V[] vArr = new Object[i];
            int i2 = this.c;
            int i3 = length - i2;
            System.arraycopy(this.a, i2, jArr, 0, i3);
            System.arraycopy(this.b, this.c, vArr, 0, i3);
            int i4 = this.c;
            if (i4 > 0) {
                System.arraycopy(this.a, 0, jArr, i3, i4);
                System.arraycopy(this.b, 0, vArr, i3, this.c);
            }
            this.a = jArr;
            this.b = vArr;
            this.c = 0;
        }
    }

    public synchronized void add(long j, V v) {
        int i = this.d;
        if (i > 0) {
            if (j <= this.a[((this.c + i) - 1) % this.b.length]) {
                clear();
            }
        }
        a();
        int i2 = this.c;
        int i3 = this.d;
        V[] vArr = this.b;
        int length = (i2 + i3) % vArr.length;
        this.a[length] = j;
        vArr[length] = v;
        this.d = i3 + 1;
    }

    @Nullable
    public final V b(long j, boolean z) {
        V v = null;
        long j2 = Long.MAX_VALUE;
        while (this.d > 0) {
            long j3 = j - this.a[this.c];
            if (j3 < 0 && (z || (-j3) >= j2)) {
                break;
            }
            v = c();
            j2 = j3;
        }
        return v;
    }

    @Nullable
    public final V c() {
        boolean z;
        if (this.d > 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        V[] vArr = this.b;
        int i = this.c;
        V v = vArr[i];
        vArr[i] = null;
        this.c = (i + 1) % vArr.length;
        this.d--;
        return v;
    }

    public synchronized void clear() {
        this.c = 0;
        this.d = 0;
        Arrays.fill(this.b, (Object) null);
    }

    @Nullable
    public synchronized V poll(long j) {
        return b(j, false);
    }

    @Nullable
    public synchronized V pollFirst() {
        V v;
        if (this.d == 0) {
            v = null;
        } else {
            v = c();
        }
        return v;
    }

    @Nullable
    public synchronized V pollFloor(long j) {
        return b(j, true);
    }

    public synchronized int size() {
        return this.d;
    }

    public TimedValueQueue(int i) {
        this.a = new long[i];
        this.b = new Object[i];
    }
}
