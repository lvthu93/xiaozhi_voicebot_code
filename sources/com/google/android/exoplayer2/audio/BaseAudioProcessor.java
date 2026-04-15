package com.google.android.exoplayer2.audio;

import androidx.annotation.CallSuper;
import com.google.android.exoplayer2.audio.AudioProcessor;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class BaseAudioProcessor implements AudioProcessor {
    public AudioProcessor.AudioFormat b;
    public AudioProcessor.AudioFormat c;
    public AudioProcessor.AudioFormat d;
    public AudioProcessor.AudioFormat e;
    public ByteBuffer f;
    public ByteBuffer g;
    public boolean h;

    public BaseAudioProcessor() {
        ByteBuffer byteBuffer = AudioProcessor.a;
        this.f = byteBuffer;
        this.g = byteBuffer;
        AudioProcessor.AudioFormat audioFormat = AudioProcessor.AudioFormat.e;
        this.d = audioFormat;
        this.e = audioFormat;
        this.b = audioFormat;
        this.c = audioFormat;
    }

    public void a() {
    }

    public void b() {
    }

    public void c() {
    }

    public final AudioProcessor.AudioFormat configure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        this.d = audioFormat;
        this.e = onConfigure(audioFormat);
        if (isActive()) {
            return this.e;
        }
        return AudioProcessor.AudioFormat.e;
    }

    public final ByteBuffer d(int i) {
        if (this.f.capacity() < i) {
            this.f = ByteBuffer.allocateDirect(i).order(ByteOrder.nativeOrder());
        } else {
            this.f.clear();
        }
        ByteBuffer byteBuffer = this.f;
        this.g = byteBuffer;
        return byteBuffer;
    }

    public final void flush() {
        this.g = AudioProcessor.a;
        this.h = false;
        this.b = this.d;
        this.c = this.e;
        a();
    }

    @CallSuper
    public ByteBuffer getOutput() {
        ByteBuffer byteBuffer = this.g;
        this.g = AudioProcessor.a;
        return byteBuffer;
    }

    public boolean isActive() {
        return this.e != AudioProcessor.AudioFormat.e;
    }

    @CallSuper
    public boolean isEnded() {
        return this.h && this.g == AudioProcessor.a;
    }

    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        return AudioProcessor.AudioFormat.e;
    }

    public final void queueEndOfStream() {
        this.h = true;
        b();
    }

    public abstract /* synthetic */ void queueInput(ByteBuffer byteBuffer);

    public final void reset() {
        flush();
        this.f = AudioProcessor.a;
        AudioProcessor.AudioFormat audioFormat = AudioProcessor.AudioFormat.e;
        this.d = audioFormat;
        this.e = audioFormat;
        this.b = audioFormat;
        this.c = audioFormat;
        c();
    }
}
