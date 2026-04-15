package info.dourok.voicebot.java.audio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class OpusDecoder {
    public static final /* synthetic */ int c = 0;
    public long a = 0;
    public final ExecutorService b = Executors.newSingleThreadExecutor(new a());

    public class a implements ThreadFactory {
        public a() {
        }

        public final Thread newThread(Runnable runnable) {
            int i = OpusDecoder.c;
            Thread thread = new Thread(runnable, "OpusDecoder");
            thread.setPriority(6);
            return thread;
        }
    }

    public class b implements Runnable {
        public b() {
        }

        public final void run() {
            OpusDecoder.this.b();
        }
    }

    public interface c {
        void a(short[] sArr);

        void onError(String str);
    }

    static {
        System.loadLibrary("voicebot");
    }

    public OpusDecoder(int i, int i2) {
        int i3 = (i2 * i) / 1000;
        long nativeInitDecoder = nativeInitDecoder(i, 1);
        this.a = nativeInitDecoder;
        if (nativeInitDecoder == 0) {
            throw new IllegalStateException("Failed to initialize Opus decoder");
        }
    }

    private native short[] nativeDecode(long j, byte[] bArr);

    private native long nativeInitDecoder(int i, int i2);

    private native void nativeReleaseDecoder(long j);

    public final void a(byte[] bArr, c cVar) {
        try {
            short[] nativeDecode = nativeDecode(this.a, bArr);
            if (nativeDecode != null) {
                cVar.a(nativeDecode);
            } else {
                cVar.onError("Decoding failed");
            }
        } catch (Exception e) {
            cVar.onError("Decoding error: " + e.getMessage());
        }
    }

    public final void b() {
        nativeReleaseDecoder(this.a);
        this.a = 0;
    }

    public final void c() {
        long j = this.a;
        ExecutorService executorService = this.b;
        if (j != 0) {
            executorService.execute(new b());
        }
        executorService.shutdown();
    }

    public final void finalize() throws Throwable {
        super.finalize();
        c();
    }
}
