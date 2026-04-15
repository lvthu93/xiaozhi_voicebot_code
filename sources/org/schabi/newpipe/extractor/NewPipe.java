package org.schabi.newpipe.extractor;

import j$.util.Collection$EL;
import java.util.List;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.localization.Localization;

public final class NewPipe {
    public static Downloader a;
    public static Localization b;
    public static ContentCountry c;

    public static Downloader getDownloader() {
        return a;
    }

    public static ContentCountry getPreferredContentCountry() {
        ContentCountry contentCountry = c;
        return contentCountry == null ? ContentCountry.f : contentCountry;
    }

    public static Localization getPreferredLocalization() {
        Localization localization = b;
        return localization == null ? Localization.g : localization;
    }

    public static StreamingService getService(int i) throws ExtractionException {
        return (StreamingService) Collection$EL.stream(ServiceList.all()).filter(new af(i, 2)).findFirst().orElseThrow(new k7(i));
    }

    public static StreamingService getServiceByUrl(String str) throws ExtractionException {
        for (StreamingService next : ServiceList.all()) {
            if (next.getLinkTypeByUrl(str) != StreamingService.LinkType.NONE) {
                return next;
            }
        }
        throw new ExtractionException(y2.j("No service can handle the url = \"", str, "\""));
    }

    public static List<StreamingService> getServices() {
        return ServiceList.all();
    }

    public static void init(Downloader downloader) {
        init(downloader, Localization.g);
    }

    public static void setPreferredContentCountry(ContentCountry contentCountry) {
        c = contentCountry;
    }

    public static void setPreferredLocalization(Localization localization) {
        b = localization;
    }

    public static void setupLocalization(Localization localization) {
        setupLocalization(localization, (ContentCountry) null);
    }

    public static void init(Downloader downloader, Localization localization) {
        init(downloader, localization, localization.getCountryCode().isEmpty() ? ContentCountry.f : new ContentCountry(localization.getCountryCode()));
    }

    public static void setupLocalization(Localization localization, ContentCountry contentCountry) {
        ContentCountry contentCountry2;
        b = localization;
        if (contentCountry != null) {
            c = contentCountry;
            return;
        }
        if (localization.getCountryCode().isEmpty()) {
            contentCountry2 = ContentCountry.f;
        } else {
            contentCountry2 = new ContentCountry(localization.getCountryCode());
        }
        c = contentCountry2;
    }

    public static StreamingService getService(String str) throws ExtractionException {
        return (StreamingService) Collection$EL.stream(ServiceList.all()).filter(new y5(str, 3)).findFirst().orElseThrow(new j7(str, 0));
    }

    public static void init(Downloader downloader, Localization localization, ContentCountry contentCountry) {
        a = downloader;
        b = localization;
        c = contentCountry;
    }
}
