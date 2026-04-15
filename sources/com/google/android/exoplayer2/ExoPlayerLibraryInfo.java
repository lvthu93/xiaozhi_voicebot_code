package com.google.android.exoplayer2;

import android.os.Build;
import java.util.HashSet;

public final class ExoPlayerLibraryInfo {
    public static final HashSet<String> a = new HashSet<>();
    public static String b = "goog.exo.core";

    static {
        new StringBuilder(y2.c(Build.VERSION.RELEASE, 57));
    }

    public static synchronized void registerModule(String str) {
        synchronized (ExoPlayerLibraryInfo.class) {
            if (a.add(str)) {
                String str2 = b;
                StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 2 + String.valueOf(str).length());
                sb.append(str2);
                sb.append(", ");
                sb.append(str);
                b = sb.toString();
            }
        }
    }

    public static synchronized String registeredModules() {
        String str;
        synchronized (ExoPlayerLibraryInfo.class) {
            str = b;
        }
        return str;
    }
}
