package defpackage;

import info.dourok.voicebot.java.activities.ChatActivity;
import info.dourok.voicebot.java.audio.b;

/* renamed from: bl  reason: default package */
public final class bl implements cz {
    public final /* synthetic */ cz a;
    public final /* synthetic */ b b;

    public bl(b bVar, ChatActivity.C0034AnonymousClass9 anonymousClass9) {
        this.b = bVar;
        this.a = anonymousClass9;
    }

    public final void onAudioData(byte[] bArr) {
        this.b.g += (long) bArr.length;
        cz czVar = this.a;
        if (czVar != null) {
            czVar.onAudioData(bArr);
        }
    }

    public final void onError(String str) {
        this.b.d = false;
        cz czVar = this.a;
        if (czVar != null) {
            czVar.onError(str);
        }
    }

    public final void onRecordingStarted() {
        this.b.d = true;
        cz czVar = this.a;
        if (czVar != null) {
            czVar.onRecordingStarted();
        }
    }

    public final void onRecordingStopped() {
        double d;
        b bVar = this.b;
        bVar.d = false;
        cz czVar = this.a;
        if (czVar != null) {
            czVar.onRecordingStopped();
        }
        bVar.getClass();
        long currentTimeMillis = System.currentTimeMillis() - bVar.e;
        long j = bVar.f;
        if (j > 0) {
            d = (double) (bVar.g / (j * 320));
        } else {
            d = 0.0d;
        }
        String.format("Session Complete - Duration: %d ms, Frames: %d, Compressed: %d bytes, Ratio: %.2f", new Object[]{Long.valueOf(currentTimeMillis), Long.valueOf(bVar.f), Long.valueOf(bVar.g), Double.valueOf(d)});
    }

    public final void onVoiceActivityDetected(boolean z) {
        cz czVar = this.a;
        if (czVar != null) {
            czVar.onVoiceActivityDetected(z);
        }
    }
}
