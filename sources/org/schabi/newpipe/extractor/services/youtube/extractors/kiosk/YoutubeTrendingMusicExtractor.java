package org.schabi.newpipe.extractor.services.youtube.extractors.kiosk;

import java.io.IOException;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.UnsupportedContentInCountryException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;

public class YoutubeTrendingMusicExtractor extends tf {
    public YoutubeTrendingMusicExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler, String str) {
        super(streamingService, listLinkHandler, str, "TRENDING_VIDEOS");
    }

    public /* bridge */ /* synthetic */ ListExtractor.InfoItemsPage getInitialPage() throws IOException, ExtractionException {
        return super.getInitialPage();
    }

    public String getName() throws ParsingException {
        return "Trending Music Videos";
    }

    public /* bridge */ /* synthetic */ ListExtractor.InfoItemsPage getPage(Page page) {
        return super.getPage(page);
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        if (tf.j.contains(getExtractorContentCountry().getCountryCode())) {
            super.onFetchPage(downloader);
            return;
        }
        throw new UnsupportedContentInCountryException("YouTube Charts doesn't support this country for trending music videos charts");
    }
}
