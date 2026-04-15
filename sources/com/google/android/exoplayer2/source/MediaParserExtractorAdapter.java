package com.google.android.exoplayer2.source;

import android.annotation.SuppressLint;
import android.media.MediaParser;
import android.net.Uri;
import android.util.Pair;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.source.mediaparser.InputReaderAdapterV30;
import com.google.android.exoplayer2.source.mediaparser.OutputConsumerAdapterV30;
import com.google.android.exoplayer2.upstream.DataReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiresApi(30)
public final class MediaParserExtractorAdapter implements ProgressiveMediaExtractor {
    public final OutputConsumerAdapterV30 a;
    public final InputReaderAdapterV30 b = new InputReaderAdapterV30();
    public final MediaParser c;
    public String d;

    @SuppressLint({"WrongConstant"})
    public MediaParserExtractorAdapter() {
        OutputConsumerAdapterV30 outputConsumerAdapterV30 = new OutputConsumerAdapterV30();
        this.a = outputConsumerAdapterV30;
        MediaParser l = MediaParser.create(outputConsumerAdapterV30, new String[0]);
        this.c = l;
        Boolean bool = Boolean.TRUE;
        l.setParameter("android.media.mediaparser.eagerlyExposeTrackType", bool);
        l.setParameter("android.media.mediaparser.inBandCryptoInfo", bool);
        l.setParameter("android.media.mediaparser.includeSupplementalData", bool);
        this.d = "android.media.mediaparser.UNKNOWN";
    }

    public void disableSeekingOnMp3Streams() {
        if ("android.media.mediaparser.Mp3Parser".equals(this.d)) {
            this.a.disableSeeking();
        }
    }

    public long getCurrentInputPosition() {
        return this.b.getPosition();
    }

    public void init(DataReader dataReader, Uri uri, Map<String, List<String>> map, long j, long j2, ExtractorOutput extractorOutput) throws IOException {
        OutputConsumerAdapterV30 outputConsumerAdapterV30 = this.a;
        outputConsumerAdapterV30.setExtractorOutput(extractorOutput);
        InputReaderAdapterV30 inputReaderAdapterV30 = this.b;
        inputReaderAdapterV30.setDataReader(dataReader, j2);
        inputReaderAdapterV30.setCurrentPosition(j);
        MediaParser mediaParser = this.c;
        String o = mediaParser.getParserName();
        if ("android.media.mediaparser.UNKNOWN".equals(o)) {
            mediaParser.advance(inputReaderAdapterV30);
            String o2 = mediaParser.getParserName();
            this.d = o2;
            outputConsumerAdapterV30.setSelectedParserName(o2);
        } else if (!o.equals(this.d)) {
            String o3 = mediaParser.getParserName();
            this.d = o3;
            outputConsumerAdapterV30.setSelectedParserName(o3);
        }
    }

    public int read(PositionHolder positionHolder) throws IOException {
        MediaParser mediaParser = this.c;
        InputReaderAdapterV30 inputReaderAdapterV30 = this.b;
        boolean w = mediaParser.advance(inputReaderAdapterV30);
        long andResetSeekPosition = inputReaderAdapterV30.getAndResetSeekPosition();
        positionHolder.a = andResetSeekPosition;
        if (!w) {
            return -1;
        }
        if (andResetSeekPosition != -1) {
            return 1;
        }
        return 0;
    }

    public void release() {
        this.c.release();
    }

    public void seek(long j, long j2) {
        Object obj;
        this.b.setCurrentPosition(j);
        Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> seekPoints = this.a.getSeekPoints(j2);
        if (((MediaParser.SeekPoint) seekPoints.second).position == j) {
            obj = seekPoints.second;
        } else {
            obj = seekPoints.first;
        }
        this.c.seek((MediaParser.SeekPoint) obj);
    }
}
