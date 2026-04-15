package info.dourok.voicebot.java.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import info.dourok.voicebot.java.receivers.AlarmReceiver;
import j$.util.Collection$EL;
import j$.util.DesugarTimeZone;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;

public class AlarmService {
    private static final String ACTION_ALARM_TRIGGER = "info.dourok.voicebot.ALARM_TRIGGER";
    private static final String KEY_ALARMS = "alarms";
    private static final String KEY_NEXT_ID = "next_id";
    private static final String PREFS_NAME = "alarm_prefs";
    private static final String TAG = "AlarmService";
    private final AlarmManager alarmManager;
    private final Context context;
    private int nextId = 1;
    private final SharedPreferences prefs;

    public AlarmService(Context context2) {
        Context applicationContext = context2.getApplicationContext();
        this.context = applicationContext;
        this.alarmManager = (AlarmManager) applicationContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences(PREFS_NAME, 0);
        this.prefs = sharedPreferences;
        this.nextId = sharedPreferences.getInt(KEY_NEXT_ID, 1);
    }

    private void cancelAlarm(int i) {
        Intent intent = new Intent(this.context, AlarmReceiver.class);
        intent.setAction(ACTION_ALARM_TRIGGER);
        this.alarmManager.cancel(PendingIntent.getBroadcast(this.context, i, intent, 201326592));
    }

    /* access modifiers changed from: private */
    public static boolean lambda$deleteAlarm$0(int i, ab abVar) {
        return abVar.a == i;
    }

    private void saveAlarms(List<ab> list) {
        try {
            JSONArray jSONArray = new JSONArray();
            for (ab d : list) {
                jSONArray.put(d.d());
            }
            SharedPreferences.Editor edit = this.prefs.edit();
            edit.putString(KEY_ALARMS, jSONArray.toString());
            edit.putInt(KEY_NEXT_ID, this.nextId);
            edit.commit();
            list.size();
        } catch (JSONException unused) {
        }
    }

    private void scheduleAlarm(ab abVar) {
        if (abVar.e) {
            Intent intent = new Intent(this.context, AlarmReceiver.class);
            intent.setAction(ACTION_ALARM_TRIGGER);
            intent.putExtra("alarm_id", abVar.a);
            intent.putExtra("alarm_label", abVar.f);
            PendingIntent broadcast = PendingIntent.getBroadcast(this.context, abVar.a, intent, 201326592);
            TimeZone timeZone = DesugarTimeZone.getTimeZone("Asia/Ho_Chi_Minh");
            Calendar instance = Calendar.getInstance(timeZone);
            Calendar instance2 = Calendar.getInstance(timeZone);
            instance2.set(11, abVar.b);
            instance2.set(12, abVar.c);
            instance2.set(13, 0);
            instance2.set(14, 0);
            if (instance2.before(instance)) {
                if ("none".equals(abVar.d)) {
                    instance2.add(5, 1);
                } else if ("daily".equals(abVar.d)) {
                    instance2.add(5, 1);
                } else if ("weekly".equals(abVar.d)) {
                    instance2.add(3, 1);
                }
            }
            long timeInMillis = instance2.getTimeInMillis();
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    this.alarmManager.setExactAndAllowWhileIdle(0, timeInMillis, broadcast);
                } else {
                    this.alarmManager.setExact(0, timeInMillis, broadcast);
                }
                TimeZone timeZone2 = DesugarTimeZone.getTimeZone("Asia/Ho_Chi_Minh");
                Calendar instance3 = Calendar.getInstance(timeZone2);
                instance3.setTimeInMillis(timeInMillis);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                simpleDateFormat.setTimeZone(timeZone2);
                simpleDateFormat.format(instance3.getTime());
                abVar.c();
                "daily".equals(abVar.d);
            } catch (Exception unused) {
            }
        }
    }

    public ab addAlarm(int i, int i2, String str, String str2, int i3, String str3, String str4) {
        int i4 = this.nextId;
        this.nextId = i4 + 1;
        ab abVar = new ab(i4, i, i2, str, str2, i3);
        if (str3 != null && !str3.isEmpty()) {
            abVar.h = str3;
        }
        if (str4 != null && !str4.isEmpty()) {
            abVar.i = str4;
        }
        List<ab> allAlarms = getAllAlarms();
        allAlarms.add(abVar);
        saveAlarms(allAlarms);
        scheduleAlarm(abVar);
        abVar.c();
        return abVar;
    }

    public boolean deleteAlarm(int i) {
        List<ab> allAlarms = getAllAlarms();
        boolean removeIf = Collection$EL.removeIf(allAlarms, new af(i, 0));
        if (removeIf) {
            saveAlarms(allAlarms);
            cancelAlarm(i);
        }
        return removeIf;
    }

    public ab getAlarmById(int i) {
        for (ab next : getAllAlarms()) {
            if (next.a == i) {
                return next;
            }
        }
        return null;
    }

    public List<ab> getAllAlarms() {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(this.prefs.getString(KEY_ALARMS, "[]"));
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(ab.a(jSONArray.getJSONObject(i)));
            }
        } catch (JSONException unused) {
        }
        return arrayList;
    }

    public void rescheduleAlarmAfterTrigger(int i) {
        ab alarmById = getAlarmById(i);
        if (alarmById != null && alarmById.e) {
            if ("daily".equals(alarmById.d)) {
                scheduleAlarm(alarmById);
            } else if ("weekly".equals(alarmById.d)) {
                scheduleAlarm(alarmById);
            }
        }
    }

    public int rescheduleAllAlarms() {
        List<ab> allAlarms = getAllAlarms();
        allAlarms.size();
        int i = 0;
        for (ab next : allAlarms) {
            if (next.e) {
                scheduleAlarm(next);
                i++;
                next.c();
            } else {
                Log.d(TAG, "Skipping disabled alarm: " + next.a);
            }
        }
        allAlarms.size();
        return i;
    }

    public boolean toggleAlarm(int i) {
        ab alarmById = getAlarmById(i);
        if (alarmById == null) {
            return false;
        }
        alarmById.e = !alarmById.e;
        return updateAlarm(alarmById);
    }

    public boolean updateAlarm(ab abVar) {
        List<ab> allAlarms = getAllAlarms();
        for (int i = 0; i < allAlarms.size(); i++) {
            if (allAlarms.get(i).a == abVar.a) {
                allAlarms.set(i, abVar);
                saveAlarms(allAlarms);
                cancelAlarm(abVar.a);
                if (!abVar.e) {
                    return true;
                }
                scheduleAlarm(abVar);
                return true;
            }
        }
        return false;
    }
}
