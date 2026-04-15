package com.google.android.exoplayer2.source.mediaparser;

import android.annotation.SuppressLint;
import android.media.MediaCodec;
import android.media.MediaParser;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.DummyExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiresApi(30)
@SuppressLint({"Override"})
public final class OutputConsumerAdapterV30 implements MediaParser.OutputConsumer {
    public static final Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> u = Pair.create(MediaParser.SeekPoint.START, MediaParser.SeekPoint.START);
    public static final Pattern v = Pattern.compile("pattern \\(encrypt: (\\d+), skip: (\\d+)\\)");
    public final ArrayList<TrackOutput> a;
    public final ArrayList<Format> b;
    public final ArrayList<MediaCodec.CryptoInfo> c;
    public final ArrayList<TrackOutput.CryptoData> d;
    public final a e;
    public final boolean f;
    public final int g;
    @Nullable
    public final Format h;
    public ExtractorOutput i;
    @Nullable
    public MediaParser.SeekMap j;
    @Nullable
    public MediaParser.SeekMap k;
    @Nullable
    public String l;
    @Nullable
    public ChunkIndex m;
    @Nullable
    public TimestampAdjuster n;
    public List<Format> o;
    public int p;
    public long q;
    public boolean r;
    public boolean s;
    public boolean t;

    public static final class a implements DataReader {
        @Nullable
        public MediaParser.InputReader a;

        public int read(byte[] bArr, int i, int i2) throws IOException {
            return ((MediaParser.InputReader) Util.castNonNull(this.a)).read(bArr, i, i2);
        }
    }

    public static final class b implements SeekMap {
        public final MediaParser.SeekMap a;

        public b(MediaParser.SeekMap seekMap) {
            this.a = seekMap;
        }

        public long getDurationUs() {
            long f = this.a.getDurationMicros();
            if (f != -2147483648L) {
                return f;
            }
            return -9223372036854775807L;
        }

        public SeekMap.SeekPoints getSeekPoints(long j) {
            Pair n = this.a.getSeekPoints(j);
            Object obj = n.first;
            if (obj == n.second) {
                MediaParser.SeekPoint seekPoint = (MediaParser.SeekPoint) obj;
                return new SeekMap.SeekPoints(new SeekPoint(seekPoint.timeMicros, seekPoint.position));
            }
            MediaParser.SeekPoint seekPoint2 = (MediaParser.SeekPoint) obj;
            SeekPoint seekPoint3 = new SeekPoint(seekPoint2.timeMicros, seekPoint2.position);
            MediaParser.SeekPoint seekPoint4 = (MediaParser.SeekPoint) n.second;
            return new SeekMap.SeekPoints(seekPoint3, new SeekPoint(seekPoint4.timeMicros, seekPoint4.position));
        }

        public boolean isSeekable() {
            return this.a.isSeekable();
        }
    }

    public OutputConsumerAdapterV30() {
        this((Format) null, 7, false);
    }

    public final void a(int i2) {
        for (int size = this.a.size(); size <= i2; size++) {
            this.a.add((Object) null);
            this.b.add((Object) null);
            this.c.add((Object) null);
            this.d.add((Object) null);
        }
    }

    public final void b() {
        if (this.r && !this.s) {
            int size = this.a.size();
            int i2 = 0;
            while (i2 < size) {
                if (this.a.get(i2) != null) {
                    i2++;
                } else {
                    return;
                }
            }
            this.i.endTracks();
            this.s = true;
        }
    }

    public void disableSeeking() {
        this.t = true;
    }

    @Nullable
    public ChunkIndex getChunkIndex() {
        return this.m;
    }

    @Nullable
    public MediaParser.SeekMap getDummySeekMap() {
        return this.j;
    }

