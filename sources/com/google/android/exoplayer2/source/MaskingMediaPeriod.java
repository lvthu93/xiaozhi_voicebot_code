package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;

public final class MaskingMediaPeriod implements MediaPeriod, MediaPeriod.Callback {
    public final MediaSource.MediaPeriodId c;
    public final long f;
    public final Allocator g;
    public MediaSource h;
    public MediaPeriod i;
    @Nullable
    public MediaPeriod.Callback j;
    @Nullable
    public PrepareListener k;
    public boolean l;
    public long m = -9223372036854775807L;

    public interface PrepareListener {
        void onPrepareComplete(MediaSource.MediaPeriodId mediaPeriodId);

        void onPrepareError(MediaSource.MediaPeriodId mediaPeriodId, IOException iOException);
    }

    public MaskingMediaPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        this.c = mediaPeriodId;
        this.g = allocator;
        this.f = j2;
    }

    public boolean continueLoading(long j2) {
        MediaPeriod mediaPeriod = this.i;
        return mediaPeriod != null && mediaPeriod.continueLoading(j2);
    }

    public void createPeriod(MediaSource.MediaPeriodId mediaPeriodId) {
        long j2 = this.m;
        if (j2 == -9223372036854775807L) {
            j2 = this.f;
        }
        MediaPeriod createPeriod = ((MediaSource) Assertions.checkNotNull(this.h)).createPeriod(mediaPeriodId, this.g, j2);
        this.i = createPeriod;
        if (this.j != null) {
            createPeriod.prepare(this, j2);
        }
    }

    public void discardBuffer(long j2, boolean z) {
        ((MediaPeriod) Util.castNonNull(this.i)).discardBuffer(j2, z);
    }

    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        return ((MediaPeriod) Util.castNonNull(this.i)).getAdjustedSeekPositionUs(j2, seekParameters);
    }

    public long getBufferedPositionUs() {
        return ((MediaPeriod) Util.castNonNull(this.i)).getBufferedPositionUs();
    }

    public long getNextLoadPositionUs() {
        return ((MediaPeriod) Util.castNonNull(this.i)).getNextLoadPositionUs();
    }

    public long getPreparePositionOverrideUs() {
        return this.m;
    }

    public long getPreparePositionUs() {
        return this.f;
    }

    public /* bridge */ /* synthetic */ List getStreamKeys(List list) {
        return a6.a(this, list);
    }

    public TrackGroupArray getTrackGroups() {
        return ((MediaPeriod) Util.castNonNull(this.i)).getTrackGroups();
    }

    public boolean isLoading() {
        MediaPeriod mediaPeriod = this.i;
        return mediaPeriod != null && mediaPeriod.isLoading();
    }

    public void maybeThrowPrepareError() throws IOException {
        try {
            MediaPeriod mediaPeriod = this.i;
            if (mediaPeriod != null) {
                mediaPeriod.maybeThrowPrepareError();
                return;
            }
            MediaSource mediaSource = this.h;
            if (mediaSource != null) {
                mediaSource.maybeThrowSourceInfoRefreshError();
            }
        } catch (IOException e) {
            PrepareListener prepareListener = this.k;
            if (prepareListener == null) {
                throw e;
            } else if (!this.l) {
                this.l = true;
                prepareListener.onPrepareError(this.c, e);
            }
        }
    }

    public void onPrepared(MediaPeriod mediaPeriod) {
        ((MediaPeriod.Callback) Util.castNonNull(this.j)).onPrepared(this);
        PrepareListener prepareListener = this.k;
        if (prepareListener != null) {
            prepareListener.onPrepareComplete(this.c);
        }
    }

    public void overridePreparePositionUs(long j2) {
        this.m = j2;
    }

    public void prepare(MediaPeriod.Callback callback, long j2) {
        this.j = callback;
        MediaPeriod mediaPeriod = this.i;
        if (mediaPeriod != null) {
            long j3 = this.m;
            if (j3 == -9223372036854775807L) {
                j3 = this.f;
            }
            mediaPeriod.prepare(this, j3);
        }
    }

    public long readDiscontinuity() {
        return ((MediaPeriod) Util.castNonNull(this.i)).readDiscontinuity();
    }

    public void reevaluateBuffer(long j2) {
        ((MediaPeriod) Util.castNonNull(this.i)).reevaluateBuffer(j2);
    }

    public void releasePeriod() {
        if (this.i != null) {
            ((MediaSource) Assertions.checkNotNull(this.h)).releasePeriod(this.i);
        }
    }

    public long seekToUs(long j2) {
        return ((MediaPeriod) Util.castNonNull(this.i)).seekToUs(j2);
    }

    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
        long j3;
        long j4 = this.m;
        if (j4 == -9223372036854775807L || j2 != this.f) {
            j3 = j2;
        } else {
            this.m = -9223372036854775807L;
            j3 = j4;
        }
        return ((MediaPeriod) Util.castNonNull(this.i)).selectTracks(exoTrackSelectionArr, zArr, sampleStreamArr, zArr2, j3);
    }

    public void setMediaSource(MediaSource mediaSource) {
        boolean z;
        if (this.h == null) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        this.h = mediaSource;
    }

    public void setPrepareListener(PrepareListener prepareListener) {
        this.k = prepareListener;
    }

    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        ((MediaPeriod.Callback) Util.castNonNull(this.j)).onContinueLoadingRequested(this);
    }
}
