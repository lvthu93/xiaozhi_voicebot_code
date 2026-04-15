package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;

public final class PesReader implements TsPayloadReader {
    public final ElementaryStreamReader a;
    public final ParsableBitArray b = new ParsableBitArray(new byte[10]);
    public int c = 0;
    public int d;
    public TimestampAdjuster e;
    public boolean f;
    public boolean g;
    public boolean h;
    public int i;
    public int j;
    public boolean k;
    public long l;

    public PesReader(ElementaryStreamReader elementaryStreamReader) {
        this.a = elementaryStreamReader;
    }

    public final boolean a(int i2, ParsableByteArray parsableByteArray, @Nullable byte[] bArr) {
        int min = Math.min(parsableByteArray.bytesLeft(), i2 - this.d);
        if (min <= 0) {
            return true;
        }
        if (bArr == null) {
            parsableByteArray.skipBytes(min);
        } else {
            parsableByteArray.readBytes(bArr, this.d, min);
        }
        int i3 = this.d + min;
        this.d = i3;
        if (i3 == i2) {
            return true;
        }
        return false;
    }

    public final void consume(ParsableByteArray parsableByteArray, int i2) throws ParserException {
        boolean z;
        int i3;
        int i4;
        int i5;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        Assertions.checkStateNotNull(this.e);
        int i6 = i2 & 1;
        ElementaryStreamReader elementaryStreamReader = this.a;
        int i7 = -1;
        int i8 = 3;
        int i9 = 2;
        if (i6 != 0) {
            int i10 = this.c;
            if (!(i10 == 0 || i10 == 1)) {
                if (i10 == 2) {
                    Log.w("PesReader", "Unexpected start indicator reading extended header");
                } else if (i10 == 3) {
                    int i11 = this.j;
                    if (i11 != -1) {
                        StringBuilder sb = new StringBuilder(59);
                        sb.append("Unexpected start indicator: expected ");
                        sb.append(i11);
                        sb.append(" more bytes");
                        Log.w("PesReader", sb.toString());
                    }
                    elementaryStreamReader.packetFinished();
                } else {
                    throw new IllegalStateException();
                }
            }
            this.c = 1;
            this.d = 0;
        }
        int i12 = i2;
        while (parsableByteArray.bytesLeft() > 0) {
            int i13 = this.c;
            if (i13 != 0) {
                ParsableBitArray parsableBitArray = this.b;
                if (i13 != 1) {
                    if (i13 != i9) {
                        if (i13 == i8) {
                            int bytesLeft = parsableByteArray.bytesLeft();
                            int i14 = this.j;
                            if (i14 == i7) {
                                i5 = 0;
                            } else {
                                i5 = bytesLeft - i14;
                            }
                            if (i5 > 0) {
                                bytesLeft -= i5;
                                parsableByteArray2.setLimit(parsableByteArray.getPosition() + bytesLeft);
                            }
                            elementaryStreamReader.consume(parsableByteArray2);
                            int i15 = this.j;
                            if (i15 != i7) {
                                int i16 = i15 - bytesLeft;
                                this.j = i16;
                                if (i16 == 0) {
                                    elementaryStreamReader.packetFinished();
                                    this.c = 1;
                                    this.d = 0;
                                }
                            }
                        } else {
                            throw new IllegalStateException();
                        }
                    } else if (a(Math.min(10, this.i), parsableByteArray2, parsableBitArray.a) && a(this.i, parsableByteArray2, (byte[]) null)) {
                        parsableBitArray.setPosition(0);
                        this.l = -9223372036854775807L;
                        if (this.f) {
                            parsableBitArray.skipBits(4);
                            parsableBitArray.skipBits(1);
                            parsableBitArray.skipBits(1);
                            long readBits = (((long) parsableBitArray.readBits(i8)) << 30) | ((long) (parsableBitArray.readBits(15) << 15)) | ((long) parsableBitArray.readBits(15));
                            parsableBitArray.skipBits(1);
                            if (!this.h && this.g) {
                                parsableBitArray.skipBits(4);
                                parsableBitArray.skipBits(1);
                                long readBits2 = (long) (parsableBitArray.readBits(15) << 15);
                                parsableBitArray.skipBits(1);
                                parsableBitArray.skipBits(1);
                                this.e.adjustTsTimestamp(readBits2 | (((long) parsableBitArray.readBits(3)) << 30) | ((long) parsableBitArray.readBits(15)));
                                this.h = true;
                            }
                            this.l = this.e.adjustTsTimestamp(readBits);
                        }
                        if (this.k) {
                            i4 = 4;
                        } else {
                            i4 = 0;
                        }
                        i12 |= i4;
                        elementaryStreamReader.packetStarted(this.l, i12);
                        this.c = 3;
                        this.d = 0;
                    }
                } else if (a(9, parsableByteArray2, parsableBitArray.a)) {
                    parsableBitArray.setPosition(0);
                    int readBits3 = parsableBitArray.readBits(24);
                    if (readBits3 != 1) {
                        y2.t(41, "Unexpected start code prefix: ", readBits3, "PesReader");
                        this.j = -1;
                        z = false;
                    } else {
                        parsableBitArray.skipBits(8);
                        int readBits4 = parsableBitArray.readBits(16);
                        parsableBitArray.skipBits(5);
                        this.k = parsableBitArray.readBit();
                        parsableBitArray.skipBits(2);
                        this.f = parsableBitArray.readBit();
                        this.g = parsableBitArray.readBit();
                        parsableBitArray.skipBits(6);
                        int readBits5 = parsableBitArray.readBits(8);
                        this.i = readBits5;
                        if (readBits4 == 0) {
                            this.j = -1;
                        } else {
                            int i17 = ((readBits4 + 6) - 9) - readBits5;
                            this.j = i17;
                            if (i17 < 0) {
                                y2.t(47, "Found negative packet payload size: ", i17, "PesReader");
                                this.j = -1;
                            }
                        }
                        z = true;
                    }
                    if (z) {
                        i3 = 2;
                    } else {
                        i3 = 0;
                    }
                    this.c = i3;
                    this.d = 0;
                }
            } else {
                parsableByteArray2.skipBytes(parsableByteArray.bytesLeft());
            }
            i7 = -1;
            i8 = 3;
            i9 = 2;
        }
    }

    public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        this.e = timestampAdjuster;
        this.a.createTracks(extractorOutput, trackIdGenerator);
    }

    public final void seek() {
        this.c = 0;
        this.d = 0;
        this.h = false;
        this.a.seek();
    }
}
