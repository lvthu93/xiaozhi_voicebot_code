package defpackage;

import android.content.Context;
import android.net.wifi.WifiManager;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Log;

/* renamed from: ff  reason: default package */
public final class ff {
    @Nullable
    public final WifiManager a;
    @Nullable
    public WifiManager.WifiLock b;
    public boolean c;
    public boolean d;

    public ff(Context context) {
        this.a = (WifiManager) context.getApplicationContext().getSystemService("wifi");
    }

    public void setEnabled(boolean z) {
        if (z && this.b == null) {
            WifiManager wifiManager = this.a;
            if (wifiManager == null) {
                Log.w("WifiLockManager", "WifiManager is null, therefore not creating the WifiLock.");
                return;
            }
            WifiManager.WifiLock createWifiLock = wifiManager.createWifiLock(3, "ExoPlayer:WifiLockManager");
            this.b = createWifiLock;
            createWifiLock.setReferenceCounted(false);
        }
        this.c = z;
        WifiManager.WifiLock wifiLock = this.b;
        if (wifiLock != null) {
            if (!z || !this.d) {
                wifiLock.release();
            } else {
                wifiLock.acquire();
            }
        }
    }

    public void setStayAwake(boolean z) {
        this.d = z;
        WifiManager.WifiLock wifiLock = this.b;
        if (wifiLock != null) {
            if (!this.c || !z) {
                wifiLock.release();
            } else {
                wifiLock.acquire();
            }
        }
    }
}
