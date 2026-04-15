package defpackage;

import org.java_websocket.WebSocket;

/* renamed from: r  reason: default package */
public final /* synthetic */ class r implements Runnable {
    public final /* synthetic */ x c;
    public final /* synthetic */ int f;
    public final /* synthetic */ String g;
    public final /* synthetic */ String h;
    public final /* synthetic */ WebSocket i;

    public /* synthetic */ r(x xVar, int i2, String str, String str2, WebSocket webSocket) {
        this.c = xVar;
        this.f = i2;
        this.g = str;
        this.h = str2;
        this.i = webSocket;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0044, code lost:
        if (r7.isEmpty() != false) goto L_0x0046;
     */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0141 A[Catch:{ Exception -> 0x017c }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0149 A[Catch:{ Exception -> 0x017c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r12 = this;
            org.java_websocket.WebSocket r0 = r12.i
            x r1 = r12.c
            android.content.Context r2 = r1.c
            android.content.Context r3 = r2.getApplicationContext()     // Catch:{ Exception -> 0x017c }
            java.lang.String r4 = "wifi"
            java.lang.Object r3 = r3.getSystemService(r4)     // Catch:{ Exception -> 0x017c }
            android.net.wifi.WifiManager r3 = (android.net.wifi.WifiManager) r3     // Catch:{ Exception -> 0x017c }
            if (r3 != 0) goto L_0x0016
            goto L_0x018d
        L_0x0016:
            ef r4 = new ef     // Catch:{ Exception -> 0x017c }
            android.content.Context r5 = r1.c     // Catch:{ Exception -> 0x017c }
            r4.<init>(r5)     // Catch:{ Exception -> 0x017c }
            boolean r5 = r4.e()     // Catch:{ Exception -> 0x017c }
            r6 = 1
            if (r5 == 0) goto L_0x0034
            r4.b()     // Catch:{ Exception -> 0x017c }
            r4 = 1500(0x5dc, double:7.41E-321)
            java.lang.Thread.sleep(r4)     // Catch:{ Exception -> 0x017c }
            r3.setWifiEnabled(r6)     // Catch:{ Exception -> 0x017c }
            r4 = 5000(0x1388, double:2.4703E-320)
            java.lang.Thread.sleep(r4)     // Catch:{ Exception -> 0x017c }
        L_0x0034:
            int r4 = r12.f
            java.lang.String r5 = r12.g
            java.lang.String r7 = r12.h
            java.lang.String r8 = "\""
            if (r4 < 0) goto L_0x0084
            if (r7 == 0) goto L_0x0046
            boolean r9 = r7.isEmpty()     // Catch:{ Exception -> 0x017c }
            if (r9 == 0) goto L_0x0084
        L_0x0046:
            boolean r0 = r5.isEmpty()     // Catch:{ Exception -> 0x017c }
            if (r0 == 0) goto L_0x0135
            java.util.List r0 = r3.getConfiguredNetworks()     // Catch:{ Exception -> 0x017c }
            if (r0 == 0) goto L_0x0135
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Exception -> 0x017c }
        L_0x0056:
            boolean r7 = r0.hasNext()     // Catch:{ Exception -> 0x017c }
            if (r7 == 0) goto L_0x0135
            java.lang.Object r7 = r0.next()     // Catch:{ Exception -> 0x017c }
            android.net.wifi.WifiConfiguration r7 = (android.net.wifi.WifiConfiguration) r7     // Catch:{ Exception -> 0x017c }
            int r9 = r7.networkId     // Catch:{ Exception -> 0x017c }
            if (r9 != r4) goto L_0x0056
            java.lang.String r7 = r7.SSID     // Catch:{ Exception -> 0x017c }
            if (r7 == 0) goto L_0x0056
            boolean r0 = r7.startsWith(r8)     // Catch:{ Exception -> 0x017c }
            if (r0 == 0) goto L_0x0081
            boolean r0 = r7.endsWith(r8)     // Catch:{ Exception -> 0x017c }
            if (r0 == 0) goto L_0x0081
            int r0 = r7.length()     // Catch:{ Exception -> 0x017c }
            int r0 = r0 - r6
            java.lang.String r5 = r7.substring(r6, r0)     // Catch:{ Exception -> 0x017c }
            goto L_0x0135
        L_0x0081:
            r5 = r7
            goto L_0x0135
        L_0x0084:
            java.util.List r4 = r3.getConfiguredNetworks()     // Catch:{ Exception -> 0x017c }
            if (r4 == 0) goto L_0x00be
            java.util.Iterator r4 = r4.iterator()     // Catch:{ Exception -> 0x017c }
        L_0x008e:
            boolean r9 = r4.hasNext()     // Catch:{ Exception -> 0x017c }
            if (r9 == 0) goto L_0x00be
            java.lang.Object r9 = r4.next()     // Catch:{ Exception -> 0x017c }
            android.net.wifi.WifiConfiguration r9 = (android.net.wifi.WifiConfiguration) r9     // Catch:{ Exception -> 0x017c }
            java.lang.String r10 = r9.SSID     // Catch:{ Exception -> 0x017c }
            if (r10 == 0) goto L_0x008e
            boolean r11 = r10.startsWith(r8)     // Catch:{ Exception -> 0x017c }
            if (r11 == 0) goto L_0x00b3
            boolean r11 = r10.endsWith(r8)     // Catch:{ Exception -> 0x017c }
            if (r11 == 0) goto L_0x00b3
            int r11 = r10.length()     // Catch:{ Exception -> 0x017c }
            int r11 = r11 - r6
            java.lang.String r10 = r10.substring(r6, r11)     // Catch:{ Exception -> 0x017c }
        L_0x00b3:
            boolean r10 = r10.equals(r5)     // Catch:{ Exception -> 0x017c }
            if (r10 == 0) goto L_0x008e
            int r4 = r9.networkId     // Catch:{ Exception -> 0x017c }
            r3.removeNetwork(r4)     // Catch:{ Exception -> 0x017c }
        L_0x00be:
            android.net.wifi.WifiConfiguration r4 = new android.net.wifi.WifiConfiguration     // Catch:{ Exception -> 0x017c }
            r4.<init>()     // Catch:{ Exception -> 0x017c }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x017c }
            r9.<init>()     // Catch:{ Exception -> 0x017c }
            r9.append(r8)     // Catch:{ Exception -> 0x017c }
            r9.append(r5)     // Catch:{ Exception -> 0x017c }
            r9.append(r8)     // Catch:{ Exception -> 0x017c }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x017c }
            r4.SSID = r9     // Catch:{ Exception -> 0x017c }
            r9 = 0
            if (r7 == 0) goto L_0x011a
            boolean r10 = r7.isEmpty()     // Catch:{ Exception -> 0x017c }
            if (r10 != 0) goto L_0x011a
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x017c }
            r10.<init>()     // Catch:{ Exception -> 0x017c }
            r10.append(r8)     // Catch:{ Exception -> 0x017c }
            r10.append(r7)     // Catch:{ Exception -> 0x017c }
            r10.append(r8)     // Catch:{ Exception -> 0x017c }
            java.lang.String r7 = r10.toString()     // Catch:{ Exception -> 0x017c }
            r4.preSharedKey = r7     // Catch:{ Exception -> 0x017c }
            java.util.BitSet r7 = r4.allowedKeyManagement     // Catch:{ Exception -> 0x017c }
            r7.set(r6)     // Catch:{ Exception -> 0x017c }
            java.util.BitSet r7 = r4.allowedProtocols     // Catch:{ Exception -> 0x017c }
            r7.set(r6)     // Catch:{ Exception -> 0x017c }
            java.util.BitSet r7 = r4.allowedProtocols     // Catch:{ Exception -> 0x017c }
            r7.set(r9)     // Catch:{ Exception -> 0x017c }
            java.util.BitSet r7 = r4.allowedPairwiseCiphers     // Catch:{ Exception -> 0x017c }
            r8 = 2
            r7.set(r8)     // Catch:{ Exception -> 0x017c }
            java.util.BitSet r7 = r4.allowedPairwiseCiphers     // Catch:{ Exception -> 0x017c }
            r7.set(r6)     // Catch:{ Exception -> 0x017c }
            java.util.BitSet r7 = r4.allowedGroupCiphers     // Catch:{ Exception -> 0x017c }
            r9 = 3
            r7.set(r9)     // Catch:{ Exception -> 0x017c }
            java.util.BitSet r7 = r4.allowedGroupCiphers     // Catch:{ Exception -> 0x017c }
            r7.set(r8)     // Catch:{ Exception -> 0x017c }
            goto L_0x011f
        L_0x011a:
            java.util.BitSet r7 = r4.allowedKeyManagement     // Catch:{ Exception -> 0x017c }
            r7.set(r9)     // Catch:{ Exception -> 0x017c }
        L_0x011f:
            int r4 = r3.addNetwork(r4)     // Catch:{ Exception -> 0x017c }
            if (r4 >= 0) goto L_0x0132
            h r2 = defpackage.h.b()     // Catch:{ Exception -> 0x017c }
            r2.e()     // Catch:{ Exception -> 0x017c }
            java.lang.String r2 = "Failed to add WiFi network"
            defpackage.x.bd(r0, r2)     // Catch:{ Exception -> 0x017c }
            goto L_0x018d
        L_0x0132:
            r3.saveConfiguration()     // Catch:{ Exception -> 0x017c }
        L_0x0135:
            r3.disconnect()     // Catch:{ Exception -> 0x017c }
            boolean r0 = r3.enableNetwork(r4, r6)     // Catch:{ Exception -> 0x017c }
            r3.reconnect()     // Catch:{ Exception -> 0x017c }
            if (r0 != 0) goto L_0x0149
            h r0 = defpackage.h.b()     // Catch:{ Exception -> 0x017c }
            r0.e()     // Catch:{ Exception -> 0x017c }
            goto L_0x018d
        L_0x0149:
            r1.y = r5     // Catch:{ Exception -> 0x017c }
            z r0 = r1.w     // Catch:{ Exception -> 0x017c }
            if (r0 == 0) goto L_0x0150
            goto L_0x0166
        L_0x0150:
            z r0 = new z     // Catch:{ Exception -> 0x017c }
            r0.<init>(r1, r3)     // Catch:{ Exception -> 0x017c }
            r1.w = r0     // Catch:{ Exception -> 0x017c }
            android.content.IntentFilter r0 = new android.content.IntentFilter     // Catch:{ Exception -> 0x017c }
            r0.<init>()     // Catch:{ Exception -> 0x017c }
            java.lang.String r3 = "android.net.wifi.STATE_CHANGE"
            r0.addAction(r3)     // Catch:{ Exception -> 0x017c }
            z r3 = r1.w     // Catch:{ Exception -> 0x017c }
            r2.registerReceiver(r3, r0)     // Catch:{ Exception -> 0x017c }
        L_0x0166:
            android.os.Handler r0 = new android.os.Handler     // Catch:{ Exception -> 0x017c }
            android.os.Looper r2 = android.os.Looper.getMainLooper()     // Catch:{ Exception -> 0x017c }
            r0.<init>(r2)     // Catch:{ Exception -> 0x017c }
            y r2 = new y     // Catch:{ Exception -> 0x017c }
            r2.<init>(r1, r5)     // Catch:{ Exception -> 0x017c }
            r1.x = r2     // Catch:{ Exception -> 0x017c }
            r3 = 30000(0x7530, double:1.4822E-319)
            r0.postDelayed(r2, r3)     // Catch:{ Exception -> 0x017c }
            goto L_0x018d
        L_0x017c:
            r0 = move-exception
            r0.getMessage()
            h r0 = defpackage.h.b()
            r0.e()
            r1.bf()
            r0 = 0
            r1.y = r0
        L_0x018d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.r.run():void");
    }
}
