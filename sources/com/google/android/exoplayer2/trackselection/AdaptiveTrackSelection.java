package com.google.android.exoplayer2.trackselection;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdaptiveTrackSelection extends BaseTrackSelection {
    public final BandwidthMeter h;
    public final long i;
    public final long j;
    public final long k;
    public final float l;
    public final float m;
    public final ImmutableList<AdaptationCheckpoint> n;
    public final Clock o;
    public float p;
    public int q;
    public int r;
    public long s;
    @Nullable
    public MediaChunk t;

    public static final class AdaptationCheckpoint {
        public final long a;
        public final long b;

        public AdaptationCheckpoint(long j, long j2) {
            this.a = j;
            this.b = j2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AdaptationCheckpoint)) {
                return false;
            }
            AdaptationCheckpoint adaptationCheckpoint = (AdaptationCheckpoint) obj;
            if (this.a == adaptationCheckpoint.a && this.b == adaptationCheckpoint.b) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((int) this.a) * 31) + ((int) this.b);
        }
    }

    public static class Factory implements ExoTrackSelection.Factory {
        public final int a;
        public final int b;
        public final int c;
        public final float d;
        public final float e;
        public final Clock f;

        public Factory() {
            this(10000, 25000, 25000, 0.7f, 0.75f, Clock.a);
        }

        public final ExoTrackSelection[] createTrackSelections(ExoTrackSelection.Definition[] definitionArr, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) {
            int i;
            ImmutableList immutableList;
            ExoTrackSelection exoTrackSelection;
            ImmutableList immutableList2;
            ListMultimap<K, V> listMultimap;
            double d2;
            long j;
            ExoTrackSelection.Definition[] definitionArr2 = definitionArr;
            ArrayList arrayList = new ArrayList();
            char c2 = 0;
            int i2 = 0;
            while (true) {
                i = 1;
                if (i2 >= definitionArr2.length) {
                    break;
                }
                ExoTrackSelection.Definition definition = definitionArr2[i2];
                if (definition == null || definition.b.length <= 1) {
                    arrayList.add((Object) null);
                } else {
                    ImmutableList.Builder builder = ImmutableList.builder();
                    builder.add((Object) new AdaptationCheckpoint(0, 0));
                    arrayList.add(builder);
                }
                i2++;
            }
            int length = definitionArr2.length;
            long[][] jArr = new long[length][];
            for (int i3 = 0; i3 < definitionArr2.length; i3++) {
                ExoTrackSelection.Definition definition2 = definitionArr2[i3];
                if (definition2 == null) {
                    jArr[i3] = new long[0];
                } else {
                    int[] iArr = definition2.b;
                    jArr[i3] = new long[iArr.length];
                    for (int i4 = 0; i4 < iArr.length; i4++) {
                        jArr[i3][i4] = (long) definition2.a.getFormat(iArr[i4]).l;
                    }
                    Arrays.sort(jArr[i3]);
                }
            }
            int[] iArr2 = new int[length];
            long[] jArr2 = new long[length];
            for (int i5 = 0; i5 < length; i5++) {
                long[] jArr3 = jArr[i5];
                if (jArr3.length == 0) {
                    j = 0;
                } else {
                    j = jArr3[0];
                }
                jArr2[i5] = j;
            }
            AdaptiveTrackSelection.a(arrayList, jArr2);
            ListMultimap<K, V> build = MultimapBuilder.treeKeys().arrayListValues().build();
            int i6 = 0;
            while (i6 < length) {
                long[] jArr4 = jArr[i6];
                if (jArr4.length <= i) {
                    listMultimap = build;
                } else {
                    int length2 = jArr4.length;
                    double[] dArr = new double[length2];
                    int i7 = 0;
                    while (true) {
                        long[] jArr5 = jArr[i6];
                        double d3 = 0.0d;
                        if (i7 >= jArr5.length) {
                            break;
                        }
                        ListMultimap<K, V> listMultimap2 = build;
                        long j2 = jArr5[i7];
                        if (j2 != -1) {
                            d3 = Math.log((double) j2);
                        }
                        dArr[i7] = d3;
                        i7++;
                        build = listMultimap2;
                    }
                    ListMultimap<K, V> listMultimap3 = build;
                    int i8 = length2 - 1;
                    double d4 = dArr[i8] - dArr[c2];
                    int i9 = 0;
                    while (i9 < i8) {
                        double d5 = dArr[i9];
                        i9++;
                        double d6 = (d5 + dArr[i9]) * 0.5d;
                        if (d4 == 0.0d) {
                            d2 = 1.0d;
                        } else {
                            d2 = (d6 - dArr[c2]) / d4;
                        }
                        listMultimap3.put(Double.valueOf(d2), Integer.valueOf(i6));
                        c2 = 0;
                    }
                    listMultimap = listMultimap3;
                }
                i6++;
                build = listMultimap;
                c2 = 0;
                i = 1;
            }
            ImmutableList<V> copyOf = ImmutableList.copyOf(build.values());
            for (int i10 = 0; i10 < copyOf.size(); i10++) {
                int intValue = ((Integer) copyOf.get(i10)).intValue();
                int i11 = iArr2[intValue] + 1;
                iArr2[intValue] = i11;
                jArr2[intValue] = jArr[intValue][i11];
                AdaptiveTrackSelection.a(arrayList, jArr2);
            }
            for (int i12 = 0; i12 < definitionArr2.length; i12++) {
                if (arrayList.get(i12) != null) {
                    jArr2[i12] = jArr2[i12] * 2;
                }
            }
            AdaptiveTrackSelection.a(arrayList, jArr2);
            ImmutableList.Builder builder2 = ImmutableList.builder();
            for (int i13 = 0; i13 < arrayList.size(); i13++) {
                ImmutableList.Builder builder3 = (ImmutableList.Builder) arrayList.get(i13);
                if (builder3 == null) {
                    immutableList2 = ImmutableList.of();
                } else {
                    immutableList2 = builder3.build();
                }
                builder2.add((Object) immutableList2);
            }
            ImmutableList build2 = builder2.build();
            ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[definitionArr2.length];
            int i14 = 0;
            while (i14 < definitionArr2.length) {
                ExoTrackSelection.Definition definition3 = definitionArr2[i14];
                if (definition3 != null) {
                    int[] iArr3 = definition3.b;
                    if (iArr3.length != 0) {
                        if (iArr3.length == 1) {
                            exoTrackSelection = new FixedTrackSelection(definition3.a, iArr3[0], definition3.c);
                            immutableList = build2;
                        } else {
                            immutableList = build2;
                            exoTrackSelection = new AdaptiveTrackSelection(definition3.a, iArr3, definition3.c, bandwidthMeter, (long) this.a, (long) this.b, (long) this.c, this.d, this.e, (ImmutableList) build2.get(i14), this.f);
                        }
                        exoTrackSelectionArr[i14] = exoTrackSelection;
                        i14++;
                        definitionArr2 = definitionArr;
                        build2 = immutableList;
                    }
                }
                immutableList = build2;
                i14++;
                definitionArr2 = definitionArr;
                build2 = immutableList;
            }
            return exoTrackSelectionArr;
        }

        public Factory(int i, int i2, int i3, float f2) {
            this(i, i2, i3, f2, 0.75f, Clock.a);
        }

        public Factory(int i, int i2, int i3, float f2, float f3, Clock clock) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = f2;
            this.e = f3;
            this.f = clock;
        }
    }

    public AdaptiveTrackSelection(TrackGroup trackGroup, int[] iArr, BandwidthMeter bandwidthMeter) {
        this(trackGroup, iArr, 0, bandwidthMeter, 10000, 25000, 25000, 0.7f, 0.75f, ImmutableList.of(), Clock.a);
    }

    public static void a(ArrayList arrayList, long[] jArr) {
        long j2 = 0;
        for (long j3 : jArr) {
            j2 += j3;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ImmutableList.Builder builder = (ImmutableList.Builder) arrayList.get(i2);
            if (builder != null) {
                builder.add((Object) new AdaptationCheckpoint(j2, jArr[i2]));
            }
        }
    }

    public static long c(List list) {
        if (list.isEmpty()) {
            return -9223372036854775807L;
        }
        MediaChunk mediaChunk = (MediaChunk) Iterables.getLast(list);
        long j2 = mediaChunk.g;
        if (j2 == -9223372036854775807L) {
            return -9223372036854775807L;
        }
        long j3 = mediaChunk.h;
        if (j3 != -9223372036854775807L) {
            return j3 - j2;
        }
        return -9223372036854775807L;
    }

    public final int b(long j2, long j3) {
        long j4;
        boolean z;
        BandwidthMeter bandwidthMeter = this.h;
        long bitrateEstimate = (long) (((float) bandwidthMeter.getBitrateEstimate()) * this.l);
        long timeToFirstByteEstimateUs = bandwidthMeter.getTimeToFirstByteEstimateUs();
        if (timeToFirstByteEstimateUs == -9223372036854775807L || j3 == -9223372036854775807L) {
            j4 = (long) (((float) bitrateEstimate) / this.p);
        } else {
            float f = (float) j3;
            j4 = (long) ((((float) bitrateEstimate) * Math.max((f / this.p) - ((float) timeToFirstByteEstimateUs), 0.0f)) / f);
        }
        ImmutableList<AdaptationCheckpoint> immutableList = this.n;
        if (!immutableList.isEmpty()) {
            int i2 = 1;
            while (i2 < immutableList.size() - 1 && immutableList.get(i2).a < j4) {
                i2++;
            }
            AdaptationCheckpoint adaptationCheckpoint = immutableList.get(i2 - 1);
            AdaptationCheckpoint adaptationCheckpoint2 = immutableList.get(i2);
            long j5 = adaptationCheckpoint.a;
            long j6 = adaptationCheckpoint2.b;
            long j7 = adaptationCheckpoint.b;
            j4 = ((long) ((((float) (j4 - j5)) / ((float) (adaptationCheckpoint2.a - j5))) * ((float) (j6 - j7)))) + j7;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.b; i4++) {
            if (j2 == Long.MIN_VALUE || !isBlacklisted(i4, j2)) {
                if (((long) getFormat(i4).l) <= j4) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    return i4;
                }
                i3 = i4;
            }
        }
        return i3;
    }

    @CallSuper
    public void disable() {
        this.t = null;
    }

    @CallSuper
    public void enable() {
        this.s = -9223372036854775807L;
        this.t = null;
    }

    public int evaluateQueueSize(long j2, List<? extends MediaChunk> list) {
        boolean z;
        MediaChunk mediaChunk;
        int i2;
        int i3;
        long elapsedRealtime = this.o.elapsedRealtime();
        long j3 = this.s;
        if (j3 == -9223372036854775807L || elapsedRealtime - j3 >= 1000 || (!list.isEmpty() && !((MediaChunk) Iterables.getLast(list)).equals(this.t))) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return list.size();
        }
        this.s = elapsedRealtime;
        if (list.isEmpty()) {
            mediaChunk = null;
        } else {
            mediaChunk = (MediaChunk) Iterables.getLast(list);
        }
        this.t = mediaChunk;
        if (list.isEmpty()) {
            return 0;
        }
        int size = list.size();
        long playoutDurationForMediaDuration = Util.getPlayoutDurationForMediaDuration(((MediaChunk) list.get(size - 1)).g - j2, this.p);
        long j4 = this.k;
        if (playoutDurationForMediaDuration < j4) {
            return size;
        }
        Format format = getFormat(b(elapsedRealtime, c(list)));
        for (int i4 = 0; i4 < size; i4++) {
            MediaChunk mediaChunk2 = (MediaChunk) list.get(i4);
            Format format2 = mediaChunk2.d;
            if (Util.getPlayoutDurationForMediaDuration(mediaChunk2.g - j2, this.p) >= j4 && format2.l < format.l && (i2 = format2.v) != -1 && i2 < 720 && (i3 = format2.u) != -1 && i3 < 1280 && i2 < format.v) {
                return i4;
            }
        }
        return size;
    }

    public int getSelectedIndex() {
        return this.q;
    }

    @Nullable
    public Object getSelectionData() {
        return null;
    }

    public int getSelectionReason() {
        return this.r;
    }

    public /* bridge */ /* synthetic */ void onDiscontinuity() {
        s2.a(this);
    }

    public /* bridge */ /* synthetic */ void onPlayWhenReadyChanged(boolean z) {
        s2.b(this, z);
    }

    public void onPlaybackSpeed(float f) {
        this.p = f;
    }

    public /* bridge */ /* synthetic */ void onRebuffer() {
        s2.c(this);
    }

    public /* bridge */ /* synthetic */ boolean shouldCancelChunkLoad(long j2, Chunk chunk, List list) {
        return s2.d(this, j2, chunk, list);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a5, code lost:
        if (r18 < r13) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ae, code lost:
        if (r18 >= r0.j) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b0, code lost:
        r6 = r10;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateSelectedTrack(long r16, long r18, long r20, java.util.List<? extends com.google.android.exoplayer2.source.chunk.MediaChunk> r22, com.google.android.exoplayer2.source.chunk.MediaChunkIterator[] r23) {
        /*
            r15 = this;
            r0 = r15
            r1 = r20
            r3 = r23
            com.google.android.exoplayer2.util.Clock r4 = r0.o
            long r4 = r4.elapsedRealtime()
            int r6 = r0.q
            int r7 = r3.length
            r8 = 0
            if (r6 >= r7) goto L_0x0026
            r6 = r3[r6]
            boolean r6 = r6.next()
            if (r6 == 0) goto L_0x0026
            int r6 = r0.q
            r3 = r3[r6]
            long r6 = r3.getChunkEndTimeUs()
            long r9 = r3.getChunkStartTimeUs()
            goto L_0x003a
        L_0x0026:
            int r6 = r3.length
            r7 = 0
        L_0x0028:
            if (r7 >= r6) goto L_0x003f
            r9 = r3[r7]
            boolean r10 = r9.next()
            if (r10 == 0) goto L_0x003c
            long r6 = r9.getChunkEndTimeUs()
            long r9 = r9.getChunkStartTimeUs()
        L_0x003a:
            long r6 = r6 - r9
            goto L_0x0043
        L_0x003c:
            int r7 = r7 + 1
            goto L_0x0028
        L_0x003f:
            long r6 = c(r22)
        L_0x0043:
            int r3 = r0.r
            r9 = 1
            if (r3 != 0) goto L_0x0051
            r0.r = r9
            int r1 = r15.b(r4, r6)
            r0.q = r1
            return
        L_0x0051:
            int r10 = r0.q
            boolean r11 = r22.isEmpty()
            r12 = -1
            if (r11 == 0) goto L_0x005c
            r11 = -1
            goto L_0x0068
        L_0x005c:
            java.lang.Object r11 = com.google.common.collect.Iterables.getLast(r22)
            com.google.android.exoplayer2.source.chunk.MediaChunk r11 = (com.google.android.exoplayer2.source.chunk.MediaChunk) r11
            com.google.android.exoplayer2.Format r11 = r11.d
            int r11 = r15.indexOf((com.google.android.exoplayer2.Format) r11)
        L_0x0068:
            if (r11 == r12) goto L_0x0073
            java.lang.Object r3 = com.google.common.collect.Iterables.getLast(r22)
            com.google.android.exoplayer2.source.chunk.MediaChunk r3 = (com.google.android.exoplayer2.source.chunk.MediaChunk) r3
            int r3 = r3.e
            r10 = r11
        L_0x0073:
            int r6 = r15.b(r4, r6)
            boolean r4 = r15.isBlacklisted(r10, r4)
            if (r4 != 0) goto L_0x00b1
            com.google.android.exoplayer2.Format r4 = r15.getFormat(r10)
            com.google.android.exoplayer2.Format r5 = r15.getFormat(r6)
            int r5 = r5.l
            int r4 = r4.l
            if (r5 <= r4) goto L_0x00a8
            r11 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            long r13 = r0.i
            int r7 = (r1 > r11 ? 1 : (r1 == r11 ? 0 : -1))
            if (r7 == 0) goto L_0x009b
            int r7 = (r1 > r13 ? 1 : (r1 == r13 ? 0 : -1))
            if (r7 > 0) goto L_0x009b
            r8 = 1
        L_0x009b:
            if (r8 == 0) goto L_0x00a3
            float r1 = (float) r1
            float r2 = r0.m
            float r1 = r1 * r2
            long r13 = (long) r1
        L_0x00a3:
            int r1 = (r18 > r13 ? 1 : (r18 == r13 ? 0 : -1))
            if (r1 >= 0) goto L_0x00a8
            goto L_0x00b0
        L_0x00a8:
            if (r5 >= r4) goto L_0x00b1
            long r1 = r0.j
            int r4 = (r18 > r1 ? 1 : (r18 == r1 ? 0 : -1))
            if (r4 < 0) goto L_0x00b1
        L_0x00b0:
            r6 = r10
        L_0x00b1:
            if (r6 != r10) goto L_0x00b4
            goto L_0x00b5
        L_0x00b4:
            r3 = 3
        L_0x00b5:
            r0.r = r3
            r0.q = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection.updateSelectedTrack(long, long, long, java.util.List, com.google.android.exoplayer2.source.chunk.MediaChunkIterator[]):void");
    }

    public AdaptiveTrackSelection(TrackGroup trackGroup, int[] iArr, int i2, BandwidthMeter bandwidthMeter, long j2, long j3, long j4, float f, float f2, ImmutableList immutableList, Clock clock) {
        super(trackGroup, iArr, i2);
        if (j4 < j2) {
            Log.w("AdaptiveTrackSelection", "Adjusting minDurationToRetainAfterDiscardMs to be at least minDurationForQualityIncreaseMs");
            j4 = j2;
        }
        this.h = bandwidthMeter;
        this.i = j2 * 1000;
        this.j = j3 * 1000;
        this.k = j4 * 1000;
        this.l = f;
        this.m = f2;
        this.n = ImmutableList.copyOf(immutableList);
        this.o = clock;
        this.p = 1.0f;
        this.r = 0;
        this.s = -9223372036854775807L;
    }
}
