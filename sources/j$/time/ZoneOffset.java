package j$.time;

import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import j$.time.temporal.l;
import j$.time.temporal.m;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.time.zone.f;
import j$.util.Objects;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.mozilla.javascript.Token;

public final class ZoneOffset extends ZoneId implements TemporalAccessor, m, Comparable<ZoneOffset> {
    public static final ZoneOffset UTC = d0(0);
    private static final ConcurrentHashMap d = new ConcurrentHashMap(16, 0.75f, 4);
    private static final ConcurrentHashMap e = new ConcurrentHashMap(16, 0.75f, 4);
    public static final ZoneOffset f = d0(-64800);
    public static final ZoneOffset g = d0(64800);
    private static final long serialVersionUID = 2357656521762053153L;
    private final int b;
    private final transient String c;

    private ZoneOffset(int i) {
        String str;
        this.b = i;
        if (i == 0) {
            str = "Z";
        } else {
            int abs = Math.abs(i);
            StringBuilder sb = new StringBuilder();
            int i2 = abs / 3600;
            int i3 = (abs / 60) % 60;
            sb.append(i < 0 ? "-" : MqttTopic.SINGLE_LEVEL_WILDCARD);
            sb.append(i2 < 10 ? "0" : "");
            sb.append(i2);
            String str2 = ":0";
            sb.append(i3 < 10 ? str2 : ":");
            sb.append(i3);
            int i4 = abs % 60;
            if (i4 != 0) {
                sb.append(i4 >= 10 ? ":" : str2);
                sb.append(i4);
            }
            str = sb.toString();
        }
        this.c = str;
    }

