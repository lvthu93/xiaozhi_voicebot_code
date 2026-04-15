package com.google.android.exoplayer2.extractor.ts;

import androidx.core.view.InputDeviceCompat;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;

public final class SectionReader implements TsPayloadReader {
    public final SectionPayloadReader a;
    public final ParsableByteArray b = new ParsableByteArray(32);
    public int c;
    public int d;
    public boolean e;
    public boolean f;

    public SectionReader(SectionPayloadReader sectionPayloadReader) {
        this.a = sectionPayloadReader;
    }

    public void consume(ParsableByteArray parsableByteArray, int i) {
        boolean z;
        int i2;
        boolean z2;
        if ((i & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            i2 = parsableByteArray.getPosition() + parsableByteArray.readUnsignedByte();
        } else {
            i2 = -1;
        }
        if (this.f) {
            if (z) {
                this.f = false;
                parsableByteArray.setPosition(i2);
                this.d = 0;
            } else {
                return;
            }
        }
        while (parsableByteArray.bytesLeft() > 0) {
            int i3 = this.d;
            ParsableByteArray parsableByteArray2 = this.b;
            if (i3 < 3) {
                if (i3 == 0) {
                    int readUnsignedByte = parsableByteArray.readUnsignedByte();
                    parsableByteArray.setPosition(parsableByteArray.getPosition() - 1);
                    if (readUnsignedByte == 255) {
                        this.f = true;
                        return;
                    }
                }
                int min = Math.min(parsableByteArray.bytesLeft(), 3 - this.d);
                parsableByteArray.readBytes(parsableByteArray2.getData(), this.d, min);
                int i4 = this.d + min;
                this.d = i4;
                if (i4 == 3) {
                    parsableByteArray2.setPosition(0);
                    parsableByteArray2.setLimit(3);
                    parsableByteArray2.skipBytes(1);
                    int readUnsignedByte2 = parsableByteArray2.readUnsignedByte();
                    int readUnsignedByte3 = parsableByteArray2.readUnsignedByte();
                    if ((readUnsignedByte2 & 128) != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    this.e = z2;
                    this.c = (((readUnsignedByte2 & 15) << 8) | readUnsignedByte3) + 3;
                    int capacity = parsableByteArray2.capacity();
                    int i5 = this.c;
                    if (capacity < i5) {
                        parsableByteArray2.ensureCapacity(Math.min(InputDeviceCompat.SOURCE_TOUCHSCREEN, Math.max(i5, parsableByteArray2.capacity() * 2)));
                    }
                }
            } else {
                int min2 = Math.min(parsableByteArray.bytesLeft(), this.c - this.d);
                parsableByteArray.readBytes(parsableByteArray2.getData(), this.d, min2);
                int i6 = this.d + min2;
                this.d = i6;
                int i7 = this.c;
                if (i6 != i7) {
                    continue;
                } else {
                    if (!this.e) {
                        parsableByteArray2.setLimit(i7);
                    } else if (Util.crc32(parsableByteArray2.getData(), 0, this.c, -1) != 0) {
                        this.f = true;
                        return;
                    } else {
                        parsableByteArray2.setLimit(this.c - 4);
                    }
                    parsableByteArray2.setPosition(0);
                    this.a.consume(parsableByteArray2);
                    this.d = 0;
                }
            }
        }
    }

    public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        this.a.init(timestampAdjuster, extractorOutput, trackIdGenerator);
        this.f = true;
    }

    public void seek() {
        this.f = true;
    }
}
