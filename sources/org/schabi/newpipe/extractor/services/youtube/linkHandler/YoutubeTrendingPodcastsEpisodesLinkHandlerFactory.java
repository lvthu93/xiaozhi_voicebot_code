package org.schabi.newpipe.extractor.services.youtube.linkHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.utils.Utils;

public final class YoutubeTrendingPodcastsEpisodesLinkHandlerFactory extends ListLinkHandlerFactory {
    public static final YoutubeTrendingPodcastsEpisodesLinkHandlerFactory a = new YoutubeTrendingPodcastsEpisodesLinkHandlerFactory();

    public String getId(String str) throws ParsingException, UnsupportedOperationException {
        return "trending_podcasts_episodes";
    }

    public String getUrl(String str, List<String> list, String str2) throws ParsingException, UnsupportedOperationException {
        return "https://www.youtube.com/podcasts/popularepisodes";
    }

    public boolean onAcceptUrl(String str) {
        try {
            URL stringToURL = Utils.stringToURL(str);
            if (!Utils.isHTTP(stringToURL)) {
                return false;
            }
            if ((YoutubeParsingHelper.isYoutubeURL(stringToURL) || YoutubeParsingHelper.isInvidiousURL(stringToURL)) && "/podcasts/popularepisodes".equals(stringToURL.getPath())) {
                return true;
            }
            return false;
        } catch (MalformedURLException unused) {
            return false;
        }
    }
}
