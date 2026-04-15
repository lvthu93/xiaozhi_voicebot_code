package com.google.android.exoplayer2;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ExoPlaybackException extends Exception implements Bundleable {
    public final int c;
    @Nullable
    public final String f;
    public final int g;
    @Nullable
    public final Format h;
    public final int i;
    public final long j;
    @Nullable
    public final MediaPeriodId k;
    public final boolean l;
    @Nullable
    public final Throwable m;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public ExoPlaybackException(int i2, Throwable th) {
        this(i2, th, (String) null, (String) null, -1, (Format) null, 4, false);
    }

    public static String b(int i2, @Nullable String str, @Nullable String str2, int i3, @Nullable Format format, int i4) {
        String str3;
        if (i2 == 0) {
            str3 = "Source error";
        } else if (i2 == 1) {
            String valueOf = String.valueOf(format);
            String formatSupportString = C.getFormatSupportString(i4);
            StringBuilder sb = new StringBuilder(y2.c(formatSupportString, valueOf.length() + y2.c(str2, 53)));
            sb.append(str2);
            sb.append(" error, index=");
            sb.append(i3);
            sb.append(", format=");
            sb.append(valueOf);
            sb.append(", format_supported=");
            sb.append(formatSupportString);
            str3 = sb.toString();
        } else if (i2 != 3) {
            str3 = "Unexpected runtime error";
        } else {
            str3 = "Remote error";
        }
        if (TextUtils.isEmpty(str)) {
            return str3;
        }
        String valueOf2 = String.valueOf(str3);
        StringBuilder sb2 = new StringBuilder(y2.c(str, valueOf2.length() + 2));
        sb2.append(valueOf2);
        sb2.append(": ");
        sb2.append(str);
        return sb2.toString();
    }

    public static String c(int i2) {
        return Integer.toString(i2, 36);
    }

    public static ExoPlaybackException createForRemote(String str) {
        return new ExoPlaybackException(3, (Throwable) null, str, (String) null, -1, (Format) null, 4, false);
    }

    public static ExoPlaybackException createForRenderer(Exception exc) {
        return new ExoPlaybackException(1, exc, (String) null, (String) null, -1, (Format) null, 4, false);
    }

    public static ExoPlaybackException createForSource(IOException iOException) {
        return new ExoPlaybackException(0, iOException);
    }

    public static ExoPlaybackException createForUnexpected(RuntimeException runtimeException) {
        return new ExoPlaybackException(2, runtimeException);
    }

    @CheckResult
    public final ExoPlaybackException a(@Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        return new ExoPlaybackException((String) Util.castNonNull(getMessage()), this.m, this.c, this.f, this.g, this.h, this.i, mediaPeriodId, this.j, this.l);
    }

    public Exception getRendererException() {
        boolean z = true;
        if (this.c != 1) {
            z = false;
        }
        Assertions.checkState(z);
        return (Exception) Assertions.checkNotNull(this.m);
    }

    public IOException getSourceException() {
        boolean z;
        if (this.c == 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        return (IOException) Assertions.checkNotNull(this.m);
    }

    public RuntimeException getUnexpectedException() {
        boolean z;
        if (this.c == 2) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        return (RuntimeException) Assertions.checkNotNull(this.m);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(c(0), getMessage());
        bundle.putInt(c(1), this.c);
        bundle.putString(c(2), this.f);
        bundle.putInt(c(3), this.g);
        bundle.putParcelable(c(4), this.h);
        bundle.putInt(c(5), this.i);
        bundle.putLong(c(6), this.j);
        bundle.putBoolean(c(7), this.l);
        Throwable th = this.m;
        if (th != null) {
            bundle.putString(c(8), th.getClass().getName());
            bundle.putString(c(9), th.getMessage());
        }
        return bundle;
    }

    public ExoPlaybackException(int i2, @Nullable Throwable th, @Nullable String str, @Nullable String str2, int i3, @Nullable Format format, int i4, boolean z) {
        this(b(i2, str, str2, i3, format, i4), th, i2, str2, i3, format, i4, (MediaSource.MediaPeriodId) null, SystemClock.elapsedRealtime(), z);
    }

    public static ExoPlaybackException createForRenderer(Throwable th, String str, int i2, @Nullable Format format, int i3) {
        return createForRenderer(th, str, i2, format, i3, false);
    }

    public static ExoPlaybackException createForRenderer(Throwable th, String str, int i2, @Nullable Format format, int i3, boolean z) {
        return new ExoPlaybackException(1, th, (String) null, str, i2, format, format == null ? 4 : i3, z);
    }

    public ExoPlaybackException(String str, @Nullable Throwable th, int i2, @Nullable String str2, int i3, @Nullable Format format, int i4, @Nullable MediaSource.MediaPeriodId mediaPeriodId, long j2, boolean z) {
        super(str, th);
        boolean z2 = true;
        if (z && i2 != 1) {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        this.c = i2;
        this.m = th;
        this.f = str2;
        this.g = i3;
        this.h = format;
        this.i = i4;
        this.k = mediaPeriodId;
        this.j = j2;
        this.l = z;
    }
}
