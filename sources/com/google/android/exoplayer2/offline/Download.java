package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Download {
    public final DownloadRequest a;
    public final int b;
    public final long c;
    public final long d;
    public final long e;
    public final int f;
    public final int g;
    public final DownloadProgress h;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface FailureReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public Download(DownloadRequest downloadRequest, int i, long j, long j2, long j3, int i2, int i3) {
        this(downloadRequest, i, j, j2, j3, i2, i3, new DownloadProgress());
    }

    public long getBytesDownloaded() {
        return this.h.a;
    }

    public float getPercentDownloaded() {
        return this.h.b;
    }

    public boolean isTerminalState() {
        int i = this.b;
        return i == 3 || i == 4;
    }

    public Download(DownloadRequest downloadRequest, int i, long j, long j2, long j3, int i2, int i3, DownloadProgress downloadProgress) {
        Assertions.checkNotNull(downloadProgress);
        boolean z = true;
        Assertions.checkArgument((i3 == 0) == (i != 4));
        if (i2 != 0) {
            Assertions.checkArgument((i == 2 || i == 0) ? false : z);
        }
        this.a = downloadRequest;
        this.b = i;
        this.c = j;
        this.d = j2;
        this.e = j3;
        this.f = i2;
        this.g = i3;
        this.h = downloadProgress;
    }
}
