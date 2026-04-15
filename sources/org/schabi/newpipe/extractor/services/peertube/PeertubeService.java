package org.schabi.newpipe.extractor.services.peertube;

import java.util.Arrays;
import java.util.List;
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
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubeAccountExtractor;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubeChannelExtractor;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubeChannelTabExtractor;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubeCommentsExtractor;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubePlaylistExtractor;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubeSearchExtractor;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubeStreamExtractor;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubeSuggestionExtractor;
import org.schabi.newpipe.extractor.services.peertube.linkHandler.PeertubeChannelLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.peertube.linkHandler.PeertubeChannelTabLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.peertube.linkHandler.PeertubeCommentsLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.peertube.linkHandler.PeertubePlaylistLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.peertube.linkHandler.PeertubeSearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.services.peertube.linkHandler.PeertubeStreamLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.peertube.linkHandler.PeertubeTrendingLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionExtractor;
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor;

public class PeertubeService extends StreamingService {
    public PeertubeInstance c;

    public PeertubeService(int i) {
        this(i, PeertubeInstance.c);
    }

    public String getBaseUrl() {
        return this.c.getUrl();
    }

    public ChannelExtractor getChannelExtractor(ListLinkHandler listLinkHandler) throws ExtractionException {
        if (listLinkHandler.getUrl().contains("/video-channels/")) {
            return new PeertubeChannelExtractor(this, listLinkHandler);
        }
        return new PeertubeAccountExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getChannelLHFactory() {
        return PeertubeChannelLinkHandlerFactory.getInstance();
    }

    public ChannelTabExtractor getChannelTabExtractor(ListLinkHandler listLinkHandler) throws ExtractionException {
        return new PeertubeChannelTabExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getChannelTabLHFactory() {
        return PeertubeChannelTabLinkHandlerFactory.getInstance();
    }

    public CommentsExtractor getCommentsExtractor(ListLinkHandler listLinkHandler) throws ExtractionException {
        return new PeertubeCommentsExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getCommentsLHFactory() {
        return PeertubeCommentsLinkHandlerFactory.getInstance();
    }

    public PeertubeInstance getInstance() {
        return this.c;
    }

    public KioskList getKioskList() throws ExtractionException {
        PeertubeTrendingLinkHandlerFactory instance = PeertubeTrendingLinkHandlerFactory.getInstance();
        ah ahVar = new ah(2, this, instance);
        KioskList kioskList = new KioskList(this);
        try {
            kioskList.addKioskEntry(ahVar, instance, "Trending");
            kioskList.addKioskEntry(ahVar, instance, "Most liked");
            kioskList.addKioskEntry(ahVar, instance, "Recently added");
            kioskList.addKioskEntry(ahVar, instance, "Local");
            kioskList.setDefaultKiosk("Trending");
            return kioskList;
        } catch (Exception e) {
            throw new ExtractionException((Throwable) e);
        }
    }

    public PlaylistExtractor getPlaylistExtractor(ListLinkHandler listLinkHandler) throws ExtractionException {
        return new PeertubePlaylistExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return PeertubePlaylistLinkHandlerFactory.getInstance();
    }

    public SearchExtractor getSearchExtractor(SearchQueryHandler searchQueryHandler) {
        List<String> contentFilters = searchQueryHandler.getContentFilters();
        boolean z = false;
        if (!contentFilters.isEmpty() && contentFilters.get(0).startsWith("sepia_")) {
            z = true;
        }
        return new PeertubeSearchExtractor(this, searchQueryHandler, z);
    }

    public SearchQueryHandlerFactory getSearchQHFactory() {
        return PeertubeSearchQueryHandlerFactory.getInstance();
    }

    public StreamExtractor getStreamExtractor(LinkHandler linkHandler) throws ExtractionException {
        return new PeertubeStreamExtractor(this, linkHandler);
    }

    public LinkHandlerFactory getStreamLHFactory() {
        return PeertubeStreamLinkHandlerFactory.getInstance();
    }

    public SubscriptionExtractor getSubscriptionExtractor() {
        return null;
    }

    public SuggestionExtractor getSuggestionExtractor() {
        return new PeertubeSuggestionExtractor(this);
    }

    public void setInstance(PeertubeInstance peertubeInstance) {
        this.c = peertubeInstance;
    }

    public PeertubeService(int i, PeertubeInstance peertubeInstance) {
        super(i, "PeerTube", Arrays.asList(new StreamingService.ServiceInfo.MediaCapability[]{StreamingService.ServiceInfo.MediaCapability.VIDEO, StreamingService.ServiceInfo.MediaCapability.COMMENTS}));
        this.c = peertubeInstance;
    }
}
