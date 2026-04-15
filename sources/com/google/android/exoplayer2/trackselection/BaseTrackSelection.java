package com.google.android.exoplayer2.trackselection;

import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.List;

public abstract class BaseTrackSelection implements ExoTrackSelection {
    public final TrackGroup a;
    public final int b;
    public final int[] c;
    public final int d;
    public final Format[] e;
    public final long[] f;
    public int g;

    public BaseTrackSelection(TrackGroup trackGroup, int... iArr) {
        this(trackGroup, iArr, 0);
    }

    public boolean blacklist(int i, long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        boolean isBlacklisted = isBlacklisted(i, elapsedRealtime);
        for (int i2 = 0; i2 < this.b && !isBlacklisted; i2++) {
            if (i2 == i || isBlacklisted(i2, elapsedRealtime)) {
                isBlacklisted = false;
            } else {
                isBlacklisted = true;
            }
        }
        if (!isBlacklisted) {
            return false;
        }
        long[] jArr = this.f;
        jArr[i] = Math.max(jArr[i], Util.addWithOverflowDefault(elapsedRealtime, j, Long.MAX_VALUE));
        return true;
    }

    public void disable() {
    }

    public void enable() {
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BaseTrackSelection baseTrackSelection = (BaseTrackSelection) obj;
        if (this.a != baseTrackSelection.a || !Arrays.equals(this.c, baseTrackSelection.c)) {
            return false;
        }
        return true;
    }

    public int evaluateQueueSize(long j, List<? extends MediaChunk> list) {
        return list.size();
    }

    public final Format getFormat(int i) {
        return this.e[i];
    }

    public final int getIndexInTrackGroup(int i) {
        return this.c[i];
    }

    public final Format getSelectedFormat() {
        return this.e[getSelectedIndex()];
    }

    public abstract /* synthetic */ int getSelectedIndex();

    public final int getSelectedIndexInTrackGroup() {
        return this.c[getSelectedIndex()];
    }

    @Nullable
    public abstract /* synthetic */ Object getSelectionData();

    public abstract /* synthetic */ int getSelectionReason();

    public final TrackGroup getTrackGroup() {
        return this.a;
    }

    public final int getType() {
        return this.d;
    }

    public int hashCode() {
        if (this.g == 0) {
            this.g = Arrays.hashCode(this.c) + (System.identityHashCode(this.a) * 31);
        }
        return this.g;
    }

    public final int indexOf(Format format) {
        for (int i = 0; i < this.b; i++) {
            if (this.e[i] == format) {
                return i;
            }
        }
        return -1;
    }

    public boolean isBlacklisted(int i, long j) {
        return this.f[i] > j;
    }

    public final int length() {
        return this.c.length;
    }

    public /* bridge */ /* synthetic */ void onDiscontinuity() {
        s2.a(this);
    }

    public /* bridge */ /* synthetic */ void onPlayWhenReadyChanged(boolean z) {
        s2.b(this, z);
    }

    public void onPlaybackSpeed(float f2) {
    }

    public /* bridge */ /* synthetic */ void onRebuffer() {
        s2.c(this);
    }

    public /* bridge */ /* synthetic */ boolean shouldCancelChunkLoad(long j, Chunk chunk, List list) {
        return s2.d(this, j, chunk, list);
    }

    public abstract /* synthetic */ void updateSelectedTrack(long j, long j2, long j3, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr);

    public BaseTrackSelection(TrackGroup trackGroup, int[] iArr, int i) {
        int i2 = 0;
        Assertions.checkState(iArr.length > 0);
        this.d = i;
        this.a = (TrackGroup) Assertions.checkNotNull(trackGroup);
        int length = iArr.length;
        this.b = length;
        this.e = new Format[length];
        for (int i3 = 0; i3 < iArr.length; i3++) {
            this.e[i3] = trackGroup.getFormat(iArr[i3]);
        }
        Arrays.sort(this.e, new db(7));
        this.c = new int[this.b];
        while (true) {
            int i4 = this.b;
            if (i2 < i4) {
                this.c[i2] = trackGroup.indexOf(this.e[i2]);
                i2++;
            } else {
                this.f = new long[i4];
                return;
            }
        }
    }

    public final int indexOf(int i) {
        for (int i2 = 0; i2 < this.b; i2++) {
            if (this.c[i2] == i) {
                return i2;
            }
        }
        return -1;
    }
}
