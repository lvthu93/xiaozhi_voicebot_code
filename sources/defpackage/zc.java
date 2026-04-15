package defpackage;

import android.util.Log;
import info.dourok.voicebot.java.audio.OpusDecoder;
import info.dourok.voicebot.java.audio.g;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: zc  reason: default package */
public final class zc {
    public final Cipher a;
    public byte[] b;
    public byte[] c;
    public cw d;
    public d e;
    public InetAddress f;
    public int g;
    public DatagramSocket h;
    public long i = 0;
    public volatile boolean j = false;
    public volatile boolean k = false;
    public long l = 0;
    public long m = 0;
    public long n = 0;
    public long o = System.currentTimeMillis();
    public final ExecutorService p = Executors.newSingleThreadExecutor(new a());
    public final ExecutorService q = Executors.newSingleThreadExecutor(new b());

    /* renamed from: zc$a */
    public class a implements ThreadFactory {
        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "UDP-Audio-Send");
            thread.setDaemon(true);
            thread.setPriority(10);
            return thread;
        }
    }

    /* renamed from: zc$b */
    public class b implements ThreadFactory {
        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "UDP-Audio-Receive");
            thread.setDaemon(true);
            thread.setPriority(5);
            return thread;
        }
    }

    /* renamed from: zc$c */
    public class c implements Runnable {
        public final /* synthetic */ byte[] c;

        public c(byte[] bArr) {
            this.c = bArr;
        }

        public final void run() {
            byte[] bArr = this.c;
            zc zcVar = zc.this;
            zcVar.getClass();
            try {
                zcVar.e(bArr);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    /* renamed from: zc$d */
    public interface d {
    }

    public zc() {
        try {
            this.a = Cipher.getInstance("AES/CTR/NoPadding");
            Log.d("UdpAudioChannel", "AES-CTR cipher initialized");
        } catch (Exception unused) {
        }
    }

    public final byte[] a(byte[] bArr) throws Exception {
        if (bArr.length <= 16) {
            throw new IllegalArgumentException("Invalid encrypted data length: " + bArr.length);
        } else if (this.b != null) {
            byte[] copyOfRange = Arrays.copyOfRange(bArr, 0, 16);
            byte[] copyOfRange2 = Arrays.copyOfRange(bArr, 16, bArr.length);
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.b, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(copyOfRange);
            Cipher cipher = this.a;
            cipher.init(2, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(copyOfRange2);
        } else {
            throw new IllegalStateException("AES key not set");
        }
    }

    public final boolean b(int i2, String str) {
        try {
            this.f = InetAddress.getByName(str);
            if (i2 <= 0) {
                i2 = 8885;
            }
            this.g = i2;
            DatagramSocket datagramSocket = new DatagramSocket();
            this.h = datagramSocket;
            datagramSocket.setSoTimeout(1000);
            this.h.setReceiveBufferSize(65536);
            this.h.setSendBufferSize(65536);
            this.j = true;
            String.format("UDP audio channel initialized: %s:%d", new Object[]{str, Integer.valueOf(this.g)});
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public final void c(byte[] bArr) {
        info.dourok.voicebot.java.audio.c cVar;
        try {
            byte[] a2 = a(bArr);
            cw cwVar = this.d;
            if (cwVar != null && a2 != null && (cVar = x7.this.h) != null && cVar.d.get()) {
                if (a2.length != 0) {
                    info.dourok.voicebot.java.audio.d dVar = new info.dourok.voicebot.java.audio.d(cVar);
                    OpusDecoder opusDecoder = cVar.b;
                    if (opusDecoder.a != 0) {
                        opusDecoder.b.execute(new g(opusDecoder, a2, dVar));
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0030, code lost:
        if (r0.q != false) goto L_0x0034;
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0036  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void d(byte[] r5) {
        /*
            r4 = this;
            boolean r0 = r4.j
            if (r0 == 0) goto L_0x007f
            if (r5 == 0) goto L_0x007f
            int r0 = r5.length
            if (r0 != 0) goto L_0x000b
            goto L_0x007f
        L_0x000b:
            zc$d r0 = r4.e
            if (r0 == 0) goto L_0x0061
            x7$a r0 = (defpackage.x7.a) r0
            x7 r0 = defpackage.x7.this
            boolean r1 = r0.g
            if (r1 != 0) goto L_0x0033
            boolean r1 = r0.o
            if (r1 == 0) goto L_0x001c
            goto L_0x0033
        L_0x001c:
            m1 r1 = r0.k
            m1 r2 = defpackage.m1.LISTENING
            r3 = 1
            if (r1 != r2) goto L_0x0024
            goto L_0x0034
        L_0x0024:
            m1 r1 = r0.k
            m1 r2 = defpackage.m1.SPEAKING
            if (r1 != r2) goto L_0x0033
            int r1 = r0.t
            if (r1 != r3) goto L_0x0033
            boolean r0 = r0.q
            if (r0 == 0) goto L_0x0033
            goto L_0x0034
        L_0x0033:
            r3 = 0
        L_0x0034:
            if (r3 != 0) goto L_0x0061
            long r0 = r4.n
            r2 = 1
            long r0 = r0 + r2
            r4.n = r0
            r2 = 100
            long r0 = r0 % r2
            r2 = 0
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 != 0) goto L_0x0060
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "sendAudioData skipped: shouldSendAudio() returned false (count="
            r5.<init>(r0)
            long r0 = r4.n
            r5.append(r0)
            java.lang.String r0 = ")"
            r5.append(r0)
            java.lang.String r5 = r5.toString()
            java.lang.String r0 = "UdpAudioChannel"
            android.util.Log.d(r0, r5)
        L_0x0060:
            return
        L_0x0061:
            java.net.DatagramSocket r0 = r4.h
            if (r0 == 0) goto L_0x007f
            boolean r0 = r0.isClosed()
            if (r0 == 0) goto L_0x006c
            goto L_0x007f
        L_0x006c:
            byte[] r0 = r4.b
            if (r0 == 0) goto L_0x007f
            byte[] r0 = r4.c
            if (r0 != 0) goto L_0x0075
            goto L_0x007f
        L_0x0075:
            java.util.concurrent.ExecutorService r0 = r4.p
            zc$c r1 = new zc$c
            r1.<init>(r5)
            r0.submit(r1)
        L_0x007f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zc.d(byte[]):void");
    }

    public final void e(byte[] bArr) throws Exception {
        DatagramSocket datagramSocket;
        Cipher cipher;
        byte[] bArr2 = bArr;
        if (this.j) {
            byte[] bArr3 = this.b;
            byte[] bArr4 = this.c;
            if (bArr3 != null && bArr4 != null && (datagramSocket = this.h) != null && !datagramSocket.isClosed()) {
                byte[] bArr5 = this.b;
                byte[] bArr6 = this.c;
                if (bArr5 == null || bArr6 == null || (cipher = this.a) == null) {
                    throw new IllegalStateException("Encryption not initialized or already shutdown");
                }
                byte[] copyOf = Arrays.copyOf(bArr6, 16);
                long currentTimeMillis = System.currentTimeMillis();
                int length = bArr2.length;
                copyOf[2] = (byte) ((length >> 8) & 255);
                copyOf[3] = (byte) (length & 255);
                copyOf[8] = (byte) ((int) ((currentTimeMillis >> 24) & 255));
                copyOf[9] = (byte) ((int) ((currentTimeMillis >> 16) & 255));
                copyOf[10] = (byte) ((int) ((currentTimeMillis >> 8) & 255));
                copyOf[11] = (byte) ((int) (currentTimeMillis & 255));
                long j2 = this.i;
                this.i = j2 + 1;
                copyOf[12] = (byte) ((int) ((j2 >> 24) & 255));
                copyOf[13] = (byte) ((int) ((j2 >> 16) & 255));
                copyOf[14] = (byte) ((int) ((j2 >> 8) & 255));
                copyOf[15] = (byte) ((int) (j2 & 255));
                cipher.init(1, new SecretKeySpec(bArr5, "AES"), new IvParameterSpec(copyOf));
                byte[] doFinal = cipher.doFinal(bArr2);
                ByteBuffer allocate = ByteBuffer.allocate(doFinal.length + 16);
                allocate.put(copyOf);
                allocate.put(doFinal);
                byte[] array = allocate.array();
                this.h.send(new DatagramPacket(array, array.length, this.f, this.g));
                long j3 = this.l + 1;
                this.l = j3;
                if (j3 <= 3 || j3 % 100 == 0) {
                    this.f.getHostAddress();
                }
                long currentTimeMillis2 = System.currentTimeMillis();
                if (currentTimeMillis2 - this.o > 5000) {
                    this.o = currentTimeMillis2;
                }
            }
        }
    }

    public final void f(byte[] bArr) {
        if (bArr.length == 16) {
            this.b = Arrays.copyOf(bArr, bArr.length);
            Log.d("UdpAudioChannel", "AES key set: " + bArr.length + " bytes");
        }
    }

    public final void g(byte[] bArr) {
        if (bArr.length == 16) {
            this.c = Arrays.copyOf(bArr, bArr.length);
            Log.d("UdpAudioChannel", "AES nonce set: " + bArr.length + " bytes");
        }
    }

    public final void h() {
        Log.d("UdpAudioChannel", "Shutting down UDP audio channel");
        this.j = false;
        this.k = false;
        this.b = null;
        this.c = null;
        DatagramSocket datagramSocket = this.h;
        if (datagramSocket != null && !datagramSocket.isClosed()) {
            this.h.close();
        }
        this.p.shutdownNow();
        this.q.shutdownNow();
        Log.d("UdpAudioChannel", String.format("UDP audio channel shutdown complete. Stats: sent=%d received=%d", new Object[]{Long.valueOf(this.l), Long.valueOf(this.m)}));
    }
}
