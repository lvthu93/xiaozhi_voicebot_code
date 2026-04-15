package j$.time;

import j$.time.chrono.C0038b;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalUnit;
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
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

public final class j implements l, m, Comparable, Serializable {
    public static final j e;
    public static final j f = new j(23, 59, 59, 999999999);
    public static final j g;
    private static final j[] h = new j[24];
    private static final long serialVersionUID = 6414437269572265201L;
    private final byte a;
    private final byte b;
    private final byte c;
    private final int d;

    static {
        int i = 0;
        while (true) {
            j[] jVarArr = h;
            if (i < jVarArr.length) {
                jVarArr[i] = new j(i, 0, 0, 0);
                i++;
            } else {
                j jVar = jVarArr[0];
                g = jVar;
                j jVar2 = jVarArr[12];
                e = jVar;
                return;
            }
        }
    }

    private j(int i, int i2, int i3, int i4) {
        this.a = (byte) i;
        this.b = (byte) i2;
        this.c = (byte) i3;
        this.d = i4;
    }

    private static j R(int i, int i2, int i3, int i4) {
        return ((i2 | i3) | i4) == 0 ? h[i] : new j(i, i2, i3, i4);
    }

    public static j S(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        j jVar = (j) temporalAccessor.H(p.g());
        if (jVar != null) {
            return jVar;
        }
        String name = temporalAccessor.getClass().getName();
        throw new DateTimeException("Unable to obtain LocalTime from TemporalAccessor: " + temporalAccessor + " of type " + name);
    }

    private int T(q qVar) {
        int i = i.a[((a) qVar).ordinal()];
        byte b2 = this.b;
        int i2 = this.d;
        byte b3 = this.a;
        switch (i) {
            case 1:
                return i2;
            case 2:
                throw new t("Invalid field 'NanoOfDay' for get() method, use getLong() instead");
            case 3:
                return i2 / 1000;
            case 4:
                throw new t("Invalid field 'MicroOfDay' for get() method, use getLong() instead");
            case 5:
                return i2 / 1000000;
            case 6:
                return (int) (h0() / 1000000);
            case 7:
                return this.c;
            case 8:
                return i0();
            case 9:
                return b2;
            case 10:
                return (b3 * 60) + b2;
            case 11:
                return b3 % MqttWireMessage.MESSAGE_TYPE_PINGREQ;
            case 12:
                int i3 = b3 % MqttWireMessage.MESSAGE_TYPE_PINGREQ;
                if (i3 % 12 == 0) {
                    return 12;
                }
                return i3;
            case 13:
                return b3;
            case 14:
                if (b3 == 0) {
                    return 24;
                }
                return b3;
            case 15:
                return b3 / MqttWireMessage.MESSAGE_TYPE_PINGREQ;
            default:
                throw new t(c.a("Unsupported field: ", qVar));
        }
    }

    public static j X(int i) {
        a.HOUR_OF_DAY.R((long) i);
        return h[i];
    }

    public static j Y(int i, int i2, int i3, int i4) {
        a.HOUR_OF_DAY.R((long) i);
        a.MINUTE_OF_HOUR.R((long) i2);
        a.SECOND_OF_MINUTE.R((long) i3);
        a.NANO_OF_SECOND.R((long) i4);
        return R(i, i2, i3, i4);
    }

    public static j Z(long j) {
        a.NANO_OF_DAY.R(j);
        int i = (int) (j / 3600000000000L);
        long j2 = j - (((long) i) * 3600000000000L);
        int i2 = (int) (j2 / 60000000000L);
        long j3 = j2 - (((long) i2) * 60000000000L);
        int i3 = (int) (j3 / 1000000000);
        return R(i, i2, i3, (int) (j3 - (((long) i3) * 1000000000)));
    }

    public static j a0(long j) {
        a.SECOND_OF_DAY.R(j);
        int i = (int) (j / 3600);
        long j2 = j - ((long) (i * 3600));
        int i2 = (int) (j2 / 60);
        return R(i, i2, (int) (j2 - ((long) (i2 * 60))), 0);
    }

    static j g0(DataInput dataInput) {
        byte b2;
        int i;
        int readByte = dataInput.readByte();
        byte b3 = 0;
        if (readByte < 0) {
            readByte = ~readByte;
            b2 = 0;
        } else {
            byte readByte2 = dataInput.readByte();
            if (readByte2 < 0) {
                int i2 = ~readByte2;
                i = 0;
                b3 = i2;
                b2 = 0;
            } else {
                byte readByte3 = dataInput.readByte();
                if (readByte3 < 0) {
                    b2 = ~readByte3;
                    b3 = readByte2;
                } else {
                    int readInt = dataInput.readInt();
                    b2 = readByte3;
                    byte b4 = readByte2;
                    i = readInt;
                    b3 = b4;
                }
            }
            return Y(readByte, b3, b2, i);
        }
        i = 0;
        return Y(readByte, b3, b2, i);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r((byte) 4, this);
    }

