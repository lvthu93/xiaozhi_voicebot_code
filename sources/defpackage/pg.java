package defpackage;

import com.grack.nanojson.JsonObject;
import j$.time.format.DateTimeFormatter;
import j$.util.function.Function$CC;
import java.util.function.Function;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemLockupExtractor;

/* renamed from: pg  reason: default package */
public final /* synthetic */ class pg implements Function {
    public final /* synthetic */ int a;
    public final /* synthetic */ YoutubeStreamInfoItemLockupExtractor b;

    public /* synthetic */ pg(YoutubeStreamInfoItemLockupExtractor youtubeStreamInfoItemLockupExtractor, int i) {
        this.a = i;
        this.b = youtubeStreamInfoItemLockupExtractor;
    }

    public final /* synthetic */ Function andThen(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$andThen(this, function);
            case 1:
                return Function$CC.$default$andThen(this, function);
            default:
                return Function$CC.$default$andThen(this, function);
        }
    }

    public final Object apply(Object obj) {
        int i = this.a;
        YoutubeStreamInfoItemLockupExtractor youtubeStreamInfoItemLockupExtractor = this.b;
        switch (i) {
            case 0:
                DateTimeFormatter dateTimeFormatter = YoutubeStreamInfoItemLockupExtractor.h;
                youtubeStreamInfoItemLockupExtractor.getClass();
                return ((JsonObject) obj).getObject("text").getString("content");
            case 1:
                DateTimeFormatter dateTimeFormatter2 = YoutubeStreamInfoItemLockupExtractor.h;
                youtubeStreamInfoItemLockupExtractor.getClass();
                return ((JsonObject) obj).getObject("text").getString("content");
            default:
                DateTimeFormatter dateTimeFormatter3 = YoutubeStreamInfoItemLockupExtractor.h;
                youtubeStreamInfoItemLockupExtractor.getClass();
                return ((JsonObject) obj).getObject("text").getString("content");
        }
    }

    public final /* synthetic */ Function compose(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$compose(this, function);
            case 1:
                return Function$CC.$default$compose(this, function);
            default:
                return Function$CC.$default$compose(this, function);
        }
    }
}
