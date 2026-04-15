package com.google.android.exoplayer2.extractor.flv;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;

public abstract class TagPayloadReader {
    public final TrackOutput a;

    public static final class UnsupportedFormatException extends ParserException {
        public UnsupportedFormatException(String str) {
            super(str);
        }
    }

    public TagPayloadReader(TrackOutput trackOutput) {
        this.a = trackOutput;
    }

    public abstract boolean a(ParsableByteArray parsableByteArray) throws ParserException;

    public abstract boolean b(ParsableByteArray parsableByteArray, long j) throws ParserException;

    public final boolean consume(ParsableByteArray parsableByteArray, long j) throws ParserException {
        return a(parsableByteArray) && b(parsableByteArray, j);
    }

    public abstract void seek();
}
