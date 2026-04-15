package com.google.android.exoplayer2.trackselection;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.util.Util;

public final class TrackSelectorResult {
    public final int a;
    public final RendererConfiguration[] b;
    public final ExoTrackSelection[] c;
    @Nullable
    public final Object d;

    public TrackSelectorResult(RendererConfiguration[] rendererConfigurationArr, ExoTrackSelection[] exoTrackSelectionArr, @Nullable Object obj) {
        this.b = rendererConfigurationArr;
        this.c = (ExoTrackSelection[]) exoTrackSelectionArr.clone();
        this.d = obj;
        this.a = rendererConfigurationArr.length;
    }

    public boolean isEquivalent(@Nullable TrackSelectorResult trackSelectorResult) {
        if (trackSelectorResult != null) {
            int length = trackSelectorResult.c.length;
            ExoTrackSelection[] exoTrackSelectionArr = this.c;
            if (length == exoTrackSelectionArr.length) {
                for (int i = 0; i < exoTrackSelectionArr.length; i++) {
                    if (!isEquivalent(trackSelectorResult, i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean isRendererEnabled(int i) {
        return this.b[i] != null;
    }

    public boolean isEquivalent(@Nullable TrackSelectorResult trackSelectorResult, int i) {
        if (trackSelectorResult != null && Util.areEqual(this.b[i], trackSelectorResult.b[i]) && Util.areEqual(this.c[i], trackSelectorResult.c[i])) {
            return true;
        }
        return false;
    }
}
