package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.Timeline;

public abstract class ForwardingTimeline extends Timeline {
    public final Timeline f;

    public ForwardingTimeline(Timeline timeline) {
        this.f = timeline;
    }

    public int getFirstWindowIndex(boolean z) {
        return this.f.getFirstWindowIndex(z);
    }

    public int getIndexOfPeriod(Object obj) {
        return this.f.getIndexOfPeriod(obj);
    }

    public int getLastWindowIndex(boolean z) {
        return this.f.getLastWindowIndex(z);
    }

    public int getNextWindowIndex(int i, int i2, boolean z) {
        return this.f.getNextWindowIndex(i, i2, z);
    }

    public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
        return this.f.getPeriod(i, period, z);
    }

    public int getPeriodCount() {
        return this.f.getPeriodCount();
    }

    public int getPreviousWindowIndex(int i, int i2, boolean z) {
        return this.f.getPreviousWindowIndex(i, i2, z);
    }

    public Object getUidOfPeriod(int i) {
        return this.f.getUidOfPeriod(i);
    }

    public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
        return this.f.getWindow(i, window, j);
    }

    public int getWindowCount() {
        return this.f.getWindowCount();
    }
}
