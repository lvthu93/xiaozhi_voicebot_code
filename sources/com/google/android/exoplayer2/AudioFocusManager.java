package com.google.android.exoplayer2;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class AudioFocusManager {
    public final AudioManager a;
    public final a b;
    @Nullable
    public PlayerControl c;
    @Nullable
    public AudioAttributes d;
    public int e;
    public int f;
    public float g = 1.0f;
    public AudioFocusRequest h;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayerCommand {
    }

    public interface PlayerControl {
        void executePlayerCommand(int i);

        void setVolumeMultiplier(float f);
    }

    public class a implements AudioManager.OnAudioFocusChangeListener {
        public final Handler c;

        public a(Handler handler) {
            this.c = handler;
        }

        public void onAudioFocusChange(int i) {
            this.c.post(new bi(this, i));
        }
    }

    public AudioFocusManager(Context context, Handler handler, PlayerControl playerControl) {
        this.a = (AudioManager) Assertions.checkNotNull((AudioManager) context.getApplicationContext().getSystemService("audio"));
        this.c = playerControl;
        this.b = new a(handler);
        this.e = 0;
    }

    public final void a() {
        if (this.e != 0) {
            int i = Util.a;
            AudioManager audioManager = this.a;
            if (i >= 26) {
                AudioFocusRequest audioFocusRequest = this.h;
                if (audioFocusRequest != null) {
                    audioManager.abandonAudioFocusRequest(audioFocusRequest);
                }
            } else {
                audioManager.abandonAudioFocus(this.b);
            }
            b(0);
        }
    }

    public final void b(int i) {
        float f2;
        if (this.e != i) {
            this.e = i;
            if (i == 3) {
                f2 = 0.2f;
            } else {
                f2 = 1.0f;
            }
            if (this.g != f2) {
                this.g = f2;
                PlayerControl playerControl = this.c;
                if (playerControl != null) {
                    playerControl.setVolumeMultiplier(f2);
                }
            }
        }
    }

    public float getVolumeMultiplier() {
        return this.g;
    }

    public void release() {
        this.c = null;
        a();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0029, code lost:
        if (r6.c == 1) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0033, code lost:
        r3 = 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setAudioAttributes(@androidx.annotation.Nullable com.google.android.exoplayer2.audio.AudioAttributes r6) {
        /*
            r5 = this;
            com.google.android.exoplayer2.audio.AudioAttributes r0 = r5.d
            boolean r0 = com.google.android.exoplayer2.util.Util.areEqual(r0, r6)
            if (r0 != 0) goto L_0x0042
            r5.d = r6
            r0 = 0
            r1 = 1
            if (r6 != 0) goto L_0x000f
            goto L_0x0035
        L_0x000f:
            java.lang.String r2 = "AudioFocusManager"
            r3 = 2
            int r4 = r6.g
            switch(r4) {
                case 0: goto L_0x002e;
                case 1: goto L_0x0033;
                case 2: goto L_0x0036;
                case 3: goto L_0x0035;
                case 4: goto L_0x0036;
                case 5: goto L_0x002c;
                case 6: goto L_0x002c;
                case 7: goto L_0x002c;
                case 8: goto L_0x002c;
                case 9: goto L_0x002c;
                case 10: goto L_0x002c;
                case 11: goto L_0x0027;
                case 12: goto L_0x002c;
                case 13: goto L_0x002c;
                case 14: goto L_0x0033;
                case 15: goto L_0x0017;
                case 16: goto L_0x001f;
                default: goto L_0x0017;
            }
        L_0x0017:
            r6 = 37
            java.lang.String r3 = "Unidentified audio usage: "
            defpackage.y2.t(r6, r3, r4, r2)
            goto L_0x0035
        L_0x001f:
            int r6 = com.google.android.exoplayer2.util.Util.a
            r2 = 19
            if (r6 < r2) goto L_0x0036
            r3 = 4
            goto L_0x0036
        L_0x0027:
            int r6 = r6.c
            if (r6 != r1) goto L_0x002c
            goto L_0x0036
        L_0x002c:
            r3 = 3
            goto L_0x0036
        L_0x002e:
            java.lang.String r6 = "Specify a proper usage in the audio attributes for audio focus handling. Using AUDIOFOCUS_GAIN by default."
            com.google.android.exoplayer2.util.Log.w(r2, r6)
        L_0x0033:
            r3 = 1
            goto L_0x0036
        L_0x0035:
            r3 = 0
        L_0x0036:
            r5.f = r3
            if (r3 == r1) goto L_0x003c
            if (r3 != 0) goto L_0x003d
        L_0x003c:
            r0 = 1
        L_0x003d:
            java.lang.String r6 = "Automatic handling of audio focus is only available for USAGE_MEDIA and USAGE_GAME."
            com.google.android.exoplayer2.util.Assertions.checkArgument(r0, r6)
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.AudioFocusManager.setAudioAttributes(com.google.android.exoplayer2.audio.AudioAttributes):void");
    }

    public int updateAudioFocus(boolean z, int i) {
        boolean z2;
        int i2;
        AudioFocusRequest.Builder builder;
        boolean z3;
        int i3 = 1;
        if (i == 1 || this.f != 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            a();
            if (z) {
                return 1;
            }
            return -1;
        } else if (!z) {
            return -1;
        } else {
            if (this.e != 1) {
                int i4 = Util.a;
                a aVar = this.b;
                AudioManager audioManager = this.a;
                if (i4 >= 26) {
                    AudioFocusRequest audioFocusRequest = this.h;
                    if (audioFocusRequest == null) {
                        if (audioFocusRequest == null) {
                            builder = new AudioFocusRequest.Builder(this.f);
                        } else {
                            builder = new AudioFocusRequest.Builder(this.h);
                        }
                        AudioAttributes audioAttributes = this.d;
                        if (audioAttributes == null || audioAttributes.c != 1) {
                            z3 = false;
                        } else {
                            z3 = true;
                        }
                        this.h = builder.setAudioAttributes(((AudioAttributes) Assertions.checkNotNull(audioAttributes)).getAudioAttributesV21()).setWillPauseWhenDucked(z3).setOnAudioFocusChangeListener(aVar).build();
                    }
                    i2 = audioManager.requestAudioFocus(this.h);
                } else {
                    i2 = audioManager.requestAudioFocus(aVar, Util.getStreamTypeForAudioUsage(((AudioAttributes) Assertions.checkNotNull(this.d)).g), this.f);
                }
                if (i2 == 1) {
                    b(1);
                } else {
                    b(0);
                    i3 = -1;
                }
            }
            return i3;
        }
    }
}
