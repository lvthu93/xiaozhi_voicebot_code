package defpackage;

import androidx.core.app.NotificationCompat;
import com.grack.nanojson.JsonObject;
import j$.util.function.Predicate$CC;
import java.util.Map;
import java.util.function.Predicate;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.services.peertube.linkHandler.PeertubeTrendingLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;

/* renamed from: y5  reason: default package */
public final /* synthetic */ class y5 implements Predicate {
    public final /* synthetic */ int a;
    public final /* synthetic */ String b;

    public /* synthetic */ y5(String str, int i) {
        this.a = i;
        this.b = str;
    }

    public final /* synthetic */ Predicate and(Predicate predicate) {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$and(this, predicate);
            case 1:
                return Predicate$CC.$default$and(this, predicate);
            case 2:
                return Predicate$CC.$default$and(this, predicate);
            case 3:
                return Predicate$CC.$default$and(this, predicate);
            case 4:
                return Predicate$CC.$default$and(this, predicate);
            case 5:
                return Predicate$CC.$default$and(this, predicate);
            case 6:
                return Predicate$CC.$default$and(this, predicate);
            case 7:
                return Predicate$CC.$default$and(this, predicate);
            case 8:
                return Predicate$CC.$default$and(this, predicate);
            case 9:
                return Predicate$CC.$default$and(this, predicate);
            case 10:
                return Predicate$CC.$default$and(this, predicate);
            case 11:
                return Predicate$CC.$default$and(this, predicate);
            case 12:
                return Predicate$CC.$default$and(this, predicate);
            default:
                return Predicate$CC.$default$and(this, predicate);
        }
    }

    public final /* synthetic */ Predicate negate() {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$negate(this);
            case 1:
                return Predicate$CC.$default$negate(this);
            case 2:
                return Predicate$CC.$default$negate(this);
            case 3:
                return Predicate$CC.$default$negate(this);
            case 4:
                return Predicate$CC.$default$negate(this);
            case 5:
                return Predicate$CC.$default$negate(this);
            case 6:
                return Predicate$CC.$default$negate(this);
            case 7:
                return Predicate$CC.$default$negate(this);
            case 8:
                return Predicate$CC.$default$negate(this);
            case 9:
                return Predicate$CC.$default$negate(this);
            case 10:
                return Predicate$CC.$default$negate(this);
            case 11:
                return Predicate$CC.$default$negate(this);
            case 12:
                return Predicate$CC.$default$negate(this);
            default:
                return Predicate$CC.$default$negate(this);
        }
    }

    public final /* synthetic */ Predicate or(Predicate predicate) {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$or(this, predicate);
            case 1:
                return Predicate$CC.$default$or(this, predicate);
            case 2:
                return Predicate$CC.$default$or(this, predicate);
            case 3:
                return Predicate$CC.$default$or(this, predicate);
            case 4:
                return Predicate$CC.$default$or(this, predicate);
            case 5:
                return Predicate$CC.$default$or(this, predicate);
            case 6:
                return Predicate$CC.$default$or(this, predicate);
            case 7:
                return Predicate$CC.$default$or(this, predicate);
            case 8:
                return Predicate$CC.$default$or(this, predicate);
            case 9:
                return Predicate$CC.$default$or(this, predicate);
            case 10:
                return Predicate$CC.$default$or(this, predicate);
            case 11:
                return Predicate$CC.$default$or(this, predicate);
            case 12:
                return Predicate$CC.$default$or(this, predicate);
            default:
                return Predicate$CC.$default$or(this, predicate);
        }
    }

    public final boolean test(Object obj) {
        int i = this.a;
        String str = this.b;
        switch (i) {
            case 0:
                return ((MediaFormat) obj).h.equals(str);
            case 1:
                return ((MediaFormat) obj).g.equals(str);
            case 2:
                return ((MediaFormat) obj).h.equals(str);
            case 3:
                return ((StreamingService) obj).getServiceInfo().getName().equals(str);
            case 4:
                return ((JsonObject) obj).has(str);
            case 5:
                return str.equals(((JsonObject) obj).getString("type"));
            case 6:
                PeertubeTrendingLinkHandlerFactory peertubeTrendingLinkHandlerFactory = PeertubeTrendingLinkHandlerFactory.a;
                return str.equals(((Map.Entry) obj).getValue());
            case 7:
                String str2 = YoutubeParsingHelper.a;
                return ((JsonObject) obj).getString(NotificationCompat.CATEGORY_SERVICE, "").equals(str);
            case 8:
                String str3 = YoutubeParsingHelper.a;
                return ((JsonObject) obj).getString("key", "").equals(str);
            case 9:
                return ((JsonObject) obj).getObject("endpoint").getObject("commandMetadata").getObject("webCommandMetadata").getString("url", "").endsWith(str);
            case 10:
                return str.equals(((JsonObject) obj).getString("entityKey"));
            case 11:
                return ((JsonObject) obj).has(str);
            case 12:
                return str.equals((String) obj);
            default:
                return str.equals((String) obj);
        }
    }
}
