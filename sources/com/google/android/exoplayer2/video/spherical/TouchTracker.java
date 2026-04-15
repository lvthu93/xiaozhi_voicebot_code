package com.google.android.exoplayer2.video.spherical;

import android.content.Context;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.BinderThread;
import com.google.android.exoplayer2.video.spherical.OrientationListener;

public final class TouchTracker extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener, OrientationListener.Listener {
    public final PointF c = new PointF();
    public final PointF f = new PointF();
    public final Listener g;
    public final float h;
    public final GestureDetector i;
    public volatile float j;

    public interface Listener {
        void onScrollChange(PointF pointF);

        boolean onSingleTapUp(MotionEvent motionEvent);
    }

    public TouchTracker(Context context, Listener listener, float f2) {
        this.g = listener;
        this.h = f2;
        this.i = new GestureDetector(context, this);
        this.j = 3.1415927f;
    }

    public boolean onDown(MotionEvent motionEvent) {
        this.c.set(motionEvent.getX(), motionEvent.getY());
        return true;
    }

    @BinderThread
    public void onOrientationChange(float[] fArr, float f2) {
        this.j = -f2;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        float x = (motionEvent2.getX() - this.c.x) / this.h;
        float y = motionEvent2.getY();
        PointF pointF = this.c;
        float f4 = (y - pointF.y) / this.h;
        pointF.set(motionEvent2.getX(), motionEvent2.getY());
        double d = (double) this.j;
        float cos = (float) Math.cos(d);
        float sin = (float) Math.sin(d);
        PointF pointF2 = this.f;
        pointF2.x -= (cos * x) - (sin * f4);
        float f5 = (cos * f4) + (sin * x) + pointF2.y;
        pointF2.y = f5;
        pointF2.y = Math.max(-45.0f, Math.min(45.0f, f5));
        this.g.onScrollChange(this.f);
        return true;
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return this.g.onSingleTapUp(motionEvent);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.i.onTouchEvent(motionEvent);
    }
}
