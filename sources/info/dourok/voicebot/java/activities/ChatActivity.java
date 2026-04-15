package info.dourok.voicebot.java.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import defpackage.i0;
import defpackage.t9;
import defpackage.w6;
import defpackage.x7;
import info.dourok.voicebot.R;
import info.dourok.voicebot.java.VoiceBotApplication;
import info.dourok.voicebot.java.audio.MusicPlayer;
import info.dourok.voicebot.java.audio.OpusDecoder;
import info.dourok.voicebot.java.audio.b;
import info.dourok.voicebot.java.audio.e;
import info.dourok.voicebot.java.audio.g;
import info.dourok.voicebot.java.audio.j;
import info.dourok.voicebot.java.receivers.AlarmReceiver;
import info.dourok.voicebot.java.services.WifiSetupService;
import info.dourok.voicebot.java.utils.MicroWakeWordDetector;
import info.dourok.voicebot.java.utils.a;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.List;
import org.java_websocket.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatActivity extends BaseActivity implements View.OnClickListener, WifiSetupService.WifiSetupListener {
    private static final int BUTTON_HOLD_THRESHOLD_MS = 6000;
    private static final long CLICK_TIMEOUT_MS = 500;
    public static final String EXTRA_WAKE_WORD_DETECTOR_CLASS = "info.dourok.voicebot.java.activities.ChatActivity.EXTRA_WAKE_WORD_DETECTOR_CLASS";
    private static final int KEY_R1_MAIN = 275;
    private static final long LED_TOGGLE_HOLD_MS = 3000;
    private static final String TAG = "ChatActivity";
    private static a staticWakeWordDetector;
    /* access modifiers changed from: private */
    public b audioManager;
    /* access modifiers changed from: private */
    public MediaPlayer bluetoothSoundPlayer = null;
    private Runnable buttonHoldCheckRunnable;
    private long buttonPressStartTime = 0;
    /* access modifiers changed from: private */
    public Button buttonTestMic;
    private Button buttonWakeUp;
    private h0 chatAdapter;
    /* access modifiers changed from: private */
    public int clickCount = 0;
    /* access modifiers changed from: private */
    public Runnable clickCountResetRunnable = null;
    /* access modifiers changed from: private */
    public Handler heartbeatHandler;
    private Runnable heartbeatRunnable;
    /* access modifiers changed from: private */
    public boolean isConnected = false;
    private boolean isInWifiSetupMode = false;
    private boolean isListening = false;
    /* access modifiers changed from: private */
    public boolean isWifiServiceBound = false;
    /* access modifiers changed from: private */
    public long lastButtonClickTime = 0;
    /* access modifiers changed from: private */
    public long lastHeartbeat = 0;
    private Runnable ledToggleCheckRunnable = null;
    private boolean ledToggled = false;
    /* access modifiers changed from: private */
    public Handler mainHandler;
    private List<i0> messages;
    /* access modifiers changed from: private */
    public w6 microphoneTester;
    /* access modifiers changed from: private */
    public t9 protocolManager;
    private RecyclerView recyclerViewMessages;
    private Runnable singleClickRunnable = null;
    private TextView textViewEmotion;
    /* access modifiers changed from: private */
    public TextView textViewMicStatus;
    private TextView textViewStatus;
    /* access modifiers changed from: private */
    public a wakeWordDetector;
    private Class<? extends a> wakeWordDetectorClass = MicroWakeWordDetector.class;
    /* access modifiers changed from: private */
    public boolean wakeWordEnabled = true;
    private volatile boolean watchdogRunning = false;
    private Thread watchdogThread;
    private String websocketToken;
    private String websocketUrl;
    private final ServiceConnection wifiServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ChatActivity.this.wifiSetupService = ((WifiSetupService.LocalBinder) iBinder).getService();
            ChatActivity.this.wifiSetupService.setListener(ChatActivity.this);
            ChatActivity.this.isWifiServiceBound = true;
            Log.d(ChatActivity.TAG, "WifiSetupService bound");
        }

        public void onServiceDisconnected(ComponentName componentName) {
            ChatActivity.this.wifiSetupService = null;
            ChatActivity.this.isWifiServiceBound = false;
            Log.d(ChatActivity.TAG, "WifiSetupService unbound");
        }
    };
    /* access modifiers changed from: private */
    public WifiSetupService wifiSetupService;

    /* renamed from: info.dourok.voicebot.java.activities.ChatActivity$24  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass24 {
        static final /* synthetic */ int[] $SwitchMap$info$dourok$voicebot$java$protocol$DeviceState;

        /* JADX WARNING: Can't wrap try/catch for region: R(11:0|1|2|3|(2:5|6)|7|9|10|11|12|14) */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0017 */
        static {
            /*
                m1[] r0 = defpackage.m1.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$info$dourok$voicebot$java$protocol$DeviceState = r0
                r1 = 1
                r2 = 0
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x000d }
            L_0x000d:
                r0 = 2
                int[] r2 = $SwitchMap$info$dourok$voicebot$java$protocol$DeviceState     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2[r1] = r0     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                r1 = 3
                int[] r2 = $SwitchMap$info$dourok$voicebot$java$protocol$DeviceState     // Catch:{ NoSuchFieldError -> 0x0017 }
                r2[r0] = r1     // Catch:{ NoSuchFieldError -> 0x0017 }
            L_0x0017:
                int[] r0 = $SwitchMap$info$dourok$voicebot$java$protocol$DeviceState     // Catch:{ NoSuchFieldError -> 0x001c }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001c }
            L_0x001c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: info.dourok.voicebot.java.activities.ChatActivity.AnonymousClass24.<clinit>():void");
        }
    }

    /* renamed from: info.dourok.voicebot.java.activities.ChatActivity$AnonymousClass3  reason: case insensitive filesystem */
    public class C0030AnonymousClass3 implements c0 {
        public C0030AnonymousClass3() {
        }

        public void lambda$onAudioChannelClosed$7() {
            ChatActivity.this.updateConnectionStatus("Audio channel closed");
        }

        public void lambda$onAudioChannelOpened$6() {
            ChatActivity.this.updateConnectionStatus("Audio channel ready");
        }

        public void lambda$onConnected$0() {
            ChatActivity.this.isConnected = true;
            ChatActivity.this.updateConnectionStatus("Connected");
            ChatActivity.this.enableWakeButton(true);
            if (ChatActivity.this.wakeWordDetector != null && ChatActivity.this.wakeWordEnabled) {
                ChatActivity.this.wakeWordDetector.startListening();
            }
        }

        public void lambda$onDisconnected$1() {
            ChatActivity.this.isConnected = false;
            ChatActivity.this.updateConnectionStatus("Disconnected");
            ChatActivity.this.enableWakeButton(false);
            if (ChatActivity.this.wakeWordDetector != null) {
                ChatActivity.this.wakeWordDetector.stopListening();
            }
        }

        public void lambda$onIncomingJson$2(JSONObject jSONObject) {
            ChatActivity.this.handleIncomingMessage(jSONObject);
        }

        public void lambda$onNetworkError$3(String str) {
            ChatActivity chatActivity = ChatActivity.this;
            chatActivity.updateConnectionStatus("Network error: " + str);
            ChatActivity.this.enableWakeButton(false);
        }

        public void lambda$onSessionEnded$5() {
            ChatActivity.this.updateConnectionStatus("Session ended");
        }

        public void lambda$onSessionStarted$4(String str) {
            ChatActivity chatActivity = ChatActivity.this;
            chatActivity.updateConnectionStatus("Session started: " + str);
        }

        public void onAudioChannelClosed() {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0030AnonymousClass3.this.lambda$onAudioChannelClosed$7();
                }
            });
        }

        public void onAudioChannelOpened() {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0030AnonymousClass3.this.lambda$onAudioChannelOpened$6();
                }
            });
        }

        public void onConnected() {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0030AnonymousClass3.this.lambda$onConnected$0();
                }
            });
        }

        public void onDisconnected() {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0030AnonymousClass3.this.lambda$onDisconnected$1();
                }
            });
        }

        public void onIncomingAudio(byte[] bArr) {
            b c = ChatActivity.this.audioManager;
            AnonymousClass4 r1 = new d0<Void>() {
                public void onError(Exception exc) {
                }

                public void onSuccess(Void voidR) {
                    Log.d(ChatActivity.TAG, "Audio playback successful");
                }
            };
            c.getClass();
            info.dourok.voicebot.java.audio.a aVar = new info.dourok.voicebot.java.audio.a(new bm(r1));
            OpusDecoder opusDecoder = c.b;
            if (opusDecoder.a == 0) {
                aVar.onError("Decoder not initialized");
                return;
            }
            opusDecoder.b.execute(new g(opusDecoder, bArr, aVar));
        }

        public void onIncomingJson(final JSONObject jSONObject) {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0030AnonymousClass3.this.lambda$onIncomingJson$2(jSONObject);
                }
            });
        }

        public void onNetworkError(final String str) {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0030AnonymousClass3.this.lambda$onNetworkError$3(str);
                }
            });
        }

        public void onSessionEnded() {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0030AnonymousClass3.this.lambda$onSessionEnded$5();
                }
            });
        }

        public void onSessionStarted(final String str) {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0030AnonymousClass3.this.lambda$onSessionStarted$4(str);
                }
            });
        }
    }

    /* renamed from: info.dourok.voicebot.java.activities.ChatActivity$AnonymousClass4  reason: case insensitive filesystem */
    public class C0031AnonymousClass4 implements a0 {
        public C0031AnonymousClass4() {
        }

        public void lambda$onConnectionEstablished$0() {
            ChatActivity.this.isConnected = true;
            ChatActivity.this.updateConnectionStatus("Connection established");
            ChatActivity.this.enableWakeButton(true);
        }

        public void lambda$onConnectionFailed$1(String str) {
            ChatActivity chatActivity = ChatActivity.this;
            chatActivity.updateConnectionStatus("Connection failed: " + str);
            ChatActivity.this.enableWakeButton(false);
        }

        public void lambda$onConnectionLost$2() {
            ChatActivity.this.updateConnectionStatus("Connection lost");
            ChatActivity.this.enableWakeButton(false);
        }

        public void lambda$onConnectionRetrying$3() {
            ChatActivity.this.updateConnectionStatus("Reconnecting...");
        }

        public void lambda$onReconnectAttempt$4(int i, long j) {
            ChatActivity chatActivity = ChatActivity.this;
            chatActivity.updateConnectionStatus("Reconnect attempt " + i + " in " + j + "ms");
        }

        public void lambda$onReconnectGiveUp$6(int i) {
            ChatActivity chatActivity = ChatActivity.this;
            chatActivity.updateConnectionStatus("Reconnection failed after " + i + " attempts");
            ChatActivity.this.enableWakeButton(false);
        }

        public void lambda$onReconnectSuccess$5() {
            ChatActivity.this.updateConnectionStatus("Reconnected successfully");
        }

        public void onConnectionEstablished() {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0031AnonymousClass4.this.lambda$onConnectionEstablished$0();
                }
            });
        }

        public void onConnectionFailed(final String str) {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0031AnonymousClass4.this.lambda$onConnectionFailed$1(str);
                }
            });
        }

        public void onConnectionLost() {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0031AnonymousClass4.this.lambda$onConnectionLost$2();
                }
            });
        }

        public void onConnectionRetrying() {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0031AnonymousClass4.this.lambda$onConnectionRetrying$3();
                }
            });
        }

        public void onReconnectAttempt(final int i, final long j) {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0031AnonymousClass4.this.lambda$onReconnectAttempt$4(i, j);
                }
            });
        }

        public void onReconnectGiveUp(final int i) {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0031AnonymousClass4.this.lambda$onReconnectGiveUp$6(i);
                }
            });
        }

        public void onReconnectSuccess() {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0031AnonymousClass4.this.lambda$onReconnectSuccess$5();
                }
            });
        }
    }

    /* renamed from: info.dourok.voicebot.java.activities.ChatActivity$AnonymousClass5  reason: case insensitive filesystem */
    public class C0032AnonymousClass5 {
        public C0032AnonymousClass5() {
        }

        public void lambda$onError$2(String str) {
            ChatActivity chatActivity = ChatActivity.this;
            chatActivity.updateConnectionStatus("Audio error: " + str);
            ChatActivity.this.stopAudioRecording();
        }

        public void lambda$onRecordingStarted$0() {
            ChatActivity.this.updateRecordingStatus(true);
        }

        public void lambda$onRecordingStopped$1() {
            ChatActivity.this.updateRecordingStatus(false);
        }

        public void onAudioData(byte[] bArr) {
            int i;
            i9 i9Var;
            i9 i9Var2 = ChatActivity.this.protocolManager.f.get();
            boolean z = true;
            if (i9Var2 != null) {
                i = i9Var2.b;
            } else {
                i = 1;
            }
            if (i != 3) {
                z = false;
            }
            if (z && (i9Var = ChatActivity.this.protocolManager.f.get()) != null) {
                i9Var.g(bArr);
            }
        }

        public void onError(final String str) {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0032AnonymousClass5.this.lambda$onError$2(str);
                }
            });
        }

        public void onPlaybackFinished() {
            Log.d(ChatActivity.TAG, "Audio playback finished");
        }

        public void onPlaybackStarted() {
            Log.d(ChatActivity.TAG, "Audio playback started");
        }

        public void onRecordingStarted() {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0032AnonymousClass5.this.lambda$onRecordingStarted$0();
                }
            });
        }

        public void onRecordingStopped() {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0032AnonymousClass5.this.lambda$onRecordingStopped$1();
                }
            });
        }
    }

    /* renamed from: info.dourok.voicebot.java.activities.ChatActivity$AnonymousClass8  reason: case insensitive filesystem */
    public class C0033AnonymousClass8 implements d0<Object> {
        public C0033AnonymousClass8() {
        }

        public void lambda$onError$1() {
            ChatActivity.this.updateConnectionStatus("Configuration error - wake word offline only");
            ChatActivity.this.startWakeWordIfReady();
        }

        public void lambda$onSuccess$0() {
            ChatActivity.this.updateConnectionStatus("Invalid MQTT configuration - wake word offline only");
            ChatActivity.this.startWakeWordIfReady();
        }

        public void onError(Exception exc) {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0033AnonymousClass8.this.lambda$onError$1();
                }
            });
        }

        public void onSuccess(Object obj) {
            if (obj instanceof c7) {
                c7 c7Var = (c7) obj;
                Log.d(ChatActivity.TAG, "Using MQTT configuration like ESP32: " + c7Var.b);
                t9 k = ChatActivity.this.protocolManager;
                t9.d dVar = t9.d.MQTT;
                String str = c7Var.b;
                if (!k.l) {
                    synchronized (k) {
                        if (!k.k) {
                            k.k = true;
                            k.j = 0;
                            k.g.set(dVar);
                            k.d.execute(new r9(k, str, c7Var));
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0033AnonymousClass8.this.lambda$onSuccess$0();
                }
            });
        }
    }

    /* renamed from: info.dourok.voicebot.java.activities.ChatActivity$AnonymousClass9  reason: case insensitive filesystem */
    public class C0034AnonymousClass9 implements cz {
        public C0034AnonymousClass9() {
        }

        public void lambda$onError$1(String str) {
            ChatActivity chatActivity = ChatActivity.this;
            chatActivity.updateConnectionStatus("Recording error: " + str);
        }

        public void lambda$onRecordingStarted$0() {
            ChatActivity.this.addMessage("🎤 Listening...", i0.a.SYSTEM);
        }

        public void onAudioData(byte[] bArr) {
            i9 i9Var = ChatActivity.this.protocolManager.f.get();
            if (i9Var != null) {
                i9Var.g(bArr);
            }
        }

        public void onBitrateChanged(int i) {
            Log.d(ChatActivity.TAG, "Audio bitrate changed to: " + i);
        }

        public void onError(final String str) {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0034AnonymousClass9.this.lambda$onError$1(str);
                }
            });
        }

        public void onRecordingStarted() {
            ChatActivity.this.mainHandler.post(new Runnable() {
                public final void run() {
                    C0034AnonymousClass9.this.lambda$onRecordingStarted$0();
                }
            });
        }

        public void onRecordingStopped() {
        }

        public void onVoiceActivityDetected(boolean z) {
        }
    }

    private void broadcastChatMessageToWS8082(String str, String str2) {
        x aiboxPlusWebSocketServer;
        try {
            if ((getApplicationContext() instanceof VoiceBotApplication) && (aiboxPlusWebSocketServer = ((VoiceBotApplication) getApplicationContext()).getAiboxPlusWebSocketServer()) != null) {
                aiboxPlusWebSocketServer.a(str, str2);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", "chat_message");
                jSONObject.put("message_type", str2);
                jSONObject.put("content", str);
                jSONObject.put("timestamp", System.currentTimeMillis());
                aiboxPlusWebSocketServer.d(jSONObject.toString());
            }
        } catch (Exception unused) {
        }
    }

    private void broadcastChatStateToWS8082(String str, String str2, boolean z) {
        x aiboxPlusWebSocketServer;
        try {
            if ((getApplicationContext() instanceof VoiceBotApplication) && (aiboxPlusWebSocketServer = ((VoiceBotApplication) getApplicationContext()).getAiboxPlusWebSocketServer()) != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", "chat_state");
                jSONObject.put("state", str);
                jSONObject.put("button_text", str2);
                jSONObject.put("button_enabled", z);
                aiboxPlusWebSocketServer.d(jSONObject.toString());
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: broadcastCurrentChatState */
    public void lambda$registerChatBroadcastReceiver$0() {
        boolean z;
        String str;
        boolean z2;
        String str2;
        String str3 = "idle";
        try {
            t9 t9Var = this.protocolManager;
            String str4 = "🎤 Wake Up";
            if (t9Var != null) {
                m1 b = t9Var.b();
                String lowerCase = b.c.toLowerCase();
                int ordinal = b.ordinal();
                if (ordinal != 0) {
                    if (ordinal != 1) {
                        if (ordinal == 2) {
                            str2 = "👋 End Session";
                        } else if (ordinal == 3) {
                            str2 = "✋ Interrupt";
                        }
                        str4 = str2;
                    } else {
                        str4 = "⏳ Connecting...";
                        z2 = false;
                        String str5 = lowerCase;
                        z = z2;
                        str3 = str5;
                    }
                }
                z2 = true;
                String str52 = lowerCase;
                z = z2;
                str3 = str52;
            } else {
                z = true;
            }
            broadcastChatStateToWS8082(str3, str4, z);
            w6 w6Var = this.microphoneTester;
            if (w6Var != null) {
                int ordinal2 = w6Var.n.ordinal();
                if (ordinal2 == 1) {
                    str = "⏹ Stop & Play";
                } else if (ordinal2 != 2) {
                    str = "🎤 Test Mic";
                } else {
                    str = "⏹ Stop";
                }
                broadcastTestMicStateToWS8082(this.microphoneTester.n.toString(), str);
            }
        } catch (Exception unused) {
        }
    }

    private void broadcastLedStateToWS8082(boolean z) {
        try {
            x aiboxPlusWebSocketServer = ((VoiceBotApplication) getApplication()).getAiboxPlusWebSocketServer();
            if (aiboxPlusWebSocketServer != null) {
                aiboxPlusWebSocketServer.c(z);
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public void broadcastTestMicStateToWS8082(String str, String str2) {
        x aiboxPlusWebSocketServer;
        try {
            if ((getApplicationContext() instanceof VoiceBotApplication) && (aiboxPlusWebSocketServer = ((VoiceBotApplication) getApplicationContext()).getAiboxPlusWebSocketServer()) != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", "test_mic_state");
                jSONObject.put("state", str);
                jSONObject.put("button_text", str2);
                aiboxPlusWebSocketServer.d(jSONObject.toString());
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public void checkLedToggle() {
        if (this.buttonPressStartTime > 0) {
            long currentTimeMillis = System.currentTimeMillis() - this.buttonPressStartTime;
            if (currentTimeMillis >= LED_TOGGLE_HOLD_MS && currentTimeMillis < 6000) {
                toggleLed();
                this.ledToggled = true;
                Runnable runnable = this.buttonHoldCheckRunnable;
                if (runnable != null) {
                    this.mainHandler.removeCallbacks(runnable);
                    this.buttonHoldCheckRunnable = null;
                }
            }
        }
    }

    private void connectToServer() {
        updateConnectionStatus("Connecting...");
        this.dependencies.j().a(new C0033AnonymousClass8());
    }

    public static Intent createIntent(Context context, Class<? extends a> cls) {
        Intent intent = new Intent(context, ChatActivity.class);
        if (cls != null) {
            intent.putExtra(EXTRA_WAKE_WORD_DETECTOR_CLASS, cls.getName());
        }
        return intent;
    }

    private void enterWifiSetupMode() {
        a5 a5Var;
        if (this.isInWifiSetupMode) {
            Log.d(TAG, "Already in WiFi setup mode");
            return;
        }
        this.isInWifiSetupMode = true;
        i0.a aVar = i0.a.SYSTEM;
        addMessage("🔧 Entering WiFi Setup Mode...", aVar);
        addMessage("⏳ Stopping all services...", aVar);
        a aVar2 = this.wakeWordDetector;
        if (aVar2 != null) {
            aVar2.stopListening();
        }
        b bVar = this.audioManager;
        if (bVar != null) {
            bVar.a();
        }
        t9 t9Var = this.protocolManager;
        if (t9Var != null) {
            x7 c = t9Var.c();
            if (c != null) {
                a5Var = c.w;
            } else {
                a5Var = null;
            }
            if (a5Var != null) {
                MusicPlayer musicPlayer = a5Var.f;
                if (musicPlayer != null && musicPlayer.isPlaying()) {
                    musicPlayer.stop();
                }
                j jVar = a5Var.h;
                if (jVar != null && jVar.b.get()) {
                    jVar.c();
                }
            }
        }
        t9 t9Var2 = this.protocolManager;
        if (t9Var2 != null) {
            i9 i9Var = t9Var2.f.get();
            if (i9Var != null) {
                i9Var.b();
            }
            synchronized (t9Var2) {
                t9Var2.k = false;
                t9Var2.j = 0;
            }
            this.isConnected = false;
        }
        updateConnectionStatus("WiFi Setup Mode - Internet disconnected");
        Intent intent = new Intent(this, WifiSetupService.class);
        intent.setAction(WifiSetupService.ACTION_START_AP);
        startService(intent);
    }

    public static a getCurrentWakeWordDetector() {
        return staticWakeWordDetector;
    }

    private String getEmojiForEmotion(String str) {
        String lowerCase = str.toLowerCase();
        char c = 65535;
        switch (lowerCase.hashCode()) {
            case -1413564991:
                if (lowerCase.equals("laughing")) {
                    c = 4;
                    break;
                }
                break;
            case -1096888401:
                if (lowerCase.equals("loving")) {
                    c = 5;
                    break;
                }
                break;
            case 113622:
                if (lowerCase.equals("sad")) {
                    c = 1;
                    break;
                }
                break;
            case 92961185:
                if (lowerCase.equals("angry")) {
                    c = 2;
                    break;
                }
                break;
            case 99047136:
                if (lowerCase.equals("happy")) {
                    c = 0;
                    break;
                }
                break;
            case 916452313:
                if (lowerCase.equals("embarrassed")) {
                    c = 6;
                    break;
                }
                break;
            case 1757705883:
                if (lowerCase.equals("surprised")) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return "😊";
            case 1:
                return "😢";
            case 2:
                return "😠";
            case 3:
                return "😲";
            case 4:
                return "😂";
            case 5:
                return "ᾗ0";
            case 6:
                return "😳";
            default:
                return "😐";
        }
    }

    /* access modifiers changed from: private */
    public void handleSingleClick() {
        boolean z;
        x aiboxPlusWebSocketServer;
        MediaPlayer mediaPlayer = AlarmReceiver.a;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            AlarmReceiver.c(this);
            if ((getApplicationContext() instanceof VoiceBotApplication) && (aiboxPlusWebSocketServer = ((VoiceBotApplication) getApplicationContext()).getAiboxPlusWebSocketServer()) != null) {
                aiboxPlusWebSocketServer.b();
            }
            addMessage("🔕 Báo thức đã được tắt", i0.a.SYSTEM);
            return;
        }
        toggleChatState();
    }

    private void initializeManagers() {
        h1 h1Var = this.dependencies;
        if (h1Var.b == null) {
            synchronized (h1Var) {
                if (h1Var.b == null) {
                    h1Var.b = new b(h1Var.a(), h1Var.h(), h1Var.g());
                }
            }
        }
        this.audioManager = h1Var.b;
        this.protocolManager = this.dependencies.i();
        initializeWakeWordDetector();
        f4.a().b(this);
        f4 a = f4.a();
        new Object() {
            public final void onTestMessage(String str) {
                ChatActivity.this.lambda$initializeManagers$1(str);
            }
        };
        a.getClass();
        this.protocolManager.a = new C0030AnonymousClass3();
        this.protocolManager.b = new C0031AnonymousClass4();
        b bVar = this.audioManager;
        new C0032AnonymousClass5();
        bVar.getClass();
        Log.d("AudioManager", "Audio callback set");
        t9 t9Var = this.protocolManager;
        AnonymousClass6 r1 = new x7.n() {
            public void onDeviceStateChanged(m1 m1Var) {
                Objects.toString(m1Var);
                ChatActivity.this.handleDeviceStateChanged(m1Var);
            }
        };
        t9Var.e = r1;
        x7 c = t9Var.c();
        if (c != null) {
            c.l = r1;
        }
    }

    private void initializeMicrophoneTester() {
        w6 w6Var = new w6(this);
        this.microphoneTester = w6Var;
        w6Var.f = new w6.d() {
            public void onError(String str) {
                ChatActivity.this.addMessage(y2.i("Mic Test Error: ", str), i0.a.SYSTEM);
                ChatActivity.this.textViewMicStatus.setVisibility(8);
            }

            public void onPlaybackProgress(int i, int i2) {
                ChatActivity.this.textViewMicStatus.setText(String.format("🔊 Playing: %.1fs / %.1fs", new Object[]{Float.valueOf(((float) i) / 1000.0f), Float.valueOf(((float) i2) / 1000.0f)}));
            }

            public void onRecordingProgress(int i, float f) {
                ChatActivity.this.textViewMicStatus.setText(String.format("🔴 Recording: %.1fs | %.1f dB", new Object[]{Float.valueOf(((float) i) / 1000.0f), Float.valueOf(f)}));
            }

            public void onStateChanged(w6.e eVar) {
                String str;
                Objects.toString(eVar);
                int ordinal = ChatActivity.this.microphoneTester.n.ordinal();
                if (ordinal == 1) {
                    str = "⏹ Stop & Play";
                } else if (ordinal != 2) {
                    str = "🎤 Test Mic";
                } else {
                    str = "⏹ Stop";
                }
                ChatActivity.this.buttonTestMic.setText(str);
                if (eVar == w6.e.IDLE) {
                    ChatActivity.this.textViewMicStatus.setVisibility(8);
                } else {
                    ChatActivity.this.textViewMicStatus.setVisibility(0);
                }
                ChatActivity.this.broadcastTestMicStateToWS8082(eVar.toString(), str);
            }
        };
    }

    private void initializeViews() {
        this.recyclerViewMessages = (RecyclerView) findViewById(R.id.recyclerViewMessages);
        this.textViewEmotion = (TextView) findViewById(R.id.textViewEmotion);
        this.textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        this.textViewMicStatus = (TextView) findViewById(R.id.textViewMicStatus);
        this.buttonWakeUp = (Button) findViewById(R.id.buttonWakeUp);
        this.buttonTestMic = (Button) findViewById(R.id.buttonTestMic);
        this.buttonWakeUp.setOnClickListener(this);
        this.buttonTestMic.setOnClickListener(this);
        this.mainHandler = new Handler(Looper.getMainLooper());
        initializeMicrophoneTester();
        startMainThreadHeartbeat();
    }

    private void initializeWakeWordDetector() {
        Class<? extends a> cls;
        try {
            cls = this.wakeWordDetectorClass;
            this.wakeWordDetector = (a) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            this.wakeWordDetectorClass.getSimpleName();
            this.wakeWordDetector.init(this);
            this.wakeWordDetector.setWakeWordListener(new a.C0024a() {
                public final void onWakeWordDetected() {
                    ChatActivity.this.lambda$initializeWakeWordDetector$2();
                }
            });
            boolean z = getSharedPreferences("voicebot_prefs", 0).getBoolean("wake_word_enabled", true);
            this.wakeWordEnabled = z;
            this.wakeWordDetector.setEnabled(z);
            staticWakeWordDetector = this.wakeWordDetector;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to create wake word detector: ".concat(cls.getName()), e);
        } catch (IllegalArgumentException unused) {
            this.wakeWordDetector = null;
        }
    }

    public static boolean isWakeWordEnabled() {
        a aVar = staticWakeWordDetector;
        if (aVar != null) {
            return aVar.isEnabled();
        }
        return false;
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$toggleBluetoothAndPlaySound$1() {
        try {
            if (getApplicationContext() instanceof VoiceBotApplication) {
                x aiboxPlusWebSocketServer = ((VoiceBotApplication) getApplicationContext()).getAiboxPlusWebSocketServer();
                if (aiboxPlusWebSocketServer != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("enable", true);
                    aiboxPlusWebSocketServer.n((WebSocket) null, jSONObject);
                    this.mainHandler.post(new Runnable() {
                        public void run() {
                            ChatActivity.this.playBluetoothSound("sounds/batbluetooth.mp3");
                            ChatActivity.this.addMessage("🔵 Bluetooth đã được bật", i0.a.SYSTEM);
                        }
                    });
                    return;
                }
                this.mainHandler.post(new Runnable() {
                    public void run() {
                        ChatActivity.this.addMessage("❌ Không thể bật Bluetooth", i0.a.SYSTEM);
                    }
                });
            }
        } catch (Exception e) {
            this.mainHandler.post(new Runnable() {
                public void run() {
                    ChatActivity chatActivity = ChatActivity.this;
                    chatActivity.addMessage("❌ Lỗi khi bật Bluetooth: " + e.getMessage(), i0.a.SYSTEM);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't wrap try/catch for region: R(6:4|5|(1:7)|8|9|10) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0015 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void playBluetoothSound(java.lang.String r8) {
        /*
            r7 = this;
            r0 = 0
            android.media.MediaPlayer r1 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x005b }
            if (r1 == 0) goto L_0x0017
            boolean r1 = r1.isPlaying()     // Catch:{ Exception -> 0x0015 }
            if (r1 == 0) goto L_0x0010
            android.media.MediaPlayer r1 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x0015 }
            r1.stop()     // Catch:{ Exception -> 0x0015 }
        L_0x0010:
            android.media.MediaPlayer r1 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x0015 }
            r1.release()     // Catch:{ Exception -> 0x0015 }
        L_0x0015:
            r7.bluetoothSoundPlayer = r0     // Catch:{ Exception -> 0x005b }
        L_0x0017:
            android.media.MediaPlayer r1 = new android.media.MediaPlayer     // Catch:{ Exception -> 0x005b }
            r1.<init>()     // Catch:{ Exception -> 0x005b }
            r7.bluetoothSoundPlayer = r1     // Catch:{ Exception -> 0x005b }
            android.content.res.AssetManager r1 = r7.getAssets()     // Catch:{ Exception -> 0x005b }
            android.content.res.AssetFileDescriptor r8 = r1.openFd(r8)     // Catch:{ Exception -> 0x005b }
            android.media.MediaPlayer r1 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x005b }
            java.io.FileDescriptor r2 = r8.getFileDescriptor()     // Catch:{ Exception -> 0x005b }
            long r3 = r8.getStartOffset()     // Catch:{ Exception -> 0x005b }
            long r5 = r8.getLength()     // Catch:{ Exception -> 0x005b }
            r1.setDataSource(r2, r3, r5)     // Catch:{ Exception -> 0x005b }
            r8.close()     // Catch:{ Exception -> 0x005b }
            android.media.MediaPlayer r8 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x005b }
            r1 = 3
            r8.setAudioStreamType(r1)     // Catch:{ Exception -> 0x005b }
            android.media.MediaPlayer r8 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x005b }
            r1 = 0
            r8.setLooping(r1)     // Catch:{ Exception -> 0x005b }
            android.media.MediaPlayer r8 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x005b }
            r8.prepare()     // Catch:{ Exception -> 0x005b }
            android.media.MediaPlayer r8 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x005b }
            r8.start()     // Catch:{ Exception -> 0x005b }
            android.media.MediaPlayer r8 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x005b }
            info.dourok.voicebot.java.activities.ChatActivity$23 r1 = new info.dourok.voicebot.java.activities.ChatActivity$23     // Catch:{ Exception -> 0x005b }
            r1.<init>()     // Catch:{ Exception -> 0x005b }
            r8.setOnCompletionListener(r1)     // Catch:{ Exception -> 0x005b }
            goto L_0x0065
        L_0x005b:
            android.media.MediaPlayer r8 = r7.bluetoothSoundPlayer
            if (r8 == 0) goto L_0x0065
            r8.release()     // Catch:{ Exception -> 0x0063 }
        L_0x0063:
            r7.bluetoothSoundPlayer = r0
        L_0x0065:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: info.dourok.voicebot.java.activities.ChatActivity.playBluetoothSound(java.lang.String):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:4|5|(1:7)|8|9|10) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0015 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void playWakeWordSound(java.lang.String r8) {
        /*
            r7 = this;
            r0 = 0
            android.media.MediaPlayer r1 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x0055 }
            if (r1 == 0) goto L_0x0017
            boolean r1 = r1.isPlaying()     // Catch:{ Exception -> 0x0015 }
            if (r1 == 0) goto L_0x0010
            android.media.MediaPlayer r1 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x0015 }
            r1.stop()     // Catch:{ Exception -> 0x0015 }
        L_0x0010:
            android.media.MediaPlayer r1 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x0015 }
            r1.release()     // Catch:{ Exception -> 0x0015 }
        L_0x0015:
            r7.bluetoothSoundPlayer = r0     // Catch:{ Exception -> 0x0055 }
        L_0x0017:
            android.media.MediaPlayer r1 = new android.media.MediaPlayer     // Catch:{ Exception -> 0x0055 }
            r1.<init>()     // Catch:{ Exception -> 0x0055 }
            r7.bluetoothSoundPlayer = r1     // Catch:{ Exception -> 0x0055 }
            android.content.res.AssetManager r1 = r7.getAssets()     // Catch:{ Exception -> 0x0055 }
            android.content.res.AssetFileDescriptor r8 = r1.openFd(r8)     // Catch:{ Exception -> 0x0055 }
            android.media.MediaPlayer r1 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x0055 }
            java.io.FileDescriptor r2 = r8.getFileDescriptor()     // Catch:{ Exception -> 0x0055 }
            long r3 = r8.getStartOffset()     // Catch:{ Exception -> 0x0055 }
            long r5 = r8.getLength()     // Catch:{ Exception -> 0x0055 }
            r1.setDataSource(r2, r3, r5)     // Catch:{ Exception -> 0x0055 }
            r8.close()     // Catch:{ Exception -> 0x0055 }
            android.media.MediaPlayer r8 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x0055 }
            r1 = 3
            r8.setAudioStreamType(r1)     // Catch:{ Exception -> 0x0055 }
            android.media.MediaPlayer r8 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x0055 }
            r8.prepare()     // Catch:{ Exception -> 0x0055 }
            android.media.MediaPlayer r8 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x0055 }
            r8.start()     // Catch:{ Exception -> 0x0055 }
            android.media.MediaPlayer r8 = r7.bluetoothSoundPlayer     // Catch:{ Exception -> 0x0055 }
            info.dourok.voicebot.java.activities.ChatActivity$19 r1 = new info.dourok.voicebot.java.activities.ChatActivity$19     // Catch:{ Exception -> 0x0055 }
            r1.<init>()     // Catch:{ Exception -> 0x0055 }
            r8.setOnCompletionListener(r1)     // Catch:{ Exception -> 0x0055 }
            goto L_0x005f
        L_0x0055:
            android.media.MediaPlayer r8 = r7.bluetoothSoundPlayer
            if (r8 == 0) goto L_0x005f
            r8.release()     // Catch:{ Exception -> 0x005d }
        L_0x005d:
            r7.bluetoothSoundPlayer = r0
        L_0x005f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: info.dourok.voicebot.java.activities.ChatActivity.playWakeWordSound(java.lang.String):void");
    }

    private void registerChatBroadcastReceiver() {
        AnonymousClass2 r0 = new BroadcastReceiver() {
            /* access modifiers changed from: private */
            public /* synthetic */ void lambda$onReceive$0() {
                ChatActivity.this.toggleChatState();
            }

            /* access modifiers changed from: private */
            public /* synthetic */ void lambda$onReceive$1() {
                if (ChatActivity.this.microphoneTester != null) {
                    ChatActivity.this.microphoneTester.f();
                }
            }

            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("info.dourok.voicebot.CHAT_WAKE_UP".equals(action)) {
                    ChatActivity.this.mainHandler.post(new a(this, 0));
                } else if ("info.dourok.voicebot.CHAT_TEST_MIC".equals(action)) {
                    ChatActivity.this.mainHandler.post(new a(this, 1));
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("info.dourok.voicebot.CHAT_WAKE_UP");
        intentFilter.addAction("info.dourok.voicebot.CHAT_TEST_MIC");
        intentFilter.addAction("info.dourok.voicebot.CHAT_GET_STATE");
        registerReceiver(r0, intentFilter);
        this.mainHandler.postDelayed(new g0(this, 0), 1000);
    }

    private void resolveWakeWordDetector(Intent intent) {
        if (intent != null) {
            String stringExtra = intent.getStringExtra(EXTRA_WAKE_WORD_DETECTOR_CLASS);
            if (TextUtils.isEmpty(stringExtra)) {
                Log.d(TAG, "Using default wake word detector: ".concat(this.wakeWordDetectorClass.getSimpleName()));
                return;
            }
            try {
                Class<?> cls = Class.forName(stringExtra);
                if (a.class.isAssignableFrom(cls)) {
                    this.wakeWordDetectorClass = cls;
                }
            } catch (ClassNotFoundException unused) {
            }
        }
    }

    public static boolean setWakeWordEnabled(boolean z) {
        a aVar = staticWakeWordDetector;
        if (aVar == null) {
            return false;
        }
        aVar.setEnabled(z);
        return true;
    }

    private void setupRecyclerView() {
        ArrayList arrayList = new ArrayList();
        this.messages = arrayList;
        this.chatAdapter = new h0(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        this.recyclerViewMessages.setLayoutManager(linearLayoutManager);
        this.recyclerViewMessages.setAdapter(this.chatAdapter);
    }

    /* access modifiers changed from: private */
    public void startAudioRecording() {
        System.currentTimeMillis();
        Log.d(TAG, "startAudioRecording called, isConnected=" + this.isConnected + ", isListening=" + this.isListening);
        if (!this.isConnected) {
            updateConnectionStatus("Not connected to server");
            return;
        }
        System.currentTimeMillis();
        b bVar = this.audioManager;
        C0034AnonymousClass9 anonymousClass9 = new C0034AnonymousClass9();
        if (!bVar.d) {
            bVar.e = System.currentTimeMillis();
            e eVar = bVar.a;
            bl blVar = new bl(bVar, anonymousClass9);
            Log.d("AudioRecorder", "startRecording called, isRecording=" + eVar.k);
            synchronized (eVar) {
                if (!eVar.k) {
                    eVar.k = true;
                    eVar.i.post(new bo(eVar, blVar));
                }
            }
        }
    }

    private void startBackgroundWatchdog() {
        this.watchdogRunning = true;
        Thread thread = new Thread(new Runnable() {
            public final void run() {
                ChatActivity.this.lambda$startBackgroundWatchdog$0();
            }
        }, "ProcessWatchdog");
        this.watchdogThread = thread;
        thread.setPriority(10);
        this.watchdogThread.start();
    }

    private void startMainThreadHeartbeat() {
        this.heartbeatHandler = new Handler(Looper.getMainLooper());
        AnonymousClass3 r0 = new Runnable() {
            private int heartbeatCount = 0;
            private long lastGcCount = 0;

            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                long j = 0;
                if (ChatActivity.this.lastHeartbeat > 0) {
                    j = currentTimeMillis - ChatActivity.this.lastHeartbeat;
                }
                ChatActivity.this.lastHeartbeat = currentTimeMillis;
                this.heartbeatCount++;
                if (j > 600) {
                    Runtime runtime = Runtime.getRuntime();
                    long freeMemory = ((runtime.totalMemory() - runtime.freeMemory()) / 1024) / 1024;
                    long maxMemory = (runtime.maxMemory() / 1024) / 1024;
                }
                ChatActivity.this.heartbeatHandler.postDelayed(this, 200);
            }
        };
        this.heartbeatRunnable = r0;
        this.heartbeatHandler.post(r0);
        startBackgroundWatchdog();
    }

    /* access modifiers changed from: private */
    public void startWakeWordIfReady() {
        a aVar = this.wakeWordDetector;
        if (aVar != null && aVar.isReady() && this.wakeWordDetector.isEnabled()) {
            this.wakeWordDetector.startListening();
        }
    }

    private void stopBackgroundWatchdog() {
        this.watchdogRunning = false;
        Thread thread = this.watchdogThread;
        if (thread != null) {
            thread.interrupt();
        }
    }

    private void stopMainThreadHeartbeat() {
        Runnable runnable;
        Handler handler = this.heartbeatHandler;
        if (!(handler == null || (runnable = this.heartbeatRunnable) == null)) {
            handler.removeCallbacks(runnable);
        }
        stopBackgroundWatchdog();
    }

    private void toggleBluetoothAndPlaySound() {
        new Thread(new g0(this, 1)).start();
    }

    /* access modifiers changed from: private */
    public void toggleChatState() {
        toggleChatState(-1);
    }

    private void toggleLed() {
        m1 m1Var;
        boolean z = !f4.e;
        f4.e(z);
        broadcastLedStateToWS8082(z);
        t9 t9Var = this.protocolManager;
        m1 m1Var2 = m1.IDLE;
        if (t9Var != null) {
            m1Var = t9Var.b();
        } else {
            m1Var = m1Var2;
        }
        if (m1Var == m1Var2) {
            f4.a().d();
        }
    }

    /* access modifiers changed from: private */
    public void toggleWakeWord() {
        x aiboxPlusWebSocketServer;
        a aVar = this.wakeWordDetector;
        if (aVar != null) {
            boolean z = !aVar.isEnabled();
            setWakeWordEnabled(z);
            getSharedPreferences("voicebot_prefs", 0).edit().putBoolean("wake_word_enabled", z).apply();
            if ((getApplicationContext() instanceof VoiceBotApplication) && (aiboxPlusWebSocketServer = ((VoiceBotApplication) getApplicationContext()).getAiboxPlusWebSocketServer()) != null) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("type", "wake_word_enabled_state");
                    jSONObject.put("enabled", z);
                    aiboxPlusWebSocketServer.d(jSONObject.toString());
                } catch (JSONException unused) {
                }
            }
            i0.a aVar2 = i0.a.SYSTEM;
            if (z) {
                playWakeWordSound("sounds/batww.mp3");
                addMessage("🎤 Wake word đã được bật", aVar2);
                return;
            }
            playWakeWordSound("sounds/tatww.mp3");
            addMessage("🔇 Wake word đã được tắt", aVar2);
        }
    }

    private void updateEmotion(String str) {
        String emojiForEmotion = getEmojiForEmotion(str);
        TextView textView = this.textViewEmotion;
        textView.setText(emojiForEmotion + " " + str);
    }

    public void addMessage(String str, i0.a aVar) {
        this.messages.add(new i0(str, aVar, System.currentTimeMillis()));
        this.chatAdapter.notifyItemInserted(this.messages.size() - 1);
        this.recyclerViewMessages.scrollToPosition(this.messages.size() - 1);
    }

    public void enableWakeButton(boolean z) {
        this.buttonWakeUp.setEnabled(z);
    }

    public void handleDeviceStateChanged(m1 m1Var) {
        int ordinal = m1Var.ordinal();
        if (ordinal == 0) {
            updateConnectionStatus("Ready - Tap or say 'Alexa' to wake up");
            updateRecordingStatus(false);
            this.isListening = false;
            this.buttonWakeUp.setEnabled(true);
            this.buttonWakeUp.setText("🎤 Wake Up");
            this.audioManager.a();
            a aVar = this.wakeWordDetector;
            if (aVar != null && this.wakeWordEnabled) {
                aVar.startListening();
            }
            broadcastChatStateToWS8082("idle", "🎤 Wake Up", true);
        } else if (ordinal == 1) {
            updateConnectionStatus("Connecting...");
            this.buttonWakeUp.setEnabled(false);
            this.buttonWakeUp.setText("⏳ Connecting...");
            a aVar2 = this.wakeWordDetector;
            if (aVar2 != null) {
                aVar2.stopListening();
            }
            broadcastChatStateToWS8082("connecting", "⏳ Connecting...", false);
        } else if (ordinal == 2) {
            updateConnectionStatus("🎤 Listening...");
            updateRecordingStatus(true);
            this.isListening = true;
            this.buttonWakeUp.setEnabled(true);
            this.buttonWakeUp.setText("👋 End Session");
            a aVar3 = this.wakeWordDetector;
            if (aVar3 != null) {
                aVar3.stopListening();
            }
            if (!this.audioManager.d) {
                startAudioRecording();
            }
            broadcastChatStateToWS8082("listening", "👋 End Session", true);
        } else if (ordinal == 3) {
            updateConnectionStatus("🔊 Speaking...");
            updateRecordingStatus(false);
            this.isListening = false;
            this.buttonWakeUp.setEnabled(true);
            this.buttonWakeUp.setText("✋ Interrupt");
            a aVar4 = this.wakeWordDetector;
            if (aVar4 != null) {
                aVar4.stopListening();
            }
            broadcastChatStateToWS8082("speaking", "✋ Interrupt", true);
        }
    }

    public void handleIncomingMessage(JSONObject jSONObject) {
        try {
            String optString = jSONObject.optString("type", "");
            jSONObject.toString();
            char c = 65535;
            switch (optString.hashCode()) {
                case -887328209:
                    if (optString.equals("system")) {
                        c = 4;
                        break;
                    }
                    break;
                case -340323263:
                    if (optString.equals("response")) {
                        c = 3;
                        break;
                    }
                    break;
                case 107245:
                    if (optString.equals("llm")) {
                        c = 2;
                        break;
                    }
                    break;
                case 114227:
                    if (optString.equals("stt")) {
                        c = 0;
                        break;
                    }
                    break;
                case 115187:
                    if (optString.equals("tts")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            if (c != 0) {
                i0.a aVar = i0.a.SERVER;
                if (c == 1) {
                    String optString2 = jSONObject.optString("state", "");
                    if ("start".equals(optString2)) {
                        updateConnectionStatus("🔊 Speaking...");
                    } else if ("stop".equals(optString2)) {
                        updateConnectionStatus("Audio channel ready");
                    } else if ("sentence_start".equals(optString2)) {
                        String optString3 = jSONObject.optString("text", "");
                        if (!optString3.isEmpty()) {
                            addMessage(optString3, aVar);
                            broadcastChatMessageToWS8082(optString3, "server");
                        }
                    }
                } else if (c == 2) {
                    String optString4 = jSONObject.optString("text", "");
                    String optString5 = jSONObject.optString("emotion", "neutral");
                    if (!optString4.isEmpty()) {
                        addMessage(optString4, aVar);
                        broadcastChatMessageToWS8082(optString4, "server");
                    }
                    updateEmotion(optString5);
                } else if (c == 3) {
                    String optString6 = jSONObject.optString("content", "");
                    String optString7 = jSONObject.optString("emotion", "neutral");
                    if (!optString6.isEmpty()) {
                        addMessage(optString6, aVar);
                        broadcastChatMessageToWS8082(optString6, "server");
                        updateEmotion(optString7);
                    }
                } else if (c == 4) {
                    String optString8 = jSONObject.optString("content", "");
                    if (!optString8.isEmpty()) {
                        addMessage(optString8, i0.a.SYSTEM);
                    }
                }
            } else {
                String optString9 = jSONObject.optString("text", "");
                if (!optString9.isEmpty()) {
                    addMessage("🎤 ".concat(optString9), i0.a.USER);
                    broadcastChatMessageToWS8082("🎤 ".concat(optString9), "user");
                }
            }
        } catch (Exception unused) {
        }
    }

    public void lambda$initializeManagers$1(String str) {
        addMessage(str, i0.a.SYSTEM);
    }

    public void lambda$initializeWakeWordDetector$2() {
        m1 b = this.protocolManager.b();
        if (!this.isConnected || b != m1.IDLE) {
            Log.d(TAG, "Wake word ignored - state: " + b + ", connected: " + this.isConnected);
            return;
        }
        addMessage("🎤 Wake word detected!", i0.a.SYSTEM);
        h.b().f("em-nghe.mp3");
        toggleChatState(1200);
    }

    public void lambda$onApModeStarted$4(String str, String str2, String str3) {
        i0.a aVar = i0.a.SYSTEM;
        addMessage("📶 WiFi AP Mode Active", aVar);
        addMessage("SSID: " + str, aVar);
        addMessage("Password: " + str2, aVar);
        addMessage("Connect to this WiFi and open http://" + str3, aVar);
    }

    public void lambda$onApModeStopped$5() {
        addMessage("WiFi AP Mode stopped", i0.a.SYSTEM);
    }

    public void lambda$onError$8(String str) {
        addMessage(y2.i("WiFi Error: ", str), i0.a.SYSTEM);
    }

    public void lambda$onKeyDown$3() {
        if (this.buttonPressStartTime > 0 && System.currentTimeMillis() - this.buttonPressStartTime >= 6000) {
            enterWifiSetupMode();
            this.buttonPressStartTime = 0;
        }
    }

    public void lambda$onWifiConnected$6(String str) {
        addMessage(y2.i("✅ WiFi connected: ", str), i0.a.SYSTEM);
    }

    public void lambda$onWifiConnectionFailed$7(String str) {
        addMessage(y2.i("❌ WiFi connection failed: ", str), i0.a.SYSTEM);
    }

    public void lambda$startBackgroundWatchdog$0() {
        long currentTimeMillis = System.currentTimeMillis();
        while (this.watchdogRunning) {
            try {
                Thread.sleep(200);
                long currentTimeMillis2 = System.currentTimeMillis();
                int i = ((currentTimeMillis2 - currentTimeMillis) > 300 ? 1 : ((currentTimeMillis2 - currentTimeMillis) == 300 ? 0 : -1));
                currentTimeMillis = currentTimeMillis2;
            } catch (InterruptedException unused) {
                return;
            }
        }
    }

    public void onApModeStarted(final String str, final String str2, final String str3) {
        this.mainHandler.post(new Runnable() {
            public final void run() {
                ChatActivity.this.lambda$onApModeStarted$4(str, str2, str3);
            }
        });
    }

    public void onApModeStopped() {
        this.isInWifiSetupMode = false;
        this.mainHandler.post(new Runnable() {
            public final void run() {
                ChatActivity.this.lambda$onApModeStopped$5();
            }
        });
    }

    public void onClick(View view) {
        w6 w6Var;
        System.currentTimeMillis();
        Thread.currentThread().getName();
        int id = view.getId();
        if (id == R.id.buttonWakeUp) {
            System.currentTimeMillis();
            toggleChatState();
            System.currentTimeMillis();
        } else if (id == R.id.buttonTestMic && (w6Var = this.microphoneTester) != null) {
            w6Var.f();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_chat);
        Log.d(TAG, "ChatActivity onCreate");
        this.websocketUrl = getIntent().getStringExtra("websocket_url");
        this.websocketToken = getIntent().getStringExtra("websocket_token");
        resolveWakeWordDetector(getIntent());
        if (this.websocketUrl != null) {
            Log.d(TAG, "Using WebSocket URL from activation: " + this.websocketUrl);
        }
        bindService(new Intent(this, WifiSetupService.class), this.wifiServiceConnection, 1);
        initializeViews();
        setupRecyclerView();
        initializeManagers();
        connectToServer();
        registerChatBroadcastReceiver();
    }

    public void onDestroy() {
        super.onDestroy();
        stopMainThreadHeartbeat();
        if (this.isWifiServiceBound) {
            unbindService(this.wifiServiceConnection);
            this.isWifiServiceBound = false;
        }
        MediaPlayer mediaPlayer = this.bluetoothSoundPlayer;
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    this.bluetoothSoundPlayer.stop();
                }
                this.bluetoothSoundPlayer.release();
            } catch (Exception unused) {
            }
            this.bluetoothSoundPlayer = null;
        }
        w6 w6Var = this.microphoneTester;
        if (w6Var != null) {
            w6Var.o = false;
            w6Var.p = false;
            w6Var.c();
            HandlerThread handlerThread = w6Var.m;
            if (handlerThread != null) {
                handlerThread.quitSafely();
                w6Var.m = null;
            }
            HandlerThread handlerThread2 = w6Var.i;
            if (handlerThread2 != null) {
                handlerThread2.quitSafely();
                w6Var.i = null;
            }
            AudioRecord audioRecord = w6Var.c;
            if (audioRecord != null) {
                try {
                    audioRecord.stop();
                    w6Var.c.release();
                } catch (Exception e) {
                    e.getMessage();
                }
                w6Var.c = null;
            }
            AudioTrack audioTrack = w6Var.d;
            if (audioTrack != null) {
                try {
                    audioTrack.stop();
                    w6Var.d.release();
                } catch (Exception e2) {
                    e2.getMessage();
                }
                w6Var.d = null;
            }
            w6Var.f = null;
        }
        a aVar = this.wakeWordDetector;
        if (aVar != null) {
            aVar.shutdown();
        }
        b bVar = this.audioManager;
        if (bVar != null) {
            bVar.a();
        }
        t9 t9Var = this.protocolManager;
        if (t9Var != null) {
            i9 i9Var = t9Var.f.get();
            if (i9Var != null) {
                i9Var.b();
            }
            synchronized (t9Var) {
                t9Var.k = false;
                t9Var.j = 0;
            }
        }
    }

    public void onError(final String str) {
        this.mainHandler.post(new Runnable() {
            public final void run() {
                ChatActivity.this.lambda$onError$8(str);
            }
        });
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != KEY_R1_MAIN) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.buttonPressStartTime == 0) {
            this.buttonPressStartTime = System.currentTimeMillis();
            this.ledToggled = false;
            Log.d(TAG, "Button 275 pressed - starting timers for LED toggle (3s) and AP mode (6s)");
            AnonymousClass9 r5 = new Runnable() {
                public void run() {
                    ChatActivity.this.checkLedToggle();
                }
            };
            this.ledToggleCheckRunnable = r5;
            this.mainHandler.postDelayed(r5, LED_TOGGLE_HOLD_MS);
            AnonymousClass10 r52 = new Runnable() {
                public final void run() {
                    ChatActivity.this.lambda$onKeyDown$3();
                }
            };
            this.buttonHoldCheckRunnable = r52;
            this.mainHandler.postDelayed(r52, 6000);
        }
        return true;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != KEY_R1_MAIN) {
            return super.onKeyUp(i, keyEvent);
        }
        long currentTimeMillis = System.currentTimeMillis() - this.buttonPressStartTime;
        Log.d(TAG, "Button 275 released after " + currentTimeMillis + "ms");
        Runnable runnable = this.ledToggleCheckRunnable;
        if (runnable != null) {
            this.mainHandler.removeCallbacks(runnable);
            this.ledToggleCheckRunnable = null;
        }
        Runnable runnable2 = this.buttonHoldCheckRunnable;
        if (runnable2 != null) {
            this.mainHandler.removeCallbacks(runnable2);
            this.buttonHoldCheckRunnable = null;
        }
        if (this.ledToggled) {
            Log.d(TAG, "LED was toggled, ignoring single click");
            this.ledToggled = false;
            this.buttonPressStartTime = 0;
            return true;
        }
        if (this.buttonPressStartTime > 0 && currentTimeMillis < 6000) {
            long currentTimeMillis2 = System.currentTimeMillis();
            long j = currentTimeMillis2 - this.lastButtonClickTime;
            Runnable runnable3 = this.singleClickRunnable;
            if (runnable3 != null) {
                this.mainHandler.removeCallbacks(runnable3);
                this.singleClickRunnable = null;
            }
            Runnable runnable4 = this.clickCountResetRunnable;
            if (runnable4 != null) {
                this.mainHandler.removeCallbacks(runnable4);
                this.clickCountResetRunnable = null;
            }
            if (j >= CLICK_TIMEOUT_MS || j <= 0) {
                this.clickCount = 1;
                this.lastButtonClickTime = currentTimeMillis2;
                Runnable runnable5 = this.clickCountResetRunnable;
                if (runnable5 != null) {
                    this.mainHandler.removeCallbacks(runnable5);
                    this.clickCountResetRunnable = null;
                }
                AnonymousClass12 r11 = new Runnable() {
                    public void run() {
                        if (ChatActivity.this.clickCount == 1) {
                            ChatActivity.this.handleSingleClick();
                        } else if (ChatActivity.this.clickCount == 2) {
                            ChatActivity.this.toggleWakeWord();
                        }
                        ChatActivity.this.clickCount = 0;
                        ChatActivity.this.lastButtonClickTime = 0;
                        ChatActivity.this.clickCountResetRunnable = null;
                    }
                };
                this.clickCountResetRunnable = r11;
                this.mainHandler.postDelayed(r11, CLICK_TIMEOUT_MS);
            } else {
                this.clickCount++;
                this.lastButtonClickTime = currentTimeMillis2;
                Runnable runnable6 = this.clickCountResetRunnable;
                if (runnable6 != null) {
                    this.mainHandler.removeCallbacks(runnable6);
                    this.clickCountResetRunnable = null;
                }
                if (this.clickCount >= 3) {
                    this.clickCount = 0;
                    this.lastButtonClickTime = 0;
                    toggleBluetoothAndPlaySound();
                } else {
                    AnonymousClass11 r112 = new Runnable() {
                        public void run() {
                            if (ChatActivity.this.clickCount == 1) {
                                ChatActivity.this.handleSingleClick();
                            } else if (ChatActivity.this.clickCount == 2) {
                                ChatActivity.this.toggleWakeWord();
                            }
                            ChatActivity.this.clickCount = 0;
                            ChatActivity.this.lastButtonClickTime = 0;
                            ChatActivity.this.clickCountResetRunnable = null;
                        }
                    };
                    this.clickCountResetRunnable = r112;
                    this.mainHandler.postDelayed(r112, CLICK_TIMEOUT_MS);
                }
            }
        }
        this.buttonPressStartTime = 0;
        return true;
    }

    public void onWifiConnected(final String str) {
        this.isInWifiSetupMode = false;
        this.mainHandler.post(new Runnable() {
            public final void run() {
                ChatActivity.this.lambda$onWifiConnected$6(str);
            }
        });
    }

    public void onWifiConnectionFailed(String str, final String str2) {
        this.mainHandler.post(new Runnable() {
            public final void run() {
                ChatActivity.this.lambda$onWifiConnectionFailed$7(str2);
            }
        });
    }

    public void stopAudioRecording() {
        Log.d(TAG, "stopAudioRecording called");
        this.audioManager.a();
        addMessage("👋 Session ended", i0.a.SYSTEM);
    }

    public void updateConnectionStatus(String str) {
        this.textViewStatus.setText(str);
        Log.d(TAG, "Status: " + str);
    }

    public void updateRecordingStatus(boolean z) {
        Log.d(TAG, "updateRecordingStatus: recording=" + z + ", isConnected=" + this.isConnected);
    }

    private void toggleChatState(long j) {
        System.currentTimeMillis();
        i9 i9Var = this.protocolManager.f.get();
        if (!this.isConnected) {
            updateConnectionStatus("Not connected to server");
            return;
        }
        m1 b = this.protocolManager.b();
        System.currentTimeMillis();
        this.protocolManager.e();
        System.currentTimeMillis();
        if (b == m1.IDLE) {
            if (j > 0) {
                System.currentTimeMillis();
                this.mainHandler.postDelayed(new Runnable() {
                    public void run() {
                        ChatActivity.this.startAudioRecording();
                    }
                }, j);
            } else {
                System.currentTimeMillis();
                startAudioRecording();
                System.currentTimeMillis();
            }
        } else if (b == m1.LISTENING) {
            stopAudioRecording();
        }
        System.currentTimeMillis();
    }
}
