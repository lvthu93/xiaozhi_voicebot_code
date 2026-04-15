package com.google.android.exoplayer2.decoder;

import androidx.annotation.Nullable;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public class DecoderInputBuffer extends Buffer {
    public final CryptoInfo f;
    @Nullable
    public ByteBuffer g;
    public boolean h;
    public long i;
    @Nullable
    public ByteBuffer j;
    public final int k;
    public final int l;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface BufferReplacementMode {
    }

    public static final class InsufficientCapacityException extends IllegalStateException {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public InsufficientCapacityException(int r3, int r4) {
            /*
                r2 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r1 = 44
                r0.<init>(r1)
                java.lang.String r1 = "Buffer too small ("
                r0.append(r1)
                r0.append(r3)
                java.lang.String r3 = " < "
                r0.append(r3)
                r0.append(r4)
                java.lang.String r3 = ")"
                r0.append(r3)
                java.lang.String r3 = r0.toString()
                r2.<init>(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.decoder.DecoderInputBuffer.InsufficientCapacityException.<init>(int, int):void");
        }
    }

    public DecoderInputBuffer(int i2) {
        this(i2, 0);
    }

    public static DecoderInputBuffer newNoDataInstance() {
        return new DecoderInputBuffer(0);
    }

    public final ByteBuffer b(int i2) {
        int i3;
        int i4 = this.k;
        if (i4 == 1) {
            return ByteBuffer.allocate(i2);
        }
        if (i4 == 2) {
            return ByteBuffer.allocateDirect(i2);
        }
        ByteBuffer byteBuffer = this.g;
        if (byteBuffer == null) {
            i3 = 0;
        } else {
            i3 = byteBuffer.capacity();
        }
        throw new InsufficientCapacityException(i3, i2);
    }

    public void clear() {
        super.clear();
        ByteBuffer byteBuffer = this.g;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
        ByteBuffer byteBuffer2 = this.j;
        if (byteBuffer2 != null) {
            byteBuffer2.clear();
        }
        this.h = false;
    }

    @EnsuresNonNull({"data"})
    public void ensureSpaceForWrite(int i2) {
        int i3 = i2 + this.l;
        ByteBuffer byteBuffer = this.g;
        if (byteBuffer == null) {
            this.g = b(i3);
            return;
        }
        int capacity = byteBuffer.capacity();
        int position = byteBuffer.position();
        int i4 = i3 + position;
        if (capacity >= i4) {
            this.g = byteBuffer;
            return;
        }
        ByteBuffer b = b(i4);
        b.order(byteBuffer.order());
        if (position > 0) {
            byteBuffer.flip();
            b.put(byteBuffer);
        }
        this.g = b;
    }

    public final void flip() {
        ByteBuffer byteBuffer = this.g;
        if (byteBuffer != null) {
            byteBuffer.flip();
        }
        ByteBuffer byteBuffer2 = this.j;
        if (byteBuffer2 != null) {
            byteBuffer2.flip();
        }
    }

    public final boolean isEncrypted() {
        return (this.c & 1073741824) == 1073741824;
    }

    @EnsuresNonNull({"supplementalData"})
    public void resetSupplementalData(int i2) {
        ByteBuffer byteBuffer = this.j;
        if (byteBuffer == null || byteBuffer.capacity() < i2) {
            this.j = ByteBuffer.allocate(i2);
        } else {
            this.j.clear();
        }
    }

    public DecoderInputBuffer(int i2, int i3) {
        this.f = new CryptoInfo();
        this.k = i2;
        this.l = i3;
    }
}
