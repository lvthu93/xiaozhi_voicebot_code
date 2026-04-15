package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import com.grack.nanojson.JsonStringWriter;
import com.grack.nanojson.JsonWriter;
import j$.util.Collection$EL;
import j$.util.stream.Collectors;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.MetaInfo;
import org.schabi.newpipe.extractor.MultiInfoItemsCollector;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeMusicSearchExtractor extends SearchExtractor {
    public JsonObject g;
    public List<JsonObject> h;

    public YoutubeMusicSearchExtractor(StreamingService streamingService, SearchQueryHandler searchQueryHandler) {
        super(streamingService, searchQueryHandler);
    }

    public final void b(MultiInfoItemsCollector multiInfoItemsCollector, JsonArray jsonArray) {
        Class<JsonObject> cls = JsonObject.class;
        y2.ab(cls, 7, y2.aa(cls, 7, Collection$EL.stream(jsonArray))).map(new p8(29)).filter(new bz(26)).forEachOrdered(new hb(3, getLinkHandler().getContentFilters().get(0), multiInfoItemsCollector));
    }

    public final List<JsonObject> c() {
        List<JsonObject> list = this.h;
        if (list != null) {
            return list;
        }
        Class<JsonObject> cls = JsonObject.class;
        List<JsonObject> list2 = (List) y2.ab(cls, 6, y2.aa(cls, 6, Collection$EL.stream(this.g.getObject("contents").getObject("tabbedSearchResultsRenderer").getArray("tabs").getObject(0).getObject("tabRenderer").getObject("content").getObject("sectionListRenderer").getArray("contents")))).map(new p8(27)).filter(new bz(25)).map(new p8(28)).collect(Collectors.toList());
        this.h = list2;
        return list2;
    }

    public final Page d(JsonArray jsonArray) {
        if (Utils.isNullOrEmpty((Collection<?>) jsonArray)) {
            return null;
        }
        String string = jsonArray.getObject(0).getObject("nextContinuationData").getString("continuation");
        return new Page("https://music.youtube.com/youtubei/v1/search?ctoken=" + string + "&continuation=" + string + "&prettyPrint=false");
    }

    public ListExtractor.InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        MultiInfoItemsCollector multiInfoItemsCollector = new MultiInfoItemsCollector(getServiceId());
        Iterator it = JsonUtils.getArray(JsonUtils.getArray(this.g, "contents.tabbedSearchResultsRenderer.tabs").getObject(0), "tabRenderer.content.sectionListRenderer.contents").iterator();
        Page page = null;
        while (it.hasNext()) {
            JsonObject jsonObject = (JsonObject) it.next();
            if (jsonObject.has("musicShelfRenderer")) {
                JsonObject object = jsonObject.getObject("musicShelfRenderer");
                b(multiInfoItemsCollector, object.getArray("contents"));
                page = d(object.getArray("continuations"));
            }
        }
        return new ListExtractor.InfoItemsPage<>(multiInfoItemsCollector, page);
    }

    public List<MetaInfo> getMetaInfo() {
        return Collections.emptyList();
    }

    public ListExtractor.InfoItemsPage<InfoItem> getPage(Page page) throws IOException, ExtractionException {
        if (page == null || Utils.isNullOrEmpty(page.getUrl())) {
            throw new IllegalArgumentException("Page doesn't contain an URL");
        }
        MultiInfoItemsCollector multiInfoItemsCollector = new MultiInfoItemsCollector(getServiceId());
        try {
            JsonObject object = JsonParser.object().from(YoutubeParsingHelper.getValidJsonResponseBody(getDownloader().postWithContentTypeJson(page.getUrl(), YoutubeParsingHelper.getYoutubeMusicHeaders(), ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) JsonWriter.string().object()).object("context")).object("client")).value("clientName", "WEB_REMIX")).value("clientVersion", YoutubeParsingHelper.getYoutubeMusicClientVersion())).value("hl", "en-GB")).value("gl", getExtractorContentCountry().getCountryCode())).value("platform", "DESKTOP")).value("utcOffsetMinutes", 0)).end()).object("request")).array("internalExperimentFlags")).end()).value("useSsl", true)).end()).object("user")).value("lockedSafetyMode", false)).end()).end()).end()).done().getBytes(StandardCharsets.UTF_8)))).getObject("continuationContents").getObject("musicShelfContinuation");
            b(multiInfoItemsCollector, object.getArray("contents"));
            return new ListExtractor.InfoItemsPage<>(multiInfoItemsCollector, d(object.getArray("continuations")));
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse JSON", e);
        }
    }

    public String getSearchSuggestion() throws ParsingException {
        for (JsonObject next : c()) {
            JsonObject object = next.getObject("didYouMeanRenderer");
            if (!object.isEmpty()) {
                return YoutubeParsingHelper.getTextFromObject(object.getObject("correctedQuery"));
            }
            JsonObject object2 = next.getObject("showingResultsForRenderer");
            if (!object2.isEmpty()) {
                return JsonUtils.getString(object2, "correctedQueryEndpoint.searchEndpoint.query");
            }
        }
        return "";
    }

    public boolean isCorrectedSearch() throws ParsingException {
        return Collection$EL.stream(c()).anyMatch(new bz(24));
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        String str;
        String str2 = getLinkHandler().getContentFilters().get(0);
        str2.getClass();
        char c = 65535;
        switch (str2.hashCode()) {
            case -1778518201:
                if (str2.equals("music_playlists")) {
                    c = 0;
                    break;
                }
                break;
            case -566908430:
                if (str2.equals("music_artists")) {
                    c = 1;
                    break;
                }
                break;
            case 1499667262:
                if (str2.equals("music_albums")) {
                    c = 2;
                    break;
                }
                break;
            case 1589120868:
                if (str2.equals("music_songs")) {
                    c = 3;
                    break;
                }
                break;
            case 2098153138:
                if (str2.equals("music_videos")) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                str = "Eg-KAQwIABAAGAAgACgBMABqChAEEAUQAxAKEAk%3D";
                break;
            case 1:
                str = "Eg-KAQwIABAAGAAgASgAMABqChAEEAUQAxAKEAk%3D";
                break;
            case 2:
                str = "Eg-KAQwIABAAGAEgACgAMABqChAEEAUQAxAKEAk%3D";
                break;
            case 3:
                str = "Eg-KAQwIARAAGAAgACgAMABqChAEEAUQAxAKEAk%3D";
                break;
            case 4:
                str = "Eg-KAQwIABABGAAgACgAMABqChAEEAUQAxAKEAk%3D";
                break;
            default:
                str = null;
                break;
        }
        try {
            this.g = JsonParser.object().from(YoutubeParsingHelper.getValidJsonResponseBody(getDownloader().postWithContentTypeJson("https://music.youtube.com/youtubei/v1/search?prettyPrint=false", YoutubeParsingHelper.getYoutubeMusicHeaders(), ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) JsonWriter.string().object()).object("context")).object("client")).value("clientName", "WEB_REMIX")).value("clientVersion", YoutubeParsingHelper.getYoutubeMusicClientVersion())).value("hl", "en-GB")).value("gl", getExtractorContentCountry().getCountryCode())).value("platform", "DESKTOP")).value("utcOffsetMinutes", 0)).end()).object("request")).array("internalExperimentFlags")).end()).value("useSsl", true)).end()).object("user")).value("lockedSafetyMode", false)).end()).end()).value("query", getSearchString())).value("params", str)).end()).done().getBytes(StandardCharsets.UTF_8))));
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse JSON", e);
        }
    }
}
