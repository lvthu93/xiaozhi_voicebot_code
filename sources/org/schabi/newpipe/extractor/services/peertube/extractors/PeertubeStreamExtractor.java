package org.schabi.newpipe.extractor.services.peertube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import j$.util.Collection$EL;
import j$.util.Objects;
import j$.util.stream.Collectors;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.peertube.PeertubeParsingHelper;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.DeliveryMethod;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.Frameset;
import org.schabi.newpipe.extractor.stream.Stream;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.stream.StreamSegment;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.stream.SubtitlesStream;
import org.schabi.newpipe.extractor.stream.VideoStream;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

public class PeertubeStreamExtractor extends StreamExtractor {
    public final String g = getBaseUrl();
    public JsonObject h;
    public final ArrayList i = new ArrayList();
    public final ArrayList j = new ArrayList();
    public final ArrayList k = new ArrayList();
    public ParsingException l = null;

    public PeertubeStreamExtractor(StreamingService streamingService, LinkHandler linkHandler) throws ParsingException {
        super(streamingService, linkHandler);
    }

    public static String f(JsonObject jsonObject, String str, String str2, String str3) throws ParsingException {
        if ("fileDownloadUrl".equals(str)) {
            str3 = JsonUtils.getString(jsonObject, "fileUrl");
        }
        return str3.replace("-fragmented." + str2, ".m3u8");
    }

    public final void c(JsonObject jsonObject, boolean z, String str, String str2, String str3, String str4) throws ParsingException {
        String str5;
        String substring = str3.substring(str3.lastIndexOf(".") + 1);
        MediaFormat fromSuffix = MediaFormat.getFromSuffix(substring);
        String str6 = str + "-" + substring;
        ArrayList arrayList = this.j;
        arrayList.add(new AudioStream.Builder().setId(str6 + "-" + str2 + "-" + DeliveryMethod.PROGRESSIVE_HTTP).setContent(str3, true).setMediaFormat(fromSuffix).setAverageBitrate(-1).build());
        if (!Utils.isNullOrEmpty(str4)) {
            if (z) {
                str5 = f(jsonObject, str2, substring, str3);
            } else {
                str5 = str4.replace("master", JsonUtils.getNumber(jsonObject, "resolution.id").toString());
            }
            AudioStream.Builder builder = new AudioStream.Builder();
            StringBuilder o = y2.o(str6, "-");
            DeliveryMethod deliveryMethod = DeliveryMethod.HLS;
            o.append(deliveryMethod);
            AudioStream build = builder.setId(o.toString()).setContent(str5, true).setDeliveryMethod(deliveryMethod).setMediaFormat(fromSuffix).setAverageBitrate(-1).setManifestUrl(str4).build();
            if (!Stream.containSimilarStream(build, arrayList)) {
                arrayList.add(build);
            }
        }
        String string = JsonUtils.getString(jsonObject, "torrentUrl");
        if (!Utils.isNullOrEmpty(string)) {
            AudioStream.Builder builder2 = new AudioStream.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(str6);
            sb.append("-");
            sb.append(str2);
            sb.append("-");
            DeliveryMethod deliveryMethod2 = DeliveryMethod.TORRENT;
            sb.append(deliveryMethod2);
            arrayList.add(builder2.setId(sb.toString()).setContent(string, true).setDeliveryMethod(deliveryMethod2).setMediaFormat(fromSuffix).setAverageBitrate(-1).build());
        }
    }

