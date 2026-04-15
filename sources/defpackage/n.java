package defpackage;

import com.google.android.exoplayer2.source.ads.AdsMediaSource;

/* renamed from: n  reason: default package */
public final /* synthetic */ class n implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ AdsMediaSource f;
    public final /* synthetic */ AdsMediaSource.c g;

    public /* synthetic */ n(AdsMediaSource adsMediaSource, AdsMediaSource.c cVar, int i) {
        this.c = i;
        this.f = adsMediaSource;
        this.g = cVar;
    }

    public final void run() {
        int i = this.c;
        AdsMediaSource adsMediaSource = this.f;
        switch (i) {
            case 0:
                adsMediaSource.l.stop(adsMediaSource, this.g);
                return;
            default:
                adsMediaSource.l.start(adsMediaSource, adsMediaSource.n, adsMediaSource.o, adsMediaSource.m, this.g);
                return;
        }
    }
}
