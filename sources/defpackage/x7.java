package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import defpackage.zc;
import info.dourok.voicebot.java.audio.MusicPlayer;
import info.dourok.voicebot.java.audio.OpusDecoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: x7  reason: default package */
public final class x7 extends i9 {
    public String aa;
    public String ab;
    public final ScheduledExecutorService ac;
    public final AtomicLong ad = new AtomicLong(0);
    public volatile int ae = 60;
    public volatile int af = 16000;
    public volatile String ag = "";
    public String ah;
    public zc ai;
    public String aj;
    public volatile boolean g = false;
    public info.dourok.voicebot.java.audio.c h;
    public final Object i = new Object();
    public String j;
    public volatile m1 k = m1.IDLE;
    public n l;
    public String m;
    public CountDownLatch n;
    public volatile boolean o = false;
    public final AtomicBoolean p = new AtomicBoolean(false);
    public volatile boolean q = false;
    public volatile boolean r = false;
    public final f4 s;
    public volatile int t = 3;
    public final AtomicLong u = new AtomicLong(0);
    public final Handler v;
    public final a5 w;
    public volatile boolean x = false;
    public MqttAsyncClient y;
    public OpusDecoder z;

    /* renamed from: x7$a */
    public class a implements zc.d {
        public a() {
        }
    }

    /* renamed from: x7$b */
    public class b implements cw {
        public b() {
        }
    }

    /* renamed from: x7$c */
    public class c implements Runnable {
        public c() {
        }

        public final void run() {
            x7.this.w();
        }
    }

    /* renamed from: x7$d */
    public class d implements IMqttActionListener {
        public final void onFailure(IMqttToken iMqttToken, Throwable th) {
            th.getMessage();
        }

        public final void onSuccess(IMqttToken iMqttToken) {
        }
    }

    /* renamed from: x7$e */
    public class e implements Runnable {
        public final /* synthetic */ String c;

        public e(String str) {
            this.c = str;
        }

