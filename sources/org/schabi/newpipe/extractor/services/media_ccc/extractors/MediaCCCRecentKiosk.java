package org.schabi.newpipe.extractor.services.media_ccc.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import j$.util.Collection$EL;
import j$.util.Comparator$CC;
import j$.util.Comparator$EL;
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
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;

public class MediaCCCRecentKiosk extends KioskExtractor<StreamInfoItem> {
    public JsonObject h;

    public MediaCCCRecentKiosk(StreamingService streamingService, ListLinkHandler listLinkHandler, String str) {
        super(streamingService, listLinkHandler, str);
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        JsonArray array = this.h.getArray("events");
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId(), Comparator$EL.reversed(Comparator$CC.comparing(new z5(27), Comparator$CC.nullsLast(Comparator$CC.comparing(new z5(28))))));
        Class<JsonObject> cls = JsonObject.class;
        y2.z(cls, 6, y2.s(cls, 6, Collection$EL.stream(array))).map(new z5(29)).filter(new bz(5)).forEach(new t5(streamInfoItemsCollector, 0));
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, (Page) null);
    }

    public String getName() throws ParsingException {
        return "recent";
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) throws IOException, ExtractionException {
        return ListExtractor.InfoItemsPage.emptyPage();
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        try {
            this.h = JsonParser.object().from(downloader.get("https://api.media.ccc.de/public/events/recent", getExtractorLocalization()).responseBody());
        } catch (JsonParserException e) {
            throw new ExtractionException("Could not parse json.", e);
        }
    }
}
