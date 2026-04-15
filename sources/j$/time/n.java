package j$.time;

import j$.time.chrono.C0037a;
import j$.time.chrono.C0038b;
import j$.time.format.w;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import j$.time.temporal.l;
import j$.time.temporal.m;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.util.Objects;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

public final class n implements TemporalAccessor, m, Comparable, Serializable {
    private static final long serialVersionUID = -939150713474957432L;
    private final int a;
    private final int b;

    static {
        w wVar = new w();
        wVar.f("--");
        wVar.m(a.MONTH_OF_YEAR, 2);
        wVar.e('-');
        wVar.m(a.DAY_OF_MONTH, 2);
        wVar.v(Locale.getDefault());
    }

    private n(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    static n Q(DataInput dataInput) {
        byte readByte = dataInput.readByte();
        byte readByte2 = dataInput.readByte();
        l T = l.T(readByte);
        Objects.requireNonNull(T, "month");
        a.DAY_OF_MONTH.R((long) readByte2);
        if (readByte2 <= T.S()) {
            return new n(T.getValue(), readByte2);
        }
        String name = T.name();
        throw new DateTimeException("Illegal value for DayOfMonth field, value " + readByte2 + " is not valid for month " + name);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r(MqttWireMessage.MESSAGE_TYPE_PINGRESP, this);
    }

    public final long E(q qVar) {
        int i;
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        int i2 = m.a[((a) qVar).ordinal()];
        if (i2 == 1) {
            i = this.b;
        } else if (i2 == 2) {
            i = this.a;
        } else {
            throw new t(c.a("Unsupported field: ", qVar));
        }
        return (long) i;
    }

    public final Object H(s sVar) {
        return sVar == p.e() ? j$.time.chrono.t.d : p.c(this, sVar);
    }

    /* access modifiers changed from: package-private */
    public final void R(DataOutput dataOutput) {
        dataOutput.writeByte(this.a);
        dataOutput.writeByte(this.b);
    }

    public final int compareTo(Object obj) {
        n nVar = (n) obj;
        int i = this.a - nVar.a;
        return i == 0 ? this.b - nVar.b : i;
    }

    public final boolean e(q qVar) {
        return qVar instanceof a ? qVar == a.MONTH_OF_YEAR || qVar == a.DAY_OF_MONTH : qVar != null && qVar.k(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        r5 = (j$.time.n) r5;
        r1 = r5.a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r4 != r5) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof j$.time.n
            r2 = 0
            if (r1 == 0) goto L_0x001a
            j$.time.n r5 = (j$.time.n) r5
            int r1 = r5.a
            int r3 = r4.a
            if (r3 != r1) goto L_0x0018
            int r1 = r4.b
            int r5 = r5.b
            if (r1 != r5) goto L_0x0018
            goto L_0x0019
        L_0x0018:
            r0 = 0
        L_0x0019:
            return r0
        L_0x001a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.n.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        return (this.a << 6) + this.b;
    }

    public final int k(q qVar) {
        return m(qVar).a(E(qVar), qVar);
    }

    public final u m(q qVar) {
        if (qVar == a.MONTH_OF_YEAR) {
            return qVar.m();
        }
        if (qVar != a.DAY_OF_MONTH) {
            return p.d(this, qVar);
        }
        int i = this.a;
        l T = l.T(i);
        T.getClass();
        int i2 = k.a[T.ordinal()];
        return u.l((long) (i2 != 1 ? (i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5) ? 30 : 31 : 28), (long) l.T(i).S());
    }

    public final l p(l lVar) {
        if (((C0037a) C0038b.s(lVar)).equals(j$.time.chrono.t.d)) {
            l c = lVar.c((long) this.a, a.MONTH_OF_YEAR);
            a aVar = a.DAY_OF_MONTH;
            return c.c(Math.min(c.m(aVar).d(), (long) this.b), aVar);
        }
        throw new DateTimeException("Adjustment only supported on ISO date-time");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(10);
        sb.append("--");
        int i = this.a;
        sb.append(i < 10 ? "0" : "");
        sb.append(i);
        int i2 = this.b;
        sb.append(i2 < 10 ? "-0" : "-");
        sb.append(i2);
        return sb.toString();
    }
}
