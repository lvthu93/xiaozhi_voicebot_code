package org.schabi.newpipe.extractor;

import java.util.Collections;
import java.util.List;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.comments.CommentsExtractor;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.feed.FeedExtractor;
import org.schabi.newpipe.extractor.kiosk.KioskList;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.localization.TimeAgoPatternsManager;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionExtractor;
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor;
import org.schabi.newpipe.extractor.utils.Utils;

public abstract class StreamingService {
    public final int a;
    public final ServiceInfo b;

    public enum LinkType {
        NONE,
        STREAM,
        CHANNEL,
        PLAYLIST
    }

    public static class ServiceInfo {
        public final String a;
        public final List<MediaCapability> b;

        public enum MediaCapability {
            AUDIO,
            VIDEO,
            LIVE,
            COMMENTS
        }

        public ServiceInfo(String str, List<MediaCapability> list) {
            this.a = str;
            this.b = Collections.unmodifiableList(list);
        }

        public List<MediaCapability> getMediaCapabilities() {
            return this.b;
        }

        public String getName() {
            return this.a;
        }
    }

    public StreamingService(int i, String str, List<ServiceInfo.MediaCapability> list) {
        this.a = i;
        this.b = new ServiceInfo(str, list);
    }

    public abstract String getBaseUrl();

    public ChannelExtractor getChannelExtractor(String str, List<String> list, String str2) throws ExtractionException {
        return getChannelExtractor(getChannelLHFactory().fromQuery(str, list, str2));
    }

    public abstract ChannelExtractor getChannelExtractor(ListLinkHandler listLinkHandler) throws ExtractionException;

    public abstract ListLinkHandlerFactory getChannelLHFactory();

    public abstract ChannelTabExtractor getChannelTabExtractor(ListLinkHandler listLinkHandler) throws ExtractionException;

    public ChannelTabExtractor getChannelTabExtractorFromId(String str, String str2) throws ExtractionException {
        return getChannelTabExtractor(getChannelTabLHFactory().fromQuery(str, Collections.singletonList(str2), ""));
    }

    public ChannelTabExtractor getChannelTabExtractorFromIdAndBaseUrl(String str, String str2, String str3) throws ExtractionException {
        return getChannelTabExtractor(getChannelTabLHFactory().fromQuery(str, Collections.singletonList(str2), "", str3));
    }

    public abstract ListLinkHandlerFactory getChannelTabLHFactory();

    public CommentsExtractor getCommentsExtractor(String str) throws ExtractionException {
        ListLinkHandlerFactory commentsLHFactory = getCommentsLHFactory();
        if (commentsLHFactory == null) {
            return null;
        }
        return getCommentsExtractor(commentsLHFactory.fromUrl(str));
    }

    public abstract CommentsExtractor getCommentsExtractor(ListLinkHandler listLinkHandler) throws ExtractionException;

    public abstract ListLinkHandlerFactory getCommentsLHFactory();

    public ContentCountry getContentCountry() {
        ContentCountry preferredContentCountry = NewPipe.getPreferredContentCountry();
        if (getSupportedCountries().contains(preferredContentCountry)) {
            return preferredContentCountry;
        }
        return ContentCountry.f;
    }

    public FeedExtractor getFeedExtractor(String str) throws ExtractionException {
        return null;
    }

    public abstract KioskList getKioskList() throws ExtractionException;

    public final LinkType getLinkTypeByUrl(String str) throws ParsingException {
        String followGoogleRedirectIfNeeded = Utils.followGoogleRedirectIfNeeded(str);
        LinkHandlerFactory streamLHFactory = getStreamLHFactory();
        ListLinkHandlerFactory channelLHFactory = getChannelLHFactory();
        ListLinkHandlerFactory playlistLHFactory = getPlaylistLHFactory();
        if (streamLHFactory != null && streamLHFactory.acceptUrl(followGoogleRedirectIfNeeded)) {
            return LinkType.STREAM;
        }
        if (channelLHFactory != null && channelLHFactory.acceptUrl(followGoogleRedirectIfNeeded)) {
            return LinkType.CHANNEL;
        }
        if (playlistLHFactory == null || !playlistLHFactory.acceptUrl(followGoogleRedirectIfNeeded)) {
            return LinkType.NONE;
        }
        return LinkType.PLAYLIST;
    }

    public Localization getLocalization() {
        Localization preferredLocalization = NewPipe.getPreferredLocalization();
        if (getSupportedLocalizations().contains(preferredLocalization)) {
            return preferredLocalization;
        }
        for (Localization next : getSupportedLocalizations()) {
            if (next.getLanguageCode().equals(preferredLocalization.getLanguageCode())) {
                return next;
            }
        }
        return Localization.g;
    }

    public PlaylistExtractor getPlaylistExtractor(String str, List<String> list, String str2) throws ExtractionException {
        return getPlaylistExtractor(getPlaylistLHFactory().fromQuery(str, list, str2));
    }

    public abstract PlaylistExtractor getPlaylistExtractor(ListLinkHandler listLinkHandler) throws ExtractionException;

    public abstract ListLinkHandlerFactory getPlaylistLHFactory();

    public SearchExtractor getSearchExtractor(String str, List<String> list, String str2) throws ExtractionException {
        return getSearchExtractor(getSearchQHFactory().fromQuery(str, (List) list, str2));
    }

    public abstract SearchExtractor getSearchExtractor(SearchQueryHandler searchQueryHandler);

    public abstract SearchQueryHandlerFactory getSearchQHFactory();

    public final int getServiceId() {
        return this.a;
    }

    public ServiceInfo getServiceInfo() {
        return this.b;
    }

    public StreamExtractor getStreamExtractor(String str) throws ExtractionException {
        return getStreamExtractor(getStreamLHFactory().fromUrl(str));
    }

    public abstract StreamExtractor getStreamExtractor(LinkHandler linkHandler) throws ExtractionException;

    public abstract LinkHandlerFactory getStreamLHFactory();

    public abstract SubscriptionExtractor getSubscriptionExtractor();

    public abstract SuggestionExtractor getSuggestionExtractor();

    public List<ContentCountry> getSupportedCountries() {
        return Collections.singletonList(ContentCountry.f);
    }

    public List<Localization> getSupportedLocalizations() {
        return Collections.singletonList(Localization.g);
    }

    public TimeAgoParser getTimeAgoParser(Localization localization) {
        TimeAgoParser timeAgoParserFor;
        TimeAgoParser timeAgoParserFor2 = TimeAgoPatternsManager.getTimeAgoParserFor(localization);
        if (timeAgoParserFor2 != null) {
            return timeAgoParserFor2;
        }
        if (!localization.getCountryCode().isEmpty() && (timeAgoParserFor = TimeAgoPatternsManager.getTimeAgoParserFor(new Localization(localization.getLanguageCode()))) != null) {
            return timeAgoParserFor;
        }
        throw new IllegalArgumentException("Localization is not supported (\"" + localization + "\")");
    }

    public String toString() {
        return this.a + ":" + this.b.getName();
    }

    public ChannelExtractor getChannelExtractor(String str) throws ExtractionException {
        return getChannelExtractor(getChannelLHFactory().fromUrl(str));
    }

    public PlaylistExtractor getPlaylistExtractor(String str) throws ExtractionException {
        return getPlaylistExtractor(getPlaylistLHFactory().fromUrl(str));
    }

    public SearchExtractor getSearchExtractor(String str) throws ExtractionException {
        return getSearchExtractor(getSearchQHFactory().fromQuery(str));
    }
}
