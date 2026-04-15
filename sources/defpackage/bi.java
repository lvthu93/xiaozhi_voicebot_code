package defpackage;

import com.google.android.exoplayer2.AudioFocusManager;
import com.google.android.exoplayer2.audio.AudioAttributes;

/* renamed from: bi  reason: default package */
public final /* synthetic */ class bi implements Runnable {
    public final /* synthetic */ AudioFocusManager.a c;
    public final /* synthetic */ int f;

    public /* synthetic */ bi(AudioFocusManager.a aVar, int i) {
        this.c = aVar;
        this.f = i;
    }

    public final void run() {
        AudioFocusManager audioFocusManager = AudioFocusManager.this;
        audioFocusManager.getClass();
        boolean z = true;
        int i = this.f;
        if (i == -3 || i == -2) {
            if (i != -2) {
                AudioAttributes audioAttributes = audioFocusManager.d;
                if (audioAttributes == null || audioAttributes.c != 1) {
                    z = false;
                }
                if (!z) {
                    audioFocusManager.b(3);
                    return;
                }
            }
            AudioFocusManager.PlayerControl playerControl = audioFocusManager.c;
            if (playerControl != null) {
                playerControl.executePlayerCommand(0);
            }
            audioFocusManager.b(2);
        } else if (i == -1) {
            AudioFocusManager.PlayerControl playerControl2 = audioFocusManager.c;
            if (playerControl2 != null) {
                playerControl2.executePlayerCommand(-1);
            }
            audioFocusManager.a();
        } else if (i != 1) {
            y2.t(38, "Unknown focus change type: ", i, "AudioFocusManager");
        } else {
            audioFocusManager.b(1);
            AudioFocusManager.PlayerControl playerControl3 = audioFocusManager.c;
            if (playerControl3 != null) {
                playerControl3.executePlayerCommand(1);
            }
        }
    }
}
