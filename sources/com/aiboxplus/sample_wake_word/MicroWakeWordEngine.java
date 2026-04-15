package com.aiboxplus.sample_wake_word;

public class MicroWakeWordEngine {
    private long nativeHandle;

    public interface DetectionListener {
        void onWakeWordDetected(String str);
    }

    static {
        System.loadLibrary("micro_wake_word_jni");
    }

    public MicroWakeWordEngine() {
        this.nativeHandle = 0;
        this.nativeHandle = nativeCreate();
    }

    private native long nativeCreate();

    private native void nativeDestroy(long j);

    private native boolean nativeInitialize(long j);

    private native boolean nativeProcessAudioSamples(long j, short[] sArr, int i);

    private native void nativeReset(long j);

    private native void nativeSetDetectionListener(long j, DetectionListener detectionListener);

    private native void nativeSetProbabilityCutoff(long j, float f);

    public void destroy() {
        long j = this.nativeHandle;
        if (j != 0) {
            nativeDestroy(j);
            this.nativeHandle = 0;
        }
    }

    public void finalize() throws Throwable {
        try {
            destroy();
        } finally {
            super.finalize();
        }
    }

    public boolean initialize() {
        long j = this.nativeHandle;
        if (j == 0) {
            return false;
        }
        return nativeInitialize(j);
    }

    public boolean processAudioSamples(short[] sArr, int i) {
        long j = this.nativeHandle;
        if (j == 0) {
            return false;
        }
        return nativeProcessAudioSamples(j, sArr, i);
    }

    public void reset() {
        long j = this.nativeHandle;
        if (j != 0) {
            nativeReset(j);
        }
    }

    public void setDetectionListener(DetectionListener detectionListener) {
        long j = this.nativeHandle;
        if (j != 0) {
            nativeSetDetectionListener(j, detectionListener);
        }
    }

    public void setProbabilityCutoff(float f) {
        long j = this.nativeHandle;
        if (j != 0) {
            nativeSetProbabilityCutoff(j, f);
        }
    }
}
