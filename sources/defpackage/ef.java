package defpackage;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

/* renamed from: ef  reason: default package */
public final class ef {
    public boolean a = false;
    public final WifiManager b;

    public ef(Context context) {
        this.b = (WifiManager) context.getSystemService("wifi");
    }

    public static WifiConfiguration a(String str) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "Phicomm-R1";
        if (str == null || str.length() < 8) {
            wifiConfiguration.allowedKeyManagement.set(0);
        } else {
            wifiConfiguration.preSharedKey = str;
            wifiConfiguration.allowedKeyManagement.set(1);
            wifiConfiguration.allowedAuthAlgorithms.set(0);
            wifiConfiguration.allowedProtocols.set(1);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.allowedGroupCiphers.set(3);
        }
        return wifiConfiguration;
    }

    public final void b() {
        WifiManager wifiManager = this.b;
        try {
            if (((Boolean) wifiManager.getClass().getMethod("setWifiApEnabled", new Class[]{WifiConfiguration.class, Boolean.TYPE}).invoke(wifiManager, new Object[]{null, Boolean.FALSE})).booleanValue() && this.a) {
                Thread.sleep(500);
                wifiManager.setWifiEnabled(true);
                Log.d("WifiApManager", "WiFi restored");
            }
        } catch (Exception unused) {
        }
    }

    public final boolean c(String str) {
        WifiManager wifiManager = this.b;
        try {
            boolean isWifiEnabled = wifiManager.isWifiEnabled();
            this.a = isWifiEnabled;
            if (isWifiEnabled) {
                Log.d("WifiApManager", "Disabling WiFi before enabling AP");
                wifiManager.setWifiEnabled(false);
                Thread.sleep(500);
            }
            WifiConfiguration a2 = a(str);
            return ((Boolean) wifiManager.getClass().getMethod("setWifiApEnabled", new Class[]{WifiConfiguration.class, Boolean.TYPE}).invoke(wifiManager, new Object[]{a2, Boolean.TRUE})).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    public final String d() {
        WifiConfiguration wifiConfiguration;
        String str;
        WifiManager wifiManager = this.b;
        try {
            wifiConfiguration = (WifiConfiguration) wifiManager.getClass().getMethod("getWifiApConfiguration", new Class[0]).invoke(wifiManager, new Object[0]);
        } catch (Exception unused) {
            wifiConfiguration = null;
        }
        if (wifiConfiguration == null || (str = wifiConfiguration.SSID) == null) {
            return "Phicomm-R1";
        }
        return str;
    }

    public final boolean e() {
        int i;
        WifiManager wifiManager = this.b;
        try {
            i = ((Integer) wifiManager.getClass().getMethod("getWifiApState", new Class[0]).invoke(wifiManager, new Object[0])).intValue();
        } catch (Exception unused) {
            i = 14;
        }
        if (i == 13 || i == 12) {
            return true;
        }
        return false;
    }
}
