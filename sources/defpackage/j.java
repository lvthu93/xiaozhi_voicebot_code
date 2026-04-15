package defpackage;

import android.media.MediaPlayer;

/* renamed from: j  reason: default package */
public final class j implements MediaPlayer.OnErrorListener {
    public final /* synthetic */ h a;

    public j(h hVar) {
        this.a = hVar;
    }

    public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        h hVar = this.a;
        hVar.g++;
        hVar.c();
        return true;
    }
}
