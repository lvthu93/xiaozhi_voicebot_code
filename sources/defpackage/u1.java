package defpackage;

import com.google.android.exoplayer2.drm.DrmSessionEventListener;

/* renamed from: u1  reason: default package */
public final /* synthetic */ class u1 implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ DrmSessionEventListener.EventDispatcher f;
    public final /* synthetic */ DrmSessionEventListener g;

    public /* synthetic */ u1(DrmSessionEventListener.EventDispatcher eventDispatcher, DrmSessionEventListener drmSessionEventListener, int i) {
        this.c = i;
        this.f = eventDispatcher;
        this.g = drmSessionEventListener;
    }

    public final void run() {
        int i = this.c;
        DrmSessionEventListener drmSessionEventListener = this.g;
        DrmSessionEventListener.EventDispatcher eventDispatcher = this.f;
        switch (i) {
            case 0:
                drmSessionEventListener.onDrmSessionReleased(eventDispatcher.a, eventDispatcher.b);
                return;
            case 1:
                drmSessionEventListener.onDrmKeysRemoved(eventDispatcher.a, eventDispatcher.b);
                return;
            case 2:
                drmSessionEventListener.onDrmKeysLoaded(eventDispatcher.a, eventDispatcher.b);
                return;
            default:
                drmSessionEventListener.onDrmKeysRestored(eventDispatcher.a, eventDispatcher.b);
                return;
        }
    }
}
