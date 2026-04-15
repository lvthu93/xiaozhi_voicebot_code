package org.schabi.newpipe.extractor.channel;

import org.schabi.newpipe.extractor.InfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public interface ChannelInfoItemExtractor extends InfoItemExtractor {
    String getDescription() throws ParsingException;

    long getStreamCount() throws ParsingException;

    long getSubscriberCount() throws ParsingException;

    boolean isVerified() throws ParsingException;
}
