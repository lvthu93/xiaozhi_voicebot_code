package com.google.android.exoplayer2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

public final class AudioBecomingNoisyManager {
    public final Context a;
    public final a b;
    public boolean c;

    public interface EventListener {
        void onAudioBecomingNoisy();
    }

    public final class a extends BroadcastReceiver implements Runnable {
        public final EventListener c;
        public final Handler f;

        public a(Handler handler, EventListener eventListener) {
            this.f = handler;
            this.c = eventListener;
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.media.AUDIO_BECOMING_NOISY".equals(intent.getAction())) {
                this.f.post(this);
            }
        }

        public void run() {
            if (AudioBecomingNoisyManager.this.c) {
                this.c.onAudioBecomingNoisy();
            }
        }
    }

    public AudioBecomingNoisyManager(Context context, Handler handler, EventListener eventListener) {
        this.a = context.getApplicationContext();
        this.b = new a(handler, eventListener);
    }

    public void setEnabled(boolean z) {
        a aVar = this.b;
        Context context = this.a;
        if (z && !this.c) {
            context.registerReceiver(aVar, new IntentFilter("android.media.AUDIO_BECOMING_NOISY"));
            this.c = true;
        } else if (!z && this.c) {
            context.unregisterReceiver(aVar);
            this.c = false;
        }
    }
}
