package com.google.android.exoplayer2.extractor.flv;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.flv.TagPayloadReader;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.video.AvcConfig;

public final class b extends TagPayloadReader {
    public final ParsableByteArray b = new ParsableByteArray(NalUnitUtil.a);
    public final ParsableByteArray c = new ParsableByteArray(4);
    public int d;
    public boolean e;
    public boolean f;
    public int g;

    public b(TrackOutput trackOutput) {
        super(trackOutput);
    }

    public final boolean a(ParsableByteArray parsableByteArray) throws TagPayloadReader.UnsupportedFormatException {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i = (readUnsignedByte >> 4) & 15;
        int i2 = readUnsignedByte & 15;
        if (i2 == 7) {
            this.g = i;
            if (i != 5) {
                return true;
            }
            return false;
        }
        throw new TagPayloadReader.UnsupportedFormatException(y2.d(39, "Video format not supported: ", i2));
    }

    public final boolean b(ParsableByteArray parsableByteArray, long j) throws ParserException {
        int i;
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        long readInt24 = (((long) parsableByteArray.readInt24()) * 1000) + j;
        TrackOutput trackOutput = this.a;
        if (readUnsignedByte == 0 && !this.e) {
            ParsableByteArray parsableByteArray2 = new ParsableByteArray(new byte[parsableByteArray.bytesLeft()]);
            parsableByteArray.readBytes(parsableByteArray2.getData(), 0, parsableByteArray.bytesLeft());
            AvcConfig parse = AvcConfig.parse(parsableByteArray2);
            this.d = parse.b;
            trackOutput.format(new Format.Builder().setSampleMimeType("video/avc").setCodecs(parse.f).setWidth(parse.c).setHeight(parse.d).setPixelWidthHeightRatio(parse.e).setInitializationData(parse.a).build());
            this.e = true;
            return false;
        } else if (readUnsignedByte != 1 || !this.e) {
            return false;
        } else {
            if (this.g == 1) {
                i = 1;
            } else {
                i = 0;
            }
            if (!this.f && i == 0) {
                return false;
            }
            ParsableByteArray parsableByteArray3 = this.c;
            byte[] data = parsableByteArray3.getData();
            data[0] = 0;
            data[1] = 0;
            data[2] = 0;
            int i2 = 4 - this.d;
            int i3 = 0;
            while (parsableByteArray.bytesLeft() > 0) {
                parsableByteArray.readBytes(parsableByteArray3.getData(), i2, this.d);
                parsableByteArray3.setPosition(0);
                int readUnsignedIntToInt = parsableByteArray3.readUnsignedIntToInt();
                ParsableByteArray parsableByteArray4 = this.b;
                parsableByteArray4.setPosition(0);
                trackOutput.sampleData(parsableByteArray4, 4);
                trackOutput.sampleData(parsableByteArray, readUnsignedIntToInt);
                i3 = i3 + 4 + readUnsignedIntToInt;
            }
            this.a.sampleMetadata(readInt24, i, i3, 0, (TrackOutput.CryptoData) null);
            this.f = true;
            return true;
        }
    }

    public void seek() {
        this.f = false;
    }
}