    public final void d(JsonObject jsonObject, boolean z, String str, String str2, String str3, String str4) throws ParsingException {
        boolean z2;
        String str5;
        JsonObject jsonObject2 = jsonObject;
        String str6 = str;
        String str7 = str2;
        String str8 = str3;
        String str9 = str4;
        String substring = str8.substring(str8.lastIndexOf(".") + 1);
        MediaFormat fromSuffix = MediaFormat.getFromSuffix(substring);
        String str10 = str6 + "-" + substring;
        if (!jsonObject2.has("hasAudio") || jsonObject2.getBoolean("hasAudio")) {
            z2 = false;
        } else {
            z2 = true;
        }
        ArrayList arrayList = this.k;
        arrayList.add(new VideoStream.Builder().setId(str10 + "-" + str7 + "-" + DeliveryMethod.PROGRESSIVE_HTTP).setContent(str8, true).setIsVideoOnly(z2).setResolution(str6).setMediaFormat(fromSuffix).build());
        if (!Utils.isNullOrEmpty(str4)) {
            if (z) {
                str5 = f(jsonObject2, str7, substring, str8);
            } else {
                str5 = str9.replace("master", JsonUtils.getNumber(jsonObject2, "resolution.id").toString());
            }
            VideoStream.Builder builder = new VideoStream.Builder();
            StringBuilder o = y2.o(str10, "-");
            DeliveryMethod deliveryMethod = DeliveryMethod.HLS;
            o.append(deliveryMethod);
            VideoStream build = builder.setId(o.toString()).setContent(str5, true).setIsVideoOnly(z2).setDeliveryMethod(deliveryMethod).setResolution(str6).setMediaFormat(fromSuffix).setManifestUrl(str9).build();
            if (!Stream.containSimilarStream(build, arrayList)) {
                arrayList.add(build);
            }
        }
        String string = JsonUtils.getString(jsonObject2, "torrentUrl");
        if (!Utils.isNullOrEmpty(string)) {
            VideoStream.Builder builder2 = new VideoStream.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(str10);
            sb.append("-");
            sb.append(str7);
            sb.append("-");
            DeliveryMethod deliveryMethod2 = DeliveryMethod.TORRENT;
            sb.append(deliveryMethod2);
            arrayList.add(builder2.setId(sb.toString()).setContent(string, true).setIsVideoOnly(z2).setDeliveryMethod(deliveryMethod2).setResolution(str6).setMediaFormat(fromSuffix).build());
        }
    }

    public final JsonObject e(String str) throws ParsingException, IOException, ReCaptchaException {
        Response response = getDownloader().get(this.g + "/api/v1/videos/" + getId() + MqttTopic.TOPIC_LEVEL_SEPARATOR + str);
        if (response == null) {
            throw new ParsingException("Could not get segments from API.");
        } else if (response.responseCode() == 400) {
            return null;
        } else {
            if (response.responseCode() == 200) {
                try {
                    return JsonParser.object().from(response.responseBody());
                } catch (JsonParserException e) {
                    throw new ParsingException("Could not parse json data for segments", e);
                }
            } else {
                throw new ParsingException("Could not get segments from API. Response code: " + response.responseCode());
            }
        }
    }

    public final void g() throws ParsingException {
        Class<JsonObject> cls = JsonObject.class;
        h(this.h.getArray("files"), "");
        try {
            for (JsonObject jsonObject : (List) Collection$EL.stream(this.h.getArray("streamingPlaylists")).filter(new c4(cls, 8)).map(new d4(cls, 8)).collect(Collectors.toList())) {
                h(jsonObject.getArray("files"), jsonObject.getString("playlistUrl"));
            }
        } catch (Exception e) {
            throw new ParsingException("Could not get streams", e);
        }
    }

    public int getAgeLimit() throws ParsingException {
        return JsonUtils.getBoolean(this.h, "nsfw").booleanValue() ? 18 : 0;
    }

    public List<AudioStream> getAudioStreams() throws ParsingException {
        a();
        ArrayList arrayList = this.j;
        if (arrayList.isEmpty() && this.k.isEmpty() && getStreamType() == StreamType.VIDEO_STREAM) {
            g();
        }
        return arrayList;
    }

    public String getCategory() throws ParsingException {
        return JsonUtils.getString(this.h, "category.label");
    }

    public Description getDescription() throws ParsingException {
        try {
            String string = JsonUtils.getString(this.h, "description");
            if (string.length() == 250 && string.substring(247).equals("...")) {
                Downloader downloader = NewPipe.getDownloader();
                try {
                    string = JsonUtils.getString(JsonParser.object().from(downloader.get(this.g + "/api/v1/videos/" + getId() + "/description").responseBody()), "description");
                } catch (JsonParserException | IOException | ReCaptchaException unused) {
                }
            }
            return new Description(string, 2);
        } catch (ParsingException unused2) {
            return Description.g;
        }
    }

