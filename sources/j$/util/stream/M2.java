package j$.util.stream;

import java.util.Arrays;

final class M2 extends I2 {
    private C0089c3 c;

    M2(C0182v2 v2Var) {
        super(v2Var);
    }

    public final void accept(long j) {
        this.c.accept(j);
    }

    public final void c(long j) {
        C0089c3 c3Var;
        if (j < 2147483639) {
            if (j > 0) {
                int i = (int) j;
            } else {
                c3Var = new C0089c3();
            }
            this.c = c3Var;
            return;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public final void end() {
        long[] jArr = (long[]) this.c.b();
        Arrays.sort(jArr);
        C0182v2 v2Var = this.a;
        v2Var.c((long) jArr.length);
        int i = 0;
        if (!this.b) {
            int length = jArr.length;
            while (i < length) {
                v2Var.accept(jArr[i]);
                i++;
            }
        } else {
            int length2 = jArr.length;
            while (i < length2) {
                long j = jArr[i];
                if (v2Var.e()) {
                    break;
                }
                v2Var.accept(j);
                i++;
            }
        }
        v2Var.end();
    }
}
