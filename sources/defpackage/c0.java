package defpackage;

import org.json.JSONObject;

/* renamed from: c0  reason: default package */
public interface c0 {
    void onAudioChannelClosed();

    void onAudioChannelOpened();

    void onConnected();

    void onDisconnected();

    void onIncomingAudio(byte[] bArr);

    void onIncomingJson(JSONObject jSONObject);

    void onNetworkError(String str);

    void onSessionEnded();

    void onSessionStarted(String str);
}
