package com.google.android.exoplayer2.extractor.mp3;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.Id3Peeker;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.EOFException;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Mp3Extractor implements Extractor {
    public static final f2 u = new f2(28);
    public final int a;
    public final long b;
    public final ParsableByteArray c;
    public final MpegAudioUtil.Header d;
    public final GaplessInfoHolder e;
    public final Id3Peeker f;
    public final DummyTrackOutput g;
    public ExtractorOutput h;
    public TrackOutput i;
    public TrackOutput j;
    public int k;
    @Nullable
    public Metadata l;
    public long m;
    public long n;
    public long o;
    public int p;
    public Seeker q;
    public boolean r;
    public boolean s;
    public long t;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public Mp3Extractor() {
        this(0);
    }

    public static long b(@Nullable Metadata metadata) {
        if (metadata == null) {
            return -9223372036854775807L;
        }
        int length = metadata.length();
        for (int i2 = 0; i2 < length; i2++) {
            Metadata.Entry entry = metadata.get(i2);
            if (entry instanceof TextInformationFrame) {
                TextInformationFrame textInformationFrame = (TextInformationFrame) entry;
                if (textInformationFrame.c.equals("TLEN")) {
                    return C.msToUs(Long.parseLong(textInformationFrame.g));
                }
            }
        }
        return -9223372036854775807L;
    }

    public final r0 a(ExtractorInput extractorInput) throws IOException {
        ParsableByteArray parsableByteArray = this.c;
        extractorInput.peekFully(parsableByteArray.getData(), 0, 4);
        parsableByteArray.setPosition(0);
        this.d.setForHeaderData(parsableByteArray.readInt());
        return new r0(extractorInput.getLength(), extractorInput.getPosition(), this.d);
    }

    public final boolean c(ExtractorInput extractorInput) throws IOException {
        Seeker seeker = this.q;
        if (seeker != null) {
            long dataEndPosition = seeker.getDataEndPosition();
            if (dataEndPosition != -1 && extractorInput.getPeekPosition() > dataEndPosition - 4) {
                return true;
            }
        }
        try {
            return !extractorInput.peekFully(this.c.getData(), 0, 4, true);
        } catch (EOFException unused) {
            return true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0073, code lost:
        if (r11 != false) goto L_0x0075;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean d(com.google.android.exoplayer2.extractor.ExtractorInput r18, boolean r19) throws java.io.IOException {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            if (r19 == 0) goto L_0x000a
            r2 = 32768(0x8000, float:4.5918E-41)
            goto L_0x000c
        L_0x000a:
            r2 = 131072(0x20000, float:1.83671E-40)
        L_0x000c:
            r18.resetPeekPosition()
            long r3 = r18.getPosition()
            r5 = 1
            r6 = 0
            r7 = 0
            r9 = 4
            int r10 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r10 != 0) goto L_0x0044
            int r3 = r0.a
            r3 = r3 & r9
            if (r3 != 0) goto L_0x0023
            r3 = 1
            goto L_0x0024
        L_0x0023:
            r3 = 0
        L_0x0024:
            if (r3 == 0) goto L_0x0028
            r3 = 0
            goto L_0x002a
        L_0x0028:
            f2 r3 = u
        L_0x002a:
            com.google.android.exoplayer2.extractor.Id3Peeker r4 = r0.f
            com.google.android.exoplayer2.metadata.Metadata r3 = r4.peekId3Data(r1, r3)
            r0.l = r3
            if (r3 == 0) goto L_0x0039
            com.google.android.exoplayer2.extractor.GaplessInfoHolder r4 = r0.e
            r4.setFromMetadata(r3)
        L_0x0039:
            long r3 = r18.getPeekPosition()
            int r4 = (int) r3
            if (r19 != 0) goto L_0x0045
            r1.skipFully(r4)
            goto L_0x0045
        L_0x0044:
            r4 = 0
        L_0x0045:
            r3 = 0
            r7 = 0
            r8 = 0
        L_0x0048:
            boolean r10 = r17.c(r18)
            if (r10 == 0) goto L_0x0057
            if (r7 <= 0) goto L_0x0051
            goto L_0x00aa
        L_0x0051:
            java.io.EOFException r1 = new java.io.EOFException
            r1.<init>()
            throw r1
        L_0x0057:
            com.google.android.exoplayer2.util.ParsableByteArray r10 = r0.c
            r10.setPosition(r6)
            int r10 = r10.readInt()
            if (r3 == 0) goto L_0x0075
            long r11 = (long) r3
            r13 = -128000(0xfffffffffffe0c00, float:NaN)
            r13 = r13 & r10
            long r13 = (long) r13
            r15 = -128000(0xfffffffffffe0c00, double:NaN)
            long r11 = r11 & r15
            int r15 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r15 != 0) goto L_0x0072
            r11 = 1
            goto L_0x0073
        L_0x0072:
            r11 = 0
        L_0x0073:
            if (r11 == 0) goto L_0x007c
        L_0x0075:
            int r11 = com.google.android.exoplayer2.audio.MpegAudioUtil.getFrameSize(r10)
            r12 = -1
            if (r11 != r12) goto L_0x009d
        L_0x007c:
            int r3 = r8 + 1
            if (r8 != r2) goto L_0x008b
            if (r19 == 0) goto L_0x0083
            return r6
        L_0x0083:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            java.lang.String r2 = "Searched too many bytes."
            r1.<init>((java.lang.String) r2)
            throw r1
        L_0x008b:
            if (r19 == 0) goto L_0x0096
            r18.resetPeekPosition()
            int r7 = r4 + r3
            r1.advancePeekPosition(r7)
            goto L_0x0099
        L_0x0096:
            r1.skipFully(r5)
        L_0x0099:
            r8 = r3
            r3 = 0
            r7 = 0
            goto L_0x0048
        L_0x009d:
            int r7 = r7 + 1
            if (r7 != r5) goto L_0x00a8
            com.google.android.exoplayer2.audio.MpegAudioUtil$Header r3 = r0.d
            r3.setForHeaderData(r10)
            r3 = r10
            goto L_0x00b7
        L_0x00a8:
            if (r7 != r9) goto L_0x00b7
        L_0x00aa:
            if (r19 == 0) goto L_0x00b1
            int r4 = r4 + r8
            r1.skipFully(r4)
            goto L_0x00b4
        L_0x00b1:
            r18.resetPeekPosition()
        L_0x00b4:
            r0.k = r3
            return r5
        L_0x00b7:
            int r11 = r11 + -4
            r1.advancePeekPosition(r11)
            goto L_0x0048
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp3.Mp3Extractor.d(com.google.android.exoplayer2.extractor.ExtractorInput, boolean):boolean");
    }

    public void disableSeeking() {
        this.r = true;
    }

    public void init(ExtractorOutput extractorOutput) {
        this.h = extractorOutput;
        TrackOutput track = extractorOutput.track(0, 1);
        this.i = track;
        this.j = track;
        this.h.endTracks();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v33, resolved type: kf} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v54, resolved type: t3} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r20v1, resolved type: t3} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v55, resolved type: kf} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v56, resolved type: kf} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v57, resolved type: kf} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v58, resolved type: r0} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v59, resolved type: md} */
    /* JADX WARNING: type inference failed for: r2v41 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x02c9  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x008a A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00d4  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010e  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0133  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0139  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0182  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0184  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x01be  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01c1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(com.google.android.exoplayer2.extractor.ExtractorInput r28, com.google.android.exoplayer2.extractor.PositionHolder r29) throws java.io.IOException {
        /*
            r27 = this;
            r0 = r27
            r1 = r28
            com.google.android.exoplayer2.extractor.TrackOutput r2 = r0.i
            com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r2)
            com.google.android.exoplayer2.extractor.ExtractorOutput r2 = r0.h
            com.google.android.exoplayer2.util.Util.castNonNull(r2)
            int r2 = r0.k
            com.google.android.exoplayer2.audio.MpegAudioUtil$Header r6 = r0.d
            r7 = 0
            if (r2 != 0) goto L_0x001c
            r0.d(r1, r7)     // Catch:{ EOFException -> 0x0019 }
            goto L_0x001c
        L_0x0019:
            goto L_0x0298
        L_0x001c:
            com.google.android.exoplayer2.extractor.mp3.Seeker r2 = r0.q
            r8 = 1
            com.google.android.exoplayer2.util.ParsableByteArray r9 = r0.c
            r10 = 0
            if (r2 != 0) goto L_0x01d5
            com.google.android.exoplayer2.util.ParsableByteArray r2 = new com.google.android.exoplayer2.util.ParsableByteArray
            int r12 = r6.c
            r2.<init>((int) r12)
            byte[] r12 = r2.getData()
            int r13 = r6.c
            r1.peekFully(r12, r7, r13)
            int r12 = r6.a
            r12 = r12 & r8
            r13 = 36
            if (r12 == 0) goto L_0x0043
            int r12 = r6.e
            if (r12 == r8) goto L_0x0047
            r14 = 36
            goto L_0x0050
        L_0x0043:
            int r12 = r6.e
            if (r12 == r8) goto L_0x004c
        L_0x0047:
            r12 = 21
            r14 = 21
            goto L_0x0050
        L_0x004c:
            r12 = 13
            r14 = 13
        L_0x0050:
            int r12 = r2.limit()
            int r15 = r14 + 4
            r8 = 1447187017(0x56425249, float:5.3414667E13)
            r3 = 1483304551(0x58696e67, float:1.02664153E15)
            r4 = 1231971951(0x496e666f, float:976486.94)
            if (r12 < r15) goto L_0x006e
            r2.setPosition(r14)
            int r12 = r2.readInt()
            if (r12 == r3) goto L_0x006c
            if (r12 != r4) goto L_0x006e
        L_0x006c:
            r15 = r12
            goto L_0x0084
        L_0x006e:
            int r12 = r2.limit()
            r15 = 40
            if (r12 < r15) goto L_0x0083
            r2.setPosition(r13)
            int r12 = r2.readInt()
            if (r12 != r8) goto L_0x0083
            r15 = 1447187017(0x56425249, float:5.3414667E13)
            goto L_0x0084
        L_0x0083:
            r15 = 0
        L_0x0084:
            r18 = 0
            com.google.android.exoplayer2.extractor.GaplessInfoHolder r12 = r0.e
            if (r15 == r3) goto L_0x00b4
            if (r15 != r4) goto L_0x008d
            goto L_0x00b4
        L_0x008d:
            if (r15 != r8) goto L_0x00aa
            long r3 = r28.getLength()
            long r14 = r28.getPosition()
            com.google.android.exoplayer2.audio.MpegAudioUtil$Header r8 = r0.d
            r19 = r12
            r12 = r3
            r16 = r8
            r17 = r2
            md r2 = defpackage.md.create(r12, r14, r16, r17)
            int r3 = r6.c
            r1.skipFully(r3)
            goto L_0x00b1
        L_0x00aa:
            r19 = r12
            r28.resetPeekPosition()
            r2 = r18
        L_0x00b1:
            r8 = r19
            goto L_0x0106
        L_0x00b4:
            r19 = r12
            long r12 = r28.getLength()
            long r16 = r28.getPosition()
            com.google.android.exoplayer2.audio.MpegAudioUtil$Header r3 = r0.d
            r8 = r14
            r5 = r15
            r14 = r16
            r16 = r3
            r17 = r2
            kf r2 = defpackage.kf.create(r12, r14, r16, r17)
            if (r2 == 0) goto L_0x00f1
            boolean r3 = r19.hasGaplessInfo()
            if (r3 != 0) goto L_0x00f1
            r28.resetPeekPosition()
            int r14 = r8 + 141
            r1.advancePeekPosition(r14)
            byte[] r3 = r9.getData()
            r8 = 3
            r1.peekFully(r3, r7, r8)
            r9.setPosition(r7)
            int r3 = r9.readUnsignedInt24()
            r8 = r19
            r8.setFromXingHeaderValue(r3)
            goto L_0x00f3
        L_0x00f1:
            r8 = r19
        L_0x00f3:
            int r3 = r6.c
            r1.skipFully(r3)
            if (r2 == 0) goto L_0x0106
            boolean r3 = r2.isSeekable()
            if (r3 != 0) goto L_0x0106
            if (r5 != r4) goto L_0x0106
            r0 r2 = r27.a(r28)
        L_0x0106:
            com.google.android.exoplayer2.metadata.Metadata r3 = r0.l
            long r4 = r28.getPosition()
            if (r3 == 0) goto L_0x012b
            int r12 = r3.length()
            r13 = 0
        L_0x0113:
            if (r13 >= r12) goto L_0x012b
            com.google.android.exoplayer2.metadata.Metadata$Entry r14 = r3.get(r13)
            boolean r15 = r14 instanceof com.google.android.exoplayer2.metadata.id3.MlltFrame
            if (r15 == 0) goto L_0x0128
            com.google.android.exoplayer2.metadata.id3.MlltFrame r14 = (com.google.android.exoplayer2.metadata.id3.MlltFrame) r14
            long r12 = b(r3)
            y6 r3 = defpackage.y6.create(r4, r14, r12)
            goto L_0x012d
        L_0x0128:
            int r13 = r13 + 1
            goto L_0x0113
        L_0x012b:
            r3 = r18
        L_0x012d:
            boolean r4 = r0.r
            int r5 = r0.a
            if (r4 == 0) goto L_0x0139
            com.google.android.exoplayer2.extractor.mp3.Seeker$UnseekableSeeker r2 = new com.google.android.exoplayer2.extractor.mp3.Seeker$UnseekableSeeker
            r2.<init>()
            goto L_0x0188
        L_0x0139:
            r4 = r5 & 2
            if (r4 == 0) goto L_0x016c
            if (r3 == 0) goto L_0x014c
            long r12 = r3.getDurationUs()
            long r2 = r3.getDataEndPosition()
        L_0x0147:
            r25 = r2
            r21 = r12
            goto L_0x0160
        L_0x014c:
            if (r2 == 0) goto L_0x0157
            long r12 = r2.getDurationUs()
            long r2 = r2.getDataEndPosition()
            goto L_0x0147
        L_0x0157:
            com.google.android.exoplayer2.metadata.Metadata r2 = r0.l
            long r12 = b(r2)
            r2 = -1
            goto L_0x0147
        L_0x0160:
            t3 r2 = new t3
            long r23 = r28.getPosition()
            r20 = r2
            r20.<init>(r21, r23, r25)
            goto L_0x0171
        L_0x016c:
            if (r3 == 0) goto L_0x016f
            goto L_0x0175
        L_0x016f:
            if (r2 == 0) goto L_0x0173
        L_0x0171:
            r3 = r2
            goto L_0x0175
        L_0x0173:
            r3 = r18
        L_0x0175:
            if (r3 == 0) goto L_0x0184
            boolean r2 = r3.isSeekable()
            if (r2 != 0) goto L_0x0182
            r2 = r5 & 1
            if (r2 == 0) goto L_0x0182
            goto L_0x0184
        L_0x0182:
            r2 = r3
            goto L_0x0188
        L_0x0184:
            r0 r2 = r27.a(r28)
        L_0x0188:
            r0.q = r2
            com.google.android.exoplayer2.extractor.ExtractorOutput r3 = r0.h
            r3.seekMap(r2)
            com.google.android.exoplayer2.extractor.TrackOutput r2 = r0.j
            com.google.android.exoplayer2.Format$Builder r3 = new com.google.android.exoplayer2.Format$Builder
            r3.<init>()
            java.lang.String r4 = r6.b
            com.google.android.exoplayer2.Format$Builder r3 = r3.setSampleMimeType(r4)
            r4 = 4096(0x1000, float:5.74E-42)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setMaxInputSize(r4)
            int r4 = r6.e
            com.google.android.exoplayer2.Format$Builder r3 = r3.setChannelCount(r4)
            int r4 = r6.d
            com.google.android.exoplayer2.Format$Builder r3 = r3.setSampleRate(r4)
            int r4 = r8.a
            com.google.android.exoplayer2.Format$Builder r3 = r3.setEncoderDelay(r4)
            int r4 = r8.b
            com.google.android.exoplayer2.Format$Builder r3 = r3.setEncoderPadding(r4)
            r4 = r5 & 4
            if (r4 == 0) goto L_0x01c1
            r4 = r18
            goto L_0x01c3
        L_0x01c1:
            com.google.android.exoplayer2.metadata.Metadata r4 = r0.l
        L_0x01c3:
            com.google.android.exoplayer2.Format$Builder r3 = r3.setMetadata(r4)
            com.google.android.exoplayer2.Format r3 = r3.build()
            r2.format(r3)
            long r2 = r28.getPosition()
            r0.o = r2
            goto L_0x01ea
        L_0x01d5:
            long r2 = r0.o
            int r4 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r4 == 0) goto L_0x01ea
            long r2 = r28.getPosition()
            long r4 = r0.o
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 >= 0) goto L_0x01ea
            long r4 = r4 - r2
            int r2 = (int) r4
            r1.skipFully(r2)
        L_0x01ea:
            int r2 = r0.p
            if (r2 != 0) goto L_0x028c
            r28.resetPeekPosition()
            boolean r2 = r27.c(r28)
            if (r2 == 0) goto L_0x01f9
            goto L_0x0298
        L_0x01f9:
            r9.setPosition(r7)
            int r2 = r9.readInt()
            int r3 = r0.k
            long r3 = (long) r3
            r5 = -128000(0xfffffffffffe0c00, float:NaN)
            r5 = r5 & r2
            long r8 = (long) r5
            r12 = -128000(0xfffffffffffe0c00, double:NaN)
            long r3 = r3 & r12
            int r5 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r5 != 0) goto L_0x0212
            r3 = 1
            goto L_0x0213
        L_0x0212:
            r3 = 0
        L_0x0213:
            if (r3 == 0) goto L_0x0285
            int r3 = com.google.android.exoplayer2.audio.MpegAudioUtil.getFrameSize(r2)
            r4 = -1
            if (r3 != r4) goto L_0x021d
            goto L_0x0285
        L_0x021d:
            r6.setForHeaderData(r2)
            long r2 = r0.m
            r4 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 != 0) goto L_0x0249
            com.google.android.exoplayer2.extractor.mp3.Seeker r2 = r0.q
            long r8 = r28.getPosition()
            long r2 = r2.getTimeUs(r8)
            r0.m = r2
            long r2 = r0.b
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 == 0) goto L_0x0249
            com.google.android.exoplayer2.extractor.mp3.Seeker r4 = r0.q
            long r4 = r4.getTimeUs(r10)
            long r8 = r0.m
            long r2 = r2 - r4
            long r2 = r2 + r8
            r0.m = r2
        L_0x0249:
            int r2 = r6.c
            r0.p = r2
            com.google.android.exoplayer2.extractor.mp3.Seeker r2 = r0.q
            boolean r3 = r2 instanceof defpackage.t3
            if (r3 == 0) goto L_0x028c
            t3 r2 = (defpackage.t3) r2
            long r3 = r0.n
            int r5 = r6.g
            long r8 = (long) r5
            long r3 = r3 + r8
            long r8 = r0.m
            r10 = 1000000(0xf4240, double:4.940656E-318)
            long r3 = r3 * r10
            int r5 = r6.d
            long r10 = (long) r5
            long r3 = r3 / r10
            long r3 = r3 + r8
            long r8 = r28.getPosition()
            int r5 = r6.c
            long r10 = (long) r5
            long r8 = r8 + r10
            r2.maybeAddSeekPoint(r3, r8)
            boolean r3 = r0.s
            if (r3 == 0) goto L_0x028c
            long r3 = r0.t
            boolean r2 = r2.isTimeUsInIndex(r3)
            if (r2 == 0) goto L_0x028c
            r0.s = r7
            com.google.android.exoplayer2.extractor.TrackOutput r2 = r0.i
            r0.j = r2
            goto L_0x028c
        L_0x0285:
            r2 = 1
            r1.skipFully(r2)
            r0.k = r7
            goto L_0x02c6
        L_0x028c:
            r2 = 1
            com.google.android.exoplayer2.extractor.TrackOutput r3 = r0.j
            int r4 = r0.p
            int r1 = r3.sampleData((com.google.android.exoplayer2.upstream.DataReader) r1, (int) r4, (boolean) r2)
            r2 = -1
            if (r1 != r2) goto L_0x029b
        L_0x0298:
            r1 = -1
            r7 = -1
            goto L_0x02c7
        L_0x029b:
            int r2 = r0.p
            int r2 = r2 - r1
            r0.p = r2
            if (r2 <= 0) goto L_0x02a3
            goto L_0x02c6
        L_0x02a3:
            com.google.android.exoplayer2.extractor.TrackOutput r8 = r0.j
            long r1 = r0.n
            long r3 = r0.m
            r9 = 1000000(0xf4240, double:4.940656E-318)
            long r1 = r1 * r9
            int r5 = r6.d
            long r9 = (long) r5
            long r1 = r1 / r9
            long r9 = r1 + r3
            r11 = 1
            int r12 = r6.c
            r13 = 0
            r14 = 0
            r8.sampleMetadata(r9, r11, r12, r13, r14)
            long r1 = r0.n
            int r3 = r6.g
            long r3 = (long) r3
            long r1 = r1 + r3
            r0.n = r1
            r0.p = r7
        L_0x02c6:
            r1 = -1
        L_0x02c7:
            if (r7 != r1) goto L_0x02f1
            com.google.android.exoplayer2.extractor.mp3.Seeker r1 = r0.q
            boolean r2 = r1 instanceof defpackage.t3
            if (r2 == 0) goto L_0x02f1
            long r2 = r0.n
            long r4 = r0.m
            r8 = 1000000(0xf4240, double:4.940656E-318)
            long r2 = r2 * r8
            int r6 = r6.d
            long r8 = (long) r6
            long r2 = r2 / r8
            long r2 = r2 + r4
            long r4 = r1.getDurationUs()
            int r1 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r1 == 0) goto L_0x02f1
            com.google.android.exoplayer2.extractor.mp3.Seeker r1 = r0.q
            r4 = r1
            t3 r4 = (defpackage.t3) r4
            r4.d = r2
            com.google.android.exoplayer2.extractor.ExtractorOutput r2 = r0.h
            r2.seekMap(r1)
        L_0x02f1:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp3.Mp3Extractor.read(com.google.android.exoplayer2.extractor.ExtractorInput, com.google.android.exoplayer2.extractor.PositionHolder):int");
    }

    public void release() {
    }

    public void seek(long j2, long j3) {
        this.k = 0;
        this.m = -9223372036854775807L;
        this.n = 0;
        this.p = 0;
        this.t = j3;
        Seeker seeker = this.q;
        if ((seeker instanceof t3) && !((t3) seeker).isTimeUsInIndex(j3)) {
            this.s = true;
            this.j = this.g;
        }
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        return d(extractorInput, true);
    }

    public Mp3Extractor(int i2) {
        this(i2, -9223372036854775807L);
    }

    public Mp3Extractor(int i2, long j2) {
        this.a = i2;
        this.b = j2;
        this.c = new ParsableByteArray(10);
        this.d = new MpegAudioUtil.Header();
        this.e = new GaplessInfoHolder();
        this.m = -9223372036854775807L;
        this.f = new Id3Peeker();
        DummyTrackOutput dummyTrackOutput = new DummyTrackOutput();
        this.g = dummyTrackOutput;
        this.j = dummyTrackOutput;
    }
}
