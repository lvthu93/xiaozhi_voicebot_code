package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Assertions;
import java.nio.ByteBuffer;

public final class a extends BaseAudioProcessor {
    @Nullable
    public int[] i;
    @Nullable
    public int[] j;

    public final void a() {
        this.j = this.i;
    }

    public final void c() {
        this.j = null;
        this.i = null;
    }

    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        boolean z;
        boolean z2;
        int[] iArr = this.i;
        if (iArr == null) {
            return AudioProcessor.AudioFormat.e;
        }
        if (audioFormat.c == 2) {
            int length = iArr.length;
            int i2 = audioFormat.b;
            if (i2 != length) {
                z = true;
            } else {
                z = false;
            }
            int i3 = 0;
            while (i3 < iArr.length) {
                int i4 = iArr[i3];
                if (i4 < i2) {
                    if (i4 != i3) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    z |= z2;
                    i3++;
                } else {
                    throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
                }
            }
            if (z) {
                return new AudioProcessor.AudioFormat(audioFormat.a, iArr.length, 2);
            }
            return AudioProcessor.AudioFormat.e;
        }
        throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
    }

    public void queueInput(ByteBuffer byteBuffer) {
        int[] iArr = (int[]) Assertions.checkNotNull(this.j);
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        ByteBuffer d = d(((limit - position) / this.b.d) * this.c.d);
        while (position < limit) {
            for (int i2 : iArr) {
                d.putShort(byteBuffer.getShort((i2 * 2) + position));
            }
            position += this.b.d;
        }
        byteBuffer.position(limit);
        d.flip();
    }

    public void setChannelMap(@Nullable int[] iArr) {
        this.i = iArr;
    }
}
