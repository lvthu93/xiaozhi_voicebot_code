package com.google.android.exoplayer2.offline;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import java.io.IOException;

@WorkerThread
public interface WritableDownloadIndex extends DownloadIndex {
    @Nullable
    /* synthetic */ Download getDownload(String str) throws IOException;

    /* synthetic */ DownloadCursor getDownloads(int... iArr) throws IOException;

    void putDownload(Download download) throws IOException;

    void removeDownload(String str) throws IOException;

    void setDownloadingStatesToQueued() throws IOException;

    void setStatesToRemoving() throws IOException;

    void setStopReason(int i) throws IOException;

    void setStopReason(String str, int i) throws IOException;
}
