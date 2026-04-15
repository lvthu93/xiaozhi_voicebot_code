package org.schabi.newpipe.extractor.services.bandcamp.linkHandler;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper;
import org.schabi.newpipe.extractor.utils.Utils;

public final class BandcampStreamLinkHandlerFactory extends LinkHandlerFactory {
    public static final BandcampStreamLinkHandlerFactory a = new BandcampStreamLinkHandlerFactory();

    public static BandcampStreamLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        if (BandcampExtractorHelper.isRadioUrl(str)) {
            return str.split("bandcamp.com/\\?show=")[1];
        }
        return getUrl(str);
    }

    public String getUrl(String str) throws ParsingException, UnsupportedOperationException {
        if (str.matches("\\d+")) {
            return "https://bandcamp.com/?show=".concat(str);
        }
        return Utils.replaceHttpWithHttps(str);
    }

    public boolean onAcceptUrl(String str) throws ParsingException {
        if (BandcampExtractorHelper.isRadioUrl(str)) {
            return true;
        }
        if (!str.toLowerCase().matches("https?://.+\\..+/track/.+")) {
            return false;
        }
        return BandcampExtractorHelper.isArtistDomain(str);
    }
}
