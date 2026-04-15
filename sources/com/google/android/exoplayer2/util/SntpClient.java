package com.google.android.exoplayer2.util;

import android.os.SystemClock;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.Loader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

public final class SntpClient {
    public static final Object a = new Object();
    public static final Object b = new Object();
    @GuardedBy("valueLock")
    public static boolean c = false;
    @GuardedBy("valueLock")
    public static long d = 0;
    @GuardedBy("valueLock")
    public static String e = "time.android.com";

    public interface InitializationCallback {
        void onInitializationFailed(IOException iOException);

        void onInitialized();
    }

    public static final class a implements Loader.Callback<Loader.Loadable> {
        @Nullable
        public final InitializationCallback c;

        public a(@Nullable InitializationCallback initializationCallback) {
            this.c = initializationCallback;
        }

        public void onLoadCanceled(Loader.Loadable loadable, long j, long j2, boolean z) {
        }

        public void onLoadCompleted(Loader.Loadable loadable, long j, long j2) {
            InitializationCallback initializationCallback = this.c;
            if (initializationCallback == null) {
                return;
            }
            if (!SntpClient.isInitialized()) {
                initializationCallback.onInitializationFailed(new IOException(new ConcurrentModificationException()));
            } else {
                initializationCallback.onInitialized();
            }
        }

        public Loader.LoadErrorAction onLoadError(Loader.Loadable loadable, long j, long j2, IOException iOException, int i) {
            InitializationCallback initializationCallback = this.c;
            if (initializationCallback != null) {
                initializationCallback.onInitializationFailed(iOException);
            }
            return Loader.d;
        }
    }

    public static final class b implements Loader.Loadable {
        public void cancelLoad() {
        }

