package com.google.android.exoplayer2.audio;

import android.media.AudioTrack;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.reflect.Method;

public final class AudioTrackPositionTracker {
    public final Listener a;
    public long aa;
    public long ab;
    public long ac;
    public boolean ad;
    public long ae;
    public long af;
    public final long[] b;
    @Nullable
    public AudioTrack c;
    public int d;
    public int e;
    @Nullable
    public bx f;
    public int g;
    public boolean h;
    public long i;
    public float j;
    public boolean k;
    public long l;
    public long m;
    @Nullable
    public Method n;
    public long o;
    public boolean p;
    public boolean q;
    public long r;
    public long s;
    public long t;
    public long u;
    public int v;
    public int w;
    public long x;
    public long y;
    public long z;

    public interface Listener {
        void onInvalidLatency(long j);

        void onPositionAdvancing(long j);

        void onPositionFramesMismatch(long j, long j2, long j3, long j4);

        void onSystemTimeUsMismatch(long j, long j2, long j3, long j4);

        void onUnderrun(int i, long j);
    }

    public AudioTrackPositionTracker(Listener listener) {
        this.a = (Listener) Assertions.checkNotNull(listener);
        if (Util.a >= 18) {
            try {
                this.n = AudioTrack.class.getMethod("getLatency", (Class[]) null);
            } catch (NoSuchMethodException unused) {
            }
        }
        this.b = new long[10];
    }

    public final long a() {
        AudioTrack audioTrack = (AudioTrack) Assertions.checkNotNull(this.c);
        if (this.x != -9223372036854775807L) {
            return Math.min(this.aa, this.z + ((((SystemClock.elapsedRealtime() * 1000) - this.x) * ((long) this.g)) / 1000000));
        }
        int playState = audioTrack.getPlayState();
        if (playState == 1) {
            return 0;
        }
        long playbackHeadPosition = ((long) audioTrack.getPlaybackHeadPosition()) & 4294967295L;
        if (this.h) {
            if (playState == 2 && playbackHeadPosition == 0) {
                this.u = this.s;
            }
            playbackHeadPosition += this.u;
        }
        if (Util.a <= 29) {
            if (playbackHeadPosition == 0 && this.s > 0 && playState == 3) {
                if (this.y == -9223372036854775807L) {
                    this.y = SystemClock.elapsedRealtime();
                }
                return this.s;
            }
            this.y = -9223372036854775807L;
        }
        if (this.s > playbackHeadPosition) {
            this.t++;
        }
        this.s = playbackHeadPosition;
        return playbackHeadPosition + (this.t << 32);
    }

    public int getAvailableBufferSize(long j2) {
        return this.e - ((int) (j2 - (a() * ((long) this.d))));
    }

    public long getCurrentPositionUs(boolean z2) {
        long j2;
        long j3;
        Method method;
        int playState = ((AudioTrack) Assertions.checkNotNull(this.c)).getPlayState();
        Listener listener = this.a;
        if (playState == 3) {
            long a2 = (a() * 1000000) / ((long) this.g);
            if (a2 != 0) {
                long nanoTime = System.nanoTime() / 1000;
                if (nanoTime - this.m >= 30000) {
                    int i2 = this.v;
                    long[] jArr = this.b;
                    jArr[i2] = a2 - nanoTime;
                    this.v = (i2 + 1) % 10;
                    int i3 = this.w;
                    if (i3 < 10) {
                        this.w = i3 + 1;
                    }
                    this.m = nanoTime;
                    this.l = 0;
                    int i4 = 0;
                    while (true) {
                        int i5 = this.w;
                        if (i4 >= i5) {
                            break;
                        }
                        this.l = (jArr[i4] / ((long) i5)) + this.l;
                        i4++;
                    }
                }
                if (!this.h) {
                    bx bxVar = (bx) Assertions.checkNotNull(this.f);
                    if (bxVar.maybePollTimestamp(nanoTime)) {
                        long timestampSystemTimeUs = bxVar.getTimestampSystemTimeUs();
                        long timestampPositionFrames = bxVar.getTimestampPositionFrames();
                        if (Math.abs(timestampSystemTimeUs - nanoTime) > 5000000) {
                            this.a.onSystemTimeUsMismatch(timestampPositionFrames, timestampSystemTimeUs, nanoTime, a2);
                            bxVar.rejectTimestamp();
                        } else if (Math.abs(((timestampPositionFrames * 1000000) / ((long) this.g)) - a2) > 5000000) {
                            this.a.onPositionFramesMismatch(timestampPositionFrames, timestampSystemTimeUs, nanoTime, a2);
                            bxVar.rejectTimestamp();
                        } else {
                            bxVar.acceptTimestamp();
                        }
                    }
                    if (this.q && (method = this.n) != null && nanoTime - this.r >= 500000) {
                        try {
                            long intValue = (((long) ((Integer) Util.castNonNull((Integer) method.invoke(Assertions.checkNotNull(this.c), new Object[0]))).intValue()) * 1000) - this.i;
                            this.o = intValue;
                            long max = Math.max(intValue, 0);
                            this.o = max;
                            if (max > 5000000) {
                                listener.onInvalidLatency(max);
                                this.o = 0;
                            }
                        } catch (Exception unused) {
                            this.n = null;
                        }
                        this.r = nanoTime;
                    }
                }
            }
        }
        long nanoTime2 = System.nanoTime() / 1000;
        bx bxVar2 = (bx) Assertions.checkNotNull(this.f);
        boolean hasAdvancingTimestamp = bxVar2.hasAdvancingTimestamp();
        if (hasAdvancingTimestamp) {
            j2 = Util.getMediaDurationForPlayoutDuration(nanoTime2 - bxVar2.getTimestampSystemTimeUs(), this.j) + ((bxVar2.getTimestampPositionFrames() * 1000000) / ((long) this.g));
        } else {
            if (this.w == 0) {
                j3 = (a() * 1000000) / ((long) this.g);
            } else {
                j3 = this.l + nanoTime2;
            }
            j2 = j3;
            if (!z2) {
                j2 = Math.max(0, j2 - this.o);
            }
        }
        if (this.ad != hasAdvancingTimestamp) {
            this.af = this.ac;
            this.ae = this.ab;
        }
        long j4 = nanoTime2 - this.af;
        if (j4 < 1000000) {
            long j5 = (j4 * 1000) / 1000000;
            long j6 = j2 * j5;
            long j7 = 1000 - j5;
            j2 = ((j7 * (Util.getMediaDurationForPlayoutDuration(j4, this.j) + this.ae)) + j6) / 1000;
        }
        if (!this.k) {
            long j8 = this.ab;
            if (j2 > j8) {
                this.k = true;
                listener.onPositionAdvancing(System.currentTimeMillis() - C.usToMs(Util.getPlayoutDurationForMediaDuration(C.usToMs(j2 - j8), this.j)));
            }
        }
        this.ac = nanoTime2;
        this.ab = j2;
        this.ad = hasAdvancingTimestamp;
        return j2;
    }

