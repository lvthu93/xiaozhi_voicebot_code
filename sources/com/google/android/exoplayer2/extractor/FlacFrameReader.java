package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

public final class FlacFrameReader {

    public static final class SampleNumberHolder {
        public long a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x009b, code lost:
        if (r10 == r0.f) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00a6, code lost:
        if ((r18.readUnsignedByte() * 1000) == r3) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00b5, code lost:
        if (r4 == r3) goto L_0x00b7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean checkAndReadFrameHeader(com.google.android.exoplayer2.util.ParsableByteArray r18, com.google.android.exoplayer2.extractor.FlacStreamMetadata r19, int r20, com.google.android.exoplayer2.extractor.FlacFrameReader.SampleNumberHolder r21) {
        /*
            r0 = r19
            int r1 = r18.getPosition()
            long r2 = r18.readUnsignedInt()
            r4 = 16
            long r4 = r2 >>> r4
            r6 = r20
            long r6 = (long) r6
            r8 = 0
            int r9 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r9 == 0) goto L_0x0017
            return r8
        L_0x0017:
            r6 = 1
            long r4 = r4 & r6
            r9 = 1
            int r10 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r10 != 0) goto L_0x0021
            r4 = 1
            goto L_0x0022
        L_0x0021:
            r4 = 0
        L_0x0022:
            r5 = 12
            long r10 = r2 >> r5
            r12 = 15
            long r10 = r10 & r12
            int r11 = (int) r10
            r10 = 8
            long r14 = r2 >> r10
            long r14 = r14 & r12
            int r10 = (int) r14
            r14 = 4
            long r14 = r2 >> r14
            long r12 = r12 & r14
            int r13 = (int) r12
            long r14 = r2 >> r9
            r16 = 7
            long r14 = r14 & r16
            int r12 = (int) r14
            long r2 = r2 & r6
            int r14 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r14 != 0) goto L_0x0043
            r2 = 1
            goto L_0x0044
        L_0x0043:
            r2 = 0
        L_0x0044:
            r3 = 7
            if (r13 > r3) goto L_0x004d
            int r3 = r0.g
            int r3 = r3 - r9
            if (r13 != r3) goto L_0x0058
            goto L_0x0056
        L_0x004d:
            r3 = 10
            if (r13 > r3) goto L_0x0058
            int r3 = r0.g
            r6 = 2
            if (r3 != r6) goto L_0x0058
        L_0x0056:
            r3 = 1
            goto L_0x0059
        L_0x0058:
            r3 = 0
        L_0x0059:
            if (r3 == 0) goto L_0x00d5
            if (r12 != 0) goto L_0x005e
            goto L_0x0062
        L_0x005e:
            int r3 = r0.i
            if (r12 != r3) goto L_0x0064
        L_0x0062:
            r3 = 1
            goto L_0x0065
        L_0x0064:
            r3 = 0
        L_0x0065:
            if (r3 == 0) goto L_0x00d5
            if (r2 != 0) goto L_0x00d5
            long r2 = r18.readUtf8EncodedLong()     // Catch:{ NumberFormatException -> 0x007b }
            if (r4 == 0) goto L_0x0070
            goto L_0x0075
        L_0x0070:
            int r4 = r0.b
            long r6 = (long) r4
            long r2 = r2 * r6
        L_0x0075:
            r4 = r21
            r4.a = r2
            r2 = 1
            goto L_0x007c
        L_0x007b:
            r2 = 0
        L_0x007c:
            if (r2 == 0) goto L_0x00d5
            r2 = r18
            int r3 = readFrameBlockSizeSamplesFromKey(r2, r11)
            r4 = -1
            if (r3 == r4) goto L_0x008d
            int r4 = r0.b
            if (r3 > r4) goto L_0x008d
            r3 = 1
            goto L_0x008e
        L_0x008d:
            r3 = 0
        L_0x008e:
            if (r3 == 0) goto L_0x00d5
            int r3 = r0.e
            if (r10 != 0) goto L_0x0095
            goto L_0x00b7
        L_0x0095:
            r4 = 11
            if (r10 > r4) goto L_0x009e
            int r0 = r0.f
            if (r10 != r0) goto L_0x00b9
            goto L_0x00b7
        L_0x009e:
            if (r10 != r5) goto L_0x00a9
            int r0 = r18.readUnsignedByte()
            int r0 = r0 * 1000
            if (r0 != r3) goto L_0x00b9
            goto L_0x00b7
        L_0x00a9:
            r0 = 14
            if (r10 > r0) goto L_0x00b9
            int r4 = r18.readUnsignedShort()
            if (r10 != r0) goto L_0x00b5
            int r4 = r4 * 10
        L_0x00b5:
            if (r4 != r3) goto L_0x00b9
        L_0x00b7:
            r0 = 1
            goto L_0x00ba
        L_0x00b9:
            r0 = 0
        L_0x00ba:
            if (r0 == 0) goto L_0x00d5
            int r0 = r18.readUnsignedByte()
            int r3 = r18.getPosition()
            byte[] r2 = r18.getData()
            int r3 = r3 - r9
            int r1 = com.google.android.exoplayer2.util.Util.crc8(r2, r1, r3, r8)
            if (r0 != r1) goto L_0x00d1
            r0 = 1
            goto L_0x00d2
        L_0x00d1:
            r0 = 0
        L_0x00d2:
            if (r0 == 0) goto L_0x00d5
            r8 = 1
        L_0x00d5:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.FlacFrameReader.checkAndReadFrameHeader(com.google.android.exoplayer2.util.ParsableByteArray, com.google.android.exoplayer2.extractor.FlacStreamMetadata, int, com.google.android.exoplayer2.extractor.FlacFrameReader$SampleNumberHolder):boolean");
    }

    public static boolean checkFrameHeaderFromPeek(ExtractorInput extractorInput, FlacStreamMetadata flacStreamMetadata, int i, SampleNumberHolder sampleNumberHolder) throws IOException {
        long peekPosition = extractorInput.getPeekPosition();
        byte[] bArr = new byte[2];
        extractorInput.peekFully(bArr, 0, 2);
        if ((((bArr[0] & 255) << 8) | (bArr[1] & 255)) != i) {
            extractorInput.resetPeekPosition();
            extractorInput.advancePeekPosition((int) (peekPosition - extractorInput.getPosition()));
            return false;
        }
        ParsableByteArray parsableByteArray = new ParsableByteArray(16);
        System.arraycopy(bArr, 0, parsableByteArray.getData(), 0, 2);
        parsableByteArray.setLimit(ExtractorUtil.peekToLength(extractorInput, parsableByteArray.getData(), 2, 14));
        extractorInput.resetPeekPosition();
        extractorInput.advancePeekPosition((int) (peekPosition - extractorInput.getPosition()));
        return checkAndReadFrameHeader(parsableByteArray, flacStreamMetadata, i, sampleNumberHolder);
    }

    public static long getFirstSampleNumber(ExtractorInput extractorInput, FlacStreamMetadata flacStreamMetadata) throws IOException {
        boolean z;
        int i;
        extractorInput.resetPeekPosition();
        boolean z2 = true;
        extractorInput.advancePeekPosition(1);
        byte[] bArr = new byte[1];
        extractorInput.peekFully(bArr, 0, 1);
        if ((bArr[0] & 1) == 1) {
            z = true;
        } else {
            z = false;
        }
        extractorInput.advancePeekPosition(2);
        if (z) {
            i = 7;
        } else {
            i = 6;
        }
        ParsableByteArray parsableByteArray = new ParsableByteArray(i);
        parsableByteArray.setLimit(ExtractorUtil.peekToLength(extractorInput, parsableByteArray.getData(), 0, i));
        extractorInput.resetPeekPosition();
        SampleNumberHolder sampleNumberHolder = new SampleNumberHolder();
        try {
            long readUtf8EncodedLong = parsableByteArray.readUtf8EncodedLong();
            if (!z) {
                readUtf8EncodedLong *= (long) flacStreamMetadata.b;
            }
            sampleNumberHolder.a = readUtf8EncodedLong;
        } catch (NumberFormatException unused) {
            z2 = false;
        }
        if (z2) {
            return sampleNumberHolder.a;
        }
        throw new ParserException();
    }

    public static int readFrameBlockSizeSamplesFromKey(ParsableByteArray parsableByteArray, int i) {
        switch (i) {
            case 1:
                return 192;
            case 2:
            case 3:
            case 4:
            case 5:
                return 576 << (i - 2);
            case 6:
                return parsableByteArray.readUnsignedByte() + 1;
            case 7:
                return parsableByteArray.readUnsignedShort() + 1;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return 256 << (i - 8);
            default:
                return -1;
        }
    }
}
