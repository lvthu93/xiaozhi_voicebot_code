package com.google.android.exoplayer2.source;

public class CompositeSequenceableLoader implements SequenceableLoader {
    public final SequenceableLoader[] c;

    public CompositeSequenceableLoader(SequenceableLoader[] sequenceableLoaderArr) {
        this.c = sequenceableLoaderArr;
    }

    public boolean continueLoading(long j) {
        boolean z;
        long j2 = j;
        boolean z2 = false;
        while (true) {
            long nextLoadPositionUs = getNextLoadPositionUs();
            if (nextLoadPositionUs != Long.MIN_VALUE) {
                boolean z3 = false;
                for (SequenceableLoader sequenceableLoader : this.c) {
                    long nextLoadPositionUs2 = sequenceableLoader.getNextLoadPositionUs();
                    if (nextLoadPositionUs2 == Long.MIN_VALUE || nextLoadPositionUs2 > j2) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (nextLoadPositionUs2 == nextLoadPositionUs || z) {
                        z3 |= sequenceableLoader.continueLoading(j2);
                    }
                }
                z2 |= z3;
                if (!z3) {
                    break;
                }
            } else {
                break;
            }
        }
        return z2;
    }

    public final long getBufferedPositionUs() {
        long j = Long.MAX_VALUE;
        for (SequenceableLoader bufferedPositionUs : this.c) {
            long bufferedPositionUs2 = bufferedPositionUs.getBufferedPositionUs();
            if (bufferedPositionUs2 != Long.MIN_VALUE) {
                j = Math.min(j, bufferedPositionUs2);
            }
        }
        if (j == Long.MAX_VALUE) {
            return Long.MIN_VALUE;
        }
        return j;
    }

    public final long getNextLoadPositionUs() {
        long j = Long.MAX_VALUE;
        for (SequenceableLoader nextLoadPositionUs : this.c) {
            long nextLoadPositionUs2 = nextLoadPositionUs.getNextLoadPositionUs();
            if (nextLoadPositionUs2 != Long.MIN_VALUE) {
                j = Math.min(j, nextLoadPositionUs2);
            }
        }
        if (j == Long.MAX_VALUE) {
            return Long.MIN_VALUE;
        }
        return j;
    }

    public boolean isLoading() {
        for (SequenceableLoader isLoading : this.c) {
            if (isLoading.isLoading()) {
                return true;
            }
        }
        return false;
    }

    public final void reevaluateBuffer(long j) {
        for (SequenceableLoader reevaluateBuffer : this.c) {
            reevaluateBuffer.reevaluateBuffer(j);
        }
    }
}
