package org.schabi.newpipe.extractor.services.youtube;

import java.util.Arrays;
import java.util.List;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.comments.CommentsExtractor;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.feed.FeedExtractor;
import org.schabi.newpipe.extractor.kiosk.KioskList;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeCommentsExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeFeedExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMixPlaylistExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMusicSearchExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubePlaylistExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeSearchExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeSubscriptionExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeSuggestionExtractor;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeChannelLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeChannelTabLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeCommentsLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeLiveLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubePlaylistLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeTrendingGamingVideosLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeTrendingLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeTrendingMoviesAndShowsTrailersLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeTrendingMusicLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeTrendingPodcastsEpisodesLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionExtractor;
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor;

public class YoutubeService extends StreamingService {
    public static final List<Localization> c = Localization.listFrom("en-GB");
    public static final List<ContentCountry> d = ContentCountry.listFrom("DZ", "AR", "AU", "AT", "AZ", "BH", "BD", "BY", "BE", "BO", "BA", "BR", "BG", "KH", "CA", "CL", "CO", "CR", "HR", "CY", "CZ", "DK", "DO", "EC", "EG", "SV", "EE", "FI", "FR", "GE", "DE", "GH", "GR", "GT", "HN", "HK", "HU", "IS", "IN", "ID", "IQ", "IE", "IL", "IT", "JM", "JP", "JO", "KZ", "KE", "KW", "LA", "LV", "LB", "LY", "LI", "LT", "LU", "MY", "MT", "MX", "ME", "MA", "NP", "NL", "NZ", "NI", "NG", "MK", "NO", "OM", "PK", "PA", "PG", "PY", "PE", "PH", "PL", "PT", "PR", "QA", "RO", "RU", "SA", "SN", "RS", "SG", "SK", "SI", "ZA", "KR", "ES", "LK", "SE", "CH", "TW", "TZ", "TH", "TN", "TR", "UG", "UA", "AE", "GB", "US", "UY", "VE", "VN", "YE", "ZW");

    public YoutubeService(int i) {
        super(i, "YouTube", Arrays.asList(new StreamingService.ServiceInfo.MediaCapability[]{StreamingService.ServiceInfo.MediaCapability.AUDIO, StreamingService.ServiceInfo.MediaCapability.VIDEO, StreamingService.ServiceInfo.MediaCapability.LIVE, StreamingService.ServiceInfo.MediaCapability.COMMENTS}));
    }

    public String getBaseUrl() {
        return "https://youtube.com";
    }

