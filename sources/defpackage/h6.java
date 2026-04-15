package defpackage;

import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import java.io.IOException;

/* renamed from: h6  reason: default package */
public final /* synthetic */ class h6 implements Runnable {
    public final /* synthetic */ MediaSourceEventListener.EventDispatcher c;
    public final /* synthetic */ MediaSourceEventListener f;
    public final /* synthetic */ LoadEventInfo g;
    public final /* synthetic */ MediaLoadData h;
    public final /* synthetic */ IOException i;
    public final /* synthetic */ boolean j;

    public /* synthetic */ h6(MediaSourceEventListener.EventDispatcher eventDispatcher, MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        this.c = eventDispatcher;
        this.f = mediaSourceEventListener;
        this.g = loadEventInfo;
        this.h = mediaLoadData;
        this.i = iOException;
        this.j = z;
    }

    public final void run() {
        MediaSourceEventListener mediaSourceEventListener = this.f;
        LoadEventInfo loadEventInfo = this.g;
        MediaLoadData mediaLoadData = this.h;
        IOException iOException = this.i;
        boolean z = this.j;
        MediaSourceEventListener.EventDispatcher eventDispatcher = this.c;
        mediaSourceEventListener.onLoadError(eventDispatcher.a, eventDispatcher.b, loadEventInfo, mediaLoadData, iOException, z);
    }
}
