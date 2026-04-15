package j$.time;

import j$.desugar.sun.nio.fs.c;
import j$.time.temporal.ChronoUnit;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.regex.Pattern;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

public final class q implements Serializable {
    public static final q d = new q(0, 0, 0);
    private static final long serialVersionUID = -3587258372562876L;
    private final int a;
    private final int b;
    private final int c;

    static {
        Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?", 2);
        c.a(new Object[]{ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS});
    }

    private q(int i, int i2, int i3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
    }

    public static q c(int i) {
        return (0 | i) == 0 ? d : new q(0, 0, i);
    }

    static q d(DataInput dataInput) {
        int readInt = dataInput.readInt();
        int readInt2 = dataInput.readInt();
        int readInt3 = dataInput.readInt();
        return ((readInt | readInt2) | readInt3) == 0 ? d : new q(readInt, readInt2, readInt3);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r(MqttWireMessage.MESSAGE_TYPE_DISCONNECT, this);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final j$.time.temporal.l a(j$.time.chrono.C0039c r6) {
        /*
            r5 = this;
            java.lang.String r0 = "temporal"
            j$.util.Objects.requireNonNull(r6, r0)
            j$.time.temporal.s r0 = j$.time.temporal.p.e()
            java.lang.Object r0 = r6.H(r0)
            j$.time.chrono.m r0 = (j$.time.chrono.m) r0
            if (r0 == 0) goto L_0x0032
            j$.time.chrono.t r1 = j$.time.chrono.t.d
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x001a
            goto L_0x0032
        L_0x001a:
            j$.time.DateTimeException r6 = new j$.time.DateTimeException
            java.lang.String r0 = r0.j()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Chronology mismatch, expected: ISO, actual: "
            r1.<init>(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r6.<init>(r0)
            throw r6
        L_0x0032:
            int r0 = r5.b
            if (r0 != 0) goto L_0x003e
            int r0 = r5.a
            if (r0 == 0) goto L_0x004e
            long r0 = (long) r0
            j$.time.temporal.ChronoUnit r2 = j$.time.temporal.ChronoUnit.YEARS
            goto L_0x004a
        L_0x003e:
            long r0 = r5.e()
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x004e
            j$.time.temporal.ChronoUnit r2 = j$.time.temporal.ChronoUnit.MONTHS
        L_0x004a:
            j$.time.temporal.l r6 = r6.d(r0, r2)
        L_0x004e:
            int r0 = r5.c
            if (r0 == 0) goto L_0x0059
            long r0 = (long) r0
            j$.time.temporal.ChronoUnit r2 = j$.time.temporal.ChronoUnit.DAYS
            j$.time.temporal.l r6 = r6.d(r0, r2)
        L_0x0059:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.q.a(j$.time.chrono.c):j$.time.temporal.l");
    }

    public final int b() {
        return this.c;
    }

    public final long e() {
        return (((long) this.a) * 12) + ((long) this.b);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        r5 = (j$.time.q) r5;
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
            boolean r1 = r5 instanceof j$.time.q
            r2 = 0
            if (r1 == 0) goto L_0x0020
            j$.time.q r5 = (j$.time.q) r5
            int r1 = r5.a
            int r3 = r4.a
            if (r3 != r1) goto L_0x001e
            int r1 = r4.b
            int r3 = r5.b
            if (r1 != r3) goto L_0x001e
            int r1 = r4.c
            int r5 = r5.c
            if (r1 != r5) goto L_0x001e
            goto L_0x001f
        L_0x001e:
            r0 = 0
        L_0x001f:
            return r0
        L_0x0020:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.q.equals(java.lang.Object):boolean");
    }

    /* access modifiers changed from: package-private */
    public final void f(DataOutput dataOutput) {
        dataOutput.writeInt(this.a);
        dataOutput.writeInt(this.b);
        dataOutput.writeInt(this.c);
    }

    public final int hashCode() {
        return Integer.rotateLeft(this.c, 16) + Integer.rotateLeft(this.b, 8) + this.a;
    }

    public final String toString() {
        if (this == d) {
            return "P0D";
        }
        StringBuilder sb = new StringBuilder("P");
        int i = this.a;
        if (i != 0) {
            sb.append(i);
            sb.append('Y');
        }
        int i2 = this.b;
        if (i2 != 0) {
            sb.append(i2);
            sb.append('M');
        }
        int i3 = this.c;
        if (i3 != 0) {
            sb.append(i3);
            sb.append('D');
        }
        return sb.toString();
    }
}
