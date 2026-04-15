package defpackage;

import android.media.MediaPlayer;
import android.util.Log;

/* renamed from: i  reason: default package */
public final class i implements MediaPlayer.OnCompletionListener {
    public final /* synthetic */ h a;

    public i(h hVar) {
        this.a = hVar;
    }

    public final void onCompletion(MediaPlayer mediaPlayer) {
        StringBuilder sb = new StringBuilder("onCompletion: currentIndex=");
        h hVar = this.a;
        sb.append(hVar.g);
        sb.append(", incrementing to ");
        sb.append(hVar.g + 1);
        Log.d("ActivationSoundPlayer", sb.toString());
        hVar.g++;
        hVar.d.postDelayed(new k(hVar), 10);
    }
}