    public long getPendingBufferDurationMs(long j2) {
        return C.usToMs(((j2 - a()) * 1000000) / ((long) this.g));
    }

    public void handleEndOfStream(long j2) {
        this.z = a();
        this.x = SystemClock.elapsedRealtime() * 1000;
        this.aa = j2;
    }

    public boolean hasPendingData(long j2) {
        boolean z2;
        if (j2 > a()) {
            return true;
        }
        if (this.h && ((AudioTrack) Assertions.checkNotNull(this.c)).getPlayState() == 2 && a() == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            return true;
        }
        return false;
    }

    public boolean isPlaying() {
        return ((AudioTrack) Assertions.checkNotNull(this.c)).getPlayState() == 3;
    }

    public boolean isStalled(long j2) {
        if (this.y == -9223372036854775807L || j2 <= 0 || SystemClock.elapsedRealtime() - this.y < 200) {
            return false;
        }
        return true;
    }

    public boolean mayHandleBuffer(long j2) {
        int playState = ((AudioTrack) Assertions.checkNotNull(this.c)).getPlayState();
        if (this.h) {
            if (playState == 2) {
                this.p = false;
                return false;
            } else if (playState == 1 && a() == 0) {
                return false;
            }
        }
        boolean z2 = this.p;
        boolean hasPendingData = hasPendingData(j2);
        this.p = hasPendingData;
        if (z2 && !hasPendingData && playState != 1) {
            this.a.onUnderrun(this.e, C.usToMs(this.i));
        }
        return true;
    }

    public boolean pause() {
        this.l = 0;
        this.w = 0;
        this.v = 0;
        this.m = 0;
        this.ac = 0;
        this.af = 0;
        this.k = false;
        if (this.x != -9223372036854775807L) {
            return false;
        }
        ((bx) Assertions.checkNotNull(this.f)).reset();
        return true;
    }

    public void reset() {
        this.l = 0;
        this.w = 0;
        this.v = 0;
        this.m = 0;
        this.ac = 0;
        this.af = 0;
        this.k = false;
        this.c = null;
        this.f = null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0026, code lost:
        if (r4 != false) goto L_0x002a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setAudioTrack(android.media.AudioTrack r3, boolean r4, int r5, int r6, int r7) {
        /*
            r2 = this;
            r2.c = r3
            r2.d = r6
            r2.e = r7
            bx r0 = new bx
            r0.<init>(r3)
            r2.f = r0
            int r3 = r3.getSampleRate()
            r2.g = r3
            r3 = 0
            if (r4 == 0) goto L_0x0029
            int r4 = com.google.android.exoplayer2.util.Util.a
            r0 = 23
            r1 = 1
            if (r4 >= r0) goto L_0x0025
            r4 = 5
            if (r5 == r4) goto L_0x0023
            r4 = 6
            if (r5 != r4) goto L_0x0025
        L_0x0023:
            r4 = 1
            goto L_0x0026
        L_0x0025:
            r4 = 0
        L_0x0026:
            if (r4 == 0) goto L_0x0029
            goto L_0x002a
        L_0x0029:
            r1 = 0
        L_0x002a:
            r2.h = r1
            boolean r4 = com.google.android.exoplayer2.util.Util.isEncodingLinearPcm(r5)
            r2.q = r4
            r0 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r4 == 0) goto L_0x0045
            int r7 = r7 / r6
            long r4 = (long) r7
            r6 = 1000000(0xf4240, double:4.940656E-318)
            long r4 = r4 * r6
            int r6 = r2.g
            long r6 = (long) r6
            long r4 = r4 / r6
            goto L_0x0046
        L_0x0045:
            r4 = r0
        L_0x0046:
            r2.i = r4
            r4 = 0
            r2.s = r4
            r2.t = r4
            r2.u = r4
            r2.p = r3
            r2.x = r0
            r2.y = r0
            r2.r = r4
            r2.o = r4
            r3 = 1065353216(0x3f800000, float:1.0)
            r2.j = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.AudioTrackPositionTracker.setAudioTrack(android.media.AudioTrack, boolean, int, int, int):void");
    }

    public void setAudioTrackPlaybackSpeed(float f2) {
        this.j = f2;
        bx bxVar = this.f;
        if (bxVar != null) {
            bxVar.reset();
        }
    }

    public void start() {
        ((bx) Assertions.checkNotNull(this.f)).reset();
    }
}
