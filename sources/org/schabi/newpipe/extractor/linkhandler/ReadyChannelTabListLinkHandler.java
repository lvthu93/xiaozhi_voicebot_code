package org.schabi.newpipe.extractor.linkhandler;

import java.io.Serializable;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;

public class ReadyChannelTabListLinkHandler extends ListLinkHandler {
    public final ChannelTabExtractorBuilder j;

    public interface ChannelTabExtractorBuilder extends Serializable {
        ChannelTabExtractor build(StreamingService streamingService, ListLinkHandler listLinkHandler);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ReadyChannelTabListLinkHandler(java.lang.String r8, java.lang.String r9, java.lang.String r10, org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler.ChannelTabExtractorBuilder r11) {
        /*
            r7 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r10
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>(r0)
            r0 = r1[r2]
            java.util.List r5 = defpackage.y2.p(r0, r10, r0, r10)
            java.lang.String r6 = ""
            r1 = r7
            r2 = r8
            r3 = r8
            r4 = r9
            r1.<init>(r2, r3, r4, r5, r6)
            r7.j = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler.<init>(java.lang.String, java.lang.String, java.lang.String, org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler$ChannelTabExtractorBuilder):void");
    }

    public ChannelTabExtractor getChannelTabExtractor(StreamingService streamingService) {
        return this.j.build(streamingService, new ListLinkHandler((ListLinkHandler) this));
    }
}
