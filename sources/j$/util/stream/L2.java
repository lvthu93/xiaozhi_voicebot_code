package j$.util.stream;

import java.util.Arrays;

final class L2 extends H2 {
    private C0079a3 c;

    L2(C0182v2 v2Var) {
        super(v2Var);
    }

    public final void accept(int i) {
        this.c.accept(i);
    }

    public final void c(long j) {
        C0079a3 a3Var;
        if (j < 2147483639) {
            if (j > 0) {
                int i = (int) j;
            } else {
                a3Var = new C0079a3();
            }
            this.c = a3Var;
            return;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public final void end() {
        int[] iArr = (int[]) this.c.b();
        Arrays.sort(iArr);
        C0182v2 v2Var = this.a;
        v2Var.c((long) iArr.length);
        int i = 0;
        if (!this.b) {
            int length = iArr.length;
            while (i < length) {
                v2Var.accept(iArr[i]);
                i++;
            }
        } else {
            int length2 = iArr.length;
            while (i < length2) {
                int i2 = iArr[i];
                if (v2Var.e()) {
                    break;
                }
                v2Var.accept(i2);
                i++;
            }
        }
        v2Var.end();
    }
}
