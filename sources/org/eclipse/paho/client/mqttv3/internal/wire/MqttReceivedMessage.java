package org.eclipse.paho.client.mqttv3.internal.wire;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttReceivedMessage extends MqttMessage {
    public int getMessageId() {
        return super.getId();
    }

    public void setDuplicate(boolean z) {
        super.setDuplicate(z);
    }

    public void setMessageId(int i) {
        super.setId(i);
    }
}
