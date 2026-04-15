package com.google.android.exoplayer2.drm;

import android.annotation.SuppressLint;
import android.media.ResourceBusyException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DefaultDrmSession;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

@RequiresApi(18)
public class DefaultDrmSessionManager implements DrmSessionManager {
    public final UUID b;
    public final ExoMediaDrm.Provider c;
    public final MediaDrmCallback d;
    public final HashMap<String, String> e;
    public final boolean f;
    public final int[] g;
    public final boolean h;
    public final d i;
    public final LoadErrorHandlingPolicy j;
    public final e k;
    public final long l;
    public final ArrayList m;
    public final Set<c> n;
    public final Set<DefaultDrmSession> o;
    public int p;
    @Nullable
    public ExoMediaDrm q;
    @Nullable
    public DefaultDrmSession r;
    @Nullable
    public DefaultDrmSession s;
    public Looper t;
    public Handler u;
    public int v;
    @Nullable
    public byte[] w;
    @Nullable
    public volatile b x;

    public static final class Builder {
        public final HashMap<String, String> a = new HashMap<>();
        public UUID b = C.d;
        public ExoMediaDrm.Provider c = FrameworkMediaDrm.d;
        public boolean d;
        public int[] e = new int[0];
        public boolean f;
        public LoadErrorHandlingPolicy g = new DefaultLoadErrorHandlingPolicy();
        public long h = 300000;

        public DefaultDrmSessionManager build(MediaDrmCallback mediaDrmCallback) {
            return new DefaultDrmSessionManager(this.b, this.c, mediaDrmCallback, this.a, this.d, this.e, this.f, this.g, this.h);
        }

        public Builder setKeyRequestParameters(@Nullable Map<String, String> map) {
            HashMap<String, String> hashMap = this.a;
            hashMap.clear();
            if (map != null) {
                hashMap.putAll(map);
            }
            return this;
        }

        public Builder setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            this.g = (LoadErrorHandlingPolicy) Assertions.checkNotNull(loadErrorHandlingPolicy);
            return this;
        }

        public Builder setMultiSession(boolean z) {
            this.d = z;
            return this;
        }

        public Builder setPlayClearSamplesWithoutKeys(boolean z) {
            this.f = z;
            return this;
        }

        public Builder setSessionKeepaliveMs(long j) {
            boolean z;
            if (j > 0 || j == -9223372036854775807L) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkArgument(z);
            this.h = j;
            return this;
        }

        public Builder setUseDrmSessionsForClearContent(int... iArr) {
            for (int i : iArr) {
                boolean z = true;
                if (!(i == 2 || i == 1)) {
                    z = false;
                }
                Assertions.checkArgument(z);
            }
            this.e = (int[]) iArr.clone();
            return this;
        }

