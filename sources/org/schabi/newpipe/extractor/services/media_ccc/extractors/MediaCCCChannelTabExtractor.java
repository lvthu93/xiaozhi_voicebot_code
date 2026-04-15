package org.schabi.newpipe.extractor.services.media_ccc.extractors;

import com.grack.nanojson.JsonObject;
import j$.util.Collection$EL;
import j$.util.Objects;
import java.io.IOException;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.MultiInfoItemsCollector;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;

public class MediaCCCChannelTabExtractor extends ChannelTabExtractor {
    public JsonObject g;

    public MediaCCCChannelTabExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler, JsonObject jsonObject) {
        super(streamingService, listLinkHandler);
        this.g = jsonObject;
    }

    public ListExtractor.InfoItemsPage<InfoItem> getInitialPage() {
        MultiInfoItemsCollector multiInfoItemsCollector = new MultiInfoItemsCollector(getServiceId());
        JsonObject jsonObject = this.g;
        Objects.requireNonNull(jsonObject);
        Class<JsonObject> cls = JsonObject.class;
        y2.z(cls, 3, y2.s(cls, 3, Collection$EL.stream(jsonObject.getArray("events")))).forEach(new q5(multiInfoItemsCollector, 0));
        return new ListExtractor.InfoItemsPage<>(multiInfoItemsCollector, (Page) null);
    }

    public ListExtractor.InfoItemsPage<InfoItem> getPage(Page page) {
        return ListExtractor.InfoItemsPage.emptyPage();
    }

    public void onFetchPage(Downloader downloader) throws ExtractionException, IOException {
        if (this.g == null) {
            this.g = MediaCCCConferenceExtractor.b(downloader, getId());
        }
    }
}
