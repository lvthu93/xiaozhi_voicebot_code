package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItem;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemsCollector;

public class BandcampFeaturedExtractor extends KioskExtractor<PlaylistInfoItem> {
    public JsonObject h;

    public BandcampFeaturedExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler, String str) {
        super(streamingService, listLinkHandler, str);
    }

    public final ListExtractor.InfoItemsPage<PlaylistInfoItem> b(JsonArray jsonArray) {
        PlaylistInfoItemsCollector playlistInfoItemsCollector = new PlaylistInfoItemsCollector(getServiceId());
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = jsonArray.getObject(i);
            if (!object.isNull("album_title")) {
                playlistInfoItemsCollector.commit(new BandcampPlaylistInfoItemFeaturedExtractor(object));
            }
        }
        JsonObject object2 = jsonArray.getObject(jsonArray.size() - 1);
        long j = object2.getLong("story_date");
        long j2 = object2.getLong("ntid");
        String string = object2.getString("story_type");
        return new ListExtractor.InfoItemsPage<>(playlistInfoItemsCollector, new Page("https://bandcamp.com/api/mobile/24/feed_older_logged_out?story_groups=featured:" + j + ':' + string + ':' + j2));
    }

    public ListExtractor.InfoItemsPage<PlaylistInfoItem> getInitialPage() throws IOException, ExtractionException {
        return b(this.h.getObject("feed_content").getObject("stories").getArray("featured"));
    }

    public String getName() throws ParsingException {
        return "Featured";
    }

    public ListExtractor.InfoItemsPage<PlaylistInfoItem> getPage(Page page) throws IOException, ExtractionException {
        try {
            return b(JsonParser.object().from(getDownloader().get(page.getUrl()).responseBody()).getObject("stories").getArray("featured"));
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse Bandcamp featured API response", e);
        }
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        try {
            this.h = JsonParser.object().from(getDownloader().postWithContentTypeJson("https://bandcamp.com/api/mobile/24/bootstrap_data", Collections.emptyMap(), "{\"platform\":\"\",\"version\":0}".getBytes(StandardCharsets.UTF_8)).responseBody());
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse Bandcamp featured API response", e);
        }
    }
}