    public long getDislikeCount() {
        return this.h.getLong("dislikes");
    }

    public List<Frameset> getFrames() throws ExtractionException {
        ArrayList arrayList = new ArrayList();
        try {
            JsonObject e = e("storyboards");
            if (e != null && e.has("storyboards")) {
                Iterator it = e.getArray("storyboards").iterator();
                while (it.hasNext()) {
                    Object next = it.next();
                    if (next instanceof JsonObject) {
                        JsonObject jsonObject = (JsonObject) next;
                        String string = jsonObject.getString("storyboardPath");
                        int i2 = jsonObject.getInt("spriteWidth");
                        int i3 = jsonObject.getInt("spriteHeight");
                        int i4 = jsonObject.getInt("totalWidth") / i2;
                        int i5 = jsonObject.getInt("totalHeight") / i3;
                        Object[] objArr = {y2.k(new StringBuilder(), this.g, string)};
                        ArrayList arrayList2 = new ArrayList(1);
                        Object obj = objArr[0];
                        arrayList.add(new Frameset(y2.p(obj, arrayList2, obj, arrayList2), i2, i3, i4 * i5, jsonObject.getInt("spriteDuration") * 1000, i4, i5));
                    }
                }
            }
            return arrayList;
        } catch (IOException | ReCaptchaException e2) {
            throw new ExtractionException("Could not get frames", e2);
        }
    }

    public String getHlsUrl() {
        a();
        if (getStreamType() != StreamType.VIDEO_STREAM || Utils.isNullOrEmpty(this.h.getObject("files"))) {
            return this.h.getArray("streamingPlaylists").getObject(0).getString("playlistUrl", "");
        }
        return this.h.getObject("files").getString("playlistUrl", "");
    }

    public String getHost() throws ParsingException {
        return JsonUtils.getString(this.h, "account.host");
    }

    public Locale getLanguageInfo() {
        try {
            return new Locale(JsonUtils.getString(this.h, "language.id"));
        } catch (ParsingException unused) {
            return null;
        }
    }

    public long getLength() {
        return this.h.getLong("duration");
    }

    public String getLicence() throws ParsingException {
        return JsonUtils.getString(this.h, "licence.label");
    }

    public long getLikeCount() {
        return this.h.getLong("likes");
    }

    public String getName() throws ParsingException {
        return JsonUtils.getString(this.h, "name");
    }

    public StreamExtractor.Privacy getPrivacy() {
        int i2 = this.h.getObject("privacy").getInt("id");
        if (i2 == 1) {
            return StreamExtractor.Privacy.c;
        }
        if (i2 == 2) {
            return StreamExtractor.Privacy.UNLISTED;
        }
        if (i2 == 3) {
            return StreamExtractor.Privacy.PRIVATE;
        }
        if (i2 != 4) {
            return StreamExtractor.Privacy.OTHER;
        }
        return StreamExtractor.Privacy.INTERNAL;
    }

    public List<StreamSegment> getStreamSegments() throws ParsingException {
        ArrayList arrayList = new ArrayList();
        try {
            JsonObject e = e("chapters");
            if (e != null && e.has("chapters")) {
                JsonArray array = e.getArray("chapters");
                for (int i2 = 0; i2 < array.size(); i2++) {
                    JsonObject object = array.getObject(i2);
                    arrayList.add(new StreamSegment(object.getString("title"), object.getInt("timecode")));
                }
            }
            return arrayList;
        } catch (IOException | ReCaptchaException e2) {
            throw new ParsingException("Could not get stream segments", e2);
        }
    }

    public StreamType getStreamType() {
        return this.h.getBoolean("isLive") ? StreamType.LIVE_STREAM : StreamType.VIDEO_STREAM;
    }

    public List<Image> getSubChannelAvatars() {
        return PeertubeParsingHelper.getAvatarsFromOwnerAccountOrVideoChannelObject(this.g, this.h.getObject("channel"));
    }

    public String getSubChannelName() throws ParsingException {
        return JsonUtils.getString(this.h, "channel.displayName");
    }

    public String getSubChannelUrl() throws ParsingException {
        return JsonUtils.getString(this.h, "channel.url");
    }

