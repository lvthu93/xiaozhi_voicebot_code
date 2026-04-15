package org.schabi.newpipe.extractor.services.youtube.linkHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.utils.Utils;

public final class YoutubePlaylistLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final YoutubePlaylistLinkHandlerFactory a = new YoutubePlaylistLinkHandlerFactory();

    public static YoutubePlaylistLinkHandlerFactory getInstance() {
        return a;
    }

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        try {
            URL stringToURL = Utils.stringToURL(str);
            if (!Utils.isHTTP(stringToURL) || (!YoutubeParsingHelper.isYoutubeURL(stringToURL) && !YoutubeParsingHelper.isInvidiousURL(stringToURL))) {
                throw new ParsingException("the url given is not a YouTube-URL");
            }
            String path = stringToURL.getPath();
            if (!path.equals("/watch")) {
                if (!path.equals("/playlist")) {
                    throw new ParsingException("the url given is neither a video nor a playlist URL");
                }
            }
            String queryValue = Utils.getQueryValue(stringToURL, "list");
            if (queryValue == null) {
                throw new ParsingException("the URL given does not include a playlist");
            } else if (queryValue.matches("[a-zA-Z0-9_-]{10,}")) {
                return queryValue;
            } else {
                throw new ParsingException("the list-ID given in the URL does not match the list pattern");
            }
        } catch (Exception e) {
            throw new ParsingException("Error could not parse URL: " + e.getMessage(), e);
        }
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return y2.i("https://www.youtube.com/playlist?list=", str);
    }

    public boolean onAcceptUrl(String str) {
        try {
            getId(str);
            return true;
        } catch (ParsingException unused) {
            return false;
        }
    }

    public ListLinkHandler fromUrl(String str) throws ParsingException {
        try {
            URL stringToURL = Utils.stringToURL(str);
            String queryValue = Utils.getQueryValue(stringToURL, "list");
            if (queryValue == null || !YoutubeParsingHelper.isYoutubeMixId(queryValue)) {
                return super.fromUrl(str);
            }
            String queryValue2 = Utils.getQueryValue(stringToURL, "v");
            if (queryValue2 == null) {
                queryValue2 = YoutubeParsingHelper.extractVideoIdFromMixId(queryValue);
            }
            return new ListLinkHandler(new LinkHandler(str, "https://www.youtube.com/watch?v=" + queryValue2 + "&list=" + queryValue, queryValue));
        } catch (MalformedURLException e) {
            throw new ParsingException("Error could not parse URL: " + e.getMessage(), e);
        }
    }
}
