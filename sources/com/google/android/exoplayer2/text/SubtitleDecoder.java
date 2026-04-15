package com.google.android.exoplayer2.text;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.Decoder;
import com.google.android.exoplayer2.decoder.DecoderException;

public interface SubtitleDecoder extends Decoder<SubtitleInputBuffer, SubtitleOutputBuffer, SubtitleDecoderException> {
    @Nullable
    /* synthetic */ Object dequeueInputBuffer() throws DecoderException;

    @Nullable
    /* synthetic */ Object dequeueOutputBuffer() throws DecoderException;

    /* synthetic */ void flush();

    /* synthetic */ String getName();

    /* synthetic */ void queueInputBuffer(Object obj) throws DecoderException;

    /* synthetic */ void release();

    void setPositionUs(long j);
}
