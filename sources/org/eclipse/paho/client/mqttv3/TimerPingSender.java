package org.eclipse.paho.client.mqttv3;

import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class TimerPingSender implements MqttPingSender {
    /* access modifiers changed from: private */
    public static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.TimerPingSender";
    private String clientid;
    /* access modifiers changed from: private */
    public ClientComms comms;
    /* access modifiers changed from: private */
    public Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private Timer timer;

    public class PingTask extends TimerTask {
        private static final String methodName = "PingTask.run";

        private PingTask() {
        }

        public void run() {
            TimerPingSender.this.log.fine(TimerPingSender.CLASS_NAME, methodName, "660", new Object[]{Long.valueOf(System.nanoTime())});
            TimerPingSender.this.comms.checkForActivity();
        }

        public /* synthetic */ PingTask(TimerPingSender timerPingSender, PingTask pingTask) {
            this();
        }
    }

    public void init(ClientComms clientComms) {
        if (clientComms != null) {
            this.comms = clientComms;
            String clientId = clientComms.getClient().getClientId();
            this.clientid = clientId;
            this.log.setResourceName(clientId);
            return;
        }
        throw new IllegalArgumentException("ClientComms cannot be null.");
    }

    public void schedule(long j) {
        this.timer.schedule(new PingTask(this, (PingTask) null), j);
    }

    public void start() {
        this.log.fine(CLASS_NAME, "start", "659", new Object[]{this.clientid});
        Timer timer2 = new Timer("MQTT Ping: " + this.clientid);
        this.timer = timer2;
        timer2.schedule(new PingTask(this, (PingTask) null), this.comms.getKeepAlive());
    }

    public void stop() {
        this.log.fine(CLASS_NAME, "stop", "661", (Object[]) null);
        Timer timer2 = this.timer;
        if (timer2 != null) {
            timer2.cancel();
        }
    }
}
