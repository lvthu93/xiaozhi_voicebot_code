package info.dourok.voicebot.java.audio;

import info.dourok.voicebot.java.audio.OpusDecoder;

public final class d implements OpusDecoder.c {
    public final /* synthetic */ c a;

    public d(c cVar) {
        this.a = cVar;
    }

    public final void a(short[] sArr) {
        if (sArr.length > 0) {
            c cVar = this.a;
            cVar.e.offer(sArr);
            if (cVar.c.compareAndSet(false, true)) {
                cVar.f.execute(new bn(cVar));
            }
        }
    }

    public final void onError(String str) {
    }
}
