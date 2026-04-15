package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.List;

public final class SilenceMediaSource extends BaseMediaSource {
    public static final Format i;
    public static final MediaItem j;
    public static final byte[] k = new byte[(Util.getPcmFrameSize(2, 2) * 1024)];
    public final long g;
    public final MediaItem h;

    public static final class Factory {
        public long a;
        @Nullable
        public Object b;

        public SilenceMediaSource createMediaSource() {
            boolean z;
            if (this.a > 0) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkState(z);
            return new SilenceMediaSource(SilenceMediaSource.j.buildUpon().setTag(this.b).build(), this.a);
        }

        public Factory setDurationUs(long j) {
            this.a = j;
            return this;
        }

        public Factory setTag(@Nullable Object obj) {
            this.b = obj;
            return this;
        }
    }

    public static final class a implements MediaPeriod {
        public static final TrackGroupArray g = new TrackGroupArray(new TrackGroup(SilenceMediaSource.i));
        public final long c;
        public final ArrayList<SampleStream> f = new ArrayList<>();

        public a(long j) {
            this.c = j;
        }

        public boolean continueLoading(long j) {
            return false;
        }

        public void discardBuffer(long j, boolean z) {
        }

        public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
            return Util.constrainValue(j, 0, this.c);
        }

        public long getBufferedPositionUs() {
            return Long.MIN_VALUE;
        }

        public long getNextLoadPositionUs() {
            return Long.MIN_VALUE;
        }

        public /* bridge */ /* synthetic */ List getStreamKeys(List list) {
            return a6.a(this, list);
        }

        public TrackGroupArray getTrackGroups() {
            return g;
        }

        public boolean isLoading() {
            return false;
        }

        public void maybeThrowPrepareError() {
        }

        public void prepare(MediaPeriod.Callback callback, long j) {
            callback.onPrepared(this);
        }

        public long readDiscontinuity() {
            return -9223372036854775807L;
        }

        public void reevaluateBuffer(long j) {
        }

        public long seekToUs(long j) {
            long constrainValue = Util.constrainValue(j, 0, this.c);
            int i = 0;
            while (true) {
                ArrayList<SampleStream> arrayList = this.f;
                if (i >= arrayList.size()) {
                    return constrainValue;
                }
                ((b) arrayList.get(i)).seekTo(constrainValue);
                i++;
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.lang.Object[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.lang.Object[]} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long selectTracks(com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r7, boolean[] r8, com.google.android.exoplayer2.source.SampleStream[] r9, boolean[] r10, long r11) {
            /*
                r6 = this;
                r2 = 0
                long r4 = r6.c
                r0 = r11
                long r11 = com.google.android.exoplayer2.util.Util.constrainValue((long) r0, (long) r2, (long) r4)
                r0 = 0
            L_0x000a:
                int r1 = r7.length
                if (r0 >= r1) goto L_0x003e
                r1 = r9[r0]
                java.util.ArrayList<com.google.android.exoplayer2.source.SampleStream> r2 = r6.f
                if (r1 == 0) goto L_0x0021
                r3 = r7[r0]
                if (r3 == 0) goto L_0x001b
                boolean r3 = r8[r0]
                if (r3 != 0) goto L_0x0021
            L_0x001b:
                r2.remove(r1)
                r1 = 0
                r9[r0] = r1
            L_0x0021:
                r1 = r9[r0]
                if (r1 != 0) goto L_0x003b
                r1 = r7[r0]
                if (r1 == 0) goto L_0x003b
                com.google.android.exoplayer2.source.SilenceMediaSource$b r1 = new com.google.android.exoplayer2.source.SilenceMediaSource$b
                long r3 = r6.c
                r1.<init>(r3)
                r1.seekTo(r11)
                r2.add(r1)
                r9[r0] = r1
                r1 = 1
                r10[r0] = r1
            L_0x003b:
                int r0 = r0 + 1
                goto L_0x000a
            L_0x003e:
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SilenceMediaSource.a.selectTracks(com.google.android.exoplayer2.trackselection.ExoTrackSelection[], boolean[], com.google.android.exoplayer2.source.SampleStream[], boolean[], long):long");
        }
    }

    public static final class b implements SampleStream {
        public final long c;
        public boolean f;
        public long g;

        public b(long j) {
            Format format = SilenceMediaSource.i;
            this.c = ((long) Util.getPcmFrameSize(2, 2)) * ((j * 44100) / 1000000);
            seekTo(0);
        }

        public boolean isReady() {
            return true;
        }

        public void maybeThrowError() {
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            if (!this.f || (i & 2) != 0) {
                formatHolder.b = SilenceMediaSource.i;
                this.f = true;
                return -5;
            }
            long j = this.g;
            long j2 = this.c - j;
            if (j2 == 0) {
                decoderInputBuffer.addFlag(4);
                return -4;
            }
            Format format = SilenceMediaSource.i;
            decoderInputBuffer.i = ((j / ((long) Util.getPcmFrameSize(2, 2))) * 1000000) / 44100;
            decoderInputBuffer.addFlag(1);
            byte[] bArr = SilenceMediaSource.k;
            int min = (int) Math.min((long) bArr.length, j2);
            if ((i & 4) == 0) {
                decoderInputBuffer.ensureSpaceForWrite(min);
                decoderInputBuffer.g.put(bArr, 0, min);
            }
            if ((i & 1) == 0) {
                this.g += (long) min;
            }
            return -4;
        }

        public void seekTo(long j) {
            Format format = SilenceMediaSource.i;
            this.g = Util.constrainValue(((long) Util.getPcmFrameSize(2, 2)) * ((j * 44100) / 1000000), 0, this.c);
        }

        public int skipData(long j) {
            long j2 = this.g;
            seekTo(j);
            return (int) ((this.g - j2) / ((long) SilenceMediaSource.k.length));
        }
    }

    static {
        Format build = new Format.Builder().setSampleMimeType("audio/raw").setChannelCount(2).setSampleRate(44100).setPcmEncoding(2).build();
        i = build;
        j = new MediaItem.Builder().setMediaId("SilenceMediaSource").setUri(Uri.EMPTY).setMimeType(build.p).build();
    }

    public SilenceMediaSource(long j2) {
        this(j, j2);
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        return new a(this.g);
    }

    @Nullable
    public /* bridge */ /* synthetic */ Timeline getInitialTimeline() {
        return e6.a(this);
    }

    public MediaItem getMediaItem() {
        return this.h;
    }

    @Deprecated
    @Nullable
    public Object getTag() {
        return ((MediaItem.PlaybackProperties) Assertions.checkNotNull(this.h.f)).h;
    }

    public /* bridge */ /* synthetic */ boolean isSingleWindow() {
        return e6.c(this);
    }

    public void maybeThrowSourceInfoRefreshError() {
    }

    public final void prepareSourceInternal(@Nullable TransferListener transferListener) {
        c(new SinglePeriodTimeline(this.g, true, false, false, (Object) null, this.h));
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
    }

    public final void releaseSourceInternal() {
    }

    public SilenceMediaSource(MediaItem mediaItem, long j2) {
        Assertions.checkArgument(j2 >= 0);
        this.g = j2;
        this.h = mediaItem;
    }
}
