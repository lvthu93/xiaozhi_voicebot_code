package com.google.android.exoplayer2.drm;

import android.annotation.SuppressLint;
import android.media.NotProvisionedException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CopyOnWriteMultiset;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

@RequiresApi(18)
public final class DefaultDrmSession implements DrmSession {
    @Nullable
    public final List<DrmInitData.SchemeData> a;
    public final ExoMediaDrm b;
    public final ProvisioningManager c;
    public final ReferenceCountListener d;
    public final int e;
    public final boolean f;
    public final boolean g;
    public final HashMap<String, String> h;
    public final CopyOnWriteMultiset<DrmSessionEventListener.EventDispatcher> i;
    public final LoadErrorHandlingPolicy j;
    public final MediaDrmCallback k;
    public final UUID l;
    public final c m;
    public int n;
    public int o;
    @Nullable
    public HandlerThread p;
    @Nullable
    public a q;
    @Nullable
    public ExoMediaCrypto r;
    @Nullable
    public DrmSession.DrmSessionException s;
    @Nullable
    public byte[] t;
    public byte[] u;
    @Nullable
    public ExoMediaDrm.KeyRequest v;
    @Nullable
    public ExoMediaDrm.ProvisionRequest w;

    public interface ProvisioningManager {
        void onProvisionCompleted();

        void onProvisionError(Exception exc);

        void provisionRequired(DefaultDrmSession defaultDrmSession);
    }

    public interface ReferenceCountListener {
        void onReferenceCountDecremented(DefaultDrmSession defaultDrmSession, int i);

        void onReferenceCountIncremented(DefaultDrmSession defaultDrmSession, int i);
    }

    public static final class UnexpectedDrmSessionException extends IOException {
        public UnexpectedDrmSessionException(@Nullable Throwable th) {
            super(th);
        }
    }

    @SuppressLint({"HandlerLeak"})
    public class a extends Handler {
        @GuardedBy("this")
        public boolean a;

        public a(Looper looper) {
            super(looper);
        }

