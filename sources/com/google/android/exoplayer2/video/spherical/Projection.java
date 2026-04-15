package com.google.android.exoplayer2.video.spherical;

import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Projection {
    public final Mesh a;
    public final Mesh b;
    public final int c;
    public final boolean d;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawMode {
    }

    public static final class Mesh {
        public final SubMesh[] a;

        public Mesh(SubMesh... subMeshArr) {
            this.a = subMeshArr;
        }

        public SubMesh getSubMesh(int i) {
            return this.a[i];
        }

        public int getSubMeshCount() {
            return this.a.length;
        }
    }

    public static final class SubMesh {
        public final int a;
        public final int b;
        public final float[] c;
        public final float[] d;

        public SubMesh(int i, float[] fArr, float[] fArr2, int i2) {
            boolean z;
            this.a = i;
            if (((long) fArr.length) * 2 == ((long) fArr2.length) * 3) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkArgument(z);
            this.c = fArr;
            this.d = fArr2;
            this.b = i2;
        }

        public int getVertexCount() {
            return this.c.length / 3;
        }
    }

    public Projection(Mesh mesh, int i) {
        this(mesh, mesh, i);
    }

    public static Projection createEquirectangular(int i) {
        return createEquirectangular(50.0f, 36, 72, 180.0f, 360.0f, i);
    }

    public Projection(Mesh mesh, Mesh mesh2, int i) {
        this.a = mesh;
        this.b = mesh2;
        this.c = i;
        this.d = mesh == mesh2;
    }

    public static Projection createEquirectangular(float f, int i, int i2, float f2, float f3, int i3) {
        int i4;
        float f4;
        float[] fArr;
        int i5;
        int i6;
        int i7;
        float f5 = f;
        int i8 = i;
        int i9 = i2;
        float f6 = f2;
        float f7 = f3;
        Assertions.checkArgument(f5 > 0.0f);
        Assertions.checkArgument(i8 >= 1);
        Assertions.checkArgument(i9 >= 1);
        Assertions.checkArgument(f6 > 0.0f && f6 <= 180.0f);
        Assertions.checkArgument(f7 > 0.0f && f7 <= 360.0f);
        float radians = (float) Math.toRadians((double) f6);
        float radians2 = (float) Math.toRadians((double) f7);
        float f8 = radians / ((float) i8);
        float f9 = radians2 / ((float) i9);
        int i10 = i9 + 1;
        int i11 = ((i10 * 2) + 2) * i8;
        float[] fArr2 = new float[(i11 * 3)];
        float[] fArr3 = new float[(i11 * 2)];
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        while (i12 < i8) {
            float f10 = radians / 2.0f;
            float f11 = (((float) i12) * f8) - f10;
            int i15 = i12 + 1;
            float f12 = (((float) i15) * f8) - f10;
            int i16 = 0;
            while (i16 < i10) {
                float f13 = f11;
                int i17 = i15;
                int i18 = 0;
                int i19 = 2;
                while (i18 < i19) {
                    if (i18 == 0) {
                        f4 = f13;
                        i4 = i10;
                    } else {
                        i4 = i10;
                        f4 = f12;
                    }
                    float f14 = ((float) i16) * f9;
                    float f15 = f9;
                    int i20 = i13 + 1;
                    int i21 = i16;
                    double d2 = (double) f5;
                    float f16 = f8;
                    double d3 = (double) ((f14 + 3.1415927f) - (radians2 / 2.0f));
                    int i22 = i18;
                    double d4 = (double) f4;
                    float[] fArr4 = fArr3;
                    float f17 = f12;
                    fArr2[i13] = -((float) (Math.cos(d4) * Math.sin(d3) * d2));
                    int i23 = i20 + 1;
                    int i24 = i12;
                    fArr2[i20] = (float) (Math.sin(d4) * d2);
                    int i25 = i23 + 1;
                    fArr2[i23] = (float) (Math.cos(d4) * Math.cos(d3) * d2);
                    int i26 = i14 + 1;
                    fArr4[i14] = f14 / radians2;
                    int i27 = i26 + 1;
                    fArr4[i26] = (((float) (i24 + i22)) * f16) / radians;
                    if (i21 == 0 && i22 == 0) {
                        i7 = i2;
                        i6 = i21;
                        i5 = i22;
                    } else {
                        i7 = i2;
                        i6 = i21;
                        i5 = i22;
                        if (!(i6 == i7 && i5 == 1)) {
                            fArr = fArr4;
                            i14 = i27;
                            i13 = i25;
                            int i28 = i5 + 1;
                            fArr3 = fArr;
                            i12 = i24;
                            i10 = i4;
                            f9 = f15;
                            f8 = f16;
                            f12 = f17;
                            i19 = 2;
                            int i29 = i28;
                            i9 = i7;
                            i16 = i6;
                            i18 = i29;
                        }
                    }
                    System.arraycopy(fArr2, i25 - 3, fArr2, i25, 3);
                    i25 += 3;
                    fArr = fArr4;
                    System.arraycopy(fArr, i27 - 2, fArr, i27, 2);
                    i27 += 2;
                    i14 = i27;
                    i13 = i25;
                    int i282 = i5 + 1;
                    fArr3 = fArr;
                    i12 = i24;
                    i10 = i4;
                    f9 = f15;
                    f8 = f16;
                    f12 = f17;
                    i19 = 2;
                    int i292 = i282;
                    i9 = i7;
                    i16 = i6;
                    i18 = i292;
                }
                int i30 = i16;
                int i31 = i9;
                float f18 = f8;
                float f19 = f9;
                float[] fArr5 = fArr3;
                float f20 = f12;
                int i32 = i12;
                int i33 = i30 + 1;
                f11 = f13;
                i15 = i17;
                i10 = i10;
                f9 = f19;
                f12 = f20;
                int i34 = i33;
                i9 = i31;
                i16 = i34;
            }
            i8 = i;
            i12 = i15;
        }
        return new Projection(new Mesh(new SubMesh(0, fArr2, fArr3, 1)), i3);
    }
}
