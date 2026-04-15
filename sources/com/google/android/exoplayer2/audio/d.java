package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

public final class d extends BaseAudioProcessor {
    public int i;
    public int j;
    public boolean k;
    public int l;
    public byte[] m = Util.f;
    public int n;
    public long o;

    public final void a() {
        if (this.k) {
            this.k = false;
            int i2 = this.j;
            int i3 = this.b.d;
            this.m = new byte[(i2 * i3)];
            this.l = this.i * i3;
        }
        this.n = 0;
    }

    public final void b() {
        if (this.k) {
            int i2 = this.n;
            if (i2 > 0) {
                this.o += (long) (i2 / this.b.d);
            }
            this.n = 0;
        }
    }

    public final void c() {
        this.m = Util.f;
    }

    public ByteBuffer getOutput() {
        int i2;
        if (super.isEnded() && (i2 = this.n) > 0) {
            d(i2).put(this.m, 0, this.n).flip();
            this.n = 0;
        }
        return super.getOutput();
    }

    public long getTrimmedFrameCount() {
        return this.o;
    }

    public boolean isEnded() {
        return super.isEnded() && this.n == 0;
    }

    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        if (audioFormat.c == 2) {
            this.k = true;
            if (this.i == 0 && this.j == 0) {
                return AudioProcessor.AudioFormat.e;
            }
            return audioFormat;
        }
        throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
    }

    public void queueInput(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        int i2 = limit - position;
        if (i2 != 0) {
            int min = Math.min(i2, this.l);
            this.o += (long) (min / this.b.d);
            this.l -= min;
            byteBuffer.position(position + min);
            if (this.l <= 0) {
                int i3 = i2 - min;
                int length = (this.n + i3) - this.m.length;
                ByteBuffer d = d(length);
                int constrainValue = Util.constrainValue(length, 0, this.n);
                d.put(this.m, 0, constrainValue);
                int constrainValue2 = Util.constrainValue(length - constrainValue, 0, i3);
                byteBuffer.limit(byteBuffer.position() + constrainValue2);
                d.put(byteBuffer);
                byteBuffer.limit(limit);
                int i4 = i3 - constrainValue2;
                int i5 = this.n - constrainValue;
                this.n = i5;
                byte[] bArr = this.m;
                System.arraycopy(bArr, constrainValue, bArr, 0, i5);
                byteBuffer.get(this.m, this.n, i4);
                this.n += i4;
                d.flip();
            }
        }
    }

    public void resetTrimmedFrameCount() {
        this.o = 0;
    }

    public void setTrimFrameCount(int i2, int i3) {
        this.i = i2;
        this.j = i3;
    }
}
