package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class TeeAudioProcessor extends BaseAudioProcessor {
    public final AudioBufferSink i;

    public interface AudioBufferSink {
        void flush(int i, int i2, int i3);

        void handleBuffer(ByteBuffer byteBuffer);
    }

    public static final class WavFileAudioBufferSink implements AudioBufferSink {
        public final String a;
        public final byte[] b;
        public final ByteBuffer c;
        public int d;
        public int e;
        public int f;
        @Nullable
        public RandomAccessFile g;
        public int h;
        public int i;

        public WavFileAudioBufferSink(String str) {
            this.a = str;
            byte[] bArr = new byte[1024];
            this.b = bArr;
            this.c = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
        }

        public final void a() throws IOException {
            if (this.g == null) {
                int i2 = this.h;
                this.h = i2 + 1;
                RandomAccessFile randomAccessFile = new RandomAccessFile(Util.formatInvariant("%s-%04d.wav", this.a, Integer.valueOf(i2)), "rw");
                randomAccessFile.writeInt(1380533830);
                randomAccessFile.writeInt(-1);
                randomAccessFile.writeInt(1463899717);
                randomAccessFile.writeInt(1718449184);
                ByteBuffer byteBuffer = this.c;
                byteBuffer.clear();
                byteBuffer.putInt(16);
                byteBuffer.putShort((short) WavUtil.getTypeForPcmEncoding(this.f));
                byteBuffer.putShort((short) this.e);
                byteBuffer.putInt(this.d);
                int pcmFrameSize = Util.getPcmFrameSize(this.f, this.e);
                byteBuffer.putInt(this.d * pcmFrameSize);
                byteBuffer.putShort((short) pcmFrameSize);
                byteBuffer.putShort((short) ((pcmFrameSize * 8) / this.e));
                randomAccessFile.write(this.b, 0, byteBuffer.position());
                randomAccessFile.writeInt(1684108385);
                randomAccessFile.writeInt(-1);
                this.g = randomAccessFile;
                this.i = 44;
            }
        }

        public final void b() throws IOException {
            byte[] bArr = this.b;
            ByteBuffer byteBuffer = this.c;
            RandomAccessFile randomAccessFile = this.g;
            if (randomAccessFile != null) {
                try {
                    byteBuffer.clear();
                    byteBuffer.putInt(this.i - 8);
                    randomAccessFile.seek(4);
                    randomAccessFile.write(bArr, 0, 4);
                    byteBuffer.clear();
                    byteBuffer.putInt(this.i - 44);
                    randomAccessFile.seek(40);
                    randomAccessFile.write(bArr, 0, 4);
                } catch (IOException e2) {
                    Log.w("WaveFileAudioBufferSink", "Error updating file size", e2);
                }
                try {
                    randomAccessFile.close();
                } finally {
                    this.g = null;
                }
            }
        }

        public void flush(int i2, int i3, int i4) {
            try {
                b();
            } catch (IOException e2) {
                Log.e("WaveFileAudioBufferSink", "Error resetting", e2);
            }
            this.d = i2;
            this.e = i3;
            this.f = i4;
        }

        public void handleBuffer(ByteBuffer byteBuffer) {
            try {
                a();
                RandomAccessFile randomAccessFile = (RandomAccessFile) Assertions.checkNotNull(this.g);
                while (byteBuffer.hasRemaining()) {
                    int remaining = byteBuffer.remaining();
                    byte[] bArr = this.b;
                    int min = Math.min(remaining, bArr.length);
                    byteBuffer.get(bArr, 0, min);
                    randomAccessFile.write(bArr, 0, min);
                    this.i += min;
                }
            } catch (IOException e2) {
                Log.e("WaveFileAudioBufferSink", "Error writing data", e2);
            }
        }
    }

    public TeeAudioProcessor(AudioBufferSink audioBufferSink) {
        this.i = (AudioBufferSink) Assertions.checkNotNull(audioBufferSink);
    }

    public final void a() {
        e();
    }

    public final void b() {
        e();
    }

    public final void c() {
        e();
    }

    public final void e() {
        if (isActive()) {
            AudioProcessor.AudioFormat audioFormat = this.b;
            this.i.flush(audioFormat.a, audioFormat.b, audioFormat.c);
        }
    }

    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) {
        return audioFormat;
    }

    public void queueInput(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        if (remaining != 0) {
            this.i.handleBuffer(byteBuffer.asReadOnlyBuffer());
            d(remaining).put(byteBuffer).flip();
        }
    }
}
