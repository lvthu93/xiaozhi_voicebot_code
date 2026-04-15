package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import androidx.core.view.InputDeviceCompat;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.IOException;

public final class PsExtractor implements Extractor {
    public final TimestampAdjuster a;
    public final SparseArray<a> b;
    public final ParsableByteArray c;
    public final v9 d;
    public boolean e;
    public boolean f;
    public boolean g;
    public long h;
    @Nullable
    public u9 i;
    public ExtractorOutput j;
    public boolean k;

    public static final class a {
        public final ElementaryStreamReader a;
        public final TimestampAdjuster b;
        public final ParsableBitArray c = new ParsableBitArray(new byte[64]);
        public boolean d;
        public boolean e;
        public boolean f;
        public long g;

        public a(ElementaryStreamReader elementaryStreamReader, TimestampAdjuster timestampAdjuster) {
            this.a = elementaryStreamReader;
            this.b = timestampAdjuster;
        }

        public void consume(ParsableByteArray parsableByteArray) throws ParserException {
            ParsableBitArray parsableBitArray = this.c;
            parsableByteArray.readBytes(parsableBitArray.a, 0, 3);
            parsableBitArray.setPosition(0);
            parsableBitArray.skipBits(8);
            this.d = parsableBitArray.readBit();
            this.e = parsableBitArray.readBit();
            parsableBitArray.skipBits(6);
            parsableByteArray.readBytes(parsableBitArray.a, 0, parsableBitArray.readBits(8));
            parsableBitArray.setPosition(0);
            this.g = 0;
            if (this.d) {
                parsableBitArray.skipBits(4);
                parsableBitArray.skipBits(1);
                parsableBitArray.skipBits(1);
                long readBits = (((long) parsableBitArray.readBits(3)) << 30) | ((long) (parsableBitArray.readBits(15) << 15)) | ((long) parsableBitArray.readBits(15));
                parsableBitArray.skipBits(1);
                boolean z = this.f;
                TimestampAdjuster timestampAdjuster = this.b;
                if (!z && this.e) {
                    parsableBitArray.skipBits(4);
                    parsableBitArray.skipBits(1);
                    parsableBitArray.skipBits(1);
                    long readBits2 = (long) parsableBitArray.readBits(15);
                    parsableBitArray.skipBits(1);
                    timestampAdjuster.adjustTsTimestamp(readBits2 | (((long) parsableBitArray.readBits(3)) << 30) | ((long) (parsableBitArray.readBits(15) << 15)));
                    this.f = true;
                }
                this.g = timestampAdjuster.adjustTsTimestamp(readBits);
            }
            long j = this.g;
            ElementaryStreamReader elementaryStreamReader = this.a;
            elementaryStreamReader.packetStarted(j, 4);
            elementaryStreamReader.consume(parsableByteArray);
            elementaryStreamReader.packetFinished();
        }

        public void seek() {
            this.f = false;
            this.a.seek();
        }
    }

    public PsExtractor() {
        this(new TimestampAdjuster(0));
    }

