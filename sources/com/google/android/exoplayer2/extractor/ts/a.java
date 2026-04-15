package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.CeaUtil;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.List;

public final class a {
    public final List<Format> a;
    public final TrackOutput[] b;

    public a(List<Format> list) {
        this.a = list;
        this.b = new TrackOutput[list.size()];
    }

    public void consume(long j, ParsableByteArray parsableByteArray) {
        if (parsableByteArray.bytesLeft() >= 9) {
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            if (readInt == 434 && readInt2 == 1195456820 && readUnsignedByte == 3) {
                CeaUtil.consumeCcData(j, parsableByteArray, this.b);
            }
        }
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        boolean z;
        String str;
        int i = 0;
        while (true) {
            TrackOutput[] trackOutputArr = this.b;
            if (i < trackOutputArr.length) {
                trackIdGenerator.generateNewId();
                TrackOutput track = extractorOutput.track(trackIdGenerator.getTrackId(), 3);
                Format format = this.a.get(i);
                String str2 = format.p;
                if ("application/cea-608".equals(str2) || "application/cea-708".equals(str2)) {
                    z = true;
                } else {
                    z = false;
                }
                String valueOf = String.valueOf(str2);
                if (valueOf.length() != 0) {
                    str = "Invalid closed caption mime type provided: ".concat(valueOf);
                } else {
                    str = new String("Invalid closed caption mime type provided: ");
                }
                Assertions.checkArgument(z, str);
                track.format(new Format.Builder().setId(trackIdGenerator.getFormatId()).setSampleMimeType(str2).setSelectionFlags(format.h).setLanguage(format.g).setAccessibilityChannel(format.ah).setInitializationData(format.r).build());
                trackOutputArr[i] = track;
                i++;
            } else {
                return;
            }
        }
    }
}
