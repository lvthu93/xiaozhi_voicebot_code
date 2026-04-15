package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mp4.a;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.MotionPhotoMetadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public final class Mp4Extractor implements Extractor, SeekMap {
    public final int a;
    public final ParsableByteArray b;
    public final ParsableByteArray c;
    public final ParsableByteArray d;
    public final ParsableByteArray e;
    public final ArrayDeque<a.C0014a> f;
    public final pa g;
    public final ArrayList h;
    public int i;
    public int j;
    public long k;
    public int l;
    @Nullable
    public ParsableByteArray m;
    public int n;
    public int o;
    public int p;
    public int q;
    public ExtractorOutput r;
    public a[] s;
    public long[][] t;
    public int u;
    public long v;
    public int w;
    @Nullable
    public MotionPhotoMetadata x;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static final class a {
        public final Track a;
        public final pc b;
        public final TrackOutput c;
        public int d;

        public a(Track track, pc pcVar, TrackOutput trackOutput) {
            this.a = track;
            this.b = pcVar;
            this.c = trackOutput;
        }
    }

    public Mp4Extractor() {
        this(0);
    }

    public final void a(long j2) throws ParserException {
        boolean z;
        Metadata metadata;
        Metadata metadata2;
        Metadata metadata3;
        boolean z2;
        long j3;
        ArrayDeque<a.C0014a> arrayDeque;
        ArrayList arrayList;
        Metadata metadata4;
        GaplessInfoHolder gaplessInfoHolder;
        Metadata metadata5;
        char c2;
        int i2;
        while (true) {
            ArrayDeque<a.C0014a> arrayDeque2 = this.f;
            if (!arrayDeque2.isEmpty() && arrayDeque2.peek().b == j2) {
                a.C0014a pop = arrayDeque2.pop();
                if (pop.a == 1836019574) {
                    ArrayList arrayList2 = new ArrayList();
                    if (this.w == 1) {
                        z = true;
                    } else {
                        z = false;
                    }
                    GaplessInfoHolder gaplessInfoHolder2 = new GaplessInfoHolder();
                    a.b leafAtomOfType = pop.getLeafAtomOfType(1969517665);
                    if (leafAtomOfType != null) {
                        Pair<Metadata, Metadata> parseUdta = b.parseUdta(leafAtomOfType);
                        Metadata metadata6 = (Metadata) parseUdta.first;
                        Metadata metadata7 = (Metadata) parseUdta.second;
                        if (metadata6 != null) {
                            gaplessInfoHolder2.setFromMetadata(metadata6);
                        }
                        metadata = metadata7;
                        metadata2 = metadata6;
                    } else {
                        metadata2 = null;
                        metadata = null;
                    }
                    a.C0014a containerAtomOfType = pop.getContainerAtomOfType(1835365473);
                    if (containerAtomOfType != null) {
                        metadata3 = b.parseMdtaFromMeta(containerAtomOfType);
                    } else {
                        metadata3 = null;
                    }
                    if ((this.a & 1) != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    Metadata metadata8 = metadata3;
                    Metadata metadata9 = metadata2;
                    List<pc> parseTraks = b.parseTraks(pop, gaplessInfoHolder2, -9223372036854775807L, (DrmInitData) null, z2, z, new a7());
                    ExtractorOutput extractorOutput = (ExtractorOutput) Assertions.checkNotNull(this.r);
                    int size = parseTraks.size();
                    long j4 = -9223372036854775807L;
                    GaplessInfoHolder gaplessInfoHolder3 = gaplessInfoHolder2;
                    int i3 = 0;
                    int i4 = -1;
                    long j5 = -9223372036854775807L;
                    while (true) {
                        j3 = 0;
                        if (i3 >= size) {
                            break;
                        }
                        pc pcVar = parseTraks.get(i3);
                        if (pcVar.b == 0) {
                            arrayDeque = arrayDeque2;
                            arrayList = arrayList2;
                            gaplessInfoHolder = gaplessInfoHolder3;
                            metadata4 = metadata9;
                        } else {
                            Track track = pcVar.a;
                            arrayDeque = arrayDeque2;
                            ArrayList arrayList3 = arrayList2;
                            long j6 = track.e;
                            if (j6 == j4) {
                                j6 = pcVar.h;
                            }
                            j5 = Math.max(j5, j6);
                            int i5 = track.b;
                            a aVar = new a(track, pcVar, extractorOutput.track(i3, i5));
                            Format.Builder buildUpon = track.f.buildUpon();
                            buildUpon.setMaxInputSize(pcVar.e + 30);
                            if (i5 == 2 && j6 > 0 && (i2 = pcVar.b) > 1) {
                                buildUpon.setFrameRate(((float) i2) / (((float) j6) / 1000000.0f));
                            }
                            gaplessInfoHolder = gaplessInfoHolder3;
                            r6.setFormatGaplessInfo(i5, gaplessInfoHolder, buildUpon);
                            Metadata[] metadataArr = new Metadata[2];
                            metadataArr[0] = metadata;
                            ArrayList arrayList4 = this.h;
                            if (arrayList4.isEmpty()) {
                                c2 = 1;
                                metadata5 = null;
                            } else {
                                metadata5 = new Metadata((List<? extends Metadata.Entry>) arrayList4);
                                c2 = 1;
                            }
                            metadataArr[c2] = metadata5;
                            metadata4 = metadata9;
                            r6.setFormatMetadata(i5, metadata4, metadata8, buildUpon, metadataArr);
                            aVar.c.format(buildUpon.build());
                            if (i5 == 2) {
                                if (i4 == -1) {
                                    i4 = arrayList3.size();
                                }
                            }
                            arrayList = arrayList3;
                            arrayList.add(aVar);
                        }
                        i3++;
                        gaplessInfoHolder3 = gaplessInfoHolder;
                        metadata9 = metadata4;
                        arrayList2 = arrayList;
                        arrayDeque2 = arrayDeque;
                        j4 = -9223372036854775807L;
                    }
                    ArrayDeque<a.C0014a> arrayDeque3 = arrayDeque2;
                    this.u = i4;
                    this.v = j5;
                    a[] aVarArr = (a[]) arrayList2.toArray(new a[0]);
                    this.s = aVarArr;
                    long[][] jArr = new long[aVarArr.length][];
                    int[] iArr = new int[aVarArr.length];
                    long[] jArr2 = new long[aVarArr.length];
                    boolean[] zArr = new boolean[aVarArr.length];
                    for (int i6 = 0; i6 < aVarArr.length; i6++) {
                        jArr[i6] = new long[aVarArr[i6].b.b];
                        jArr2[i6] = aVarArr[i6].b.f[0];
                    }
                    int i7 = 0;
                    while (i7 < aVarArr.length) {
                        long j7 = Long.MAX_VALUE;
                        int i8 = -1;
                        for (int i9 = 0; i9 < aVarArr.length; i9++) {
                            if (!zArr[i9]) {
                                long j8 = jArr2[i9];
                                if (j8 <= j7) {
                                    i8 = i9;
                                    j7 = j8;
                                }
                            }
                        }
                        int i10 = iArr[i8];
                        long[] jArr3 = jArr[i8];
                        jArr3[i10] = j3;
                        pc pcVar2 = aVarArr[i8].b;
                        j3 += (long) pcVar2.d[i10];
                        int i11 = i10 + 1;
                        iArr[i8] = i11;
                        if (i11 < jArr3.length) {
                            jArr2[i8] = pcVar2.f[i11];
                        } else {
                            zArr[i8] = true;
                            i7++;
                        }
                    }
                    this.t = jArr;
                    extractorOutput.endTracks();
                    extractorOutput.seekMap(this);
                    arrayDeque3.clear();
                    this.i = 2;
                } else {
                    ArrayDeque<a.C0014a> arrayDeque4 = arrayDeque2;
                    if (!arrayDeque4.isEmpty()) {
                        arrayDeque4.peek().add(pop);
                    }
                }
            }
        }
        if (this.i != 2) {
            this.i = 0;
            this.l = 0;
        }
    }

    public long getDurationUs() {
        return this.v;
    }

    public SeekMap.SeekPoints getSeekPoints(long j2) {
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        int indexOfLaterOrEqualSynchronizationSample;
        long j8 = j2;
        if (((a[]) Assertions.checkNotNull(this.s)).length == 0) {
            return new SeekMap.SeekPoints(SeekPoint.c);
        }
        int i2 = this.u;
        if (i2 != -1) {
            pc pcVar = this.s[i2].b;
            int indexOfEarlierOrEqualSynchronizationSample = pcVar.getIndexOfEarlierOrEqualSynchronizationSample(j8);
            if (indexOfEarlierOrEqualSynchronizationSample == -1) {
                indexOfEarlierOrEqualSynchronizationSample = pcVar.getIndexOfLaterOrEqualSynchronizationSample(j8);
            }
            if (indexOfEarlierOrEqualSynchronizationSample == -1) {
                return new SeekMap.SeekPoints(SeekPoint.c);
            }
            long[] jArr = pcVar.f;
            long j9 = jArr[indexOfEarlierOrEqualSynchronizationSample];
            long[] jArr2 = pcVar.c;
            j4 = jArr2[indexOfEarlierOrEqualSynchronizationSample];
            if (j9 >= j8 || indexOfEarlierOrEqualSynchronizationSample >= pcVar.b - 1 || (indexOfLaterOrEqualSynchronizationSample = pcVar.getIndexOfLaterOrEqualSynchronizationSample(j8)) == -1 || indexOfLaterOrEqualSynchronizationSample == indexOfEarlierOrEqualSynchronizationSample) {
                j7 = -9223372036854775807L;
                j6 = -1;
            } else {
                j7 = jArr[indexOfLaterOrEqualSynchronizationSample];
                j6 = jArr2[indexOfLaterOrEqualSynchronizationSample];
            }
            j3 = j6;
            j5 = j7;
            j8 = j9;
        } else {
            j4 = Long.MAX_VALUE;
            j5 = -9223372036854775807L;
            j3 = -1;
        }
        int i3 = 0;
        long j10 = j3;
        while (true) {
            a[] aVarArr = this.s;
            if (i3 >= aVarArr.length) {
                break;
            }
            if (i3 != this.u) {
                pc pcVar2 = aVarArr[i3].b;
                int indexOfEarlierOrEqualSynchronizationSample2 = pcVar2.getIndexOfEarlierOrEqualSynchronizationSample(j8);
                if (indexOfEarlierOrEqualSynchronizationSample2 == -1) {
                    indexOfEarlierOrEqualSynchronizationSample2 = pcVar2.getIndexOfLaterOrEqualSynchronizationSample(j8);
                }
                if (indexOfEarlierOrEqualSynchronizationSample2 != -1) {
                    j4 = Math.min(pcVar2.c[indexOfEarlierOrEqualSynchronizationSample2], j4);
                }
                if (j5 != -9223372036854775807L) {
                    int indexOfEarlierOrEqualSynchronizationSample3 = pcVar2.getIndexOfEarlierOrEqualSynchronizationSample(j5);
                    if (indexOfEarlierOrEqualSynchronizationSample3 == -1) {
                        indexOfEarlierOrEqualSynchronizationSample3 = pcVar2.getIndexOfLaterOrEqualSynchronizationSample(j5);
                    }
                    if (indexOfEarlierOrEqualSynchronizationSample3 != -1) {
                        j10 = Math.min(pcVar2.c[indexOfEarlierOrEqualSynchronizationSample3], j10);
                    }
                }
            }
            i3++;
        }
        SeekPoint seekPoint = new SeekPoint(j8, j4);
        if (j5 == -9223372036854775807L) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(j5, j10));
    }

    public void init(ExtractorOutput extractorOutput) {
        this.r = extractorOutput;
    }

    public boolean isSeekable() {
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:114:0x024f  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x0251  */
    /* JADX WARNING: Removed duplicated region for block: B:231:0x0441 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:232:0x0254 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:233:0x0006 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:234:0x0006 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(com.google.android.exoplayer2.extractor.ExtractorInput r31, com.google.android.exoplayer2.extractor.PositionHolder r32) throws java.io.IOException {
        /*
            r30 = this;
            r0 = r30
            r1 = r31
            r2 = r32
        L_0x0006:
            int r3 = r0.i
            java.util.ArrayDeque<com.google.android.exoplayer2.extractor.mp4.a$a> r4 = r0.f
            r6 = 2
            r7 = -1
            r8 = 1
            r9 = 0
            r10 = 1718909296(0x66747970, float:2.8862439E23)
            com.google.android.exoplayer2.util.ParsableByteArray r11 = r0.d
            r12 = 8
            r13 = 0
            if (r3 == 0) goto L_0x0256
            r15 = 262144(0x40000, double:1.295163E-318)
            if (r3 == r8) goto L_0x01c8
            if (r3 == r6) goto L_0x003e
            r4 = 3
            if (r3 != r4) goto L_0x0038
            java.util.ArrayList r3 = r0.h
            pa r4 = r0.g
            int r1 = r4.read(r1, r2, r3)
            if (r1 != r8) goto L_0x0037
            long r2 = r2.a
            int r4 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1))
            if (r4 != 0) goto L_0x0037
            r0.i = r9
            r0.l = r9
        L_0x0037:
            return r1
        L_0x0038:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            r1.<init>()
            throw r1
        L_0x003e:
            long r3 = r31.getPosition()
            int r10 = r0.n
            if (r10 != r7) goto L_0x00c8
            r17 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r19 = r17
            r21 = r19
            r25 = r21
            r5 = 1
            r10 = 0
            r12 = 1
            r23 = -1
            r24 = -1
        L_0x0058:
            com.google.android.exoplayer2.extractor.mp4.Mp4Extractor$a[] r6 = r0.s
            java.lang.Object r6 = com.google.android.exoplayer2.util.Util.castNonNull(r6)
            com.google.android.exoplayer2.extractor.mp4.Mp4Extractor$a[] r6 = (com.google.android.exoplayer2.extractor.mp4.Mp4Extractor.a[]) r6
            int r6 = r6.length
            if (r10 >= r6) goto L_0x00ad
            com.google.android.exoplayer2.extractor.mp4.Mp4Extractor$a[] r6 = r0.s
            r6 = r6[r10]
            int r9 = r6.d
            pc r6 = r6.b
            int r8 = r6.b
            if (r9 != r8) goto L_0x0070
            goto L_0x00a8
        L_0x0070:
            long[] r6 = r6.c
            r27 = r6[r9]
            long[][] r6 = r0.t
            java.lang.Object r6 = com.google.android.exoplayer2.util.Util.castNonNull(r6)
            long[][] r6 = (long[][]) r6
            r6 = r6[r10]
            r8 = r6[r9]
            long r27 = r27 - r3
            int r6 = (r27 > r13 ? 1 : (r27 == r13 ? 0 : -1))
            if (r6 < 0) goto L_0x008d
            int r6 = (r27 > r15 ? 1 : (r27 == r15 ? 0 : -1))
            if (r6 < 0) goto L_0x008b
            goto L_0x008d
        L_0x008b:
            r6 = 0
            goto L_0x008e
        L_0x008d:
            r6 = 1
        L_0x008e:
            if (r6 != 0) goto L_0x0092
            if (r5 != 0) goto L_0x0098
        L_0x0092:
            if (r6 != r5) goto L_0x009f
            int r29 = (r27 > r25 ? 1 : (r27 == r25 ? 0 : -1))
            if (r29 >= 0) goto L_0x009f
        L_0x0098:
            r5 = r6
            r21 = r8
            r24 = r10
            r25 = r27
        L_0x009f:
            int r27 = (r8 > r19 ? 1 : (r8 == r19 ? 0 : -1))
            if (r27 >= 0) goto L_0x00a8
            r12 = r6
            r19 = r8
            r23 = r10
        L_0x00a8:
            int r10 = r10 + 1
            r8 = 1
            r9 = 0
            goto L_0x0058
        L_0x00ad:
            int r5 = (r19 > r17 ? 1 : (r19 == r17 ? 0 : -1))
            if (r5 == 0) goto L_0x00c0
            if (r12 == 0) goto L_0x00c0
            r5 = 10485760(0xa00000, double:5.180654E-317)
            long r19 = r19 + r5
            int r5 = (r21 > r19 ? 1 : (r21 == r19 ? 0 : -1))
            if (r5 >= 0) goto L_0x00bd
            goto L_0x00c0
        L_0x00bd:
            r5 = r23
            goto L_0x00c2
        L_0x00c0:
            r5 = r24
        L_0x00c2:
            r0.n = r5
            if (r5 != r7) goto L_0x00c8
            goto L_0x01c7
        L_0x00c8:
            com.google.android.exoplayer2.extractor.mp4.Mp4Extractor$a[] r5 = r0.s
            java.lang.Object r5 = com.google.android.exoplayer2.util.Util.castNonNull(r5)
            com.google.android.exoplayer2.extractor.mp4.Mp4Extractor$a[] r5 = (com.google.android.exoplayer2.extractor.mp4.Mp4Extractor.a[]) r5
            int r6 = r0.n
            r5 = r5[r6]
            com.google.android.exoplayer2.extractor.TrackOutput r6 = r5.c
            int r8 = r5.d
            pc r9 = r5.b
            long[] r10 = r9.c
            r13 = r10[r8]
            int[] r10 = r9.d
            r10 = r10[r8]
            long r3 = r13 - r3
            int r12 = r0.o
            r19 = r8
            long r7 = (long) r12
            long r3 = r3 + r7
            r7 = 0
            int r12 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r12 < 0) goto L_0x01c4
            int r7 = (r3 > r15 ? 1 : (r3 == r15 ? 0 : -1))
            if (r7 < 0) goto L_0x00f6
            goto L_0x01c4
        L_0x00f6:
            com.google.android.exoplayer2.extractor.mp4.Track r2 = r5.a
            int r7 = r2.g
            r8 = 1
            if (r7 != r8) goto L_0x0102
            r7 = 8
            long r3 = r3 + r7
            int r10 = r10 + -8
        L_0x0102:
            int r4 = (int) r3
            r1.skipFully(r4)
            int r3 = r2.j
            if (r3 == 0) goto L_0x0164
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r0.c
            byte[] r4 = r2.getData()
            r7 = 0
            r4[r7] = r7
            r8 = 1
            r4[r8] = r7
            r8 = 2
            r4[r8] = r7
            int r7 = 4 - r3
        L_0x011b:
            int r8 = r0.p
            if (r8 >= r10) goto L_0x019d
            int r8 = r0.q
            if (r8 != 0) goto L_0x014f
            r1.readFully(r4, r7, r3)
            int r8 = r0.o
            int r8 = r8 + r3
            r0.o = r8
            r11 = 0
            r2.setPosition(r11)
            int r8 = r2.readInt()
            if (r8 < 0) goto L_0x0147
            r0.q = r8
            com.google.android.exoplayer2.util.ParsableByteArray r8 = r0.b
            r8.setPosition(r11)
            r12 = 4
            r6.sampleData(r8, r12)
            int r8 = r0.p
            int r8 = r8 + r12
            r0.p = r8
            int r10 = r10 + r7
            goto L_0x011b
        L_0x0147:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            java.lang.String r2 = "Invalid NAL length"
            r1.<init>((java.lang.String) r2)
            throw r1
        L_0x014f:
            r11 = 0
            int r8 = r6.sampleData((com.google.android.exoplayer2.upstream.DataReader) r1, (int) r8, (boolean) r11)
            int r11 = r0.o
            int r11 = r11 + r8
            r0.o = r11
            int r11 = r0.p
            int r11 = r11 + r8
            r0.p = r11
            int r11 = r0.q
            int r11 = r11 - r8
            r0.q = r11
            goto L_0x011b
        L_0x0164:
            com.google.android.exoplayer2.Format r2 = r2.f
            java.lang.String r2 = r2.p
            java.lang.String r3 = "audio/ac4"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0182
            int r2 = r0.p
            if (r2 != 0) goto L_0x0180
            com.google.android.exoplayer2.audio.Ac4Util.getAc4SampleHeader(r10, r11)
            r2 = 7
            r6.sampleData(r11, r2)
            int r3 = r0.p
            int r3 = r3 + r2
            r0.p = r3
        L_0x0180:
            int r10 = r10 + 7
        L_0x0182:
            int r2 = r0.p
            if (r2 >= r10) goto L_0x019d
            int r2 = r10 - r2
            r3 = 0
            int r2 = r6.sampleData((com.google.android.exoplayer2.upstream.DataReader) r1, (int) r2, (boolean) r3)
            int r3 = r0.o
            int r3 = r3 + r2
            r0.o = r3
            int r3 = r0.p
            int r3 = r3 + r2
            r0.p = r3
            int r3 = r0.q
            int r3 = r3 - r2
            r0.q = r3
            goto L_0x0182
        L_0x019d:
            r21 = r10
            long[] r1 = r9.f
            r2 = r1[r19]
            int[] r1 = r9.g
            r20 = r1[r19]
            r22 = 0
            r23 = 0
            r17 = r6
            r18 = r2
            r17.sampleMetadata(r18, r20, r21, r22, r23)
            int r1 = r5.d
            r2 = 1
            int r1 = r1 + r2
            r5.d = r1
            r1 = -1
            r0.n = r1
            r1 = 0
            r0.o = r1
            r0.p = r1
            r0.q = r1
            r7 = 0
            goto L_0x01c7
        L_0x01c4:
            r2.a = r13
            r7 = 1
        L_0x01c7:
            return r7
        L_0x01c8:
            long r5 = r0.k
            int r3 = r0.l
            long r7 = (long) r3
            long r5 = r5 - r7
            long r7 = r31.getPosition()
            long r7 = r7 + r5
            com.google.android.exoplayer2.util.ParsableByteArray r3 = r0.m
            if (r3 == 0) goto L_0x0233
            byte[] r9 = r3.getData()
            int r11 = r0.l
            int r6 = (int) r5
            r1.readFully(r9, r11, r6)
            int r5 = r0.j
            if (r5 != r10) goto L_0x021c
            r3.setPosition(r12)
            int r4 = r3.readInt()
            r5 = 1903435808(0x71742020, float:1.2088509E30)
            r6 = 1751476579(0x68656963, float:4.333464E24)
            if (r4 == r6) goto L_0x01fa
            if (r4 == r5) goto L_0x01f8
            r4 = 0
            goto L_0x01fb
        L_0x01f8:
            r4 = 1
            goto L_0x01fb
        L_0x01fa:
            r4 = 2
        L_0x01fb:
            if (r4 == 0) goto L_0x01fe
            goto L_0x0219
        L_0x01fe:
            r4 = 4
            r3.skipBytes(r4)
        L_0x0202:
            int r4 = r3.bytesLeft()
            if (r4 <= 0) goto L_0x0218
            int r4 = r3.readInt()
            if (r4 == r6) goto L_0x0214
            if (r4 == r5) goto L_0x0212
            r4 = 0
            goto L_0x0215
        L_0x0212:
            r4 = 1
            goto L_0x0215
        L_0x0214:
            r4 = 2
        L_0x0215:
            if (r4 == 0) goto L_0x0202
            goto L_0x0219
        L_0x0218:
            r4 = 0
        L_0x0219:
            r0.w = r4
            goto L_0x023b
        L_0x021c:
            boolean r5 = r4.isEmpty()
            if (r5 != 0) goto L_0x023b
            java.lang.Object r4 = r4.peek()
            com.google.android.exoplayer2.extractor.mp4.a$a r4 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r4
            com.google.android.exoplayer2.extractor.mp4.a$b r5 = new com.google.android.exoplayer2.extractor.mp4.a$b
            int r6 = r0.j
            r5.<init>(r6, r3)
            r4.add((com.google.android.exoplayer2.extractor.mp4.a.b) r5)
            goto L_0x023b
        L_0x0233:
            int r3 = (r5 > r15 ? 1 : (r5 == r15 ? 0 : -1))
            if (r3 >= 0) goto L_0x023d
            int r3 = (int) r5
            r1.skipFully(r3)
        L_0x023b:
            r3 = 0
            goto L_0x0245
        L_0x023d:
            long r3 = r31.getPosition()
            long r3 = r3 + r5
            r2.a = r3
            r3 = 1
        L_0x0245:
            r0.a(r7)
            if (r3 == 0) goto L_0x0251
            int r3 = r0.i
            r4 = 2
            if (r3 == r4) goto L_0x0251
            r9 = 1
            goto L_0x0252
        L_0x0251:
            r9 = 0
        L_0x0252:
            if (r9 == 0) goto L_0x0006
            r3 = 1
            return r3
        L_0x0256:
            r3 = 1
            int r5 = r0.l
            r6 = 0
            com.google.android.exoplayer2.util.ParsableByteArray r7 = r0.e
            if (r5 != 0) goto L_0x02c4
            byte[] r5 = r7.getData()
            r8 = 0
            boolean r5 = r1.readFully(r5, r8, r12, r3)
            if (r5 != 0) goto L_0x02b2
            int r3 = r0.w
            r4 = 2
            if (r3 != r4) goto L_0x02af
            int r3 = r0.a
            r3 = r3 & r4
            if (r3 == 0) goto L_0x02af
            com.google.android.exoplayer2.extractor.ExtractorOutput r3 = r0.r
            java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r3)
            com.google.android.exoplayer2.extractor.ExtractorOutput r3 = (com.google.android.exoplayer2.extractor.ExtractorOutput) r3
            r4 = 4
            com.google.android.exoplayer2.extractor.TrackOutput r4 = r3.track(r8, r4)
            com.google.android.exoplayer2.metadata.mp4.MotionPhotoMetadata r5 = r0.x
            if (r5 != 0) goto L_0x0285
            goto L_0x028f
        L_0x0285:
            com.google.android.exoplayer2.metadata.Metadata r6 = new com.google.android.exoplayer2.metadata.Metadata
            r7 = 1
            com.google.android.exoplayer2.metadata.Metadata$Entry[] r7 = new com.google.android.exoplayer2.metadata.Metadata.Entry[r7]
            r7[r8] = r5
            r6.<init>((com.google.android.exoplayer2.metadata.Metadata.Entry[]) r7)
        L_0x028f:
            com.google.android.exoplayer2.Format$Builder r5 = new com.google.android.exoplayer2.Format$Builder
            r5.<init>()
            com.google.android.exoplayer2.Format$Builder r5 = r5.setMetadata(r6)
            com.google.android.exoplayer2.Format r5 = r5.build()
            r4.format(r5)
            r3.endTracks()
            com.google.android.exoplayer2.extractor.SeekMap$Unseekable r4 = new com.google.android.exoplayer2.extractor.SeekMap$Unseekable
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r4.<init>(r5)
            r3.seekMap(r4)
        L_0x02af:
            r8 = 0
            goto L_0x043f
        L_0x02b2:
            r0.l = r12
            r3 = 0
            r7.setPosition(r3)
            long r8 = r7.readUnsignedInt()
            r0.k = r8
            int r3 = r7.readInt()
            r0.j = r3
        L_0x02c4:
            long r8 = r0.k
            r13 = 1
            int r3 = (r8 > r13 ? 1 : (r8 == r13 ? 0 : -1))
            if (r3 != 0) goto L_0x02df
            byte[] r3 = r7.getData()
            r1.readFully(r3, r12, r12)
            int r3 = r0.l
            int r3 = r3 + r12
            r0.l = r3
            long r8 = r7.readUnsignedLongToLong()
            r0.k = r8
            goto L_0x0308
        L_0x02df:
            r13 = 0
            int r3 = (r8 > r13 ? 1 : (r8 == r13 ? 0 : -1))
            if (r3 != 0) goto L_0x0308
            long r8 = r31.getLength()
            r13 = -1
            int r3 = (r8 > r13 ? 1 : (r8 == r13 ? 0 : -1))
            if (r3 != 0) goto L_0x02f9
            java.lang.Object r3 = r4.peek()
            com.google.android.exoplayer2.extractor.mp4.a$a r3 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r3
            if (r3 == 0) goto L_0x02f9
            long r8 = r3.b
        L_0x02f9:
            int r3 = (r8 > r13 ? 1 : (r8 == r13 ? 0 : -1))
            if (r3 == 0) goto L_0x0308
            long r13 = r31.getPosition()
            long r8 = r8 - r13
            int r3 = r0.l
            long r13 = (long) r3
            long r8 = r8 + r13
            r0.k = r8
        L_0x0308:
            long r8 = r0.k
            int r3 = r0.l
            long r13 = (long) r3
            int r5 = (r8 > r13 ? 1 : (r8 == r13 ? 0 : -1))
            if (r5 < 0) goto L_0x0443
            int r5 = r0.j
            r8 = 1836019574(0x6d6f6f76, float:4.631354E27)
            r9 = 1835365473(0x6d657461, float:4.4382975E27)
            if (r5 == r8) goto L_0x0339
            r8 = 1953653099(0x7472616b, float:7.681346E31)
            if (r5 == r8) goto L_0x0339
            r8 = 1835297121(0x6d646961, float:4.4181236E27)
            if (r5 == r8) goto L_0x0339
            r8 = 1835626086(0x6d696e66, float:4.515217E27)
            if (r5 == r8) goto L_0x0339
            r8 = 1937007212(0x7374626c, float:1.9362132E31)
            if (r5 == r8) goto L_0x0339
            r8 = 1701082227(0x65647473, float:6.742798E22)
            if (r5 == r8) goto L_0x0339
            if (r5 != r9) goto L_0x0337
            goto L_0x0339
        L_0x0337:
            r8 = 0
            goto L_0x033a
        L_0x0339:
            r8 = 1
        L_0x033a:
            if (r8 == 0) goto L_0x0386
            long r5 = r31.getPosition()
            long r7 = r0.k
            long r5 = r5 + r7
            int r3 = r0.l
            long r13 = (long) r3
            long r5 = r5 - r13
            int r3 = (r7 > r13 ? 1 : (r7 == r13 ? 0 : -1))
            if (r3 == 0) goto L_0x0367
            int r3 = r0.j
            if (r3 != r9) goto L_0x0367
            r11.reset((int) r12)
            byte[] r3 = r11.getData()
            r7 = 0
            r1.peekFully(r3, r7, r12)
            com.google.android.exoplayer2.extractor.mp4.b.maybeSkipRemainingMetaAtomHeaderBytes(r11)
            int r3 = r11.getPosition()
            r1.skipFully(r3)
            r31.resetPeekPosition()
        L_0x0367:
            com.google.android.exoplayer2.extractor.mp4.a$a r3 = new com.google.android.exoplayer2.extractor.mp4.a$a
            int r7 = r0.j
            r3.<init>(r7, r5)
            r4.push(r3)
            long r3 = r0.k
            int r7 = r0.l
            long r7 = (long) r7
            int r9 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r9 != 0) goto L_0x037e
            r0.a(r5)
            goto L_0x0383
        L_0x037e:
            r3 = 0
            r0.i = r3
            r0.l = r3
        L_0x0383:
            r3 = 1
            goto L_0x043e
        L_0x0386:
            r4 = 1835296868(0x6d646864, float:4.418049E27)
            if (r5 == r4) goto L_0x03e0
            r4 = 1836476516(0x6d766864, float:4.7662196E27)
            if (r5 == r4) goto L_0x03e0
            r4 = 1751411826(0x68646c72, float:4.3148E24)
            if (r5 == r4) goto L_0x03e0
            r4 = 1937011556(0x73747364, float:1.9367383E31)
            if (r5 == r4) goto L_0x03e0
            r4 = 1937011827(0x73747473, float:1.9367711E31)
            if (r5 == r4) goto L_0x03e0
            r4 = 1937011571(0x73747373, float:1.9367401E31)
            if (r5 == r4) goto L_0x03e0
            r4 = 1668576371(0x63747473, float:4.5093966E21)
            if (r5 == r4) goto L_0x03e0
            r4 = 1701606260(0x656c7374, float:6.9788014E22)
            if (r5 == r4) goto L_0x03e0
            r4 = 1937011555(0x73747363, float:1.9367382E31)
            if (r5 == r4) goto L_0x03e0
            r4 = 1937011578(0x7374737a, float:1.936741E31)
            if (r5 == r4) goto L_0x03e0
            r4 = 1937013298(0x73747a32, float:1.9369489E31)
            if (r5 == r4) goto L_0x03e0
            r4 = 1937007471(0x7374636f, float:1.9362445E31)
            if (r5 == r4) goto L_0x03e0
            r4 = 1668232756(0x636f3634, float:4.4126776E21)
            if (r5 == r4) goto L_0x03e0
            r4 = 1953196132(0x746b6864, float:7.46037E31)
            if (r5 == r4) goto L_0x03e0
            if (r5 == r10) goto L_0x03e0
            r4 = 1969517665(0x75647461, float:2.8960062E32)
            if (r5 == r4) goto L_0x03e0
            r4 = 1801812339(0x6b657973, float:2.7741754E26)
            if (r5 == r4) goto L_0x03e0
            r4 = 1768715124(0x696c7374, float:1.7865732E25)
            if (r5 != r4) goto L_0x03de
            goto L_0x03e0
        L_0x03de:
            r4 = 0
            goto L_0x03e1
        L_0x03e0:
            r4 = 1
        L_0x03e1:
            if (r4 == 0) goto L_0x0414
            if (r3 != r12) goto L_0x03e7
            r3 = 1
            goto L_0x03e8
        L_0x03e7:
            r3 = 0
        L_0x03e8:
            com.google.android.exoplayer2.util.Assertions.checkState(r3)
            long r3 = r0.k
            r5 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 > 0) goto L_0x03f6
            r3 = 1
            goto L_0x03f7
        L_0x03f6:
            r3 = 0
        L_0x03f7:
            com.google.android.exoplayer2.util.Assertions.checkState(r3)
            com.google.android.exoplayer2.util.ParsableByteArray r3 = new com.google.android.exoplayer2.util.ParsableByteArray
            long r4 = r0.k
            int r5 = (int) r4
            r3.<init>((int) r5)
            byte[] r4 = r7.getData()
            byte[] r5 = r3.getData()
            r6 = 0
            java.lang.System.arraycopy(r4, r6, r5, r6, r12)
            r0.m = r3
            r3 = 1
            r0.i = r3
            goto L_0x043e
        L_0x0414:
            long r3 = r31.getPosition()
            int r5 = r0.l
            long r7 = (long) r5
            long r12 = r3 - r7
            int r3 = r0.j
            r4 = 1836086884(0x6d707664, float:4.6512205E27)
            if (r3 != r4) goto L_0x0439
            com.google.android.exoplayer2.metadata.mp4.MotionPhotoMetadata r3 = new com.google.android.exoplayer2.metadata.mp4.MotionPhotoMetadata
            r10 = 0
            r14 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            long r16 = r12 + r7
            long r4 = r0.k
            long r18 = r4 - r7
            r9 = r3
            r9.<init>(r10, r12, r14, r16, r18)
            r0.x = r3
        L_0x0439:
            r0.m = r6
            r3 = 1
            r0.i = r3
        L_0x043e:
            r8 = 1
        L_0x043f:
            if (r8 != 0) goto L_0x0006
            r3 = -1
            return r3
        L_0x0443:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            java.lang.String r2 = "Atom size less than header length (unsupported)."
            r1.<init>((java.lang.String) r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.Mp4Extractor.read(com.google.android.exoplayer2.extractor.ExtractorInput, com.google.android.exoplayer2.extractor.PositionHolder):int");
    }

    public void release() {
    }

    public void seek(long j2, long j3) {
        this.f.clear();
        this.l = 0;
        this.n = -1;
        this.o = 0;
        this.p = 0;
        this.q = 0;
        if (j2 != 0) {
            a[] aVarArr = this.s;
            if (aVarArr != null) {
                for (a aVar : aVarArr) {
                    pc pcVar = aVar.b;
                    int indexOfEarlierOrEqualSynchronizationSample = pcVar.getIndexOfEarlierOrEqualSynchronizationSample(j3);
                    if (indexOfEarlierOrEqualSynchronizationSample == -1) {
                        indexOfEarlierOrEqualSynchronizationSample = pcVar.getIndexOfLaterOrEqualSynchronizationSample(j3);
                    }
                    aVar.d = indexOfEarlierOrEqualSynchronizationSample;
                }
            }
        } else if (this.i != 3) {
            this.i = 0;
            this.l = 0;
        } else {
            this.g.reset();
            this.h.clear();
        }
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        return eb.sniffUnfragmented(extractorInput, (this.a & 2) != 0);
    }

    public Mp4Extractor(int i2) {
        this.a = i2;
        this.i = (i2 & 4) != 0 ? 3 : 0;
        this.g = new pa();
        this.h = new ArrayList();
        this.e = new ParsableByteArray(16);
        this.f = new ArrayDeque<>();
        this.b = new ParsableByteArray(NalUnitUtil.a);
        this.c = new ParsableByteArray(4);
        this.d = new ParsableByteArray();
        this.n = -1;
    }
}
