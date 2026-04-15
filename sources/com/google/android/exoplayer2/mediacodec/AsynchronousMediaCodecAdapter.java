package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.common.base.Supplier;
import j$.util.Objects;
import java.nio.ByteBuffer;

@RequiresApi(23)
public final class AsynchronousMediaCodecAdapter implements MediaCodecAdapter {
    public final MediaCodec a;
    public final bh b;
    public final bg c;
    public final boolean d;
    public boolean e;
    public int f = 0;

    public static final class Factory implements MediaCodecAdapter.Factory {
        public final Supplier<HandlerThread> b;
        public final Supplier<HandlerThread> c;
        public final boolean d;
        public final boolean e;

        @VisibleForTesting
        public Factory() {
            throw null;
        }

        public Factory(int i) {
            this(i, false, false);
        }

        public Factory(int i, boolean z, boolean z2) {
            bf bfVar = new bf(i, 0);
            bf bfVar2 = new bf(i, 1);
            this.b = bfVar;
            this.c = bfVar2;
            this.d = z;
            this.e = z2;
        }

        /* JADX WARNING: Removed duplicated region for block: B:18:0x007b  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x0081  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter createAdapter(com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.Configuration r10) throws java.io.IOException {
            /*
                r9 = this;
                com.google.android.exoplayer2.mediacodec.MediaCodecInfo r0 = r10.a
                java.lang.String r0 = r0.a
                r1 = 0
                java.lang.String r2 = "createCodec:"
                java.lang.String r3 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x0077 }
                int r4 = r3.length()     // Catch:{ Exception -> 0x0077 }
                if (r4 == 0) goto L_0x0016
                java.lang.String r2 = r2.concat(r3)     // Catch:{ Exception -> 0x0077 }
                goto L_0x001c
            L_0x0016:
                java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x0077 }
                r3.<init>(r2)     // Catch:{ Exception -> 0x0077 }
                r2 = r3
            L_0x001c:
                com.google.android.exoplayer2.util.TraceUtil.beginSection(r2)     // Catch:{ Exception -> 0x0077 }
                android.media.MediaCodec r0 = android.media.MediaCodec.createByCodecName(r0)     // Catch:{ Exception -> 0x0077 }
                com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter r2 = new com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter     // Catch:{ Exception -> 0x0075 }
                com.google.common.base.Supplier<android.os.HandlerThread> r3 = r9.b     // Catch:{ Exception -> 0x0075 }
                java.lang.Object r3 = r3.get()     // Catch:{ Exception -> 0x0075 }
                r5 = r3
                android.os.HandlerThread r5 = (android.os.HandlerThread) r5     // Catch:{ Exception -> 0x0075 }
                com.google.common.base.Supplier<android.os.HandlerThread> r3 = r9.c     // Catch:{ Exception -> 0x0075 }
                java.lang.Object r3 = r3.get()     // Catch:{ Exception -> 0x0075 }
                r6 = r3
                android.os.HandlerThread r6 = (android.os.HandlerThread) r6     // Catch:{ Exception -> 0x0075 }
                boolean r7 = r9.d     // Catch:{ Exception -> 0x0075 }
                boolean r8 = r9.e     // Catch:{ Exception -> 0x0075 }
                r3 = r2
                r4 = r0
                r3.<init>(r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0075 }
                com.google.android.exoplayer2.util.TraceUtil.endSection()     // Catch:{ Exception -> 0x0072 }
                java.lang.String r1 = "configureCodec"
                com.google.android.exoplayer2.util.TraceUtil.beginSection(r1)     // Catch:{ Exception -> 0x0072 }
                android.media.MediaFormat r1 = r10.b     // Catch:{ Exception -> 0x0072 }
                android.view.Surface r3 = r10.c     // Catch:{ Exception -> 0x0072 }
                android.media.MediaCrypto r4 = r10.d     // Catch:{ Exception -> 0x0072 }
                int r10 = r10.e     // Catch:{ Exception -> 0x0072 }
                bh r5 = r2.b     // Catch:{ Exception -> 0x0072 }
                r5.initialize(r0)     // Catch:{ Exception -> 0x0072 }
                r0.configure(r1, r3, r4, r10)     // Catch:{ Exception -> 0x0072 }
                r10 = 1
                r2.f = r10     // Catch:{ Exception -> 0x0072 }
                com.google.android.exoplayer2.util.TraceUtil.endSection()     // Catch:{ Exception -> 0x0072 }
                java.lang.String r10 = "startCodec"
                com.google.android.exoplayer2.util.TraceUtil.beginSection(r10)     // Catch:{ Exception -> 0x0072 }
                bg r10 = r2.c     // Catch:{ Exception -> 0x0072 }
                r10.start()     // Catch:{ Exception -> 0x0072 }
                r0.start()     // Catch:{ Exception -> 0x0072 }
                r10 = 2
                r2.f = r10     // Catch:{ Exception -> 0x0072 }
                com.google.android.exoplayer2.util.TraceUtil.endSection()     // Catch:{ Exception -> 0x0072 }
                return r2
            L_0x0072:
                r10 = move-exception
                r1 = r2
                goto L_0x0079
            L_0x0075:
                r10 = move-exception
                goto L_0x0079
            L_0x0077:
                r10 = move-exception
                r0 = r1
            L_0x0079:
                if (r1 != 0) goto L_0x0081
                if (r0 == 0) goto L_0x0084
                r0.release()
                goto L_0x0084
            L_0x0081:
                r1.release()
            L_0x0084:
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter.Factory.createAdapter(com.google.android.exoplayer2.mediacodec.MediaCodecAdapter$Configuration):com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter");
        }
    }

    public AsynchronousMediaCodecAdapter(MediaCodec mediaCodec, HandlerThread handlerThread, HandlerThread handlerThread2, boolean z, boolean z2) {
        this.a = mediaCodec;
        this.b = new bh(handlerThread);
        this.c = new bg(mediaCodec, handlerThread2, z);
        this.d = z2;
    }

    public static String a(int i, String str) {
        StringBuilder sb = new StringBuilder(str);
        if (i == 1) {
            sb.append("Audio");
        } else if (i == 2) {
            sb.append("Video");
        } else {
            sb.append("Unknown(");
            sb.append(i);
            sb.append(")");
        }
        return sb.toString();
    }

    public final void b() {
        if (this.d) {
            try {
                this.c.waitUntilQueueingComplete();
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e2);
            }
        }
    }

    public int dequeueInputBufferIndex() {
        return this.b.dequeueInputBufferIndex();
    }

    public int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo) {
        return this.b.dequeueOutputBufferIndex(bufferInfo);
    }

    public void flush() {
        this.c.flush();
        MediaCodec mediaCodec = this.a;
        mediaCodec.flush();
        Objects.requireNonNull(mediaCodec);
        this.b.flushAsync(new qb(3, mediaCodec));
    }

    @Nullable
    public ByteBuffer getInputBuffer(int i) {
        return this.a.getInputBuffer(i);
    }

    @Nullable
    public ByteBuffer getOutputBuffer(int i) {
        return this.a.getOutputBuffer(i);
    }

    public MediaFormat getOutputFormat() {
        return this.b.getOutputFormat();
    }

    public void queueInputBuffer(int i, int i2, int i3, long j, int i4) {
        this.c.queueInputBuffer(i, i2, i3, j, i4);
    }

    public void queueSecureInputBuffer(int i, int i2, CryptoInfo cryptoInfo, long j, int i3) {
        this.c.queueSecureInputBuffer(i, i2, cryptoInfo, j, i3);
    }

    public void release() {
        MediaCodec mediaCodec = this.a;
        try {
            if (this.f == 2) {
                this.c.shutdown();
            }
            int i = this.f;
            if (i == 1 || i == 2) {
                this.b.shutdown();
            }
            this.f = 3;
        } finally {
            if (!this.e) {
                mediaCodec.release();
                this.e = true;
            }
        }
    }

    public void releaseOutputBuffer(int i, boolean z) {
        this.a.releaseOutputBuffer(i, z);
    }

    public void setOnFrameRenderedListener(MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, Handler handler) {
        b();
        this.a.setOnFrameRenderedListener(new be(this, onFrameRenderedListener, 0), handler);
    }

    public void setOutputSurface(Surface surface) {
        b();
        this.a.setOutputSurface(surface);
    }

    public void setParameters(Bundle bundle) {
        b();
        this.a.setParameters(bundle);
    }

    public void setVideoScalingMode(int i) {
        b();
        this.a.setVideoScalingMode(i);
    }

    public void releaseOutputBuffer(int i, long j) {
        this.a.releaseOutputBuffer(i, j);
    }
}
