package com.google.android.exoplayer2.util;

import androidx.core.view.MotionEventCompat;
import com.google.common.base.Charsets;
import java.nio.charset.Charset;

public final class ParsableBitArray {
    public byte[] a;
    public int b;
    public int c;
    public int d;

    public ParsableBitArray() {
        this.a = Util.f;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r1 = r2.d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a() {
        /*
            r2 = this;
            int r0 = r2.b
            if (r0 < 0) goto L_0x0010
            int r1 = r2.d
            if (r0 < r1) goto L_0x000e
            if (r0 != r1) goto L_0x0010
            int r0 = r2.c
            if (r0 != 0) goto L_0x0010
        L_0x000e:
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            com.google.android.exoplayer2.util.Assertions.checkState(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.ParsableBitArray.a():void");
    }

    public int bitsLeft() {
        return ((this.d - this.b) * 8) - this.c;
    }

    public void byteAlign() {
        if (this.c != 0) {
            this.c = 0;
            this.b++;
            a();
        }
    }

    public int getBytePosition() {
        boolean z;
        if (this.c == 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        return this.b;
    }

    public int getPosition() {
        return (this.b * 8) + this.c;
    }

    public void putInt(int i, int i2) {
        if (i2 < 32) {
            i &= (1 << i2) - 1;
        }
        int min = Math.min(8 - this.c, i2);
        int i3 = this.c;
        int i4 = (8 - i3) - min;
        byte b2 = (MotionEventCompat.ACTION_POINTER_INDEX_MASK >> i3) | ((1 << i4) - 1);
        byte[] bArr = this.a;
        int i5 = this.b;
        byte b3 = (byte) (b2 & bArr[i5]);
        bArr[i5] = b3;
        int i6 = i2 - min;
        bArr[i5] = (byte) (b3 | ((i >>> i6) << i4));
        int i7 = i5 + 1;
        while (i6 > 8) {
            this.a[i7] = (byte) (i >>> (i6 - 8));
            i6 -= 8;
            i7++;
        }
        int i8 = 8 - i6;
        byte[] bArr2 = this.a;
        byte b4 = (byte) (bArr2[i7] & ((1 << i8) - 1));
        bArr2[i7] = b4;
        bArr2[i7] = (byte) (((i & ((1 << i6) - 1)) << i8) | b4);
        skipBits(i2);
        a();
    }

    public boolean readBit() {
        boolean z;
        if ((this.a[this.b] & (128 >> this.c)) != 0) {
            z = true;
        } else {
            z = false;
        }
        skipBit();
        return z;
    }

    public int readBits(int i) {
        int i2;
        if (i == 0) {
            return 0;
        }
        this.c += i;
        int i3 = 0;
        while (true) {
            i2 = this.c;
            if (i2 <= 8) {
                break;
            }
            int i4 = i2 - 8;
            this.c = i4;
            byte[] bArr = this.a;
            int i5 = this.b;
            this.b = i5 + 1;
            i3 |= (bArr[i5] & 255) << i4;
        }
        byte[] bArr2 = this.a;
        int i6 = this.b;
        int i7 = (-1 >>> (32 - i)) & (i3 | ((bArr2[i6] & 255) >> (8 - i2)));
        if (i2 == 8) {
            this.c = 0;
            this.b = i6 + 1;
        }
        a();
        return i7;
    }

    public long readBitsToLong(int i) {
        if (i <= 32) {
            return Util.toUnsignedLong(readBits(i));
        }
        return Util.toLong(readBits(i - 32), readBits(32));
    }

    public void readBytes(byte[] bArr, int i, int i2) {
        boolean z;
        if (this.c == 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        System.arraycopy(this.a, this.b, bArr, i, i2);
        this.b += i2;
        a();
    }

    public String readBytesAsString(int i) {
        return readBytesAsString(i, Charsets.c);
    }

    public void reset(byte[] bArr) {
        reset(bArr, bArr.length);
    }

    public void setPosition(int i) {
        int i2 = i / 8;
        this.b = i2;
        this.c = i - (i2 * 8);
        a();
    }

    public void skipBit() {
        int i = this.c + 1;
        this.c = i;
        if (i == 8) {
            this.c = 0;
            this.b++;
        }
        a();
    }

    public void skipBits(int i) {
        int i2 = i / 8;
        int i3 = this.b + i2;
        this.b = i3;
        int i4 = (i - (i2 * 8)) + this.c;
        this.c = i4;
        if (i4 > 7) {
            this.b = i3 + 1;
            this.c = i4 - 8;
        }
        a();
    }

    public void skipBytes(int i) {
        boolean z;
        if (this.c == 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        this.b += i;
        a();
    }

    public String readBytesAsString(int i, Charset charset) {
        byte[] bArr = new byte[i];
        readBytes(bArr, 0, i);
        return new String(bArr, charset);
    }

    public void reset(ParsableByteArray parsableByteArray) {
        reset(parsableByteArray.getData(), parsableByteArray.limit());
        setPosition(parsableByteArray.getPosition() * 8);
    }

    public ParsableBitArray(byte[] bArr) {
        this(bArr, bArr.length);
    }

    public ParsableBitArray(byte[] bArr, int i) {
        this.a = bArr;
        this.d = i;
    }

    public void reset(byte[] bArr, int i) {
        this.a = bArr;
        this.b = 0;
        this.c = 0;
        this.d = i;
    }

    public void readBits(byte[] bArr, int i, int i2) {
        int i3 = (i2 >> 3) + i;
        while (i < i3) {
            byte[] bArr2 = this.a;
            int i4 = this.b;
            int i5 = i4 + 1;
            this.b = i5;
            byte b2 = bArr2[i4];
            int i6 = this.c;
            byte b3 = (byte) (b2 << i6);
            bArr[i] = b3;
            bArr[i] = (byte) (((255 & bArr2[i5]) >> (8 - i6)) | b3);
            i++;
        }
        int i7 = i2 & 7;
        if (i7 != 0) {
            byte b4 = (byte) (bArr[i3] & (255 >> i7));
            bArr[i3] = b4;
            int i8 = this.c;
            if (i8 + i7 > 8) {
                byte[] bArr3 = this.a;
                int i9 = this.b;
                this.b = i9 + 1;
                bArr[i3] = (byte) (b4 | ((bArr3[i9] & 255) << i8));
                this.c = i8 - 8;
            }
            int i10 = this.c + i7;
            this.c = i10;
            byte[] bArr4 = this.a;
            int i11 = this.b;
            bArr[i3] = (byte) (((byte) (((255 & bArr4[i11]) >> (8 - i10)) << (8 - i7))) | bArr[i3]);
            if (i10 == 8) {
                this.c = 0;
                this.b = i11 + 1;
            }
            a();
        }
    }
}
