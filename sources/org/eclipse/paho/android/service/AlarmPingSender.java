package org.eclipse.paho.android.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;

class AlarmPingSender implements MqttPingSender {
    private static final String TAG = "AlarmPingSender";
    private BroadcastReceiver alarmReceiver;
    /* access modifiers changed from: private */
    public ClientComms comms;
    private volatile boolean hasStarted = false;
    private PendingIntent pendingIntent;
    /* access modifiers changed from: private */
    public MqttService service;
    /* access modifiers changed from: private */
    public AlarmPingSender that;

    public class AlarmReceiver extends BroadcastReceiver {
        /* access modifiers changed from: private */
        public final String wakeLockTag;
        /* access modifiers changed from: private */
        public PowerManager.WakeLock wakelock;

        public AlarmReceiver() {
            this.wakeLockTag = MqttServiceConstants.PING_WAKELOCK + AlarmPingSender.this.that.comms.getClient().getClientId();
        }

        @SuppressLint({"Wakelock"})
        public void onReceive(Context context, Intent intent) {
            Log.d(AlarmPingSender.TAG, "Sending Ping at:" + System.currentTimeMillis());
            PowerManager.WakeLock newWakeLock = ((PowerManager) AlarmPingSender.this.service.getSystemService("power")).newWakeLock(1, this.wakeLockTag);
            this.wakelock = newWakeLock;
            newWakeLock.acquire();
            if (AlarmPingSender.this.comms.checkForActivity(new IMqttActionListener() {
                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    Log.d(AlarmPingSender.TAG, "Failure. Release lock(" + AlarmReceiver.this.wakeLockTag + "):" + System.currentTimeMillis());
                    AlarmReceiver.this.wakelock.release();
                }

                public void onSuccess(IMqttToken iMqttToken) {
                    Log.d(AlarmPingSender.TAG, "Success. Release lock(" + AlarmReceiver.this.wakeLockTag + "):" + System.currentTimeMillis());
                    AlarmReceiver.this.wakelock.release();
                }
            }) == null && this.wakelock.isHeld()) {
                this.wakelock.release();
            }
        }
    }

    public AlarmPingSender(MqttService mqttService) {
        if (mqttService != null) {
            this.service = mqttService;
            this.that = this;
            return;
        }
        throw new IllegalArgumentException("Neither service nor client can be null.");
    }

    public void init(ClientComms clientComms) {
        this.comms = clientComms;
        this.alarmReceiver = new AlarmReceiver();
    }

    public void schedule(long j) {
        long currentTimeMillis = System.currentTimeMillis() + j;
        Log.d(TAG, "Schedule next alarm at " + currentTimeMillis);
        AlarmManager alarmManager = (AlarmManager) this.service.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d(TAG, "Alarm scheule using setExactAndAllowWhileIdle, next: " + j);
            alarmManager.setExactAndAllowWhileIdle(0, currentTimeMillis, this.pendingIntent);
            return;
        }
        Log.d(TAG, "Alarm scheule using setExact, delay: " + j);
        alarmManager.setExact(0, currentTimeMillis, this.pendingIntent);
    }

    public void start() {
        String str = MqttServiceConstants.PING_SENDER + this.comms.getClient().getClientId();
        Log.d(TAG, "Register alarmreceiver to MqttService" + str);
        this.service.registerReceiver(this.alarmReceiver, new IntentFilter(str));
        this.pendingIntent = PendingIntent.getBroadcast(this.service, 0, new Intent(str), 134217728);
        schedule(this.comms.getKeepAlive());
        this.hasStarted = true;
    }

    public void stop() {
        Log.d(TAG, "Unregister alarmreceiver to MqttService" + this.comms.getClient().getClientId());
        if (this.hasStarted) {
            if (this.pendingIntent != null) {
                ((AlarmManager) this.service.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(this.pendingIntent);
            }
            this.hasStarted = false;
            try {
                this.service.unregisterReceiver(this.alarmReceiver);
            } catch (IllegalArgumentException unused) {
            }
        }
    }
}
