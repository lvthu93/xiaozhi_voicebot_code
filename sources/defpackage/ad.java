package defpackage;

import android.media.MediaPlayer;
import info.dourok.voicebot.java.receivers.AlarmReceiver;

/* renamed from: ad  reason: default package */
public final class ad implements MediaPlayer.OnErrorListener {
    public final /* synthetic */ AlarmReceiver a;

    public ad(AlarmReceiver alarmReceiver) {
        this.a = alarmReceiver;
    }

    public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        MediaPlayer mediaPlayer2 = AlarmReceiver.a;
        this.a.getClass();
        AlarmReceiver.b();
        return true;
    }
}
