package info.dourok.voicebot.java.audio;

import info.dourok.voicebot.java.audio.OpusDecoder;

public final class g implements Runnable {
    public final /* synthetic */ byte[] c;
    public final /* synthetic */ OpusDecoder.c f;
    public final /* synthetic */ OpusDecoder g;

    public g(OpusDecoder opusDecoder, byte[] bArr, OpusDecoder.c cVar) {
        this.g = opusDecoder;
        this.c = bArr;
        this.f = cVar;
    }

    public final void run() {
        this.g.a(this.c, this.f);
    }
}
