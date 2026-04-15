package org.schabi.newpipe.extractor.services.bandcamp.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.utils.Utils;

public final class BandcampSearchQueryHandlerFactory extends SearchQueryHandlerFactory {
    public static final BandcampSearchQueryHandlerFactory a = new BandcampSearchQueryHandlerFactory();

    public static BandcampSearchQueryHandlerFactory getInstance() {
        return a;
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return "https://bandcamp.com/search?q=" + Utils.encodeUrlUtf8(str) + "&page=1";
    }
}
