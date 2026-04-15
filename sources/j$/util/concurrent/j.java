package j$.util.concurrent;

import j$.util.C0057b;
import j$.util.U;
import java.util.Comparator;
import java.util.function.Consumer;

final class j extends q implements U {
    public final /* synthetic */ int i;
    long j;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public /* synthetic */ j(l[] lVarArr, int i2, int i3, int i4, long j2, int i5) {
        super(lVarArr, i2, i3, i4);
        this.i = i5;
        this.j = j2;
    }

    public final int characteristics() {
        switch (this.i) {
            case 0:
                return 4353;
            default:
                return 4352;
        }
    }

    public final long estimateSize() {
        switch (this.i) {
            case 0:
                return this.j;
            default:
                return this.j;
        }
    }

    public final void forEachRemaining(Consumer consumer) {
        switch (this.i) {
            case 0:
                consumer.getClass();
                while (true) {
                    l a = a();
                    if (a != null) {
                        consumer.accept(a.b);
                    } else {
                        return;
                    }
                }
            default:
                consumer.getClass();
                while (true) {
                    l a2 = a();
                    if (a2 != null) {
                        consumer.accept(a2.c);
                    } else {
                        return;
                    }
                }
        }
    }

    public final Comparator getComparator() {
        switch (this.i) {
            case 0:
                throw new IllegalStateException();
            default:
                throw new IllegalStateException();
        }
    }

    public final /* synthetic */ long getExactSizeIfKnown() {
        switch (this.i) {
            case 0:
                return C0057b.d(this);
            default:
                return C0057b.d(this);
        }
    }

    public final /* synthetic */ boolean hasCharacteristics(int i2) {
        switch (this.i) {
            case 0:
                return C0057b.e(this, i2);
            default:
                return C0057b.e(this, i2);
        }
    }

    public final boolean tryAdvance(Consumer consumer) {
        switch (this.i) {
            case 0:
                consumer.getClass();
                l a = a();
                if (a == null) {
                    return false;
                }
                consumer.accept(a.b);
                return true;
            default:
                consumer.getClass();
                l a2 = a();
                if (a2 == null) {
                    return false;
                }
                consumer.accept(a2.c);
                return true;
        }
    }

    public final U trySplit() {
        switch (this.i) {
            case 0:
                int i2 = this.f;
                int i3 = this.g;
                int i4 = (i2 + i3) >>> 1;
                if (i4 <= i2) {
                    return null;
                }
                l[] lVarArr = this.a;
                int i5 = this.h;
                this.g = i4;
                long j2 = this.j >>> 1;
                this.j = j2;
                return new j(lVarArr, i5, i4, i3, j2, 0);
            default:
                int i6 = this.f;
                int i7 = this.g;
                int i8 = (i6 + i7) >>> 1;
                if (i8 <= i6) {
                    return null;
                }
                l[] lVarArr2 = this.a;
                int i9 = this.h;
                this.g = i8;
                long j3 = this.j >>> 1;
                this.j = j3;
                return new j(lVarArr2, i9, i8, i7, j3, 1);
        }
    }
}
