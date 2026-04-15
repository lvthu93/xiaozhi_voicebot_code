package org.eclipse.paho.client.mqttv3;

public class MqttDeliveryToken extends MqttToken implements IMqttDeliveryToken {
    public MqttDeliveryToken() {
    }

    public MqttMessage getMessage() throws MqttException {
        return this.internalTok.getMessage();
    }

    public void setMessage(MqttMessage mqttMessage) {
        this.internalTok.setMessage(mqttMessage);
    }

    public MqttDeliveryToken(String str) {
        super(str);
    }
}
