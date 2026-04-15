package defpackage;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.webvtt.WebvttCueInfo;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* renamed from: df  reason: default package */
public final class df implements Subtitle {
    public final List<WebvttCueInfo> c;
    public final long[] f;
    public final long[] g;

    public df(List<WebvttCueInfo> list) {
        this.c = Collections.unmodifiableList(new ArrayList(list));
        this.f = new long[(list.size() * 2)];
        for (int i = 0; i < list.size(); i++) {
            WebvttCueInfo webvttCueInfo = list.get(i);
            int i2 = i * 2;
            long[] jArr = this.f;
            jArr[i2] = webvttCueInfo.b;
            jArr[i2 + 1] = webvttCueInfo.c;
        }
        long[] jArr2 = this.f;
        long[] copyOf = Arrays.copyOf(jArr2, jArr2.length);
        this.g = copyOf;
        Arrays.sort(copyOf);
    }

    public List<Cue> getCues(long j) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        while (true) {
            List<WebvttCueInfo> list = this.c;
            if (i >= list.size()) {
                break;
            }
            int i2 = i * 2;
            long[] jArr = this.f;
            if (jArr[i2] <= j && j < jArr[i2 + 1]) {
                WebvttCueInfo webvttCueInfo = list.get(i);
                Cue cue = webvttCueInfo.a;
                if (cue.e == -3.4028235E38f) {
                    arrayList2.add(webvttCueInfo);
                } else {
                    arrayList.add(cue);
                }
            }
            i++;
        }
        Collections.sort(arrayList2, new db(6));
        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
            arrayList.add(((WebvttCueInfo) arrayList2.get(i3)).a.buildUpon().setLine((float) (-1 - i3), 1).build());
        }
        return arrayList;
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
        long[] jArr = this.g;
        if (i >= jArr.length) {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        return jArr[i];
    }

    public int getEventTimeCount() {
        return this.g.length;
    }

    public int getNextEventTimeIndex(long j) {
        long[] jArr = this.g;
        int binarySearchCeil = Util.binarySearchCeil(jArr, j, false, false);
        if (binarySearchCeil < jArr.length) {
            return binarySearchCeil;
        }
        return -1;
    }
}
