package org.schabi.newpipe.extractor.services.youtube.extractors.kiosk;

import java.io.IOException;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;

public class YoutubeLiveExtractor extends zf {
    public YoutubeLiveExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler, String str) {
        super(streamingService, listLinkHandler, str, "UC4R8DWoMoI7CAwX8_LjQHig", "EgdsaXZldGFikgEDCKEK");
    }

    public /* bridge */ /* synthetic */ ListExtractor.InfoItemsPage getInitialPage() throws IOException, ExtractionException {
        return super.getInitialPage();
    }

    public /* bridge */ /* synthetic */ String getName() throws ParsingException {
        return super.getName();
    }

    public /* bridge */ /* synthetic */ ListExtractor.InfoItemsPage getPage(Page page) throws IOException, ExtractionException {
        return super.getPage(page);
    }

    public /* bridge */ /* synthetic */ void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        super.onFetchPage(downloader);
    }
}
