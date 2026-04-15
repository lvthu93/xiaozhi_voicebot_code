package defpackage;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/* renamed from: m8  reason: default package */
public final class m8 implements IMqttActionListener {
    public final /* synthetic */ x7 a;

    public m8(x7 x7Var) {
        this.a = x7Var;
    }

    public final void onFailure(IMqttToken iMqttToken, Throwable th) {
        th.getMessage();
    }

    public final void onSuccess(IMqttToken iMqttToken) {
        String str = this.a.ah;
    }
}
