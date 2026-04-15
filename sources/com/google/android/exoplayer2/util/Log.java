package com.google.android.exoplayer2.util;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import java.net.UnknownHostException;
import org.checkerframework.dataflow.qual.Pure;

public final class Log {
    public static int a = 0;
    public static boolean b = true;

    @Pure
    public static String a(String str, @Nullable Throwable th) {
        String throwableString = getThrowableString(th);
        if (TextUtils.isEmpty(throwableString)) {
            return str;
        }
        String valueOf = String.valueOf(str);
        String replace = throwableString.replace("\n", "\n  ");
        StringBuilder sb = new StringBuilder(y2.c(replace, valueOf.length() + 4));
        sb.append(valueOf);
        sb.append("\n  ");
        sb.append(replace);
        sb.append(10);
        return sb.toString();
    }

    @Pure
    public static void d(String str, String str2) {
        if (a == 0) {
            android.util.Log.d(str, str2);
        }
    }

    @Pure
    public static void e(String str, String str2) {
    }

    @Pure
    public static void e(String str, String str2, @Nullable Throwable th) {
        e(str, a(str2, th));
    }

    @Pure
    public static int getLogLevel() {
        return a;
    }

    @Nullable
    @Pure
    public static String getThrowableString(@Nullable Throwable th) {
        boolean z;
        if (th == null) {
            return null;
        }
        Throwable th2 = th;
        while (true) {
            if (th2 == null) {
                z = false;
                break;
            } else if (th2 instanceof UnknownHostException) {
                z = true;
                break;
            } else {
                th2 = th2.getCause();
            }
        }
        if (z) {
            return "UnknownHostException (no network)";
        }
        if (!b) {
            return th.getMessage();
        }
        return android.util.Log.getStackTraceString(th).trim().replace("\t", "    ");
    }

    @Pure
    public static void i(String str, String str2) {
    }

    @Pure
    public static void i(String str, String str2, @Nullable Throwable th) {
        i(str, a(str2, th));
    }

    public static void setLogLevel(int i) {
        a = i;
    }

    public static void setLogStackTraces(boolean z) {
        b = z;
    }

    @Pure
    public static void w(String str, String str2) {
    }

    @Pure
    public static void w(String str, String str2, @Nullable Throwable th) {
        w(str, a(str2, th));
    }

    @Pure
    public boolean getLogStackTraces() {
        return b;
    }

    @Pure
    public static void d(String str, String str2, @Nullable Throwable th) {
        d(str, a(str2, th));
    }
}
