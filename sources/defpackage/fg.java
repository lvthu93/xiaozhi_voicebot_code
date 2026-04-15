package defpackage;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import j$.time.LocalDate;
import j$.time.format.DateTimeFormatter;
import j$.util.Collection$EL;
import j$.util.function.Function$CC;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemLockupExtractor;

/* renamed from: fg  reason: default package */
public final /* synthetic */ class fg implements Function {
    public final /* synthetic */ int a;

    public /* synthetic */ fg(int i) {
        this.a = i;
    }

    public final /* synthetic */ Function andThen(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$andThen(this, function);
            case 1:
                return Function$CC.$default$andThen(this, function);
            case 2:
                return Function$CC.$default$andThen(this, function);
            case 3:
                return Function$CC.$default$andThen(this, function);
            case 4:
                return Function$CC.$default$andThen(this, function);
            case 5:
                return Function$CC.$default$andThen(this, function);
            case 6:
                return Function$CC.$default$andThen(this, function);
            case 7:
                return Function$CC.$default$andThen(this, function);
            case 8:
                return Function$CC.$default$andThen(this, function);
            case 9:
                return Function$CC.$default$andThen(this, function);
            case 10:
                return Function$CC.$default$andThen(this, function);
            case 11:
                return Function$CC.$default$andThen(this, function);
            case 12:
                return Function$CC.$default$andThen(this, function);
            case 13:
                return Function$CC.$default$andThen(this, function);
            case 14:
                return Function$CC.$default$andThen(this, function);
            case 15:
                return Function$CC.$default$andThen(this, function);
            case 16:
                return Function$CC.$default$andThen(this, function);
            case 17:
                return Function$CC.$default$andThen(this, function);
            case 18:
                return Function$CC.$default$andThen(this, function);
            case 19:
                return Function$CC.$default$andThen(this, function);
            case 20:
                return Function$CC.$default$andThen(this, function);
            case 21:
                return Function$CC.$default$andThen(this, function);
            case 22:
                return Function$CC.$default$andThen(this, function);
            case 23:
                return Function$CC.$default$andThen(this, function);
            case 24:
                return Function$CC.$default$andThen(this, function);
            case 25:
                return Function$CC.$default$andThen(this, function);
            case 26:
                return Function$CC.$default$andThen(this, function);
            case 27:
                return Function$CC.$default$andThen(this, function);
            case 28:
                return Function$CC.$default$andThen(this, function);
            default:
                return Function$CC.$default$andThen(this, function);
        }
    }

    public final Object apply(Object obj) {
        Class<JsonObject> cls = JsonObject.class;
        switch (this.a) {
            case 0:
                return ((JsonObject) obj).getObject("playlistSidebarPrimaryInfoRenderer");
            case 1:
                return ((JsonObject) obj).getObject("playlistSidebarSecondaryInfoRenderer").getObject("videoOwner").getObject("videoOwnerRenderer");
            case 2:
                return new DateWrapper(((LocalDate) obj).atStartOfDay(), true);
            case 3:
                return y2.ab(cls, 18, y2.aa(cls, 18, Collection$EL.stream(((JsonObject) obj).getObject("metadataRowRenderer").getArray("contents"))));
            case 4:
                return y2.ab(cls, 19, y2.aa(cls, 19, Collection$EL.stream(((JsonObject) obj).getArray("runs"))));
            case 5:
                return ((JsonObject) obj).getString("text", "");
            case 6:
                return ((JsonObject) obj).getObject("engagementPanelSectionListRenderer").getObject("content").getObject("macroMarkersListRenderer").getArray("contents");
            case 7:
                return ((JsonObject) obj).getObject("macroMarkersListItemRenderer");
            case 8:
                return ((JsonObject) obj).getObject("segmentedLikeDislikeButtonRenderer").getObject("likeButton").getObject("toggleButtonRenderer");
            case 9:
                return ((JsonObject) obj).getObject("segmentedLikeDislikeButtonViewModel").getObject("likeButtonViewModel").getObject("likeButtonViewModel").getObject("toggleButtonViewModel").getObject("toggleButtonViewModel").getObject("defaultButtonViewModel").getObject("buttonViewModel");
            case 10:
                Pattern pattern = YoutubeStreamInfoItemExtractor.e;
                return ((JsonObject) obj).getObject("thumbnailOverlayTimeStatusRenderer");
            case 11:
                Pattern pattern2 = YoutubeStreamInfoItemExtractor.e;
                return ((JsonObject) obj).getObject("metadataBadgeRenderer").getString("style");
            case 12:
                DateTimeFormatter dateTimeFormatter = YoutubeStreamInfoItemLockupExtractor.h;
                return ((JsonObject) obj).getObject("text").getArray("attachmentRuns");
            case 13:
                return Boolean.valueOf(YoutubeParsingHelper.hasArtistOrVerifiedIconBadgeAttachment((JsonArray) obj));
            case 14:
                DateTimeFormatter dateTimeFormatter2 = YoutubeStreamInfoItemLockupExtractor.h;
                return ((JsonObject) obj).getObject("thumbnailOverlayBadgeViewModel").getArray("thumbnailBadges").streamAsJsonObjects();
            case 15:
                DateTimeFormatter dateTimeFormatter3 = YoutubeStreamInfoItemLockupExtractor.h;
                return ((JsonObject) obj).getObject("thumbnailBadgeViewModel").getString("text");
            case 16:
                DateTimeFormatter dateTimeFormatter4 = YoutubeStreamInfoItemLockupExtractor.h;
                return Boolean.valueOf(((String) obj).contains("Premieres "));
            case 17:
                DateTimeFormatter dateTimeFormatter5 = YoutubeStreamInfoItemLockupExtractor.h;
                return ((String) obj).replace("Premieres ", "");
            case 18:
                DateTimeFormatter dateTimeFormatter6 = YoutubeStreamInfoItemLockupExtractor.h;
                return ((JsonObject) obj).getObject("thumbnailOverlayBadgeViewModel").getArray("thumbnailBadges").streamAsJsonObjects();
            case 19:
                DateTimeFormatter dateTimeFormatter7 = YoutubeStreamInfoItemLockupExtractor.h;
                return ((JsonObject) obj).getObject("thumbnailBadgeViewModel");
            case 20:
                DateTimeFormatter dateTimeFormatter8 = YoutubeStreamInfoItemLockupExtractor.h;
                return ((JsonObject) obj).getObject("clientResource").getString("imageName");
            case 21:
                return ((JsonObject) obj).getObject("listItemViewModel");
            case 22:
                return ((String) obj).split(",");
            case 23:
                return ((JsonArray) obj).getString(0);
            case 24:
                return ((JsonObject) obj).getObject("appendContinuationItemsAction");
            case 25:
                return ((JsonObject) obj).getObject("shelfRenderer");
            case 26:
                return Collection$EL.stream(((JsonObject) obj).getObject("content").getObject("expandedShelfContentsRenderer").getArray("items"));
            case 27:
                return ((JsonObject) obj).getObject("videoRenderer");
            case 28:
                return ((JsonObject) obj).getObject("richItemRenderer").getObject("content").getObject("videoRenderer");
            default:
                return Collection$EL.stream(((JsonObject) obj).getObject("itemSectionRenderer").getArray("contents"));
        }
    }

    public final /* synthetic */ Function compose(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$compose(this, function);
            case 1:
                return Function$CC.$default$compose(this, function);
            case 2:
                return Function$CC.$default$compose(this, function);
            case 3:
                return Function$CC.$default$compose(this, function);
            case 4:
                return Function$CC.$default$compose(this, function);
            case 5:
                return Function$CC.$default$compose(this, function);
            case 6:
                return Function$CC.$default$compose(this, function);
            case 7:
                return Function$CC.$default$compose(this, function);
            case 8:
                return Function$CC.$default$compose(this, function);
            case 9:
                return Function$CC.$default$compose(this, function);
            case 10:
                return Function$CC.$default$compose(this, function);
            case 11:
                return Function$CC.$default$compose(this, function);
            case 12:
                return Function$CC.$default$compose(this, function);
            case 13:
                return Function$CC.$default$compose(this, function);
            case 14:
                return Function$CC.$default$compose(this, function);
            case 15:
                return Function$CC.$default$compose(this, function);
            case 16:
                return Function$CC.$default$compose(this, function);
            case 17:
                return Function$CC.$default$compose(this, function);
            case 18:
                return Function$CC.$default$compose(this, function);
            case 19:
                return Function$CC.$default$compose(this, function);
            case 20:
                return Function$CC.$default$compose(this, function);
            case 21:
                return Function$CC.$default$compose(this, function);
            case 22:
                return Function$CC.$default$compose(this, function);
            case 23:
                return Function$CC.$default$compose(this, function);
            case 24:
                return Function$CC.$default$compose(this, function);
            case 25:
                return Function$CC.$default$compose(this, function);
            case 26:
                return Function$CC.$default$compose(this, function);
            case 27:
                return Function$CC.$default$compose(this, function);
            case 28:
                return Function$CC.$default$compose(this, function);
            default:
                return Function$CC.$default$compose(this, function);
        }
    }
}
