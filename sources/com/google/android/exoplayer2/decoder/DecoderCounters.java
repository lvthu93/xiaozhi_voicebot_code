package com.google.android.exoplayer2.decoder;

public final class DecoderCounters {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public long g;
    public int h;

    public void addVideoFrameProcessingOffset(long j) {
        this.g += j;
        this.h++;
    }

    public synchronized void ensureUpdated() {
    }

    public void merge(DecoderCounters decoderCounters) {
        decoderCounters.getClass();
        this.a += decoderCounters.a;
        this.b += decoderCounters.b;
        this.c += decoderCounters.c;
        this.d += decoderCounters.d;
        this.e = Math.max(this.e, decoderCounters.e);
        this.f += decoderCounters.f;
        long j = decoderCounters.g;
        int i = decoderCounters.h;
        this.g += j;
        this.h += i;
    }
}
