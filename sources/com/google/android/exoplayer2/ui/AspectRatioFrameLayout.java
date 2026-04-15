package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class AspectRatioFrameLayout extends FrameLayout {
    public final a c;
    @Nullable
    public AspectRatioListener f;
    public float g;
    public int h;

    public interface AspectRatioListener {
        void onAspectRatioUpdated(float f, float f2, boolean z);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ResizeMode {
    }

    public final class a implements Runnable {
        public float c;
        public float f;
        public boolean g;
        public boolean h;

        public a() {
        }

        public void run() {
            this.h = false;
            AspectRatioListener aspectRatioListener = AspectRatioFrameLayout.this.f;
            if (aspectRatioListener != null) {
                aspectRatioListener.onAspectRatioUpdated(this.c, this.f, this.g);
            }
        }

        public void scheduleUpdate(float f2, float f3, boolean z) {
            this.c = f2;
            this.f = f3;
            this.g = z;
            if (!this.h) {
                this.h = true;
                AspectRatioFrameLayout.this.post(this);
            }
        }
    }

    public AspectRatioFrameLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public int getResizeMode() {
        return this.h;
    }

    public final void onMeasure(int i, int i2) {
        float f2;
        float f3;
        super.onMeasure(i, i2);
        if (this.g > 0.0f) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f4 = (float) measuredWidth;
            float f5 = (float) measuredHeight;
            float f6 = f4 / f5;
            float f7 = (this.g / f6) - 1.0f;
            float abs = Math.abs(f7);
            a aVar = this.c;
            if (abs <= 0.01f) {
                aVar.scheduleUpdate(this.g, f6, false);
                return;
            }
            int i3 = this.h;
            if (i3 != 0) {
                if (i3 == 1) {
                    f3 = this.g;
                } else if (i3 != 2) {
                    if (i3 == 4) {
                        if (f7 > 0.0f) {
                            f2 = this.g;
                        } else {
                            f3 = this.g;
                        }
                    }
                    aVar.scheduleUpdate(this.g, f6, true);
                    super.onMeasure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
                } else {
                    f2 = this.g;
                }
                measuredHeight = (int) (f4 / f3);
                aVar.scheduleUpdate(this.g, f6, true);
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
            } else if (f7 > 0.0f) {
                f3 = this.g;
                measuredHeight = (int) (f4 / f3);
                aVar.scheduleUpdate(this.g, f6, true);
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
            } else {
                f2 = this.g;
            }
            measuredWidth = (int) (f5 * f2);
            aVar.scheduleUpdate(this.g, f6, true);
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
        }
    }

    public void setAspectRatio(float f2) {
        if (this.g != f2) {
            this.g = f2;
            requestLayout();
        }
    }

    public void setAspectRatioListener(@Nullable AspectRatioListener aspectRatioListener) {
        this.f = aspectRatioListener;
    }

    public void setResizeMode(int i) {
        if (this.h != i) {
            this.h = i;
            requestLayout();
        }
    }

    public AspectRatioFrameLayout(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.h = 0;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.a, 0, 0);
            try {
                this.h = obtainStyledAttributes.getInt(0, 0);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        this.c = new a();
    }
}
