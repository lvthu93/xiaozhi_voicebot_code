package org.schabi.newpipe.extractor.services.media_ccc.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Parser;

public final class MediaCCCConferenceLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final MediaCCCConferenceLinkHandlerFactory a = new MediaCCCConferenceLinkHandlerFactory();

    public static MediaCCCConferenceLinkHandlerFactory getInstance() {
        return a;
    }

    public String[] getAvailableContentFilter() {
        return new String[]{"videos"};
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return Parser.matchGroup1("(?:(?:(?:api\\.)?media\\.ccc\\.de/public/conferences/)|(?:media\\.ccc\\.de/[bc]/))([^/?&#]*)", str);
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return y2.i("https://media.ccc.de/c/", str);
    }

    public boolean onAcceptUrl(String str) {
        try {
            return getId(str) != null;
        } catch (ParsingException unused) {
            return false;
        }
    }
}
