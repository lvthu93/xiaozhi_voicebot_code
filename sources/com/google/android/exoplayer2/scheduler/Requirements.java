package com.google.android.exoplayer2.scheduler;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Requirements implements Parcelable {
    public static final Parcelable.Creator<Requirements> CREATOR = new a();
    public final int c;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequirementFlags {
    }

    public class a implements Parcelable.Creator<Requirements> {
        public Requirements createFromParcel(Parcel parcel) {
            return new Requirements(parcel.readInt());
        }

        public Requirements[] newArray(int i) {
            return new Requirements[i];
        }
    }

    public Requirements(int i) {
        this.c = (i & 2) != 0 ? i | 1 : i;
    }

    public boolean checkRequirements(Context context) {
        return getNotMetRequirements(context) == 0;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && Requirements.class == obj.getClass() && this.c == ((Requirements) obj).c) {
            return true;
        }
        return false;
    }

    public Requirements filterRequirements(int i) {
        int i2 = this.c;
        int i3 = i & i2;
        if (i3 == i2) {
            return this;
        }
        return new Requirements(i3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getNotMetRequirements(android.content.Context r9) {
        /*
            r8 = this;
            boolean r0 = r8.isNetworkRequired()
            r1 = 2
            r2 = 1
            r3 = 0
            if (r0 != 0) goto L_0x000b
        L_0x0009:
            r0 = 0
            goto L_0x0057
        L_0x000b:
            java.lang.String r0 = "connectivity"
            java.lang.Object r0 = r9.getSystemService(r0)
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)
            android.net.ConnectivityManager r0 = (android.net.ConnectivityManager) r0
            android.net.NetworkInfo r4 = r0.getActiveNetworkInfo()
            if (r4 == 0) goto L_0x0053
            boolean r4 = r4.isConnected()
            if (r4 == 0) goto L_0x0053
            int r4 = com.google.android.exoplayer2.util.Util.a
            r5 = 24
            if (r4 >= r5) goto L_0x002a
            goto L_0x003f
        L_0x002a:
            android.net.Network r4 = r0.getActiveNetwork()
            if (r4 != 0) goto L_0x0031
            goto L_0x0041
        L_0x0031:
            android.net.NetworkCapabilities r4 = r0.getNetworkCapabilities(r4)
            if (r4 == 0) goto L_0x0041
            r5 = 16
            boolean r4 = r4.hasCapability(r5)
            if (r4 == 0) goto L_0x0041
        L_0x003f:
            r4 = 1
            goto L_0x0042
        L_0x0041:
            r4 = 0
        L_0x0042:
            if (r4 != 0) goto L_0x0045
            goto L_0x0053
        L_0x0045:
            boolean r4 = r8.isUnmeteredNetworkRequired()
            if (r4 == 0) goto L_0x0009
            boolean r0 = r0.isActiveNetworkMetered()
            if (r0 == 0) goto L_0x0009
            r0 = 2
            goto L_0x0057
        L_0x0053:
            int r0 = r8.c
            r0 = r0 & 3
        L_0x0057:
            boolean r4 = r8.isChargingRequired()
            r5 = 0
            if (r4 == 0) goto L_0x0080
            android.content.IntentFilter r4 = new android.content.IntentFilter
            java.lang.String r6 = "android.intent.action.BATTERY_CHANGED"
            r4.<init>(r6)
            android.content.Intent r4 = r9.registerReceiver(r5, r4)
            if (r4 != 0) goto L_0x006c
            goto L_0x0079
        L_0x006c:
            java.lang.String r6 = "status"
            r7 = -1
            int r4 = r4.getIntExtra(r6, r7)
            if (r4 == r1) goto L_0x007b
            r1 = 5
            if (r4 != r1) goto L_0x0079
            goto L_0x007b
        L_0x0079:
            r1 = 0
            goto L_0x007c
        L_0x007b:
            r1 = 1
        L_0x007c:
            if (r1 != 0) goto L_0x0080
            r0 = r0 | 8
        L_0x0080:
            boolean r1 = r8.isIdleRequired()
            if (r1 == 0) goto L_0x00b5
            java.lang.String r1 = "power"
            java.lang.Object r1 = r9.getSystemService(r1)
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            android.os.PowerManager r1 = (android.os.PowerManager) r1
            int r4 = com.google.android.exoplayer2.util.Util.a
            r6 = 23
            if (r4 < r6) goto L_0x009d
            boolean r1 = r1.isDeviceIdleMode()
            goto L_0x00b1
        L_0x009d:
            r6 = 20
            if (r4 < r6) goto L_0x00a8
            boolean r1 = r1.isInteractive()
            if (r1 != 0) goto L_0x00b0
            goto L_0x00ae
        L_0x00a8:
            boolean r1 = r1.isScreenOn()
            if (r1 != 0) goto L_0x00b0
        L_0x00ae:
            r1 = 1
            goto L_0x00b1
        L_0x00b0:
            r1 = 0
        L_0x00b1:
            if (r1 != 0) goto L_0x00b5
            r0 = r0 | 4
        L_0x00b5:
            boolean r1 = r8.isStorageNotLowRequired()
            if (r1 == 0) goto L_0x00ce
            android.content.IntentFilter r1 = new android.content.IntentFilter
            java.lang.String r4 = "android.intent.action.DEVICE_STORAGE_LOW"
            r1.<init>(r4)
            android.content.Intent r9 = r9.registerReceiver(r5, r1)
            if (r9 != 0) goto L_0x00c9
            goto L_0x00ca
        L_0x00c9:
            r2 = 0
        L_0x00ca:
            if (r2 != 0) goto L_0x00ce
            r0 = r0 | 16
        L_0x00ce:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.scheduler.Requirements.getNotMetRequirements(android.content.Context):int");
    }

    public int getRequirements() {
        return this.c;
    }

    public int hashCode() {
        return this.c;
    }

    public boolean isChargingRequired() {
        return (this.c & 8) != 0;
    }

    public boolean isIdleRequired() {
        return (this.c & 4) != 0;
    }

    public boolean isNetworkRequired() {
        return (this.c & 1) != 0;
    }

    public boolean isStorageNotLowRequired() {
        return (this.c & 16) != 0;
    }

    public boolean isUnmeteredNetworkRequired() {
        return (this.c & 2) != 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.c);
    }
}
