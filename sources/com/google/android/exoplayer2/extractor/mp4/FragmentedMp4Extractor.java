package com.google.android.exoplayer2.extractor.mp4;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mp4.a;
import com.google.android.exoplayer2.metadata.emsg.EventMessageEncoder;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FragmentedMp4Extractor implements Extractor {
    public static final byte[] ai = {-94, 57, 79, 82, 90, -101, 79, 20, -94, 68, 108, 66, 124, 100, -115, -12};
    public static final Format aj = new Format.Builder().setSampleMimeType("application/x-emsg").build();
    public final int a;
    public int aa;
    public int ab;
    public int ac;
    public boolean ad;
    public ExtractorOutput ae;
    public TrackOutput[] af;
    public TrackOutput[] ag;
    public boolean ah;
    @Nullable
    public final Track b;
    public final List<Format> c;
    public final SparseArray<b> d;
    public final ParsableByteArray e;
    public final ParsableByteArray f;
    public final ParsableByteArray g;
    public final byte[] h;
    public final ParsableByteArray i;
    @Nullable
    public final TimestampAdjuster j;
    public final EventMessageEncoder k;
    public final ParsableByteArray l;
    public final ArrayDeque<a.C0014a> m;
    public final ArrayDeque<a> n;
    @Nullable
    public final TrackOutput o;
    public int p;
    public int q;
    public long r;
    public int s;
    @Nullable
    public ParsableByteArray t;
    public long u;
    public int v;
    public long w;
    public long x;
    public long y;
    @Nullable
    public b z;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static final class a {
        public final long a;
        public final int b;

        public a(long j, int i) {
            this.a = j;
            this.b = i;
        }
    }

    public static final class b {
        public final TrackOutput a;
        public final nc b = new nc();
        public final ParsableByteArray c = new ParsableByteArray();
        public pc d;
        public f1 e;
        public int f;
        public int g;
        public int h;
        public int i;
        public final ParsableByteArray j = new ParsableByteArray(1);
        public final ParsableByteArray k = new ParsableByteArray();
        public boolean l;

        public b(TrackOutput trackOutput, pc pcVar, f1 f1Var) {
            this.a = trackOutput;
            this.d = pcVar;
            this.e = f1Var;
            reset(pcVar, f1Var);
        }

        public int getCurrentSampleFlags() {
            int i2;
            if (!this.l) {
                i2 = this.d.g[this.f];
            } else if (this.b.k[this.f]) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            if (getEncryptionBoxIfEncrypted() != null) {
                return i2 | 1073741824;
            }
            return i2;
        }

        public long getCurrentSampleOffset() {
            if (!this.l) {
                return this.d.c[this.f];
            }
            return this.b.f[this.h];
        }

        public long getCurrentSamplePresentationTimeUs() {
            if (!this.l) {
                return this.d.f[this.f];
            }
            return this.b.getSamplePresentationTimeUs(this.f);
        }

        public int getCurrentSampleSize() {
            if (!this.l) {
                return this.d.d[this.f];
            }
            return this.b.h[this.f];
        }

        @Nullable
        public TrackEncryptionBox getEncryptionBoxIfEncrypted() {
            if (!this.l) {
                return null;
            }
            nc ncVar = this.b;
            int i2 = ((f1) Util.castNonNull(ncVar.a)).a;
            TrackEncryptionBox trackEncryptionBox = ncVar.n;
            if (trackEncryptionBox == null) {
                trackEncryptionBox = this.d.a.getSampleDescriptionEncryptionBox(i2);
            }
            if (trackEncryptionBox == null || !trackEncryptionBox.a) {
                return null;
            }
            return trackEncryptionBox;
        }

        public boolean next() {
            this.f++;
            if (!this.l) {
                return false;
            }
            int i2 = this.g + 1;
            this.g = i2;
            int[] iArr = this.b.g;
            int i3 = this.h;
            if (i2 != iArr[i3]) {
                return true;
            }
            this.h = i3 + 1;
            this.g = 0;
            return false;
        }

        public int outputSampleEncryptionData(int i2, int i3) {
            ParsableByteArray parsableByteArray;
            boolean z;
            int i4;
            TrackEncryptionBox encryptionBoxIfEncrypted = getEncryptionBoxIfEncrypted();
            if (encryptionBoxIfEncrypted == null) {
                return 0;
            }
            nc ncVar = this.b;
            int i5 = encryptionBoxIfEncrypted.d;
            if (i5 != 0) {
                parsableByteArray = ncVar.o;
            } else {
                byte[] bArr = (byte[]) Util.castNonNull(encryptionBoxIfEncrypted.e);
                int length = bArr.length;
                ParsableByteArray parsableByteArray2 = this.k;
                parsableByteArray2.reset(bArr, length);
                i5 = bArr.length;
                parsableByteArray = parsableByteArray2;
            }
            boolean sampleHasSubsampleEncryptionTable = ncVar.sampleHasSubsampleEncryptionTable(this.f);
            if (sampleHasSubsampleEncryptionTable || i3 != 0) {
                z = true;
            } else {
                z = false;
            }
            ParsableByteArray parsableByteArray3 = this.j;
            byte[] data = parsableByteArray3.getData();
            if (z) {
                i4 = 128;
            } else {
                i4 = 0;
            }
            data[0] = (byte) (i4 | i5);
            parsableByteArray3.setPosition(0);
            TrackOutput trackOutput = this.a;
            trackOutput.sampleData(parsableByteArray3, 1, 1);
            trackOutput.sampleData(parsableByteArray, i5, 1);
            if (!z) {
                return i5 + 1;
            }
            ParsableByteArray parsableByteArray4 = this.c;
            if (!sampleHasSubsampleEncryptionTable) {
                parsableByteArray4.reset(8);
                byte[] data2 = parsableByteArray4.getData();
                data2[0] = 0;
                data2[1] = 1;
                data2[2] = (byte) ((i3 >> 8) & 255);
                data2[3] = (byte) (i3 & 255);
                data2[4] = (byte) ((i2 >> 24) & 255);
                data2[5] = (byte) ((i2 >> 16) & 255);
                data2[6] = (byte) ((i2 >> 8) & 255);
                data2[7] = (byte) (i2 & 255);
                trackOutput.sampleData(parsableByteArray4, 8, 1);
                return i5 + 1 + 8;
            }
            ParsableByteArray parsableByteArray5 = ncVar.o;
            int readUnsignedShort = parsableByteArray5.readUnsignedShort();
            parsableByteArray5.skipBytes(-2);
            int i6 = (readUnsignedShort * 6) + 2;
            if (i3 != 0) {
                parsableByteArray4.reset(i6);
                byte[] data3 = parsableByteArray4.getData();
                parsableByteArray5.readBytes(data3, 0, i6);
                int i7 = (((data3[2] & 255) << 8) | (data3[3] & 255)) + i3;
                data3[2] = (byte) ((i7 >> 8) & 255);
                data3[3] = (byte) (i7 & 255);
            } else {
                parsableByteArray4 = parsableByteArray5;
            }
            trackOutput.sampleData(parsableByteArray4, i6, 1);
            return i5 + 1 + i6;
        }

        public void reset(pc pcVar, f1 f1Var) {
            this.d = pcVar;
            this.e = f1Var;
            this.a.format(pcVar.a.f);
            resetFragmentInfo();
        }

        public void resetFragmentInfo() {
            this.b.reset();
            this.f = 0;
            this.h = 0;
            this.g = 0;
            this.i = 0;
            this.l = false;
        }

        public void seek(long j2) {
            int i2 = this.f;
            while (true) {
                nc ncVar = this.b;
                if (i2 < ncVar.e && ncVar.getSamplePresentationTimeUs(i2) < j2) {
                    if (ncVar.k[i2]) {
                        this.i = i2;
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }

        public void skipSampleEncryptionData() {
            TrackEncryptionBox encryptionBoxIfEncrypted = getEncryptionBoxIfEncrypted();
            if (encryptionBoxIfEncrypted != null) {
                nc ncVar = this.b;
                ParsableByteArray parsableByteArray = ncVar.o;
                int i2 = encryptionBoxIfEncrypted.d;
                if (i2 != 0) {
                    parsableByteArray.skipBytes(i2);
                }
                if (ncVar.sampleHasSubsampleEncryptionTable(this.f)) {
                    parsableByteArray.skipBytes(parsableByteArray.readUnsignedShort() * 6);
                }
            }
        }

        public void updateDrmInitData(DrmInitData drmInitData) {
            String str;
            TrackEncryptionBox sampleDescriptionEncryptionBox = this.d.a.getSampleDescriptionEncryptionBox(((f1) Util.castNonNull(this.b.a)).a);
            if (sampleDescriptionEncryptionBox != null) {
                str = sampleDescriptionEncryptionBox.b;
            } else {
                str = null;
            }
            this.a.format(this.d.a.f.buildUpon().setDrmInitData(drmInitData.copyWithSchemeType(str)).build());
        }
    }

    public FragmentedMp4Extractor() {
        this(0);
    }

    @Nullable
    public static DrmInitData a(ArrayList arrayList) {
        int size = arrayList.size();
        ArrayList arrayList2 = null;
        for (int i2 = 0; i2 < size; i2++) {
            a.b bVar = (a.b) arrayList.get(i2);
            if (bVar.a == 1886614376) {
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                }
                byte[] data = bVar.b.getData();
                UUID parseUuid = PsshAtomUtil.parseUuid(data);
                if (parseUuid == null) {
                    Log.w("FragmentedMp4Extractor", "Skipped pssh atom (failed to extract uuid)");
                } else {
                    arrayList2.add(new DrmInitData.SchemeData(parseUuid, "video/mp4", data));
                }
            }
        }
        if (arrayList2 == null) {
            return null;
        }
        return new DrmInitData((List<DrmInitData.SchemeData>) arrayList2);
    }

    public static void b(ParsableByteArray parsableByteArray, int i2, nc ncVar) throws ParserException {
        boolean z2;
        parsableByteArray.setPosition(i2 + 8);
        int parseFullAtomFlags = a.parseFullAtomFlags(parsableByteArray.readInt());
        if ((parseFullAtomFlags & 1) == 0) {
            if ((parseFullAtomFlags & 2) != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            if (readUnsignedIntToInt == 0) {
                Arrays.fill(ncVar.m, 0, ncVar.e, false);
            } else if (readUnsignedIntToInt == ncVar.e) {
                Arrays.fill(ncVar.m, 0, readUnsignedIntToInt, z2);
                ncVar.initEncryptionData(parsableByteArray.bytesLeft());
                ncVar.fillEncryptionData(parsableByteArray);
            } else {
                int i3 = ncVar.e;
                StringBuilder sb = new StringBuilder(80);
                sb.append("Senc sample count ");
                sb.append(readUnsignedIntToInt);
                sb.append(" is different from fragment sample count");
                sb.append(i3);
                throw new ParserException(sb.toString());
            }
        } else {
            throw new ParserException("Overriding TrackEncryptionBox parameters is unsupported.");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:150:0x0395  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x0397  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x03a6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void c(long r49) throws com.google.android.exoplayer2.ParserException {
        /*
            r48 = this;
            r0 = r48
            r1 = r0
        L_0x0003:
            java.util.ArrayDeque<com.google.android.exoplayer2.extractor.mp4.a$a> r2 = r0.m
            boolean r3 = r2.isEmpty()
            if (r3 != 0) goto L_0x0757
            java.lang.Object r3 = r2.peek()
            com.google.android.exoplayer2.extractor.mp4.a$a r3 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r3
            long r3 = r3.b
            int r5 = (r3 > r49 ? 1 : (r3 == r49 ? 0 : -1))
            if (r5 != 0) goto L_0x0757
            java.lang.Object r3 = r2.pop()
            r4 = r3
            com.google.android.exoplayer2.extractor.mp4.a$a r4 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r4
            int r3 = r4.a
            android.util.SparseArray<com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b> r12 = r0.d
            java.util.ArrayList r5 = r4.c
            r6 = 12
            r7 = 1836019574(0x6d6f6f76, float:4.631354E27)
            com.google.android.exoplayer2.extractor.mp4.Track r8 = r0.b
            if (r3 != r7) goto L_0x0181
            if (r8 != 0) goto L_0x0031
            r2 = 1
            goto L_0x0032
        L_0x0031:
            r2 = 0
        L_0x0032:
            java.lang.String r3 = "Unexpected moov box."
            com.google.android.exoplayer2.util.Assertions.checkState(r2, r3)
            com.google.android.exoplayer2.drm.DrmInitData r8 = a(r5)
            r2 = 1836475768(0x6d766578, float:4.7659988E27)
            com.google.android.exoplayer2.extractor.mp4.a$a r2 = r4.getContainerAtomOfType(r2)
            java.lang.Object r2 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)
            com.google.android.exoplayer2.extractor.mp4.a$a r2 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r2
            android.util.SparseArray r3 = new android.util.SparseArray
            r3.<init>()
            java.util.ArrayList r5 = r2.c
            int r5 = r5.size()
            r9 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r7 = 0
        L_0x0059:
            if (r7 >= r5) goto L_0x00ca
            java.util.ArrayList r11 = r2.c
            java.lang.Object r11 = r11.get(r7)
            com.google.android.exoplayer2.extractor.mp4.a$b r11 = (com.google.android.exoplayer2.extractor.mp4.a.b) r11
            int r13 = r11.a
            r14 = 1953654136(0x74726578, float:7.6818474E31)
            com.google.android.exoplayer2.util.ParsableByteArray r11 = r11.b
            if (r13 != r14) goto L_0x00a4
            r11.setPosition(r6)
            int r6 = r11.readInt()
            int r13 = r11.readInt()
            int r13 = r13 + -1
            int r14 = r11.readInt()
            int r15 = r11.readInt()
            int r11 = r11.readInt()
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r16 = r2
            f1 r2 = new f1
            r2.<init>(r13, r14, r15, r11)
            android.util.Pair r2 = android.util.Pair.create(r6, r2)
            java.lang.Object r6 = r2.first
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            java.lang.Object r2 = r2.second
            f1 r2 = (defpackage.f1) r2
            r3.put(r6, r2)
            goto L_0x00c3
        L_0x00a4:
            r16 = r2
            r2 = 1835362404(0x6d656864, float:4.4373917E27)
            if (r13 != r2) goto L_0x00c3
            r2 = 8
            r11.setPosition(r2)
            int r2 = r11.readInt()
            int r2 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomVersion(r2)
            if (r2 != 0) goto L_0x00bf
            long r9 = r11.readUnsignedInt()
            goto L_0x00c3
        L_0x00bf:
            long r9 = r11.readUnsignedLongToLong()
        L_0x00c3:
            int r7 = r7 + 1
            r6 = 12
            r2 = r16
            goto L_0x0059
        L_0x00ca:
            com.google.android.exoplayer2.extractor.GaplessInfoHolder r5 = new com.google.android.exoplayer2.extractor.GaplessInfoHolder
            r5.<init>()
            int r2 = r0.a
            r2 = r2 & 16
            if (r2 == 0) goto L_0x00d7
            r2 = 1
            goto L_0x00d8
        L_0x00d7:
            r2 = 0
        L_0x00d8:
            r11 = 0
            h3 r13 = new h3
            r13.<init>(r0)
            r6 = r9
            r9 = r2
            r10 = r11
            r11 = r13
            java.util.List r2 = com.google.android.exoplayer2.extractor.mp4.b.parseTraks(r4, r5, r6, r8, r9, r10, r11)
            int r4 = r2.size()
            int r5 = r12.size()
            if (r5 != 0) goto L_0x013b
            r5 = 0
        L_0x00f1:
            if (r5 >= r4) goto L_0x0135
            java.lang.Object r6 = r2.get(r5)
            pc r6 = (defpackage.pc) r6
            com.google.android.exoplayer2.extractor.mp4.Track r7 = r6.a
            com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b r8 = new com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b
            com.google.android.exoplayer2.extractor.ExtractorOutput r9 = r0.ae
            int r10 = r7.b
            com.google.android.exoplayer2.extractor.TrackOutput r9 = r9.track(r5, r10)
            int r10 = r3.size()
            int r11 = r7.a
            r13 = 1
            if (r10 != r13) goto L_0x0116
            r10 = 0
            java.lang.Object r10 = r3.valueAt(r10)
            f1 r10 = (defpackage.f1) r10
            goto L_0x0122
        L_0x0116:
            java.lang.Object r10 = r3.get(r11)
            f1 r10 = (defpackage.f1) r10
            java.lang.Object r10 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r10)
            f1 r10 = (defpackage.f1) r10
        L_0x0122:
            r8.<init>(r9, r6, r10)
            r12.put(r11, r8)
            long r8 = r0.x
            long r6 = r7.e
            long r6 = java.lang.Math.max(r8, r6)
            r0.x = r6
            int r5 = r5 + 1
            goto L_0x00f1
        L_0x0135:
            com.google.android.exoplayer2.extractor.ExtractorOutput r2 = r0.ae
            r2.endTracks()
            goto L_0x017d
        L_0x013b:
            int r5 = r12.size()
            if (r5 != r4) goto L_0x0143
            r5 = 1
            goto L_0x0144
        L_0x0143:
            r5 = 0
        L_0x0144:
            com.google.android.exoplayer2.util.Assertions.checkState(r5)
            r5 = 0
        L_0x0148:
            if (r5 >= r4) goto L_0x017d
            java.lang.Object r6 = r2.get(r5)
            pc r6 = (defpackage.pc) r6
            com.google.android.exoplayer2.extractor.mp4.Track r7 = r6.a
            int r8 = r7.a
            java.lang.Object r8 = r12.get(r8)
            com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b r8 = (com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor.b) r8
            int r9 = r3.size()
            r10 = 1
            if (r9 != r10) goto L_0x0169
            r7 = 0
            java.lang.Object r7 = r3.valueAt(r7)
            f1 r7 = (defpackage.f1) r7
            goto L_0x0177
        L_0x0169:
            int r7 = r7.a
            java.lang.Object r7 = r3.get(r7)
            f1 r7 = (defpackage.f1) r7
            java.lang.Object r7 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r7)
            f1 r7 = (defpackage.f1) r7
        L_0x0177:
            r8.reset(r6, r7)
            int r5 = r5 + 1
            goto L_0x0148
        L_0x017d:
            r5 = r48
            goto L_0x0003
        L_0x0181:
            r6 = 1836019558(0x6d6f6f66, float:4.6313494E27)
            if (r3 != r6) goto L_0x0744
            if (r8 == 0) goto L_0x018a
            r2 = 1
            goto L_0x018b
        L_0x018a:
            r2 = 0
        L_0x018b:
            java.util.ArrayList r3 = r4.d
            int r4 = r3.size()
            r6 = 0
            r6 = r1
            r7 = 0
            r1 = r0
        L_0x0195:
            if (r7 >= r4) goto L_0x06f3
            java.lang.Object r8 = r3.get(r7)
            com.google.android.exoplayer2.extractor.mp4.a$a r8 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r8
            int r9 = r8.a
            r10 = 1953653094(0x74726166, float:7.6813435E31)
            if (r9 != r10) goto L_0x06d3
            r9 = 1952868452(0x74666864, float:7.301914E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r9 = r8.getLeafAtomOfType(r9)
            java.lang.Object r9 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r9)
            com.google.android.exoplayer2.extractor.mp4.a$b r9 = (com.google.android.exoplayer2.extractor.mp4.a.b) r9
            com.google.android.exoplayer2.util.ParsableByteArray r9 = r9.b
            r10 = 8
            r9.setPosition(r10)
            int r10 = r9.readInt()
            int r10 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomFlags(r10)
            int r11 = r9.readInt()
            if (r2 == 0) goto L_0x01cc
            r11 = 0
            java.lang.Object r11 = r12.valueAt(r11)
            goto L_0x01d0
        L_0x01cc:
            java.lang.Object r11 = r12.get(r11)
        L_0x01d0:
            com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b r11 = (com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor.b) r11
            if (r11 != 0) goto L_0x01dc
            r11 = 0
            r16 = r0
            r13 = r2
            r15 = r3
            r17 = r4
            goto L_0x0236
        L_0x01dc:
            r13 = r10 & 1
            nc r14 = r11.b
            if (r13 == 0) goto L_0x01ed
            r13 = r2
            r15 = r3
            long r2 = r9.readUnsignedLongToLong()
            r14.b = r2
            r14.c = r2
            goto L_0x01ef
        L_0x01ed:
            r13 = r2
            r15 = r3
        L_0x01ef:
            f1 r2 = r11.e
            r3 = r10 & 2
            if (r3 == 0) goto L_0x01fc
            int r3 = r9.readInt()
            int r3 = r3 + -1
            goto L_0x01fe
        L_0x01fc:
            int r3 = r2.a
        L_0x01fe:
            r16 = r10 & 8
            if (r16 == 0) goto L_0x020d
            int r16 = r9.readInt()
            r47 = r16
            r16 = r0
            r0 = r47
            goto L_0x0211
        L_0x020d:
            r16 = r0
            int r0 = r2.b
        L_0x0211:
            r17 = r10 & 16
            if (r17 == 0) goto L_0x0220
            int r17 = r9.readInt()
            r47 = r17
            r17 = r4
            r4 = r47
            goto L_0x0224
        L_0x0220:
            r17 = r4
            int r4 = r2.c
        L_0x0224:
            r10 = r10 & 32
            if (r10 == 0) goto L_0x022d
            int r2 = r9.readInt()
            goto L_0x022f
        L_0x022d:
            int r2 = r2.d
        L_0x022f:
            f1 r9 = new f1
            r9.<init>(r3, r0, r4, r2)
            r14.a = r9
        L_0x0236:
            if (r11 != 0) goto L_0x0244
            r26 = r5
            r23 = r7
            r25 = r12
            r18 = r13
            r22 = r15
            goto L_0x06e1
        L_0x0244:
            nc r0 = r11.b
            long r2 = r0.q
            boolean r4 = r0.r
            r11.resetFragmentInfo()
            r6 = 1
            r11.l = r6
            r6 = 1952867444(0x74666474, float:7.3014264E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r6 = r8.getLeafAtomOfType(r6)
            int r1 = r1.a
            if (r6 == 0) goto L_0x027f
            r9 = r1 & 2
            if (r9 != 0) goto L_0x027f
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r6.b
            r3 = 8
            r2.setPosition(r3)
            int r3 = r2.readInt()
            int r3 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomVersion(r3)
            r4 = 1
            if (r3 != r4) goto L_0x0276
            long r2 = r2.readUnsignedLongToLong()
            goto L_0x027a
        L_0x0276:
            long r2 = r2.readUnsignedInt()
        L_0x027a:
            r0.q = r2
            r0.r = r4
            goto L_0x0283
        L_0x027f:
            r0.q = r2
            r0.r = r4
        L_0x0283:
            java.util.ArrayList r2 = r8.c
            int r3 = r2.size()
            r4 = 0
            r6 = 0
            r9 = 0
        L_0x028c:
            r10 = 1953658222(0x7472756e, float:7.683823E31)
            if (r4 >= r3) goto L_0x02b2
            java.lang.Object r14 = r2.get(r4)
            com.google.android.exoplayer2.extractor.mp4.a$b r14 = (com.google.android.exoplayer2.extractor.mp4.a.b) r14
            r18 = r13
            int r13 = r14.a
            if (r13 != r10) goto L_0x02ad
            com.google.android.exoplayer2.util.ParsableByteArray r10 = r14.b
            r13 = 12
            r10.setPosition(r13)
            int r10 = r10.readUnsignedIntToInt()
            if (r10 <= 0) goto L_0x02ad
            int r9 = r9 + r10
            int r6 = r6 + 1
        L_0x02ad:
            int r4 = r4 + 1
            r13 = r18
            goto L_0x028c
        L_0x02b2:
            r18 = r13
            r4 = 0
            r11.h = r4
            r11.g = r4
            r11.f = r4
            r0.initTables(r6, r9)
            r6 = 0
            r9 = 0
        L_0x02c0:
            if (r4 >= r3) goto L_0x04a5
            java.lang.Object r16 = r2.get(r4)
            r13 = r16
            com.google.android.exoplayer2.extractor.mp4.a$b r13 = (com.google.android.exoplayer2.extractor.mp4.a.b) r13
            int r14 = r13.a
            if (r14 != r10) goto L_0x0479
            int r10 = r9 + 1
            com.google.android.exoplayer2.util.ParsableByteArray r13 = r13.b
            r14 = 8
            r13.setPosition(r14)
            int r14 = r13.readInt()
            int r14 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomFlags(r14)
            r16 = r3
            pc r3 = r11.d
            com.google.android.exoplayer2.extractor.mp4.Track r3 = r3.a
            r21 = r10
            f1 r10 = r0.a
            java.lang.Object r10 = com.google.android.exoplayer2.util.Util.castNonNull(r10)
            f1 r10 = (defpackage.f1) r10
            r22 = r15
            int[] r15 = r0.g
            int r23 = r13.readUnsignedIntToInt()
            r15[r9] = r23
            long[] r15 = r0.f
            r23 = r7
            r24 = r8
            long r7 = r0.b
            r15[r9] = r7
            r25 = r14 & 1
            if (r25 == 0) goto L_0x0316
            r25 = r12
            int r12 = r13.readInt()
            r27 = r4
            r26 = r5
            long r4 = (long) r12
            long r7 = r7 + r4
            r15[r9] = r7
            goto L_0x031c
        L_0x0316:
            r27 = r4
            r26 = r5
            r25 = r12
        L_0x031c:
            r4 = r14 & 4
            if (r4 == 0) goto L_0x0322
            r4 = 1
            goto L_0x0323
        L_0x0322:
            r4 = 0
        L_0x0323:
            int r5 = r10.d
            if (r4 == 0) goto L_0x032b
            int r5 = r13.readInt()
        L_0x032b:
            r7 = r14 & 256(0x100, float:3.59E-43)
            if (r7 == 0) goto L_0x0331
            r7 = 1
            goto L_0x0332
        L_0x0331:
            r7 = 0
        L_0x0332:
            r8 = r14 & 512(0x200, float:7.175E-43)
            if (r8 == 0) goto L_0x0338
            r8 = 1
            goto L_0x0339
        L_0x0338:
            r8 = 0
        L_0x0339:
            r12 = r14 & 1024(0x400, float:1.435E-42)
            if (r12 == 0) goto L_0x033f
            r12 = 1
            goto L_0x0340
        L_0x033f:
            r12 = 0
        L_0x0340:
            r14 = r14 & 2048(0x800, float:2.87E-42)
            if (r14 == 0) goto L_0x0346
            r14 = 1
            goto L_0x0347
        L_0x0346:
            r14 = 0
        L_0x0347:
            long[] r15 = r3.h
            if (r15 == 0) goto L_0x0373
            r28 = r5
            int r5 = r15.length
            r29 = r2
            r2 = 1
            if (r5 != r2) goto L_0x0377
            r2 = 0
            r30 = r15[r2]
            r19 = 0
            int r5 = (r30 > r19 ? 1 : (r30 == r19 ? 0 : -1))
            if (r5 != 0) goto L_0x0377
            long[] r5 = r3.i
            java.lang.Object r5 = com.google.android.exoplayer2.util.Util.castNonNull(r5)
            long[] r5 = (long[]) r5
            r30 = r5[r2]
            r32 = 1000000(0xf4240, double:4.940656E-318)
            r2 = r14
            long r14 = r3.c
            r34 = r14
            long r14 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r30, r32, r34)
            goto L_0x037a
        L_0x0373:
            r29 = r2
            r28 = r5
        L_0x0377:
            r2 = r14
            r14 = 0
        L_0x037a:
            int[] r5 = r0.h
            r19 = r5
            int[] r5 = r0.i
            r30 = r11
            long[] r11 = r0.j
            r20 = r11
            boolean[] r11 = r0.k
            r31 = r11
            int r11 = r3.b
            r32 = r14
            r14 = 2
            if (r11 != r14) goto L_0x0397
            r11 = r1 & 1
            if (r11 == 0) goto L_0x0397
            r11 = 1
            goto L_0x0398
        L_0x0397:
            r11 = 0
        L_0x0398:
            int[] r14 = r0.g
            r9 = r14[r9]
            int r9 = r9 + r6
            long r14 = r3.c
            r40 = r5
            r3 = r6
            long r5 = r0.q
        L_0x03a4:
            if (r3 >= r9) goto L_0x046c
            if (r7 == 0) goto L_0x03b3
            int r34 = r13.readInt()
            r41 = r1
            r42 = r7
            r1 = r34
            goto L_0x03b9
        L_0x03b3:
            r41 = r1
            int r1 = r10.b
            r42 = r7
        L_0x03b9:
            java.lang.String r7 = "Unexpected negative value: "
            if (r1 < 0) goto L_0x0460
            if (r8 == 0) goto L_0x03c8
            int r34 = r13.readInt()
            r43 = r8
            r8 = r34
            goto L_0x03cc
        L_0x03c8:
            r43 = r8
            int r8 = r10.c
        L_0x03cc:
            if (r8 < 0) goto L_0x0454
            if (r12 == 0) goto L_0x03d5
            int r7 = r13.readInt()
            goto L_0x03de
        L_0x03d5:
            if (r3 != 0) goto L_0x03dc
            if (r4 == 0) goto L_0x03dc
            r7 = r28
            goto L_0x03de
        L_0x03dc:
            int r7 = r10.d
        L_0x03de:
            if (r2 == 0) goto L_0x03f5
            r44 = r2
            int r2 = r13.readInt()
            r46 = r9
            r45 = r10
            long r9 = (long) r2
            r34 = 1000000(0xf4240, double:4.940656E-318)
            long r9 = r9 * r34
            long r9 = r9 / r14
            int r2 = (int) r9
            r40[r3] = r2
            goto L_0x03fe
        L_0x03f5:
            r44 = r2
            r46 = r9
            r45 = r10
            r2 = 0
            r40[r3] = r2
        L_0x03fe:
            r36 = 1000000(0xf4240, double:4.940656E-318)
            r34 = r5
            r38 = r14
            long r9 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r34, r36, r38)
            long r9 = r9 - r32
            r20[r3] = r9
            boolean r2 = r0.r
            if (r2 != 0) goto L_0x0421
            r2 = r30
            r30 = r4
            pc r4 = r2.d
            r35 = r12
            r34 = r13
            long r12 = r4.h
            long r9 = r9 + r12
            r20[r3] = r9
            goto L_0x0429
        L_0x0421:
            r35 = r12
            r34 = r13
            r2 = r30
            r30 = r4
        L_0x0429:
            r19[r3] = r8
            int r4 = r7 >> 16
            r4 = r4 & 1
            if (r4 != 0) goto L_0x0437
            if (r11 == 0) goto L_0x0435
            if (r3 != 0) goto L_0x0437
        L_0x0435:
            r4 = 1
            goto L_0x0438
        L_0x0437:
            r4 = 0
        L_0x0438:
            r31[r3] = r4
            long r7 = (long) r1
            long r5 = r5 + r7
            int r3 = r3 + 1
            r4 = r30
            r13 = r34
            r12 = r35
            r1 = r41
            r7 = r42
            r8 = r43
            r10 = r45
            r9 = r46
            r30 = r2
            r2 = r44
            goto L_0x03a4
        L_0x0454:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            r1 = 38
            java.lang.String r1 = defpackage.y2.d(r1, r7, r8)
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x0460:
            r0 = 38
            com.google.android.exoplayer2.ParserException r2 = new com.google.android.exoplayer2.ParserException
            java.lang.String r0 = defpackage.y2.d(r0, r7, r1)
            r2.<init>((java.lang.String) r0)
            throw r2
        L_0x046c:
            r41 = r1
            r46 = r9
            r2 = r30
            r0.q = r5
            r9 = r21
            r6 = r46
            goto L_0x048d
        L_0x0479:
            r41 = r1
            r29 = r2
            r16 = r3
            r27 = r4
            r26 = r5
            r3 = r6
            r23 = r7
            r24 = r8
            r2 = r11
            r25 = r12
            r22 = r15
        L_0x048d:
            int r4 = r27 + 1
            r10 = 1953658222(0x7472756e, float:7.683823E31)
            r11 = r2
            r3 = r16
            r15 = r22
            r7 = r23
            r8 = r24
            r12 = r25
            r5 = r26
            r2 = r29
            r1 = r41
            goto L_0x02c0
        L_0x04a5:
            r29 = r2
            r26 = r5
            r23 = r7
            r24 = r8
            r2 = r11
            r25 = r12
            r22 = r15
            pc r1 = r2.d
            com.google.android.exoplayer2.extractor.mp4.Track r1 = r1.a
            f1 r2 = r0.a
            java.lang.Object r2 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)
            f1 r2 = (defpackage.f1) r2
            int r2 = r2.a
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox r1 = r1.getSampleDescriptionEncryptionBox(r2)
            r2 = 1935763834(0x7361697a, float:1.785898E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r2 = r8.getLeafAtomOfType(r2)
            if (r2 == 0) goto L_0x054e
            java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox r3 = (com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox) r3
            int r3 = r3.d
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r2.b
            r4 = 8
            r2.setPosition(r4)
            int r5 = r2.readInt()
            int r5 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomFlags(r5)
            r6 = 1
            r5 = r5 & r6
            if (r5 != r6) goto L_0x04eb
            r2.skipBytes(r4)
        L_0x04eb:
            int r4 = r2.readUnsignedByte()
            int r5 = r2.readUnsignedIntToInt()
            int r6 = r0.e
            if (r5 > r6) goto L_0x052b
            if (r4 != 0) goto L_0x050e
            boolean[] r4 = r0.m
            r6 = 0
            r7 = 0
        L_0x04fd:
            if (r6 >= r5) goto L_0x051d
            int r9 = r2.readUnsignedByte()
            int r7 = r7 + r9
            if (r9 <= r3) goto L_0x0508
            r9 = 1
            goto L_0x0509
        L_0x0508:
            r9 = 0
        L_0x0509:
            r4[r6] = r9
            int r6 = r6 + 1
            goto L_0x04fd
        L_0x050e:
            if (r4 <= r3) goto L_0x0512
            r2 = 1
            goto L_0x0513
        L_0x0512:
            r2 = 0
        L_0x0513:
            int r4 = r4 * r5
            r3 = 0
            int r7 = r4 + 0
            boolean[] r4 = r0.m
            java.util.Arrays.fill(r4, r3, r5, r2)
        L_0x051d:
            r2 = 0
            boolean[] r3 = r0.m
            int r4 = r0.e
            java.util.Arrays.fill(r3, r5, r4, r2)
            if (r7 <= 0) goto L_0x054e
            r0.initEncryptionData(r7)
            goto L_0x054e
        L_0x052b:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            int r0 = r0.e
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 78
            r2.<init>(r3)
            java.lang.String r3 = "Saiz sample count "
            r2.append(r3)
            r2.append(r5)
            java.lang.String r3 = " is greater than fragment sample count"
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>((java.lang.String) r0)
            throw r1
        L_0x054e:
            r2 = 1935763823(0x7361696f, float:1.7858967E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r2 = r8.getLeafAtomOfType(r2)
            if (r2 == 0) goto L_0x0596
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r2.b
            r3 = 8
            r2.setPosition(r3)
            int r4 = r2.readInt()
            int r5 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomFlags(r4)
            r6 = 1
            r5 = r5 & r6
            if (r5 != r6) goto L_0x056d
            r2.skipBytes(r3)
        L_0x056d:
            int r3 = r2.readUnsignedIntToInt()
            if (r3 != r6) goto L_0x0588
            int r3 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomVersion(r4)
            long r4 = r0.c
            if (r3 != 0) goto L_0x0580
            long r2 = r2.readUnsignedInt()
            goto L_0x0584
        L_0x0580:
            long r2 = r2.readUnsignedLongToLong()
        L_0x0584:
            long r4 = r4 + r2
            r0.c = r4
            goto L_0x0596
        L_0x0588:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            r1 = 40
            java.lang.String r2 = "Unexpected saio entry count: "
            java.lang.String r1 = defpackage.y2.d(r1, r2, r3)
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x0596:
            r2 = 1936027235(0x73656e63, float:1.8177412E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r2 = r8.getLeafAtomOfType(r2)
            if (r2 == 0) goto L_0x05a5
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r2.b
            r3 = 0
            b(r2, r3, r0)
        L_0x05a5:
            if (r1 == 0) goto L_0x05aa
            java.lang.String r1 = r1.b
            goto L_0x05ab
        L_0x05aa:
            r1 = 0
        L_0x05ab:
            r4 = r1
            r1 = 0
            r2 = 0
            r3 = 0
        L_0x05af:
            int r5 = r29.size()
            if (r1 >= r5) goto L_0x05ec
            r10 = r29
            java.lang.Object r5 = r10.get(r1)
            com.google.android.exoplayer2.extractor.mp4.a$b r5 = (com.google.android.exoplayer2.extractor.mp4.a.b) r5
            com.google.android.exoplayer2.util.ParsableByteArray r6 = r5.b
            r7 = 1935828848(0x73626770, float:1.7937577E31)
            r8 = 1936025959(0x73656967, float:1.817587E31)
            int r5 = r5.a
            if (r5 != r7) goto L_0x05d6
            r5 = 12
            r6.setPosition(r5)
            int r5 = r6.readInt()
            if (r5 != r8) goto L_0x05e7
            r2 = r6
            goto L_0x05e7
        L_0x05d6:
            r7 = 12
            r9 = 1936158820(0x73677064, float:1.8336489E31)
            if (r5 != r9) goto L_0x05e7
            r6.setPosition(r7)
            int r5 = r6.readInt()
            if (r5 != r8) goto L_0x05e7
            r3 = r6
        L_0x05e7:
            int r1 = r1 + 1
            r29 = r10
            goto L_0x05af
        L_0x05ec:
            r10 = r29
            if (r2 == 0) goto L_0x0696
            if (r3 != 0) goto L_0x05f4
            goto L_0x0696
        L_0x05f4:
            r1 = 8
            r2.setPosition(r1)
            int r5 = r2.readInt()
            int r5 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomVersion(r5)
            r6 = 4
            r2.skipBytes(r6)
            r7 = 1
            if (r5 != r7) goto L_0x060b
            r2.skipBytes(r6)
        L_0x060b:
            int r2 = r2.readInt()
            if (r2 != r7) goto L_0x068e
            r3.setPosition(r1)
            int r1 = r3.readInt()
            int r1 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomVersion(r1)
            r3.skipBytes(r6)
            if (r1 != r7) goto L_0x0634
            long r1 = r3.readUnsignedInt()
            r5 = 0
            int r7 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x062c
            goto L_0x063a
        L_0x062c:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Variable length description in sgpd found (unsupported)"
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x0634:
            r2 = 2
            if (r1 < r2) goto L_0x063a
            r3.skipBytes(r6)
        L_0x063a:
            long r1 = r3.readUnsignedInt()
            r5 = 1
            int r7 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r7 != 0) goto L_0x0686
            r1 = 1
            r3.skipBytes(r1)
            int r2 = r3.readUnsignedByte()
            r5 = r2 & 240(0xf0, float:3.36E-43)
            int r7 = r5 >> 4
            r8 = r2 & 15
            int r2 = r3.readUnsignedByte()
            if (r2 != r1) goto L_0x065a
            r1 = 1
            goto L_0x065b
        L_0x065a:
            r1 = 0
        L_0x065b:
            if (r1 != 0) goto L_0x065e
            goto L_0x0696
        L_0x065e:
            int r5 = r3.readUnsignedByte()
            r2 = 16
            byte[] r6 = new byte[r2]
            r9 = 0
            r3.readBytes(r6, r9, r2)
            if (r5 != 0) goto L_0x0677
            int r2 = r3.readUnsignedByte()
            byte[] r11 = new byte[r2]
            r3.readBytes(r11, r9, r2)
            r9 = r11
            goto L_0x0679
        L_0x0677:
            r2 = 0
            r9 = r2
        L_0x0679:
            r2 = 1
            r0.l = r2
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox r11 = new com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox
            r2 = r11
            r3 = r1
            r2.<init>(r3, r4, r5, r6, r7, r8, r9)
            r0.n = r11
            goto L_0x0696
        L_0x0686:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Entry count in sgpd != 1 (unsupported)."
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x068e:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Entry count in sbgp != 1 (unsupported)."
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x0696:
            int r1 = r10.size()
            r2 = 0
        L_0x069b:
            if (r2 >= r1) goto L_0x06cd
            java.lang.Object r3 = r10.get(r2)
            com.google.android.exoplayer2.extractor.mp4.a$b r3 = (com.google.android.exoplayer2.extractor.mp4.a.b) r3
            int r4 = r3.a
            r5 = 1970628964(0x75756964, float:3.1109627E32)
            if (r4 != r5) goto L_0x06c8
            com.google.android.exoplayer2.util.ParsableByteArray r3 = r3.b
            r4 = 8
            r3.setPosition(r4)
            r5 = r48
            byte[] r4 = r5.h
            r6 = 16
            r7 = 0
            r3.readBytes(r4, r7, r6)
            byte[] r7 = ai
            boolean r4 = java.util.Arrays.equals(r4, r7)
            if (r4 != 0) goto L_0x06c4
            goto L_0x06ca
        L_0x06c4:
            b(r3, r6, r0)
            goto L_0x06ca
        L_0x06c8:
            r5 = r48
        L_0x06ca:
            int r2 = r2 + 1
            goto L_0x069b
        L_0x06cd:
            r5 = r48
            r0 = r5
            r1 = r0
            r6 = r1
            goto L_0x06e5
        L_0x06d3:
            r16 = r0
            r18 = r2
            r22 = r3
            r17 = r4
            r26 = r5
            r23 = r7
            r25 = r12
        L_0x06e1:
            r5 = r48
            r0 = r16
        L_0x06e5:
            int r7 = r23 + 1
            r4 = r17
            r2 = r18
            r3 = r22
            r12 = r25
            r5 = r26
            goto L_0x0195
        L_0x06f3:
            r16 = r0
            r26 = r5
            r25 = r12
            r5 = r48
            com.google.android.exoplayer2.drm.DrmInitData r0 = a(r26)
            if (r0 == 0) goto L_0x0716
            int r2 = r25.size()
            r3 = 0
        L_0x0706:
            if (r3 >= r2) goto L_0x0716
            r4 = r25
            java.lang.Object r7 = r4.valueAt(r3)
            com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b r7 = (com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor.b) r7
            r7.updateDrmInitData(r0)
            int r3 = r3 + 1
            goto L_0x0706
        L_0x0716:
            r4 = r25
            long r2 = r1.w
            r7 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r0 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r0 == 0) goto L_0x073f
            int r0 = r4.size()
            r2 = 0
        L_0x0728:
            if (r2 >= r0) goto L_0x0738
            java.lang.Object r3 = r4.valueAt(r2)
            com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b r3 = (com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor.b) r3
            long r7 = r1.w
            r3.seek(r7)
            int r2 = r2 + 1
            goto L_0x0728
        L_0x0738:
            r2 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r1.w = r2
        L_0x073f:
            r1 = r6
            r0 = r16
            goto L_0x0003
        L_0x0744:
            r5 = r48
            boolean r3 = r2.isEmpty()
            if (r3 != 0) goto L_0x0003
            java.lang.Object r2 = r2.peek()
            com.google.android.exoplayer2.extractor.mp4.a$a r2 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r2
            r2.add((com.google.android.exoplayer2.extractor.mp4.a.C0014a) r4)
            goto L_0x0003
        L_0x0757:
            r5 = r48
            r0 = 0
            r1.p = r0
            r1.s = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor.c(long):void");
    }

    public void init(ExtractorOutput extractorOutput) {
        int i2;
        this.ae = extractorOutput;
        this.p = 0;
        this.s = 0;
        TrackOutput[] trackOutputArr = new TrackOutput[2];
        this.af = trackOutputArr;
        TrackOutput trackOutput = this.o;
        if (trackOutput != null) {
            trackOutputArr[0] = trackOutput;
            i2 = 1;
        } else {
            i2 = 0;
        }
        int i3 = 100;
        if ((this.a & 4) != 0) {
            trackOutputArr[i2] = extractorOutput.track(100, 5);
            i3 = 101;
            i2++;
        }
        TrackOutput[] trackOutputArr2 = (TrackOutput[]) Util.nullSafeArrayCopy(this.af, i2);
        this.af = trackOutputArr2;
        for (TrackOutput format : trackOutputArr2) {
            format.format(aj);
        }
        List<Format> list = this.c;
        this.ag = new TrackOutput[list.size()];
        int i4 = 0;
        while (i4 < this.ag.length) {
            TrackOutput track = this.ae.track(i3, 3);
            track.format(list.get(i4));
            this.ag[i4] = track;
            i4++;
            i3++;
        }
        Track track2 = this.b;
        if (track2 != null) {
            this.d.put(0, new b(extractorOutput.track(0, track2.b), new pc(this.b, new long[0], new int[0], 0, new long[0], new int[0], 0), new f1(0, 0, 0, 0)));
            this.ae.endTracks();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:299:0x06f0  */
    /* JADX WARNING: Removed duplicated region for block: B:307:0x026c A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:312:0x06ee A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(com.google.android.exoplayer2.extractor.ExtractorInput r30, com.google.android.exoplayer2.extractor.PositionHolder r31) throws java.io.IOException {
        /*
            r29 = this;
            r0 = r29
            r3 = r30
            r1 = r0
            r2 = r1
        L_0x0006:
            int r4 = r1.p
            java.util.ArrayDeque<com.google.android.exoplayer2.extractor.mp4.a$a> r5 = r1.m
            android.util.SparseArray<com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b> r6 = r1.d
            r7 = 2
            r8 = 1
            r10 = 1936286840(0x73696478, float:1.8491255E31)
            if (r4 == 0) goto L_0x04e7
            java.util.ArrayDeque<com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$a> r11 = r1.n
            com.google.android.exoplayer2.util.TimestampAdjuster r12 = r1.j
            java.lang.String r13 = "FragmentedMp4Extractor"
            if (r4 == r8) goto L_0x02b6
            r14 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            if (r4 == r7) goto L_0x026e
            com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b r4 = r1.z
            if (r4 != 0) goto L_0x0098
            int r4 = r6.size()
            r7 = 0
            r8 = 0
        L_0x002c:
            if (r7 >= r4) goto L_0x005f
            java.lang.Object r10 = r6.valueAt(r7)
            com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b r10 = (com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor.b) r10
            boolean r5 = r10.l
            if (r5 != 0) goto L_0x0043
            int r9 = r10.f
            r17 = r4
            pc r4 = r10.d
            int r4 = r4.b
            if (r9 == r4) goto L_0x005a
            goto L_0x0045
        L_0x0043:
            r17 = r4
        L_0x0045:
            if (r5 == 0) goto L_0x0050
            int r4 = r10.h
            nc r5 = r10.b
            int r5 = r5.d
            if (r4 != r5) goto L_0x0050
            goto L_0x005a
        L_0x0050:
            long r4 = r10.getCurrentSampleOffset()
            int r9 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1))
            if (r9 >= 0) goto L_0x005a
            r14 = r4
            r8 = r10
        L_0x005a:
            int r7 = r7 + 1
            r4 = r17
            goto L_0x002c
        L_0x005f:
            if (r8 != 0) goto L_0x0080
            long r4 = r1.u
            long r6 = r30.getPosition()
            long r4 = r4 - r6
            int r5 = (int) r4
            if (r5 < 0) goto L_0x0078
            r3.skipFully(r5)
            r4 = 0
            r2.p = r4
            r2.s = r4
            r4 = 0
            r16 = r2
            goto L_0x026a
        L_0x0078:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            java.lang.String r2 = "Offset to end of mdat was negative."
            r1.<init>((java.lang.String) r2)
            throw r1
        L_0x0080:
            long r4 = r8.getCurrentSampleOffset()
            long r6 = r30.getPosition()
            long r4 = r4 - r6
            int r5 = (int) r4
            if (r5 >= 0) goto L_0x0092
            java.lang.String r4 = "Ignoring negative offset to sample data."
            com.google.android.exoplayer2.util.Log.w(r13, r4)
            r5 = 0
        L_0x0092:
            r3.skipFully(r5)
            r1.z = r8
            r4 = r8
        L_0x0098:
            int r5 = r1.p
            r6 = 3
            if (r5 != r6) goto L_0x0112
            int r5 = r4.getCurrentSampleSize()
            r1.aa = r5
            int r6 = r4.f
            int r7 = r4.i
            if (r6 >= r7) goto L_0x00bf
            r3.skipFully(r5)
            r4.skipSampleEncryptionData()
            boolean r4 = r4.next()
            if (r4 != 0) goto L_0x00b8
            r4 = 0
            r1.z = r4
        L_0x00b8:
            r4 = 3
            r1.p = r4
            r16 = r2
            goto L_0x0269
        L_0x00bf:
            pc r6 = r4.d
            com.google.android.exoplayer2.extractor.mp4.Track r6 = r6.a
            int r6 = r6.g
            r7 = 1
            if (r6 != r7) goto L_0x00d1
            int r5 = r5 + -8
            r1.aa = r5
            r5 = 8
            r3.skipFully(r5)
        L_0x00d1:
            pc r5 = r4.d
            com.google.android.exoplayer2.extractor.mp4.Track r5 = r5.a
            com.google.android.exoplayer2.Format r5 = r5.f
            java.lang.String r5 = r5.p
            java.lang.String r6 = "audio/ac4"
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x00fd
            int r5 = r1.aa
            r6 = 7
            int r5 = r4.outputSampleEncryptionData(r5, r6)
            r1.ab = r5
            int r5 = r1.aa
            com.google.android.exoplayer2.util.ParsableByteArray r7 = r1.i
            com.google.android.exoplayer2.audio.Ac4Util.getAc4SampleHeader(r5, r7)
            com.google.android.exoplayer2.extractor.TrackOutput r5 = r4.a
            r5.sampleData(r7, r6)
            int r5 = r1.ab
            int r5 = r5 + r6
            r1.ab = r5
            r6 = 0
            goto L_0x0106
        L_0x00fd:
            int r5 = r1.aa
            r6 = 0
            int r5 = r4.outputSampleEncryptionData(r5, r6)
            r1.ab = r5
        L_0x0106:
            int r5 = r1.aa
            int r7 = r1.ab
            int r5 = r5 + r7
            r1.aa = r5
            r5 = 4
            r1.p = r5
            r1.ac = r6
        L_0x0112:
            pc r5 = r4.d
            com.google.android.exoplayer2.extractor.mp4.Track r5 = r5.a
            com.google.android.exoplayer2.extractor.TrackOutput r6 = r4.a
            long r7 = r4.getCurrentSamplePresentationTimeUs()
            if (r12 == 0) goto L_0x0122
            long r7 = r12.adjustSampleTimestamp(r7)
        L_0x0122:
            int r9 = r5.j
            if (r9 == 0) goto L_0x01f4
            com.google.android.exoplayer2.util.ParsableByteArray r9 = r1.f
            byte[] r10 = r9.getData()
            r13 = 0
            r10[r13] = r13
            r14 = 1
            r10[r14] = r13
            r14 = 2
            r10[r14] = r13
            int r13 = r5.j
            int r14 = r13 + 1
            int r13 = 4 - r13
        L_0x013b:
            int r15 = r1.ab
            r16 = r2
            int r2 = r1.aa
            if (r15 >= r2) goto L_0x0208
            int r2 = r1.ac
            com.google.android.exoplayer2.Format r15 = r5.f
            if (r2 != 0) goto L_0x0197
            r3.readFully(r10, r13, r14)
            r2 = 0
            r9.setPosition(r2)
            int r2 = r9.readInt()
            r17 = r5
            r5 = 1
            if (r2 < r5) goto L_0x018f
            int r2 = r2 + -1
            r1.ac = r2
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r1.e
            r5 = 0
            r2.setPosition(r5)
            r5 = 4
            r6.sampleData(r2, r5)
            r2 = 1
            r6.sampleData(r9, r2)
            com.google.android.exoplayer2.extractor.TrackOutput[] r2 = r1.ag
            int r2 = r2.length
            if (r2 <= 0) goto L_0x017c
            java.lang.String r2 = r15.p
            byte r5 = r10[r5]
            boolean r2 = com.google.android.exoplayer2.util.NalUnitUtil.isNalUnitSei(r2, r5)
            if (r2 == 0) goto L_0x017c
            r2 = 1
            goto L_0x017d
        L_0x017c:
            r2 = 0
        L_0x017d:
            r1.ad = r2
            int r2 = r1.ab
            int r2 = r2 + 5
            r1.ab = r2
            int r2 = r1.aa
            int r2 = r2 + r13
            r1.aa = r2
            r18 = r9
            r31 = r10
            goto L_0x01ea
        L_0x018f:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            java.lang.String r2 = "Invalid NAL length"
            r1.<init>((java.lang.String) r2)
            throw r1
        L_0x0197:
            r17 = r5
            boolean r5 = r1.ad
            if (r5 == 0) goto L_0x01d7
            com.google.android.exoplayer2.util.ParsableByteArray r5 = r1.g
            r5.reset((int) r2)
            byte[] r2 = r5.getData()
            r18 = r9
            int r9 = r1.ac
            r31 = r10
            r10 = 0
            r3.readFully(r2, r10, r9)
            int r2 = r1.ac
            r6.sampleData(r5, r2)
            int r2 = r1.ac
            byte[] r9 = r5.getData()
            int r10 = r5.limit()
            int r9 = com.google.android.exoplayer2.util.NalUnitUtil.unescapeStream(r9, r10)
            java.lang.String r10 = "video/hevc"
            java.lang.String r15 = r15.p
            boolean r10 = r10.equals(r15)
            r5.setPosition(r10)
            r5.setLimit(r9)
            com.google.android.exoplayer2.extractor.TrackOutput[] r9 = r1.ag
            com.google.android.exoplayer2.extractor.CeaUtil.consume(r7, r5, r9)
            goto L_0x01e0
        L_0x01d7:
            r18 = r9
            r31 = r10
            r5 = 0
            int r2 = r6.sampleData((com.google.android.exoplayer2.upstream.DataReader) r3, (int) r2, (boolean) r5)
        L_0x01e0:
            int r5 = r1.ab
            int r5 = r5 + r2
            r1.ab = r5
            int r5 = r1.ac
            int r5 = r5 - r2
            r1.ac = r5
        L_0x01ea:
            r10 = r31
            r2 = r16
            r5 = r17
            r9 = r18
            goto L_0x013b
        L_0x01f4:
            r16 = r2
        L_0x01f6:
            int r2 = r1.ab
            int r5 = r1.aa
            if (r2 >= r5) goto L_0x0208
            int r5 = r5 - r2
            r2 = 0
            int r2 = r6.sampleData((com.google.android.exoplayer2.upstream.DataReader) r3, (int) r5, (boolean) r2)
            int r5 = r1.ab
            int r5 = r5 + r2
            r1.ab = r5
            goto L_0x01f6
        L_0x0208:
            int r20 = r4.getCurrentSampleFlags()
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox r2 = r4.getEncryptionBoxIfEncrypted()
            if (r2 == 0) goto L_0x0215
            com.google.android.exoplayer2.extractor.TrackOutput$CryptoData r2 = r2.c
            goto L_0x0216
        L_0x0215:
            r2 = 0
        L_0x0216:
            r23 = r2
            int r2 = r1.aa
            r22 = 0
            r17 = r6
            r18 = r7
            r21 = r2
            r17.sampleMetadata(r18, r20, r21, r22, r23)
        L_0x0225:
            boolean r2 = r11.isEmpty()
            if (r2 != 0) goto L_0x025d
            java.lang.Object r2 = r11.removeFirst()
            com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$a r2 = (com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor.a) r2
            int r5 = r1.v
            int r6 = r2.b
            int r5 = r5 - r6
            r1.v = r5
            long r5 = r2.a
            long r5 = r5 + r7
            if (r12 == 0) goto L_0x0241
            long r5 = r12.adjustSampleTimestamp(r5)
        L_0x0241:
            com.google.android.exoplayer2.extractor.TrackOutput[] r9 = r1.af
            int r10 = r9.length
            r13 = 0
        L_0x0245:
            if (r13 >= r10) goto L_0x0225
            r17 = r9[r13]
            r20 = 1
            int r14 = r2.b
            int r15 = r1.v
            r23 = 0
            r18 = r5
            r21 = r14
            r22 = r15
            r17.sampleMetadata(r18, r20, r21, r22, r23)
            int r13 = r13 + 1
            goto L_0x0245
        L_0x025d:
            boolean r2 = r4.next()
            if (r2 != 0) goto L_0x0266
            r2 = 0
            r1.z = r2
        L_0x0266:
            r2 = 3
            r1.p = r2
        L_0x0269:
            r4 = 1
        L_0x026a:
            if (r4 == 0) goto L_0x02aa
            r1 = 0
            return r1
        L_0x026e:
            r16 = r2
            int r2 = r6.size()
            r4 = 0
            r5 = 0
        L_0x0276:
            if (r5 >= r2) goto L_0x0294
            java.lang.Object r7 = r6.valueAt(r5)
            com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b r7 = (com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor.b) r7
            nc r7 = r7.b
            boolean r8 = r7.p
            if (r8 == 0) goto L_0x0291
            long r7 = r7.c
            int r9 = (r7 > r14 ? 1 : (r7 == r14 ? 0 : -1))
            if (r9 >= 0) goto L_0x0291
            java.lang.Object r4 = r6.valueAt(r5)
            com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b r4 = (com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor.b) r4
            r14 = r7
        L_0x0291:
            int r5 = r5 + 1
            goto L_0x0276
        L_0x0294:
            if (r4 != 0) goto L_0x029a
            r2 = 3
            r1.p = r2
            goto L_0x02aa
        L_0x029a:
            long r5 = r30.getPosition()
            long r14 = r14 - r5
            int r2 = (int) r14
            if (r2 < 0) goto L_0x02ae
            r3.skipFully(r2)
            nc r2 = r4.b
            r2.fillEncryptionData((com.google.android.exoplayer2.extractor.ExtractorInput) r3)
        L_0x02aa:
            r2 = r16
            goto L_0x0006
        L_0x02ae:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            java.lang.String r2 = "Offset to encryption data was negative."
            r1.<init>((java.lang.String) r2)
            throw r1
        L_0x02b6:
            r16 = r2
            long r6 = r1.r
            int r2 = (int) r6
            int r4 = r1.s
            int r2 = r2 - r4
            com.google.android.exoplayer2.util.ParsableByteArray r4 = r1.t
            if (r4 == 0) goto L_0x04d8
            byte[] r6 = r4.getData()
            r7 = 8
            r3.readFully(r6, r7, r2)
            com.google.android.exoplayer2.extractor.mp4.a$b r2 = new com.google.android.exoplayer2.extractor.mp4.a$b
            int r3 = r1.q
            r2.<init>(r3, r4)
            long r3 = r30.getPosition()
            boolean r6 = r5.isEmpty()
            if (r6 != 0) goto L_0x02e8
            java.lang.Object r3 = r5.peek()
            com.google.android.exoplayer2.extractor.mp4.a$a r3 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r3
            r3.add((com.google.android.exoplayer2.extractor.mp4.a.b) r2)
            r4 = r0
            goto L_0x04d3
        L_0x02e8:
            int r5 = r2.a
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r2.b
            if (r5 != r10) goto L_0x03b4
            r1 = 8
            r2.setPosition(r1)
            int r1 = r2.readInt()
            int r1 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomVersion(r1)
            r5 = 4
            r2.skipBytes(r5)
            long r12 = r2.readUnsignedInt()
            if (r1 != 0) goto L_0x030e
            long r5 = r2.readUnsignedInt()
            long r7 = r2.readUnsignedInt()
            goto L_0x0316
        L_0x030e:
            long r5 = r2.readUnsignedLongToLong()
            long r7 = r2.readUnsignedLongToLong()
        L_0x0316:
            r14 = r5
            long r3 = r3 + r7
            r8 = 1000000(0xf4240, double:4.940656E-318)
            r6 = r14
            r10 = r12
            long r16 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r6, r8, r10)
            r1 = 2
            r2.skipBytes(r1)
            int r1 = r2.readUnsignedShort()
            int[] r5 = new int[r1]
            long[] r10 = new long[r1]
            long[] r11 = new long[r1]
            long[] r8 = new long[r1]
            r6 = 0
            r6 = r14
            r18 = r16
            r14 = 0
        L_0x0336:
            if (r14 >= r1) goto L_0x0388
            int r9 = r2.readInt()
            r15 = -2147483648(0xffffffff80000000, float:-0.0)
            r15 = r15 & r9
            if (r15 != 0) goto L_0x0380
            long r20 = r2.readUnsignedInt()
            r15 = 2147483647(0x7fffffff, float:NaN)
            r9 = r9 & r15
            r5[r14] = r9
            r10[r14] = r3
            r8[r14] = r18
            long r18 = r6 + r20
            r20 = 1000000(0xf4240, double:4.940656E-318)
            r6 = r18
            r15 = r8
            r8 = r20
            r31 = r1
            r1 = r10
            r0 = r11
            r10 = r12
            long r6 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r6, r8, r10)
            r8 = r15[r14]
            long r8 = r6 - r8
            r0[r14] = r8
            r8 = 4
            r2.skipBytes(r8)
            r8 = r5[r14]
            long r8 = (long) r8
            long r3 = r3 + r8
            int r14 = r14 + 1
            r11 = r0
            r10 = r1
            r8 = r15
            r0 = r29
            r1 = r31
            r27 = r6
            r6 = r18
            r18 = r27
            goto L_0x0336
        L_0x0380:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Unhandled indirect reference"
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x0388:
            r15 = r8
            r1 = r10
            r0 = r11
            java.lang.Long r2 = java.lang.Long.valueOf(r16)
            com.google.android.exoplayer2.extractor.ChunkIndex r3 = new com.google.android.exoplayer2.extractor.ChunkIndex
            r3.<init>(r5, r1, r0, r15)
            android.util.Pair r0 = android.util.Pair.create(r2, r3)
            java.lang.Object r1 = r0.first
            java.lang.Long r1 = (java.lang.Long) r1
            long r1 = r1.longValue()
            r4 = r29
            r4.y = r1
            com.google.android.exoplayer2.extractor.ExtractorOutput r1 = r4.ae
            java.lang.Object r0 = r0.second
            com.google.android.exoplayer2.extractor.SeekMap r0 = (com.google.android.exoplayer2.extractor.SeekMap) r0
            r1.seekMap(r0)
            r0 = 1
            r4.ah = r0
            r1 = r4
            r2 = r1
            goto L_0x04d5
        L_0x03b4:
            r4 = r0
            r0 = 1701671783(0x656d7367, float:7.0083103E22)
            if (r5 != r0) goto L_0x04d3
            com.google.android.exoplayer2.extractor.TrackOutput[] r0 = r1.af
            int r0 = r0.length
            if (r0 != 0) goto L_0x03c1
            goto L_0x04d3
        L_0x03c1:
            r0 = 8
            r2.setPosition(r0)
            int r0 = r2.readInt()
            int r0 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomVersion(r0)
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r0 == 0) goto L_0x041e
            r3 = 1
            if (r0 == r3) goto L_0x03e1
            r2 = 46
            java.lang.String r3 = "Skipping unsupported emsg version: "
            defpackage.y2.t(r2, r3, r0, r13)
            goto L_0x04d3
        L_0x03e1:
            long r7 = r2.readUnsignedInt()
            long r17 = r2.readUnsignedLongToLong()
            r19 = 1000000(0xf4240, double:4.940656E-318)
            r21 = r7
            long r9 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r17, r19, r21)
            long r17 = r2.readUnsignedInt()
            r19 = 1000(0x3e8, double:4.94E-321)
            long r7 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r17, r19, r21)
            long r13 = r2.readUnsignedInt()
            java.lang.String r0 = r2.readNullTerminatedString()
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r3 = r2.readNullTerminatedString()
            java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r3)
            java.lang.String r3 = (java.lang.String) r3
            r20 = r0
            r21 = r3
            r22 = r7
            r24 = r13
            r7 = r5
            goto L_0x0466
        L_0x041e:
            java.lang.String r0 = r2.readNullTerminatedString()
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r3 = r2.readNullTerminatedString()
            java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r3)
            java.lang.String r3 = (java.lang.String) r3
            long r7 = r2.readUnsignedInt()
            long r17 = r2.readUnsignedInt()
            r19 = 1000000(0xf4240, double:4.940656E-318)
            r21 = r7
            long r9 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r17, r19, r21)
            long r13 = r1.y
            int r15 = (r13 > r5 ? 1 : (r13 == r5 ? 0 : -1))
            if (r15 == 0) goto L_0x044b
            long r13 = r13 + r9
            goto L_0x044c
        L_0x044b:
            r13 = r5
        L_0x044c:
            long r17 = r2.readUnsignedInt()
            r19 = 1000(0x3e8, double:4.94E-321)
            r21 = r7
            long r7 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r17, r19, r21)
            long r17 = r2.readUnsignedInt()
            r20 = r0
            r21 = r3
            r22 = r7
            r7 = r9
            r9 = r13
            r24 = r17
        L_0x0466:
            int r0 = r2.bytesLeft()
            byte[] r0 = new byte[r0]
            int r3 = r2.bytesLeft()
            r13 = 0
            r2.readBytes(r0, r13, r3)
            com.google.android.exoplayer2.metadata.emsg.EventMessage r2 = new com.google.android.exoplayer2.metadata.emsg.EventMessage
            r19 = r2
            r26 = r0
            r19.<init>(r20, r21, r22, r24, r26)
            com.google.android.exoplayer2.util.ParsableByteArray r0 = new com.google.android.exoplayer2.util.ParsableByteArray
            com.google.android.exoplayer2.metadata.emsg.EventMessageEncoder r3 = r1.k
            byte[] r2 = r3.encode(r2)
            r0.<init>((byte[]) r2)
            int r2 = r0.bytesLeft()
            com.google.android.exoplayer2.extractor.TrackOutput[] r3 = r1.af
            int r13 = r3.length
            r14 = 0
        L_0x0490:
            if (r14 >= r13) goto L_0x04a3
            r5 = r3[r14]
            r6 = 0
            r0.setPosition(r6)
            r5.sampleData(r0, r2)
            int r14 = r14 + 1
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            goto L_0x0490
        L_0x04a3:
            int r0 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r0 != 0) goto L_0x04b5
            com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$a r0 = new com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$a
            r0.<init>(r7, r2)
            r11.addLast(r0)
            int r0 = r1.v
            int r0 = r0 + r2
            r1.v = r0
            goto L_0x04d3
        L_0x04b5:
            if (r12 == 0) goto L_0x04bb
            long r9 = r12.adjustSampleTimestamp(r9)
        L_0x04bb:
            com.google.android.exoplayer2.extractor.TrackOutput[] r0 = r1.af
            int r3 = r0.length
            r5 = 0
        L_0x04bf:
            if (r5 >= r3) goto L_0x04d3
            r17 = r0[r5]
            r20 = 1
            r22 = 0
            r23 = 0
            r18 = r9
            r21 = r2
            r17.sampleMetadata(r18, r20, r21, r22, r23)
            int r5 = r5 + 1
            goto L_0x04bf
        L_0x04d3:
            r2 = r16
        L_0x04d5:
            r3 = r30
            goto L_0x04de
        L_0x04d8:
            r4 = r0
            r3.skipFully(r2)
            r2 = r16
        L_0x04de:
            long r5 = r30.getPosition()
            r1.c(r5)
            goto L_0x06f1
        L_0x04e7:
            r4 = r0
            r16 = r2
            int r0 = r1.s
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r1.l
            if (r0 != 0) goto L_0x0513
            byte[] r0 = r2.getData()
            r7 = 8
            r8 = 1
            r9 = 0
            boolean r0 = r3.readFully(r0, r9, r7, r8)
            if (r0 != 0) goto L_0x0502
            r5 = r16
            goto L_0x06ec
        L_0x0502:
            r1.s = r7
            r2.setPosition(r9)
            long r7 = r2.readUnsignedInt()
            r1.r = r7
            int r0 = r2.readInt()
            r1.q = r0
        L_0x0513:
            long r7 = r1.r
            r11 = 1
            int r0 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r0 != 0) goto L_0x0530
            byte[] r0 = r2.getData()
            r7 = 8
            r3.readFully(r0, r7, r7)
            int r0 = r1.s
            int r0 = r0 + r7
            r1.s = r0
            long r7 = r2.readUnsignedLongToLong()
            r1.r = r7
            goto L_0x055d
        L_0x0530:
            r11 = 0
            int r0 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r0 != 0) goto L_0x055d
            long r7 = r30.getLength()
            r11 = -1
            int r0 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r0 != 0) goto L_0x054e
            boolean r0 = r5.isEmpty()
            if (r0 != 0) goto L_0x054e
            java.lang.Object r0 = r5.peek()
            com.google.android.exoplayer2.extractor.mp4.a$a r0 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r0
            long r7 = r0.b
        L_0x054e:
            int r0 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r0 == 0) goto L_0x055d
            long r11 = r30.getPosition()
            long r7 = r7 - r11
            int r0 = r1.s
            long r11 = (long) r0
            long r7 = r7 + r11
            r1.r = r7
        L_0x055d:
            long r7 = r1.r
            int r0 = r1.s
            long r11 = (long) r0
            int r0 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r0 < 0) goto L_0x06fc
            long r7 = r30.getPosition()
            int r0 = r1.s
            long r11 = (long) r0
            long r7 = r7 - r11
            int r0 = r1.q
            r9 = 1835295092(0x6d646174, float:4.4175247E27)
            r11 = 1836019558(0x6d6f6f66, float:4.6313494E27)
            if (r0 == r11) goto L_0x057a
            if (r0 != r9) goto L_0x058d
        L_0x057a:
            boolean r0 = r1.ah
            if (r0 != 0) goto L_0x058d
            com.google.android.exoplayer2.extractor.ExtractorOutput r0 = r1.ae
            com.google.android.exoplayer2.extractor.SeekMap$Unseekable r12 = new com.google.android.exoplayer2.extractor.SeekMap$Unseekable
            long r13 = r1.x
            r12.<init>(r13, r7)
            r0.seekMap(r12)
            r0 = 1
            r1.ah = r0
        L_0x058d:
            int r0 = r1.q
            if (r0 != r11) goto L_0x05aa
            int r0 = r6.size()
            r12 = 0
        L_0x0596:
            if (r12 >= r0) goto L_0x05aa
            java.lang.Object r13 = r6.valueAt(r12)
            com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor$b r13 = (com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor.b) r13
            nc r13 = r13.b
            r13.getClass()
            r13.c = r7
            r13.b = r7
            int r12 = r12 + 1
            goto L_0x0596
        L_0x05aa:
            int r0 = r1.q
            if (r0 != r9) goto L_0x05bd
            r0 = 0
            r1.z = r0
            long r5 = r1.r
            long r7 = r7 + r5
            r1.u = r7
            r0 = 2
            r1.p = r0
        L_0x05b9:
            r5 = r16
            goto L_0x06eb
        L_0x05bd:
            r6 = 1836019574(0x6d6f6f76, float:4.631354E27)
            if (r0 == r6) goto L_0x05ea
            r6 = 1953653099(0x7472616b, float:7.681346E31)
            if (r0 == r6) goto L_0x05ea
            r6 = 1835297121(0x6d646961, float:4.4181236E27)
            if (r0 == r6) goto L_0x05ea
            r6 = 1835626086(0x6d696e66, float:4.515217E27)
            if (r0 == r6) goto L_0x05ea
            r6 = 1937007212(0x7374626c, float:1.9362132E31)
            if (r0 == r6) goto L_0x05ea
            if (r0 == r11) goto L_0x05ea
            r6 = 1953653094(0x74726166, float:7.6813435E31)
            if (r0 == r6) goto L_0x05ea
            r6 = 1836475768(0x6d766578, float:4.7659988E27)
            if (r0 == r6) goto L_0x05ea
            r6 = 1701082227(0x65647473, float:6.742798E22)
            if (r0 != r6) goto L_0x05e8
            goto L_0x05ea
        L_0x05e8:
            r6 = 0
            goto L_0x05eb
        L_0x05ea:
            r6 = 1
        L_0x05eb:
            if (r6 == 0) goto L_0x0617
            long r6 = r30.getPosition()
            long r8 = r1.r
            long r6 = r6 + r8
            r8 = 8
            long r6 = r6 - r8
            com.google.android.exoplayer2.extractor.mp4.a$a r0 = new com.google.android.exoplayer2.extractor.mp4.a$a
            int r2 = r1.q
            r0.<init>(r2, r6)
            r5.push(r0)
            long r8 = r1.r
            int r0 = r1.s
            long r10 = (long) r0
            int r0 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r0 != 0) goto L_0x060e
            r1.c(r6)
            goto L_0x05b9
        L_0x060e:
            r0 = 0
            r5 = r16
            r5.p = r0
            r5.s = r0
            goto L_0x06eb
        L_0x0617:
            r5 = r16
            r6 = 1751411826(0x68646c72, float:4.3148E24)
            if (r0 == r6) goto L_0x06a5
            r6 = 1835296868(0x6d646864, float:4.418049E27)
            if (r0 == r6) goto L_0x06a5
            r6 = 1836476516(0x6d766864, float:4.7662196E27)
            if (r0 == r6) goto L_0x06a5
            if (r0 == r10) goto L_0x06a5
            r6 = 1937011556(0x73747364, float:1.9367383E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1937011827(0x73747473, float:1.9367711E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1668576371(0x63747473, float:4.5093966E21)
            if (r0 == r6) goto L_0x06a5
            r6 = 1937011555(0x73747363, float:1.9367382E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1937011578(0x7374737a, float:1.936741E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1937013298(0x73747a32, float:1.9369489E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1937007471(0x7374636f, float:1.9362445E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1668232756(0x636f3634, float:4.4126776E21)
            if (r0 == r6) goto L_0x06a5
            r6 = 1937011571(0x73747373, float:1.9367401E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1952867444(0x74666474, float:7.3014264E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1952868452(0x74666864, float:7.301914E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1953196132(0x746b6864, float:7.46037E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1953654136(0x74726578, float:7.6818474E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1953658222(0x7472756e, float:7.683823E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1886614376(0x70737368, float:3.013775E29)
            if (r0 == r6) goto L_0x06a5
            r6 = 1935763834(0x7361697a, float:1.785898E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1935763823(0x7361696f, float:1.7858967E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1936027235(0x73656e63, float:1.8177412E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1970628964(0x75756964, float:3.1109627E32)
            if (r0 == r6) goto L_0x06a5
            r6 = 1935828848(0x73626770, float:1.7937577E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1936158820(0x73677064, float:1.8336489E31)
            if (r0 == r6) goto L_0x06a5
            r6 = 1701606260(0x656c7374, float:6.9788014E22)
            if (r0 == r6) goto L_0x06a5
            r6 = 1835362404(0x6d656864, float:4.4373917E27)
            if (r0 == r6) goto L_0x06a5
            r6 = 1701671783(0x656d7367, float:7.0083103E22)
            if (r0 != r6) goto L_0x06a3
            goto L_0x06a5
        L_0x06a3:
            r0 = 0
            goto L_0x06a6
        L_0x06a5:
            r0 = 1
        L_0x06a6:
            r6 = 2147483647(0x7fffffff, double:1.060997895E-314)
            if (r0 == 0) goto L_0x06df
            int r0 = r1.s
            r8 = 8
            if (r0 != r8) goto L_0x06d7
            long r9 = r1.r
            int r0 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r0 > 0) goto L_0x06cf
            com.google.android.exoplayer2.util.ParsableByteArray r0 = new com.google.android.exoplayer2.util.ParsableByteArray
            int r6 = (int) r9
            r0.<init>((int) r6)
            byte[] r2 = r2.getData()
            byte[] r6 = r0.getData()
            r7 = 0
            java.lang.System.arraycopy(r2, r7, r6, r7, r8)
            r1.t = r0
            r0 = 1
            r1.p = r0
            goto L_0x06eb
        L_0x06cf:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Leaf atom with length > 2147483647 (unsupported)."
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x06d7:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Leaf atom defines extended atom size (unsupported)."
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x06df:
            long r8 = r1.r
            int r0 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r0 > 0) goto L_0x06f4
            r0 = 0
            r1.t = r0
            r0 = 1
            r1.p = r0
        L_0x06eb:
            r9 = 1
        L_0x06ec:
            if (r9 != 0) goto L_0x06f0
            r0 = -1
            return r0
        L_0x06f0:
            r2 = r5
        L_0x06f1:
            r0 = r4
            goto L_0x0006
        L_0x06f4:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Skipping atom with length > 2147483647 (unsupported)."
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x06fc:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Atom size less than header length (unsupported)."
            r0.<init>((java.lang.String) r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor.read(com.google.android.exoplayer2.extractor.ExtractorInput, com.google.android.exoplayer2.extractor.PositionHolder):int");
    }

    public void release() {
    }

    public void seek(long j2, long j3) {
        SparseArray<b> sparseArray = this.d;
        int size = sparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            sparseArray.valueAt(i2).resetFragmentInfo();
        }
        this.n.clear();
        this.v = 0;
        this.w = j3;
        this.m.clear();
        this.p = 0;
        this.s = 0;
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        return eb.sniffFragmented(extractorInput);
    }

    public FragmentedMp4Extractor(int i2) {
        this(i2, (TimestampAdjuster) null);
    }

    public FragmentedMp4Extractor(int i2, @Nullable TimestampAdjuster timestampAdjuster) {
        this(i2, timestampAdjuster, (Track) null, Collections.emptyList());
    }

    public FragmentedMp4Extractor(int i2, @Nullable TimestampAdjuster timestampAdjuster, @Nullable Track track) {
        this(i2, timestampAdjuster, track, Collections.emptyList());
    }

    public FragmentedMp4Extractor(int i2, @Nullable TimestampAdjuster timestampAdjuster, @Nullable Track track, List<Format> list) {
        this(i2, timestampAdjuster, track, list, (TrackOutput) null);
    }

    public FragmentedMp4Extractor(int i2, @Nullable TimestampAdjuster timestampAdjuster, @Nullable Track track, List<Format> list, @Nullable TrackOutput trackOutput) {
        this.a = i2;
        this.j = timestampAdjuster;
        this.b = track;
        this.c = Collections.unmodifiableList(list);
        this.o = trackOutput;
        this.k = new EventMessageEncoder();
        this.l = new ParsableByteArray(16);
        this.e = new ParsableByteArray(NalUnitUtil.a);
        this.f = new ParsableByteArray(5);
        this.g = new ParsableByteArray();
        byte[] bArr = new byte[16];
        this.h = bArr;
        this.i = new ParsableByteArray(bArr);
        this.m = new ArrayDeque<>();
        this.n = new ArrayDeque<>();
        this.d = new SparseArray<>();
        this.x = -9223372036854775807L;
        this.w = -9223372036854775807L;
        this.y = -9223372036854775807L;
        this.ae = ExtractorOutput.b;
        this.af = new TrackOutput[0];
        this.ag = new TrackOutput[0];
    }
}
