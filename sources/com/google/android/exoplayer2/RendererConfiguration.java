package com.google.android.exoplayer2;

import androidx.annotation.Nullable;

public final class RendererConfiguration {
    public static final RendererConfiguration b = new RendererConfiguration(false);
    public final boolean a;

    public RendererConfiguration(boolean z) {
        this.a = z;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && RendererConfiguration.class == obj.getClass() && this.a == ((RendererConfiguration) obj).a) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.a ^ true ? 1 : 0;
    }
}
