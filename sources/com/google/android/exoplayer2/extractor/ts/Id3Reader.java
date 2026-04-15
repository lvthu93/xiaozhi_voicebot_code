package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;

public final class Id3Reader implements ElementaryStreamReader {
    public final ParsableByteArray a = new ParsableByteArray(10);
    public TrackOutput b;
    public boolean c;
    public long d;
    public int e;
    public int f;

    public void consume(ParsableByteArray parsableByteArray) {
        Assertions.checkStateNotNull(this.b);
        if (this.c) {
            int bytesLeft = parsableByteArray.bytesLeft();
            int i = this.f;
            if (i < 10) {
                int min = Math.min(bytesLeft, 10 - i);
                byte[] data = parsableByteArray.getData();
                int position = parsableByteArray.getPosition();
                ParsableByteArray parsableByteArray2 = this.a;
                System.arraycopy(data, position, parsableByteArray2.getData(), this.f, min);
                if (this.f + min == 10) {
                    parsableByteArray2.setPosition(0);
                    if (73 == parsableByteArray2.readUnsignedByte() && 68 == parsableByteArray2.readUnsignedByte() && 51 == parsableByteArray2.readUnsignedByte()) {
                        parsableByteArray2.skipBytes(3);
                        this.e = parsableByteArray2.readSynchSafeInt() + 10;
                    } else {
                        Log.w("Id3Reader", "Discarding invalid ID3 tag");
                        this.c = false;
                        return;
                    }
                }
            }
            int min2 = Math.min(bytesLeft, this.e - this.f);
            this.b.sampleData(parsableByteArray, min2);
            this.f += min2;
        }
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        TrackOutput track = extractorOutput.track(trackIdGenerator.getTrackId(), 5);
        this.b = track;
        track.format(new Format.Builder().setId(trackIdGenerator.getFormatId()).setSampleMimeType("application/id3").build());
    }

    public void packetFinished() {
        int i;
        Assertions.checkStateNotNull(this.b);
        if (this.c && (i = this.e) != 0 && this.f == i) {
            this.b.sampleMetadata(this.d, 1, i, 0, (TrackOutput.CryptoData) null);
            this.c = false;
        }
    }

    public void packetStarted(long j, int i) {
        if ((i & 4) != 0) {
            this.c = true;
            this.d = j;
            this.e = 0;
            this.f = 0;
        }
    }

    public void seek() {
        this.c = false;
    }
}
