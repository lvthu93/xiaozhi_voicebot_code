package com.google.android.exoplayer2.audio;

import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.decoder.Decoder;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.decoder.SimpleOutputBuffer;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;

public abstract class DecoderAudioRenderer<T extends Decoder<DecoderInputBuffer, ? extends SimpleOutputBuffer, ? extends DecoderException>> extends BaseRenderer implements MediaClock {
    @Nullable
    public DrmSession aa;
    @Nullable
    public DrmSession ab;
    public int ac;
    public boolean ad;
    public boolean ae;
    public long af;
    public boolean ag;
    public boolean ah;
    public boolean ai;
    public boolean aj;
    public final AudioRendererEventListener.EventDispatcher p;
    public final AudioSink q;
    public final DecoderInputBuffer r;
    public DecoderCounters s;
    public Format t;
    public int u;
    public int v;
    public boolean w;
    @Nullable
    public T x;
    @Nullable
    public DecoderInputBuffer y;
    @Nullable
    public SimpleOutputBuffer z;

    public final class a implements AudioSink.Listener {
        public a() {
        }

        public void onAudioSinkError(Exception exc) {
            Log.e("DecoderAudioRenderer", "Audio sink error", exc);
            DecoderAudioRenderer.this.p.audioSinkError(exc);
        }

        public /* bridge */ /* synthetic */ void onOffloadBufferEmptying() {
            bw.b(this);
        }

        public /* bridge */ /* synthetic */ void onOffloadBufferFull(long j) {
            bw.c(this, j);
        }

        public void onPositionAdvancing(long j) {
            DecoderAudioRenderer.this.p.positionAdvancing(j);
        }

        public void onPositionDiscontinuity() {
            DecoderAudioRenderer.this.ah = true;
        }

        public void onSkipSilenceEnabledChanged(boolean z) {
            DecoderAudioRenderer.this.p.skipSilenceEnabledChanged(z);
        }

        public void onUnderrun(int i, long j, long j2) {
            DecoderAudioRenderer.this.p.underrun(i, j, j2);
        }
    }

    public DecoderAudioRenderer() {
        this((Handler) null, (AudioRendererEventListener) null, new AudioProcessor[0]);
    }

    public final void c() {
        AudioRendererEventListener.EventDispatcher eventDispatcher = this.p;
        this.t = null;
        this.ae = true;
        try {
            s1.b(this.ab, (DrmSession) null);
            this.ab = null;
            q();
            this.q.reset();
        } finally {
            eventDispatcher.disabled(this.s);
        }
    }

    public final void d(boolean z2, boolean z3) throws ExoPlaybackException {
        DecoderCounters decoderCounters = new DecoderCounters();
        this.s = decoderCounters;
        this.p.enabled(decoderCounters);
        boolean z4 = ((RendererConfiguration) Assertions.checkNotNull(this.g)).a;
        AudioSink audioSink = this.q;
        if (z4) {
            audioSink.enableTunnelingV21();
        } else {
            audioSink.disableTunneling();
        }
    }

    public final void e(long j, boolean z2) throws ExoPlaybackException {
        boolean z3 = this.w;
        AudioSink audioSink = this.q;
        if (z3) {
            audioSink.experimentalFlushWithoutAudioTrackRelease();
        } else {
            audioSink.flush();
        }
        this.af = j;
        this.ag = true;
        this.ah = true;
        this.ai = false;
        this.aj = false;
        if (this.x == null) {
            return;
        }
        if (this.ac != 0) {
            q();
            o();
            return;
        }
        this.y = null;
        SimpleOutputBuffer simpleOutputBuffer = this.z;
        if (simpleOutputBuffer != null) {
            simpleOutputBuffer.release();
            this.z = null;
        }
        this.x.flush();
        this.ad = false;
    }

    public void experimentalSetEnableKeepAudioTrackOnSeek(boolean z2) {
        this.w = z2;
    }

    public final void g() {
        this.q.play();
    }

    @Nullable
    public MediaClock getMediaClock() {
        return this;
    }

    public abstract /* synthetic */ String getName();

    public PlaybackParameters getPlaybackParameters() {
        return this.q.getPlaybackParameters();
    }

    public long getPositionUs() {
        if (getState() == 2) {
            s();
        }
        return this.af;
    }

