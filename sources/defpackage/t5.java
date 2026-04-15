package defpackage;

import com.grack.nanojson.JsonObject;
import defpackage.tf;
import j$.util.function.Consumer$CC;
import java.util.Set;
import java.util.function.Consumer;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCRecentKioskExtractor;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;

/* renamed from: t5  reason: default package */
public final /* synthetic */ class t5 implements Consumer {
    public final /* synthetic */ int a;
    public final /* synthetic */ StreamInfoItemsCollector b;

    public /* synthetic */ t5(StreamInfoItemsCollector streamInfoItemsCollector, int i) {
        this.a = i;
        this.b = streamInfoItemsCollector;
    }

    public final void accept(Object obj) {
        int i = this.a;
        StreamInfoItemsCollector streamInfoItemsCollector = this.b;
        switch (i) {
            case 0:
                streamInfoItemsCollector.commit((StreamInfoItemExtractor) (MediaCCCRecentKioskExtractor) obj);
                return;
            case 1:
                streamInfoItemsCollector.commit((StreamInfoItemExtractor) (YoutubeStreamInfoItemExtractor) obj);
                return;
            default:
                Set<String> set = tf.j;
                streamInfoItemsCollector.commit((StreamInfoItemExtractor) new tf.a((JsonObject) obj));
                return;
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
            case 0:
                return Consumer$CC.$default$andThen(this, consumer);
            case 1:
                return Consumer$CC.$default$andThen(this, consumer);
            default:
                return Consumer$CC.$default$andThen(this, consumer);
        }
    }
}
