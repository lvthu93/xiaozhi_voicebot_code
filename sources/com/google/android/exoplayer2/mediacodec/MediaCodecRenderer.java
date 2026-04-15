package com.google.android.exoplayer2.mediacodec;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaCryptoException;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.TimedValueQueue;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

public abstract class MediaCodecRenderer extends BaseRenderer {
    public static final byte[] cm = {0, 0, 1, 103, 66, -64, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, -38, 37, -112, 0, 0, 1, 104, -50, 15, 19, 32, 0, 0, 1, 101, -120, -124, MqttWireMessage.MESSAGE_TYPE_PINGRESP, -50, 113, 24, -96, 0, 47, -65, 28, 49, -61, 39, 93, 120};
    public final long[] aa;
    public final long[] ab;
    public final long[] ac;
    @Nullable
    public Format ad;
    @Nullable
    public Format ae;
    @Nullable
    public DrmSession af;
    @Nullable
    public DrmSession ag;
    @Nullable
    public MediaCrypto ah;
    public boolean ai;
    public long aj;
    public float ak;
    public float al;
    @Nullable
    public MediaCodecAdapter am;
    @Nullable
    public Format an;
    @Nullable
    public MediaFormat ao;
    public boolean ap;
    public float aq;
    @Nullable
    public ArrayDeque<MediaCodecInfo> ar;
    @Nullable
    public DecoderInitializationException as;
    @Nullable
    public MediaCodecInfo at;
    public int au;
    public boolean av;
    public boolean aw;
    public boolean ax;
    public boolean ay;
    public boolean az;
    public boolean ba;
    public boolean bb;
    public boolean bc;
    public boolean bd;
    public boolean be;
    @Nullable
    public cr bf;
    public long bg;
    public int bh;
    public int bi;
    @Nullable
    public ByteBuffer bj;
    public boolean bk;
    public boolean bl;
    public boolean bm;
    public boolean bn;
    public boolean bo;
    public boolean bp;
    public int bq;
    public int br;
    public int bs;
    public boolean bt;
    public boolean bu;
    public boolean bv;
    public long bw;
    public long bx;
    public boolean bz;
    public boolean ca;
    public boolean cb;
    public boolean cc;
    public boolean cd;
    public boolean ce;
    public boolean cf;
    public boolean cg;
    @Nullable
    public ExoPlaybackException ch;
    public DecoderCounters ci;
    public long cj;
    public long ck;
    public int cl;
    public final MediaCodecAdapter.Factory p;
    public final MediaCodecSelector q;
    public final boolean r;
    public final float s;
    public final DecoderInputBuffer t = DecoderInputBuffer.newNoDataInstance();
    public final DecoderInputBuffer u = new DecoderInputBuffer(0);
    public final DecoderInputBuffer v = new DecoderInputBuffer(2);
    public final ch w;
    public final TimedValueQueue<Format> x;
    public final ArrayList<Long> y;
    public final MediaCodec.BufferInfo z;

    public MediaCodecRenderer(int i, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, boolean z2, float f) {
        super(i);
        this.p = factory;
        this.q = (MediaCodecSelector) Assertions.checkNotNull(mediaCodecSelector);
        this.r = z2;
        this.s = f;
        ch chVar = new ch();
        this.w = chVar;
        this.x = new TimedValueQueue<>();
        this.y = new ArrayList<>();
        this.z = new MediaCodec.BufferInfo();
        this.ak = 1.0f;
        this.al = 1.0f;
        this.aj = -9223372036854775807L;
        this.aa = new long[10];
        this.ab = new long[10];
        this.ac = new long[10];
        this.cj = -9223372036854775807L;
        this.ck = -9223372036854775807L;
        chVar.ensureSpaceForWrite(0);
        chVar.g.order(ByteOrder.nativeOrder());
        this.aq = -1.0f;
        this.au = 0;
        this.bq = 0;
        this.bh = -1;
        this.bi = -1;
        this.bg = -9223372036854775807L;
        this.bw = -9223372036854775807L;
        this.bx = -9223372036854775807L;
        this.br = 0;
        this.bs = 0;
    }

    @TargetApi(23)
    private void al() throws ExoPlaybackException {
        int i = this.bs;
        if (i == 1) {
            r();
        } else if (i == 2) {
            r();
            ax();
        } else if (i != 3) {
            this.ca = true;
            ap();
        } else {
            ao();
            ab();
        }
    }

