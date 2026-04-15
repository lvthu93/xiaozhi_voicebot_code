package defpackage;

import android.opengl.Matrix;
import com.google.android.exoplayer2.util.TimedValueQueue;

/* renamed from: j3  reason: default package */
public final class j3 {
    public final float[] a = new float[16];
    public final float[] b = new float[16];
    public final TimedValueQueue<float[]> c = new TimedValueQueue<>();
    public boolean d;

    public static void computeRecenterMatrix(float[] fArr, float[] fArr2) {
        Matrix.setIdentityM(fArr, 0);
        float f = fArr2[10];
        float f2 = fArr2[8];
        float sqrt = (float) Math.sqrt((double) ((f2 * f2) + (f * f)));
        float f3 = fArr2[10];
        fArr[0] = f3 / sqrt;
        float f4 = fArr2[8];
        fArr[2] = f4 / sqrt;
        fArr[8] = (-f4) / sqrt;
        fArr[10] = f3 / sqrt;
    }

    public boolean pollRotationMatrix(float[] fArr, long j) {
        float[] pollFloor = this.c.pollFloor(j);
        if (pollFloor == null) {
            return false;
        }
        float[] fArr2 = this.b;
        float f = pollFloor[0];
        float f2 = -pollFloor[1];
        float f3 = -pollFloor[2];
        float length = Matrix.length(f, f2, f3);
        if (length != 0.0f) {
            Matrix.setRotateM(fArr2, 0, (float) Math.toDegrees((double) length), f / length, f2 / length, f3 / length);
        } else {
            Matrix.setIdentityM(fArr2, 0);
        }
        if (!this.d) {
            computeRecenterMatrix(this.a, this.b);
            this.d = true;
        }
        Matrix.multiplyMM(fArr, 0, this.a, 0, this.b, 0);
        return true;
    }

    public void reset() {
        this.c.clear();
        this.d = false;
    }

    public void setRotation(long j, float[] fArr) {
        this.c.add(j, fArr);
    }
}
