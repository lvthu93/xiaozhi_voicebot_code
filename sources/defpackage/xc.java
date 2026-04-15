package defpackage;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.ttml.TtmlStyle;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* renamed from: xc  reason: default package */
public final class xc implements Subtitle {
    public final uc c;
    public final long[] f;
    public final Map<String, TtmlStyle> g;
    public final Map<String, vc> h;
    public final Map<String, String> i;

    public xc(uc ucVar, Map<String, TtmlStyle> map, Map<String, vc> map2, Map<String, String> map3) {
        Map<String, TtmlStyle> map4;
        this.c = ucVar;
        this.h = map2;
        this.i = map3;
        if (map != null) {
            map4 = Collections.unmodifiableMap(map);
        } else {
            map4 = Collections.emptyMap();
        }
        this.g = map4;
        this.f = ucVar.getEventTimesUs();
    }

    public List<Cue> getCues(long j) {
        return this.c.getCues(j, this.g, this.h, this.i);
    }

    public long getEventTime(int i2) {
        return this.f[i2];
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
