package com.google.android.exoplayer2.decoder;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.OutputBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SimpleOutputBuffer extends OutputBuffer {
    public final OutputBuffer.Owner<SimpleOutputBuffer> h;
    @Nullable
    public ByteBuffer i;

    public SimpleOutputBuffer(OutputBuffer.Owner<SimpleOutputBuffer> owner) {
        this.h = owner;
    }

    public void clear() {
        super.clear();
        ByteBuffer byteBuffer = this.i;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
    }

    public ByteBuffer init(long j, int i2) {
        this.f = j;
        ByteBuffer byteBuffer = this.i;
        if (byteBuffer == null || byteBuffer.capacity() < i2) {
            this.i = ByteBuffer.allocateDirect(i2).order(ByteOrder.nativeOrder());
        }
        this.i.position(0);
        this.i.limit(i2);
        return this.i;
    }

    public void release() {
        this.h.releaseOutputBuffer(this);
    }
}
