package defpackage;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ExoFlags;
import com.google.android.exoplayer2.util.ListenerSet;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.optimizer.OptRuntime;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.kiosk.KioskList;
import org.schabi.newpipe.extractor.services.peertube.PeertubeService;
import org.schabi.newpipe.extractor.services.peertube.extractors.PeertubeTrendingExtractor;
import org.schabi.newpipe.extractor.services.peertube.linkHandler.PeertubeTrendingLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.soundcloud.SoundcloudService;
import org.schabi.newpipe.extractor.services.soundcloud.extractors.SoundcloudChartsExtractor;
import org.schabi.newpipe.extractor.services.soundcloud.linkHandler.SoundcloudChartsLinkHandlerFactory;

/* renamed from: ah  reason: default package */
public final /* synthetic */ class ah implements ListenerSet.IterationFinishedEvent, ContextAction, KioskList.KioskExtractorFactory {
    public final /* synthetic */ int c;
    public final /* synthetic */ Object f;
    public final /* synthetic */ Object g;

    public /* synthetic */ ah(int i, Object obj, Object obj2) {
        this.c = i;
        this.f = obj;
        this.g = obj2;
    }

    public final KioskExtractor createNewKiosk(StreamingService streamingService, String str, String str2) {
        int i = this.c;
        Object obj = this.g;
        Object obj2 = this.f;
        switch (i) {
            case 2:
                PeertubeService peertubeService = (PeertubeService) obj2;
                peertubeService.getClass();
                return new PeertubeTrendingExtractor(peertubeService, ((PeertubeTrendingLinkHandlerFactory) obj).fromId(str2), str2);
            default:
                SoundcloudService soundcloudService = (SoundcloudService) obj2;
                soundcloudService.getClass();
                return new SoundcloudChartsExtractor(soundcloudService, ((SoundcloudChartsLinkHandlerFactory) obj).fromUrl(str), str2);
        }
    }

    public final void invoke(Object obj, ExoFlags exoFlags) {
        ((AnalyticsListener) obj).onEvents((Player) this.g, new AnalyticsListener.Events(exoFlags, ((AnalyticsCollector) this.f).i));
    }

    public final Object run(Context context) {
        return OptRuntime.lambda$main$0((String[]) this.f, (Script) this.g, context);
    }
}
