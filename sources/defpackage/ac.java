package defpackage;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import info.dourok.voicebot.java.receivers.AlarmReceiver;

/* renamed from: ac  reason: default package */
public final class ac implements MediaPlayer.OnCompletionListener {
    public final /* synthetic */ Context a;
    public final /* synthetic */ AlarmReceiver b;

    public ac(AlarmReceiver alarmReceiver, Context context) {
        this.b = alarmReceiver;
        this.a = context;
    }

    public final void onCompletion(MediaPlayer mediaPlayer) {
        Context context;
        AudioManager audioManager;
        MediaPlayer mediaPlayer2 = AlarmReceiver.a;
        this.b.getClass();
        AlarmReceiver.b();
        if (!(AlarmReceiver.c < 0 || (context = this.a) == null || (audioManager = (AudioManager) context.getSystemService("audio")) == null)) {
            audioManager.setStreamVolume(3, AlarmReceiver.c, 0);
            AlarmReceiver.c = -1;
        }
        PowerManager.WakeLock wakeLock = AlarmReceiver.b;
        if (wakeLock != null && wakeLock.isHeld()) {
            AlarmReceiver.b.release();
            AlarmReceiver.b = null;
        }
    }
}
