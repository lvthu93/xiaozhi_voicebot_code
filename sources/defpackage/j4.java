package defpackage;

import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import java.io.IOException;

/* renamed from: j4  reason: default package */
public final /* synthetic */ class j4 {
    @Deprecated
    public static long a(LoadErrorHandlingPolicy loadErrorHandlingPolicy, int i, long j, IOException iOException, int i2) {
        throw new UnsupportedOperationException();
    }

    public static long b(LoadErrorHandlingPolicy loadErrorHandlingPolicy, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo) {
        return loadErrorHandlingPolicy.getBlacklistDurationMsFor(loadErrorInfo.b.a, loadErrorInfo.a.b, loadErrorInfo.c, loadErrorInfo.d);
    }

    @Deprecated
    public static long c(LoadErrorHandlingPolicy loadErrorHandlingPolicy, int i, long j, IOException iOException, int i2) {
        throw new UnsupportedOperationException();
    }

    public static long d(LoadErrorHandlingPolicy loadErrorHandlingPolicy, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo) {
        return loadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo.b.a, loadErrorInfo.a.b, loadErrorInfo.c, loadErrorInfo.d);
    }

    public static void e(LoadErrorHandlingPolicy loadErrorHandlingPolicy, long j) {
    }
}