    public List<SubtitlesStream> getSubtitles(MediaFormat mediaFormat) throws ParsingException {
        ParsingException parsingException = this.l;
        if (parsingException == null) {
            return (List) Collection$EL.stream(this.i).filter(new o8(mediaFormat, 0)).collect(Collectors.toList());
        }
        throw parsingException;
    }

    public List<SubtitlesStream> getSubtitlesDefault() throws ParsingException {
        ParsingException parsingException = this.l;
        if (parsingException == null) {
            return this.i;
        }
        throw parsingException;
    }

    public String getSupportInfo() {
        try {
            return JsonUtils.getString(this.h, "support");
        } catch (ParsingException unused) {
            return "";
        }
    }

    public List<String> getTags() {
        return JsonUtils.getStringListFromJsonArray(this.h.getArray("tags"));
    }

    public String getTextualUploadDate() throws ParsingException {
        return JsonUtils.getString(this.h, "publishedAt");
    }

    public List<Image> getThumbnails() throws ParsingException {
        return PeertubeParsingHelper.getThumbnailsFromPlaylistOrVideoItem(this.g, this.h);
    }

    public long getTimeStamp() throws ParsingException {
        long b = b("((#|&|\\?)start=\\d{0,3}h?\\d{0,3}m?\\d{1,3}s?)");
        if (b == -2) {
            return 0;
        }
        return b;
    }

    public DateWrapper getUploadDate() throws ParsingException {
        return DateWrapper.fromInstant(getTextualUploadDate());
    }

    public List<Image> getUploaderAvatars() {
        return PeertubeParsingHelper.getAvatarsFromOwnerAccountOrVideoChannelObject(this.g, this.h.getObject("account"));
    }

    public String getUploaderName() throws ParsingException {
        return JsonUtils.getString(this.h, "account.displayName");
    }

    public String getUploaderUrl() throws ParsingException {
        String string = JsonUtils.getString(this.h, "account.name");
        String string2 = JsonUtils.getString(this.h, "account.host");
        ListLinkHandlerFactory channelLHFactory = getService().getChannelLHFactory();
        return channelLHFactory.fromId("accounts/" + string + "@" + string2, this.g).getUrl();
    }

    public List<VideoStream> getVideoOnlyStreams() {
        return Collections.emptyList();
    }

    public List<VideoStream> getVideoStreams() throws ExtractionException {
        a();
        ArrayList arrayList = this.k;
        if (arrayList.isEmpty()) {
            if (getStreamType() == StreamType.VIDEO_STREAM) {
                g();
            } else {
                Class<JsonObject> cls = JsonObject.class;
                try {
                    j$.util.stream.Stream map = Collection$EL.stream(this.h.getArray("streamingPlaylists")).filter(new c4(cls, 9)).map(new d4(cls, 9)).map(new p8(0));
                    Objects.requireNonNull(arrayList);
                    map.forEachOrdered(new q8(arrayList, 0));
                } catch (Exception e) {
                    throw new ParsingException("Could not get video streams", e);
                }
            }
        }
        return arrayList;
    }

    public long getViewCount() {
        return this.h.getLong("views");
    }

