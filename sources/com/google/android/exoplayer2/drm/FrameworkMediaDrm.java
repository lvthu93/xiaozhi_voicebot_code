package com.google.android.exoplayer2.drm;

import android.media.DeniedByServerException;
import android.media.MediaCryptoException;
import android.media.MediaDrm;
import android.media.MediaDrmException;
import android.media.NotProvisionedException;
import android.media.UnsupportedSchemeException;
import android.os.Handler;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Map;
import java.util.UUID;

@RequiresApi(18)
public final class FrameworkMediaDrm implements ExoMediaDrm {
    public static final m3 d = new m3();
    public final UUID a;
    public final MediaDrm b;
    public int c = 1;

    public FrameworkMediaDrm(UUID uuid) throws UnsupportedSchemeException {
        Assertions.checkNotNull(uuid);
        Assertions.checkArgument(!C.b.equals(uuid), "Use C.CLEARKEY_UUID instead");
        this.a = uuid;
        MediaDrm mediaDrm = new MediaDrm(a(uuid));
        this.b = mediaDrm;
        if (C.d.equals(uuid) && "ASUS_Z00AD".equals(Util.d)) {
            mediaDrm.setPropertyString("securityLevel", "L3");
        }
    }

    public static UUID a(UUID uuid) {
        return (Util.a >= 27 || !C.c.equals(uuid)) ? uuid : C.b;
    }

    public static boolean isCryptoSchemeSupported(UUID uuid) {
        return MediaDrm.isCryptoSchemeSupported(a(uuid));
    }

    public static FrameworkMediaDrm newInstance(UUID uuid) throws UnsupportedDrmException {
        try {
            return new FrameworkMediaDrm(uuid);
        } catch (UnsupportedSchemeException e) {
            throw new UnsupportedDrmException(1, e);
        } catch (Exception e2) {
            throw new UnsupportedDrmException(2, e2);
        }
    }

