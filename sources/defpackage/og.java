package defpackage;

import com.grack.nanojson.JsonObject;
import j$.util.Collection$EL;
import j$.util.function.Function$CC;
import j$.util.stream.Stream;
import java.util.function.Function;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor;
import org.schabi.newpipe.extractor.utils.Pair;

/* renamed from: og  reason: default package */
public final /* synthetic */ class og implements Function {
    public final /* synthetic */ YoutubeStreamExtractor a;
    public final /* synthetic */ String b;
    public final /* synthetic */ String c;
    public final /* synthetic */ ItagItem.ItagType d;

    public /* synthetic */ og(YoutubeStreamExtractor youtubeStreamExtractor, String str, String str2, ItagItem.ItagType itagType) {
        this.a = youtubeStreamExtractor;
        this.b = str;
        this.c = str2;
        this.d = itagType;
    }

    public final /* synthetic */ Function andThen(Function function) {
        return Function$CC.$default$andThen(this, function);
    }

    public final Object apply(Object obj) {
        String str = this.b;
        ItagItem.ItagType itagType = this.d;
        Pair pair = (Pair) obj;
        YoutubeStreamExtractor youtubeStreamExtractor = this.a;
        youtubeStreamExtractor.getClass();
        JsonObject jsonObject = (JsonObject) pair.getFirst();
        String str2 = (String) ((Pair) pair.getSecond()).getFirst();
        String str3 = (String) ((Pair) pair.getSecond()).getSecond();
        if (jsonObject != null) {
            String str4 = this.c;
            if (jsonObject.has(str4)) {
                Class<JsonObject> cls = JsonObject.class;
                return y2.ab(cls, 21, y2.aa(cls, 21, Collection$EL.stream(jsonObject.getArray(str4)))).map(new sf(youtubeStreamExtractor, itagType, str, str2, str3)).filter(new mg(5));
            }
        }
        return Stream.CC.empty();
    }

    public final /* synthetic */ Function compose(Function function) {
        return Function$CC.$default$compose(this, function);
    }
}
