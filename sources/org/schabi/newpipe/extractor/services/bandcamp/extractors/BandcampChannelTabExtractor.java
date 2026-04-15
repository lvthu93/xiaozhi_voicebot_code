package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import java.io.IOException;
import java.util.Iterator;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.MultiInfoItemsCollector;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.streaminfoitem.BandcampDiscographStreamInfoItemExtractor;

public class BandcampChannelTabExtractor extends ChannelTabExtractor {
    public JsonArray g;
    public final String h;

    public BandcampChannelTabExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
        String str = listLinkHandler.getContentFilters().get(0);
        str.getClass();
        if (str.equals("albums")) {
            this.h = "album";
        } else if (str.equals("tracks")) {
            this.h = "track";
        } else {
            throw new IllegalArgumentException("Unsupported channel tab: ".concat(str));
        }
    }

    public static BandcampChannelTabExtractor fromDiscography(StreamingService streamingService, ListLinkHandler listLinkHandler, JsonArray jsonArray) {
        BandcampChannelTabExtractor bandcampChannelTabExtractor = new BandcampChannelTabExtractor(streamingService, listLinkHandler);
        bandcampChannelTabExtractor.g = jsonArray;
        return bandcampChannelTabExtractor;
    }

    public ListExtractor.InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        MultiInfoItemsCollector multiInfoItemsCollector = new MultiInfoItemsCollector(getServiceId());
        Iterator it = this.g.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) next;
                String string = jsonObject.getString("item_type", "");
                if (string.equals(this.h)) {
                    if (string.equals("album")) {
                        multiInfoItemsCollector.commit(new BandcampAlbumInfoItemExtractor(jsonObject, getUrl()));
                    } else if (string.equals("track")) {
                        multiInfoItemsCollector.commit(new BandcampDiscographStreamInfoItemExtractor(jsonObject, getUrl()));
                    }
                }
            }
        }
        return new ListExtractor.InfoItemsPage<>(multiInfoItemsCollector, (Page) null);
    }

    public ListExtractor.InfoItemsPage<InfoItem> getPage(Page page) {
        return null;
    }

    public void onFetchPage(Downloader downloader) throws ParsingException {
        if (this.g == null) {
            this.g = BandcampExtractorHelper.getArtistDetails(getId()).getArray("discography");
        }
    }
}
