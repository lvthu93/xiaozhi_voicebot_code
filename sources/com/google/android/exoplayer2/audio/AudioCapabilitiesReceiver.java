package com.google.android.exoplayer2.audio;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;

public final class AudioCapabilitiesReceiver {
    public final Context a;
    public final Listener b;
    public final Handler c;
    @Nullable
    public final b d;
    @Nullable
    public final a e;
    @Nullable
    public AudioCapabilities f;
    public boolean g;

    public interface Listener {
        void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities);
    }

    public final class a extends ContentObserver {
        public final ContentResolver a;
        public final Uri b;

        public a(Handler handler, ContentResolver contentResolver, Uri uri) {
            super(handler);
            this.a = contentResolver;
            this.b = uri;
        }

        public void onChange(boolean z) {
            AudioCapabilitiesReceiver audioCapabilitiesReceiver = AudioCapabilitiesReceiver.this;
            AudioCapabilities capabilities = AudioCapabilities.getCapabilities(audioCapabilitiesReceiver.a);
            if (audioCapabilitiesReceiver.g && !capabilities.equals(audioCapabilitiesReceiver.f)) {
                audioCapabilitiesReceiver.f = capabilities;
                audioCapabilitiesReceiver.b.onAudioCapabilitiesChanged(capabilities);
            }
        }

        public void register() {
            this.a.registerContentObserver(this.b, false, this);
        }

        public void unregister() {
            this.a.unregisterContentObserver(this);
        }
    }

    public final class b extends BroadcastReceiver {
        public b() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!isInitialStickyBroadcast()) {
                AudioCapabilities a2 = AudioCapabilities.a(context, intent);
                AudioCapabilitiesReceiver audioCapabilitiesReceiver = AudioCapabilitiesReceiver.this;
                if (audioCapabilitiesReceiver.g && !a2.equals(audioCapabilitiesReceiver.f)) {
                    audioCapabilitiesReceiver.f = a2;
                    audioCapabilitiesReceiver.b.onAudioCapabilitiesChanged(a2);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AudioCapabilitiesReceiver(android.content.Context r4, com.google.android.exoplayer2.audio.AudioCapabilitiesReceiver.Listener r5) {
        /*
            r3 = this;
            r3.<init>()
            android.content.Context r4 = r4.getApplicationContext()
            r3.a = r4
            java.lang.Object r5 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r5)
            com.google.android.exoplayer2.audio.AudioCapabilitiesReceiver$Listener r5 = (com.google.android.exoplayer2.audio.AudioCapabilitiesReceiver.Listener) r5
            r3.b = r5
            android.os.Handler r5 = com.google.android.exoplayer2.util.Util.createHandlerForCurrentOrMainLooper()
            r3.c = r5
            int r0 = com.google.android.exoplayer2.util.Util.a
            r1 = 21
            r2 = 0
            if (r0 < r1) goto L_0x0024
            com.google.android.exoplayer2.audio.AudioCapabilitiesReceiver$b r1 = new com.google.android.exoplayer2.audio.AudioCapabilitiesReceiver$b
            r1.<init>()
            goto L_0x0025
        L_0x0024:
            r1 = r2
        L_0x0025:
            r3.d = r1
            com.google.android.exoplayer2.audio.AudioCapabilities r1 = com.google.android.exoplayer2.audio.AudioCapabilities.c
            r1 = 17
            if (r0 < r1) goto L_0x0041
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.c
            java.lang.String r1 = "Amazon"
            boolean r1 = r1.equals(r0)
            if (r1 != 0) goto L_0x003f
            java.lang.String r1 = "Xiaomi"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0041
        L_0x003f:
            r0 = 1
            goto L_0x0042
        L_0x0041:
            r0 = 0
        L_0x0042:
            if (r0 == 0) goto L_0x004b
            java.lang.String r0 = "external_surround_sound_enabled"
            android.net.Uri r0 = android.provider.Settings.Global.getUriFor(r0)
            goto L_0x004c
        L_0x004b:
            r0 = r2
        L_0x004c:
            if (r0 == 0) goto L_0x0057
            com.google.android.exoplayer2.audio.AudioCapabilitiesReceiver$a r2 = new com.google.android.exoplayer2.audio.AudioCapabilitiesReceiver$a
            android.content.ContentResolver r4 = r4.getContentResolver()
            r2.<init>(r5, r4, r0)
        L_0x0057:
            r3.e = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.AudioCapabilitiesReceiver.<init>(android.content.Context, com.google.android.exoplayer2.audio.AudioCapabilitiesReceiver$Listener):void");
    }

    public AudioCapabilities register() {
        if (this.g) {
            return (AudioCapabilities) Assertions.checkNotNull(this.f);
        }
        this.g = true;
        a aVar = this.e;
        if (aVar != null) {
            aVar.register();
        }
        b bVar = this.d;
        Context context = this.a;
        Intent intent = null;
        if (bVar != null) {
            intent = context.registerReceiver(bVar, new IntentFilter("android.media.action.HDMI_AUDIO_PLUG"), (String) null, this.c);
        }
        AudioCapabilities a2 = AudioCapabilities.a(context, intent);
        this.f = a2;
        return a2;
    }

    public void unregister() {
        if (this.g) {
            this.f = null;
            b bVar = this.d;
            if (bVar != null) {
                this.a.unregisterReceiver(bVar);
            }
            a aVar = this.e;
            if (aVar != null) {
                aVar.unregister();
            }
            this.g = false;
        }
    }
}
