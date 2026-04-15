package com.google.android.exoplayer2.audio;

import android.content.Context;
import android.media.MediaFormat;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MediaCodecAudioRenderer extends MediaCodecRenderer implements MediaClock {
    public final Context cn;
    public final AudioRendererEventListener.EventDispatcher co;
    public final AudioSink cp;
    public int cq;
    public boolean cr;
    @Nullable
    public Format cs;
    public long ct;
    public boolean cu;
    public boolean cv;
    public boolean cw;
    public boolean cx;
    @Nullable
    public Renderer.WakeupListener cy;

    public final class a implements AudioSink.Listener {
        public a() {
        }

        public void onAudioSinkError(Exception exc) {
            Log.e("MediaCodecAudioRenderer", "Audio sink error", exc);
            MediaCodecAudioRenderer.this.co.audioSinkError(exc);
        }

        public void onOffloadBufferEmptying() {
            Renderer.WakeupListener wakeupListener = MediaCodecAudioRenderer.this.cy;
            if (wakeupListener != null) {
                wakeupListener.onWakeup();
            }
        }

        public void onOffloadBufferFull(long j) {
            Renderer.WakeupListener wakeupListener = MediaCodecAudioRenderer.this.cy;
            if (wakeupListener != null) {
                wakeupListener.onSleep(j);
            }
        }

        public void onPositionAdvancing(long j) {
            MediaCodecAudioRenderer.this.co.positionAdvancing(j);
        }

        public void onPositionDiscontinuity() {
            MediaCodecAudioRenderer.this.cv = true;
        }

        public void onSkipSilenceEnabledChanged(boolean z) {
            MediaCodecAudioRenderer.this.co.skipSilenceEnabledChanged(z);
        }

        public void onUnderrun(int i, long j, long j2) {
            MediaCodecAudioRenderer.this.co.underrun(i, j, j2);
        }
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        this(context, mediaCodecSelector, (Handler) null, (AudioRendererEventListener) null);
    }

    public final void ad(IllegalStateException illegalStateException) {
        Log.e("MediaCodecAudioRenderer", "Audio codec error", illegalStateException);
        this.co.audioCodecError(illegalStateException);
    }

    public final void ae(long j, long j2, String str) {
        this.co.decoderInitialized(str, j, j2);
    }

    public final void af(String str) {
        this.co.decoderReleased(str);
    }

    @Nullable
    public final DecoderReuseEvaluation ag(FormatHolder formatHolder) throws ExoPlaybackException {
        DecoderReuseEvaluation ag = super.ag(formatHolder);
        this.co.inputFormatChanged(formatHolder.b, ag);
        return ag;
    }

    public final void ah(Format format, @Nullable MediaFormat mediaFormat) throws ExoPlaybackException {
        int i;
        Format format2 = this.cs;
        int[] iArr = null;
        if (format2 != null) {
            format = format2;
        } else if (this.am != null) {
            boolean equals = "audio/raw".equals(format.p);
            int i2 = format.ae;
            if (!equals) {
                if (Util.a >= 24 && mediaFormat.containsKey("pcm-encoding")) {
                    i2 = mediaFormat.getInteger("pcm-encoding");
                } else if (mediaFormat.containsKey("v-bits-per-sample")) {
                    i2 = Util.getPcmEncoding(mediaFormat.getInteger("v-bits-per-sample"));
                } else if (!"audio/raw".equals(format.p)) {
                    i2 = 2;
                }
            }
            Format build = new Format.Builder().setSampleMimeType("audio/raw").setPcmEncoding(i2).setEncoderDelay(format.af).setEncoderPadding(format.ag).setChannelCount(mediaFormat.getInteger("channel-count")).setSampleRate(mediaFormat.getInteger("sample-rate")).build();
            if (this.cr && build.ac == 6 && (i = format.ac) < 6) {
                iArr = new int[i];
                for (int i3 = 0; i3 < i; i3++) {
                    iArr[i3] = i3;
                }
            }
            format = build;
        }
        try {
            this.cp.configure(format, 0, iArr);
        } catch (AudioSink.ConfigurationException e) {
            throw a(e, e.c, false);
        }
    }

    public final void aj() {
        this.cp.handleDiscontinuity();
    }

    public final void ak(DecoderInputBuffer decoderInputBuffer) {
        if (this.cu && !decoderInputBuffer.isDecodeOnly()) {
            if (Math.abs(decoderInputBuffer.i - this.ct) > 500000) {
                this.ct = decoderInputBuffer.i;
            }
            this.cu = false;
        }
    }

    public final boolean am(long j, long j2, @Nullable MediaCodecAdapter mediaCodecAdapter, @Nullable ByteBuffer byteBuffer, int i, int i2, int i3, long j3, boolean z, boolean z2, Format format) throws ExoPlaybackException {
        Assertions.checkNotNull(byteBuffer);
        if (this.cs == null || (i2 & 2) == 0) {
            AudioSink audioSink = this.cp;
            if (z) {
                if (mediaCodecAdapter != null) {
                    mediaCodecAdapter.releaseOutputBuffer(i, false);
                }
                this.ci.c += i3;
                audioSink.handleDiscontinuity();
                return true;
            }
            try {
                if (!audioSink.handleBuffer(byteBuffer, j3, i3)) {
                    return false;
                }
                if (mediaCodecAdapter != null) {
                    mediaCodecAdapter.releaseOutputBuffer(i, false);
                }
                this.ci.b += i3;
                return true;
            } catch (AudioSink.InitializationException e) {
                throw a(e, e.f, e.c);
            } catch (AudioSink.WriteException e2) {
                throw a(e2, format, e2.c);
            }
        } else {
            ((MediaCodecAdapter) Assertions.checkNotNull(mediaCodecAdapter)).releaseOutputBuffer(i, false);
            return true;
        }
    }

    public final void ap() throws ExoPlaybackException {
        try {
            this.cp.playToEndOfStream();
        } catch (AudioSink.WriteException e) {
            throw a(e, e.f, e.c);
        }
    }

    public final boolean au(Format format) {
        return this.cp.supportsFormat(format);
    }

    public final int av(MediaCodecSelector mediaCodecSelector, Format format) throws MediaCodecUtil.DecoderQueryException {
        int i;
        boolean z;
        boolean z2;
        if (!MimeTypes.isAudio(format.p)) {
            return ha.a(0);
        }
        if (Util.a >= 21) {
            i = 32;
        } else {
            i = 0;
        }
        Class<? extends ExoMediaCrypto> cls = format.ai;
        if (cls != null) {
            z = true;
        } else {
            z = false;
        }
        if (cls == null || FrameworkMediaCrypto.class.equals(cls)) {
            z2 = true;
        } else {
            z2 = false;
        }
        int i2 = 8;
        int i3 = 4;
        AudioSink audioSink = this.cp;
        if (z2 && audioSink.supportsFormat(format) && (!z || MediaCodecUtil.getDecryptOnlyDecoderInfo() != null)) {
            return ha.b(4, 8, i);
        }
        if ("audio/raw".equals(format.p) && !audioSink.supportsFormat(format)) {
            return ha.a(1);
        }
        if (!audioSink.supportsFormat(Util.getPcmFormat(2, format.ac, format.ad))) {
            return ha.a(1);
        }
        List<MediaCodecInfo> w = w(mediaCodecSelector, format, false);
        if (w.isEmpty()) {
            return ha.a(1);
        }
        if (!z2) {
            return ha.a(2);
        }
        MediaCodecInfo mediaCodecInfo = w.get(0);
        boolean isFormatSupported = mediaCodecInfo.isFormatSupported(format);
        if (isFormatSupported && mediaCodecInfo.isSeamlessAdaptationSupported(format)) {
            i2 = 16;
        }
        if (!isFormatSupported) {
            i3 = 3;
        }
        return ha.b(i3, i2, i);
    }

    public final int az(Format format, MediaCodecInfo mediaCodecInfo) {
        int i;
        if (!"OMX.google.raw.decoder".equals(mediaCodecInfo.a) || (i = Util.a) >= 24 || (i == 23 && Util.isTv(this.cn))) {
            return format.q;
        }
        return -1;
    }

    public final void ba() {
        long currentPositionUs = this.cp.getCurrentPositionUs(isEnded());
        if (currentPositionUs != Long.MIN_VALUE) {
            if (!this.cv) {
                currentPositionUs = Math.max(this.ct, currentPositionUs);
            }
            this.ct = currentPositionUs;
            this.cv = false;
        }
    }

    public final void c() {
        AudioRendererEventListener.EventDispatcher eventDispatcher = this.co;
        this.cw = true;
        try {
            this.cp.flush();
            try {
                super.c();
            } finally {
                eventDispatcher.disabled(this.ci);
            }
        } catch (Throwable th) {
            super.c();
            throw th;
        } finally {
            eventDispatcher.disabled(this.ci);
        }
    }

    public final void d(boolean z, boolean z2) throws ExoPlaybackException {
        super.d(z, z2);
        this.co.enabled(this.ci);
        boolean z3 = ((RendererConfiguration) Assertions.checkNotNull(this.g)).a;
        AudioSink audioSink = this.cp;
        if (z3) {
            audioSink.enableTunnelingV21();
        } else {
            audioSink.disableTunneling();
        }
    }

    public final void e(long j, boolean z) throws ExoPlaybackException {
        super.e(j, z);
        boolean z2 = this.cx;
        AudioSink audioSink = this.cp;
        if (z2) {
            audioSink.experimentalFlushWithoutAudioTrackRelease();
        } else {
            audioSink.flush();
        }
        this.ct = j;
        this.cu = true;
        this.cv = true;
    }

    public void experimentalSetEnableKeepAudioTrackOnSeek(boolean z) {
        this.cx = z;
    }

    public final void f() {
        AudioSink audioSink = this.cp;
        try {
            super.f();
        } finally {
            if (this.cw) {
                this.cw = false;
                audioSink.reset();
            }
        }
    }

    public final void g() {
        this.cp.play();
    }

    @Nullable
    public MediaClock getMediaClock() {
        return this;
    }

    public String getName() {
        return "MediaCodecAudioRenderer";
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.cp.getPlaybackParameters();
    }

    public long getPositionUs() {
        if (getState() == 2) {
            ba();
        }
        return this.ct;
    }

    public final void h() {
        ba();
        this.cp.pause();
    }

    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        AudioSink audioSink = this.cp;
        if (i == 2) {
            audioSink.setVolume(((Float) obj).floatValue());
        } else if (i == 3) {
            audioSink.setAudioAttributes((AudioAttributes) obj);
        } else if (i != 5) {
            switch (i) {
                case 101:
                    audioSink.setSkipSilenceEnabled(((Boolean) obj).booleanValue());
                    return;
                case 102:
                    audioSink.setAudioSessionId(((Integer) obj).intValue());
                    return;
                case 103:
                    this.cy = (Renderer.WakeupListener) obj;
                    return;
                default:
                    super.handleMessage(i, obj);
                    return;
            }
        } else {
            audioSink.setAuxEffectInfo((AuxEffectInfo) obj);
        }
    }

    public boolean isEnded() {
        return super.isEnded() && this.cp.isEnded();
    }

    public boolean isReady() {
        return this.cp.hasPendingData() || super.isReady();
    }

    public final DecoderReuseEvaluation l(MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        int i;
        DecoderReuseEvaluation canReuseCodec = mediaCodecInfo.canReuseCodec(format, format2);
        int i2 = canReuseCodec.e;
        if (az(format2, mediaCodecInfo) > this.cq) {
            i2 |= 64;
        }
        int i3 = i2;
        String str = mediaCodecInfo.a;
        if (i3 != 0) {
            i = 0;
        } else {
            i = canReuseCodec.d;
        }
        return new DecoderReuseEvaluation(str, format, format2, i, i3);
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.cp.setPlaybackParameters(playbackParameters);
    }

    public final float v(float f, Format[] formatArr) {
        int i = -1;
        for (Format format : formatArr) {
            int i2 = format.ad;
            if (i2 != -1) {
                i = Math.max(i, i2);
            }
        }
        if (i == -1) {
            return -1.0f;
        }
        return f * ((float) i);
    }

    public final List<MediaCodecInfo> w(MediaCodecSelector mediaCodecSelector, Format format, boolean z) throws MediaCodecUtil.DecoderQueryException {
        MediaCodecInfo decryptOnlyDecoderInfo;
        String str = format.p;
        if (str == null) {
            return Collections.emptyList();
        }
        if (this.cp.supportsFormat(format) && (decryptOnlyDecoderInfo = MediaCodecUtil.getDecryptOnlyDecoderInfo()) != null) {
            return Collections.singletonList(decryptOnlyDecoderInfo);
        }
        List<MediaCodecInfo> decoderInfosSortedByFormatSupport = MediaCodecUtil.getDecoderInfosSortedByFormatSupport(mediaCodecSelector.getDecoderInfos(str, z, false), format);
        if ("audio/eac3-joc".equals(str)) {
            ArrayList arrayList = new ArrayList(decoderInfosSortedByFormatSupport);
            arrayList.addAll(mediaCodecSelector.getDecoderInfos("audio/eac3", z, false));
            decoderInfosSortedByFormatSupport = arrayList;
        }
        return Collections.unmodifiableList(decoderInfosSortedByFormatSupport);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b9  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00fd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.Configuration y(com.google.android.exoplayer2.mediacodec.MediaCodecInfo r13, com.google.android.exoplayer2.Format r14, @androidx.annotation.Nullable android.media.MediaCrypto r15, float r16) {
        /*
            r12 = this;
            r0 = r12
            r2 = r13
            r4 = r14
            r1 = r16
            com.google.android.exoplayer2.Format[] r3 = r0.k
            java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r3)
            com.google.android.exoplayer2.Format[] r3 = (com.google.android.exoplayer2.Format[]) r3
            int r5 = r12.az(r14, r13)
            int r6 = r3.length
            r7 = 0
            r8 = 1
            if (r6 != r8) goto L_0x0017
            goto L_0x0030
        L_0x0017:
            int r6 = r3.length
            r9 = 0
        L_0x0019:
            if (r9 >= r6) goto L_0x0030
            r10 = r3[r9]
            com.google.android.exoplayer2.decoder.DecoderReuseEvaluation r11 = r13.canReuseCodec(r14, r10)
            int r11 = r11.d
            if (r11 == 0) goto L_0x002d
            int r10 = r12.az(r10, r13)
            int r5 = java.lang.Math.max(r5, r10)
        L_0x002d:
            int r9 = r9 + 1
            goto L_0x0019
        L_0x0030:
            r0.cq = r5
            int r3 = com.google.android.exoplayer2.util.Util.a
            r5 = 24
            if (r3 >= r5) goto L_0x0068
            java.lang.String r6 = "OMX.SEC.aac.dec"
            java.lang.String r9 = r2.a
            boolean r6 = r6.equals(r9)
            if (r6 == 0) goto L_0x0068
            java.lang.String r6 = "samsung"
            java.lang.String r9 = com.google.android.exoplayer2.util.Util.c
            boolean r6 = r6.equals(r9)
            if (r6 == 0) goto L_0x0068
            java.lang.String r6 = com.google.android.exoplayer2.util.Util.b
            java.lang.String r9 = "zeroflte"
            boolean r9 = r6.startsWith(r9)
            if (r9 != 0) goto L_0x0066
            java.lang.String r9 = "herolte"
            boolean r9 = r6.startsWith(r9)
            if (r9 != 0) goto L_0x0066
            java.lang.String r9 = "heroqlte"
            boolean r6 = r6.startsWith(r9)
            if (r6 == 0) goto L_0x0068
        L_0x0066:
            r6 = 1
            goto L_0x0069
        L_0x0068:
            r6 = 0
        L_0x0069:
            r0.cr = r6
            int r6 = r0.cq
            android.media.MediaFormat r9 = new android.media.MediaFormat
            r9.<init>()
            java.lang.String r10 = "mime"
            java.lang.String r11 = r2.c
            r9.setString(r10, r11)
            int r10 = r4.ac
            java.lang.String r11 = "channel-count"
            r9.setInteger(r11, r10)
            java.lang.String r10 = "sample-rate"
            int r11 = r4.ad
            r9.setInteger(r10, r11)
            java.util.List<byte[]> r10 = r4.r
            com.google.android.exoplayer2.util.MediaFormatUtil.setCsdBuffers(r9, r10)
            java.lang.String r10 = "max-input-size"
            com.google.android.exoplayer2.util.MediaFormatUtil.maybeSetInteger(r9, r10, r6)
            r6 = 23
            if (r3 < r6) goto L_0x00be
            java.lang.String r10 = "priority"
            r9.setInteger(r10, r7)
            r10 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r10 = (r1 > r10 ? 1 : (r1 == r10 ? 0 : -1))
            if (r10 == 0) goto L_0x00be
            if (r3 != r6) goto L_0x00b6
            java.lang.String r6 = com.google.android.exoplayer2.util.Util.d
            java.lang.String r10 = "ZTE B2017G"
            boolean r10 = r10.equals(r6)
            if (r10 != 0) goto L_0x00b4
            java.lang.String r10 = "AXON 7 mini"
            boolean r6 = r10.equals(r6)
            if (r6 == 0) goto L_0x00b6
        L_0x00b4:
            r6 = 1
            goto L_0x00b7
        L_0x00b6:
            r6 = 0
        L_0x00b7:
            if (r6 != 0) goto L_0x00be
            java.lang.String r6 = "operating-rate"
            r9.setFloat(r6, r1)
        L_0x00be:
            r1 = 28
            java.lang.String r6 = r4.p
            if (r3 > r1) goto L_0x00d1
            java.lang.String r1 = "audio/ac4"
            boolean r1 = r1.equals(r6)
            if (r1 == 0) goto L_0x00d1
            java.lang.String r1 = "ac4-is-sync"
            r9.setInteger(r1, r8)
        L_0x00d1:
            if (r3 < r5) goto L_0x00e8
            int r1 = r4.ac
            r3 = 4
            com.google.android.exoplayer2.Format r1 = com.google.android.exoplayer2.util.Util.getPcmFormat(r3, r1, r11)
            com.google.android.exoplayer2.audio.AudioSink r5 = r0.cp
            int r1 = r5.getFormatSupport(r1)
            r5 = 2
            if (r1 != r5) goto L_0x00e8
            java.lang.String r1 = "pcm-encoding"
            r9.setInteger(r1, r3)
        L_0x00e8:
            java.lang.String r1 = "audio/raw"
            java.lang.String r3 = r2.b
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x00f9
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x00f9
            r7 = 1
        L_0x00f9:
            if (r7 == 0) goto L_0x00fd
            r1 = r4
            goto L_0x00fe
        L_0x00fd:
            r1 = 0
        L_0x00fe:
            r0.cs = r1
            com.google.android.exoplayer2.mediacodec.MediaCodecAdapter$Configuration r8 = new com.google.android.exoplayer2.mediacodec.MediaCodecAdapter$Configuration
            r5 = 0
            r7 = 0
            r1 = r8
            r2 = r13
            r3 = r9
            r4 = r14
            r6 = r15
            r1.<init>(r2, r3, r4, r5, r6, r7)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.MediaCodecAudioRenderer.y(com.google.android.exoplayer2.mediacodec.MediaCodecInfo, com.google.android.exoplayer2.Format, android.media.MediaCrypto, float):com.google.android.exoplayer2.mediacodec.MediaCodecAdapter$Configuration");
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, @Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener) {
        this(context, mediaCodecSelector, handler, audioRendererEventListener, (AudioCapabilities) null, new AudioProcessor[0]);
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, @Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, @Nullable AudioCapabilities audioCapabilities, AudioProcessor... audioProcessorArr) {
        this(context, mediaCodecSelector, handler, audioRendererEventListener, new DefaultAudioSink(audioCapabilities, audioProcessorArr));
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, @Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioSink audioSink) {
        this(context, MediaCodecAdapter.Factory.a, mediaCodecSelector, false, handler, audioRendererEventListener, audioSink);
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecSelector mediaCodecSelector, boolean z, @Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioSink audioSink) {
        this(context, MediaCodecAdapter.Factory.a, mediaCodecSelector, z, handler, audioRendererEventListener, audioSink);
    }

    public MediaCodecAudioRenderer(Context context, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, boolean z, @Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioSink audioSink) {
        super(1, factory, mediaCodecSelector, z, 44100.0f);
        this.cn = context.getApplicationContext();
        this.cp = audioSink;
        this.co = new AudioRendererEventListener.EventDispatcher(handler, audioRendererEventListener);
        audioSink.setListener(new a());
    }
}
