package defpackage;

import com.grack.nanojson.JsonObject;
import j$.util.function.Function$CC;
import java.util.Map;
import java.util.function.Function;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCLiveStreamExtractor;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeSubscriptionExtractor;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.DeliveryMethod;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.subscription.SubscriptionItem;

/* renamed from: s5  reason: default package */
public final /* synthetic */ class s5 implements Function {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ s5(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    public final /* synthetic */ Function andThen(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$andThen(this, function);
            case 1:
                return Function$CC.$default$andThen(this, function);
            default:
                return Function$CC.$default$andThen(this, function);
        }
    }

    public final Object apply(Object obj) {
        int i = this.a;
        Object obj2 = this.b;
        switch (i) {
            case 0:
                Map.Entry entry = (Map.Entry) obj;
                return new MediaCCCLiveStreamExtractor.a((JsonObject) obj2, (String) entry.getKey(), (JsonObject) entry.getValue());
            case 1:
                YoutubeStreamExtractor youtubeStreamExtractor = (YoutubeStreamExtractor) obj2;
                z3 z3Var = (z3) obj;
                youtubeStreamExtractor.getClass();
                ItagItem itagItem = z3Var.f;
                AudioStream.Builder itagItem2 = new AudioStream.Builder().setId(String.valueOf(itagItem.f)).setContent(z3Var.c, z3Var.g).setMediaFormat(itagItem.getMediaFormat()).setAverageBitrate(itagItem.getAverageBitrate()).setAudioTrackId(itagItem.getAudioTrackId()).setAudioTrackName(itagItem.getAudioTrackName()).setAudioLocale(itagItem.getAudioLocale()).setAudioTrackType(itagItem.getAudioTrackType()).setItagItem(itagItem);
                StreamType streamType = youtubeStreamExtractor.q;
                if (streamType == StreamType.LIVE_STREAM || streamType == StreamType.POST_LIVE_STREAM || !z3Var.g) {
                    itagItem2.setDeliveryMethod(DeliveryMethod.DASH);
                }
                return itagItem2.build();
            default:
                YoutubeSubscriptionExtractor youtubeSubscriptionExtractor = (YoutubeSubscriptionExtractor) obj2;
                String[] strArr = (String[]) obj;
                youtubeSubscriptionExtractor.getClass();
                String replace = strArr[1].replace("http://", "https://");
                if (replace.startsWith("https://www.youtube.com/channel/")) {
                    return new SubscriptionItem(youtubeSubscriptionExtractor.b.getServiceId(), replace, strArr[2]);
                }
                return null;
        }
    }

    public final /* synthetic */ Function compose(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$compose(this, function);
            case 1:
                return Function$CC.$default$compose(this, function);
            default:
                return Function$CC.$default$compose(this, function);
        }
    }
}