    public final long E(q qVar) {
        return qVar instanceof a ? qVar == a.NANO_OF_DAY ? h0() : qVar == a.MICRO_OF_DAY ? h0() / 1000 : (long) T(qVar) : qVar.E(this);
    }

    public final Object H(s sVar) {
        if (sVar == p.e() || sVar == p.l() || sVar == p.k() || sVar == p.i()) {
            return null;
        }
        if (sVar == p.g()) {
            return this;
        }
        if (sVar == p.f()) {
            return null;
        }
        return sVar == p.j() ? ChronoUnit.NANOS : sVar.a(this);
    }

    /* renamed from: Q */
    public final int compareTo(j jVar) {
        int compare = Integer.compare(this.a, jVar.a);
        if (compare != 0) {
            return compare;
        }
        int compare2 = Integer.compare(this.b, jVar.b);
        if (compare2 != 0) {
            return compare2;
        }
        int compare3 = Integer.compare(this.c, jVar.c);
        return compare3 == 0 ? Integer.compare(this.d, jVar.d) : compare3;
    }

    public final int U() {
        return this.a;
    }

    public final int V() {
        return this.d;
    }

    public final int W() {
        return this.c;
    }

    /* renamed from: b0 */
    public final j d(long j, TemporalUnit temporalUnit) {
        if (!(temporalUnit instanceof ChronoUnit)) {
            return (j) temporalUnit.k(this, j);
        }
        switch (i.b[((ChronoUnit) temporalUnit).ordinal()]) {
            case 1:
                return e0(j);
            case 2:
                return e0((j % 86400000000L) * 1000);
            case 3:
                return e0((j % 86400000) * 1000000);
            case 4:
                return f0(j);
            case 5:
                return d0(j);
            case 6:
                return c0(j);
            case 7:
                return c0((j % 2) * 12);
            default:
                throw new t("Unsupported unit: " + temporalUnit);
        }
    }

    public final j c0(long j) {
        if (j == 0) {
            return this;
        }
        return R(((((int) (j % 24)) + this.a) + 24) % 24, this.b, this.c, this.d);
    }

    public final j d0(long j) {
        if (j == 0) {
            return this;
        }
        int i = (this.a * 60) + this.b;
        int i2 = ((((int) (j % 1440)) + i) + 1440) % 1440;
        return i == i2 ? this : R(i2 / 60, i2 % 60, this.c, this.d);
    }

    public final boolean e(q qVar) {
        return qVar instanceof a ? qVar.isTimeBased() : qVar != null && qVar.k(this);
    }

    public final j e0(long j) {
        if (j == 0) {
            return this;
        }
        long h0 = h0();
        long j2 = (((j % 86400000000000L) + h0) + 86400000000000L) % 86400000000000L;
        return h0 == j2 ? this : R((int) (j2 / 3600000000000L), (int) ((j2 / 60000000000L) % 60), (int) ((j2 / 1000000000) % 60), (int) (j2 % 1000000000));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        r5 = (j$.time.j) r5;
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
            boolean r1 = r5 instanceof j$.time.j
            r2 = 0
            if (r1 == 0) goto L_0x0026
            j$.time.j r5 = (j$.time.j) r5
            byte r1 = r5.a
            byte r3 = r4.a
            if (r3 != r1) goto L_0x0024
            byte r1 = r4.b
            byte r3 = r5.b
            if (r1 != r3) goto L_0x0024
            byte r1 = r4.c
            byte r3 = r5.c
            if (r1 != r3) goto L_0x0024
            int r1 = r4.d
            int r5 = r5.d
            if (r1 != r5) goto L_0x0024
            goto L_0x0025
        L_0x0024:
            r0 = 0
        L_0x0025:
            return r0
        L_0x0026:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.j.equals(java.lang.Object):boolean");
    }

    public final j f0(long j) {
        if (j == 0) {
            return this;
        }
        int i = (this.b * 60) + (this.a * 3600) + this.c;
        int i2 = ((((int) (j % 86400)) + i) + 86400) % 86400;
        return i == i2 ? this : R(i2 / 3600, (i2 / 60) % 60, i2 % 60, this.d);
    }

    public final l g(long j, ChronoUnit chronoUnit) {
        return j == Long.MIN_VALUE ? d(Long.MAX_VALUE, chronoUnit).d(1, chronoUnit) : d(-j, chronoUnit);
    }

    public final long h0() {
        return (((long) this.c) * 1000000000) + (((long) this.b) * 60000000000L) + (((long) this.a) * 3600000000000L) + ((long) this.d);
    }

    public final int hashCode() {
        long h0 = h0();
        return (int) (h0 ^ (h0 >>> 32));
    }

