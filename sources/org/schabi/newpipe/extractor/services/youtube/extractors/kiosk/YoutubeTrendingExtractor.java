package org.schabi.newpipe.extractor.services.youtube.extractors.kiosk;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;
import j$.util.Collection$EL;
import j$.util.stream.Stream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.localization.TimeAgoParser;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.utils.Utils;

public class YoutubeTrendingExtractor extends KioskExtractor<StreamInfoItem> {
    public JsonObject h;

    public YoutubeTrendingExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler, String str) {
        super(streamingService, listLinkHandler, str);
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() throws ParsingException {
        Stream stream;
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        TimeAgoParser timeAgoParser = getTimeAgoParser();
        Class<JsonObject> cls = JsonObject.class;
        JsonObject jsonObject = (JsonObject) Collection$EL.stream(this.h.getObject("contents").getObject("twoColumnBrowseResultsRenderer").getArray("tabs")).filter(new sg(cls, 1)).map(new tg(cls, 1)).map(new ug(0)).filter(new mg(16)).filter(new mg(17)).findFirst().orElseThrow(new cb(14));
        JsonObject object = jsonObject.getObject("content");
        boolean equals = jsonObject.getObject("endpoint").getObject("browseEndpoint").getString("params", "").equals("4gIOGgxtb3N0X3BvcHVsYXI%3D");
        if (object.has("richGridRenderer")) {
            y2.ab(cls, 28, y2.aa(cls, 27, Collection$EL.stream(object.getObject("richGridRenderer").getArray("contents")))).filter(new mg(15)).map(new fg(28)).forEachOrdered(new gg(streamInfoItemsCollector, timeAgoParser, 3));
        } else if (object.has("sectionListRenderer")) {
            Stream map = y2.ab(cls, 29, y2.aa(cls, 29, Collection$EL.stream(object.getObject("sectionListRenderer").getArray("contents")))).flatMap(new fg(29)).filter(new sg(cls, 0)).map(new tg(cls, 0)).map(new fg(25));
            if (equals) {
                stream = map.findFirst().stream();
            } else {
                stream = map.filter(new mg(14));
            }
            y2.ab(cls, 27, y2.aa(cls, 28, stream.flatMap(new fg(26)))).map(new fg(27)).forEachOrdered(new gg(streamInfoItemsCollector, timeAgoParser, 2));
        }
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, (Page) null);
    }

    public String getName() throws ParsingException {
        String str;
        JsonObject object = this.h.getObject("header");
        if (object.has("feedTabbedHeaderRenderer")) {
            str = YoutubeParsingHelper.getTextAtKey(object.getObject("feedTabbedHeaderRenderer"), "title");
        } else if (object.has("c4TabbedHeaderRenderer")) {
            str = YoutubeParsingHelper.getTextAtKey(object.getObject("c4TabbedHeaderRenderer"), "title");
        } else if (object.has("pageHeaderRenderer")) {
            str = YoutubeParsingHelper.getTextAtKey(object.getObject("pageHeaderRenderer"), "pageTitle");
        } else {
            str = null;
        }
        if (!Utils.isNullOrEmpty(str)) {
            return str;
        }
        throw new ParsingException("Could not get Trending name");
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) {
        return ListExtractor.InfoItemsPage.emptyPage();
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        this.h = YoutubeParsingHelper.getJsonPostResponse("browse", JsonWriter.string(YoutubeParsingHelper.prepareDesktopJsonBuilder(getExtractorLocalization(), getExtractorContentCountry()).value("browseId", "FEtrending").value("params", "4gIOGgxtb3N0X3BvcHVsYXI%3D").done()).getBytes(StandardCharsets.UTF_8), getExtractorLocalization());
    }
}
