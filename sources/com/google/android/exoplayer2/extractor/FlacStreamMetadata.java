package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.flac.PictureFrame;
import com.google.android.exoplayer2.metadata.flac.VorbisComment;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FlacStreamMetadata {
    public final int a;
    public final int b;
    public final int c;
    public final int d;
    public final int e;
    public final int f;
    public final int g;
    public final int h;
    public final int i;
    public final long j;
    @Nullable
    public final SeekTable k;
    @Nullable
    public final Metadata l;

    public static class SeekTable {
        public final long[] a;
        public final long[] b;

        public SeekTable(long[] jArr, long[] jArr2) {
            this.a = jArr;
            this.b = jArr2;
        }
    }

    public FlacStreamMetadata(byte[] bArr, int i2) {
        ParsableBitArray parsableBitArray = new ParsableBitArray(bArr);
        parsableBitArray.setPosition(i2 * 8);
        this.a = parsableBitArray.readBits(16);
        this.b = parsableBitArray.readBits(16);
        this.c = parsableBitArray.readBits(24);
        this.d = parsableBitArray.readBits(24);
        int readBits = parsableBitArray.readBits(20);
        this.e = readBits;
        this.f = c(readBits);
        this.g = parsableBitArray.readBits(3) + 1;
        int readBits2 = parsableBitArray.readBits(5) + 1;
        this.h = readBits2;
        this.i = b(readBits2);
        this.j = parsableBitArray.readBitsToLong(36);
        this.k = null;
        this.l = null;
    }

    @Nullable
    public static Metadata a(List<String> list, List<PictureFrame> list2) {
        String str;
        if (list.isEmpty() && list2.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            String str2 = list.get(i2);
            String[] splitAtFirst = Util.splitAtFirst(str2, "=");
            if (splitAtFirst.length != 2) {
                String valueOf = String.valueOf(str2);
                if (valueOf.length() != 0) {
                    str = "Failed to parse Vorbis comment: ".concat(valueOf);
                } else {
                    str = new String("Failed to parse Vorbis comment: ");
                }
                Log.w("FlacStreamMetadata", str);
            } else {
                arrayList.add(new VorbisComment(splitAtFirst[0], splitAtFirst[1]));
            }
        }
        arrayList.addAll(list2);
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata((List<? extends Metadata.Entry>) arrayList);
    }

    public static int b(int i2) {
        if (i2 == 8) {
            return 1;
        }
        if (i2 == 12) {
            return 2;
        }
        if (i2 == 16) {
            return 4;
        }
        if (i2 != 20) {
            return i2 != 24 ? -1 : 6;
        }
        return 5;
    }

    public static int c(int i2) {
        switch (i2) {
            case 8000:
                return 4;
            case 16000:
                return 5;
            case 22050:
                return 6;
            case 24000:
                return 7;
            case 32000:
                return 8;
            case 44100:
                return 9;
            case 48000:
                return 10;
            case 88200:
                return 1;
            case 96000:
                return 11;
            case 176400:
                return 2;
            case 192000:
                return 3;
            default:
                return -1;
        }
    }

    public FlacStreamMetadata copyWithPictureFrames(List<PictureFrame> list) {
        return new FlacStreamMetadata(this.a, this.b, this.c, this.d, this.e, this.g, this.h, this.j, this.k, getMetadataCopyWithAppendedEntriesFrom(a(Collections.emptyList(), list)));
    }

    public FlacStreamMetadata copyWithSeekTable(@Nullable SeekTable seekTable) {
        return new FlacStreamMetadata(this.a, this.b, this.c, this.d, this.e, this.g, this.h, this.j, seekTable, this.l);
    }

    public FlacStreamMetadata copyWithVorbisComments(List<String> list) {
        return new FlacStreamMetadata(this.a, this.b, this.c, this.d, this.e, this.g, this.h, this.j, this.k, getMetadataCopyWithAppendedEntriesFrom(a(list, Collections.emptyList())));
    }

    public long getApproxBytesPerFrame() {
        long j2;
        long j3;
        long j4;
        int i2 = this.d;
        if (i2 > 0) {
            j3 = (((long) i2) + ((long) this.c)) / 2;
            j4 = 1;
        } else {
            int i3 = this.b;
            int i4 = this.a;
            if (i4 != i3 || i4 <= 0) {
                j2 = 4096;
            } else {
                j2 = (long) i4;
            }
            j3 = ((j2 * ((long) this.g)) * ((long) this.h)) / 8;
            j4 = 64;
        }
        return j3 + j4;
    }

    public int getDecodedBitrate() {
        return this.h * this.e * this.g;
    }

    public long getDurationUs() {
        long j2 = this.j;
        if (j2 == 0) {
            return -9223372036854775807L;
        }
        return (j2 * 1000000) / ((long) this.e);
    }

    public Format getFormat(byte[] bArr, @Nullable Metadata metadata) {
        bArr[4] = Byte.MIN_VALUE;
        int i2 = this.d;
        if (i2 <= 0) {
            i2 = -1;
        }
        return new Format.Builder().setSampleMimeType("audio/flac").setMaxInputSize(i2).setChannelCount(this.g).setSampleRate(this.e).setInitializationData(Collections.singletonList(bArr)).setMetadata(getMetadataCopyWithAppendedEntriesFrom(metadata)).build();
    }

    public int getMaxDecodedFrameSize() {
        return (this.h / 8) * this.b * this.g;
    }

    @Nullable
    public Metadata getMetadataCopyWithAppendedEntriesFrom(@Nullable Metadata metadata) {
        Metadata metadata2 = this.l;
        return metadata2 == null ? metadata : metadata2.copyWithAppendedEntriesFrom(metadata);
    }

    public long getSampleNumber(long j2) {
        return Util.constrainValue((j2 * ((long) this.e)) / 1000000, 0, this.j - 1);
    }

    public FlacStreamMetadata(int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j2, ArrayList<String> arrayList, ArrayList<PictureFrame> arrayList2) {
        this(i2, i3, i4, i5, i6, i7, i8, j2, (SeekTable) null, a(arrayList, arrayList2));
    }

    public FlacStreamMetadata(int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j2, @Nullable SeekTable seekTable, @Nullable Metadata metadata) {
        this.a = i2;
        this.b = i3;
        this.c = i4;
        this.d = i5;
        this.e = i6;
        this.f = c(i6);
        this.g = i7;
        this.h = i8;
        this.i = b(i8);
        this.j = j2;
        this.k = seekTable;
        this.l = metadata;
    }
}
