package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;

public final class ClippingMediaPeriod implements MediaPeriod, MediaPeriod.Callback {
    public final MediaPeriod c;
    @Nullable
    public MediaPeriod.Callback f;
    public a[] g = new a[0];
    public long h;
    public long i;
    public long j;

    public final class a implements SampleStream {
        public final SampleStream c;
        public boolean f;

        public a(SampleStream sampleStream) {
            this.c = sampleStream;
        }

        public void clearSentEos() {
            this.f = false;
        }

        public boolean isReady() {
            return !ClippingMediaPeriod.this.a() && this.c.isReady();
        }

        public void maybeThrowError() throws IOException {
            this.c.maybeThrowError();
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            ClippingMediaPeriod clippingMediaPeriod = ClippingMediaPeriod.this;
            if (clippingMediaPeriod.a()) {
                return -3;
            }
            if (this.f) {
                decoderInputBuffer.setFlags(4);
                return -4;
            }
            int readData = this.c.readData(formatHolder, decoderInputBuffer, i);
            if (readData == -5) {
                Format format = (Format) Assertions.checkNotNull(formatHolder.b);
                int i2 = format.af;
                int i3 = format.ag;
                if (!(i2 == 0 && i3 == 0)) {
                    if (clippingMediaPeriod.i != 0) {
                        i2 = 0;
                    }
                    if (clippingMediaPeriod.j != Long.MIN_VALUE) {
                        i3 = 0;
                    }
                    formatHolder.b = format.buildUpon().setEncoderDelay(i2).setEncoderPadding(i3).build();
                }
                return -5;
            }
            long j = clippingMediaPeriod.j;
            if (j == Long.MIN_VALUE || ((readData != -4 || decoderInputBuffer.i < j) && (readData != -3 || clippingMediaPeriod.getBufferedPositionUs() != Long.MIN_VALUE || decoderInputBuffer.h))) {
                return readData;
            }
            decoderInputBuffer.clear();
            decoderInputBuffer.setFlags(4);
            this.f = true;
            return -4;
        }

        public int skipData(long j) {
            if (ClippingMediaPeriod.this.a()) {
                return -3;
            }
            return this.c.skipData(j);
        }
    }

    public ClippingMediaPeriod(MediaPeriod mediaPeriod, boolean z, long j2, long j3) {
        long j4;
        this.c = mediaPeriod;
        if (z) {
            j4 = j2;
        } else {
            j4 = -9223372036854775807L;
        }
        this.h = j4;
        this.i = j2;
        this.j = j3;
    }

    public final boolean a() {
        return this.h != -9223372036854775807L;
    }

    public boolean continueLoading(long j2) {
        return this.c.continueLoading(j2);
    }

