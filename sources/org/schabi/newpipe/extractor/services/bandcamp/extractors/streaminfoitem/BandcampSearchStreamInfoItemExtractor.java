package org.schabi.newpipe.extractor.services.bandcamp.extractors.streaminfoitem;

import java.util.List;
import org.jsoup.nodes.Element;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper;

public class BandcampSearchStreamInfoItemExtractor extends BandcampStreamInfoItemExtractor {
    public final Element b;
    public final Element c;

    public BandcampSearchStreamInfoItemExtractor(Element element, String str) {
        super(str);
        this.c = element;
        this.b = element.getElementsByClass("result-info").first();
    }

    public long getDuration() {
        return -1;
    }

    public String getName() throws ParsingException {
        return this.b.getElementsByClass("heading").text();
    }

    public List<Image> getThumbnails() throws ParsingException {
        return BandcampExtractorHelper.getImagesFromSearchResult(this.c);
    }

    public String getUploaderName() {
        String[] split = this.b.getElementsByClass("subhead").text().split("by ");
        if (split.length > 1) {
            return split[1];
        }
        return split[0];
    }

    public String getUrl() throws ParsingException {
        return this.b.getElementsByClass("itemurl").text();
    }
}