    public final void h(JsonArray jsonArray, String str) throws ParsingException {
        boolean z;
        String str2;
        String str3;
        Class<JsonObject> cls = JsonObject.class;
        try {
            if (Utils.isNullOrEmpty(str) || !str.endsWith("-master.m3u8")) {
                z = false;
            } else {
                z = true;
            }
            for (JsonObject jsonObject : (List) Collection$EL.stream(jsonArray).filter(new c4(cls, 10)).map(new d4(cls, 10)).collect(Collectors.toList())) {
                if (jsonObject.has("fileUrl")) {
                    str2 = "fileUrl";
                } else {
                    str2 = "fileDownloadUrl";
                }
                String string = JsonUtils.getString(jsonObject, str2);
                if (!Utils.isNullOrEmpty(string)) {
                    String string2 = JsonUtils.getString(jsonObject, "resolution.label");
                    if (jsonObject.has("fileUrl")) {
                        str3 = "fileUrl";
                    } else {
                        str3 = "fileDownloadUrl";
                    }
                    if (string2.toLowerCase().contains("audio")) {
                        c(jsonObject, z, string2, str3, string, str);
                    } else {
                        d(jsonObject, z, string2, str3, string, str);
                    }
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            throw new ParsingException("Could not get streams from array", e);
        }
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        StringBuilder sb = new StringBuilder();
        String str = this.g;
        sb.append(str);
        sb.append("/api/v1/videos/");
        sb.append(getId());
        Response response = downloader.get(sb.toString());
        if (response != null) {
            try {
                JsonObject from = JsonParser.object().from(response.responseBody());
                this.h = from;
                if (from != null) {
                    PeertubeParsingHelper.validate(from);
                    ArrayList arrayList = this.i;
                    if (arrayList.isEmpty()) {
                        try {
                            Iterator it = JsonUtils.getArray(JsonParser.object().from(getDownloader().get(str + "/api/v1/videos/" + getId() + "/captions").responseBody()), "data").iterator();
                            while (it.hasNext()) {
                                Object next = it.next();
                                if (next instanceof JsonObject) {
                                    JsonObject jsonObject = (JsonObject) next;
                                    String str2 = str + JsonUtils.getString(jsonObject, "captionPath");
                                    String string = JsonUtils.getString(jsonObject, "language.id");
                                    MediaFormat fromSuffix = MediaFormat.getFromSuffix(str2.substring(str2.lastIndexOf(".") + 1));
                                    if (fromSuffix != null && !Utils.isNullOrEmpty(string)) {
                                        arrayList.add(new SubtitlesStream.Builder().setContent(str2, true).setMediaFormat(fromSuffix).setLanguageCode(string).setAutoGenerated(false).build());
                                    }
                                }
                            }
                        } catch (Exception e) {
                            this.l = new ParsingException("Could not get subtitles", e);
                        }
                    }
                } else {
                    throw new ExtractionException("Could not extract PeerTube stream data");
                }
            } catch (JsonParserException e2) {
                throw new ExtractionException("Could not extract PeerTube stream data", e2);
            }
        } else {
            throw new ExtractionException("Could not extract PeerTube channel data");
        }
    }

    public StreamInfoItemsCollector getRelatedItems() throws IOException, ExtractionException {
        String str;
        List<String> tags = getTags();
        boolean isEmpty = tags.isEmpty();
        String str2 = this.g;
        if (isEmpty) {
            StringBuilder o = y2.o(str2, "/api/v1/accounts/");
            o.append(JsonUtils.getString(this.h, "account.name"));
            o.append("@");
            o.append(JsonUtils.getString(this.h, "account.host"));
            o.append("/videos?start=0&count=8");
            str = o.toString();
        } else {
            String x = y2.x(str2, "/api/v1/search/videos");
            StringBuilder sb = new StringBuilder("start=0&count=8&sort=-createdAt");
            for (String encodeUrlUtf8 : tags) {
                sb.append("&tagsOneOf=");
                sb.append(Utils.encodeUrlUtf8(encodeUrlUtf8));
            }
            str = x + "?" + sb;
        }
        JsonObject jsonObject = null;
        if (Utils.isBlank(str)) {
            return null;
        }
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        Response response = getDownloader().get(str);
        if (response != null && !Utils.isBlank(response.responseBody())) {
            try {
                jsonObject = JsonParser.object().from(response.responseBody());
            } catch (JsonParserException e) {
                throw new ParsingException("Could not parse json data for related videos", e);
            }
        }
        if (jsonObject != null) {
            try {
                Iterator it = ((JsonArray) JsonUtils.getValue(jsonObject, "data")).iterator();
                while (it.hasNext()) {
                    Object next = it.next();
                    if (next instanceof JsonObject) {
                        PeertubeStreamInfoItemExtractor peertubeStreamInfoItemExtractor = new PeertubeStreamInfoItemExtractor((JsonObject) next, str2);
                        if (!peertubeStreamInfoItemExtractor.getUrl().equals(getUrl())) {
                            streamInfoItemsCollector.commit((StreamInfoItemExtractor) peertubeStreamInfoItemExtractor);
                        }
                    }
                }
            } catch (Exception e2) {
                throw new ParsingException("Could not extract related videos", e2);
            }
        }
        return streamInfoItemsCollector;
    }
}
