package defpackage;

import com.grack.nanojson.JsonObject;
import j$.util.function.Function$CC;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampExtractorHelper;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudParsingHelper;
import org.schabi.newpipe.extractor.services.youtube.YoutubeDescriptionHelper;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeMixPlaylistExtractor;
import org.schabi.newpipe.extractor.utils.ImageSuffix;
import org.schabi.newpipe.extractor.utils.Utils;

/* renamed from: cc  reason: default package */
public final /* synthetic */ class cc implements Function {
    public final /* synthetic */ int a;
    public final /* synthetic */ String b;

    public /* synthetic */ cc(String str, int i) {
        this.a = i;
        this.b = str;
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
            default:
                return Function$CC.$default$andThen(this, function);
        }
    }

    public final Object apply(Object obj) {
        int i = this.a;
        String str = this.b;
        switch (i) {
            case 0:
                ImageSuffix imageSuffix = (ImageSuffix) obj;
                List<ImageSuffix> list = BandcampExtractorHelper.a;
                StringBuilder m = y2.m(str);
                m.append(imageSuffix.getSuffix());
                return new Image(m.toString(), imageSuffix.getHeight(), imageSuffix.getWidth(), imageSuffix.getResolutionLevel());
            case 1:
                return ((JsonObject) obj).getObject(str).getString("url", "");
            case 2:
                JsonObject jsonObject = (JsonObject) obj;
                StringBuilder m2 = y2.m(str);
                m2.append(jsonObject.getString("path"));
                return new Image(m2.toString(), -1, jsonObject.getInt("width", -1), Image.ResolutionLevel.UNKNOWN);
            case 3:
                ImageSuffix imageSuffix2 = (ImageSuffix) obj;
                List<ImageSuffix> list2 = SoundcloudParsingHelper.a;
                return new Image(String.format(str, new Object[]{imageSuffix2.getSuffix()}), imageSuffix2.getHeight(), imageSuffix2.getWidth(), imageSuffix2.getResolutionLevel());
            case 4:
                String str2 = (String) obj;
                Pattern pattern = YoutubeDescriptionHelper.a;
                return str;
            case 5:
                ImageSuffix imageSuffix3 = (ImageSuffix) obj;
                List<ImageSuffix> list3 = YoutubeMixPlaylistExtractor.j;
                StringBuilder m3 = y2.m(str);
                m3.append(imageSuffix3.getSuffix());
                return new Image(m3.toString(), imageSuffix3.getHeight(), imageSuffix3.getWidth(), imageSuffix3.getResolutionLevel());
            case 6:
                return ((JsonObject) obj).getObject(str);
            default:
                Map.Entry entry = (Map.Entry) obj;
                Pattern pattern2 = Utils.a;
                return entry.getKey() + str + entry.getValue();
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
            default:
                return Function$CC.$default$compose(this, function);
        }
    }
}
