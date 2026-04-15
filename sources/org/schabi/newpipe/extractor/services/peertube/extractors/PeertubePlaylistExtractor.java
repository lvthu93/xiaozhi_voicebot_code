package org.schabi.newpipe.extractor.services.peertube.extractors;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import java.util.List;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.services.peertube.PeertubeParsingHelper;
import org.schabi.newpipe.extractor.stream.Description;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.utils.Utils;

public class PeertubePlaylistExtractor extends PlaylistExtractor {
    public JsonObject g;

    public PeertubePlaylistExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public Description getDescription() throws ParsingException {
        String string = this.g.getString("description");
        if (Utils.isNullOrEmpty(string)) {
            return Description.g;
        }
        return new Description(string, 3);
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        return getPage(new Page(getUrl() + "/videos?start=0&count=12"));
    }

    public String getName() throws ParsingException {
        return this.g.getString("displayName");
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) throws IOException, ExtractionException {
        JsonObject jsonObject;
        if (page == null || Utils.isNullOrEmpty(page.getUrl())) {
            throw new IllegalArgumentException("Page doesn't contain an URL");
        }
        Response response = getDownloader().get(page.getUrl());
        if (response == null || Utils.isBlank(response.responseBody())) {
            jsonObject = null;
        } else {
            try {
                jsonObject = JsonParser.object().from(response.responseBody());
            } catch (Exception e) {
                throw new ParsingException("Could not parse json data for playlist info", e);
            }
        }
        if (jsonObject != null) {
            PeertubeParsingHelper.validate(jsonObject);
            long j = jsonObject.getLong("total");
            StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
            PeertubeParsingHelper.collectItemsFrom(streamInfoItemsCollector, jsonObject, getBaseUrl());
            return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, PeertubeParsingHelper.getNextPage(page.getUrl(), j));
        }
        throw new ExtractionException("Unable to get PeerTube playlist info");
    }

    public long getStreamCount() {
        return this.g.getLong("videosLength");
    }

    public List<Image> getSubChannelAvatars() throws ParsingException {
        return PeertubeParsingHelper.getAvatarsFromOwnerAccountOrVideoChannelObject(getBaseUrl(), this.g.getObject("videoChannel"));
    }

    public String getSubChannelName() {
        return this.g.getObject("videoChannel").getString("displayName");
    }

    public String getSubChannelUrl() {
        return this.g.getObject("videoChannel").getString("url");
    }

    public List<Image> getThumbnails() throws ParsingException {
        return PeertubeParsingHelper.getThumbnailsFromPlaylistOrVideoItem(getBaseUrl(), this.g);
    }

    public List<Image> getUploaderAvatars() throws ParsingException {
        return PeertubeParsingHelper.getAvatarsFromOwnerAccountOrVideoChannelObject(getBaseUrl(), this.g.getObject("ownerAccount"));
    }

    public String getUploaderName() {
        return this.g.getObject("ownerAccount").getString("displayName");
    }

    public String getUploaderUrl() {
        return this.g.getObject("ownerAccount").getString("url");
    }

    public boolean isUploaderVerified() throws ParsingException {
        return false;
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        try {
            JsonObject from = JsonParser.object().from(downloader.get(getUrl()).responseBody());
            this.g = from;
            PeertubeParsingHelper.validate(from);
        } catch (JsonParserException e) {
            throw new ExtractionException("Could not parse json", e);
        }
    }
}
