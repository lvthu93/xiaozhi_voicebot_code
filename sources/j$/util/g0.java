package j$.util;

import j$.util.Iterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;

class g0 implements U {
    private final Collection a;
    private Iterator b;
    private final int c;
    private long d;
    private int e;

    public g0(Collection collection, int i) {
        this.a = collection;
        this.b = null;
        this.c = (i & 4096) == 0 ? i | 64 | 16384 : i;
    }

    public g0(Iterator it) {
        this.a = null;
        this.b = it;
        this.d = Long.MAX_VALUE;
        this.c = 272;
    }

    public final int characteristics() {
        return this.c;
    }

    public final long estimateSize() {
        if (this.b != null) {
            return this.d;
        }
        Collection collection = this.a;
        this.b = collection.iterator();
        long size = (long) collection.size();
        this.d = size;
        return size;
    }

    public final void forEachRemaining(Consumer consumer) {
        consumer.getClass();
        Iterator it = this.b;
        if (it == null) {
            Collection collection = this.a;
            Iterator it2 = collection.iterator();
            this.b = it2;
            this.d = (long) collection.size();
            it = it2;
        }
        Iterator.EL.forEachRemaining(it, consumer);
    }

    public Comparator getComparator() {
        if (C0057b.e(this, 4)) {
            return null;
        }
        throw new IllegalStateException();
    }

    public final /* synthetic */ long getExactSizeIfKnown() {
        return C0057b.d(this);
    }

    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return C0057b.e(this, i);
    }

    public final boolean tryAdvance(Consumer consumer) {
        consumer.getClass();
        if (this.b == null) {
            Collection collection = this.a;
            this.b = collection.iterator();
            this.d = (long) collection.size();
        }
        if (!this.b.hasNext()) {
            return false;
        }
        consumer.accept(this.b.next());
        return true;
    }

    public final U trySplit() {
        long j;
        java.util.Iterator it = this.b;
        if (it == null) {
            Collection collection = this.a;
            java.util.Iterator it2 = collection.iterator();
            this.b = it2;
            j = (long) collection.size();
            this.d = j;
            it = it2;
        } else {
            j = this.d;
        }
        if (j <= 1 || !it.hasNext()) {
            return null;
        }
        int i = this.e + 1024;
        if (((long) i) > j) {
            i = (int) j;
        }
        if (i > 33554432) {
            i = 33554432;
        }
        Object[] objArr = new Object[i];
        int i2 = 0;
        do {
            objArr[i2] = it.next();
            i2++;
            if (i2 >= i || !it.hasNext()) {
                this.e = i2;
                long j2 = this.d;
            }
            objArr[i2] = it.next();
            i2++;
            break;
        } while (!it.hasNext());
        this.e = i2;
        long j22 = this.d;
        if (j22 != Long.MAX_VALUE) {
            this.d = j22 - ((long) i2);
        }
        return new Z(objArr, 0, i2, this.c);
    }
}
