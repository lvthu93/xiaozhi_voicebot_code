package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

public abstract class BinarySearchSeeker {
    public final BinarySearchSeekMap a;
    public final TimestampSeeker b;
    @Nullable
    public SeekOperationParams c;
    public final int d;

    public static class BinarySearchSeekMap implements SeekMap {
        public final SeekTimestampConverter a;
        public final long b;
        public final long c;
        public final long d;
        public final long e;
        public final long f;
        public final long g;

        public BinarySearchSeekMap(SeekTimestampConverter seekTimestampConverter, long j, long j2, long j3, long j4, long j5, long j6) {
            this.a = seekTimestampConverter;
            this.b = j;
            this.c = j2;
            this.d = j3;
            this.e = j4;
            this.f = j5;
            this.g = j6;
        }

        public long getDurationUs() {
            return this.b;
        }

        public SeekMap.SeekPoints getSeekPoints(long j) {
            return new SeekMap.SeekPoints(new SeekPoint(j, SeekOperationParams.a(this.a.timeUsToTargetTime(j), this.c, this.d, this.e, this.f, this.g)));
        }

        public boolean isSeekable() {
            return true;
        }

        public long timeUsToTargetTime(long j) {
            return this.a.timeUsToTargetTime(j);
        }
    }

    public static final class DefaultSeekTimestampConverter implements SeekTimestampConverter {
        public long timeUsToTargetTime(long j) {
            return j;
        }
    }

    public static class SeekOperationParams {
        public final long a;
        public final long b;
        public final long c;
        public long d;
        public long e;
        public long f;
        public long g;
        public long h;

        public SeekOperationParams(long j, long j2, long j3, long j4, long j5, long j6, long j7) {
            this.a = j;
            this.b = j2;
            this.d = j3;
            this.e = j4;
            this.f = j5;
            this.g = j6;
            this.c = j7;
            this.h = a(j2, j3, j4, j5, j6, j7);
        }

        public static long a(long j, long j2, long j3, long j4, long j5, long j6) {
            if (j4 + 1 >= j5 || j2 + 1 >= j3) {
                return j4;
            }
            long j7 = (long) (((float) (j - j2)) * (((float) (j5 - j4)) / ((float) (j3 - j2))));
            return Util.constrainValue(((j7 + j4) - j6) - (j7 / 20), j4, j5 - 1);
        }
    }

    public interface SeekTimestampConverter {
        long timeUsToTargetTime(long j);
    }

    public static final class TimestampSearchResult {
        public static final TimestampSearchResult d = new TimestampSearchResult(-3, -9223372036854775807L, -1);
        public final int a;
        public final long b;
        public final long c;

        public TimestampSearchResult(int i, long j, long j2) {
            this.a = i;
            this.b = j;
            this.c = j2;
        }

        public static TimestampSearchResult overestimatedResult(long j, long j2) {
            return new TimestampSearchResult(-1, j, j2);
        }

        public static TimestampSearchResult targetFoundResult(long j) {
            return new TimestampSearchResult(0, -9223372036854775807L, j);
        }

        public static TimestampSearchResult underestimatedResult(long j, long j2) {
            return new TimestampSearchResult(-2, j, j2);
        }
    }

    public interface TimestampSeeker {
        void onSeekFinished();

        TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j) throws IOException;
    }

    public BinarySearchSeeker(SeekTimestampConverter seekTimestampConverter, TimestampSeeker timestampSeeker, long j, long j2, long j3, long j4, long j5, int i) {
        this.b = timestampSeeker;
        this.d = i;
        this.a = new BinarySearchSeekMap(seekTimestampConverter, j, 0, j2, j3, j4, j5);
    }

    public static int a(ExtractorInput extractorInput, long j, PositionHolder positionHolder) {
        if (j == extractorInput.getPosition()) {
            return 0;
        }
        positionHolder.a = j;
        return 1;
    }

    public final SeekMap getSeekMap() {
        return this.a;
    }

    public int handlePendingSeek(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        boolean z;
        ExtractorInput extractorInput2 = extractorInput;
        PositionHolder positionHolder2 = positionHolder;
        while (true) {
            SeekOperationParams seekOperationParams = (SeekOperationParams) Assertions.checkStateNotNull(this.c);
            long j = seekOperationParams.f;
            long j2 = seekOperationParams.g;
            long j3 = seekOperationParams.h;
            TimestampSeeker timestampSeeker = this.b;
            if (j2 - j <= ((long) this.d)) {
                this.c = null;
                timestampSeeker.onSeekFinished();
                return a(extractorInput2, j, positionHolder2);
            }
            long position = j3 - extractorInput.getPosition();
            if (position < 0 || position > 262144) {
                z = false;
            } else {
                extractorInput2.skipFully((int) position);
                z = true;
            }
            if (!z) {
                return a(extractorInput2, j3, positionHolder2);
            }
            extractorInput.resetPeekPosition();
            TimestampSearchResult searchForTimestamp = timestampSeeker.searchForTimestamp(extractorInput2, seekOperationParams.b);
            int i = searchForTimestamp.a;
            if (i != -3) {
                long j4 = searchForTimestamp.b;
                long j5 = searchForTimestamp.c;
                if (i == -2) {
                    seekOperationParams.d = j4;
                    long j6 = j5;
                    seekOperationParams.f = j6;
                    long j7 = seekOperationParams.b;
                    long j8 = seekOperationParams.e;
                    seekOperationParams.h = SeekOperationParams.a(j7, j4, j8, j6, seekOperationParams.g, seekOperationParams.c);
                } else if (i == -1) {
                    seekOperationParams.e = j4;
                    seekOperationParams.g = j5;
                    long j9 = seekOperationParams.b;
                    long j10 = seekOperationParams.d;
                    seekOperationParams.h = SeekOperationParams.a(j9, j10, j4, seekOperationParams.f, j5, seekOperationParams.c);
                } else if (i == 0) {
                    long position2 = j5 - extractorInput.getPosition();
                    if (position2 >= 0 && position2 <= 262144) {
                        extractorInput2.skipFully((int) position2);
                    }
                    this.c = null;
                    timestampSeeker.onSeekFinished();
                    return a(extractorInput2, j5, positionHolder2);
                } else {
                    throw new IllegalStateException("Invalid case");
                }
            } else {
                this.c = null;
                timestampSeeker.onSeekFinished();
                return a(extractorInput2, j3, positionHolder2);
            }
        }
    }

    public final boolean isSeeking() {
        return this.c != null;
    }

    public final void setSeekTargetUs(long j) {
        long j2 = j;
        SeekOperationParams seekOperationParams = this.c;
        if (seekOperationParams == null || seekOperationParams.a != j2) {
            BinarySearchSeekMap binarySearchSeekMap = this.a;
            SeekOperationParams seekOperationParams2 = r1;
            SeekOperationParams seekOperationParams3 = new SeekOperationParams(j, binarySearchSeekMap.timeUsToTargetTime(j2), binarySearchSeekMap.c, binarySearchSeekMap.d, binarySearchSeekMap.e, binarySearchSeekMap.f, binarySearchSeekMap.g);
            this.c = seekOperationParams2;
        }
    }
}
