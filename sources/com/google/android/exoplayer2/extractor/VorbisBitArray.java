package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.util.Assertions;

public final class VorbisBitArray {
    public final byte[] a;
    public final int b;
    public int c;
    public int d;

    public VorbisBitArray(byte[] bArr) {
        this.a = bArr;
        this.b = bArr.length;
    }

    public int bitsLeft() {
        return ((this.b - this.c) * 8) - this.d;
    }

    public int getPosition() {
        return (this.c * 8) + this.d;
    }

    public boolean readBit() {
        boolean z;
        if ((((this.a[this.c] & 255) >> this.d) & 1) == 1) {
            z = true;
        } else {
            z = false;
        }
        skipBits(1);
        return z;
    }

    public int readBits(int i) {
        int i2 = this.c;
        int min = Math.min(i, 8 - this.d);
        int i3 = i2 + 1;
        byte[] bArr = this.a;
        int i4 = ((bArr[i2] & 255) >> this.d) & (255 >> (8 - min));
        while (min < i) {
            i4 |= (bArr[i3] & 255) << min;
            min += 8;
            i3++;
        }
        int i5 = i4 & (-1 >>> (32 - i));
        skipBits(i);
        return i5;
    }

    public void reset() {
        this.c = 0;
        this.d = 0;
    }

    public void setPosition(int i) {
        boolean z;
        int i2;
        int i3 = i / 8;
        this.c = i3;
        int i4 = i - (i3 * 8);
        this.d = i4;
        if (i3 < 0 || (i3 >= (i2 = this.b) && !(i3 == i2 && i4 == 0))) {
            z = false;
        } else {
            z = true;
        }
        Assertions.checkState(z);
    }

    public void skipBits(int i) {
        int i2;
        int i3 = i / 8;
        int i4 = this.c + i3;
        this.c = i4;
        int i5 = (i - (i3 * 8)) + this.d;
        this.d = i5;
        boolean z = true;
        if (i5 > 7) {
            this.c = i4 + 1;
            this.d = i5 - 8;
        }
        int i6 = this.c;
        if (i6 < 0 || (i6 >= (i2 = this.b) && !(i6 == i2 && this.d == 0))) {
            z = false;
        }
        Assertions.checkState(z);
    }
}
