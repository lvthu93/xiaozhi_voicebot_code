package defpackage;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Collections;
import java.util.List;

/* renamed from: yc  reason: default package */
public final class yc implements Subtitle {
    public static final yc f = new yc();
    public final List<Cue> c;

    public yc(Cue cue) {
        this.c = Collections.singletonList(cue);
    }

    public List<Cue> getCues(long j) {
        return j >= 0 ? this.c : Collections.emptyList();
    }

    public long getEventTime(int i) {
        Assertions.checkArgument(i == 0);
        return 0;
    }

    public int getEventTimeCount() {
        return 1;
    }

    public int getNextEventTimeIndex(long j) {
        return j < 0 ? 0 : -1;
    }

    public yc() {
        this.c = Collections.emptyList();
    }
}
