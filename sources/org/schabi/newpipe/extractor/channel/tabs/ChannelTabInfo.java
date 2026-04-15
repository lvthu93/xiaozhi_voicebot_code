package org.schabi.newpipe.extractor.channel.tabs;

import java.io.IOException;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.ListInfo;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.utils.ExtractorHelper;

public class ChannelTabInfo extends ListInfo<InfoItem> {
    public ChannelTabInfo(int i, ListLinkHandler listLinkHandler) {
        super(i, listLinkHandler, listLinkHandler.getContentFilters().get(0));
    }

    public static ChannelTabInfo getInfo(StreamingService streamingService, ListLinkHandler listLinkHandler) throws ExtractionException, IOException {
        ChannelTabExtractor channelTabExtractor = streamingService.getChannelTabExtractor(listLinkHandler);
        channelTabExtractor.fetchPage();
        return getInfo(channelTabExtractor);
    }

    public static ListExtractor.InfoItemsPage<InfoItem> getMoreItems(StreamingService streamingService, ListLinkHandler listLinkHandler, Page page) throws ExtractionException, IOException {
        return streamingService.getChannelTabExtractor(listLinkHandler).getPage(page);
    }

    public static ChannelTabInfo getInfo(ChannelTabExtractor channelTabExtractor) {
        ChannelTabInfo channelTabInfo = new ChannelTabInfo(channelTabExtractor.getServiceId(), channelTabExtractor.getLinkHandler());
        try {
            channelTabInfo.setOriginalUrl(channelTabExtractor.getOriginalUrl());
        } catch (Exception e) {
            channelTabInfo.addError(e);
        }
        ListExtractor.InfoItemsPage itemsPageOrLogError = ExtractorHelper.getItemsPageOrLogError(channelTabInfo, channelTabExtractor);
        channelTabInfo.setRelatedItems(itemsPageOrLogError.getItems());
        channelTabInfo.setNextPage(itemsPageOrLogError.getNextPage());
        return channelTabInfo;
    }
}
