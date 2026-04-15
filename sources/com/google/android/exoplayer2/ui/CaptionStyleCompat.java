package com.google.android.exoplayer2.ui;

import android.graphics.Typeface;
import android.view.accessibility.CaptioningManager;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class CaptionStyleCompat {
    public static final CaptionStyleCompat g = new CaptionStyleCompat(-1, ViewCompat.MEASURED_STATE_MASK, 0, 0, -1, (Typeface) null);
    public final int a;
    public final int b;
    public final int c;
    public final int d;
    public final int e;
    @Nullable
    public final Typeface f;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface EdgeType {
    }

    public CaptionStyleCompat(int i, int i2, int i3, int i4, int i5, @Nullable Typeface typeface) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
        this.e = i5;
        this.f = typeface;
    }

    @RequiresApi(19)
    public static CaptionStyleCompat createFromCaptionStyle(CaptioningManager.CaptionStyle captionStyle) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        CaptioningManager.CaptionStyle captionStyle2 = captionStyle;
        if (Util.a < 21) {
            return new CaptionStyleCompat(captionStyle2.foregroundColor, captionStyle2.backgroundColor, 0, captionStyle2.edgeType, captionStyle2.edgeColor, captionStyle.getTypeface());
        }
        boolean hasForegroundColor = captionStyle.hasForegroundColor();
        CaptionStyleCompat captionStyleCompat = g;
        if (hasForegroundColor) {
            i = captionStyle2.foregroundColor;
        } else {
            i = captionStyleCompat.a;
        }
        int i6 = i;
        if (captionStyle.hasBackgroundColor()) {
            i2 = captionStyle2.backgroundColor;
        } else {
            i2 = captionStyleCompat.b;
        }
        int i7 = i2;
        if (captionStyle.hasWindowColor()) {
            i3 = captionStyle2.windowColor;
        } else {
            i3 = captionStyleCompat.c;
        }
        int i8 = i3;
        if (captionStyle.hasEdgeType()) {
            i4 = captionStyle2.edgeType;
        } else {
            i4 = captionStyleCompat.d;
        }
        int i9 = i4;
        if (captionStyle.hasEdgeColor()) {
            i5 = captionStyle2.edgeColor;
        } else {
            i5 = captionStyleCompat.e;
        }
        return new CaptionStyleCompat(i6, i7, i8, i9, i5, captionStyle.getTypeface());
    }
}
