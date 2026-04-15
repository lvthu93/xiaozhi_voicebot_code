package j$.util.stream;

import j$.util.C0057b;
import j$.util.Objects;
import j$.util.U;
import java.util.Comparator;
import java.util.function.Consumer;

final class H3 extends I3 implements U {
    H3(U u, long j, long j2) {
        super(u, j, j2, 0, Math.min(u.estimateSize(), j2));
    }

    private H3(U u, long j, long j2, long j3, long j4) {
        super(u, j, j2, j3, j4);
    }

    /* access modifiers changed from: protected */
    public final U a(U u, long j, long j2, long j3, long j4) {
        return new H3(u, j, j2, j3, j4);
    }

    public final void forEachRemaining(Consumer consumer) {
        Objects.requireNonNull(consumer);
        long j = this.e;
        long j2 = this.a;
        if (j2 < j) {
            long j3 = this.d;
            if (j3 < j) {
                if (j3 < j2 || this.c.estimateSize() + j3 > this.b) {
                    while (j2 > this.d) {
                        this.c.tryAdvance(new R0(10));
                        this.d++;
                    }
                    while (this.d < this.e) {
                        this.c.tryAdvance(consumer);
                        this.d++;
                    }
                    return;
                }
                this.c.forEachRemaining(consumer);
                this.d = this.e;
            }
        }
    }

    public final Comparator getComparator() {
        throw new IllegalStateException();
    }

    public final /* synthetic */ long getExactSizeIfKnown() {
        return C0057b.d(this);
    }

    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return C0057b.e(this, i);
    }

    public final boolean tryAdvance(Consumer consumer) {
        long j;
        Objects.requireNonNull(consumer);
        long j2 = this.e;
        long j3 = this.a;
        if (j3 >= j2) {
            return false;
        }
        while (true) {
            j = this.d;
            if (j3 <= j) {
                break;
            }
            this.c.tryAdvance(new R0(11));
            this.d++;
        }
        if (j >= this.e) {
            return false;
        }
        this.d = j + 1;
        return this.c.tryAdvance(consumer);
    }
}
