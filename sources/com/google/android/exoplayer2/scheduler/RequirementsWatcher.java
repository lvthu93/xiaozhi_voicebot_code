package com.google.android.exoplayer2.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class RequirementsWatcher {
    public final Context a;
    public final Listener b;
    public final Requirements c;
    public final Handler d = Util.createHandlerForCurrentOrMainLooper();
    @Nullable
    public a e;
    public int f;
    @Nullable
    public b g;

    public interface Listener {
        void onRequirementsStateChanged(RequirementsWatcher requirementsWatcher, int i);
    }

    public class a extends BroadcastReceiver {
        public a() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!isInitialStickyBroadcast()) {
                RequirementsWatcher.this.a();
            }
        }
    }

    @RequiresApi(24)
    public final class b extends ConnectivityManager.NetworkCallback {
        public boolean a;
        public boolean b;

        public b() {
        }

        public void onAvailable(Network network) {
            RequirementsWatcher.this.d.post(new ia(this, 1));
        }

        public void onBlockedStatusChanged(Network network, boolean z) {
            if (!z) {
                RequirementsWatcher.this.d.post(new ia(this, 0));
            }
        }

        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            boolean hasCapability = networkCapabilities.hasCapability(16);
            boolean z = this.a;
            RequirementsWatcher requirementsWatcher = RequirementsWatcher.this;
            if (!z || this.b != hasCapability) {
                this.a = true;
                this.b = hasCapability;
                requirementsWatcher.d.post(new ia(this, 1));
            } else if (hasCapability) {
                requirementsWatcher.d.post(new ia(this, 0));
            }
        }

        public void onLost(Network network) {
            RequirementsWatcher.this.d.post(new ia(this, 1));
        }
    }

    public RequirementsWatcher(Context context, Listener listener, Requirements requirements) {
        this.a = context.getApplicationContext();
        this.b = listener;
        this.c = requirements;
    }

    public final void a() {
        int notMetRequirements = this.c.getNotMetRequirements(this.a);
        if (this.f != notMetRequirements) {
            this.f = notMetRequirements;
            this.b.onRequirementsStateChanged(this, notMetRequirements);
        }
    }

    public Requirements getRequirements() {
        return this.c;
    }

    public int start() {
        Requirements requirements = this.c;
        Context context = this.a;
        this.f = requirements.getNotMetRequirements(context);
        IntentFilter intentFilter = new IntentFilter();
        if (requirements.isNetworkRequired()) {
            if (Util.a >= 24) {
                b bVar = new b();
                this.g = bVar;
                ((ConnectivityManager) Assertions.checkNotNull((ConnectivityManager) context.getSystemService("connectivity"))).registerDefaultNetworkCallback(bVar);
            } else {
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            }
        }
        if (requirements.isChargingRequired()) {
            intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        }
        if (requirements.isIdleRequired()) {
            if (Util.a >= 23) {
                intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
            } else {
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
            }
        }
        if (requirements.isStorageNotLowRequired()) {
            intentFilter.addAction("android.intent.action.DEVICE_STORAGE_LOW");
            intentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
        }
        a aVar = new a();
        this.e = aVar;
        context.registerReceiver(aVar, intentFilter, (String) null, this.d);
        return this.f;
    }

    public void stop() {
        Context context = this.a;
        context.unregisterReceiver((BroadcastReceiver) Assertions.checkNotNull(this.e));
        this.e = null;
        if (Util.a >= 24 && this.g != null) {
            ((ConnectivityManager) Assertions.checkNotNull((ConnectivityManager) context.getSystemService("connectivity"))).unregisterNetworkCallback((ConnectivityManager.NetworkCallback) Assertions.checkNotNull(this.g));
            this.g = null;
        }
    }
}
