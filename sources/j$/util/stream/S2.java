package j$.util.stream;

import java.util.Arrays;

final class S2 extends G2 {
    private double[] c;
    private int d;

    S2(C0182v2 v2Var) {
        super(v2Var);
    }

    public final void accept(double d2) {
        double[] dArr = this.c;
        int i = this.d;
        this.d = i + 1;
        dArr[i] = d2;
    }

    public final void c(long j) {
        if (j < 2147483639) {
            this.c = new double[((int) j)];
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
