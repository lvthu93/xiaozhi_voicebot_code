package com.google.android.exoplayer2.drm;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Map;
import java.util.UUID;

@RequiresApi(18)
public final class OfflineLicenseHelper {
    public static final Format e = new Format.Builder().setDrmInitData(new DrmInitData(new DrmInitData.SchemeData[0])).build();
    public final ConditionVariable a;
    public final DefaultDrmSessionManager b;
    public final HandlerThread c;
    public final DrmSessionEventListener.EventDispatcher d;

    public class a implements DrmSessionEventListener {
        public a() {
        }

        public void onDrmKeysLoaded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            OfflineLicenseHelper.this.a.open();
        }

        public void onDrmKeysRemoved(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            OfflineLicenseHelper.this.a.open();
        }

        public void onDrmKeysRestored(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            OfflineLicenseHelper.this.a.open();
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onDrmSessionAcquired(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            t1.d(this, i, mediaPeriodId);
        }

        public /* bridge */ /* synthetic */ void onDrmSessionAcquired(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, int i2) {
            t1.e(this, i, mediaPeriodId, i2);
        }

        public void onDrmSessionManagerError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception exc) {
            OfflineLicenseHelper.this.a.open();
        }

        public /* bridge */ /* synthetic */ void onDrmSessionReleased(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            t1.g(this, i, mediaPeriodId);
        }
    }

    @Deprecated
    public OfflineLicenseHelper(UUID uuid, ExoMediaDrm.Provider provider, MediaDrmCallback mediaDrmCallback, @Nullable Map<String, String> map, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        this(new DefaultDrmSessionManager.Builder().setUuidAndExoMediaDrmProvider(uuid, provider).setKeyRequestParameters(map).build(mediaDrmCallback), eventDispatcher);
    }

    public static OfflineLicenseHelper newWidevineInstance(String str, HttpDataSource.Factory factory, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return newWidevineInstance(str, false, factory, eventDispatcher);
    }

    public final byte[] a(int i, @Nullable byte[] bArr, Format format) throws DrmSession.DrmSessionException {
        DefaultDrmSessionManager defaultDrmSessionManager = this.b;
        defaultDrmSessionManager.prepare();
        DrmSession b2 = b(i, bArr, format);
        DrmSession.DrmSessionException error = b2.getError();
        byte[] offlineLicenseKeySetId = b2.getOfflineLicenseKeySetId();
        b2.release(this.d);
        defaultDrmSessionManager.release();
        if (error == null) {
            return (byte[]) Assertions.checkNotNull(offlineLicenseKeySetId);
        }
        throw error;
    }

    public final DrmSession b(int i, @Nullable byte[] bArr, Format format) {
        Assertions.checkNotNull(format.s);
        DefaultDrmSessionManager defaultDrmSessionManager = this.b;
        defaultDrmSessionManager.setMode(i, bArr);
        ConditionVariable conditionVariable = this.a;
        conditionVariable.close();
        DrmSession acquireSession = defaultDrmSessionManager.acquireSession(this.c.getLooper(), this.d, format);
        conditionVariable.block();
        return (DrmSession) Assertions.checkNotNull(acquireSession);
    }

    public synchronized byte[] downloadLicense(Format format) throws DrmSession.DrmSessionException {
        boolean z;
        if (format.s != null) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        return a(2, (byte[]) null, format);
    }

    public synchronized Pair<Long, Long> getLicenseDurationRemainingSec(byte[] bArr) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(bArr);
        this.b.prepare();
        DrmSession b2 = b(1, bArr, e);
        DrmSession.DrmSessionException error = b2.getError();
        Pair<Long, Long> licenseDurationRemainingSec = WidevineUtil.getLicenseDurationRemainingSec(b2);
        b2.release(this.d);
        this.b.release();
        if (error == null) {
            return (Pair) Assertions.checkNotNull(licenseDurationRemainingSec);
        } else if (error.getCause() instanceof KeysExpiredException) {
            return Pair.create(0L, 0L);
        } else {
            throw error;
        }
    }

    public void release() {
        this.c.quit();
    }

    public synchronized void releaseLicense(byte[] bArr) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(bArr);
        a(3, bArr, e);
    }

    public synchronized byte[] renewLicense(byte[] bArr) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(bArr);
        return a(2, bArr, e);
    }

    public static OfflineLicenseHelper newWidevineInstance(String str, boolean z, HttpDataSource.Factory factory, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return newWidevineInstance(str, z, factory, (Map<String, String>) null, eventDispatcher);
    }

    public static OfflineLicenseHelper newWidevineInstance(String str, boolean z, HttpDataSource.Factory factory, @Nullable Map<String, String> map, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return new OfflineLicenseHelper(new DefaultDrmSessionManager.Builder().setKeyRequestParameters(map).build(new HttpMediaDrmCallback(str, z, factory)), eventDispatcher);
    }

    public OfflineLicenseHelper(DefaultDrmSessionManager defaultDrmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        this.b = defaultDrmSessionManager;
        this.d = eventDispatcher;
        HandlerThread handlerThread = new HandlerThread("ExoPlayer:OfflineLicenseHelper");
        this.c = handlerThread;
        handlerThread.start();
        this.a = new ConditionVariable();
        eventDispatcher.addEventListener(new Handler(handlerThread.getLooper()), new a());
    }
}
