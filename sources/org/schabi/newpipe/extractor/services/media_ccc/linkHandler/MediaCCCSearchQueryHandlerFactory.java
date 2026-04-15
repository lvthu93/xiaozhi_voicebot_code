package org.schabi.newpipe.extractor.services.media_ccc.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.utils.Utils;

public final class MediaCCCSearchQueryHandlerFactory extends SearchQueryHandlerFactory {
    public static final MediaCCCSearchQueryHandlerFactory a = new MediaCCCSearchQueryHandlerFactory();

    public static MediaCCCSearchQueryHandlerFactory getInstance() {
        return a;
    }

    public String[] getAvailableContentFilter() {
        return new String[]{"all", "conferences", "events"};
    }

    public String[] getAvailableSortFilter() {
        return new String[0];
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return "https://media.ccc.de/public/events/search?q=" + Utils.encodeUrlUtf8(str);
    }
}
