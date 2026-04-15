package org.schabi.newpipe.extractor;

import j$.util.Objects;
import java.io.IOException;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;

public abstract class Extractor {
    public final StreamingService a;
    public final LinkHandler b;
    public Localization c = null;
    public ContentCountry d = null;
    public boolean e = false;
    public final Downloader f;

    public Extractor(StreamingService streamingService, LinkHandler linkHandler) {
        Objects.requireNonNull(streamingService, "service is null");
        this.a = streamingService;
        Objects.requireNonNull(linkHandler, "LinkHandler is null");
        this.b = linkHandler;
        Downloader downloader = NewPipe.getDownloader();
        Objects.requireNonNull(downloader, "downloader is null");
        this.f = downloader;
    }

    public final void a() {
        if (!this.e) {
            throw new IllegalStateException("Page is not fetched. Make sure you call fetchPage()");
        }
    }

    public void fetchPage() throws IOException, ExtractionException {
        if (!this.e) {
            onFetchPage(this.f);
            this.e = true;
        }
    }

    public void forceContentCountry(ContentCountry contentCountry) {
        this.d = contentCountry;
    }

    public void forceLocalization(Localization localization) {
        this.c = localization;
    }

    public String getBaseUrl() throws ParsingException {
        return this.b.getBaseUrl();
    }

    public Downloader getDownloader() {
        return this.f;
    }

    public ContentCountry getExtractorContentCountry() {
        ContentCountry contentCountry = this.d;
        return contentCountry == null ? getService().getContentCountry() : contentCountry;
    }

    public Localization getExtractorLocalization() {
        Localization localization = this.c;
        return localization == null ? getService().getLocalization() : localization;
    }

    public String getId() throws ParsingException {
        return this.b.getId();
    }

    public LinkHandler getLinkHandler() {
        return this.b;
    }

    public abstract String getName() throws ParsingException;

    public String getOriginalUrl() throws ParsingException {
        return this.b.getOriginalUrl();
    }

    public StreamingService getService() {
        return this.a;
    }

    public int getServiceId() {
        return this.a.getServiceId();
    }

    public TimeAgoParser getTimeAgoParser() {
        return getService().getTimeAgoParser(getExtractorLocalization());
    }

    public String getUrl() throws ParsingException {
        return this.b.getUrl();
    }

    public abstract void onFetchPage(Downloader downloader) throws IOException, ExtractionException;
}
