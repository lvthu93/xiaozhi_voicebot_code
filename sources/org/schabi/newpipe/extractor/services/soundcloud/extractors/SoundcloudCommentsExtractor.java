package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import java.io.IOException;
import java.util.Iterator;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.comments.CommentsExtractor;
import org.schabi.newpipe.extractor.comments.CommentsInfoItem;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemExtractor;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemsCollector;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.utils.Utils;

public class SoundcloudCommentsExtractor extends CommentsExtractor {
    public SoundcloudCommentsExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public final ListExtractor.InfoItemsPage<CommentsInfoItem> b(String str) throws ParsingException, IOException, ReCaptchaException {
        try {
            JsonObject from = JsonParser.object().from(NewPipe.getDownloader().get(str).responseBody());
            CommentsInfoItemsCollector commentsInfoItemsCollector = new CommentsInfoItemsCollector(getServiceId());
            JsonArray array = from.getArray("collection");
            String url = getUrl();
            Iterator it = array.iterator();
            while (it.hasNext()) {
                commentsInfoItemsCollector.commit((CommentsInfoItemExtractor) new SoundcloudCommentsInfoItemExtractor((JsonObject) it.next(), url));
            }
            return new ListExtractor.InfoItemsPage<>(commentsInfoItemsCollector, new Page(from.getString("next_href", (String) null)));
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse json", e);
        }
    }

    public ListExtractor.InfoItemsPage<CommentsInfoItem> getInitialPage() throws ExtractionException, IOException {
        return b(getUrl());
    }

    public ListExtractor.InfoItemsPage<CommentsInfoItem> getPage(Page page) throws ExtractionException, IOException {
        if (page != null && !Utils.isNullOrEmpty(page.getUrl())) {
            return b(page.getUrl());
        }
        throw new IllegalArgumentException("Page doesn't contain an URL");
    }

    public void onFetchPage(Downloader downloader) {
    }
}
