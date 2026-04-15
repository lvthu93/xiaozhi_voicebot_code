package com.google.android.exoplayer2.util;

import java.util.Arrays;

public final class LibraryLoader {
    public String[] a;
    public boolean b;
    public boolean c;

    public LibraryLoader(String... strArr) {
        this.a = strArr;
    }

    public synchronized boolean isAvailable() {
        String str;
        if (this.b) {
            return this.c;
        }
        this.b = true;
        try {
            for (String loadLibrary : this.a) {
                System.loadLibrary(loadLibrary);
            }
            this.c = true;
        } catch (UnsatisfiedLinkError unused) {
            String valueOf = String.valueOf(Arrays.toString(this.a));
            if (valueOf.length() != 0) {
                str = "Failed to load ".concat(valueOf);
            } else {
                str = new String("Failed to load ");
            }
            Log.w("LibraryLoader", str);
        }
        return this.c;
    }

    public synchronized void setLibraries(String... strArr) {
        boolean z;
        if (!this.b) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z, "Cannot set libraries after loading");
        this.a = strArr;
    }
}
