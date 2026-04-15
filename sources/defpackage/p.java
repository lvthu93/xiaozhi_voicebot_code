package defpackage;

import java.net.URI;
import org.java_websocket.WebSocket;
import org.json.JSONObject;

/* renamed from: p  reason: default package */
public final /* synthetic */ class p implements Runnable {
    public final /* synthetic */ x c;
    public final /* synthetic */ boolean f;
    public final /* synthetic */ WebSocket g;

    public /* synthetic */ p(x xVar, boolean z, WebSocket webSocket) {
        this.c = xVar;
        this.f = z;
        this.g = webSocket;
    }

    public final void run() {
        String str;
        boolean z = this.f;
        WebSocket webSocket = this.g;
        x xVar = this.c;
        xVar.getClass();
        try {
            boolean[] zArr = {false};
            aa aaVar = new aa(xVar, new URI("ws://127.0.0.1:8080"), z, zArr);
            aaVar.connect();
            int i = 0;
            while (!aaVar.isOpen() && i < 20) {
                Thread.sleep(100);
                i++;
            }
            if (aaVar.isOpen()) {
                int i2 = 0;
                while (!zArr[0] && i2 < 10) {
                    Thread.sleep(100);
                    i2++;
                }
            }
            if (webSocket != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", "bluetooth_set_result");
                jSONObject.put("success", true);
                if (z) {
                    str = "蓝牙控制命令已发送 / Bluetooth enable command sent";
                } else {
                    str = "蓝牙控制命令已发送 / Bluetooth disable command sent";
                }
                jSONObject.put("message", str);
                jSONObject.put("enabled", z);
                x.bb(webSocket, jSONObject.toString());
            }
        } catch (Exception e) {
            if (webSocket != null) {
                y2.v(e, new StringBuilder("Error controlling Bluetooth: "), webSocket);
            }
        }
    }
}
