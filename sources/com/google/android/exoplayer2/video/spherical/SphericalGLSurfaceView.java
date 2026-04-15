package com.google.android.exoplayer2.video.spherical;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.SurfaceTexture;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.WindowManager;
import androidx.annotation.BinderThread;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.spherical.OrientationListener;
import com.google.android.exoplayer2.video.spherical.TouchTracker;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public final class SphericalGLSurfaceView extends GLSurfaceView {
    public final CopyOnWriteArrayList<VideoSurfaceListener> c;
    public final SensorManager f;
    @Nullable
    public final Sensor g;
    public final OrientationListener h;
    public final Handler i;
    public final la j;
    @Nullable
    public SurfaceTexture k;
    @Nullable
    public Surface l;
    public boolean m;
    public boolean n;
    public boolean o;

    public interface VideoSurfaceListener {
        void onVideoSurfaceCreated(Surface surface);

        void onVideoSurfaceDestroyed(Surface surface);
    }

    @VisibleForTesting
    public final class a implements GLSurfaceView.Renderer, TouchTracker.Listener, OrientationListener.Listener {
        public final la c;
        public final float[] f = new float[16];
        public final float[] g = new float[16];
        public final float[] h;
        public final float[] i;
        public final float[] j;
        public float k;
        public float l;
        public final float[] m;
        public final float[] n;

        public a(la laVar) {
            float[] fArr = new float[16];
            this.h = fArr;
            float[] fArr2 = new float[16];
            this.i = fArr2;
            float[] fArr3 = new float[16];
            this.j = fArr3;
            this.m = new float[16];
            this.n = new float[16];
            this.c = laVar;
            Matrix.setIdentityM(fArr, 0);
            Matrix.setIdentityM(fArr2, 0);
            Matrix.setIdentityM(fArr3, 0);
            this.l = 3.1415927f;
        }

        public void onDrawFrame(GL10 gl10) {
            synchronized (this) {
                Matrix.multiplyMM(this.n, 0, this.h, 0, this.j, 0);
                Matrix.multiplyMM(this.m, 0, this.i, 0, this.n, 0);
            }
            Matrix.multiplyMM(this.g, 0, this.f, 0, this.m, 0);
            this.c.drawFrame(this.g, false);
        }

        @BinderThread
        public synchronized void onOrientationChange(float[] fArr, float f2) {
            float[] fArr2 = this.h;
            System.arraycopy(fArr, 0, fArr2, 0, fArr2.length);
            float f3 = -f2;
            this.l = f3;
            Matrix.setRotateM(this.i, 0, -this.k, (float) Math.cos((double) f3), (float) Math.sin((double) this.l), 0.0f);
        }

        @UiThread
        public synchronized void onScrollChange(PointF pointF) {
            float f2 = pointF.y;
            this.k = f2;
            Matrix.setRotateM(this.i, 0, -f2, (float) Math.cos((double) this.l), (float) Math.sin((double) this.l), 0.0f);
            Matrix.setRotateM(this.j, 0, -pointF.x, 0.0f, 1.0f, 0.0f);
        }

        @UiThread
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return SphericalGLSurfaceView.this.performClick();
        }

        public void onSurfaceChanged(GL10 gl10, int i2, int i3) {
            float f2;
            boolean z = false;
            GLES20.glViewport(0, 0, i2, i3);
            float f3 = ((float) i2) / ((float) i3);
            if (f3 > 1.0f) {
                z = true;
            }
            if (z) {
                f2 = (float) (Math.toDegrees(Math.atan(Math.tan(Math.toRadians(45.0d)) / ((double) f3))) * 2.0d);
            } else {
                f2 = 90.0f;
            }
            Matrix.perspectiveM(this.f, 0, f2, f3, 0.1f, 100.0f);
        }

        public synchronized void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
            SphericalGLSurfaceView sphericalGLSurfaceView = SphericalGLSurfaceView.this;
            sphericalGLSurfaceView.i.post(new h2(15, sphericalGLSurfaceView, this.c.init()));
        }
    }

    public SphericalGLSurfaceView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void a() {
        boolean z;
        if (!this.m || !this.n) {
            z = false;
        } else {
            z = true;
        }
        Sensor sensor = this.g;
        if (sensor != null && z != this.o) {
            OrientationListener orientationListener = this.h;
            SensorManager sensorManager = this.f;
            if (z) {
                sensorManager.registerListener(orientationListener, sensor, 0);
            } else {
                sensorManager.unregisterListener(orientationListener);
            }
            this.o = z;
        }
    }

    public void addVideoSurfaceListener(VideoSurfaceListener videoSurfaceListener) {
        this.c.add(videoSurfaceListener);
    }

    public CameraMotionListener getCameraMotionListener() {
        return this.j;
    }

    public VideoFrameMetadataListener getVideoFrameMetadataListener() {
        return this.j;
    }

    @Nullable
    public Surface getVideoSurface() {
        return this.l;
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.i.post(new qb(8, this));
    }

    public void onPause() {
        this.n = false;
        a();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.n = true;
        a();
    }

    public void removeVideoSurfaceListener(VideoSurfaceListener videoSurfaceListener) {
        this.c.remove(videoSurfaceListener);
    }

    public void setDefaultStereoMode(int i2) {
        this.j.setDefaultStereoMode(i2);
    }

    public void setUseSensorRotation(boolean z) {
        this.m = z;
        a();
    }

    public SphericalGLSurfaceView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = new CopyOnWriteArrayList<>();
        this.i = new Handler(Looper.getMainLooper());
        SensorManager sensorManager = (SensorManager) Assertions.checkNotNull(context.getSystemService("sensor"));
        this.f = sensorManager;
        Sensor defaultSensor = Util.a >= 18 ? sensorManager.getDefaultSensor(15) : null;
        this.g = defaultSensor == null ? sensorManager.getDefaultSensor(11) : defaultSensor;
        la laVar = new la();
        this.j = laVar;
        a aVar = new a(laVar);
        TouchTracker touchTracker = new TouchTracker(context, aVar, 25.0f);
        this.h = new OrientationListener(((WindowManager) Assertions.checkNotNull((WindowManager) context.getSystemService("window"))).getDefaultDisplay(), touchTracker, aVar);
        this.m = true;
        setEGLContextClientVersion(2);
        setRenderer(aVar);
        setOnTouchListener(touchTracker);
    }
}
