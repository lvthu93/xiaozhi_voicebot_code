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
import j$.time.temporal.u;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

public final class t implements l, m, Comparable, Serializable {
    public static final /* synthetic */ int b = 0;
    private static final long serialVersionUID = -23038383694477807L;
    private final int a;

    static {
        w wVar = new w();
        wVar.k(a.YEAR, 4, 10, F.EXCEEDS_PAD);
        wVar.v(Locale.getDefault());
    }

    private t(int i) {
        this.a = i;
    }

    public static t Q(int i) {
        a.YEAR.R((long) i);
        return new t(i);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r(MqttWireMessage.MESSAGE_TYPE_UNSUBACK, this);
    }

    public final long E(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        int i = s.a[((a) qVar).ordinal()];
        int i2 = this.a;
        int i3 = 1;
        if (i == 1) {
            if (i2 < 1) {
                i2 = 1 - i2;
            }
            return (long) i2;
        } else if (i == 2) {
            return (long) i2;
        } else {
            if (i == 3) {
                if (i2 < 1) {
                    i3 = 0;
                }
                return (long) i3;
            }
            throw new j$.time.temporal.t(c.a("Unsupported field: ", qVar));
        }
    }

    public final Object H(s sVar) {
        return sVar == p.e() ? j$.time.chrono.t.d : sVar == p.j() ? ChronoUnit.YEARS : p.c(this, sVar);
    }

    /* renamed from: R */
    public final t d(long j, TemporalUnit temporalUnit) {
        if (!(temporalUnit instanceof ChronoUnit)) {
            return (t) temporalUnit.k(this, j);
        }
        int i = s.b[((ChronoUnit) temporalUnit).ordinal()];
        if (i == 1) {
            return S(j);
        }
        if (i == 2) {
            return S(j$.lang.a.m(j, (long) 10));
        }
        if (i == 3) {
            return S(j$.lang.a.m(j, (long) 100));
        }
        if (i == 4) {
            return S(j$.lang.a.m(j, (long) 1000));
        }
        if (i == 5) {
            a aVar = a.ERA;
            return c(j$.lang.a.l(E(aVar), j), aVar);
        }
        throw new j$.time.temporal.t("Unsupported unit: " + temporalUnit);
    }

    public final t S(long j) {
        return j == 0 ? this : Q(a.YEAR.Q(((long) this.a) + j));
    }

    /* renamed from: T */
    public final t c(long j, q qVar) {
        if (!(qVar instanceof a)) {
            return (t) qVar.H(this, j);
        }
        a aVar = (a) qVar;
        aVar.R(j);
        int i = s.a[aVar.ordinal()];
        int i2 = this.a;
        if (i == 1) {
            if (i2 < 1) {
                j = 1 - j;
            }
            return Q((int) j);
        } else if (i == 2) {
            return Q((int) j);
        } else {
            if (i == 3) {
                return E(a.ERA) == j ? this : Q(1 - i2);
            }
            throw new j$.time.temporal.t(c.a("Unsupported field: ", qVar));
        }
    }

    /* access modifiers changed from: package-private */
    public final void U(DataOutput dataOutput) {
        dataOutput.writeInt(this.a);
    }

    public final int compareTo(Object obj) {
        return this.a - ((t) obj).a;
    }

    public final boolean e(q qVar) {
        return qVar instanceof a ? qVar == a.YEAR || qVar == a.YEAR_OF_ERA || qVar == a.ERA : qVar != null && qVar.k(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        r4 = ((j$.time.t) r4).a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r4) {
        /*
            r3 = this;
            r0 = 1
            if (r3 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r4 instanceof j$.time.t
            r2 = 0
            if (r1 == 0) goto L_0x0014
            j$.time.t r4 = (j$.time.t) r4
            int r4 = r4.a
            int r1 = r3.a
            if (r1 != r4) goto L_0x0012
            goto L_0x0013
        L_0x0012:
            r0 = 0
        L_0x0013:
            return r0
        L_0x0014:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.t.equals(java.lang.Object):boolean");
    }

    public final l g(long j, ChronoUnit chronoUnit) {
        return j == Long.MIN_VALUE ? d(Long.MAX_VALUE, chronoUnit).d(1, chronoUnit) : d(-j, chronoUnit);
    }

    public final int hashCode() {
        return this.a;
    }

    public final int k(q qVar) {
        return m(qVar).a(E(qVar), qVar);
    }

    public final l l(LocalDate localDate) {
        return (t) C0038b.a(localDate, this);
    }

    public final u m(q qVar) {
        if (qVar != a.YEAR_OF_ERA) {
            return p.d(this, qVar);
        }
        return u.j(1, this.a <= 0 ? 1000000000 : 999999999);
    }

    public final l p(l lVar) {
        if (((C0037a) C0038b.s(lVar)).equals(j$.time.chrono.t.d)) {
            return lVar.c((long) this.a, a.YEAR);
        }
        throw new DateTimeException("Adjustment only supported on ISO date-time");
    }

    public final String toString() {
        return Integer.toString(this.a);
    }
}
