package org.schabi.newpipe.extractor.services.media_ccc.linkHandler;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCParsingHelper;
import org.schabi.newpipe.extractor.utils.Parser;

public final class MediaCCCStreamLinkHandlerFactory extends LinkHandlerFactory {
    public static final MediaCCCStreamLinkHandlerFactory a = new MediaCCCStreamLinkHandlerFactory();

    public static MediaCCCStreamLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        String str2;
        try {
            str2 = Parser.matchGroup1("streaming\\.media\\.ccc\\.de\\/(\\w+\\/\\w+)", str);
        } catch (Parser.RegexException unused) {
            str2 = null;
        }
        if (str2 == null) {
            return Parser.matchGroup1("(?:(?:(?:api\\.)?media\\.ccc\\.de/public/events/)|(?:media\\.ccc\\.de/v/))([^/?&#]*)", str);
        }
        return str2;
    }

    public String getUrl(String str) throws ParsingException, UnsupportedOperationException {
        if (MediaCCCParsingHelper.isLiveStreamId(str)) {
            return y2.i("https://streaming.media.ccc.de/", str);
        }
        return y2.i("https://media.ccc.de/v/", str);
    }

    public boolean onAcceptUrl(String str) {
        try {
            return getId(str) != null;
        } catch (ParsingException unused) {
            return false;
        }
    }
}
