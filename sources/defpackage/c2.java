package defpackage;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;
import info.dourok.voicebot.java.data.model.DeviceInfo;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.UUID;
import org.mozilla.javascript.Token;

/* renamed from: c2  reason: default package */
public final class c2 {
    public final Context a;
    public String b;
    public final String c;
    public final String d;

    /* renamed from: c2$a */
    public static class a {
        public int a;
        public String b;
        public int c;
        public String d;
    }

    public c2(Context context) {
        String str;
        String str2;
        try {
            str = UUID.nameUUIDFromBytes(Settings.Secure.getString(this.a.getContentResolver(), "android_id").getBytes()).toString();
        } catch (Exception unused) {
            str = UUID.randomUUID().toString();
        }
        this.c = str;
        Context context2 = this.a;
        try {
            String packageName = context2.getPackageName();
            String str3 = context2.getPackageManager().getPackageInfo(packageName, 0).versionName;
            int hashCode = (packageName + str3 + "4.0.0").hashCode();
            str2 = String.format("%032d%032d", new Object[]{Integer.valueOf(Math.abs(hashCode)), Integer.valueOf(Math.abs(hashCode * 17))});
        } catch (Exception unused2) {
            str2 = "bdc2b62695f1cc02ec83626332332a71366156c3a310e24a4f591553f40d9665";
        }
        this.d = str2;
        this.a = context;
        this.b = a();
    }

