package com.google.android.exoplayer2.offline;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import java.io.File;
import java.io.IOException;

public final class ActionFileUpgradeUtil {

    public interface DownloadIdProvider {
        String getId(DownloadRequest downloadRequest);
    }

    @WorkerThread
    public static void upgradeAndDelete(File file, @Nullable DownloadIdProvider downloadIdProvider, DefaultDownloadIndex defaultDownloadIndex, boolean z, boolean z2) throws IOException {
        int i;
        long j;
        int i2;
        Download download;
        int i3;
        DownloadIdProvider downloadIdProvider2 = downloadIdProvider;
        DefaultDownloadIndex defaultDownloadIndex2 = defaultDownloadIndex;
        a aVar = new a(file);
        if (aVar.exists()) {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                DownloadRequest[] load = aVar.load();
                int length = load.length;
                int i4 = 0;
                while (i4 < length) {
                    DownloadRequest downloadRequest = load[i4];
                    if (downloadIdProvider2 != null) {
                        downloadRequest = downloadRequest.copyWithId(downloadIdProvider2.getId(downloadRequest));
                    }
                    DownloadRequest downloadRequest2 = downloadRequest;
                    Download download2 = defaultDownloadIndex2.getDownload(downloadRequest2.c);
                    if (download2 != null) {
                        download = DownloadManager.a(download2, downloadRequest2, download2.f, currentTimeMillis);
                        i = i4;
                        i2 = length;
                        j = currentTimeMillis;
                    } else {
                        if (z2) {
                            i3 = 3;
                        } else {
                            i3 = 0;
                        }
                        i = i4;
                        i2 = length;
                        j = currentTimeMillis;
                        download = new Download(downloadRequest2, i3, currentTimeMillis, currentTimeMillis, -1, 0, 0);
                    }
                    defaultDownloadIndex2.putDownload(download);
                    i4 = i + 1;
                    length = i2;
                    currentTimeMillis = j;
                }
                aVar.delete();
            } catch (Throwable th) {
                if (z) {
                    aVar.delete();
                }
                throw th;
            }
        }
    }
}
