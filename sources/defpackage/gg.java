package defpackage;

import com.grack.nanojson.JsonObject;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeReelInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemLockupExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;

/* renamed from: gg  reason: default package */
public final /* synthetic */ class gg implements Consumer {
    public final /* synthetic */ int a;
    public final /* synthetic */ StreamInfoItemsCollector b;
    public final /* synthetic */ TimeAgoParser c;

    public /* synthetic */ gg(StreamInfoItemsCollector streamInfoItemsCollector, TimeAgoParser timeAgoParser, int i) {
        this.a = i;
        this.b = streamInfoItemsCollector;
        this.c = timeAgoParser;
    }

    public final void accept(Object obj) {
        int i = this.a;
        TimeAgoParser timeAgoParser = this.c;
        StreamInfoItemsCollector streamInfoItemsCollector = this.b;
        switch (i) {
            case 0:
                JsonObject jsonObject = (JsonObject) obj;
                if (jsonObject.has("playlistVideoRenderer")) {
                    streamInfoItemsCollector.commit((StreamInfoItemExtractor) new YoutubeStreamInfoItemExtractor(jsonObject.getObject("playlistVideoRenderer"), timeAgoParser));
                    return;
                } else if (jsonObject.has("richItemRenderer")) {
                    JsonObject object = jsonObject.getObject("richItemRenderer");
                    if (object.has("content")) {
                        JsonObject object2 = object.getObject("content");
                        if (object2.has("reelItemRenderer")) {
                            streamInfoItemsCollector.commit((StreamInfoItemExtractor) new YoutubeReelInfoItemExtractor(object2.getObject("reelItemRenderer")));
                            return;
                        }
                        return;
                    }
                    return;
                } else {
                    return;
                }
            case 1:
                JsonObject jsonObject2 = (JsonObject) obj;
                if (jsonObject2.has("richItemRenderer")) {
                    JsonObject object3 = jsonObject2.getObject("richItemRenderer").getObject("content");
                    if (object3.has("videoRenderer")) {
                        streamInfoItemsCollector.commit((StreamInfoItemExtractor) new YoutubeStreamInfoItemExtractor(object3.getObject("videoRenderer"), timeAgoParser));
                        return;
                    }
                    return;
                } else if (jsonObject2.has("gridVideoRenderer")) {
                    streamInfoItemsCollector.commit((StreamInfoItemExtractor) new YoutubeStreamInfoItemExtractor(jsonObject2.getObject("gridVideoRenderer"), timeAgoParser));
                    return;
                } else if (jsonObject2.has("lockupViewModel")) {
                    JsonObject object4 = jsonObject2.getObject("lockupViewModel");
                    if ("LOCKUP_CONTENT_TYPE_VIDEO".equals(object4.getString("contentType"))) {
                        streamInfoItemsCollector.commit((StreamInfoItemExtractor) new YoutubeStreamInfoItemLockupExtractor(object4, timeAgoParser));
                        return;
                    }
                    return;
                } else {
                    return;
                }
            case 2:
                streamInfoItemsCollector.commit((StreamInfoItemExtractor) new YoutubeStreamInfoItemExtractor((JsonObject) obj, timeAgoParser));
                return;
            default:
                streamInfoItemsCollector.commit((StreamInfoItemExtractor) new YoutubeStreamInfoItemExtractor((JsonObject) obj, timeAgoParser));
                return;
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
            case 0:
                return Consumer$CC.$default$andThen(this, consumer);
            case 1:
                return Consumer$CC.$default$andThen(this, consumer);
            case 2:
                return Consumer$CC.$default$andThen(this, consumer);
            default:
                return Consumer$CC.$default$andThen(this, consumer);
        }
    }
}