        public final boolean a(Message message, MediaDrmCallbackException mediaDrmCallbackException) {
            IOException iOException;
            MediaDrmCallbackException mediaDrmCallbackException2 = mediaDrmCallbackException;
            b bVar = (b) message.obj;
            if (!bVar.b) {
                return false;
            }
            int i = bVar.e + 1;
            bVar.e = i;
            if (i > DefaultDrmSession.this.j.getMinimumLoadableRetryCount(3)) {
                return false;
            }
            LoadEventInfo loadEventInfo = new LoadEventInfo(bVar.a, mediaDrmCallbackException2.c, mediaDrmCallbackException2.f, mediaDrmCallbackException2.g, SystemClock.elapsedRealtime(), SystemClock.elapsedRealtime() - bVar.c, mediaDrmCallbackException2.h);
            MediaLoadData mediaLoadData = new MediaLoadData(3);
            if (mediaDrmCallbackException.getCause() instanceof IOException) {
                iOException = (IOException) mediaDrmCallbackException.getCause();
            } else {
                iOException = new UnexpectedDrmSessionException(mediaDrmCallbackException.getCause());
            }
            long retryDelayMsFor = DefaultDrmSession.this.j.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, mediaLoadData, iOException, bVar.e));
            if (retryDelayMsFor == -9223372036854775807L) {
                return false;
            }
            synchronized (this) {
                if (this.a) {
                    return false;
                }
                sendMessageDelayed(Message.obtain(message), retryDelayMsFor);
                return true;
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: com.google.android.exoplayer2.drm.MediaDrmCallbackException} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: com.google.android.exoplayer2.drm.MediaDrmCallbackException} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: com.google.android.exoplayer2.drm.MediaDrmCallbackException} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: byte[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: byte[]} */
        /* JADX WARNING: type inference failed for: r1v2, types: [java.lang.Throwable, java.lang.Exception] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r6) {
            /*
                r5 = this;
                java.lang.Object r0 = r6.obj
                com.google.android.exoplayer2.drm.DefaultDrmSession$b r0 = (com.google.android.exoplayer2.drm.DefaultDrmSession.b) r0
                int r1 = r6.what     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                if (r1 == 0) goto L_0x0020
                r2 = 1
                if (r1 != r2) goto L_0x001a
                com.google.android.exoplayer2.drm.DefaultDrmSession r1 = com.google.android.exoplayer2.drm.DefaultDrmSession.this     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                com.google.android.exoplayer2.drm.MediaDrmCallback r2 = r1.k     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                java.util.UUID r1 = r1.l     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                java.lang.Object r3 = r0.d     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                com.google.android.exoplayer2.drm.ExoMediaDrm$KeyRequest r3 = (com.google.android.exoplayer2.drm.ExoMediaDrm.KeyRequest) r3     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                byte[] r1 = r2.executeKeyRequest(r1, r3)     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                goto L_0x0040
            L_0x001a:
                java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                r1.<init>()     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                throw r1     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
            L_0x0020:
                com.google.android.exoplayer2.drm.DefaultDrmSession r1 = com.google.android.exoplayer2.drm.DefaultDrmSession.this     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                com.google.android.exoplayer2.drm.MediaDrmCallback r2 = r1.k     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                java.util.UUID r1 = r1.l     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                java.lang.Object r3 = r0.d     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                com.google.android.exoplayer2.drm.ExoMediaDrm$ProvisionRequest r3 = (com.google.android.exoplayer2.drm.ExoMediaDrm.ProvisionRequest) r3     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                byte[] r1 = r2.executeProvisionRequest(r1, r3)     // Catch:{ MediaDrmCallbackException -> 0x0038, Exception -> 0x002f }
                goto L_0x0040
            L_0x002f:
                r1 = move-exception
                java.lang.String r2 = "DefaultDrmSession"
                java.lang.String r3 = "Key/provisioning request produced an unexpected exception. Not retrying."
                com.google.android.exoplayer2.util.Log.w(r2, r3, r1)
                goto L_0x0040
            L_0x0038:
                r1 = move-exception
                boolean r2 = r5.a(r6, r1)
                if (r2 == 0) goto L_0x0040
                return
            L_0x0040:
                com.google.android.exoplayer2.drm.DefaultDrmSession r2 = com.google.android.exoplayer2.drm.DefaultDrmSession.this
                com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy r2 = r2.j
                long r3 = r0.a
                r2.onLoadTaskConcluded(r3)
                monitor-enter(r5)
                boolean r2 = r5.a     // Catch:{ all -> 0x0063 }
                if (r2 != 0) goto L_0x0061
                com.google.android.exoplayer2.drm.DefaultDrmSession r2 = com.google.android.exoplayer2.drm.DefaultDrmSession.this     // Catch:{ all -> 0x0063 }
                com.google.android.exoplayer2.drm.DefaultDrmSession$c r2 = r2.m     // Catch:{ all -> 0x0063 }
                int r6 = r6.what     // Catch:{ all -> 0x0063 }
                java.lang.Object r0 = r0.d     // Catch:{ all -> 0x0063 }
                android.util.Pair r0 = android.util.Pair.create(r0, r1)     // Catch:{ all -> 0x0063 }
                android.os.Message r6 = r2.obtainMessage(r6, r0)     // Catch:{ all -> 0x0063 }
                r6.sendToTarget()     // Catch:{ all -> 0x0063 }
            L_0x0061:
                monitor-exit(r5)     // Catch:{ all -> 0x0063 }
                return
            L_0x0063:
                r6 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x0063 }
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.DefaultDrmSession.a.handleMessage(android.os.Message):void");
        }

        public synchronized void release() {
            removeCallbacksAndMessages((Object) null);
            this.a = true;
        }
    }

    public static final class b {
        public final long a;
        public final boolean b;
        public final long c;
        public final Object d;
        public int e;

        public b(long j, boolean z, long j2, Object obj) {
            this.a = j;
            this.b = z;
            this.c = j2;
            this.d = obj;
        }
    }

    @SuppressLint({"HandlerLeak"})
    public class c extends Handler {
        public c(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            Pair pair = (Pair) message.obj;
            Object obj = pair.first;
            Object obj2 = pair.second;
            int i = message.what;
            DefaultDrmSession defaultDrmSession = DefaultDrmSession.this;
            if (i != 0) {
                if (i == 1 && obj == defaultDrmSession.v && defaultDrmSession.b()) {
                    defaultDrmSession.v = null;
                    boolean z = obj2 instanceof Exception;
                    ProvisioningManager provisioningManager = defaultDrmSession.c;
                    if (z) {
                        Exception exc = (Exception) obj2;
                        if (exc instanceof NotProvisionedException) {
                            provisioningManager.provisionRequired(defaultDrmSession);
                        } else {
                            defaultDrmSession.c(exc);
                        }
                    } else {
                        try {
                            byte[] bArr = (byte[]) obj2;
                            CopyOnWriteMultiset<DrmSessionEventListener.EventDispatcher> copyOnWriteMultiset = defaultDrmSession.i;
                            ExoMediaDrm exoMediaDrm = defaultDrmSession.b;
                            int i2 = defaultDrmSession.e;
                            if (i2 == 3) {
                                exoMediaDrm.provideKeyResponse((byte[]) Util.castNonNull(defaultDrmSession.u), bArr);
                                for (DrmSessionEventListener.EventDispatcher drmKeysRemoved : copyOnWriteMultiset.elementSet()) {
                                    drmKeysRemoved.drmKeysRemoved();
                                }
                                return;
                            }
                            byte[] provideKeyResponse = exoMediaDrm.provideKeyResponse(defaultDrmSession.t, bArr);
                            if (!((i2 != 2 && (i2 != 0 || defaultDrmSession.u == null)) || provideKeyResponse == null || provideKeyResponse.length == 0)) {
                                defaultDrmSession.u = provideKeyResponse;
                            }
                            defaultDrmSession.n = 4;
                            for (DrmSessionEventListener.EventDispatcher drmKeysLoaded : copyOnWriteMultiset.elementSet()) {
                                drmKeysLoaded.drmKeysLoaded();
                            }
                        } catch (Exception e) {
                            if (e instanceof NotProvisionedException) {
                                provisioningManager.provisionRequired(defaultDrmSession);
                            } else {
                                defaultDrmSession.c(e);
                            }
                        }
                    }
                }
            } else if (obj != defaultDrmSession.w) {
            } else {
                if (defaultDrmSession.n == 2 || defaultDrmSession.b()) {
                    defaultDrmSession.w = null;
                    boolean z2 = obj2 instanceof Exception;
                    ProvisioningManager provisioningManager2 = defaultDrmSession.c;
                    if (z2) {
                        provisioningManager2.onProvisionError((Exception) obj2);
                        return;
                    }
                    try {
                        defaultDrmSession.b.provideProvisionResponse((byte[]) obj2);
                        provisioningManager2.onProvisionCompleted();
                    } catch (Exception e2) {
                        provisioningManager2.onProvisionError(e2);
                    }
                }
            }
        }
    }

    public DefaultDrmSession(UUID uuid, ExoMediaDrm exoMediaDrm, ProvisioningManager provisioningManager, ReferenceCountListener referenceCountListener, @Nullable List<DrmInitData.SchemeData> list, int i2, boolean z, boolean z2, @Nullable byte[] bArr, HashMap<String, String> hashMap, MediaDrmCallback mediaDrmCallback, Looper looper, LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        if (i2 == 1 || i2 == 3) {
            Assertions.checkNotNull(bArr);
        }
        this.l = uuid;
        this.c = provisioningManager;
        this.d = referenceCountListener;
        this.b = exoMediaDrm;
        this.e = i2;
        this.f = z;
        this.g = z2;
        if (bArr != null) {
            this.u = bArr;
            this.a = null;
        } else {
            this.a = Collections.unmodifiableList((List) Assertions.checkNotNull(list));
        }
        this.h = hashMap;
        this.k = mediaDrmCallback;
        this.i = new CopyOnWriteMultiset<>();
        this.j = loadErrorHandlingPolicy;
        this.n = 2;
        this.m = new c(looper);
    }

    @RequiresNonNull({"sessionId"})
    public final void a(boolean z) {
        long j2;
        if (!this.g) {
            byte[] bArr = (byte[]) Util.castNonNull(this.t);
            boolean z2 = false;
            ExoMediaDrm exoMediaDrm = this.b;
            int i2 = this.e;
            if (i2 == 0 || i2 == 1) {
                byte[] bArr2 = this.u;
                if (bArr2 == null) {
                    e(bArr, 1, z);
                    return;
                }
                if (this.n != 4) {
                    try {
                        exoMediaDrm.restoreKeys(this.t, bArr2);
                        z2 = true;
                    } catch (Exception e2) {
                        c(e2);
                    }
                    if (!z2) {
                        return;
                    }
                }
                if (!C.d.equals(this.l)) {
                    j2 = Long.MAX_VALUE;
                } else {
                    Pair pair = (Pair) Assertions.checkNotNull(WidevineUtil.getLicenseDurationRemainingSec(this));
                    j2 = Math.min(((Long) pair.first).longValue(), ((Long) pair.second).longValue());
                }
                if (i2 == 0 && j2 <= 60) {
                    StringBuilder sb = new StringBuilder(88);
                    sb.append("Offline license has expired or will expire soon. Remaining seconds: ");
                    sb.append(j2);
                    Log.d("DefaultDrmSession", sb.toString());
                    e(bArr, 2, z);
                } else if (j2 <= 0) {
                    c(new KeysExpiredException());
                } else {
                    this.n = 4;
                    for (DrmSessionEventListener.EventDispatcher drmKeysRestored : this.i.elementSet()) {
                        drmKeysRestored.drmKeysRestored();
                    }
                }
            } else if (i2 == 2) {
                byte[] bArr3 = this.u;
                if (bArr3 != null) {
                    try {
                        exoMediaDrm.restoreKeys(this.t, bArr3);
                        z2 = true;
                    } catch (Exception e3) {
                        c(e3);
                    }
                    if (!z2) {
                        return;
                    }
                }
                e(bArr, 2, z);
            } else if (i2 == 3) {
                Assertions.checkNotNull(this.u);
                Assertions.checkNotNull(this.t);
                e(this.u, 3, z);
            }
        }
    }

    public void acquire(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
        boolean z;
        boolean z2 = false;
        if (this.o >= 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        CopyOnWriteMultiset<DrmSessionEventListener.EventDispatcher> copyOnWriteMultiset = this.i;
        if (eventDispatcher != null) {
            copyOnWriteMultiset.add(eventDispatcher);
        }
        int i2 = this.o + 1;
        this.o = i2;
        if (i2 == 1) {
            if (this.n == 2) {
                z2 = true;
            }
            Assertions.checkState(z2);
            HandlerThread handlerThread = new HandlerThread("ExoPlayer:DrmRequestHandler");
            this.p = handlerThread;
            handlerThread.start();
            this.q = new a(this.p.getLooper());
            if (d()) {
                a(true);
            }
        } else if (eventDispatcher != null && b() && copyOnWriteMultiset.count(eventDispatcher) == 1) {
            eventDispatcher.drmSessionAcquired(this.n);
        }
        this.d.onReferenceCountIncremented(this, this.o);
    }

    @EnsuresNonNullIf(expression = {"sessionId"}, result = true)
    public final boolean b() {
        int i2 = this.n;
        return i2 == 3 || i2 == 4;
    }

    public final void c(Exception exc) {
        this.s = new DrmSession.DrmSessionException(exc);
        Log.e("DefaultDrmSession", "DRM session error", exc);
        for (DrmSessionEventListener.EventDispatcher drmSessionManagerError : this.i.elementSet()) {
            drmSessionManagerError.drmSessionManagerError(exc);
        }
        if (this.n != 4) {
            this.n = 1;
        }
    }

    @EnsuresNonNullIf(expression = {"sessionId"}, result = true)
    public final boolean d() {
        ExoMediaDrm exoMediaDrm = this.b;
        if (b()) {
            return true;
        }
        try {
            byte[] openSession = exoMediaDrm.openSession();
            this.t = openSession;
            this.r = exoMediaDrm.createMediaCrypto(openSession);
            this.n = 3;
            for (DrmSessionEventListener.EventDispatcher drmSessionAcquired : this.i.elementSet()) {
                drmSessionAcquired.drmSessionAcquired(3);
            }
            Assertions.checkNotNull(this.t);
            return true;
        } catch (NotProvisionedException unused) {
            this.c.provisionRequired(this);
            return false;
        } catch (Exception e2) {
            c(e2);
            return false;
        }
    }

    public final void e(byte[] bArr, int i2, boolean z) {
        try {
            this.v = this.b.getKeyRequest(bArr, this.a, i2, this.h);
            a aVar = (a) Util.castNonNull(this.q);
            Object checkNotNull = Assertions.checkNotNull(this.v);
            aVar.getClass();
            aVar.obtainMessage(1, new b(LoadEventInfo.getNewId(), z, SystemClock.elapsedRealtime(), checkNotNull)).sendToTarget();
        } catch (Exception e2) {
            if (e2 instanceof NotProvisionedException) {
                this.c.provisionRequired(this);
            } else {
                c(e2);
            }
        }
    }

    @Nullable
    public final DrmSession.DrmSessionException getError() {
        if (this.n == 1) {
            return this.s;
        }
        return null;
    }

    @Nullable
    public final ExoMediaCrypto getMediaCrypto() {
        return this.r;
    }

    @Nullable
    public byte[] getOfflineLicenseKeySetId() {
        return this.u;
    }

    public final UUID getSchemeUuid() {
        return this.l;
    }

    public final int getState() {
        return this.n;
    }

    public boolean hasSessionId(byte[] bArr) {
        return Arrays.equals(this.t, bArr);
    }

    public void onMediaDrmEvent(int i2) {
        if (i2 == 2 && this.e == 0 && this.n == 4) {
            Util.castNonNull(this.t);
            a(false);
        }
    }

    public void onProvisionCompleted() {
        if (d()) {
            a(true);
        }
    }

    public void onProvisionError(Exception exc) {
        c(exc);
    }

    public boolean playClearSamplesWithoutKeys() {
        return this.f;
    }

    public void provision() {
        this.w = this.b.getProvisionRequest();
        a aVar = (a) Util.castNonNull(this.q);
        Object checkNotNull = Assertions.checkNotNull(this.w);
        aVar.getClass();
        aVar.obtainMessage(0, new b(LoadEventInfo.getNewId(), true, SystemClock.elapsedRealtime(), checkNotNull)).sendToTarget();
    }

    @Nullable
    public Map<String, String> queryKeyStatus() {
        byte[] bArr = this.t;
        if (bArr == null) {
            return null;
        }
        return this.b.queryKeyStatus(bArr);
    }

    public void release(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
        boolean z;
        if (this.o > 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        int i2 = this.o - 1;
        this.o = i2;
        if (i2 == 0) {
            this.n = 0;
            ((c) Util.castNonNull(this.m)).removeCallbacksAndMessages((Object) null);
            ((a) Util.castNonNull(this.q)).release();
            this.q = null;
            ((HandlerThread) Util.castNonNull(this.p)).quit();
            this.p = null;
            this.r = null;
            this.s = null;
            this.v = null;
            this.w = null;
            byte[] bArr = this.t;
            if (bArr != null) {
                this.b.closeSession(bArr);
                this.t = null;
            }
        }
        if (eventDispatcher != null) {
            CopyOnWriteMultiset<DrmSessionEventListener.EventDispatcher> copyOnWriteMultiset = this.i;
            copyOnWriteMultiset.remove(eventDispatcher);
            if (copyOnWriteMultiset.count(eventDispatcher) == 0) {
                eventDispatcher.drmSessionReleased();
            }
        }
        this.d.onReferenceCountDecremented(this, this.o);
    }
}
