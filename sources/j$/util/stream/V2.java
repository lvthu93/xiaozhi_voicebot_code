package j$.util.stream;

import java.util.Arrays;
import java.util.Comparator;

final class V2 extends J2 {
    private Object[] d;
    private int e;

    V2(C0182v2 v2Var, Comparator comparator) {
        super(v2Var, comparator);
    }

    public final void accept(Object obj) {
        Object[] objArr = this.d;
        int i = this.e;
        this.e = i + 1;
        objArr[i] = obj;
    }

    public final void c(long j) {
        if (j < 2147483639) {
            this.d = new Object[((int) j)];
            return;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public final void end() {
        int i = 0;
        Arrays.sort(this.d, 0, this.e, this.b);
        C0182v2 v2Var = this.a;
        v2Var.c((long) this.e);
        if (!this.c) {
            while (i < this.e) {
                v2Var.accept(this.d[i]);
                i++;
            }
        } else {
            while (i < this.e && !v2Var.e()) {
                v2Var.accept(this.d[i]);
                i++;
            }
        }
        v2Var.end();
        this.d = null;
    }
}
