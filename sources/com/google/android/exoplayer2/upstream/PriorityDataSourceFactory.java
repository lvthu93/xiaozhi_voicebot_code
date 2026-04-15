package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.PriorityTaskManager;

public final class PriorityDataSourceFactory implements DataSource.Factory {
    public final DataSource.Factory a;
    public final PriorityTaskManager b;
    public final int c;

    public PriorityDataSourceFactory(DataSource.Factory factory, PriorityTaskManager priorityTaskManager, int i) {
        this.a = factory;
        this.b = priorityTaskManager;
        this.c = i;
    }

    public PriorityDataSource createDataSource() {
        return new PriorityDataSource(this.a.createDataSource(), this.b, this.c);
    }
}
