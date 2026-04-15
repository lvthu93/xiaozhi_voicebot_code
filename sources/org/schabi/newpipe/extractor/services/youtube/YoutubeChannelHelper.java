package org.schabi.newpipe.extractor.services.youtube;

import androidx.core.app.NotificationCompat;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;
import j$.util.Collection$EL;
import j$.util.Optional;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.schabi.newpipe.extractor.exceptions.ContentNotAvailableException;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.utils.Utils;

public final class YoutubeChannelHelper {

    public static final class ChannelHeader implements Serializable {
        public final JsonObject c;
        public final HeaderType f;

        public enum HeaderType {
            C4_TABBED,
            INTERACTIVE_TABBED,
            CAROUSEL,
            PAGE
        }

        public ChannelHeader(JsonObject jsonObject, HeaderType headerType) {
            this.c = jsonObject;
            this.f = headerType;
        }
    }

    public static final class ChannelResponseData {
        public final JsonObject a;
        public final String b;

        public ChannelResponseData(JsonObject jsonObject, String str) {
            this.a = jsonObject;
            this.b = str;
        }
    }

    public static void a(JsonObject jsonObject) throws ContentNotAvailableException {
        if (!Utils.isNullOrEmpty(jsonObject.getObject(MqttServiceConstants.TRACE_ERROR))) {
            JsonObject object = jsonObject.getObject(MqttServiceConstants.TRACE_ERROR);
            if (object.getInt("code") == 404) {
                throw new ContentNotAvailableException("This channel doesn't exist.");
            }
            throw new ContentNotAvailableException("Got error:\"" + object.getString(NotificationCompat.CATEGORY_STATUS) + "\": " + object.getString("message"));
        }
    }

    public static JsonObject getChannelAgeGateRenderer(JsonObject jsonObject) {
        Class<JsonObject> cls = JsonObject.class;
        return (JsonObject) y2.z(cls, 16, y2.s(cls, 16, Collection$EL.stream(jsonObject.getObject("contents").getObject("twoColumnBrowseResultsRenderer").getArray("tabs")))).flatMap(new p8(7)).filter(new bz(9)).map(new p8(8)).findFirst().orElse(null);
    }

    public static ChannelHeader getChannelHeader(JsonObject jsonObject) {
        JsonObject object = jsonObject.getObject("header");
        if (object.has("c4TabbedHeaderRenderer")) {
            return (ChannelHeader) Optional.of(object.getObject("c4TabbedHeaderRenderer")).map(new p8(2)).orElse(null);
        }
        if (object.has("carouselHeaderRenderer")) {
            Class<JsonObject> cls = JsonObject.class;
            return (ChannelHeader) y2.z(cls, 14, y2.s(cls, 14, Collection$EL.stream(object.getObject("carouselHeaderRenderer").getArray("contents")))).filter(new bz(7)).findFirst().map(new p8(3)).map(new p8(4)).orElse(null);
        } else if (object.has("pageHeaderRenderer")) {
            return (ChannelHeader) Optional.of(object.getObject("pageHeaderRenderer")).map(new p8(5)).orElse(null);
        } else {
            if (object.has("interactiveTabbedHeaderRenderer")) {
                return (ChannelHeader) Optional.of(object.getObject("interactiveTabbedHeaderRenderer")).map(new p8(6)).orElse(null);
            }
            return null;
        }
    }

    public static String getChannelId(ChannelHeader channelHeader, JsonObject jsonObject, String str) throws ParsingException {
        if (channelHeader != null) {
            int ordinal = channelHeader.f.ordinal();
            JsonObject jsonObject2 = channelHeader.c;
            if (ordinal == 0) {
                String string = jsonObject2.getObject("header").getObject("c4TabbedHeaderRenderer").getString("channelId", "");
                if (!Utils.isNullOrEmpty(string)) {
                    return string;
                }
                String string2 = jsonObject2.getObject("navigationEndpoint").getObject("browseEndpoint").getString("browseId");
                if (!Utils.isNullOrEmpty(string2)) {
                    return string2;
                }
            } else if (ordinal == 2) {
                Class<JsonObject> cls = JsonObject.class;
                String string3 = ((JsonObject) y2.z(cls, 15, y2.s(cls, 15, Collection$EL.stream(jsonObject2.getObject("header").getObject("carouselHeaderRenderer").getArray("contents")))).filter(new bz(8)).findFirst().orElse(new JsonObject())).getObject("topicChannelDetailsRenderer").getObject("navigationEndpoint").getObject("browseEndpoint").getString("browseId");
                if (!Utils.isNullOrEmpty(string3)) {
                    return string3;
                }
            }
        }
        String string4 = jsonObject.getObject("metadata").getObject("channelMetadataRenderer").getString("externalChannelId");
        if (!Utils.isNullOrEmpty(string4)) {
            return string4;
        }
        if (!Utils.isNullOrEmpty(str)) {
            return str;
        }
        throw new ParsingException("Could not get channel ID");
    }

