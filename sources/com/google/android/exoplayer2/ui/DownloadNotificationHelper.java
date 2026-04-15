package com.google.android.exoplayer2.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;
import com.google.android.exoplayer2.offline.Download;
import info.dourok.voicebot.R;
import java.util.List;

public final class DownloadNotificationHelper {
    public final NotificationCompat.Builder a;

    public DownloadNotificationHelper(Context context, String str) {
        this.a = new NotificationCompat.Builder(context.getApplicationContext(), str);
    }

    public final Notification a(Context context, @DrawableRes int i, @Nullable PendingIntent pendingIntent, @Nullable String str, @StringRes int i2, int i3, int i4, boolean z, boolean z2, boolean z3) {
        String str2;
        NotificationCompat.Builder builder = this.a;
        builder.setSmallIcon(i);
        NotificationCompat.BigTextStyle bigTextStyle = null;
        if (i2 == 0) {
            str2 = null;
        } else {
            str2 = context.getResources().getString(i2);
        }
        builder.setContentTitle(str2);
        builder.setContentIntent(pendingIntent);
        if (str != null) {
            bigTextStyle = new NotificationCompat.BigTextStyle().bigText(str);
        }
        builder.setStyle(bigTextStyle);
        builder.setProgress(i3, i4, z);
        builder.setOngoing(z2);
        builder.setShowWhen(z3);
        return builder.build();
    }

    public Notification buildDownloadCompletedNotification(Context context, @DrawableRes int i, @Nullable PendingIntent pendingIntent, @Nullable String str) {
        return a(context, i, pendingIntent, str, R.string.exo_download_completed, 0, 0, false, false, true);
    }

    public Notification buildDownloadFailedNotification(Context context, @DrawableRes int i, @Nullable PendingIntent pendingIntent, @Nullable String str) {
        return a(context, i, pendingIntent, str, R.string.exo_download_failed, 0, 0, false, false, true);
    }

    public Notification buildProgressNotification(Context context, @DrawableRes int i, @Nullable PendingIntent pendingIntent, @Nullable String str, List<Download> list) {
        int i2;
        boolean z;
        int i3;
        boolean z2;
        float f = 0.0f;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        int i4 = 0;
        boolean z6 = true;
        boolean z7 = false;
        for (int i5 = 0; i5 < list.size(); i5++) {
            Download download = list.get(i5);
            int i6 = download.b;
            if (i6 == 5) {
                z5 = true;
            } else if (i6 == 7 || i6 == 2) {
                float percentDownloaded = download.getPercentDownloaded();
                if (percentDownloaded != -1.0f) {
                    f += percentDownloaded;
                    z6 = false;
                }
                if (download.getBytesDownloaded() > 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                z7 |= z2;
                i4++;
                z4 = true;
            }
        }
        if (z4) {
            i2 = R.string.exo_download_downloading;
        } else if (z5) {
            i2 = R.string.exo_download_removing;
        } else {
            i2 = 0;
        }
        if (z4) {
            int i7 = (int) (f / ((float) i4));
            if (z6 && z7) {
                z3 = true;
            }
            i3 = i7;
            z = z3;
        } else {
            i3 = 0;
            z = true;
        }
        return a(context, i, pendingIntent, str, i2, 100, i3, z, true, false);
    }
}