    public final void h() {
        s();
        this.q.pause();
    }

    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        AudioSink audioSink = this.q;
        if (i == 2) {
            audioSink.setVolume(((Float) obj).floatValue());
        } else if (i == 3) {
            audioSink.setAudioAttributes((AudioAttributes) obj);
        } else if (i == 5) {
            audioSink.setAuxEffectInfo((AuxEffectInfo) obj);
        } else if (i == 101) {
            audioSink.setSkipSilenceEnabled(((Boolean) obj).booleanValue());
        } else if (i != 102) {
            super.handleMessage(i, obj);
        } else {
            audioSink.setAudioSessionId(((Integer) obj).intValue());
        }
    }

    public boolean isEnded() {
        return this.aj && this.q.isEnded();
    }

    public boolean isReady() {
        if (this.q.hasPendingData() || (this.t != null && (b() || this.z != null))) {
            return true;
        }
        return false;
    }

    public abstract Decoder k() throws DecoderException;

    public final boolean l() throws ExoPlaybackException, DecoderException, AudioSink.ConfigurationException, AudioSink.InitializationException, AudioSink.WriteException {
        SimpleOutputBuffer simpleOutputBuffer = this.z;
        AudioSink audioSink = this.q;
        if (simpleOutputBuffer == null) {
            SimpleOutputBuffer simpleOutputBuffer2 = (SimpleOutputBuffer) this.x.dequeueOutputBuffer();
            this.z = simpleOutputBuffer2;
            if (simpleOutputBuffer2 == null) {
                return false;
            }
            int i = simpleOutputBuffer2.g;
            if (i > 0) {
                this.s.c += i;
                audioSink.handleDiscontinuity();
            }
        }
        if (this.z.isEndOfStream()) {
            if (this.ac == 2) {
                q();
                o();
                this.ae = true;
            } else {
                this.z.release();
                this.z = null;
                try {
                    this.aj = true;
                    audioSink.playToEndOfStream();
                } catch (AudioSink.WriteException e) {
                    throw a(e, e.f, e.c);
                }
            }
            return false;
        }
        if (this.ae) {
            audioSink.configure(n().buildUpon().setEncoderDelay(this.u).setEncoderPadding(this.v).build(), 0, (int[]) null);
            this.ae = false;
        }
        SimpleOutputBuffer simpleOutputBuffer3 = this.z;
        if (!audioSink.handleBuffer(simpleOutputBuffer3.i, simpleOutputBuffer3.f, 1)) {
            return false;
        }
        this.s.b++;
        this.z.release();
        this.z = null;
        return true;
    }

    public final boolean m() throws DecoderException, ExoPlaybackException {
        T t2 = this.x;
        if (t2 == null || this.ac == 2 || this.ai) {
            return false;
        }
        if (this.y == null) {
            DecoderInputBuffer decoderInputBuffer = (DecoderInputBuffer) t2.dequeueInputBuffer();
            this.y = decoderInputBuffer;
            if (decoderInputBuffer == null) {
                return false;
            }
        }
        if (this.ac == 1) {
            this.y.setFlags(4);
            this.x.queueInputBuffer(this.y);
            this.y = null;
            this.ac = 2;
            return false;
        }
        FormatHolder formatHolder = this.f;
        formatHolder.clear();
        int j = j(formatHolder, this.y, 0);
        if (j == -5) {
            p(formatHolder);
            return true;
        } else if (j != -4) {
            if (j == -3) {
                return false;
            }
            throw new IllegalStateException();
        } else if (this.y.isEndOfStream()) {
            this.ai = true;
            this.x.queueInputBuffer(this.y);
            this.y = null;
            return false;
        } else {
            this.y.flip();
            DecoderInputBuffer decoderInputBuffer2 = this.y;
            if (this.ag && !decoderInputBuffer2.isDecodeOnly()) {
                if (Math.abs(decoderInputBuffer2.i - this.af) > 500000) {
                    this.af = decoderInputBuffer2.i;
                }
                this.ag = false;
            }
            this.x.queueInputBuffer(this.y);
            this.ad = true;
            this.s.getClass();
            this.y = null;
            return true;
        }
    }

    public abstract Format n();

    public final void o() throws ExoPlaybackException {
        if (this.x == null) {
            DrmSession drmSession = this.ab;
            s1.b(this.aa, drmSession);
            this.aa = drmSession;
            if (drmSession == null || drmSession.getMediaCrypto() != null || this.aa.getError() != null) {
                try {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    TraceUtil.beginSection("createAudioDecoder");
                    this.x = k();
                    TraceUtil.endSection();
                    long elapsedRealtime2 = SystemClock.elapsedRealtime();
                    this.p.decoderInitialized(this.x.getName(), elapsedRealtime2, elapsedRealtime2 - elapsedRealtime);
                    this.s.getClass();
                } catch (DecoderException e) {
                    Log.e("DecoderAudioRenderer", "Audio codec error", e);
                    this.p.audioCodecError(e);
                    throw a(e, this.t, false);
                } catch (OutOfMemoryError e2) {
                    throw a(e2, this.t, false);
                }
            }
        }
    }

    public final void p(FormatHolder formatHolder) throws ExoPlaybackException {
        DecoderReuseEvaluation decoderReuseEvaluation;
        Format format = (Format) Assertions.checkNotNull(formatHolder.b);
        DrmSession drmSession = formatHolder.a;
        s1.b(this.ab, drmSession);
        this.ab = drmSession;
        Format format2 = this.t;
        this.t = format;
        this.u = format.af;
        this.v = format.ag;
        T t2 = this.x;
        AudioRendererEventListener.EventDispatcher eventDispatcher = this.p;
        if (t2 == null) {
            o();
            eventDispatcher.inputFormatChanged(this.t, (DecoderReuseEvaluation) null);
            return;
        }
        if (drmSession != this.aa) {
            decoderReuseEvaluation = new DecoderReuseEvaluation(t2.getName(), format2, format, 0, 128);
        } else {
            decoderReuseEvaluation = new DecoderReuseEvaluation(t2.getName(), format2, format, 0, 1);
        }
        if (decoderReuseEvaluation.d == 0) {
            if (this.ad) {
                this.ac = 1;
            } else {
                q();
                o();
                this.ae = true;
            }
        }
        eventDispatcher.inputFormatChanged(this.t, decoderReuseEvaluation);
    }

    public final void q() {
        this.y = null;
        this.z = null;
        this.ac = 0;
        this.ad = false;
        T t2 = this.x;
        if (t2 != null) {
            this.s.getClass();
            t2.release();
            this.p.decoderReleased(this.x.getName());
            this.x = null;
        }
        s1.b(this.aa, (DrmSession) null);
        this.aa = null;
    }

    public abstract int r();

    public void render(long j, long j2) throws ExoPlaybackException {
        boolean z2 = this.aj;
        AudioSink audioSink = this.q;
        if (z2) {
            try {
                audioSink.playToEndOfStream();
            } catch (AudioSink.WriteException e) {
                throw a(e, e.f, e.c);
            }
        } else {
            if (this.t == null) {
                FormatHolder formatHolder = this.f;
                formatHolder.clear();
                DecoderInputBuffer decoderInputBuffer = this.r;
                decoderInputBuffer.clear();
                int j3 = j(formatHolder, decoderInputBuffer, 2);
                if (j3 == -5) {
                    p(formatHolder);
                } else if (j3 == -4) {
                    Assertions.checkState(decoderInputBuffer.isEndOfStream());
                    this.ai = true;
                    try {
                        this.aj = true;
                        audioSink.playToEndOfStream();
                        return;
                    } catch (AudioSink.WriteException e2) {
                        throw a(e2, (Format) null, false);
                    }
                } else {
                    return;
                }
            }
            o();
            if (this.x != null) {
                try {
                    TraceUtil.beginSection("drainAndFeed");
                    while (l()) {
                    }
                    while (m()) {
                    }
                    TraceUtil.endSection();
                    this.s.ensureUpdated();
                } catch (DecoderException e3) {
                    Log.e("DecoderAudioRenderer", "Audio codec error", e3);
                    this.p.audioCodecError(e3);
                    throw a(e3, this.t, false);
                } catch (AudioSink.ConfigurationException e4) {
                    throw a(e4, e4.c, false);
                } catch (AudioSink.InitializationException e5) {
                    throw a(e5, e5.f, e5.c);
                } catch (AudioSink.WriteException e6) {
                    throw a(e6, e6.f, e6.c);
                }
            }
        }
    }

    public final void s() {
        long currentPositionUs = this.q.getCurrentPositionUs(isEnded());
        if (currentPositionUs != Long.MIN_VALUE) {
            if (!this.ah) {
                currentPositionUs = Math.max(this.af, currentPositionUs);
            }
            this.af = currentPositionUs;
            this.ah = false;
        }
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.q.setPlaybackParameters(playbackParameters);
    }

    public /* bridge */ /* synthetic */ void setPlaybackSpeed(float f, float f2) throws ExoPlaybackException {
        ga.a(this, f, f2);
    }

    public final int supportsFormat(Format format) {
        int i = 0;
        if (!MimeTypes.isAudio(format.p)) {
            return ha.a(0);
        }
        int r2 = r();
        if (r2 <= 2) {
            return ha.a(r2);
        }
        if (Util.a >= 21) {
            i = 32;
        }
        return ha.b(r2, 8, i);
    }

    public DecoderAudioRenderer(@Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioProcessor... audioProcessorArr) {
        this(handler, audioRendererEventListener, (AudioCapabilities) null, audioProcessorArr);
    }

    public DecoderAudioRenderer(@Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, @Nullable AudioCapabilities audioCapabilities, AudioProcessor... audioProcessorArr) {
        this(handler, audioRendererEventListener, (AudioSink) new DefaultAudioSink(audioCapabilities, audioProcessorArr));
    }

    public DecoderAudioRenderer(@Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener, AudioSink audioSink) {
        super(1);
        this.p = new AudioRendererEventListener.EventDispatcher(handler, audioRendererEventListener);
        this.q = audioSink;
        audioSink.setListener(new a());
        this.r = DecoderInputBuffer.newNoDataInstance();
        this.ac = 0;
        this.ae = true;
    }
}
