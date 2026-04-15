package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaSourceList;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ClippingMediaPeriod;
import com.google.android.exoplayer2.source.EmptySampleStream;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;

/* renamed from: b6  reason: default package */
public final class b6 {
    public final MediaPeriod a;
    public final Object b;
    public final SampleStream[] c;
    public boolean d;
    public boolean e;
    public c6 f;
    public boolean g;
    public final boolean[] h;
    public final RendererCapabilities[] i;
    public final TrackSelector j;
    public final MediaSourceList k;
    @Nullable
    public b6 l;
    public TrackGroupArray m = TrackGroupArray.h;
    public TrackSelectorResult n;
    public long o;

    public b6(RendererCapabilities[] rendererCapabilitiesArr, long j2, TrackSelector trackSelector, Allocator allocator, MediaSourceList mediaSourceList, c6 c6Var, TrackSelectorResult trackSelectorResult) {
        this.i = rendererCapabilitiesArr;
        this.o = j2;
        this.j = trackSelector;
        this.k = mediaSourceList;
        MediaSource.MediaPeriodId mediaPeriodId = c6Var.a;
        this.b = mediaPeriodId.a;
        this.f = c6Var;
        this.n = trackSelectorResult;
        this.c = new SampleStream[rendererCapabilitiesArr.length];
        this.h = new boolean[rendererCapabilitiesArr.length];
        long j3 = c6Var.d;
        MediaPeriod createPeriod = mediaSourceList.createPeriod(mediaPeriodId, allocator, c6Var.b);
        if (!(j3 == -9223372036854775807L || j3 == Long.MIN_VALUE)) {
            createPeriod = new ClippingMediaPeriod(createPeriod, true, 0, j3);
        }
        this.a = createPeriod;
    }

