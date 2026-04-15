package defpackage;

import com.grack.nanojson.JsonObject;
import j$.util.function.Function$CC;
import java.util.function.Function;
import org.schabi.newpipe.extractor.Extractor;
import org.schabi.newpipe.extractor.MultiInfoItemsCollector;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor;

/* renamed from: sf  reason: default package */
public final /* synthetic */ class sf implements Function {
    public final /* synthetic */ int a = 0;
    public final /* synthetic */ String b;
    public final /* synthetic */ String c;
    public final /* synthetic */ Extractor d;
    public final /* synthetic */ Object e;
    public final /* synthetic */ Object f;

    public /* synthetic */ sf(YoutubeChannelTabExtractor youtubeChannelTabExtractor, MultiInfoItemsCollector multiInfoItemsCollector, YoutubeChannelTabExtractor.a aVar, String str, String str2) {
        this.d = youtubeChannelTabExtractor;
        this.e = multiInfoItemsCollector;
        this.f = aVar;
        this.b = str;
        this.c = str2;
    }

    public /* synthetic */ sf(YoutubeStreamExtractor youtubeStreamExtractor, ItagItem.ItagType itagType, String str, String str2, String str3) {
        this.d = youtubeStreamExtractor;
        this.e = itagType;
        this.b = str;
        this.c = str2;
        this.f = str3;
    }

    public final /* synthetic */ Function andThen(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$andThen(this, function);
            default:
                return Function$CC.$default$andThen(this, function);
        }
    }

    public final Object apply(Object obj) {
        Object obj2 = this.f;
        int i = this.a;
        Object obj3 = this.e;
        Extractor extractor = this.d;
        switch (i) {
            case 0:
                return ((YoutubeChannelTabExtractor) extractor).b((JsonObject) obj, this.b, this.c, (MultiInfoItemsCollector) obj3, (YoutubeChannelTabExtractor.a) obj2);
            default:
                YoutubeStreamExtractor youtubeStreamExtractor = (YoutubeStreamExtractor) extractor;
                ItagItem.ItagType itagType = (ItagItem.ItagType) obj3;
                String str = this.b;
                String str2 = this.c;
                String str3 = (String) obj2;
                JsonObject jsonObject = (JsonObject) obj;
                youtubeStreamExtractor.getClass();
                try {
                    ItagItem itag = ItagItem.getItag(jsonObject.getInt("itag"));
                    ItagItem.ItagType itagType2 = itag.g;
                    if (itagType2 == itagType) {
                        return youtubeStreamExtractor.c(str, jsonObject, itag, itagType2, str2, str3);
                    }
                } catch (ExtractionException unused) {
                }
                return null;
        }
    }

    public final /* synthetic */ Function compose(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$compose(this, function);
            default:
                return Function$CC.$default$compose(this, function);
        }
    }
}
