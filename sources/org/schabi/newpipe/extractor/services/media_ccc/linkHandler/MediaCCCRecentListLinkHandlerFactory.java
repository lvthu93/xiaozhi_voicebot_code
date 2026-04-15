package org.schabi.newpipe.extractor.services.media_ccc.linkHandler;

import java.util.List;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;

public final class MediaCCCRecentListLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final MediaCCCRecentListLinkHandlerFactory a = new MediaCCCRecentListLinkHandlerFactory();

    public static MediaCCCRecentListLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return "recent";
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return "https://media.ccc.de/recent";
    }

    public boolean onAcceptUrl(String str) {
        return Pattern.matches("^(https?://)?media\\.ccc\\.de/recent/?$", str);
    }
}