    public final void a() {
        boolean z;
        int i2 = 0;
        if (this.l == null) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            while (true) {
                TrackSelectorResult trackSelectorResult = this.n;
                if (i2 < trackSelectorResult.a) {
                    boolean isRendererEnabled = trackSelectorResult.isRendererEnabled(i2);
                    ExoTrackSelection exoTrackSelection = this.n.c[i2];
                    if (isRendererEnabled && exoTrackSelection != null) {
                        exoTrackSelection.disable();
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    public long applyTrackSelection(TrackSelectorResult trackSelectorResult, long j2, boolean z) {
        return applyTrackSelection(trackSelectorResult, j2, z, new boolean[this.i.length]);
    }

    public final void b() {
        boolean z;
        int i2 = 0;
        if (this.l == null) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            while (true) {
                TrackSelectorResult trackSelectorResult = this.n;
                if (i2 < trackSelectorResult.a) {
                    boolean isRendererEnabled = trackSelectorResult.isRendererEnabled(i2);
                    ExoTrackSelection exoTrackSelection = this.n.c[i2];
                    if (isRendererEnabled && exoTrackSelection != null) {
                        exoTrackSelection.enable();
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    public void continueLoading(long j2) {
        boolean z;
        if (this.l == null) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        this.a.continueLoading(toPeriodTime(j2));
    }

    public long getBufferedPositionUs() {
        long j2;
        if (!this.d) {
            return this.f.b;
        }
        if (this.e) {
            j2 = this.a.getBufferedPositionUs();
        } else {
            j2 = Long.MIN_VALUE;
        }
        if (j2 == Long.MIN_VALUE) {
            return this.f.e;
        }
        return j2;
    }

    @Nullable
    public b6 getNext() {
        return this.l;
    }

    public long getNextLoadPositionUs() {
        if (!this.d) {
            return 0;
        }
        return this.a.getNextLoadPositionUs();
    }

    public long getRendererOffset() {
        return this.o;
    }

    public long getStartPositionRendererTime() {
        return this.f.b + this.o;
    }

    public TrackGroupArray getTrackGroups() {
        return this.m;
    }

    public TrackSelectorResult getTrackSelectorResult() {
        return this.n;
    }

    public void handlePrepared(float f2, Timeline timeline) throws ExoPlaybackException {
        this.d = true;
        this.m = this.a.getTrackGroups();
        TrackSelectorResult selectTracks = selectTracks(f2, timeline);
        c6 c6Var = this.f;
        long j2 = c6Var.b;
        long j3 = c6Var.e;
        if (j3 != -9223372036854775807L && j2 >= j3) {
            j2 = Math.max(0, j3 - 1);
        }
        long applyTrackSelection = applyTrackSelection(selectTracks, j2, false);
        long j4 = this.o;
        c6 c6Var2 = this.f;
        this.o = (c6Var2.b - applyTrackSelection) + j4;
        this.f = c6Var2.copyWithStartPositionUs(applyTrackSelection);
    }

    public boolean isFullyBuffered() {
        if (!this.d || (this.e && this.a.getBufferedPositionUs() != Long.MIN_VALUE)) {
            return false;
        }
        return true;
    }

    public void reevaluateBuffer(long j2) {
        boolean z;
        if (this.l == null) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        if (this.d) {
            this.a.reevaluateBuffer(toPeriodTime(j2));
        }
    }

    public void release() {
        a();
        long j2 = this.f.d;
        MediaSourceList mediaSourceList = this.k;
        MediaPeriod mediaPeriod = this.a;
        if (j2 == -9223372036854775807L || j2 == Long.MIN_VALUE) {
            mediaSourceList.releasePeriod(mediaPeriod);
            return;
        }
        try {
            mediaSourceList.releasePeriod(((ClippingMediaPeriod) mediaPeriod).c);
        } catch (RuntimeException e2) {
            Log.e("MediaPeriodHolder", "Period release failed.", e2);
        }
    }

    public TrackSelectorResult selectTracks(float f2, Timeline timeline) throws ExoPlaybackException {
        TrackSelectorResult selectTracks = this.j.selectTracks(this.i, getTrackGroups(), this.f.a, timeline);
        for (ExoTrackSelection exoTrackSelection : selectTracks.c) {
            if (exoTrackSelection != null) {
                exoTrackSelection.onPlaybackSpeed(f2);
            }
        }
        return selectTracks;
    }

    public void setNext(@Nullable b6 b6Var) {
        if (b6Var != this.l) {
            a();
            this.l = b6Var;
            b();
        }
    }

    public void setRendererOffset(long j2) {
        this.o = j2;
    }

    public long toPeriodTime(long j2) {
        return j2 - getRendererOffset();
    }

    public long toRendererTime(long j2) {
        return getRendererOffset() + j2;
    }

    public long applyTrackSelection(TrackSelectorResult trackSelectorResult, long j2, boolean z, boolean[] zArr) {
        RendererCapabilities[] rendererCapabilitiesArr;
        SampleStream[] sampleStreamArr;
        TrackSelectorResult trackSelectorResult2 = trackSelectorResult;
        int i2 = 0;
        while (true) {
            boolean z2 = true;
            if (i2 >= trackSelectorResult2.a) {
                break;
            }
            if (z || !trackSelectorResult2.isEquivalent(this.n, i2)) {
                z2 = false;
            }
            this.h[i2] = z2;
            i2++;
        }
        int i3 = 0;
        while (true) {
            rendererCapabilitiesArr = this.i;
            int length = rendererCapabilitiesArr.length;
            sampleStreamArr = this.c;
            if (i3 >= length) {
                break;
            }
            if (rendererCapabilitiesArr[i3].getTrackType() == 7) {
                sampleStreamArr[i3] = null;
            }
            i3++;
        }
        a();
        this.n = trackSelectorResult2;
        b();
        long selectTracks = this.a.selectTracks(trackSelectorResult2.c, this.h, this.c, zArr, j2);
        for (int i4 = 0; i4 < rendererCapabilitiesArr.length; i4++) {
            if (rendererCapabilitiesArr[i4].getTrackType() == 7 && this.n.isRendererEnabled(i4)) {
                sampleStreamArr[i4] = new EmptySampleStream();
            }
        }
        this.e = false;
        for (int i5 = 0; i5 < sampleStreamArr.length; i5++) {
            if (sampleStreamArr[i5] != null) {
                Assertions.checkState(trackSelectorResult2.isRendererEnabled(i5));
                if (rendererCapabilitiesArr[i5].getTrackType() != 7) {
                    this.e = true;
                }
            } else {
                Assertions.checkState(trackSelectorResult2.c[i5] == null);
            }
        }
        return selectTracks;
    }
}
