package org.schabi.newpipe.extractor.services.bandcamp.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper;
import org.schabi.newpipe.extractor.utils.Utils;

public final class BandcampCommentsLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final BandcampCommentsLinkHandlerFactory a = new BandcampCommentsLinkHandlerFactory();

    public static BandcampCommentsLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return Utils.replaceHttpWithHttps(str);
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return Utils.replaceHttpWithHttps(str);
    }

    public boolean onAcceptUrl(String str) throws ParsingException {
        if (BandcampExtractorHelper.isRadioUrl(str)) {
            return true;
        }
        if (!str.toLowerCase().matches("https?://.+\\..+/(track|album)/.+")) {
            return false;
        }
        return BandcampExtractorHelper.isArtistDomain(str);
    }
}
