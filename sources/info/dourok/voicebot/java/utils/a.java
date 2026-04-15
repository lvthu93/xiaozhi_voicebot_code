package info.dourok.voicebot.java.utils;

import android.content.Context;

public interface a {

    /* renamed from: info.dourok.voicebot.java.utils.a$a  reason: collision with other inner class name */
    public interface C0024a {
        void onWakeWordDetected();
    }

    void init(Context context);

    boolean isEnabled();

    boolean isReady();

    void setEnabled(boolean z);

    void setSensitivity(float f);

    void setWakeWordListener(C0024a aVar);

    void shutdown();

    void startListening();

    void stopListening();
}
