package defpackage;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import j$.time.temporal.ChronoUnit;
import j$.util.Collection$EL;
import j$.util.function.Function$CC;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import org.jsoup.nodes.Element;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampRelatedPlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCLiveStreamExtractor;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCRecentKioskExtractor;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.DeliveryMethod;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.VideoStream;
import org.schabi.newpipe.extractor.utils.ImageSuffix;
import org.schabi.newpipe.extractor.utils.Utils;

/* renamed from: z5  reason: default package */
public final /* synthetic */ class z5 implements Function {
    public final /* synthetic */ int a;

    public /* synthetic */ z5(int i) {
        this.a = i;
    }

    public final /* synthetic */ Function andThen(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$andThen(this, function);
            case 1:
                return Function$CC.$default$andThen(this, function);
            case 2:
                return Function$CC.$default$andThen(this, function);
            case 3:
                return Function$CC.$default$andThen(this, function);
            case 4:
                return Function$CC.$default$andThen(this, function);
            case 5:
                return Function$CC.$default$andThen(this, function);
            case 6:
                return Function$CC.$default$andThen(this, function);
            case 7:
                return Function$CC.$default$andThen(this, function);
            case 8:
                return Function$CC.$default$andThen(this, function);
            case 9:
                return Function$CC.$default$andThen(this, function);
            case 10:
                return Function$CC.$default$andThen(this, function);
            case 11:
                return Function$CC.$default$andThen(this, function);
            case 12:
                return Function$CC.$default$andThen(this, function);
            case 13:
                return Function$CC.$default$andThen(this, function);
            case 14:
                return Function$CC.$default$andThen(this, function);
            case 15:
                return Function$CC.$default$andThen(this, function);
            case 16:
                return Function$CC.$default$andThen(this, function);
            case 17:
                return Function$CC.$default$andThen(this, function);
            case 18:
                return Function$CC.$default$andThen(this, function);
            case 19:
                return Function$CC.$default$andThen(this, function);
            case 20:
                return Function$CC.$default$andThen(this, function);
            case 21:
                return Function$CC.$default$andThen(this, function);
            case 22:
                return Function$CC.$default$andThen(this, function);
            case 23:
                return Function$CC.$default$andThen(this, function);
            case 24:
                return Function$CC.$default$andThen(this, function);
            case 25:
                return Function$CC.$default$andThen(this, function);
            case 26:
                return Function$CC.$default$andThen(this, function);
            case 27:
                return Function$CC.$default$andThen(this, function);
            case 28:
                return Function$CC.$default$andThen(this, function);
            default:
                return Function$CC.$default$andThen(this, function);
        }
    }

    public final Object apply(Object obj) {
        DeliveryMethod deliveryMethod = DeliveryMethod.HLS;
        switch (this.a) {
            case 0:
                return ((MediaFormat) obj).getSuffix();
            case 1:
                return (MediaFormat) obj;
            case 2:
                return ((MediaFormat) obj).getName();
            case 3:
                return ((MediaFormat) obj).getMimeType();
            case 4:
                return Localization.fromLocale((Locale) obj);
            case 5:
                return (ChronoUnit) ((Map.Entry) obj).getKey();
            case 6:
                return Collection$EL.stream(((Element) obj).getElementsByTag("img"));
            case 7:
                return ((Element) obj).attr("src");
            case 8:
                return new Image(Utils.replaceHttpWithHttps((String) obj), -1, -1, Image.ResolutionLevel.UNKNOWN);
            case 9:
                return ((JsonObject) obj).getString("token");
            case 10:
                List<ImageSuffix> list = BandcampExtractorHelper.a;
                return Collection$EL.stream(((Element) obj).getElementsByTag("img"));
            case 11:
                List<ImageSuffix> list2 = BandcampExtractorHelper.a;
                return ((Element) obj).attr("src");
            case 12:
                return ((Element) obj).attr("src");
            case 13:
                return ((Element) obj).text();
            case 14:
                return Collection$EL.stream(((Element) obj).getElementsByClass("itemtype"));
            case 15:
                return ((Element) obj).text();
            case 16:
                return ((Element) obj).getElementsByTag("li");
            case 17:
                return new BandcampRelatedPlaylistInfoItemExtractor((Element) obj);
            case 18:
                return ((Element) obj).attr("src");
            case 19:
                return ((Element) obj).text();
            case 20:
                return Collection$EL.stream(((Element) obj).getElementsByClass("tag"));
            case 21:
                return ((Element) obj).text();
            case 22:
                return ((JsonObject) obj).getString("name");
            case 23:
                MediaCCCLiveStreamExtractor.a aVar = (MediaCCCLiveStreamExtractor.a) obj;
                AudioStream.Builder averageBitrate = new AudioStream.Builder().setId(aVar.c.getString("tech", " ")).setContent(aVar.c.getString("url"), true).setAverageBitrate(-1);
                String str = aVar.b;
                if ("hls".equals(str)) {
                    return averageBitrate.setDeliveryMethod(deliveryMethod).build();
                }
                return averageBitrate.setMediaFormat(MediaFormat.getFromSuffix(str)).build();
            case 24:
                MediaCCCLiveStreamExtractor.a aVar2 = (MediaCCCLiveStreamExtractor.a) obj;
                JsonArray array = aVar2.a.getArray("videoSize");
                VideoStream.Builder builder = new VideoStream.Builder();
                JsonObject jsonObject = aVar2.c;
                VideoStream.Builder isVideoOnly = builder.setId(jsonObject.getString("tech", " ")).setContent(jsonObject.getString("url"), true).setIsVideoOnly(false);
                VideoStream.Builder resolution = isVideoOnly.setResolution(array.getInt(0) + "x" + array.getInt(1));
                String str2 = aVar2.b;
                if ("hls".equals(str2)) {
                    return resolution.setDeliveryMethod(deliveryMethod).build();
                }
                return resolution.setMediaFormat(MediaFormat.getFromSuffix(str2)).build();
            case 25:
                return ((JsonObject) obj).getObject("urls");
            case 26:
                JsonObject jsonObject2 = (JsonObject) obj;
                return Collection$EL.stream(jsonObject2.getObject("urls").entrySet()).filter(new bz(4)).map(new s5(0, jsonObject2));
            case 27:
                return ((StreamInfoItem) obj).getUploadDate();
            case 28:
                return ((DateWrapper) obj).getInstant();
            default:
                return new MediaCCCRecentKioskExtractor((JsonObject) obj);
        }
    }

    public final /* synthetic */ Function compose(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$compose(this, function);
            case 1:
                return Function$CC.$default$compose(this, function);
            case 2:
                return Function$CC.$default$compose(this, function);
            case 3:
                return Function$CC.$default$compose(this, function);
            case 4:
                return Function$CC.$default$compose(this, function);
            case 5:
                return Function$CC.$default$compose(this, function);
            case 6:
                return Function$CC.$default$compose(this, function);
            case 7:
                return Function$CC.$default$compose(this, function);
            case 8:
                return Function$CC.$default$compose(this, function);
            case 9:
                return Function$CC.$default$compose(this, function);
            case 10:
                return Function$CC.$default$compose(this, function);
            case 11:
                return Function$CC.$default$compose(this, function);
            case 12:
                return Function$CC.$default$compose(this, function);
            case 13:
                return Function$CC.$default$compose(this, function);
            case 14:
                return Function$CC.$default$compose(this, function);
            case 15:
                return Function$CC.$default$compose(this, function);
            case 16:
                return Function$CC.$default$compose(this, function);
            case 17:
                return Function$CC.$default$compose(this, function);
            case 18:
                return Function$CC.$default$compose(this, function);
            case 19:
                return Function$CC.$default$compose(this, function);
            case 20:
                return Function$CC.$default$compose(this, function);
            case 21:
                return Function$CC.$default$compose(this, function);
            case 22:
                return Function$CC.$default$compose(this, function);
            case 23:
                return Function$CC.$default$compose(this, function);
            case 24:
                return Function$CC.$default$compose(this, function);
            case 25:
                return Function$CC.$default$compose(this, function);
            case 26:
                return Function$CC.$default$compose(this, function);
            case 27:
                return Function$CC.$default$compose(this, function);
            case 28:
                return Function$CC.$default$compose(this, function);
            default:
                return Function$CC.$default$compose(this, function);
        }
    }
}
