package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class MergingMediaSource extends CompositeMediaSource<Integer> {
    public static final MediaItem u = new MediaItem.Builder().setMediaId("MergingMediaSource").build();
    public final boolean j;
    public final boolean k;
    public final MediaSource[] l;
    public final Timeline[] m;
    public final ArrayList<MediaSource> n;
    public final CompositeSequenceableLoaderFactory o;
    public final HashMap p;
    public final ListMultimap q;
    public int r;
    public long[][] s;
    @Nullable
    public IllegalMergeException t;

    public static final class IllegalMergeException extends IOException {

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Reason {
        }

        public IllegalMergeException(int i) {
        }
    }

    public static final class a extends ForwardingTimeline {
        public final long[] g;
        public final long[] h;

        public a(Timeline timeline, Map<Object, Long> map) {
            super(timeline);
            int windowCount = timeline.getWindowCount();
            this.h = new long[timeline.getWindowCount()];
            Timeline.Window window = new Timeline.Window();
            for (int i = 0; i < windowCount; i++) {
                this.h[i] = timeline.getWindow(i, window).r;
            }
            int periodCount = timeline.getPeriodCount();
            this.g = new long[periodCount];
            Timeline.Period period = new Timeline.Period();
            for (int i2 = 0; i2 < periodCount; i2++) {
                timeline.getPeriod(i2, period, true);
                long longValue = ((Long) Assertions.checkNotNull(map.get(period.f))).longValue();
                long[] jArr = this.g;
                longValue = longValue == Long.MIN_VALUE ? period.h : longValue;
                jArr[i2] = longValue;
                long j = period.h;
                if (j != -9223372036854775807L) {
                    long[] jArr2 = this.h;
                    int i3 = period.g;
                    jArr2[i3] = jArr2[i3] - (j - longValue);
                }
            }
        }

        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            super.getPeriod(i, period, z);
            period.h = this.g[i];
            return period;
        }

        public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
            long j2;
            super.getWindow(i, window, j);
            long j3 = this.h[i];
            window.r = j3;
            if (j3 != -9223372036854775807L) {
                long j4 = window.q;
                if (j4 != -9223372036854775807L) {
                    j2 = Math.min(j4, j3);
                    window.q = j2;
                    return window;
                }
            }
            j2 = window.q;
            window.q = j2;
            return window;
        }
    }

    public MergingMediaSource(MediaSource... mediaSourceArr) {
        this(false, mediaSourceArr);
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        MediaSource.MediaPeriodId mediaPeriodId2 = mediaPeriodId;
        MediaSource[] mediaSourceArr = this.l;
        int length = mediaSourceArr.length;
        MediaPeriod[] mediaPeriodArr = new MediaPeriod[length];
        Timeline[] timelineArr = this.m;
        int indexOfPeriod = timelineArr[0].getIndexOfPeriod(mediaPeriodId2.a);
        for (int i = 0; i < length; i++) {
            mediaPeriodArr[i] = mediaSourceArr[i].createPeriod(mediaPeriodId2.copyWithPeriodUid(timelineArr[i].getUidOfPeriod(indexOfPeriod)), allocator, j2 - this.s[indexOfPeriod][i]);
        }
        a aVar = new a(this.o, this.s[indexOfPeriod], mediaPeriodArr);
        if (!this.k) {
            return aVar;
        }
        HashMap hashMap = this.p;
        Object obj = mediaPeriodId2.a;
        ClippingMediaPeriod clippingMediaPeriod = new ClippingMediaPeriod(aVar, true, 0, ((Long) Assertions.checkNotNull((Long) hashMap.get(obj))).longValue());
        this.q.put(obj, clippingMediaPeriod);
        return clippingMediaPeriod;
    }

    @Nullable
    public final MediaSource.MediaPeriodId d(Object obj, MediaSource.MediaPeriodId mediaPeriodId) {
        if (((Integer) obj).intValue() == 0) {
            return mediaPeriodId;
        }
        return null;
    }

    public final void f(Object obj, MediaSource mediaSource, Timeline timeline) {
        HashMap hashMap;
        Integer num = (Integer) obj;
        if (this.t == null) {
            if (this.r == -1) {
                this.r = timeline.getPeriodCount();
            } else if (timeline.getPeriodCount() != this.r) {
                this.t = new IllegalMergeException(0);
                return;
            }
            int length = this.s.length;
            Timeline[] timelineArr = this.m;
            if (length == 0) {
                int i = this.r;
                int[] iArr = new int[2];
                iArr[1] = timelineArr.length;
                iArr[0] = i;
                this.s = (long[][]) Array.newInstance(Long.TYPE, iArr);
            }
            ArrayList<MediaSource> arrayList = this.n;
            arrayList.remove(mediaSource);
            timelineArr[num.intValue()] = timeline;
            if (arrayList.isEmpty()) {
                if (this.j) {
                    Timeline.Period period = new Timeline.Period();
                    for (int i2 = 0; i2 < this.r; i2++) {
                        long j2 = -timelineArr[0].getPeriod(i2, period).getPositionInWindowUs();
                        for (int i3 = 1; i3 < timelineArr.length; i3++) {
                            this.s[i2][i3] = j2 - (-timelineArr[i3].getPeriod(i2, period).getPositionInWindowUs());
                        }
                    }
                }
                a aVar = timelineArr[0];
                if (this.k) {
                    Timeline.Period period2 = new Timeline.Period();
                    int i4 = 0;
                    while (true) {
                        int i5 = this.r;
                        hashMap = this.p;
                        if (i4 >= i5) {
                            break;
                        }
                        long j3 = Long.MIN_VALUE;
                        for (int i6 = 0; i6 < timelineArr.length; i6++) {
                            long durationUs = timelineArr[i6].getPeriod(i4, period2).getDurationUs();
                            if (durationUs != -9223372036854775807L) {
                                long j4 = durationUs + this.s[i4][i6];
                                if (j3 == Long.MIN_VALUE || j4 < j3) {
                                    j3 = j4;
                                }
                            }
                        }
                        Object uidOfPeriod = timelineArr[0].getUidOfPeriod(i4);
                        hashMap.put(uidOfPeriod, Long.valueOf(j3));
                        for (ClippingMediaPeriod updateClipping : this.q.get(uidOfPeriod)) {
                            updateClipping.updateClipping(0, j3);
                        }
                        i4++;
                    }
                    aVar = new a(aVar, hashMap);
                }
                c(aVar);
            }
        }
    }

    @Nullable
    public /* bridge */ /* synthetic */ Timeline getInitialTimeline() {
        return e6.a(this);
    }

    public MediaItem getMediaItem() {
        MediaSource[] mediaSourceArr = this.l;
        return mediaSourceArr.length > 0 ? mediaSourceArr[0].getMediaItem() : u;
    }

    @Deprecated
    @Nullable
    public Object getTag() {
        MediaSource[] mediaSourceArr = this.l;
        if (mediaSourceArr.length > 0) {
            return mediaSourceArr[0].getTag();
        }
        return null;
    }

    public /* bridge */ /* synthetic */ boolean isSingleWindow() {
        return e6.c(this);
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        IllegalMergeException illegalMergeException = this.t;
        if (illegalMergeException == null) {
            super.maybeThrowSourceInfoRefreshError();
            return;
        }
        throw illegalMergeException;
    }

    public final void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        int i = 0;
        while (true) {
            MediaSource[] mediaSourceArr = this.l;
            if (i < mediaSourceArr.length) {
                g(Integer.valueOf(i), mediaSourceArr[i]);
                i++;
            } else {
                return;
            }
        }
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        if (this.k) {
            ClippingMediaPeriod clippingMediaPeriod = (ClippingMediaPeriod) mediaPeriod;
            ListMultimap listMultimap = this.q;
            Iterator it = listMultimap.entries().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry entry = (Map.Entry) it.next();
                if (((ClippingMediaPeriod) entry.getValue()).equals(clippingMediaPeriod)) {
                    listMultimap.remove(entry.getKey(), entry.getValue());
                    break;
                }
            }
            mediaPeriod = clippingMediaPeriod.c;
        }
        a aVar = (a) mediaPeriod;
        int i = 0;
        while (true) {
            MediaSource[] mediaSourceArr = this.l;
            if (i < mediaSourceArr.length) {
                mediaSourceArr[i].releasePeriod(aVar.getChildPeriod(i));
                i++;
            } else {
                return;
            }
        }
    }

    public final void releaseSourceInternal() {
        super.releaseSourceInternal();
        Arrays.fill(this.m, (Object) null);
        this.r = -1;
        this.t = null;
        ArrayList<MediaSource> arrayList = this.n;
        arrayList.clear();
        Collections.addAll(arrayList, this.l);
    }

    public MergingMediaSource(boolean z, MediaSource... mediaSourceArr) {
        this(z, false, mediaSourceArr);
    }

    public MergingMediaSource(boolean z, boolean z2, MediaSource... mediaSourceArr) {
        this(z, z2, new DefaultCompositeSequenceableLoaderFactory(), mediaSourceArr);
    }

    public MergingMediaSource(boolean z, boolean z2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, MediaSource... mediaSourceArr) {
        this.j = z;
        this.k = z2;
        this.l = mediaSourceArr;
        this.o = compositeSequenceableLoaderFactory;
        this.n = new ArrayList<>(Arrays.asList(mediaSourceArr));
        this.r = -1;
        this.m = new Timeline[mediaSourceArr.length];
        this.s = new long[0][];
        this.p = new HashMap();
        this.q = MultimapBuilder.hashKeys().arrayListValues().build();
    }
}
