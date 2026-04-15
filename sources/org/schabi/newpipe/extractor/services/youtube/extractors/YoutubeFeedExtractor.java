package org.schabi.newpipe.extractor.services.youtube.extractors;

import java.io.IOException;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Response;
import org.schabi.newpipe.extractor.exceptions.ContentNotAvailableException;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.feed.FeedExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;

public class YoutubeFeedExtractor extends FeedExtractor {
    public Document g;

    public YoutubeFeedExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public String getId() {
        return getUrl().replace("https://www.youtube.com/channel/", "");
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() {
        Elements select = this.g.select("feed > entry");
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        Iterator it = select.iterator();
        while (it.hasNext()) {
            streamInfoItemsCollector.commit((StreamInfoItemExtractor) new YoutubeFeedInfoItemExtractor((Element) it.next()));
        }
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, (Page) null);
    }

    public String getName() {
        Element first = this.g.select("feed > author > name").first();
        if (first == null) {
            return "";
        }
        return first.text();
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) {
        return ListExtractor.InfoItemsPage.emptyPage();
    }

    public String getUrl() {
        Element first = this.g.select("feed > author > uri").first();
        if (first != null) {
            String text = first.text();
            if (!text.equals("")) {
                return text;
            }
        }
        Element first2 = this.g.select("feed > link[rel*=alternate]").first();
        if (first2 != null) {
            return first2.attr("href");
        }
        return "";
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        Response response = downloader.get(YoutubeParsingHelper.getFeedUrlFrom(getLinkHandler().getId()));
        if (response.responseCode() != 404) {
            this.g = Jsoup.parse(response.responseBody());
            return;
        }
        throw new ContentNotAvailableException("Could not get feed: 404 - not found");
    }
}
