package com.google.android.exoplayer2.source.ads;

import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ForwardingTimeline;
import com.google.android.exoplayer2.util.Assertions;

@VisibleForTesting(otherwise = 3)
public final class SinglePeriodAdTimeline extends ForwardingTimeline {
    public final AdPlaybackState g;

    public SinglePeriodAdTimeline(Timeline timeline, AdPlaybackState adPlaybackState) {
        super(timeline);
        boolean z;
        boolean z2 = false;
        if (timeline.getPeriodCount() == 1) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        Assertions.checkState(timeline.getWindowCount() == 1 ? true : z2);
        this.g = adPlaybackState;
    }

    public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
        this.f.getPeriod(i, period, z);
        long j = period.h;
        if (j == -9223372036854775807L) {
            j = this.g.j;
        }
        Timeline.Period period2 = period;
        period2.set(period.c, period.f, period.g, j, period.getPositionInWindowUs(), this.g, period.j);
        return period;
    }
}
