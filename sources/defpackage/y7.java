package defpackage;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/* renamed from: y7  reason: default package */
public final class y7 implements IMqttActionListener {
    public final /* synthetic */ x7 a;

    public y7(x7 x7Var) {
        this.a = x7Var;
    }

    public final void onFailure(IMqttToken iMqttToken, Throwable th) {
        th.getMessage();
    }

    public final void onSuccess(IMqttToken iMqttToken) {
        String str = this.a.ab;
    }
}
