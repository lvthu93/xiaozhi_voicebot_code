package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import java.io.IOException;

public interface LoadErrorHandlingPolicy {

    public static final class LoadErrorInfo {
        public final LoadEventInfo a;
        public final MediaLoadData b;
        public final IOException c;
        public final int d;

        public LoadErrorInfo(LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, int i) {
            this.a = loadEventInfo;
            this.b = mediaLoadData;
            this.c = iOException;
            this.d = i;
        }
    }

    @Deprecated
    long getBlacklistDurationMsFor(int i, long j, IOException iOException, int i2);

    long getBlacklistDurationMsFor(LoadErrorInfo loadErrorInfo);

    int getMinimumLoadableRetryCount(int i);

    @Deprecated
    long getRetryDelayMsFor(int i, long j, IOException iOException, int i2);

    long getRetryDelayMsFor(LoadErrorInfo loadErrorInfo);

    void onLoadTaskConcluded(long j);
}
