package j$.util.stream;

import java.util.Arrays;

final class T2 extends H2 {
    private int[] c;
    private int d;

    T2(C0182v2 v2Var) {
        super(v2Var);
    }

    public final void accept(int i) {
        int[] iArr = this.c;
        int i2 = this.d;
        this.d = i2 + 1;
        iArr[i2] = i;
    }

    public final void c(long j) {
        if (j < 2147483639) {
            this.c = new int[((int) j)];
            return;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public final void end() {
        int i = 0;
        Arrays.sort(this.c, 0, this.d);
        C0182v2 v2Var = this.a;
        v2Var.c((long) this.d);
        if (!this.b) {
            while (i < this.d) {
                v2Var.accept(this.c[i]);
                i++;
            }
        } else {
            while (i < this.d && !v2Var.e()) {
                v2Var.accept(this.c[i]);
                i++;
            }
        }
        v2Var.end();
        this.c = null;
    }
}
