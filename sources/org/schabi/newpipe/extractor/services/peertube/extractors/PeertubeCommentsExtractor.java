package org.schabi.newpipe.extractor.services.peertube.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.comments.CommentsExtractor;
import org.schabi.newpipe.extractor.comments.CommentsInfoItem;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemExtractor;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemsCollector;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.services.peertube.PeertubeParsingHelper;
import org.schabi.newpipe.extractor.utils.Utils;

public class PeertubeCommentsExtractor extends CommentsExtractor {
    public Boolean g = null;

    public PeertubeCommentsExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public final void b(CommentsInfoItemsCollector commentsInfoItemsCollector, JsonObject jsonObject) throws ParsingException {
        Iterator it = jsonObject.getArray("children").iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof JsonObject) {
                JsonObject jsonObject2 = (JsonObject) next;
                JsonObject object = jsonObject2.getObject("comment");
                JsonArray array = jsonObject2.getArray("children");
                if (!object.getBoolean("isDeleted")) {
                    commentsInfoItemsCollector.commit((CommentsInfoItemExtractor) new PeertubeCommentsInfoItemExtractor(object, array, getUrl(), getBaseUrl(), c()));
                }
            }
        }
    }

    public final boolean c() throws ParsingException {
        if (this.g == null) {
            if (getOriginalUrl().contains("/videos/watch/")) {
                this.g = Boolean.FALSE;
            } else {
                this.g = Boolean.valueOf(getOriginalUrl().contains("/comment-threads/"));
            }
        }
        return this.g.booleanValue();
    }

    public ListExtractor.InfoItemsPage<CommentsInfoItem> getInitialPage() throws IOException, ExtractionException {
        if (c()) {
            return getPage(new Page(getOriginalUrl()));
        }
        return getPage(new Page(getUrl() + "?start=0&count=12"));
    }

    public ListExtractor.InfoItemsPage<CommentsInfoItem> getPage(Page page) throws IOException, ExtractionException {
        long j;
        JsonObject jsonObject;
        if (page == null || Utils.isNullOrEmpty(page.getUrl())) {
            throw new IllegalArgumentException("Page doesn't contain an URL");
        }
        CommentsInfoItemsCollector commentsInfoItemsCollector = new CommentsInfoItemsCollector(getServiceId());
        if (page.getBody() == null) {
            Response response = getDownloader().get(page.getUrl());
            if (response == null || Utils.isBlank(response.responseBody())) {
                jsonObject = null;
            } else {
                try {
                    jsonObject = JsonParser.object().from(response.responseBody());
                } catch (Exception e) {
                    throw new ParsingException("Could not parse json data for comments info", e);
                }
            }
            if (jsonObject != null) {
                PeertubeParsingHelper.validate(jsonObject);
                if (c() || jsonObject.has("children")) {
                    j = (long) jsonObject.getArray("children").size();
                    b(commentsInfoItemsCollector, jsonObject);
                } else {
                    j = jsonObject.getLong("total");
                    Iterator it = jsonObject.getArray("data").iterator();
                    while (it.hasNext()) {
                        Object next = it.next();
                        if (next instanceof JsonObject) {
                            JsonObject jsonObject2 = (JsonObject) next;
                            if (!jsonObject2.getBoolean("isDeleted")) {
                                commentsInfoItemsCollector.commit((CommentsInfoItemExtractor) new PeertubeCommentsInfoItemExtractor(jsonObject2, (JsonArray) null, getUrl(), getBaseUrl(), c()));
                            }
                        }
                    }
                }
            } else {
                throw new ExtractionException("Unable to get PeerTube kiosk info");
            }
        } else {
            try {
                JsonObject from = JsonParser.object().from(new String(page.getBody(), StandardCharsets.UTF_8));
                this.g = Boolean.TRUE;
                j = (long) from.getArray("children").size();
                b(commentsInfoItemsCollector, from);
            } catch (JsonParserException e2) {
                throw new ParsingException("Could not parse json data for nested comments  info", e2);
            }
        }
        return new ListExtractor.InfoItemsPage<>(commentsInfoItemsCollector, PeertubeParsingHelper.getNextPage(page.getUrl(), j));
    }

    public void onFetchPage(Downloader downloader) {
    }
}
