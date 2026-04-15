package org.schabi.newpipe.extractor.services.soundcloud;

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
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudChannelExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudChannelTabExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudCommentsExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudPlaylistExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudSearchExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudStreamExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudSubscriptionExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudSuggestionExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.linkHandler.SoundcloudChannelLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.soundcloud.linkHandler.SoundcloudChannelTabLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.soundcloud.linkHandler.SoundcloudChartsLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.soundcloud.linkHandler.SoundcloudCommentsLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.soundcloud.linkHandler.SoundcloudPlaylistLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.soundcloud.linkHandler.SoundcloudSearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.services.soundcloud.linkHandler.SoundcloudStreamLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionExtractor;

public class SoundcloudService extends StreamingService {
    public SoundcloudService(int i) {
        super(i, "SoundCloud", Arrays.asList(new StreamingService.ServiceInfo.MediaCapability[]{StreamingService.ServiceInfo.MediaCapability.AUDIO, StreamingService.ServiceInfo.MediaCapability.COMMENTS}));
    }

    public String getBaseUrl() {
        return "https://soundcloud.com";
    }

    public ChannelExtractor getChannelExtractor(ListLinkHandler listLinkHandler) {
        return new SoundcloudChannelExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getChannelLHFactory() {
        return SoundcloudChannelLinkHandlerFactory.getInstance();
    }

    public ChannelTabExtractor getChannelTabExtractor(ListLinkHandler listLinkHandler) {
        return new SoundcloudChannelTabExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getChannelTabLHFactory() {
        return SoundcloudChannelTabLinkHandlerFactory.getInstance();
    }

    public CommentsExtractor getCommentsExtractor(ListLinkHandler listLinkHandler) throws ExtractionException {
        return new SoundcloudCommentsExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getCommentsLHFactory() {
        return SoundcloudCommentsLinkHandlerFactory.getInstance();
    }

    public KioskList getKioskList() throws ExtractionException {
        KioskList kioskList = new KioskList(this);
        SoundcloudChartsLinkHandlerFactory instance = SoundcloudChartsLinkHandlerFactory.getInstance();
        try {
            kioskList.addKioskEntry(new ah(3, this, instance), instance, "New & hot");
            kioskList.setDefaultKiosk("New & hot");
            return kioskList;
        } catch (Exception e) {
            throw new ExtractionException((Throwable) e);
        }
    }

    public PlaylistExtractor getPlaylistExtractor(ListLinkHandler listLinkHandler) {
        return new SoundcloudPlaylistExtractor(this, listLinkHandler);
    }

    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return SoundcloudPlaylistLinkHandlerFactory.getInstance();
    }

    public SearchExtractor getSearchExtractor(SearchQueryHandler searchQueryHandler) {
        return new SoundcloudSearchExtractor(this, searchQueryHandler);
    }

    public SearchQueryHandlerFactory getSearchQHFactory() {
        return SoundcloudSearchQueryHandlerFactory.getInstance();
    }

    public StreamExtractor getStreamExtractor(LinkHandler linkHandler) {
        return new SoundcloudStreamExtractor(this, linkHandler);
    }

    public LinkHandlerFactory getStreamLHFactory() {
        return SoundcloudStreamLinkHandlerFactory.getInstance();
    }

    public SubscriptionExtractor getSubscriptionExtractor() {
        return new SoundcloudSubscriptionExtractor(this);
    }

    public List<ContentCountry> getSupportedCountries() {
        return ContentCountry.listFrom("AU", "CA", "DE", "FR", "GB", "IE", "NL", "NZ", "US");
    }

    public SoundcloudSuggestionExtractor getSuggestionExtractor() {
        return new SoundcloudSuggestionExtractor(this);
    }
}
