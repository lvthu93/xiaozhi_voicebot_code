package com.google.android.exoplayer2.trackselection;

import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import java.util.List;
import java.util.Random;

public final class RandomTrackSelection extends BaseTrackSelection {
    public final Random h;
    public int i;

    public RandomTrackSelection(TrackGroup trackGroup, int[] iArr, int i2, Random random) {
        super(trackGroup, iArr, i2);
        this.h = random;
        this.i = random.nextInt(this.b);
    }

    public int getSelectedIndex() {
        return this.i;
    }

    @Nullable
    public Object getSelectionData() {
        return null;
    }

    public int getSelectionReason() {
        return 3;
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
        int i2;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            i2 = this.b;
            if (i3 >= i2) {
                break;
            }
            if (!isBlacklisted(i3, elapsedRealtime)) {
                i4++;
            }
            i3++;
        }
        this.i = this.h.nextInt(i4);
        if (i4 != i2) {
            int i5 = 0;
            for (int i6 = 0; i6 < i2; i6++) {
                if (!isBlacklisted(i6, elapsedRealtime)) {
                    int i7 = i5 + 1;
                    if (this.i == i5) {
                        this.i = i6;
                        return;
                    }
                    i5 = i7;
                }
            }
        }
    }

    public static final class Factory implements ExoTrackSelection.Factory {
        public final Random a;

        public Factory() {
            this.a = new Random();
        }

        public ExoTrackSelection[] createTrackSelections(ExoTrackSelection.Definition[] definitionArr, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) {
            return TrackSelectionUtil.createTrackSelectionsForDefinitions(definitionArr, new i2(10, this));
        }

        public Factory(int i) {
            this.a = new Random((long) i);
        }
    }
}
