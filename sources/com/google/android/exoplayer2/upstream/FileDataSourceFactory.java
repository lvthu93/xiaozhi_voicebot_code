package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.FileDataSource;

@Deprecated
public final class FileDataSourceFactory implements DataSource.Factory {
    public final FileDataSource.Factory a;

    public FileDataSourceFactory() {
        this((TransferListener) null);
    }

    public FileDataSourceFactory(@Nullable TransferListener transferListener) {
        this.a = new FileDataSource.Factory().setListener(transferListener);
    }

    public FileDataSource createDataSource() {
        return this.a.createDataSource();
    }
}
