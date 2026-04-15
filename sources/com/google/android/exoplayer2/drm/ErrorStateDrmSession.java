package com.google.android.exoplayer2.drm;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Map;
import java.util.UUID;

public final class ErrorStateDrmSession implements DrmSession {
    public final DrmSession.DrmSessionException a;

    public ErrorStateDrmSession(DrmSession.DrmSessionException drmSessionException) {
        this.a = (DrmSession.DrmSessionException) Assertions.checkNotNull(drmSessionException);
    }

    public void acquire(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
    }

    @Nullable
    public DrmSession.DrmSessionException getError() {
        return this.a;
    }

    @Nullable
    public ExoMediaCrypto getMediaCrypto() {
        return null;
    }

    @Nullable
    public byte[] getOfflineLicenseKeySetId() {
        return null;
    }

    public final UUID getSchemeUuid() {
        return C.a;
    }

    public int getState() {
        return 1;
    }

    public boolean playClearSamplesWithoutKeys() {
        return false;
    }

    @Nullable
    public Map<String, String> queryKeyStatus() {
        return null;
    }

    public void release(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
    }
}