    @Nullable
    public Format[] getSampleFormats() {
        if (!this.r) {
            return null;
        }
        Format[] formatArr = new Format[this.b.size()];
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            formatArr[i2] = (Format) Assertions.checkNotNull(this.b.get(i2));
        }
        return formatArr;
    }

    public Pair<MediaParser.SeekPoint, MediaParser.SeekPoint> getSeekPoints(long j2) {
        MediaParser.SeekMap seekMap = this.k;
        return seekMap != null ? seekMap.getSeekPoints(j2) : u;
    }

    public void onSampleCompleted(int i2, long j2, int i3, int i4, int i5, @Nullable MediaCodec.CryptoInfo cryptoInfo) {
        int i6;
        int i7;
        TrackOutput.CryptoData cryptoData;
        long j3 = this.q;
        if (j3 == -9223372036854775807L || j2 < j3) {
            TimestampAdjuster timestampAdjuster = this.n;
            if (timestampAdjuster != null) {
                j2 = timestampAdjuster.adjustSampleTimestamp(j2);
            }
            long j4 = j2;
            TrackOutput trackOutput = (TrackOutput) Assertions.checkNotNull(this.a.get(i2));
            if (cryptoInfo == null) {
                cryptoData = null;
            } else if (this.c.get(i2) == cryptoInfo) {
                cryptoData = (TrackOutput.CryptoData) Assertions.checkNotNull(this.d.get(i2));
            } else {
                try {
                    Matcher matcher = v.matcher(cryptoInfo.toString());
                    matcher.find();
                    i6 = Integer.parseInt((String) Util.castNonNull(matcher.group(1)));
                    i7 = Integer.parseInt((String) Util.castNonNull(matcher.group(2)));
                } catch (RuntimeException e2) {
                    String valueOf = String.valueOf(cryptoInfo);
                    StringBuilder sb = new StringBuilder(valueOf.length() + 43);
                    sb.append("Unexpected error while parsing CryptoInfo: ");
                    sb.append(valueOf);
                    Log.e("OutputConsumerAdapterV30", sb.toString(), e2);
                    i6 = 0;
                    i7 = 0;
                }
                TrackOutput.CryptoData cryptoData2 = new TrackOutput.CryptoData(cryptoInfo.mode, cryptoInfo.key, i6, i7);
                this.c.set(i2, cryptoInfo);
                this.d.set(i2, cryptoData2);
                cryptoData = cryptoData2;
            }
            trackOutput.sampleMetadata(j4, i3, i4, i5, cryptoData);
        }
    }

    public void onSampleDataFound(int i2, MediaParser.InputReader inputReader) throws IOException {
        a(i2);
        this.e.a = inputReader;
        TrackOutput trackOutput = this.a.get(i2);
        if (trackOutput == null) {
            trackOutput = this.i.track(i2, -1);
            this.a.set(i2, trackOutput);
        }
        trackOutput.sampleData((DataReader) this.e, (int) inputReader.getLength(), true);
    }

    public void onSeekMapFound(MediaParser.SeekMap seekMap) {
        SeekMap seekMap2;
        if (!this.f || this.j != null) {
            this.k = seekMap;
            long f2 = seekMap.getDurationMicros();
            ExtractorOutput extractorOutput = this.i;
            if (this.t) {
                if (f2 == -2147483648L) {
                    f2 = -9223372036854775807L;
                }
                seekMap2 = new SeekMap.Unseekable(f2);
            } else {
                seekMap2 = new b(seekMap);
            }
            extractorOutput.seekMap(seekMap2);
            return;
        }
        this.j = seekMap;
    }

    public void onTrackCountFound(int i2) {
        this.r = true;
        b();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x010f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0110  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onTrackDataFound(int r19, android.media.MediaParser.TrackData r20) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            android.media.MediaFormat r2 = r20.mediaFormat
            java.lang.String r3 = "chunk-index-int-sizes"
            java.nio.ByteBuffer r3 = r2.getByteBuffer(r3)
            r5 = 1
            if (r3 != 0) goto L_0x0013
            r2 = 0
            goto L_0x0078
        L_0x0013:
            java.nio.IntBuffer r3 = r3.asIntBuffer()
            java.lang.String r6 = "chunk-index-long-offsets"
            java.nio.ByteBuffer r6 = r2.getByteBuffer(r6)
            java.lang.Object r6 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r6)
            java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
            java.nio.LongBuffer r6 = r6.asLongBuffer()
            java.lang.String r7 = "chunk-index-long-us-durations"
            java.nio.ByteBuffer r7 = r2.getByteBuffer(r7)
            java.lang.Object r7 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r7)
            java.nio.ByteBuffer r7 = (java.nio.ByteBuffer) r7
            java.nio.LongBuffer r7 = r7.asLongBuffer()
            java.lang.String r8 = "chunk-index-long-us-times"
            java.nio.ByteBuffer r2 = r2.getByteBuffer(r8)
            java.lang.Object r2 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)
            java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
            java.nio.LongBuffer r2 = r2.asLongBuffer()
            int r8 = r3.remaining()
            int[] r8 = new int[r8]
            int r9 = r6.remaining()
            long[] r9 = new long[r9]
            int r10 = r7.remaining()
            long[] r10 = new long[r10]
            int r11 = r2.remaining()
            long[] r11 = new long[r11]
            r3.get(r8)
            r6.get(r9)
            r7.get(r10)
            r2.get(r11)
            com.google.android.exoplayer2.extractor.ChunkIndex r2 = new com.google.android.exoplayer2.extractor.ChunkIndex
            r2.<init>(r8, r9, r10, r11)
            r0.m = r2
            com.google.android.exoplayer2.extractor.ExtractorOutput r3 = r0.i
            r3.seekMap(r2)
            r2 = 1
        L_0x0078:
            if (r2 == 0) goto L_0x007b
            return
        L_0x007b:
            r18.a(r19)
            java.util.ArrayList<com.google.android.exoplayer2.extractor.TrackOutput> r2 = r0.a
            java.lang.Object r2 = r2.get(r1)
            com.google.android.exoplayer2.extractor.TrackOutput r2 = (com.google.android.exoplayer2.extractor.TrackOutput) r2
            r6 = 4
            r7 = 2
            java.lang.String r8 = "mime"
            if (r2 != 0) goto L_0x0111
            android.media.MediaFormat r2 = r20.mediaFormat
            java.lang.String r9 = "track-type-string"
            java.lang.String r2 = r2.getString(r9)
            if (r2 == 0) goto L_0x009a
            r9 = r2
            goto L_0x00a2
        L_0x009a:
            android.media.MediaFormat r9 = r20.mediaFormat
            java.lang.String r9 = r9.getString(r8)
        L_0x00a2:
            if (r9 != 0) goto L_0x00a5
            goto L_0x00f9
        L_0x00a5:
            int r10 = r9.hashCode()
            r11 = 3
            switch(r10) {
                case -450004177: goto L_0x00da;
                case -284840886: goto L_0x00cf;
                case 3556653: goto L_0x00c4;
                case 93166550: goto L_0x00b9;
                case 112202875: goto L_0x00ae;
                default: goto L_0x00ad;
            }
        L_0x00ad:
            goto L_0x00e5
        L_0x00ae:
            java.lang.String r10 = "video"
            boolean r10 = r9.equals(r10)
            if (r10 != 0) goto L_0x00b7
            goto L_0x00e5
        L_0x00b7:
            r10 = 4
            goto L_0x00e6
        L_0x00b9:
            java.lang.String r10 = "audio"
            boolean r10 = r9.equals(r10)
            if (r10 != 0) goto L_0x00c2
            goto L_0x00e5
        L_0x00c2:
            r10 = 3
            goto L_0x00e6
        L_0x00c4:
            java.lang.String r10 = "text"
            boolean r10 = r9.equals(r10)
            if (r10 != 0) goto L_0x00cd
            goto L_0x00e5
        L_0x00cd:
            r10 = 2
            goto L_0x00e6
        L_0x00cf:
            java.lang.String r10 = "unknown"
            boolean r10 = r9.equals(r10)
            if (r10 != 0) goto L_0x00d8
            goto L_0x00e5
        L_0x00d8:
            r10 = 1
            goto L_0x00e6
        L_0x00da:
            java.lang.String r10 = "metadata"
            boolean r10 = r9.equals(r10)
            if (r10 != 0) goto L_0x00e3
            goto L_0x00e5
        L_0x00e3:
            r10 = 0
            goto L_0x00e6
        L_0x00e5:
            r10 = -1
        L_0x00e6:
            if (r10 == 0) goto L_0x00fb
            if (r10 == r5) goto L_0x00f9
            if (r10 == r7) goto L_0x00fc
            if (r10 == r11) goto L_0x00f7
            if (r10 == r6) goto L_0x00f5
            int r11 = com.google.android.exoplayer2.util.MimeTypes.getTrackType(r9)
            goto L_0x00fc
        L_0x00f5:
            r11 = 2
            goto L_0x00fc
        L_0x00f7:
            r11 = 1
            goto L_0x00fc
        L_0x00f9:
            r11 = -1
            goto L_0x00fc
        L_0x00fb:
            r11 = 5
        L_0x00fc:
            int r9 = r0.g
            if (r11 != r9) goto L_0x0102
            r0.p = r1
        L_0x0102:
            com.google.android.exoplayer2.extractor.ExtractorOutput r9 = r0.i
            com.google.android.exoplayer2.extractor.TrackOutput r9 = r9.track(r1, r11)
            java.util.ArrayList<com.google.android.exoplayer2.extractor.TrackOutput> r10 = r0.a
            r10.set(r1, r9)
            if (r2 == 0) goto L_0x0110
            return
        L_0x0110:
            r2 = r9
        L_0x0111:
            android.media.MediaFormat r9 = r20.mediaFormat
            java.lang.String r8 = r9.getString(r8)
            int r10 = r9.getInteger("caption-service-number", -1)
            com.google.android.exoplayer2.Format$Builder r11 = new com.google.android.exoplayer2.Format$Builder
            r11.<init>()
            java.lang.String r12 = "crypto-mode-fourcc"
            java.lang.String r12 = r9.getString(r12)
            android.media.DrmInitData r13 = r20.drmInitData
            if (r13 != 0) goto L_0x0130
            r3 = 0
            goto L_0x0159
        L_0x0130:
            int r15 = r13.getSchemeInitDataCount()
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData[] r5 = new com.google.android.exoplayer2.drm.DrmInitData.SchemeData[r15]
            r6 = 0
        L_0x0137:
            if (r6 >= r15) goto L_0x0154
            android.media.DrmInitData$SchemeInitData r16 = r13.getSchemeInitDataAt(r6)
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData r7 = new com.google.android.exoplayer2.drm.DrmInitData$SchemeData
            java.util.UUID r14 = r16.uuid
            java.lang.String r4 = r16.mimeType
            byte[] r3 = r16.data
            r7.<init>(r14, r4, r3)
            r5[r6] = r7
            int r6 = r6 + 1
            r7 = 2
            goto L_0x0137
        L_0x0154:
            com.google.android.exoplayer2.drm.DrmInitData r3 = new com.google.android.exoplayer2.drm.DrmInitData
            r3.<init>((java.lang.String) r12, (com.google.android.exoplayer2.drm.DrmInitData.SchemeData[]) r5)
        L_0x0159:
            com.google.android.exoplayer2.Format$Builder r3 = r11.setDrmInitData(r3)
            java.lang.String r4 = r0.l
            com.google.android.exoplayer2.Format$Builder r3 = r3.setContainerMimeType(r4)
            int r4 = r9.getInteger("bitrate", -1)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setPeakBitrate(r4)
            int r4 = r9.getInteger("channel-count", -1)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setChannelCount(r4)
            java.lang.String r4 = "hdr-static-info"
            java.nio.ByteBuffer r4 = r9.getByteBuffer(r4)
            if (r4 == 0) goto L_0x0185
            int r5 = r4.remaining()
            byte[] r5 = new byte[r5]
            r4.get(r5)
            goto L_0x0186
        L_0x0185:
            r5 = 0
        L_0x0186:
            int r4 = r9.getInteger("color-transfer", -1)
            int r6 = r9.getInteger("color-range", -1)
            int r7 = r9.getInteger("color-standard", -1)
            if (r5 != 0) goto L_0x019e
            r11 = -1
            if (r4 != r11) goto L_0x019e
            if (r6 != r11) goto L_0x019e
            if (r7 == r11) goto L_0x019c
            goto L_0x019e
        L_0x019c:
            r14 = 0
            goto L_0x01a3
        L_0x019e:
            com.google.android.exoplayer2.video.ColorInfo r14 = new com.google.android.exoplayer2.video.ColorInfo
            r14.<init>(r7, r6, r4, r5)
        L_0x01a3:
            com.google.android.exoplayer2.Format$Builder r3 = r3.setColorInfo(r14)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setSampleMimeType(r8)
            java.lang.String r4 = "codecs-string"
            java.lang.String r4 = r9.getString(r4)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setCodecs(r4)
            float r4 = r9.getFloat("frame-rate", -1.0f)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setFrameRate(r4)
            int r4 = r9.getInteger("width", -1)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setWidth(r4)
            int r4 = r9.getInteger("height", -1)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setHeight(r4)
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r5 = 0
        L_0x01d3:
            int r6 = r5 + 1
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r11 = 15
            r7.<init>(r11)
            java.lang.String r11 = "csd-"
            r7.append(r11)
            r7.append(r5)
            java.lang.String r5 = r7.toString()
            java.nio.ByteBuffer r5 = r9.getByteBuffer(r5)
            if (r5 != 0) goto L_0x02c9
            com.google.android.exoplayer2.Format$Builder r3 = r3.setInitializationData(r4)
            java.lang.String r4 = "language"
            java.lang.String r4 = r9.getString(r4)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setLanguage(r4)
            int r4 = r9.getInteger("max-input-size", -1)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setMaxInputSize(r4)
            int r4 = r9.getInteger("exo-pcm-encoding", -1)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setPcmEncoding(r4)
            int r4 = r9.getInteger("rotation-degrees", 0)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setRotationDegrees(r4)
            int r4 = r9.getInteger("sample-rate", -1)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setSampleRate(r4)
            java.lang.String r4 = "is-autoselect"
            int r4 = r9.getInteger(r4, 0)
            if (r4 == 0) goto L_0x0226
            r6 = 4
            goto L_0x0227
        L_0x0226:
            r6 = 0
        L_0x0227:
            r7 = 0
            r4 = r6 | 0
            java.lang.String r5 = "is-default"
            int r5 = r9.getInteger(r5, 0)
            if (r5 == 0) goto L_0x0234
            r5 = 1
            goto L_0x0235
        L_0x0234:
            r5 = 0
        L_0x0235:
            r4 = r4 | r5
            java.lang.String r5 = "is-forced-subtitle"
            int r5 = r9.getInteger(r5, 0)
            if (r5 == 0) goto L_0x0241
            r17 = 2
            goto L_0x0243
        L_0x0241:
            r17 = 0
        L_0x0243:
            r4 = r4 | r17
            com.google.android.exoplayer2.Format$Builder r3 = r3.setSelectionFlags(r4)
            int r4 = r9.getInteger("encoder-delay", 0)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setEncoderDelay(r4)
            int r4 = r9.getInteger("encoder-padding", 0)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setEncoderPadding(r4)
            float r4 = r9.getFloat("pixel-width-height-ratio-float", 1.0f)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setPixelWidthHeightRatio(r4)
            long r4 = r9.getLong("subsample-offset-us-long", Long.MAX_VALUE)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setSubsampleOffsetUs(r4)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setAccessibilityChannel(r10)
            r4 = 0
        L_0x026e:
            java.util.List<com.google.android.exoplayer2.Format> r5 = r0.o
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x02ab
            java.util.List<com.google.android.exoplayer2.Format> r5 = r0.o
            java.lang.Object r5 = r5.get(r4)
            com.google.android.exoplayer2.Format r5 = (com.google.android.exoplayer2.Format) r5
            java.lang.String r6 = r5.p
            boolean r6 = com.google.android.exoplayer2.util.Util.areEqual(r6, r8)
            if (r6 == 0) goto L_0x02a8
            int r6 = r5.ah
            if (r6 != r10) goto L_0x02a8
            java.lang.String r4 = r5.g
            com.google.android.exoplayer2.Format$Builder r4 = r3.setLanguage(r4)
            int r6 = r5.i
            com.google.android.exoplayer2.Format$Builder r4 = r4.setRoleFlags(r6)
            int r6 = r5.h
            com.google.android.exoplayer2.Format$Builder r4 = r4.setSelectionFlags(r6)
            java.lang.String r6 = r5.f
            com.google.android.exoplayer2.Format$Builder r4 = r4.setLabel(r6)
            com.google.android.exoplayer2.metadata.Metadata r5 = r5.n
            r4.setMetadata(r5)
            goto L_0x02ab
        L_0x02a8:
            int r4 = r4 + 1
            goto L_0x026e
        L_0x02ab:
            com.google.android.exoplayer2.Format r3 = r3.build()
            com.google.android.exoplayer2.Format r4 = r0.h
            if (r4 == 0) goto L_0x02bc
            int r5 = r0.p
            if (r1 != r5) goto L_0x02bc
            com.google.android.exoplayer2.Format r4 = r3.withManifestFormatInfo(r4)
            goto L_0x02bd
        L_0x02bc:
            r4 = r3
        L_0x02bd:
            r2.format(r4)
            java.util.ArrayList<com.google.android.exoplayer2.Format> r2 = r0.b
            r2.set(r1, r3)
            r18.b()
            return
        L_0x02c9:
            r7 = 0
            int r11 = r5.remaining()
            byte[] r11 = new byte[r11]
            r5.get(r11)
            r4.add(r11)
            r5 = r6
            goto L_0x01d3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.mediaparser.OutputConsumerAdapterV30.onTrackDataFound(int, android.media.MediaParser$TrackData):void");
    }

    public void setExtractorOutput(ExtractorOutput extractorOutput) {
        this.i = extractorOutput;
    }

    public void setMuxedCaptionFormats(List<Format> list) {
        this.o = list;
    }

    public void setSampleTimestampUpperLimitFilterUs(long j2) {
        this.q = j2;
    }

    public void setSelectedParserName(String str) {
        String str2;
        String str3;
        str.getClass();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -2063506020:
                if (str.equals("android.media.mediaparser.Mp4Parser")) {
                    c2 = 0;
                    break;
                }
                break;
            case -1870824006:
                if (str.equals("android.media.mediaparser.OggParser")) {
                    c2 = 1;
                    break;
                }
                break;
            case -1566427438:
                if (str.equals("android.media.mediaparser.TsParser")) {
                    c2 = 2;
                    break;
                }
                break;
            case -900207883:
                if (str.equals("android.media.mediaparser.AdtsParser")) {
                    c2 = 3;
                    break;
                }
                break;
            case -589864617:
                if (str.equals("android.media.mediaparser.WavParser")) {
                    c2 = 4;
                    break;
                }
                break;
            case 52265814:
                if (str.equals("android.media.mediaparser.PsParser")) {
                    c2 = 5;
                    break;
                }
                break;
            case 116768877:
                if (str.equals("android.media.mediaparser.FragmentedMp4Parser")) {
                    c2 = 6;
                    break;
                }
                break;
            case 376876796:
                if (str.equals("android.media.mediaparser.Ac3Parser")) {
                    c2 = 7;
                    break;
                }
                break;
            case 703268017:
                if (str.equals("android.media.mediaparser.AmrParser")) {
                    c2 = 8;
                    break;
                }
                break;
            case 768643067:
                if (str.equals("android.media.mediaparser.FlacParser")) {
                    c2 = 9;
                    break;
                }
                break;
            case 965962719:
                if (str.equals("android.media.mediaparser.MatroskaParser")) {
                    c2 = 10;
                    break;
                }
                break;
            case 1264380477:
                if (str.equals("android.media.mediaparser.Ac4Parser")) {
                    c2 = 11;
                    break;
                }
                break;
            case 1343957595:
                if (str.equals("android.media.mediaparser.Mp3Parser")) {
                    c2 = 12;
                    break;
                }
                break;
            case 2063134683:
                if (str.equals("android.media.mediaparser.FlvParser")) {
                    c2 = 13;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 6:
                str2 = "video/mp4";
                break;
            case 1:
                str2 = "audio/ogg";
                break;
            case 2:
                str2 = "video/mp2t";
                break;
            case 3:
                str2 = "audio/mp4a-latm";
                break;
            case 4:
                str2 = "audio/raw";
                break;
            case 5:
                str2 = "video/mp2p";
                break;
            case 7:
                str2 = "audio/ac3";
                break;
            case 8:
                str2 = "audio/amr";
                break;
            case 9:
                str2 = "audio/flac";
                break;
            case 10:
                str2 = "video/webm";
                break;
            case 11:
                str2 = "audio/ac4";
                break;
            case 12:
                str2 = "audio/mpeg";
                break;
            case 13:
                str2 = "video/x-flv";
                break;
            default:
                if (str.length() != 0) {
                    str3 = "Illegal parser name: ".concat(str);
                } else {
                    str3 = new String("Illegal parser name: ");
                }
                throw new IllegalArgumentException(str3);
        }
        this.l = str2;
    }

    public void setTimestampAdjuster(TimestampAdjuster timestampAdjuster) {
        this.n = timestampAdjuster;
    }

    public OutputConsumerAdapterV30(@Nullable Format format, int i2, boolean z) {
        this.f = z;
        this.h = format;
        this.g = i2;
        this.a = new ArrayList<>();
        this.b = new ArrayList<>();
        this.c = new ArrayList<>();
        this.d = new ArrayList<>();
        this.e = new a();
        this.i = new DummyExtractorOutput();
        this.q = -9223372036854775807L;
        this.o = ImmutableList.of();
    }
}
