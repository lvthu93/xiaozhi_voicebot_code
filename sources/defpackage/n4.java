package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.Random;

/* renamed from: n4  reason: default package */
public final class n4 {
    public static n4 b;
    public final SharedPreferences a;

    public n4(Context context) {
        if (context != null) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                this.a = applicationContext.getSharedPreferences("device_mac_prefs", 0);
                return;
            }
            throw new IllegalStateException("ApplicationContext is null");
        }
        throw new IllegalArgumentException("Context cannot be null");
    }

    public static synchronized n4 a(Context context) {
        synchronized (n4.class) {
            if (b == null) {
                if (context == null) {
                    return null;
                }
                try {
                    b = new n4(context);
                } catch (Exception unused) {
                    return null;
                }
            }
            n4 n4Var = b;
            return n4Var;
        }
    }

    public final String b() {
        SharedPreferences sharedPreferences = this.a;
        if (sharedPreferences == null) {
            return null;
        }
        try {
            String string = sharedPreferences.getString("custom_mac_address", (String) null);
            if (string != null) {
                if (!string.isEmpty()) {
                    Log.d("MacAddressManager", "Retrieved saved MAC from SharedPreferences: ".concat(string));
                    return string;
                }
            }
            Log.d("MacAddressManager", "No saved MAC address found in SharedPreferences");
        } catch (Exception unused) {
        }
        return null;
    }

    public final String c() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (int i = 0; i < 6; i++) {
            if (i > 0) {
                sb.append(":");
            }
            sb.append(String.format("%02x", new Object[]{Integer.valueOf(random.nextInt(256))}));
        }
        String sb2 = sb.toString();
        SharedPreferences sharedPreferences = this.a;
        if (sharedPreferences != null && sb2 != null && !sb2.isEmpty() && sb2.matches("^([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$")) {
            try {
                sharedPreferences.edit().putString("custom_mac_address", sb2.toLowerCase()).apply();
                sb2.toLowerCase();
                z = true;
            } catch (Exception unused) {
            }
        }
        if (z) {
            return sb2;
        }
        return null;
    }
}
