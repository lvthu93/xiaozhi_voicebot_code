package defpackage;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonBuilder;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;
import j$.time.LocalDate;
import j$.time.ZoneOffset;
import j$.util.Objects;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.services.youtube.InnertubeClientRequestInfo;
import org.schabi.newpipe.extractor.services.youtube.YoutubeParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeChannelLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.ContentAvailability;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.utils.JsonUtils;
import org.schabi.newpipe.extractor.utils.Utils;

/* renamed from: tf  reason: default package */
public abstract class tf extends KioskExtractor<StreamInfoItem> {
    public static final Set<String> j;
    public final String h;
    public JsonObject i;

    /* renamed from: tf$a */
    public static final class a implements StreamInfoItemExtractor {
        public final JsonObject a;

        public a(JsonObject jsonObject) {
            this.a = jsonObject;
        }

        public final /* synthetic */ ContentAvailability getContentAvailability() {
            return ob.a(this);
        }

        public long getDuration() {
            return (long) this.a.getInt("videoDuration", -1);
        }

        public String getName() {
            return this.a.getString("title");
        }

        public final /* synthetic */ String getShortDescription() {
            return ob.b(this);
        }

        public StreamType getStreamType() {
            return StreamType.VIDEO_STREAM;
        }

        public String getTextualUploadDate() {
            return null;
        }

        public List<Image> getThumbnails() throws ParsingException {
            return YoutubeParsingHelper.getThumbnailsFromInfoItem(this.a);
        }

        public DateWrapper getUploadDate() {
            JsonObject object = this.a.getObject("releaseDate");
            return new DateWrapper(LocalDate.of(object.getInt("year"), object.getInt("month"), object.getInt("day")).atStartOfDay(ZoneOffset.UTC).toInstant(), true);
        }

        public final /* synthetic */ List getUploaderAvatars() {
            return ob.c(this);
        }

        public String getUploaderName() {
            return this.a.getString("channelName");
        }

        public String getUploaderUrl() throws ParsingException {
            String string = this.a.getString("externalChannelId");
            if (!Utils.isNullOrEmpty(string)) {
                YoutubeChannelLinkHandlerFactory instance = YoutubeChannelLinkHandlerFactory.getInstance();
                return instance.getUrl("channel/" + string);
            }
            throw new ParsingException("Could not get channel ID");
        }

        public String getUrl() throws ParsingException {
            return YoutubeStreamLinkHandlerFactory.getInstance().getUrl(this.a.getString("id"));
        }

        public long getViewCount() {
            return -1;
        }

        public boolean isAd() {
            return false;
        }

        public final /* synthetic */ boolean isShortFormContent() {
            return ob.d(this);
        }

        public boolean isUploaderVerified() {
            return false;
        }
    }

    static {
        String[] strArr = {"AE", "AR", "AT", "AU", "BE", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CZ", "DE", "DK", "DO", "EC", "EE", "EG", "ES", "FI", "FR", "GB", "GT", "HN", "HU", "ID", "IE", "IL", "IN", "IS", "IT", "JP", "KE", "KR", "LU", "MX", "NG", "NI", "NL", "NO", "NZ", "PA", "PE", "PL", "PT", "PY", "RO", "RS", "RU", "SA", "SE", "SV", "TR", "TZ", "UA", "UG", "US", "UY", "ZA", "ZW"};
        HashSet hashSet = new HashSet(61);
        int i2 = 0;
        while (i2 < 61) {
            String str = strArr[i2];
            Objects.requireNonNull(str);
            if (hashSet.add(str)) {
                i2++;
            } else {
                throw new IllegalArgumentException("duplicate element: " + str);
            }
        }
        j = Collections.unmodifiableSet(hashSet);
    }

    public tf(StreamingService streamingService, ListLinkHandler listLinkHandler, String str, String str2) {
        super(streamingService, listLinkHandler, str);
        this.h = str2;
    }

    public ListExtractor.InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        JsonArray array = this.i.getObject("contents").getObject("sectionListRenderer").getArray("contents").getObject(0).getObject("musicAnalyticsSectionRenderer").getObject("content").getArray("videos").getObject(0).getArray("videoViews");
        StreamInfoItemsCollector streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());
        array.streamAsJsonObjects().forEachOrdered(new t5(streamInfoItemsCollector, 2));
        return new ListExtractor.InfoItemsPage<>(streamInfoItemsCollector, (Page) null);
    }

    public abstract String getName() throws ParsingException;

    public ListExtractor.InfoItemsPage<StreamInfoItem> getPage(Page page) {
        return ListExtractor.InfoItemsPage.emptyPage();
    }

    public void onFetchPage(Downloader downloader) throws IOException, ExtractionException {
        Localization extractorLocalization = getExtractorLocalization();
        ContentCountry extractorContentCountry = getExtractorContentCountry();
        InnertubeClientRequestInfo ofWebMusicAnalyticsChartsClient = InnertubeClientRequestInfo.ofWebMusicAnalyticsChartsClient();
        JsonBuilder<JsonObject> value = YoutubeParsingHelper.prepareJsonBuilder(getExtractorLocalization(), extractorContentCountry, ofWebMusicAnalyticsChartsClient, (String) null).value("browseId", "FEmusic_analytics_charts_home");
        byte[] bytes = JsonWriter.string(value.value("query", "perspective=CHART_DETAILS&chart_params_country_code=" + extractorContentCountry.getCountryCode() + "&chart_params_chart_type=" + this.h).done()).getBytes(StandardCharsets.UTF_8);
        HashMap hashMap = new HashMap(YoutubeParsingHelper.getOriginReferrerHeaders("https://charts.youtube.com"));
        InnertubeClientRequestInfo.ClientInfo clientInfo = ofWebMusicAnalyticsChartsClient.a;
        hashMap.putAll(YoutubeParsingHelper.getClientHeaders(clientInfo.c, clientInfo.b));
        this.i = JsonUtils.toJsonObject(YoutubeParsingHelper.getValidJsonResponseBody(getDownloader().postWithContentTypeJson("https://charts.youtube.com/youtubei/v1/browse?alt=json&prettyPrint=false", hashMap, bytes, extractorLocalization)));
    }
}
