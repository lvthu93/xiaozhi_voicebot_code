package com.google.android.exoplayer2.audio;

import android.media.AudioAttributes;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Util;

public final class AudioAttributes implements Bundleable {
    public static final AudioAttributes j = new Builder().build();
    public final int c;
    public final int f;
    public final int g;
    public final int h;
    @Nullable
    public android.media.AudioAttributes i;

    public static final class Builder {
        public int a = 0;
        public int b = 0;
        public int c = 1;
        public int d = 1;

        public AudioAttributes build() {
            return new AudioAttributes(this.a, this.b, this.c, this.d);
        }

        public Builder setAllowedCapturePolicy(int i) {
            this.d = i;
            return this;
        }

        public Builder setContentType(int i) {
            this.a = i;
            return this;
        }

        public Builder setFlags(int i) {
            this.b = i;
            return this;
        }

        public Builder setUsage(int i) {
            this.c = i;
            return this;
        }
    }

    public AudioAttributes(int i2, int i3, int i4, int i5) {
        this.c = i2;
        this.f = i3;
        this.g = i4;
        this.h = i5;
    }

    public static String a(int i2) {
        return Integer.toString(i2, 36);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AudioAttributes.class != obj.getClass()) {
            return false;
        }
        AudioAttributes audioAttributes = (AudioAttributes) obj;
        if (this.c == audioAttributes.c && this.f == audioAttributes.f && this.g == audioAttributes.g && this.h == audioAttributes.h) {
            return true;
        }
        return false;
    }

    @RequiresApi(21)
    public android.media.AudioAttributes getAudioAttributesV21() {
        if (this.i == null) {
            AudioAttributes.Builder usage = new AudioAttributes.Builder().setContentType(this.c).setFlags(this.f).setUsage(this.g);
            if (Util.a >= 29) {
                usage.setAllowedCapturePolicy(this.h);
            }
            this.i = usage.build();
        }
        return this.i;
    }

    public int hashCode() {
        return ((((((527 + this.c) * 31) + this.f) * 31) + this.g) * 31) + this.h;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(a(0), this.c);
        bundle.putInt(a(1), this.f);
        bundle.putInt(a(2), this.g);
        bundle.putInt(a(3), this.h);
        return bundle;
    }
}
