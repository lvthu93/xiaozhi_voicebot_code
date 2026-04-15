package com.google.android.exoplayer2.extractor.flac;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.FlacFrameReader;
import com.google.android.exoplayer2.extractor.FlacMetadataReader;
import com.google.android.exoplayer2.extractor.FlacSeekTableSeekMap;
import com.google.android.exoplayer2.extractor.FlacStreamMetadata;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class FlacExtractor implements Extractor {
    public final byte[] a;
    public final ParsableByteArray b;
    public final boolean c;
    public final FlacFrameReader.SampleNumberHolder d;
    public ExtractorOutput e;
    public TrackOutput f;
    public int g;
    @Nullable
    public Metadata h;
    public FlacStreamMetadata i;
    public int j;
    public int k;
    public c3 l;
    public int m;
    public long n;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public FlacExtractor() {
        this(0);
    }

    public final void a() {
        ((TrackOutput) Util.castNonNull(this.f)).sampleMetadata((this.n * 1000000) / ((long) ((FlacStreamMetadata) Util.castNonNull(this.i)).e), 1, this.m, 0, (TrackOutput.CryptoData) null);
    }

    public void init(ExtractorOutput extractorOutput) {
        this.e = extractorOutput;
        this.f = extractorOutput.track(0, 1);
        extractorOutput.endTracks();
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        SeekMap seekMap;
        long j2;
        boolean z;
        ExtractorInput extractorInput2 = extractorInput;
        int i2 = this.g;
        boolean z2 = true;
        if (i2 != 0) {
            byte[] bArr = this.a;
            if (i2 == 1) {
                extractorInput2.peekFully(bArr, 0, bArr.length);
                extractorInput.resetPeekPosition();
                this.g = 2;
                return 0;
            } else if (i2 == 2) {
                FlacMetadataReader.readStreamMarker(extractorInput);
                this.g = 3;
                return 0;
            } else if (i2 == 3) {
                FlacMetadataReader.FlacStreamMetadataHolder flacStreamMetadataHolder = new FlacMetadataReader.FlacStreamMetadataHolder(this.i);
                boolean z3 = false;
                while (!z3) {
                    z3 = FlacMetadataReader.readMetadataBlock(extractorInput2, flacStreamMetadataHolder);
                    this.i = (FlacStreamMetadata) Util.castNonNull(flacStreamMetadataHolder.a);
                }
                Assertions.checkNotNull(this.i);
                this.j = Math.max(this.i.c, 6);
                ((TrackOutput) Util.castNonNull(this.f)).format(this.i.getFormat(bArr, this.h));
                this.g = 4;
                return 0;
            } else if (i2 == 4) {
                this.k = FlacMetadataReader.getFrameStartMarker(extractorInput);
                ExtractorOutput extractorOutput = (ExtractorOutput) Util.castNonNull(this.e);
                long position = extractorInput.getPosition();
                long length = extractorInput.getLength();
                Assertions.checkNotNull(this.i);
                FlacStreamMetadata flacStreamMetadata = this.i;
                if (flacStreamMetadata.k != null) {
                    seekMap = new FlacSeekTableSeekMap(flacStreamMetadata, position);
                } else if (length == -1 || flacStreamMetadata.j <= 0) {
                    seekMap = new SeekMap.Unseekable(flacStreamMetadata.getDurationUs());
                } else {
                    c3 c3Var = new c3(flacStreamMetadata, this.k, position, length);
                    this.l = c3Var;
                    seekMap = c3Var.getSeekMap();
                }
                extractorOutput.seekMap(seekMap);
                this.g = 5;
                return 0;
            } else if (i2 == 5) {
                Assertions.checkNotNull(this.f);
                Assertions.checkNotNull(this.i);
                c3 c3Var2 = this.l;
                if (c3Var2 != null && c3Var2.isSeeking()) {
                    return this.l.handlePendingSeek(extractorInput2, positionHolder);
                }
                if (this.n == -1) {
                    this.n = FlacFrameReader.getFirstSampleNumber(extractorInput2, this.i);
                    return 0;
                }
                ParsableByteArray parsableByteArray = this.b;
                int limit = parsableByteArray.limit();
                if (limit < 32768) {
                    int read = extractorInput2.read(parsableByteArray.getData(), limit, 32768 - limit);
                    if (read != -1) {
                        z2 = false;
                    }
                    if (!z2) {
                        parsableByteArray.setLimit(limit + read);
                    } else if (parsableByteArray.bytesLeft() == 0) {
                        a();
                        return -1;
                    }
                } else {
                    z2 = false;
                }
                int position2 = parsableByteArray.getPosition();
                int i3 = this.m;
                int i4 = this.j;
                if (i3 < i4) {
                    parsableByteArray.skipBytes(Math.min(i4 - i3, parsableByteArray.bytesLeft()));
                }
                Assertions.checkNotNull(this.i);
                int position3 = parsableByteArray.getPosition();
                while (true) {
                    int limit2 = parsableByteArray.limit() - 16;
                    FlacFrameReader.SampleNumberHolder sampleNumberHolder = this.d;
                    if (position3 <= limit2) {
                        parsableByteArray.setPosition(position3);
                        if (FlacFrameReader.checkAndReadFrameHeader(parsableByteArray, this.i, this.k, sampleNumberHolder)) {
                            parsableByteArray.setPosition(position3);
                            j2 = sampleNumberHolder.a;
                            break;
                        }
                        position3++;
                    } else {
                        if (z2) {
                            while (position3 <= parsableByteArray.limit() - this.j) {
                                parsableByteArray.setPosition(position3);
                                try {
                                    z = FlacFrameReader.checkAndReadFrameHeader(parsableByteArray, this.i, this.k, sampleNumberHolder);
                                } catch (IndexOutOfBoundsException unused) {
                                    z = false;
                                }
                                if (parsableByteArray.getPosition() > parsableByteArray.limit()) {
                                    z = false;
                                }
                                if (z) {
                                    parsableByteArray.setPosition(position3);
                                    j2 = sampleNumberHolder.a;
                                    break;
                                }
                                position3++;
                            }
                            parsableByteArray.setPosition(parsableByteArray.limit());
                        } else {
                            parsableByteArray.setPosition(position3);
                        }
                        j2 = -1;
                    }
                }
                int position4 = parsableByteArray.getPosition() - position2;
                parsableByteArray.setPosition(position2);
                this.f.sampleData(parsableByteArray, position4);
                this.m += position4;
                if (j2 != -1) {
                    a();
                    this.m = 0;
                    this.n = j2;
                }
                if (parsableByteArray.bytesLeft() >= 16) {
                    return 0;
                }
                int bytesLeft = parsableByteArray.bytesLeft();
                System.arraycopy(parsableByteArray.getData(), parsableByteArray.getPosition(), parsableByteArray.getData(), 0, bytesLeft);
                parsableByteArray.setPosition(0);
                parsableByteArray.setLimit(bytesLeft);
                return 0;
            } else {
                throw new IllegalStateException();
            }
        } else {
            this.h = FlacMetadataReader.readId3Metadata(extractorInput2, !this.c);
            this.g = 1;
            return 0;
        }
    }

    public void release() {
    }

    public void seek(long j2, long j3) {
        long j4 = 0;
        if (j2 == 0) {
            this.g = 0;
        } else {
            c3 c3Var = this.l;
            if (c3Var != null) {
                c3Var.setSeekTargetUs(j3);
            }
        }
        if (j3 != 0) {
            j4 = -1;
        }
        this.n = j4;
        this.m = 0;
        this.b.reset(0);
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        FlacMetadataReader.peekId3Metadata(extractorInput, false);
        return FlacMetadataReader.checkAndPeekStreamMarker(extractorInput);
    }

    public FlacExtractor(int i2) {
        this.a = new byte[42];
        this.b = new ParsableByteArray(new byte[32768], 0);
        this.c = (i2 & 1) == 0 ? false : true;
        this.d = new FlacFrameReader.SampleNumberHolder();
        this.g = 0;
    }
}
