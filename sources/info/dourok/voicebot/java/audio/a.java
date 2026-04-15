package info.dourok.voicebot.java.audio;

import android.util.Log;
import info.dourok.voicebot.java.audio.OpusDecoder;

public final class a implements OpusDecoder.c {
    public final /* synthetic */ cy a;

    public a(bm bmVar) {
        this.a = bmVar;
    }

    public final void a(short[] sArr) {
        Log.d("AudioManager", "Decoded " + sArr.length + " PCM samples");
        cy cyVar = this.a;
        if (cyVar != null) {
            Log.d("AudioManager", "Audio playback started");
            d0 d0Var = ((bm) cyVar).a;
            if (d0Var != null) {
                d0Var.onSuccess(null);
            }
        }
    }

    public final void onError(String str) {
        d0 d0Var;
        cy cyVar = this.a;
        if (cyVar != null && (d0Var = ((bm) cyVar).a) != null) {
            d0Var.onError(new Exception(str));
        }
    }
}
