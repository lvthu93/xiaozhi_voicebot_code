package defpackage;

import android.media.MediaPlayer;
import info.dourok.voicebot.java.receivers.AlarmReceiver;

/* renamed from: ae  reason: default package */
public final class ae implements MediaPlayer.OnPreparedListener {
    public final /* synthetic */ AlarmReceiver a;

    public ae(AlarmReceiver alarmReceiver, int i) {
        this.a = alarmReceiver;
    }

    public final void onPrepared(MediaPlayer mediaPlayer) {
        try {
            mediaPlayer.start();
        } catch (Exception unused) {
            MediaPlayer mediaPlayer2 = AlarmReceiver.a;
            this.a.getClass();
            AlarmReceiver.b();
        }
    }
}
