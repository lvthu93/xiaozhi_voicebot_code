package j$.time.chrono;

import j$.time.DateTimeException;
import j$.time.LocalDate;
import j$.time.temporal.a;
import j$.time.temporal.l;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.u;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;

public final class z implements n, Serializable {
    public static final z d;
    private static final z[] e;
    private static final long serialVersionUID = 1466499369062886794L;
    private final transient int a;
    private final transient LocalDate b;
    private final transient String c;

    static {
        z zVar = new z(-1, LocalDate.of(1868, 1, 1), "Meiji");
        d = zVar;
        z zVar2 = new z(0, LocalDate.of(1912, 7, 30), "Taisho");
        z zVar3 = new z(1, LocalDate.of(1926, 12, 25), "Showa");
        z zVar4 = new z(2, LocalDate.of(1989, 1, 8), "Heisei");
        z zVar5 = new z(3, LocalDate.of(2019, 5, 1), "Reiwa");
        z[] zVarArr = new z[5];
        e = zVarArr;
        zVarArr[0] = zVar;
        zVarArr[1] = zVar2;
        zVarArr[2] = zVar3;
        zVarArr[3] = zVar4;
        zVarArr[4] = zVar5;
    }

    private z(int i, LocalDate localDate, String str) {
        this.a = i;
        this.b = localDate;
        this.c = str;
    }

    static z i(LocalDate localDate) {
        z zVar;
        if (!localDate.Y(y.d)) {
            z[] zVarArr = e;
            int length = zVarArr.length;
            do {
                length--;
                if (length < 0) {
                    return null;
                }
                zVar = zVarArr[length];
            } while (localDate.compareTo(zVar.b) < 0);
            return zVar;
        }
        throw new DateTimeException("JapaneseDate before Meiji 6 are not supported");
    }

    static z j() {
        z[] zVarArr = e;
        return zVarArr[zVarArr.length - 1];
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public static z s(int i) {
        int i2 = (i + 2) - 1;
        if (i2 >= 0) {
            z[] zVarArr = e;
            if (i2 < zVarArr.length) {
                return zVarArr[i2];
            }
        }
        throw new DateTimeException("Invalid era: " + i);
    }

    static long t() {
        long f = a.DAY_OF_YEAR.m().f();
        for (z zVar : e) {
            f = Math.min(f, (long) ((zVar.b.M() - zVar.b.V()) + 1));
            if (zVar.o() != null) {
                f = Math.min(f, (long) (zVar.o().b.V() - 1));
            }
        }
        return f;
    }

    static long u() {
        int X = (999999999 - j().b.X()) + 1;
        z[] zVarArr = e;
        int X2 = zVarArr[0].b.X();
        for (int i = 1; i < zVarArr.length; i++) {
            z zVar = zVarArr[i];
            X = Math.min(X, (zVar.b.X() - X2) + 1);
            X2 = zVar.b.X();
        }
        return (long) X;
    }

    public static z[] v() {
        z[] zVarArr = e;
        return (z[]) Arrays.copyOf(zVarArr, zVarArr.length);
    }

    private Object writeReplace() {
        return new F((byte) 5, this);
    }

    public final /* synthetic */ long E(q qVar) {
        return C0038b.i(this, qVar);
    }

    public final /* synthetic */ Object H(s sVar) {
        return C0038b.o(this, sVar);
    }

    public final /* synthetic */ boolean e(q qVar) {
        return C0038b.k(this, qVar);
    }

    public final int getValue() {
        return this.a;
    }

    public final /* synthetic */ int k(q qVar) {
        return C0038b.h(this, (a) qVar);
    }

    public final u m(q qVar) {
        a aVar = a.ERA;
        return qVar == aVar ? w.d.s(aVar) : p.d(this, qVar);
    }

    /* access modifiers changed from: package-private */
    public final LocalDate n() {
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public final z o() {
        if (this == j()) {
            return null;
        }
        return s(this.a + 1);
    }

    public final /* synthetic */ l p(l lVar) {
        return C0038b.c(this, lVar);
    }

    public final String toString() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public final void w(DataOutput dataOutput) {
        dataOutput.writeByte(this.a);
    }
}
