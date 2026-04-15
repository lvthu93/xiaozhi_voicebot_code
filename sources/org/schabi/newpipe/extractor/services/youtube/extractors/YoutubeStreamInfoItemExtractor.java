package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonObject;
import j$.time.Instant;
import j$.time.LocalDateTime;
import j$.time.ZoneId;
import j$.time.format.DateTimeFormatter;
import j$.util.Collection$EL;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Parser;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeStreamInfoItemExtractor implements StreamInfoItemExtractor {
    public static final Pattern e = Pattern.compile("([\\d,]+) views$");
    public final JsonObject a;
    public final TimeAgoParser b;
    public StreamType c;
    public Boolean d;

    public YoutubeStreamInfoItemExtractor(JsonObject jsonObject, TimeAgoParser timeAgoParser) {
        this.a = jsonObject;
        this.b = timeAgoParser;
    }

    public static long c(String str, boolean z) throws NumberFormatException, ParsingException {
        if (str.toLowerCase().contains("no views")) {
            return 0;
        }
        if (str.toLowerCase().contains("recommended")) {
            return -1;
        }
        if (z) {
            return Utils.mixedNumberWordToLong(str);
        }
        return Long.parseLong(Utils.removeNonDigitCharacters(str));
    }

    public final Instant a() throws ParsingException {
        String string = this.a.getObject("upcomingEventData").getString("startTime");
        try {
            return Instant.ofEpochSecond(Long.parseLong(string));
        } catch (Exception e2) {
            throw new ParsingException(y2.j("Could not parse date from premiere: \"", string, "\""), e2);
        }
    }

    public final long b() throws NumberFormatException, Parser.RegexException {
        String string = this.a.getObject("title").getObject("accessibility").getObject("accessibilityData").getString("label", "");
        if (string.toLowerCase().endsWith("no views")) {
            return 0;
        }
        return Long.parseLong(Utils.removeNonDigitCharacters(Parser.matchGroup1(e, string)));
    }

    public final boolean d() {
        if (this.d == null) {
            this.d = Boolean.valueOf(this.a.has("upcomingEventData"));
        }
        return this.d.booleanValue();
    }

    public final boolean e() {
        Iterator it = this.a.getArray("badges").iterator();
        while (it.hasNext()) {
            if (((JsonObject) it.next()).getObject("metadataBadgeRenderer").getString("label", "").equals("Premium")) {
                return true;
            }
        }
        return false;
    }

    public ContentAvailability getContentAvailability() throws ParsingException {
        if (d()) {
            return ContentAvailability.UPCOMING;
        }
        Class<JsonObject> cls = JsonObject.class;
        if (y2.ab(cls, 24, y2.aa(cls, 24, Collection$EL.stream(this.a.getArray("badges")))).map(new fg(11)).anyMatch(new y5("BADGE_STYLE_TYPE_MEMBERS_ONLY", 12))) {
            return ContentAvailability.MEMBERSHIP;
        }
        if (e()) {
            return ContentAvailability.PAID;
        }
        return ContentAvailability.AVAILABLE;
    }

    public long getDuration() throws ParsingException {
        if (getStreamType() == StreamType.LIVE_STREAM) {
            return -1;
        }
        JsonObject jsonObject = this.a;
        String textFromObject = YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("lengthText"));
        if (Utils.isNullOrEmpty(textFromObject)) {
            textFromObject = jsonObject.getString("lengthSeconds");
            if (Utils.isNullOrEmpty(textFromObject)) {
                Class<JsonObject> cls = JsonObject.class;
                JsonObject jsonObject2 = (JsonObject) y2.ab(cls, 22, y2.aa(cls, 22, Collection$EL.stream(jsonObject.getArray("thumbnailOverlays")))).filter(new mg(6)).findFirst().orElse(null);
                if (jsonObject2 != null) {
                    textFromObject = YoutubeParsingHelper.getTextFromObject(jsonObject2.getObject("thumbnailOverlayTimeStatusRenderer").getObject("text"));
                }
            }
            if (Utils.isNullOrEmpty(textFromObject)) {
                if (d()) {
                    return -1;
                }
                throw new ParsingException("Could not get duration");
            }
        }
        return (long) YoutubeParsingHelper.parseDurationString(textFromObject);
    }

    public String getName() throws ParsingException {
        String textFromObject = YoutubeParsingHelper.getTextFromObject(this.a.getObject("title"));
        if (!Utils.isNullOrEmpty(textFromObject)) {
            return textFromObject;
        }
        throw new ParsingException("Could not get name");
    }

    public String getShortDescription() throws ParsingException {
        JsonObject jsonObject = this.a;
        if (jsonObject.has("detailedMetadataSnippets")) {
            return YoutubeParsingHelper.getTextFromObject(jsonObject.getArray("detailedMetadataSnippets").getObject(0).getObject("snippetText"));
        }
        if (jsonObject.has("descriptionSnippet")) {
            return YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("descriptionSnippet"));
        }
        return null;
    }

    public StreamType getStreamType() {
        StreamType streamType;
        StreamType streamType2 = this.c;
        if (streamType2 != null) {
            return streamType2;
        }
        JsonObject jsonObject = this.a;
        Iterator it = jsonObject.getArray("badges").iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            streamType = StreamType.LIVE_STREAM;
            if (hasNext) {
                Object next = it.next();
                if (next instanceof JsonObject) {
                    JsonObject object = ((JsonObject) next).getObject("metadataBadgeRenderer");
                    if (object.getString("style", "").equals("BADGE_STYLE_TYPE_LIVE_NOW") || object.getString("label", "").equals("LIVE NOW")) {
                        this.c = streamType;
                    }
                }
            } else {
                Iterator it2 = jsonObject.getArray("thumbnailOverlays").iterator();
                while (it2.hasNext()) {
                    Object next2 = it2.next();
                    if ((next2 instanceof JsonObject) && ((JsonObject) next2).getObject("thumbnailOverlayTimeStatusRenderer").getString("style", "").equalsIgnoreCase("LIVE")) {
                        this.c = streamType;
                        return streamType;
                    }
                }
                StreamType streamType3 = StreamType.VIDEO_STREAM;
                this.c = streamType3;
                return streamType3;
            }
        }
        this.c = streamType;
        return streamType;
    }

    public String getTextualUploadDate() throws ParsingException {
        if (getStreamType() == StreamType.LIVE_STREAM) {
            return null;
        }
        if (d()) {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.ofInstant(a(), ZoneId.systemDefault()));
        }
        JsonObject jsonObject = this.a;
        String textFromObject = YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("publishedTimeText"));
        if (Utils.isNullOrEmpty(textFromObject) && jsonObject.has("videoInfo")) {
            textFromObject = jsonObject.getObject("videoInfo").getArray("runs").getObject(2).getString("text");
        }
        if (Utils.isNullOrEmpty(textFromObject)) {
            return null;
        }
        return textFromObject;
    }

    public List<Image> getThumbnails() throws ParsingException {
        return YoutubeParsingHelper.getThumbnailsFromInfoItem(this.a);
    }

    public DateWrapper getUploadDate() throws ParsingException {
        if (getStreamType() == StreamType.LIVE_STREAM) {
            return null;
        }
        if (d()) {
            return new DateWrapper(a());
        }
        String textualUploadDate = getTextualUploadDate();
        TimeAgoParser timeAgoParser = this.b;
        if (timeAgoParser == null || Utils.isNullOrEmpty(textualUploadDate)) {
            return null;
        }
        try {
            return timeAgoParser.parse(textualUploadDate);
        } catch (ParsingException e2) {
            throw new ParsingException("Could not get upload date", e2);
        }
    }

    public List<Image> getUploaderAvatars() throws ParsingException {
        JsonObject jsonObject = this.a;
        if (jsonObject.has("channelThumbnailSupportedRenderers")) {
            return YoutubeParsingHelper.getImagesFromThumbnailsArray(JsonUtils.getArray(jsonObject, "channelThumbnailSupportedRenderers.channelThumbnailWithLinkRenderer.thumbnail.thumbnails"));
        }
        if (jsonObject.has("channelThumbnail")) {
            return YoutubeParsingHelper.getImagesFromThumbnailsArray(JsonUtils.getArray(jsonObject, "channelThumbnail.thumbnails"));
        }
        return Collections.emptyList();
    }

    public String getUploaderName() throws ParsingException {
        JsonObject jsonObject = this.a;
        String textFromObject = YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("longBylineText"));
        if (Utils.isNullOrEmpty(textFromObject)) {
            textFromObject = YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("ownerText"));
            if (Utils.isNullOrEmpty(textFromObject)) {
                textFromObject = YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("shortBylineText"));
                if (Utils.isNullOrEmpty(textFromObject)) {
                    throw new ParsingException("Could not get uploader name");
                }
            }
        }
        return textFromObject;
    }

    public String getUploaderUrl() throws ParsingException {
        JsonObject jsonObject = this.a;
        String urlFromNavigationEndpoint = YoutubeParsingHelper.getUrlFromNavigationEndpoint(jsonObject.getObject("longBylineText").getArray("runs").getObject(0).getObject("navigationEndpoint"));
        if (Utils.isNullOrEmpty(urlFromNavigationEndpoint)) {
            urlFromNavigationEndpoint = YoutubeParsingHelper.getUrlFromNavigationEndpoint(jsonObject.getObject("ownerText").getArray("runs").getObject(0).getObject("navigationEndpoint"));
            if (Utils.isNullOrEmpty(urlFromNavigationEndpoint)) {
                urlFromNavigationEndpoint = YoutubeParsingHelper.getUrlFromNavigationEndpoint(jsonObject.getObject("shortBylineText").getArray("runs").getObject(0).getObject("navigationEndpoint"));
                if (Utils.isNullOrEmpty(urlFromNavigationEndpoint)) {
                    throw new ParsingException("Could not get uploader url");
                }
            }
        }
        return urlFromNavigationEndpoint;
    }

    public String getUrl() throws ParsingException {
        try {
            return YoutubeStreamLinkHandlerFactory.getInstance().getUrl(this.a.getString("videoId"));
        } catch (Exception e2) {
            throw new ParsingException("Could not get url", e2);
        }
    }

    public long getViewCount() throws ParsingException {
        if (e() || d()) {
            return -1;
        }
        JsonObject jsonObject = this.a;
        String textFromObject = YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("viewCountText"));
        if (!Utils.isNullOrEmpty(textFromObject)) {
            try {
                return c(textFromObject, false);
            } catch (Exception unused) {
            }
        }
        if (getStreamType() != StreamType.LIVE_STREAM) {
            try {
                return b();
            } catch (Exception unused2) {
            }
        }
        if (jsonObject.has("videoInfo")) {
            try {
                return c(jsonObject.getObject("videoInfo").getArray("runs").getObject(0).getString("text", ""), true);
            } catch (Exception unused3) {
            }
        }
        if (!jsonObject.has("shortViewCountText")) {
            return -1;
        }
        try {
            String textFromObject2 = YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("shortViewCountText"));
            if (!Utils.isNullOrEmpty(textFromObject2)) {
                return c(textFromObject2, true);
            }
            return -1;
        } catch (Exception unused4) {
            return -1;
        }
    }

    public boolean isAd() throws ParsingException {
        if (e() || getName().equals("[Private video]") || getName().equals("[Deleted video]")) {
            return true;
        }
        return false;
    }

    public boolean isShortFormContent() throws ParsingException {
        boolean z;
        Class<JsonObject> cls = JsonObject.class;
        JsonObject jsonObject = this.a;
        try {
            String string = jsonObject.getObject("navigationEndpoint").getObject("commandMetadata").getObject("webCommandMetadata").getString("webPageType");
            boolean z2 = true;
            if (Utils.isNullOrEmpty(string) || !string.equals("WEB_PAGE_TYPE_SHORTS")) {
                z = false;
            } else {
                z = true;
            }
            if (!z) {
                z = jsonObject.getObject("navigationEndpoint").has("reelWatchEndpoint");
            }
            if (z) {
                return z;
            }
            JsonObject jsonObject2 = (JsonObject) Collection$EL.stream(jsonObject.getArray("thumbnailOverlays")).filter(new uf(cls, 23)).map(new vf(cls, 23)).filter(new mg(7)).map(new fg(10)).findFirst().orElse(null);
            if (Utils.isNullOrEmpty(jsonObject2)) {
                return z;
            }
            if (!jsonObject2.getString("style", "").equalsIgnoreCase("SHORTS") && !jsonObject2.getObject("icon").getString("iconType", "").toLowerCase().contains("shorts")) {
                z2 = false;
            }
            return z2;
        } catch (Exception e2) {
            throw new ParsingException("Could not determine if this is short-form content", e2);
        }
    }

    public boolean isUploaderVerified() throws ParsingException {
        return YoutubeParsingHelper.isVerified(this.a.getArray("ownerBadges"));
    }
}
