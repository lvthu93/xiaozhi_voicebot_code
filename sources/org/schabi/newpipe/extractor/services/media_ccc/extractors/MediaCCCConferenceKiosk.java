package org.schabi.newpipe.extractor.services.media_ccc.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelInfoItem;
import org.schabi.newpipe.extractor.channel.ChannelInfoItemsCollector;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.infoItems.MediaCCCConferenceInfoItemExtractor;

public class MediaCCCConferenceKiosk extends KioskExtractor<ChannelInfoItem> {
    public JsonObject h;

    public MediaCCCConferenceKiosk(StreamingService streamingService, ListLinkHandler listLinkHandler, String str) {
        super(streamingService, listLinkHandler, str);
    }

    public ListExtractor.InfoItemsPage<ChannelInfoItem> getInitialPage() {
        JsonArray array = this.h.getArray("conferences");
        ChannelInfoItemsCollector channelInfoItemsCollector = new ChannelInfoItemsCollector(getServiceId());
        for (int i = 0; i < array.size(); i++) {
            channelInfoItemsCollector.commit(new MediaCCCConferenceInfoItemExtractor(array.getObject(i)));
        }
        return new ListExtractor.InfoItemsPage<>(channelInfoItemsCollector, (Page) null);
    }

    public String getName() throws ParsingException {
        return this.h.getString("Conferences");
    }

    public ListExtractor.InfoItemsPage<ChannelInfoItem> getPage(Page page) {
        return ListExtractor.InfoItemsPage.emptyPage();
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        try {
            this.h = JsonParser.object().from(downloader.get(getLinkHandler().getUrl(), getExtractorLocalization()).responseBody());
        } catch (JsonParserException e) {
            throw new ExtractionException("Could not parse json.", e);
        }
    }
}
