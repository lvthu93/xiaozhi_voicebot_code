package defpackage;

import android.util.Log;
import defpackage.t9;
import info.dourok.voicebot.java.data.model.DeviceInfo;

/* renamed from: s9  reason: default package */
public final class s9 implements d0<DeviceInfo> {
    public final /* synthetic */ t9.d a;
    public final /* synthetic */ String b;
    public final /* synthetic */ Object c;
    public final /* synthetic */ t9 d;

    public s9(t9 t9Var, t9.d dVar, String str, Object obj) {
        this.d = t9Var;
        this.a = dVar;
        this.b = str;
        this.c = obj;
    }

    public final void onError(Exception exc) {
        this.d.d("Failed to get device information: " + exc.getMessage());
    }

    public final void onSuccess(Object obj) {
        Log.d("ProtocolManager", "DeviceInfo obtained for WebSocket connection");
        this.d.a(this.a, this.b, this.c, (DeviceInfo) obj);
    }
}
