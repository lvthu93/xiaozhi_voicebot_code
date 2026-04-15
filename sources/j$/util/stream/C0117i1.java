package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/* renamed from: j$.util.stream.i1  reason: case insensitive filesystem */
final class C0117i1 extends P0 implements H0 {
    C0117i1(long j, IntFunction intFunction) {
        super(j, intFunction);
    }

    public final /* synthetic */ void accept(double d) {
        D0.z();
        throw null;
    }

    public final /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public final /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final void accept(Object obj) {
        int i = this.b;
        Object[] objArr = this.a;
        if (i < objArr.length) {
            this.b = i + 1;
            objArr[i] = obj;
            return;
        }
        throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", new Object[]{Integer.valueOf(objArr.length)}));
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final M0 build() {
        int i = this.b;
        Object[] objArr = this.a;
        if (i >= objArr.length) {
            return this;
        }
        throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", new Object[]{Integer.valueOf(this.b), Integer.valueOf(objArr.length)}));
    }

    public final void c(long j) {
        Object[] objArr = this.a;
        if (j == ((long) objArr.length)) {
            this.b = 0;
        } else {
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", new Object[]{Long.valueOf(j), Integer.valueOf(objArr.length)}));
        }
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final void end() {
        int i = this.b;
        Object[] objArr = this.a;
        if (i < objArr.length) {
            throw new IllegalStateException(String.format("End size %d is less than fixed size %d", new Object[]{Integer.valueOf(this.b), Integer.valueOf(objArr.length)}));
        }
    }

    public final String toString() {
        Object[] objArr = this.a;
        return String.format("FixedNodeBuilder[%d][%s]", new Object[]{Integer.valueOf(objArr.length - this.b), Arrays.toString(objArr)});
    }
}
