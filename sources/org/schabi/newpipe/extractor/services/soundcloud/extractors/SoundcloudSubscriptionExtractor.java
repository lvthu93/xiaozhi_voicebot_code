package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelInfoItem;
import org.schabi.newpipe.extractor.channel.ChannelInfoItemsCollector;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudService;
import org.schabi.newpipe.extractor.subscription.SubscriptionExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionItem;
import org.schabi.newpipe.extractor.utils.Utils;

public class SoundcloudSubscriptionExtractor extends SubscriptionExtractor {
    public SoundcloudSubscriptionExtractor(SoundcloudService soundcloudService) {
        super(soundcloudService, Collections.singletonList(SubscriptionExtractor.ContentSource.CHANNEL_URL));
    }

    public List<SubscriptionItem> fromChannelUrl(String str) throws IOException, ExtractionException {
        StreamingService streamingService = this.b;
        if (str != null) {
            try {
                ListLinkHandlerFactory channelLHFactory = streamingService.getChannelLHFactory();
                String replaceHttpWithHttps = Utils.replaceHttpWithHttps(str);
                if (!replaceHttpWithHttps.startsWith("https://")) {
                    if (!replaceHttpWithHttps.contains("soundcloud.com/")) {
                        str = "https://soundcloud.com/".concat(replaceHttpWithHttps);
                    } else {
                        str = "https://".concat(replaceHttpWithHttps);
                    }
                }
                String str2 = "https://api-v2.soundcloud.com/users/" + channelLHFactory.fromUrl(str).getId() + "/followings?client_id=" + SoundcloudParsingHelper.clientId() + "&limit=200";
                ChannelInfoItemsCollector channelInfoItemsCollector = new ChannelInfoItemsCollector(streamingService.getServiceId());
                SoundcloudParsingHelper.getUsersFromApiMinItems(2500, channelInfoItemsCollector, str2);
                List<ChannelInfoItem> items = channelInfoItemsCollector.getItems();
                ArrayList arrayList = new ArrayList(items.size());
                for (ChannelInfoItem channelInfoItem : items) {
                    arrayList.add(new SubscriptionItem(channelInfoItem.getServiceId(), channelInfoItem.getUrl(), channelInfoItem.getName()));
                }
                return arrayList;
            } catch (ExtractionException e) {
                throw new SubscriptionExtractor.InvalidSourceException((Throwable) e);
            }
        } else {
            throw new SubscriptionExtractor.InvalidSourceException("Channel url is null");
        }
    }

    public String getRelatedUrl() {
        return "https://soundcloud.com/you";
    }
}
