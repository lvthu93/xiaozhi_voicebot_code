package org.schabi.newpipe.extractor.feed;

import java.io.IOException;
import java.util.List;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.ListInfo;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.utils.ExtractorHelper;

public class FeedInfo extends ListInfo<StreamInfoItem> {
    public FeedInfo(int i, String str, String str2, String str3, String str4, List<String> list, String str5) {
        super(i, str, str2, str3, str4, list, str5);
    }

    public static FeedInfo getInfo(String str) throws IOException, ExtractionException {
        return getInfo(NewPipe.getServiceByUrl(str), str);
    }

    public static FeedInfo getInfo(StreamingService streamingService, String str) throws IOException, ExtractionException {
        FeedExtractor feedExtractor = streamingService.getFeedExtractor(str);
        if (feedExtractor != null) {
            feedExtractor.fetchPage();
            return getInfo(feedExtractor);
        }
        throw new IllegalArgumentException("Service \"" + streamingService.getServiceInfo().getName() + "\" doesn't support FeedExtractor.");
    }

    public static FeedInfo getInfo(FeedExtractor feedExtractor) throws IOException, ExtractionException {
        feedExtractor.fetchPage();
        FeedInfo feedInfo = new FeedInfo(feedExtractor.getServiceId(), feedExtractor.getId(), feedExtractor.getUrl(), feedExtractor.getOriginalUrl(), feedExtractor.getName(), (List<String>) null, (String) null);
        ListExtractor.InfoItemsPage itemsPageOrLogError = ExtractorHelper.getItemsPageOrLogError(feedInfo, feedExtractor);
        feedInfo.setRelatedItems(itemsPageOrLogError.getItems());
        feedInfo.setNextPage(itemsPageOrLogError.getNextPage());
        return feedInfo;
    }
}
