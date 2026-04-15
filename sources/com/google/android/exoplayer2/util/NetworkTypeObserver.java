package com.google.android.exoplayer2.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyDisplayInfo;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public final class NetworkTypeObserver {
    @Nullable
    public static NetworkTypeObserver e;
    public final Handler a = new Handler(Looper.getMainLooper());
    public final CopyOnWriteArrayList<WeakReference<Listener>> b = new CopyOnWriteArrayList<>();
    public final Object c = new Object();
    @GuardedBy("networkTypeLock")
    public int d = 0;

    public interface Listener {
        void onNetworkTypeChanged(int i);
    }

    public final class a extends BroadcastReceiver {
        public a() {
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x006f A[Catch:{ RuntimeException -> 0x007c }] */
        /* JADX WARNING: Removed duplicated region for block: B:37:0x0073 A[Catch:{ RuntimeException -> 0x007c }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r9, android.content.Intent r10) {
            /*
                r8 = this;
                java.lang.String r10 = "connectivity"
                java.lang.Object r10 = r9.getSystemService(r10)
                android.net.ConnectivityManager r10 = (android.net.ConnectivityManager) r10
                r0 = 29
                r1 = 0
                r2 = 5
                r3 = 1
                if (r10 != 0) goto L_0x0010
                goto L_0x004f
            L_0x0010:
                android.net.NetworkInfo r10 = r10.getActiveNetworkInfo()     // Catch:{ SecurityException -> 0x004e }
                if (r10 == 0) goto L_0x004c
                boolean r4 = r10.isConnected()
                if (r4 != 0) goto L_0x001d
                goto L_0x004c
            L_0x001d:
                int r4 = r10.getType()
                r5 = 4
                r6 = 6
                r7 = 9
                if (r4 == 0) goto L_0x0036
                if (r4 == r3) goto L_0x0046
                if (r4 == r5) goto L_0x0036
                if (r4 == r2) goto L_0x0036
                if (r4 == r6) goto L_0x0048
                if (r4 == r7) goto L_0x0034
                r5 = 8
                goto L_0x0050
            L_0x0034:
                r5 = 7
                goto L_0x0050
            L_0x0036:
                int r10 = r10.getSubtype()
                switch(r10) {
                    case 1: goto L_0x004a;
                    case 2: goto L_0x004a;
                    case 3: goto L_0x0050;
                    case 4: goto L_0x0050;
                    case 5: goto L_0x0050;
                    case 6: goto L_0x0050;
                    case 7: goto L_0x0050;
                    case 8: goto L_0x0050;
                    case 9: goto L_0x0050;
                    case 10: goto L_0x0050;
                    case 11: goto L_0x0050;
                    case 12: goto L_0x0050;
                    case 13: goto L_0x0048;
                    case 14: goto L_0x0050;
                    case 15: goto L_0x0050;
                    case 16: goto L_0x003d;
                    case 17: goto L_0x0050;
                    case 18: goto L_0x0046;
                    case 19: goto L_0x003d;
                    case 20: goto L_0x003f;
                    default: goto L_0x003d;
                }
            L_0x003d:
                r5 = 6
                goto L_0x0050
            L_0x003f:
                int r10 = com.google.android.exoplayer2.util.Util.a
                if (r10 < r0) goto L_0x004f
                r5 = 9
                goto L_0x0050
            L_0x0046:
                r5 = 2
                goto L_0x0050
            L_0x0048:
                r5 = 5
                goto L_0x0050
            L_0x004a:
                r5 = 3
                goto L_0x0050
            L_0x004c:
                r5 = 1
                goto L_0x0050
            L_0x004e:
            L_0x004f:
                r5 = 0
            L_0x0050:
                com.google.android.exoplayer2.util.NetworkTypeObserver r10 = com.google.android.exoplayer2.util.NetworkTypeObserver.this
                if (r5 != r2) goto L_0x007c
                int r2 = com.google.android.exoplayer2.util.Util.a
                if (r2 < r0) goto L_0x007c
                java.lang.String r0 = "phone"
                java.lang.Object r9 = r9.getSystemService(r0)     // Catch:{ RuntimeException -> 0x007c }
                android.telephony.TelephonyManager r9 = (android.telephony.TelephonyManager) r9     // Catch:{ RuntimeException -> 0x007c }
                java.lang.Object r9 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r9)     // Catch:{ RuntimeException -> 0x007c }
                android.telephony.TelephonyManager r9 = (android.telephony.TelephonyManager) r9     // Catch:{ RuntimeException -> 0x007c }
                com.google.android.exoplayer2.util.NetworkTypeObserver$b r0 = new com.google.android.exoplayer2.util.NetworkTypeObserver$b     // Catch:{ RuntimeException -> 0x007c }
                r0.<init>()     // Catch:{ RuntimeException -> 0x007c }
                r4 = 31
                if (r2 >= r4) goto L_0x0073
                r9.listen(r0, r3)     // Catch:{ RuntimeException -> 0x007c }
                goto L_0x0078
            L_0x0073:
                r2 = 1048576(0x100000, float:1.469368E-39)
                r9.listen(r0, r2)     // Catch:{ RuntimeException -> 0x007c }
            L_0x0078:
                r9.listen(r0, r1)     // Catch:{ RuntimeException -> 0x007c }
                return
            L_0x007c:
                com.google.android.exoplayer2.util.NetworkTypeObserver.a(r10, r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.NetworkTypeObserver.a.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    public class b extends PhoneStateListener {
        public b() {
        }

        @RequiresApi(31)
        public void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
            boolean z;
            int i;
            int d = telephonyDisplayInfo.getOverrideNetworkType();
            if (d == 3 || d == 4) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                i = 10;
            } else {
                i = 5;
            }
            NetworkTypeObserver.a(NetworkTypeObserver.this, i);
        }

        public void onServiceStateChanged(@Nullable ServiceState serviceState) {
            String str;
            boolean z;
            int i;
            if (serviceState == null) {
                str = "";
            } else {
                str = serviceState.toString();
            }
            if (str.contains("nrState=CONNECTED") || str.contains("nrState=NOT_RESTRICTED")) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                i = 10;
            } else {
                i = 5;
            }
            NetworkTypeObserver.a(NetworkTypeObserver.this, i);
        }
    }

    public NetworkTypeObserver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(new a(), intentFilter);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0016, code lost:
        if (r0.hasNext() == false) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        r1 = r0.next();
        r2 = (com.google.android.exoplayer2.util.NetworkTypeObserver.Listener) r1.get();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0024, code lost:
        if (r2 == null) goto L_0x002a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0026, code lost:
        r2.onNetworkTypeChanged(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002a, code lost:
        r3.b.remove(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000c, code lost:
        r0 = r3.b.iterator();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(com.google.android.exoplayer2.util.NetworkTypeObserver r3, int r4) {
        /*
            java.lang.Object r0 = r3.c
            monitor-enter(r0)
            int r1 = r3.d     // Catch:{ all -> 0x0031 }
            if (r1 != r4) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0031 }
            goto L_0x0030
        L_0x0009:
            r3.d = r4     // Catch:{ all -> 0x0031 }
            monitor-exit(r0)     // Catch:{ all -> 0x0031 }
            java.util.concurrent.CopyOnWriteArrayList<java.lang.ref.WeakReference<com.google.android.exoplayer2.util.NetworkTypeObserver$Listener>> r0 = r3.b
            java.util.Iterator r0 = r0.iterator()
        L_0x0012:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0030
            java.lang.Object r1 = r0.next()
            java.lang.ref.WeakReference r1 = (java.lang.ref.WeakReference) r1
            java.lang.Object r2 = r1.get()
            com.google.android.exoplayer2.util.NetworkTypeObserver$Listener r2 = (com.google.android.exoplayer2.util.NetworkTypeObserver.Listener) r2
            if (r2 == 0) goto L_0x002a
            r2.onNetworkTypeChanged(r4)
            goto L_0x0012
        L_0x002a:
            java.util.concurrent.CopyOnWriteArrayList<java.lang.ref.WeakReference<com.google.android.exoplayer2.util.NetworkTypeObserver$Listener>> r2 = r3.b
            r2.remove(r1)
            goto L_0x0012
        L_0x0030:
            return
        L_0x0031:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0031 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.NetworkTypeObserver.a(com.google.android.exoplayer2.util.NetworkTypeObserver, int):void");
    }

    public static synchronized NetworkTypeObserver getInstance(Context context) {
        NetworkTypeObserver networkTypeObserver;
        synchronized (NetworkTypeObserver.class) {
            if (e == null) {
                e = new NetworkTypeObserver(context);
            }
            networkTypeObserver = e;
        }
        return networkTypeObserver;
    }

    @VisibleForTesting
    public static synchronized void resetForTests() {
        synchronized (NetworkTypeObserver.class) {
            e = null;
        }
    }

    public int getNetworkType() {
        int i;
        synchronized (this.c) {
            i = this.d;
        }
        return i;
    }

    public void register(Listener listener) {
        CopyOnWriteArrayList<WeakReference<Listener>> copyOnWriteArrayList = this.b;
        Iterator<WeakReference<Listener>> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            WeakReference next = it.next();
            if (next.get() == null) {
                copyOnWriteArrayList.remove(next);
            }
        }
        copyOnWriteArrayList.add(new WeakReference(listener));
        this.a.post(new h2(11, this, listener));
    }
}
