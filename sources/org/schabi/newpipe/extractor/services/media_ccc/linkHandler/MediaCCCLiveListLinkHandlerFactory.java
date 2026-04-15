package org.schabi.newpipe.extractor.services.media_ccc.linkHandler;

import java.util.List;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;

public final class MediaCCCLiveListLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final MediaCCCLiveListLinkHandlerFactory a = new MediaCCCLiveListLinkHandlerFactory();

    public static MediaCCCLiveListLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return "live";
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return "https://media.ccc.de/live";
    }

    public boolean onAcceptUrl(String str) throws ParsingException {
        return Pattern.matches("^(?:https?://)?media\\.ccc\\.de/live$", str);
    }
}
