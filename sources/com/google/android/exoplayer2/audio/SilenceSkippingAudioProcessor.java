package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

public final class SilenceSkippingAudioProcessor extends BaseAudioProcessor {
    public final long i;
    public final long j;
    public final short k;
    public int l;
    public boolean m;
    public byte[] n;
    public byte[] o;
    public int p;
    public int q;
    public int r;
    public boolean s;
    public long t;

    public SilenceSkippingAudioProcessor() {
        this(150000, 20000, 1024);
    }

    public final void a() {
        if (this.m) {
            AudioProcessor.AudioFormat audioFormat = this.b;
            int i2 = audioFormat.d;
            this.l = i2;
            int i3 = audioFormat.a;
            int i4 = ((int) ((this.i * ((long) i3)) / 1000000)) * i2;
            if (this.n.length != i4) {
                this.n = new byte[i4];
            }
            int i5 = ((int) ((this.j * ((long) i3)) / 1000000)) * i2;
            this.r = i5;
            if (this.o.length != i5) {
                this.o = new byte[i5];
            }
        }
        this.p = 0;
        this.t = 0;
        this.q = 0;
        this.s = false;
    }

    public final void b() {
        int i2 = this.q;
        if (i2 > 0) {
            f(this.n, i2);
        }
        if (!this.s) {
            this.t += (long) (this.r / this.l);
        }
    }

    public final void c() {
        this.m = false;
        this.r = 0;
        byte[] bArr = Util.f;
        this.n = bArr;
        this.o = bArr;
    }

    public final int e(ByteBuffer byteBuffer) {
        for (int position = byteBuffer.position(); position < byteBuffer.limit(); position += 2) {
            if (Math.abs(byteBuffer.getShort(position)) > this.k) {
                int i2 = this.l;
                return (position / i2) * i2;
            }
        }
        return byteBuffer.limit();
    }

    public final void f(byte[] bArr, int i2) {
        d(i2).put(bArr, 0, i2).flip();
        if (i2 > 0) {
            this.s = true;
        }
    }

    public final void g(ByteBuffer byteBuffer, int i2, byte[] bArr) {
        int min = Math.min(byteBuffer.remaining(), this.r);
        int i3 = this.r - min;
        System.arraycopy(bArr, i2 - i3, this.o, 0, i3);
        byteBuffer.position(byteBuffer.limit() - min);
        byteBuffer.get(this.o, i3, min);
    }

    public long getSkippedFrames() {
        return this.t;
    }

    public boolean isActive() {
        return this.m;
    }

    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        if (audioFormat.c != 2) {
            throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
        } else if (this.m) {
            return audioFormat;
        } else {
            return AudioProcessor.AudioFormat.e;
        }
    }

    public void queueInput(ByteBuffer byteBuffer) {
        int position;
        while (byteBuffer.hasRemaining() && !this.g.hasRemaining()) {
            int i2 = this.p;
            if (i2 == 0) {
                int limit = byteBuffer.limit();
                byteBuffer.limit(Math.min(limit, byteBuffer.position() + this.n.length));
                int limit2 = byteBuffer.limit();
                while (true) {
                    limit2 -= 2;
                    if (limit2 >= byteBuffer.position()) {
                        if (Math.abs(byteBuffer.getShort(limit2)) > this.k) {
                            int i3 = this.l;
                            position = ((limit2 / i3) * i3) + i3;
                            break;
                        }
                    } else {
                        position = byteBuffer.position();
                        break;
                    }
                }
                if (position == byteBuffer.position()) {
                    this.p = 1;
                } else {
                    byteBuffer.limit(position);
                    int remaining = byteBuffer.remaining();
                    d(remaining).put(byteBuffer).flip();
                    if (remaining > 0) {
                        this.s = true;
                    }
                }
                byteBuffer.limit(limit);
            } else if (i2 == 1) {
                int limit3 = byteBuffer.limit();
                int e = e(byteBuffer);
                int position2 = e - byteBuffer.position();
                byte[] bArr = this.n;
                int length = bArr.length;
                int i4 = this.q;
                int i5 = length - i4;
                if (e >= limit3 || position2 >= i5) {
                    int min = Math.min(position2, i5);
                    byteBuffer.limit(byteBuffer.position() + min);
                    byteBuffer.get(this.n, this.q, min);
                    int i6 = this.q + min;
                    this.q = i6;
                    byte[] bArr2 = this.n;
                    if (i6 == bArr2.length) {
                        if (this.s) {
                            f(bArr2, this.r);
                            this.t += (long) ((this.q - (this.r * 2)) / this.l);
                        } else {
                            this.t += (long) ((i6 - this.r) / this.l);
                        }
                        g(byteBuffer, this.q, this.n);
                        this.q = 0;
                        this.p = 2;
                    }
                    byteBuffer.limit(limit3);
                } else {
                    f(bArr, i4);
                    this.q = 0;
                    this.p = 0;
                }
            } else if (i2 == 2) {
                int limit4 = byteBuffer.limit();
                int e2 = e(byteBuffer);
                byteBuffer.limit(e2);
                this.t += (long) (byteBuffer.remaining() / this.l);
                g(byteBuffer, this.r, this.o);
                if (e2 < limit4) {
                    f(this.o, this.r);
                    this.p = 0;
                    byteBuffer.limit(limit4);
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public void setEnabled(boolean z) {
        this.m = z;
    }

    public SilenceSkippingAudioProcessor(long j2, long j3, short s2) {
        Assertions.checkArgument(j3 <= j2);
        this.i = j2;
        this.j = j3;
        this.k = s2;
        byte[] bArr = Util.f;
        this.n = bArr;
        this.o = bArr;
    }
}
