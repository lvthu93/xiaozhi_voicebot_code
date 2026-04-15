package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.CeaUtil;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.List;

public final class SeiReader {
    public final List<Format> a;
    public final TrackOutput[] b;

    public SeiReader(List<Format> list) {
        this.a = list;
        this.b = new TrackOutput[list.size()];
    }

    public void consume(long j, ParsableByteArray parsableByteArray) {
        CeaUtil.consume(j, parsableByteArray, this.b);
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
                String str3 = format.c;
                if (str3 == null) {
                    str3 = trackIdGenerator.getFormatId();
                }
                track.format(new Format.Builder().setId(str3).setSampleMimeType(str2).setSelectionFlags(format.h).setLanguage(format.g).setAccessibilityChannel(format.ah).setInitializationData(format.r).build());
                trackOutputArr[i] = track;
                i++;
            } else {
                return;
            }
        }
    }
}
