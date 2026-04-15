package defpackage;

import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.FlacFrameReader;
import com.google.android.exoplayer2.extractor.FlacStreamMetadata;
import java.io.IOException;

/* renamed from: c3  reason: default package */
public final class c3 extends BinarySearchSeeker {

    /* renamed from: c3$a */
    public static final class a implements BinarySearchSeeker.TimestampSeeker {
        public final FlacStreamMetadata a;
        public final int b;
        public final FlacFrameReader.SampleNumberHolder c = new FlacFrameReader.SampleNumberHolder();

        public a(FlacStreamMetadata flacStreamMetadata, int i) {
            this.a = flacStreamMetadata;
            this.b = i;
        }

        public final long a(ExtractorInput extractorInput) throws IOException {
            FlacFrameReader.SampleNumberHolder sampleNumberHolder;
            FlacStreamMetadata flacStreamMetadata;
            while (true) {
                sampleNumberHolder = this.c;
                flacStreamMetadata = this.a;
                if (extractorInput.getPeekPosition() < extractorInput.getLength() - 6 && !FlacFrameReader.checkFrameHeaderFromPeek(extractorInput, flacStreamMetadata, this.b, sampleNumberHolder)) {
                    extractorInput.advancePeekPosition(1);
                }
            }
            if (extractorInput.getPeekPosition() < extractorInput.getLength() - 6) {
                return sampleNumberHolder.a;
            }
            extractorInput.advancePeekPosition((int) (extractorInput.getLength() - extractorInput.getPeekPosition()));
            return flacStreamMetadata.j;
        }

        public /* bridge */ /* synthetic */ void onSeekFinished() {
            ci.a(this);
        }

        public BinarySearchSeeker.TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j) throws IOException {
            long position = extractorInput.getPosition();
            long a2 = a(extractorInput);
            long peekPosition = extractorInput.getPeekPosition();
            extractorInput.advancePeekPosition(Math.max(6, this.a.c));
            long a3 = a(extractorInput);
            long peekPosition2 = extractorInput.getPeekPosition();
            if (a2 <= j && a3 > j) {
                return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(peekPosition);
            }
            if (a3 <= j) {
                return BinarySearchSeeker.TimestampSearchResult.underestimatedResult(a3, peekPosition2);
            }
            return BinarySearchSeeker.TimestampSearchResult.overestimatedResult(a2, position);
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public c3(com.google.android.exoplayer2.extractor.FlacStreamMetadata r15, int r16, long r17, long r19) {
        /*
            r14 = this;
            r0 = r15
            j$.util.Objects.requireNonNull(r15)
            i2 r1 = new i2
            r2 = 3
            r1.<init>(r2, r15)
            c3$a r2 = new c3$a
            r3 = r16
            r2.<init>(r15, r3)
            long r3 = r15.getDurationUs()
            long r5 = r0.j
            long r11 = r15.getApproxBytesPerFrame()
            r7 = 6
            int r0 = r0.c
            int r13 = java.lang.Math.max(r7, r0)
            r0 = r14
            r7 = r17
            r9 = r19
            r0.<init>(r1, r2, r3, r5, r7, r9, r11, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.c3.<init>(com.google.android.exoplayer2.extractor.FlacStreamMetadata, int, long, long):void");
    }
}
