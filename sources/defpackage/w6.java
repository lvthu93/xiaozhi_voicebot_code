package defpackage;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.io.ByteArrayOutputStream;

/* renamed from: w6  reason: default package */
public final class w6 {
    public AcousticEchoCanceler a;
    public AutomaticGainControl b;
    public AudioRecord c;
    public AudioTrack d;
    public final Context e;
    public d f;
    public NoiseSuppressor g;
    public Handler h;
    public HandlerThread i;
    public ByteArrayOutputStream j;
    public Handler k;
    public long l;
    public HandlerThread m;
    public e n = e.IDLE;
    public volatile boolean o = false;
    public volatile boolean p = false;
    public final Handler q = new Handler(Looper.getMainLooper());

    /* renamed from: w6$a */
    public class a implements Runnable {
        public a() {
        }

        public final void run() {
            w6 w6Var = w6.this;
            w6Var.getClass();
            e eVar = e.IDLE;
            try {
                byte[] byteArray = w6Var.j.toByteArray();
                int length = byteArray.length;
                int i = ((length / 4) * 1000) / 16000;
                AudioTrack audioTrack = new AudioTrack(new AudioAttributes.Builder().setUsage(1).setContentType(1).build(), new AudioFormat.Builder().setSampleRate(16000).setChannelMask(12).setEncoding(2).build(), AudioTrack.getMinBufferSize(16000, 12, 2) * 2, 1, 0);
                w6Var.d = audioTrack;
                if (audioTrack.getState() != 1) {
                    w6Var.b("AudioTrack initialization failed");
                    w6Var.d(eVar);
                    return;
                }
                w6Var.d.setStereoVolume(1.0f, 1.0f);
                w6Var.d.play();
                w6Var.p = true;
                w6Var.d(e.PLAYING);
                int i2 = 0;
                while (true) {
                    if (!w6Var.p) {
                        break;
                    } else if (i2 >= length) {
                        break;
                    } else {
                        int write = w6Var.d.write(byteArray, i2, Math.min(6400, length - i2));
                        if (write > 0) {
                            i2 += write;
                            w6Var.q.post(new x6(w6Var, ((i2 / 4) * 1000) / 16000, i));
                        } else if (write < 0) {
                            break;
                        }
                    }
                }
                if (w6Var.p) {
                    w6Var.d.flush();
                }
                w6Var.d.stop();
                w6Var.d.release();
                w6Var.d = null;
                w6Var.p = false;
                w6Var.d(eVar);
            } catch (Exception e) {
                e.getMessage();
                w6Var.b("Playback failed: " + e.getMessage());
                w6Var.d(eVar);
            }
        }
    }

    /* renamed from: w6$b */
    public class b implements Runnable {
        public final /* synthetic */ e c;

        public b(e eVar) {
            this.c = eVar;
        }

        public final void run() {
            d dVar = w6.this.f;
            if (dVar != null) {
                dVar.onStateChanged(this.c);
            }
        }
    }

    /* renamed from: w6$c */
    public class c implements Runnable {
        public final /* synthetic */ String c;

        public c(String str) {
            this.c = str;
        }

        public final void run() {
            d dVar = w6.this.f;
            if (dVar != null) {
                dVar.onError(this.c);
            }
        }
    }

    /* renamed from: w6$d */
    public interface d {
        void onError(String str);

        void onPlaybackProgress(int i, int i2);

        void onRecordingProgress(int i, float f);

        void onStateChanged(e eVar);
    }

    /* renamed from: w6$e */
    public enum e {
        IDLE,
        RECORDING,
        PLAYING
    }

    public w6(Context context) {
        this.e = context.getApplicationContext();
        AcousticEchoCanceler.isAvailable();
        NoiseSuppressor.isAvailable();
        AutomaticGainControl.isAvailable();
    }

