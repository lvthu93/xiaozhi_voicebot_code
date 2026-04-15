package info.dourok.voicebot.java.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import defpackage.h;
import info.dourok.voicebot.java.services.WifiSetupService;
import info.dourok.voicebot.java.utils.MicroWakeWordDetector;

public class MainActivity extends BaseActivity implements WifiSetupService.WifiSetupListener {
    private static final int BOOT_DELAY_MS = 3000;
    private static final int BUTTON_CODE_AP_MODE = 275;
    private static final int BUTTON_HOLD_THRESHOLD_MS = 6000;
    private static final int INTERNET_CHECK_INTERVAL_MS = 3000;
    private static final String TAG = "MainActivity";
    private Runnable buttonHoldCheckRunnable;
    private long buttonPressStartTime = 0;
    /* access modifiers changed from: private */
    public final Handler handler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public boolean isCheckingInternet = false;
    /* access modifiers changed from: private */
    public boolean isInWifiSetupMode = false;
    /* access modifiers changed from: private */
    public boolean isServiceBound = false;
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MainActivity.this.wifiSetupService = ((WifiSetupService.LocalBinder) iBinder).getService();
            MainActivity.this.wifiSetupService.setListener(MainActivity.this);
            MainActivity.this.isServiceBound = true;
            Log.d(MainActivity.TAG, "WifiSetupService bound");
        }

        public void onServiceDisconnected(ComponentName componentName) {
            MainActivity.this.wifiSetupService = null;
            MainActivity.this.isServiceBound = false;
            Log.d(MainActivity.TAG, "WifiSetupService unbound");
        }
    };
    /* access modifiers changed from: private */
    public WifiSetupService wifiSetupService;

    /* renamed from: info.dourok.voicebot.java.activities.MainActivity$AnonymousClass2  reason: case insensitive filesystem */
    public class C0035AnonymousClass2 implements h.d {
        public C0035AnonymousClass2() {
        }

        public void lambda$onPlaybackComplete$0() {
            MainActivity.this.checkInternetAndProceed();
        }

        public void lambda$onPlaybackError$1() {
            MainActivity.this.checkInternetAndProceed();
        }

        public void onPlaybackComplete() {
            Log.d(MainActivity.TAG, "No internet sound finished, scheduling retry in 3000ms");
            if (MainActivity.this.isCheckingInternet && !MainActivity.this.isInWifiSetupMode) {
                MainActivity.this.handler.postDelayed(new Runnable() {
                    public final void run() {
                        C0035AnonymousClass2.this.lambda$onPlaybackComplete$0();
                    }
                }, 3000);
            }
        }

        public void onPlaybackError(String str) {
            if (MainActivity.this.isCheckingInternet && !MainActivity.this.isInWifiSetupMode) {
                MainActivity.this.handler.postDelayed(new Runnable() {
                    public final void run() {
                        C0035AnonymousClass2.this.lambda$onPlaybackError$1();
                    }
                }, 3000);
            }
        }

        public void onPlaybackStarted() {
            Log.d(MainActivity.TAG, "No internet sound started");
        }
    }

    private void enterWifiSetupMode() {
        if (this.isInWifiSetupMode) {
            Log.d(TAG, "Already in WiFi setup mode");
            return;
        }
        this.isInWifiSetupMode = true;
        this.isCheckingInternet = false;
        this.handler.removeCallbacksAndMessages((Object) null);
        h.b().g();
        Intent intent = new Intent(this, WifiSetupService.class);
        intent.setAction(WifiSetupService.ACTION_START_AP);
        startService(intent);
    }

    private void exitWifiSetupMode() {
        if (this.isInWifiSetupMode) {
            this.isInWifiSetupMode = false;
            Intent intent = new Intent(this, WifiSetupService.class);
            intent.setAction(WifiSetupService.ACTION_STOP_AP);
            startService(intent);
        }
    }

    private boolean hasInternetConnectivity() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
            return false;
        }
        return activeNetworkInfo.isConnected();
    }

    public void checkActivationStatus() {
        this.dependencies.j().a(new d0<Object>() {
            public void onError(Exception exc) {
                MainActivity.this.navigateToActivation();
            }

            public void onSuccess(Object obj) {
                if (obj instanceof c7) {
                    c7 c7Var = (c7) obj;
                    Log.d(MainActivity.TAG, "✓ Device is activated (MQTT config found in RAM cache)");
                    Log.d(MainActivity.TAG, "  → Endpoint: " + c7Var.b);
                    Log.d(MainActivity.TAG, "  → ClientId: " + c7Var.a);
                    MainActivity.this.navigateToChat();
                    return;
                }
                Log.d(MainActivity.TAG, "⚠️  No MQTT config in RAM → going to activation screen");
                MainActivity.this.navigateToActivation();
            }
        });
    }

    public void checkInternetAndProceed() {
        if (this.isInWifiSetupMode) {
            Log.d(TAG, "In WiFi setup mode - skipping internet check");
        } else if (hasInternetConnectivity()) {
            this.isCheckingInternet = false;
            checkActivationStatus();
        } else {
            this.isCheckingInternet = false;
            h b = h.b();
            b.b = null;
            b.f("khong_co_internet.mp3");
        }
    }

    public void lambda$onKeyDown$0() {
        if (this.buttonPressStartTime > 0 && System.currentTimeMillis() - this.buttonPressStartTime >= 6000) {
            enterWifiSetupMode();
        }
    }

    public void navigateToActivation() {
        startActivity(new Intent(this, ActivationActivity.class));
        finish();
    }

    public void navigateToChat() {
        startActivity(ChatActivity.createIntent(this, MicroWakeWordDetector.class));
        finish();
    }

    public void onApModeStarted(String str, String str2, String str3) {
    }

    public void onApModeStopped() {
        this.isInWifiSetupMode = false;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "MainActivity onCreate");
        h b = h.b();
        b.getClass();
        b.a = getApplicationContext();
        b.a();
        bindService(new Intent(this, WifiSetupService.class), this.serviceConnection, 1);
        Log.d(TAG, "Waiting 3000ms for system to stabilize...");
        this.handler.postDelayed(new Runnable() {
            public final void run() {
                MainActivity.this.checkInternetAndProceed();
            }
        }, 3000);
    }

    public void onDestroy() {
        if (this.isServiceBound) {
            unbindService(this.serviceConnection);
            this.isServiceBound = false;
        }
        super.onDestroy();
    }

    public void onError(String str) {
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != BUTTON_CODE_AP_MODE) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.buttonPressStartTime == 0) {
            this.buttonPressStartTime = System.currentTimeMillis();
            Log.d(TAG, "Button 275 pressed - starting timer");
            AnonymousClass3 r5 = new Runnable() {
                public final void run() {
                    MainActivity.this.lambda$onKeyDown$0();
                }
            };
            this.buttonHoldCheckRunnable = r5;
            this.handler.postDelayed(r5, 6000);
        }
        return true;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != BUTTON_CODE_AP_MODE) {
            return super.onKeyUp(i, keyEvent);
        }
        long currentTimeMillis = System.currentTimeMillis() - this.buttonPressStartTime;
        Log.d(TAG, "Button 275 released after " + currentTimeMillis + "ms");
        this.buttonPressStartTime = 0;
        Runnable runnable = this.buttonHoldCheckRunnable;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
            this.buttonHoldCheckRunnable = null;
        }
        return true;
    }

    public void onWifiConnected(String str) {
        this.isInWifiSetupMode = false;
        this.handler.postDelayed(new Runnable() {
            public final void run() {
                MainActivity.this.checkActivationStatus();
            }
        }, 5000);
    }

    public void onWifiConnectionFailed(String str, String str2) {
    }
}
