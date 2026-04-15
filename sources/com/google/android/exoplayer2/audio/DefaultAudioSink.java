package com.google.android.exoplayer2.audio;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.PlaybackParams;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.audio.AudioTrackPositionTracker;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import j$.util.Objects;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import org.mozilla.javascript.Context;

public final class DefaultAudioSink implements AudioSink {
    @Nullable
    public final AudioCapabilities a;
    public long aa;
    public long ab;
    public long ac;
    public int ad;
    public boolean ae;
    public boolean af;
    public long ag;
    public float ah;
    public AudioProcessor[] ai;
    public ByteBuffer[] aj;
    @Nullable
    public ByteBuffer ak;
    public int al;
    @Nullable
    public ByteBuffer am;
    public byte[] an;
    public int ao;
    public int ap;
    public boolean aq;
    public boolean ar;
    public boolean as;
    public boolean at;
    public int au;
    public AuxEffectInfo av;
    public boolean aw;
    public long ax;
    public boolean ay;
    public boolean az;
    public final AudioProcessorChain b;
    public final boolean c;
    public final a d;
    public final d e;
    public final AudioProcessor[] f;
    public final AudioProcessor[] g;
    public final ConditionVariable h;
    public final AudioTrackPositionTracker i;
    public final ArrayDeque<c> j;
    public final boolean k;
    public final int l;
    public f m;
    public final d<AudioSink.InitializationException> n;
    public final d<AudioSink.WriteException> o;
    @Nullable
    public AudioSink.Listener p;
    @Nullable
    public b q;
    public b r;
    @Nullable
    public AudioTrack s;
    public AudioAttributes t;
    @Nullable
    public c u;
    public c v;
    public PlaybackParameters w;
    @Nullable
    public ByteBuffer x;
    public int y;
    public long z;

    public interface AudioProcessorChain {
        PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters);

        boolean applySkipSilenceEnabled(boolean z);

        AudioProcessor[] getAudioProcessors();

        long getMediaDuration(long j);

