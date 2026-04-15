package com.google.android.exoplayer2.extractor.jpeg;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.MotionPhotoMetadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

public final class JpegExtractor implements Extractor {
    public final ParsableByteArray a = new ParsableByteArray(6);
    public ExtractorOutput b;
    public int c;
    public int d;
    public int e;
    public long f = -1;
    @Nullable
    public MotionPhotoMetadata g;
    public ExtractorInput h;
    public nb i;
    @Nullable
    public Mp4Extractor j;

    public final void a() {
        b(new Metadata.Entry[0]);
        ((ExtractorOutput) Assertions.checkNotNull(this.b)).endTracks();
        this.b.seekMap(new SeekMap.Unseekable(-9223372036854775807L));
        this.c = 6;
    }

    public final void b(Metadata.Entry... entryArr) {
        ((ExtractorOutput) Assertions.checkNotNull(this.b)).track(1024, 4).format(new Format.Builder().setMetadata(new Metadata(entryArr)).build());
    }

    public final int c(ExtractorInput extractorInput) throws IOException {
        ParsableByteArray parsableByteArray = this.a;
        parsableByteArray.reset(2);
        extractorInput.peekFully(parsableByteArray.getData(), 0, 2);
        return parsableByteArray.readUnsignedShort();
    }

    public void init(ExtractorOutput extractorOutput) {
        this.b = extractorOutput;
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        String readNullTerminatedString;
        MotionPhotoMetadata motionPhotoMetadata;
        MotionPhotoDescription parse;
        int i2 = this.c;
        ParsableByteArray parsableByteArray = this.a;
        if (i2 == 0) {
            parsableByteArray.reset(2);
            extractorInput.readFully(parsableByteArray.getData(), 0, 2);
            int readUnsignedShort = parsableByteArray.readUnsignedShort();
            this.d = readUnsignedShort;
            if (readUnsignedShort == 65498) {
                if (this.f != -1) {
                    this.c = 4;
                } else {
                    a();
                }
            } else if ((readUnsignedShort < 65488 || readUnsignedShort > 65497) && readUnsignedShort != 65281) {
                this.c = 1;
            }
            return 0;
        } else if (i2 == 1) {
            parsableByteArray.reset(2);
            extractorInput.readFully(parsableByteArray.getData(), 0, 2);
            this.e = parsableByteArray.readUnsignedShort() - 2;
            this.c = 2;
            return 0;
        } else if (i2 == 2) {
            if (this.d == 65505) {
                ParsableByteArray parsableByteArray2 = new ParsableByteArray(this.e);
                extractorInput.readFully(parsableByteArray2.getData(), 0, this.e);
                if (this.g == null && "http://ns.adobe.com/xap/1.0/".equals(parsableByteArray2.readNullTerminatedString()) && (readNullTerminatedString = parsableByteArray2.readNullTerminatedString()) != null) {
                    long length = extractorInput.getLength();
                    if (length == -1 || (parse = a.parse(readNullTerminatedString)) == null) {
                        motionPhotoMetadata = null;
                    } else {
                        motionPhotoMetadata = parse.getMotionPhotoMetadata(length);
                    }
                    this.g = motionPhotoMetadata;
                    if (motionPhotoMetadata != null) {
                        this.f = motionPhotoMetadata.h;
                    }
                }
            } else {
                extractorInput.skipFully(this.e);
            }
            this.c = 0;
            return 0;
        } else if (i2 == 4) {
            long position = extractorInput.getPosition();
            long j2 = this.f;
            if (position != j2) {
                positionHolder.a = j2;
                return 1;
            }
            if (!extractorInput.peekFully(parsableByteArray.getData(), 0, 1, true)) {
                a();
            } else {
                extractorInput.resetPeekPosition();
                if (this.j == null) {
                    this.j = new Mp4Extractor();
                }
                nb nbVar = new nb(extractorInput, this.f);
                this.i = nbVar;
                if (this.j.sniff(nbVar)) {
                    this.j.init(new StartOffsetExtractorOutput(this.f, (ExtractorOutput) Assertions.checkNotNull(this.b)));
                    b((Metadata.Entry) Assertions.checkNotNull(this.g));
                    this.c = 5;
                } else {
                    a();
                }
            }
            return 0;
        } else if (i2 == 5) {
            if (this.i == null || extractorInput != this.h) {
                this.h = extractorInput;
                this.i = new nb(extractorInput, this.f);
            }
            int read = ((Mp4Extractor) Assertions.checkNotNull(this.j)).read(this.i, positionHolder);
            if (read == 1) {
                positionHolder.a += this.f;
            }
            return read;
        } else if (i2 == 6) {
            return -1;
        } else {
            throw new IllegalStateException();
        }
    }

    public void release() {
        Mp4Extractor mp4Extractor = this.j;
        if (mp4Extractor != null) {
            mp4Extractor.release();
        }
    }

    public void seek(long j2, long j3) {
        if (j2 == 0) {
            this.c = 0;
            this.j = null;
        } else if (this.c == 5) {
            ((Mp4Extractor) Assertions.checkNotNull(this.j)).seek(j2, j3);
        }
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        if (c(extractorInput) != 65496) {
            return false;
        }
        int c2 = c(extractorInput);
        this.d = c2;
        ParsableByteArray parsableByteArray = this.a;
        if (c2 == 65504) {
            parsableByteArray.reset(2);
            extractorInput.peekFully(parsableByteArray.getData(), 0, 2);
            extractorInput.advancePeekPosition(parsableByteArray.readUnsignedShort() - 2);
            this.d = c(extractorInput);
        }
        if (this.d != 65505) {
            return false;
        }
        extractorInput.advancePeekPosition(2);
        parsableByteArray.reset(6);
        extractorInput.peekFully(parsableByteArray.getData(), 0, 6);
        if (parsableByteArray.readUnsignedInt() == 1165519206 && parsableByteArray.readUnsignedShort() == 0) {
            return true;
        }
        return false;
    }
}
