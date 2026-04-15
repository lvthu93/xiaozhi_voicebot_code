package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public final class SonicAudioProcessor implements AudioProcessor {
    public int b;
    public float c = 1.0f;
    public float d = 1.0f;
    public AudioProcessor.AudioFormat e;
    public AudioProcessor.AudioFormat f;
    public AudioProcessor.AudioFormat g;
    public AudioProcessor.AudioFormat h;
    public boolean i;
    @Nullable
    public gb j;
    public ByteBuffer k;
    public ShortBuffer l;
    public ByteBuffer m;
    public long n;
    public long o;
    public boolean p;

    public SonicAudioProcessor() {
        AudioProcessor.AudioFormat audioFormat = AudioProcessor.AudioFormat.e;
        this.e = audioFormat;
        this.f = audioFormat;
        this.g = audioFormat;
        this.h = audioFormat;
        ByteBuffer byteBuffer = AudioProcessor.a;
        this.k = byteBuffer;
        this.l = byteBuffer.asShortBuffer();
        this.m = byteBuffer;
        this.b = -1;
    }

    public AudioProcessor.AudioFormat configure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        if (audioFormat.c == 2) {
            int i2 = this.b;
            if (i2 == -1) {
                i2 = audioFormat.a;
            }
            this.e = audioFormat;
            AudioProcessor.AudioFormat audioFormat2 = new AudioProcessor.AudioFormat(i2, audioFormat.b, 2);
            this.f = audioFormat2;
            this.i = true;
            return audioFormat2;
        }
        throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
    }

    public void flush() {
        if (isActive()) {
            AudioProcessor.AudioFormat audioFormat = this.e;
            this.g = audioFormat;
            AudioProcessor.AudioFormat audioFormat2 = this.f;
            this.h = audioFormat2;
            if (this.i) {
                this.j = new gb(audioFormat.a, audioFormat.b, this.c, this.d, audioFormat2.a);
            } else {
                gb gbVar = this.j;
                if (gbVar != null) {
                    gbVar.flush();
                }
            }
        }
        this.m = AudioProcessor.a;
        this.n = 0;
        this.o = 0;
        this.p = false;
    }

    public long getMediaDuration(long j2) {
        if (this.o < 1024) {
            return (long) (((double) this.c) * ((double) j2));
        }
        long pendingInputBytes = this.n - ((long) ((gb) Assertions.checkNotNull(this.j)).getPendingInputBytes());
        int i2 = this.h.a;
        int i3 = this.g.a;
        if (i2 == i3) {
            return Util.scaleLargeTimestamp(j2, pendingInputBytes, this.o);
        }
        return Util.scaleLargeTimestamp(j2, pendingInputBytes * ((long) i2), this.o * ((long) i3));
    }

    public ByteBuffer getOutput() {
        int outputSize;
        gb gbVar = this.j;
        if (gbVar != null && (outputSize = gbVar.getOutputSize()) > 0) {
            if (this.k.capacity() < outputSize) {
                ByteBuffer order = ByteBuffer.allocateDirect(outputSize).order(ByteOrder.nativeOrder());
                this.k = order;
                this.l = order.asShortBuffer();
            } else {
                this.k.clear();
                this.l.clear();
            }
            gbVar.getOutput(this.l);
            this.o += (long) outputSize;
            this.k.limit(outputSize);
            this.m = this.k;
        }
        ByteBuffer byteBuffer = this.m;
        this.m = AudioProcessor.a;
        return byteBuffer;
    }

    public boolean isActive() {
        if (this.f.a == -1 || (Math.abs(this.c - 1.0f) < 1.0E-4f && Math.abs(this.d - 1.0f) < 1.0E-4f && this.f.a == this.e.a)) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r1.j;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isEnded() {
        /*
            r1 = this;
            boolean r0 = r1.p
            if (r0 == 0) goto L_0x0010
            gb r0 = r1.j
            if (r0 == 0) goto L_0x000e
            int r0 = r0.getOutputSize()
            if (r0 != 0) goto L_0x0010
        L_0x000e:
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.SonicAudioProcessor.isEnded():boolean");
    }

    public void queueEndOfStream() {
        gb gbVar = this.j;
        if (gbVar != null) {
            gbVar.queueEndOfStream();
        }
        this.p = true;
    }

    public void queueInput(ByteBuffer byteBuffer) {
        if (byteBuffer.hasRemaining()) {
            ShortBuffer asShortBuffer = byteBuffer.asShortBuffer();
            int remaining = byteBuffer.remaining();
            this.n += (long) remaining;
            ((gb) Assertions.checkNotNull(this.j)).queueInput(asShortBuffer);
            byteBuffer.position(byteBuffer.position() + remaining);
        }
    }

    public void reset() {
        this.c = 1.0f;
        this.d = 1.0f;
        AudioProcessor.AudioFormat audioFormat = AudioProcessor.AudioFormat.e;
        this.e = audioFormat;
        this.f = audioFormat;
        this.g = audioFormat;
        this.h = audioFormat;
        ByteBuffer byteBuffer = AudioProcessor.a;
        this.k = byteBuffer;
        this.l = byteBuffer.asShortBuffer();
        this.m = byteBuffer;
        this.b = -1;
        this.i = false;
        this.j = null;
        this.n = 0;
        this.o = 0;
        this.p = false;
    }

    public void setOutputSampleRateHz(int i2) {
        this.b = i2;
    }

    public void setPitch(float f2) {
        if (this.d != f2) {
            this.d = f2;
            this.i = true;
        }
    }

    public void setSpeed(float f2) {
        if (this.c != f2) {
            this.c = f2;
            this.i = true;
        }
    }
}
