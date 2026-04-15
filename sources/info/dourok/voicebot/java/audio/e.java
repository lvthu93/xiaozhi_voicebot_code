package info.dourok.voicebot.java.audio;

import android.media.AudioRecord;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.NoiseSuppressor;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import info.dourok.voicebot.java.audio.OpusEncoder;

public final class e {
    public AcousticEchoCanceler a;
    public AudioRecord b;
    public final int c = (AudioRecord.getMinBufferSize(16000, 12, 2) * 4);
    public final int d = 12;
    public final int e = 960;
    public NoiseSuppressor f;
    public final OpusEncoder g;
    public cx h;
    public Handler i;
    public HandlerThread j;
    public volatile boolean k = false;
    public long l = 0;
    public long m = 0;

    public class a implements OpusEncoder.c {
        public final /* synthetic */ cz a;

        public a(long j, cz czVar) {
            this.a = czVar;
        }
    }

    public class b implements Runnable {
        public b() {
        }

        public final void run() {
            e eVar = e.this;
            eVar.getClass();
            try {
                AudioRecord audioRecord = eVar.b;
                if (audioRecord != null) {
                    audioRecord.release();
                    eVar.b = null;
                    Log.d("AudioRecorder", "AudioRecord released");
                }
                AcousticEchoCanceler acousticEchoCanceler = eVar.a;
                if (acousticEchoCanceler != null) {
                    acousticEchoCanceler.setEnabled(false);
                    eVar.a.release();
                    eVar.a = null;
                }
                NoiseSuppressor noiseSuppressor = eVar.f;
                if (noiseSuppressor != null) {
                    noiseSuppressor.setEnabled(false);
                    eVar.f.release();
                    eVar.f = null;
                }
            } catch (Exception unused) {
            }
        }
    }

    public e(OpusEncoder opusEncoder) {
        this.g = opusEncoder;
        HandlerThread handlerThread = new HandlerThread("AudioRecordingThread", -19);
        this.j = handlerThread;
        handlerThread.start();
        this.i = new Handler(this.j.getLooper());
    }

    public final void a() {
        c();
        OpusEncoder opusEncoder = this.g;
        if (opusEncoder != null) {
            opusEncoder.d();
        }
        HandlerThread handlerThread = this.j;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.j.join();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public final void b(cz czVar) {
        short[] sArr;
        double d2;
        cz czVar2 = czVar;
        int i2 = this.e * 2;
        short[] sArr2 = new short[i2];
        while (true) {
            if (!this.k) {
                Log.d("AudioRecorder", "Recording loop: isRecording=false, exiting");
                break;
            }
            AudioRecord audioRecord = this.b;
            if (audioRecord == null) {
                Log.d("AudioRecorder", "Recording loop: audioRecord=null, exiting");
                break;
            } else if (audioRecord.getRecordingState() != 3) {
                Log.d("AudioRecorder", "Recording loop: not in RECORDING state, exiting");
                break;
            } else {
                int read = this.b.read(sArr2, 0, i2);
                if (read > 0) {
                    long j2 = this.l + ((long) read);
                    this.l = j2;
                    int i3 = this.e;
                    if (j2 > ((long) (i3 * 5))) {
                        long j3 = j2 % ((long) (i3 * 100));
                    }
                    for (int i4 = 0; i4 < read; i4++) {
                        sArr2[i4] = (short) Math.max(-32768, Math.min(32767, (int) (((float) sArr2[i4]) * 2.0f)));
                    }
                    long j4 = this.l;
                    OpusEncoder opusEncoder = this.g;
                    a aVar = new a(j4, czVar2);
                    if (opusEncoder.a == 0) {
                        czVar2.onError("Encoding error: Encoder not initialized");
                    } else {
                        opusEncoder.b.execute(new h(opusEncoder, sArr2, aVar));
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - this.m >= 5000) {
                        cx cxVar = this.h;
                        if (cxVar != null) {
                            f fVar = new f(this);
                            OpusEncoder opusEncoder2 = this.g;
                            if (opusEncoder2.a == 0) {
                                b bVar = ((bk) cxVar).a;
                                bVar.f = 0;
                                bVar.g = 0;
                                sArr = sArr2;
                                long currentTimeMillis2 = System.currentTimeMillis() - bVar.e;
                                long j5 = bVar.g;
                                if (j5 > 0) {
                                    d2 = ((((double) j5) * 8.0d) * 1000.0d) / ((double) currentTimeMillis2);
                                } else {
                                    d2 = 0.0d;
                                }
                                String.format("Audio Performance - Frames: %d, Bytes: %d, Avg Bitrate: %.1f bps, Duration: %d ms", new Object[]{Long.valueOf(bVar.f), Long.valueOf(bVar.g), Double.valueOf(d2), Long.valueOf(currentTimeMillis2)});
                            } else {
                                sArr = sArr2;
                                opusEncoder2.b.execute(new i(opusEncoder2, fVar));
                            }
                            long j6 = (this.l * 1000) / ((long) 32000);
                            ((bk) this.h).a.getClass();
                        } else {
                            sArr = sArr2;
                        }
                        this.m = currentTimeMillis;
                        sArr2 = sArr;
                    }
                } else if (read == -3) {
                    czVar2.onError("AudioRecord invalid operation");
                    break;
                } else if (read == -2) {
                    czVar2.onError("AudioRecord bad value");
                    break;
                } else if (read == 0) {
                    Log.d("AudioRecorder", "Recording loop: read returned 0 (stopped), exiting");
                    break;
                }
            }
        }
        Log.d("AudioRecorder", "Recording loop exited, notifying callback");
        czVar.onRecordingStopped();
    }

    public final void c() {
        Log.d("AudioRecorder", "stopRecording called, isRecording=" + this.k);
        synchronized (this) {
            if (this.k) {
                this.k = false;
                try {
                    AudioRecord audioRecord = this.b;
                    if (audioRecord != null && audioRecord.getState() == 1) {
                        this.b.stop();
                        Log.d("AudioRecorder", "AudioRecord stopped (unblocked read)");
                    }
                } catch (Exception e2) {
                    e2.getMessage();
                }
                this.i.post(new b());
            }
        }
    }
}
