package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import org.schabi.newpipe.extractor.utils.Parser;
import org.schabi.newpipe.extractor.utils.Utils;

public class SoundcloudSearchExtractor extends SearchExtractor {
    public JsonObject g;

    public SoundcloudSearchExtractor(StreamingService streamingService, SearchQueryHandler searchQueryHandler) {
        super(streamingService, searchQueryHandler);
    }

    public static Page c(String str, ib ibVar) throws ParsingException {
        int d = d(str);
        String f = y2.f("&offset=", d);
        return new Page(str.replace(f, "&offset=" + ibVar.applyAsInt(d)));
    }

    public static int d(String str) throws ParsingException {
        try {
            return Integer.parseInt(Parser.compatParseMap(new URL(str).getQuery()).get("offset"));
        } catch (MalformedURLException e) {
            throw new ParsingException("Could not get offset from page URL", e);
        }
    }

    public final MultiInfoItemsCollector b(JsonArray jsonArray) {
        MultiInfoItemsCollector multiInfoItemsCollector = new MultiInfoItemsCollector(getServiceId());
        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) next;
                String string = jsonObject.getString("kind", "");
                string.getClass();
                char c = 65535;
                switch (string.hashCode()) {
                    case 3599307:
                        if (string.equals("user")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 110621003:
                        if (string.equals("track")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1879474642:
                        if (string.equals("playlist")) {
                            c = 2;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        multiInfoItemsCollector.commit(new SoundcloudChannelInfoItemExtractor(jsonObject));
                        break;
                    case 1:
                        multiInfoItemsCollector.commit(new SoundcloudStreamInfoItemExtractor(jsonObject));
                        break;
                    case 2:
                        multiInfoItemsCollector.commit(new SoundcloudPlaylistInfoItemExtractor(jsonObject));
                        break;
                }
            }
        }
        return multiInfoItemsCollector;
    }

    public ListExtractor.InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        if (this.g.getInt("total_results") > 10) {
            return new ListExtractor.InfoItemsPage<>(b(this.g.getArray("collection")), c(getUrl(), new ib(1)));
        }
        return new ListExtractor.InfoItemsPage<>(b(this.g.getArray("collection")), (Page) null);
    }

    public List<MetaInfo> getMetaInfo() {
        return Collections.emptyList();
    }

    public ListExtractor.InfoItemsPage<InfoItem> getPage(Page page) throws IOException, ExtractionException {
        if (page == null || Utils.isNullOrEmpty(page.getUrl())) {
            throw new IllegalArgumentException("Page doesn't contain an URL");
        }
        try {
            JsonObject from = JsonParser.object().from(getDownloader().get(page.getUrl(), getExtractorLocalization()).responseBody());
            JsonArray array = from.getArray("collection");
            if (d(page.getUrl()) + 10 < from.getInt("total_results")) {
                return new ListExtractor.InfoItemsPage<>(b(array), c(page.getUrl(), new ib(0)));
            }
            return new ListExtractor.InfoItemsPage<>(b(array), (Page) null);
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse json response", e);
        }
    }

    public String getSearchSuggestion() {
        return "";
    }

    public boolean isCorrectedSearch() {
        return false;
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        try {
            JsonObject from = JsonParser.object().from(getDownloader().get(getUrl(), getExtractorLocalization()).responseBody());
            this.g = from;
            if (from.getArray("collection").isEmpty()) {
                throw new SearchExtractor.NothingFoundException("Nothing found");
            }
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse json response", e);
        }
    }
}
