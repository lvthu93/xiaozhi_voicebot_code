package defpackage;

import j$.util.function.Predicate$CC;
import java.io.Serializable;
import java.net.URL;
import java.util.function.Predicate;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.SubtitlesStream;

/* renamed from: o8  reason: default package */
public final /* synthetic */ class o8 implements Predicate {
    public final /* synthetic */ int a;
    public final /* synthetic */ Serializable b;

    public /* synthetic */ o8(Serializable serializable, int i) {
        this.a = i;
        this.b = serializable;
    }

    public final /* synthetic */ Predicate and(Predicate predicate) {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$and(this, predicate);
            default:
                return Predicate$CC.$default$and(this, predicate);
        }
    }

    public final /* synthetic */ Predicate negate() {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$negate(this);
            default:
                return Predicate$CC.$default$negate(this);
        }
    }

    public final /* synthetic */ Predicate or(Predicate predicate) {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$or(this, predicate);
            default:
                return Predicate$CC.$default$or(this, predicate);
        }
    }

    public final boolean test(Object obj) {
        int i = this.a;
        Serializable serializable = this.b;
        switch (i) {
            case 0:
                if (((SubtitlesStream) obj).getFormat() == ((MediaFormat) serializable)) {
                    return true;
                }
                return false;
            default:
                String str = YoutubeParsingHelper.a;
                return ((URL) serializable).getHost().startsWith((String) obj);
        }
    }
}