        public final void run() {
            String str = this.c;
            x7 x7Var = x7.this;
            x7Var.getClass();
            try {
                byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
                int length = bytes.length;
                Thread.currentThread().getName();
                MqttAsyncClient mqttAsyncClient = x7Var.y;
                if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
                    MqttMessage mqttMessage = new MqttMessage(bytes);
                    mqttMessage.setQos(0);
                    mqttMessage.setRetained(false);
                    x7Var.y.publish(x7Var.ab, mqttMessage, (Object) null, (IMqttActionListener) new y7(x7Var));
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    /* renamed from: x7$f */
    public class f implements Runnable {
        public final /* synthetic */ d0 c;

        public f(d0 d0Var) {
            this.c = d0Var;
        }

        public final void run() {
            this.c.onError(new IllegalStateException("Not connected"));
        }
    }

    /* renamed from: x7$g */
    public class g implements Runnable {
        public final /* synthetic */ d0 c;
        public final /* synthetic */ Exception f;

        public g(d0 d0Var, Exception exc) {
            this.c = d0Var;
            this.f = exc;
        }

        public final void run() {
            this.c.onError(this.f);
        }
    }

    /* renamed from: x7$h */
    public class h implements Runnable {
        public final /* synthetic */ m1 c;

        public h(m1 m1Var) {
            this.c = m1Var;
        }

        public final void run() {
            x7.this.m(this.c);
        }
    }

    /* renamed from: x7$i */
    public class i implements Runnable {
        public i(m1 m1Var) {
        }

        public final void run() {
            if (x7.this.r) {
                MusicPlayer musicPlayer = x7.this.w.f;
                if (musicPlayer != null && !musicPlayer.isPlaying()) {
                    musicPlayer.resume();
                }
                x7.this.r = false;
            }
        }
    }

    /* renamed from: x7$j */
    public class j implements d0<Boolean> {
        public j(long j) {
        }

        public final void onError(Exception exc) {
            System.currentTimeMillis();
            exc.getMessage();
            x7.this.t(m1.IDLE);
        }

        public final void onSuccess(Object obj) {
            System.currentTimeMillis();
            boolean booleanValue = ((Boolean) obj).booleanValue();
            x7 x7Var = x7.this;
            if (booleanValue) {
                x7Var.i();
            } else {
                x7Var.t(m1.IDLE);
            }
        }
    }

    /* renamed from: x7$k */
    public class k implements MqttCallbackExtended {

        /* renamed from: x7$k$a */
        public class a implements Runnable {
            public final /* synthetic */ String c;

            public a(String str, String str2) {
                this.c = str2;
            }

            /* JADX WARNING: Can't fix incorrect switch cases order */
            /* JADX WARNING: Can't wrap try/catch for region: R(13:58|59|60|61|62|63|(1:65)|66|(1:68)(1:69)|70|(2:72|(1:74))(1:75)|76|104) */
            /* JADX WARNING: Can't wrap try/catch for region: R(7:77|(3:81|82|(1:84))|85|86|(1:91)(1:90)|92|105) */
            /* JADX WARNING: Missing exception handler attribute for start block: B:61:0x00e5 */
            /* JADX WARNING: Missing exception handler attribute for start block: B:85:0x0148 */
            /* JADX WARNING: Removed duplicated region for block: B:90:0x0150 A[Catch:{ Exception -> 0x0170 }] */
            /* JADX WARNING: Removed duplicated region for block: B:91:0x0154 A[Catch:{ Exception -> 0x0170 }] */
            /* JADX WARNING: Unknown top exception splitter block from list: {B:61:0x00e5=Splitter:B:61:0x00e5, B:85:0x0148=Splitter:B:85:0x0148} */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void run() {
                /*
                    r12 = this;
                    x7$k r0 = defpackage.x7.k.this
                    java.lang.String r1 = r12.c
                    x7 r0 = defpackage.x7.this
                    r0.getClass()
                    org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0170 }
                    r2.<init>(r1)     // Catch:{ Exception -> 0x0170 }
                    java.lang.String r1 = "type"
                    java.lang.String r1 = r2.optString(r1)     // Catch:{ Exception -> 0x0170 }
                    android.os.Looper.myLooper()     // Catch:{ Exception -> 0x0170 }
                    android.os.Looper.getMainLooper()     // Catch:{ Exception -> 0x0170 }
                    int r3 = r1.hashCode()     // Catch:{ Exception -> 0x0170 }
                    r4 = 65535(0xffff, float:9.1834E-41)
                    r5 = 4
                    r6 = 5
                    r7 = 3
                    r8 = 0
                    r9 = 2
                    r10 = 1
                    switch(r3) {
                        case 107245: goto L_0x005d;
                        case 107930: goto L_0x0053;
                        case 114227: goto L_0x0049;
                        case 115187: goto L_0x003f;
                        case 99162322: goto L_0x0035;
                        case 207022353: goto L_0x002b;
                        default: goto L_0x002a;
                    }     // Catch:{ Exception -> 0x0170 }
                L_0x002a:
                    goto L_0x0067
                L_0x002b:
                    java.lang.String r3 = "goodbye"
                    boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0170 }
                    if (r1 == 0) goto L_0x0067
                    r1 = 1
                    goto L_0x006a
                L_0x0035:
                    java.lang.String r3 = "hello"
                    boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0170 }
                    if (r1 == 0) goto L_0x0067
                    r1 = 0
                    goto L_0x006a
                L_0x003f:
                    java.lang.String r3 = "tts"
                    boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0170 }
                    if (r1 == 0) goto L_0x0067
                    r1 = 4
                    goto L_0x006a
                L_0x0049:
                    java.lang.String r3 = "stt"
                    boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0170 }
                    if (r1 == 0) goto L_0x0067
                    r1 = 3
                    goto L_0x006a
                L_0x0053:
                    java.lang.String r3 = "mcp"
                    boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0170 }
                    if (r1 == 0) goto L_0x0067
                    r1 = 2
                    goto L_0x006a
                L_0x005d:
                    java.lang.String r3 = "llm"
                    boolean r1 = r1.equals(r3)     // Catch:{ Exception -> 0x0170 }
                    if (r1 == 0) goto L_0x0067
                    r1 = 5
                    goto L_0x006a
                L_0x0067:
                    r1 = 65535(0xffff, float:9.1834E-41)
                L_0x006a:
                    if (r1 == 0) goto L_0x016c
                    if (r1 == r10) goto L_0x0168
                    if (r1 == r9) goto L_0x0164
                    java.lang.String r3 = "text"
                    java.lang.String r11 = ""
                    if (r1 == r7) goto L_0x015d
                    if (r1 == r5) goto L_0x008c
                    if (r1 == r6) goto L_0x007f
                    r0.d(r2)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0174
                L_0x007f:
                    java.lang.String r1 = "emotion"
                    r2.optString(r1, r11)     // Catch:{ Exception -> 0x0170 }
                    r2.optString(r3, r11)     // Catch:{ Exception -> 0x0170 }
                    r0.d(r2)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0174
                L_0x008c:
                    java.lang.String r1 = "state"
                    java.lang.String r1 = r2.optString(r1, r11)     // Catch:{ Exception -> 0x0170 }
                    int r5 = r1.hashCode()     // Catch:{ Exception -> 0x0170 }
                    switch(r5) {
                        case -1089952513: goto L_0x00b8;
                        case 3540994: goto L_0x00ae;
                        case 109757538: goto L_0x00a4;
                        case 540764038: goto L_0x009a;
                        default: goto L_0x0099;
                    }     // Catch:{ Exception -> 0x0170 }
                L_0x0099:
                    goto L_0x00c1
                L_0x009a:
                    java.lang.String r5 = "sentence_start"
                    boolean r1 = r1.equals(r5)     // Catch:{ Exception -> 0x0170 }
                    if (r1 == 0) goto L_0x00c1
                    r4 = 2
                    goto L_0x00c1
                L_0x00a4:
                    java.lang.String r5 = "start"
                    boolean r1 = r1.equals(r5)     // Catch:{ Exception -> 0x0170 }
                    if (r1 == 0) goto L_0x00c1
                    r4 = 0
                    goto L_0x00c1
                L_0x00ae:
                    java.lang.String r5 = "stop"
                    boolean r1 = r1.equals(r5)     // Catch:{ Exception -> 0x0170 }
                    if (r1 == 0) goto L_0x00c1
                    r4 = 1
                    goto L_0x00c1
                L_0x00b8:
                    java.lang.String r5 = "sentence_end"
                    boolean r1 = r1.equals(r5)     // Catch:{ Exception -> 0x0170 }
                    if (r1 == 0) goto L_0x00c1
                    r4 = 3
                L_0x00c1:
                    m1 r1 = defpackage.m1.LISTENING
                    java.lang.String r5 = "AudioPlayer"
                    if (r4 == 0) goto L_0x0127
                    if (r4 == r10) goto L_0x00df
                    if (r4 == r9) goto L_0x00d7
                    if (r4 == r7) goto L_0x00d2
                    r0.d(r2)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0174
                L_0x00d2:
                    r0.d(r2)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0174
                L_0x00d7:
                    r2.optString(r3, r11)     // Catch:{ Exception -> 0x0170 }
                    r0.d(r2)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0174
                L_0x00df:
                    r3 = 600(0x258, double:2.964E-321)
                    java.lang.Thread.sleep(r3)     // Catch:{ InterruptedException -> 0x00e5 }
                    goto L_0x00ec
                L_0x00e5:
                    java.lang.Thread r3 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0170 }
                    r3.interrupt()     // Catch:{ Exception -> 0x0170 }
                L_0x00ec:
                    info.dourok.voicebot.java.audio.c r3 = r0.h     // Catch:{ Exception -> 0x0170 }
                    if (r3 == 0) goto L_0x00fa
                    java.util.concurrent.atomic.AtomicBoolean r3 = r3.c     // Catch:{ Exception -> 0x0170 }
                    r3.set(r8)     // Catch:{ Exception -> 0x0170 }
                    java.lang.String r3 = "stopPlayback requested - will stop after buffer drain"
                    android.util.Log.d(r5, r3)     // Catch:{ Exception -> 0x0170 }
                L_0x00fa:
                    r0.o = r10     // Catch:{ Exception -> 0x0170 }
                    android.os.Handler r3 = r0.v     // Catch:{ Exception -> 0x0170 }
                    if (r3 == 0) goto L_0x010b
                    e8 r4 = new e8     // Catch:{ Exception -> 0x0170 }
                    r4.<init>(r0)     // Catch:{ Exception -> 0x0170 }
                    r5 = 400(0x190, double:1.976E-321)
                    r3.postDelayed(r4, r5)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x010d
                L_0x010b:
                    r0.o = r8     // Catch:{ Exception -> 0x0170 }
                L_0x010d:
                    boolean r3 = r0.q     // Catch:{ Exception -> 0x0170 }
                    if (r3 == 0) goto L_0x011e
                    r0.t(r1)     // Catch:{ Exception -> 0x0170 }
                    int r1 = r0.t     // Catch:{ Exception -> 0x0170 }
                    if (r1 == r10) goto L_0x0123
                    int r1 = r0.t     // Catch:{ Exception -> 0x0170 }
                    r0.s(r1)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0123
                L_0x011e:
                    m1 r1 = defpackage.m1.IDLE     // Catch:{ Exception -> 0x0170 }
                    r0.t(r1)     // Catch:{ Exception -> 0x0170 }
                L_0x0123:
                    r0.d(r2)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0174
                L_0x0127:
                    r0.g = r8     // Catch:{ Exception -> 0x0170 }
                    info.dourok.voicebot.java.audio.c r3 = r0.h     // Catch:{ Exception -> 0x0170 }
                    if (r3 == 0) goto L_0x0148
                    java.util.concurrent.atomic.AtomicBoolean r4 = r3.d     // Catch:{ Exception -> 0x0170 }
                    boolean r4 = r4.get()     // Catch:{ Exception -> 0x0170 }
                    if (r4 != 0) goto L_0x0136
                    goto L_0x0148
                L_0x0136:
                    android.media.AudioTrack r4 = r3.a     // Catch:{ Exception -> 0x0148 }
                    int r4 = r4.getPlayState()     // Catch:{ Exception -> 0x0148 }
                    if (r4 == r7) goto L_0x0148
                    android.media.AudioTrack r3 = r3.a     // Catch:{ Exception -> 0x0148 }
                    r3.play()     // Catch:{ Exception -> 0x0148 }
                    java.lang.String r3 = "AudioTrack started"
                    android.util.Log.d(r5, r3)     // Catch:{ Exception -> 0x0148 }
                L_0x0148:
                    boolean r3 = r0.q     // Catch:{ Exception -> 0x0170 }
                    if (r3 == 0) goto L_0x0154
                    int r3 = r0.t     // Catch:{ Exception -> 0x0170 }
                    if (r3 != r10) goto L_0x0154
                    r0.t(r1)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0159
                L_0x0154:
                    m1 r1 = defpackage.m1.SPEAKING     // Catch:{ Exception -> 0x0170 }
                    r0.t(r1)     // Catch:{ Exception -> 0x0170 }
                L_0x0159:
                    r0.d(r2)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0174
                L_0x015d:
                    r2.optString(r3, r11)     // Catch:{ Exception -> 0x0170 }
                    r0.d(r2)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0174
                L_0x0164:
                    r0.k(r2)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0174
                L_0x0168:
                    r0.j(r2)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0174
                L_0x016c:
                    r0.n(r2)     // Catch:{ Exception -> 0x0170 }
                    goto L_0x0174
                L_0x0170:
                    r0 = move-exception
                    r0.getMessage()
                L_0x0174:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: defpackage.x7.k.a.run():void");
            }
        }

        public k() {
        }

        public final void connectComplete(boolean z, String str) {
            x7 x7Var = x7.this;
            MqttAsyncClient mqttAsyncClient = x7Var.y;
            if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
                try {
                    x7Var.y.subscribe(x7Var.ah, 0, (Object) null, (IMqttActionListener) new m8(x7Var));
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }

        public final void connectionLost(Throwable th) {
            Exception exc;
            if (th != null) {
                th.getMessage();
            }
            x7 x7Var = x7.this;
            if (th == null) {
                exc = new Exception("Connection lost");
            }
            x7Var.getClass();
            exc.getMessage();
            x7Var.b = 1;
            f4 f4Var = x7Var.s;
            if (f4Var != null) {
                f4Var.c(309);
                try {
                    Thread.sleep((long) 100);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                }
                f4Var.c(210);
            }
            x7Var.x = false;
            x7Var.o();
        }

        public final void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        }

        public final void messageArrived(String str, MqttMessage mqttMessage) throws Exception {
            String str2 = new String(mqttMessage.getPayload(), StandardCharsets.UTF_8);
            Thread.currentThread().getId();
            System.currentTimeMillis();
            x7 x7Var = x7.this;
            x7Var.getClass();
            x7Var.v.post(new a(str, str2));
        }
    }

    /* renamed from: x7$l */
    public class l implements IMqttActionListener {
        public l() {
        }

        public final void onFailure(IMqttToken iMqttToken, Throwable th) {
            th.getMessage();
            x7 x7Var = x7.this;
            x7Var.b = 5;
            f4 f4Var = x7Var.s;
            if (f4Var != null) {
                f4Var.c(309);
                try {
                    Thread.sleep((long) 100);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                }
                f4Var.c(210);
            }
            x7Var.e("Connection failed: " + th.getMessage());
            x7Var.o();
        }

        public final void onSuccess(IMqttToken iMqttToken) {
            x7 x7Var = x7.this;
            x7Var.b = 3;
            if (!x7Var.c.get() && x7Var.e != null) {
                x7Var.a.execute(new g9(x7Var));
            }
            System.currentTimeMillis();
            System.currentTimeMillis();
        }
    }

    /* renamed from: x7$m */
    public class m implements IMqttActionListener {
        public final d0 a;

        /* renamed from: x7$m$a */
        public class a implements Runnable {
            public final /* synthetic */ d0 c;

            public a(d0 d0Var) {
                this.c = d0Var;
            }

            public final void run() {
                this.c.onSuccess(null);
            }
        }

        /* renamed from: x7$m$b */
        public class b implements Runnable {
            public final /* synthetic */ d0 c;
            public final /* synthetic */ Throwable f;

            public b(d0 d0Var, Throwable th) {
                this.c = d0Var;
                this.f = th;
            }

            public final void run() {
                this.c.onError(new Exception(this.f));
            }
        }

        public m(d0 d0Var) {
            this.a = d0Var;
        }

        public final void onFailure(IMqttToken iMqttToken, Throwable th) {
            d0 d0Var = this.a;
            if (d0Var != null) {
                x7.this.a.execute(new b(d0Var, th));
            }
        }

        public final void onSuccess(IMqttToken iMqttToken) {
            d0 d0Var = this.a;
            if (d0Var != null) {
                x7.this.a.execute(new a(d0Var));
            }
        }
    }

    /* renamed from: x7$n */
    public interface n {
        void onDeviceStateChanged(m1 m1Var);
    }

    public x7(Context context, a5 a5Var, ExecutorService executorService) {
        super(executorService);
        this.w = a5Var;
        this.v = new Handler(Looper.getMainLooper());
        this.ac = Executors.newSingleThreadScheduledExecutor();
        f4 a2 = f4.a();
        this.s = a2;
        a2.b(context);
        if (a5Var != null) {
            a5Var.e = new z7(this);
            a5Var.d = new f8(this);
            g8 g8Var = new g8(this);
            a5Var.i = g8Var;
            MusicPlayer musicPlayer = a5Var.f;
            if (musicPlayer != null) {
                musicPlayer.setStateController(g8Var);
            }
        }
    }

    public static byte[] l(String str) {
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i2 = 0; i2 < length; i2 += 2) {
            bArr[i2 / 2] = (byte) (Character.digit(str.charAt(i2 + 1), 16) + (Character.digit(str.charAt(i2), 16) << 4));
        }
        return bArr;
    }

    public final void a() {
        synchronized (this.i) {
            zc zcVar = this.ai;
            if (zcVar != null) {
                zcVar.h();
                this.ai = null;
            }
            this.ag = "";
        }
        t(m1.IDLE);
    }

    public final void b() {
        this.p.set(false);
        this.ag = "";
        a();
        MqttAsyncClient mqttAsyncClient = this.y;
        if (mqttAsyncClient != null) {
            try {
                if (mqttAsyncClient.isConnected()) {
                    this.y.disconnect();
                }
                this.y.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
            this.y = null;
        }
        ScheduledExecutorService scheduledExecutorService = this.ac;
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
            this.ac.shutdown();
        }
        this.b = 1;
        if (!this.c.get() && this.e != null) {
            this.a.execute(new h9(this));
        }
    }

    public final void g(byte[] bArr) {
        zc zcVar = this.ai;
        if (zcVar != null && zcVar.j) {
            try {
                zcVar.d(bArr);
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    public final void h(String str, d0<Void> d0Var) {
        MqttAsyncClient mqttAsyncClient = this.y;
        ExecutorService executorService = this.a;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            try {
                MqttMessage mqttMessage = new MqttMessage(str.getBytes(StandardCharsets.UTF_8));
                mqttMessage.setQos(0);
                this.y.publish(this.ab, mqttMessage, (Object) null, (IMqttActionListener) new m(d0Var));
            } catch (Exception e2) {
                if (d0Var != null) {
                    executorService.execute(new g(d0Var, e2));
                }
            }
        } else if (d0Var != null) {
            executorService.execute(new f(d0Var));
        }
    }

    public final void i() {
        this.q = true;
        this.t = 2;
        this.g = false;
        if (this.k == m1.SPEAKING) {
            p();
        }
        s(this.t);
        t(m1.LISTENING);
    }

    public final void j(JSONObject jSONObject) {
        String optString = jSONObject.optString("session_id");
        if (!optString.isEmpty() && !optString.equals(this.ag)) {
            return;
        }
        if (this.k == m1.IDLE) {
            this.ag = "";
            this.q = false;
            f();
            return;
        }
        info.dourok.voicebot.java.audio.c cVar = this.h;
        if (cVar != null) {
            cVar.a();
        }
        this.q = false;
        a();
        f();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void k(org.json.JSONObject r10) {
        /*
            r9 = this;
            a5 r0 = r9.w
            if (r0 == 0) goto L_0x007d
            java.lang.String r1 = "Method '"
            java.lang.String r2 = "payload"
            org.json.JSONObject r10 = r10.optJSONObject(r2)     // Catch:{ Exception -> 0x007d }
            if (r10 != 0) goto L_0x0010
            goto L_0x007d
        L_0x0010:
            java.lang.String r2 = "method"
            java.lang.String r2 = r10.optString(r2)     // Catch:{ Exception -> 0x007d }
            java.lang.String r3 = "id"
            r4 = -1
            int r3 = r10.optInt(r3, r4)     // Catch:{ Exception -> 0x007d }
            int r5 = r2.hashCode()     // Catch:{ Exception -> 0x007d }
            r6 = 3
            r7 = 2
            r8 = 1
            switch(r5) {
                case -729711571: goto L_0x0046;
                case 498659858: goto L_0x003c;
                case 498935890: goto L_0x0032;
                case 871091088: goto L_0x0028;
                default: goto L_0x0027;
            }     // Catch:{ Exception -> 0x007d }
        L_0x0027:
            goto L_0x0050
        L_0x0028:
            java.lang.String r5 = "initialize"
            boolean r5 = r2.equals(r5)     // Catch:{ Exception -> 0x007d }
            if (r5 == 0) goto L_0x0050
            r5 = 0
            goto L_0x0053
        L_0x0032:
            java.lang.String r5 = "tools/list"
            boolean r5 = r2.equals(r5)     // Catch:{ Exception -> 0x007d }
            if (r5 == 0) goto L_0x0050
            r5 = 1
            goto L_0x0053
        L_0x003c:
            java.lang.String r5 = "tools/call"
            boolean r5 = r2.equals(r5)     // Catch:{ Exception -> 0x007d }
            if (r5 == 0) goto L_0x0050
            r5 = 2
            goto L_0x0053
        L_0x0046:
            java.lang.String r5 = "notifications/initialized"
            boolean r5 = r2.equals(r5)     // Catch:{ Exception -> 0x007d }
            if (r5 == 0) goto L_0x0050
            r5 = 3
            goto L_0x0053
        L_0x0050:
            r5 = 65535(0xffff, float:9.1834E-41)
        L_0x0053:
            if (r5 == 0) goto L_0x007a
            if (r5 == r8) goto L_0x0076
            if (r5 == r7) goto L_0x0072
            if (r5 == r6) goto L_0x007d
            if (r3 == r4) goto L_0x007d
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x007d }
            r10.<init>(r1)     // Catch:{ Exception -> 0x007d }
            r10.append(r2)     // Catch:{ Exception -> 0x007d }
            java.lang.String r1 = "' not supported"
            r10.append(r1)     // Catch:{ Exception -> 0x007d }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x007d }
            r0.d(r3, r10)     // Catch:{ Exception -> 0x007d }
            goto L_0x007d
        L_0x0072:
            r0.c(r3, r10)     // Catch:{ Exception -> 0x007d }
            goto L_0x007d
        L_0x0076:
            r0.g(r3)     // Catch:{ Exception -> 0x007d }
            goto L_0x007d
        L_0x007a:
            r0.e(r3)     // Catch:{ Exception -> 0x007d }
        L_0x007d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x7.k(org.json.JSONObject):void");
    }

    public final void m(m1 m1Var) {
        a5 a5Var;
        m1 m1Var2 = this.k;
        Thread.currentThread().getId();
        if (m1Var2 != m1Var) {
            Thread.currentThread().getId();
            m1 m1Var3 = m1.IDLE;
            if (m1Var2 != m1Var3 && m1Var == m1Var3 && this.w != null && this.r) {
                this.w.getClass();
                this.v.postDelayed(new i(m1Var2), 500);
            }
            this.k = m1Var;
            if (!(m1Var2 != m1Var3 || m1Var == m1Var3 || (a5Var = this.w) == null)) {
                MusicPlayer musicPlayer = a5Var.f;
                if (musicPlayer != null && musicPlayer.isPlaying() && !this.r) {
                    musicPlayer.pause();
                    this.r = true;
                } else if (musicPlayer != null) {
                    musicPlayer.isPlaying();
                }
                info.dourok.voicebot.java.audio.j jVar = this.w.h;
                if (jVar != null && jVar.b.get()) {
                    jVar.c();
                }
            }
            x(m1Var);
            n nVar = this.l;
            if (nVar != null) {
                nVar.onDeviceStateChanged(m1Var);
            }
        }
    }

    public final void n(JSONObject jSONObject) {
        try {
            jSONObject.toString();
            this.ag = jSONObject.optString("session_id", "");
            JSONObject optJSONObject = jSONObject.optJSONObject("audio_params");
            if (optJSONObject != null) {
                this.af = optJSONObject.optInt("sample_rate", 16000);
                this.ae = optJSONObject.optInt("frame_duration", 60);
            }
            JSONObject optJSONObject2 = jSONObject.optJSONObject("udp");
            if (optJSONObject2 != null) {
                String optString = optJSONObject2.optString("server");
                int optInt = optJSONObject2.optInt("port");
                String optString2 = optJSONObject2.optString("key");
                String optString3 = optJSONObject2.optString("nonce");
                if (!optString.isEmpty() && optInt > 0) {
                    u(optString, optInt, optString2, optString3);
                }
            }
            CountDownLatch countDownLatch = this.n;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            if (!this.c.get() && this.e != null) {
                this.a.execute(new m9(this));
            }
        } catch (Exception e2) {
            e2.getMessage();
        }
    }

    public final void o() {
        ScheduledExecutorService scheduledExecutorService = this.ac;
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.schedule(new c(), 15000, TimeUnit.MILLISECONDS);
        }
    }

    public final void p() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("session_id", this.ag);
            jSONObject.put("type", "abort");
            q(jSONObject);
            info.dourok.voicebot.java.audio.c cVar = this.h;
            if (cVar != null) {
                cVar.a();
            }
            this.g = true;
        } catch (JSONException e2) {
            e2.getMessage();
        }
    }

    public final void q(JSONObject jSONObject) {
        MqttAsyncClient mqttAsyncClient = this.y;
        if (mqttAsyncClient != null) {
            mqttAsyncClient.isConnected();
        }
        Thread.currentThread().getName();
        MqttAsyncClient mqttAsyncClient2 = this.y;
        if (mqttAsyncClient2 != null && mqttAsyncClient2.isConnected()) {
            this.d.execute(new e(jSONObject.toString()));
        }
    }

    public final void r(JSONObject jSONObject) {
        MqttAsyncClient mqttAsyncClient = this.y;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            try {
                byte[] bytes = jSONObject.toString().getBytes(StandardCharsets.UTF_8);
                int length = bytes.length;
                MqttMessage mqttMessage = new MqttMessage(bytes);
                mqttMessage.setQos(0);
                mqttMessage.setRetained(false);
                this.y.publish(this.ab, mqttMessage, (Object) null, (IMqttActionListener) new d());
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    public final void s(int i2) {
        String str;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("session_id", this.ag);
            jSONObject.put("type", "listen");
            jSONObject.put("state", "start");
            if (i2 != 0) {
                int i3 = i2 - 1;
                if (i3 == 0) {
                    str = "realtime";
                } else if (i3 != 1) {
                    str = "manual";
                } else {
                    str = "auto";
                }
                jSONObject.put("mode", str);
                jSONObject.toString();
                q(jSONObject);
                return;
            }
            throw null;
        } catch (JSONException e2) {
            e2.getMessage();
        }
    }

    public final void t(m1 m1Var) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            m(m1Var);
        } else {
            this.v.post(new h(m1Var));
        }
    }

    public final void u(String str, int i2, String str2, String str3) {
        synchronized (this.i) {
            try {
                zc zcVar = this.ai;
                if (zcVar != null) {
                    zcVar.h();
                }
                byte[] l2 = l(str2);
                byte[] l3 = l(str3);
                new SecretKeySpec(l2, "AES");
                this.u.set(0);
                this.ad.set(0);
                if (this.z == null) {
                    this.z = new OpusDecoder(this.af, this.ae);
                }
                if (this.h == null) {
                    this.h = new info.dourok.voicebot.java.audio.c(this.z);
                }
                zc zcVar2 = new zc();
                this.ai = zcVar2;
                zcVar2.f(l2);
                this.ai.g(l3);
                zc zcVar3 = this.ai;
                zcVar3.e = new a();
                if (zcVar3.b(i2, str)) {
                    zc zcVar4 = this.ai;
                    if (zcVar4.j) {
                        if (!zcVar4.k) {
                            zcVar4.k = true;
                            zcVar4.q.submit(new dd(zcVar4));
                        }
                    }
                    this.ai.d = new b();
                }
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    public final void v() {
        long currentTimeMillis = System.currentTimeMillis();
        t(m1.CONNECTING);
        System.currentTimeMillis();
        if (this.ag == null || this.ag.isEmpty()) {
            System.currentTimeMillis();
            this.d.execute(new a8(this, System.currentTimeMillis(), new j(currentTimeMillis)));
            return;
        }
        System.currentTimeMillis();
        i();
    }

    public final void w() {
        try {
            String str = this.m;
            if (str == null || str.isEmpty()) {
                e("Server not found");
                return;
            }
            MqttAsyncClient mqttAsyncClient = this.y;
            if (mqttAsyncClient != null) {
                try {
                    if (mqttAsyncClient.isConnected()) {
                        this.y.disconnect();
                    }
                    this.y.close();
                } catch (Exception e2) {
                    e2.getMessage();
                }
                this.y = null;
            }
            MqttAsyncClient mqttAsyncClient2 = new MqttAsyncClient("ssl://" + this.m + ":8883", this.j, new MemoryPersistence());
            this.y = mqttAsyncClient2;
            mqttAsyncClient2.setCallback(new k());
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setUserName(this.aj);
            mqttConnectOptions.setPassword(this.aa.toCharArray());
            mqttConnectOptions.setKeepAliveInterval(60);
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setAutomaticReconnect(false);
            mqttConnectOptions.setConnectionTimeout(30);
            mqttConnectOptions.setMqttVersion(4);
            TrustManager[] trustManagerArr = {new l8()};
            SSLContext instance = SSLContext.getInstance(SSLSocketFactoryFactory.DEFAULT_PROTOCOL);
            instance.init((KeyManager[]) null, trustManagerArr, new SecureRandom());
            mqttConnectOptions.setSocketFactory(instance.getSocketFactory());
            this.y.connect(mqttConnectOptions, (Object) null, new l());
        } catch (Exception e3) {
            e3.getMessage();
            this.b = 5;
            e("Failed to create client: " + e3.getMessage());
            o();
        }
    }

    public final void x(m1 m1Var) {
        if (this.s != null) {
            int ordinal = m1Var.ordinal();
            if (ordinal != 0) {
                if (ordinal == 2) {
                    this.s.c(501);
                } else if (ordinal != 3) {
                    this.s.c(504);
                } else {
                    this.s.c(204);
                }
            } else if (this.x) {
                this.s.d();
            } else {
                this.s.c(504);
            }
        }
    }
}
