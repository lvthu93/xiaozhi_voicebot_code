package defpackage;

import android.content.Context;
import android.os.PowerManager;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Log;

/* renamed from: ee  reason: default package */
public final class ee {
    @Nullable
    public final PowerManager a;
    @Nullable
    public PowerManager.WakeLock b;
    public boolean c;
    public boolean d;

    public ee(Context context) {
        this.a = (PowerManager) context.getApplicationContext().getSystemService("power");
    }

    public void setEnabled(boolean z) {
        if (z && this.b == null) {
            PowerManager powerManager = this.a;
            if (powerManager == null) {
                Log.w("WakeLockManager", "PowerManager is null, therefore not creating the WakeLock.");
                return;
            }
            PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "ExoPlayer:WakeLockManager");
            this.b = newWakeLock;
            newWakeLock.setReferenceCounted(false);
        }
        this.c = z;
        PowerManager.WakeLock wakeLock = this.b;
        if (wakeLock != null) {
            if (!z || !this.d) {
                wakeLock.release();
            } else {
                wakeLock.acquire();
            }
        }
    }

    public void setStayAwake(boolean z) {
        this.d = z;
        PowerManager.WakeLock wakeLock = this.b;
        if (wakeLock != null) {
            if (!this.c || !z) {
                wakeLock.release();
            } else {
                wakeLock.acquire();
            }
        }
    }
}