    private boolean q() throws ExoPlaybackException {
        MediaCodecAdapter mediaCodecAdapter = this.am;
        if (mediaCodecAdapter == null || this.br == 2 || this.bz) {
            return false;
        }
        int i = this.bh;
        DecoderInputBuffer decoderInputBuffer = this.u;
        if (i < 0) {
            int dequeueInputBufferIndex = mediaCodecAdapter.dequeueInputBufferIndex();
            this.bh = dequeueInputBufferIndex;
            if (dequeueInputBufferIndex < 0) {
                return false;
            }
            decoderInputBuffer.g = this.am.getInputBuffer(dequeueInputBufferIndex);
            decoderInputBuffer.clear();
        }
        if (this.br == 1) {
            if (!this.be) {
                this.bu = true;
                this.am.queueInputBuffer(this.bh, 0, 0, 0, 4);
                this.bh = -1;
                decoderInputBuffer.g = null;
            }
            this.br = 2;
            return false;
        } else if (this.bc) {
            this.bc = false;
            decoderInputBuffer.g.put(cm);
            this.am.queueInputBuffer(this.bh, 0, 38, 0, 0);
            this.bh = -1;
            decoderInputBuffer.g = null;
            this.bt = true;
            return true;
        } else {
            if (this.bq == 1) {
                for (int i2 = 0; i2 < this.an.r.size(); i2++) {
                    decoderInputBuffer.g.put(this.an.r.get(i2));
                }
                this.bq = 2;
            }
            int position = decoderInputBuffer.g.position();
            FormatHolder formatHolder = this.f;
            formatHolder.clear();
            try {
                int j = j(formatHolder, decoderInputBuffer, 0);
                if (hasReadStreamToEnd()) {
                    this.bx = this.bw;
                }
                if (j == -3) {
                    return false;
                }
                if (j == -5) {
                    if (this.bq == 2) {
                        decoderInputBuffer.clear();
                        this.bq = 1;
                    }
                    ag(formatHolder);
                    return true;
                } else if (decoderInputBuffer.isEndOfStream()) {
                    if (this.bq == 2) {
                        decoderInputBuffer.clear();
                        this.bq = 1;
                    }
                    this.bz = true;
                    if (!this.bt) {
                        al();
                        return false;
                    }
                    try {
                        if (!this.be) {
                            this.bu = true;
                            this.am.queueInputBuffer(this.bh, 0, 0, 0, 4);
                            this.bh = -1;
                            decoderInputBuffer.g = null;
                        }
                        return false;
                    } catch (MediaCodec.CryptoException e) {
                        throw a(e, this.ad, false);
                    }
                } else if (this.bt || decoderInputBuffer.isKeyFrame()) {
                    boolean isEncrypted = decoderInputBuffer.isEncrypted();
                    if (isEncrypted) {
                        decoderInputBuffer.f.increaseClearDataFirstSubSampleBy(position);
                    }
                    if (this.av && !isEncrypted) {
                        NalUnitUtil.discardToSps(decoderInputBuffer.g);
                        if (decoderInputBuffer.g.position() == 0) {
                            return true;
                        }
                        this.av = false;
                    }
                    long j2 = decoderInputBuffer.i;
                    cr crVar = this.bf;
                    if (crVar != null) {
                        j2 = crVar.updateAndGetPresentationTimeUs(this.ad, decoderInputBuffer);
                    }
                    long j3 = j2;
                    if (decoderInputBuffer.isDecodeOnly()) {
                        this.y.add(Long.valueOf(j3));
                    }
                    if (this.cb) {
                        this.x.add(j3, this.ad);
                        this.cb = false;
                    }
                    if (this.bf != null) {
                        this.bw = Math.max(this.bw, decoderInputBuffer.i);
                    } else {
                        this.bw = Math.max(this.bw, j3);
                    }
                    decoderInputBuffer.flip();
                    if (decoderInputBuffer.hasSupplementalData()) {
                        z(decoderInputBuffer);
                    }
                    ak(decoderInputBuffer);
                    if (isEncrypted) {
                        try {
                            this.am.queueSecureInputBuffer(this.bh, 0, decoderInputBuffer.f, j3, 0);
                        } catch (MediaCodec.CryptoException e2) {
                            throw a(e2, this.ad, false);
                        }
                    } else {
                        this.am.queueInputBuffer(this.bh, 0, decoderInputBuffer.g.limit(), j3, 0);
                    }
                    this.bh = -1;
                    decoderInputBuffer.g = null;
                    this.bt = true;
                    this.bq = 0;
                    this.ci.getClass();
                    return true;
                } else {
                    decoderInputBuffer.clear();
                    if (this.bq == 2) {
                        this.bq = 1;
                    }
                    return true;
                }
            } catch (DecoderInputBuffer.InsufficientCapacityException e3) {
                DecoderInputBuffer.InsufficientCapacityException insufficientCapacityException = e3;
                ad(insufficientCapacityException);
                if (this.cg) {
                    an(0);
                    r();
                    return true;
                }
                throw a(m(insufficientCapacityException, this.at), this.ad, false);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0164, code lost:
        if ("stvm8".equals(r3) == false) goto L_0x0178;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0174, code lost:
        if ("OMX.amlogic.avc.decoder.awesome.secure".equals(r5) == false) goto L_0x0178;
     */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x01e5  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x01e7  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x023a  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x023c  */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x0247  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0255  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00fc  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0105 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0143  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0145  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0154  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0185  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0187  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void aa(com.google.android.exoplayer2.mediacodec.MediaCodecInfo r17, android.media.MediaCrypto r18) throws java.lang.Exception {
        /*
            r16 = this;
            r6 = r16
            r0 = r17
            java.lang.String r5 = r0.a
            int r1 = com.google.android.exoplayer2.util.Util.a
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            r3 = 23
            if (r1 >= r3) goto L_0x0011
            r4 = -1082130432(0xffffffffbf800000, float:-1.0)
            goto L_0x001f
        L_0x0011:
            float r4 = r6.al
            com.google.android.exoplayer2.Format[] r7 = r6.k
            java.lang.Object r7 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r7)
            com.google.android.exoplayer2.Format[] r7 = (com.google.android.exoplayer2.Format[]) r7
            float r4 = r6.v(r4, r7)
        L_0x001f:
            float r7 = r6.s
            int r7 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r7 > 0) goto L_0x0026
            goto L_0x0027
        L_0x0026:
            r2 = r4
        L_0x0027:
            long r7 = android.os.SystemClock.elapsedRealtime()
            java.lang.String r4 = java.lang.String.valueOf(r5)
            int r9 = r4.length()
            java.lang.String r10 = "createCodec:"
            if (r9 == 0) goto L_0x003c
            java.lang.String r4 = r10.concat(r4)
            goto L_0x0041
        L_0x003c:
            java.lang.String r4 = new java.lang.String
            r4.<init>(r10)
        L_0x0041:
            com.google.android.exoplayer2.util.TraceUtil.beginSection(r4)
            com.google.android.exoplayer2.Format r4 = r6.ad
            r9 = r18
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter$Configuration r4 = r6.y(r0, r4, r9, r2)
            boolean r9 = r6.cd
            if (r9 == 0) goto L_0x0064
            if (r1 < r3) goto L_0x0064
            com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter$Factory r9 = new com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter$Factory
            int r10 = r16.getTrackType()
            boolean r11 = r6.ce
            boolean r12 = r6.cf
            r9.<init>(r10, r11, r12)
            com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter r4 = r9.createAdapter((com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.Configuration) r4)
            goto L_0x006a
        L_0x0064:
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter$Factory r9 = r6.p
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r4 = r9.createAdapter(r4)
        L_0x006a:
            long r9 = android.os.SystemClock.elapsedRealtime()
            r6.am = r4
            r6.at = r0
            r6.aq = r2
            com.google.android.exoplayer2.Format r2 = r6.ad
            r6.an = r2
            r4 = 25
            r12 = 1
            java.lang.String r13 = "OMX.Exynos.avc.dec.secure"
            if (r1 > r4) goto L_0x00a9
            boolean r14 = r13.equals(r5)
            if (r14 == 0) goto L_0x00a9
            java.lang.String r14 = com.google.android.exoplayer2.util.Util.d
            java.lang.String r15 = "SM-T585"
            boolean r15 = r14.startsWith(r15)
            if (r15 != 0) goto L_0x00a7
            java.lang.String r15 = "SM-A510"
            boolean r15 = r14.startsWith(r15)
            if (r15 != 0) goto L_0x00a7
            java.lang.String r15 = "SM-A520"
            boolean r15 = r14.startsWith(r15)
            if (r15 != 0) goto L_0x00a7
            java.lang.String r15 = "SM-J700"
            boolean r14 = r14.startsWith(r15)
            if (r14 == 0) goto L_0x00a9
        L_0x00a7:
            r14 = 2
            goto L_0x00e2
        L_0x00a9:
            r14 = 24
            if (r1 >= r14) goto L_0x00e1
            java.lang.String r14 = "OMX.Nvidia.h264.decode"
            boolean r14 = r14.equals(r5)
            if (r14 != 0) goto L_0x00bd
            java.lang.String r14 = "OMX.Nvidia.h264.decode.secure"
            boolean r14 = r14.equals(r5)
            if (r14 == 0) goto L_0x00e1
        L_0x00bd:
            java.lang.String r14 = com.google.android.exoplayer2.util.Util.b
            java.lang.String r15 = "flounder"
            boolean r15 = r15.equals(r14)
            if (r15 != 0) goto L_0x00df
            java.lang.String r15 = "flounder_lte"
            boolean r15 = r15.equals(r14)
            if (r15 != 0) goto L_0x00df
            java.lang.String r15 = "grouper"
            boolean r15 = r15.equals(r14)
            if (r15 != 0) goto L_0x00df
            java.lang.String r15 = "tilapia"
            boolean r14 = r15.equals(r14)
            if (r14 == 0) goto L_0x00e1
        L_0x00df:
            r14 = 1
            goto L_0x00e2
        L_0x00e1:
            r14 = 0
        L_0x00e2:
            r6.au = r14
            com.google.android.exoplayer2.Format r14 = r6.an
            r15 = 21
            if (r1 >= r15) goto L_0x00fc
            java.util.List<byte[]> r14 = r14.r
            boolean r14 = r14.isEmpty()
            if (r14 == 0) goto L_0x00fc
            java.lang.String r14 = "OMX.MTK.VIDEO.DECODER.AVC"
            boolean r14 = r14.equals(r5)
            if (r14 == 0) goto L_0x00fc
            r14 = 1
            goto L_0x00fd
        L_0x00fc:
            r14 = 0
        L_0x00fd:
            r6.av = r14
            r14 = 19
            r11 = 18
            if (r1 < r11) goto L_0x0134
            if (r1 != r11) goto L_0x0117
            java.lang.String r2 = "OMX.SEC.avc.dec"
            boolean r2 = r2.equals(r5)
            if (r2 != 0) goto L_0x0134
            java.lang.String r2 = "OMX.SEC.avc.dec.secure"
            boolean r2 = r2.equals(r5)
            if (r2 != 0) goto L_0x0134
        L_0x0117:
            if (r1 != r14) goto L_0x0132
            java.lang.String r2 = com.google.android.exoplayer2.util.Util.d
            java.lang.String r4 = "SM-G800"
            boolean r2 = r2.startsWith(r4)
            if (r2 == 0) goto L_0x0132
            java.lang.String r2 = "OMX.Exynos.avc.dec"
            boolean r2 = r2.equals(r5)
            if (r2 != 0) goto L_0x0134
            boolean r2 = r13.equals(r5)
            if (r2 == 0) goto L_0x0132
            goto L_0x0134
        L_0x0132:
            r2 = 0
            goto L_0x0135
        L_0x0134:
            r2 = 1
        L_0x0135:
            r6.aw = r2
            r2 = 29
            if (r1 != r2) goto L_0x0145
            java.lang.String r4 = "c2.android.aac.decoder"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0145
            r4 = 1
            goto L_0x0146
        L_0x0145:
            r4 = 0
        L_0x0146:
            r6.ax = r4
            if (r1 > r3) goto L_0x0152
            java.lang.String r3 = "OMX.google.vorbis.decoder"
            boolean r3 = r3.equals(r5)
            if (r3 != 0) goto L_0x0176
        L_0x0152:
            if (r1 > r14) goto L_0x0178
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.b
            java.lang.String r4 = "hb2000"
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L_0x0166
            java.lang.String r4 = "stvm8"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x0178
        L_0x0166:
            java.lang.String r3 = "OMX.amlogic.avc.decoder.awesome"
            boolean r3 = r3.equals(r5)
            if (r3 != 0) goto L_0x0176
            java.lang.String r3 = "OMX.amlogic.avc.decoder.awesome.secure"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0178
        L_0x0176:
            r3 = 1
            goto L_0x0179
        L_0x0178:
            r3 = 0
        L_0x0179:
            r6.ay = r3
            if (r1 != r15) goto L_0x0187
            java.lang.String r3 = "OMX.google.aac.decoder"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0187
            r3 = 1
            goto L_0x0188
        L_0x0187:
            r3 = 0
        L_0x0188:
            r6.az = r3
            if (r1 >= r15) goto L_0x01d2
            java.lang.String r3 = "OMX.SEC.mp3.dec"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x01d2
            java.lang.String r3 = "samsung"
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.c
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x01d2
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.b
            java.lang.String r4 = "baffin"
            boolean r4 = r3.startsWith(r4)
            if (r4 != 0) goto L_0x01d0
            java.lang.String r4 = "grand"
            boolean r4 = r3.startsWith(r4)
            if (r4 != 0) goto L_0x01d0
            java.lang.String r4 = "fortuna"
            boolean r4 = r3.startsWith(r4)
            if (r4 != 0) goto L_0x01d0
            java.lang.String r4 = "gprimelte"
            boolean r4 = r3.startsWith(r4)
            if (r4 != 0) goto L_0x01d0
            java.lang.String r4 = "j2y18lte"
            boolean r4 = r3.startsWith(r4)
            if (r4 != 0) goto L_0x01d0
            java.lang.String r4 = "ms01"
            boolean r3 = r3.startsWith(r4)
            if (r3 == 0) goto L_0x01d2
        L_0x01d0:
            r3 = 1
            goto L_0x01d3
        L_0x01d2:
            r3 = 0
        L_0x01d3:
            r6.ba = r3
            com.google.android.exoplayer2.Format r3 = r6.an
            if (r1 > r11) goto L_0x01e7
            int r3 = r3.ac
            if (r3 != r12) goto L_0x01e7
            java.lang.String r3 = "OMX.MTK.AUDIO.DECODER.MP3"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x01e7
            r3 = 1
            goto L_0x01e8
        L_0x01e7:
            r3 = 0
        L_0x01e8:
            r6.bb = r3
            java.lang.String r3 = r0.a
            r4 = 25
            if (r1 > r4) goto L_0x01f8
            java.lang.String r4 = "OMX.rk.video_decoder.avc"
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L_0x022e
        L_0x01f8:
            r4 = 17
            if (r1 > r4) goto L_0x0204
            java.lang.String r4 = "OMX.allwinner.video.decoder.avc"
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L_0x022e
        L_0x0204:
            if (r1 > r2) goto L_0x0216
            java.lang.String r1 = "OMX.broadcom.video_decoder.tunnel"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x022e
            java.lang.String r1 = "OMX.broadcom.video_decoder.tunnel.secure"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x022e
        L_0x0216:
            java.lang.String r1 = "Amazon"
            java.lang.String r2 = com.google.android.exoplayer2.util.Util.c
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0230
            java.lang.String r1 = "AFTS"
            java.lang.String r2 = com.google.android.exoplayer2.util.Util.d
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0230
            boolean r0 = r0.f
            if (r0 == 0) goto L_0x0230
        L_0x022e:
            r0 = 1
            goto L_0x0231
        L_0x0230:
            r0 = 0
        L_0x0231:
            if (r0 != 0) goto L_0x023c
            boolean r0 = r16.u()
            if (r0 == 0) goto L_0x023a
            goto L_0x023c
        L_0x023a:
            r11 = 0
            goto L_0x023d
        L_0x023c:
            r11 = 1
        L_0x023d:
            r6.be = r11
            java.lang.String r0 = "c2.android.mp3.decoder"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x024e
            cr r0 = new cr
            r0.<init>()
            r6.bf = r0
        L_0x024e:
            int r0 = r16.getState()
            r1 = 2
            if (r0 != r1) goto L_0x025e
            long r0 = android.os.SystemClock.elapsedRealtime()
            r2 = 1000(0x3e8, double:4.94E-321)
            long r0 = r0 + r2
            r6.bg = r0
        L_0x025e:
            com.google.android.exoplayer2.decoder.DecoderCounters r0 = r6.ci
            r0.getClass()
            long r3 = r9 - r7
            r0 = r16
            r1 = r9
            r0.ae(r1, r3, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.aa(com.google.android.exoplayer2.mediacodec.MediaCodecInfo, android.media.MediaCrypto):void");
    }

    public final void ab() throws ExoPlaybackException {
        Format format;
        boolean z2;
        if (this.am == null && !this.bm && (format = this.ad) != null) {
            if (this.ag != null || !au(format)) {
                as(this.ag);
                String str = this.ad.p;
                DrmSession drmSession = this.af;
                if (drmSession != null) {
                    if (this.ah == null) {
                        FrameworkMediaCrypto x2 = x(drmSession);
                        if (x2 != null) {
                            try {
                                MediaCrypto mediaCrypto = new MediaCrypto(x2.a, x2.b);
                                this.ah = mediaCrypto;
                                if (x2.c || !mediaCrypto.requiresSecureDecoderComponent(str)) {
                                    z2 = false;
                                } else {
                                    z2 = true;
                                }
                                this.ai = z2;
                            } catch (MediaCryptoException e) {
                                throw a(e, this.ad, false);
                            }
                        } else if (this.af.getError() == null) {
                            return;
                        }
                    }
                    if (FrameworkMediaCrypto.d) {
                        int state = this.af.getState();
                        if (state == 1) {
                            throw a(this.af.getError(), this.ad, false);
                        } else if (state != 4) {
                            return;
                        }
                    }
                }
                try {
                    ac(this.ah, this.ai);
                } catch (DecoderInitializationException e2) {
                    throw a(e2, this.ad, false);
                }
            } else {
                Format format2 = this.ad;
                n();
                String str2 = format2.p;
                boolean equals = "audio/mp4a-latm".equals(str2);
                ch chVar = this.w;
                if (equals || "audio/mpeg".equals(str2) || "audio/opus".equals(str2)) {
                    chVar.setMaxSampleCount(32);
                } else {
                    chVar.setMaxSampleCount(1);
                }
                this.bm = true;
            }
        }
    }

    public final void ac(MediaCrypto mediaCrypto, boolean z2) throws DecoderInitializationException {
        if (this.ar == null) {
            try {
                List<MediaCodecInfo> t2 = t(z2);
                ArrayDeque<MediaCodecInfo> arrayDeque = new ArrayDeque<>();
                this.ar = arrayDeque;
                if (this.r) {
                    arrayDeque.addAll(t2);
                } else if (!t2.isEmpty()) {
                    this.ar.add(t2.get(0));
                }
                this.as = null;
            } catch (MediaCodecUtil.DecoderQueryException e) {
                throw new DecoderInitializationException(this.ad, (Throwable) e, z2, -49998);
            }
        }
        if (!this.ar.isEmpty()) {
            while (this.am == null) {
                MediaCodecInfo peekFirst = this.ar.peekFirst();
                if (at(peekFirst)) {
                    try {
                        aa(peekFirst, mediaCrypto);
                    } catch (Exception e2) {
                        String valueOf = String.valueOf(peekFirst);
                        StringBuilder sb = new StringBuilder(valueOf.length() + 30);
                        sb.append("Failed to initialize decoder: ");
                        sb.append(valueOf);
                        Log.w("MediaCodecRenderer", sb.toString(), e2);
                        this.ar.removeFirst();
                        DecoderInitializationException decoderInitializationException = new DecoderInitializationException(this.ad, (Throwable) e2, z2, peekFirst);
                        if (this.as == null) {
                            this.as = decoderInitializationException;
                        } else {
                            DecoderInitializationException decoderInitializationException2 = this.as;
                            this.as = new DecoderInitializationException(decoderInitializationException2.getMessage(), decoderInitializationException2.getCause(), decoderInitializationException2.c, decoderInitializationException2.f, decoderInitializationException2.g, decoderInitializationException2.h);
                        }
                        if (this.ar.isEmpty()) {
                            throw this.as;
                        }
                    }
                } else {
                    return;
                }
            }
            this.ar = null;
            return;
        }
        throw new DecoderInitializationException(this.ad, (Throwable) null, z2, -49999);
    }

    public void ad(IllegalStateException illegalStateException) {
    }

    public void ae(long j, long j2, String str) {
    }

    public void af(String str) {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x013b, code lost:
        if (r0 == false) goto L_0x013d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x013d, code lost:
        r12 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0085, code lost:
        if (r12 == false) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00dd, code lost:
        if (o() == false) goto L_0x013d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x010f, code lost:
        if (o() == false) goto L_0x013d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0123, code lost:
        if (o() == false) goto L_0x013d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x015b  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x0166 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00a9  */
    @androidx.annotation.CallSuper
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.decoder.DecoderReuseEvaluation ag(com.google.android.exoplayer2.FormatHolder r12) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r11 = this;
            r0 = 1
            r11.cb = r0
            com.google.android.exoplayer2.Format r1 = r12.b
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            r5 = r1
            com.google.android.exoplayer2.Format r5 = (com.google.android.exoplayer2.Format) r5
            java.lang.String r1 = r5.p
            r2 = 0
            if (r1 == 0) goto L_0x0167
            com.google.android.exoplayer2.drm.DrmSession r12 = r12.a
            com.google.android.exoplayer2.drm.DrmSession r1 = r11.ag
            defpackage.s1.b(r1, r12)
            r11.ag = r12
            r11.ad = r5
            boolean r1 = r11.bm
            r3 = 0
            if (r1 == 0) goto L_0x0024
            r11.bo = r0
            return r3
        L_0x0024:
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r1 = r11.am
            if (r1 != 0) goto L_0x002e
            r11.ar = r3
            r11.ab()
            return r3
        L_0x002e:
            com.google.android.exoplayer2.mediacodec.MediaCodecInfo r3 = r11.at
            com.google.android.exoplayer2.Format r4 = r11.an
            com.google.android.exoplayer2.drm.DrmSession r6 = r11.af
            r7 = 23
            if (r6 != r12) goto L_0x0039
            goto L_0x0088
        L_0x0039:
            if (r12 == 0) goto L_0x008a
            if (r6 != 0) goto L_0x003e
            goto L_0x008a
        L_0x003e:
            int r8 = com.google.android.exoplayer2.util.Util.a
            if (r8 >= r7) goto L_0x0043
            goto L_0x008a
        L_0x0043:
            java.util.UUID r8 = com.google.android.exoplayer2.C.e
            java.util.UUID r6 = r6.getSchemeUuid()
            boolean r6 = r8.equals(r6)
            if (r6 != 0) goto L_0x008a
            java.util.UUID r6 = r12.getSchemeUuid()
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L_0x005a
            goto L_0x008a
        L_0x005a:
            com.google.android.exoplayer2.drm.FrameworkMediaCrypto r12 = r11.x(r12)
            if (r12 != 0) goto L_0x0061
            goto L_0x008a
        L_0x0061:
            boolean r6 = r3.f
            if (r6 != 0) goto L_0x0088
            boolean r6 = r12.c
            if (r6 == 0) goto L_0x006b
            r12 = 0
            goto L_0x0085
        L_0x006b:
            android.media.MediaCrypto r6 = new android.media.MediaCrypto     // Catch:{ MediaCryptoException -> 0x0083 }
            java.util.UUID r8 = r12.a     // Catch:{ MediaCryptoException -> 0x0083 }
            byte[] r12 = r12.b     // Catch:{ MediaCryptoException -> 0x0083 }
            r6.<init>(r8, r12)     // Catch:{ MediaCryptoException -> 0x0083 }
            java.lang.String r12 = r5.p     // Catch:{ all -> 0x007e }
            boolean r12 = r6.requiresSecureDecoderComponent(r12)     // Catch:{ all -> 0x007e }
            r6.release()
            goto L_0x0085
        L_0x007e:
            r12 = move-exception
            r6.release()
            throw r12
        L_0x0083:
            r12 = 1
        L_0x0085:
            if (r12 == 0) goto L_0x0088
            goto L_0x008a
        L_0x0088:
            r12 = 0
            goto L_0x008b
        L_0x008a:
            r12 = 1
        L_0x008b:
            r6 = 3
            if (r12 == 0) goto L_0x00a9
            boolean r12 = r11.bt
            if (r12 == 0) goto L_0x0097
            r11.br = r0
            r11.bs = r6
            goto L_0x009d
        L_0x0097:
            r11.ao()
            r11.ab()
        L_0x009d:
            com.google.android.exoplayer2.decoder.DecoderReuseEvaluation r12 = new com.google.android.exoplayer2.decoder.DecoderReuseEvaluation
            java.lang.String r3 = r3.a
            r6 = 0
            r7 = 128(0x80, float:1.794E-43)
            r2 = r12
            r2.<init>(r3, r4, r5, r6, r7)
            return r12
        L_0x00a9:
            com.google.android.exoplayer2.drm.DrmSession r12 = r11.ag
            com.google.android.exoplayer2.drm.DrmSession r8 = r11.af
            if (r12 == r8) goto L_0x00b1
            r12 = 1
            goto L_0x00b2
        L_0x00b1:
            r12 = 0
        L_0x00b2:
            if (r12 == 0) goto L_0x00bb
            int r8 = com.google.android.exoplayer2.util.Util.a
            if (r8 < r7) goto L_0x00b9
            goto L_0x00bb
        L_0x00b9:
            r7 = 0
            goto L_0x00bc
        L_0x00bb:
            r7 = 1
        L_0x00bc:
            com.google.android.exoplayer2.util.Assertions.checkState(r7)
            com.google.android.exoplayer2.decoder.DecoderReuseEvaluation r7 = r11.l(r3, r4, r5)
            int r8 = r7.d
            if (r8 == 0) goto L_0x013f
            r9 = 2
            if (r8 == r0) goto L_0x0112
            if (r8 == r9) goto L_0x00e6
            if (r8 != r6) goto L_0x00e0
            boolean r0 = r11.aw(r5)
            if (r0 != 0) goto L_0x00d5
            goto L_0x0118
        L_0x00d5:
            r11.an = r5
            if (r12 == 0) goto L_0x014e
            boolean r12 = r11.o()
            if (r12 != 0) goto L_0x014e
            goto L_0x013d
        L_0x00e0:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            r12.<init>()
            throw r12
        L_0x00e6:
            boolean r8 = r11.aw(r5)
            if (r8 != 0) goto L_0x00ed
            goto L_0x0118
        L_0x00ed:
            r11.bp = r0
            r11.bq = r0
            int r8 = r11.au
            if (r8 == r9) goto L_0x0105
            if (r8 != r0) goto L_0x0104
            int r8 = r4.u
            int r10 = r5.u
            if (r10 != r8) goto L_0x0104
            int r8 = r5.v
            int r10 = r4.v
            if (r8 != r10) goto L_0x0104
            goto L_0x0105
        L_0x0104:
            r0 = 0
        L_0x0105:
            r11.bc = r0
            r11.an = r5
            if (r12 == 0) goto L_0x014e
            boolean r12 = r11.o()
            if (r12 != 0) goto L_0x014e
            goto L_0x013d
        L_0x0112:
            boolean r8 = r11.aw(r5)
            if (r8 != 0) goto L_0x011b
        L_0x0118:
            r12 = 16
            goto L_0x014f
        L_0x011b:
            r11.an = r5
            if (r12 == 0) goto L_0x0126
            boolean r12 = r11.o()
            if (r12 != 0) goto L_0x014e
            goto L_0x013d
        L_0x0126:
            boolean r12 = r11.bt
            if (r12 == 0) goto L_0x013b
            r11.br = r0
            boolean r12 = r11.aw
            if (r12 != 0) goto L_0x0138
            boolean r12 = r11.ay
            if (r12 == 0) goto L_0x0135
            goto L_0x0138
        L_0x0135:
            r11.bs = r0
            goto L_0x013b
        L_0x0138:
            r11.bs = r6
            r0 = 0
        L_0x013b:
            if (r0 != 0) goto L_0x014e
        L_0x013d:
            r12 = 2
            goto L_0x014f
        L_0x013f:
            boolean r12 = r11.bt
            if (r12 == 0) goto L_0x0148
            r11.br = r0
            r11.bs = r6
            goto L_0x014e
        L_0x0148:
            r11.ao()
            r11.ab()
        L_0x014e:
            r12 = 0
        L_0x014f:
            int r0 = r7.d
            if (r0 == 0) goto L_0x0166
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r0 = r11.am
            if (r0 != r1) goto L_0x015b
            int r0 = r11.bs
            if (r0 != r6) goto L_0x0166
        L_0x015b:
            com.google.android.exoplayer2.decoder.DecoderReuseEvaluation r0 = new com.google.android.exoplayer2.decoder.DecoderReuseEvaluation
            java.lang.String r3 = r3.a
            r6 = 0
            r2 = r0
            r7 = r12
            r2.<init>(r3, r4, r5, r6, r7)
            return r0
        L_0x0166:
            return r7
        L_0x0167:
            java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException
            r12.<init>()
            com.google.android.exoplayer2.ExoPlaybackException r12 = r11.a(r12, r5, r2)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.ag(com.google.android.exoplayer2.FormatHolder):com.google.android.exoplayer2.decoder.DecoderReuseEvaluation");
    }

    public void ah(Format format, @Nullable MediaFormat mediaFormat) throws ExoPlaybackException {
    }

    @CallSuper
    public void ai(long j) {
        while (true) {
            int i = this.cl;
            if (i != 0) {
                long[] jArr = this.ac;
                if (j >= jArr[0]) {
                    long[] jArr2 = this.aa;
                    this.cj = jArr2[0];
                    long[] jArr3 = this.ab;
                    this.ck = jArr3[0];
                    int i2 = i - 1;
                    this.cl = i2;
                    System.arraycopy(jArr2, 1, jArr2, 0, i2);
                    System.arraycopy(jArr3, 1, jArr3, 0, this.cl);
                    System.arraycopy(jArr, 1, jArr, 0, this.cl);
                    aj();
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public void aj() {
    }

    public void ak(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
    }

    public abstract boolean am(long j, long j2, @Nullable MediaCodecAdapter mediaCodecAdapter, @Nullable ByteBuffer byteBuffer, int i, int i2, int i3, long j3, boolean z2, boolean z3, Format format) throws ExoPlaybackException;

    public final boolean an(int i) throws ExoPlaybackException {
        FormatHolder formatHolder = this.f;
        formatHolder.clear();
        DecoderInputBuffer decoderInputBuffer = this.t;
        decoderInputBuffer.clear();
        int j = j(formatHolder, decoderInputBuffer, i | 4);
        if (j == -5) {
            ag(formatHolder);
            return true;
        } else if (j != -4 || !decoderInputBuffer.isEndOfStream()) {
            return false;
        } else {
            this.bz = true;
            al();
            return false;
        }
    }

    public final void ao() {
        try {
            MediaCodecAdapter mediaCodecAdapter = this.am;
            if (mediaCodecAdapter != null) {
                mediaCodecAdapter.release();
                this.ci.getClass();
                af(this.at.a);
            }
            this.am = null;
            try {
                MediaCrypto mediaCrypto = this.ah;
                if (mediaCrypto != null) {
                    mediaCrypto.release();
                }
            } finally {
                this.ah = null;
                as((DrmSession) null);
                ar();
            }
        } catch (Throwable th) {
            this.am = null;
            MediaCrypto mediaCrypto2 = this.ah;
            if (mediaCrypto2 != null) {
                mediaCrypto2.release();
            }
            throw th;
        } finally {
            this.ah = null;
            as((DrmSession) null);
            ar();
        }
    }

    public void ap() throws ExoPlaybackException {
    }

    @CallSuper
    public void aq() {
        this.bh = -1;
        this.u.g = null;
        this.bi = -1;
        this.bj = null;
        this.bg = -9223372036854775807L;
        this.bu = false;
        this.bt = false;
        this.bc = false;
        this.bd = false;
        this.bk = false;
        this.bl = false;
        this.y.clear();
        this.bw = -9223372036854775807L;
        this.bx = -9223372036854775807L;
        cr crVar = this.bf;
        if (crVar != null) {
            crVar.reset();
        }
        this.br = 0;
        this.bs = 0;
        this.bq = this.bp ? 1 : 0;
    }

    @CallSuper
    public final void ar() {
        aq();
        this.ch = null;
        this.bf = null;
        this.ar = null;
        this.at = null;
        this.an = null;
        this.ao = null;
        this.ap = false;
        this.bv = false;
        this.aq = -1.0f;
        this.au = 0;
        this.av = false;
        this.aw = false;
        this.ax = false;
        this.ay = false;
        this.az = false;
        this.ba = false;
        this.bb = false;
        this.be = false;
        this.bp = false;
        this.bq = 0;
        this.ai = false;
    }

    public final void as(@Nullable DrmSession drmSession) {
        s1.b(this.af, drmSession);
        this.af = drmSession;
    }

    public boolean at(MediaCodecInfo mediaCodecInfo) {
        return true;
    }

    public boolean au(Format format) {
        return false;
    }

    public abstract int av(MediaCodecSelector mediaCodecSelector, Format format) throws MediaCodecUtil.DecoderQueryException;

    public final boolean aw(Format format) throws ExoPlaybackException {
        if (!(Util.a < 23 || this.am == null || this.bs == 3 || getState() == 0)) {
            float v2 = v(this.al, (Format[]) Assertions.checkNotNull(this.k));
            float f = this.aq;
            if (f == v2) {
                return true;
            }
            if (v2 == -1.0f) {
                if (this.bt) {
                    this.br = 1;
                    this.bs = 3;
                    return false;
                }
                ao();
                ab();
                return false;
            } else if (f == -1.0f && v2 <= this.s) {
                return true;
            } else {
                Bundle bundle = new Bundle();
                bundle.putFloat("operating-rate", v2);
                this.am.setParameters(bundle);
                this.aq = v2;
            }
        }
        return true;
    }

    @RequiresApi(23)
    public final void ax() throws ExoPlaybackException {
        try {
            this.ah.setMediaDrmSession(x(this.ag).b);
            as(this.ag);
            this.br = 0;
            this.bs = 0;
        } catch (MediaCryptoException e) {
            throw a(e, this.ad, false);
        }
    }

    public final void ay(long j) throws ExoPlaybackException {
        boolean z2;
        TimedValueQueue<Format> timedValueQueue = this.x;
        Format pollFloor = timedValueQueue.pollFloor(j);
        if (pollFloor == null && this.ap) {
            pollFloor = timedValueQueue.pollFirst();
        }
        if (pollFloor != null) {
            this.ae = pollFloor;
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 || (this.ap && this.ae != null)) {
            ah(this.ae, this.ao);
            this.ap = false;
        }
    }

    public void c() {
        this.ad = null;
        this.cj = -9223372036854775807L;
        this.ck = -9223372036854775807L;
        this.cl = 0;
        s();
    }

    public void d(boolean z2, boolean z3) throws ExoPlaybackException {
        this.ci = new DecoderCounters();
    }

    public void e(long j, boolean z2) throws ExoPlaybackException {
        this.bz = false;
        this.ca = false;
        this.cc = false;
        if (this.bm) {
            this.w.clear();
            this.v.clear();
            this.bn = false;
        } else if (s()) {
            ab();
        }
        TimedValueQueue<Format> timedValueQueue = this.x;
        if (timedValueQueue.size() > 0) {
            this.cb = true;
        }
        timedValueQueue.clear();
        int i = this.cl;
        if (i != 0) {
            this.ck = this.ab[i - 1];
            this.cj = this.aa[i - 1];
            this.cl = 0;
        }
    }

    public void experimentalSetAsynchronousBufferQueueingEnabled(boolean z2) {
        this.cd = z2;
    }

    public void experimentalSetForceAsyncQueueingSynchronizationWorkaround(boolean z2) {
        this.ce = z2;
    }

    public void experimentalSetSkipAndContinueIfSampleTooLarge(boolean z2) {
        this.cg = z2;
    }

    public void experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(boolean z2) {
        this.cf = z2;
    }

    public void f() {
        try {
            n();
            ao();
        } finally {
            s1.b(this.ag, (DrmSession) null);
            this.ag = null;
        }
    }

    public void g() {
    }

    public abstract /* synthetic */ String getName();

    public void h() {
    }

    public final void i(Format[] formatArr, long j, long j2) throws ExoPlaybackException {
        boolean z2 = true;
        if (this.ck == -9223372036854775807L) {
            if (this.cj != -9223372036854775807L) {
                z2 = false;
            }
            Assertions.checkState(z2);
            this.cj = j;
            this.ck = j2;
            return;
        }
        int i = this.cl;
        long[] jArr = this.ab;
        if (i == jArr.length) {
            long j3 = jArr[i - 1];
            StringBuilder sb = new StringBuilder(65);
            sb.append("Too many stream changes, so dropping offset: ");
            sb.append(j3);
            Log.w("MediaCodecRenderer", sb.toString());
        } else {
            this.cl = i + 1;
        }
        int i2 = this.cl;
        this.aa[i2 - 1] = j;
        jArr[i2 - 1] = j2;
        long j4 = this.bw;
        this.ac[i2 - 1] = j4;
    }

    public boolean isEnded() {
        return this.ca;
    }

    public boolean isReady() {
        boolean z2;
        if (this.ad == null) {
            return false;
        }
        if (!b()) {
            if (this.bi >= 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2 || (this.bg != -9223372036854775807L && SystemClock.elapsedRealtime() < this.bg)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public final boolean k(long j, long j2) throws ExoPlaybackException {
        ch chVar;
        ch chVar2;
        Assertions.checkState(!this.ca);
        ch chVar3 = this.w;
        if (chVar3.hasSamples()) {
            chVar = chVar3;
            if (!am(j, j2, (MediaCodecAdapter) null, chVar3.g, this.bi, 0, chVar3.getSampleCount(), chVar3.getFirstSampleTimeUs(), chVar3.isDecodeOnly(), chVar3.isEndOfStream(), this.ae)) {
                return false;
            }
            ai(chVar.getLastSampleTimeUs());
            chVar.clear();
        } else {
            chVar = chVar3;
        }
        if (this.bz) {
            this.ca = true;
            return false;
        }
        boolean z2 = this.bn;
        DecoderInputBuffer decoderInputBuffer = this.v;
        if (z2) {
            chVar2 = chVar;
            Assertions.checkState(chVar2.append(decoderInputBuffer));
            this.bn = false;
        } else {
            chVar2 = chVar;
        }
        if (this.bo) {
            if (chVar2.hasSamples()) {
                return true;
            }
            n();
            this.bo = false;
            ab();
            if (!this.bm) {
                return false;
            }
        }
        Assertions.checkState(!this.bz);
        FormatHolder formatHolder = this.f;
        formatHolder.clear();
        decoderInputBuffer.clear();
        while (true) {
            decoderInputBuffer.clear();
            int j3 = j(formatHolder, decoderInputBuffer, 0);
            if (j3 != -5) {
                if (j3 == -4) {
                    if (!decoderInputBuffer.isEndOfStream()) {
                        if (this.cb) {
                            Format format = (Format) Assertions.checkNotNull(this.ad);
                            this.ae = format;
                            ah(format, (MediaFormat) null);
                            this.cb = false;
                        }
                        decoderInputBuffer.flip();
                        if (!chVar2.append(decoderInputBuffer)) {
                            this.bn = true;
                            break;
                        }
                    } else {
                        this.bz = true;
                        break;
                    }
                } else if (j3 != -3) {
                    throw new IllegalStateException();
                }
            } else {
                ag(formatHolder);
                break;
            }
        }
        if (chVar2.hasSamples()) {
            chVar2.flip();
        }
        if (chVar2.hasSamples() || this.bz || this.bo) {
            return true;
        }
        return false;
    }

    public DecoderReuseEvaluation l(MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        return new DecoderReuseEvaluation(mediaCodecInfo.a, format, format2, 0, 1);
    }

    public MediaCodecDecoderException m(IllegalStateException illegalStateException, @Nullable MediaCodecInfo mediaCodecInfo) {
        return new MediaCodecDecoderException(illegalStateException, mediaCodecInfo);
    }

    public final void n() {
        this.bo = false;
        this.w.clear();
        this.v.clear();
        this.bn = false;
        this.bm = false;
    }

    @TargetApi(23)
    public final boolean o() throws ExoPlaybackException {
        if (this.bt) {
            this.br = 1;
            if (this.aw || this.ay) {
                this.bs = 3;
                return false;
            }
            this.bs = 2;
        } else {
            ax();
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:90:0x0141  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean p(long r21, long r23) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r20 = this;
            r15 = r20
            int r0 = r15.bi
            r14 = 1
            r13 = 0
            if (r0 < 0) goto L_0x000a
            r0 = 1
            goto L_0x000b
        L_0x000a:
            r0 = 0
        L_0x000b:
            android.media.MediaCodec$BufferInfo r12 = r15.z
            if (r0 != 0) goto L_0x00fd
            boolean r0 = r15.az
            if (r0 == 0) goto L_0x002a
            boolean r0 = r15.bu
            if (r0 == 0) goto L_0x002a
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r0 = r15.am     // Catch:{ IllegalStateException -> 0x001e }
            int r0 = r0.dequeueOutputBufferIndex(r12)     // Catch:{ IllegalStateException -> 0x001e }
            goto L_0x0030
        L_0x001e:
            r20.al()
            boolean r0 = r15.ca
            if (r0 == 0) goto L_0x0029
            r20.ao()
        L_0x0029:
            return r13
        L_0x002a:
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r0 = r15.am
            int r0 = r0.dequeueOutputBufferIndex(r12)
        L_0x0030:
            if (r0 >= 0) goto L_0x0075
            r1 = -2
            if (r0 != r1) goto L_0x0064
            r15.bv = r14
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r0 = r15.am
            android.media.MediaFormat r0 = r0.getOutputFormat()
            int r1 = r15.au
            if (r1 == 0) goto L_0x0056
            java.lang.String r1 = "width"
            int r1 = r0.getInteger(r1)
            r2 = 32
            if (r1 != r2) goto L_0x0056
            java.lang.String r1 = "height"
            int r1 = r0.getInteger(r1)
            if (r1 != r2) goto L_0x0056
            r15.bd = r14
            goto L_0x0063
        L_0x0056:
            boolean r1 = r15.bb
            if (r1 == 0) goto L_0x005f
            java.lang.String r1 = "channel-count"
            r0.setInteger(r1, r14)
        L_0x005f:
            r15.ao = r0
            r15.ap = r14
        L_0x0063:
            return r14
        L_0x0064:
            boolean r0 = r15.be
            if (r0 == 0) goto L_0x0074
            boolean r0 = r15.bz
            if (r0 != 0) goto L_0x0071
            int r0 = r15.br
            r1 = 2
            if (r0 != r1) goto L_0x0074
        L_0x0071:
            r20.al()
        L_0x0074:
            return r13
        L_0x0075:
            boolean r1 = r15.bd
            if (r1 == 0) goto L_0x0081
            r15.bd = r13
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r1 = r15.am
            r1.releaseOutputBuffer((int) r0, (boolean) r13)
            return r14
        L_0x0081:
            int r1 = r12.size
            if (r1 != 0) goto L_0x008f
            int r1 = r12.flags
            r1 = r1 & 4
            if (r1 == 0) goto L_0x008f
            r20.al()
            return r13
        L_0x008f:
            r15.bi = r0
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r1 = r15.am
            java.nio.ByteBuffer r0 = r1.getOutputBuffer(r0)
            r15.bj = r0
            if (r0 == 0) goto L_0x00aa
            int r1 = r12.offset
            r0.position(r1)
            java.nio.ByteBuffer r0 = r15.bj
            int r1 = r12.offset
            int r2 = r12.size
            int r1 = r1 + r2
            r0.limit(r1)
        L_0x00aa:
            boolean r0 = r15.ba
            if (r0 == 0) goto L_0x00c9
            long r0 = r12.presentationTimeUs
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x00c9
            int r0 = r12.flags
            r0 = r0 & 4
            if (r0 == 0) goto L_0x00c9
            long r0 = r15.bw
            r2 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x00c9
            r12.presentationTimeUs = r0
        L_0x00c9:
            long r0 = r12.presentationTimeUs
            java.util.ArrayList<java.lang.Long> r2 = r15.y
            int r3 = r2.size()
            r4 = 0
        L_0x00d2:
            if (r4 >= r3) goto L_0x00ea
            java.lang.Object r5 = r2.get(r4)
            java.lang.Long r5 = (java.lang.Long) r5
            long r5 = r5.longValue()
            int r7 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r7 != 0) goto L_0x00e7
            r2.remove(r4)
            r0 = 1
            goto L_0x00eb
        L_0x00e7:
            int r4 = r4 + 1
            goto L_0x00d2
        L_0x00ea:
            r0 = 0
        L_0x00eb:
            r15.bk = r0
            long r0 = r15.bx
            long r2 = r12.presentationTimeUs
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x00f7
            r0 = 1
            goto L_0x00f8
        L_0x00f7:
            r0 = 0
        L_0x00f8:
            r15.bl = r0
            r15.ay(r2)
        L_0x00fd:
            boolean r0 = r15.az
            if (r0 == 0) goto L_0x0145
            boolean r0 = r15.bu
            if (r0 == 0) goto L_0x0145
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r5 = r15.am     // Catch:{ IllegalStateException -> 0x0137 }
            java.nio.ByteBuffer r6 = r15.bj     // Catch:{ IllegalStateException -> 0x0137 }
            int r7 = r15.bi     // Catch:{ IllegalStateException -> 0x0137 }
            int r8 = r12.flags     // Catch:{ IllegalStateException -> 0x0137 }
            r9 = 1
            long r10 = r12.presentationTimeUs     // Catch:{ IllegalStateException -> 0x0137 }
            boolean r3 = r15.bk     // Catch:{ IllegalStateException -> 0x0137 }
            boolean r4 = r15.bl     // Catch:{ IllegalStateException -> 0x0137 }
            com.google.android.exoplayer2.Format r1 = r15.ae     // Catch:{ IllegalStateException -> 0x0137 }
            r0 = r20
            r16 = r1
            r1 = r21
            r17 = r3
            r18 = r4
            r3 = r23
            r19 = r12
            r12 = r17
            r17 = 0
            r13 = r18
            r18 = 1
            r14 = r16
            boolean r0 = r0.am(r1, r3, r5, r6, r7, r8, r9, r10, r12, r13, r14)     // Catch:{ IllegalStateException -> 0x0135 }
            r15 = r19
            goto L_0x016d
        L_0x0135:
            goto L_0x013a
        L_0x0137:
            r17 = 0
            goto L_0x0135
        L_0x013a:
            r20.al()
            boolean r0 = r15.ca
            if (r0 == 0) goto L_0x0144
            r20.ao()
        L_0x0144:
            return r17
        L_0x0145:
            r19 = r12
            r17 = 0
            r18 = 1
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r5 = r15.am
            java.nio.ByteBuffer r6 = r15.bj
            int r7 = r15.bi
            r14 = r19
            int r8 = r14.flags
            r9 = 1
            long r10 = r14.presentationTimeUs
            boolean r12 = r15.bk
            boolean r13 = r15.bl
            com.google.android.exoplayer2.Format r3 = r15.ae
            r0 = r20
            r1 = r21
            r16 = r3
            r3 = r23
            r15 = r14
            r14 = r16
            boolean r0 = r0.am(r1, r3, r5, r6, r7, r8, r9, r10, r12, r13, r14)
        L_0x016d:
            if (r0 == 0) goto L_0x018d
            long r0 = r15.presentationTimeUs
            r2 = r20
            r3 = r15
            r2.ai(r0)
            int r0 = r3.flags
            r0 = r0 & 4
            if (r0 == 0) goto L_0x017f
            r14 = 1
            goto L_0x0180
        L_0x017f:
            r14 = 0
        L_0x0180:
            r0 = -1
            r2.bi = r0
            r0 = 0
            r2.bj = r0
            if (r14 != 0) goto L_0x0189
            return r18
        L_0x0189:
            r20.al()
            goto L_0x018f
        L_0x018d:
            r2 = r20
        L_0x018f:
            return r17
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.p(long, long):boolean");
    }

    public final void r() {
        try {
            this.am.flush();
        } finally {
            aq();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x006a A[Catch:{ IllegalStateException -> 0x00ac }, LOOP:1: B:26:0x0048->B:36:0x006a, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0088 A[Catch:{ IllegalStateException -> 0x00ac }, LOOP:2: B:37:0x006b->B:47:0x0088, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x006b A[EDGE_INSN: B:81:0x006b->B:84:0x006b ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0089 A[EDGE_INSN: B:82:0x0089->B:48:0x0089 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void render(long r12, long r14) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r11 = this;
            boolean r0 = r11.cc
            r1 = 0
            if (r0 == 0) goto L_0x000a
            r11.cc = r1
            r11.al()
        L_0x000a:
            com.google.android.exoplayer2.ExoPlaybackException r0 = r11.ch
            if (r0 != 0) goto L_0x00fa
            r0 = 1
            boolean r2 = r11.ca     // Catch:{ IllegalStateException -> 0x00ac }
            if (r2 == 0) goto L_0x0017
            r11.ap()     // Catch:{ IllegalStateException -> 0x00ac }
            return
        L_0x0017:
            com.google.android.exoplayer2.Format r2 = r11.ad     // Catch:{ IllegalStateException -> 0x00ac }
            if (r2 != 0) goto L_0x0023
            r2 = 2
            boolean r2 = r11.an(r2)     // Catch:{ IllegalStateException -> 0x00ac }
            if (r2 != 0) goto L_0x0023
            return
        L_0x0023:
            r11.ab()     // Catch:{ IllegalStateException -> 0x00ac }
            boolean r2 = r11.bm     // Catch:{ IllegalStateException -> 0x00ac }
            if (r2 == 0) goto L_0x003b
            java.lang.String r2 = "bypassRender"
            com.google.android.exoplayer2.util.TraceUtil.beginSection(r2)     // Catch:{ IllegalStateException -> 0x00ac }
        L_0x002f:
            boolean r2 = r11.k(r12, r14)     // Catch:{ IllegalStateException -> 0x00ac }
            if (r2 == 0) goto L_0x0036
            goto L_0x002f
        L_0x0036:
            com.google.android.exoplayer2.util.TraceUtil.endSection()     // Catch:{ IllegalStateException -> 0x00ac }
            goto L_0x00a6
        L_0x003b:
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter r2 = r11.am     // Catch:{ IllegalStateException -> 0x00ac }
            if (r2 == 0) goto L_0x008d
            long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ IllegalStateException -> 0x00ac }
            java.lang.String r4 = "drainAndFeed"
            com.google.android.exoplayer2.util.TraceUtil.beginSection(r4)     // Catch:{ IllegalStateException -> 0x00ac }
        L_0x0048:
            boolean r4 = r11.p(r12, r14)     // Catch:{ IllegalStateException -> 0x00ac }
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r4 == 0) goto L_0x006b
            long r7 = r11.aj     // Catch:{ IllegalStateException -> 0x00ac }
            int r4 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r4 == 0) goto L_0x0067
            long r7 = android.os.SystemClock.elapsedRealtime()     // Catch:{ IllegalStateException -> 0x00ac }
            long r7 = r7 - r2
            long r9 = r11.aj     // Catch:{ IllegalStateException -> 0x00ac }
            int r4 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r4 >= 0) goto L_0x0065
            goto L_0x0067
        L_0x0065:
            r4 = 0
            goto L_0x0068
        L_0x0067:
            r4 = 1
        L_0x0068:
            if (r4 == 0) goto L_0x006b
            goto L_0x0048
        L_0x006b:
            boolean r12 = r11.q()     // Catch:{ IllegalStateException -> 0x00ac }
            if (r12 == 0) goto L_0x0089
            long r12 = r11.aj     // Catch:{ IllegalStateException -> 0x00ac }
            int r14 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r14 == 0) goto L_0x0085
            long r12 = android.os.SystemClock.elapsedRealtime()     // Catch:{ IllegalStateException -> 0x00ac }
            long r12 = r12 - r2
            long r14 = r11.aj     // Catch:{ IllegalStateException -> 0x00ac }
            int r4 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r4 >= 0) goto L_0x0083
            goto L_0x0085
        L_0x0083:
            r12 = 0
            goto L_0x0086
        L_0x0085:
            r12 = 1
        L_0x0086:
            if (r12 == 0) goto L_0x0089
            goto L_0x006b
        L_0x0089:
            com.google.android.exoplayer2.util.TraceUtil.endSection()     // Catch:{ IllegalStateException -> 0x00ac }
            goto L_0x00a6
        L_0x008d:
            com.google.android.exoplayer2.decoder.DecoderCounters r14 = r11.ci     // Catch:{ IllegalStateException -> 0x00ac }
            int r15 = r14.a     // Catch:{ IllegalStateException -> 0x00ac }
            com.google.android.exoplayer2.source.SampleStream r2 = r11.j     // Catch:{ IllegalStateException -> 0x00ac }
            java.lang.Object r2 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)     // Catch:{ IllegalStateException -> 0x00ac }
            com.google.android.exoplayer2.source.SampleStream r2 = (com.google.android.exoplayer2.source.SampleStream) r2     // Catch:{ IllegalStateException -> 0x00ac }
            long r3 = r11.l     // Catch:{ IllegalStateException -> 0x00ac }
            long r12 = r12 - r3
            int r12 = r2.skipData(r12)     // Catch:{ IllegalStateException -> 0x00ac }
            int r15 = r15 + r12
            r14.a = r15     // Catch:{ IllegalStateException -> 0x00ac }
            r11.an(r0)     // Catch:{ IllegalStateException -> 0x00ac }
        L_0x00a6:
            com.google.android.exoplayer2.decoder.DecoderCounters r12 = r11.ci     // Catch:{ IllegalStateException -> 0x00ac }
            r12.ensureUpdated()     // Catch:{ IllegalStateException -> 0x00ac }
            return
        L_0x00ac:
            r12 = move-exception
            int r13 = com.google.android.exoplayer2.util.Util.a
            r14 = 21
            if (r13 < r14) goto L_0x00b8
            boolean r15 = r12 instanceof android.media.MediaCodec.CodecException
            if (r15 == 0) goto L_0x00b8
            goto L_0x00cd
        L_0x00b8:
            java.lang.StackTraceElement[] r15 = r12.getStackTrace()
            int r2 = r15.length
            if (r2 <= 0) goto L_0x00cf
            r15 = r15[r1]
            java.lang.String r15 = r15.getClassName()
            java.lang.String r2 = "android.media.MediaCodec"
            boolean r15 = r15.equals(r2)
            if (r15 == 0) goto L_0x00cf
        L_0x00cd:
            r15 = 1
            goto L_0x00d0
        L_0x00cf:
            r15 = 0
        L_0x00d0:
            if (r15 == 0) goto L_0x00f9
            r11.ad(r12)
            if (r13 < r14) goto L_0x00e7
            boolean r13 = r12 instanceof android.media.MediaCodec.CodecException
            if (r13 == 0) goto L_0x00e3
            r13 = r12
            android.media.MediaCodec$CodecException r13 = (android.media.MediaCodec.CodecException) r13
            boolean r13 = r13.isRecoverable()
            goto L_0x00e4
        L_0x00e3:
            r13 = 0
        L_0x00e4:
            if (r13 == 0) goto L_0x00e7
            r1 = 1
        L_0x00e7:
            if (r1 == 0) goto L_0x00ec
            r11.ao()
        L_0x00ec:
            com.google.android.exoplayer2.mediacodec.MediaCodecInfo r13 = r11.at
            com.google.android.exoplayer2.mediacodec.MediaCodecDecoderException r12 = r11.m(r12, r13)
            com.google.android.exoplayer2.Format r13 = r11.ad
            com.google.android.exoplayer2.ExoPlaybackException r12 = r11.a(r12, r13, r1)
            throw r12
        L_0x00f9:
            throw r12
        L_0x00fa:
            r12 = 0
            r11.ch = r12
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.render(long, long):void");
    }

    public final boolean s() {
        if (this.am == null) {
            return false;
        }
        if (this.bs == 3 || this.aw || ((this.ax && !this.bv) || (this.ay && this.bu))) {
            ao();
            return true;
        }
        r();
        return false;
    }

    public void setPlaybackSpeed(float f, float f2) throws ExoPlaybackException {
        this.ak = f;
        this.al = f2;
        aw(this.an);
    }

    public void setRenderTimeLimitMs(long j) {
        this.aj = j;
    }

    public final int supportsFormat(Format format) throws ExoPlaybackException {
        try {
            return av(this.q, format);
        } catch (MediaCodecUtil.DecoderQueryException e) {
            throw a(e, format, false);
        }
    }

    public final int supportsMixedMimeTypeAdaptation() {
        return 8;
    }

    public final List<MediaCodecInfo> t(boolean z2) throws MediaCodecUtil.DecoderQueryException {
        Format format = this.ad;
        MediaCodecSelector mediaCodecSelector = this.q;
        List<MediaCodecInfo> w2 = w(mediaCodecSelector, format, z2);
        if (w2.isEmpty() && z2) {
            w2 = w(mediaCodecSelector, this.ad, false);
            if (!w2.isEmpty()) {
                String str = this.ad.p;
                String valueOf = String.valueOf(w2);
                StringBuilder l = y2.l(valueOf.length() + y2.c(str, 99), "Drm session requires secure decoder for ", str, ", but no secure decoder available. Trying to proceed with ", valueOf);
                l.append(".");
                Log.w("MediaCodecRenderer", l.toString());
            }
        }
        return w2;
    }

    public boolean u() {
        return false;
    }

    public float v(float f, Format[] formatArr) {
        return -1.0f;
    }

    public abstract List<MediaCodecInfo> w(MediaCodecSelector mediaCodecSelector, Format format, boolean z2) throws MediaCodecUtil.DecoderQueryException;

    @Nullable
    public final FrameworkMediaCrypto x(DrmSession drmSession) throws ExoPlaybackException {
        ExoMediaCrypto mediaCrypto = drmSession.getMediaCrypto();
        if (mediaCrypto == null || (mediaCrypto instanceof FrameworkMediaCrypto)) {
            return (FrameworkMediaCrypto) mediaCrypto;
        }
        String valueOf = String.valueOf(mediaCrypto);
        StringBuilder sb = new StringBuilder(valueOf.length() + 42);
        sb.append("Expecting FrameworkMediaCrypto but found: ");
        sb.append(valueOf);
        throw a(new IllegalArgumentException(sb.toString()), this.ad, false);
    }

    @Nullable
    public abstract MediaCodecAdapter.Configuration y(MediaCodecInfo mediaCodecInfo, Format format, @Nullable MediaCrypto mediaCrypto, float f);

    public void z(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
    }

    public static class DecoderInitializationException extends Exception {
        public final String c;
        public final boolean f;
        @Nullable
        public final MediaCodecInfo g;
        @Nullable
        public final String h;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public DecoderInitializationException(com.google.android.exoplayer2.Format r11, @androidx.annotation.Nullable java.lang.Throwable r12, boolean r13, int r14) {
            /*
                r10 = this;
                java.lang.String r0 = java.lang.String.valueOf(r11)
                int r1 = r0.length()
                int r1 = r1 + 36
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>(r1)
                java.lang.String r1 = "Decoder init failed: ["
                r2.append(r1)
                r2.append(r14)
                java.lang.String r1 = "], "
                r2.append(r1)
                r2.append(r0)
                java.lang.String r4 = r2.toString()
                java.lang.String r6 = r11.p
                r8 = 0
                if (r14 >= 0) goto L_0x002b
                java.lang.String r11 = "neg_"
                goto L_0x002d
            L_0x002b:
                java.lang.String r11 = ""
            L_0x002d:
                int r14 = java.lang.Math.abs(r14)
                int r0 = r11.length()
                int r0 = r0 + 71
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>(r0)
                java.lang.String r0 = "com.google.android.exoplayer2.mediacodec.MediaCodecRenderer_"
                r1.append(r0)
                r1.append(r11)
                r1.append(r14)
                java.lang.String r9 = r1.toString()
                r3 = r10
                r5 = r12
                r7 = r13
                r3.<init>(r4, r5, r6, r7, r8, r9)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException.<init>(com.google.android.exoplayer2.Format, java.lang.Throwable, boolean, int):void");
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public DecoderInitializationException(com.google.android.exoplayer2.Format r11, @androidx.annotation.Nullable java.lang.Throwable r12, boolean r13, com.google.android.exoplayer2.mediacodec.MediaCodecInfo r14) {
            /*
                r10 = this;
                java.lang.String r0 = r14.a
                java.lang.String r1 = java.lang.String.valueOf(r11)
                r2 = 23
                int r2 = defpackage.y2.c(r0, r2)
                int r3 = r1.length()
                int r3 = r3 + r2
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>(r3)
                java.lang.String r3 = "Decoder init failed: "
                r2.append(r3)
                r2.append(r0)
                java.lang.String r0 = ", "
                r2.append(r0)
                r2.append(r1)
                java.lang.String r4 = r2.toString()
                java.lang.String r6 = r11.p
                int r11 = com.google.android.exoplayer2.util.Util.a
                r0 = 21
                if (r11 < r0) goto L_0x003e
                boolean r11 = r12 instanceof android.media.MediaCodec.CodecException
                if (r11 == 0) goto L_0x003e
                r11 = r12
                android.media.MediaCodec$CodecException r11 = (android.media.MediaCodec.CodecException) r11
                java.lang.String r11 = r11.getDiagnosticInfo()
                goto L_0x003f
            L_0x003e:
                r11 = 0
            L_0x003f:
                r9 = r11
                r3 = r10
                r5 = r12
                r7 = r13
                r8 = r14
                r3.<init>(r4, r5, r6, r7, r8, r9)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException.<init>(com.google.android.exoplayer2.Format, java.lang.Throwable, boolean, com.google.android.exoplayer2.mediacodec.MediaCodecInfo):void");
        }

        public DecoderInitializationException(String str, @Nullable Throwable th, String str2, boolean z, @Nullable MediaCodecInfo mediaCodecInfo, @Nullable String str3) {
            super(str, th);
            this.c = str2;
            this.f = z;
            this.g = mediaCodecInfo;
            this.h = str3;
        }
    }
}
