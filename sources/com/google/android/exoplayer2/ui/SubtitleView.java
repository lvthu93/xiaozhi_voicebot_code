package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.FrameLayout;
import androidx.annotation.Dimension;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SubtitleView extends FrameLayout implements TextOutput {
    public List<Cue> c;
    public CaptionStyleCompat f;
    public int g;
    public float h;
    public float i;
    public boolean j;
    public boolean k;
    public int l;
    public a m;
    public View n;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    public interface a {
        void update(List<Cue> list, CaptionStyleCompat captionStyleCompat, float f, int i, float f2);
    }

    public SubtitleView(Context context) {
        this(context, (AttributeSet) null);
    }

    private List<Cue> getCuesWithStylingPreferencesApplied() {
        if (this.j && this.k) {
            return this.c;
        }
        ArrayList arrayList = new ArrayList(this.c.size());
        for (int i2 = 0; i2 < this.c.size(); i2++) {
            Cue.Builder buildUpon = this.c.get(i2).buildUpon();
            if (!this.j) {
                ic.removeAllEmbeddedStyling(buildUpon);
            } else if (!this.k) {
                ic.removeEmbeddedFontSizes(buildUpon);
            }
            arrayList.add(buildUpon.build());
        }
        return arrayList;
    }

    private float getUserCaptionFontScale() {
        CaptioningManager captioningManager;
        if (Util.a < 19 || isInEditMode() || (captioningManager = (CaptioningManager) getContext().getSystemService("captioning")) == null || !captioningManager.isEnabled()) {
            return 1.0f;
        }
        return captioningManager.getFontScale();
    }

    private CaptionStyleCompat getUserCaptionStyle() {
        if (Util.a < 19 || isInEditMode()) {
            return CaptionStyleCompat.g;
        }
        CaptioningManager captioningManager = (CaptioningManager) getContext().getSystemService("captioning");
        if (captioningManager == null || !captioningManager.isEnabled()) {
            return CaptionStyleCompat.g;
        }
        return CaptionStyleCompat.createFromCaptionStyle(captioningManager.getUserStyle());
    }

    private <T extends View & a> void setView(T t) {
        removeView(this.n);
        View view = this.n;
        if (view instanceof c) {
            ((c) view).destroy();
        }
        this.n = t;
        this.m = (a) t;
        addView(t);
    }

    public final void a() {
        this.m.update(getCuesWithStylingPreferencesApplied(), this.f, this.h, this.g, this.i);
    }

    public void onCues(List<Cue> list) {
        setCues(list);
    }

    public void setApplyEmbeddedFontSizes(boolean z) {
        this.k = z;
        a();
    }

    public void setApplyEmbeddedStyles(boolean z) {
        this.j = z;
        a();
    }

    public void setBottomPaddingFraction(float f2) {
        this.i = f2;
        a();
    }

    public void setCues(@Nullable List<Cue> list) {
        if (list == null) {
            list = Collections.emptyList();
        }
        this.c = list;
        a();
    }

    public void setFixedTextSize(@Dimension int i2, float f2) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        float applyDimension = TypedValue.applyDimension(i2, f2, resources.getDisplayMetrics());
        this.g = 2;
        this.h = applyDimension;
        a();
    }

    public void setFractionalTextSize(float f2) {
        setFractionalTextSize(f2, false);
    }

    public void setStyle(CaptionStyleCompat captionStyleCompat) {
        this.f = captionStyleCompat;
        a();
    }

    public void setUserDefaultStyle() {
        setStyle(getUserCaptionStyle());
    }

    public void setUserDefaultTextSize() {
        setFractionalTextSize(getUserCaptionFontScale() * 0.0533f);
    }

    public void setViewType(int i2) {
        if (this.l != i2) {
            if (i2 == 1) {
                setView(new a(getContext()));
            } else if (i2 == 2) {
                setView(new c(getContext()));
            } else {
                throw new IllegalArgumentException();
            }
            this.l = i2;
        }
    }

    public SubtitleView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = Collections.emptyList();
        this.f = CaptionStyleCompat.g;
        this.g = 0;
        this.h = 0.0533f;
        this.i = 0.08f;
        this.j = true;
        this.k = true;
        a aVar = new a(context);
        this.m = aVar;
        this.n = aVar;
        addView(aVar);
        this.l = 1;
    }

    public void setFractionalTextSize(float f2, boolean z) {
        this.g = z ? 1 : 0;
        this.h = f2;
        a();
    }
}
