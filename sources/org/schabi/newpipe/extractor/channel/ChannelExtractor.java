package org.schabi.newpipe.extractor.channel;

import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.Extractor;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;

public abstract class ChannelExtractor extends Extractor {
    public ChannelExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public abstract List<Image> getAvatars() throws ParsingException;

    public abstract List<Image> getBanners() throws ParsingException;

    public abstract String getDescription() throws ParsingException;

    public abstract String getFeedUrl() throws ParsingException;

    public abstract List<Image> getParentChannelAvatars() throws ParsingException;

    public abstract String getParentChannelName() throws ParsingException;

    public abstract String getParentChannelUrl() throws ParsingException;

    public abstract long getSubscriberCount() throws ParsingException;

    public abstract List<ListLinkHandler> getTabs() throws ParsingException;

    public List<String> getTags() throws ParsingException {
        return Collections.emptyList();
    }

    public abstract boolean isVerified() throws ParsingException;
}
