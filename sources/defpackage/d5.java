package defpackage;

import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: d5  reason: default package */
public final class d5 extends WebSocketClient {
    public final /* synthetic */ String c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public d5(URI uri, String str) {
        super(uri);
        this.c = str;
    }

    public final void onClose(int i, String str, boolean z) {
    }

    public final void onError(Exception exc) {
    }

    public final void onMessage(String str) {
        close();
    }

    public final void onOpen(ServerHandshake serverHandshake) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("action", "search_songs");
            jSONObject.put("query", this.c);
            jSONObject.put("autoplay", true);
            send(jSONObject.toString());
        } catch (JSONException unused) {
        }
    }
}
