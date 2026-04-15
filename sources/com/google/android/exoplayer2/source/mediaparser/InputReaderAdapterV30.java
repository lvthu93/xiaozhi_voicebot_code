package com.google.android.exoplayer2.source.mediaparser;

import android.annotation.SuppressLint;
import android.media.MediaParser;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

@RequiresApi(30)
@SuppressLint({"Override"})
public final class InputReaderAdapterV30 implements MediaParser.SeekableInputReader {
    @Nullable
    public DataReader a;
    public long b;
    public long c;
    public long d;

    public long getAndResetSeekPosition() {
        long j = this.d;
        this.d = -1;
        return j;
    }

    public long getLength() {
        return this.b;
    }

    public long getPosition() {
        return this.c;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = ((DataReader) Util.castNonNull(this.a)).read(bArr, i, i2);
        this.c += (long) read;
        return read;
    }

    public void seekToPosition(long j) {
        this.d = j;
    }

    public void setCurrentPosition(long j) {
        this.c = j;
    }

    public void setDataReader(DataReader dataReader, long j) {
        this.a = dataReader;
        this.b = j;
        this.d = -1;
    }
}
