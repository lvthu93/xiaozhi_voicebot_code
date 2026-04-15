package com.google.android.exoplayer2.text;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.OutputBuffer;
import com.google.android.exoplayer2.decoder.SimpleDecoder;
import com.google.android.exoplayer2.util.Assertions;
import java.nio.ByteBuffer;

public abstract class SimpleSubtitleDecoder extends SimpleDecoder<SubtitleInputBuffer, SubtitleOutputBuffer, SubtitleDecoderException> implements SubtitleDecoder {
    public final String n;

    public SimpleSubtitleDecoder(String str) {
        super(new SubtitleInputBuffer[2], new SubtitleOutputBuffer[2]);
        boolean z;
        this.n = str;
        int i = this.g;
        I[] iArr = this.e;
        if (i == iArr.length) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        for (I ensureSpaceForWrite : iArr) {
            ensureSpaceForWrite.ensureSpaceForWrite(1024);
        }
    }

    public final ya a() {
        return new ya(new i2(8, this));
    }

    public final SubtitleDecoderException b(Throwable th) {
        return new SubtitleDecoderException("Unexpected decode error", th);
    }

    @Nullable
    public final SubtitleDecoderException c(DecoderInputBuffer decoderInputBuffer, OutputBuffer outputBuffer, boolean z) {
        SubtitleInputBuffer subtitleInputBuffer = (SubtitleInputBuffer) decoderInputBuffer;
        SubtitleOutputBuffer subtitleOutputBuffer = (SubtitleOutputBuffer) outputBuffer;
        try {
            ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(subtitleInputBuffer.g);
            Subtitle e = e(byteBuffer.array(), byteBuffer.limit(), z);
            subtitleOutputBuffer.setContent(subtitleInputBuffer.i, e, subtitleInputBuffer.m);
            subtitleOutputBuffer.clearFlag(Integer.MIN_VALUE);
            return null;
        } catch (SubtitleDecoderException e2) {
            return e2;
        }
    }

    public abstract Subtitle e(byte[] bArr, int i, boolean z) throws SubtitleDecoderException;

    public final String getName() {
        return this.n;
    }

    public void setPositionUs(long j) {
    }
}
