package com.google.android.exoplayer2.video;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.ActivityChooserView;
import com.google.android.exoplayer2.decoder.OutputBuffer;
import java.nio.ByteBuffer;

public class VideoDecoderOutputBuffer extends OutputBuffer {
    @Nullable
    public ByteBuffer h;
    public int i;
    public int j;
    @Nullable
    public ByteBuffer[] k;
    @Nullable
    public int[] l;
    public int m;
    @Nullable
    public ByteBuffer n;
    public final OutputBuffer.Owner<VideoDecoderOutputBuffer> o;

    public VideoDecoderOutputBuffer(OutputBuffer.Owner<VideoDecoderOutputBuffer> owner) {
        this.o = owner;
    }

    public void init(long j2, int i2, @Nullable ByteBuffer byteBuffer) {
        this.f = j2;
        if (byteBuffer == null || !byteBuffer.hasRemaining()) {
            this.n = null;
            return;
        }
        addFlag(268435456);
        int limit = byteBuffer.limit();
        ByteBuffer byteBuffer2 = this.n;
        if (byteBuffer2 == null || byteBuffer2.capacity() < limit) {
            this.n = ByteBuffer.allocate(limit);
        } else {
            this.n.clear();
        }
        this.n.put(byteBuffer);
        this.n.flip();
        byteBuffer.position(0);
    }

    public void initForPrivateFrame(int i2, int i3) {
        this.i = i2;
        this.j = i3;
    }

    public boolean initForYuvFrame(int i2, int i3, int i4, int i5, int i6) {
        boolean z;
        boolean z2;
        boolean z3;
        this.i = i2;
        this.j = i3;
        this.m = i6;
        int i7 = (int) ((((long) i3) + 1) / 2);
        if (i4 < 0 || i3 < 0 || (i3 > 0 && i4 >= ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED / i3)) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            if (i5 < 0 || i7 < 0 || (i7 > 0 && i5 >= ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED / i7)) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                int i8 = i3 * i4;
                int i9 = i7 * i5;
                int i10 = (i9 * 2) + i8;
                if (i9 < 0 || i9 >= 1073741823) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                if (z3 && i10 >= i8) {
                    ByteBuffer byteBuffer = this.h;
                    if (byteBuffer == null || byteBuffer.capacity() < i10) {
                        this.h = ByteBuffer.allocateDirect(i10);
                    } else {
                        this.h.position(0);
                        this.h.limit(i10);
                    }
                    if (this.k == null) {
                        this.k = new ByteBuffer[3];
                    }
                    ByteBuffer byteBuffer2 = this.h;
                    ByteBuffer[] byteBufferArr = this.k;
                    ByteBuffer slice = byteBuffer2.slice();
                    byteBufferArr[0] = slice;
                    slice.limit(i8);
                    byteBuffer2.position(i8);
                    ByteBuffer slice2 = byteBuffer2.slice();
                    byteBufferArr[1] = slice2;
                    slice2.limit(i9);
                    byteBuffer2.position(i8 + i9);
                    ByteBuffer slice3 = byteBuffer2.slice();
                    byteBufferArr[2] = slice3;
                    slice3.limit(i9);
                    if (this.l == null) {
                        this.l = new int[3];
                    }
                    int[] iArr = this.l;
                    iArr[0] = i4;
                    iArr[1] = i5;
                    iArr[2] = i5;
                    return true;
                }
            }
        }
        return false;
    }

    public void release() {
        this.o.releaseOutputBuffer(this);
    }
}