    public final int i0() {
        return (this.b * 60) + (this.a * 3600) + this.c;
    }

    /* renamed from: j0 */
    public final j c(long j, q qVar) {
        if (!(qVar instanceof a)) {
            return (j) qVar.H(this, j);
        }
        a aVar = (a) qVar;
        aVar.R(j);
        int i = i.a[aVar.ordinal()];
        byte b2 = this.b;
        byte b3 = this.c;
        int i2 = this.d;
        byte b4 = this.a;
        switch (i) {
            case 1:
                return k0((int) j);
            case 2:
                return Z(j);
            case 3:
                return k0(((int) j) * 1000);
            case 4:
                return Z(j * 1000);
            case 5:
                return k0(((int) j) * 1000000);
            case 6:
                return Z(j * 1000000);
            case 7:
                int i3 = (int) j;
                if (b3 == i3) {
                    return this;
                }
                a.SECOND_OF_MINUTE.R((long) i3);
                return R(b4, b2, i3, i2);
            case 8:
                return f0(j - ((long) i0()));
            case 9:
                int i4 = (int) j;
                if (b2 == i4) {
                    return this;
                }
                a.MINUTE_OF_HOUR.R((long) i4);
                return R(b4, i4, b3, i2);
            case 10:
                return d0(j - ((long) ((b4 * 60) + b2)));
            case 11:
                return c0(j - ((long) (b4 % MqttWireMessage.MESSAGE_TYPE_PINGREQ)));
            case 12:
                if (j == 12) {
                    j = 0;
                }
                return c0(j - ((long) (b4 % MqttWireMessage.MESSAGE_TYPE_PINGREQ)));
            case 13:
                int i5 = (int) j;
                if (b4 == i5) {
                    return this;
                }
                a.HOUR_OF_DAY.R((long) i5);
                return R(i5, b2, b3, i2);
            case 14:
                if (j == 24) {
                    j = 0;
                }
                int i6 = (int) j;
                if (b4 == i6) {
                    return this;
                }
                a.HOUR_OF_DAY.R((long) i6);
                return R(i6, b2, b3, i2);
            case 15:
                return c0((j - ((long) (b4 / MqttWireMessage.MESSAGE_TYPE_PINGREQ))) * 12);
            default:
                throw new t(c.a("Unsupported field: ", qVar));
        }
    }

    public final int k(q qVar) {
        return qVar instanceof a ? T(qVar) : p.a(this, qVar);
    }

    public final j k0(int i) {
        if (this.d == i) {
            return this;
        }
        a.NANO_OF_SECOND.R((long) i);
        return R(this.a, this.b, this.c, i);
    }

    public final l l(LocalDate localDate) {
        boolean z = localDate instanceof j;
        TemporalAccessor temporalAccessor = localDate;
        if (!z) {
            temporalAccessor = C0038b.a(localDate, this);
        }
        return (j) temporalAccessor;
    }

    /* access modifiers changed from: package-private */
    public final void l0(DataOutput dataOutput) {
        int i;
        byte b2 = this.c;
        byte b3 = this.b;
        byte b4 = this.a;
        int i2 = this.d;
        if (i2 == 0) {
            if (b2 != 0) {
                dataOutput.writeByte(b4);
                dataOutput.writeByte(b3);
                i = ~b2;
            } else if (b3 == 0) {
                i = ~b4;
            } else {
                dataOutput.writeByte(b4);
                i = ~b3;
            }
            dataOutput.writeByte(i);
            return;
        }
        dataOutput.writeByte(b4);
        dataOutput.writeByte(b3);
        dataOutput.writeByte(b2);
        dataOutput.writeInt(i2);
    }

    public final u m(q qVar) {
        return p.d(this, qVar);
    }

    public final l p(l lVar) {
        return lVar.c(h0(), a.NANO_OF_DAY);
    }

    public final String toString() {
        int i;
        StringBuilder sb = new StringBuilder(18);
        byte b2 = this.a;
        sb.append(b2 < 10 ? "0" : "");
        sb.append(b2);
        String str = ":0";
        byte b3 = this.b;
        sb.append(b3 < 10 ? str : ":");
        sb.append(b3);
        byte b4 = this.c;
        int i2 = this.d;
        if (b4 > 0 || i2 > 0) {
            if (b4 >= 10) {
                str = ":";
            }
            sb.append(str);
            sb.append(b4);
            if (i2 > 0) {
                sb.append('.');
                int i3 = 1000000;
                if (i2 % 1000000 == 0) {
                    i = (i2 / 1000000) + 1000;
                } else {
                    if (i2 % 1000 == 0) {
                        i2 /= 1000;
                    } else {
                        i3 = 1000000000;
                    }
                    i = i2 + i3;
                }
                sb.append(Integer.toString(i).substring(1));
            }
        }
        return sb.toString();
    }
}
