package org.schabi.newpipe.extractor.comments;

import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;

public abstract class CommentsExtractor extends ListExtractor<CommentsInfoItem> {
    public CommentsExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public int getCommentsCount() throws ExtractionException {
        return -1;
    }

    public String getName() throws ParsingException {
        return "Comments";
    }

    public boolean isCommentsDisabled() throws ExtractionException {
        return false;
    }
}
