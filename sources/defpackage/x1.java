package defpackage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Token;

/* renamed from: x1  reason: default package */
public final class x1 {
    public static final byte[] h = {0, 7, 8, 15};
    public static final byte[] i = {0, 119, -120, -1};
    public static final byte[] j = {0, 17, 34, 51, 68, 85, 102, 119, -120, -103, -86, -69, -52, -35, -18, -1};
    public final Paint a;
    public final Paint b;
    public final Canvas c = new Canvas();
    public final b d = new b(719, 575, 0, 719, 0, 575);
    public final a e = new a(0, new int[]{0, -1, ViewCompat.MEASURED_STATE_MASK, -8421505}, a(), b());
    public final h f;
    public Bitmap g;

    /* renamed from: x1$a */
    public static final class a {
        public final int a;
        public final int[] b;
        public final int[] c;
        public final int[] d;

        public a(int i, int[] iArr, int[] iArr2, int[] iArr3) {
            this.a = i;
            this.b = iArr;
            this.c = iArr2;
            this.d = iArr3;
        }
    }

    /* renamed from: x1$b */
    public static final class b {
        public final int a;
        public final int b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;

        public b(int i, int i2, int i3, int i4, int i5, int i6) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
            this.f = i6;
        }
    }

    /* renamed from: x1$c */
    public static final class c {
        public final int a;
        public final boolean b;
        public final byte[] c;
        public final byte[] d;

        public c(int i, boolean z, byte[] bArr, byte[] bArr2) {
            this.a = i;
            this.b = z;
            this.c = bArr;
            this.d = bArr2;
        }
    }

    /* renamed from: x1$d */
    public static final class d {
        public final int a;
        public final int b;
        public final SparseArray<e> c;

        public d(int i, int i2, int i3, SparseArray<e> sparseArray) {
            this.a = i2;
            this.b = i3;
            this.c = sparseArray;
        }
    }

    /* renamed from: x1$e */
    public static final class e {
        public final int a;
        public final int b;

        public e(int i, int i2) {
            this.a = i;
            this.b = i2;
        }
    }

    /* renamed from: x1$f */
    public static final class f {
        public final int a;
        public final boolean b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;
        public final int g;
        public final int h;
        public final int i;
        public final SparseArray<g> j;

        public f(int i2, boolean z, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, SparseArray<g> sparseArray) {
            this.a = i2;
            this.b = z;
            this.c = i3;
            this.d = i4;
            this.e = i6;
            this.f = i7;
            this.g = i8;
            this.h = i9;
            this.i = i10;
            this.j = sparseArray;
        }

        public void mergeFrom(f fVar) {
            SparseArray<g> sparseArray = fVar.j;
            for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                this.j.put(sparseArray.keyAt(i2), sparseArray.valueAt(i2));
            }
        }
    }

    /* renamed from: x1$g */
    public static final class g {
        public final int a;
        public final int b;

        public g(int i, int i2, int i3, int i4, int i5, int i6) {
            this.a = i3;
            this.b = i4;
        }
    }

    /* renamed from: x1$h */
    public static final class h {
        public final int a;
        public final int b;
        public final SparseArray<f> c = new SparseArray<>();
        public final SparseArray<a> d = new SparseArray<>();
        public final SparseArray<c> e = new SparseArray<>();
        public final SparseArray<a> f = new SparseArray<>();
        public final SparseArray<c> g = new SparseArray<>();
        @Nullable
        public b h;
        @Nullable
        public d i;

        public h(int i2, int i3) {
            this.a = i2;
            this.b = i3;
        }

        public void reset() {
            this.c.clear();
            this.d.clear();
            this.e.clear();
            this.f.clear();
            this.g.clear();
            this.h = null;
            this.i = null;
        }
    }

    public x1(int i2, int i3) {
        Paint paint = new Paint();
        this.a = paint;
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint.setPathEffect((PathEffect) null);
        Paint paint2 = new Paint();
        this.b = paint2;
        paint2.setStyle(Paint.Style.FILL);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        paint2.setPathEffect((PathEffect) null);
        this.f = new h(i2, i3);
    }

    public static int[] a() {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int[] iArr = new int[16];
        iArr[0] = 0;
        for (int i7 = 1; i7 < 16; i7++) {
            if (i7 < 8) {
                if ((i7 & 1) != 0) {
                    i4 = 255;
                } else {
                    i4 = 0;
                }
                if ((i7 & 2) != 0) {
                    i5 = 255;
                } else {
                    i5 = 0;
                }
                if ((i7 & 4) != 0) {
                    i6 = 255;
                } else {
                    i6 = 0;
                }
                iArr[i7] = c(255, i4, i5, i6);
            } else {
                int i8 = i7 & 1;
                int i9 = Token.VOID;
                if (i8 != 0) {
                    i2 = Token.VOID;
                } else {
                    i2 = 0;
                }
                if ((i7 & 2) != 0) {
                    i3 = Token.VOID;
                } else {
                    i3 = 0;
                }
                if ((i7 & 4) == 0) {
                    i9 = 0;
                }
                iArr[i7] = c(255, i2, i3, i9);
            }
        }
        return iArr;
    }

    public static int[] b() {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int[] iArr = new int[256];
        iArr[0] = 0;
        for (int i20 = 0; i20 < 256; i20++) {
            int i21 = 255;
            if (i20 < 8) {
                if ((i20 & 1) != 0) {
                    i18 = 255;
                } else {
                    i18 = 0;
                }
                if ((i20 & 2) != 0) {
                    i19 = 255;
                } else {
                    i19 = 0;
                }
                if ((i20 & 4) == 0) {
                    i21 = 0;
                }
                iArr[i20] = c(63, i18, i19, i21);
            } else {
                int i22 = i20 & Token.JSR;
                int i23 = Context.VERSION_1_7;
                int i24 = 85;
                if (i22 == 0) {
                    if ((i20 & 1) != 0) {
                        i2 = 85;
                    } else {
                        i2 = 0;
                    }
                    if ((i20 & 16) != 0) {
                        i3 = Context.VERSION_1_7;
                    } else {
                        i3 = 0;
                    }
                    int i25 = i2 + i3;
                    if ((i20 & 2) != 0) {
                        i4 = 85;
                    } else {
                        i4 = 0;
                    }
                    if ((i20 & 32) != 0) {
                        i5 = Context.VERSION_1_7;
                    } else {
                        i5 = 0;
                    }
                    int i26 = i4 + i5;
                    if ((i20 & 4) == 0) {
                        i24 = 0;
                    }
                    if ((i20 & 64) == 0) {
                        i23 = 0;
                    }
                    iArr[i20] = c(255, i25, i26, i24 + i23);
                } else if (i22 != 8) {
                    int i27 = 43;
                    if (i22 == 128) {
                        if ((i20 & 1) != 0) {
                            i10 = 43;
                        } else {
                            i10 = 0;
                        }
                        int i28 = i10 + Token.VOID;
                        if ((i20 & 16) != 0) {
                            i11 = 85;
                        } else {
                            i11 = 0;
                        }
                        int i29 = i28 + i11;
                        if ((i20 & 2) != 0) {
                            i12 = 43;
                        } else {
                            i12 = 0;
                        }
                        int i30 = i12 + Token.VOID;
                        if ((i20 & 32) != 0) {
                            i13 = 85;
                        } else {
                            i13 = 0;
                        }
                        int i31 = i30 + i13;
                        if ((i20 & 4) == 0) {
                            i27 = 0;
                        }
                        int i32 = i27 + Token.VOID;
                        if ((i20 & 64) == 0) {
                            i24 = 0;
                        }
                        iArr[i20] = c(255, i29, i31, i32 + i24);
                    } else if (i22 == 136) {
                        if ((i20 & 1) != 0) {
                            i14 = 43;
                        } else {
                            i14 = 0;
                        }
                        if ((i20 & 16) != 0) {
                            i15 = 85;
                        } else {
                            i15 = 0;
                        }
                        int i33 = i14 + i15;
                        if ((i20 & 2) != 0) {
                            i16 = 43;
                        } else {
                            i16 = 0;
                        }
                        if ((i20 & 32) != 0) {
                            i17 = 85;
                        } else {
                            i17 = 0;
                        }
                        int i34 = i16 + i17;
                        if ((i20 & 4) == 0) {
                            i27 = 0;
                        }
                        if ((i20 & 64) == 0) {
                            i24 = 0;
                        }
                        iArr[i20] = c(255, i33, i34, i27 + i24);
                    }
                } else {
                    if ((i20 & 1) != 0) {
                        i6 = 85;
                    } else {
                        i6 = 0;
                    }
                    if ((i20 & 16) != 0) {
                        i7 = Context.VERSION_1_7;
                    } else {
                        i7 = 0;
                    }
                    int i35 = i6 + i7;
                    if ((i20 & 2) != 0) {
                        i8 = 85;
                    } else {
                        i8 = 0;
                    }
                    if ((i20 & 32) != 0) {
                        i9 = Context.VERSION_1_7;
                    } else {
                        i9 = 0;
                    }
                    int i36 = i8 + i9;
                    if ((i20 & 4) == 0) {
                        i24 = 0;
                    }
                    if ((i20 & 64) == 0) {
                        i23 = 0;
                    }
                    iArr[i20] = c(Token.VOID, i35, i36, i24 + i23);
                }
            }
        }
        return iArr;
    }

    public static int c(int i2, int i3, int i4, int i5) {
        return (i2 << 24) | (i3 << 16) | (i4 << 8) | i5;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v18, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v23, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v44, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v26, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v27, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v28, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v29, resolved type: byte} */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v13, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r3v5, types: [byte] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x01b9 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x01e2 A[LOOP:3: B:80:0x0158->B:110:0x01e2, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x012e A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x01dc A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00ff A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0134 A[LOOP:2: B:35:0x0098->B:67:0x0134, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x015f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void d(byte[] r23, int[] r24, int r25, int r26, int r27, @androidx.annotation.Nullable android.graphics.Paint r28, android.graphics.Canvas r29) {
        /*
            r0 = r25
            r7 = r28
            com.google.android.exoplayer2.util.ParsableBitArray r8 = new com.google.android.exoplayer2.util.ParsableBitArray
            r1 = r23
            r8.<init>(r1)
            r1 = r26
            r10 = r27
            r11 = 0
            r12 = 0
            r13 = 0
        L_0x0012:
            int r2 = r8.bitsLeft()
            if (r2 == 0) goto L_0x0221
            r14 = 8
            int r2 = r8.readBits(r14)
            r3 = 240(0xf0, float:3.36E-43)
            if (r2 == r3) goto L_0x021b
            r15 = 4
            r6 = 2
            r5 = 1
            r16 = 0
            r4 = 3
            switch(r2) {
                case 16: goto L_0x013d;
                case 17: goto L_0x0088;
                case 18: goto L_0x002f;
                default: goto L_0x002b;
            }
        L_0x002b:
            switch(r2) {
                case 32: goto L_0x020b;
                case 33: goto L_0x01f9;
                case 34: goto L_0x01e6;
                default: goto L_0x002e;
            }
        L_0x002e:
            goto L_0x0012
        L_0x002f:
            r15 = r1
            r1 = 0
        L_0x0031:
            int r2 = r8.readBits(r14)
            if (r2 == 0) goto L_0x003c
            r17 = r1
            r18 = 1
            goto L_0x0062
        L_0x003c:
            boolean r2 = r8.readBit()
            r3 = 7
            if (r2 != 0) goto L_0x0055
            int r2 = r8.readBits(r3)
            if (r2 == 0) goto L_0x004f
            r17 = r1
            r18 = r2
            r2 = 0
            goto L_0x0062
        L_0x004f:
            r2 = 0
            r17 = 1
            r18 = 0
            goto L_0x0062
        L_0x0055:
            int r2 = r8.readBits(r3)
            int r3 = r8.readBits(r14)
            r17 = r1
            r18 = r2
            r2 = r3
        L_0x0062:
            if (r18 == 0) goto L_0x007d
            if (r7 == 0) goto L_0x007d
            r1 = r24[r2]
            r7.setColor(r1)
            float r2 = (float) r15
            float r3 = (float) r10
            int r1 = r15 + r18
            float r4 = (float) r1
            int r1 = r10 + 1
            float r6 = (float) r1
            r1 = r29
            r9 = 1
            r5 = r6
            r6 = r28
            r1.drawRect(r2, r3, r4, r5, r6)
            goto L_0x007e
        L_0x007d:
            r9 = 1
        L_0x007e:
            int r15 = r15 + r18
            if (r17 == 0) goto L_0x0084
            r1 = r15
            goto L_0x0012
        L_0x0084:
            r1 = r17
            r5 = 1
            goto L_0x0031
        L_0x0088:
            r9 = 1
            if (r0 != r4) goto L_0x0094
            if (r12 != 0) goto L_0x0090
            byte[] r2 = j
            goto L_0x0091
        L_0x0090:
            r2 = r12
        L_0x0091:
            r17 = r2
            goto L_0x0096
        L_0x0094:
            r17 = 0
        L_0x0096:
            r5 = r1
            r1 = 0
        L_0x0098:
            int r2 = r8.readBits(r15)
            if (r2 == 0) goto L_0x009f
            goto L_0x00f8
        L_0x009f:
            boolean r2 = r8.readBit()
            if (r2 != 0) goto L_0x00b5
            int r2 = r8.readBits(r4)
            if (r2 == 0) goto L_0x00b3
            int r2 = r2 + 2
            r18 = r1
            r19 = r2
            r3 = 0
            goto L_0x00fd
        L_0x00b3:
            r1 = 1
            goto L_0x00d1
        L_0x00b5:
            boolean r2 = r8.readBit()
            if (r2 != 0) goto L_0x00c5
            int r2 = r8.readBits(r6)
            int r2 = r2 + r15
            int r3 = r8.readBits(r15)
            goto L_0x00ec
        L_0x00c5:
            int r2 = r8.readBits(r6)
            if (r2 == 0) goto L_0x00f7
            if (r2 == r9) goto L_0x00f1
            if (r2 == r6) goto L_0x00e2
            if (r2 == r4) goto L_0x00d7
        L_0x00d1:
            r18 = r1
            r3 = 0
            r19 = 0
            goto L_0x00fd
        L_0x00d7:
            int r2 = r8.readBits(r14)
            int r2 = r2 + 25
            int r3 = r8.readBits(r15)
            goto L_0x00ec
        L_0x00e2:
            int r2 = r8.readBits(r15)
            int r2 = r2 + 9
            int r3 = r8.readBits(r15)
        L_0x00ec:
            r18 = r1
            r19 = r2
            goto L_0x00fd
        L_0x00f1:
            r18 = r1
            r3 = 0
            r19 = 2
            goto L_0x00fd
        L_0x00f7:
            r2 = 0
        L_0x00f8:
            r18 = r1
            r3 = r2
            r19 = 1
        L_0x00fd:
            if (r19 == 0) goto L_0x0126
            if (r7 == 0) goto L_0x0126
            if (r17 == 0) goto L_0x0105
            byte r3 = r17[r3]
        L_0x0105:
            r1 = r24[r3]
            r7.setColor(r1)
            float r2 = (float) r5
            float r3 = (float) r10
            int r1 = r5 + r19
            float r1 = (float) r1
            int r4 = r10 + 1
            float r4 = (float) r4
            r20 = r1
            r1 = r29
            r21 = r4
            r15 = 3
            r4 = r20
            r20 = r5
            r5 = r21
            r14 = 2
            r6 = r28
            r1.drawRect(r2, r3, r4, r5, r6)
            goto L_0x012a
        L_0x0126:
            r20 = r5
            r14 = 2
            r15 = 3
        L_0x012a:
            int r5 = r20 + r19
            if (r18 == 0) goto L_0x0134
            r8.byteAlign()
            r1 = r5
            goto L_0x0012
        L_0x0134:
            r1 = r18
            r4 = 3
            r6 = 2
            r14 = 8
            r15 = 4
            goto L_0x0098
        L_0x013d:
            r9 = 1
            r14 = 2
            r15 = 3
            if (r0 != r15) goto L_0x0149
            if (r11 != 0) goto L_0x0147
            byte[] r2 = i
            goto L_0x0151
        L_0x0147:
            r2 = r11
            goto L_0x0151
        L_0x0149:
            if (r0 != r14) goto L_0x0154
            if (r13 != 0) goto L_0x0150
            byte[] r2 = h
            goto L_0x0151
        L_0x0150:
            r2 = r13
        L_0x0151:
            r17 = r2
            goto L_0x0156
        L_0x0154:
            r17 = 0
        L_0x0156:
            r6 = r1
            r5 = 0
        L_0x0158:
            int r1 = r8.readBits(r14)
            if (r1 == 0) goto L_0x015f
            goto L_0x0176
        L_0x015f:
            boolean r1 = r8.readBit()
            if (r1 == 0) goto L_0x016f
            int r1 = r8.readBits(r15)
            int r1 = r1 + r15
            int r2 = r8.readBits(r14)
            goto L_0x01a5
        L_0x016f:
            boolean r1 = r8.readBit()
            if (r1 == 0) goto L_0x017b
            r1 = 0
        L_0x0176:
            r18 = r5
            r19 = 1
            goto L_0x01b7
        L_0x017b:
            int r1 = r8.readBits(r14)
            if (r1 == 0) goto L_0x01b1
            if (r1 == r9) goto L_0x01ab
            if (r1 == r14) goto L_0x0195
            if (r1 == r15) goto L_0x0188
            goto L_0x01b2
        L_0x0188:
            r1 = 8
            int r2 = r8.readBits(r1)
            int r2 = r2 + 29
            int r1 = r8.readBits(r14)
            goto L_0x01a0
        L_0x0195:
            r1 = 4
            int r2 = r8.readBits(r1)
            int r2 = r2 + 12
            int r1 = r8.readBits(r14)
        L_0x01a0:
            r22 = r2
            r2 = r1
            r1 = r22
        L_0x01a5:
            r19 = r1
            r1 = r2
            r18 = r5
            goto L_0x01b7
        L_0x01ab:
            r18 = r5
            r1 = 0
            r19 = 2
            goto L_0x01b7
        L_0x01b1:
            r5 = 1
        L_0x01b2:
            r18 = r5
            r1 = 0
            r19 = 0
        L_0x01b7:
            if (r19 == 0) goto L_0x01d6
            if (r7 == 0) goto L_0x01d6
            if (r17 == 0) goto L_0x01bf
            byte r1 = r17[r1]
        L_0x01bf:
            r1 = r24[r1]
            r7.setColor(r1)
            float r2 = (float) r6
            float r3 = (float) r10
            int r1 = r6 + r19
            float r4 = (float) r1
            int r1 = r10 + 1
            float r5 = (float) r1
            r1 = r29
            r20 = r6
            r6 = r28
            r1.drawRect(r2, r3, r4, r5, r6)
            goto L_0x01d8
        L_0x01d6:
            r20 = r6
        L_0x01d8:
            int r6 = r20 + r19
            if (r18 == 0) goto L_0x01e2
            r8.byteAlign()
            r1 = r6
            goto L_0x0012
        L_0x01e2:
            r5 = r18
            goto L_0x0158
        L_0x01e6:
            r2 = 16
            byte[] r12 = new byte[r2]
            r3 = 0
        L_0x01eb:
            if (r3 >= r2) goto L_0x0012
            r4 = 8
            int r5 = r8.readBits(r4)
            byte r4 = (byte) r5
            r12[r3] = r4
            int r3 = r3 + 1
            goto L_0x01eb
        L_0x01f9:
            r2 = 4
            byte[] r11 = new byte[r2]
            r3 = 0
        L_0x01fd:
            if (r3 >= r2) goto L_0x0012
            r4 = 8
            int r5 = r8.readBits(r4)
            byte r5 = (byte) r5
            r11[r3] = r5
            int r3 = r3 + 1
            goto L_0x01fd
        L_0x020b:
            r2 = 4
            byte[] r13 = new byte[r2]
            r3 = 0
        L_0x020f:
            if (r3 >= r2) goto L_0x0012
            int r4 = r8.readBits(r2)
            byte r4 = (byte) r4
            r13[r3] = r4
            int r3 = r3 + 1
            goto L_0x020f
        L_0x021b:
            int r10 = r10 + 2
            r1 = r26
            goto L_0x0012
        L_0x0221:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x1.d(byte[], int[], int, int, int, android.graphics.Paint, android.graphics.Canvas):void");
    }

    public static a e(ParsableBitArray parsableBitArray, int i2) {
        int[] iArr;
        int i3;
        int i4;
        int i5;
        int i6;
        ParsableBitArray parsableBitArray2 = parsableBitArray;
        int i7 = 8;
        int readBits = parsableBitArray2.readBits(8);
        parsableBitArray2.skipBits(8);
        int i8 = i2 - 2;
        int i9 = 4;
        int[] iArr2 = {0, -1, ViewCompat.MEASURED_STATE_MASK, -8421505};
        int[] a2 = a();
        int[] b2 = b();
        while (i8 > 0) {
            int readBits2 = parsableBitArray2.readBits(i7);
            int readBits3 = parsableBitArray2.readBits(i7);
            int i10 = i8 - 2;
            if ((readBits3 & 128) != 0) {
                iArr = iArr2;
            } else if ((readBits3 & 64) != 0) {
                iArr = a2;
            } else {
                iArr = b2;
            }
            if ((readBits3 & 1) != 0) {
                i6 = parsableBitArray2.readBits(i7);
                i5 = parsableBitArray2.readBits(i7);
                i4 = parsableBitArray2.readBits(i7);
                i3 = parsableBitArray2.readBits(i7);
                i8 = i10 - 4;
            } else {
                int readBits4 = parsableBitArray2.readBits(2) << 6;
                i8 = i10 - 2;
                i4 = parsableBitArray2.readBits(i9) << i9;
                int readBits5 = parsableBitArray2.readBits(i9) << i9;
                i3 = readBits4;
                i6 = parsableBitArray2.readBits(6) << 2;
                i5 = readBits5;
            }
            if (i6 == 0) {
                i5 = 0;
                i4 = 0;
                i3 = 255;
            }
            double d2 = (double) i6;
            double d3 = (double) (i5 - 128);
            double d4 = (double) (i4 - 128);
            iArr[readBits2] = c((byte) (255 - (i3 & 255)), Util.constrainValue((int) ((1.402d * d3) + d2), 0, 255), Util.constrainValue((int) ((d2 - (0.34414d * d4)) - (d3 * 0.71414d)), 0, 255), Util.constrainValue((int) ((d4 * 1.772d) + d2), 0, 255));
            iArr2 = iArr2;
            readBits = readBits;
            i7 = 8;
            i9 = 4;
        }
        return new a(readBits, iArr2, a2, b2);
    }

    public static c f(ParsableBitArray parsableBitArray) {
        byte[] bArr;
        int readBits = parsableBitArray.readBits(16);
        parsableBitArray.skipBits(4);
        int readBits2 = parsableBitArray.readBits(2);
        boolean readBit = parsableBitArray.readBit();
        parsableBitArray.skipBits(1);
        byte[] bArr2 = Util.f;
        if (readBits2 == 1) {
            parsableBitArray.skipBits(parsableBitArray.readBits(8) * 16);
        } else if (readBits2 == 0) {
            int readBits3 = parsableBitArray.readBits(16);
            int readBits4 = parsableBitArray.readBits(16);
            if (readBits3 > 0) {
                bArr2 = new byte[readBits3];
                parsableBitArray.readBytes(bArr2, 0, readBits3);
            }
            if (readBits4 > 0) {
                bArr = new byte[readBits4];
                parsableBitArray.readBytes(bArr, 0, readBits4);
                return new c(readBits, readBit, bArr2, bArr);
            }
        }
        bArr = bArr2;
        return new c(readBits, readBit, bArr2, bArr);
    }

    public List<Cue> decode(byte[] bArr, int i2) {
        h hVar;
        int i3;
        int i4;
        Canvas canvas;
        int i5;
        int i6;
        int i7;
        b bVar;
        ArrayList arrayList;
        h hVar2;
        d dVar;
        int i8;
        f fVar;
        Paint paint;
        int[] iArr;
        f fVar2;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        ParsableBitArray parsableBitArray = new ParsableBitArray(bArr, i2);
        while (true) {
            int bitsLeft = parsableBitArray.bitsLeft();
            hVar = this.f;
            if (bitsLeft < 48 || parsableBitArray.readBits(8) != 15) {
                d dVar2 = hVar.i;
            } else {
                int readBits = parsableBitArray.readBits(8);
                int readBits2 = parsableBitArray.readBits(16);
                int readBits3 = parsableBitArray.readBits(16);
                int bytePosition = parsableBitArray.getBytePosition() + readBits3;
                if (readBits3 * 8 > parsableBitArray.bitsLeft()) {
                    Log.w("DvbParser", "Data field length exceeds limit");
                    parsableBitArray.skipBits(parsableBitArray.bitsLeft());
                } else {
                    switch (readBits) {
                        case 16:
                            if (readBits2 == hVar.a) {
                                d dVar3 = hVar.i;
                                int readBits4 = parsableBitArray.readBits(8);
                                int readBits5 = parsableBitArray.readBits(4);
                                int readBits6 = parsableBitArray.readBits(2);
                                parsableBitArray.skipBits(2);
                                int i15 = readBits3 - 2;
                                SparseArray sparseArray = new SparseArray();
                                while (i15 > 0) {
                                    int readBits7 = parsableBitArray.readBits(8);
                                    parsableBitArray.skipBits(8);
                                    i15 -= 6;
                                    sparseArray.put(readBits7, new e(parsableBitArray.readBits(16), parsableBitArray.readBits(16)));
                                }
                                d dVar4 = new d(readBits4, readBits5, readBits6, sparseArray);
                                if (dVar4.b == 0) {
                                    if (!(dVar3 == null || dVar3.a == dVar4.a)) {
                                        hVar.i = dVar4;
                                        break;
                                    }
                                } else {
                                    hVar.i = dVar4;
                                    hVar.c.clear();
                                    hVar.d.clear();
                                    hVar.e.clear();
                                    break;
                                }
                            }
                            break;
                        case 17:
                            d dVar5 = hVar.i;
                            if (readBits2 == hVar.a && dVar5 != null) {
                                int readBits8 = parsableBitArray.readBits(8);
                                parsableBitArray.skipBits(4);
                                boolean readBit = parsableBitArray.readBit();
                                parsableBitArray.skipBits(3);
                                int readBits9 = parsableBitArray.readBits(16);
                                int readBits10 = parsableBitArray.readBits(16);
                                int readBits11 = parsableBitArray.readBits(3);
                                int readBits12 = parsableBitArray.readBits(3);
                                parsableBitArray.skipBits(2);
                                int readBits13 = parsableBitArray.readBits(8);
                                int readBits14 = parsableBitArray.readBits(8);
                                int readBits15 = parsableBitArray.readBits(4);
                                int readBits16 = parsableBitArray.readBits(2);
                                parsableBitArray.skipBits(2);
                                int i16 = readBits3 - 10;
                                SparseArray sparseArray2 = new SparseArray();
                                while (i16 > 0) {
                                    int readBits17 = parsableBitArray.readBits(16);
                                    int readBits18 = parsableBitArray.readBits(2);
                                    int readBits19 = parsableBitArray.readBits(2);
                                    int readBits20 = parsableBitArray.readBits(12);
                                    parsableBitArray.skipBits(4);
                                    int readBits21 = parsableBitArray.readBits(12);
                                    i16 -= 6;
                                    if (readBits18 == 1 || readBits18 == 2) {
                                        i16 -= 2;
                                        i10 = parsableBitArray.readBits(8);
                                        i9 = parsableBitArray.readBits(8);
                                    } else {
                                        i10 = 0;
                                        i9 = 0;
                                    }
                                    sparseArray2.put(readBits17, new g(readBits18, readBits19, readBits20, readBits21, i10, i9));
                                }
                                f fVar3 = new f(readBits8, readBit, readBits9, readBits10, readBits11, readBits12, readBits13, readBits14, readBits15, readBits16, sparseArray2);
                                SparseArray<f> sparseArray3 = hVar.c;
                                int i17 = dVar5.b;
                                int i18 = fVar3.a;
                                if (i17 == 0 && (fVar2 = sparseArray3.get(i18)) != null) {
                                    fVar3.mergeFrom(fVar2);
                                }
                                sparseArray3.put(i18, fVar3);
                                break;
                            }
                        case 18:
                            if (readBits2 != hVar.a) {
                                if (readBits2 == hVar.b) {
                                    a e2 = e(parsableBitArray, readBits3);
                                    hVar.f.put(e2.a, e2);
                                    break;
                                }
                            } else {
                                a e3 = e(parsableBitArray, readBits3);
                                hVar.d.put(e3.a, e3);
                                break;
                            }
                            break;
                        case 19:
                            if (readBits2 != hVar.a) {
                                if (readBits2 == hVar.b) {
                                    c f2 = f(parsableBitArray);
                                    hVar.g.put(f2.a, f2);
                                    break;
                                }
                            } else {
                                c f3 = f(parsableBitArray);
                                hVar.e.put(f3.a, f3);
                                break;
                            }
                            break;
                        case 20:
                            if (readBits2 == hVar.a) {
                                parsableBitArray.skipBits(4);
                                boolean readBit2 = parsableBitArray.readBit();
                                parsableBitArray.skipBits(3);
                                int readBits22 = parsableBitArray.readBits(16);
                                int readBits23 = parsableBitArray.readBits(16);
                                if (readBit2) {
                                    int readBits24 = parsableBitArray.readBits(16);
                                    i13 = parsableBitArray.readBits(16);
                                    i12 = parsableBitArray.readBits(16);
                                    i11 = parsableBitArray.readBits(16);
                                    i14 = readBits24;
                                } else {
                                    i13 = readBits22;
                                    i11 = readBits23;
                                    i14 = 0;
                                    i12 = 0;
                                }
                                hVar.h = new b(readBits22, readBits23, i14, i13, i12, i11);
                                break;
                            }
                            break;
                    }
                    parsableBitArray.skipBytes(bytePosition - parsableBitArray.getBytePosition());
                }
            }
        }
        d dVar22 = hVar.i;
        if (dVar22 == null) {
            return Collections.emptyList();
        }
        b bVar2 = hVar.h;
        if (bVar2 == null) {
            bVar2 = this.d;
        }
        Bitmap bitmap = this.g;
        Canvas canvas2 = this.c;
        if (!(bitmap != null && bVar2.a + 1 == bitmap.getWidth() && bVar2.b + 1 == this.g.getHeight())) {
            Bitmap createBitmap = Bitmap.createBitmap(bVar2.a + 1, bVar2.b + 1, Bitmap.Config.ARGB_8888);
            this.g = createBitmap;
            canvas2.setBitmap(createBitmap);
        }
        ArrayList arrayList2 = new ArrayList();
        int i19 = 0;
        while (true) {
            SparseArray<e> sparseArray4 = dVar22.c;
            if (i19 >= sparseArray4.size()) {
                return Collections.unmodifiableList(arrayList2);
            }
            canvas2.save();
            e valueAt = sparseArray4.valueAt(i19);
            f fVar4 = hVar.c.get(sparseArray4.keyAt(i19));
            int i20 = valueAt.a + bVar2.c;
            int i21 = valueAt.b + bVar2.e;
            int min = Math.min(fVar4.c + i20, bVar2.d);
            int i22 = fVar4.d;
            canvas2.clipRect(i20, i21, min, Math.min(i21 + i22, bVar2.f));
            SparseArray<a> sparseArray5 = hVar.d;
            int i23 = fVar4.f;
            a aVar = sparseArray5.get(i23);
            if (aVar == null && (aVar = hVar.f.get(i23)) == null) {
                aVar = this.e;
            }
            int i24 = 0;
            while (true) {
                SparseArray<g> sparseArray6 = fVar4.j;
                if (i24 < sparseArray6.size()) {
                    int keyAt = sparseArray6.keyAt(i24);
                    g valueAt2 = sparseArray6.valueAt(i24);
                    c cVar = hVar.e.get(keyAt);
                    if (cVar == null) {
                        cVar = hVar.g.get(keyAt);
                    }
                    if (cVar != null) {
                        if (cVar.b) {
                            paint = null;
                        } else {
                            paint = this.a;
                        }
                        dVar = dVar22;
                        int i25 = fVar4.e;
                        hVar2 = hVar;
                        int i26 = valueAt2.a + i20;
                        int i27 = i21 + valueAt2.b;
                        if (i25 == 3) {
                            iArr = aVar.d;
                        } else if (i25 == 2) {
                            iArr = aVar.c;
                        } else {
                            iArr = aVar.b;
                        }
                        arrayList = arrayList2;
                        int i28 = i22;
                        int[] iArr2 = iArr;
                        bVar = bVar2;
                        int i29 = i21;
                        int i30 = i25;
                        i7 = i28;
                        i8 = i20;
                        int i31 = i26;
                        i6 = i29;
                        fVar = fVar4;
                        i5 = i19;
                        Paint paint2 = paint;
                        canvas = canvas2;
                        d(cVar.c, iArr2, i30, i31, i27, paint2, canvas2);
                        d(cVar.d, iArr2, i30, i31, i27 + 1, paint2, canvas2);
                    } else {
                        dVar = dVar22;
                        bVar = bVar2;
                        arrayList = arrayList2;
                        hVar2 = hVar;
                        i7 = i22;
                        i6 = i21;
                        i8 = i20;
                        fVar = fVar4;
                        i5 = i19;
                        canvas = canvas2;
                    }
                    i24++;
                    fVar4 = fVar;
                    i20 = i8;
                    dVar22 = dVar;
                    hVar = hVar2;
                    arrayList2 = arrayList;
                    bVar2 = bVar;
                    i22 = i7;
                    i21 = i6;
                    i19 = i5;
                    canvas2 = canvas;
                } else {
                    d dVar6 = dVar22;
                    b bVar3 = bVar2;
                    ArrayList arrayList3 = arrayList2;
                    h hVar3 = hVar;
                    int i32 = i22;
                    int i33 = i21;
                    int i34 = i20;
                    f fVar5 = fVar4;
                    int i35 = i19;
                    Canvas canvas3 = canvas2;
                    boolean z = fVar5.b;
                    int i36 = fVar5.c;
                    if (z) {
                        int i37 = fVar5.e;
                        if (i37 == 3) {
                            i4 = aVar.d[fVar5.g];
                        } else if (i37 == 2) {
                            i4 = aVar.c[fVar5.h];
                        } else {
                            i4 = aVar.b[fVar5.i];
                        }
                        Paint paint3 = this.b;
                        paint3.setColor(i4);
                        i3 = i33;
                        canvas3.drawRect((float) i34, (float) i3, (float) (i34 + i36), (float) (i3 + i32), paint3);
                    } else {
                        i3 = i33;
                    }
                    int i38 = i32;
                    Cue.Builder bitmap2 = new Cue.Builder().setBitmap(Bitmap.createBitmap(this.g, i34, i3, i36, i38));
                    b bVar4 = bVar3;
                    int i39 = bVar4.a;
                    Cue.Builder positionAnchor = bitmap2.setPosition(((float) i34) / ((float) i39)).setPositionAnchor(0);
                    int i40 = bVar4.b;
                    ArrayList arrayList4 = arrayList3;
                    arrayList4.add(positionAnchor.setLine(((float) i3) / ((float) i40), 0).setLineAnchor(0).setSize(((float) i36) / ((float) i39)).setBitmapHeight(((float) i38) / ((float) i40)).build());
                    Canvas canvas4 = canvas3;
                    canvas4.drawColor(0, PorterDuff.Mode.CLEAR);
                    canvas4.restore();
                    i19 = i35 + 1;
                    canvas2 = canvas4;
                    dVar22 = dVar6;
                    hVar = hVar3;
                    arrayList2 = arrayList4;
                    bVar2 = bVar4;
                }
            }
        }
    }

    public void reset() {
        this.f.reset();
    }
}