    public static String getChannelName(ChannelHeader channelHeader, JsonObject jsonObject, JsonObject jsonObject2) throws ParsingException {
        if (jsonObject != null) {
            String string = jsonObject.getString("channelTitle");
            if (!Utils.isNullOrEmpty(string)) {
                return string;
            }
            throw new ParsingException("Could not get channel name");
        }
        String string2 = jsonObject2.getObject("metadata").getObject("channelMetadataRenderer").getString("title");
        if (!Utils.isNullOrEmpty(string2)) {
            return string2;
        }
        return (String) Optional.ofNullable(channelHeader).map(new p8(9)).or(new rf(0, jsonObject2)).orElseThrow(new cb(4));
    }

    public static ChannelResponseData getChannelResponse(String str, String str2, Localization localization, ContentCountry contentCountry) throws ExtractionException, IOException {
        JsonObject jsonObject;
        int i = 0;
        while (true) {
            if (i >= 3) {
                jsonObject = null;
                break;
            }
            jsonObject = YoutubeParsingHelper.getJsonPostResponse("browse", JsonWriter.string(YoutubeParsingHelper.prepareDesktopJsonBuilder(localization, contentCountry).value("browseId", str).value("params", str2).done()).getBytes(StandardCharsets.UTF_8), localization);
            a(jsonObject);
            JsonObject object = jsonObject.getArray("onResponseReceivedActions").getObject(0).getObject("navigateAction").getObject("endpoint");
            String string = object.getObject("commandMetadata").getObject("webCommandMetadata").getString("webPageType");
            String string2 = object.getObject("browseEndpoint").getString("browseId", "");
            if ((!"WEB_PAGE_TYPE_BROWSE".equalsIgnoreCase(string) && !"WEB_PAGE_TYPE_CHANNEL".equalsIgnoreCase(string)) || string2.isEmpty()) {
                break;
            } else if (string2.startsWith("UC")) {
                i++;
                str = string2;
            } else {
                throw new ExtractionException("Redirected id is not pointing to a channel");
            }
        }
        if (jsonObject != null) {
            YoutubeParsingHelper.defaultAlertsCheck(jsonObject);
            return new ChannelResponseData(jsonObject, str);
        }
        throw new ExtractionException("Got no channel response after 3 redirects");
    }

    public static boolean isChannelVerified(ChannelHeader channelHeader) {
        int ordinal = channelHeader.f.ordinal();
        JsonObject jsonObject = channelHeader.c;
        if (ordinal == 1) {
            return jsonObject.has("autoGenerated");
        }
        if (ordinal == 2) {
            return true;
        }
        if (ordinal != 3) {
            return YoutubeParsingHelper.isVerified(jsonObject.getArray("badges"));
        }
        JsonObject object = jsonObject.getObject("content").getObject("pageHeaderViewModel");
        boolean hasArtistOrVerifiedIconBadgeAttachment = YoutubeParsingHelper.hasArtistOrVerifiedIconBadgeAttachment(object.getObject("title").getObject("dynamicTextViewModel").getObject("text").getArray("attachmentRuns"));
        if (hasArtistOrVerifiedIconBadgeAttachment || !object.getObject("image").has("contentPreviewImageViewModel")) {
            return hasArtistOrVerifiedIconBadgeAttachment;
        }
        return true;
    }

    public static String resolveChannelId(String str) throws ExtractionException, IOException {
        String[] split = str.split(MqttTopic.TOPIC_LEVEL_SEPARATOR);
        int i = 0;
        if (split[0].startsWith("UC")) {
            return split[0];
        }
        if (!split[0].equals("channel")) {
            String concat = "https://www.youtube.com/".concat(str);
            JsonObject jsonObject = new JsonObject();
            String str2 = "";
            while (concat != null && i < 3) {
                Localization localization = Localization.g;
                JsonObject jsonPostResponse = YoutubeParsingHelper.getJsonPostResponse("navigation/resolve_url", JsonWriter.string(YoutubeParsingHelper.prepareDesktopJsonBuilder(localization, ContentCountry.f).value("url", concat).done()).getBytes(StandardCharsets.UTF_8), localization);
                a(jsonPostResponse);
                jsonObject = jsonPostResponse.getObject("endpoint");
                str2 = jsonObject.getObject("commandMetadata").getObject("webCommandMetadata").getString("webPageType");
                if ("WEB_PAGE_TYPE_UNKNOWN".equals(str2)) {
                    concat = jsonObject.getObject("urlEndpoint").getString("url");
                } else {
                    concat = null;
                }
                i++;
            }
            String string = jsonObject.getObject("browseEndpoint").getString("browseId", "");
            if (("WEB_PAGE_TYPE_BROWSE".equalsIgnoreCase(str2) || "WEB_PAGE_TYPE_CHANNEL".equalsIgnoreCase(str2)) && !string.isEmpty()) {
                if (string.startsWith("UC")) {
                    return string;
                }
                throw new ExtractionException("Redirected id is not pointing to a channel");
            } else if (split.length < 2) {
                throw new ExtractionException("Failed to resolve channelId for ".concat(str));
            }
        }
        return split[1];
    }
}
