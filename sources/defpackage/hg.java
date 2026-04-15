package defpackage;

import java.util.List;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.kiosk.KioskList;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.services.youtube.YoutubeService;
import org.schabi.newpipe.extractor.services.youtube.extractors.kiosk.YoutubeLiveExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.kiosk.YoutubeTrendingExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.kiosk.YoutubeTrendingGamingVideosExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.kiosk.YoutubeTrendingMoviesAndShowsTrailersExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.kiosk.YoutubeTrendingMusicExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.kiosk.YoutubeTrendingPodcastsEpisodesExtractor;

/* renamed from: hg  reason: default package */
public final /* synthetic */ class hg implements KioskList.KioskExtractorFactory {
    public final /* synthetic */ int c;
    public final /* synthetic */ YoutubeService f;
    public final /* synthetic */ ListLinkHandlerFactory g;

    public /* synthetic */ hg(YoutubeService youtubeService, ListLinkHandlerFactory listLinkHandlerFactory, int i) {
        this.c = i;
        this.f = youtubeService;
        this.g = listLinkHandlerFactory;
    }

    public final KioskExtractor createNewKiosk(StreamingService streamingService, String str, String str2) {
        int i = this.c;
        ListLinkHandlerFactory listLinkHandlerFactory = this.g;
        YoutubeService youtubeService = this.f;
        switch (i) {
            case 0:
                List<Localization> list = YoutubeService.c;
                youtubeService.getClass();
                return new YoutubeLiveExtractor(youtubeService, listLinkHandlerFactory.fromUrl(str), str2);
            case 1:
                List<Localization> list2 = YoutubeService.c;
                youtubeService.getClass();
                return new YoutubeTrendingPodcastsEpisodesExtractor(youtubeService, listLinkHandlerFactory.fromUrl(str), str2);
            case 2:
                List<Localization> list3 = YoutubeService.c;
                youtubeService.getClass();
                return new YoutubeTrendingGamingVideosExtractor(youtubeService, listLinkHandlerFactory.fromUrl(str), str2);
            case 3:
                List<Localization> list4 = YoutubeService.c;
                youtubeService.getClass();
                return new YoutubeTrendingMoviesAndShowsTrailersExtractor(youtubeService, listLinkHandlerFactory.fromUrl(str), str2);
            case 4:
                List<Localization> list5 = YoutubeService.c;
                youtubeService.getClass();
                return new YoutubeTrendingMusicExtractor(youtubeService, listLinkHandlerFactory.fromUrl(str), str2);
            default:
                List<Localization> list6 = YoutubeService.c;
                youtubeService.getClass();
                return new YoutubeTrendingExtractor(youtubeService, listLinkHandlerFactory.fromUrl(str), str2);
        }
    }
}
