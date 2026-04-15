package info.dourok.voicebot.java.audio;

import info.dourok.voicebot.java.audio.OpusEncoder;

public final class i implements Runnable {
    public final /* synthetic */ OpusEncoder.d c;
    public final /* synthetic */ OpusEncoder f;

    public i(OpusEncoder opusEncoder, f fVar) {
        this.f = opusEncoder;
        this.c = fVar;
    }

    public final void run() {
        this.f.b(this.c);
    }
}
