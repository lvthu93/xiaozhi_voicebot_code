package com.google.android.exoplayer2.video.spherical;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

public final class CameraMotionRenderer extends BaseRenderer {
    public final DecoderInputBuffer p = new DecoderInputBuffer(1);
    public final ParsableByteArray q = new ParsableByteArray();
    public long r;
    @Nullable
    public CameraMotionListener s;
    public long t;

    public CameraMotionRenderer() {
        super(6);
    }

    public final void c() {
        CameraMotionListener cameraMotionListener = this.s;
        if (cameraMotionListener != null) {
            cameraMotionListener.onCameraMotionReset();
        }
    }

    public final void e(long j, boolean z) {
        this.t = Long.MIN_VALUE;
        CameraMotionListener cameraMotionListener = this.s;
        if (cameraMotionListener != null) {
            cameraMotionListener.onCameraMotionReset();
        }
    }

    public String getName() {
        return "CameraMotionRenderer";
    }

    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        if (i == 7) {
            this.s = (CameraMotionListener) obj;
        } else {
            super.handleMessage(i, obj);
        }
    }

    public final void i(Format[] formatArr, long j, long j2) {
        this.r = j2;
    }

    public boolean isEnded() {
        return hasReadStreamToEnd();
    }

    public boolean isReady() {
        return true;
    }

    public void render(long j, long j2) {
        float[] fArr;
        while (!hasReadStreamToEnd() && this.t < 100000 + j) {
            DecoderInputBuffer decoderInputBuffer = this.p;
            decoderInputBuffer.clear();
            FormatHolder formatHolder = this.f;
            formatHolder.clear();
            if (j(formatHolder, decoderInputBuffer, 0) == -4 && !decoderInputBuffer.isEndOfStream()) {
                this.t = decoderInputBuffer.i;
                if (this.s != null && !decoderInputBuffer.isDecodeOnly()) {
                    decoderInputBuffer.flip();
                    ByteBuffer byteBuffer = (ByteBuffer) Util.castNonNull(decoderInputBuffer.g);
                    if (byteBuffer.remaining() != 16) {
                        fArr = null;
                    } else {
                        byte[] array = byteBuffer.array();
                        int limit = byteBuffer.limit();
                        ParsableByteArray parsableByteArray = this.q;
                        parsableByteArray.reset(array, limit);
                        parsableByteArray.setPosition(byteBuffer.arrayOffset() + 4);
                        float[] fArr2 = new float[3];
                        for (int i = 0; i < 3; i++) {
                            fArr2[i] = Float.intBitsToFloat(parsableByteArray.readLittleEndianInt());
                        }
                        fArr = fArr2;
                    }
                    if (fArr != null) {
                        ((CameraMotionListener) Util.castNonNull(this.s)).onCameraMotion(this.t - this.r, fArr);
                    }
                }
            } else {
                return;
            }
        }
    }

    public /* bridge */ /* synthetic */ void setPlaybackSpeed(float f, float f2) throws ExoPlaybackException {
        ga.a(this, f, f2);
    }

    public int supportsFormat(Format format) {
        if ("application/x-camera-motion".equals(format.p)) {
            return ha.a(4);
        }
        return ha.a(0);
    }
}
