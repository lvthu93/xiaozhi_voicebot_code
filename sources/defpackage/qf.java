package defpackage;

import java.io.Serializable;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeChannelTabPlaylistExtractor;

/* renamed from: qf  reason: default package */
public final /* synthetic */ class qf implements ReadyChannelTabListLinkHandler.ChannelTabExtractorBuilder, Serializable {
    public final ChannelTabExtractor build(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        return new YoutubeChannelTabPlaylistExtractor(streamingService, listLinkHandler);
    }
}
