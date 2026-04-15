package com.google.android.exoplayer2.metadata.scte35;

import com.google.android.exoplayer2.metadata.SimpleMetadataDecoder;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;

public final class SpliceInfoDecoder extends SimpleMetadataDecoder {
    public final ParsableByteArray a = new ParsableByteArray();
    public final ParsableBitArray b = new ParsableBitArray();
    public TimestampAdjuster c;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: com.google.android.exoplayer2.metadata.scte35.PrivateCommand} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: com.google.android.exoplayer2.metadata.scte35.SpliceNullCommand} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: com.google.android.exoplayer2.metadata.scte35.PrivateCommand} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v4, resolved type: com.google.android.exoplayer2.metadata.scte35.SpliceInsertCommand} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: com.google.android.exoplayer2.metadata.scte35.PrivateCommand} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v21, resolved type: com.google.android.exoplayer2.metadata.scte35.TimeSignalCommand} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v22, resolved type: com.google.android.exoplayer2.metadata.scte35.PrivateCommand} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v25, resolved type: com.google.android.exoplayer2.metadata.scte35.PrivateCommand} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v12, resolved type: com.google.android.exoplayer2.metadata.scte35.PrivateCommand} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v26, resolved type: com.google.android.exoplayer2.metadata.scte35.PrivateCommand} */
    /* JADX WARNING: type inference failed for: r0v4, types: [com.google.android.exoplayer2.metadata.scte35.SpliceScheduleCommand] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.exoplayer2.metadata.Metadata a(com.google.android.exoplayer2.metadata.MetadataInputBuffer r57, java.nio.ByteBuffer r58) {
        /*
            r56 = this;
            r0 = r56
            r1 = r57
            com.google.android.exoplayer2.util.TimestampAdjuster r2 = r0.c
            if (r2 == 0) goto L_0x0012
            long r3 = r1.m
            long r5 = r2.getTimestampOffsetUs()
            int r2 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r2 == 0) goto L_0x0023
        L_0x0012:
            com.google.android.exoplayer2.util.TimestampAdjuster r2 = new com.google.android.exoplayer2.util.TimestampAdjuster
            long r3 = r1.i
            r2.<init>(r3)
            r0.c = r2
            long r3 = r1.i
            long r5 = r1.m
            long r3 = r3 - r5
            r2.adjustSampleTimestamp(r3)
        L_0x0023:
            byte[] r1 = r58.array()
            int r2 = r58.limit()
            com.google.android.exoplayer2.util.ParsableByteArray r3 = r0.a
            r3.reset(r1, r2)
            com.google.android.exoplayer2.util.ParsableBitArray r4 = r0.b
            r4.reset(r1, r2)
            r1 = 39
            r4.skipBits(r1)
            r1 = 1
            int r2 = r4.readBits(r1)
            long r5 = (long) r2
            r2 = 32
            long r5 = r5 << r2
            int r7 = r4.readBits(r2)
            long r7 = (long) r7
            long r13 = r5 | r7
            r5 = 20
            r4.skipBits(r5)
            r5 = 12
            int r5 = r4.readBits(r5)
            r6 = 8
            int r4 = r4.readBits(r6)
            r6 = 14
            r3.skipBytes(r6)
            if (r4 == 0) goto L_0x027b
            r7 = 255(0xff, float:3.57E-43)
            if (r4 == r7) goto L_0x0268
            r11 = 0
            r15 = 1
            r5 = 4
            r17 = 128(0x80, double:6.32E-322)
            r19 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r4 == r5) goto L_0x0174
            r5 = 5
            if (r4 == r5) goto L_0x008e
            r2 = 6
            if (r4 == r2) goto L_0x007d
            r2 = 0
            goto L_0x0280
        L_0x007d:
            com.google.android.exoplayer2.util.TimestampAdjuster r2 = r0.c
            long r3 = com.google.android.exoplayer2.metadata.scte35.TimeSignalCommand.a(r3, r13)
            long r7 = r2.adjustTsTimestamp(r3)
            com.google.android.exoplayer2.metadata.scte35.TimeSignalCommand r2 = new com.google.android.exoplayer2.metadata.scte35.TimeSignalCommand
            r2.<init>(r3, r7)
            goto L_0x0280
        L_0x008e:
            com.google.android.exoplayer2.util.TimestampAdjuster r4 = r0.c
            long r22 = r3.readUnsignedInt()
            int r5 = r3.readUnsignedByte()
            r5 = r5 & 128(0x80, float:1.794E-43)
            if (r5 == 0) goto L_0x009f
            r24 = 1
            goto L_0x00a1
        L_0x009f:
            r24 = 0
        L_0x00a1:
            java.util.List r5 = java.util.Collections.emptyList()
            if (r24 != 0) goto L_0x0150
            int r1 = r3.readUnsignedByte()
            r6 = r1 & 128(0x80, float:1.794E-43)
            if (r6 == 0) goto L_0x00b1
            r6 = 1
            goto L_0x00b2
        L_0x00b1:
            r6 = 0
        L_0x00b2:
            r21 = r1 & 64
            if (r21 == 0) goto L_0x00b9
            r21 = 1
            goto L_0x00bb
        L_0x00b9:
            r21 = 0
        L_0x00bb:
            r25 = r1 & 32
            if (r25 == 0) goto L_0x00c2
            r25 = 1
            goto L_0x00c4
        L_0x00c2:
            r25 = 0
        L_0x00c4:
            r1 = r1 & 16
            if (r1 == 0) goto L_0x00ca
            r1 = 1
            goto L_0x00cb
        L_0x00ca:
            r1 = 0
        L_0x00cb:
            if (r21 == 0) goto L_0x00d4
            if (r1 != 0) goto L_0x00d4
            long r26 = com.google.android.exoplayer2.metadata.scte35.TimeSignalCommand.a(r3, r13)
            goto L_0x00d6
        L_0x00d4:
            r26 = r19
        L_0x00d6:
            if (r21 != 0) goto L_0x0109
            int r5 = r3.readUnsignedByte()
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>(r5)
            r8 = 0
        L_0x00e2:
            if (r8 >= r5) goto L_0x0108
            int r31 = r3.readUnsignedByte()
            if (r1 != 0) goto L_0x00f1
            long r32 = com.google.android.exoplayer2.metadata.scte35.TimeSignalCommand.a(r3, r13)
            r9 = r32
            goto L_0x00f3
        L_0x00f1:
            r9 = r19
        L_0x00f3:
            com.google.android.exoplayer2.metadata.scte35.SpliceInsertCommand$ComponentSplice r2 = new com.google.android.exoplayer2.metadata.scte35.SpliceInsertCommand$ComponentSplice
            long r34 = r4.adjustTsTimestamp(r9)
            r30 = r2
            r32 = r9
            r30.<init>(r31, r32, r34)
            r7.add(r2)
            int r8 = r8 + 1
            r2 = 32
            goto L_0x00e2
        L_0x0108:
            r5 = r7
        L_0x0109:
            if (r25 == 0) goto L_0x012c
            int r2 = r3.readUnsignedByte()
            long r7 = (long) r2
            long r9 = r7 & r17
            int r2 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r2 == 0) goto L_0x0118
            r2 = 1
            goto L_0x0119
        L_0x0118:
            r2 = 0
        L_0x0119:
            long r7 = r7 & r15
            r9 = 32
            long r7 = r7 << r9
            long r9 = r3.readUnsignedInt()
            long r7 = r7 | r9
            r9 = 1000(0x3e8, double:4.94E-321)
            long r7 = r7 * r9
            r9 = 90
            long r7 = r7 / r9
            r19 = r7
            goto L_0x012d
        L_0x012c:
            r2 = 0
        L_0x012d:
            int r7 = r3.readUnsignedShort()
            int r8 = r3.readUnsignedByte()
            int r3 = r3.readUnsignedByte()
            r33 = r2
            r38 = r3
            r32 = r5
            r25 = r6
            r36 = r7
            r37 = r8
            r34 = r19
            r54 = r26
            r27 = r1
            r26 = r21
            r1 = r54
            goto L_0x0164
        L_0x0150:
            r32 = r5
            r1 = r19
            r34 = r1
            r25 = 0
            r26 = 0
            r27 = 0
            r33 = 0
            r36 = 0
            r37 = 0
            r38 = 0
        L_0x0164:
            com.google.android.exoplayer2.metadata.scte35.SpliceInsertCommand r3 = new com.google.android.exoplayer2.metadata.scte35.SpliceInsertCommand
            r21 = r3
            long r30 = r4.adjustTsTimestamp(r1)
            r28 = r1
            r21.<init>(r22, r24, r25, r26, r27, r28, r30, r32, r33, r34, r36, r37, r38)
            r2 = r3
            goto L_0x0280
        L_0x0174:
            int r1 = r3.readUnsignedByte()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>(r1)
            r4 = 0
        L_0x017e:
            if (r4 >= r1) goto L_0x0261
            long r40 = r3.readUnsignedInt()
            int r5 = r3.readUnsignedByte()
            r5 = r5 & 128(0x80, float:1.794E-43)
            if (r5 == 0) goto L_0x018f
            r42 = 1
            goto L_0x0191
        L_0x018f:
            r42 = 0
        L_0x0191:
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            if (r42 != 0) goto L_0x0235
            int r6 = r3.readUnsignedByte()
            r7 = r6 & 128(0x80, float:1.794E-43)
            if (r7 == 0) goto L_0x01a2
            r7 = 1
            goto L_0x01a3
        L_0x01a2:
            r7 = 0
        L_0x01a3:
            r8 = r6 & 64
            if (r8 == 0) goto L_0x01a9
            r8 = 1
            goto L_0x01aa
        L_0x01a9:
            r8 = 0
        L_0x01aa:
            r6 = r6 & 32
            if (r6 == 0) goto L_0x01b0
            r6 = 1
            goto L_0x01b1
        L_0x01b0:
            r6 = 0
        L_0x01b1:
            if (r8 == 0) goto L_0x01b8
            long r9 = r3.readUnsignedInt()
            goto L_0x01ba
        L_0x01b8:
            r9 = r19
        L_0x01ba:
            if (r8 != 0) goto L_0x01e2
            int r5 = r3.readUnsignedByte()
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>(r5)
            r14 = 0
        L_0x01c6:
            if (r14 >= r5) goto L_0x01e1
            int r15 = r3.readUnsignedByte()
            long r11 = r3.readUnsignedInt()
            com.google.android.exoplayer2.metadata.scte35.SpliceScheduleCommand$ComponentSplice r0 = new com.google.android.exoplayer2.metadata.scte35.SpliceScheduleCommand$ComponentSplice
            r0.<init>(r15, r11)
            r13.add(r0)
            int r14 = r14 + 1
            r0 = r56
            r11 = 0
            r15 = 1
            goto L_0x01c6
        L_0x01e1:
            r5 = r13
        L_0x01e2:
            if (r6 == 0) goto L_0x0209
            int r0 = r3.readUnsignedByte()
            long r11 = (long) r0
            long r13 = r11 & r17
            r15 = 0
            int r0 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r0 == 0) goto L_0x01f3
            r0 = 1
            goto L_0x01f4
        L_0x01f3:
            r0 = 0
        L_0x01f4:
            r13 = 1
            long r11 = r11 & r13
            r6 = 32
            long r11 = r11 << r6
            long r21 = r3.readUnsignedInt()
            long r11 = r11 | r21
            r21 = 1000(0x3e8, double:4.94E-321)
            long r11 = r11 * r21
            r23 = 90
            long r11 = r11 / r23
            goto L_0x0216
        L_0x0209:
            r6 = 32
            r13 = 1
            r15 = 0
            r21 = 1000(0x3e8, double:4.94E-321)
            r23 = 90
            r11 = r19
            r0 = 0
        L_0x0216:
            int r25 = r3.readUnsignedShort()
            int r26 = r3.readUnsignedByte()
            int r27 = r3.readUnsignedByte()
            r48 = r0
            r45 = r5
            r43 = r7
            r44 = r8
            r46 = r9
            r49 = r11
            r51 = r25
            r52 = r26
            r53 = r27
            goto L_0x024f
        L_0x0235:
            r13 = r15
            r6 = 32
            r21 = 1000(0x3e8, double:4.94E-321)
            r23 = 90
            r15 = r11
            r45 = r5
            r46 = r19
            r49 = r46
            r43 = 0
            r44 = 0
            r48 = 0
            r51 = 0
            r52 = 0
            r53 = 0
        L_0x024f:
            com.google.android.exoplayer2.metadata.scte35.SpliceScheduleCommand$Event r0 = new com.google.android.exoplayer2.metadata.scte35.SpliceScheduleCommand$Event
            r39 = r0
            r39.<init>(r40, r42, r43, r44, r45, r46, r48, r49, r51, r52, r53)
            r2.add(r0)
            int r4 = r4 + 1
            r0 = r56
            r11 = r15
            r15 = r13
            goto L_0x017e
        L_0x0261:
            com.google.android.exoplayer2.metadata.scte35.SpliceScheduleCommand r0 = new com.google.android.exoplayer2.metadata.scte35.SpliceScheduleCommand
            r0.<init>((java.util.ArrayList) r2)
            r2 = r0
            goto L_0x0280
        L_0x0268:
            long r10 = r3.readUnsignedInt()
            int r5 = r5 + -4
            byte[] r12 = new byte[r5]
            r0 = 0
            r3.readBytes(r12, r0, r5)
            com.google.android.exoplayer2.metadata.scte35.PrivateCommand r2 = new com.google.android.exoplayer2.metadata.scte35.PrivateCommand
            r9 = r2
            r9.<init>(r10, r12, r13)
            goto L_0x0280
        L_0x027b:
            com.google.android.exoplayer2.metadata.scte35.SpliceNullCommand r2 = new com.google.android.exoplayer2.metadata.scte35.SpliceNullCommand
            r2.<init>()
        L_0x0280:
            if (r2 != 0) goto L_0x028b
            com.google.android.exoplayer2.metadata.Metadata r0 = new com.google.android.exoplayer2.metadata.Metadata
            r1 = 0
            com.google.android.exoplayer2.metadata.Metadata$Entry[] r1 = new com.google.android.exoplayer2.metadata.Metadata.Entry[r1]
            r0.<init>((com.google.android.exoplayer2.metadata.Metadata.Entry[]) r1)
            goto L_0x0296
        L_0x028b:
            r1 = 0
            com.google.android.exoplayer2.metadata.Metadata r0 = new com.google.android.exoplayer2.metadata.Metadata
            r3 = 1
            com.google.android.exoplayer2.metadata.Metadata$Entry[] r3 = new com.google.android.exoplayer2.metadata.Metadata.Entry[r3]
            r3[r1] = r2
            r0.<init>((com.google.android.exoplayer2.metadata.Metadata.Entry[]) r3)
        L_0x0296:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.scte35.SpliceInfoDecoder.a(com.google.android.exoplayer2.metadata.MetadataInputBuffer, java.nio.ByteBuffer):com.google.android.exoplayer2.metadata.Metadata");
    }
}