    public synchronized void acquire() {
        boolean z;
        if (this.c > 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        this.c++;
    }

    public void closeSession(byte[] bArr) {
        this.b.closeSession(bArr);
    }

    public Class<FrameworkMediaCrypto> getExoMediaCryptoType() {
        return FrameworkMediaCrypto.class;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01a3, code lost:
        if ("AFTT".equals(r6) == false) goto L_0x01ac;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.drm.ExoMediaDrm.KeyRequest getKeyRequest(byte[] r16, @androidx.annotation.Nullable java.util.List<com.google.android.exoplayer2.drm.DrmInitData.SchemeData> r17, int r18, @androidx.annotation.Nullable java.util.HashMap<java.lang.String, java.lang.String> r19) throws android.media.NotProvisionedException {
        /*
            r15 = this;
            r0 = r15
            r1 = r17
            r2 = 23
            java.util.UUID r3 = r0.a
            if (r1 == 0) goto L_0x01cd
            java.util.UUID r4 = com.google.android.exoplayer2.C.d
            boolean r4 = r4.equals(r3)
            r5 = 1
            r6 = 0
            if (r4 != 0) goto L_0x001b
            java.lang.Object r1 = r1.get(r6)
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData r1 = (com.google.android.exoplayer2.drm.DrmInitData.SchemeData) r1
            goto L_0x00bb
        L_0x001b:
            int r4 = com.google.android.exoplayer2.util.Util.a
            r7 = 28
            if (r4 < r7) goto L_0x008c
            int r4 = r17.size()
            if (r4 <= r5) goto L_0x008c
            java.lang.Object r4 = r1.get(r6)
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData r4 = (com.google.android.exoplayer2.drm.DrmInitData.SchemeData) r4
            r7 = 0
            r8 = 0
        L_0x002f:
            int r9 = r17.size()
            if (r7 >= r9) goto L_0x0064
            java.lang.Object r9 = r1.get(r7)
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData r9 = (com.google.android.exoplayer2.drm.DrmInitData.SchemeData) r9
            byte[] r10 = r9.i
            java.lang.Object r10 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r10)
            byte[] r10 = (byte[]) r10
            java.lang.String r11 = r4.h
            java.lang.String r12 = r9.h
            boolean r11 = com.google.android.exoplayer2.util.Util.areEqual(r12, r11)
            if (r11 == 0) goto L_0x0062
            java.lang.String r9 = r9.g
            java.lang.String r11 = r4.g
            boolean r9 = com.google.android.exoplayer2.util.Util.areEqual(r9, r11)
            if (r9 == 0) goto L_0x0062
            boolean r9 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.isPsshAtom(r10)
            if (r9 == 0) goto L_0x0062
            int r9 = r10.length
            int r8 = r8 + r9
            int r7 = r7 + 1
            goto L_0x002f
        L_0x0062:
            r7 = 0
            goto L_0x0065
        L_0x0064:
            r7 = 1
        L_0x0065:
            if (r7 == 0) goto L_0x008c
            byte[] r7 = new byte[r8]
            r8 = 0
            r9 = 0
        L_0x006b:
            int r10 = r17.size()
            if (r8 >= r10) goto L_0x0087
            java.lang.Object r10 = r1.get(r8)
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData r10 = (com.google.android.exoplayer2.drm.DrmInitData.SchemeData) r10
            byte[] r10 = r10.i
            java.lang.Object r10 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r10)
            byte[] r10 = (byte[]) r10
            int r11 = r10.length
            java.lang.System.arraycopy(r10, r6, r7, r9, r11)
            int r9 = r9 + r11
            int r8 = r8 + 1
            goto L_0x006b
        L_0x0087:
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData r1 = r4.copyWithData(r7)
            goto L_0x00bb
        L_0x008c:
            r4 = 0
        L_0x008d:
            int r7 = r17.size()
            if (r4 >= r7) goto L_0x00b5
            java.lang.Object r7 = r1.get(r4)
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData r7 = (com.google.android.exoplayer2.drm.DrmInitData.SchemeData) r7
            byte[] r8 = r7.i
            java.lang.Object r8 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r8)
            byte[] r8 = (byte[]) r8
            int r8 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.parseVersion(r8)
            int r9 = com.google.android.exoplayer2.util.Util.a
            if (r9 >= r2) goto L_0x00ac
            if (r8 != 0) goto L_0x00ac
            goto L_0x00b0
        L_0x00ac:
            if (r9 < r2) goto L_0x00b2
            if (r8 != r5) goto L_0x00b2
        L_0x00b0:
            r1 = r7
            goto L_0x00bb
        L_0x00b2:
            int r4 = r4 + 1
            goto L_0x008d
        L_0x00b5:
            java.lang.Object r1 = r1.get(r6)
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData r1 = (com.google.android.exoplayer2.drm.DrmInitData.SchemeData) r1
        L_0x00bb:
            byte[] r4 = r1.i
            java.lang.Object r4 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r4)
            byte[] r4 = (byte[]) r4
            java.util.UUID r7 = com.google.android.exoplayer2.C.e
            boolean r8 = r7.equals(r3)
            r9 = 26
            if (r8 == 0) goto L_0x0167
            byte[] r8 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.parseSchemeSpecificData(r4, r3)
            if (r8 != 0) goto L_0x00d4
            goto L_0x00d5
        L_0x00d4:
            r4 = r8
        L_0x00d5:
            com.google.android.exoplayer2.util.ParsableByteArray r8 = new com.google.android.exoplayer2.util.ParsableByteArray
            r8.<init>((byte[]) r4)
            int r10 = r8.readLittleEndianInt()
            short r11 = r8.readLittleEndianShort()
            short r12 = r8.readLittleEndianShort()
            java.lang.String r13 = "FrameworkMediaDrm"
            if (r11 != r5) goto L_0x015e
            if (r12 == r5) goto L_0x00ed
            goto L_0x015e
        L_0x00ed:
            short r5 = r8.readLittleEndianShort()
            java.nio.charset.Charset r14 = com.google.common.base.Charsets.d
            java.lang.String r5 = r8.readString(r5, r14)
            java.lang.String r8 = "<LA_URL>"
            boolean r8 = r5.contains(r8)
            if (r8 == 0) goto L_0x0100
            goto L_0x0163
        L_0x0100:
            java.lang.String r4 = "</DATA>"
            int r4 = r5.indexOf(r4)
            r8 = -1
            if (r4 != r8) goto L_0x010e
            java.lang.String r8 = "Could not find the </DATA> tag. Skipping LA_URL workaround."
            com.google.android.exoplayer2.util.Log.w(r13, r8)
        L_0x010e:
            java.lang.String r6 = r5.substring(r6, r4)
            java.lang.String r4 = r5.substring(r4)
            int r5 = defpackage.y2.c(r6, r9)
            int r5 = defpackage.y2.c(r4, r5)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r5)
            r8.append(r6)
            java.lang.String r5 = "<LA_URL>https://x</LA_URL>"
            r8.append(r5)
            r8.append(r4)
            java.lang.String r4 = r8.toString()
            int r10 = r10 + 52
            java.nio.ByteBuffer r5 = java.nio.ByteBuffer.allocate(r10)
            java.nio.ByteOrder r6 = java.nio.ByteOrder.LITTLE_ENDIAN
            r5.order(r6)
            r5.putInt(r10)
            short r6 = (short) r11
            r5.putShort(r6)
            short r6 = (short) r12
            r5.putShort(r6)
            int r6 = r4.length()
            int r6 = r6 * 2
            short r6 = (short) r6
            r5.putShort(r6)
            byte[] r4 = r4.getBytes(r14)
            r5.put(r4)
            byte[] r4 = r5.array()
            goto L_0x0163
        L_0x015e:
            java.lang.String r5 = "Unexpected record count or type. Skipping LA_URL workaround."
            com.google.android.exoplayer2.util.Log.i(r13, r5)
        L_0x0163:
            byte[] r4 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.buildPsshAtom(r7, r4)
        L_0x0167:
            int r5 = com.google.android.exoplayer2.util.Util.a
            if (r5 >= r2) goto L_0x0173
            java.util.UUID r6 = com.google.android.exoplayer2.C.d
            boolean r6 = r6.equals(r3)
            if (r6 != 0) goto L_0x01a5
        L_0x0173:
            boolean r6 = r7.equals(r3)
            if (r6 == 0) goto L_0x01ac
            java.lang.String r6 = "Amazon"
            java.lang.String r7 = com.google.android.exoplayer2.util.Util.c
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x01ac
            java.lang.String r6 = com.google.android.exoplayer2.util.Util.d
            java.lang.String r7 = "AFTB"
            boolean r7 = r7.equals(r6)
            if (r7 != 0) goto L_0x01a5
            java.lang.String r7 = "AFTS"
            boolean r7 = r7.equals(r6)
            if (r7 != 0) goto L_0x01a5
            java.lang.String r7 = "AFTM"
            boolean r7 = r7.equals(r6)
            if (r7 != 0) goto L_0x01a5
            java.lang.String r7 = "AFTT"
            boolean r6 = r7.equals(r6)
            if (r6 == 0) goto L_0x01ac
        L_0x01a5:
            byte[] r6 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.parseSchemeSpecificData(r4, r3)
            if (r6 == 0) goto L_0x01ac
            r4 = r6
        L_0x01ac:
            java.lang.String r6 = r1.h
            if (r5 >= r9) goto L_0x01ca
            java.util.UUID r5 = com.google.android.exoplayer2.C.c
            boolean r5 = r5.equals(r3)
            if (r5 == 0) goto L_0x01ca
            java.lang.String r5 = "video/mp4"
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L_0x01c8
            java.lang.String r5 = "audio/mp4"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x01ca
        L_0x01c8:
            java.lang.String r6 = "cenc"
        L_0x01ca:
            r7 = r6
            r6 = r4
            goto L_0x01d0
        L_0x01cd:
            r1 = 0
            r6 = r1
            r7 = r6
        L_0x01d0:
            android.media.MediaDrm r4 = r0.b
            r5 = r16
            r8 = r18
            r9 = r19
            android.media.MediaDrm$KeyRequest r4 = r4.getKeyRequest(r5, r6, r7, r8, r9)
            byte[] r5 = r4.getData()
            java.util.UUID r6 = com.google.android.exoplayer2.C.c
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x01ec
            byte[] r5 = defpackage.m0.adjustRequestData(r5)
        L_0x01ec:
            java.lang.String r3 = r4.getDefaultUrl()
            java.lang.String r6 = "https://x"
            boolean r6 = r6.equals(r3)
            if (r6 == 0) goto L_0x01fa
            java.lang.String r3 = ""
        L_0x01fa:
            boolean r6 = android.text.TextUtils.isEmpty(r3)
            if (r6 == 0) goto L_0x020b
            if (r1 == 0) goto L_0x020b
            java.lang.String r1 = r1.g
            boolean r6 = android.text.TextUtils.isEmpty(r1)
            if (r6 != 0) goto L_0x020b
            r3 = r1
        L_0x020b:
            int r1 = com.google.android.exoplayer2.util.Util.a
            if (r1 < r2) goto L_0x0214
            int r1 = r4.getRequestType()
            goto L_0x0216
        L_0x0214:
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
        L_0x0216:
            com.google.android.exoplayer2.drm.ExoMediaDrm$KeyRequest r2 = new com.google.android.exoplayer2.drm.ExoMediaDrm$KeyRequest
            r2.<init>(r5, r3, r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.FrameworkMediaDrm.getKeyRequest(byte[], java.util.List, int, java.util.HashMap):com.google.android.exoplayer2.drm.ExoMediaDrm$KeyRequest");
    }

    @Nullable
    public PersistableBundle getMetrics() {
        if (Util.a < 28) {
            return null;
        }
        return this.b.getMetrics();
    }

    public byte[] getPropertyByteArray(String str) {
        return this.b.getPropertyByteArray(str);
    }

    public String getPropertyString(String str) {
        return this.b.getPropertyString(str);
    }

    public ExoMediaDrm.ProvisionRequest getProvisionRequest() {
        MediaDrm.ProvisionRequest provisionRequest = this.b.getProvisionRequest();
        return new ExoMediaDrm.ProvisionRequest(provisionRequest.getData(), provisionRequest.getDefaultUrl());
    }

    public byte[] openSession() throws MediaDrmException {
        return this.b.openSession();
    }

    @Nullable
    public byte[] provideKeyResponse(byte[] bArr, byte[] bArr2) throws NotProvisionedException, DeniedByServerException {
        if (C.c.equals(this.a)) {
            bArr2 = m0.adjustResponseData(bArr2);
        }
        return this.b.provideKeyResponse(bArr, bArr2);
    }

    public void provideProvisionResponse(byte[] bArr) throws DeniedByServerException {
        this.b.provideProvisionResponse(bArr);
    }

    public Map<String, String> queryKeyStatus(byte[] bArr) {
        return this.b.queryKeyStatus(bArr);
    }

    public synchronized void release() {
        int i = this.c - 1;
        this.c = i;
        if (i == 0) {
            this.b.release();
        }
    }

    public void restoreKeys(byte[] bArr, byte[] bArr2) {
        this.b.restoreKeys(bArr, bArr2);
    }

    public void setOnEventListener(@Nullable ExoMediaDrm.OnEventListener onEventListener) {
        l3 l3Var;
        if (onEventListener == null) {
            l3Var = null;
        } else {
            l3Var = new l3(this, onEventListener);
        }
        this.b.setOnEventListener(l3Var);
    }

    @RequiresApi(23)
    public void setOnExpirationUpdateListener(@Nullable ExoMediaDrm.OnExpirationUpdateListener onExpirationUpdateListener) {
        k3 k3Var;
        if (Util.a >= 23) {
            if (onExpirationUpdateListener == null) {
                k3Var = null;
            } else {
                k3Var = new k3(this, onExpirationUpdateListener);
            }
            this.b.setOnExpirationUpdateListener(k3Var, (Handler) null);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @RequiresApi(23)
    public void setOnKeyStatusChangeListener(@Nullable ExoMediaDrm.OnKeyStatusChangeListener onKeyStatusChangeListener) {
        n3 n3Var;
        if (Util.a >= 23) {
            if (onKeyStatusChangeListener == null) {
                n3Var = null;
            } else {
                n3Var = new n3(this, onKeyStatusChangeListener);
            }
            this.b.setOnKeyStatusChangeListener(n3Var, (Handler) null);
            return;
        }
        throw new UnsupportedOperationException();
    }

    public void setPropertyByteArray(String str, byte[] bArr) {
        this.b.setPropertyByteArray(str, bArr);
    }

    public void setPropertyString(String str, String str2) {
        this.b.setPropertyString(str, str2);
    }

    public FrameworkMediaCrypto createMediaCrypto(byte[] bArr) throws MediaCryptoException {
        int i = Util.a;
        UUID uuid = this.a;
        return new FrameworkMediaCrypto(a(uuid), bArr, i < 21 && C.d.equals(uuid) && "L3".equals(getPropertyString("securityLevel")));
    }
}
