package defpackage;

import android.media.MediaCodec;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.GuardedBy;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@RequiresApi(23)
/* renamed from: bg  reason: default package */
public final class bg {
    @GuardedBy("MESSAGE_PARAMS_INSTANCE_POOL")
    public static final ArrayDeque<b> h = new ArrayDeque<>();
    public static final Object i = new Object();
    public final MediaCodec a;
    public final HandlerThread b;
    public a c;
    public final AtomicReference<RuntimeException> d = new AtomicReference<>();
    public final ConditionVariable e;
    public final boolean f;
    public boolean g;

    /* renamed from: bg$a */
    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            b bVar;
            bg bgVar = bg.this;
            ArrayDeque<b> arrayDeque = bg.h;
            bgVar.getClass();
            int i = message.what;
            if (i == 0) {
                bVar = (b) message.obj;
                try {
                    bgVar.a.queueInputBuffer(bVar.a, bVar.b, bVar.c, bVar.e, bVar.f);
                } catch (RuntimeException e) {
                    bgVar.d.set(e);
                }
            } else if (i != 1) {
                if (i != 2) {
                    bgVar.d.set(new IllegalStateException(String.valueOf(message.what)));
                } else {
                    bgVar.e.open();
                }
                bVar = null;
            } else {
                bVar = (b) message.obj;
                int i2 = bVar.a;
                int i3 = bVar.b;
                MediaCodec.CryptoInfo cryptoInfo = bVar.d;
                long j = bVar.e;
                int i4 = bVar.f;
                try {
                    if (bgVar.f) {
                        synchronized (bg.i) {
                            bgVar.a.queueSecureInputBuffer(i2, i3, cryptoInfo, j, i4);
                        }
                    } else {
                        bgVar.a.queueSecureInputBuffer(i2, i3, cryptoInfo, j, i4);
                    }
                } catch (RuntimeException e2) {
                    bgVar.d.set(e2);
                }
            }
            if (bVar != null) {
                bg.a(bVar);
            }
        }
    }

    /* renamed from: bg$b */
    public static class b {
        public int a;
        public int b;
        public int c;
        public final MediaCodec.CryptoInfo d = new MediaCodec.CryptoInfo();
        public long e;
        public int f;

        public void setQueueParams(int i, int i2, int i3, long j, int i4) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.e = j;
            this.f = i4;
        }
    }

    public bg(MediaCodec mediaCodec, HandlerThread handlerThread, boolean z) {
        boolean z2;
        ConditionVariable conditionVariable = new ConditionVariable();
        this.a = mediaCodec;
        this.b = handlerThread;
        this.e = conditionVariable;
        boolean z3 = true;
        if (!z) {
            String lowerCase = Ascii.toLowerCase(Util.c);
            if (lowerCase.contains("samsung") || lowerCase.contains("motorola")) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2) {
                z3 = false;
            }
        }
        this.f = z3;
    }

    public static void a(b bVar) {
        ArrayDeque<b> arrayDeque = h;
        synchronized (arrayDeque) {
            arrayDeque.add(bVar);
        }
    }

    public void flush() {
        if (this.g) {
            try {
                ((Handler) Util.castNonNull(this.c)).removeCallbacksAndMessages((Object) null);
                ConditionVariable conditionVariable = this.e;
                conditionVariable.close();
                ((Handler) Util.castNonNull(this.c)).obtainMessage(2).sendToTarget();
                conditionVariable.block();
                RuntimeException andSet = this.d.getAndSet((Object) null);
                if (andSet != null) {
                    throw andSet;
                }
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e2);
            }
        }
    }

    public void queueInputBuffer(int i2, int i3, int i4, long j, int i5) {
        b bVar;
        RuntimeException andSet = this.d.getAndSet((Object) null);
        if (andSet == null) {
            ArrayDeque<b> arrayDeque = h;
            synchronized (arrayDeque) {
                if (arrayDeque.isEmpty()) {
                    bVar = new b();
                } else {
                    bVar = arrayDeque.removeFirst();
                }
            }
            bVar.setQueueParams(i2, i3, i4, j, i5);
            ((Handler) Util.castNonNull(this.c)).obtainMessage(0, bVar).sendToTarget();
            return;
        }
        throw andSet;
    }

    public void queueSecureInputBuffer(int i2, int i3, CryptoInfo cryptoInfo, long j, int i4) {
        b bVar;
        RuntimeException andSet = this.d.getAndSet((Object) null);
        if (andSet == null) {
            ArrayDeque<b> arrayDeque = h;
            synchronized (arrayDeque) {
                if (arrayDeque.isEmpty()) {
                    bVar = new b();
                } else {
                    bVar = arrayDeque.removeFirst();
                }
            }
            bVar.setQueueParams(i2, i3, 0, j, i4);
            int i5 = cryptoInfo.f;
            MediaCodec.CryptoInfo cryptoInfo2 = bVar.d;
            cryptoInfo2.numSubSamples = i5;
            int[] iArr = cryptoInfo.d;
            int[] iArr2 = cryptoInfo2.numBytesOfClearData;
            if (iArr != null) {
                if (iArr2 == null || iArr2.length < iArr.length) {
                    iArr2 = Arrays.copyOf(iArr, iArr.length);
                } else {
                    System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                }
            }
            cryptoInfo2.numBytesOfClearData = iArr2;
            int[] iArr3 = cryptoInfo.e;
            int[] iArr4 = cryptoInfo2.numBytesOfEncryptedData;
            if (iArr3 != null) {
                if (iArr4 == null || iArr4.length < iArr3.length) {
                    iArr4 = Arrays.copyOf(iArr3, iArr3.length);
                } else {
                    System.arraycopy(iArr3, 0, iArr4, 0, iArr3.length);
                }
            }
            cryptoInfo2.numBytesOfEncryptedData = iArr4;
            byte[] bArr = cryptoInfo.b;
            byte[] bArr2 = cryptoInfo2.key;
            if (bArr != null) {
                if (bArr2 == null || bArr2.length < bArr.length) {
                    bArr2 = Arrays.copyOf(bArr, bArr.length);
                } else {
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                }
            }
            cryptoInfo2.key = (byte[]) Assertions.checkNotNull(bArr2);
            byte[] bArr3 = cryptoInfo.a;
            byte[] bArr4 = cryptoInfo2.iv;
            if (bArr3 != null) {
                if (bArr4 == null || bArr4.length < bArr3.length) {
                    bArr4 = Arrays.copyOf(bArr3, bArr3.length);
                } else {
                    System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
                }
            }
            cryptoInfo2.iv = (byte[]) Assertions.checkNotNull(bArr4);
            cryptoInfo2.mode = cryptoInfo.c;
            if (Util.a >= 24) {
                cryptoInfo2.setPattern(new MediaCodec.CryptoInfo.Pattern(cryptoInfo.g, cryptoInfo.h));
            }
            ((Handler) Util.castNonNull(this.c)).obtainMessage(1, bVar).sendToTarget();
            return;
        }
        throw andSet;
    }

    public void shutdown() {
        if (this.g) {
            flush();
            this.b.quit();
        }
        this.g = false;
    }

    public void start() {
        if (!this.g) {
            HandlerThread handlerThread = this.b;
            handlerThread.start();
            this.c = new a(handlerThread.getLooper());
            this.g = true;
        }
    }

    public void waitUntilQueueingComplete() throws InterruptedException {
        ConditionVariable conditionVariable = this.e;
        conditionVariable.close();
        ((Handler) Util.castNonNull(this.c)).obtainMessage(2).sendToTarget();
        conditionVariable.block();
    }
}
