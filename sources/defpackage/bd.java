package defpackage;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import androidx.core.app.NotificationCompat;

/* renamed from: bd  reason: default package */
public final class bd {
    public static void a(Context context) {
        int i;
        try {
            Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            if (launchIntentForPackage != null) {
                launchIntentForPackage.addFlags(335577088);
                if (Build.VERSION.SDK_INT >= 23) {
                    i = 1140850688;
                } else {
                    i = 1073741824;
                }
                PendingIntent activity = PendingIntent.getActivity(context.getApplicationContext(), 12345, launchIntentForPackage, i);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
                if (alarmManager != null) {
                    alarmManager.set(1, System.currentTimeMillis() + 500, activity);
                }
                if (context instanceof Activity) {
                    ((Activity) context).finishAffinity();
                }
                Process.killProcess(Process.myPid());
                System.exit(0);
            }
        } catch (Exception unused) {
        }
    }
}
