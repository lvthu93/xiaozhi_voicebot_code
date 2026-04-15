package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;

public final class AuxEffectInfo {
    public final int a;
    public final float b;

    public AuxEffectInfo(int i, float f) {
        this.a = i;
        this.b = f;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AuxEffectInfo.class != obj.getClass()) {
            return false;
        }
        AuxEffectInfo auxEffectInfo = (AuxEffectInfo) obj;
        if (this.a == auxEffectInfo.a && Float.compare(auxEffectInfo.b, this.b) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.b) + ((527 + this.a) * 31);
    }
}
