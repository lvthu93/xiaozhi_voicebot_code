package defpackage;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

/* renamed from: dc  reason: default package */
public final class dc implements Subtitle {
    public final Cue[] c;
    public final long[] f;

    public dc(Cue[] cueArr, long[] jArr) {
        this.c = cueArr;
        this.f = jArr;
    }

    public List<Cue> getCues(long j) {
        Cue cue;
        int binarySearchFloor = Util.binarySearchFloor(this.f, j, true, false);
        if (binarySearchFloor == -1 || (cue = this.c[binarySearchFloor]) == Cue.r) {
            return Collections.emptyList();
        }
        return Collections.singletonList(cue);
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
        long[] jArr = this.f;
        if (i >= jArr.length) {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        return jArr[i];
    }

    public int getEventTimeCount() {
        return this.f.length;
    }

    public int getNextEventTimeIndex(long j) {
        long[] jArr = this.f;
        int binarySearchCeil = Util.binarySearchCeil(jArr, j, false, false);
        if (binarySearchCeil < jArr.length) {
            return binarySearchCeil;
        }
        return -1;
    }
}
