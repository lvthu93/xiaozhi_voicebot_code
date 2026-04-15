package com.google.android.exoplayer2.decoder;

public abstract class OutputBuffer extends Buffer {
    public long f;
    public int g;

    public interface Owner<S extends OutputBuffer> {
        void releaseOutputBuffer(S s);
    }

    public abstract void release();
}
