package info.dourok.voicebot.java.utils;

import android.content.Context;
import android.media.AudioRecord;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.aiboxplus.sample_wake_word.MicroWakeWordEngine;
import info.dourok.voicebot.java.utils.a;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MicroWakeWordDetector implements a {
    private static final int AUDIO_FORMAT = 2;
    private static final int AUDIO_RATE = 16000;
    private static final int CHANNEL_CONFIG = 16;
    private static final int FRAME_SIZE = 512;
    private static final String TAG = "MicroWakeWordDetector";
    private Context appContext;
    private AudioRecord audioRecord;
    private Handler callbackHandler;
    private short[] chunkBuffer;
    private boolean isEnabled = true;
    private boolean isInitialized = false;
    private volatile boolean isListening = false;
    /* access modifiers changed from: private */
    public a.C0024a listener;
    private Handler recordingHandler;
    private HandlerThread recordingThread;
    private MicroWakeWordEngine wakeWordEngine;

    static {
        try {
            System.loadLibrary("micro_wake_word_jni");
        } catch (UnsatisfiedLinkError e) {
            e.getMessage();
        }
    }

    private void initMicroWakeWord() {
        try {
            MicroWakeWordEngine microWakeWordEngine = new MicroWakeWordEngine();
            this.wakeWordEngine = microWakeWordEngine;
            microWakeWordEngine.setDetectionListener(new MicroWakeWordEngine.DetectionListener() {
                public void onWakeWordDetected(String str) {
                    MicroWakeWordDetector.this.notifyListener();
                }
            });
            if (this.wakeWordEngine.initialize()) {
                this.isInitialized = true;
            } else {
                this.isInitialized = false;
            }
        } catch (Exception e) {
            e.getMessage();
            this.isInitialized = false;
        }
    }

    /* access modifiers changed from: private */
    public void notifyListener() {
        Handler handler;
        if (this.listener != null && (handler = this.callbackHandler) != null) {
            handler.post(new Runnable() {
                public void run() {
                    if (MicroWakeWordDetector.this.listener != null) {
                        MicroWakeWordDetector.this.listener.onWakeWordDetected();
                    }
                }
            });
        }
    }

    private boolean processSamplesInChunks(short[] sArr, int i) {
        boolean processAudioSamples;
        if (!this.isInitialized || !this.isEnabled || this.wakeWordEngine == null || i <= 0) {
            return false;
        }
        int i2 = 0;
        boolean z = false;
        while (i2 < i) {
            int min = Math.min(512, i - i2);
            if (i2 == 0 && min == i) {
                processAudioSamples = this.wakeWordEngine.processAudioSamples(sArr, min);
            } else {
                short[] sArr2 = this.chunkBuffer;
                if (sArr2 == null || sArr2.length < min) {
                    this.chunkBuffer = new short[512];
                }
                System.arraycopy(sArr, i2, this.chunkBuffer, 0, min);
                processAudioSamples = this.wakeWordEngine.processAudioSamples(this.chunkBuffer, min);
            }
            z |= processAudioSamples;
            i2 += min;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void startListeningInternal() {
        try {
            AudioRecord audioRecord2 = new AudioRecord(1, AUDIO_RATE, 16, 2, AudioRecord.getMinBufferSize(AUDIO_RATE, 16, 2) * 2);
            this.audioRecord = audioRecord2;
            if (audioRecord2.getState() != 1) {
                this.isListening = false;
                return;
            }
            this.audioRecord.startRecording();
            MicroWakeWordEngine microWakeWordEngine = this.wakeWordEngine;
            if (microWakeWordEngine != null) {
                microWakeWordEngine.reset();
            }
            short[] sArr = new short[512];
            long j = 0;
            while (this.isListening && this.isEnabled) {
                int read = this.audioRecord.read(sArr, 0, 512);
                if (read > 0) {
                    j++;
                    if (j % 100 == 0) {
                        Log.d(TAG, "Processed frames: " + j);
                    }
                    processSamplesInChunks(sArr, read);
                } else if (read < 0) {
                    break;
                }
            }
            AudioRecord audioRecord3 = this.audioRecord;
            if (audioRecord3 != null) {
                audioRecord3.stop();
                this.audioRecord.release();
                this.audioRecord = null;
            }
        } catch (Exception e) {
            e.getMessage();
            this.isListening = false;
        }
    }

    public void init(Context context) {
        if (!this.isInitialized) {
            this.appContext = context.getApplicationContext();
            this.callbackHandler = new Handler(context.getMainLooper());
            initMicroWakeWord();
        }
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public boolean isListening() {
        return this.isListening;
    }

    public boolean isReady() {
        return this.isInitialized && this.wakeWordEngine != null;
    }

    public boolean processAudio(byte[] bArr) {
        if (!(bArr == null || bArr.length == 0)) {
            try {
                int length = bArr.length / 2;
                short[] sArr = new short[length];
                ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(sArr);
                return processSamplesInChunks(sArr, length);
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return false;
    }

    public void reset() {
        MicroWakeWordEngine microWakeWordEngine = this.wakeWordEngine;
        if (microWakeWordEngine != null) {
            microWakeWordEngine.reset();
        }
    }

    public void setEnabled(boolean z) {
        this.isEnabled = z;
        if (z && this.isInitialized && isReady() && !this.isListening) {
            startListening();
        }
        if (!z && this.isListening) {
            stopListening();
        }
    }

    public void setSensitivity(float f) {
        Log.d(TAG, "Sensitivity setting set to " + f);
        MicroWakeWordEngine microWakeWordEngine = this.wakeWordEngine;
        if (microWakeWordEngine != null) {
            microWakeWordEngine.setProbabilityCutoff(f);
        }
    }

    public void setWakeWordListener(a.C0024a aVar) {
        this.listener = aVar;
    }

    public void shutdown() {
        stopListening();
        HandlerThread handlerThread = this.recordingThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            this.recordingThread = null;
        }
        MicroWakeWordEngine microWakeWordEngine = this.wakeWordEngine;
        if (microWakeWordEngine != null) {
            microWakeWordEngine.destroy();
            this.wakeWordEngine = null;
        }
        this.isInitialized = false;
        this.listener = null;
    }

    public void startListening() {
        if (!this.isInitialized || this.isListening || !this.isEnabled) {
            Log.d(TAG, "Cannot start listening: initialized=" + this.isInitialized + ", already listening=" + this.isListening + ", enabled=" + this.isEnabled);
            return;
        }
        this.isListening = true;
        HandlerThread handlerThread = this.recordingThread;
        if (handlerThread == null || !handlerThread.isAlive()) {
            HandlerThread handlerThread2 = new HandlerThread("WakeWordRecording", -19);
            this.recordingThread = handlerThread2;
            handlerThread2.start();
            this.recordingHandler = new Handler(this.recordingThread.getLooper());
        }
        this.recordingHandler.post(new Runnable() {
            public void run() {
                MicroWakeWordDetector.this.startListeningInternal();
            }
        });
    }

    public void stopListening() {
        if (this.isListening) {
            this.isListening = false;
        }
    }

    public boolean processAudio(short[] sArr) {
        if (sArr == null || sArr.length == 0) {
            return false;
        }
        return processSamplesInChunks(sArr, sArr.length);
    }
}
