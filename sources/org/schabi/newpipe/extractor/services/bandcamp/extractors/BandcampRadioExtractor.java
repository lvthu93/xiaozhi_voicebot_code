package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
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

public class BandcampRadioExtractor extends KioskExtractor<StreamInfoItem> {
    public JsonObject h = null;

    public BandcampRadioExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler, String str) {
        super(streamingService, listLinkHandler, str);
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() {
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        JsonArray array = this.h.getArray("results");
        for (int i = 0; i < array.size(); i++) {
            streamInfoItemsCollector.commit((StreamInfoItemExtractor) new BandcampRadioInfoItemExtractor(array.getObject(i)));
        }
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, (Page) null);
    }

    public String getName() throws ParsingException {
        return "Radio";
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) {
        return null;
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        try {
            this.h = JsonParser.object().from(getDownloader().get("https://bandcamp.com/api/bcweekly/3/list").responseBody());
        } catch (JsonParserException e) {
            throw new ExtractionException("Could not parse Bandcamp Radio API response", e);
        }
    }
}
