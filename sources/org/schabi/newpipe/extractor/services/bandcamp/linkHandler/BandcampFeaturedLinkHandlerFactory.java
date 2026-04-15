package org.schabi.newpipe.extractor.services.bandcamp.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper;
import org.schabi.newpipe.extractor.utils.Utils;

public final class BandcampFeaturedLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final BandcampFeaturedLinkHandlerFactory a = new BandcampFeaturedLinkHandlerFactory();

    public static BandcampFeaturedLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        String replaceHttpWithHttps = Utils.replaceHttpWithHttps(str);
        if (BandcampExtractorHelper.isRadioUrl(replaceHttpWithHttps) || replaceHttpWithHttps.equals("https://bandcamp.com/api/bcweekly/3/list")) {
            return "Radio";
        }
        if (replaceHttpWithHttps.equals("https://bandcamp.com/api/mobile/24/bootstrap_data")) {
            return "Featured";
        }
        return null;
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        if (str.equals("Featured")) {
            return "https://bandcamp.com/api/mobile/24/bootstrap_data";
        }
        if (str.equals("Radio")) {
            return "https://bandcamp.com/api/bcweekly/3/list";
        }
        return null;
    }

    public boolean onAcceptUrl(String str) {
        String replaceHttpWithHttps = Utils.replaceHttpWithHttps(str);
        if (replaceHttpWithHttps.equals("https://bandcamp.com/api/mobile/24/bootstrap_data") || replaceHttpWithHttps.equals("https://bandcamp.com/api/bcweekly/3/list") || BandcampExtractorHelper.isRadioUrl(replaceHttpWithHttps)) {
            return true;
        }
        return false;
    }
}
