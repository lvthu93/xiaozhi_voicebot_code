package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import j$.time.LocalDateTime;
import j$.time.ZoneOffset;
import j$.time.format.DateTimeFormatter;
import j$.time.format.DateTimeParseException;
import j$.util.Optional;
import j$.util.stream.Collectors;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeChannelLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeStreamInfoItemLockupExtractor implements StreamInfoItemExtractor {
    public static final DateTimeFormatter h = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
    public final JsonObject a;
    public final TimeAgoParser b;
    public StreamType c;
    public String d;
    public Optional<String> e;
    public a f;
    public JsonArray g;

    public static abstract class a {
        public final JsonObject a;

        public a(JsonObject jsonObject) {
            this.a = jsonObject;
        }

        public abstract JsonObject forAvatarExtraction();

        public abstract JsonObject forUploaderUrlExtraction();
    }

    public static class b extends a {
        public b(JsonObject jsonObject) {
            super(jsonObject);
        }

        public JsonObject forAvatarExtraction() {
            return this.a.getArray("avatars").getObject(0);
        }

        public JsonObject forUploaderUrlExtraction() {
            return (JsonObject) this.a.getObject("rendererContext").getObject("commandContext").getObject("onTap").getObject("innertubeCommand").getObject("showDialogCommand").getObject("panelLoadingStrategy").getObject("inlineContent").getObject("dialogViewModel").getObject("customContent").getObject("listViewModel").getArray("listItems").streamAsJsonObjects().map(new fg(21)).findFirst().orElse(null);
        }
    }

    public static class c extends a {
        public c(JsonObject jsonObject) {
            super(jsonObject);
        }

        public JsonObject forAvatarExtraction() {
            return this.a.getObject("avatar");
        }

        public JsonObject forUploaderUrlExtraction() {
            return this.a;
        }
    }

    public YoutubeStreamInfoItemLockupExtractor(JsonObject jsonObject, TimeAgoParser timeAgoParser) {
        this.a = jsonObject;
        this.b = timeAgoParser;
    }

    public final a a() throws ParsingException {
        a aVar;
        if (this.f == null) {
            JsonObject object = this.a.getObject("metadata").getObject("lockupMetadataViewModel").getObject("image");
            JsonObject object2 = object.getObject("decoratedAvatarViewModel", (JsonObject) null);
            if (object2 != null) {
                aVar = new c(object2);
            } else {
                JsonObject object3 = object.getObject("avatarStackViewModel", (JsonObject) null);
                if (object3 != null) {
                    aVar = new b(object3);
                } else {
                    throw new ParsingException("Failed to determine channel image view model");
                }
            }
            this.f = aVar;
        }
        return this.f;
    }

    public final Optional<String> b() throws ParsingException {
        if (this.e == null) {
            this.e = d(1, 1).map(new pg(this, 2));
        }
        return this.e;
    }

    public final boolean c() throws ParsingException {
        return ((Boolean) b().map(new fg(16)).orElse(Boolean.FALSE)).booleanValue();
    }

    public final Optional<JsonObject> d(int i, int i2) throws ParsingException {
        if (this.g == null) {
            this.g = JsonUtils.getArray(this.a, "metadata.lockupMetadataViewModel.metadata.contentMetadataViewModel.metadataRows");
        }
        return this.g.streamAsJsonObjects().skip((long) i).limit(1).flatMap(new qg(i2)).findFirst();
    }

    public final /* synthetic */ ContentAvailability getContentAvailability() {
        return ob.a(this);
    }

    public long getDuration() throws ParsingException {
        boolean z;
        if (getStreamType() != StreamType.VIDEO_STREAM) {
            z = true;
        } else {
            z = false;
        }
        if (z || c()) {
            return -1;
        }
        List<String> list = (List) JsonUtils.getArray(this.a, "contentImage.thumbnailViewModel.overlays").streamAsJsonObjects().flatMap(new fg(14)).map(new fg(15)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            ParsingException e2 = null;
            for (String parseDurationString : list) {
                try {
                    return (long) YoutubeParsingHelper.parseDurationString(parseDurationString);
                } catch (ParsingException e3) {
                    e2 = e3;
                }
            }
            throw new ParsingException("Could not get duration", e2);
        }
        throw new ParsingException("Could not get duration: No parsable durations detected");
    }

    public String getName() throws ParsingException {
        String str = this.d;
        if (str != null) {
            return str;
        }
        String string = JsonUtils.getString(this.a, "metadata.lockupMetadataViewModel.title.content");
        if (!Utils.isNullOrEmpty(string)) {
            this.d = string;
            return string;
        }
        throw new ParsingException("Could not get name");
    }

    public final /* synthetic */ String getShortDescription() {
        return ob.b(this);
    }

    public StreamType getStreamType() throws ParsingException {
        StreamType streamType;
        if (this.c == null) {
            if (JsonUtils.getArray(this.a, "contentImage.thumbnailViewModel.overlays").streamAsJsonObjects().flatMap(new fg(18)).map(new fg(19)).anyMatch(new mg(9))) {
                streamType = StreamType.LIVE_STREAM;
            } else {
                streamType = StreamType.VIDEO_STREAM;
            }
            this.c = streamType;
        }
        return this.c;
    }

    public String getTextualUploadDate() throws ParsingException {
        boolean z;
        if (getStreamType() != StreamType.VIDEO_STREAM) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return null;
        }
        Optional<String> b2 = b();
        if (c()) {
            return (String) b2.map(new fg(17)).orElse(null);
        }
        return b2.orElse(null);
    }

    public List<Image> getThumbnails() throws ParsingException {
        return YoutubeParsingHelper.getImagesFromThumbnailsArray(JsonUtils.getArray(this.a, "contentImage.thumbnailViewModel.image.sources"));
    }

    public DateWrapper getUploadDate() throws ParsingException {
        String textualUploadDate;
        TimeAgoParser timeAgoParser = this.b;
        if (timeAgoParser == null || (textualUploadDate = getTextualUploadDate()) == null) {
            return null;
        }
        if (!c()) {
            return timeAgoParser.parse(textualUploadDate);
        }
        String str = (String) b().map(new fg(17)).orElse(null);
        if (str != null) {
            try {
                return new DateWrapper(LocalDateTime.parse(str, h).A(ZoneOffset.UTC).toInstant(), false);
            } catch (DateTimeParseException e2) {
                throw new ParsingException("Could not parse premiere upload date", e2);
            }
        } else {
            throw new ParsingException("Could not get upload date from premiere");
        }
    }

    public List<Image> getUploaderAvatars() throws ParsingException {
        return YoutubeParsingHelper.getImagesFromThumbnailsArray(JsonUtils.getArray(a().forAvatarExtraction(), "avatarViewModel.image.sources"));
    }

    public String getUploaderName() throws ParsingException {
        return (String) d(0, 0).map(new pg(this, 1)).filter(new mg(8)).orElseThrow(new cb(13));
    }

    public String getUploaderUrl() throws ParsingException {
        JsonObject object = a().forUploaderUrlExtraction().getObject("rendererContext").getObject("commandContext").getObject("onTap").getObject("innertubeCommand");
        JsonObject object2 = object.getObject("browseEndpoint");
        String string = object2.getString("browseId");
        if (string != null && string.startsWith("UC")) {
            return YoutubeChannelLinkHandlerFactory.getInstance().getUrl("channel/".concat(string));
        }
        String string2 = object2.getString("canonicalBaseUrl");
        if (!Utils.isNullOrEmpty(string2)) {
            YoutubeChannelLinkHandlerFactory instance = YoutubeChannelLinkHandlerFactory.getInstance();
            if (string2.startsWith(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
                string2 = string2.substring(1);
            }
            return instance.getUrl(string2);
        }
        String string3 = object.getObject("commandMetadata").getObject("webCommandMetadata").getString("url");
        if (!Utils.isNullOrEmpty(string3)) {
            YoutubeChannelLinkHandlerFactory instance2 = YoutubeChannelLinkHandlerFactory.getInstance();
            if (string3.startsWith(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
                string3 = string3.substring(1);
            }
            return instance2.getUrl(string3);
        }
        throw new ParsingException("Could not get uploader url");
    }

    public String getUrl() throws ParsingException {
        JsonObject jsonObject = this.a;
        try {
            String string = jsonObject.getString("contentId");
            if (Utils.isNullOrEmpty(string)) {
                string = JsonUtils.getString(jsonObject, "rendererContext.commandContext.onTap.innertubeCommand.watchEndpoint.videoId");
            }
            return YoutubeStreamLinkHandlerFactory.getInstance().getUrl(string);
        } catch (Exception e2) {
            throw new ParsingException("Could not get url", e2);
        }
    }

    public long getViewCount() throws ParsingException {
        if (c()) {
            return -1;
        }
        boolean z = true;
        Optional<U> map = d(1, 0).map(new pg(this, 0));
        if (map.isPresent()) {
            String str = (String) map.get();
            if (str.toLowerCase().contains("no views")) {
                return 0;
            }
            if (str.toLowerCase().contains("recommended")) {
                return -1;
            }
            return Utils.mixedNumberWordToLong(str);
        }
        if (getStreamType() == StreamType.VIDEO_STREAM) {
            z = false;
        }
        if (!z) {
            return -1;
        }
        return 0;
    }

    public boolean isAd() throws ParsingException {
        String name = getName();
        if ("[Private video]".equals(name) || "[Deleted video]".equals(name)) {
            return true;
        }
        return false;
    }

    public final /* synthetic */ boolean isShortFormContent() {
        return ob.d(this);
    }

    public boolean isUploaderVerified() throws ParsingException {
        return ((Boolean) d(0, 0).map(new fg(12)).map(new fg(13)).orElse(Boolean.FALSE)).booleanValue();
    }
}
