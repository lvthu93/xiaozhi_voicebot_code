package info.dourok.voicebot.java.audio;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.Build;
import android.util.Log;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

public final class c {
    public AudioTrack a;
    public final OpusDecoder b;
    public final AtomicBoolean c = new AtomicBoolean(false);
    public final AtomicBoolean d;
    public final ConcurrentLinkedQueue<short[]> e;
    public final ExecutorService f;

    public class a implements ThreadFactory {
        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "AudioPlayer");
            thread.setPriority(10);
            return thread;
        }
    }

    public c(OpusDecoder opusDecoder) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.d = atomicBoolean;
        this.e = new ConcurrentLinkedQueue<>();
        this.f = Executors.newSingleThreadExecutor(new a());
        this.b = opusDecoder;
        try {
            int max = Math.max(AudioTrack.getMinBufferSize(24000, 4, 2), 48000);
            if (Build.VERSION.SDK_INT >= 23) {
                AudioAttributes build = new AudioAttributes.Builder().setUsage(1).setContentType(1).build();
                this.a = new AudioTrack.Builder().setAudioAttributes(build).setAudioFormat(new AudioFormat.Builder().setSampleRate(24000).setChannelMask(4).setEncoding(2).build()).setBufferSizeInBytes(max).setTransferMode(1).build();
            } else {
                this.a = new AudioTrack(3, 24000, 4, 2, max, 1);
            }
            atomicBoolean.set(true);
        } catch (Exception unused) {
            atomicBoolean.set(false);
        }
    }

    public final void a() {
        this.c.set(false);
        this.e.clear();
        try {
            AudioTrack audioTrack = this.a;
            if (audioTrack != null && audioTrack.getPlayState() == 3) {
                this.a.stop();
                this.a.flush();
                Log.d("AudioPlayer", "AudioTrack force stopped");
            }
        } catch (Exception unused) {
        }
    }
}
