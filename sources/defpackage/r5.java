package defpackage;

import com.grack.nanojson.JsonObject;
import java.io.Serializable;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCChannelTabExtractor;

/* renamed from: r5  reason: default package */
public final /* synthetic */ class r5 implements ReadyChannelTabListLinkHandler.ChannelTabExtractorBuilder, Serializable {
    public final /* synthetic */ JsonObject c;

    public /* synthetic */ r5(JsonObject jsonObject) {
        this.c = jsonObject;
    }

    public final ChannelTabExtractor build(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        return new MediaCCCChannelTabExtractor(streamingService, listLinkHandler, this.c);
    }
}
