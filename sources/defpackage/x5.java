package defpackage;

import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.ui.TrackSelectionView;
import java.util.Comparator;
import java.util.regex.Pattern;

/* renamed from: x5  reason: default package */
public final /* synthetic */ class x5 implements Comparator {
    public final /* synthetic */ int c;
    public final /* synthetic */ Object f;

    public /* synthetic */ x5(int i, Object obj) {
        this.c = i;
        this.f = obj;
    }

    public final int compare(Object obj, Object obj2) {
        int i = this.c;
        Object obj3 = this.f;
        switch (i) {
            case 0:
                MediaCodecUtil.e eVar = (MediaCodecUtil.e) obj3;
                Pattern pattern = MediaCodecUtil.a;
                return eVar.getScore(obj2) - eVar.getScore(obj);
            default:
                int i2 = TrackSelectionView.u;
                return ((Comparator) obj3).compare(((TrackSelectionView.b) obj).c, ((TrackSelectionView.b) obj2).c);
        }
    }
}
