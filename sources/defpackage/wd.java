package defpackage;

import android.media.MediaPlayer;

/* renamed from: wd  reason: default package */
public final /* synthetic */ class wd implements MediaPlayer.OnErrorListener {
    public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        return mediaPlayer.release();
    }
}
