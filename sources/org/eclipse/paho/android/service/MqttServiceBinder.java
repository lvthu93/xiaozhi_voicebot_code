package org.eclipse.paho.android.service;

import android.os.Binder;

class MqttServiceBinder extends Binder {
    private String activityToken;
    private MqttService mqttService;

    public MqttServiceBinder(MqttService mqttService2) {
        this.mqttService = mqttService2;
    }

    public String getActivityToken() {
        return this.activityToken;
    }

    public MqttService getService() {
        return this.mqttService;
    }

    public void setActivityToken(String str) {
        this.activityToken = str;
    }
}
