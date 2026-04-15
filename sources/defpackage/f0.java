package defpackage;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Collections;
import java.util.List;

/* renamed from: f0  reason: default package */
public final class f0 implements Subtitle {
    public final List<Cue> c;

    public f0(List<Cue> list) {
        this.c = list;
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
}
