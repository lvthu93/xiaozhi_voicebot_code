package org.schabi.newpipe.extractor.services.youtube.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.FoundAdException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;

public final class YoutubeCommentsLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final YoutubeCommentsLinkHandlerFactory a = new YoutubeCommentsLinkHandlerFactory();

    public static YoutubeCommentsLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return YoutubeStreamLinkHandlerFactory.getInstance().getId(str);
    }

    public String getUrl(String str) throws ParsingException, UnsupportedOperationException {
        return y2.i("https://www.youtube.com/watch?v=", str);
    }

    public boolean onAcceptUrl(String str) throws FoundAdException {
        try {
            getId(str);
            return true;
        } catch (FoundAdException e) {
            throw e;
        } catch (ParsingException unused) {
            return false;
        }
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return getUrl(str);
    }
}
