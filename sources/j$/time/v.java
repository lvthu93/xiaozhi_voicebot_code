package j$.time;

import j$.time.chrono.C0037a;
import j$.time.chrono.C0038b;
import j$.time.format.F;
import j$.time.format.w;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.a;
import j$.time.temporal.l;
import j$.time.temporal.m;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.time.temporal.u;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

public final class v implements l, m, Comparable, Serializable {
    private static final long serialVersionUID = 4183400860270640070L;
    private final int a;
    private final int b;

    static {
        w wVar = new w();
        wVar.k(a.YEAR, 4, 10, F.EXCEEDS_PAD);
        wVar.e('-');
        wVar.m(a.MONTH_OF_YEAR, 2);
        wVar.v(Locale.getDefault());
    }

    private v(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    static v T(DataInput dataInput) {
        int readInt = dataInput.readInt();
        byte readByte = dataInput.readByte();
        a.YEAR.R((long) readInt);
        a.MONTH_OF_YEAR.R((long) readByte);
        return new v(readInt, readByte);
    }

    private v U(int i, int i2) {
        return (this.a == i && this.b == i2) ? this : new v(i, i2);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r(MqttWireMessage.MESSAGE_TYPE_PINGREQ, this);
    }

    public final long E(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        int i = u.a[((a) qVar).ordinal()];
        int i2 = this.b;
        int i3 = 1;
        if (i == 1) {
            return (long) i2;
        }
        int i4 = this.a;
        if (i == 2) {
            return ((((long) i4) * 12) + ((long) i2)) - 1;
        }
        if (i == 3) {
            if (i4 < 1) {
                i4 = 1 - i4;
            }
            return (long) i4;
        } else if (i == 4) {
            return (long) i4;
        } else {
            if (i == 5) {
                if (i4 < 1) {
                    i3 = 0;
                }
                return (long) i3;
            }
            throw new t(c.a("Unsupported field: ", qVar));
        }
    }

    public final Object H(s sVar) {
        return sVar == p.e() ? j$.time.chrono.t.d : sVar == p.j() ? ChronoUnit.MONTHS : p.c(this, sVar);
    }

    /* renamed from: Q */
    public final v d(long j, TemporalUnit temporalUnit) {
        if (!(temporalUnit instanceof ChronoUnit)) {
            return (v) temporalUnit.k(this, j);
        }
        switch (u.b[((ChronoUnit) temporalUnit).ordinal()]) {
            case 1:
                return R(j);
            case 2:
                return S(j);
            case 3:
                return S(j$.lang.a.m(j, (long) 10));
            case 4:
                return S(j$.lang.a.m(j, (long) 100));
            case 5:
                return S(j$.lang.a.m(j, (long) 1000));
            case 6:
                a aVar = a.ERA;
                return c(j$.lang.a.l(E(aVar), j), aVar);
            default:
                throw new t("Unsupported unit: " + temporalUnit);
        }
    }

    public final v R(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = (((long) this.a) * 12) + ((long) (this.b - 1)) + j;
        long j3 = (long) 12;
        return U(a.YEAR.Q(j$.lang.a.i(j2, j3)), ((int) j$.lang.a.k(j2, j3)) + 1);
    }

    public final v S(long j) {
        return j == 0 ? this : U(a.YEAR.Q(((long) this.a) + j), this.b);
    }

    /* renamed from: V */
    public final v c(long j, q qVar) {
        if (!(qVar instanceof a)) {
            return (v) qVar.H(this, j);
        }
        a aVar = (a) qVar;
        aVar.R(j);
        int i = u.a[aVar.ordinal()];
        int i2 = this.a;
        if (i != 1) {
            int i3 = this.b;
            if (i == 2) {
                return R(j - (((((long) i2) * 12) + ((long) i3)) - 1));
            }
            if (i == 3) {
                if (i2 < 1) {
                    j = 1 - j;
                }
                int i4 = (int) j;
                a.YEAR.R((long) i4);
                return U(i4, i3);
            } else if (i == 4) {
                int i5 = (int) j;
                a.YEAR.R((long) i5);
                return U(i5, i3);
            } else if (i != 5) {
                throw new t(c.a("Unsupported field: ", qVar));
            } else if (E(a.ERA) == j) {
                return this;
            } else {
                int i6 = 1 - i2;
                a.YEAR.R((long) i6);
                return U(i6, i3);
            }
        } else {
            int i7 = (int) j;
            a.MONTH_OF_YEAR.R((long) i7);
            return U(i2, i7);
        }
    }

    /* access modifiers changed from: package-private */
    public final void W(DataOutput dataOutput) {
        dataOutput.writeInt(this.a);
        dataOutput.writeByte(this.b);
    }

    public final int compareTo(Object obj) {
        v vVar = (v) obj;
        int i = this.a - vVar.a;
        return i == 0 ? this.b - vVar.b : i;
    }

    public final boolean e(q qVar) {
        return qVar instanceof a ? qVar == a.YEAR || qVar == a.MONTH_OF_YEAR || qVar == a.PROLEPTIC_MONTH || qVar == a.YEAR_OF_ERA || qVar == a.ERA : qVar != null && qVar.k(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        r5 = (j$.time.v) r5;
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
            boolean r1 = r5 instanceof j$.time.v
            r2 = 0
            if (r1 == 0) goto L_0x001a
            j$.time.v r5 = (j$.time.v) r5
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
        throw new UnsupportedOperationException("Method not decompiled: j$.time.v.equals(java.lang.Object):boolean");
    }

    public final l g(long j, ChronoUnit chronoUnit) {
        return j == Long.MIN_VALUE ? d(Long.MAX_VALUE, chronoUnit).d(1, chronoUnit) : d(-j, chronoUnit);
    }

    public final int hashCode() {
        return (this.b << 27) ^ this.a;
    }

    public final int k(q qVar) {
        return m(qVar).a(E(qVar), qVar);
    }

    public final l l(LocalDate localDate) {
        return (v) C0038b.a(localDate, this);
    }

    public final u m(q qVar) {
        if (qVar != a.YEAR_OF_ERA) {
            return p.d(this, qVar);
        }
        return u.j(1, this.a <= 0 ? 1000000000 : 999999999);
    }

    public final l p(l lVar) {
        if (((C0037a) C0038b.s(lVar)).equals(j$.time.chrono.t.d)) {
            return lVar.c(((((long) this.a) * 12) + ((long) this.b)) - 1, a.PROLEPTIC_MONTH);
        }
        throw new DateTimeException("Adjustment only supported on ISO date-time");
    }

    public final String toString() {
        int i;
        int i2 = this.a;
        int abs = Math.abs(i2);
        StringBuilder sb = new StringBuilder(9);
        if (abs < 1000) {
            if (i2 < 0) {
                sb.append(i2 - 10000);
                i = 1;
            } else {
                sb.append(i2 + 10000);
                i = 0;
            }
            sb.deleteCharAt(i);
        } else {
            sb.append(i2);
        }
        int i3 = this.b;
        sb.append(i3 < 10 ? "-0" : "-");
        sb.append(i3);
        return sb.toString();
    }
}
