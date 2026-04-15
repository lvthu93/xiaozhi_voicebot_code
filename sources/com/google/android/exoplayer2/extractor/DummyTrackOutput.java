package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.EOFException;
import java.io.IOException;

public final class DummyTrackOutput implements TrackOutput {
    public final byte[] a = new byte[4096];

    public void format(Format format) {
    }

    public /* bridge */ /* synthetic */ int sampleData(DataReader dataReader, int i, boolean z) throws IOException {
        return oc.a(this, dataReader, i, z);
    }

    public int sampleData(DataReader dataReader, int i, boolean z, int i2) throws IOException {
        byte[] bArr = this.a;
        int read = dataReader.read(bArr, 0, Math.min(bArr.length, i));
        if (read != -1) {
            return read;
        }
        if (z) {
            return -1;
        }
        throw new EOFException();
    }

    public /* bridge */ /* synthetic */ void sampleData(ParsableByteArray parsableByteArray, int i) {
        oc.b(this, parsableByteArray, i);
    }

    public void sampleMetadata(long j, int i, int i2, int i3, @Nullable TrackOutput.CryptoData cryptoData) {
    }

    public void sampleData(ParsableByteArray parsableByteArray, int i, int i2) {
        parsableByteArray.skipBytes(i);
    }
}
