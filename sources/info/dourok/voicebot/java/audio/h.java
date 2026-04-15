package info.dourok.voicebot.java.audio;

import info.dourok.voicebot.java.audio.OpusEncoder;
import info.dourok.voicebot.java.audio.e;

public final class h implements Runnable {
    public final /* synthetic */ short[] c;
    public final /* synthetic */ OpusEncoder.c f;
    public final /* synthetic */ OpusEncoder g;

    public h(OpusEncoder opusEncoder, short[] sArr, e.a aVar) {
        this.g = opusEncoder;
        this.c = sArr;
        this.f = aVar;
    }

    public final void run() {
        this.g.a(this.c, this.f);
    }
}
