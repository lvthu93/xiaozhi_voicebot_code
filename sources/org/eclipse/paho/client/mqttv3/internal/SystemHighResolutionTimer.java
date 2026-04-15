package org.eclipse.paho.client.mqttv3.internal;

public class SystemHighResolutionTimer implements HighResolutionTimer {
    public long nanoTime() {
        return System.nanoTime();
    }
}
