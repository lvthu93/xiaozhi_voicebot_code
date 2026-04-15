package defpackage;

import j$.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import org.jsoup.select.Elements;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.peertube.linkHandler.PeertubeTrendingLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemLockupExtractor;

/* renamed from: cb  reason: default package */
public final /* synthetic */ class cb implements Supplier {
    public final /* synthetic */ int a;

    public /* synthetic */ cb(int i) {
        this.a = i;
    }

    public final Object get() {
        switch (this.a) {
            case 0:
                return new ParsingException("Could not get token");
            case 1:
                return new ParsingException("Could not get uploader name");
            case 2:
                return new Elements();
            case 3:
                PeertubeTrendingLinkHandlerFactory peertubeTrendingLinkHandlerFactory = PeertubeTrendingLinkHandlerFactory.a;
                return new ParsingException("no id found for this url");
            case 4:
                return new ParsingException("Could not get channel name");
            case 5:
                return new ParsingException("Could not get avatars");
            case 6:
                return new ParsingException("Could not get avatars");
            case 7:
                return new ParsingException("Could not get comment replies continuation");
            case 8:
                return new ParsingException("Could not get comment entity payload mutation");
            case 9:
                return new ParsingException("Could not get thumbnailOverlayBadgeViewModel");
            case 10:
                return new ParsingException("Could not get thumbnailBadgeViewModel");
            case 11:
                return new ParsingException("Could not get playlist info");
            case 12:
                return new ParsingException("Could not get uploader info");
            case 13:
                DateTimeFormatter dateTimeFormatter = YoutubeStreamInfoItemLockupExtractor.h;
                return new ParsingException("Could not get uploader name");
            default:
                return new ParsingException("Could not get \"Now\" or \"Videos\" trending tab");
        }
    }
}