    public static ZoneOffset Z(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        ZoneOffset zoneOffset = (ZoneOffset) temporalAccessor.H(p.i());
        if (zoneOffset != null) {
            return zoneOffset;
        }
        String name = temporalAccessor.getClass().getName();
        throw new DateTimeException("Unable to obtain ZoneOffset from TemporalAccessor: " + temporalAccessor + " of type " + name);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x008e A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static j$.time.ZoneOffset b0(java.lang.String r7) {
        /*
            java.lang.String r0 = "offsetId"
            j$.util.Objects.requireNonNull(r7, r0)
            j$.util.concurrent.ConcurrentHashMap r0 = e
            java.lang.Object r0 = r0.get(r7)
            j$.time.ZoneOffset r0 = (j$.time.ZoneOffset) r0
            if (r0 == 0) goto L_0x0010
            return r0
        L_0x0010:
            int r0 = r7.length()
            r1 = 0
            r2 = 2
            r3 = 1
            if (r0 == r2) goto L_0x0062
            r2 = 3
            if (r0 == r2) goto L_0x007e
            r4 = 5
            if (r0 == r4) goto L_0x0059
            r5 = 6
            r6 = 4
            if (r0 == r5) goto L_0x0050
            r5 = 7
            if (r0 == r5) goto L_0x0043
            r2 = 9
            if (r0 != r2) goto L_0x0037
            int r0 = e0(r7, r3, r1)
            int r2 = e0(r7, r6, r3)
            int r3 = e0(r7, r5, r3)
            goto L_0x0084
        L_0x0037:
            j$.time.DateTimeException r0 = new j$.time.DateTimeException
            java.lang.String r1 = "Invalid ID for ZoneOffset, invalid format: "
            java.lang.String r7 = r1.concat(r7)
            r0.<init>(r7)
            throw r0
        L_0x0043:
            int r0 = e0(r7, r3, r1)
            int r2 = e0(r7, r2, r1)
            int r3 = e0(r7, r4, r1)
            goto L_0x0084
        L_0x0050:
            int r0 = e0(r7, r3, r1)
            int r2 = e0(r7, r6, r3)
            goto L_0x0083
        L_0x0059:
            int r0 = e0(r7, r3, r1)
            int r2 = e0(r7, r2, r1)
            goto L_0x0083
        L_0x0062:
            char r0 = r7.charAt(r1)
            char r7 = r7.charAt(r3)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            java.lang.String r0 = "0"
            r2.append(r0)
            r2.append(r7)
            java.lang.String r7 = r2.toString()
        L_0x007e:
            int r0 = e0(r7, r3, r1)
            r2 = 0
        L_0x0083:
            r3 = 0
        L_0x0084:
            char r1 = r7.charAt(r1)
            r4 = 43
            r5 = 45
            if (r1 == r4) goto L_0x009d
            if (r1 != r5) goto L_0x0091
            goto L_0x009d
        L_0x0091:
            j$.time.DateTimeException r0 = new j$.time.DateTimeException
            java.lang.String r1 = "Invalid ID for ZoneOffset, plus/minus not found when expected: "
            java.lang.String r7 = r1.concat(r7)
            r0.<init>(r7)
            throw r0
        L_0x009d:
            if (r1 != r5) goto L_0x00a7
            int r7 = -r0
            int r0 = -r2
            int r1 = -r3
            j$.time.ZoneOffset r7 = c0(r7, r0, r1)
            return r7
        L_0x00a7:
            j$.time.ZoneOffset r7 = c0(r0, r2, r3)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.ZoneOffset.b0(java.lang.String):j$.time.ZoneOffset");
    }

    public static ZoneOffset c0(int i, int i2, int i3) {
        if (i < -18 || i > 18) {
            throw new DateTimeException("Zone offset hours not in valid range: value " + i + " is not in the range -18 to 18");
        }
        if (i > 0) {
            if (i2 < 0 || i3 < 0) {
                throw new DateTimeException("Zone offset minutes and seconds must be positive because hours is positive");
            }
        } else if (i < 0) {
            if (i2 > 0 || i3 > 0) {
                throw new DateTimeException("Zone offset minutes and seconds must be negative because hours is negative");
            }
        } else if ((i2 > 0 && i3 < 0) || (i2 < 0 && i3 > 0)) {
            throw new DateTimeException("Zone offset minutes and seconds must have the same sign");
        }
        if (i2 < -59 || i2 > 59) {
            throw new DateTimeException("Zone offset minutes not in valid range: value " + i2 + " is not in the range -59 to 59");
        } else if (i3 < -59 || i3 > 59) {
            throw new DateTimeException("Zone offset seconds not in valid range: value " + i3 + " is not in the range -59 to 59");
        } else if (Math.abs(i) != 18 || (i2 | i3) == 0) {
            return d0((i2 * 60) + (i * 3600) + i3);
        } else {
            throw new DateTimeException("Zone offset not in valid range: -18:00 to +18:00");
        }
    }

    public static ZoneOffset d0(int i) {
        if (i < -64800 || i > 64800) {
            throw new DateTimeException("Zone offset not in valid range: -18:00 to +18:00");
        } else if (i % 900 != 0) {
            return new ZoneOffset(i);
        } else {
            Integer valueOf = Integer.valueOf(i);
            ConcurrentHashMap concurrentHashMap = d;
            ZoneOffset zoneOffset = (ZoneOffset) concurrentHashMap.get(valueOf);
            if (zoneOffset != null) {
                return zoneOffset;
            }
            concurrentHashMap.putIfAbsent(valueOf, new ZoneOffset(i));
            ZoneOffset zoneOffset2 = (ZoneOffset) concurrentHashMap.get(valueOf);
            e.putIfAbsent(zoneOffset2.c, zoneOffset2);
            return zoneOffset2;
        }
    }

    private static int e0(CharSequence charSequence, int i, boolean z) {
        if (!z || charSequence.charAt(i - 1) == ':') {
            char charAt = charSequence.charAt(i);
            char charAt2 = charSequence.charAt(i + 1);
            if (charAt < '0' || charAt > '9' || charAt2 < '0' || charAt2 > '9') {
                throw new DateTimeException("Invalid ID for ZoneOffset, non numeric characters found: " + charSequence);
            }
            return (charAt2 - '0') + ((charAt - '0') * 10);
        }
        throw new DateTimeException("Invalid ID for ZoneOffset, colon not found when expected: " + charSequence);
    }

    static ZoneOffset f0(DataInput dataInput) {
        byte readByte = dataInput.readByte();
        return readByte == Byte.MAX_VALUE ? d0(dataInput.readInt()) : d0(readByte * 900);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r((byte) 8, this);
    }

    public final long E(q qVar) {
        if (qVar == a.OFFSET_SECONDS) {
            return (long) this.b;
        }
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        throw new t(c.a("Unsupported field: ", qVar));
    }

    public final Object H(s sVar) {
        return (sVar == p.i() || sVar == p.k()) ? this : p.c(this, sVar);
    }

    public final f R() {
        return f.j(this);
    }

    /* access modifiers changed from: package-private */
    public final void X(DataOutput dataOutput) {
        dataOutput.writeByte(8);
        g0(dataOutput);
    }

    public final int Y(ZoneOffset zoneOffset) {
        return zoneOffset.b - this.b;
    }

    public final int a0() {
        return this.b;
    }

    public final int compareTo(Object obj) {
        return ((ZoneOffset) obj).b - this.b;
    }

    public final boolean e(q qVar) {
        return qVar instanceof a ? qVar == a.OFFSET_SECONDS : qVar != null && qVar.k(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        r4 = ((j$.time.ZoneOffset) r4).b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r4) {
        /*
            r3 = this;
            r0 = 1
            if (r3 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r4 instanceof j$.time.ZoneOffset
            r2 = 0
            if (r1 == 0) goto L_0x0014
            j$.time.ZoneOffset r4 = (j$.time.ZoneOffset) r4
            int r4 = r4.b
            int r1 = r3.b
            if (r1 != r4) goto L_0x0012
            goto L_0x0013
        L_0x0012:
            r0 = 0
        L_0x0013:
            return r0
        L_0x0014:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.ZoneOffset.equals(java.lang.Object):boolean");
    }

    /* access modifiers changed from: package-private */
    public final void g0(DataOutput dataOutput) {
        int i = this.b;
        int i2 = i % 900 == 0 ? i / 900 : Token.VOID;
        dataOutput.writeByte(i2);
        if (i2 == 127) {
            dataOutput.writeInt(i);
        }
    }

    public final int hashCode() {
        return this.b;
    }

    public final String j() {
        return this.c;
    }

    public final int k(q qVar) {
        if (qVar == a.OFFSET_SECONDS) {
            return this.b;
        }
        if (!(qVar instanceof a)) {
            return p.d(this, qVar).a(E(qVar), qVar);
        }
        throw new t(c.a("Unsupported field: ", qVar));
    }

    public final u m(q qVar) {
        return p.d(this, qVar);
    }

    public final l p(l lVar) {
        return lVar.c((long) this.b, a.OFFSET_SECONDS);
    }

    public final String toString() {
        return this.c;
    }
}
