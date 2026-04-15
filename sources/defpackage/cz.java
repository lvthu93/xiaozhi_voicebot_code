package defpackage;

/* renamed from: cz  reason: default package */
public interface cz {
    void onAudioData(byte[] bArr);

    void onError(String str);

    void onRecordingStarted();

    void onRecordingStopped();

    void onVoiceActivityDetected(boolean z);
}
