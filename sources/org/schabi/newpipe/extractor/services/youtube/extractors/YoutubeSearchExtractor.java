package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonBuilder;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;
import j$.util.Objects;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
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
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.services.youtube.YoutubeMetaInfoHelper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeSearchExtractor extends SearchExtractor {
    public final String g;
    public final boolean h;
    public final boolean i;
    public final boolean j;
    public JsonObject k;

    public YoutubeSearchExtractor(StreamingService streamingService, SearchQueryHandler searchQueryHandler) {
        super(streamingService, searchQueryHandler);
        String str;
        boolean z;
        boolean z2;
        List<String> contentFilters = searchQueryHandler.getContentFilters();
        boolean z3 = false;
        if (Utils.isNullOrEmpty((Collection<?>) contentFilters)) {
            str = null;
        } else {
            str = contentFilters.get(0);
        }
        this.g = str;
        if (str == null || "all".equals(str) || "videos".equals(str)) {
            z = true;
        } else {
            z = false;
        }
        this.h = z;
        if (str == null || "all".equals(str) || "channels".equals(str)) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.i = z2;
        this.j = (str == null || "all".equals(str) || "playlists".equals(str)) ? true : z3;
    }

    public final void b(MultiInfoItemsCollector multiInfoItemsCollector, JsonArray jsonArray) throws SearchExtractor.NothingFoundException {
        TimeAgoParser timeAgoParser = getTimeAgoParser();
        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            JsonObject jsonObject = (JsonObject) it.next();
            if (!jsonObject.has("backgroundPromoRenderer")) {
                boolean has = jsonObject.has("videoRenderer");
                boolean z = this.h;
                if (has && z) {
                    multiInfoItemsCollector.commit(new YoutubeStreamInfoItemExtractor(jsonObject.getObject("videoRenderer"), timeAgoParser));
                } else if (!jsonObject.has("channelRenderer") || !this.i) {
                    boolean has2 = jsonObject.has("playlistRenderer");
                    boolean z2 = this.j;
                    if (has2 && z2) {
                        multiInfoItemsCollector.commit(new YoutubePlaylistInfoItemExtractor(jsonObject.getObject("playlistRenderer")));
                    } else if (jsonObject.has("showRenderer") && z2) {
                        multiInfoItemsCollector.commit(new jg(jsonObject.getObject("showRenderer")));
                    } else if (jsonObject.has("lockupViewModel")) {
                        JsonObject object = jsonObject.getObject("lockupViewModel");
                        String string = object.getString("contentType");
                        if (("LOCKUP_CONTENT_TYPE_PLAYLIST".equals(string) || "LOCKUP_CONTENT_TYPE_PODCAST".equals(string)) && z2) {
                            multiInfoItemsCollector.commit(new YoutubeMixOrPlaylistLockupInfoItemExtractor(object));
                        } else if ("LOCKUP_CONTENT_TYPE_VIDEO".equals(string) && z) {
                            multiInfoItemsCollector.commit(new YoutubeStreamInfoItemLockupExtractor(object, timeAgoParser));
                        }
                    }
                } else {
                    multiInfoItemsCollector.commit(new YoutubeChannelInfoItemExtractor(jsonObject.getObject("channelRenderer")));
                }
            } else {
                throw new SearchExtractor.NothingFoundException(YoutubeParsingHelper.getTextFromObject(jsonObject.getObject("backgroundPromoRenderer").getObject("bodyText")));
            }
        }
    }

    public final Page c(JsonObject jsonObject) {
        if (Utils.isNullOrEmpty(jsonObject)) {
            return null;
        }
        return new Page("https://www.youtube.com/youtubei/v1/search?prettyPrint=false", jsonObject.getObject("continuationEndpoint").getObject("continuationCommand").getString("token"));
    }

    public ListExtractor.InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        MultiInfoItemsCollector multiInfoItemsCollector = new MultiInfoItemsCollector(getServiceId());
        Iterator it = this.k.getObject("contents").getObject("twoColumnSearchResultsRenderer").getObject("primaryContents").getObject("sectionListRenderer").getArray("contents").iterator();
        Page page = null;
        while (it.hasNext()) {
            JsonObject jsonObject = (JsonObject) it.next();
            if (jsonObject.has("itemSectionRenderer")) {
                b(multiInfoItemsCollector, jsonObject.getObject("itemSectionRenderer").getArray("contents"));
            } else if (jsonObject.has("continuationItemRenderer")) {
                page = c(jsonObject.getObject("continuationItemRenderer"));
            }
        }
        return new ListExtractor.InfoItemsPage<>(multiInfoItemsCollector, page);
    }

    public List<MetaInfo> getMetaInfo() throws ParsingException {
        return YoutubeMetaInfoHelper.getMetaInfo(this.k.getObject("contents").getObject("twoColumnSearchResultsRenderer").getObject("primaryContents").getObject("sectionListRenderer").getArray("contents"));
    }

    public ListExtractor.InfoItemsPage<InfoItem> getPage(Page page) throws IOException, ExtractionException {
        if (page == null || Utils.isNullOrEmpty(page.getUrl())) {
            throw new IllegalArgumentException("Page doesn't contain an URL");
        }
        Localization extractorLocalization = getExtractorLocalization();
        MultiInfoItemsCollector multiInfoItemsCollector = new MultiInfoItemsCollector(getServiceId());
        JsonArray array = YoutubeParsingHelper.getJsonPostResponse("search", JsonWriter.string(YoutubeParsingHelper.prepareDesktopJsonBuilder(extractorLocalization, getExtractorContentCountry()).value("continuation", page.getId()).done()).getBytes(StandardCharsets.UTF_8), extractorLocalization).getArray("onResponseReceivedCommands").getObject(0).getObject("appendContinuationItemsAction").getArray("continuationItems");
        b(multiInfoItemsCollector, array.getObject(0).getObject("itemSectionRenderer").getArray("contents"));
        return new ListExtractor.InfoItemsPage<>(multiInfoItemsCollector, c(array.getObject(1).getObject("continuationItemRenderer")));
    }

    public String getSearchSuggestion() throws ParsingException {
        JsonObject object = this.k.getObject("contents").getObject("twoColumnSearchResultsRenderer").getObject("primaryContents").getObject("sectionListRenderer").getArray("contents").getObject(0).getObject("itemSectionRenderer");
        JsonObject object2 = object.getArray("contents").getObject(0).getObject("didYouMeanRenderer");
        if (!object2.isEmpty()) {
            return JsonUtils.getString(object2, "correctedQueryEndpoint.searchEndpoint.query");
        }
        return (String) Objects.requireNonNullElse(YoutubeParsingHelper.getTextFromObject(object.getArray("contents").getObject(0).getObject("showingResultsForRenderer").getObject("correctedQuery")), "");
    }

    public String getUrl() throws ParsingException {
        return super.getUrl() + "&gl=" + getExtractorContentCountry().getCountryCode();
    }

    public boolean isCorrectedSearch() {
        return !this.k.getObject("contents").getObject("twoColumnSearchResultsRenderer").getObject("primaryContents").getObject("sectionListRenderer").getArray("contents").getObject(0).getObject("itemSectionRenderer").getArray("contents").getObject(0).getObject("showingResultsForRenderer").isEmpty();
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        String searchString = super.getSearchString();
        Localization extractorLocalization = getExtractorLocalization();
        String searchParameter = YoutubeSearchQueryHandlerFactory.getSearchParameter(this.g);
        JsonBuilder<JsonObject> value = YoutubeParsingHelper.prepareDesktopJsonBuilder(extractorLocalization, getExtractorContentCountry()).value("query", searchString);
        if (!Utils.isNullOrEmpty(searchParameter)) {
            value.value("params", searchParameter);
        }
        this.k = YoutubeParsingHelper.getJsonPostResponse("search", JsonWriter.string(value.done()).getBytes(StandardCharsets.UTF_8), extractorLocalization);
    }
}
