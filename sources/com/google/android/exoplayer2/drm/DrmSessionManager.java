package com.google.android.exoplayer2.drm;

import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;

public interface DrmSessionManager {
    public static final a a = new a();

    public interface DrmSessionReference {
        public static final f2 a = new f2(21);

        void release();
    }

    public class a implements DrmSessionManager {
        @Nullable
        public DrmSession acquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
            if (format.s == null) {
                return null;
            }
            return new ErrorStateDrmSession(new DrmSession.DrmSessionException(new UnsupportedDrmException(1)));
        }

        @Nullable
        public Class<UnsupportedMediaCrypto> getExoMediaCryptoType(Format format) {
            if (format.s != null) {
                return UnsupportedMediaCrypto.class;
            }
            return null;
        }

        public /* bridge */ /* synthetic */ DrmSessionReference preacquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
            return w1.a(this, looper, eventDispatcher, format);
        }

        public /* bridge */ /* synthetic */ void prepare() {
            w1.b(this);
        }

        public /* bridge */ /* synthetic */ void release() {
            w1.c(this);
        }
    }

    @Nullable
    DrmSession acquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format);

    @Nullable
    Class<? extends ExoMediaCrypto> getExoMediaCryptoType(Format format);

    DrmSessionReference preacquireSession(Looper looper, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher, Format format);

    void prepare();

    void release();
}
