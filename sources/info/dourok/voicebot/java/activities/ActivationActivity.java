package info.dourok.voicebot.java.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import defpackage.h;
import defpackage.u7;
import info.dourok.voicebot.R;
import info.dourok.voicebot.java.data.model.DeviceInfo;
import info.dourok.voicebot.java.services.OtaService;
import info.dourok.voicebot.java.services.WifiSetupService;
import info.dourok.voicebot.java.utils.MicroWakeWordDetector;
import j$.util.Objects;
import java.util.ArrayList;

public class ActivationActivity extends BaseActivity implements View.OnClickListener, WifiSetupService.WifiSetupListener {
    private static final int AUDIO_REPLAY_INTERVAL_MS = 15000;
    private static final int BUTTON_CODE_AP_MODE = 275;
    private static final int BUTTON_HOLD_THRESHOLD_MS = 6000;
    private static final int INTERNET_CHECK_INTERVAL_MS = 3000;
    private static final int POLLING_INTERVAL_MS = 3000;
    private static final String TAG = "ActivationActivity";
    /* access modifiers changed from: private */
    public u7 activatedResponse;
    private String activationCode;
    private Runnable buttonHoldCheckRunnable;
    private long buttonPressStartTime = 0;
    private Button buttonRetry;
    private k1 deviceInfoRepository;
    /* access modifiers changed from: private */
    public boolean isCheckingInternet = false;
    /* access modifiers changed from: private */
    public boolean isInWifiSetupMode = false;
    /* access modifiers changed from: private */
    public boolean isPolling = false;
    /* access modifiers changed from: private */
    public boolean isWifiServiceBound = false;
    private long lastAudioPlaybackTime = 0;
    /* access modifiers changed from: private */
    public final Handler mainHandler = new Handler(Looper.getMainLooper());
    private OtaService otaService;
    private int pollingAttempts = 0;
    /* access modifiers changed from: private */
    public Handler pollingHandler;
    private ProgressBar progressBar;
    private h soundPlayer;
    private TextView textViewActivationCode;
    private TextView textViewInstructions;
    /* access modifiers changed from: private */
    public TextView textViewStatus;
    private TextView textViewTitle;
    private final ServiceConnection wifiServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ActivationActivity.this.wifiSetupService = ((WifiSetupService.LocalBinder) iBinder).getService();
            ActivationActivity.this.wifiSetupService.setListener(ActivationActivity.this);
            ActivationActivity.this.isWifiServiceBound = true;
            Log.d(ActivationActivity.TAG, "WifiSetupService bound");
        }

        public void onServiceDisconnected(ComponentName componentName) {
            ActivationActivity.this.wifiSetupService = null;
            ActivationActivity.this.isWifiServiceBound = false;
            Log.d(ActivationActivity.TAG, "WifiSetupService unbound");
        }
    };
    /* access modifiers changed from: private */
    public WifiSetupService wifiSetupService;

    public class AnonymousClass10 implements d0<u7> {
        public AnonymousClass10() {
        }

        public void lambda$onError$1() {
            ActivationActivity activationActivity = ActivationActivity.this;
            activationActivity.saveMqttConfigAndProceed(activationActivity.activatedResponse);
        }

        public void lambda$onSuccess$0(u7 u7Var) {
            Log.d(ActivationActivity.TAG, "Final /ota request completed like ESP32 - proceeding with MQTT setup");
            if (u7Var == null || u7Var.b == null) {
                u7Var = ActivationActivity.this.activatedResponse;
            }
            ActivationActivity.this.saveMqttConfigAndProceed(u7Var);
        }

        public void onError(Exception exc) {
            ActivationActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    AnonymousClass10.this.lambda$onError$1();
                }
            });
        }

        public void onSuccess(final u7 u7Var) {
            ActivationActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    AnonymousClass10.this.lambda$onSuccess$0(u7Var);
                }
            });
        }
    }

    /* renamed from: info.dourok.voicebot.java.activities.ActivationActivity$AnonymousClass3  reason: case insensitive filesystem */
    public class C0027AnonymousClass3 implements h.d {
        public C0027AnonymousClass3() {
        }

        public void lambda$onPlaybackComplete$0() {
            ActivationActivity.this.checkInternetAndProceed();
        }

        public void lambda$onPlaybackError$1() {
            ActivationActivity.this.checkInternetAndProceed();
        }

        public void onPlaybackComplete() {
            Log.d(ActivationActivity.TAG, "No internet sound finished, scheduling retry in 3000ms");
            if (ActivationActivity.this.isCheckingInternet && !ActivationActivity.this.isInWifiSetupMode) {
                ActivationActivity.this.mainHandler.postDelayed(new Runnable() {
                    public final void run() {
                        C0027AnonymousClass3.this.lambda$onPlaybackComplete$0();
                    }
                }, 3000);
            }
        }

        public void onPlaybackError(String str) {
            if (ActivationActivity.this.isCheckingInternet && !ActivationActivity.this.isInWifiSetupMode) {
                ActivationActivity.this.mainHandler.postDelayed(new Runnable() {
                    public final void run() {
                        C0027AnonymousClass3.this.lambda$onPlaybackError$1();
                    }
                }, 3000);
            }
        }

        public void onPlaybackStarted() {
            Log.d(ActivationActivity.TAG, "No internet sound started");
        }
    }

    /* renamed from: info.dourok.voicebot.java.activities.ActivationActivity$AnonymousClass4  reason: case insensitive filesystem */
    public class C0028AnonymousClass4 implements b0 {
        public C0028AnonymousClass4() {
        }

        public void lambda$onDeviceInfoReady$0(DeviceInfo deviceInfo) {
            ActivationActivity.this.requestActivationCode(deviceInfo);
        }

        public void lambda$onError$2(String str) {
            ActivationActivity.this.setLoadingState(false, "");
            ActivationActivity activationActivity = ActivationActivity.this;
            activationActivity.showError("Using demo device info: " + str);
            ActivationActivity.this.requestActivationCode((DeviceInfo) null);
        }

        public void lambda$onPermissionRequired$1(String str) {
            ActivationActivity.this.setLoadingState(false, "");
            ActivationActivity activationActivity = ActivationActivity.this;
            activationActivity.showError("Permission required: " + str + "\nUsing fallback data for demo");
            ActivationActivity.this.requestActivationCode((DeviceInfo) null);
        }

        public void onDeviceInfoReady(final DeviceInfo deviceInfo) {
            String str;
            if (deviceInfo != null) {
                str = "available";
            } else {
                str = "null";
            }
            Log.d(ActivationActivity.TAG, "DeviceInfo received: ".concat(str));
            ActivationActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0028AnonymousClass4.this.lambda$onDeviceInfoReady$0(deviceInfo);
                }
            });
        }

        public void onError(final String str) {
            ActivationActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0028AnonymousClass4.this.lambda$onError$2(str);
                }
            });
        }

        public void onPermissionRequired(final String str) {
            ActivationActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0028AnonymousClass4.this.lambda$onPermissionRequired$1(str);
                }
            });
        }
    }

    /* renamed from: info.dourok.voicebot.java.activities.ActivationActivity$AnonymousClass5  reason: case insensitive filesystem */
    public class C0029AnonymousClass5 implements d0<u7> {
        public C0029AnonymousClass5() {
        }

        public void lambda$onError$1(Exception exc) {
            ActivationActivity.this.setLoadingState(false, "");
            ActivationActivity activationActivity = ActivationActivity.this;
            activationActivity.showError("Failed to contact activation server: " + exc.getMessage() + "\nThis may be due to server configuration or network issues.\nYou can skip for testing purposes.");
            ActivationActivity.this.showRetryButton(true);
        }

        public void lambda$onSuccess$0(u7 u7Var) {
            ActivationActivity.this.handleOtaResponse(u7Var);
        }

        public void onError(final Exception exc) {
            ActivationActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0029AnonymousClass5.this.lambda$onError$1(exc);
                }
            });
        }

        public void onSuccess(final u7 u7Var) {
            ActivationActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0029AnonymousClass5.this.lambda$onSuccess$0(u7Var);
                }
            });
        }
    }

    public class AnonymousClass7 implements d0<u7> {
        public AnonymousClass7() {
        }

        public void lambda$onError$2() {
            ActivationActivity.this.sendPeriodicActivationRequests();
        }

        public void lambda$onError$3(Exception exc) {
            exc.getMessage();
            if (ActivationActivity.this.isPolling) {
                ActivationActivity.this.pollingHandler.postDelayed(new Runnable() {
                    public final void run() {
                        AnonymousClass7.this.lambda$onError$2();
                    }
                }, 3000);
            }
        }

        public void lambda$onSuccess$0() {
            ActivationActivity.this.sendPeriodicActivationRequests();
        }

        public void lambda$onSuccess$1(u7 u7Var) {
            boolean z;
            boolean z2 = true;
            if (u7Var.b == null || u7Var.a != null) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                Log.d(ActivationActivity.TAG, "🎉 Device activated! Server returned MQTT config (no activation code anymore)");
                ActivationActivity.this.isPolling = false;
                ActivationActivity.this.activatedResponse = u7Var;
                ActivationActivity.this.onActivationComplete();
                return;
            }
            if (u7Var.a == null) {
                z2 = false;
            }
            if (z2) {
                if (ActivationActivity.this.isPolling) {
                    ActivationActivity.this.pollingHandler.postDelayed(new Runnable() {
                        public final void run() {
                            AnonymousClass7.this.lambda$onSuccess$0();
                        }
                    }, 3000);
                }
            } else if (ActivationActivity.this.isPolling) {
                ActivationActivity.this.pollingHandler.postDelayed(new Runnable() {
                    public final void run() {
                        AnonymousClass7.this.lambda$onSuccess$0();
                    }
                }, 3000);
            }
        }

        public void onError(final Exception exc) {
            ActivationActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    AnonymousClass7.this.lambda$onError$3(exc);
                }
            });
        }

        public void onSuccess(final u7 u7Var) {
            ActivationActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    AnonymousClass7.this.lambda$onSuccess$1(u7Var);
                }
            });
        }
    }

    public class AnonymousClass8 implements d0<u7> {
        public AnonymousClass8() {
        }

        public void lambda$onError$1(Exception exc) {
            exc.getMessage();
            ActivationActivity.this.textViewStatus.setText("Checking activation status... (network error)");
        }

        public void lambda$onSuccess$0(u7 u7Var) {
            boolean z;
            if (u7Var.b == null || u7Var.a != null) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                Log.d(ActivationActivity.TAG, "Device activation confirmed by server");
                ActivationActivity.this.activatedResponse = u7Var;
                ActivationActivity.this.onActivationComplete();
                return;
            }
            ActivationActivity.this.textViewStatus.setText("Checking activation status with server...");
        }

        public void onError(final Exception exc) {
            ActivationActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    AnonymousClass8.this.lambda$onError$1(exc);
                }
            });
        }

        public void onSuccess(final u7 u7Var) {
            ActivationActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    AnonymousClass8.this.lambda$onSuccess$0(u7Var);
                }
            });
        }
    }

    private void checkMockActivationStatus() {
        this.deviceInfoRepository.a();
        this.otaService.checkActivationStatus(this.deviceInfoRepository.a(), "https://api.tenclass.net/xiaozhi/ota/", new AnonymousClass8());
    }

    private void cleanupResources() {
        Log.d(TAG, "Cleaning up resources before navigating to ChatActivity");
        stopPolling();
        h hVar = this.soundPlayer;
        if (hVar != null) {
            hVar.g();
            HandlerThread handlerThread = hVar.e;
            if (handlerThread != null) {
                handlerThread.quitSafely();
                hVar.e = null;
            }
            hVar.b = null;
            this.soundPlayer = null;
        }
    }

    private void enterWifiSetupMode() {
        if (this.isWifiServiceBound && this.wifiSetupService != null && !this.isInWifiSetupMode) {
            this.isInWifiSetupMode = true;
            Log.d(TAG, "Entering WiFi setup mode from ActivationActivity (button 275 held for 6s)");
            this.isCheckingInternet = false;
            this.mainHandler.removeCallbacksAndMessages((Object) null);
            h hVar = this.soundPlayer;
            if (hVar != null) {
                hVar.g();
            }
            stopPolling();
            this.wifiSetupService.lambda$connectToWifi$9();
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

    private void initializeData() {
        this.deviceInfoRepository = this.dependencies.b();
        h1 h1Var = this.dependencies;
        if (h1Var.i == null) {
            synchronized (h1Var) {
                if (h1Var.i == null) {
                    h1Var.i = new OtaService(h1Var.n, h1Var.a, h1Var.c());
                }
            }
        }
        this.otaService = h1Var.i;
        h b = h.b();
        this.soundPlayer = b;
        b.getClass();
        b.a = getApplicationContext();
        b.a();
    }

    private void initializeViews() {
        this.textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        this.textViewActivationCode = (TextView) findViewById(R.id.textViewActivationCode);
        this.textViewInstructions = (TextView) findViewById(R.id.textViewInstructions);
        this.textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        this.buttonRetry = (Button) findViewById(R.id.buttonRetry);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.buttonRetry.setOnClickListener(this);
        this.pollingHandler = new Handler(Looper.getMainLooper());
    }

    private void playActivationCodeAudio(String str) {
        if (this.isInWifiSetupMode) {
            Log.d(TAG, "Skipping activation code audio - in WiFi setup mode");
        } else if (this.soundPlayer != null && str != null && !str.isEmpty()) {
            Log.d(TAG, "Playing activation code audio: ".concat(str));
            h hVar = this.soundPlayer;
            if (hVar.a != null && !str.isEmpty()) {
                ArrayList arrayList = hVar.h;
                arrayList.clear();
                arrayList.add("activation.mp3");
                for (char c : str.toCharArray()) {
                    if (Character.isDigit(c)) {
                        arrayList.add(c + ".mp3");
                    }
                }
                Objects.toString(arrayList);
                hVar.g = 0;
                hVar.f = true;
                h.d dVar = hVar.b;
                if (dVar != null) {
                    dVar.onPlaybackStarted();
                }
                hVar.c();
            }
        }
    }

    private void retryActivation() {
        stopPolling();
        showRetryButton(false);
        this.textViewActivationCode.setVisibility(8);
        this.textViewInstructions.setVisibility(8);
        startActivationProcess();
    }

    private void sendDeviceInfoToServer(DeviceInfo deviceInfo) {
        String str;
        Log.d(TAG, "Sending DeviceInfo to OTA server: https://api.tenclass.net/xiaozhi/ota/");
        StringBuilder sb = new StringBuilder("DeviceInfo details - MAC: ");
        String str2 = "null";
        if (deviceInfo != null) {
            str = deviceInfo.l;
        } else {
            str = str2;
        }
        sb.append(str);
        sb.append(", UUID: ");
        if (deviceInfo != null) {
            str2 = deviceInfo.q;
        }
        sb.append(str2);
        Log.d(TAG, sb.toString());
        this.otaService.checkActivationStatus(deviceInfo, "https://api.tenclass.net/xiaozhi/ota/", new C0029AnonymousClass5());
    }

    private void showSuccess(String str) {
        Toast.makeText(this, str, 0).show();
    }

    private void startActivationProcess() {
        setLoadingState(true, "Đang kiểm tra kết nối internet...");
        checkInternetAndProceed();
    }

    private void startDeviceInfoRequest() {
        setLoadingState(true, "Initializing device activation...");
        this.textViewStatus.setText("");
        this.textViewStatus.setVisibility(8);
        k1 k1Var = this.deviceInfoRepository;
        C0028AnonymousClass4 anonymousClass4 = new C0028AnonymousClass4();
        k1Var.getClass();
        k1Var.c.execute(new j1(k1Var, anonymousClass4));
    }

    private void startPolling() {
        if (!this.isPolling) {
            this.isPolling = true;
            this.textViewStatus.setText("Waiting for activation...");
            pollActivationStatus();
        }
    }

    private void stopPolling() {
        this.isPolling = false;
        this.pollingHandler.removeCallbacksAndMessages((Object) null);
        this.lastAudioPlaybackTime = 0;
    }

    public void checkInternetAndProceed() {
        if (this.isInWifiSetupMode) {
            Log.d(TAG, "In WiFi setup mode - skipping internet check");
        } else if (hasInternetConnectivity()) {
            this.isCheckingInternet = false;
            startDeviceInfoRequest();
        } else {
            this.isCheckingInternet = false;
            setLoadingState(true, "Không có kết nối internet.");
            this.textViewStatus.setVisibility(0);
            this.textViewStatus.setText("⚠️ Không có internet.");
            h hVar = this.soundPlayer;
            hVar.b = null;
            hVar.f("khong_co_internet.mp3");
        }
    }

    public void displayActivationCode(String str) {
        this.textViewActivationCode.setText(str);
        this.textViewActivationCode.setVisibility(0);
        TextView textView = this.textViewInstructions;
        textView.setText("Please visit xiaozhi.me and enter this code to activate your device.\n\nCode: " + str + "\n\nWe'll automatically detect when activation is complete.");
        this.textViewInstructions.setVisibility(0);
        this.lastAudioPlaybackTime = System.currentTimeMillis();
        playActivationCodeAudio(str);
    }

    public void handleOtaResponse(u7 u7Var) {
        boolean z;
        boolean z2;
        if (u7Var.b == null || u7Var.a != null) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            Log.d(TAG, "Device activation confirmed by server");
            this.activatedResponse = u7Var;
            setLoadingState(false, "");
            proceedWithActivation();
            return;
        }
        u7.a aVar = u7Var.a;
        if (aVar != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            this.activationCode = aVar.a;
            Log.d(TAG, "Server provided activation code: " + this.activationCode);
            setLoadingState(false, "");
            displayActivationCode(this.activationCode);
            startEsp32ActivationFlow();
            return;
        }
        setLoadingState(false, "");
        showError("Unexpected server response");
        showRetryButton(true);
    }

    public void lambda$navigateToChatActivity$0() {
        startActivity(ChatActivity.createIntent(this, MicroWakeWordDetector.class));
        finish();
    }

    public void lambda$onKeyDown$1() {
        if (this.buttonPressStartTime > 0 && !this.isInWifiSetupMode) {
            enterWifiSetupMode();
        }
    }

    public void navigateToChatActivity() {
        cleanupResources();
        this.pollingHandler.postDelayed(new Runnable() {
            public final void run() {
                ActivationActivity.this.lambda$navigateToChatActivity$0();
            }
        }, 5000);
    }

    public void onActivationComplete() {
        this.isPolling = false;
        this.lastAudioPlaybackTime = 0;
        h hVar = this.soundPlayer;
        if (hVar != null && hVar.f) {
            Log.d(TAG, "Stopping activation code audio playback");
            this.soundPlayer.g();
        }
        this.textViewStatus.setText("✅ Activation successful! Getting final configuration...");
        showSuccess("Device activated successfully");
        proceedWithActivation();
    }

    public void onApModeStarted(String str, String str2, String str3) {
        Log.d(TAG, "AP Mode started - SSID: " + str + ", IP: " + str3);
    }

    public void onApModeStopped() {
        this.isInWifiSetupMode = false;
        Log.d(TAG, "AP Mode stopped");
    }

    public void onClick(View view) {
        if (view.getId() == R.id.buttonRetry) {
            retryActivation();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_activation);
        Log.d(TAG, "ActivationActivity onCreate");
        bindService(new Intent(this, WifiSetupService.class), this.wifiServiceConnection, 1);
        initializeViews();
        initializeData();
        startActivationProcess();
    }

    public void onDestroy() {
        super.onDestroy();
        cleanupResources();
        if (this.isWifiServiceBound) {
            unbindService(this.wifiServiceConnection);
            this.isWifiServiceBound = false;
        }
    }

    public void onError(String str) {
        this.isInWifiSetupMode = false;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != BUTTON_CODE_AP_MODE) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.buttonPressStartTime == 0) {
            this.buttonPressStartTime = System.currentTimeMillis();
            AnonymousClass5 r5 = new Runnable() {
                public final void run() {
                    ActivationActivity.this.lambda$onKeyDown$1();
                }
            };
            this.buttonHoldCheckRunnable = r5;
            this.mainHandler.postDelayed(r5, 6000);
        }
        return true;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == BUTTON_CODE_AP_MODE) {
            this.buttonPressStartTime = 0;
            Runnable runnable = this.buttonHoldCheckRunnable;
            if (runnable != null) {
                this.mainHandler.removeCallbacks(runnable);
                this.buttonHoldCheckRunnable = null;
            }
            return true;
        } else if (super.onKeyUp(i, keyEvent)) {
            return true;
        } else {
            return false;
        }
    }

    public void onWifiConnected(String str) {
        this.isInWifiSetupMode = false;
        Log.d(TAG, "WiFi connected to: " + str);
        recreate();
    }

    public void onWifiConnectionFailed(String str, String str2) {
        Log.d(TAG, "WiFi connection failed: " + str + " - " + str2);
    }

    public void pollActivationStatus() {
        if (this.isPolling) {
            checkMockActivationStatus();
            this.pollingHandler.postDelayed(new Runnable() {
                public final void run() {
                    ActivationActivity.this.pollActivationStatus();
                }
            }, 3000);
        }
    }

    public void proceedWithActivation() {
        this.textViewStatus.setText("✅ Activation successful! Getting final configuration...");
        Log.d(TAG, "Sending final /ota request like ESP32 after activation success");
        this.otaService.checkActivationStatus(this.deviceInfoRepository.a(), "https://api.tenclass.net/xiaozhi/ota/", new AnonymousClass10());
    }

    public void requestActivationCode(DeviceInfo deviceInfo) {
        setLoadingState(true, "Requesting activation code from XiaoZhi server...");
        sendDeviceInfoToServer(deviceInfo);
    }

    public void saveMqttConfigAndProceed(u7 u7Var) {
        u7.b bVar;
        if (u7Var != null && (bVar = u7Var.b) != null) {
            Log.d(TAG, "⚡ Saving Xiaozhi MQTT config to RAM (NOT SharedPreferences)");
            Log.d(TAG, "  → Endpoint: " + bVar.b);
            Log.d(TAG, "  → ClientId: " + bVar.a);
            Log.d(TAG, "  → Username: " + bVar.f);
            Log.d(TAG, "  → Will be cleared on app restart → force re-fetch with new MAC");
            String str = bVar.b;
            String str2 = bVar.a;
            String str3 = bVar.f;
            String str4 = bVar.c;
            c7 c7Var = new c7(str, str2, str3, str4, bVar.d, bVar.e);
            va j = this.dependencies.j();
            AnonymousClass3 r2 = new d0<Void>() {
                public void onError(Exception exc) {
                    ActivationActivity.this.showError("Failed to save server configuration");
                }

                public void onSuccess(Void voidR) {
                    Log.d(ActivationActivity.TAG, "✓ MQTT config saved to RAM cache successfully");
                    ActivationActivity.this.navigateToChatActivity();
                }
            };
            j.getClass();
            try {
                j.b = c7Var;
                if (str4 != null) {
                    str4.substring(Math.max(0, str4.length() - 4));
                }
                r2.onSuccess(null);
            } catch (Exception e) {
                r2.onError(e);
            }
        }
    }

    public void sendPeriodicActivationRequests() {
        String str;
        if (this.isPolling) {
            int i = this.pollingAttempts + 1;
            this.pollingAttempts = i;
            if (i % 10 == 1) {
                Log.d(TAG, "Activation polling attempt " + this.pollingAttempts + " (polling until activated)");
            }
            if (System.currentTimeMillis() - this.lastAudioPlaybackTime >= 15000 && (str = this.activationCode) != null && !str.isEmpty()) {
                if (!this.soundPlayer.f) {
                    Log.d(TAG, "Replaying activation code audio (15s elapsed since last playback)");
                    playActivationCodeAudio(this.activationCode);
                    this.lastAudioPlaybackTime = System.currentTimeMillis();
                } else {
                    Log.d(TAG, "Audio is still playing, skipping replay");
                }
            }
            this.otaService.checkActivationStatus(this.deviceInfoRepository.a(), "https://api.tenclass.net/xiaozhi/ota/", new AnonymousClass7());
        }
    }

    public void setLoadingState(boolean z, String str) {
        int i;
        ProgressBar progressBar2 = this.progressBar;
        int i2 = 0;
        if (z) {
            i = 0;
        } else {
            i = 8;
        }
        progressBar2.setVisibility(i);
        this.textViewStatus.setText(str);
        TextView textView = this.textViewStatus;
        if (str.isEmpty()) {
            i2 = 8;
        }
        textView.setVisibility(i2);
    }

    public void showError(String str) {
        TextView textView = this.textViewStatus;
        textView.setText("❌ " + str);
        this.textViewStatus.setVisibility(0);
        Toast.makeText(this, str, 1).show();
    }

    public void showRetryButton(boolean z) {
        this.buttonRetry.setVisibility(z ? 0 : 8);
    }

    public void startEsp32ActivationFlow() {
        Log.d(TAG, "Starting ESP32-style activation flow - sending periodic activation requests to /activate only");
        this.textViewStatus.setText("Waiting for user to enter activation code on xiaozhi.me...");
        this.isPolling = true;
        this.pollingAttempts = 0;
        sendPeriodicActivationRequests();
    }
}
