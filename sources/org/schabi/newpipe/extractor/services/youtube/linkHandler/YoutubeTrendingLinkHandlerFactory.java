package org.schabi.newpipe.extractor.services.youtube.linkHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.utils.Utils;

public final class YoutubeTrendingLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final YoutubeTrendingLinkHandlerFactory a = new YoutubeTrendingLinkHandlerFactory();

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return "Trending";
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return "https://www.youtube.com/feed/trending";
    }

    public boolean onAcceptUrl(String str) {
        try {
            URL stringToURL = Utils.stringToURL(str);
            String path = stringToURL.getPath();
            if (!Utils.isHTTP(stringToURL)) {
                return false;
            }
            if ((YoutubeParsingHelper.isYoutubeURL(stringToURL) || YoutubeParsingHelper.isInvidiousURL(stringToURL)) && path.equals("/feed/trending")) {
                return true;
            }
            return false;
        } catch (MalformedURLException unused) {
            return false;
        }
    }
}
