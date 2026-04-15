package org.schabi.newpipe.extractor.channel;

import org.schabi.newpipe.extractor.InfoItemsCollector;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public final class ChannelInfoItemsCollector extends InfoItemsCollector<ChannelInfoItem, ChannelInfoItemExtractor> {
    public ChannelInfoItemsCollector(int i) {
        super(i);
    }

    public ChannelInfoItem extract(ChannelInfoItemExtractor channelInfoItemExtractor) throws ParsingException {
        ChannelInfoItem channelInfoItem = new ChannelInfoItem(getServiceId(), channelInfoItemExtractor.getUrl(), channelInfoItemExtractor.getName());
        try {
            channelInfoItem.setSubscriberCount(channelInfoItemExtractor.getSubscriberCount());
        } catch (Exception e) {
            a(e);
        }
        try {
            channelInfoItem.setStreamCount(channelInfoItemExtractor.getStreamCount());
        } catch (Exception e2) {
            a(e2);
        }
        try {
            channelInfoItem.setThumbnails(channelInfoItemExtractor.getThumbnails());
        } catch (Exception e3) {
            a(e3);
        }
        try {
            channelInfoItem.setDescription(channelInfoItemExtractor.getDescription());
        } catch (Exception e4) {
            a(e4);
        }
        try {
            channelInfoItem.setVerified(channelInfoItemExtractor.isVerified());
        } catch (Exception e5) {
            a(e5);
        }
        return channelInfoItem;
    }
}
