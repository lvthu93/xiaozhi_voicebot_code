package com.google.android.exoplayer2.extractor;

import java.io.IOException;

public class ForwardingExtractorInput implements ExtractorInput {
    public final ExtractorInput a;

    public ForwardingExtractorInput(ExtractorInput extractorInput) {
        this.a = extractorInput;
    }

    public boolean advancePeekPosition(int i, boolean z) throws IOException {
        return this.a.advancePeekPosition(i, z);
    }

    public long getLength() {
        return this.a.getLength();
    }

    public long getPeekPosition() {
        return this.a.getPeekPosition();
    }

    public long getPosition() {
        return this.a.getPosition();
    }

    public int peek(byte[] bArr, int i, int i2) throws IOException {
        return this.a.peek(bArr, i, i2);
    }

    public boolean peekFully(byte[] bArr, int i, int i2, boolean z) throws IOException {
        return this.a.peekFully(bArr, i, i2, z);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.a.read(bArr, i, i2);
    }

    public boolean readFully(byte[] bArr, int i, int i2, boolean z) throws IOException {
        return this.a.readFully(bArr, i, i2, z);
    }

    public void resetPeekPosition() {
        this.a.resetPeekPosition();
    }

    public <E extends Throwable> void setRetryPosition(long j, E e) throws Throwable {
        this.a.setRetryPosition(j, e);
    }

    public int skip(int i) throws IOException {
        return this.a.skip(i);
    }

    public boolean skipFully(int i, boolean z) throws IOException {
        return this.a.skipFully(i, z);
    }

    public void advancePeekPosition(int i) throws IOException {
        this.a.advancePeekPosition(i);
    }

    public void peekFully(byte[] bArr, int i, int i2) throws IOException {
        this.a.peekFully(bArr, i, i2);
    }

    public void readFully(byte[] bArr, int i, int i2) throws IOException {
        this.a.readFully(bArr, i, i2);
    }

    public void skipFully(int i) throws IOException {
        this.a.skipFully(i);
    }
}
