package info.dourok.voicebot.java.audio;

public final class b {
    public final e a;
    public final OpusDecoder b;
    public final OpusEncoder c;
    public boolean d = false;
    public long e = 0;
    public long f = 0;
    public long g = 0;

    public b(e eVar, OpusEncoder opusEncoder, OpusDecoder opusDecoder) {
        this.a = eVar;
        this.c = opusEncoder;
        this.b = opusDecoder;
        eVar.h = new bk(this);
    }

    public final void a() {
        if (this.d) {
            this.a.c();
        }
    }
}
