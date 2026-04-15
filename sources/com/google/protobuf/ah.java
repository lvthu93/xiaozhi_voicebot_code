package com.google.protobuf;

import com.google.protobuf.q;
import java.io.IOException;
import java.util.Arrays;

public final class ah {
    public static final ah f = new ah(0, new int[0], new Object[0], false);
    public int a;
    public int[] b;
    public Object[] c;
    public int d;
    public boolean e;

    public ah() {
        this(0, new int[8], new Object[8], true);
    }

    public static ah e(ah ahVar, ah ahVar2) {
        int i = ahVar.a + ahVar2.a;
        int[] copyOf = Arrays.copyOf(ahVar.b, i);
        System.arraycopy(ahVar2.b, 0, copyOf, ahVar.a, ahVar2.a);
        Object[] copyOf2 = Arrays.copyOf(ahVar.c, i);
        System.arraycopy(ahVar2.c, 0, copyOf2, ahVar.a, ahVar2.a);
        return new ah(i, copyOf, copyOf2, true);
    }

    public final void a() {
        if (!this.e) {
            throw new UnsupportedOperationException();
        }
    }

    public final void b(int i) {
        int[] iArr = this.b;
        if (i > iArr.length) {
            int i2 = this.a;
            int i3 = (i2 / 2) + i2;
            if (i3 >= i) {
                i = i3;
            }
            if (i < 8) {
                i = 8;
            }
            this.b = Arrays.copyOf(iArr, i);
            this.c = Arrays.copyOf(this.c, i);
        }
    }

    public final int c() {
        int i;
        int i2 = this.d;
        if (i2 != -1) {
            return i2;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.a; i4++) {
            int i5 = this.b[i4];
            int i6 = i5 >>> 3;
            int i7 = i5 & 7;
            if (i7 == 0) {
                i = n0.v(i6, ((Long) this.c[i4]).longValue());
            } else if (i7 == 1) {
                ((Long) this.c[i4]).longValue();
                i = n0.g(i6);
            } else if (i7 == 2) {
                i = n0.c(i6, (cp) this.c[i4]);
            } else if (i7 == 3) {
                i3 = ((ah) this.c[i4]).c() + (n0.s(i6) * 2) + i3;
            } else if (i7 == 5) {
                ((Integer) this.c[i4]).intValue();
                i = n0.f(i6);
            } else {
                int i8 = q.f;
                throw new IllegalStateException(new q.a());
            }
            i3 = i + i3;
        }
        this.d = i3;
        return i3;
    }

    public final boolean d(int i, f fVar) throws IOException {
        int aa;
        a();
        int i2 = i >>> 3;
        int i3 = i & 7;
        if (i3 == 0) {
            f(i, Long.valueOf(fVar.s()));
            return true;
        } else if (i3 == 1) {
            f(i, Long.valueOf(fVar.p()));
            return true;
        } else if (i3 == 2) {
            f(i, fVar.l());
            return true;
        } else if (i3 == 3) {
            ah ahVar = new ah();
            do {
                aa = fVar.aa();
                if (aa == 0 || !ahVar.d(aa, fVar)) {
                    fVar.a((i2 << 3) | 4);
                    f(i, ahVar);
                }
                aa = fVar.aa();
                break;
            } while (!ahVar.d(aa, fVar));
            fVar.a((i2 << 3) | 4);
            f(i, ahVar);
            return true;
        } else if (i3 == 4) {
            if (fVar.b == 0) {
                fVar.a(0);
            }
            return false;
        } else if (i3 == 5) {
            f(i, Integer.valueOf(fVar.o()));
            return true;
        } else {
            int i4 = q.f;
            throw new q.a();
        }
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ah)) {
            return false;
        }
        ah ahVar = (ah) obj;
        int i = this.a;
        if (i == ahVar.a) {
            int[] iArr = this.b;
            int[] iArr2 = ahVar.b;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    z = true;
                    break;
                } else if (iArr[i2] != iArr2[i2]) {
                    z = false;
                    break;
                } else {
                    i2++;
                }
            }
            if (z) {
                Object[] objArr = this.c;
                Object[] objArr2 = ahVar.c;
                int i3 = this.a;
                int i4 = 0;
                while (true) {
                    if (i4 >= i3) {
                        z2 = true;
                        break;
                    } else if (!objArr[i4].equals(objArr2[i4])) {
                        z2 = false;
                        break;
                    } else {
                        i4++;
                    }
                }
                if (!z2) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public final void f(int i, Object obj) {
        a();
        b(this.a + 1);
        int[] iArr = this.b;
        int i2 = this.a;
        iArr[i2] = i;
        this.c[i2] = obj;
        this.a = i2 + 1;
    }

    public final void g(jf jfVar) throws IOException {
        if (this.a != 0) {
            jfVar.getClass();
            for (int i = 0; i < this.a; i++) {
                int i2 = this.b[i];
                Object obj = this.c[i];
                int i3 = i2 >>> 3;
                int i4 = i2 & 7;
                if (i4 == 0) {
                    ((o0) jfVar).k(i3, ((Long) obj).longValue());
                } else if (i4 == 1) {
                    ((o0) jfVar).g(i3, ((Long) obj).longValue());
                } else if (i4 == 2) {
                    ((o0) jfVar).b(i3, (cp) obj);
                } else if (i4 == 3) {
                    o0 o0Var = (o0) jfVar;
                    o0Var.r(i3);
                    ((ah) obj).g(jfVar);
                    o0Var.d(i3);
                } else if (i4 == 5) {
                    ((o0) jfVar).f(i3, ((Integer) obj).intValue());
                } else {
                    throw new RuntimeException(q.d());
                }
            }
        }
    }

    public final int hashCode() {
        int i = this.a;
        int i2 = (527 + i) * 31;
        int[] iArr = this.b;
        int i3 = 17;
        int i4 = 17;
        for (int i5 = 0; i5 < i; i5++) {
            i4 = (i4 * 31) + iArr[i5];
        }
        int i6 = (i2 + i4) * 31;
        Object[] objArr = this.c;
        int i7 = this.a;
        for (int i8 = 0; i8 < i7; i8++) {
            i3 = (i3 * 31) + objArr[i8].hashCode();
        }
        return i6 + i3;
    }

    public ah(int i, int[] iArr, Object[] objArr, boolean z) {
        this.d = -1;
        this.a = i;
        this.b = iArr;
        this.c = objArr;
        this.e = z;
    }
}
