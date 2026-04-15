package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonBuilder;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;
import j$.util.Collection$EL;
import j$.util.Objects;
import j$.util.stream.Collectors;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.mozilla.javascript.Context;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ContentNotAvailableException;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.playlist.PlaylistInfo;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.utils.ImageSuffix;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeMixPlaylistExtractor extends PlaylistExtractor {
    public static final List<ImageSuffix> j;
    public JsonObject g;
    public JsonObject h;
    public String i;

    static {
        ImageSuffix imageSuffix = new ImageSuffix("default.jpg", 90, 120, Image.ResolutionLevel.LOW);
        Image.ResolutionLevel resolutionLevel = Image.ResolutionLevel.MEDIUM;
        Object[] objArr = {imageSuffix, new ImageSuffix("mqdefault.jpg", Context.VERSION_1_8, 320, resolutionLevel), new ImageSuffix("hqdefault.jpg", 360, 480, resolutionLevel)};
        ArrayList arrayList = new ArrayList(3);
        for (int i2 = 0; i2 < 3; i2++) {
            Object obj = objArr[i2];
            Objects.requireNonNull(obj);
            arrayList.add(obj);
        }
        j = Collections.unmodifiableList(arrayList);
    }

    public YoutubeMixPlaylistExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public static List d(String str) {
        return (List) Collection$EL.stream(j).map(new cc(y2.j("https://i.ytimg.com/vi/", str, MqttTopic.TOPIC_LEVEL_SEPARATOR), 5)).collect(Collectors.toUnmodifiableList());
    }

    public final void b(StreamInfoItemsCollector streamInfoItemsCollector, List<Object> list) {
        if (list != null) {
            Class<JsonObject> cls = JsonObject.class;
            y2.ab(cls, 5, y2.aa(cls, 5, Collection$EL.stream(list))).map(new p8(26)).filter(new bz(23)).map(new eg(getTimeAgoParser(), 0)).forEachOrdered(new t5(streamInfoItemsCollector, 1));
        }
    }

    public final Page c(JsonObject jsonObject, Map<String, String> map) throws IOException, ExtractionException {
        JsonObject jsonObject2 = (JsonObject) jsonObject.getArray("contents").get(jsonObject.getArray("contents").size() - 1);
        if (jsonObject2 == null || jsonObject2.getObject("playlistPanelVideoRenderer") == null) {
            throw new ExtractionException("Could not extract next page url");
        }
        JsonObject object = jsonObject2.getObject("playlistPanelVideoRenderer").getObject("navigationEndpoint").getObject("watchEndpoint");
        String string = object.getString("playlistId");
        String string2 = object.getString("videoId");
        int i2 = object.getInt("index");
        return new Page("https://www.youtube.com/youtubei/v1/next?prettyPrint=false", (String) null, (List<String>) null, map, JsonWriter.string(YoutubeParsingHelper.prepareDesktopJsonBuilder(getExtractorLocalization(), getExtractorContentCountry()).value("videoId", string2).value("playlistId", string).value("playlistIndex", i2).value("params", object.getString("params")).done()).getBytes(StandardCharsets.UTF_8));
    }

    public Description getDescription() throws ParsingException {
        return Description.g;
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        b(streamInfoItemsCollector, this.h.getArray("contents"));
        HashMap hashMap = new HashMap();
        hashMap.put("VISITOR_INFO1_LIVE", this.i);
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, c(this.h, hashMap));
    }

    public String getName() throws ParsingException {
        String textAtKey = YoutubeParsingHelper.getTextAtKey(this.h, "title");
        if (!Utils.isNullOrEmpty(textAtKey)) {
            return textAtKey;
        }
        throw new ParsingException("Could not get playlist name");
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) throws IOException, ExtractionException {
        if (page == null || Utils.isNullOrEmpty(page.getUrl())) {
            throw new IllegalArgumentException("Page doesn't contain an URL");
        } else if (page.getCookies().containsKey("VISITOR_INFO1_LIVE")) {
            StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
            JsonObject object = JsonUtils.toJsonObject(YoutubeParsingHelper.getValidJsonResponseBody(getDownloader().postWithContentTypeJson(page.getUrl(), YoutubeParsingHelper.getYouTubeHeaders(), page.getBody(), getExtractorLocalization()))).getObject("contents").getObject("twoColumnWatchNextResults").getObject("playlist").getObject("playlist");
            JsonArray array = object.getArray("contents");
            b(streamInfoItemsCollector, array.subList(object.getInt("currentIndex") + 1, array.size()));
            return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, c(object, page.getCookies()));
        } else {
            throw new IllegalArgumentException("Cookie 'VISITOR_INFO1_LIVE' is missing");
        }
    }

    public PlaylistInfo.PlaylistType getPlaylistType() throws ParsingException {
        return YoutubeParsingHelper.extractPlaylistTypeFromPlaylistId(this.h.getString("playlistId"));
    }

    public long getStreamCount() {
        return -2;
    }

    public List<Image> getThumbnails() throws ParsingException {
        try {
            return d(YoutubeParsingHelper.extractVideoIdFromMixId(this.h.getString("playlistId")));
        } catch (Exception e) {
            try {
                return d(this.g.getObject("currentVideoEndpoint").getObject("watchEndpoint").getString("videoId"));
            } catch (Exception unused) {
                throw new ParsingException("Could not get playlist thumbnails", e);
            }
        }
    }

    public List<Image> getUploaderAvatars() {
        return Collections.emptyList();
    }

    public String getUploaderName() {
        return "YouTube";
    }

    public String getUploaderUrl() {
        return "";
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        Localization extractorLocalization = getExtractorLocalization();
        URL stringToURL = Utils.stringToURL(getUrl());
        String id = getId();
        String queryValue = Utils.getQueryValue(stringToURL, "v");
        String queryValue2 = Utils.getQueryValue(stringToURL, "index");
        JsonBuilder<JsonObject> value = YoutubeParsingHelper.prepareDesktopJsonBuilder(extractorLocalization, getExtractorContentCountry()).value("playlistId", id);
        if (queryValue != null) {
            value.value("videoId", queryValue);
        }
        if (queryValue2 != null) {
            value.value("playlistIndex", Integer.parseInt(queryValue2));
        }
        byte[] bytes = JsonWriter.string(value.done()).getBytes(StandardCharsets.UTF_8);
        Response postWithContentTypeJson = getDownloader().postWithContentTypeJson("https://www.youtube.com/youtubei/v1/next?prettyPrint=false", YoutubeParsingHelper.getYouTubeHeaders(), bytes, extractorLocalization);
        JsonObject jsonObject = JsonUtils.toJsonObject(YoutubeParsingHelper.getValidJsonResponseBody(postWithContentTypeJson));
        this.g = jsonObject;
        JsonObject object = jsonObject.getObject("contents").getObject("twoColumnWatchNextResults").getObject("playlist").getObject("playlist");
        this.h = object;
        if (Utils.isNullOrEmpty(object)) {
            ExtractionException extractionException = new ExtractionException("Could not get playlistData");
            if (!YoutubeParsingHelper.isConsentAccepted()) {
                throw new ContentNotAvailableException("Consent is required in some countries to view Mix playlists", extractionException);
            }
            throw extractionException;
        }
        this.i = YoutubeParsingHelper.extractCookieValue("VISITOR_INFO1_LIVE", postWithContentTypeJson);
    }
}
