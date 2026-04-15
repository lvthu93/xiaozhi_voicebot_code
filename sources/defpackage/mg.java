package defpackage;

import com.grack.nanojson.JsonObject;
import j$.time.format.DateTimeFormatter;
import j$.util.Objects;
import j$.util.function.Predicate$CC;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.InfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemLockupExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionItem;
import org.schabi.newpipe.extractor.utils.Utils;

/* renamed from: mg  reason: default package */
public final /* synthetic */ class mg implements Predicate {
    public final /* synthetic */ int a;

    public /* synthetic */ mg(int i) {
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
            default:
                return Predicate$CC.$default$or(this, predicate);
        }
    }

    public final boolean test(Object obj) {
        switch (this.a) {
            case 0:
                return ((String) obj).contains("Age-restricted");
            case 1:
                return Objects.nonNull((InfoItemExtractor) obj);
            case 2:
                return "engagement-panel-macro-markers-description-chapters".equals(((JsonObject) obj).getObject("engagementPanelSectionListRenderer").getString("panelIdentifier"));
            case 3:
                return !Utils.isNullOrEmpty((JsonObject) obj);
            case 4:
                return !Utils.isNullOrEmpty((JsonObject) obj);
            case 5:
                return Objects.nonNull((z3) obj);
            case 6:
                Pattern pattern = YoutubeStreamInfoItemExtractor.e;
                return ((JsonObject) obj).has("thumbnailOverlayTimeStatusRenderer");
            case 7:
                Pattern pattern2 = YoutubeStreamInfoItemExtractor.e;
                return ((JsonObject) obj).has("thumbnailOverlayTimeStatusRenderer");
            case 8:
                DateTimeFormatter dateTimeFormatter = YoutubeStreamInfoItemLockupExtractor.h;
                return !Utils.isNullOrEmpty((String) obj);
            case 9:
                JsonObject jsonObject = (JsonObject) obj;
                DateTimeFormatter dateTimeFormatter2 = YoutubeStreamInfoItemLockupExtractor.h;
                if ("THUMBNAIL_OVERLAY_BADGE_STYLE_LIVE".equals(jsonObject.getString("badgeStyle"))) {
                    return true;
                }
                return jsonObject.getObject("icon").getArray("sources").streamAsJsonObjects().map(new fg(20)).anyMatch(new y5("LIVE", 13));
            case 10:
                if (((String[]) obj).length >= 3) {
                    return true;
                }
                return false;
            case 11:
                return Objects.nonNull((SubscriptionItem) obj);
            case 12:
                return !Utils.isBlank((String) obj);
            case 13:
                return ((JsonObject) obj).has("appendContinuationItemsAction");
            case 14:
                return !((JsonObject) obj).has("title");
            case 15:
                return ((JsonObject) obj).has("richItemRenderer");
            case 16:
                return ((JsonObject) obj).getBoolean("selected");
            case 17:
                return ((JsonObject) obj).has("content");
            case 18:
                if (((String[]) obj).length > 1) {
                    return true;
                }
                return false;
            case 19:
                return Objects.nonNull((String) obj);
            default:
                String str = (String) obj;
                if (Utils.isNullOrEmpty(str) || str.equals("null")) {
                    return false;
                }
                return true;
        }
    }
}
