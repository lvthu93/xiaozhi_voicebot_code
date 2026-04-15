package defpackage;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import java.util.List;

/* renamed from: r8  reason: default package */
public final class r8 implements Subtitle {
    public final List<Cue> c;

    public r8(List<Cue> list) {
        this.c = list;
    }

    public List<Cue> getCues(long j) {
        return this.c;
    }

    public long getEventTime(int i) {
        return 0;
    }

    public int getEventTimeCount() {
        return 1;
    }

    public int getNextEventTimeIndex(long j) {
        return -1;
    }
}
