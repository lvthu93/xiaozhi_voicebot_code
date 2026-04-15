package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.ui.SubtitleView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class a extends View implements SubtitleView.a {
    public final ArrayList c;
    public List<Cue> f;
    public int g;
    public float h;
    public CaptionStyleCompat i;
    public float j;

    public a(Context context) {
        this(context, (AttributeSet) null);
    }

    public void dispatchDraw(Canvas canvas) {
        List<Cue> list = this.f;
        if (!list.isEmpty()) {
            int height = getHeight();
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int width = getWidth() - getPaddingRight();
            int paddingBottom = height - getPaddingBottom();
            if (paddingBottom > paddingTop && width > paddingLeft) {
                int i2 = paddingBottom - paddingTop;
                float resolveTextSize = ic.resolveTextSize(this.g, this.h, height, i2);
                if (resolveTextSize > 0.0f) {
                    int size = list.size();
                    int i3 = 0;
                    int i4 = 0;
                    while (i4 < size) {
                        Cue cue = list.get(i4);
                        if (cue.p != Integer.MIN_VALUE) {
                            Cue.Builder textAlignment = cue.buildUpon().setPosition(-3.4028235E38f).setPositionAnchor(Integer.MIN_VALUE).setTextAlignment((Layout.Alignment) null);
                            int i5 = cue.f;
                            float f2 = cue.e;
                            if (i5 == 0) {
                                textAlignment.setLine(1.0f - f2, i3);
                            } else {
                                textAlignment.setLine((-f2) - 1.0f, 1);
                            }
                            int i6 = cue.g;
                            if (i6 == 0) {
                                textAlignment.setLineAnchor(2);
                            } else if (i6 == 2) {
                                textAlignment.setLineAnchor(i3);
                            }
                            cue = textAlignment.build();
                        }
                        Cue cue2 = cue;
                        float resolveTextSize2 = ic.resolveTextSize(cue2.n, cue2.o, height, i2);
                        int i7 = paddingBottom;
                        ((hc) this.c.get(i4)).draw(cue2, this.i, resolveTextSize, resolveTextSize2, this.j, canvas, paddingLeft, paddingTop, width, i7);
                        i4++;
                        size = size;
                        i2 = i2;
                        paddingBottom = i7;
                        width = width;
                        i3 = 0;
                    }
                }
            }
        }
    }

    public void update(List<Cue> list, CaptionStyleCompat captionStyleCompat, float f2, int i2, float f3) {
        this.f = list;
        this.i = captionStyleCompat;
        this.h = f2;
        this.g = i2;
        this.j = f3;
        while (true) {
            ArrayList arrayList = this.c;
            if (arrayList.size() < list.size()) {
                arrayList.add(new hc(getContext()));
            } else {
                invalidate();
                return;
            }
        }
    }

    public a(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = new ArrayList();
        this.f = Collections.emptyList();
        this.g = 0;
        this.h = 0.0533f;
        this.i = CaptionStyleCompat.g;
        this.j = 0.08f;
    }
}
