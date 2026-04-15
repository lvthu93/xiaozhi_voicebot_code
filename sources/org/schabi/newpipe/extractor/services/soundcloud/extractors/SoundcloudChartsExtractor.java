package org.schabi.newpipe.extractor.services.soundcloud.extractors;

import java.io.IOException;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.utils.Utils;

public class SoundcloudChartsExtractor extends KioskExtractor<StreamInfoItem> {
    public String h;
    public StreamInfoItemsCollector i;

    public SoundcloudChartsExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler, String str) {
        super(streamingService, listLinkHandler, str);
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        return new ListExtractor.InfoItemsPage<>(this.i, new Page(this.h));
    }

    public String getName() {
        return getId();
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) throws IOException, ExtractionException {
        if (page == null || Utils.isNullOrEmpty(page.getUrl())) {
            throw new IllegalArgumentException("Page doesn't contain an URL");
        }
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, new Page(SoundcloudParsingHelper.getStreamsFromApi(streamInfoItemsCollector, page.getUrl(), true)));
    }

    public void onFetchPage(Downloader downloader) throws ExtractionException, IOException {
        String str;
        if (this.h == null) {
            this.i = new StreamInfoItemsCollector(getServiceId());
            String str2 = "https://api-v2.soundcloud.com/charts?genre=soundcloud:genres:all-music&client_id=" + SoundcloudParsingHelper.clientId() + "&kind=trending";
            ContentCountry contentCountry = ServiceList.b.getContentCountry();
            if (getService().getSupportedCountries().contains(contentCountry)) {
                StringBuilder o = y2.o(str2, "&region=soundcloud:regions:");
                o.append(contentCountry.getCountryCode());
                str = o.toString();
            } else {
                str = null;
            }
            try {
                StreamInfoItemsCollector streamInfoItemsCollector = this.i;
                if (str == null) {
                    str = str2;
                }
                this.h = SoundcloudParsingHelper.getStreamsFromApi(streamInfoItemsCollector, str, true);
            } catch (IOException unused) {
                this.h = SoundcloudParsingHelper.getStreamsFromApi(this.i, str2, true);
            }
        }
    }
}
