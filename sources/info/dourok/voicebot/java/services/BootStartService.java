package info.dourok.voicebot.java.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import info.dourok.voicebot.java.activities.MainActivity;

public class BootStartService extends IntentService {
    private static final String TAG = "BootStartService";

    public BootStartService() {
        super(TAG);
    }

    private void startFrontActivity() {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(268435456);
            intent.addFlags(32768);
            intent.addFlags(67108864);
            startActivity(intent);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onHandleIntent(Intent intent) {
        SystemClock.sleep(3000);
        startFrontActivity();
    }
}
