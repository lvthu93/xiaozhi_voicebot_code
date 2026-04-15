package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;

public final class DefaultExtractorInput implements ExtractorInput {
    public final byte[] a = new byte[4096];
    public final DataReader b;
    public final long c;
    public long d;
    public byte[] e = new byte[65536];
    public int f;
    public int g;

    public DefaultExtractorInput(DataReader dataReader, long j, long j2) {
        this.b = dataReader;
        this.d = j;
        this.c = j2;
    }

    public final void a(int i) {
        int i2 = this.f + i;
        byte[] bArr = this.e;
        if (i2 > bArr.length) {
            this.e = Arrays.copyOf(this.e, Util.constrainValue(bArr.length * 2, 65536 + i2, i2 + 524288));
        }
    }

    public boolean advancePeekPosition(int i, boolean z) throws IOException {
        a(i);
        int i2 = this.g - this.f;
        while (i2 < i) {
            i2 = b(this.e, this.f, i, i2, z);
            if (i2 == -1) {
                return false;
            }
            this.g = this.f + i2;
        }
        this.f += i;
        return true;
    }

    public final int b(byte[] bArr, int i, int i2, int i3, boolean z) throws IOException {
        if (!Thread.interrupted()) {
            int read = this.b.read(bArr, i + i3, i2 - i3);
            if (read != -1) {
                return i3 + read;
            }
            if (i3 == 0 && z) {
                return -1;
            }
            throw new EOFException();
        }
        throw new InterruptedIOException();
    }

    public final void c(int i) {
        byte[] bArr;
        int i2 = this.g - i;
        this.g = i2;
        this.f = 0;
        byte[] bArr2 = this.e;
        if (i2 < bArr2.length - 524288) {
            bArr = new byte[(65536 + i2)];
        } else {
            bArr = bArr2;
        }
        System.arraycopy(bArr2, i, bArr, 0, i2);
        this.e = bArr;
    }

    public long getLength() {
        return this.c;
    }

    public long getPeekPosition() {
        return this.d + ((long) this.f);
    }

    public long getPosition() {
        return this.d;
    }

    public int peek(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        a(i2);
        int i4 = this.g;
        int i5 = this.f;
        int i6 = i4 - i5;
        if (i6 == 0) {
            i3 = b(this.e, i5, i2, 0, true);
            if (i3 == -1) {
                return -1;
            }
            this.g += i3;
        } else {
            i3 = Math.min(i2, i6);
        }
        System.arraycopy(this.e, this.f, bArr, i, i3);
        this.f += i3;
        return i3;
    }

    public boolean peekFully(byte[] bArr, int i, int i2, boolean z) throws IOException {
        if (!advancePeekPosition(i2, z)) {
            return false;
        }
        System.arraycopy(this.e, this.f - i2, bArr, i, i2);
        return true;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.g;
        int i4 = 0;
        if (i3 != 0) {
            int min = Math.min(i3, i2);
            System.arraycopy(this.e, 0, bArr, i, min);
            c(min);
            i4 = min;
        }
        if (i4 == 0) {
            i4 = b(bArr, i, i2, 0, true);
        }
        if (i4 != -1) {
            this.d += (long) i4;
        }
        return i4;
    }

    public void readFully(byte[] bArr, int i, int i2) throws IOException {
        readFully(bArr, i, i2, false);
    }

    public void resetPeekPosition() {
        this.f = 0;
    }

    public <E extends Throwable> void setRetryPosition(long j, E e2) throws Throwable {
        boolean z;
        if (j >= 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        this.d = j;
        throw e2;
    }

    public int skip(int i) throws IOException {
        int min = Math.min(this.g, i);
        c(min);
        if (min == 0) {
            byte[] bArr = this.a;
            min = b(bArr, 0, Math.min(i, bArr.length), 0, true);
        }
        if (min != -1) {
            this.d += (long) min;
        }
        return min;
    }

    public void skipFully(int i) throws IOException {
        skipFully(i, false);
    }

    public boolean readFully(byte[] bArr, int i, int i2, boolean z) throws IOException {
        int i3;
        int i4 = this.g;
        if (i4 == 0) {
            i3 = 0;
        } else {
            i3 = Math.min(i4, i2);
            System.arraycopy(this.e, 0, bArr, i, i3);
            c(i3);
        }
        int i5 = i3;
        while (i5 < i2 && i5 != -1) {
            i5 = b(bArr, i, i2, i5, z);
        }
        if (i5 != -1) {
            this.d += (long) i5;
        }
        if (i5 != -1) {
            return true;
        }
        return false;
    }

    public boolean skipFully(int i, boolean z) throws IOException {
        int min = Math.min(this.g, i);
        c(min);
        int i2 = min;
        while (i2 < i && i2 != -1) {
            i2 = b(this.a, -i2, Math.min(i, this.a.length + i2), i2, z);
        }
        if (i2 != -1) {
            this.d += (long) i2;
        }
        return i2 != -1;
    }

    public void peekFully(byte[] bArr, int i, int i2) throws IOException {
        peekFully(bArr, i, i2, false);
    }

    public void advancePeekPosition(int i) throws IOException {
        advancePeekPosition(i, false);
    }
}
