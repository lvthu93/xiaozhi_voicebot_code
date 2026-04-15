package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.drm.DrmSession;

public final class FormatHolder {
    @Nullable
    public DrmSession a;
    @Nullable
    public Format b;

    public void clear() {
        this.a = null;
        this.b = null;
    }
}
