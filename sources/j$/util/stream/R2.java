package j$.util.stream;

import j$.util.C0058c;
import j$.util.List$EL;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

final class R2 extends J2 {
    private ArrayList d;

    R2(C0182v2 v2Var, Comparator comparator) {
        super(v2Var, comparator);
    }

    public final void accept(Object obj) {
        this.d.add(obj);
    }

    public final void c(long j) {
        ArrayList arrayList;
        if (j < 2147483639) {
            if (j >= 0) {
                int i = (int) j;
            } else {
                arrayList = new ArrayList();
            }
            this.d = arrayList;
            return;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public final void end() {
        List$EL.sort(this.d, this.b);
        C0182v2 v2Var = this.a;
        v2Var.c((long) this.d.size());
        if (!this.c) {
            ArrayList<Object> arrayList = this.d;
            Objects.requireNonNull(v2Var);
            C0075a aVar = new C0075a(v2Var, 3);
            if (arrayList instanceof C0058c) {
                ((C0058c) arrayList).forEach(aVar);
            } else {
                Objects.requireNonNull(aVar);
                for (Object accept : arrayList) {
                    aVar.accept(accept);
                }
            }
        } else {
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (v2Var.e()) {
                    break;
                }
                v2Var.accept(next);
            }
        }
        v2Var.end();
        this.d = null;
    }
}
