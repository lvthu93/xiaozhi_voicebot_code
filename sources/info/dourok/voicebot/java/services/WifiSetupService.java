package info.dourok.voicebot.java.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import defpackage.h;
import info.dourok.voicebot.java.VoiceBotApplication;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class WifiSetupService extends Service {
    public static final String ACTION_CHECK_CONNECTIVITY = "info.dourok.voicebot.CHECK_CONNECTIVITY";
    public static final String ACTION_START_AP = "info.dourok.voicebot.START_WIFI_AP";
    public static final String ACTION_STOP_AP = "info.dourok.voicebot.STOP_WIFI_AP";
    private static final String AP_PASSWORD = null;
    private static final String AP_SSID = "Phicomm-R1";
    private static final String TAG = "WifiSetupService";
    private static final long WIFI_CONNECTION_TIMEOUT_MS = 30000;
    private ef apManager;
    private final IBinder binder = new LocalBinder();
    private ConnectivityManager connectivityManager;
    private BroadcastReceiver connectivityReceiver;
    private boolean isApModeActive = false;
    /* access modifiers changed from: private */
    public WifiSetupListener listener;
    /* access modifiers changed from: private */
    public Handler mainHandler;
    /* access modifiers changed from: private */
    public Runnable wifiConnectionTimeoutRunnable;
    /* access modifiers changed from: private */
    public WifiManager wifiManager;
    private Handler workerHandler;
    private HandlerThread workerThread;

    /* renamed from: info.dourok.voicebot.java.services.WifiSetupService$AnonymousClass1  reason: case insensitive filesystem */
    public class C0036AnonymousClass1 extends BroadcastReceiver {
        public C0036AnonymousClass1() {
        }

        public void lambda$onReceive$0(String str, String str2) {
            if (WifiSetupService.this.listener != null) {
                WifiSetupService.this.listener.onWifiConnected(str);
            }
            if (str2 == null || str2.isEmpty() || str2.equals("0.0.0.0")) {
                bd.a(WifiSetupService.this);
                return;
            }
            h b = h.b();
            b.b = new h.d() {
                public void onPlaybackComplete() {
                    bd.a(WifiSetupService.this);
                }

                public void onPlaybackError(String str) {
                    bd.a(WifiSetupService.this);
                }

                public void onPlaybackStarted() {
                }
            };
            b.d(str2);
        }

        public void onReceive(Context context, Intent intent) {
            NetworkInfo networkInfo;
            NetworkInfo networkInfo2;
            if ("android.net.wifi.STATE_CHANGE".equals(intent.getAction()) && (networkInfo2 = (NetworkInfo) intent.getParcelableExtra("networkInfo")) != null && networkInfo2.isConnected()) {
                if (WifiSetupService.this.wifiConnectionTimeoutRunnable != null) {
                    WifiSetupService.this.mainHandler.removeCallbacks(WifiSetupService.this.wifiConnectionTimeoutRunnable);
                    WifiSetupService.this.wifiConnectionTimeoutRunnable = null;
                }
                final String ssid = WifiSetupService.this.wifiManager.getConnectionInfo().getSSID();
                if (ssid != null && ssid.startsWith("\"") && ssid.endsWith("\"")) {
                    ssid = ssid.substring(1, ssid.length() - 1);
                }
                final String deviceIpAddress = WifiSetupService.this.getDeviceIpAddress();
                WifiSetupService.this.unregisterConnectivityReceiver();
                WifiSetupService.this.mainHandler.post(new Runnable() {
                    public final void run() {
                        C0036AnonymousClass1.this.lambda$onReceive$0(ssid, deviceIpAddress);
                    }
                });
            } else if ("android.net.wifi.STATE_CHANGE".equals(intent.getAction()) && (networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo")) != null) {
                networkInfo.isConnected();
            }
        }
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public WifiSetupService getService() {
            return WifiSetupService.this;
        }
    }

    public interface WifiSetupListener {
        void onApModeStarted(String str, String str2, String str3);

        void onApModeStopped();

        void onError(String str);

        void onWifiConnected(String str);

        void onWifiConnectionFailed(String str, String str2);
    }

    private boolean connectWifiDirect(String str, String str2) {
        try {
            List<WifiConfiguration> configuredNetworks = this.wifiManager.getConfiguredNetworks();
            if (configuredNetworks != null) {
                Iterator<WifiConfiguration> it = configuredNetworks.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    WifiConfiguration next = it.next();
                    String str3 = next.SSID;
                    if (str3 != null) {
                        if (str3.startsWith("\"") && str3.endsWith("\"")) {
                            str3 = str3.substring(1, str3.length() - 1);
                        }
                        if (str3.equals(str)) {
                            this.wifiManager.removeNetwork(next.networkId);
                            break;
                        }
                    }
                }
            }
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + str + "\"";
            if (str2 == null || str2.isEmpty()) {
                wifiConfiguration.allowedKeyManagement.set(0);
            } else {
                wifiConfiguration.preSharedKey = "\"" + str2 + "\"";
                wifiConfiguration.allowedKeyManagement.set(1);
                wifiConfiguration.allowedProtocols.set(1);
                wifiConfiguration.allowedProtocols.set(0);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedGroupCiphers.set(2);
            }
            int addNetwork = this.wifiManager.addNetwork(wifiConfiguration);
            if (addNetwork < 0) {
                h.b().e();
                return false;
            }
            this.wifiManager.saveConfiguration();
            this.wifiManager.disconnect();
            if (!this.wifiManager.enableNetwork(addNetwork, true)) {
                h.b().e();
                return false;
            }
            this.wifiManager.reconnect();
            registerConnectivityReceiver();
            return true;
        } catch (Exception e) {
            e.getMessage();
            h.b().e();
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void handleWifiConnectionFailure(String str) {
        unregisterConnectivityReceiver();
        h.b().e();
        WifiSetupListener wifiSetupListener = this.listener;
        if (wifiSetupListener != null) {
            wifiSetupListener.onWifiConnectionFailed("", str);
        }
    }

    private void notifyError(final String str) {
        this.mainHandler.post(new Runnable() {
            public final void run() {
                WifiSetupService.this.lambda$notifyError$13(str);
            }
        });
    }

    private void registerConnectivityReceiver() {
        if (this.connectivityReceiver == null) {
            this.connectivityReceiver = new C0036AnonymousClass1();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.STATE_CHANGE");
            registerReceiver(this.connectivityReceiver, intentFilter);
            Log.d(TAG, "Registered connectivity receiver");
            Runnable runnable = this.wifiConnectionTimeoutRunnable;
            if (runnable != null) {
                this.mainHandler.removeCallbacks(runnable);
            }
            AnonymousClass5 r0 = new Runnable() {
                public void run() {
                    WifiSetupService.this.handleWifiConnectionFailure("Connection timeout");
                }
            };
            this.wifiConnectionTimeoutRunnable = r0;
            this.mainHandler.postDelayed(r0, WIFI_CONNECTION_TIMEOUT_MS);
            Log.d(TAG, "Registered WiFi connection timeout handler (30 seconds)");
        }
    }

    public String getApIpAddress() {
        this.apManager.getClass();
        return "192.168.43.1";
    }

    public String getApPassword() {
        return AP_PASSWORD;
    }

    public String getApSsid() {
        return AP_SSID;
    }

    public String getDeviceIpAddress() {
        try {
            int ipAddress = this.wifiManager.getConnectionInfo().getIpAddress();
            if (ipAddress == 0) {
                return null;
            }
            return String.format("%d.%d.%d.%d", new Object[]{Integer.valueOf(ipAddress & 255), Integer.valueOf((ipAddress >> 8) & 255), Integer.valueOf((ipAddress >> 16) & 255), Integer.valueOf((ipAddress >> 24) & 255)});
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public boolean hasInternetConnectivity() {
        NetworkInfo activeNetworkInfo = this.connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public boolean isApModeActive() {
        return this.isApModeActive;
    }

    public void lambda$connectToWifi$9() {
        Handler handler;
        Thread.currentThread().getName();
        Looper.myLooper();
        Looper.getMainLooper();
        if (!this.isApModeActive && (handler = this.workerHandler) != null) {
            handler.post(new Runnable() {
                public final void run() {
                    Thread.currentThread().getName();
                    WifiSetupService.this.lambda$startApMode$1();
                }
            });
        }
    }

    public void lambda$notifyError$13(String str) {
        WifiSetupListener wifiSetupListener = this.listener;
        if (wifiSetupListener != null) {
            wifiSetupListener.onError(str);
        }
    }

    public void lambda$startApMode$0(String str) {
        x aiboxPlusWebSocketServer;
        WifiSetupListener wifiSetupListener = this.listener;
        if (wifiSetupListener != null) {
            wifiSetupListener.onApModeStarted(AP_SSID, AP_PASSWORD, str);
        }
        if ((getApplicationContext() instanceof VoiceBotApplication) && (aiboxPlusWebSocketServer = ((VoiceBotApplication) getApplicationContext()).getAiboxPlusWebSocketServer()) != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", "wifi_ap_status");
                jSONObject.put("ap_mode_active", true);
                jSONObject.put("ap_ssid", AP_SSID);
                jSONObject.put("ap_ip", str);
                aiboxPlusWebSocketServer.d(jSONObject.toString());
            } catch (Exception unused) {
            }
        }
    }

    public void lambda$startApMode$1() {
        Thread.currentThread().getName();
        Looper.myLooper();
        Looper.getMainLooper();
        try {
            h.b().f("wificonfig.mp3");
            Thread.sleep(5000);
            if (!this.apManager.c(AP_PASSWORD)) {
                notifyError("Failed to enable WiFi AP mode");
                return;
            }
            Thread.sleep(2000);
            this.isApModeActive = true;
            this.apManager.getClass();
            this.mainHandler.post(new Runnable("192.168.43.1") {
                public final void run() {
                    WifiSetupService.this.lambda$startApMode$0("192.168.43.1");
                }
            });
        } catch (Exception e) {
            e.getMessage();
            notifyError("Error starting AP mode: " + e.getMessage());
        }
    }

    public void lambda$stopApMode$2() {
        WifiSetupListener wifiSetupListener = this.listener;
        if (wifiSetupListener != null) {
            wifiSetupListener.onApModeStopped();
        }
    }

    public void lambda$stopApMode$3() {
        x aiboxPlusWebSocketServer;
        try {
            this.apManager.b();
            this.isApModeActive = false;
            this.mainHandler.post(new Runnable() {
                public final void run() {
                    WifiSetupService.this.lambda$stopApMode$2();
                }
            });
            if ((getApplicationContext() instanceof VoiceBotApplication) && (aiboxPlusWebSocketServer = ((VoiceBotApplication) getApplicationContext()).getAiboxPlusWebSocketServer()) != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", "wifi_ap_status");
                jSONObject.put("ap_mode_active", false);
                aiboxPlusWebSocketServer.d(jSONObject.toString());
            }
        } catch (Exception unused) {
        }
    }

    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    public void onCreate() {
        super.onCreate();
        this.mainHandler = new Handler(Looper.getMainLooper());
        HandlerThread handlerThread = new HandlerThread("WifiSetupWorker");
        this.workerThread = handlerThread;
        handlerThread.start();
        this.workerHandler = new Handler(this.workerThread.getLooper());
        this.wifiManager = (WifiManager) getApplicationContext().getSystemService("wifi");
        this.connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        this.apManager = new ef(this);
        h b = h.b();
        b.getClass();
        b.a = getApplicationContext();
        b.a();
    }

    public void onDestroy() {
        Runnable runnable = this.wifiConnectionTimeoutRunnable;
        if (runnable != null) {
            this.mainHandler.removeCallbacks(runnable);
            this.wifiConnectionTimeoutRunnable = null;
        }
        unregisterConnectivityReceiver();
        if (this.isApModeActive) {
            this.apManager.b();
        }
        HandlerThread handlerThread = this.workerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            this.workerThread = null;
        }
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            return 2;
        }
        String action = intent.getAction();
        Thread.currentThread().getName();
        Looper.myLooper();
        Looper.getMainLooper();
        if (ACTION_START_AP.equals(action)) {
            lambda$connectToWifi$9();
        } else if (ACTION_STOP_AP.equals(action)) {
            stopApMode();
        } else if (ACTION_CHECK_CONNECTIVITY.equals(action)) {
            if (!hasInternetConnectivity()) {
                lambda$connectToWifi$9();
            } else {
                stopSelf();
            }
        }
        return 2;
    }

    public void setListener(WifiSetupListener wifiSetupListener) {
        this.listener = wifiSetupListener;
    }

    public void stopApMode() {
        if (this.isApModeActive) {
            this.workerHandler.post(new Runnable() {
                public final void run() {
                    WifiSetupService.this.lambda$stopApMode$3();
                }
            });
        }
    }

    public void unregisterConnectivityReceiver() {
        Runnable runnable = this.wifiConnectionTimeoutRunnable;
        if (runnable != null) {
            this.mainHandler.removeCallbacks(runnable);
            this.wifiConnectionTimeoutRunnable = null;
        }
        BroadcastReceiver broadcastReceiver = this.connectivityReceiver;
        if (broadcastReceiver != null) {
            try {
                unregisterReceiver(broadcastReceiver);
            } catch (Exception unused) {
            }
            this.connectivityReceiver = null;
            Log.d(TAG, "Unregistered connectivity receiver");
        }
    }
}
