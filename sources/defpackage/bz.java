package defpackage;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import j$.util.Collection$EL;
import j$.util.Objects;
import j$.util.function.Predicate$CC;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.jsoup.nodes.Element;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCLiveStreamExtractor;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCRecentKioskExtractor;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.utils.ImageSuffix;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

/* renamed from: bz  reason: default package */
public final /* synthetic */ class bz implements Predicate {
    public final /* synthetic */ int a;

    public /* synthetic */ bz(int i) {
        this.a = i;
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
            case 13:
                return Predicate$CC.$default$and(this, predicate);
            case 14:
                return Predicate$CC.$default$and(this, predicate);
            case 15:
                return Predicate$CC.$default$and(this, predicate);
            case 16:
                return Predicate$CC.$default$and(this, predicate);
            case 17:
                return Predicate$CC.$default$and(this, predicate);
            case 18:
                return Predicate$CC.$default$and(this, predicate);
            case 19:
                return Predicate$CC.$default$and(this, predicate);
            case 20:
                return Predicate$CC.$default$and(this, predicate);
            case 21:
                return Predicate$CC.$default$and(this, predicate);
            case 22:
                return Predicate$CC.$default$and(this, predicate);
            case 23:
                return Predicate$CC.$default$and(this, predicate);
            case 24:
                return Predicate$CC.$default$and(this, predicate);
            case 25:
                return Predicate$CC.$default$and(this, predicate);
            case 26:
                return Predicate$CC.$default$and(this, predicate);
            case 27:
                return Predicate$CC.$default$and(this, predicate);
            case 28:
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
            case 13:
                return Predicate$CC.$default$negate(this);
            case 14:
                return Predicate$CC.$default$negate(this);
            case 15:
                return Predicate$CC.$default$negate(this);
            case 16:
                return Predicate$CC.$default$negate(this);
            case 17:
                return Predicate$CC.$default$negate(this);
            case 18:
                return Predicate$CC.$default$negate(this);
            case 19:
                return Predicate$CC.$default$negate(this);
            case 20:
                return Predicate$CC.$default$negate(this);
            case 21:
                return Predicate$CC.$default$negate(this);
            case 22:
                return Predicate$CC.$default$negate(this);
            case 23:
                return Predicate$CC.$default$negate(this);
            case 24:
                return Predicate$CC.$default$negate(this);
            case 25:
                return Predicate$CC.$default$negate(this);
            case 26:
                return Predicate$CC.$default$negate(this);
            case 27:
                return Predicate$CC.$default$negate(this);
            case 28:
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
            case 13:
                return Predicate$CC.$default$or(this, predicate);
            case 14:
                return Predicate$CC.$default$or(this, predicate);
            case 15:
                return Predicate$CC.$default$or(this, predicate);
            case 16:
                return Predicate$CC.$default$or(this, predicate);
            case 17:
                return Predicate$CC.$default$or(this, predicate);
            case 18:
                return Predicate$CC.$default$or(this, predicate);
            case 19:
                return Predicate$CC.$default$or(this, predicate);
            case 20:
                return Predicate$CC.$default$or(this, predicate);
            case 21:
                return Predicate$CC.$default$or(this, predicate);
            case 22:
                return Predicate$CC.$default$or(this, predicate);
            case 23:
                return Predicate$CC.$default$or(this, predicate);
            case 24:
                return Predicate$CC.$default$or(this, predicate);
            case 25:
                return Predicate$CC.$default$or(this, predicate);
            case 26:
                return Predicate$CC.$default$or(this, predicate);
            case 27:
                return Predicate$CC.$default$or(this, predicate);
            case 28:
                return Predicate$CC.$default$or(this, predicate);
            default:
                return Predicate$CC.$default$or(this, predicate);
        }
    }

    public final boolean test(Object obj) {
        switch (this.a) {
            case 0:
                return Objects.nonNull((Element) obj);
            case 1:
                return !((String) obj).isEmpty();
            case 2:
                List<ImageSuffix> list = BandcampExtractorHelper.a;
                return !Utils.isNullOrEmpty((String) obj);
            case 3:
                return !"dash".equals(((MediaCCCLiveStreamExtractor.a) obj).b);
            case 4:
                return ((Map.Entry) obj).getValue() instanceof JsonObject;
            case 5:
                if (((MediaCCCRecentKioskExtractor) obj).getDuration() > 0) {
                    return true;
                }
                return false;
            case 6:
                return !Utils.isNullOrEmpty(((JsonObject) obj).getString("path"));
            case 7:
                return ((JsonObject) obj).has("topicChannelDetailsRenderer");
            case 8:
                return ((JsonObject) obj).has("topicChannelDetailsRenderer");
            case 9:
                return ((JsonObject) obj).has("channelAgeGateRenderer");
            case 10:
                if (!(obj instanceof JsonObject) || !((JsonObject) obj).has("singleActionEmergencySupportRenderer")) {
                    return false;
                }
                return true;
            case 11:
                String str = YoutubeParsingHelper.a;
                return !Utils.isNullOrEmpty(((JsonObject) obj).getString("url"));
            case 12:
                String str2 = YoutubeParsingHelper.a;
                Class<JsonObject> cls = JsonObject.class;
                return y2.z(cls, 24, y2.s(cls, 24, Collection$EL.stream(((JsonObject) obj).getObject("element").getObject("type").getObject("imageType").getObject("image").getArray("sources")))).anyMatch(new bz(14));
            case 13:
                String str3 = YoutubeParsingHelper.a;
                return !Utils.isNullOrEmpty((String) obj);
            case 14:
                String str4 = YoutubeParsingHelper.a;
                String string = ((JsonObject) obj).getObject("clientResource").getString("imageName");
                if ("CHECK_CIRCLE_FILLED".equals(string) || "AUDIO_BADGE".equals(string) || "MUSIC_FILLED".equals(string)) {
                    return true;
                }
                return false;
            case 15:
                return ((JsonObject) obj).has("tabRenderer");
            case 16:
                return ((JsonObject) obj).has("appendContinuationItemsAction");
            case 17:
                return ((JsonObject) obj).has("tabRenderer");
            case 18:
                JsonArray array = ((JsonObject) obj).getObject("content").getObject("sectionListRenderer").getArray("contents").getObject(0).getObject("itemSectionRenderer").getArray("contents");
                if (array.size() != 1 || !array.getObject(0).has("messageRenderer")) {
                    return true;
                }
                return false;
            case 19:
                return Objects.nonNull((JsonObject) obj);
            case 20:
                try {
                    return "comments-section".equals(JsonUtils.getString((JsonObject) obj, "itemSectionRenderer.targetId"));
                } catch (ParsingException unused) {
                    return false;
                }
            case 21:
                return ((JsonObject) obj).has("thumbnailOverlayBadgeViewModel");
            case 22:
                return ((JsonObject) obj).has("thumbnailBadgeViewModel");
            case 23:
                return Objects.nonNull((JsonObject) obj);
            case 24:
                return ((JsonObject) obj).has("showingResultsForRenderer");
            case 25:
                return !((JsonObject) obj).isEmpty();
            case 26:
                return Objects.nonNull((JsonObject) obj);
            case 27:
                return ((JsonObject) obj).has("playlistSidebarPrimaryInfoRenderer");
            case 28:
                return ((JsonObject) obj).getObject("playlistSidebarSecondaryInfoRenderer").getObject("videoOwner").has("videoOwnerRenderer");
            default:
                return ((JsonObject) obj).has("continuationCommand");
        }
    }
}
