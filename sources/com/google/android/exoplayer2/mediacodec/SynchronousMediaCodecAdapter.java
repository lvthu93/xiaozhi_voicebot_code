package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Surface;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.nio.ByteBuffer;

public class SynchronousMediaCodecAdapter implements MediaCodecAdapter {
    public final MediaCodec a;
    @Nullable
    public ByteBuffer[] b;
    @Nullable
    public ByteBuffer[] c;

    public static class Factory implements MediaCodecAdapter.Factory {
        public static MediaCodec a(MediaCodecAdapter.Configuration configuration) throws IOException {
            String str;
            Assertions.checkNotNull(configuration.a);
            String str2 = configuration.a.a;
            String valueOf = String.valueOf(str2);
            if (valueOf.length() != 0) {
                str = "createCodec:".concat(valueOf);
            } else {
                str = new String("createCodec:");
            }
            TraceUtil.beginSection(str);
            MediaCodec createByCodecName = MediaCodec.createByCodecName(str2);
            TraceUtil.endSection();
            return createByCodecName;
        }

        public MediaCodecAdapter createAdapter(MediaCodecAdapter.Configuration configuration) throws IOException {
            MediaCodec mediaCodec = null;
            try {
                mediaCodec = a(configuration);
                TraceUtil.beginSection("configureCodec");
                mediaCodec.configure(configuration.b, configuration.c, configuration.d, configuration.e);
                TraceUtil.endSection();
                TraceUtil.beginSection("startCodec");
                mediaCodec.start();
                TraceUtil.endSection();
                return new SynchronousMediaCodecAdapter(mediaCodec);
            } catch (IOException | RuntimeException e) {
                if (mediaCodec != null) {
                    mediaCodec.release();
                }
                throw e;
            }
        }
    }

    public SynchronousMediaCodecAdapter(MediaCodec mediaCodec) {
        this.a = mediaCodec;
        if (Util.a < 21) {
            this.b = mediaCodec.getInputBuffers();
            this.c = mediaCodec.getOutputBuffers();
        }
    }

    public int dequeueInputBufferIndex() {
        return this.a.dequeueInputBuffer(0);
    }

    public int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo) {
        int dequeueOutputBuffer;
        do {
            MediaCodec mediaCodec = this.a;
            dequeueOutputBuffer = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
            if (dequeueOutputBuffer == -3 && Util.a < 21) {
                this.c = mediaCodec.getOutputBuffers();
                continue;
            }
        } while (dequeueOutputBuffer == -3);
        return dequeueOutputBuffer;
    }

    public void flush() {
        this.a.flush();
    }

    @Nullable
    public ByteBuffer getInputBuffer(int i) {
        if (Util.a >= 21) {
            return this.a.getInputBuffer(i);
        }
        return ((ByteBuffer[]) Util.castNonNull(this.b))[i];
    }

    @Nullable
    public ByteBuffer getOutputBuffer(int i) {
        if (Util.a >= 21) {
            return this.a.getOutputBuffer(i);
        }
        return ((ByteBuffer[]) Util.castNonNull(this.c))[i];
    }

    public MediaFormat getOutputFormat() {
        return this.a.getOutputFormat();
    }

    public void queueInputBuffer(int i, int i2, int i3, long j, int i4) {
        this.a.queueInputBuffer(i, i2, i3, j, i4);
    }

    public void queueSecureInputBuffer(int i, int i2, CryptoInfo cryptoInfo, long j, int i3) {
        this.a.queueSecureInputBuffer(i, i2, cryptoInfo.getFrameworkCryptoInfo(), j, i3);
    }

    public void release() {
        this.b = null;
        this.c = null;
        this.a.release();
    }

    public void releaseOutputBuffer(int i, boolean z) {
        this.a.releaseOutputBuffer(i, z);
    }

    @RequiresApi(23)
    public void setOnFrameRenderedListener(MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, Handler handler) {
        this.a.setOnFrameRenderedListener(new be(this, onFrameRenderedListener, 1), handler);
    }

    @RequiresApi(23)
    public void setOutputSurface(Surface surface) {
        this.a.setOutputSurface(surface);
    }

    @RequiresApi(19)
    public void setParameters(Bundle bundle) {
        this.a.setParameters(bundle);
    }

    public void setVideoScalingMode(int i) {
        this.a.setVideoScalingMode(i);
    }

    @RequiresApi(21)
    public void releaseOutputBuffer(int i, long j) {
        this.a.releaseOutputBuffer(i, j);
    }
}
