package info.dourok.voicebot.java.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import info.dourok.voicebot.java.services.BootStartService;

public class BootReceiver extends BroadcastReceiver {
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.BOOT_COMPLETED".equals(action) || "android.intent.action.QUICKBOOT_POWERON".equals(action) || "com.htc.intent.action.QUICKBOOT_POWERON".equals(action) || "info.dourok.voicebot.START".equals(action)) {
            context.startService(new Intent(context, BootStartService.class));
        }
    }
}
