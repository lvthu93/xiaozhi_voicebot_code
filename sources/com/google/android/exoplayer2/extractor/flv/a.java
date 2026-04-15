package com.google.android.exoplayer2.extractor.flv;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.flv.TagPayloadReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Collections;

public final class a extends TagPayloadReader {
    public static final int[] e = {5512, 11025, 22050, 44100};
    public boolean b;
    public boolean c;
    public int d;

    public a(TrackOutput trackOutput) {
        super(trackOutput);
    }

    public final boolean a(ParsableByteArray parsableByteArray) throws TagPayloadReader.UnsupportedFormatException {
        String str;
        if (!this.b) {
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            int i = (readUnsignedByte >> 4) & 15;
            this.d = i;
            TrackOutput trackOutput = this.a;
            if (i == 2) {
                trackOutput.format(new Format.Builder().setSampleMimeType("audio/mpeg").setChannelCount(1).setSampleRate(e[(readUnsignedByte >> 2) & 3]).build());
                this.c = true;
            } else if (i == 7 || i == 8) {
                if (i == 7) {
                    str = "audio/g711-alaw";
                } else {
                    str = "audio/g711-mlaw";
                }
                trackOutput.format(new Format.Builder().setSampleMimeType(str).setChannelCount(1).setSampleRate(8000).build());
                this.c = true;
            } else if (i != 10) {
                throw new TagPayloadReader.UnsupportedFormatException(y2.d(39, "Audio format not supported: ", this.d));
            }
            this.b = true;
        } else {
            parsableByteArray.skipBytes(1);
        }
        return true;
    }

    public final boolean b(ParsableByteArray parsableByteArray, long j) throws ParserException {
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int i = this.d;
        TrackOutput trackOutput = this.a;
        if (i == 2) {
            int bytesLeft = parsableByteArray.bytesLeft();
            trackOutput.sampleData(parsableByteArray2, bytesLeft);
            this.a.sampleMetadata(j, 1, bytesLeft, 0, (TrackOutput.CryptoData) null);
            return true;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if (readUnsignedByte == 0 && !this.c) {
            int bytesLeft2 = parsableByteArray.bytesLeft();
            byte[] bArr = new byte[bytesLeft2];
            parsableByteArray2.readBytes(bArr, 0, bytesLeft2);
            AacUtil.Config parseAudioSpecificConfig = AacUtil.parseAudioSpecificConfig(bArr);
            trackOutput.format(new Format.Builder().setSampleMimeType("audio/mp4a-latm").setCodecs(parseAudioSpecificConfig.c).setChannelCount(parseAudioSpecificConfig.b).setSampleRate(parseAudioSpecificConfig.a).setInitializationData(Collections.singletonList(bArr)).build());
            this.c = true;
            return false;
        } else if (this.d == 10 && readUnsignedByte != 1) {
            return false;
        } else {
            int bytesLeft3 = parsableByteArray.bytesLeft();
            trackOutput.sampleData(parsableByteArray2, bytesLeft3);
            this.a.sampleMetadata(j, 1, bytesLeft3, 0, (TrackOutput.CryptoData) null);
            return true;
        }
    }

    public void seek() {
    }
}