        public Builder setUuidAndExoMediaDrmProvider(UUID uuid, ExoMediaDrm.Provider provider) {
            this.b = (UUID) Assertions.checkNotNull(uuid);
            this.c = (ExoMediaDrm.Provider) Assertions.checkNotNull(provider);
            return this;
        }
    }

    public static final class MissingSchemeDataException extends Exception {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public MissingSchemeDataException(java.util.UUID r3) {
            /*
                r2 = this;
                java.lang.String r3 = java.lang.String.valueOf(r3)
                int r0 = r3.length()
                int r0 = r0 + 29
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>(r0)
                java.lang.String r0 = "Media does not support uuid: "
                r1.append(r0)
                r1.append(r3)
                java.lang.String r3 = r1.toString()
                r2.<init>(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.DefaultDrmSessionManager.MissingSchemeDataException.<init>(java.util.UUID):void");
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public class a implements ExoMediaDrm.OnEventListener {
        public a() {
        }

        public void onEvent(ExoMediaDrm exoMediaDrm, @Nullable byte[] bArr, int i, int i2, @Nullable byte[] bArr2) {
            ((b) Assertions.checkNotNull(DefaultDrmSessionManager.this.x)).obtainMessage(i, bArr).sendToTarget();
        }
    }

    @SuppressLint({"HandlerLeak"})
    public class b extends Handler {
        public b(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            byte[] bArr = (byte[]) message.obj;
            if (bArr != null) {
                Iterator it = DefaultDrmSessionManager.this.m.iterator();
                while (it.hasNext()) {
                    DefaultDrmSession defaultDrmSession = (DefaultDrmSession) it.next();
                    if (defaultDrmSession.hasSessionId(bArr)) {
                        defaultDrmSession.onMediaDrmEvent(message.what);
                        return;
                    }
                }
            }
        }
    }

    public class c implements DrmSessionManager.DrmSessionReference {
        @Nullable
        public final DrmSessionEventListener.EventDispatcher c;
        @Nullable
        public DrmSession f;
        public boolean g;

        public c(@Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
            this.c = eventDispatcher;
        }

        public void acquire(Format format) {
            ((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.u)).post(new h2(3, this, format));
        }

        public void release() {
            Util.postOrRun((Handler) Assertions.checkNotNull(DefaultDrmSessionManager.this.u), new qb(1, this));
        }
    }

    public class d implements DefaultDrmSession.ProvisioningManager {
        public final HashSet a = new HashSet();
        @Nullable
        public DefaultDrmSession b;

        public d(DefaultDrmSessionManager defaultDrmSessionManager) {
        }

        public void onProvisionCompleted() {
            this.b = null;
            HashSet hashSet = this.a;
            ImmutableList copyOf = ImmutableList.copyOf(hashSet);
            hashSet.clear();
            UnmodifiableIterator it = copyOf.iterator();
            while (it.hasNext()) {
                ((DefaultDrmSession) it.next()).onProvisionCompleted();
            }
        }

        public void onProvisionError(Exception exc) {
            this.b = null;
            HashSet hashSet = this.a;
            ImmutableList copyOf = ImmutableList.copyOf(hashSet);
            hashSet.clear();
            UnmodifiableIterator it = copyOf.iterator();
            while (it.hasNext()) {
                ((DefaultDrmSession) it.next()).onProvisionError(exc);
            }
        }

        public void onSessionFullyReleased(DefaultDrmSession defaultDrmSession) {
            HashSet hashSet = this.a;
            hashSet.remove(defaultDrmSession);
            if (this.b == defaultDrmSession) {
                this.b = null;
                if (!hashSet.isEmpty()) {
                    DefaultDrmSession defaultDrmSession2 = (DefaultDrmSession) hashSet.iterator().next();
                    this.b = defaultDrmSession2;
                    defaultDrmSession2.provision();
                }
            }
        }

        public void provisionRequired(DefaultDrmSession defaultDrmSession) {
            this.a.add(defaultDrmSession);
            if (this.b == null) {
                this.b = defaultDrmSession;
                defaultDrmSession.provision();
            }
        }
    }

    public class e implements DefaultDrmSession.ReferenceCountListener {
        public e() {
        }

        public void onReferenceCountDecremented(DefaultDrmSession defaultDrmSession, int i) {
            DefaultDrmSessionManager defaultDrmSessionManager = DefaultDrmSessionManager.this;
            if (i == 1 && defaultDrmSessionManager.p > 0) {
                long j = defaultDrmSessionManager.l;
                if (j != -9223372036854775807L) {
                    defaultDrmSessionManager.o.add(defaultDrmSession);
                    ((Handler) Assertions.checkNotNull(defaultDrmSessionManager.u)).postAtTime(new qb(2, defaultDrmSession), defaultDrmSession, SystemClock.uptimeMillis() + j);
                    defaultDrmSessionManager.g();
                }
            }
            if (i == 0) {
                defaultDrmSessionManager.m.remove(defaultDrmSession);
                if (defaultDrmSessionManager.r == defaultDrmSession) {
                    defaultDrmSessionManager.r = null;
                }
                if (defaultDrmSessionManager.s == defaultDrmSession) {
                    defaultDrmSessionManager.s = null;
                }
                defaultDrmSessionManager.i.onSessionFullyReleased(defaultDrmSession);
                if (defaultDrmSessionManager.l != -9223372036854775807L) {
                    ((Handler) Assertions.checkNotNull(defaultDrmSessionManager.u)).removeCallbacksAndMessages(defaultDrmSession);
                    defaultDrmSessionManager.o.remove(defaultDrmSession);
                }
            }
            defaultDrmSessionManager.g();
        }

        public void onReferenceCountIncremented(DefaultDrmSession defaultDrmSession, int i) {
            DefaultDrmSessionManager defaultDrmSessionManager = DefaultDrmSessionManager.this;
            if (defaultDrmSessionManager.l != -9223372036854775807L) {
                defaultDrmSessionManager.o.remove(defaultDrmSession);
                ((Handler) Assertions.checkNotNull(defaultDrmSessionManager.u)).removeCallbacksAndMessages(defaultDrmSession);
            }
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm exoMediaDrm, MediaDrmCallback mediaDrmCallback, @Nullable HashMap<String, String> hashMap) {
        this(uuid, exoMediaDrm, mediaDrmCallback, hashMap == null ? new HashMap<>() : hashMap, false, 3);
    }

    public static boolean b(DefaultDrmSession defaultDrmSession) {
        if (defaultDrmSession.getState() != 1 || (Util.a >= 19 && !(((DrmSession.DrmSessionException) Assertions.checkNotNull(defaultDrmSession.getError())).getCause() instanceof ResourceBusyException))) {
            return false;
        }
        return true;
    }

    public static ArrayList e(DrmInitData drmInitData, UUID uuid, boolean z) {
        boolean z2;
        ArrayList arrayList = new ArrayList(drmInitData.h);
        for (int i2 = 0; i2 < drmInitData.h; i2++) {
            DrmInitData.SchemeData schemeData = drmInitData.get(i2);
            if (schemeData.matches(uuid) || (C.c.equals(uuid) && schemeData.matches(C.b))) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2 && (schemeData.i != null || z)) {
                arrayList.add(schemeData);
            }
        }
        return arrayList;
    }

    @Nullable
    public final DrmSession a(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format, boolean z) {
        ArrayList arrayList;
        if (this.x == null) {
            this.x = new b(looper);
        }
        DrmInitData drmInitData = format.s;
        boolean z2 = false;
        DefaultDrmSession defaultDrmSession = null;
        if (drmInitData == null) {
            int trackType = MimeTypes.getTrackType(format.p);
            ExoMediaDrm exoMediaDrm = (ExoMediaDrm) Assertions.checkNotNull(this.q);
            if (FrameworkMediaCrypto.class.equals(exoMediaDrm.getExoMediaCryptoType()) && FrameworkMediaCrypto.d) {
                z2 = true;
            }
            if (z2 || Util.linearSearch(this.g, trackType) == -1 || UnsupportedMediaCrypto.class.equals(exoMediaDrm.getExoMediaCryptoType())) {
                return null;
            }
            DefaultDrmSession defaultDrmSession2 = this.r;
            if (defaultDrmSession2 == null) {
                DefaultDrmSession d2 = d(ImmutableList.of(), true, (DrmSessionEventListener.EventDispatcher) null, z);
                this.m.add(d2);
                this.r = d2;
            } else {
                defaultDrmSession2.acquire((DrmSessionEventListener.EventDispatcher) null);
            }
            return this.r;
        }
        if (this.w == null) {
            arrayList = e((DrmInitData) Assertions.checkNotNull(drmInitData), this.b, false);
            if (arrayList.isEmpty()) {
                MissingSchemeDataException missingSchemeDataException = new MissingSchemeDataException(this.b);
                Log.e("DefaultDrmSessionMgr", "DRM error", missingSchemeDataException);
                if (eventDispatcher != null) {
                    eventDispatcher.drmSessionManagerError(missingSchemeDataException);
                }
                return new ErrorStateDrmSession(new DrmSession.DrmSessionException(missingSchemeDataException));
            }
        } else {
            arrayList = null;
        }
        if (this.f) {
            Iterator it = this.m.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                DefaultDrmSession defaultDrmSession3 = (DefaultDrmSession) it.next();
                if (Util.areEqual(defaultDrmSession3.a, arrayList)) {
                    defaultDrmSession = defaultDrmSession3;
                    break;
                }
            }
        } else {
            defaultDrmSession = this.s;
        }
        if (defaultDrmSession == null) {
            defaultDrmSession = d(arrayList, false, eventDispatcher, z);
            if (!this.f) {
                this.s = defaultDrmSession;
            }
            this.m.add(defaultDrmSession);
        } else {
            defaultDrmSession.acquire(eventDispatcher);
        }
        return defaultDrmSession;
    }

    @Nullable
    public DrmSession acquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
        boolean z;
        if (this.p > 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        f(looper);
        return a(looper, eventDispatcher, format, true);
    }

    public final DefaultDrmSession c(@Nullable List<DrmInitData.SchemeData> list, boolean z, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
        Assertions.checkNotNull(this.q);
        List<DrmInitData.SchemeData> list2 = list;
        DefaultDrmSession defaultDrmSession = new DefaultDrmSession(this.b, this.q, this.i, this.k, list2, this.v, this.h | z, z, this.w, this.e, this.d, (Looper) Assertions.checkNotNull(this.t), this.j);
        defaultDrmSession.acquire(eventDispatcher);
        if (this.l != -9223372036854775807L) {
            defaultDrmSession.acquire((DrmSessionEventListener.EventDispatcher) null);
        }
        return defaultDrmSession;
    }

    public final DefaultDrmSession d(@Nullable List<DrmInitData.SchemeData> list, boolean z, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, boolean z2) {
        DefaultDrmSession c2 = c(list, z, eventDispatcher);
        boolean b2 = b(c2);
        long j2 = this.l;
        if (b2) {
            Set<DefaultDrmSession> set = this.o;
            if (!set.isEmpty()) {
                UnmodifiableIterator<DefaultDrmSession> it = ImmutableSet.copyOf(set).iterator();
                while (it.hasNext()) {
                    it.next().release((DrmSessionEventListener.EventDispatcher) null);
                }
                c2.release(eventDispatcher);
                if (j2 != -9223372036854775807L) {
                    c2.release((DrmSessionEventListener.EventDispatcher) null);
                }
                c2 = c(list, z, eventDispatcher);
            }
        }
        if (!b(c2) || !z2) {
            return c2;
        }
        Set<c> set2 = this.n;
        if (set2.isEmpty()) {
            return c2;
        }
        UnmodifiableIterator<c> it2 = ImmutableSet.copyOf(set2).iterator();
        while (it2.hasNext()) {
            it2.next().release();
        }
        c2.release(eventDispatcher);
        if (j2 != -9223372036854775807L) {
            c2.release((DrmSessionEventListener.EventDispatcher) null);
        }
        return c(list, z, eventDispatcher);
    }

    @EnsuresNonNull({"this.playbackLooper", "this.playbackHandler"})
    public final synchronized void f(Looper looper) {
        boolean z;
        Looper looper2 = this.t;
        if (looper2 == null) {
            this.t = looper;
            this.u = new Handler(looper);
        } else {
            if (looper2 == looper) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkState(z);
            Assertions.checkNotNull(this.u);
        }
    }

    public final void g() {
        if (this.q != null && this.p == 0 && this.m.isEmpty() && this.n.isEmpty()) {
            ((ExoMediaDrm) Assertions.checkNotNull(this.q)).release();
            this.q = null;
        }
    }

    @Nullable
    public Class<? extends ExoMediaCrypto> getExoMediaCryptoType(Format format) {
        Class<? extends ExoMediaCrypto> exoMediaCryptoType = ((ExoMediaDrm) Assertions.checkNotNull(this.q)).getExoMediaCryptoType();
        DrmInitData drmInitData = format.s;
        if (drmInitData == null) {
            if (Util.linearSearch(this.g, MimeTypes.getTrackType(format.p)) != -1) {
                return exoMediaCryptoType;
            }
            return null;
        }
        boolean z = true;
        if (this.w == null) {
            UUID uuid = this.b;
            if (e(drmInitData, uuid, true).isEmpty()) {
                if (drmInitData.h == 1 && drmInitData.get(0).matches(C.b)) {
                    String valueOf = String.valueOf(uuid);
                    StringBuilder sb = new StringBuilder(valueOf.length() + 72);
                    sb.append("DrmInitData only contains common PSSH SchemeData. Assuming support for: ");
                    sb.append(valueOf);
                    Log.w("DefaultDrmSessionMgr", sb.toString());
                }
            }
            String str = drmInitData.g;
            if (str != null) {
                if (!"cenc".equals(str)) {
                    z = !"cbcs".equals(str) ? false : false;
                }
            }
        }
        if (z) {
            return exoMediaCryptoType;
        }
        return UnsupportedMediaCrypto.class;
    }

    public DrmSessionManager.DrmSessionReference preacquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
        boolean z;
        if (this.p > 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        f(looper);
        c cVar = new c(eventDispatcher);
        cVar.acquire(format);
        return cVar;
    }

    public final void prepare() {
        int i2 = this.p;
        this.p = i2 + 1;
        if (i2 == 0) {
            if (this.q == null) {
                ExoMediaDrm acquireExoMediaDrm = this.c.acquireExoMediaDrm(this.b);
                this.q = acquireExoMediaDrm;
                acquireExoMediaDrm.setOnEventListener(new a());
            } else if (this.l != -9223372036854775807L) {
                int i3 = 0;
                while (true) {
                    ArrayList arrayList = this.m;
                    if (i3 < arrayList.size()) {
                        ((DefaultDrmSession) arrayList.get(i3)).acquire((DrmSessionEventListener.EventDispatcher) null);
                        i3++;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public final void release() {
        int i2 = this.p - 1;
        this.p = i2;
        if (i2 == 0) {
            if (this.l != -9223372036854775807L) {
                ArrayList arrayList = new ArrayList(this.m);
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    ((DefaultDrmSession) arrayList.get(i3)).release((DrmSessionEventListener.EventDispatcher) null);
                }
            }
            UnmodifiableIterator<c> it = ImmutableSet.copyOf(this.n).iterator();
            while (it.hasNext()) {
                it.next().release();
            }
            g();
        }
    }

    public void setMode(int i2, @Nullable byte[] bArr) {
        Assertions.checkState(this.m.isEmpty());
        if (i2 == 1 || i2 == 3) {
            Assertions.checkNotNull(bArr);
        }
        this.v = i2;
        this.w = bArr;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm exoMediaDrm, MediaDrmCallback mediaDrmCallback, @Nullable HashMap<String, String> hashMap, boolean z) {
        this(uuid, exoMediaDrm, mediaDrmCallback, hashMap == null ? new HashMap<>() : hashMap, z, 3);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm exoMediaDrm, MediaDrmCallback mediaDrmCallback, @Nullable HashMap<String, String> hashMap, boolean z, int i2) {
        this(uuid, new ExoMediaDrm.AppManagedProvider(exoMediaDrm), mediaDrmCallback, hashMap == null ? new HashMap<>() : hashMap, z, new int[0], false, new DefaultLoadErrorHandlingPolicy(i2), 300000);
        ExoMediaDrm exoMediaDrm2 = exoMediaDrm;
    }

    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm.Provider provider, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, boolean z, int[] iArr, boolean z2, LoadErrorHandlingPolicy loadErrorHandlingPolicy, long j2) {
        Assertions.checkNotNull(uuid);
        Assertions.checkArgument(!C.b.equals(uuid), "Use C.CLEARKEY_UUID instead");
        this.b = uuid;
        this.c = provider;
        this.d = mediaDrmCallback;
        this.e = hashMap;
        this.f = z;
        this.g = iArr;
        this.h = z2;
        this.j = loadErrorHandlingPolicy;
        this.i = new d(this);
        this.k = new e();
        this.v = 0;
        this.m = new ArrayList();
        this.n = Sets.newIdentityHashSet();
        this.o = Sets.newIdentityHashSet();
        this.l = j2;
    }
}
