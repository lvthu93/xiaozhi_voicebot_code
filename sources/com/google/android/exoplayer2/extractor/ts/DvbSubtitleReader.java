package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Collections;
import java.util.List;

public final class DvbSubtitleReader implements ElementaryStreamReader {
    public final List<TsPayloadReader.DvbSubtitleInfo> a;
    public final TrackOutput[] b;
    public boolean c;
    public int d;
    public int e;
    public long f;

    public DvbSubtitleReader(List<TsPayloadReader.DvbSubtitleInfo> list) {
        this.a = list;
        this.b = new TrackOutput[list.size()];
    }

    public void consume(ParsableByteArray parsableByteArray) {
        boolean z;
        boolean z2;
        if (this.c) {
            if (this.d == 2) {
                if (parsableByteArray.bytesLeft() == 0) {
                    z2 = false;
                } else {
                    if (parsableByteArray.readUnsignedByte() != 32) {
                        this.c = false;
                    }
                    this.d--;
                    z2 = this.c;
                }
                if (!z2) {
                    return;
                }
            }
            if (this.d == 1) {
                if (parsableByteArray.bytesLeft() == 0) {
                    z = false;
                } else {
                    if (parsableByteArray.readUnsignedByte() != 0) {
                        this.c = false;
                    }
                    this.d--;
                    z = this.c;
                }
                if (!z) {
                    return;
                }
            }
            int position = parsableByteArray.getPosition();
            int bytesLeft = parsableByteArray.bytesLeft();
            for (TrackOutput sampleData : this.b) {
                parsableByteArray.setPosition(position);
                sampleData.sampleData(parsableByteArray, bytesLeft);
            }
            this.e += bytesLeft;
        }
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        int i = 0;
        while (true) {
            TrackOutput[] trackOutputArr = this.b;
            if (i < trackOutputArr.length) {
                TsPayloadReader.DvbSubtitleInfo dvbSubtitleInfo = this.a.get(i);
                trackIdGenerator.generateNewId();
                TrackOutput track = extractorOutput.track(trackIdGenerator.getTrackId(), 3);
                track.format(new Format.Builder().setId(trackIdGenerator.getFormatId()).setSampleMimeType("application/dvbsubs").setInitializationData(Collections.singletonList(dvbSubtitleInfo.b)).setLanguage(dvbSubtitleInfo.a).build());
                trackOutputArr[i] = track;
                i++;
            } else {
                return;
            }
        }
    }

    public void packetFinished() {
        if (this.c) {
            for (TrackOutput sampleMetadata : this.b) {
                sampleMetadata.sampleMetadata(this.f, 1, this.e, 0, (TrackOutput.CryptoData) null);
            }
            this.c = false;
        }
    }

    public void packetStarted(long j, int i) {
        if ((i & 4) != 0) {
            this.c = true;
            this.f = j;
            this.e = 0;
            this.d = 2;
        }
    }

    public void seek() {
        this.c = false;
    }
}
