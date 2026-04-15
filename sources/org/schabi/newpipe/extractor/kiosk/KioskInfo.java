package org.schabi.newpipe.extractor.kiosk;

import java.io.IOException;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.ListInfo;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.utils.ExtractorHelper;

public final class KioskInfo extends ListInfo<StreamInfoItem> {
    public KioskInfo(int i, ListLinkHandler listLinkHandler, String str) {
        super(i, listLinkHandler, str);
    }

    public static KioskInfo getInfo(String str) throws IOException, ExtractionException {
        return getInfo(NewPipe.getServiceByUrl(str), str);
    }

    public static ListExtractor.InfoItemsPage<StreamInfoItem> getMoreItems(StreamingService streamingService, String str, Page page) throws IOException, ExtractionException {
        return streamingService.getKioskList().getExtractorByUrl(str, page).getPage(page);
    }

    public static KioskInfo getInfo(StreamingService streamingService, String str) throws IOException, ExtractionException {
        KioskExtractor extractorByUrl = streamingService.getKioskList().getExtractorByUrl(str, (Page) null);
        extractorByUrl.fetchPage();
        return getInfo(extractorByUrl);
    }

    public static KioskInfo getInfo(KioskExtractor kioskExtractor) throws ExtractionException {
        KioskInfo kioskInfo = new KioskInfo(kioskExtractor.getServiceId(), kioskExtractor.getLinkHandler(), kioskExtractor.getName());
        ListExtractor.InfoItemsPage itemsPageOrLogError = ExtractorHelper.getItemsPageOrLogError(kioskInfo, kioskExtractor);
        kioskInfo.setRelatedItems(itemsPageOrLogError.getItems());
        kioskInfo.setNextPage(itemsPageOrLogError.getNextPage());
        return kioskInfo;
    }
}
