package defpackage;

import j$.util.function.Function$CC;
import java.util.function.Function;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor;
import org.schabi.newpipe.extractor.stream.DeliveryMethod;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.stream.VideoStream;

/* renamed from: ng  reason: default package */
public final /* synthetic */ class ng implements Function {
    public final /* synthetic */ YoutubeStreamExtractor a;
    public final /* synthetic */ boolean b;

    public /* synthetic */ ng(YoutubeStreamExtractor youtubeStreamExtractor, boolean z) {
        this.a = youtubeStreamExtractor;
        this.b = z;
    }

    public final /* synthetic */ Function andThen(Function function) {
        return Function$CC.$default$andThen(this, function);
    }

    public final Object apply(Object obj) {
        z3 z3Var = (z3) obj;
        YoutubeStreamExtractor youtubeStreamExtractor = this.a;
        youtubeStreamExtractor.getClass();
        ItagItem itagItem = z3Var.f;
        VideoStream.Builder itagItem2 = new VideoStream.Builder().setId(String.valueOf(itagItem.f)).setContent(z3Var.c, z3Var.g).setMediaFormat(itagItem.getMediaFormat()).setIsVideoOnly(this.b).setItagItem(itagItem);
        String resolutionString = itagItem.getResolutionString();
        if (resolutionString == null) {
            resolutionString = "";
        }
        itagItem2.setResolution(resolutionString);
        if (youtubeStreamExtractor.q != StreamType.VIDEO_STREAM || !z3Var.g) {
            itagItem2.setDeliveryMethod(DeliveryMethod.DASH);
        }
        return itagItem2.build();
    }

    public final /* synthetic */ Function compose(Function function) {
        return Function$CC.$default$compose(this, function);
    }
}
