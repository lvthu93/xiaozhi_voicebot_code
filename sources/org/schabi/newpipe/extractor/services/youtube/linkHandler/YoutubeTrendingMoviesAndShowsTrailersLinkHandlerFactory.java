package org.schabi.newpipe.extractor.services.youtube.linkHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Utils;

public final class YoutubeTrendingMoviesAndShowsTrailersLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final YoutubeTrendingMoviesAndShowsTrailersLinkHandlerFactory a = new YoutubeTrendingMoviesAndShowsTrailersLinkHandlerFactory();

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return "trending_movies_and_shows";
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return "https://charts.youtube.com/charts/TrendingTrailers";
    }

    public boolean onAcceptUrl(String str) throws ParsingException {
        try {
            URL stringToURL = Utils.stringToURL(str);
            if (!Utils.isHTTP(stringToURL) || !"charts.youtube.com".equals(stringToURL.getHost().toLowerCase(Locale.ROOT)) || !"/charts/TrendingTrailers".equals(stringToURL.getPath())) {
                return false;
            }
            return true;
        } catch (MalformedURLException unused) {
            return false;
        }
    }
}
