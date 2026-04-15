package org.schabi.newpipe.extractor.search;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.ListInfo;
import org.schabi.newpipe.extractor.MetaInfo;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.utils.ExtractorHelper;

public class SearchInfo extends ListInfo<InfoItem> {
    public final String o;
    public String p;
    public boolean q;
    public List<MetaInfo> r = Collections.emptyList();

    public SearchInfo(int i, SearchQueryHandler searchQueryHandler, String str) {
        super(i, searchQueryHandler, "Search");
        this.o = str;
    }

    public static SearchInfo getInfo(StreamingService streamingService, SearchQueryHandler searchQueryHandler) throws ExtractionException, IOException {
        SearchExtractor searchExtractor = streamingService.getSearchExtractor(searchQueryHandler);
        searchExtractor.fetchPage();
        return getInfo(searchExtractor);
    }

    public static ListExtractor.InfoItemsPage<InfoItem> getMoreItems(StreamingService streamingService, SearchQueryHandler searchQueryHandler, Page page) throws IOException, ExtractionException {
        return streamingService.getSearchExtractor(searchQueryHandler).getPage(page);
    }

    public List<MetaInfo> getMetaInfo() {
        return this.r;
    }

    public String getSearchString() {
        return this.o;
    }

    public String getSearchSuggestion() {
        return this.p;
    }

    public boolean isCorrectedSearch() {
        return this.q;
    }

    public void setIsCorrectedSearch(boolean z) {
        this.q = z;
    }

    public void setMetaInfo(List<MetaInfo> list) {
        this.r = list;
    }

    public void setSearchSuggestion(String str) {
        this.p = str;
    }

    public static SearchInfo getInfo(SearchExtractor searchExtractor) throws ExtractionException, IOException {
        SearchInfo searchInfo = new SearchInfo(searchExtractor.getServiceId(), searchExtractor.getLinkHandler(), searchExtractor.getSearchString());
        try {
            searchInfo.setOriginalUrl(searchExtractor.getOriginalUrl());
        } catch (Exception e) {
            searchInfo.addError(e);
        }
        try {
            searchInfo.setSearchSuggestion(searchExtractor.getSearchSuggestion());
        } catch (Exception e2) {
            searchInfo.addError(e2);
        }
        try {
            searchInfo.setIsCorrectedSearch(searchExtractor.isCorrectedSearch());
        } catch (Exception e3) {
            searchInfo.addError(e3);
        }
        try {
            searchInfo.setMetaInfo(searchExtractor.getMetaInfo());
        } catch (Exception e4) {
            searchInfo.addError(e4);
        }
        ListExtractor.InfoItemsPage itemsPageOrLogError = ExtractorHelper.getItemsPageOrLogError(searchInfo, searchExtractor);
        searchInfo.setRelatedItems(itemsPageOrLogError.getItems());
        searchInfo.setNextPage(itemsPageOrLogError.getNextPage());
        return searchInfo;
    }
}
