package com.google.android.exoplayer2.drm;

import android.media.DeniedByServerException;
import android.media.MediaCryptoException;
import android.media.MediaDrmException;
import android.media.NotProvisionedException;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.drm.DrmInitData;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ExoMediaDrm {

    public static final class AppManagedProvider implements Provider {
        public final ExoMediaDrm a;

        public AppManagedProvider(ExoMediaDrm exoMediaDrm) {
            this.a = exoMediaDrm;
        }

        public ExoMediaDrm acquireExoMediaDrm(UUID uuid) {
            ExoMediaDrm exoMediaDrm = this.a;
            exoMediaDrm.acquire();
            return exoMediaDrm;
        }
    }

    public static final class KeyRequest {
        public final byte[] a;
        public final String b;
        public final int c;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface RequestType {
        }

        public KeyRequest(byte[] bArr, String str) {
            this(bArr, str, Integer.MIN_VALUE);
        }

        public byte[] getData() {
            return this.a;
        }

        public String getLicenseServerUrl() {
            return this.b;
        }

        public int getRequestType() {
            return this.c;
        }

        public KeyRequest(byte[] bArr, String str, int i) {
            this.a = bArr;
            this.b = str;
            this.c = i;
        }
    }

    public static final class KeyStatus {
        public final int a;
        public final byte[] b;

        public KeyStatus(int i, byte[] bArr) {
            this.a = i;
            this.b = bArr;
        }

        public byte[] getKeyId() {
            return this.b;
        }

        public int getStatusCode() {
            return this.a;
        }
    }

    public interface OnEventListener {
        void onEvent(ExoMediaDrm exoMediaDrm, @Nullable byte[] bArr, int i, int i2, @Nullable byte[] bArr2);
    }

    public interface OnExpirationUpdateListener {
        void onExpirationUpdate(ExoMediaDrm exoMediaDrm, byte[] bArr, long j);
    }

    public interface OnKeyStatusChangeListener {
        void onKeyStatusChange(ExoMediaDrm exoMediaDrm, byte[] bArr, List<KeyStatus> list, boolean z);
    }

    public interface Provider {
        ExoMediaDrm acquireExoMediaDrm(UUID uuid);
    }

    public static final class ProvisionRequest {
        public final byte[] a;
        public final String b;

        public ProvisionRequest(byte[] bArr, String str) {
            this.a = bArr;
            this.b = str;
        }

        public byte[] getData() {
            return this.a;
        }

        public String getDefaultUrl() {
            return this.b;
        }
    }

    void acquire();

    void closeSession(byte[] bArr);

    ExoMediaCrypto createMediaCrypto(byte[] bArr) throws MediaCryptoException;

    Class<? extends ExoMediaCrypto> getExoMediaCryptoType();

    KeyRequest getKeyRequest(byte[] bArr, @Nullable List<DrmInitData.SchemeData> list, int i, @Nullable HashMap<String, String> hashMap) throws NotProvisionedException;

    @Nullable
    PersistableBundle getMetrics();

    byte[] getPropertyByteArray(String str);

    String getPropertyString(String str);

    ProvisionRequest getProvisionRequest();

    byte[] openSession() throws MediaDrmException;

    @Nullable
    byte[] provideKeyResponse(byte[] bArr, byte[] bArr2) throws NotProvisionedException, DeniedByServerException;

    void provideProvisionResponse(byte[] bArr) throws DeniedByServerException;

    Map<String, String> queryKeyStatus(byte[] bArr);

    void release();

    void restoreKeys(byte[] bArr, byte[] bArr2);

    void setOnEventListener(@Nullable OnEventListener onEventListener);

    void setOnExpirationUpdateListener(@Nullable OnExpirationUpdateListener onExpirationUpdateListener);

    void setOnKeyStatusChangeListener(@Nullable OnKeyStatusChangeListener onKeyStatusChangeListener);

    void setPropertyByteArray(String str, byte[] bArr);

    void setPropertyString(String str, String str2);
}