    public final void a() {
        AudioRecord audioRecord = this.c;
        if (audioRecord != null) {
            int audioSessionId = audioRecord.getAudioSessionId();
            if (AcousticEchoCanceler.isAvailable()) {
                try {
                    AcousticEchoCanceler create = AcousticEchoCanceler.create(audioSessionId);
                    this.a = create;
                    if (create != null) {
                        create.setEnabled(true);
                    }
                } catch (Exception e2) {
                    e2.getMessage();
                }
            }
            if (NoiseSuppressor.isAvailable()) {
                try {
                    NoiseSuppressor create2 = NoiseSuppressor.create(audioSessionId);
                    this.g = create2;
                    if (create2 != null) {
                        create2.setEnabled(true);
                    }
                } catch (Exception e3) {
                    e3.getMessage();
                }
            }
            if (AutomaticGainControl.isAvailable()) {
                try {
                    AutomaticGainControl create3 = AutomaticGainControl.create(audioSessionId);
                    this.b = create3;
                    if (create3 != null) {
                        create3.setEnabled(true);
                    }
                } catch (Exception e4) {
                    e4.getMessage();
                }
            }
        }
    }

    public final void b(String str) {
        this.q.post(new c(str));
    }

    public final void c() {
        AcousticEchoCanceler acousticEchoCanceler = this.a;
        if (acousticEchoCanceler != null) {
            try {
                acousticEchoCanceler.setEnabled(false);
                this.a.release();
            } catch (Exception e2) {
                e2.getMessage();
            }
            this.a = null;
        }
        NoiseSuppressor noiseSuppressor = this.g;
        if (noiseSuppressor != null) {
            try {
                noiseSuppressor.setEnabled(false);
                this.g.release();
            } catch (Exception e3) {
                e3.getMessage();
            }
            this.g = null;
        }
        AutomaticGainControl automaticGainControl = this.b;
        if (automaticGainControl != null) {
            try {
                automaticGainControl.setEnabled(false);
                this.b.release();
            } catch (Exception e4) {
                e4.getMessage();
            }
            this.b = null;
        }
    }

    public final void d(e eVar) {
        this.n = eVar;
        this.q.post(new b(eVar));
    }

    public final void e() {
        AudioManager audioManager;
        ByteArrayOutputStream byteArrayOutputStream = this.j;
        if (byteArrayOutputStream == null || byteArrayOutputStream.size() == 0) {
            b("No audio recorded");
            d(e.IDLE);
            return;
        }
        Context context = this.e;
        if (!(context == null || (audioManager = (AudioManager) context.getSystemService("audio")) == null)) {
            audioManager.setStreamVolume(3, (audioManager.getStreamMaxVolume(3) * 100) / 100, 0);
        }
        this.j.size();
        HandlerThread handlerThread = this.i;
        if (handlerThread == null || !handlerThread.isAlive()) {
            HandlerThread handlerThread2 = new HandlerThread("MicTesterPlayback", -19);
            this.i = handlerThread2;
            handlerThread2.start();
            this.h = new Handler(this.i.getLooper());
        }
        this.h.post(new a());
    }

    public final void f() {
        int ordinal = this.n.ordinal();
        if (ordinal == 0) {
            HandlerThread handlerThread = this.m;
            if (handlerThread == null || !handlerThread.isAlive()) {
                HandlerThread handlerThread2 = new HandlerThread("MicTesterRecording", -19);
                this.m = handlerThread2;
                handlerThread2.start();
                this.k = new Handler(this.m.getLooper());
            }
            this.k.post(new s6(this));
        } else if (ordinal == 1) {
            this.o = false;
            Handler handler = this.k;
            if (handler != null) {
                handler.post(new u6(this));
            } else {
                e();
            }
        } else if (ordinal == 2) {
            this.p = false;
            AudioTrack audioTrack = this.d;
            if (audioTrack != null) {
                try {
                    audioTrack.stop();
                } catch (Exception e2) {
                    e2.getMessage();
                }
            }
            d(e.IDLE);
        }
    }
}
