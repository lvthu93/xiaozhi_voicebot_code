package defpackage;

import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ts.TsUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* renamed from: sc  reason: default package */
public final class sc extends BinarySearchSeeker {

    /* renamed from: sc$a */
    public static final class a implements BinarySearchSeeker.TimestampSeeker {
        public final TimestampAdjuster a;
        public final ParsableByteArray b = new ParsableByteArray();
        public final int c;
        public final int d;

        public a(int i, TimestampAdjuster timestampAdjuster, int i2) {
            this.c = i;
            this.a = timestampAdjuster;
            this.d = i2;
        }

        public void onSeekFinished() {
            this.b.reset(Util.f);
        }

        public BinarySearchSeeker.TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j) throws IOException {
            int findSyncBytePosition;
            int i;
            long position = extractorInput.getPosition();
            int min = (int) Math.min((long) this.d, extractorInput.getLength() - position);
            ParsableByteArray parsableByteArray = this.b;
            parsableByteArray.reset(min);
            extractorInput.peekFully(parsableByteArray.getData(), 0, min);
            int limit = parsableByteArray.limit();
            long j2 = -1;
            long j3 = -1;
            long j4 = -9223372036854775807L;
            while (parsableByteArray.bytesLeft() >= 188 && (i = findSyncBytePosition + 188) <= limit) {
                long readPcrFromPacket = TsUtil.readPcrFromPacket(parsableByteArray, (findSyncBytePosition = TsUtil.findSyncBytePosition(parsableByteArray.getData(), parsableByteArray.getPosition(), limit)), this.c);
                if (readPcrFromPacket != -9223372036854775807L) {
                    long adjustTsTimestamp = this.a.adjustTsTimestamp(readPcrFromPacket);
                    if (adjustTsTimestamp > j) {
                        if (j4 == -9223372036854775807L) {
                            return BinarySearchSeeker.TimestampSearchResult.overestimatedResult(adjustTsTimestamp, position);
                        }
                        return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(position + j3);
                    } else if (100000 + adjustTsTimestamp > j) {
                        return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(position + ((long) findSyncBytePosition));
                    } else {
                        j3 = (long) findSyncBytePosition;
                        j4 = adjustTsTimestamp;
                    }
                }
                parsableByteArray.setPosition(i);
                j2 = (long) i;
            }
            if (j4 != -9223372036854775807L) {
                return BinarySearchSeeker.TimestampSearchResult.underestimatedResult(j4, position + j2);
            }
            return BinarySearchSeeker.TimestampSearchResult.d;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public sc(TimestampAdjuster timestampAdjuster, long j, long j2, int i, int i2) {
        super(new BinarySearchSeeker.DefaultSeekTimestampConverter(), new a(i, timestampAdjuster, i2), j, j + 1, 0, j2, 188, 940);
        TimestampAdjuster timestampAdjuster2 = timestampAdjuster;
    }
}
