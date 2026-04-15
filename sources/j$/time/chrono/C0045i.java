package j$.time.chrono;

import j$.desugar.sun.nio.fs.c;
import j$.time.temporal.ChronoUnit;
import j$.util.Objects;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/* renamed from: j$.time.chrono.i  reason: case insensitive filesystem */
final class C0045i implements Serializable {
    public static final /* synthetic */ int e = 0;
    private static final long serialVersionUID = 57387258289L;
    private final m a;
    final int b;
    final int c;
    final int d;

    static {
        c.a(new Object[]{ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS});
    }

    C0045i(m mVar, int i, int i2, int i3) {
        Objects.requireNonNull(mVar, "chrono");
        this.a = mVar;
        this.b = i;
        this.c = i2;
        this.d = i3;
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    /* access modifiers changed from: package-private */
    public final void a(DataOutput dataOutput) {
        dataOutput.writeUTF(this.a.j());
        dataOutput.writeInt(this.b);
        dataOutput.writeInt(this.c);
        dataOutput.writeInt(this.d);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        r5 = (j$.time.chrono.C0045i) r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r4 != r5) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof j$.time.chrono.C0045i
            r2 = 0
            if (r1 == 0) goto L_0x002c
            j$.time.chrono.i r5 = (j$.time.chrono.C0045i) r5
            int r1 = r5.b
            int r3 = r4.b
            if (r3 != r1) goto L_0x002a
            int r1 = r4.c
            int r3 = r5.c
            if (r1 != r3) goto L_0x002a
            int r1 = r4.d
            int r3 = r5.d
            if (r1 != r3) goto L_0x002a
            j$.time.chrono.m r5 = r5.a
            j$.time.chrono.m r1 = r4.a
            j$.time.chrono.a r1 = (j$.time.chrono.C0037a) r1
            boolean r5 = r1.equals(r5)
            if (r5 == 0) goto L_0x002a
            goto L_0x002b
        L_0x002a:
            r0 = 0
        L_0x002b:
            return r0
        L_0x002c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.chrono.C0045i.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        return ((C0037a) this.a).hashCode() ^ (Integer.rotateLeft(this.d, 16) + (Integer.rotateLeft(this.c, 8) + this.b));
    }

    public final String toString() {
        int i = this.d;
        int i2 = this.c;
        int i3 = this.b;
        boolean z = i3 == 0 && i2 == 0 && i == 0;
        m mVar = this.a;
        if (z) {
            String j = ((C0037a) mVar).j();
            return j + " P0D";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(((C0037a) mVar).j());
        sb.append(" P");
        if (i3 != 0) {
            sb.append(i3);
            sb.append('Y');
        }
        if (i2 != 0) {
            sb.append(i2);
            sb.append('M');
        }
        if (i != 0) {
            sb.append(i);
            sb.append('D');
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public Object writeReplace() {
        return new F((byte) 9, this);
    }
}
