package defpackage;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

/* renamed from: mb  reason: default package */
public final class mb implements Subtitle {
    public final List<List<Cue>> c;
    public final List<Long> f;

    public mb(List<List<Cue>> list, List<Long> list2) {
        this.c = list;
        this.f = list2;
    }

    public List<Cue> getCues(long j) {
        int binarySearchFloor = Util.binarySearchFloor(this.f, Long.valueOf(j), true, false);
        if (binarySearchFloor == -1) {
            return Collections.emptyList();
        }
        return this.c.get(binarySearchFloor);
    }

    public long getEventTime(int i) {
        boolean z;
        boolean z2 = true;
        if (i >= 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        List<Long> list = this.f;
        if (i >= list.size()) {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        return list.get(i).longValue();
    }

    public int getEventTimeCount() {
        return this.f.size();
    }

    public int getNextEventTimeIndex(long j) {
        Long valueOf = Long.valueOf(j);
        List<Long> list = this.f;
        int binarySearchCeil = Util.binarySearchCeil(list, valueOf, false, false);
        if (binarySearchCeil < list.size()) {
            return binarySearchCeil;
        }
        return -1;
    }
}
