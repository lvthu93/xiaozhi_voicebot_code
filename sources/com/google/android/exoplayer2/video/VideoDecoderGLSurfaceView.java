package com.google.android.exoplayer2.video;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.GlUtil;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.concurrent.atomic.AtomicReference;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public final class VideoDecoderGLSurfaceView extends GLSurfaceView implements VideoDecoderOutputBufferRenderer {
    public final a c;

    public static final class a implements GLSurfaceView.Renderer {
        public static final float[] o = {1.164f, 1.164f, 1.164f, 0.0f, -0.392f, 2.017f, 1.596f, -0.813f, 0.0f};
        public static final float[] p = {1.164f, 1.164f, 1.164f, 0.0f, -0.213f, 2.112f, 1.793f, -0.533f, 0.0f};
        public static final float[] q = {1.168f, 1.168f, 1.168f, 0.0f, -0.188f, 2.148f, 1.683f, -0.652f, 0.0f};
        public static final String[] r = {"y_tex", "u_tex", "v_tex"};
        public static final FloatBuffer s = GlUtil.createBuffer(new float[]{-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f});
        public final GLSurfaceView c;
        public final int[] f = new int[3];
        public final int[] g = new int[3];
        public final int[] h = new int[3];
        public final int[] i = new int[3];
        public final AtomicReference<VideoDecoderOutputBuffer> j = new AtomicReference<>();
        public final FloatBuffer[] k = new FloatBuffer[3];
        public int l;
        public int m;
        public VideoDecoderOutputBuffer n;

        public a(GLSurfaceView gLSurfaceView) {
            this.c = gLSurfaceView;
            for (int i2 = 0; i2 < 3; i2++) {
                int[] iArr = this.h;
                this.i[i2] = -1;
                iArr[i2] = -1;
            }
        }

        public void onDrawFrame(GL10 gl10) {
            boolean z;
            int i2;
            VideoDecoderOutputBuffer andSet = this.j.getAndSet((Object) null);
            if (andSet != null || this.n != null) {
                if (andSet != null) {
                    VideoDecoderOutputBuffer videoDecoderOutputBuffer = this.n;
                    if (videoDecoderOutputBuffer != null) {
                        videoDecoderOutputBuffer.release();
                    }
                    this.n = andSet;
                }
                VideoDecoderOutputBuffer videoDecoderOutputBuffer2 = (VideoDecoderOutputBuffer) Assertions.checkNotNull(this.n);
                float[] fArr = p;
                int i3 = videoDecoderOutputBuffer2.m;
                if (i3 == 1) {
                    fArr = o;
                } else if (i3 == 3) {
                    fArr = q;
                }
                GLES20.glUniformMatrix3fv(this.m, 1, false, fArr, 0);
                int[] iArr = (int[]) Assertions.checkNotNull(videoDecoderOutputBuffer2.l);
                ByteBuffer[] byteBufferArr = (ByteBuffer[]) Assertions.checkNotNull(videoDecoderOutputBuffer2.k);
                for (int i4 = 0; i4 < 3; i4++) {
                    if (i4 == 0) {
                        i2 = videoDecoderOutputBuffer2.j;
                    } else {
                        i2 = (videoDecoderOutputBuffer2.j + 1) / 2;
                    }
                    GLES20.glActiveTexture(33984 + i4);
                    GLES20.glBindTexture(3553, this.f[i4]);
                    GLES20.glPixelStorei(3317, 1);
                    GLES20.glTexImage2D(3553, 0, 6409, iArr[i4], i2, 0, 6409, 5121, byteBufferArr[i4]);
                }
                int[] iArr2 = new int[3];
                int i5 = videoDecoderOutputBuffer2.i;
                iArr2[0] = i5;
                int i6 = (i5 + 1) / 2;
                iArr2[2] = i6;
                iArr2[1] = i6;
                for (int i7 = 0; i7 < 3; i7++) {
                    int[] iArr3 = this.h;
                    int i8 = iArr3[i7];
                    int i9 = iArr2[i7];
                    int[] iArr4 = this.i;
                    if (i8 != i9 || iArr4[i7] != iArr[i7]) {
                        if (iArr[i7] != 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        Assertions.checkState(z);
                        float f2 = ((float) iArr2[i7]) / ((float) iArr[i7]);
                        FloatBuffer createBuffer = GlUtil.createBuffer(new float[]{0.0f, 0.0f, 0.0f, 1.0f, f2, 0.0f, f2, 1.0f});
                        FloatBuffer[] floatBufferArr = this.k;
                        floatBufferArr[i7] = createBuffer;
                        GLES20.glVertexAttribPointer(this.g[i7], 2, 5126, false, 0, floatBufferArr[i7]);
                        iArr3[i7] = iArr2[i7];
                        iArr4[i7] = iArr[i7];
                    }
                }
                GLES20.glClear(16384);
                GLES20.glDrawArrays(5, 0, 4);
                GlUtil.checkGlError();
            }
        }

        public void onSurfaceChanged(GL10 gl10, int i2, int i3) {
            GLES20.glViewport(0, 0, i2, i3);
        }

        public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
            int compileProgram = GlUtil.compileProgram("varying vec2 interp_tc_y;\nvarying vec2 interp_tc_u;\nvarying vec2 interp_tc_v;\nattribute vec4 in_pos;\nattribute vec2 in_tc_y;\nattribute vec2 in_tc_u;\nattribute vec2 in_tc_v;\nvoid main() {\n  gl_Position = in_pos;\n  interp_tc_y = in_tc_y;\n  interp_tc_u = in_tc_u;\n  interp_tc_v = in_tc_v;\n}\n", "precision mediump float;\nvarying vec2 interp_tc_y;\nvarying vec2 interp_tc_u;\nvarying vec2 interp_tc_v;\nuniform sampler2D y_tex;\nuniform sampler2D u_tex;\nuniform sampler2D v_tex;\nuniform mat3 mColorConversion;\nvoid main() {\n  vec3 yuv;\n  yuv.x = texture2D(y_tex, interp_tc_y).r - 0.0625;\n  yuv.y = texture2D(u_tex, interp_tc_u).r - 0.5;\n  yuv.z = texture2D(v_tex, interp_tc_v).r - 0.5;\n  gl_FragColor = vec4(mColorConversion * yuv, 1.0);\n}\n");
            this.l = compileProgram;
            GLES20.glUseProgram(compileProgram);
            int glGetAttribLocation = GLES20.glGetAttribLocation(this.l, "in_pos");
            GLES20.glEnableVertexAttribArray(glGetAttribLocation);
            GLES20.glVertexAttribPointer(glGetAttribLocation, 2, 5126, false, 0, s);
            int glGetAttribLocation2 = GLES20.glGetAttribLocation(this.l, "in_tc_y");
            int[] iArr = this.g;
            iArr[0] = glGetAttribLocation2;
            GLES20.glEnableVertexAttribArray(iArr[0]);
            iArr[1] = GLES20.glGetAttribLocation(this.l, "in_tc_u");
            GLES20.glEnableVertexAttribArray(iArr[1]);
            iArr[2] = GLES20.glGetAttribLocation(this.l, "in_tc_v");
            GLES20.glEnableVertexAttribArray(iArr[2]);
            GlUtil.checkGlError();
            this.m = GLES20.glGetUniformLocation(this.l, "mColorConversion");
            GlUtil.checkGlError();
            int[] iArr2 = this.f;
            GLES20.glGenTextures(3, iArr2, 0);
            for (int i2 = 0; i2 < 3; i2++) {
                GLES20.glUniform1i(GLES20.glGetUniformLocation(this.l, r[i2]), i2);
                GLES20.glActiveTexture(33984 + i2);
                GLES20.glBindTexture(3553, iArr2[i2]);
                GLES20.glTexParameterf(3553, 10241, 9729.0f);
                GLES20.glTexParameterf(3553, 10240, 9729.0f);
                GLES20.glTexParameterf(3553, 10242, 33071.0f);
                GLES20.glTexParameterf(3553, 10243, 33071.0f);
            }
            GlUtil.checkGlError();
            GlUtil.checkGlError();
        }

        public void setOutputBuffer(VideoDecoderOutputBuffer videoDecoderOutputBuffer) {
            VideoDecoderOutputBuffer andSet = this.j.getAndSet(videoDecoderOutputBuffer);
            if (andSet != null) {
                andSet.release();
            }
            this.c.requestRender();
        }
    }

    public VideoDecoderGLSurfaceView(Context context) {
        this(context, (AttributeSet) null);
    }

    @Deprecated
    public VideoDecoderOutputBufferRenderer getVideoDecoderOutputBufferRenderer() {
        return this;
    }

    public void setOutputBuffer(VideoDecoderOutputBuffer videoDecoderOutputBuffer) {
        this.c.setOutputBuffer(videoDecoderOutputBuffer);
    }

    public VideoDecoderGLSurfaceView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        a aVar = new a(this);
        this.c = aVar;
        setPreserveEGLContextOnPause(true);
        setEGLContextClientVersion(2);
        setRenderer(aVar);
        setRenderMode(0);
    }
}
