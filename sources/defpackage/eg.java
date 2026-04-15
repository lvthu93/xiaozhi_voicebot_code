package defpackage;

import com.grack.nanojson.JsonObject;
import j$.util.function.Function$CC;
import java.util.List;
import java.util.function.Function;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMixOrPlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMixOrPlaylistLockupInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMixPlaylistExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemLockupExtractor;
import org.schabi.newpipe.extractor.utils.ImageSuffix;

/* renamed from: eg  reason: default package */
public final /* synthetic */ class eg implements Function {
    public final /* synthetic */ int a;
    public final /* synthetic */ TimeAgoParser b;

    public /* synthetic */ eg(TimeAgoParser timeAgoParser, int i) {
        this.a = i;
        this.b = timeAgoParser;
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
        int i = this.a;
        TimeAgoParser timeAgoParser = this.b;
        switch (i) {
            case 0:
                List<ImageSuffix> list = YoutubeMixPlaylistExtractor.j;
                return new YoutubeStreamInfoItemExtractor((JsonObject) obj, timeAgoParser);
            default:
                JsonObject jsonObject = (JsonObject) obj;
                if (jsonObject.has("compactVideoRenderer")) {
                    return new YoutubeStreamInfoItemExtractor(jsonObject.getObject("compactVideoRenderer"), timeAgoParser);
                }
                if (jsonObject.has("compactRadioRenderer")) {
                    return new YoutubeMixOrPlaylistInfoItemExtractor(jsonObject.getObject("compactRadioRenderer"));
                }
                if (jsonObject.has("compactPlaylistRenderer")) {
                    return new YoutubeMixOrPlaylistInfoItemExtractor(jsonObject.getObject("compactPlaylistRenderer"));
                }
                if (jsonObject.has("lockupViewModel")) {
                    JsonObject object = jsonObject.getObject("lockupViewModel");
                    String string = object.getString("contentType");
                    if ("LOCKUP_CONTENT_TYPE_PLAYLIST".equals(string) || "LOCKUP_CONTENT_TYPE_PODCAST".equals(string)) {
                        return new YoutubeMixOrPlaylistLockupInfoItemExtractor(object);
                    }
                    if ("LOCKUP_CONTENT_TYPE_VIDEO".equals(string)) {
                        return new YoutubeStreamInfoItemLockupExtractor(object, timeAgoParser);
                    }
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
