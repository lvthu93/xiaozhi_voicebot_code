package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;

/* renamed from: s1  reason: default package */
public final /* synthetic */ class s1 {
    public static boolean a(DrmSession drmSession) {
        return false;
    }

    public static void b(@Nullable DrmSession drmSession, @Nullable DrmSession drmSession2) {
        if (drmSession != drmSession2) {
            if (drmSession2 != null) {
                drmSession2.acquire((DrmSessionEventListener.EventDispatcher) null);
            }
            if (drmSession != null) {
                drmSession.release((DrmSessionEventListener.EventDispatcher) null);
            }
        }
    }
}
