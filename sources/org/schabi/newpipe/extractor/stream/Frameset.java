package org.schabi.newpipe.extractor.stream;

import java.io.Serializable;
import java.util.List;

public final class Frameset implements Serializable {
    public final List<String> c;
    public final int f;
    public final int g;
    public final int h;
    public final int i;
    public final int j;
    public final int k;

    public Frameset(List<String> list, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.c = list;
        this.h = i4;
        this.i = i5;
        this.f = i2;
        this.g = i3;
        this.j = i6;
        this.k = i7;
    }

    public int getDurationPerFrame() {
        return this.i;
    }

    public int[] getFrameBoundsAt(long j2) {
        int i2 = this.g;
        int i3 = this.f;
        if (j2 >= 0) {
            int i4 = this.h;
            long j3 = (long) (i4 + 1);
            int i5 = this.i;
            if (j2 <= j3 * ((long) i5)) {
                int i6 = this.j;
                int i7 = this.k;
                int i8 = i6 * i7;
                int min = Math.min((int) (j2 / ((long) i5)), i4);
                int i9 = min % i8;
                int i10 = i9 / i6;
                if (i9 - (i6 * i10) != 0 && (((i6 ^ i9) >> 31) | 1) < 0) {
                    i10--;
                }
                int i11 = i9 % i7;
                int[] iArr = new int[5];
                int i12 = min / i8;
                if (min - (i8 * i12) != 0 && (((min ^ i8) >> 31) | 1) < 0) {
                    i12--;
                }
                iArr[0] = i12;
                iArr[1] = i11 * i3;
                iArr[2] = i10 * i2;
                iArr[3] = (i11 * i3) + i3;
                iArr[4] = (i10 * i2) + i2;
                return iArr;
            }
        }
        return new int[]{0, 0, 0, i3, i2};
    }

    public int getFrameHeight() {
        return this.g;
    }

    public int getFrameWidth() {
        return this.f;
    }

    public int getFramesPerPageX() {
        return this.j;
    }

    public int getFramesPerPageY() {
        return this.k;
    }

    public int getTotalCount() {
        return this.h;
    }

    public List<String> getUrls() {
        return this.c;
    }
}
