package defpackage;

import java.util.function.ToIntFunction;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.services.youtube.YoutubeDescriptionHelper;

/* renamed from: xf  reason: default package */
public final /* synthetic */ class xf implements ToIntFunction {
    public final /* synthetic */ int a;

    public /* synthetic */ xf(int i) {
        this.a = i;
    }

    public final int applyAsInt(Object obj) {
        switch (this.a) {
            case 0:
                Pattern pattern = YoutubeDescriptionHelper.a;
                return ((YoutubeDescriptionHelper.a) obj).c;
            default:
                Pattern pattern2 = YoutubeDescriptionHelper.a;
                return ((YoutubeDescriptionHelper.a) obj).c;
        }
    }
}
