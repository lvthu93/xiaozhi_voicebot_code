package org.schabi.newpipe.extractor.utils;

import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.Info;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.InfoItemExtractor;
import org.schabi.newpipe.extractor.InfoItemsCollector;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfo;

public final class ExtractorHelper {
    public static <T extends InfoItem> ListExtractor.InfoItemsPage<T> getItemsPageOrLogError(Info info2, ListExtractor<T> listExtractor) {
        try {
            ListExtractor.InfoItemsPage<T> initialPage = listExtractor.getInitialPage();
            info2.addAllErrors(initialPage.getErrors());
            return initialPage;
        } catch (Exception e) {
            info2.addError(e);
            return ListExtractor.InfoItemsPage.emptyPage();
        }
    }

    public static List<InfoItem> getRelatedItemsOrLogError(StreamInfo streamInfo, StreamExtractor streamExtractor) {
        try {
            InfoItemsCollector<? extends InfoItem, ? extends InfoItemExtractor> relatedItems = streamExtractor.getRelatedItems();
            if (relatedItems == null) {
                return Collections.emptyList();
            }
            streamInfo.addAllErrors(relatedItems.getErrors());
            return relatedItems.getItems();
        } catch (Exception e) {
            streamInfo.addError(e);
            return Collections.emptyList();
        }
    }

    @Deprecated
    public static List<InfoItem> getRelatedVideosOrLogError(StreamInfo streamInfo, StreamExtractor streamExtractor) {
        return getRelatedItemsOrLogError(streamInfo, streamExtractor);
    }
}
