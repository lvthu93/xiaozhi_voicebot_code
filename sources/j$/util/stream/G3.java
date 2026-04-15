package j$.util.stream;

import j$.util.C0057b;
import j$.util.Objects;
import j$.util.Q;
import java.util.Comparator;

abstract class G3 extends I3 implements Q {
    G3(Q q, long j, long j2) {
        super(q, j, j2, 0, Math.min(q.estimateSize(), j2));
    }

    G3(Q q, long j, long j2, long j3, long j4) {
        super(q, j, j2, j3, j4);
    }

    /* access modifiers changed from: protected */
    public abstract Object b();

    public final void forEachRemaining(Object obj) {
        Objects.requireNonNull(obj);
        long j = this.e;
        long j2 = this.a;
        if (j2 < j) {
            long j3 = this.d;
            if (j3 < j) {
                if (j3 < j2 || ((Q) this.c).estimateSize() + j3 > this.b) {
                    while (j2 > this.d) {
                        ((Q) this.c).tryAdvance(b());
                        this.d++;
                    }
                    while (this.d < this.e) {
                        ((Q) this.c).tryAdvance(obj);
                        this.d++;
                    }
                    return;
                }
                ((Q) this.c).forEachRemaining(obj);
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

    public final boolean tryAdvance(Object obj) {
        long j;
        Objects.requireNonNull(obj);
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
            ((Q) this.c).tryAdvance(b());
            this.d++;
        }
        if (j >= this.e) {
            return false;
        }
        this.d = j + 1;
        return ((Q) this.c).tryAdvance(obj);
    }
}
