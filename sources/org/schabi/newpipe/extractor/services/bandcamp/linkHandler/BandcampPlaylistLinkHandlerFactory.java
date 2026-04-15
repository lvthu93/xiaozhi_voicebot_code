package org.schabi.newpipe.extractor.services.bandcamp.linkHandler;

import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper;
import org.schabi.newpipe.extractor.utils.Utils;

public final class BandcampPlaylistLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final BandcampPlaylistLinkHandlerFactory a = new BandcampPlaylistLinkHandlerFactory();

    public static BandcampPlaylistLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return getUrl(str);
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return Utils.replaceHttpWithHttps(str);
    }

    public boolean onAcceptUrl(String str) throws ParsingException {
        if (!str.toLowerCase().matches("https?://.+\\..+/album/.+")) {
            return false;
        }
        return BandcampExtractorHelper.isArtistDomain(str);
    }
}
