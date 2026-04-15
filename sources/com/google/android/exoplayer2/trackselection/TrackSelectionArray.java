package com.google.android.exoplayer2.trackselection;

import androidx.annotation.Nullable;
import java.util.Arrays;

public final class TrackSelectionArray {
    public final int a;
    public final TrackSelection[] b;
    public int c;

    public TrackSelectionArray(TrackSelection... trackSelectionArr) {
        this.b = trackSelectionArr;
        this.a = trackSelectionArr.length;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || TrackSelectionArray.class != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.b, ((TrackSelectionArray) obj).b);
    }

    @Nullable
    public TrackSelection get(int i) {
        return this.b[i];
    }

    public TrackSelection[] getAll() {
        return (TrackSelection[]) this.b.clone();
    }

    public int hashCode() {
        if (this.c == 0) {
            this.c = 527 + Arrays.hashCode(this.b);
        }
        return this.c;
    }
}
