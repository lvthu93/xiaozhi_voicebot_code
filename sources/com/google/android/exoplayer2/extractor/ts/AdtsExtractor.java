package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.EOFException;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class AdtsExtractor implements Extractor {
    public final int a;
    public final AdtsReader b;
    public final ParsableByteArray c;
    public final ParsableByteArray d;
    public final ParsableBitArray e;
    public ExtractorOutput f;
    public long g;
    public long h;
    public int i;
    public boolean j;
    public boolean k;
    public boolean l;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public AdtsExtractor() {
        this(0);
    }

    public final int a(ExtractorInput extractorInput) throws IOException {
        int i2 = 0;
        while (true) {
            ParsableByteArray parsableByteArray = this.d;
            extractorInput.peekFully(parsableByteArray.getData(), 0, 10);
            parsableByteArray.setPosition(0);
            if (parsableByteArray.readUnsignedInt24() != 4801587) {
                break;
            }
            parsableByteArray.skipBytes(3);
            int readSynchSafeInt = parsableByteArray.readSynchSafeInt();
            i2 += readSynchSafeInt + 10;
            extractorInput.advancePeekPosition(readSynchSafeInt);
        }
        extractorInput.resetPeekPosition();
        extractorInput.advancePeekPosition(i2);
        if (this.h == -1) {
            this.h = (long) i2;
        }
        return i2;
    }

    public void init(ExtractorOutput extractorOutput) {
        this.f = extractorOutput;
        this.b.createTracks(extractorOutput, new TsPayloadReader.TrackIdGenerator(0, 1));
        extractorOutput.endTracks();
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        boolean z;
        boolean z2;
        boolean z3;
        ExtractorInput extractorInput2 = extractorInput;
        Assertions.checkStateNotNull(this.f);
        long length = extractorInput.getLength();
        int i2 = 0;
        if ((this.a & 1) == 0 || length == -1) {
            z = false;
        } else {
            z = true;
        }
        int i3 = 4;
        if (z) {
            ParsableBitArray parsableBitArray = this.e;
            ParsableByteArray parsableByteArray = this.d;
            if (!this.j) {
                this.i = -1;
                extractorInput.resetPeekPosition();
                long j2 = 0;
                if (extractorInput.getPosition() == 0) {
                    a(extractorInput);
                }
                int i4 = 0;
                while (true) {
                    try {
                        if (!extractorInput2.peekFully(parsableByteArray.getData(), i2, 2, true)) {
                            break;
                        }
                        parsableByteArray.setPosition(i2);
                        if (!AdtsReader.isAdtsSyncWord(parsableByteArray.readUnsignedShort())) {
                            i4 = 0;
                            break;
                        } else if (!extractorInput2.peekFully(parsableByteArray.getData(), i2, i3, true)) {
                            break;
                        } else {
                            parsableBitArray.setPosition(14);
                            int readBits = parsableBitArray.readBits(13);
                            if (readBits > 6) {
                                j2 += (long) readBits;
                                i4++;
                                if (i4 == 1000) {
                                    break;
                                } else if (!extractorInput2.advancePeekPosition(readBits - 6, true)) {
                                    break;
                                } else {
                                    i2 = 0;
                                    i3 = 4;
                                }
                            } else {
                                this.j = true;
                                throw new ParserException("Malformed ADTS stream");
                            }
                        }
                    } catch (EOFException unused) {
                    }
                }
                extractorInput.resetPeekPosition();
                if (i4 > 0) {
                    this.i = (int) (j2 / ((long) i4));
                } else {
                    this.i = -1;
                }
                this.j = true;
            }
        }
        ParsableByteArray parsableByteArray2 = this.c;
        int read = extractorInput2.read(parsableByteArray2.getData(), 0, 2048);
        if (read == -1) {
            z2 = true;
        } else {
            z2 = false;
        }
        boolean z4 = this.l;
        AdtsReader adtsReader = this.b;
        if (!z4) {
            if (!z || this.i <= 0) {
                z3 = false;
            } else {
                z3 = true;
            }
            if (!z3 || adtsReader.getSampleDurationUs() != -9223372036854775807L || z2) {
                if (!z3 || adtsReader.getSampleDurationUs() == -9223372036854775807L) {
                    this.f.seekMap(new SeekMap.Unseekable(-9223372036854775807L));
                } else {
                    long j3 = this.h;
                    this.f.seekMap(new ConstantBitrateSeekMap(length, j3, (int) ((((long) (this.i * 8)) * 1000000) / adtsReader.getSampleDurationUs()), this.i));
                }
                this.l = true;
            }
        }
        if (z2) {
            return -1;
        }
        parsableByteArray2.setPosition(0);
        parsableByteArray2.setLimit(read);
        if (!this.k) {
            adtsReader.packetStarted(this.g, 4);
            this.k = true;
        }
        adtsReader.consume(parsableByteArray2);
        return 0;
    }

    public void release() {
    }

    public void seek(long j2, long j3) {
        this.k = false;
        this.b.seek();
        this.g = j3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x001f, code lost:
        r10.resetPeekPosition();
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0028, code lost:
        if ((r3 - r0) < 8192) goto L_0x002b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x002a, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean sniff(com.google.android.exoplayer2.extractor.ExtractorInput r10) throws java.io.IOException {
        /*
            r9 = this;
            int r0 = r9.a(r10)
            r1 = 0
            r3 = r0
        L_0x0006:
            r2 = 0
            r4 = 0
        L_0x0008:
            com.google.android.exoplayer2.util.ParsableByteArray r5 = r9.d
            byte[] r6 = r5.getData()
            r7 = 2
            r10.peekFully(r6, r1, r7)
            r5.setPosition(r1)
            int r6 = r5.readUnsignedShort()
            boolean r6 = com.google.android.exoplayer2.extractor.ts.AdtsReader.isAdtsSyncWord(r6)
            if (r6 != 0) goto L_0x002f
            r10.resetPeekPosition()
            int r3 = r3 + 1
            int r2 = r3 - r0
            r4 = 8192(0x2000, float:1.14794E-41)
            if (r2 < r4) goto L_0x002b
            return r1
        L_0x002b:
            r10.advancePeekPosition(r3)
            goto L_0x0006
        L_0x002f:
            r6 = 1
            int r2 = r2 + r6
            r7 = 4
            if (r2 < r7) goto L_0x0039
            r8 = 188(0xbc, float:2.63E-43)
            if (r4 <= r8) goto L_0x0039
            return r6
        L_0x0039:
            byte[] r5 = r5.getData()
            r10.peekFully(r5, r1, r7)
            r5 = 14
            com.google.android.exoplayer2.util.ParsableBitArray r6 = r9.e
            r6.setPosition(r5)
            r5 = 13
            int r5 = r6.readBits(r5)
            r6 = 6
            if (r5 > r6) goto L_0x0051
            return r1
        L_0x0051:
            int r6 = r5 + -6
            r10.advancePeekPosition(r6)
            int r4 = r4 + r5
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.AdtsExtractor.sniff(com.google.android.exoplayer2.extractor.ExtractorInput):boolean");
    }

    public AdtsExtractor(int i2) {
        this.a = i2;
        this.b = new AdtsReader(true);
        this.c = new ParsableByteArray(2048);
        this.i = -1;
        this.h = -1;
        ParsableByteArray parsableByteArray = new ParsableByteArray(10);
        this.d = parsableByteArray;
        this.e = new ParsableBitArray(parsableByteArray.getData());
    }
}
