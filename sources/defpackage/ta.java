package defpackage;

import java.nio.charset.Charset;
import java.util.Arrays;

/* renamed from: ta  reason: default package */
public final class ta extends cq {
    public final transient byte[][] j;
    public final transient int[] k;

    public ta(ck ckVar, int i) {
        super((byte[]) null);
        jd.a(ckVar.f, 0, (long) i);
        qa qaVar = ckVar.c;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            int i5 = qaVar.c;
            int i6 = qaVar.b;
            if (i5 != i6) {
                i3 += i5 - i6;
                i4++;
                qaVar = qaVar.f;
            } else {
                throw new AssertionError("s.limit == s.pos");
            }
        }
        this.j = new byte[i4][];
        this.k = new int[(i4 * 2)];
        qa qaVar2 = ckVar.c;
        int i7 = 0;
        while (i2 < i) {
            byte[][] bArr = this.j;
            bArr[i7] = qaVar2.a;
            int i8 = qaVar2.c;
            int i9 = qaVar2.b;
            int i10 = (i8 - i9) + i2;
            if (i10 > i) {
                i2 = i;
            } else {
                i2 = i10;
            }
            int[] iArr = this.k;
            iArr[i7] = i2;
            iArr[bArr.length + i7] = i9;
            qaVar2.d = true;
            i7++;
            qaVar2 = qaVar2.f;
        }
    }

    private Object writeReplace() {
        return aa();
    }

    public final cq aa() {
        return new cq(w());
    }

    public final String b() {
        return aa().b();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof cq) {
            cq cqVar = (cq) obj;
            if (cqVar.t() != t() || !s(cqVar, t())) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i = this.f;
        if (i != 0) {
            return i;
        }
        byte[][] bArr = this.j;
        int length = bArr.length;
        int i2 = 0;
        int i3 = 0;
        int i4 = 1;
        while (i2 < length) {
            byte[] bArr2 = bArr[i2];
            int[] iArr = this.k;
            int i5 = iArr[length + i2];
            int i6 = iArr[i2];
            int i7 = (i6 - i3) + i5;
            while (i5 < i7) {
                i4 = (i4 * 31) + bArr2[i5];
                i5++;
            }
            i2++;
            i3 = i6;
        }
        this.f = i4;
        return i4;
    }

    public final byte n(int i) {
        int i2;
        byte[][] bArr = this.j;
        int[] iArr = this.k;
        jd.a((long) iArr[bArr.length - 1], (long) i, 1);
        int z = z(i);
        if (z == 0) {
            i2 = 0;
        } else {
            i2 = iArr[z - 1];
        }
        return bArr[z][(i - i2) + iArr[bArr.length + z]];
    }

    public final String o() {
        return aa().o();
    }

    public final byte[] p() {
        return w();
    }

    public final boolean r(int i, int i2, int i3, byte[] bArr) {
        int i4;
        if (i < 0 || i > t() - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int z = z(i);
        while (true) {
            boolean z2 = true;
            if (i3 <= 0) {
                return true;
            }
            int[] iArr = this.k;
            if (z == 0) {
                i4 = 0;
            } else {
                i4 = iArr[z - 1];
            }
            int min = Math.min(i3, ((iArr[z] - i4) + i4) - i);
            byte[][] bArr2 = this.j;
            int i5 = (i - i4) + iArr[bArr2.length + z];
            byte[] bArr3 = bArr2[z];
            Charset charset = jd.a;
            int i6 = 0;
            while (true) {
                if (i6 >= min) {
                    break;
                } else if (bArr3[i6 + i5] != bArr[i6 + i2]) {
                    z2 = false;
                    break;
                } else {
                    i6++;
                }
            }
            if (!z2) {
                return false;
            }
            i += min;
            i2 += min;
            i3 -= min;
            z++;
        }
    }

    public final boolean s(cq cqVar, int i) {
        int i2;
        if (t() - i < 0) {
            return false;
        }
        int z = z(0);
        int i3 = 0;
        int i4 = 0;
        while (i > 0) {
            int[] iArr = this.k;
            if (z == 0) {
                i2 = 0;
            } else {
                i2 = iArr[z - 1];
            }
            int min = Math.min(i, ((iArr[z] - i2) + i2) - i3);
            byte[][] bArr = this.j;
            if (!cqVar.r(i4, (i3 - i2) + iArr[bArr.length + z], min, bArr[z])) {
                return false;
            }
            i3 += min;
            i4 += min;
            i -= min;
            z++;
        }
        return true;
    }

    public final int t() {
        return this.k[this.j.length - 1];
    }

    public final String toString() {
        return aa().toString();
    }

    public final cq u() {
        return aa().u();
    }

    public final cq v() {
        return aa().v();
    }

    public final byte[] w() {
        byte[][] bArr = this.j;
        int[] iArr = this.k;
        byte[] bArr2 = new byte[iArr[bArr.length - 1]];
        int length = bArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = iArr[length + i];
            int i4 = iArr[i];
            System.arraycopy(bArr[i], i3, bArr2, i2, i4 - i2);
            i++;
            i2 = i4;
        }
        return bArr2;
    }

    public final String x() {
        return aa().x();
    }

    public final void y(ck ckVar) {
        byte[][] bArr = this.j;
        int length = bArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int[] iArr = this.k;
            int i3 = iArr[length + i];
            int i4 = iArr[i];
            qa qaVar = new qa(bArr[i], i3, (i3 + i4) - i2, true, false);
            qa qaVar2 = ckVar.c;
            if (qaVar2 == null) {
                qaVar.g = qaVar;
                qaVar.f = qaVar;
                ckVar.c = qaVar;
            } else {
                qaVar2.g.b(qaVar);
            }
            i++;
            i2 = i4;
        }
        ckVar.f += (long) i2;
    }

    public final int z(int i) {
        int binarySearch = Arrays.binarySearch(this.k, 0, this.j.length, i + 1);
        return binarySearch >= 0 ? binarySearch : ~binarySearch;
    }
}
