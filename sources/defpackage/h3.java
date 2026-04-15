package defpackage;

import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.mp4.Track;
import com.google.common.base.Function;

/* renamed from: h3  reason: default package */
public final /* synthetic */ class h3 implements Function {
    public final /* synthetic */ FragmentedMp4Extractor c;

    public /* synthetic */ h3(FragmentedMp4Extractor fragmentedMp4Extractor) {
        this.c = fragmentedMp4Extractor;
    }

    public final Object apply(Object obj) {
        Track track = (Track) obj;
        this.c.getClass();
        return track;
    }
}
