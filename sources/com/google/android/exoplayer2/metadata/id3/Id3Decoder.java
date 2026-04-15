package com.google.android.exoplayer2.metadata.id3;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.SimpleMetadataDecoder;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public final class Id3Decoder extends SimpleMetadataDecoder {
    public static final z6 b = new z6(10);
    @Nullable
    public final FramePredicate a;

    public interface FramePredicate {
        boolean evaluate(int i, int i2, int i3, int i4, int i5);
    }

    public static final class a {
        public final int a;
        public final boolean b;
        public final int c;

        public a(int i, boolean z, int i2) {
            this.a = i;
            this.b = z;
            this.c = i2;
        }
    }

    public Id3Decoder() {
        this((FramePredicate) null);
    }

    public static ApicFrame b(ParsableByteArray parsableByteArray, int i, int i2) throws UnsupportedEncodingException {
        int i3;
        String str;
        byte[] bArr;
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String p = p(readUnsignedByte);
        int i4 = i - 1;
        byte[] bArr2 = new byte[i4];
        parsableByteArray.readBytes(bArr2, 0, i4);
        if (i2 == 2) {
            String valueOf = String.valueOf(Ascii.toLowerCase(new String(bArr2, 0, 3, "ISO-8859-1")));
            if (valueOf.length() != 0) {
                str = "image/".concat(valueOf);
            } else {
                str = new String("image/");
            }
            if ("image/jpg".equals(str)) {
                str = "image/jpeg";
            }
            i3 = 2;
        } else {
            i3 = s(bArr2, 0);
            String lowerCase = Ascii.toLowerCase(new String(bArr2, 0, i3, "ISO-8859-1"));
            if (lowerCase.indexOf(47) != -1) {
                str = lowerCase;
            } else if (lowerCase.length() != 0) {
                str = "image/".concat(lowerCase);
            } else {
                str = new String("image/");
            }
        }
        byte b2 = bArr2[i3 + 1] & 255;
        int i5 = i3 + 2;
        int r = r(bArr2, i5, readUnsignedByte);
        String str2 = new String(bArr2, i5, r - i5, p);
        int o = o(readUnsignedByte) + r;
        if (i4 <= o) {
            bArr = Util.f;
        } else {
            bArr = Arrays.copyOfRange(bArr2, o, i4);
        }
        return new ApicFrame(str, str2, b2, bArr);
    }

    public static ChapterFrame c(ParsableByteArray parsableByteArray, int i, int i2, boolean z, int i3, @Nullable FramePredicate framePredicate) throws UnsupportedEncodingException {
        long j;
        long j2;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int position = parsableByteArray.getPosition();
        int s = s(parsableByteArray.getData(), position);
        String str = new String(parsableByteArray.getData(), position, s - position, "ISO-8859-1");
        parsableByteArray.setPosition(s + 1);
        int readInt = parsableByteArray.readInt();
        int readInt2 = parsableByteArray.readInt();
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        if (readUnsignedInt == 4294967295L) {
            j = -1;
        } else {
            j = readUnsignedInt;
        }
        long readUnsignedInt2 = parsableByteArray.readUnsignedInt();
        if (readUnsignedInt2 == 4294967295L) {
            j2 = -1;
        } else {
            j2 = readUnsignedInt2;
        }
        ArrayList arrayList = new ArrayList();
        int i4 = position + i;
        while (parsableByteArray.getPosition() < i4) {
            Id3Frame f = f(i2, parsableByteArray, z, i3, framePredicate);
            if (f != null) {
                arrayList.add(f);
            }
        }
        return new ChapterFrame(str, readInt, readInt2, j, j2, (Id3Frame[]) arrayList.toArray(new Id3Frame[0]));
    }

    public static ChapterTocFrame d(ParsableByteArray parsableByteArray, int i, int i2, boolean z, int i3, @Nullable FramePredicate framePredicate) throws UnsupportedEncodingException {
        boolean z2;
        boolean z3;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int position = parsableByteArray.getPosition();
        int s = s(parsableByteArray.getData(), position);
        String str = new String(parsableByteArray.getData(), position, s - position, "ISO-8859-1");
        parsableByteArray2.setPosition(s + 1);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if ((readUnsignedByte & 2) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if ((readUnsignedByte & 1) != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
        String[] strArr = new String[readUnsignedByte2];
        for (int i4 = 0; i4 < readUnsignedByte2; i4++) {
            int position2 = parsableByteArray.getPosition();
            int s2 = s(parsableByteArray.getData(), position2);
            strArr[i4] = new String(parsableByteArray.getData(), position2, s2 - position2, "ISO-8859-1");
            parsableByteArray2.setPosition(s2 + 1);
        }
        ArrayList arrayList = new ArrayList();
        int i5 = position + i;
        while (parsableByteArray.getPosition() < i5) {
            Id3Frame f = f(i2, parsableByteArray2, z, i3, framePredicate);
            if (f != null) {
                arrayList.add(f);
            }
        }
        return new ChapterTocFrame(str, z2, z3, strArr, (Id3Frame[]) arrayList.toArray(new Id3Frame[0]));
    }

    @Nullable
    public static CommentFrame e(int i, ParsableByteArray parsableByteArray) throws UnsupportedEncodingException {
        if (i < 4) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String p = p(readUnsignedByte);
        byte[] bArr = new byte[3];
        parsableByteArray.readBytes(bArr, 0, 3);
        String str = new String(bArr, 0, 3);
        int i2 = i - 4;
        byte[] bArr2 = new byte[i2];
        parsableByteArray.readBytes(bArr2, 0, i2);
        int r = r(bArr2, 0, readUnsignedByte);
        String str2 = new String(bArr2, 0, r, p);
        int o = o(readUnsignedByte) + r;
        return new CommentFrame(str, str2, j(p, bArr2, o, r(bArr2, o, readUnsignedByte)));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:130:0x018d, code lost:
        if (r13 == 67) goto L_0x018f;
     */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.metadata.id3.Id3Frame f(int r18, com.google.android.exoplayer2.util.ParsableByteArray r19, boolean r20, int r21, @androidx.annotation.Nullable com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate r22) {
        /*
            r0 = r18
            r7 = r19
            int r8 = r19.readUnsignedByte()
            int r9 = r19.readUnsignedByte()
            int r10 = r19.readUnsignedByte()
            r12 = 3
            if (r0 < r12) goto L_0x0019
            int r1 = r19.readUnsignedByte()
            r13 = r1
            goto L_0x001a
        L_0x0019:
            r13 = 0
        L_0x001a:
            r14 = 4
            if (r0 != r14) goto L_0x003c
            int r1 = r19.readUnsignedIntToInt()
            if (r20 != 0) goto L_0x003a
            r2 = r1 & 255(0xff, float:3.57E-43)
            int r3 = r1 >> 8
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r3 = r3 << 7
            r2 = r2 | r3
            int r3 = r1 >> 16
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r3 = r3 << 14
            r2 = r2 | r3
            int r1 = r1 >> 24
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 << 21
            r1 = r1 | r2
        L_0x003a:
            r15 = r1
            goto L_0x0048
        L_0x003c:
            if (r0 != r12) goto L_0x0043
            int r1 = r19.readUnsignedIntToInt()
            goto L_0x003a
        L_0x0043:
            int r1 = r19.readUnsignedInt24()
            goto L_0x003a
        L_0x0048:
            if (r0 < r12) goto L_0x0050
            int r1 = r19.readUnsignedShort()
            r6 = r1
            goto L_0x0051
        L_0x0050:
            r6 = 0
        L_0x0051:
            r16 = 0
            if (r8 != 0) goto L_0x0067
            if (r9 != 0) goto L_0x0067
            if (r10 != 0) goto L_0x0067
            if (r13 != 0) goto L_0x0067
            if (r15 != 0) goto L_0x0067
            if (r6 != 0) goto L_0x0067
            int r0 = r19.limit()
            r7.setPosition(r0)
            return r16
        L_0x0067:
            int r1 = r19.getPosition()
            int r5 = r1 + r15
            int r1 = r19.limit()
            java.lang.String r4 = "Id3Decoder"
            if (r5 <= r1) goto L_0x0082
            java.lang.String r0 = "Frame size exceeds remaining tag data"
            com.google.android.exoplayer2.util.Log.w(r4, r0)
            int r0 = r19.limit()
            r7.setPosition(r0)
            return r16
        L_0x0082:
            if (r22 == 0) goto L_0x009a
            r1 = r22
            r2 = r18
            r3 = r8
            r17 = r4
            r4 = r9
            r11 = r5
            r5 = r10
            r14 = r6
            r6 = r13
            boolean r1 = r1.evaluate(r2, r3, r4, r5, r6)
            if (r1 != 0) goto L_0x009e
            r7.setPosition(r11)
            return r16
        L_0x009a:
            r17 = r4
            r11 = r5
            r14 = r6
        L_0x009e:
            r1 = 1
            if (r0 != r12) goto L_0x00bb
            r2 = r14 & 128(0x80, float:1.794E-43)
            if (r2 == 0) goto L_0x00a7
            r2 = 1
            goto L_0x00a8
        L_0x00a7:
            r2 = 0
        L_0x00a8:
            r3 = r14 & 64
            if (r3 == 0) goto L_0x00ae
            r3 = 1
            goto L_0x00af
        L_0x00ae:
            r3 = 0
        L_0x00af:
            r4 = r14 & 32
            if (r4 == 0) goto L_0x00b5
            r4 = 1
            goto L_0x00b6
        L_0x00b5:
            r4 = 0
        L_0x00b6:
            r5 = r4
            r6 = 0
            r4 = r3
            r3 = r2
            goto L_0x00eb
        L_0x00bb:
            r2 = 4
            if (r0 != r2) goto L_0x00e3
            r2 = r14 & 64
            if (r2 == 0) goto L_0x00c4
            r4 = 1
            goto L_0x00c5
        L_0x00c4:
            r4 = 0
        L_0x00c5:
            r2 = r14 & 8
            if (r2 == 0) goto L_0x00cb
            r2 = 1
            goto L_0x00cc
        L_0x00cb:
            r2 = 0
        L_0x00cc:
            r3 = r14 & 4
            if (r3 == 0) goto L_0x00d2
            r3 = 1
            goto L_0x00d3
        L_0x00d2:
            r3 = 0
        L_0x00d3:
            r5 = r14 & 2
            if (r5 == 0) goto L_0x00d9
            r5 = 1
            goto L_0x00da
        L_0x00d9:
            r5 = 0
        L_0x00da:
            r6 = r14 & 1
            if (r6 == 0) goto L_0x00e7
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = 1
            goto L_0x00eb
        L_0x00e3:
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
        L_0x00e7:
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = 0
        L_0x00eb:
            if (r2 != 0) goto L_0x0236
            if (r4 == 0) goto L_0x00f1
            goto L_0x0236
        L_0x00f1:
            if (r5 == 0) goto L_0x00f8
            int r15 = r15 + -1
            r7.skipBytes(r1)
        L_0x00f8:
            if (r3 == 0) goto L_0x0100
            int r15 = r15 + -4
            r1 = 4
            r7.skipBytes(r1)
        L_0x0100:
            if (r6 == 0) goto L_0x0106
            int r15 = t(r15, r7)
        L_0x0106:
            r1 = 2
            r2 = 84
            r3 = 88
            if (r8 != r2) goto L_0x011b
            if (r9 != r3) goto L_0x011b
            if (r10 != r3) goto L_0x011b
            if (r0 == r1) goto L_0x0115
            if (r13 != r3) goto L_0x011b
        L_0x0115:
            com.google.android.exoplayer2.metadata.id3.TextInformationFrame r1 = l(r15, r7)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            goto L_0x01f7
        L_0x011b:
            if (r8 != r2) goto L_0x012e
            java.lang.String r1 = q(r0, r8, r9, r10, r13)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            com.google.android.exoplayer2.metadata.id3.TextInformationFrame r1 = k(r7, r15, r1)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            goto L_0x01f7
        L_0x0127:
            r0 = move-exception
            goto L_0x0232
        L_0x012a:
            r2 = r17
            goto L_0x0229
        L_0x012e:
            r4 = 87
            if (r8 != r4) goto L_0x0140
            if (r9 != r3) goto L_0x0140
            if (r10 != r3) goto L_0x0140
            if (r0 == r1) goto L_0x013a
            if (r13 != r3) goto L_0x0140
        L_0x013a:
            com.google.android.exoplayer2.metadata.id3.UrlLinkFrame r1 = n(r15, r7)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            goto L_0x01f7
        L_0x0140:
            if (r8 != r4) goto L_0x014c
            java.lang.String r1 = q(r0, r8, r9, r10, r13)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            com.google.android.exoplayer2.metadata.id3.UrlLinkFrame r1 = m(r7, r15, r1)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            goto L_0x01f7
        L_0x014c:
            r3 = 73
            r4 = 80
            if (r8 != r4) goto L_0x0162
            r5 = 82
            if (r9 != r5) goto L_0x0162
            if (r10 != r3) goto L_0x0162
            r5 = 86
            if (r13 != r5) goto L_0x0162
            com.google.android.exoplayer2.metadata.id3.PrivFrame r1 = i(r15, r7)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            goto L_0x01f7
        L_0x0162:
            r5 = 71
            r6 = 79
            if (r8 != r5) goto L_0x017a
            r5 = 69
            if (r9 != r5) goto L_0x017a
            if (r10 != r6) goto L_0x017a
            r5 = 66
            if (r13 == r5) goto L_0x0174
            if (r0 != r1) goto L_0x017a
        L_0x0174:
            com.google.android.exoplayer2.metadata.id3.GeobFrame r1 = g(r15, r7)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            goto L_0x01f7
        L_0x017a:
            r5 = 65
            r12 = 67
            if (r0 != r1) goto L_0x0187
            if (r8 != r4) goto L_0x0195
            if (r9 != r3) goto L_0x0195
            if (r10 != r12) goto L_0x0195
            goto L_0x018f
        L_0x0187:
            if (r8 != r5) goto L_0x0195
            if (r9 != r4) goto L_0x0195
            if (r10 != r3) goto L_0x0195
            if (r13 != r12) goto L_0x0195
        L_0x018f:
            com.google.android.exoplayer2.metadata.id3.ApicFrame r1 = b(r7, r15, r0)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            goto L_0x01f7
        L_0x0195:
            r3 = 77
            if (r8 != r12) goto L_0x01a6
            if (r9 != r6) goto L_0x01a6
            if (r10 != r3) goto L_0x01a6
            if (r13 == r3) goto L_0x01a1
            if (r0 != r1) goto L_0x01a6
        L_0x01a1:
            com.google.android.exoplayer2.metadata.id3.CommentFrame r1 = e(r15, r7)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            goto L_0x01f7
        L_0x01a6:
            if (r8 != r12) goto L_0x01c0
            r1 = 72
            if (r9 != r1) goto L_0x01c0
            if (r10 != r5) goto L_0x01c0
            if (r13 != r4) goto L_0x01c0
            r1 = r19
            r2 = r15
            r3 = r18
            r4 = r20
            r5 = r21
            r6 = r22
            com.google.android.exoplayer2.metadata.id3.ChapterFrame r1 = c(r1, r2, r3, r4, r5, r6)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            goto L_0x01f7
        L_0x01c0:
            if (r8 != r12) goto L_0x01d8
            if (r9 != r2) goto L_0x01d8
            if (r10 != r6) goto L_0x01d8
            if (r13 != r12) goto L_0x01d8
            r1 = r19
            r2 = r15
            r3 = r18
            r4 = r20
            r5 = r21
            r6 = r22
            com.google.android.exoplayer2.metadata.id3.ChapterTocFrame r1 = d(r1, r2, r3, r4, r5, r6)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            goto L_0x01f7
        L_0x01d8:
            if (r8 != r3) goto L_0x01e7
            r1 = 76
            if (r9 != r1) goto L_0x01e7
            if (r10 != r1) goto L_0x01e7
            if (r13 != r2) goto L_0x01e7
            com.google.android.exoplayer2.metadata.id3.MlltFrame r1 = h(r15, r7)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            goto L_0x01f7
        L_0x01e7:
            java.lang.String r1 = q(r0, r8, r9, r10, r13)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            byte[] r2 = new byte[r15]     // Catch:{ UnsupportedEncodingException -> 0x012a }
            r3 = 0
            r7.readBytes(r2, r3, r15)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            com.google.android.exoplayer2.metadata.id3.BinaryFrame r3 = new com.google.android.exoplayer2.metadata.id3.BinaryFrame     // Catch:{ UnsupportedEncodingException -> 0x012a }
            r3.<init>(r1, r2)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            r1 = r3
        L_0x01f7:
            if (r1 != 0) goto L_0x0225
            java.lang.String r0 = q(r0, r8, r9, r10, r13)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            java.lang.String r2 = java.lang.String.valueOf(r0)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            int r2 = r2.length()     // Catch:{ UnsupportedEncodingException -> 0x012a }
            int r2 = r2 + 50
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x012a }
            r3.<init>(r2)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            java.lang.String r2 = "Failed to decode frame: id="
            r3.append(r2)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            r3.append(r0)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            java.lang.String r0 = ", frameSize="
            r3.append(r0)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            r3.append(r15)     // Catch:{ UnsupportedEncodingException -> 0x012a }
            java.lang.String r0 = r3.toString()     // Catch:{ UnsupportedEncodingException -> 0x012a }
            r2 = r17
            com.google.android.exoplayer2.util.Log.w(r2, r0)     // Catch:{ UnsupportedEncodingException -> 0x0229 }
        L_0x0225:
            r7.setPosition(r11)
            return r1
        L_0x0229:
            java.lang.String r0 = "Unsupported character encoding"
            com.google.android.exoplayer2.util.Log.w(r2, r0)     // Catch:{ all -> 0x0127 }
            r7.setPosition(r11)
            return r16
        L_0x0232:
            r7.setPosition(r11)
            throw r0
        L_0x0236:
            r2 = r17
            java.lang.String r0 = "Skipping unsupported compressed or encrypted frame"
            com.google.android.exoplayer2.util.Log.w(r2, r0)
            r7.setPosition(r11)
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.id3.Id3Decoder.f(int, com.google.android.exoplayer2.util.ParsableByteArray, boolean, int, com.google.android.exoplayer2.metadata.id3.Id3Decoder$FramePredicate):com.google.android.exoplayer2.metadata.id3.Id3Frame");
    }

    public static GeobFrame g(int i, ParsableByteArray parsableByteArray) throws UnsupportedEncodingException {
        byte[] bArr;
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String p = p(readUnsignedByte);
        int i2 = i - 1;
        byte[] bArr2 = new byte[i2];
        parsableByteArray.readBytes(bArr2, 0, i2);
        int s = s(bArr2, 0);
        String str = new String(bArr2, 0, s, "ISO-8859-1");
        int i3 = s + 1;
        int r = r(bArr2, i3, readUnsignedByte);
        String j = j(p, bArr2, i3, r);
        int o = o(readUnsignedByte) + r;
        int r2 = r(bArr2, o, readUnsignedByte);
        String j2 = j(p, bArr2, o, r2);
        int o2 = o(readUnsignedByte) + r2;
        if (i2 <= o2) {
            bArr = Util.f;
        } else {
            bArr = Arrays.copyOfRange(bArr2, o2, i2);
        }
        return new GeobFrame(str, j, j2, bArr);
    }

    public static MlltFrame h(int i, ParsableByteArray parsableByteArray) {
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int readUnsignedInt24 = parsableByteArray.readUnsignedInt24();
        int readUnsignedInt242 = parsableByteArray.readUnsignedInt24();
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
        ParsableBitArray parsableBitArray = new ParsableBitArray();
        parsableBitArray.reset(parsableByteArray);
        int i2 = ((i - 10) * 8) / (readUnsignedByte + readUnsignedByte2);
        int[] iArr = new int[i2];
        int[] iArr2 = new int[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            int readBits = parsableBitArray.readBits(readUnsignedByte);
            int readBits2 = parsableBitArray.readBits(readUnsignedByte2);
            iArr[i3] = readBits;
            iArr2[i3] = readBits2;
        }
        return new MlltFrame(readUnsignedShort, readUnsignedInt24, readUnsignedInt242, iArr, iArr2);
    }

    public static PrivFrame i(int i, ParsableByteArray parsableByteArray) throws UnsupportedEncodingException {
        byte[] bArr;
        byte[] bArr2 = new byte[i];
        parsableByteArray.readBytes(bArr2, 0, i);
        int s = s(bArr2, 0);
        String str = new String(bArr2, 0, s, "ISO-8859-1");
        int i2 = s + 1;
        if (i <= i2) {
            bArr = Util.f;
        } else {
            bArr = Arrays.copyOfRange(bArr2, i2, i);
        }
        return new PrivFrame(str, bArr);
    }

    public static String j(String str, byte[] bArr, int i, int i2) throws UnsupportedEncodingException {
        if (i2 <= i || i2 > bArr.length) {
            return "";
        }
        return new String(bArr, i, i2 - i, str);
    }

    @Nullable
    public static TextInformationFrame k(ParsableByteArray parsableByteArray, int i, String str) throws UnsupportedEncodingException {
        if (i < 1) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String p = p(readUnsignedByte);
        int i2 = i - 1;
        byte[] bArr = new byte[i2];
        parsableByteArray.readBytes(bArr, 0, i2);
        return new TextInformationFrame(str, (String) null, new String(bArr, 0, r(bArr, 0, readUnsignedByte), p));
    }

    @Nullable
    public static TextInformationFrame l(int i, ParsableByteArray parsableByteArray) throws UnsupportedEncodingException {
        if (i < 1) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String p = p(readUnsignedByte);
        int i2 = i - 1;
        byte[] bArr = new byte[i2];
        parsableByteArray.readBytes(bArr, 0, i2);
        int r = r(bArr, 0, readUnsignedByte);
        String str = new String(bArr, 0, r, p);
        int o = o(readUnsignedByte) + r;
        return new TextInformationFrame("TXXX", str, j(p, bArr, o, r(bArr, o, readUnsignedByte)));
    }

    public static UrlLinkFrame m(ParsableByteArray parsableByteArray, int i, String str) throws UnsupportedEncodingException {
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        return new UrlLinkFrame(str, (String) null, new String(bArr, 0, s(bArr, 0), "ISO-8859-1"));
    }

    @Nullable
    public static UrlLinkFrame n(int i, ParsableByteArray parsableByteArray) throws UnsupportedEncodingException {
        if (i < 1) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String p = p(readUnsignedByte);
        int i2 = i - 1;
        byte[] bArr = new byte[i2];
        parsableByteArray.readBytes(bArr, 0, i2);
        int r = r(bArr, 0, readUnsignedByte);
        String str = new String(bArr, 0, r, p);
        int o = o(readUnsignedByte) + r;
        return new UrlLinkFrame("WXXX", str, j("ISO-8859-1", bArr, o, s(bArr, o)));
    }

    public static int o(int i) {
        return (i == 0 || i == 3) ? 1 : 2;
    }

    public static String p(int i) {
        return i != 1 ? i != 2 ? i != 3 ? "ISO-8859-1" : "UTF-8" : "UTF-16BE" : "UTF-16";
    }

    public static String q(int i, int i2, int i3, int i4, int i5) {
        if (i == 2) {
            return String.format(Locale.US, "%c%c%c", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)});
        }
        return String.format(Locale.US, "%c%c%c%c", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5)});
    }

    public static int r(byte[] bArr, int i, int i2) {
        int s = s(bArr, i);
        if (i2 == 0 || i2 == 3) {
            return s;
        }
        while (s < bArr.length - 1) {
            if (s % 2 == 0 && bArr[s + 1] == 0) {
                return s;
            }
            s = s(bArr, s + 1);
        }
        return bArr.length;
    }

    public static int s(byte[] bArr, int i) {
        while (i < bArr.length) {
            if (bArr[i] == 0) {
                return i;
            }
            i++;
        }
        return bArr.length;
    }

    public static int t(int i, ParsableByteArray parsableByteArray) {
        byte[] data = parsableByteArray.getData();
        int position = parsableByteArray.getPosition();
        int i2 = position;
        while (true) {
            int i3 = i2 + 1;
            if (i3 >= position + i) {
                return i;
            }
            if ((data[i2] & 255) == 255 && data[i3] == 0) {
                System.arraycopy(data, i2 + 2, data, i3, (i - (i2 - position)) - 2);
                i--;
            }
            i2 = i3;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0076, code lost:
        if ((r10 & 1) != 0) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0084, code lost:
        if ((r10 & 128) != 0) goto L_0x0089;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean u(com.google.android.exoplayer2.util.ParsableByteArray r18, int r19, int r20, boolean r21) {
        /*
            r1 = r18
            r0 = r19
            int r2 = r18.getPosition()
        L_0x0008:
            int r3 = r18.bytesLeft()     // Catch:{ all -> 0x00ad }
            r4 = 1
            r5 = r20
            if (r3 < r5) goto L_0x00a9
            r3 = 0
            r6 = 3
            if (r0 < r6) goto L_0x0022
            int r7 = r18.readInt()     // Catch:{ all -> 0x00ad }
            long r8 = r18.readUnsignedInt()     // Catch:{ all -> 0x00ad }
            int r10 = r18.readUnsignedShort()     // Catch:{ all -> 0x00ad }
            goto L_0x002c
        L_0x0022:
            int r7 = r18.readUnsignedInt24()     // Catch:{ all -> 0x00ad }
            int r8 = r18.readUnsignedInt24()     // Catch:{ all -> 0x00ad }
            long r8 = (long) r8
            r10 = 0
        L_0x002c:
            r11 = 0
            if (r7 != 0) goto L_0x003a
            int r7 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1))
            if (r7 != 0) goto L_0x003a
            if (r10 != 0) goto L_0x003a
            r1.setPosition(r2)
            return r4
        L_0x003a:
            r7 = 4
            if (r0 != r7) goto L_0x006b
            if (r21 != 0) goto L_0x006b
            r13 = 8421504(0x808080, double:4.160776E-317)
            long r13 = r13 & r8
            int r15 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r15 == 0) goto L_0x004b
            r1.setPosition(r2)
            return r3
        L_0x004b:
            r11 = 255(0xff, double:1.26E-321)
            long r13 = r8 & r11
            r15 = 8
            long r15 = r8 >> r15
            long r15 = r15 & r11
            r17 = 7
            long r15 = r15 << r17
            long r13 = r13 | r15
            r15 = 16
            long r15 = r8 >> r15
            long r15 = r15 & r11
            r17 = 14
            long r15 = r15 << r17
            long r13 = r13 | r15
            r15 = 24
            long r8 = r8 >> r15
            long r8 = r8 & r11
            r11 = 21
            long r8 = r8 << r11
            long r8 = r8 | r13
        L_0x006b:
            if (r0 != r7) goto L_0x0079
            r6 = r10 & 64
            if (r6 == 0) goto L_0x0073
            r6 = 1
            goto L_0x0074
        L_0x0073:
            r6 = 0
        L_0x0074:
            r7 = r10 & 1
            if (r7 == 0) goto L_0x0088
            goto L_0x0089
        L_0x0079:
            if (r0 != r6) goto L_0x0087
            r6 = r10 & 32
            if (r6 == 0) goto L_0x0081
            r6 = 1
            goto L_0x0082
        L_0x0081:
            r6 = 0
        L_0x0082:
            r7 = r10 & 128(0x80, float:1.794E-43)
            if (r7 == 0) goto L_0x0088
            goto L_0x0089
        L_0x0087:
            r6 = 0
        L_0x0088:
            r4 = 0
        L_0x0089:
            if (r4 == 0) goto L_0x008d
            int r6 = r6 + 4
        L_0x008d:
            long r6 = (long) r6
            int r4 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r4 >= 0) goto L_0x0096
            r1.setPosition(r2)
            return r3
        L_0x0096:
            int r4 = r18.bytesLeft()     // Catch:{ all -> 0x00ad }
            long r6 = (long) r4
            int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r4 >= 0) goto L_0x00a3
            r1.setPosition(r2)
            return r3
        L_0x00a3:
            int r3 = (int) r8
            r1.skipBytes(r3)     // Catch:{ all -> 0x00ad }
            goto L_0x0008
        L_0x00a9:
            r1.setPosition(r2)
            return r4
        L_0x00ad:
            r0 = move-exception
            r1.setPosition(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.id3.Id3Decoder.u(com.google.android.exoplayer2.util.ParsableByteArray, int, int, boolean):boolean");
    }

    @Nullable
    public final Metadata a(MetadataInputBuffer metadataInputBuffer, ByteBuffer byteBuffer) {
        return decode(byteBuffer.array(), byteBuffer.limit());
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x00c1 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00c2  */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.metadata.Metadata decode(byte[] r13, int r14) {
        /*
            r12 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            com.google.android.exoplayer2.util.ParsableByteArray r1 = new com.google.android.exoplayer2.util.ParsableByteArray
            r1.<init>(r13, r14)
            int r13 = r1.bytesLeft()
            r14 = 2
            java.lang.String r2 = "Id3Decoder"
            r3 = 10
            r4 = 4
            r5 = 0
            r6 = 0
            r7 = 1
            if (r13 >= r3) goto L_0x0020
            java.lang.String r13 = "Data too short to be an ID3 tag"
            com.google.android.exoplayer2.util.Log.w(r2, r13)
            goto L_0x00be
        L_0x0020:
            int r13 = r1.readUnsignedInt24()
            r8 = 4801587(0x494433, float:6.728456E-39)
            if (r13 == r8) goto L_0x0052
            java.lang.Object[] r8 = new java.lang.Object[r7]
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)
            r8[r6] = r13
            java.lang.String r13 = "%06X"
            java.lang.String r13 = java.lang.String.format(r13, r8)
            java.lang.String r13 = java.lang.String.valueOf(r13)
            int r8 = r13.length()
            java.lang.String r9 = "Unexpected first three bytes of ID3 tag header: 0x"
            if (r8 == 0) goto L_0x0048
            java.lang.String r13 = r9.concat(r13)
            goto L_0x004d
        L_0x0048:
            java.lang.String r13 = new java.lang.String
            r13.<init>(r9)
        L_0x004d:
            com.google.android.exoplayer2.util.Log.w(r2, r13)
            goto L_0x00be
        L_0x0052:
            int r13 = r1.readUnsignedByte()
            r1.skipBytes(r7)
            int r8 = r1.readUnsignedByte()
            int r9 = r1.readSynchSafeInt()
            if (r13 != r14) goto L_0x0072
            r10 = r8 & 64
            if (r10 == 0) goto L_0x0069
            r10 = 1
            goto L_0x006a
        L_0x0069:
            r10 = 0
        L_0x006a:
            if (r10 == 0) goto L_0x00a8
            java.lang.String r13 = "Skipped ID3 tag with majorVersion=2 and undefined compression scheme"
            com.google.android.exoplayer2.util.Log.w(r2, r13)
            goto L_0x00be
        L_0x0072:
            r10 = 3
            if (r13 != r10) goto L_0x0088
            r10 = r8 & 64
            if (r10 == 0) goto L_0x007b
            r10 = 1
            goto L_0x007c
        L_0x007b:
            r10 = 0
        L_0x007c:
            if (r10 == 0) goto L_0x00a8
            int r10 = r1.readInt()
            r1.skipBytes(r10)
            int r10 = r10 + r4
            int r9 = r9 - r10
            goto L_0x00a8
        L_0x0088:
            if (r13 != r4) goto L_0x00b7
            r10 = r8 & 64
            if (r10 == 0) goto L_0x0090
            r10 = 1
            goto L_0x0091
        L_0x0090:
            r10 = 0
        L_0x0091:
            if (r10 == 0) goto L_0x009d
            int r10 = r1.readSynchSafeInt()
            int r11 = r10 + -4
            r1.skipBytes(r11)
            int r9 = r9 - r10
        L_0x009d:
            r10 = r8 & 16
            if (r10 == 0) goto L_0x00a3
            r10 = 1
            goto L_0x00a4
        L_0x00a3:
            r10 = 0
        L_0x00a4:
            if (r10 == 0) goto L_0x00a8
            int r9 = r9 + -10
        L_0x00a8:
            if (r13 >= r4) goto L_0x00b0
            r8 = r8 & 128(0x80, float:1.794E-43)
            if (r8 == 0) goto L_0x00b0
            r8 = 1
            goto L_0x00b1
        L_0x00b0:
            r8 = 0
        L_0x00b1:
            com.google.android.exoplayer2.metadata.id3.Id3Decoder$a r10 = new com.google.android.exoplayer2.metadata.id3.Id3Decoder$a
            r10.<init>(r13, r8, r9)
            goto L_0x00bf
        L_0x00b7:
            r8 = 57
            java.lang.String r9 = "Skipped ID3 tag with unsupported majorVersion="
            defpackage.y2.t(r8, r9, r13, r2)
        L_0x00be:
            r10 = r5
        L_0x00bf:
            if (r10 != 0) goto L_0x00c2
            return r5
        L_0x00c2:
            int r13 = r1.getPosition()
            int r8 = r10.a
            if (r8 != r14) goto L_0x00cb
            r3 = 6
        L_0x00cb:
            int r14 = r10.c
            boolean r9 = r10.b
            if (r9 == 0) goto L_0x00d5
            int r14 = t(r14, r1)
        L_0x00d5:
            int r13 = r13 + r14
            r1.setLimit(r13)
            boolean r13 = u(r1, r8, r3, r6)
            if (r13 != 0) goto L_0x00f1
            if (r8 != r4) goto L_0x00e9
            boolean r13 = u(r1, r4, r3, r7)
            if (r13 == 0) goto L_0x00e9
            r6 = 1
            goto L_0x00f1
        L_0x00e9:
            r13 = 56
            java.lang.String r14 = "Failed to validate ID3 tag with majorVersion="
            defpackage.y2.t(r13, r14, r8, r2)
            return r5
        L_0x00f1:
            int r13 = r1.bytesLeft()
            if (r13 < r3) goto L_0x0103
            com.google.android.exoplayer2.metadata.id3.Id3Decoder$FramePredicate r13 = r12.a
            com.google.android.exoplayer2.metadata.id3.Id3Frame r13 = f(r8, r1, r6, r3, r13)
            if (r13 == 0) goto L_0x00f1
            r0.add(r13)
            goto L_0x00f1
        L_0x0103:
            com.google.android.exoplayer2.metadata.Metadata r13 = new com.google.android.exoplayer2.metadata.Metadata
            r13.<init>((java.util.List<? extends com.google.android.exoplayer2.metadata.Metadata.Entry>) r0)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.id3.Id3Decoder.decode(byte[], int):com.google.android.exoplayer2.metadata.Metadata");
    }

    public Id3Decoder(@Nullable FramePredicate framePredicate) {
        this.a = framePredicate;
    }
}
