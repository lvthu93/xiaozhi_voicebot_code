package com.google.android.exoplayer2.util;

import java.util.NoSuchElementException;

public final class IntArrayQueue {
    public int a = 0;
    public int b = -1;
    public int c = 0;
    public int[] d;
    public int e;

    public IntArrayQueue() {
        int[] iArr = new int[16];
        this.d = iArr;
        this.e = iArr.length - 1;
    }

    public void add(int i) {
        int i2 = this.c;
        int[] iArr = this.d;
        if (i2 == iArr.length) {
            int length = iArr.length << 1;
            if (length >= 0) {
                int[] iArr2 = new int[length];
                int length2 = iArr.length;
                int i3 = this.a;
                int i4 = length2 - i3;
                System.arraycopy(iArr, i3, iArr2, 0, i4);
                System.arraycopy(this.d, 0, iArr2, i4, i3);
                this.a = 0;
                this.b = this.c - 1;
                this.d = iArr2;
                this.e = length - 1;
            } else {
                throw new IllegalStateException();
            }
        }
        int i5 = (this.b + 1) & this.e;
        this.b = i5;
        this.d[i5] = i;
        this.c++;
    }

    public int capacity() {
        return this.d.length;
    }

    public void clear() {
        this.a = 0;
        this.b = -1;
        this.c = 0;
    }

    public boolean isEmpty() {
        return this.c == 0;
    }

    public int remove() {
        int i = this.c;
        if (i != 0) {
            int[] iArr = this.d;
            int i2 = this.a;
            int i3 = iArr[i2];
            this.a = (i2 + 1) & this.e;
            this.c = i - 1;
            return i3;
        }
        throw new NoSuchElementException();
    }

    public int size() {
        return this.c;
    }
}