    public ChannelExtractor getChannelExtractor(ListLinkHandler listLinkHandler) {
        return new YoutubeChannelExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getChannelLHFactory() {
        return YoutubeChannelLinkHandlerFactory.getInstance();
    }

    public ChannelTabExtractor getChannelTabExtractor(ListLinkHandler listLinkHandler) {
        if (listLinkHandler instanceof ReadyChannelTabListLinkHandler) {
            return ((ReadyChannelTabListLinkHandler) listLinkHandler).getChannelTabExtractor(this);
        }
        return new YoutubeChannelTabExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getChannelTabLHFactory() {
        return YoutubeChannelTabLinkHandlerFactory.getInstance();
    }

    public CommentsExtractor getCommentsExtractor(ListLinkHandler listLinkHandler) throws ExtractionException {
        return new YoutubeCommentsExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getCommentsLHFactory() {
        return YoutubeCommentsLinkHandlerFactory.getInstance();
    }

    public FeedExtractor getFeedExtractor(String str) throws ExtractionException {
        return new YoutubeFeedExtractor(this, getChannelLHFactory().fromUrl(str));
    }

    public KioskList getKioskList() throws ExtractionException {
        KioskList kioskList = new KioskList(this);
        YoutubeTrendingLinkHandlerFactory youtubeTrendingLinkHandlerFactory = YoutubeTrendingLinkHandlerFactory.a;
        YoutubeLiveLinkHandlerFactory youtubeLiveLinkHandlerFactory = YoutubeLiveLinkHandlerFactory.a;
        YoutubeTrendingPodcastsEpisodesLinkHandlerFactory youtubeTrendingPodcastsEpisodesLinkHandlerFactory = YoutubeTrendingPodcastsEpisodesLinkHandlerFactory.a;
        YoutubeTrendingGamingVideosLinkHandlerFactory youtubeTrendingGamingVideosLinkHandlerFactory = YoutubeTrendingGamingVideosLinkHandlerFactory.a;
        YoutubeTrendingMoviesAndShowsTrailersLinkHandlerFactory youtubeTrendingMoviesAndShowsTrailersLinkHandlerFactory = YoutubeTrendingMoviesAndShowsTrailersLinkHandlerFactory.a;
        YoutubeTrendingMusicLinkHandlerFactory youtubeTrendingMusicLinkHandlerFactory = YoutubeTrendingMusicLinkHandlerFactory.a;
        try {
            kioskList.addKioskEntry(new hg(this, youtubeLiveLinkHandlerFactory, 0), youtubeLiveLinkHandlerFactory, "live");
            kioskList.addKioskEntry(new hg(this, youtubeTrendingPodcastsEpisodesLinkHandlerFactory, 1), youtubeTrendingPodcastsEpisodesLinkHandlerFactory, "trending_podcasts_episodes");
            kioskList.addKioskEntry(new hg(this, youtubeTrendingGamingVideosLinkHandlerFactory, 2), youtubeTrendingGamingVideosLinkHandlerFactory, "trending_gaming");
            kioskList.addKioskEntry(new hg(this, youtubeTrendingMoviesAndShowsTrailersLinkHandlerFactory, 3), youtubeTrendingMoviesAndShowsTrailersLinkHandlerFactory, "trending_movies_and_shows");
            kioskList.addKioskEntry(new hg(this, youtubeTrendingMusicLinkHandlerFactory, 4), youtubeTrendingMusicLinkHandlerFactory, "trending_music");
            kioskList.addKioskEntry(new hg(this, youtubeTrendingLinkHandlerFactory, 5), youtubeTrendingLinkHandlerFactory, "Trending");
            kioskList.setDefaultKiosk("live");
            return kioskList;
        } catch (Exception e) {
            throw new ExtractionException((Throwable) e);
        }
    }

    public PlaylistExtractor getPlaylistExtractor(ListLinkHandler listLinkHandler) {
        if (YoutubeParsingHelper.isYoutubeMixId(listLinkHandler.getId())) {
            return new YoutubeMixPlaylistExtractor(this, listLinkHandler);
        }
        return new YoutubePlaylistExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return YoutubePlaylistLinkHandlerFactory.getInstance();
    }

    public SearchExtractor getSearchExtractor(SearchQueryHandler searchQueryHandler) {
        List<String> contentFilters = searchQueryHandler.getContentFilters();
        if (contentFilters.isEmpty() || !contentFilters.get(0).startsWith("music_")) {
            return new YoutubeSearchExtractor(this, searchQueryHandler);
        }
        return new YoutubeMusicSearchExtractor(this, searchQueryHandler);
    }

    public SearchQueryHandlerFactory getSearchQHFactory() {
        return YoutubeSearchQueryHandlerFactory.getInstance();
    }

    public StreamExtractor getStreamExtractor(LinkHandler linkHandler) {
        return new YoutubeStreamExtractor(this, linkHandler);
    }

    public LinkHandlerFactory getStreamLHFactory() {
        return YoutubeStreamLinkHandlerFactory.getInstance();
    }

    public SubscriptionExtractor getSubscriptionExtractor() {
        return new YoutubeSubscriptionExtractor(this);
    }

    public SuggestionExtractor getSuggestionExtractor() {
        return new YoutubeSuggestionExtractor(this);
    }

    public List<ContentCountry> getSupportedCountries() {
        return d;
    }

    public List<Localization> getSupportedLocalizations() {
        return c;
    }
}
