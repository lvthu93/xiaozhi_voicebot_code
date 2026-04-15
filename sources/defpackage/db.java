package defpackage;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.offline.Download;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.text.cea.Cea708Decoder;
import com.google.android.exoplayer2.text.webvtt.WebvttCueInfo;
import com.google.android.exoplayer2.text.webvtt.WebvttCueParser;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SpannedToHtmlConverter;
import com.google.android.exoplayer2.upstream.cache.CacheSpan;
import com.google.android.exoplayer2.util.SlidingPercentile;
import com.google.android.exoplayer2.util.Util;
import java.util.Comparator;

/* renamed from: db  reason: default package */
public final /* synthetic */ class db implements Comparator {
    public final /* synthetic */ int c;

    public /* synthetic */ db(int i) {
        this.c = i;
    }

    public final int compare(Object obj, Object obj2) {
        switch (this.c) {
            case 1:
                int i = DownloadManager.b.m;
                return Util.compareLong(((Download) obj).c, ((Download) obj2).c);
            case 2:
                int i2 = DownloadManager.b.m;
                return Util.compareLong(((Download) obj).c, ((Download) obj2).c);
            case 3:
                int i3 = DownloadManager.b.m;
                return Util.compareLong(((Download) obj).c, ((Download) obj2).c);
            case 4:
                return Integer.compare(((Cea708Decoder.a) obj2).b, ((Cea708Decoder.a) obj).b);
            case 5:
                return Integer.compare(((WebvttCueParser.a) obj).a.b, ((WebvttCueParser.a) obj2).a.b);
            case 6:
                return Long.compare(((WebvttCueInfo) obj).b, ((WebvttCueInfo) obj2).b);
            case 7:
                return ((Format) obj2).l - ((Format) obj).l;
            case 8:
                Integer num = (Integer) obj;
                Integer num2 = (Integer) obj2;
                int[] iArr = DefaultTrackSelector.f;
                if (num.intValue() == -1) {
                    if (num2.intValue() == -1) {
                        return 0;
                    }
                    return -1;
                } else if (num2.intValue() == -1) {
                    return 1;
                } else {
                    return num.intValue() - num2.intValue();
                }
            case 9:
                Integer num3 = (Integer) obj;
                Integer num4 = (Integer) obj2;
                int[] iArr2 = DefaultTrackSelector.f;
                return 0;
            case 10:
                SpannedToHtmlConverter.a aVar = (SpannedToHtmlConverter.a) obj;
                SpannedToHtmlConverter.a aVar2 = (SpannedToHtmlConverter.a) obj2;
                int compare = Integer.compare(aVar2.b, aVar.b);
                if (compare != 0) {
                    return compare;
                }
                int compareTo = aVar.c.compareTo(aVar2.c);
                if (compareTo != 0) {
                    return compareTo;
                }
                return aVar.d.compareTo(aVar2.d);
            case 11:
                SpannedToHtmlConverter.a aVar3 = (SpannedToHtmlConverter.a) obj;
                SpannedToHtmlConverter.a aVar4 = (SpannedToHtmlConverter.a) obj2;
                int compare2 = Integer.compare(aVar4.a, aVar3.a);
                if (compare2 != 0) {
                    return compare2;
                }
                int compareTo2 = aVar4.c.compareTo(aVar3.c);
                if (compareTo2 != 0) {
                    return compareTo2;
                }
                return aVar4.d.compareTo(aVar3.d);
            case 12:
                CacheSpan cacheSpan = (CacheSpan) obj;
                CacheSpan cacheSpan2 = (CacheSpan) obj2;
                long j = cacheSpan.j;
                long j2 = cacheSpan2.j;
                if (j - j2 == 0) {
                    return cacheSpan.compareTo(cacheSpan2);
                }
                if (j < j2) {
                    return -1;
                }
                return 1;
            case 13:
                return ((SlidingPercentile.a) obj).a - ((SlidingPercentile.a) obj2).a;
            default:
                return Float.compare(((SlidingPercentile.a) obj).c, ((SlidingPercentile.a) obj2).c);
        }
    }
}
