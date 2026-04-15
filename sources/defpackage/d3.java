package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.FlacFrameReader;
import com.google.android.exoplayer2.extractor.FlacMetadataReader;
import com.google.android.exoplayer2.extractor.FlacSeekTableSeekMap;
import com.google.android.exoplayer2.extractor.FlacStreamMetadata;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import defpackage.pb;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* renamed from: d3  reason: default package */
public final class d3 extends pb {
    @Nullable
    public FlacStreamMetadata n;
    @Nullable
    public a o;

    /* renamed from: d3$a */
    public static final class a implements n7 {
        public final FlacStreamMetadata a;
        public final FlacStreamMetadata.SeekTable b;
        public long c = -1;
        public long d = -1;

        public a(FlacStreamMetadata flacStreamMetadata, FlacStreamMetadata.SeekTable seekTable) {
            this.a = flacStreamMetadata;
            this.b = seekTable;
        }

        public SeekMap createSeekMap() {
            boolean z;
            if (this.c != -1) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkState(z);
            return new FlacSeekTableSeekMap(this.a, this.c);
        }

        public long read(ExtractorInput extractorInput) {
            long j = this.d;
            if (j < 0) {
                return -1;
            }
            long j2 = -(j + 2);
            this.d = -1;
            return j2;
        }

        public void setFirstFrameOffset(long j) {
            this.c = j;
        }

        public void startSeek(long j) {
            long[] jArr = this.b.a;
            this.d = jArr[Util.binarySearchFloor(jArr, j, true, true)];
        }
    }

    public static boolean verifyBitstreamType(ParsableByteArray parsableByteArray) {
        if (parsableByteArray.bytesLeft() >= 5 && parsableByteArray.readUnsignedByte() == 127 && parsableByteArray.readUnsignedInt() == 1179402563) {
            return true;
        }
        return false;
    }

    public final long b(ParsableByteArray parsableByteArray) {
        boolean z;
        if (parsableByteArray.getData()[0] == -1) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return -1;
        }
        int i = (parsableByteArray.getData()[2] & 255) >> 4;
        if (i == 6 || i == 7) {
            parsableByteArray.skipBytes(4);
            parsableByteArray.readUtf8EncodedLong();
        }
        int readFrameBlockSizeSamplesFromKey = FlacFrameReader.readFrameBlockSizeSamplesFromKey(parsableByteArray, i);
        parsableByteArray.setPosition(0);
        return (long) readFrameBlockSizeSamplesFromKey;
    }

    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    public final boolean c(ParsableByteArray parsableByteArray, long j, pb.a aVar) {
        boolean z;
        byte[] data = parsableByteArray.getData();
        FlacStreamMetadata flacStreamMetadata = this.n;
        if (flacStreamMetadata == null) {
            FlacStreamMetadata flacStreamMetadata2 = new FlacStreamMetadata(data, 17);
            this.n = flacStreamMetadata2;
            aVar.a = flacStreamMetadata2.getFormat(Arrays.copyOfRange(data, 9, parsableByteArray.limit()), (Metadata) null);
            return true;
        }
        byte b = data[0];
        if ((b & Byte.MAX_VALUE) == 3) {
            FlacStreamMetadata.SeekTable readSeekTableMetadataBlock = FlacMetadataReader.readSeekTableMetadataBlock(parsableByteArray);
            FlacStreamMetadata copyWithSeekTable = flacStreamMetadata.copyWithSeekTable(readSeekTableMetadataBlock);
            this.n = copyWithSeekTable;
            this.o = new a(copyWithSeekTable, readSeekTableMetadataBlock);
            return true;
        }
        if (b == -1) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return true;
        }
        a aVar2 = this.o;
        if (aVar2 != null) {
            aVar2.setFirstFrameOffset(j);
            aVar.b = this.o;
        }
        Assertions.checkNotNull(aVar.a);
        return false;
    }

    public final void d(boolean z) {
        super.d(z);
        if (z) {
            this.n = null;
            this.o = null;
        }
    }
}