        long getSkippedOutputFrameCount();
    }

    public static class DefaultAudioProcessorChain implements AudioProcessorChain {
        public final AudioProcessor[] a;
        public final SilenceSkippingAudioProcessor b;
        public final SonicAudioProcessor c;

        public DefaultAudioProcessorChain(AudioProcessor... audioProcessorArr) {
            this(audioProcessorArr, new SilenceSkippingAudioProcessor(), new SonicAudioProcessor());
        }

        public PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters) {
            float f = playbackParameters.c;
            SonicAudioProcessor sonicAudioProcessor = this.c;
            sonicAudioProcessor.setSpeed(f);
            sonicAudioProcessor.setPitch(playbackParameters.f);
            return playbackParameters;
        }

        public boolean applySkipSilenceEnabled(boolean z) {
            this.b.setEnabled(z);
            return z;
        }

        public AudioProcessor[] getAudioProcessors() {
            return this.a;
        }

        public long getMediaDuration(long j) {
            return this.c.getMediaDuration(j);
        }

        public long getSkippedOutputFrameCount() {
            return this.b.getSkippedFrames();
        }

        public DefaultAudioProcessorChain(AudioProcessor[] audioProcessorArr, SilenceSkippingAudioProcessor silenceSkippingAudioProcessor, SonicAudioProcessor sonicAudioProcessor) {
            AudioProcessor[] audioProcessorArr2 = new AudioProcessor[(audioProcessorArr.length + 2)];
            this.a = audioProcessorArr2;
            System.arraycopy(audioProcessorArr, 0, audioProcessorArr2, 0, audioProcessorArr.length);
            this.b = silenceSkippingAudioProcessor;
            this.c = sonicAudioProcessor;
            audioProcessorArr2[audioProcessorArr.length] = silenceSkippingAudioProcessor;
            audioProcessorArr2[audioProcessorArr.length + 1] = sonicAudioProcessor;
        }
    }

    public static final class InvalidAudioTrackTimestampException extends RuntimeException {
        public InvalidAudioTrackTimestampException() {
            throw null;
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface OffloadMode {
    }

    public class a extends Thread {
        public final /* synthetic */ AudioTrack c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(AudioTrack audioTrack) {
            super("ExoPlayer:AudioTrackReleaseThread");
            this.c = audioTrack;
        }

        public void run() {
            DefaultAudioSink defaultAudioSink = DefaultAudioSink.this;
            AudioTrack audioTrack = this.c;
            try {
                audioTrack.flush();
                audioTrack.release();
            } finally {
                defaultAudioSink.h.open();
            }
        }
    }

    public static final class b {
        public final Format a;
        public final int b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;
        public final int g;
        public final int h;
        public final AudioProcessor[] i;

        public b(Format format, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z, AudioProcessor[] audioProcessorArr) {
            float f2;
            this.a = format;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
            this.f = i6;
            this.g = i7;
            this.i = audioProcessorArr;
            if (i8 == 0) {
                boolean z2 = true;
                if (i3 == 0) {
                    if (z) {
                        f2 = 8.0f;
                    } else {
                        f2 = 1.0f;
                    }
                    int minBufferSize = AudioTrack.getMinBufferSize(i5, i6, i7);
                    Assertions.checkState(minBufferSize == -2 ? false : z2);
                    int constrainValue = Util.constrainValue(minBufferSize * 4, ((int) durationUsToFrames(250000)) * i4, Math.max(minBufferSize, ((int) durationUsToFrames(750000)) * i4));
                    i8 = f2 != 1.0f ? Math.round(((float) constrainValue) * f2) : constrainValue;
                } else if (i3 == 1) {
                    i8 = c(50000000);
                } else if (i3 == 2) {
                    i8 = c(250000);
                } else {
                    throw new IllegalStateException();
                }
            }
            this.h = i8;
        }

        @RequiresApi(21)
        public static AudioAttributes b(AudioAttributes audioAttributes, boolean z) {
            if (z) {
                return new AudioAttributes.Builder().setContentType(3).setFlags(16).setUsage(1).build();
            }
            return audioAttributes.getAudioAttributesV21();
        }

        public final AudioTrack a(boolean z, AudioAttributes audioAttributes, int i2) {
            int i3 = Util.a;
            int i4 = this.g;
            int i5 = this.f;
            int i6 = this.e;
            if (i3 >= 29) {
                AudioFormat c2 = DefaultAudioSink.c(i6, i5, i4);
                AudioAttributes b2 = b(audioAttributes, z);
                boolean z2 = true;
                AudioTrack.Builder sessionId = new AudioTrack.Builder().setAudioAttributes(b2).setAudioFormat(c2).setTransferMode(1).setBufferSizeInBytes(this.h).setSessionId(i2);
                if (this.c != 1) {
                    z2 = false;
                }
                return sessionId.setOffloadedPlayback(z2).build();
            } else if (i3 >= 21) {
                return new AudioTrack(b(audioAttributes, z), DefaultAudioSink.c(i6, i5, i4), this.h, 1, i2);
            } else {
                int streamTypeForAudioUsage = Util.getStreamTypeForAudioUsage(audioAttributes.g);
                if (i2 == 0) {
                    return new AudioTrack(streamTypeForAudioUsage, this.e, this.f, this.g, this.h, 1);
                }
                return new AudioTrack(streamTypeForAudioUsage, this.e, this.f, this.g, this.h, 1, i2);
            }
        }

        public AudioTrack buildAudioTrack(boolean z, AudioAttributes audioAttributes, int i2) throws AudioSink.InitializationException {
            try {
                AudioTrack a2 = a(z, audioAttributes, i2);
                int state = a2.getState();
                if (state == 1) {
                    return a2;
                }
                try {
                    a2.release();
                } catch (Exception unused) {
                }
                throw new AudioSink.InitializationException(state, this.e, this.f, this.h, this.a, outputModeIsOffload(), (Exception) null);
            } catch (IllegalArgumentException | UnsupportedOperationException e2) {
                throw new AudioSink.InitializationException(0, this.e, this.f, this.h, this.a, outputModeIsOffload(), e2);
            }
        }

        public final int c(long j) {
            int i2;
            int i3 = this.g;
            switch (i3) {
                case 5:
                    i2 = 80000;
                    break;
                case 6:
                case 18:
                    i2 = 768000;
                    break;
                case 7:
                    i2 = 192000;
                    break;
                case 8:
                    i2 = 2250000;
                    break;
                case 9:
                    i2 = 40000;
                    break;
                case 10:
                    i2 = 100000;
                    break;
                case 11:
                    i2 = 16000;
                    break;
                case 12:
                    i2 = 7000;
                    break;
                case 14:
                    i2 = 3062500;
                    break;
                case 15:
                    i2 = 8000;
                    break;
                case 16:
                    i2 = 256000;
                    break;
                case 17:
                    i2 = 336000;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            if (i3 == 5) {
                i2 *= 2;
            }
            return (int) ((j * ((long) i2)) / 1000000);
        }

        public boolean canReuseAudioTrack(b bVar) {
            return bVar.c == this.c && bVar.g == this.g && bVar.e == this.e && bVar.f == this.f && bVar.d == this.d;
        }

        public long durationUsToFrames(long j) {
            return (j * ((long) this.e)) / 1000000;
        }

        public long framesToDurationUs(long j) {
            return (j * 1000000) / ((long) this.e);
        }

        public long inputFramesToDurationUs(long j) {
            return (j * 1000000) / ((long) this.a.ad);
        }

        public boolean outputModeIsOffload() {
            return this.c == 1;
        }
    }

    public static final class c {
        public final PlaybackParameters a;
        public final boolean b;
        public final long c;
        public final long d;

        public c(PlaybackParameters playbackParameters, boolean z, long j, long j2) {
            this.a = playbackParameters;
            this.b = z;
            this.c = j;
            this.d = j2;
        }
    }

    public static final class d<T extends Exception> {
        public final long a;
        @Nullable
        public T b;
        public long c;

        public d(long j) {
            this.a = j;
        }

        public void clear() {
            this.b = null;
        }

        public void throwExceptionIfDeadlineIsReached(T t) throws Exception {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (this.b == null) {
                this.b = t;
                this.c = this.a + elapsedRealtime;
            }
            if (elapsedRealtime >= this.c) {
                T t2 = this.b;
                if (t2 != t) {
                    t2.addSuppressed(t);
                }
                T t3 = this.b;
                clear();
                throw t3;
            }
        }
    }

    public final class e implements AudioTrackPositionTracker.Listener {
        public e() {
        }

        public void onInvalidLatency(long j) {
            StringBuilder sb = new StringBuilder(61);
            sb.append("Ignoring impossibly large audio latency: ");
            sb.append(j);
            Log.w("DefaultAudioSink", sb.toString());
        }

        public void onPositionAdvancing(long j) {
            AudioSink.Listener listener = DefaultAudioSink.this.p;
            if (listener != null) {
                listener.onPositionAdvancing(j);
            }
        }

        public void onPositionFramesMismatch(long j, long j2, long j3, long j4) {
            DefaultAudioSink defaultAudioSink = DefaultAudioSink.this;
            long f = defaultAudioSink.f();
            long g = defaultAudioSink.g();
            StringBuilder sb = new StringBuilder(182);
            sb.append("Spurious audio timestamp (frame position mismatch): ");
            sb.append(j);
            sb.append(", ");
            sb.append(j2);
            sb.append(", ");
            sb.append(j3);
            sb.append(", ");
            sb.append(j4);
            sb.append(", ");
            sb.append(f);
            sb.append(", ");
            sb.append(g);
            Log.w("DefaultAudioSink", sb.toString());
        }

        public void onSystemTimeUsMismatch(long j, long j2, long j3, long j4) {
            DefaultAudioSink defaultAudioSink = DefaultAudioSink.this;
            long f = defaultAudioSink.f();
            long g = defaultAudioSink.g();
            StringBuilder sb = new StringBuilder(Context.VERSION_1_8);
            sb.append("Spurious audio timestamp (system clock mismatch): ");
            sb.append(j);
            sb.append(", ");
            sb.append(j2);
            sb.append(", ");
            sb.append(j3);
            sb.append(", ");
            sb.append(j4);
            sb.append(", ");
            sb.append(f);
            sb.append(", ");
            sb.append(g);
            Log.w("DefaultAudioSink", sb.toString());
        }

        public void onUnderrun(int i, long j) {
            DefaultAudioSink defaultAudioSink = DefaultAudioSink.this;
            if (defaultAudioSink.p != null) {
                defaultAudioSink.p.onUnderrun(i, j, SystemClock.elapsedRealtime() - defaultAudioSink.ax);
            }
        }
    }

    @RequiresApi(29)
    public final class f {
        public final Handler a = new Handler();
        public final a b = new a();

        public class a extends AudioTrack.StreamEventCallback {
            public a() {
            }

            public void onDataRequest(AudioTrack audioTrack, int i) {
                boolean z;
                if (audioTrack == DefaultAudioSink.this.s) {
                    z = true;
                } else {
                    z = false;
                }
                Assertions.checkState(z);
                DefaultAudioSink defaultAudioSink = DefaultAudioSink.this;
                AudioSink.Listener listener = defaultAudioSink.p;
                if (listener != null && defaultAudioSink.as) {
                    listener.onOffloadBufferEmptying();
                }
            }

            public void onTearDown(@NonNull AudioTrack audioTrack) {
                boolean z;
                if (audioTrack == DefaultAudioSink.this.s) {
                    z = true;
                } else {
                    z = false;
                }
                Assertions.checkState(z);
                DefaultAudioSink defaultAudioSink = DefaultAudioSink.this;
                AudioSink.Listener listener = defaultAudioSink.p;
                if (listener != null && defaultAudioSink.as) {
                    listener.onOffloadBufferEmptying();
                }
            }
        }

        public f() {
        }

        public void register(AudioTrack audioTrack) {
            Handler handler = this.a;
            Objects.requireNonNull(handler);
            audioTrack.registerStreamEventCallback(new z0(handler), this.b);
        }

        public void unregister(AudioTrack audioTrack) {
            audioTrack.unregisterStreamEventCallback(this.b);
            this.a.removeCallbacksAndMessages((Object) null);
        }
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities, AudioProcessor[] audioProcessorArr) {
        this(audioCapabilities, audioProcessorArr, false);
    }

    @RequiresApi(21)
    public static AudioFormat c(int i2, int i3, int i4) {
        return new AudioFormat.Builder().setSampleRate(i2).setChannelMask(i3).setEncoding(i4).build();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00b7, code lost:
        if (r2 != 5) goto L_0x00ba;
     */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00d2 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00d3  */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.util.Pair<java.lang.Integer, java.lang.Integer> d(com.google.android.exoplayer2.Format r13, @androidx.annotation.Nullable com.google.android.exoplayer2.audio.AudioCapabilities r14) {
        /*
            r0 = 0
            if (r14 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.String r1 = r13.p
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r2 = r13.m
            int r1 = com.google.android.exoplayer2.util.MimeTypes.getEncoding(r1, r2)
            r2 = 0
            r3 = 5
            r4 = 7
            r5 = 1
            r6 = 6
            r7 = 8
            r8 = 18
            if (r1 == r3) goto L_0x0030
            if (r1 == r6) goto L_0x0030
            if (r1 == r8) goto L_0x0030
            r9 = 17
            if (r1 == r9) goto L_0x0030
            if (r1 == r4) goto L_0x0030
            if (r1 == r7) goto L_0x0030
            r9 = 14
            if (r1 != r9) goto L_0x002e
            goto L_0x0030
        L_0x002e:
            r9 = 0
            goto L_0x0031
        L_0x0030:
            r9 = 1
        L_0x0031:
            if (r9 != 0) goto L_0x0034
            return r0
        L_0x0034:
            if (r1 != r8) goto L_0x003e
            boolean r9 = r14.supportsEncoding(r8)
            if (r9 != 0) goto L_0x003e
            r1 = 6
            goto L_0x0047
        L_0x003e:
            if (r1 != r7) goto L_0x0047
            boolean r9 = r14.supportsEncoding(r7)
            if (r9 != 0) goto L_0x0047
            r1 = 7
        L_0x0047:
            boolean r9 = r14.supportsEncoding(r1)
            if (r9 != 0) goto L_0x004e
            return r0
        L_0x004e:
            r9 = 3
            if (r1 != r8) goto L_0x009e
            int r14 = com.google.android.exoplayer2.util.Util.a
            r10 = 29
            if (r14 < r10) goto L_0x009c
            android.media.AudioAttributes$Builder r14 = new android.media.AudioAttributes$Builder
            r14.<init>()
            android.media.AudioAttributes$Builder r14 = r14.setUsage(r5)
            android.media.AudioAttributes$Builder r14 = r14.setContentType(r9)
            android.media.AudioAttributes r14 = r14.build()
            r10 = 8
        L_0x006a:
            if (r10 <= 0) goto L_0x0092
            android.media.AudioFormat$Builder r11 = new android.media.AudioFormat$Builder
            r11.<init>()
            android.media.AudioFormat$Builder r11 = r11.setEncoding(r8)
            int r12 = r13.ad
            android.media.AudioFormat$Builder r11 = r11.setSampleRate(r12)
            int r12 = com.google.android.exoplayer2.util.Util.getAudioTrackChannelConfig(r10)
            android.media.AudioFormat$Builder r11 = r11.setChannelMask(r12)
            android.media.AudioFormat r11 = r11.build()
            boolean r11 = android.media.AudioTrack.isDirectPlaybackSupported(r11, r14)
            if (r11 == 0) goto L_0x008f
            r2 = r10
            goto L_0x0092
        L_0x008f:
            int r10 = r10 + -1
            goto L_0x006a
        L_0x0092:
            if (r2 != 0) goto L_0x00a7
            java.lang.String r13 = "DefaultAudioSink"
            java.lang.String r14 = "E-AC3 JOC encoding supported but no channel count supported"
            com.google.android.exoplayer2.util.Log.w(r13, r14)
            return r0
        L_0x009c:
            r2 = 6
            goto L_0x00a7
        L_0x009e:
            int r14 = r14.getMaxChannelCount()
            int r2 = r13.ac
            if (r2 <= r14) goto L_0x00a7
            return r0
        L_0x00a7:
            int r13 = com.google.android.exoplayer2.util.Util.a
            r14 = 28
            if (r13 > r14) goto L_0x00ba
            if (r2 != r4) goto L_0x00b2
            r6 = 8
            goto L_0x00bb
        L_0x00b2:
            if (r2 == r9) goto L_0x00bb
            r14 = 4
            if (r2 == r14) goto L_0x00bb
            if (r2 != r3) goto L_0x00ba
            goto L_0x00bb
        L_0x00ba:
            r6 = r2
        L_0x00bb:
            r14 = 26
            if (r13 > r14) goto L_0x00cc
            java.lang.String r13 = "fugu"
            java.lang.String r14 = com.google.android.exoplayer2.util.Util.b
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x00cc
            if (r6 != r5) goto L_0x00cc
            r6 = 2
        L_0x00cc:
            int r13 = com.google.android.exoplayer2.util.Util.getAudioTrackChannelConfig(r6)
            if (r13 != 0) goto L_0x00d3
            return r0
        L_0x00d3:
            java.lang.Integer r14 = java.lang.Integer.valueOf(r1)
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)
            android.util.Pair r13 = android.util.Pair.create(r14, r13)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.DefaultAudioSink.d(com.google.android.exoplayer2.Format, com.google.android.exoplayer2.audio.AudioCapabilities):android.util.Pair");
    }

    public static boolean j(AudioTrack audioTrack) {
        return Util.a >= 29 && audioTrack.isOffloadedPlayback();
    }

    public final void a(long j2) {
        PlaybackParameters playbackParameters;
        boolean z2;
        boolean p2 = p();
        AudioProcessorChain audioProcessorChain = this.b;
        if (p2) {
            playbackParameters = audioProcessorChain.applyPlaybackParameters(e().a);
        } else {
            playbackParameters = PlaybackParameters.h;
        }
        PlaybackParameters playbackParameters2 = playbackParameters;
        int i2 = 0;
        if (p()) {
            z2 = audioProcessorChain.applySkipSilenceEnabled(getSkipSilenceEnabled());
        } else {
            z2 = false;
        }
        this.j.add(new c(playbackParameters2, z2, Math.max(0, j2), this.r.framesToDurationUs(g())));
        AudioProcessor[] audioProcessorArr = this.r.i;
        ArrayList arrayList = new ArrayList();
        for (AudioProcessor audioProcessor : audioProcessorArr) {
            if (audioProcessor.isActive()) {
                arrayList.add(audioProcessor);
            } else {
                audioProcessor.flush();
            }
        }
        int size = arrayList.size();
        this.ai = (AudioProcessor[]) arrayList.toArray(new AudioProcessor[size]);
        this.aj = new ByteBuffer[size];
        while (true) {
            AudioProcessor[] audioProcessorArr2 = this.ai;
            if (i2 >= audioProcessorArr2.length) {
                break;
            }
            AudioProcessor audioProcessor2 = audioProcessorArr2[i2];
            audioProcessor2.flush();
            this.aj[i2] = audioProcessor2.getOutput();
            i2++;
        }
        AudioSink.Listener listener = this.p;
        if (listener != null) {
            listener.onSkipSilenceEnabledChanged(z2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0018  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean b() throws com.google.android.exoplayer2.audio.AudioSink.WriteException {
        /*
            r9 = this;
            int r0 = r9.ap
            r1 = 1
            r2 = 0
            r3 = -1
            if (r0 != r3) goto L_0x000b
            r9.ap = r2
        L_0x0009:
            r0 = 1
            goto L_0x000c
        L_0x000b:
            r0 = 0
        L_0x000c:
            int r4 = r9.ap
            com.google.android.exoplayer2.audio.AudioProcessor[] r5 = r9.ai
            int r6 = r5.length
            r7 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r4 >= r6) goto L_0x002f
            r4 = r5[r4]
            if (r0 == 0) goto L_0x001f
            r4.queueEndOfStream()
        L_0x001f:
            r9.k(r7)
            boolean r0 = r4.isEnded()
            if (r0 != 0) goto L_0x0029
            return r2
        L_0x0029:
            int r0 = r9.ap
            int r0 = r0 + r1
            r9.ap = r0
            goto L_0x0009
        L_0x002f:
            java.nio.ByteBuffer r0 = r9.am
            if (r0 == 0) goto L_0x003b
            r9.r(r0, r7)
            java.nio.ByteBuffer r0 = r9.am
            if (r0 == 0) goto L_0x003b
            return r2
        L_0x003b:
            r9.ap = r3
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.DefaultAudioSink.b():boolean");
    }

    public void configure(Format format, int i2, @Nullable int[] iArr) throws AudioSink.ConfigurationException {
        AudioProcessor[] audioProcessorArr;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        AudioProcessor[] audioProcessorArr2;
        int[] iArr2;
        Format format2 = format;
        boolean equals = "audio/raw".equals(format2.p);
        int i11 = 1;
        int i12 = format2.ad;
        int i13 = format2.ac;
        if (equals) {
            int i14 = format2.ae;
            Assertions.checkArgument(Util.isEncodingLinearPcm(i14));
            i5 = Util.getPcmFrameSize(i14, i13);
            if (!this.c || !Util.isEncodingHighResolutionPcm(i14)) {
                i11 = 0;
            }
            if (i11 != 0) {
                audioProcessorArr2 = this.g;
            } else {
                audioProcessorArr2 = this.f;
            }
            this.e.setTrimFrameCount(format2.af, format2.ag);
            if (Util.a < 21 && i13 == 8 && iArr == null) {
                iArr2 = new int[6];
                for (int i15 = 0; i15 < 6; i15++) {
                    iArr2[i15] = i15;
                }
            } else {
                iArr2 = iArr;
            }
            this.d.setChannelMap(iArr2);
            AudioProcessor.AudioFormat audioFormat = new AudioProcessor.AudioFormat(i12, i13, i14);
            int length = audioProcessorArr2.length;
            int i16 = 0;
            while (i16 < length) {
                AudioProcessor audioProcessor = audioProcessorArr2[i16];
                try {
                    AudioProcessor.AudioFormat configure = audioProcessor.configure(audioFormat);
                    if (audioProcessor.isActive()) {
                        audioFormat = configure;
                    }
                    i16++;
                } catch (AudioProcessor.UnhandledAudioFormatException e2) {
                    throw new AudioSink.ConfigurationException((Throwable) e2, format);
                }
            }
            int i17 = audioFormat.c;
            int i18 = audioFormat.b;
            int audioTrackChannelConfig = Util.getAudioTrackChannelConfig(i18);
            int pcmFrameSize = Util.getPcmFrameSize(i17, i18);
            i4 = audioFormat.a;
            i3 = i17;
            audioProcessorArr = audioProcessorArr2;
            i8 = audioTrackChannelConfig;
            i6 = pcmFrameSize;
            i7 = 0;
        } else {
            AudioProcessor[] audioProcessorArr3 = new AudioProcessor[0];
            if (q(format, this.t)) {
                i9 = MimeTypes.getEncoding((String) Assertions.checkNotNull(format2.p), format2.m);
                i10 = Util.getAudioTrackChannelConfig(i13);
            } else {
                Pair<Integer, Integer> d2 = d(format, this.a);
                if (d2 != null) {
                    i9 = ((Integer) d2.first).intValue();
                    i10 = ((Integer) d2.second).intValue();
                    i11 = 2;
                } else {
                    String valueOf = String.valueOf(format);
                    StringBuilder sb = new StringBuilder(valueOf.length() + 37);
                    sb.append("Unable to configure passthrough for: ");
                    sb.append(valueOf);
                    throw new AudioSink.ConfigurationException(sb.toString(), format);
                }
            }
            audioProcessorArr = audioProcessorArr3;
            i4 = i12;
            i8 = i10;
            i3 = i9;
            i6 = -1;
            i5 = -1;
            i7 = i11;
        }
        if (i3 == 0) {
            String valueOf2 = String.valueOf(format);
            StringBuilder sb2 = new StringBuilder(valueOf2.length() + 48);
            sb2.append("Invalid output encoding (mode=");
            sb2.append(i7);
            sb2.append(") for: ");
            sb2.append(valueOf2);
            throw new AudioSink.ConfigurationException(sb2.toString(), format);
        } else if (i8 != 0) {
            this.ay = false;
            b bVar = new b(format, i5, i7, i6, i4, i8, i3, i2, this.k, audioProcessorArr);
            if (i()) {
                this.q = bVar;
            } else {
                this.r = bVar;
            }
        } else {
            String valueOf3 = String.valueOf(format);
            StringBuilder sb3 = new StringBuilder(valueOf3.length() + 54);
            sb3.append("Invalid output channel config (mode=");
            sb3.append(i7);
            sb3.append(") for: ");
            sb3.append(valueOf3);
            throw new AudioSink.ConfigurationException(sb3.toString(), format);
        }
    }

    public void disableTunneling() {
        if (this.aw) {
            this.aw = false;
            flush();
        }
    }

    public final c e() {
        c cVar = this.u;
        if (cVar != null) {
            return cVar;
        }
        ArrayDeque<c> arrayDeque = this.j;
        if (!arrayDeque.isEmpty()) {
            return arrayDeque.getLast();
        }
        return this.v;
    }

    public void enableTunnelingV21() {
        boolean z2;
        if (Util.a >= 21) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkState(z2);
        Assertions.checkState(this.at);
        if (!this.aw) {
            this.aw = true;
            flush();
        }
    }

    public void experimentalFlushWithoutAudioTrackRelease() {
        boolean z2;
        if (Util.a < 25) {
            flush();
            return;
        }
        this.o.clear();
        this.n.clear();
        if (i()) {
            l();
            AudioTrackPositionTracker audioTrackPositionTracker = this.i;
            if (audioTrackPositionTracker.isPlaying()) {
                this.s.pause();
            }
            this.s.flush();
            audioTrackPositionTracker.reset();
            AudioTrackPositionTracker audioTrackPositionTracker2 = this.i;
            AudioTrack audioTrack = this.s;
            b bVar = this.r;
            if (bVar.c == 2) {
                z2 = true;
            } else {
                z2 = false;
            }
            audioTrackPositionTracker2.setAudioTrack(audioTrack, z2, bVar.g, bVar.d, bVar.h);
            this.af = true;
        }
    }

    public final long f() {
        b bVar = this.r;
        if (bVar.c == 0) {
            return this.z / ((long) bVar.b);
        }
        return this.aa;
    }

    public void flush() {
        if (i()) {
            l();
            AudioTrackPositionTracker audioTrackPositionTracker = this.i;
            if (audioTrackPositionTracker.isPlaying()) {
                this.s.pause();
            }
            if (j(this.s)) {
                ((f) Assertions.checkNotNull(this.m)).unregister(this.s);
            }
            AudioTrack audioTrack = this.s;
            this.s = null;
            if (Util.a < 21 && !this.at) {
                this.au = 0;
            }
            b bVar = this.q;
            if (bVar != null) {
                this.r = bVar;
                this.q = null;
            }
            audioTrackPositionTracker.reset();
            this.h.close();
            new a(audioTrack).start();
        }
        this.o.clear();
        this.n.clear();
    }

    public final long g() {
        b bVar = this.r;
        if (bVar.c == 0) {
            return this.ab / ((long) bVar.d);
        }
        return this.ac;
    }

    public long getCurrentPositionUs(boolean z2) {
        ArrayDeque<c> arrayDeque;
        long j2;
        if (!i() || this.af) {
            return Long.MIN_VALUE;
        }
        long min = Math.min(this.i.getCurrentPositionUs(z2), this.r.framesToDurationUs(g()));
        while (true) {
            arrayDeque = this.j;
            if (arrayDeque.isEmpty() || min < arrayDeque.getFirst().d) {
                c cVar = this.v;
                long j3 = min - cVar.d;
                boolean equals = cVar.a.equals(PlaybackParameters.h);
                AudioProcessorChain audioProcessorChain = this.b;
            } else {
                this.v = arrayDeque.remove();
            }
        }
        c cVar2 = this.v;
        long j32 = min - cVar2.d;
        boolean equals2 = cVar2.a.equals(PlaybackParameters.h);
        AudioProcessorChain audioProcessorChain2 = this.b;
        if (equals2) {
            j2 = this.v.c + j32;
        } else if (arrayDeque.isEmpty()) {
            j2 = audioProcessorChain2.getMediaDuration(j32) + this.v.c;
        } else {
            c first = arrayDeque.getFirst();
            j2 = first.c - Util.getMediaDurationForPlayoutDuration(first.d - min, this.v.a.c);
        }
        return this.r.framesToDurationUs(audioProcessorChain2.getSkippedOutputFrameCount()) + j2;
    }

    public int getFormatSupport(Format format) {
        boolean z2 = true;
        if ("audio/raw".equals(format.p)) {
            int i2 = format.ae;
            if (!Util.isEncodingLinearPcm(i2)) {
                y2.t(33, "Invalid PCM encoding: ", i2, "DefaultAudioSink");
                return 0;
            } else if (i2 == 2 || (this.c && i2 == 4)) {
                return 2;
            } else {
                return 1;
            }
        } else if (!this.ay && q(format, this.t)) {
            return 2;
        } else {
            if (d(format, this.a) == null) {
                z2 = false;
            }
            if (z2) {
                return 2;
            }
            return 0;
        }
    }

    public PlaybackParameters getPlaybackParameters() {
        if (this.k) {
            return this.w;
        }
        return e().a;
    }

    public boolean getSkipSilenceEnabled() {
        return e().b;
    }

    public final void h() throws AudioSink.InitializationException {
        boolean z2;
        this.h.block();
        try {
            AudioTrack buildAudioTrack = ((b) Assertions.checkNotNull(this.r)).buildAudioTrack(this.aw, this.t, this.au);
            this.s = buildAudioTrack;
            if (j(buildAudioTrack)) {
                AudioTrack audioTrack = this.s;
                if (this.m == null) {
                    this.m = new f();
                }
                this.m.register(audioTrack);
                AudioTrack audioTrack2 = this.s;
                Format format = this.r.a;
                audioTrack2.setOffloadDelayPadding(format.af, format.ag);
            }
            this.au = this.s.getAudioSessionId();
            AudioTrackPositionTracker audioTrackPositionTracker = this.i;
            AudioTrack audioTrack3 = this.s;
            b bVar = this.r;
            if (bVar.c == 2) {
                z2 = true;
            } else {
                z2 = false;
            }
            audioTrackPositionTracker.setAudioTrack(audioTrack3, z2, bVar.g, bVar.d, bVar.h);
            o();
            int i2 = this.av.a;
            if (i2 != 0) {
                this.s.attachAuxEffect(i2);
                this.s.setAuxEffectSendLevel(this.av.b);
            }
            this.af = true;
        } catch (AudioSink.InitializationException e2) {
            if (this.r.outputModeIsOffload()) {
                this.ay = true;
            }
            AudioSink.Listener listener = this.p;
            if (listener != null) {
                listener.onAudioSinkError(e2);
            }
            throw e2;
        }
    }

    public boolean handleBuffer(ByteBuffer byteBuffer, long j2, int i2) throws AudioSink.InitializationException, AudioSink.WriteException {
        boolean z2;
        boolean z3;
        int i3;
        ByteBuffer byteBuffer2 = byteBuffer;
        long j3 = j2;
        int i4 = i2;
        ByteBuffer byteBuffer3 = this.ak;
        if (byteBuffer3 == null || byteBuffer2 == byteBuffer3) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        b bVar = this.q;
        AudioTrackPositionTracker audioTrackPositionTracker = this.i;
        if (bVar != null) {
            if (!b()) {
                return false;
            }
            if (!this.q.canReuseAudioTrack(this.r)) {
                if (!this.ar) {
                    this.ar = true;
                    audioTrackPositionTracker.handleEndOfStream(g());
                    this.s.stop();
                    this.y = 0;
                }
                if (hasPendingData()) {
                    return false;
                }
                flush();
            } else {
                this.r = this.q;
                this.q = null;
                if (j(this.s)) {
                    this.s.setOffloadEndOfStream();
                    AudioTrack audioTrack = this.s;
                    Format format = this.r.a;
                    audioTrack.setOffloadDelayPadding(format.af, format.ag);
                    this.az = true;
                }
            }
            a(j3);
        }
        boolean i5 = i();
        d<AudioSink.InitializationException> dVar = this.n;
        if (!i5) {
            try {
                h();
            } catch (AudioSink.InitializationException e2) {
                AudioSink.InitializationException initializationException = e2;
                if (!initializationException.c) {
                    dVar.throwExceptionIfDeadlineIsReached(initializationException);
                    return false;
                }
                throw initializationException;
            }
        }
        dVar.clear();
        if (this.af) {
            this.ag = Math.max(0, j3);
            this.ae = false;
            this.af = false;
            if (this.k && Util.a >= 23) {
                n(this.w);
            }
            a(j3);
            if (this.as) {
                play();
            }
        }
        if (!audioTrackPositionTracker.mayHandleBuffer(g())) {
            return false;
        }
        if (this.ak == null) {
            if (byteBuffer.order() == ByteOrder.LITTLE_ENDIAN) {
                z3 = true;
            } else {
                z3 = false;
            }
            Assertions.checkArgument(z3);
            if (!byteBuffer.hasRemaining()) {
                return true;
            }
            b bVar2 = this.r;
            if (bVar2.c != 0 && this.ad == 0) {
                int i6 = bVar2.g;
                switch (i6) {
                    case 5:
                    case 6:
                    case 18:
                        i3 = Ac3Util.parseAc3SyncframeAudioSampleCount(byteBuffer);
                        break;
                    case 7:
                    case 8:
                        i3 = DtsUtil.parseDtsAudioSampleCount(byteBuffer);
                        break;
                    case 9:
                        i3 = MpegAudioUtil.parseMpegAudioFrameSampleCount(Util.getBigEndianInt(byteBuffer2, byteBuffer.position()));
                        if (i3 == -1) {
                            throw new IllegalArgumentException();
                        }
                        break;
                    case 10:
                    case 16:
                        i3 = 1024;
                        break;
                    case 11:
                    case 12:
                        i3 = 2048;
                        break;
                    case 14:
                        int findTrueHdSyncframeOffset = Ac3Util.findTrueHdSyncframeOffset(byteBuffer);
                        if (findTrueHdSyncframeOffset != -1) {
                            i3 = Ac3Util.parseTrueHdSyncframeAudioSampleCount(byteBuffer2, findTrueHdSyncframeOffset) * 16;
                            break;
                        } else {
                            i3 = 0;
                            break;
                        }
                    case 15:
                        i3 = 512;
                        break;
                    case 17:
                        i3 = Ac4Util.parseAc4SyncframeAudioSampleCount(byteBuffer);
                        break;
                    default:
                        throw new IllegalStateException(y2.d(38, "Unexpected audio encoding: ", i6));
                }
                this.ad = i3;
                if (i3 == 0) {
                    return true;
                }
            }
            if (this.u != null) {
                if (!b()) {
                    return false;
                }
                a(j3);
                this.u = null;
            }
            long inputFramesToDurationUs = this.r.inputFramesToDurationUs(f() - this.e.getTrimmedFrameCount()) + this.ag;
            if (!this.ae && Math.abs(inputFramesToDurationUs - j3) > 200000) {
                this.p.onAudioSinkError(new AudioSink.UnexpectedDiscontinuityException(j3, inputFramesToDurationUs));
                this.ae = true;
            }
            if (this.ae) {
                if (!b()) {
                    return false;
                }
                long j4 = j3 - inputFramesToDurationUs;
                this.ag += j4;
                this.ae = false;
                a(j3);
                AudioSink.Listener listener = this.p;
                if (!(listener == null || j4 == 0)) {
                    listener.onPositionDiscontinuity();
                }
            }
            if (this.r.c == 0) {
                this.z += (long) byteBuffer.remaining();
            } else {
                this.aa += (long) (this.ad * i4);
            }
            this.ak = byteBuffer2;
            this.al = i4;
        }
        k(j3);
        if (!this.ak.hasRemaining()) {
            this.ak = null;
            this.al = 0;
            return true;
        } else if (!audioTrackPositionTracker.isStalled(g())) {
            return false;
        } else {
            Log.w("DefaultAudioSink", "Resetting stalled audio track");
            flush();
            return true;
        }
    }

    public void handleDiscontinuity() {
        this.ae = true;
    }

    public boolean hasPendingData() {
        if (!i() || !this.i.hasPendingData(g())) {
            return false;
        }
        return true;
    }

    public final boolean i() {
        return this.s != null;
    }

    public boolean isEnded() {
        return !i() || (this.aq && !hasPendingData());
    }

    public final void k(long j2) throws AudioSink.WriteException {
        ByteBuffer byteBuffer;
        int length = this.ai.length;
        int i2 = length;
        while (i2 >= 0) {
            if (i2 > 0) {
                byteBuffer = this.aj[i2 - 1];
            } else {
                byteBuffer = this.ak;
                if (byteBuffer == null) {
                    byteBuffer = AudioProcessor.a;
                }
            }
            if (i2 == length) {
                r(byteBuffer, j2);
            } else {
                AudioProcessor audioProcessor = this.ai[i2];
                if (i2 > this.ap) {
                    audioProcessor.queueInput(byteBuffer);
                }
                ByteBuffer output = audioProcessor.getOutput();
                this.aj[i2] = output;
                if (output.hasRemaining()) {
                    i2++;
                }
            }
            if (!byteBuffer.hasRemaining()) {
                i2--;
            } else {
                return;
            }
        }
    }

    public final void l() {
        this.z = 0;
        this.aa = 0;
        this.ab = 0;
        this.ac = 0;
        int i2 = 0;
        this.az = false;
        this.ad = 0;
        this.v = new c(e().a, getSkipSilenceEnabled(), 0, 0);
        this.ag = 0;
        this.u = null;
        this.j.clear();
        this.ak = null;
        this.al = 0;
        this.am = null;
        this.ar = false;
        this.aq = false;
        this.ap = -1;
        this.x = null;
        this.y = 0;
        this.e.resetTrimmedFrameCount();
        while (true) {
            AudioProcessor[] audioProcessorArr = this.ai;
            if (i2 < audioProcessorArr.length) {
                AudioProcessor audioProcessor = audioProcessorArr[i2];
                audioProcessor.flush();
                this.aj[i2] = audioProcessor.getOutput();
                i2++;
            } else {
                return;
            }
        }
    }

    public final void m(PlaybackParameters playbackParameters, boolean z2) {
        c e2 = e();
        if (!playbackParameters.equals(e2.a) || z2 != e2.b) {
            c cVar = new c(playbackParameters, z2, -9223372036854775807L, -9223372036854775807L);
            if (i()) {
                this.u = cVar;
            } else {
                this.v = cVar;
            }
        }
    }

    @RequiresApi(23)
    public final void n(PlaybackParameters playbackParameters) {
        if (i()) {
            try {
                this.s.setPlaybackParams(new PlaybackParams().allowDefaults().setSpeed(playbackParameters.c).setPitch(playbackParameters.f).setAudioFallbackMode(2));
            } catch (IllegalArgumentException e2) {
                Log.w("DefaultAudioSink", "Failed to set playback params", e2);
            }
            playbackParameters = new PlaybackParameters(this.s.getPlaybackParams().getSpeed(), this.s.getPlaybackParams().getPitch());
            this.i.setAudioTrackPlaybackSpeed(playbackParameters.c);
        }
        this.w = playbackParameters;
    }

    public final void o() {
        if (i()) {
            if (Util.a >= 21) {
                this.s.setVolume(this.ah);
                return;
            }
            AudioTrack audioTrack = this.s;
            float f2 = this.ah;
            audioTrack.setStereoVolume(f2, f2);
        }
    }

    public final boolean p() {
        boolean z2;
        if (this.aw || !"audio/raw".equals(this.r.a.p)) {
            return false;
        }
        int i2 = this.r.a.ae;
        if (!this.c || !Util.isEncodingHighResolutionPcm(i2)) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (!z2) {
            return true;
        }
        return false;
    }

    public void pause() {
        this.as = false;
        if (i() && this.i.pause()) {
            this.s.pause();
        }
    }

    public void play() {
        this.as = true;
        if (i()) {
            this.i.start();
            this.s.play();
        }
    }

    public void playToEndOfStream() throws AudioSink.WriteException {
        if (!this.aq && i() && b()) {
            if (!this.ar) {
                this.ar = true;
                this.i.handleEndOfStream(g());
                this.s.stop();
                this.y = 0;
            }
            this.aq = true;
        }
    }

    public final boolean q(Format format, AudioAttributes audioAttributes) {
        int i2;
        int encoding;
        int audioTrackChannelConfig;
        boolean z2;
        boolean z3;
        boolean z4;
        int i3 = Util.a;
        if (i3 < 29 || (i2 = this.l) == 0 || (encoding = MimeTypes.getEncoding((String) Assertions.checkNotNull(format.p), format.m)) == 0 || (audioTrackChannelConfig = Util.getAudioTrackChannelConfig(format.ac)) == 0 || !AudioManager.isOffloadedPlaybackSupported(c(format.ad, audioTrackChannelConfig, encoding), audioAttributes.getAudioAttributesV21())) {
            return false;
        }
        if (format.af == 0 && format.ag == 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (i2 == 1) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z2 && z3) {
            if (i3 < 30 || !Util.d.startsWith("Pixel")) {
                z4 = false;
            } else {
                z4 = true;
            }
            if (!z4) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d5, code lost:
        if (r13 < r12) goto L_0x00d7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f8  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x012a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void r(java.nio.ByteBuffer r11, long r12) throws com.google.android.exoplayer2.audio.AudioSink.WriteException {
        /*
            r10 = this;
            boolean r0 = r11.hasRemaining()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            java.nio.ByteBuffer r0 = r10.am
            r1 = 1
            r2 = 0
            r3 = 21
            if (r0 == 0) goto L_0x0018
            if (r0 != r11) goto L_0x0013
            r0 = 1
            goto L_0x0014
        L_0x0013:
            r0 = 0
        L_0x0014:
            com.google.android.exoplayer2.util.Assertions.checkArgument(r0)
            goto L_0x003b
        L_0x0018:
            r10.am = r11
            int r0 = com.google.android.exoplayer2.util.Util.a
            if (r0 >= r3) goto L_0x003b
            int r0 = r11.remaining()
            byte[] r4 = r10.an
            if (r4 == 0) goto L_0x0029
            int r4 = r4.length
            if (r4 >= r0) goto L_0x002d
        L_0x0029:
            byte[] r4 = new byte[r0]
            r10.an = r4
        L_0x002d:
            int r4 = r11.position()
            byte[] r5 = r10.an
            r11.get(r5, r2, r0)
            r11.position(r4)
            r10.ao = r2
        L_0x003b:
            int r0 = r11.remaining()
            int r4 = com.google.android.exoplayer2.util.Util.a
            com.google.android.exoplayer2.audio.AudioTrackPositionTracker r5 = r10.i
            if (r4 >= r3) goto L_0x006c
            long r12 = r10.ab
            int r12 = r5.getAvailableBufferSize(r12)
            if (r12 <= 0) goto L_0x00d7
            int r12 = java.lang.Math.min(r0, r12)
            android.media.AudioTrack r13 = r10.s
            byte[] r3 = r10.an
            int r6 = r10.ao
            int r12 = r13.write(r3, r6, r12)
            if (r12 <= 0) goto L_0x00ee
            int r13 = r10.ao
            int r13 = r13 + r12
            r10.ao = r13
            int r13 = r11.position()
            int r13 = r13 + r12
            r11.position(r13)
            goto L_0x00ee
        L_0x006c:
            boolean r3 = r10.aw
            if (r3 == 0) goto L_0x00e8
            r6 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r3 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r3 == 0) goto L_0x007b
            r3 = 1
            goto L_0x007c
        L_0x007b:
            r3 = 0
        L_0x007c:
            com.google.android.exoplayer2.util.Assertions.checkState(r3)
            android.media.AudioTrack r3 = r10.s
            r6 = 26
            r7 = 1000(0x3e8, double:4.94E-321)
            if (r4 < r6) goto L_0x008e
            long r12 = r12 * r7
            int r12 = r3.write(r11, r0, 1, r12)
            goto L_0x00ee
        L_0x008e:
            java.nio.ByteBuffer r6 = r10.x
            if (r6 != 0) goto L_0x00a7
            r6 = 16
            java.nio.ByteBuffer r6 = java.nio.ByteBuffer.allocate(r6)
            r10.x = r6
            java.nio.ByteOrder r9 = java.nio.ByteOrder.BIG_ENDIAN
            r6.order(r9)
            java.nio.ByteBuffer r6 = r10.x
            r9 = 1431633921(0x55550001, float:1.46372496E13)
            r6.putInt(r9)
        L_0x00a7:
            int r6 = r10.y
            if (r6 != 0) goto L_0x00c1
            java.nio.ByteBuffer r6 = r10.x
            r9 = 4
            r6.putInt(r9, r0)
            java.nio.ByteBuffer r6 = r10.x
            r9 = 8
            long r12 = r12 * r7
            r6.putLong(r9, r12)
            java.nio.ByteBuffer r12 = r10.x
            r12.position(r2)
            r10.y = r0
        L_0x00c1:
            java.nio.ByteBuffer r12 = r10.x
            int r12 = r12.remaining()
            if (r12 <= 0) goto L_0x00d9
            java.nio.ByteBuffer r13 = r10.x
            int r13 = r3.write(r13, r12, r1)
            if (r13 >= 0) goto L_0x00d5
            r10.y = r2
            r12 = r13
            goto L_0x00ee
        L_0x00d5:
            if (r13 >= r12) goto L_0x00d9
        L_0x00d7:
            r12 = 0
            goto L_0x00ee
        L_0x00d9:
            int r12 = r3.write(r11, r0, r1)
            if (r12 >= 0) goto L_0x00e2
            r10.y = r2
            goto L_0x00ee
        L_0x00e2:
            int r13 = r10.y
            int r13 = r13 - r12
            r10.y = r13
            goto L_0x00ee
        L_0x00e8:
            android.media.AudioTrack r12 = r10.s
            int r12 = r12.write(r11, r0, r1)
        L_0x00ee:
            long r6 = android.os.SystemClock.elapsedRealtime()
            r10.ax = r6
            com.google.android.exoplayer2.audio.DefaultAudioSink$d<com.google.android.exoplayer2.audio.AudioSink$WriteException> r13 = r10.o
            if (r12 >= 0) goto L_0x012a
            r11 = 24
            if (r4 < r11) goto L_0x00ff
            r11 = -6
            if (r12 == r11) goto L_0x0103
        L_0x00ff:
            r11 = -32
            if (r12 != r11) goto L_0x0104
        L_0x0103:
            r2 = 1
        L_0x0104:
            if (r2 == 0) goto L_0x0111
            com.google.android.exoplayer2.audio.DefaultAudioSink$b r11 = r10.r
            boolean r11 = r11.outputModeIsOffload()
            if (r11 != 0) goto L_0x010f
            goto L_0x0111
        L_0x010f:
            r10.ay = r1
        L_0x0111:
            com.google.android.exoplayer2.audio.AudioSink$WriteException r11 = new com.google.android.exoplayer2.audio.AudioSink$WriteException
            com.google.android.exoplayer2.audio.DefaultAudioSink$b r0 = r10.r
            com.google.android.exoplayer2.Format r0 = r0.a
            r11.<init>(r12, r0, r2)
            com.google.android.exoplayer2.audio.AudioSink$Listener r12 = r10.p
            if (r12 == 0) goto L_0x0121
            r12.onAudioSinkError(r11)
        L_0x0121:
            boolean r12 = r11.c
            if (r12 != 0) goto L_0x0129
            r13.throwExceptionIfDeadlineIsReached(r11)
            return
        L_0x0129:
            throw r11
        L_0x012a:
            r13.clear()
            android.media.AudioTrack r13 = r10.s
            boolean r13 = j(r13)
            if (r13 == 0) goto L_0x0156
            long r3 = r10.ac
            r6 = 0
            int r13 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r13 <= 0) goto L_0x013f
            r10.az = r2
        L_0x013f:
            boolean r13 = r10.as
            if (r13 == 0) goto L_0x0156
            com.google.android.exoplayer2.audio.AudioSink$Listener r13 = r10.p
            if (r13 == 0) goto L_0x0156
            if (r12 >= r0) goto L_0x0156
            boolean r13 = r10.az
            if (r13 != 0) goto L_0x0156
            long r3 = r5.getPendingBufferDurationMs(r3)
            com.google.android.exoplayer2.audio.AudioSink$Listener r13 = r10.p
            r13.onOffloadBufferFull(r3)
        L_0x0156:
            com.google.android.exoplayer2.audio.DefaultAudioSink$b r13 = r10.r
            int r13 = r13.c
            if (r13 != 0) goto L_0x0162
            long r3 = r10.ab
            long r5 = (long) r12
            long r3 = r3 + r5
            r10.ab = r3
        L_0x0162:
            if (r12 != r0) goto L_0x017e
            if (r13 == 0) goto L_0x017b
            java.nio.ByteBuffer r12 = r10.ak
            if (r11 != r12) goto L_0x016b
            goto L_0x016c
        L_0x016b:
            r1 = 0
        L_0x016c:
            com.google.android.exoplayer2.util.Assertions.checkState(r1)
            long r11 = r10.ac
            int r13 = r10.ad
            int r0 = r10.al
            int r13 = r13 * r0
            long r0 = (long) r13
            long r11 = r11 + r0
            r10.ac = r11
        L_0x017b:
            r11 = 0
            r10.am = r11
        L_0x017e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.DefaultAudioSink.r(java.nio.ByteBuffer, long):void");
    }

    public void reset() {
        flush();
        for (AudioProcessor reset : this.f) {
            reset.reset();
        }
        for (AudioProcessor reset2 : this.g) {
            reset2.reset();
        }
        this.as = false;
        this.ay = false;
    }

    public void setAudioAttributes(AudioAttributes audioAttributes) {
        if (!this.t.equals(audioAttributes)) {
            this.t = audioAttributes;
            if (!this.aw) {
                flush();
            }
        }
    }

    public void setAudioSessionId(int i2) {
        boolean z2;
        if (this.au != i2) {
            this.au = i2;
            if (i2 != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            this.at = z2;
            flush();
        }
    }

    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        if (!this.av.equals(auxEffectInfo)) {
            int i2 = auxEffectInfo.a;
            AudioTrack audioTrack = this.s;
            if (audioTrack != null) {
                if (this.av.a != i2) {
                    audioTrack.attachAuxEffect(i2);
                }
                if (i2 != 0) {
                    this.s.setAuxEffectSendLevel(auxEffectInfo.b);
                }
            }
            this.av = auxEffectInfo;
        }
    }

    public void setListener(AudioSink.Listener listener) {
        this.p = listener;
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        PlaybackParameters playbackParameters2 = new PlaybackParameters(Util.constrainValue(playbackParameters.c, 0.1f, 8.0f), Util.constrainValue(playbackParameters.f, 0.1f, 8.0f));
        if (!this.k || Util.a < 23) {
            m(playbackParameters2, getSkipSilenceEnabled());
        } else {
            n(playbackParameters2);
        }
    }

    public void setSkipSilenceEnabled(boolean z2) {
        m(e().a, z2);
    }

    public void setVolume(float f2) {
        if (this.ah != f2) {
            this.ah = f2;
            o();
        }
    }

    public boolean supportsFormat(Format format) {
        return getFormatSupport(format) != 0;
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities, AudioProcessor[] audioProcessorArr, boolean z2) {
        this(audioCapabilities, new DefaultAudioProcessorChain(audioProcessorArr), z2, false, 0);
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities, AudioProcessorChain audioProcessorChain, boolean z2, boolean z3, int i2) {
        this.a = audioCapabilities;
        this.b = (AudioProcessorChain) Assertions.checkNotNull(audioProcessorChain);
        int i3 = Util.a;
        this.c = i3 >= 21 && z2;
        this.k = i3 >= 23 && z3;
        this.l = i3 < 29 ? 0 : i2;
        this.h = new ConditionVariable(true);
        this.i = new AudioTrackPositionTracker(new e());
        a aVar = new a();
        this.d = aVar;
        d dVar = new d();
        this.e = dVar;
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, new BaseAudioProcessor[]{new c(), aVar, dVar});
        Collections.addAll(arrayList, audioProcessorChain.getAudioProcessors());
        this.f = (AudioProcessor[]) arrayList.toArray(new AudioProcessor[0]);
        this.g = new AudioProcessor[]{new b()};
        this.ah = 1.0f;
        this.t = AudioAttributes.j;
        this.au = 0;
        this.av = new AuxEffectInfo(0, 0.0f);
        PlaybackParameters playbackParameters = PlaybackParameters.h;
        this.v = new c(playbackParameters, false, 0, 0);
        this.w = playbackParameters;
        this.ap = -1;
        this.ai = new AudioProcessor[0];
        this.aj = new ByteBuffer[0];
        this.j = new ArrayDeque<>();
        this.n = new d<>(100);
        this.o = new d<>(100);
    }
}
