package org.schabi.newpipe.extractor.services.media_ccc;

import com.grack.nanojson.JsonObject;
import java.util.Arrays;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.comments.CommentsExtractor;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.kiosk.KioskList;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCChannelTabExtractor;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCConferenceExtractor;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCLiveStreamExtractor;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCParsingHelper;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCSearchExtractor;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCStreamExtractor;
import org.schabi.newpipe.extractor.services.media_ccc.linkHandler.MediaCCCConferenceLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.media_ccc.linkHandler.MediaCCCConferencesListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.media_ccc.linkHandler.MediaCCCSearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.services.media_ccc.linkHandler.MediaCCCStreamLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionExtractor;
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor;

public class MediaCCCService extends StreamingService {
    public MediaCCCService(int i) {
        super(i, "media.ccc.de", Arrays.asList(new StreamingService.ServiceInfo.MediaCapability[]{StreamingService.ServiceInfo.MediaCapability.AUDIO, StreamingService.ServiceInfo.MediaCapability.VIDEO}));
    }

    public String getBaseUrl() {
        return "https://media.ccc.de";
    }

    public ChannelExtractor getChannelExtractor(ListLinkHandler listLinkHandler) {
        return new MediaCCCConferenceExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getChannelLHFactory() {
        return MediaCCCConferenceLinkHandlerFactory.getInstance();
    }

    public ChannelTabExtractor getChannelTabExtractor(ListLinkHandler listLinkHandler) {
        if (listLinkHandler instanceof ReadyChannelTabListLinkHandler) {
            return ((ReadyChannelTabListLinkHandler) listLinkHandler).getChannelTabExtractor(this);
        }
        return new MediaCCCChannelTabExtractor(this, listLinkHandler, (JsonObject) null);
    }

    public ListLinkHandlerFactory getChannelTabLHFactory() {
        return MediaCCCConferenceLinkHandlerFactory.getInstance();
    }

    public CommentsExtractor getCommentsExtractor(ListLinkHandler listLinkHandler) {
        return null;
    }

    public ListLinkHandlerFactory getCommentsLHFactory() {
        return null;
    }

    public KioskList getKioskList() throws ExtractionException {
        KioskList kioskList = new KioskList(this);
        MediaCCCConferencesListLinkHandlerFactory instance = MediaCCCConferencesListLinkHandlerFactory.getInstance();
        try {
            kioskList.addKioskEntry(new v5(this, instance, 0), instance, "conferences");
            kioskList.addKioskEntry(new v5(this, instance, 1), instance, "recent");
            kioskList.addKioskEntry(new v5(this, instance, 2), instance, "live");
            kioskList.setDefaultKiosk("recent");
            return kioskList;
        } catch (Exception e) {
            throw new ExtractionException((Throwable) e);
        }
    }

    public PlaylistExtractor getPlaylistExtractor(ListLinkHandler listLinkHandler) {
        return null;
    }

    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return null;
    }

    public SearchExtractor getSearchExtractor(SearchQueryHandler searchQueryHandler) {
        return new MediaCCCSearchExtractor(this, searchQueryHandler);
    }

    public SearchQueryHandlerFactory getSearchQHFactory() {
        return MediaCCCSearchQueryHandlerFactory.getInstance();
    }

    public StreamExtractor getStreamExtractor(LinkHandler linkHandler) {
        if (MediaCCCParsingHelper.isLiveStreamId(linkHandler.getId())) {
            return new MediaCCCLiveStreamExtractor(this, linkHandler);
        }
        return new MediaCCCStreamExtractor(this, linkHandler);
    }

    public LinkHandlerFactory getStreamLHFactory() {
        return MediaCCCStreamLinkHandlerFactory.getInstance();
    }

    public SubscriptionExtractor getSubscriptionExtractor() {
        return null;
    }

    public SuggestionExtractor getSuggestionExtractor() {
        return null;
    }
}
