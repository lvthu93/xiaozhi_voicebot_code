package com.google.android.exoplayer2.metadata;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class MetadataRenderer extends BaseRenderer implements Handler.Callback {
    public final MetadataDecoderFactory p;
    public final MetadataOutput q;
    @Nullable
    public final Handler r;
    public final MetadataInputBuffer s;
    @Nullable
    public MetadataDecoder t;
    public boolean u;
    public boolean v;
    public long w;
    public long x;
    @Nullable
    public Metadata y;

    public MetadataRenderer(MetadataOutput metadataOutput, @Nullable Looper looper) {
        this(metadataOutput, looper, MetadataDecoderFactory.a);
    }

    public final void c() {
        this.y = null;
        this.x = -9223372036854775807L;
        this.t = null;
    }

    public final void e(long j, boolean z) {
        this.y = null;
        this.x = -9223372036854775807L;
        this.u = false;
        this.v = false;
    }

    public String getName() {
        return "MetadataRenderer";
    }

    public boolean handleMessage(Message message) {
        if (message.what == 0) {
            this.q.onMetadata((Metadata) message.obj);
            return true;
        }
        throw new IllegalStateException();
    }

    public final void i(Format[] formatArr, long j, long j2) {
        this.t = this.p.createDecoder(formatArr[0]);
    }

    public boolean isEnded() {
        return this.v;
    }

    public boolean isReady() {
        return true;
    }

    public final void k(Metadata metadata, ArrayList arrayList) {
        for (int i = 0; i < metadata.length(); i++) {
            Format wrappedMetadataFormat = metadata.get(i).getWrappedMetadataFormat();
            if (wrappedMetadataFormat != null) {
                MetadataDecoderFactory metadataDecoderFactory = this.p;
                if (metadataDecoderFactory.supportsFormat(wrappedMetadataFormat)) {
                    MetadataDecoder createDecoder = metadataDecoderFactory.createDecoder(wrappedMetadataFormat);
                    byte[] bArr = (byte[]) Assertions.checkNotNull(metadata.get(i).getWrappedMetadataBytes());
                    MetadataInputBuffer metadataInputBuffer = this.s;
                    metadataInputBuffer.clear();
                    metadataInputBuffer.ensureSpaceForWrite(bArr.length);
                    ((ByteBuffer) Util.castNonNull(metadataInputBuffer.g)).put(bArr);
                    metadataInputBuffer.flip();
                    Metadata decode = createDecoder.decode(metadataInputBuffer);
                    if (decode != null) {
                        k(decode, arrayList);
                    }
                }
            }
            arrayList.add(metadata.get(i));
        }
    }

    public void render(long j, long j2) {
        boolean z = true;
        while (z) {
            if (!this.u && this.y == null) {
                MetadataInputBuffer metadataInputBuffer = this.s;
                metadataInputBuffer.clear();
                FormatHolder formatHolder = this.f;
                formatHolder.clear();
                int j3 = j(formatHolder, metadataInputBuffer, 0);
                if (j3 == -4) {
                    if (metadataInputBuffer.isEndOfStream()) {
                        this.u = true;
                    } else {
                        metadataInputBuffer.m = this.w;
                        metadataInputBuffer.flip();
                        Metadata decode = ((MetadataDecoder) Util.castNonNull(this.t)).decode(metadataInputBuffer);
                        if (decode != null) {
                            ArrayList arrayList = new ArrayList(decode.length());
                            k(decode, arrayList);
                            if (!arrayList.isEmpty()) {
                                this.y = new Metadata((List<? extends Metadata.Entry>) arrayList);
                                this.x = metadataInputBuffer.i;
                            }
                        }
                    }
                } else if (j3 == -5) {
                    this.w = ((Format) Assertions.checkNotNull(formatHolder.b)).t;
                }
            }
            Metadata metadata = this.y;
            if (metadata == null || this.x > j) {
                z = false;
            } else {
                Handler handler = this.r;
                if (handler != null) {
                    handler.obtainMessage(0, metadata).sendToTarget();
                } else {
                    this.q.onMetadata(metadata);
                }
                this.y = null;
                this.x = -9223372036854775807L;
                z = true;
            }
            if (this.u && this.y == null) {
                this.v = true;
            }
        }
    }

    public /* bridge */ /* synthetic */ void setPlaybackSpeed(float f, float f2) throws ExoPlaybackException {
        ga.a(this, f, f2);
    }

    public int supportsFormat(Format format) {
        int i;
        if (!this.p.supportsFormat(format)) {
            return ha.a(0);
        }
        if (format.ai == null) {
            i = 4;
        } else {
            i = 2;
        }
        return ha.a(i);
    }

    public MetadataRenderer(MetadataOutput metadataOutput, @Nullable Looper looper, MetadataDecoderFactory metadataDecoderFactory) {
        super(5);
        Handler handler;
        this.q = (MetadataOutput) Assertions.checkNotNull(metadataOutput);
        if (looper == null) {
            handler = null;
        } else {
            handler = Util.createHandler(looper, this);
        }
        this.r = handler;
        this.p = (MetadataDecoderFactory) Assertions.checkNotNull(metadataDecoderFactory);
        this.s = new MetadataInputBuffer();
        this.x = -9223372036854775807L;
    }
}
