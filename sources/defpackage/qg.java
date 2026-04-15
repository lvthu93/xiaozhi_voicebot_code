package defpackage;

import com.grack.nanojson.JsonObject;
import j$.time.format.DateTimeFormatter;
import j$.util.function.Function$CC;
import java.util.function.Function;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemLockupExtractor;

/* renamed from: qg  reason: default package */
public final /* synthetic */ class qg implements Function {
    public final /* synthetic */ int a;

    public /* synthetic */ qg(int i) {
        this.a = i;
    }

    public final /* synthetic */ Function andThen(Function function) {
        return Function$CC.$default$andThen(this, function);
    }

    public final Object apply(Object obj) {
        DateTimeFormatter dateTimeFormatter = YoutubeStreamInfoItemLockupExtractor.h;
        return ((JsonObject) obj).getArray("metadataParts").streamAsJsonObjects().skip((long) this.a).limit(1);
    }

    public final /* synthetic */ Function compose(Function function) {
        return Function$CC.$default$compose(this, function);
    }
}
