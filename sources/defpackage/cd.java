package defpackage;

import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.kiosk.KioskList;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.bandcamp.BandcampService;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampFeaturedExtractor;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampRadioExtractor;
import org.schabi.newpipe.extractor.services.bandcamp.linkHandler.BandcampFeaturedLinkHandlerFactory;

/* renamed from: cd  reason: default package */
public final /* synthetic */ class cd implements KioskList.KioskExtractorFactory {
    public final /* synthetic */ int c;
    public final /* synthetic */ BandcampService f;
    public final /* synthetic */ ListLinkHandlerFactory g;

    public /* synthetic */ cd(BandcampService bandcampService, BandcampFeaturedLinkHandlerFactory bandcampFeaturedLinkHandlerFactory, int i) {
        this.c = i;
        this.f = bandcampService;
        this.g = bandcampFeaturedLinkHandlerFactory;
    }

    public final KioskExtractor createNewKiosk(StreamingService streamingService, String str, String str2) {
        int i = this.c;
        ListLinkHandlerFactory listLinkHandlerFactory = this.g;
        BandcampService bandcampService = this.f;
        switch (i) {
            case 0:
                bandcampService.getClass();
                return new BandcampFeaturedExtractor(bandcampService, listLinkHandlerFactory.fromUrl("https://bandcamp.com/api/mobile/24/bootstrap_data"), str2);
            default:
                bandcampService.getClass();
                return new BandcampRadioExtractor(bandcampService, listLinkHandlerFactory.fromUrl("https://bandcamp.com/api/bcweekly/3/list"), str2);
        }
    }
}
