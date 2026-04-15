package defpackage;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Collections;
import java.util.List;

/* renamed from: b7  reason: default package */
public final class b7 implements Subtitle {
    public final List<Cue> c;

    public b7(List<Cue> list) {
        this.c = Collections.unmodifiableList(list);
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
