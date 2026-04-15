package defpackage;

import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.kiosk.KioskList;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.media_ccc.MediaCCCService;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCConferenceKiosk;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCLiveStreamKiosk;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCRecentKiosk;
import org.schabi.newpipe.extractor.services.media_ccc.linkHandler.MediaCCCConferencesListLinkHandlerFactory;

/* renamed from: v5  reason: default package */
public final /* synthetic */ class v5 implements KioskList.KioskExtractorFactory {
    public final /* synthetic */ int c;
    public final /* synthetic */ MediaCCCService f;
    public final /* synthetic */ ListLinkHandlerFactory g;

    public /* synthetic */ v5(MediaCCCService mediaCCCService, MediaCCCConferencesListLinkHandlerFactory mediaCCCConferencesListLinkHandlerFactory, int i) {
        this.c = i;
        this.f = mediaCCCService;
        this.g = mediaCCCConferencesListLinkHandlerFactory;
    }

    public final KioskExtractor createNewKiosk(StreamingService streamingService, String str, String str2) {
        int i = this.c;
        ListLinkHandlerFactory listLinkHandlerFactory = this.g;
        MediaCCCService mediaCCCService = this.f;
        switch (i) {
            case 0:
                mediaCCCService.getClass();
                return new MediaCCCConferenceKiosk(mediaCCCService, listLinkHandlerFactory.fromUrl(str), str2);
            case 1:
                mediaCCCService.getClass();
                return new MediaCCCRecentKiosk(mediaCCCService, listLinkHandlerFactory.fromUrl(str), str2);
            default:
                mediaCCCService.getClass();
                return new MediaCCCLiveStreamKiosk(mediaCCCService, listLinkHandlerFactory.fromUrl(str), str2);
        }
    }
}
