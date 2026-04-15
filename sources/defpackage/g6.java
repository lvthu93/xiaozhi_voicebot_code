package defpackage;

import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSourceEventListener;

/* renamed from: g6  reason: default package */
public final /* synthetic */ class g6 implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ MediaSourceEventListener.EventDispatcher f;
    public final /* synthetic */ MediaSourceEventListener g;
    public final /* synthetic */ LoadEventInfo h;
    public final /* synthetic */ MediaLoadData i;

    public /* synthetic */ g6(MediaSourceEventListener.EventDispatcher eventDispatcher, MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, int i2) {
        this.c = i2;
        this.f = eventDispatcher;
        this.g = mediaSourceEventListener;
        this.h = loadEventInfo;
        this.i = mediaLoadData;
    }

    public final void run() {
        int i2 = this.c;
        MediaLoadData mediaLoadData = this.i;
        LoadEventInfo loadEventInfo = this.h;
        MediaSourceEventListener mediaSourceEventListener = this.g;
        MediaSourceEventListener.EventDispatcher eventDispatcher = this.f;
        switch (i2) {
            case 0:
                mediaSourceEventListener.onLoadStarted(eventDispatcher.a, eventDispatcher.b, loadEventInfo, mediaLoadData);
                return;
            case 1:
                mediaSourceEventListener.onLoadCompleted(eventDispatcher.a, eventDispatcher.b, loadEventInfo, mediaLoadData);
                return;
            default:
                mediaSourceEventListener.onLoadCanceled(eventDispatcher.a, eventDispatcher.b, loadEventInfo, mediaLoadData);
                return;
        }
    }
}
