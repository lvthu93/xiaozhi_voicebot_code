package com.google.android.exoplayer2.text;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.OutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.util.List;

public abstract class SubtitleOutputBuffer extends OutputBuffer implements Subtitle {
    @Nullable
    public Subtitle h;
    public long i;

    public void clear() {
        super.clear();
        this.h = null;
    }

    public List<Cue> getCues(long j) {
        return ((Subtitle) Assertions.checkNotNull(this.h)).getCues(j - this.i);
    }

    public long getEventTime(int i2) {
        return ((Subtitle) Assertions.checkNotNull(this.h)).getEventTime(i2) + this.i;
    }

    public int getEventTimeCount() {
        return ((Subtitle) Assertions.checkNotNull(this.h)).getEventTimeCount();
    }

    public int getNextEventTimeIndex(long j) {
        return ((Subtitle) Assertions.checkNotNull(this.h)).getNextEventTimeIndex(j - this.i);
    }

    public void setContent(long j, Subtitle subtitle, long j2) {
        this.f = j;
        this.h = subtitle;
        if (j2 != Long.MAX_VALUE) {
            j = j2;
        }
        this.i = j;
    }
}
