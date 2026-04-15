package org.schabi.newpipe.extractor.services.media_ccc.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;

public final class MediaCCCConferencesListLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final MediaCCCConferencesListLinkHandlerFactory a = new MediaCCCConferencesListLinkHandlerFactory();

    public static MediaCCCConferencesListLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return "conferences";
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return "https://media.ccc.de/public/conferences";
    }

    public boolean onAcceptUrl(String str) {
        if (str.equals("https://media.ccc.de/b/conferences") || str.equals("https://media.ccc.de/public/conferences") || str.equals("https://api.media.ccc.de/public/conferences")) {
            return true;
        }
        return false;
    }
}
