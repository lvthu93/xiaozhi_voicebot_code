package defpackage;

import com.google.android.exoplayer2.util.Assertions;
import java.util.Arrays;

/* renamed from: e7  reason: default package */
public final class e7 {
    public final int a;
    public boolean b;
    public boolean c;
    public byte[] d;
    public int e;

    public e7(int i, int i2) {
        this.a = i;
        byte[] bArr = new byte[(i2 + 3)];
        this.d = bArr;
        bArr[2] = 1;
    }

    public void appendToNalUnit(byte[] bArr, int i, int i2) {
        if (this.b) {
            int i3 = i2 - i;
            byte[] bArr2 = this.d;
            int length = bArr2.length;
            int i4 = this.e;
            if (length < i4 + i3) {
                this.d = Arrays.copyOf(bArr2, (i4 + i3) * 2);
            }
            System.arraycopy(bArr, i, this.d, this.e, i3);
            this.e += i3;
        }
    }

    public boolean endNalUnit(int i) {
        if (!this.b) {
            return false;
        }
        this.e -= i;
        this.b = false;
        this.c = true;
        return true;
    }

    public boolean isCompleted() {
        return this.c;
    }

    public void reset() {
        this.b = false;
        this.c = false;
    }

    public void startNalUnit(int i) {
        boolean z = true;
        Assertions.checkState(!this.b);
        if (i != this.a) {
            z = false;
        }
        this.b = z;
        if (z) {
            this.e = 3;
            this.c = false;
        }
    }
}
