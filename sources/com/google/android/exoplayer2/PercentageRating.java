package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;

public final class PercentageRating extends Rating {
    public static final f2 g = new f2(6);
    public final float f;

    public PercentageRating() {
        this.f = -1.0f;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof PercentageRating)) {
            return false;
        }
        if (this.f == ((PercentageRating) obj).f) {
            return true;
        }
        return false;
    }

    public float getPercent() {
        return this.f;
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.f));
    }

    public boolean isRated() {
        return this.f != -1.0f;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.toString(0, 36), 1);
        bundle.putFloat(Integer.toString(1, 36), this.f);
        return bundle;
    }

    public PercentageRating(@FloatRange(from = 0.0d, to = 100.0d) float f2) {
        Assertions.checkArgument(f2 >= 0.0f && f2 <= 100.0f, "percent must be in the range of [0, 100]");
        this.f = f2;
    }
}
