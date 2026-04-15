package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DefaultLoadErrorHandlingPolicy implements LoadErrorHandlingPolicy {
    public final int a;

    public DefaultLoadErrorHandlingPolicy() {
        this(-1);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ long getBlacklistDurationMsFor(int i, long j, IOException iOException, int i2) {
        return j4.a(this, i, j, iOException, i2);
    }

    public long getBlacklistDurationMsFor(LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo) {
        IOException iOException = loadErrorInfo.c;
        if (!(iOException instanceof HttpDataSource.InvalidResponseCodeException)) {
            return -9223372036854775807L;
        }
        int i = ((HttpDataSource.InvalidResponseCodeException) iOException).c;
        if (i == 403 || i == 404 || i == 410 || i == 416 || i == 500 || i == 503) {
            return 60000;
        }
        return -9223372036854775807L;
    }

    public int getMinimumLoadableRetryCount(int i) {
        int i2 = this.a;
        return i2 == -1 ? i == 7 ? 6 : 3 : i2;
    }

    @Deprecated
    public /* bridge */ /* synthetic */ long getRetryDelayMsFor(int i, long j, IOException iOException, int i2) {
        return j4.c(this, i, j, iOException, i2);
    }

    public long getRetryDelayMsFor(LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo) {
        IOException iOException = loadErrorInfo.c;
        if ((iOException instanceof ParserException) || (iOException instanceof FileNotFoundException) || (iOException instanceof HttpDataSource.CleartextNotPermittedException) || (iOException instanceof Loader.UnexpectedLoaderException)) {
            return -9223372036854775807L;
        }
        return (long) Math.min((loadErrorInfo.d - 1) * 1000, 5000);
    }

    public /* bridge */ /* synthetic */ void onLoadTaskConcluded(long j) {
        j4.e(this, j);
    }

    public DefaultLoadErrorHandlingPolicy(int i) {
        this.a = i;
    }
}
