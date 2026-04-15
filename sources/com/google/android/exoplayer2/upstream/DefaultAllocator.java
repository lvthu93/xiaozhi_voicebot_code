package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class DefaultAllocator implements Allocator {
    public final boolean a;
    public final int b;
    @Nullable
    public final byte[] c;
    public final Allocation[] d;
    public int e;
    public int f;
    public int g;
    public Allocation[] h;

    public DefaultAllocator(boolean z, int i) {
        this(z, i, 0);
    }

    public synchronized Allocation allocate() {
        Allocation allocation;
        this.f++;
        int i = this.g;
        if (i > 0) {
            Allocation[] allocationArr = this.h;
            int i2 = i - 1;
            this.g = i2;
            allocation = (Allocation) Assertions.checkNotNull(allocationArr[i2]);
            this.h[this.g] = null;
        } else {
            allocation = new Allocation(new byte[this.b], 0);
        }
        return allocation;
    }

    public int getIndividualAllocationLength() {
        return this.b;
    }

    public synchronized int getTotalBytesAllocated() {
        return this.f * this.b;
    }

    public synchronized void release(Allocation allocation) {
        Allocation[] allocationArr = this.d;
        allocationArr[0] = allocation;
        release(allocationArr);
    }

    public synchronized void reset() {
        if (this.a) {
            setTargetBufferSize(0);
        }
    }

    public synchronized void setTargetBufferSize(int i) {
        boolean z;
        if (i < this.e) {
            z = true;
        } else {
            z = false;
        }
        this.e = i;
        if (z) {
            trim();
        }
    }

    public synchronized void trim() {
        int i = 0;
        int max = Math.max(0, Util.ceilDivide(this.e, this.b) - this.f);
        int i2 = this.g;
        if (max < i2) {
            if (this.c != null) {
                int i3 = i2 - 1;
                while (i <= i3) {
                    Allocation allocation = (Allocation) Assertions.checkNotNull(this.h[i]);
                    if (allocation.a == this.c) {
                        i++;
                    } else {
                        Allocation allocation2 = (Allocation) Assertions.checkNotNull(this.h[i3]);
                        if (allocation2.a != this.c) {
                            i3--;
                        } else {
                            Allocation[] allocationArr = this.h;
                            allocationArr[i] = allocation2;
                            allocationArr[i3] = allocation;
                            i3--;
                            i++;
                        }
                    }
                }
                max = Math.max(max, i);
                if (max >= this.g) {
                    return;
                }
            }
            Arrays.fill(this.h, max, this.g, (Object) null);
            this.g = max;
        }
    }

    public DefaultAllocator(boolean z, int i, int i2) {
        Assertions.checkArgument(i > 0);
        Assertions.checkArgument(i2 >= 0);
        this.a = z;
        this.b = i;
        this.g = i2;
        this.h = new Allocation[(i2 + 100)];
        if (i2 > 0) {
            this.c = new byte[(i2 * i)];
            for (int i3 = 0; i3 < i2; i3++) {
                this.h[i3] = new Allocation(this.c, i3 * i);
            }
        } else {
            this.c = null;
        }
        this.d = new Allocation[1];
    }

    public synchronized void release(Allocation[] allocationArr) {
        int i = this.g;
        int length = allocationArr.length + i;
        Allocation[] allocationArr2 = this.h;
        if (length >= allocationArr2.length) {
            this.h = (Allocation[]) Arrays.copyOf(allocationArr2, Math.max(allocationArr2.length * 2, i + allocationArr.length));
        }
        for (Allocation allocation : allocationArr) {
            Allocation[] allocationArr3 = this.h;
            int i2 = this.g;
            this.g = i2 + 1;
            allocationArr3[i2] = allocation;
        }
        this.f -= allocationArr.length;
        notifyAll();
    }
}
