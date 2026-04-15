package org.schabi.newpipe.extractor.kiosk;

import j$.util.Collection$EL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.utils.Utils;

public class KioskList {
    public final StreamingService a;
    public final HashMap<String, a> b = new HashMap<>();
    public String c = null;
    public Localization d;
    public ContentCountry e;

    public interface KioskExtractorFactory {
        KioskExtractor createNewKiosk(StreamingService streamingService, String str, String str2) throws ExtractionException, IOException;
    }

    public static class a {
        public final KioskExtractorFactory a;
        public final ListLinkHandlerFactory b;

        public a(KioskExtractorFactory kioskExtractorFactory, ListLinkHandlerFactory listLinkHandlerFactory) {
            this.a = kioskExtractorFactory;
            this.b = listLinkHandlerFactory;
        }
    }

    public KioskList(StreamingService streamingService) {
        this.a = streamingService;
    }

    public void addKioskEntry(KioskExtractorFactory kioskExtractorFactory, ListLinkHandlerFactory listLinkHandlerFactory, String str) throws Exception {
        HashMap<String, a> hashMap = this.b;
        if (hashMap.get(str) == null) {
            hashMap.put(str, new a(kioskExtractorFactory, listLinkHandlerFactory));
            return;
        }
        throw new Exception(y2.j("Kiosk with type ", str, " already exists."));
    }

    public void forceContentCountry(ContentCountry contentCountry) {
        this.e = contentCountry;
    }

    public void forceLocalization(Localization localization) {
        this.d = localization;
    }

    public Set<String> getAvailableKiosks() {
        return this.b.keySet();
    }

    public KioskExtractor getDefaultKioskExtractor() throws ExtractionException, IOException {
        return getDefaultKioskExtractor((Page) null);
    }

    public String getDefaultKioskId() {
        return this.c;
    }

    public KioskExtractor getExtractorById(String str, Page page) throws ExtractionException, IOException {
        return getExtractorById(str, page, NewPipe.getPreferredLocalization());
    }

    public KioskExtractor getExtractorByUrl(String str, Page page) throws ExtractionException, IOException {
        return getExtractorByUrl(str, page, NewPipe.getPreferredLocalization());
    }

    public ListLinkHandlerFactory getListLinkHandlerFactoryByType(String str) {
        return this.b.get(str).b;
    }

    public void setDefaultKiosk(String str) {
        this.c = str;
    }

    public KioskExtractor getDefaultKioskExtractor(Page page) throws ExtractionException, IOException {
        return getDefaultKioskExtractor(page, NewPipe.getPreferredLocalization());
    }

    public KioskExtractor getExtractorById(String str, Page page, Localization localization) throws ExtractionException, IOException {
        a aVar = this.b.get(str);
        if (aVar != null) {
            KioskExtractor createNewKiosk = aVar.a.createNewKiosk(this.a, aVar.b.fromId(str).getUrl(), str);
            Localization localization2 = this.d;
            if (localization2 != null) {
                createNewKiosk.forceLocalization(localization2);
            }
            ContentCountry contentCountry = this.e;
            if (contentCountry != null) {
                createNewKiosk.forceContentCountry(contentCountry);
            }
            return createNewKiosk;
        }
        throw new ExtractionException(y2.i("No kiosk found with the type: ", str));
    }

    public KioskExtractor getExtractorByUrl(String str, Page page, Localization localization) throws ExtractionException, IOException {
        for (Map.Entry<String, a> value : this.b.entrySet()) {
            a aVar = (a) value.getValue();
            if (aVar.b.acceptUrl(str)) {
                return getExtractorById(aVar.b.getId(str), page, localization);
            }
        }
        throw new ExtractionException(y2.i("Could not find a kiosk that fits to the url: ", str));
    }

    public KioskExtractor getDefaultKioskExtractor(Page page, Localization localization) throws ExtractionException, IOException {
        if (!Utils.isNullOrEmpty(this.c)) {
            return getExtractorById(this.c, page, localization);
        }
        String str = (String) Collection$EL.stream(this.b.keySet()).findAny().orElse(null);
        if (str != null) {
            return getExtractorById(str, page, localization);
        }
        return null;
    }
}
