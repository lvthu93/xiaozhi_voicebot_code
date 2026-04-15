package org.schabi.newpipe.extractor.suggestion;

import java.io.IOException;
import java.util.List;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.localization.Localization;

public abstract class SuggestionExtractor {
    public final StreamingService a;
    public Localization b;
    public ContentCountry c;

    public SuggestionExtractor(StreamingService streamingService) {
        this.a = streamingService;
    }

    public void forceContentCountry(ContentCountry contentCountry) {
        this.c = contentCountry;
    }

    public void forceLocalization(Localization localization) {
        this.b = localization;
    }

    public ContentCountry getExtractorContentCountry() {
        ContentCountry contentCountry = this.c;
        if (contentCountry == null) {
            return getService().getContentCountry();
        }
        return contentCountry;
    }

    public Localization getExtractorLocalization() {
        Localization localization = this.b;
        return localization == null ? getService().getLocalization() : localization;
    }

    public StreamingService getService() {
        return this.a;
    }

    public int getServiceId() {
        return this.a.getServiceId();
    }

    public abstract List<String> suggestionList(String str) throws IOException, ExtractionException;
}