    public void discardBuffer(long j2, boolean z) {
        this.c.discardBuffer(j2, z);
    }

    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        long j3;
        long j4 = this.i;
        if (j2 == j4) {
            return j4;
        }
        long constrainValue = Util.constrainValue(seekParameters.a, 0, j2 - j4);
        long j5 = seekParameters.b;
        long j6 = this.j;
        if (j6 == Long.MIN_VALUE) {
            j3 = Long.MAX_VALUE;
        } else {
            j3 = j6 - j2;
        }
        long constrainValue2 = Util.constrainValue(j5, 0, j3);
        if (!(constrainValue == seekParameters.a && constrainValue2 == seekParameters.b)) {
            seekParameters = new SeekParameters(constrainValue, constrainValue2);
        }
        return this.c.getAdjustedSeekPositionUs(j2, seekParameters);
    }

    public long getBufferedPositionUs() {
        long bufferedPositionUs = this.c.getBufferedPositionUs();
        if (bufferedPositionUs != Long.MIN_VALUE) {
            long j2 = this.j;
            if (j2 == Long.MIN_VALUE || bufferedPositionUs < j2) {
                return bufferedPositionUs;
            }
        }
        return Long.MIN_VALUE;
    }

    public long getNextLoadPositionUs() {
        long nextLoadPositionUs = this.c.getNextLoadPositionUs();
        if (nextLoadPositionUs != Long.MIN_VALUE) {
            long j2 = this.j;
            if (j2 == Long.MIN_VALUE || nextLoadPositionUs < j2) {
                return nextLoadPositionUs;
            }
        }
        return Long.MIN_VALUE;
    }

    public /* bridge */ /* synthetic */ List getStreamKeys(List list) {
        return a6.a(this, list);
    }

    public TrackGroupArray getTrackGroups() {
        return this.c.getTrackGroups();
    }

    public boolean isLoading() {
        return this.c.isLoading();
    }

    public void maybeThrowPrepareError() throws IOException {
        this.c.maybeThrowPrepareError();
    }

    public void onPrepared(MediaPeriod mediaPeriod) {
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.f)).onPrepared(this);
    }

    public void prepare(MediaPeriod.Callback callback, long j2) {
        this.f = callback;
        this.c.prepare(this, j2);
    }

    public long readDiscontinuity() {
        boolean z;
        if (a()) {
            long j2 = this.h;
            this.h = -9223372036854775807L;
            long readDiscontinuity = readDiscontinuity();
            if (readDiscontinuity != -9223372036854775807L) {
                return readDiscontinuity;
            }
            return j2;
        }
        long readDiscontinuity2 = this.c.readDiscontinuity();
        if (readDiscontinuity2 == -9223372036854775807L) {
            return -9223372036854775807L;
        }
        boolean z2 = true;
        if (readDiscontinuity2 >= this.i) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        long j3 = this.j;
        if (j3 != Long.MIN_VALUE && readDiscontinuity2 > j3) {
            z2 = false;
        }
        Assertions.checkState(z2);
        return readDiscontinuity2;
    }

    public void reevaluateBuffer(long j2) {
        this.c.reevaluateBuffer(j2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0032, code lost:
        if (r0 > r7) goto L_0x0035;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long seekToUs(long r7) {
        /*
            r6 = this;
            r0 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r6.h = r0
            com.google.android.exoplayer2.source.ClippingMediaPeriod$a[] r0 = r6.g
            int r1 = r0.length
            r2 = 0
            r3 = 0
        L_0x000c:
            if (r3 >= r1) goto L_0x0018
            r4 = r0[r3]
            if (r4 == 0) goto L_0x0015
            r4.clearSentEos()
        L_0x0015:
            int r3 = r3 + 1
            goto L_0x000c
        L_0x0018:
            com.google.android.exoplayer2.source.MediaPeriod r0 = r6.c
            long r0 = r0.seekToUs(r7)
            int r3 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r3 == 0) goto L_0x0034
            long r7 = r6.i
            int r3 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r3 < 0) goto L_0x0035
            long r7 = r6.j
            r3 = -9223372036854775808
            int r5 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r5 == 0) goto L_0x0034
            int r3 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r3 > 0) goto L_0x0035
        L_0x0034:
            r2 = 1
        L_0x0035:
            com.google.android.exoplayer2.util.Assertions.checkState(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ClippingMediaPeriod.seekToUs(long):long");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0081, code lost:
        if (r1 > r5) goto L_0x0084;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x008b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long selectTracks(com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r16, boolean[] r17, com.google.android.exoplayer2.source.SampleStream[] r18, boolean[] r19, long r20) {
        /*
            r15 = this;
            r0 = r15
            r8 = r16
            r9 = r18
            int r1 = r9.length
            com.google.android.exoplayer2.source.ClippingMediaPeriod$a[] r1 = new com.google.android.exoplayer2.source.ClippingMediaPeriod.a[r1]
            r0.g = r1
            int r1 = r9.length
            com.google.android.exoplayer2.source.SampleStream[] r10 = new com.google.android.exoplayer2.source.SampleStream[r1]
            r11 = 0
            r1 = 0
        L_0x000f:
            int r2 = r9.length
            r12 = 0
            if (r1 >= r2) goto L_0x0024
            com.google.android.exoplayer2.source.ClippingMediaPeriod$a[] r2 = r0.g
            r3 = r9[r1]
            com.google.android.exoplayer2.source.ClippingMediaPeriod$a r3 = (com.google.android.exoplayer2.source.ClippingMediaPeriod.a) r3
            r2[r1] = r3
            if (r3 == 0) goto L_0x001f
            com.google.android.exoplayer2.source.SampleStream r12 = r3.c
        L_0x001f:
            r10[r1] = r12
            int r1 = r1 + 1
            goto L_0x000f
        L_0x0024:
            com.google.android.exoplayer2.source.MediaPeriod r1 = r0.c
            r2 = r16
            r3 = r17
            r4 = r10
            r5 = r19
            r6 = r20
            long r1 = r1.selectTracks(r2, r3, r4, r5, r6)
            boolean r3 = r15.a()
            r4 = 1
            if (r3 == 0) goto L_0x0066
            long r5 = r0.i
            int r3 = (r20 > r5 ? 1 : (r20 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x0066
            r13 = 0
            int r3 = (r5 > r13 ? 1 : (r5 == r13 ? 0 : -1))
            if (r3 == 0) goto L_0x0061
            int r3 = r8.length
            r5 = 0
        L_0x0048:
            if (r5 >= r3) goto L_0x0061
            r6 = r8[r5]
            if (r6 == 0) goto L_0x005e
            com.google.android.exoplayer2.Format r6 = r6.getSelectedFormat()
            java.lang.String r7 = r6.p
            java.lang.String r6 = r6.m
            boolean r6 = com.google.android.exoplayer2.util.MimeTypes.allSamplesAreSyncSamples(r7, r6)
            if (r6 != 0) goto L_0x005e
            r3 = 1
            goto L_0x0062
        L_0x005e:
            int r5 = r5 + 1
            goto L_0x0048
        L_0x0061:
            r3 = 0
        L_0x0062:
            if (r3 == 0) goto L_0x0066
            r5 = r1
            goto L_0x006b
        L_0x0066:
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L_0x006b:
            r0.h = r5
            int r3 = (r1 > r20 ? 1 : (r1 == r20 ? 0 : -1))
            if (r3 == 0) goto L_0x0085
            long r5 = r0.i
            int r3 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r3 < 0) goto L_0x0084
            long r5 = r0.j
            r7 = -9223372036854775808
            int r3 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r3 == 0) goto L_0x0085
            int r3 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r3 > 0) goto L_0x0084
            goto L_0x0085
        L_0x0084:
            r4 = 0
        L_0x0085:
            com.google.android.exoplayer2.util.Assertions.checkState(r4)
        L_0x0088:
            int r3 = r9.length
            if (r11 >= r3) goto L_0x00ae
            r3 = r10[r11]
            if (r3 != 0) goto L_0x0094
            com.google.android.exoplayer2.source.ClippingMediaPeriod$a[] r3 = r0.g
            r3[r11] = r12
            goto L_0x00a5
        L_0x0094:
            com.google.android.exoplayer2.source.ClippingMediaPeriod$a[] r4 = r0.g
            r5 = r4[r11]
            if (r5 == 0) goto L_0x009e
            com.google.android.exoplayer2.source.SampleStream r5 = r5.c
            if (r5 == r3) goto L_0x00a5
        L_0x009e:
            com.google.android.exoplayer2.source.ClippingMediaPeriod$a r5 = new com.google.android.exoplayer2.source.ClippingMediaPeriod$a
            r5.<init>(r3)
            r4[r11] = r5
        L_0x00a5:
            com.google.android.exoplayer2.source.ClippingMediaPeriod$a[] r3 = r0.g
            r3 = r3[r11]
            r9[r11] = r3
            int r11 = r11 + 1
            goto L_0x0088
        L_0x00ae:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ClippingMediaPeriod.selectTracks(com.google.android.exoplayer2.trackselection.ExoTrackSelection[], boolean[], com.google.android.exoplayer2.source.SampleStream[], boolean[], long):long");
    }

    public void updateClipping(long j2, long j3) {
        this.i = j2;
        this.j = j3;
    }

    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.f)).onContinueLoadingRequested(this);
    }
}
