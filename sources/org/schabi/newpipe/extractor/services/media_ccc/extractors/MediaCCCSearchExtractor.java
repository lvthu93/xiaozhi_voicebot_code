package org.schabi.newpipe.extractor.services.media_ccc.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.MetaInfo;
import org.schabi.newpipe.extractor.MultiInfoItemsCollector;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelInfoItem;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.infoItems.MediaCCCStreamInfoItemExtractor;
import org.schabi.newpipe.extractor.services.media_ccc.linkHandler.MediaCCCConferencesListLinkHandlerFactory;

public class MediaCCCSearchExtractor extends SearchExtractor {
    public JsonObject g;
    public final MediaCCCConferenceKiosk h;

    public MediaCCCSearchExtractor(StreamingService streamingService, SearchQueryHandler searchQueryHandler) {
        super(streamingService, searchQueryHandler);
        try {
            this.h = new MediaCCCConferenceKiosk(streamingService, MediaCCCConferencesListLinkHandlerFactory.getInstance().fromId("conferences"), "conferences");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ListExtractor.InfoItemsPage<InfoItem> getInitialPage() {
        MultiInfoItemsCollector multiInfoItemsCollector = new MultiInfoItemsCollector(getServiceId());
        if (getLinkHandler().getContentFilters().contains("conferences") || getLinkHandler().getContentFilters().contains("all") || getLinkHandler().getContentFilters().isEmpty()) {
            String searchString = getSearchString();
            for (ChannelInfoItem next : this.h.getInitialPage().getItems()) {
                if (next.getName().toUpperCase().contains(searchString.toUpperCase())) {
                    multiInfoItemsCollector.commit(new u5(next));
                }
            }
        }
        if (getLinkHandler().getContentFilters().contains("events") || getLinkHandler().getContentFilters().contains("all") || getLinkHandler().getContentFilters().isEmpty()) {
            JsonArray array = this.g.getArray("events");
            for (int i = 0; i < array.size(); i++) {
                if (array.getObject(i).getString("release_date") != null) {
                    multiInfoItemsCollector.commit(new MediaCCCStreamInfoItemExtractor(array.getObject(i)));
                }
            }
        }
        return new ListExtractor.InfoItemsPage<>(multiInfoItemsCollector, (Page) null);
    }

    public List<MetaInfo> getMetaInfo() {
        return Collections.emptyList();
    }

    public ListExtractor.InfoItemsPage<InfoItem> getPage(Page page) {
        return ListExtractor.InfoItemsPage.emptyPage();
    }

    public String getSearchSuggestion() {
        return "";
    }

    public boolean isCorrectedSearch() {
        return false;
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        if (getLinkHandler().getContentFilters().contains("events") || getLinkHandler().getContentFilters().contains("all") || getLinkHandler().getContentFilters().isEmpty()) {
            try {
                this.g = JsonParser.object().from(downloader.get(getUrl(), getExtractorLocalization()).responseBody());
            } catch (JsonParserException e) {
                throw new ExtractionException("Could not parse JSON.", e);
            }
        }
        if (getLinkHandler().getContentFilters().contains("conferences") || getLinkHandler().getContentFilters().contains("all") || getLinkHandler().getContentFilters().isEmpty()) {
            this.h.fetchPage();
        }
    }
}
