package defpackage;

import android.media.MediaPlayer;

/* renamed from: vd  reason: default package */
public final /* synthetic */ class vd implements MediaPlayer.OnCompletionListener {
    public final void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.release();
    }
}
