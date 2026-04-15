package com.google.android.exoplayer2.source.chunk;

import android.annotation.SuppressLint;
import android.media.MediaParser;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.mediaparser.InputReaderAdapterV30;
import com.google.android.exoplayer2.source.mediaparser.MediaParserUtil;
import com.google.android.exoplayer2.source.mediaparser.OutputConsumerAdapterV30;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(30)
public final class MediaParserChunkExtractor implements ChunkExtractor {
    public final OutputConsumerAdapterV30 c;
    public final InputReaderAdapterV30 f = new InputReaderAdapterV30();
    public final MediaParser g;
    public final a h;
    public final DummyTrackOutput i;
    public long j;
    @Nullable
    public ChunkExtractor.TrackOutputProvider k;
    @Nullable
    public Format[] l;

    public class a implements ExtractorOutput {
        public a() {
        }

        public void endTracks() {
            MediaParserChunkExtractor mediaParserChunkExtractor = MediaParserChunkExtractor.this;
            mediaParserChunkExtractor.l = mediaParserChunkExtractor.c.getSampleFormats();
        }

        public void seekMap(SeekMap seekMap) {
        }

        public TrackOutput track(int i, int i2) {
            MediaParserChunkExtractor mediaParserChunkExtractor = MediaParserChunkExtractor.this;
            ChunkExtractor.TrackOutputProvider trackOutputProvider = mediaParserChunkExtractor.k;
            if (trackOutputProvider != null) {
                return trackOutputProvider.track(i, i2);
            }
            return mediaParserChunkExtractor.i;
        }
    }

    @SuppressLint({"WrongConstant"})
    public MediaParserChunkExtractor(int i2, Format format, List<Format> list) {
        String str;
        OutputConsumerAdapterV30 outputConsumerAdapterV30 = new OutputConsumerAdapterV30(format, i2, true);
        this.c = outputConsumerAdapterV30;
        if (MimeTypes.isMatroska((String) Assertions.checkNotNull(format.o))) {
            str = "android.media.mediaparser.MatroskaParser";
        } else {
            str = "android.media.mediaparser.FragmentedMp4Parser";
        }
        outputConsumerAdapterV30.setSelectedParserName(str);
        MediaParser m = MediaParser.createByName(str, outputConsumerAdapterV30);
        this.g = m;
        Boolean bool = Boolean.TRUE;
        m.setParameter("android.media.mediaparser.matroska.disableCuesSeeking", bool);
        m.setParameter("android.media.mediaparser.inBandCryptoInfo", bool);
        m.setParameter("android.media.mediaparser.includeSupplementalData", bool);
        m.setParameter("android.media.mediaparser.eagerlyExposeTrackType", bool);
        m.setParameter("android.media.mediaparser.exposeDummySeekMap", bool);
        m.setParameter("android.media.mediaParser.exposeChunkIndexAsMediaFormat", bool);
        m.setParameter("android.media.mediaParser.overrideInBandCaptionDeclarations", bool);
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < list.size(); i3++) {
            arrayList.add(MediaParserUtil.toCaptionsMediaFormat(list.get(i3)));
        }
        this.g.setParameter("android.media.mediaParser.exposeCaptionFormats", arrayList);
        this.c.setMuxedCaptionFormats(list);
        this.h = new a();
        this.i = new DummyTrackOutput();
        this.j = -9223372036854775807L;
    }

    @Nullable
    public ChunkIndex getChunkIndex() {
        return this.c.getChunkIndex();
    }

    @Nullable
    public Format[] getSampleFormats() {
        return this.l;
    }

    public void init(@Nullable ChunkExtractor.TrackOutputProvider trackOutputProvider, long j2, long j3) {
        this.k = trackOutputProvider;
        OutputConsumerAdapterV30 outputConsumerAdapterV30 = this.c;
        outputConsumerAdapterV30.setSampleTimestampUpperLimitFilterUs(j3);
        outputConsumerAdapterV30.setExtractorOutput(this.h);
        this.j = j2;
    }

    public boolean read(ExtractorInput extractorInput) throws IOException {
        MediaParser.SeekMap dummySeekMap = this.c.getDummySeekMap();
        long j2 = this.j;
        MediaParser mediaParser = this.g;
        if (!(j2 == -9223372036854775807L || dummySeekMap == null)) {
            mediaParser.seek((MediaParser.SeekPoint) dummySeekMap.getSeekPoints(j2).first);
            this.j = -9223372036854775807L;
        }
        long length = extractorInput.getLength();
        InputReaderAdapterV30 inputReaderAdapterV30 = this.f;
        inputReaderAdapterV30.setDataReader(extractorInput, length);
        return mediaParser.advance(inputReaderAdapterV30);
    }

    public void release() {
        this.g.release();
    }
}
