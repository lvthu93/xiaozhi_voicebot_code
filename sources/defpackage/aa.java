package defpackage;

import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: aa  reason: default package */
public final class aa extends WebSocketClient {
    public final /* synthetic */ boolean c;
    public final /* synthetic */ boolean[] f;
    public final /* synthetic */ x g;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public aa(x xVar, URI uri, boolean z, boolean[] zArr) {
        super(uri);
        this.g = xVar;
        this.c = z;
        this.f = zArr;
    }

    public final void onClose(int i, String str, boolean z) {
    }

    public final void onError(Exception exc) {
    }

    public final void onMessage(String str) {
    }

    public final void onOpen(ServerHandshake serverHandshake) {
        int i;
        String str;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", "send_message");
            jSONObject.put("what", 64);
            boolean z = this.c;
            if (z) {
                i = 1;
            } else {
                i = 2;
            }
            jSONObject.put("arg1", i);
            jSONObject.put("arg2", -1);
            if (z) {
                str = "Open Bluetooth";
            } else {
                str = "Close Bluetooth";
            }
            jSONObject.put("type_id", str);
            send(jSONObject.toString());
            this.f[0] = true;
            this.g.getClass();
            new Thread(new qb(11, this)).start();
        } catch (JSONException unused) {
            close();
        }
    }
}
