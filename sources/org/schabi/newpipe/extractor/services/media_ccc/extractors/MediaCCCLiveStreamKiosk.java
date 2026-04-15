package org.schabi.newpipe.extractor.services.media_ccc.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import java.io.IOException;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;

public class MediaCCCLiveStreamKiosk extends KioskExtractor<StreamInfoItem> {
    public JsonArray h;

    public MediaCCCLiveStreamKiosk(StreamingService streamingService, ListLinkHandler listLinkHandler, String str) {
        super(streamingService, listLinkHandler, str);
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        for (int i = 0; i < this.h.size(); i++) {
            JsonObject object = this.h.getObject(i);
            if (object.getBoolean("isCurrentlyStreaming")) {
                JsonArray array = object.getArray("groups");
                for (int i2 = 0; i2 < array.size(); i2++) {
                    String string = array.getObject(i2).getString("group");
                    JsonArray array2 = array.getObject(i2).getArray("rooms");
                    for (int i3 = 0; i3 < array2.size(); i3++) {
                        streamInfoItemsCollector.commit((StreamInfoItemExtractor) new MediaCCCLiveStreamKioskExtractor(object, string, array2.getObject(i3)));
                    }
                }
            }
        }
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, (Page) null);
    }

    public String getName() throws ParsingException {
        return "live";
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) throws IOException, ExtractionException {
        return ListExtractor.InfoItemsPage.emptyPage();
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        this.h = MediaCCCParsingHelper.getLiveStreams(downloader, getExtractorLocalization());
    }
}
