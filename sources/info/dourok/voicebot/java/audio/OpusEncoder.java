package info.dourok.voicebot.java.audio;

import info.dourok.voicebot.java.audio.e;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class OpusEncoder {
    public static final /* synthetic */ int c = 0;
    public long a = 0;
    public final ExecutorService b = Executors.newSingleThreadExecutor(new a());

    public class a implements ThreadFactory {
        public a() {
        }

        public final Thread newThread(Runnable runnable) {
            int i = OpusEncoder.c;
            Thread thread = new Thread(runnable, "OpusEncoder");
            thread.setPriority(10);
            return thread;
        }
    }

    public class b implements Runnable {
        public b() {
        }

        public final void run() {
            OpusEncoder.this.c();
        }
    }

    public interface c {
    }

    public interface d {
    }

    static {
        System.loadLibrary("voicebot");
    }

    public OpusEncoder() {
        long nativeInitEnhancedEncoder = nativeInitEnhancedEncoder(16000, 2, 2048);
        this.a = nativeInitEnhancedEncoder;
        if (nativeInitEnhancedEncoder == 0) {
            throw new IllegalStateException("Failed to initialize enhanced Opus encoder");
        }
    }

    private native byte[] nativeEncodeWithVAD(long j, short[] sArr);

    private native long nativeGetEncodingStats(long j);

    private native long nativeInitEnhancedEncoder(int i, int i2, int i3);

    private native void nativeReleaseEnhancedEncoder(long j);

    public final void a(short[] sArr, c cVar) {
        try {
            byte[] nativeEncodeWithVAD = nativeEncodeWithVAD(this.a, sArr);
            if (nativeEncodeWithVAD == null) {
                nativeEncodeWithVAD = new byte[0];
            }
            e.a aVar = (e.a) cVar;
            int i = e.this.e;
            int length = nativeEncodeWithVAD.length;
            cz czVar = aVar.a;
            if (length > 0) {
                czVar.onAudioData(nativeEncodeWithVAD);
                czVar.onVoiceActivityDetected(true);
                return;
            }
            czVar.onVoiceActivityDetected(false);
        } catch (Exception e) {
            e.a aVar2 = (e.a) cVar;
            aVar2.getClass();
            aVar2.a.onError("Encoding error: " + ("Encoding failed: " + e.getMessage()));
        }
    }

    public final void b(d dVar) {
        double d2;
        long nativeGetEncodingStats = nativeGetEncodingStats(this.a);
        long j = (nativeGetEncodingStats >> 32) & 4294967295L;
        b bVar = ((bk) ((f) dVar).a.h).a;
        bVar.f = j;
        bVar.g = nativeGetEncodingStats & 4294967295L;
        if (j % 100 == 0) {
            long currentTimeMillis = System.currentTimeMillis() - bVar.e;
            long j2 = bVar.g;
            if (j2 > 0) {
                d2 = ((((double) j2) * 8.0d) * 1000.0d) / ((double) currentTimeMillis);
            } else {
                d2 = 0.0d;
            }
            String.format("Audio Performance - Frames: %d, Bytes: %d, Avg Bitrate: %.1f bps, Duration: %d ms", new Object[]{Long.valueOf(bVar.f), Long.valueOf(bVar.g), Double.valueOf(d2), Long.valueOf(currentTimeMillis)});
        }
    }

    public final void c() {
        nativeReleaseEnhancedEncoder(this.a);
        this.a = 0;
    }

    public final void d() {
        long j = this.a;
        ExecutorService executorService = this.b;
        if (j != 0) {
            executorService.execute(new b());
        }
        executorService.shutdown();
    }

    public final void finalize() throws Throwable {
        super.finalize();
        d();
    }
}
