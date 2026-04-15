package com.google.android.exoplayer2.video;

import android.os.SystemClock;
import android.view.Surface;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.Decoder;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TraceUtil;

public abstract class DecoderVideoRenderer extends BaseRenderer {
    public boolean aa;
    public boolean ab;
    public long ac;
    public long ad;
    public boolean ae;
    public boolean af;
    public boolean ag;
    @Nullable
    public VideoSize ah;
    public int ai;
    public DecoderCounters aj;
    public Format p;
    @Nullable
    public Decoder<VideoDecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> q;
    public VideoDecoderInputBuffer r;
    public VideoDecoderOutputBuffer s;
    public int t;
    @Nullable
    public Object u;
    @Nullable
    public VideoDecoderOutputBufferRenderer v;
    @Nullable
    public VideoFrameMetadataListener w;
    @Nullable
    public DrmSession x;
    @Nullable
    public DrmSession y;
    public int z;

    public final void c() {
        this.p = null;
        this.ah = null;
        this.ab = false;
        s1.b(this.y, (DrmSession) null);
        this.y = null;
        o();
        throw null;
    }

    public final void d(boolean z2, boolean z3) throws ExoPlaybackException {
        this.aj = new DecoderCounters();
        throw null;
    }

    public final void e(long j, boolean z2) throws ExoPlaybackException {
        this.af = false;
        this.ag = false;
        this.ab = false;
        this.ac = -9223372036854775807L;
        if (this.q != null) {
            if (this.z != 0) {
                o();
                n();
            } else {
                this.r = null;
                VideoDecoderOutputBuffer videoDecoderOutputBuffer = this.s;
                if (videoDecoderOutputBuffer != null) {
                    videoDecoderOutputBuffer.release();
                    this.s = null;
                }
                this.q.flush();
                this.aa = false;
            }
        }
        if (z2) {
            this.ad = -9223372036854775807L;
        } else {
            this.ad = -9223372036854775807L;
        }
        throw null;
    }

    public final void g() {
        this.ai = 0;
        SystemClock.elapsedRealtime();
        SystemClock.elapsedRealtime();
    }

    public abstract /* synthetic */ String getName();