    public static String e(WifiInfo wifiInfo) {
        try {
            int ipAddress = wifiInfo.getIpAddress();
            if (ipAddress != 0) {
                return String.format("%d.%d.%d.%d", new Object[]{Integer.valueOf(ipAddress & 255), Integer.valueOf((ipAddress >> 8) & 255), Integer.valueOf((ipAddress >> 16) & 255), Integer.valueOf((ipAddress >> 24) & 255)});
            }
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                    while (true) {
                        if (inetAddresses.hasMoreElements()) {
                            InetAddress nextElement = inetAddresses.nextElement();
                            if (!nextElement.isLoopbackAddress() && !nextElement.isLinkLocalAddress()) {
                                return nextElement.getHostAddress();
                            }
                        }
                    }
                }
            } catch (SocketException e) {
                e.getMessage();
            }
            return "192.168.1.100";
        } catch (Exception e2) {
            e2.getMessage();
            return "192.168.1.100";
        }
    }

    public final String a() {
        WifiManager wifiManager;
        byte[] hardwareAddress;
        WifiInfo connectionInfo;
        String b2;
        Context context = this.a;
        if (context != null) {
            try {
                n4 a2 = n4.a(context);
                if (!(a2 == null || (b2 = a2.b()) == null || b2.isEmpty())) {
                    return b2;
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        try {
            wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        } catch (Exception e2) {
            e2.getMessage();
            wifiManager = null;
        }
        if (wifiManager != null && (connectionInfo = wifiManager.getConnectionInfo()) != null && connectionInfo.getMacAddress() != null && !connectionInfo.getMacAddress().equals("02:00:00:00:00:00")) {
            return connectionInfo.getMacAddress().toLowerCase();
        }
        try {
            for (T t : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (t.getName().equalsIgnoreCase("wlan0") && (hardwareAddress = t.getHardwareAddress()) != null) {
                    StringBuilder sb = new StringBuilder();
                    int length = hardwareAddress.length;
                    for (int i = 0; i < length; i++) {
                        sb.append(String.format("%02x:", new Object[]{Byte.valueOf(hardwareAddress[i])}));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
        } catch (SocketException e3) {
            e3.getMessage();
        }
        try {
            int hashCode = Settings.Secure.getString(context.getContentResolver(), "android_id").hashCode();
            return String.format("%02x:%02x:%02x:%02x:%02x:%02x", new Object[]{Integer.valueOf(Token.GET), 187, Integer.valueOf(Token.SET), Integer.valueOf((hashCode >> 24) & 255), Integer.valueOf((hashCode >> 16) & 255), Integer.valueOf((hashCode >> 8) & 255)});
        } catch (Exception unused) {
            return "3c:dc:75:63:35:48";
        }
    }

    public final a b() {
        WifiInfo connectionInfo;
        int i;
        a aVar = new a();
        try {
            WifiManager wifiManager = (WifiManager) this.a.getApplicationContext().getSystemService("wifi");
            if (!(wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null)) {
                String ssid = connectionInfo.getSSID();
                if (ssid != null && ssid.startsWith("\"") && ssid.endsWith("\"")) {
                    ssid = ssid.substring(1, ssid.length() - 1);
                } else if (ssid == null) {
                    ssid = "Unknown";
                }
                aVar.d = ssid;
                aVar.c = connectionInfo.getRssi();
                int frequency = connectionInfo.getFrequency();
                if (frequency < 2412 || frequency > 2484) {
                    if (frequency >= 5170) {
                        if (frequency <= 5825) {
                            i = (frequency - 5000) / 5;
                        }
                    }
                    i = 1;
                } else if (frequency == 2484) {
                    i = 14;
                } else {
                    i = ((frequency - 2412) / 5) + 1;
                }
                aVar.a = i;
                aVar.b = e(connectionInfo);
                Log.d("Esp32DeviceInfoProvider", "Real WiFi info - SSID: " + aVar.d + ", RSSI: " + aVar.c + ", Channel: " + aVar.a + ", IP: " + aVar.b);
                return aVar;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        aVar.d = "Ai Box Plus";
        aVar.c = -50;
        aVar.a = 1;
        aVar.b = "192.168.1.100";
        return aVar;
    }

    public final DeviceInfo c() {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.r = 2;
        deviceInfo.k = "vi-VN";
        deviceInfo.j = 16777216;
        deviceInfo.m = String.valueOf(Runtime.getRuntime().freeMemory());
        deviceInfo.l = d();
        deviceInfo.q = this.c;
        deviceInfo.h = "esp32s3";
        DeviceInfo.ChipInfo chipInfo = new DeviceInfo.ChipInfo();
        chipInfo.g = 9;
        chipInfo.c = 2;
        chipInfo.h = 2;
        chipInfo.f = 18;
        deviceInfo.g = chipInfo;
        DeviceInfo.Application application = new DeviceInfo.Application();
        application.i = "xiaozhi";
        application.j = "4.0.0";
        application.f = "Nov 21 2025T20:17:19Z";
        application.h = "v5.5.1";
        application.g = this.d;
        deviceInfo.c = application;
        deviceInfo.o = Arrays.asList(new DeviceInfo.Partition[]{new DeviceInfo.Partition("nvs", 1, 2, 36864, 16384), new DeviceInfo.Partition("otadata", 1, 0, 53248, 8192), new DeviceInfo.Partition("phy_init", 1, 1, 61440, 4096), new DeviceInfo.Partition("ota_0", 0, 16, 131072, 4128768), new DeviceInfo.Partition("ota_1", 0, 17, 4259840, 4128768), new DeviceInfo.Partition("assets", 1, 130, 8388608, 8388608)});
        DeviceInfo.Ota ota = new DeviceInfo.Ota();
        ota.f = "ota_0";
        deviceInfo.n = ota;
        DeviceInfo.Display display = new DeviceInfo.Display();
        display.g = false;
        display.i = 284;
        display.f = 128;
        deviceInfo.i = display;
        DeviceInfo.Board board = new DeviceInfo.Board();
        board.q = "phicomm-r1-aiboxplus";
        board.l = "phicomm-r1-aiboxplus";
        a b2 = b();
        board.p = b2.d;
        board.n = b2.c;
        board.c = b2.a;
        board.g = b2.b;
        board.h = d();
        deviceInfo.f = board;
        return deviceInfo;
    }

    public final String d() {
        String b2;
        Context context = this.a;
        if (context != null) {
            try {
                n4 a2 = n4.a(context);
                if (!(a2 == null || (b2 = a2.b()) == null || b2.isEmpty())) {
                    this.b = b2;
                    return b2;
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        String str = this.b;
        if (str != null && !str.isEmpty()) {
            return this.b;
        }
        String a3 = a();
        this.b = a3;
        return a3;
    }
}
