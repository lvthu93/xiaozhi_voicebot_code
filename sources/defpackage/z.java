package defpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.wifi.WifiManager;
import defpackage.h;

/* renamed from: z  reason: default package */
public final class z extends BroadcastReceiver {
    public final /* synthetic */ WifiManager a;
    public final /* synthetic */ x b;

    /* renamed from: z$a */
    public class a implements Runnable {
        public final /* synthetic */ String c;
        public final /* synthetic */ Context f;

        /* renamed from: z$a$a  reason: collision with other inner class name */
        public class C0026a implements h.d {
            public C0026a() {
            }

            public final void onPlaybackComplete() {
                bd.a(a.this.f);
            }

            public final void onPlaybackStarted() {
            }
        }

        public a(String str, Context context) {
            this.c = str;
            this.f = context;
        }

        public final void run() {
            String str = this.c;
            if (str == null || str.isEmpty() || str.equals("0.0.0.0")) {
                bd.a(this.f);
                return;
            }
            h b = h.b();
            b.b = new C0026a();
            b.d(str);
        }
    }

    public z(x xVar, WifiManager wifiManager) {
        this.b = xVar;
        this.a = wifiManager;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0090  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onReceive(android.content.Context r8, android.content.Intent r9) {
        /*
            r7 = this;
            java.lang.String r0 = r9.getAction()
            java.lang.String r1 = "android.net.wifi.STATE_CHANGE"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00b6
            java.lang.String r0 = "networkInfo"
            android.os.Parcelable r9 = r9.getParcelableExtra(r0)
            android.net.NetworkInfo r9 = (android.net.NetworkInfo) r9
            if (r9 == 0) goto L_0x00b6
            boolean r9 = r9.isConnected()
            if (r9 == 0) goto L_0x00b6
            android.net.wifi.WifiManager r9 = r7.a
            android.net.wifi.WifiInfo r9 = r9.getConnectionInfo()
            java.lang.String r0 = r9.getSSID()
            r1 = 1
            if (r0 == 0) goto L_0x0040
            java.lang.String r2 = "\""
            boolean r3 = r0.startsWith(r2)
            if (r3 == 0) goto L_0x0040
            boolean r2 = r0.endsWith(r2)
            if (r2 == 0) goto L_0x0040
            int r2 = r0.length()
            int r2 = r2 - r1
            java.lang.String r0 = r0.substring(r1, r2)
        L_0x0040:
            x r2 = r7.b
            java.lang.String r3 = r2.y
            if (r3 == 0) goto L_0x00b6
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00b6
            r0 = 0
            int r9 = r9.getIpAddress()     // Catch:{ Exception -> 0x0087 }
            if (r9 != 0) goto L_0x0054
            goto L_0x008b
        L_0x0054:
            java.lang.String r3 = "%d.%d.%d.%d"
            r4 = 4
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0087 }
            r5 = r9 & 255(0xff, float:3.57E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Exception -> 0x0087 }
            r6 = 0
            r4[r6] = r5     // Catch:{ Exception -> 0x0087 }
            int r5 = r9 >> 8
            r5 = r5 & 255(0xff, float:3.57E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Exception -> 0x0087 }
            r4[r1] = r5     // Catch:{ Exception -> 0x0087 }
            int r1 = r9 >> 16
            r1 = r1 & 255(0xff, float:3.57E-43)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0087 }
            r5 = 2
            r4[r5] = r1     // Catch:{ Exception -> 0x0087 }
            int r9 = r9 >> 24
            r9 = r9 & 255(0xff, float:3.57E-43)
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x0087 }
            r1 = 3
            r4[r1] = r9     // Catch:{ Exception -> 0x0087 }
            java.lang.String r9 = java.lang.String.format(r3, r4)     // Catch:{ Exception -> 0x0087 }
            goto L_0x008c
        L_0x0087:
            r9 = move-exception
            r9.getMessage()
        L_0x008b:
            r9 = r0
        L_0x008c:
            java.lang.Runnable r1 = r2.x
            if (r1 == 0) goto L_0x00a0
            android.os.Handler r1 = new android.os.Handler
            android.os.Looper r3 = android.os.Looper.getMainLooper()
            r1.<init>(r3)
            java.lang.Runnable r3 = r2.x
            r1.removeCallbacks(r3)
            r2.x = r0
        L_0x00a0:
            r2.bf()
            r2.y = r0
            android.os.Handler r0 = new android.os.Handler
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            r0.<init>(r1)
            z$a r1 = new z$a
            r1.<init>(r9, r8)
            r0.post(r1)
        L_0x00b6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.z.onReceive(android.content.Context, android.content.Intent):void");
    }
}
