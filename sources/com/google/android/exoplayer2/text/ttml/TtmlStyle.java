package com.google.android.exoplayer2.text.ttml;

import android.text.Layout;
import androidx.annotation.Nullable;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class TtmlStyle {
    @Nullable
    public String a;
    public int b;
    public boolean c;
    public int d;
    public boolean e;
    public int f = -1;
    public int g = -1;
    public int h = -1;
    public int i = -1;
    public int j = -1;
    public float k;
    @Nullable
    public String l;
    public int m = -1;
    public int n = -1;
    @Nullable
    public Layout.Alignment o;
    @Nullable
    public Layout.Alignment p;
    public int q = -1;
    @Nullable
    public TextEmphasis r;
    public float s = Float.MAX_VALUE;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface FontSizeUnit {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface RubyType {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface StyleFlags {
    }

    public final void a(@Nullable TtmlStyle ttmlStyle, boolean z) {
        int i2;
        Layout.Alignment alignment;
        Layout.Alignment alignment2;
        String str;
        if (ttmlStyle != null) {
            if (!this.c && ttmlStyle.c) {
                setFontColor(ttmlStyle.b);
            }
            if (this.h == -1) {
                this.h = ttmlStyle.h;
            }
            if (this.i == -1) {
                this.i = ttmlStyle.i;
            }
            if (this.a == null && (str = ttmlStyle.a) != null) {
                this.a = str;
            }
            if (this.f == -1) {
                this.f = ttmlStyle.f;
            }
            if (this.g == -1) {
                this.g = ttmlStyle.g;
            }
            if (this.n == -1) {
                this.n = ttmlStyle.n;
            }
            if (this.o == null && (alignment2 = ttmlStyle.o) != null) {
                this.o = alignment2;
            }
            if (this.p == null && (alignment = ttmlStyle.p) != null) {
                this.p = alignment;
            }
            if (this.q == -1) {
                this.q = ttmlStyle.q;
            }
            if (this.j == -1) {
                this.j = ttmlStyle.j;
                this.k = ttmlStyle.k;
            }
            if (this.r == null) {
                this.r = ttmlStyle.r;
            }
            if (this.s == Float.MAX_VALUE) {
                this.s = ttmlStyle.s;
            }
            if (z && !this.e && ttmlStyle.e) {
                setBackgroundColor(ttmlStyle.d);
            }
            if (z && this.m == -1 && (i2 = ttmlStyle.m) != -1) {
                this.m = i2;
            }
        }
    }

    public TtmlStyle chain(@Nullable TtmlStyle ttmlStyle) {
        a(ttmlStyle, true);
        return this;
    }

    public int getBackgroundColor() {
        if (this.e) {
            return this.d;
        }
        throw new IllegalStateException("Background color has not been defined.");
    }

    public int getFontColor() {
        if (this.c) {
            return this.b;
        }
        throw new IllegalStateException("Font color has not been defined.");
    }

    @Nullable
    public String getFontFamily() {
        return this.a;
    }

    public float getFontSize() {
        return this.k;
    }

    public int getFontSizeUnit() {
        return this.j;
    }

    @Nullable
    public String getId() {
        return this.l;
    }

    @Nullable
    public Layout.Alignment getMultiRowAlign() {
        return this.p;
    }

    public int getRubyPosition() {
        return this.n;
    }

    public int getRubyType() {
        return this.m;
    }

    public float getShearPercentage() {
        return this.s;
    }

    public int getStyle() {
        int i2;
        int i3 = this.h;
        if (i3 == -1 && this.i == -1) {
            return -1;
        }
        int i4 = 0;
        if (i3 == 1) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        if (this.i == 1) {
            i4 = 2;
        }
        return i2 | i4;
    }

    @Nullable
    public Layout.Alignment getTextAlign() {
        return this.o;
    }

    public boolean getTextCombine() {
        return this.q == 1;
    }

    @Nullable
    public TextEmphasis getTextEmphasis() {
        return this.r;
    }

    public boolean hasBackgroundColor() {
        return this.e;
    }

    public boolean hasFontColor() {
        return this.c;
    }

    public TtmlStyle inherit(@Nullable TtmlStyle ttmlStyle) {
        a(ttmlStyle, false);
        return this;
    }

    public boolean isLinethrough() {
        return this.f == 1;
    }

    public boolean isUnderline() {
        return this.g == 1;
    }

    public TtmlStyle setBackgroundColor(int i2) {
        this.d = i2;
        this.e = true;
        return this;
    }

    public TtmlStyle setBold(boolean z) {
        this.h = z ? 1 : 0;
        return this;
    }

    public TtmlStyle setFontColor(int i2) {
        this.b = i2;
        this.c = true;
        return this;
    }

    public TtmlStyle setFontFamily(@Nullable String str) {
        this.a = str;
        return this;
    }

    public TtmlStyle setFontSize(float f2) {
        this.k = f2;
        return this;
    }

    public TtmlStyle setFontSizeUnit(int i2) {
        this.j = i2;
        return this;
    }

    public TtmlStyle setId(@Nullable String str) {
        this.l = str;
        return this;
    }

    public TtmlStyle setItalic(boolean z) {
        this.i = z ? 1 : 0;
        return this;
    }

    public TtmlStyle setLinethrough(boolean z) {
        this.f = z ? 1 : 0;
        return this;
    }

    public TtmlStyle setMultiRowAlign(@Nullable Layout.Alignment alignment) {
        this.p = alignment;
        return this;
    }

    public TtmlStyle setRubyPosition(int i2) {
        this.n = i2;
        return this;
    }

    public TtmlStyle setRubyType(int i2) {
        this.m = i2;
        return this;
    }

    public TtmlStyle setShearPercentage(float f2) {
        this.s = f2;
        return this;
    }

    public TtmlStyle setTextAlign(@Nullable Layout.Alignment alignment) {
        this.o = alignment;
        return this;
    }

    public TtmlStyle setTextCombine(boolean z) {
        this.q = z ? 1 : 0;
        return this;
    }

    public TtmlStyle setTextEmphasis(@Nullable TextEmphasis textEmphasis) {
        this.r = textEmphasis;
        return this;
    }

    public TtmlStyle setUnderline(boolean z) {
        this.g = z ? 1 : 0;
        return this;
    }
}
