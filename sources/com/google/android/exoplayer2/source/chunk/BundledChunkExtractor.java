package com.google.android.exoplayer2.source.chunk;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

public final class BundledChunkExtractor implements ExtractorOutput, ChunkExtractor {
    public static final PositionHolder n = new PositionHolder();
    public final Extractor c;
    public final int f;
    public final Format g;
    public final SparseArray<a> h = new SparseArray<>();
    public boolean i;
    @Nullable
    public ChunkExtractor.TrackOutputProvider j;
    public long k;
    public SeekMap l;
    public Format[] m;

    public static final class a implements TrackOutput {
        public final int a;
        public final int b;
        @Nullable
        public final Format c;
        public final DummyTrackOutput d = new DummyTrackOutput();
        public Format e;
        public TrackOutput f;
        public long g;

        public a(int i, int i2, @Nullable Format format) {
            this.a = i;
            this.b = i2;
            this.c = format;
        }

        public void bind(@Nullable ChunkExtractor.TrackOutputProvider trackOutputProvider, long j) {
            if (trackOutputProvider == null) {
                this.f = this.d;
                return;
            }
            this.g = j;
            TrackOutput track = trackOutputProvider.track(this.a, this.b);
            this.f = track;
            Format format = this.e;
            if (format != null) {
                track.format(format);
            }
        }

        public void format(Format format) {
            Format format2 = this.c;
            if (format2 != null) {
                format = format.withManifestFormatInfo(format2);
            }
            this.e = format;
            ((TrackOutput) Util.castNonNull(this.f)).format(this.e);
        }

        public /* bridge */ /* synthetic */ int sampleData(DataReader dataReader, int i, boolean z) throws IOException {
            return oc.a(this, dataReader, i, z);
        }

        public int sampleData(DataReader dataReader, int i, boolean z, int i2) throws IOException {
            return ((TrackOutput) Util.castNonNull(this.f)).sampleData(dataReader, i, z);
        }

        public /* bridge */ /* synthetic */ void sampleData(ParsableByteArray parsableByteArray, int i) {
            oc.b(this, parsableByteArray, i);
        }

        public void sampleMetadata(long j, int i, int i2, int i3, @Nullable TrackOutput.CryptoData cryptoData) {
            long j2 = this.g;
            if (j2 != -9223372036854775807L && j >= j2) {
                this.f = this.d;
            }
            ((TrackOutput) Util.castNonNull(this.f)).sampleMetadata(j, i, i2, i3, cryptoData);
        }

        public void sampleData(ParsableByteArray parsableByteArray, int i, int i2) {
            ((TrackOutput) Util.castNonNull(this.f)).sampleData(parsableByteArray, i);
        }
    }

    public BundledChunkExtractor(Extractor extractor, int i2, Format format) {
        this.c = extractor;
        this.f = i2;
        this.g = format;
    }

    public void endTracks() {
        SparseArray<a> sparseArray = this.h;
        Format[] formatArr = new Format[sparseArray.size()];
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            formatArr[i2] = (Format) Assertions.checkStateNotNull(sparseArray.valueAt(i2).e);
        }
        this.m = formatArr;
    }

    @Nullable
    public ChunkIndex getChunkIndex() {
        SeekMap seekMap = this.l;
        if (seekMap instanceof ChunkIndex) {
            return (ChunkIndex) seekMap;
        }
        return null;
    }

    @Nullable
    public Format[] getSampleFormats() {
        return this.m;
    }

    public void init(@Nullable ChunkExtractor.TrackOutputProvider trackOutputProvider, long j2, long j3) {
        this.j = trackOutputProvider;
        this.k = j3;
        boolean z = this.i;
        Extractor extractor = this.c;
        if (!z) {
            extractor.init(this);
            if (j2 != -9223372036854775807L) {
                extractor.seek(0, j2);
            }
            this.i = true;
            return;
        }
        if (j2 == -9223372036854775807L) {
            j2 = 0;
        }
        extractor.seek(0, j2);
        int i2 = 0;
        while (true) {
            SparseArray<a> sparseArray = this.h;
            if (i2 < sparseArray.size()) {
                sparseArray.valueAt(i2).bind(trackOutputProvider, j3);
                i2++;
            } else {
                return;
            }
        }
    }

    public boolean read(ExtractorInput extractorInput) throws IOException {
        boolean z;
        int read = this.c.read(extractorInput, n);
        if (read != 1) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        if (read == 0) {
            return true;
        }
        return false;
    }

    public void release() {
        this.c.release();
    }

    public void seekMap(SeekMap seekMap) {
        this.l = seekMap;
    }

    public TrackOutput track(int i2, int i3) {
        boolean z;
        Format format;
        SparseArray<a> sparseArray = this.h;
        a aVar = sparseArray.get(i2);
        if (aVar == null) {
            if (this.m == null) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkState(z);
            if (i3 == this.f) {
                format = this.g;
            } else {
                format = null;
            }
            aVar = new a(i2, i3, format);
            aVar.bind(this.j, this.k);
            sparseArray.put(i2, aVar);
        }
        return aVar;
    }
}
