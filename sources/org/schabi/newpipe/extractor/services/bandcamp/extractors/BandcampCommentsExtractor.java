package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonStringWriter;
import com.grack.nanojson.JsonWriter;
import j$.util.Collection$EL;
import j$.util.Objects;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.comments.CommentsExtractor;
import org.schabi.newpipe.extractor.comments.CommentsInfoItem;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemExtractor;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemsCollector;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.utils.JsonUtils;

public class BandcampCommentsExtractor extends CommentsExtractor {
    public Document g;

    public BandcampCommentsExtractor(StreamingService streamingService, ListLinkHandler listLinkHandler) {
        super(streamingService, listLinkHandler);
    }

    public static String b(JsonArray jsonArray) throws ParsingException {
        Class<JsonObject> cls = JsonObject.class;
        return (String) y2.z(cls, 1, y2.s(cls, 1, Collection$EL.stream(jsonArray))).map(new z5(9)).reduce(new ca(0)).orElseThrow(new cb(0));
    }

    public ListExtractor.InfoItemsPage<CommentsInfoItem> getInitialPage() throws IOException, ExtractionException {
        CommentsInfoItemsCollector commentsInfoItemsCollector = new CommentsInfoItemsCollector(getServiceId());
        JsonObject jsonObject = JsonUtils.toJsonObject(this.g.getElementById("collectors-data").attr("data-blob"));
        JsonArray array = jsonObject.getArray("reviews");
        Iterator it = array.iterator();
        while (it.hasNext()) {
            commentsInfoItemsCollector.commit((CommentsInfoItemExtractor) new BandcampCommentsInfoItemExtractor((JsonObject) it.next(), getUrl()));
        }
        if (!jsonObject.getBoolean("more_reviews_available")) {
            return new ListExtractor.InfoItemsPage<>(commentsInfoItemsCollector, (Page) null);
        }
        Object[] objArr = {Long.toString(JsonUtils.toJsonObject(this.g.selectFirst("meta[name=bc-page-properties]").attr("content")).getLong("item_id")), b(array)};
        ArrayList arrayList = new ArrayList(2);
        for (int i = 0; i < 2; i++) {
            Object obj = objArr[i];
            Objects.requireNonNull(obj);
            arrayList.add(obj);
        }
        return new ListExtractor.InfoItemsPage<>(commentsInfoItemsCollector, new Page((List<String>) Collections.unmodifiableList(arrayList)));
    }

    public ListExtractor.InfoItemsPage<CommentsInfoItem> getPage(Page page) throws IOException, ExtractionException {
        CommentsInfoItemsCollector commentsInfoItemsCollector = new CommentsInfoItemsCollector(getServiceId());
        List<String> ids = page.getIds();
        String str = ids.get(0);
        try {
            JsonObject jsonObject = JsonUtils.toJsonObject(getDownloader().postWithContentTypeJson("https://bandcamp.com/api/tralbumcollectors/2/reviews", Collections.emptyMap(), ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) ((JsonStringWriter) JsonWriter.string().object()).value("tralbum_type", "t")).value("tralbum_id", str)).value("token", ids.get(1))).value("count", 7)).array("exclude_fan_ids")).end()).end()).done().getBytes(StandardCharsets.UTF_8)).responseBody());
            JsonArray array = jsonObject.getArray("results");
            Iterator it = array.iterator();
            while (it.hasNext()) {
                commentsInfoItemsCollector.commit((CommentsInfoItemExtractor) new BandcampCommentsInfoItemExtractor((JsonObject) it.next(), getUrl()));
            }
            if (!jsonObject.getBoolean("more_available")) {
                return new ListExtractor.InfoItemsPage<>(commentsInfoItemsCollector, (Page) null);
            }
            Object[] objArr = {str, b(array)};
            ArrayList arrayList = new ArrayList(2);
            for (int i = 0; i < 2; i++) {
                Object obj = objArr[i];
                Objects.requireNonNull(obj);
                arrayList.add(obj);
            }
            return new ListExtractor.InfoItemsPage<>(commentsInfoItemsCollector, new Page((List<String>) Collections.unmodifiableList(arrayList)));
        } catch (IOException | ReCaptchaException e) {
            throw new ParsingException("Could not fetch reviews", e);
        }
    }

    public boolean isCommentsDisabled() throws ExtractionException {
        return BandcampExtractorHelper.isRadioUrl(getUrl());
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        this.g = Jsoup.parse(downloader.get(getLinkHandler().getUrl()).responseBody());
    }
}
