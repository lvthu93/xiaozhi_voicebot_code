package org.schabi.newpipe.extractor.services.soundcloud.linkHandler;

import java.io.IOException;
import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;

public final class SoundcloudCommentsLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final SoundcloudCommentsLinkHandlerFactory a = new SoundcloudCommentsLinkHandlerFactory();

    public static SoundcloudCommentsLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return SoundcloudStreamLinkHandlerFactory.getInstance().getId(str);
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        try {
            return "https://api-v2.soundcloud.com/tracks/" + str + "/comments?client_id=" + SoundcloudParsingHelper.clientId() + "&threaded=0&filter_replies=1";
        } catch (IOException | ExtractionException unused) {
            throw new ParsingException("Could not get comments");
        }
    }

    public boolean onAcceptUrl(String str) {
        try {
            getId(str);
            return true;
        } catch (ParsingException unused) {
            return false;
        }
    }
}
