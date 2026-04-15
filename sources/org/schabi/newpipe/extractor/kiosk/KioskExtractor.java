package org.schabi.newpipe.extractor.kiosk;

import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;

public abstract class KioskExtractor<T extends InfoItem> extends ListExtractor<T> {
    public final String g;

    public KioskExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler, String str) {
        super(streamingService, listLinkHandler);
        this.g = str;
    }

    public String getId() {
        return this.g;
    }

    public abstract String getName() throws ParsingException;
}