    public void init(ExtractorOutput extractorOutput) {
        this.j = extractorOutput;
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        boolean z;
        long j2;
        long j3;
        ElementaryStreamReader elementaryStreamReader;
        ExtractorInput extractorInput2 = extractorInput;
        PositionHolder positionHolder2 = positionHolder;
        Assertions.checkStateNotNull(this.j);
        long length = extractorInput.getLength();
        int i2 = (length > -1 ? 1 : (length == -1 ? 0 : -1));
        if (i2 != 0) {
            z = true;
        } else {
            z = false;
        }
        v9 v9Var = this.d;
        if (z && !v9Var.isDurationReadFinished()) {
            return v9Var.readDuration(extractorInput2, positionHolder2);
        }
        if (!this.k) {
            this.k = true;
            if (v9Var.getDurationUs() != -9223372036854775807L) {
                u9 u9Var = r4;
                u9 u9Var2 = new u9(v9Var.getScrTimestampAdjuster(), v9Var.getDurationUs(), length);
                this.i = u9Var;
                this.j.seekMap(u9Var.getSeekMap());
            } else {
                this.j.seekMap(new SeekMap.Unseekable(v9Var.getDurationUs()));
            }
        }
        u9 u9Var3 = this.i;
        if (u9Var3 != null && u9Var3.isSeeking()) {
            return this.i.handlePendingSeek(extractorInput2, positionHolder2);
        }
        extractorInput.resetPeekPosition();
        if (i2 != 0) {
            j2 = length - extractorInput.getPeekPosition();
        } else {
            j2 = -1;
        }
        if (j2 != -1 && j2 < 4) {
            return -1;
        }
        ParsableByteArray parsableByteArray = this.c;
        if (!extractorInput2.peekFully(parsableByteArray.getData(), 0, 4, true)) {
            return -1;
        }
        parsableByteArray.setPosition(0);
        int readInt = parsableByteArray.readInt();
        if (readInt == 441) {
            return -1;
        }
        if (readInt == 442) {
            extractorInput2.peekFully(parsableByteArray.getData(), 0, 10);
            parsableByteArray.setPosition(9);
            extractorInput2.skipFully((parsableByteArray.readUnsignedByte() & 7) + 14);
            return 0;
        } else if (readInt == 443) {
            extractorInput2.peekFully(parsableByteArray.getData(), 0, 2);
            parsableByteArray.setPosition(0);
            extractorInput2.skipFully(parsableByteArray.readUnsignedShort() + 6);
            return 0;
        } else if (((readInt & InputDeviceCompat.SOURCE_ANY) >> 8) != 1) {
            extractorInput2.skipFully(1);
            return 0;
        } else {
            int i3 = readInt & 255;
            SparseArray<a> sparseArray = this.b;
            a aVar = sparseArray.get(i3);
            if (!this.e) {
                if (aVar == null) {
                    if (i3 == 189) {
                        elementaryStreamReader = new Ac3Reader();
                        this.f = true;
                        this.h = extractorInput.getPosition();
                    } else if ((i3 & 224) == 192) {
                        elementaryStreamReader = new MpegAudioReader();
                        this.f = true;
                        this.h = extractorInput.getPosition();
                    } else if ((i3 & 240) == 224) {
                        elementaryStreamReader = new H262Reader();
                        this.g = true;
                        this.h = extractorInput.getPosition();
                    } else {
                        elementaryStreamReader = null;
                    }
                    if (elementaryStreamReader != null) {
                        elementaryStreamReader.createTracks(this.j, new TsPayloadReader.TrackIdGenerator(i3, 256));
                        aVar = new a(elementaryStreamReader, this.a);
                        sparseArray.put(i3, aVar);
                    }
                }
                if (!this.f || !this.g) {
                    j3 = 1048576;
                } else {
                    j3 = this.h + 8192;
                }
                if (extractorInput.getPosition() > j3) {
                    this.e = true;
                    this.j.endTracks();
                }
            }
            extractorInput2.peekFully(parsableByteArray.getData(), 0, 2);
            parsableByteArray.setPosition(0);
            int readUnsignedShort = parsableByteArray.readUnsignedShort() + 6;
            if (aVar == null) {
                extractorInput2.skipFully(readUnsignedShort);
            } else {
                parsableByteArray.reset(readUnsignedShort);
                extractorInput2.readFully(parsableByteArray.getData(), 0, readUnsignedShort);
                parsableByteArray.setPosition(6);
                aVar.consume(parsableByteArray);
                parsableByteArray.setLimit(parsableByteArray.capacity());
            }
            return 0;
        }
    }

    public void release() {
    }

    public void seek(long j2, long j3) {
        boolean z;
        TimestampAdjuster timestampAdjuster = this.a;
        int i2 = 0;
        if (timestampAdjuster.getTimestampOffsetUs() == -9223372036854775807L) {
            z = true;
        } else {
            z = false;
        }
        if (z || !(timestampAdjuster.getFirstSampleTimestampUs() == 0 || timestampAdjuster.getFirstSampleTimestampUs() == j3)) {
            timestampAdjuster.reset(j3);
        }
        u9 u9Var = this.i;
        if (u9Var != null) {
            u9Var.setSeekTargetUs(j3);
        }
        while (true) {
            SparseArray<a> sparseArray = this.b;
            if (i2 < sparseArray.size()) {
                sparseArray.valueAt(i2).seek();
                i2++;
            } else {
                return;
            }
        }
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        byte[] bArr = new byte[14];
        extractorInput.peekFully(bArr, 0, 14);
        if (442 != (((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8) | (bArr[3] & 255)) || (bArr[4] & 196) != 68 || (bArr[6] & 4) != 4 || (bArr[8] & 4) != 4 || (bArr[9] & 1) != 1 || (bArr[12] & 3) != 3) {
            return false;
        }
        extractorInput.advancePeekPosition(bArr[13] & 7);
        extractorInput.peekFully(bArr, 0, 3);
        if (1 == (((bArr[0] & 255) << 16) | ((bArr[1] & 255) << 8) | (bArr[2] & 255))) {
            return true;
        }
        return false;
    }

    public PsExtractor(TimestampAdjuster timestampAdjuster) {
        this.a = timestampAdjuster;
        this.c = new ParsableByteArray(4096);
        this.b = new SparseArray<>();
        this.d = new v9();
    }
}