    public final void h() {
        this.ad = -9223372036854775807L;
        if (this.ai > 0) {
            SystemClock.elapsedRealtime();
            throw null;
        }
    }

    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        if (i == 1) {
            if (obj instanceof Surface) {
                Surface surface = (Surface) obj;
                this.v = null;
                this.t = 1;
            } else if (obj instanceof VideoDecoderOutputBufferRenderer) {
                this.v = (VideoDecoderOutputBufferRenderer) obj;
                this.t = 0;
            } else {
                this.v = null;
                this.t = -1;
                obj = null;
            }
            if (this.u != obj) {
                this.u = obj;
                if (obj != null) {
                    if (this.q != null) {
                        p();
                    }
                    if (this.ah == null) {
                        this.ab = false;
                        if (getState() == 2) {
                            this.ad = -9223372036854775807L;
                            return;
                        }
                        return;
                    }
                    throw null;
                }
                this.ah = null;
                this.ab = false;
            } else if (obj == null) {
            } else {
                if (this.ah != null) {
                    throw null;
                } else if (this.ab) {
                    throw null;
                }
            }
        } else if (i == 6) {
            this.w = (VideoFrameMetadataListener) obj;
        } else {
            super.handleMessage(i, obj);
        }
    }

    public final void i(Format[] formatArr, long j, long j2) throws ExoPlaybackException {
    }

    public boolean isEnded() {
        return this.ag;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
        if (r0 == false) goto L_0x0023;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isReady() {
        /*
            r9 = this;
            com.google.android.exoplayer2.Format r0 = r9.p
            r1 = 0
            r2 = 1
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r0 == 0) goto L_0x0026
            boolean r0 = r9.b()
            if (r0 != 0) goto L_0x0015
            com.google.android.exoplayer2.video.VideoDecoderOutputBuffer r0 = r9.s
            if (r0 == 0) goto L_0x0026
        L_0x0015:
            boolean r0 = r9.ab
            if (r0 != 0) goto L_0x0023
            int r0 = r9.t
            r5 = -1
            if (r0 == r5) goto L_0x0020
            r0 = 1
            goto L_0x0021
        L_0x0020:
            r0 = 0
        L_0x0021:
            if (r0 != 0) goto L_0x0026
        L_0x0023:
            r9.ad = r3
            return r2
        L_0x0026:
            long r5 = r9.ad
            int r0 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x002d
            return r1
        L_0x002d:
            long r5 = android.os.SystemClock.elapsedRealtime()
            long r7 = r9.ad
            int r0 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r0 >= 0) goto L_0x0038
            return r2
        L_0x0038:
            r9.ad = r3
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.DecoderVideoRenderer.isReady():boolean");
    }

    public abstract Decoder k() throws DecoderException;

    public final boolean l(long j, long j2) throws ExoPlaybackException, DecoderException {
        boolean z2;
        boolean z3;
        boolean z4 = false;
        if (this.s == null) {
            VideoDecoderOutputBuffer videoDecoderOutputBuffer = (VideoDecoderOutputBuffer) this.q.dequeueOutputBuffer();
            this.s = videoDecoderOutputBuffer;
            if (videoDecoderOutputBuffer == null) {
                return false;
            }
            this.aj.c += videoDecoderOutputBuffer.g;
        }
        if (this.s.isEndOfStream()) {
            if (this.z == 2) {
                o();
                n();
            } else {
                this.s.release();
                this.s = null;
                this.ag = true;
            }
            return false;
        }
        if (this.ac == -9223372036854775807L) {
            this.ac = j;
        }
        VideoDecoderOutputBuffer videoDecoderOutputBuffer2 = this.s;
        long j3 = videoDecoderOutputBuffer2.f - j;
        if (this.t != -1) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2) {
            if (j3 < -30000) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z3) {
                this.aj.c++;
                videoDecoderOutputBuffer2.release();
                z4 = true;
            }
            if (z4) {
                long j4 = this.s.f;
                this.s = null;
            }
            return z4;
        }
        throw null;
    }

    public final boolean m() throws DecoderException, ExoPlaybackException {
        DecoderReuseEvaluation decoderReuseEvaluation;
        Decoder<VideoDecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder = this.q;
        if (decoder == null || this.z == 2 || this.af) {
            return false;
        }
        if (this.r == null) {
            VideoDecoderInputBuffer dequeueInputBuffer = decoder.dequeueInputBuffer();
            this.r = dequeueInputBuffer;
            if (dequeueInputBuffer == null) {
                return false;
            }
        }
        if (this.z == 1) {
            this.r.setFlags(4);
            this.q.queueInputBuffer(this.r);
            this.r = null;
            this.z = 2;
            return false;
        }
        FormatHolder formatHolder = this.f;
        formatHolder.clear();
        int j = j(formatHolder, this.r, 0);
        if (j == -5) {
            this.ae = true;
            Format format = (Format) Assertions.checkNotNull(formatHolder.b);
            DrmSession drmSession = formatHolder.a;
            s1.b(this.y, drmSession);
            this.y = drmSession;
            Format format2 = this.p;
            this.p = format;
            Decoder<VideoDecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder2 = this.q;
            if (decoder2 != null) {
                if (drmSession != this.x) {
                    decoderReuseEvaluation = new DecoderReuseEvaluation(decoder2.getName(), format2, format, 0, 128);
                } else {
                    decoderReuseEvaluation = new DecoderReuseEvaluation(decoder2.getName(), format2, format, 0, 1);
                }
                if (decoderReuseEvaluation.d == 0) {
                    if (this.aa) {
                        this.z = 1;
                    } else {
                        o();
                        n();
                    }
                }
                throw null;
            }
            n();
            throw null;
        } else if (j != -4) {
            if (j == -3) {
                return false;
            }
            throw new IllegalStateException();
        } else if (this.r.isEndOfStream()) {
            this.af = true;
            this.q.queueInputBuffer(this.r);
            this.r = null;
            return false;
        } else if (!this.ae) {
            this.r.flip();
            this.r.getClass();
            this.q.queueInputBuffer(this.r);
            this.aa = true;
            this.aj.getClass();
            this.r = null;
            return true;
        } else {
            long j2 = this.r.i;
            throw null;
        }
    }

    public final void n() throws ExoPlaybackException {
        if (this.q == null) {
            DrmSession drmSession = this.y;
            s1.b(this.x, drmSession);
            this.x = drmSession;
            if (drmSession == null || drmSession.getMediaCrypto() != null || this.x.getError() != null) {
                try {
                    SystemClock.elapsedRealtime();
                    this.q = k();
                    p();
                    SystemClock.elapsedRealtime();
                    this.q.getName();
                    throw null;
                } catch (DecoderException e) {
                    Log.e("DecoderVideoRenderer", "Video codec error", e);
                    throw null;
                } catch (OutOfMemoryError e2) {
                    throw a(e2, this.p, false);
                }
            }
        }
    }

    @CallSuper
    public final void o() {
        this.r = null;
        this.s = null;
        this.z = 0;
        this.aa = false;
        Decoder<VideoDecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder = this.q;
        if (decoder == null) {
            s1.b(this.x, (DrmSession) null);
            this.x = null;
            return;
        }
        this.aj.getClass();
        decoder.release();
        this.q.getName();
        throw null;
    }

    public abstract void p();

    public void render(long j, long j2) throws ExoPlaybackException {
        if (!this.ag) {
            if (this.p != null) {
                n();
                if (this.q != null) {
                    try {
                        TraceUtil.beginSection("drainAndFeed");
                        while (l(j, j2)) {
                        }
                        while (m()) {
                        }
                        TraceUtil.endSection();
                        this.aj.ensureUpdated();
                    } catch (DecoderException e) {
                        Log.e("DecoderVideoRenderer", "Video codec error", e);
                        throw null;
                    }
                }
            } else {
                this.f.clear();
                throw null;
            }
        }
    }

    public /* bridge */ /* synthetic */ void setPlaybackSpeed(float f, float f2) throws ExoPlaybackException {
        ga.a(this, f, f2);
    }

    public abstract /* synthetic */ int supportsFormat(Format format) throws ExoPlaybackException;
}
