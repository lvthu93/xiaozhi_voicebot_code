package org.schabi.newpipe.extractor.services.youtube.linkHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Utils;

public final class YoutubeTrendingMusicLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final YoutubeTrendingMusicLinkHandlerFactory a = new YoutubeTrendingMusicLinkHandlerFactory();

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return "trending_music";
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return "https://charts.youtube.com/charts/TrendingVideos/RightNow";
    }

    public boolean onAcceptUrl(String str) throws ParsingException {
        try {
            URL stringToURL = Utils.stringToURL(str);
            if (!Utils.isHTTP(stringToURL) || !"charts.youtube.com".equals(stringToURL.getHost().toLowerCase(Locale.ROOT)) || !stringToURL.getPath().startsWith("/charts/TrendingVideos")) {
                return false;
            }
            return true;
        } catch (MalformedURLException unused) {
            return false;
        }
    }
}
