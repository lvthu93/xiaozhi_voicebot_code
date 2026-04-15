package defpackage;

import android.media.AudioRecord;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.NoiseSuppressor;
import info.dourok.voicebot.java.audio.e;

/* renamed from: bo  reason: default package */
public final class bo implements Runnable {
    public final /* synthetic */ cz c;
    public final /* synthetic */ e f;

    public bo(e eVar, bl blVar) {
        this.f = eVar;
        this.c = blVar;
    }

    public final void run() {
        e eVar = this.f;
        cz czVar = this.c;
        eVar.getClass();
        try {
            AudioRecord audioRecord = eVar.b;
            if (audioRecord != null) {
                try {
                    audioRecord.stop();
                    eVar.b.release();
                } catch (Exception e) {
                    e.getMessage();
                }
                eVar.b = null;
            }
            AudioRecord audioRecord2 = new AudioRecord(1, 16000, eVar.d, 2, eVar.c);
            eVar.b = audioRecord2;
            if (audioRecord2.getState() == 1) {
                AudioRecord audioRecord3 = eVar.b;
                if (audioRecord3 != null) {
                    int audioSessionId = audioRecord3.getAudioSessionId();
                    AcousticEchoCanceler.isAvailable();
                    NoiseSuppressor.isAvailable();
                    if (AcousticEchoCanceler.isAvailable()) {
                        try {
                            AcousticEchoCanceler create = AcousticEchoCanceler.create(audioSessionId);
                            eVar.a = create;
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
                            eVar.f = create2;
                            if (create2 != null) {
                                create2.setEnabled(true);
                            }
                        } catch (Exception e3) {
                            e3.getMessage();
                        }
                    }
                }
                eVar.b.startRecording();
                eVar.l = 0;
                eVar.m = System.currentTimeMillis();
                czVar.onRecordingStarted();
                eVar.b(czVar);
                return;
            }
            synchronized (eVar) {
                eVar.k = false;
            }
            throw new IllegalStateException("AudioRecord initialization failed");
        } catch (Exception e4) {
            synchronized (eVar) {
                eVar.k = false;
                czVar.onError("Failed to start recording: " + e4.getMessage());
            }
        }
    }
}
