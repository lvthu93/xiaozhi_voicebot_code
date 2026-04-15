package defpackage;

import android.graphics.SurfaceTexture;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.opengl.Matrix;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.GlUtil;
import com.google.android.exoplayer2.util.TimedValueQueue;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import com.google.android.exoplayer2.video.spherical.Projection;
import com.google.android.exoplayer2.video.spherical.a;
import com.google.android.exoplayer2.video.spherical.b;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: la  reason: default package */
public final class la implements VideoFrameMetadataListener, CameraMotionListener {
    public final AtomicBoolean c = new AtomicBoolean();
    public final AtomicBoolean f = new AtomicBoolean(true);
    public final b g = new b();
    public final j3 h = new j3();
    public final TimedValueQueue<Long> i = new TimedValueQueue<>();
    public final TimedValueQueue<Projection> j = new TimedValueQueue<>();
    public final float[] k = new float[16];
    public final float[] l = new float[16];
    public int m;
    public SurfaceTexture n;
    public volatile int o = 0;
    public int p = -1;
    @Nullable
    public byte[] q;

    public void drawFrame(float[] fArr, boolean z) {
        b.a aVar;
        float[] fArr2;
        GLES20.glClear(16384);
        GlUtil.checkGlError();
        boolean compareAndSet = this.c.compareAndSet(true, false);
        b bVar = this.g;
        if (compareAndSet) {
            ((SurfaceTexture) Assertions.checkNotNull(this.n)).updateTexImage();
            GlUtil.checkGlError();
            boolean compareAndSet2 = this.f.compareAndSet(true, false);
            float[] fArr3 = this.k;
            if (compareAndSet2) {
                Matrix.setIdentityM(fArr3, 0);
            }
            long timestamp = this.n.getTimestamp();
            Long poll = this.i.poll(timestamp);
            if (poll != null) {
                this.h.pollRotationMatrix(fArr3, poll.longValue());
            }
            Projection pollFloor = this.j.pollFloor(timestamp);
            if (pollFloor != null) {
                bVar.setProjection(pollFloor);
            }
        }
        Matrix.multiplyMM(this.l, 0, fArr, 0, this.k, 0);
        int i2 = this.m;
        if (z) {
            aVar = bVar.c;
        } else {
            aVar = bVar.b;
        }
        if (aVar != null) {
            GLES20.glUseProgram(bVar.d);
            GlUtil.checkGlError();
            GLES20.glEnableVertexAttribArray(bVar.g);
            GLES20.glEnableVertexAttribArray(bVar.h);
            GlUtil.checkGlError();
            int i3 = bVar.a;
            if (i3 == 1) {
                if (z) {
                    fArr2 = b.n;
                } else {
                    fArr2 = b.m;
                }
            } else if (i3 != 2) {
                fArr2 = b.l;
            } else if (z) {
                fArr2 = b.p;
            } else {
                fArr2 = b.o;
            }
            GLES20.glUniformMatrix3fv(bVar.f, 1, false, fArr2, 0);
            GLES20.glUniformMatrix4fv(bVar.e, 1, false, this.l, 0);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, i2);
            GLES20.glUniform1i(bVar.i, 0);
            GlUtil.checkGlError();
            GLES20.glVertexAttribPointer(bVar.g, 3, 5126, false, 12, aVar.b);
            GlUtil.checkGlError();
            GLES20.glVertexAttribPointer(bVar.h, 2, 5126, false, 8, aVar.c);
            GlUtil.checkGlError();
            GLES20.glDrawArrays(aVar.d, 0, aVar.a);
            GlUtil.checkGlError();
            GLES20.glDisableVertexAttribArray(bVar.g);
            GLES20.glDisableVertexAttribArray(bVar.h);
        }
    }

    public SurfaceTexture init() {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        GlUtil.checkGlError();
        b bVar = this.g;
        bVar.getClass();
        int compileProgram = GlUtil.compileProgram(b.j, b.k);
        bVar.d = compileProgram;
        bVar.e = GLES20.glGetUniformLocation(compileProgram, "uMvpMatrix");
        bVar.f = GLES20.glGetUniformLocation(bVar.d, "uTexMatrix");
        bVar.g = GLES20.glGetAttribLocation(bVar.d, "aPosition");
        bVar.h = GLES20.glGetAttribLocation(bVar.d, "aTexCoords");
        bVar.i = GLES20.glGetUniformLocation(bVar.d, "uTexture");
        GlUtil.checkGlError();
        this.m = GlUtil.createExternalTexture();
        SurfaceTexture surfaceTexture = new SurfaceTexture(this.m);
        this.n = surfaceTexture;
        surfaceTexture.setOnFrameAvailableListener(new ka(this));
        return this.n;
    }

    public void onCameraMotion(long j2, float[] fArr) {
        this.h.setRotation(j2, fArr);
    }

    public void onCameraMotionReset() {
        this.i.clear();
        this.h.reset();
        this.f.set(true);
    }

    public void onVideoFrameAboutToBeRendered(long j2, long j3, Format format, @Nullable MediaFormat mediaFormat) {
        Projection projection;
        this.i.add(j3, Long.valueOf(j2));
        byte[] bArr = format.z;
        int i2 = format.aa;
        byte[] bArr2 = this.q;
        int i3 = this.p;
        this.q = bArr;
        if (i2 == -1) {
            i2 = this.o;
        }
        this.p = i2;
        if (i3 != i2 || !Arrays.equals(bArr2, this.q)) {
            byte[] bArr3 = this.q;
            if (bArr3 != null) {
                projection = a.decode(bArr3, this.p);
            } else {
                projection = null;
            }
            if (projection == null || !b.isSupported(projection)) {
                projection = Projection.createEquirectangular(this.p);
            }
            this.j.add(j3, projection);
        }
    }

    public void setDefaultStereoMode(int i2) {
        this.o = i2;
    }

    public void shutdown() {
        int i2 = this.g.d;
        if (i2 != 0) {
            GLES20.glDeleteProgram(i2);
        }
    }
}
