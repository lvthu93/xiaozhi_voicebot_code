package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;

public final class MpegAudioReader implements ElementaryStreamReader {
    public final ParsableByteArray a;
    public final MpegAudioUtil.Header b;
    @Nullable
    public final String c;
    public TrackOutput d;
    public String e;
    public int f;
    public int g;
    public boolean h;
    public boolean i;
    public long j;
    public int k;
    public long l;

    public MpegAudioReader() {
        this((String) null);
    }

    public void consume(ParsableByteArray parsableByteArray) {
        boolean z;
        boolean z2;
        Assertions.checkStateNotNull(this.d);
        while (parsableByteArray.bytesLeft() > 0) {
            int i2 = this.f;
            ParsableByteArray parsableByteArray2 = this.a;
            if (i2 == 0) {
                byte[] data = parsableByteArray.getData();
                int position = parsableByteArray.getPosition();
                int limit = parsableByteArray.limit();
                while (true) {
                    if (position >= limit) {
                        parsableByteArray.setPosition(limit);
                        break;
                    }
                    byte b2 = data[position];
                    if ((b2 & 255) == 255) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!this.i || (b2 & 224) != 224) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    this.i = z;
                    if (z2) {
                        parsableByteArray.setPosition(position + 1);
                        this.i = false;
                        parsableByteArray2.getData()[1] = data[position];
                        this.g = 2;
                        this.f = 1;
                        break;
                    }
                    position++;
                }
            } else if (i2 == 1) {
                int min = Math.min(parsableByteArray.bytesLeft(), 4 - this.g);
                parsableByteArray.readBytes(parsableByteArray2.getData(), this.g, min);
                int i3 = this.g + min;
                this.g = i3;
                if (i3 >= 4) {
                    parsableByteArray2.setPosition(0);
                    int readInt = parsableByteArray2.readInt();
                    MpegAudioUtil.Header header = this.b;
                    if (!header.setForHeaderData(readInt)) {
                        this.g = 0;
                        this.f = 1;
                    } else {
                        this.k = header.c;
                        if (!this.h) {
                            this.j = (((long) header.g) * 1000000) / ((long) header.d);
                            this.d.format(new Format.Builder().setId(this.e).setSampleMimeType(header.b).setMaxInputSize(4096).setChannelCount(header.e).setSampleRate(header.d).setLanguage(this.c).build());
                            this.h = true;
                        }
                        parsableByteArray2.setPosition(0);
                        this.d.sampleData(parsableByteArray2, 4);
                        this.f = 2;
                    }
                }
            } else if (i2 == 2) {
                int min2 = Math.min(parsableByteArray.bytesLeft(), this.k - this.g);
                this.d.sampleData(parsableByteArray, min2);
                int i4 = this.g + min2;
                this.g = i4;
                int i5 = this.k;
                if (i4 >= i5) {
                    this.d.sampleMetadata(this.l, 1, i5, 0, (TrackOutput.CryptoData) null);
                    this.l += this.j;
                    this.g = 0;
                    this.f = 0;
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.e = trackIdGenerator.getFormatId();
        this.d = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
    }

    public void packetFinished() {
    }

    public void packetStarted(long j2, int i2) {
        this.l = j2;
    }

    public void seek() {
        this.f = 0;
        this.g = 0;
        this.i = false;
    }

    public MpegAudioReader(@Nullable String str) {
        this.f = 0;
        ParsableByteArray parsableByteArray = new ParsableByteArray(4);
        this.a = parsableByteArray;
        parsableByteArray.getData()[0] = -1;
        this.b = new MpegAudioUtil.Header();
        this.c = str;
    }
}