        /* JADX WARNING: CFG modification limit reached, blocks count: 139 */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            r2 = com.google.android.exoplayer2.util.SntpClient.a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0012, code lost:
            monitor-enter(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            com.google.android.exoplayer2.util.SntpClient.d = r2;
            com.google.android.exoplayer2.util.SntpClient.c = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0018, code lost:
            monitor-exit(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x001a, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void load() throws java.io.IOException {
            /*
                r4 = this;
                java.lang.Object r0 = com.google.android.exoplayer2.util.SntpClient.a
                monitor-enter(r0)
                java.lang.Object r1 = com.google.android.exoplayer2.util.SntpClient.b     // Catch:{ all -> 0x0023 }
                monitor-enter(r1)     // Catch:{ all -> 0x0023 }
                boolean r2 = com.google.android.exoplayer2.util.SntpClient.c     // Catch:{ all -> 0x0021 }
                if (r2 == 0) goto L_0x000d
                monitor-exit(r1)     // Catch:{ all -> 0x0021 }
                monitor-exit(r0)     // Catch:{ all -> 0x0023 }
                return
            L_0x000d:
                monitor-exit(r1)     // Catch:{ all -> 0x0021 }
                long r2 = com.google.android.exoplayer2.util.SntpClient.a()     // Catch:{ all -> 0x0023 }
                monitor-enter(r1)     // Catch:{ all -> 0x0023 }
                com.google.android.exoplayer2.util.SntpClient.d = r2     // Catch:{ all -> 0x001d }
                r2 = 1
                com.google.android.exoplayer2.util.SntpClient.c = r2     // Catch:{ all -> 0x001d }
                monitor-exit(r1)     // Catch:{ all -> 0x001d }
                monitor-exit(r0)     // Catch:{ all -> 0x0023 }
                return
            L_0x001b:
                monitor-exit(r1)     // Catch:{ all -> 0x001d }
                throw r2     // Catch:{ all -> 0x0023 }
            L_0x001d:
                r2 = move-exception
                goto L_0x001b
            L_0x001f:
                monitor-exit(r1)     // Catch:{ all -> 0x0021 }
                throw r2     // Catch:{ all -> 0x0023 }
            L_0x0021:
                r2 = move-exception
                goto L_0x001f
            L_0x0023:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0023 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.SntpClient.b.load():void");
        }
    }

    public static long a() throws IOException {
        DatagramSocket datagramSocket;
        InetAddress byName = InetAddress.getByName(getNtpHost());
        DatagramSocket datagramSocket2 = new DatagramSocket();
        try {
            datagramSocket2.setSoTimeout(10000);
            byte[] bArr = new byte[48];
            DatagramPacket datagramPacket = new DatagramPacket(bArr, 48, byName, 123);
            bArr[0] = 27;
            long currentTimeMillis = System.currentTimeMillis();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (currentTimeMillis == 0) {
                Arrays.fill(bArr, 40, 48, (byte) 0);
                datagramSocket = datagramSocket2;
            } else {
                long j = currentTimeMillis / 1000;
                Long.signum(j);
                long j2 = currentTimeMillis - (j * 1000);
                long j3 = j + 2208988800L;
                bArr[40] = (byte) ((int) (j3 >> 24));
                DatagramSocket datagramSocket3 = datagramSocket2;
                try {
                    bArr[41] = (byte) ((int) (j3 >> 16));
                    datagramSocket = datagramSocket3;
                    try {
                        bArr[42] = (byte) ((int) (j3 >> 8));
                        bArr[43] = (byte) ((int) (j3 >> 0));
                        long j4 = (j2 * 4294967296L) / 1000;
                        bArr[44] = (byte) ((int) (j4 >> 24));
                        bArr[45] = (byte) ((int) (j4 >> 16));
                        bArr[46] = (byte) ((int) (j4 >> 8));
                        bArr[47] = (byte) ((int) (Math.random() * 255.0d));
                    } catch (Throwable th) {
                        th = th;
                        datagramSocket2 = datagramSocket;
                        Throwable th2 = th;
                        try {
                            datagramSocket2.close();
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                        }
                        throw th2;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    datagramSocket2 = datagramSocket3;
                    Throwable th22 = th;
                    datagramSocket2.close();
                    throw th22;
                }
            }
            datagramSocket2 = datagramSocket;
            datagramSocket2.send(datagramPacket);
            datagramSocket2.receive(new DatagramPacket(bArr, 48));
            long elapsedRealtime2 = SystemClock.elapsedRealtime();
            long j5 = (elapsedRealtime2 - elapsedRealtime) + currentTimeMillis;
            byte b2 = bArr[0];
            long d2 = d(bArr, 24);
            long d3 = d(bArr, 32);
            long j6 = elapsedRealtime2;
            long d4 = d(bArr, 40);
            b((byte) ((b2 >> 6) & 3), (byte) (b2 & 7), bArr[1] & 255, d4);
            long j7 = (j5 + (((d4 - j5) + (d3 - d2)) / 2)) - j6;
            datagramSocket2.close();
            return j7;
        } catch (Throwable th5) {
            th = th5;
            Throwable th222 = th;
            datagramSocket2.close();
            throw th222;
        }
    }

    public static void b(byte b2, byte b3, int i, long j) throws IOException {
        if (b2 == 3) {
            throw new IOException("SNTP: Unsynchronized server");
        } else if (b3 != 4 && b3 != 5) {
            throw new IOException(y2.d(26, "SNTP: Untrusted mode: ", b3));
        } else if (i == 0 || i > 15) {
            throw new IOException(y2.d(36, "SNTP: Untrusted stratum: ", i));
        } else if (j == 0) {
            throw new IOException("SNTP: Zero transmitTime");
        }
    }

    public static long c(byte[] bArr, int i) {
        byte b2 = bArr[i];
        byte b3 = bArr[i + 1];
        byte b4 = bArr[i + 2];
        byte b5 = bArr[i + 3];
        byte b6 = b2 & true;
        int i2 = b2;
        if (b6 == true) {
            i2 = (b2 & Byte.MAX_VALUE) + 128;
        }
        byte b7 = b3 & true;
        int i3 = b3;
        if (b7 == true) {
            i3 = (b3 & Byte.MAX_VALUE) + 128;
        }
        byte b8 = b4 & true;
        int i4 = b4;
        if (b8 == true) {
            i4 = (b4 & Byte.MAX_VALUE) + 128;
        }
        byte b9 = b5 & true;
        int i5 = b5;
        if (b9 == true) {
            i5 = (b5 & Byte.MAX_VALUE) + 128;
        }
        return (((long) i2) << 24) + (((long) i3) << 16) + (((long) i4) << 8) + ((long) i5);
    }

    public static long d(byte[] bArr, int i) {
        long c2 = c(bArr, i);
        long c3 = c(bArr, i + 4);
        if (c2 == 0 && c3 == 0) {
            return 0;
        }
        return ((c3 * 1000) / 4294967296L) + ((c2 - 2208988800L) * 1000);
    }

    public static long getElapsedRealtimeOffsetMs() {
        long j;
        synchronized (b) {
            if (c) {
                j = d;
            } else {
                j = -9223372036854775807L;
            }
        }
        return j;
    }

    public static String getNtpHost() {
        String str;
        synchronized (b) {
            str = e;
        }
        return str;
    }

    public static void initialize(@Nullable Loader loader, @Nullable InitializationCallback initializationCallback) {
        if (!isInitialized()) {
            if (loader == null) {
                loader = new Loader("SntpClient");
            }
            loader.startLoading(new b(), new a(initializationCallback), 1);
        } else if (initializationCallback != null) {
            initializationCallback.onInitialized();
        }
    }

    public static boolean isInitialized() {
        boolean z;
        synchronized (b) {
            z = c;
        }
        return z;
    }

    public static void setNtpHost(String str) {
        synchronized (b) {
            if (!e.equals(str)) {
                e = str;
                c = false;
            }
        }
    }
}
