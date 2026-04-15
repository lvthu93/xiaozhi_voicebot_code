package org.schabi.newpipe.extractor.services.bandcamp;

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
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampChannelExtractor;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampChannelTabExtractor;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampCommentsExtractor;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampPlaylistExtractor;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampRadioStreamExtractor;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampSearchExtractor;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampStreamExtractor;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampSuggestionExtractor;
import org.schabi.newpipe.extractor.services.bandcamp.linkHandler.BandcampChannelLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.bandcamp.linkHandler.BandcampChannelTabLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.bandcamp.linkHandler.BandcampCommentsLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.bandcamp.linkHandler.BandcampFeaturedLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.bandcamp.linkHandler.BandcampPlaylistLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.bandcamp.linkHandler.BandcampSearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.services.bandcamp.linkHandler.BandcampStreamLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionExtractor;
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor;

public class BandcampService extends StreamingService {
    public BandcampService(int i) {
        super(i, "Bandcamp", Arrays.asList(new StreamingService.ServiceInfo.MediaCapability[]{StreamingService.ServiceInfo.MediaCapability.AUDIO, StreamingService.ServiceInfo.MediaCapability.COMMENTS}));
    }

    public String getBaseUrl() {
        return "https://bandcamp.com";
    }

    public ChannelExtractor getChannelExtractor(ListLinkHandler listLinkHandler) {
        return new BandcampChannelExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getChannelLHFactory() {
        return BandcampChannelLinkHandlerFactory.getInstance();
    }

    public ChannelTabExtractor getChannelTabExtractor(ListLinkHandler listLinkHandler) {
        if (listLinkHandler instanceof ReadyChannelTabListLinkHandler) {
            return ((ReadyChannelTabListLinkHandler) listLinkHandler).getChannelTabExtractor(this);
        }
        return new BandcampChannelTabExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getChannelTabLHFactory() {
        return BandcampChannelTabLinkHandlerFactory.getInstance();
    }

    public CommentsExtractor getCommentsExtractor(ListLinkHandler listLinkHandler) {
        return new BandcampCommentsExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getCommentsLHFactory() {
        return BandcampCommentsLinkHandlerFactory.getInstance();
    }

    public KioskList getKioskList() throws ExtractionException {
        KioskList kioskList = new KioskList(this);
        BandcampFeaturedLinkHandlerFactory instance = BandcampFeaturedLinkHandlerFactory.getInstance();
        try {
            kioskList.addKioskEntry(new cd(this, instance, 0), instance, "Featured");
            kioskList.addKioskEntry(new cd(this, instance, 1), instance, "Radio");
            kioskList.setDefaultKiosk("Featured");
            return kioskList;
        } catch (Exception e) {
            throw new ExtractionException((Throwable) e);
        }
    }

    public PlaylistExtractor getPlaylistExtractor(ListLinkHandler listLinkHandler) {
        return new BandcampPlaylistExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return BandcampPlaylistLinkHandlerFactory.getInstance();
    }

    public SearchExtractor getSearchExtractor(SearchQueryHandler searchQueryHandler) {
        return new BandcampSearchExtractor(this, searchQueryHandler);
    }

    public SearchQueryHandlerFactory getSearchQHFactory() {
        return BandcampSearchQueryHandlerFactory.getInstance();
    }

    public StreamExtractor getStreamExtractor(LinkHandler linkHandler) {
        if (BandcampExtractorHelper.isRadioUrl(linkHandler.getUrl())) {
            return new BandcampRadioStreamExtractor(this, linkHandler);
        }
        return new BandcampStreamExtractor(this, linkHandler);
    }

    public LinkHandlerFactory getStreamLHFactory() {
        return BandcampStreamLinkHandlerFactory.getInstance();
    }

    public SubscriptionExtractor getSubscriptionExtractor() {
        return null;
    }

    public SuggestionExtractor getSuggestionExtractor() {
        return new BandcampSuggestionExtractor(this);
    }
}
