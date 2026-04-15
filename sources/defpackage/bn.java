package defpackage;

import android.media.AudioTrack;
import android.util.Log;
import info.dourok.voicebot.java.audio.c;

/* renamed from: bn  reason: default package */
public final class bn implements Runnable {
    public final /* synthetic */ c c;

    public bn(c cVar) {
        this.c = cVar;
    }

    public final void run() {
        short[] poll;
        c cVar = this.c;
        cVar.getClass();
        Log.d("AudioPlayer", "Playback loop started");
        if (cVar.a.getPlayState() != 3) {
            cVar.a.play();
        }
        loop0:
        while (true) {
            int i = 0;
            while (true) {
                poll = cVar.e.poll();
                if (poll == null) {
                    i++;
                    if (!cVar.c.get() && i > 10) {
                        break loop0;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException unused) {
                    }
                } else {
                    break;
                }
            }
            cVar.a.write(poll, 0, poll.length);
        }
        Log.d("AudioPlayer", "Playback loop exiting - graceful stop after buffer drain");
        try {
            AudioTrack audioTrack = cVar.a;
            if (audioTrack != null && audioTrack.getPlayState() == 3) {
                Thread.sleep(50);
                cVar.a.stop();
                cVar.a.flush();
                Log.d("AudioPlayer", "AudioTrack stopped after buffer drain");
            }
        } catch (Exception unused2) {
        }
        Log.d("AudioPlayer", "Playback loop ended");
    }
}
