package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.DtsUtil;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;

public final class DtsReader implements ElementaryStreamReader {
    public final ParsableByteArray a = new ParsableByteArray(new byte[18]);
    @Nullable
    public final String b;
    public String c;
    public TrackOutput d;
    public int e = 0;
    public int f;
    public int g;
    public long h;
    public Format i;
    public int j;
    public long k;

    public DtsReader(@Nullable String str) {
        this.b = str;
    }

    public void consume(ParsableByteArray parsableByteArray) {
        Assertions.checkStateNotNull(this.d);
        while (parsableByteArray.bytesLeft() > 0) {
            int i2 = this.e;
            boolean z = false;
            boolean z2 = true;
            ParsableByteArray parsableByteArray2 = this.a;
            if (i2 == 0) {
                while (true) {
                    if (parsableByteArray.bytesLeft() <= 0) {
                        break;
                    }
                    int i3 = this.g << 8;
                    this.g = i3;
                    int readUnsignedByte = i3 | parsableByteArray.readUnsignedByte();
                    this.g = readUnsignedByte;
                    if (DtsUtil.isSyncWord(readUnsignedByte)) {
                        byte[] data = parsableByteArray2.getData();
                        int i4 = this.g;
                        data[0] = (byte) ((i4 >> 24) & 255);
                        data[1] = (byte) ((i4 >> 16) & 255);
                        data[2] = (byte) ((i4 >> 8) & 255);
                        data[3] = (byte) (i4 & 255);
                        this.f = 4;
                        this.g = 0;
                        z = true;
                        break;
                    }
                }
                if (z) {
                    this.e = 1;
                }
            } else if (i2 == 1) {
                byte[] data2 = parsableByteArray2.getData();
                int min = Math.min(parsableByteArray.bytesLeft(), 18 - this.f);
                parsableByteArray.readBytes(data2, this.f, min);
                int i5 = this.f + min;
                this.f = i5;
                if (i5 != 18) {
                    z2 = false;
                }
                if (z2) {
                    byte[] data3 = parsableByteArray2.getData();
                    if (this.i == null) {
                        Format parseDtsFormat = DtsUtil.parseDtsFormat(data3, this.c, this.b, (DrmInitData) null);
                        this.i = parseDtsFormat;
                        this.d.format(parseDtsFormat);
                    }
                    this.j = DtsUtil.getDtsFrameSize(data3);
                    this.h = (long) ((int) ((((long) DtsUtil.parseDtsAudioSampleCount(data3)) * 1000000) / ((long) this.i.ad)));
                    parsableByteArray2.setPosition(0);
                    this.d.sampleData(parsableByteArray2, 18);
                    this.e = 2;
                }
            } else if (i2 == 2) {
                int min2 = Math.min(parsableByteArray.bytesLeft(), this.j - this.f);
                this.d.sampleData(parsableByteArray, min2);
                int i6 = this.f + min2;
                this.f = i6;
                int i7 = this.j;
                if (i6 == i7) {
                    this.d.sampleMetadata(this.k, 1, i7, 0, (TrackOutput.CryptoData) null);
                    this.k += this.h;
                    this.e = 0;
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.c = trackIdGenerator.getFormatId();
        this.d = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
    }

    public void packetFinished() {
    }

    public void packetStarted(long j2, int i2) {
        this.k = j2;
    }

    public void seek() {
        this.e = 0;
        this.f = 0;
        this.g = 0;
    }
}
