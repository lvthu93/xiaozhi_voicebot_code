package defpackage;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;
import j$.util.Collection$EL;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.services.youtube.InnertubeClientRequestInfo;
import org.schabi.newpipe.extractor.services.youtube.YoutubeChannelHelper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.utils.Utils;

/* renamed from: zf  reason: default package */
public abstract class zf extends KioskExtractor<StreamInfoItem> {
    public final String h;
    public final String i;
    public YoutubeChannelHelper.ChannelResponseData j;

    public zf(StreamingService streamingService, ListLinkHandler listLinkHandler, String str, String str2, String str3) {
        super(streamingService, listLinkHandler, str);
        this.h = str2;
        this.i = str3;
    }

    public final ListExtractor.InfoItemsPage<StreamInfoItem> b(JsonArray jsonArray, String str) throws IOException, ExtractionException {
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        Page page = null;
        if (!jsonArray.isEmpty()) {
            jsonArray.streamAsJsonObjects().forEachOrdered(new gg(streamInfoItemsCollector, getTimeAgoParser(), 1));
            JsonObject object = jsonArray.getObject(jsonArray.size() - 1);
            if (object.has("continuationItemRenderer")) {
                JsonObject object2 = object.getObject("continuationItemRenderer");
                if (!Utils.isNullOrEmpty(object2)) {
                    String string = object2.getObject("continuationEndpoint").getObject("continuationCommand").getString("token");
                    InnertubeClientRequestInfo ofWebClient = InnertubeClientRequestInfo.ofWebClient();
                    ofWebClient.a.b = YoutubeParsingHelper.getClientVersion();
                    ofWebClient.a.e = str;
                    page = new Page("https://www.youtube.com/youtubei/v1/browse?prettyPrint=false", str, (List<String>) null, (Map<String, String>) null, JsonWriter.string(YoutubeParsingHelper.prepareJsonBuilder(getExtractorLocalization(), getExtractorContentCountry(), ofWebClient, (String) null).value("continuation", string).done()).getBytes(StandardCharsets.UTF_8));
                }
            }
        }
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, page);
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        JsonArray jsonArray;
        JsonObject object = this.j.a.getObject("contents").getObject("twoColumnBrowseResultsRenderer").getArray("tabs").getObject(0).getObject("tabRenderer").getObject("content");
        if (object.has("sectionListRenderer")) {
            jsonArray = object.getObject("sectionListRenderer").getArray("contents").getObject(0).getObject("itemSectionRenderer").getArray("contents").getObject(0).getObject("shelfRenderer").getObject("content").getObject("gridRenderer").getArray("items");
        } else if (object.has("richGridRenderer")) {
            jsonArray = object.getObject("richGridRenderer").getArray("contents");
        } else {
            jsonArray = new JsonArray();
        }
        return b(jsonArray, this.j.a.getObject("responseContext").getString("visitorData"));
    }

    public String getName() throws ParsingException {
        return YoutubeChannelHelper.getChannelName(YoutubeChannelHelper.getChannelHeader(this.j.a), (JsonObject) null, this.j.a);
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) throws IOException, ExtractionException {
        if (page == null || page.getBody() == null) {
            throw new IllegalArgumentException("Page is null or doesn't contain a body");
        }
        Class<JsonObject> cls = JsonObject.class;
        return b(((JsonObject) y2.ab(cls, 26, y2.aa(cls, 26, Collection$EL.stream(YoutubeParsingHelper.getJsonPostResponse("browse", page.getBody(), getExtractorLocalization()).getArray("onResponseReceivedActions")))).filter(new mg(13)).map(new fg(24)).findFirst().orElse(new JsonObject())).getArray("continuationItems"), page.getId());
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        this.j = YoutubeChannelHelper.getChannelResponse(this.h, this.i, getExtractorLocalization(), getExtractorContentCountry());
    }
}
