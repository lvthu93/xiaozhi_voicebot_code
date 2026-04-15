package j$.util.stream;

import java.util.Arrays;

final class K2 extends G2 {
    private Y2 c;

    K2(C0182v2 v2Var) {
        super(v2Var);
    }

    public final void accept(double d) {
        this.c.accept(d);
    }

    public final void c(long j) {
        Y2 y2;
        if (j < 2147483639) {
            if (j > 0) {
                int i = (int) j;
            } else {
                y2 = new Y2();
            }
            this.c = y2;
            return;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public final void end() {
        double[] dArr = (double[]) this.c.b();
        Arrays.sort(dArr);
        C0182v2 v2Var = this.a;
        v2Var.c((long) dArr.length);
        int i = 0;
        if (!this.b) {
            int length = dArr.length;
            while (i < length) {
                v2Var.accept(dArr[i]);
                i++;
            }
        } else {
            int length2 = dArr.length;
            while (i < length2) {
                double d = dArr[i];
                if (v2Var.e()) {
                    break;
                }
                v2Var.accept(d);
                i++;
            }
        }
        v2Var.end();
    }
}
