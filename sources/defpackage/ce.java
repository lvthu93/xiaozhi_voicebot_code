package defpackage;

import j$.util.function.Consumer$CC;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItemsCollector;
import org.schabi.newpipe.extractor.services.bandcamp.extractors.BandcampRelatedPlaylistInfoItemExtractor;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.utils.ManifestCreatorCache;

/* renamed from: ce  reason: default package */
public final /* synthetic */ class ce implements Consumer {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ ce(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    public final void accept(Object obj) {
        int i = this.a;
        Object obj2 = this.b;
        switch (i) {
            case 0:
                ((PlaylistInfoItemsCollector) obj2).commit((BandcampRelatedPlaylistInfoItemExtractor) obj);
                return;
            case 1:
                ((ItagItem) obj2).setAudioLocale((Locale) obj);
                return;
            default:
                Map.Entry entry = (Map.Entry) obj;
                ((ManifestCreatorCache) obj2).c.remove(entry.getKey(), entry.getValue());
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
