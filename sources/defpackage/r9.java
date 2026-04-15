package defpackage;

import defpackage.t9;
import info.dourok.voicebot.java.data.model.DeviceInfo;

/* renamed from: r9  reason: default package */
public final class r9 implements Runnable {
    public final /* synthetic */ t9.d c;
    public final /* synthetic */ String f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ t9 h;

    public r9(t9 t9Var, String str, Object obj) {
        t9.d dVar = t9.d.MQTT;
        this.h = t9Var;
        this.c = dVar;
        this.f = str;
        this.g = obj;
    }

    public final void run() {
        t9 t9Var = this.h;
        t9Var.getClass();
        try {
            t9.d dVar = t9.d.WEBSOCKET;
            t9.d dVar2 = this.c;
            String str = this.f;
            Object obj = this.g;
            if (dVar2 == dVar) {
                k1 b = t9Var.c.b();
                s9 s9Var = new s9(t9Var, dVar2, str, obj);
                b.getClass();
                b.c.execute(new i1(b, s9Var));
                return;
            }
            t9Var.a(dVar2, str, obj, (DeviceInfo) null);
        } catch (Exception e) {
            t9Var.d("Connection failed: " + e.getMessage());
        }
    }
}
