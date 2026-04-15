package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.ActivityChooserView;
import com.google.android.exoplayer2.AbstractConcatenatedTimeline;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.HashMap;

@Deprecated
public final class LoopingMediaSource extends CompositeMediaSource<Void> {
    public final MaskingMediaSource j;
    public final int k;
    public final HashMap l;
    public final HashMap m;

    public static final class a extends ForwardingTimeline {
        public a(Timeline timeline) {
            super(timeline);
        }

        public int getNextWindowIndex(int i, int i2, boolean z) {
            int nextWindowIndex = this.f.getNextWindowIndex(i, i2, z);
            if (nextWindowIndex == -1) {
                return getFirstWindowIndex(z);
            }
            return nextWindowIndex;
        }

        public int getPreviousWindowIndex(int i, int i2, boolean z) {
            int previousWindowIndex = this.f.getPreviousWindowIndex(i, i2, z);
            if (previousWindowIndex == -1) {
                return getLastWindowIndex(z);
            }
            return previousWindowIndex;
        }
    }

    public static final class b extends AbstractConcatenatedTimeline {
        public final Timeline i;
        public final int j;
        public final int k;
        public final int l;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(Timeline timeline, int i2) {
            super(false, new ShuffleOrder.UnshuffledShuffleOrder(i2));
            boolean z = false;
            this.i = timeline;
            int periodCount = timeline.getPeriodCount();
            this.j = periodCount;
            this.k = timeline.getWindowCount();
            this.l = i2;
            if (periodCount > 0) {
                Assertions.checkState(i2 <= ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED / periodCount ? true : z, "LoopingMediaSource contains too many periods");
            }
        }

        public final int b(Object obj) {
            if (!(obj instanceof Integer)) {
                return -1;
            }
            return ((Integer) obj).intValue();
        }

        public final int c(int i2) {
            return i2 / this.j;
        }

        public final int d(int i2) {
            return i2 / this.k;
        }

        public final Object e(int i2) {
            return Integer.valueOf(i2);
        }

        public final int f(int i2) {
            return i2 * this.j;
        }

        public final int g(int i2) {
            return i2 * this.k;
        }

        public int getPeriodCount() {
            return this.j * this.l;
        }

        public int getWindowCount() {
            return this.k * this.l;
        }

        public final Timeline i(int i2) {
            return this.i;
        }
    }

    public LoopingMediaSource(MediaSource mediaSource) {
        this(mediaSource, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        int i = this.k;
        MaskingMediaSource maskingMediaSource = this.j;
        if (i == Integer.MAX_VALUE) {
            return maskingMediaSource.createPeriod(mediaPeriodId, allocator, j2);
        }
        MediaSource.MediaPeriodId copyWithPeriodUid = mediaPeriodId.copyWithPeriodUid(AbstractConcatenatedTimeline.getChildPeriodUidFromConcatenatedUid(mediaPeriodId.a));
        this.l.put(copyWithPeriodUid, mediaPeriodId);
        MaskingMediaPeriod createPeriod = maskingMediaSource.createPeriod(copyWithPeriodUid, allocator, j2);
        this.m.put(createPeriod, copyWithPeriodUid);
        return createPeriod;
    }

    @Nullable
    public final MediaSource.MediaPeriodId d(Object obj, MediaSource.MediaPeriodId mediaPeriodId) {
        Void voidR = (Void) obj;
        if (this.k != Integer.MAX_VALUE) {
            return (MediaSource.MediaPeriodId) this.l.get(mediaPeriodId);
        }
        return mediaPeriodId;
    }

    public final void f(Object obj, MediaSource mediaSource, Timeline timeline) {
        Timeline timeline2;
        Void voidR = (Void) obj;
        int i = this.k;
        if (i != Integer.MAX_VALUE) {
            timeline2 = new b(timeline, i);
        } else {
            timeline2 = new a(timeline);
        }
        c(timeline2);
    }

    @Nullable
    public Timeline getInitialTimeline() {
        MaskingMediaSource maskingMediaSource = this.j;
        int i = this.k;
        if (i != Integer.MAX_VALUE) {
            return new b(maskingMediaSource.getTimeline(), i);
        }
        return new a(maskingMediaSource.getTimeline());
    }

    public MediaItem getMediaItem() {
        return this.j.getMediaItem();
    }

    @Deprecated
    @Nullable
    public Object getTag() {
        return this.j.getTag();
    }

    public boolean isSingleWindow() {
        return false;
    }

    public final void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        g(null, this.j);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        this.j.releasePeriod(mediaPeriod);
        MediaSource.MediaPeriodId mediaPeriodId = (MediaSource.MediaPeriodId) this.m.remove(mediaPeriod);
        if (mediaPeriodId != null) {
            this.l.remove(mediaPeriodId);
        }
    }

    public LoopingMediaSource(MediaSource mediaSource, int i) {
        Assertions.checkArgument(i > 0);
        this.j = new MaskingMediaSource(mediaSource, false);
        this.k = i;
        this.l = new HashMap();
        this.m = new HashMap();
    }
}
