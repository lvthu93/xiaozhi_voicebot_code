package com.google.android.exoplayer2.trackselection;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import java.util.List;

public final class FixedTrackSelection extends BaseTrackSelection {
    public final int h;
    @Nullable
    public final Object i;

    public FixedTrackSelection(TrackGroup trackGroup, int i2) {
        this(trackGroup, i2, 0);
    }

    public int getSelectedIndex() {
        return 0;
    }

    @Nullable
    public Object getSelectionData() {
        return this.i;
    }

    public int getSelectionReason() {
        return this.h;
    }

    public /* bridge */ /* synthetic */ void onDiscontinuity() {
        s2.a(this);
    }

    public /* bridge */ /* synthetic */ void onPlayWhenReadyChanged(boolean z) {
        s2.b(this, z);
    }

    public /* bridge */ /* synthetic */ void onRebuffer() {
        s2.c(this);
    }

    public /* bridge */ /* synthetic */ boolean shouldCancelChunkLoad(long j, Chunk chunk, List list) {
        return s2.d(this, j, chunk, list);
    }

    public void updateSelectedTrack(long j, long j2, long j3, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr) {
    }

    public FixedTrackSelection(TrackGroup trackGroup, int i2, int i3) {
        this(trackGroup, i2, i3, 0, (Object) null);
    }

    public FixedTrackSelection(TrackGroup trackGroup, int i2, int i3, int i4, @Nullable Object obj) {
        super(trackGroup, new int[]{i2}, i3);
        this.h = i4;
        this.i = obj;
    }
}
