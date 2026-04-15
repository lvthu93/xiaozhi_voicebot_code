package com.google.android.exoplayer2.text;

import android.graphics.Bitmap;
import android.text.Layout;
import android.text.Spanned;
import android.text.SpannedString;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Cue {
    public static final Cue r = new Builder().setText("").build();
    @Nullable
    public final CharSequence a;
    @Nullable
    public final Layout.Alignment b;
    @Nullable
    public final Layout.Alignment c;
    @Nullable
    public final Bitmap d;
    public final float e;
    public final int f;
    public final int g;
    public final float h;
    public final int i;
    public final float j;
    public final float k;
    public final boolean l;
    public final int m;
    public final int n;
    public final float o;
    public final int p;
    public final float q;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnchorType {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface LineType {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface TextSizeType {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface VerticalType {
    }

    @Deprecated
    public Cue(CharSequence charSequence) {
        this(charSequence, (Layout.Alignment) null, -3.4028235E38f, Integer.MIN_VALUE, Integer.MIN_VALUE, -3.4028235E38f, Integer.MIN_VALUE, -3.4028235E38f);
    }

    public Builder buildUpon() {
        return new Builder(this);
    }

    @Deprecated
    public Cue(CharSequence charSequence, @Nullable Layout.Alignment alignment, float f2, int i2, int i3, float f3, int i4, float f4) {
        this(charSequence, alignment, f2, i2, i3, f3, i4, f4, false, (int) ViewCompat.MEASURED_STATE_MASK);
    }

    @Deprecated
    public Cue(CharSequence charSequence, @Nullable Layout.Alignment alignment, float f2, int i2, int i3, float f3, int i4, float f4, int i5, float f5) {
        this(charSequence, alignment, (Layout.Alignment) null, (Bitmap) null, f2, i2, i3, f3, i4, i5, f5, f4, -3.4028235E38f, false, ViewCompat.MEASURED_STATE_MASK, Integer.MIN_VALUE, 0.0f);
    }

    @Deprecated
    public Cue(CharSequence charSequence, @Nullable Layout.Alignment alignment, float f2, int i2, int i3, float f3, int i4, float f4, boolean z, int i5) {
        this(charSequence, alignment, (Layout.Alignment) null, (Bitmap) null, f2, i2, i3, f3, i4, Integer.MIN_VALUE, -3.4028235E38f, f4, -3.4028235E38f, z, i5, Integer.MIN_VALUE, 0.0f);
    }

    public Cue(@Nullable CharSequence charSequence, @Nullable Layout.Alignment alignment, @Nullable Layout.Alignment alignment2, @Nullable Bitmap bitmap, float f2, int i2, int i3, float f3, int i4, int i5, float f4, float f5, float f6, boolean z, int i6, int i7, float f7) {
        CharSequence charSequence2 = charSequence;
        Bitmap bitmap2 = bitmap;
        if (charSequence2 == null) {
            Assertions.checkNotNull(bitmap);
        } else {
            Assertions.checkArgument(bitmap2 == null);
        }
        if (charSequence2 instanceof Spanned) {
            this.a = SpannedString.valueOf(charSequence);
        } else if (charSequence2 != null) {
            this.a = charSequence.toString();
        } else {
            this.a = null;
        }
        this.b = alignment;
        this.c = alignment2;
        this.d = bitmap2;
        this.e = f2;
        this.f = i2;
        this.g = i3;
        this.h = f3;
        this.i = i4;
        this.j = f5;
        this.k = f6;
        this.l = z;
        this.m = i6;
        this.n = i5;
        this.o = f4;
        this.p = i7;
        this.q = f7;
    }

    public static final class Builder {
        @Nullable
        public CharSequence a;
        @Nullable
        public Bitmap b;
        @Nullable
        public Layout.Alignment c;
        @Nullable
        public Layout.Alignment d;
        public float e;
        public int f;
        public int g;
        public float h;
        public int i;
        public int j;
        public float k;
        public float l;
        public float m;
        public boolean n;
        @ColorInt
        public int o;
        public int p;
        public float q;

        public Builder() {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = -3.4028235E38f;
            this.f = Integer.MIN_VALUE;
            this.g = Integer.MIN_VALUE;
            this.h = -3.4028235E38f;
            this.i = Integer.MIN_VALUE;
            this.j = Integer.MIN_VALUE;
            this.k = -3.4028235E38f;
            this.l = -3.4028235E38f;
            this.m = -3.4028235E38f;
            this.n = false;
            this.o = ViewCompat.MEASURED_STATE_MASK;
            this.p = Integer.MIN_VALUE;
        }

        public Cue build() {
            CharSequence charSequence = this.a;
            Layout.Alignment alignment = this.c;
            Layout.Alignment alignment2 = this.d;
            Bitmap bitmap = this.b;
            float f2 = this.e;
            int i2 = this.f;
            int i3 = this.g;
            float f3 = this.h;
            int i4 = this.i;
            int i5 = this.j;
            float f4 = this.k;
            float f5 = this.l;
            float f6 = this.m;
            boolean z = this.n;
            return new Cue(charSequence, alignment, alignment2, bitmap, f2, i2, i3, f3, i4, i5, f4, f5, f6, z, this.o, this.p, this.q);
        }

        public Builder clearWindowColor() {
            this.n = false;
            return this;
        }

        @Nullable
        public Bitmap getBitmap() {
            return this.b;
        }

        public float getBitmapHeight() {
            return this.m;
        }

        public float getLine() {
            return this.e;
        }

        public int getLineAnchor() {
            return this.g;
        }

        public int getLineType() {
            return this.f;
        }

        public float getPosition() {
            return this.h;
        }

        public int getPositionAnchor() {
            return this.i;
        }

        public float getSize() {
            return this.l;
        }

        @Nullable
        public CharSequence getText() {
            return this.a;
        }

        @Nullable
        public Layout.Alignment getTextAlignment() {
            return this.c;
        }

        public float getTextSize() {
            return this.k;
        }

        public int getTextSizeType() {
            return this.j;
        }

        public int getVerticalType() {
            return this.p;
        }

        @ColorInt
        public int getWindowColor() {
            return this.o;
        }

        public boolean isWindowColorSet() {
            return this.n;
        }

        public Builder setBitmap(Bitmap bitmap) {
            this.b = bitmap;
            return this;
        }

        public Builder setBitmapHeight(float f2) {
            this.m = f2;
            return this;
        }

        public Builder setLine(float f2, int i2) {
            this.e = f2;
            this.f = i2;
            return this;
        }

        public Builder setLineAnchor(int i2) {
            this.g = i2;
            return this;
        }

        public Builder setMultiRowAlignment(@Nullable Layout.Alignment alignment) {
            this.d = alignment;
            return this;
        }

        public Builder setPosition(float f2) {
            this.h = f2;
            return this;
        }

        public Builder setPositionAnchor(int i2) {
            this.i = i2;
            return this;
        }

        public Builder setShearDegrees(float f2) {
            this.q = f2;
            return this;
        }

        public Builder setSize(float f2) {
            this.l = f2;
            return this;
        }

        public Builder setText(CharSequence charSequence) {
            this.a = charSequence;
            return this;
        }

        public Builder setTextAlignment(@Nullable Layout.Alignment alignment) {
            this.c = alignment;
            return this;
        }

        public Builder setTextSize(float f2, int i2) {
            this.k = f2;
            this.j = i2;
            return this;
        }

        public Builder setVerticalType(int i2) {
            this.p = i2;
            return this;
        }

        public Builder setWindowColor(@ColorInt int i2) {
            this.o = i2;
            this.n = true;
            return this;
        }

        public Builder(Cue cue) {
            this.a = cue.a;
            this.b = cue.d;
            this.c = cue.b;
            this.d = cue.c;
            this.e = cue.e;
            this.f = cue.f;
            this.g = cue.g;
            this.h = cue.h;
            this.i = cue.i;
            this.j = cue.n;
            this.k = cue.o;
            this.l = cue.j;
            this.m = cue.k;
            this.n = cue.l;
            this.o = cue.m;
            this.p = cue.p;
            this.q = cue.q;
        }
    }
}
