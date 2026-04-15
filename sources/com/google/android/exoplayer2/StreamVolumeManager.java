package com.google.android.exoplayer2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

public final class StreamVolumeManager {
    public final Context a;
    public final Handler b;
    public final Listener c;
    public final AudioManager d;
    @Nullable
    public a e;
    public int f = 3;
    public int g;
    public boolean h;

    public interface Listener {
        void onStreamTypeChanged(int i);

        void onStreamVolumeChanged(int i, boolean z);
    }

    public final class a extends BroadcastReceiver {
        public a() {
        }

        public void onReceive(Context context, Intent intent) {
            StreamVolumeManager streamVolumeManager = StreamVolumeManager.this;
            streamVolumeManager.b.post(new qb(0, streamVolumeManager));
        }
    }

    public StreamVolumeManager(Context context, Handler handler, Listener listener) {
        boolean z;
        Context applicationContext = context.getApplicationContext();
        this.a = applicationContext;
        this.b = handler;
        this.c = listener;
        AudioManager audioManager = (AudioManager) Assertions.checkStateNotNull((AudioManager) applicationContext.getSystemService("audio"));
        this.d = audioManager;
        this.g = a(audioManager, 3);
        int i = this.f;
        if (Util.a >= 23) {
            z = audioManager.isStreamMute(i);
        } else if (a(audioManager, i) == 0) {
            z = true;
        } else {
            z = false;
        }
        this.h = z;
        a aVar = new a();
        try {
            applicationContext.registerReceiver(aVar, new IntentFilter("android.media.VOLUME_CHANGED_ACTION"));
            this.e = aVar;
        } catch (RuntimeException e2) {
            Log.w("StreamVolumeManager", "Error registering stream volume receiver", e2);
        }
    }

    public static int a(AudioManager audioManager, int i) {
        try {
            return audioManager.getStreamVolume(i);
        } catch (RuntimeException e2) {
            StringBuilder sb = new StringBuilder(60);
            sb.append("Could not retrieve stream volume for stream type ");
            sb.append(i);
            Log.w("StreamVolumeManager", sb.toString(), e2);
            return audioManager.getStreamMaxVolume(i);
        }
    }

    public final void b() {
        boolean z;
        int i = this.f;
        AudioManager audioManager = this.d;
        int a2 = a(audioManager, i);
        int i2 = this.f;
        if (Util.a >= 23) {
            z = audioManager.isStreamMute(i2);
        } else if (a(audioManager, i2) == 0) {
            z = true;
        } else {
            z = false;
        }
        if (this.g != a2 || this.h != z) {
            this.g = a2;
            this.h = z;
            this.c.onStreamVolumeChanged(a2, z);
        }
    }

    public void decreaseVolume() {
        if (this.g > getMinVolume()) {
            this.d.adjustStreamVolume(this.f, -1, 1);
            b();
        }
    }

    public int getMaxVolume() {
        return this.d.getStreamMaxVolume(this.f);
    }

    public int getMinVolume() {
        if (Util.a >= 28) {
            return this.d.getStreamMinVolume(this.f);
        }
        return 0;
    }

    public int getVolume() {
        return this.g;
    }

    public void increaseVolume() {
        if (this.g < getMaxVolume()) {
            this.d.adjustStreamVolume(this.f, 1, 1);
            b();
        }
    }

    public boolean isMuted() {
        return this.h;
    }

    public void release() {
        a aVar = this.e;
        if (aVar != null) {
            try {
                this.a.unregisterReceiver(aVar);
            } catch (RuntimeException e2) {
                Log.w("StreamVolumeManager", "Error unregistering stream volume receiver", e2);
            }
            this.e = null;
        }
    }

    public void setMuted(boolean z) {
        int i;
        int i2 = Util.a;
        AudioManager audioManager = this.d;
        if (i2 >= 23) {
            int i3 = this.f;
            if (z) {
                i = -100;
            } else {
                i = 100;
            }
            audioManager.adjustStreamVolume(i3, i, 1);
        } else {
            audioManager.setStreamMute(this.f, z);
        }
        b();
    }

    public void setStreamType(int i) {
        if (this.f != i) {
            this.f = i;
            b();
            this.c.onStreamTypeChanged(i);
        }
    }

    public void setVolume(int i) {
        if (i >= getMinVolume() && i <= getMaxVolume()) {
            this.d.setStreamVolume(this.f, i, 1);
            b();
        }
    }
}
