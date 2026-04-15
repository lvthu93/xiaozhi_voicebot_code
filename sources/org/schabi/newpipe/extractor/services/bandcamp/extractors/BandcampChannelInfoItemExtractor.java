package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import java.util.List;
import org.jsoup.nodes.Element;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.channel.ChannelInfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

public class BandcampChannelInfoItemExtractor implements ChannelInfoItemExtractor {
    public final Element a;
    public final Element b;

    public BandcampChannelInfoItemExtractor(Element element) {
        this.b = element;
        this.a = element.getElementsByClass("result-info").first();
    }

    public String getDescription() {
        return this.a.getElementsByClass("subhead").text();
    }

    public String getName() throws ParsingException {
        return this.a.getElementsByClass("heading").text();
    }

    public long getStreamCount() {
        return -1;
    }

    public long getSubscriberCount() {
        return -1;
    }

    public List<Image> getThumbnails() throws ParsingException {
        return BandcampExtractorHelper.getImagesFromSearchResult(this.b);
    }

    public String getUrl() throws ParsingException {
        return this.a.getElementsByClass("itemurl").text();
    }

    public boolean isVerified() throws ParsingException {
        return false;
    }
}
