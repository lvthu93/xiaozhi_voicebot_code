package defpackage;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ForwardingExtractorInput;
import com.google.android.exoplayer2.util.Assertions;

/* renamed from: nb  reason: default package */
public final class nb extends ForwardingExtractorInput {
    public final long b;

    public nb(ExtractorInput extractorInput, long j) {
        super(extractorInput);
        boolean z;
        if (extractorInput.getPosition() >= j) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        this.b = j;
    }

    public long getLength() {
        return super.getLength() - this.b;
    }

    public long getPeekPosition() {
        return super.getPeekPosition() - this.b;
    }

    public long getPosition() {
        return super.getPosition() - this.b;
    }

    public <E extends Throwable> void setRetryPosition(long j, E e) throws Throwable {
        super.setRetryPosition(j + this.b, e);
    }
}
