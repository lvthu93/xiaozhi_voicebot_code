package j$.time.zone;

import j$.time.ZoneOffset;
import j$.time.d;
import j$.time.j;
import j$.time.l;
import j$.util.Objects;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public final class e implements Serializable {
    private static final long serialVersionUID = 6889046316657758795L;
    private final l a;
    private final byte b;
    private final d c;
    private final j d;
    private final boolean e;
    private final d f;
    private final ZoneOffset g;
    private final ZoneOffset h;
    private final ZoneOffset i;

    e(l lVar, int i2, d dVar, j jVar, boolean z, d dVar2, ZoneOffset zoneOffset, ZoneOffset zoneOffset2, ZoneOffset zoneOffset3) {
        this.a = lVar;
        this.b = (byte) i2;
        this.c = dVar;
        this.d = jVar;
        this.e = z;
        this.f = dVar2;
        this.g = zoneOffset;
        this.h = zoneOffset2;
        this.i = zoneOffset3;
    }

    static e b(DataInput dataInput) {
        int readInt = dataInput.readInt();
        l T = l.T(readInt >>> 28);
        int i2 = ((264241152 & readInt) >>> 22) - 32;
        int i3 = (3670016 & readInt) >>> 19;
        d Q = i3 == 0 ? null : d.Q(i3);
        int i4 = (507904 & readInt) >>> 14;
        d dVar = d.values()[(readInt & 12288) >>> 12];
        int i5 = (readInt & 4080) >>> 4;
        int i6 = (readInt & 12) >>> 2;
        int i7 = readInt & 3;
        j a0 = i4 == 31 ? j.a0((long) dataInput.readInt()) : j.X(i4 % 24);
        ZoneOffset d0 = ZoneOffset.d0(i5 == 255 ? dataInput.readInt() : (i5 - 128) * 900);
        ZoneOffset d02 = i6 == 3 ? ZoneOffset.d0(dataInput.readInt()) : ZoneOffset.d0((i6 * 1800) + d0.a0());
        ZoneOffset d03 = i7 == 3 ? ZoneOffset.d0(dataInput.readInt()) : ZoneOffset.d0((i7 * 1800) + d0.a0());
        boolean z = i4 == 24;
        Objects.requireNonNull(T, "month");
        Objects.requireNonNull(a0, "time");
        Objects.requireNonNull(dVar, "timeDefnition");
        Objects.requireNonNull(d0, "standardOffset");
        Objects.requireNonNull(d02, "offsetBefore");
        Objects.requireNonNull(d03, "offsetAfter");
        if (i2 < -28 || i2 > 31 || i2 == 0) {
            throw new IllegalArgumentException("Day of month indicator must be between -28 and 31 inclusive excluding zero");
        } else if (z && !a0.equals(j.g)) {
            throw new IllegalArgumentException("Time must be midnight when end of day flag is true");
        } else if (a0.V() == 0) {
            return new e(T, i2, Q, a0, z, dVar, d0, d02, d03);
        } else {
            throw new IllegalArgumentException("Time's nano-of-second must be zero");
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new a((byte) 3, this);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0066  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final j$.time.zone.b a(int r8) {
        /*
            r7 = this;
            j$.time.d r0 = r7.c
            j$.time.l r1 = r7.a
            r2 = 1
            byte r3 = r7.b
            if (r3 >= 0) goto L_0x0026
            j$.time.chrono.t r4 = j$.time.chrono.t.d
            long r5 = (long) r8
            boolean r4 = r4.O(r5)
            int r4 = r1.R(r4)
            int r4 = r4 + r2
            int r4 = r4 + r3
            j$.time.LocalDate r8 = j$.time.LocalDate.c0(r8, r1, r4)
            if (r0 == 0) goto L_0x003a
            int r0 = r0.getValue()
            j$.time.temporal.n r1 = new j$.time.temporal.n
            r1.<init>(r0, r2)
            goto L_0x0036
        L_0x0026:
            j$.time.LocalDate r8 = j$.time.LocalDate.c0(r8, r1, r3)
            if (r0 == 0) goto L_0x003a
            int r0 = r0.getValue()
            j$.time.temporal.n r1 = new j$.time.temporal.n
            r3 = 0
            r1.<init>(r0, r3)
        L_0x0036:
            j$.time.LocalDate r8 = r8.z(r1)
        L_0x003a:
            boolean r0 = r7.e
            if (r0 == 0) goto L_0x0044
            r0 = 1
            j$.time.LocalDate r8 = r8.g0(r0)
        L_0x0044:
            j$.time.j r0 = r7.d
            j$.time.LocalDateTime r8 = j$.time.LocalDateTime.Z(r8, r0)
            j$.time.zone.d r0 = r7.f
            r0.getClass()
            int[] r1 = j$.time.zone.c.a
            int r0 = r0.ordinal()
            r0 = r1[r0]
            j$.time.ZoneOffset r1 = r7.h
            if (r0 == r2) goto L_0x0066
            r2 = 2
            if (r0 == r2) goto L_0x005f
            goto L_0x0076
        L_0x005f:
            int r0 = r1.a0()
            j$.time.ZoneOffset r2 = r7.g
            goto L_0x006c
        L_0x0066:
            int r0 = r1.a0()
            j$.time.ZoneOffset r2 = j$.time.ZoneOffset.UTC
        L_0x006c:
            int r2 = r2.a0()
            int r0 = r0 - r2
            long r2 = (long) r0
            j$.time.LocalDateTime r8 = r8.d0(r2)
        L_0x0076:
            j$.time.zone.b r0 = new j$.time.zone.b
            j$.time.ZoneOffset r2 = r7.i
            r0.<init>((j$.time.LocalDateTime) r8, (j$.time.ZoneOffset) r1, (j$.time.ZoneOffset) r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.zone.e.a(int):j$.time.zone.b");
    }

    /* access modifiers changed from: package-private */
    public final void c(DataOutput dataOutput) {
        j jVar = this.d;
        boolean z = this.e;
        int i0 = z ? 86400 : jVar.i0();
        int a0 = this.g.a0();
        ZoneOffset zoneOffset = this.h;
        int a02 = zoneOffset.a0() - a0;
        ZoneOffset zoneOffset2 = this.i;
        int a03 = zoneOffset2.a0() - a0;
        int U = i0 % 3600 == 0 ? z ? 24 : jVar.U() : 31;
        int i2 = a0 % 900 == 0 ? (a0 / 900) + 128 : 255;
        int i3 = (a02 == 0 || a02 == 1800 || a02 == 3600) ? a02 / 1800 : 3;
        int i4 = (a03 == 0 || a03 == 1800 || a03 == 3600) ? a03 / 1800 : 3;
        d dVar = this.c;
        dataOutput.writeInt((this.a.getValue() << 28) + ((this.b + 32) << 22) + ((dVar == null ? 0 : dVar.getValue()) << 19) + (U << 14) + (this.f.ordinal() << 12) + (i2 << 4) + (i3 << 2) + i4);
        if (U == 31) {
            dataOutput.writeInt(i0);
        }
        if (i2 == 255) {
            dataOutput.writeInt(a0);
        }
        if (i3 == 3) {
            dataOutput.writeInt(zoneOffset.a0());
        }
        if (i4 == 3) {
            dataOutput.writeInt(zoneOffset2.a0());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        r5 = (j$.time.zone.e) r5;
        r1 = r5.a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof j$.time.zone.e
            r2 = 0
            if (r1 == 0) goto L_0x0054
            j$.time.zone.e r5 = (j$.time.zone.e) r5
            j$.time.l r1 = r5.a
            j$.time.l r3 = r4.a
            if (r3 != r1) goto L_0x0052
            byte r1 = r4.b
            byte r3 = r5.b
            if (r1 != r3) goto L_0x0052
            j$.time.d r1 = r4.c
            j$.time.d r3 = r5.c
            if (r1 != r3) goto L_0x0052
            j$.time.zone.d r1 = r4.f
            j$.time.zone.d r3 = r5.f
            if (r1 != r3) goto L_0x0052
            j$.time.j r1 = r4.d
            j$.time.j r3 = r5.d
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0052
            boolean r1 = r4.e
            boolean r3 = r5.e
            if (r1 != r3) goto L_0x0052
            j$.time.ZoneOffset r1 = r4.g
            j$.time.ZoneOffset r3 = r5.g
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0052
            j$.time.ZoneOffset r1 = r4.h
            j$.time.ZoneOffset r3 = r5.h
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0052
            j$.time.ZoneOffset r1 = r4.i
            j$.time.ZoneOffset r5 = r5.i
            boolean r5 = r1.equals(r5)
            if (r5 == 0) goto L_0x0052
            goto L_0x0053
        L_0x0052:
            r0 = 0
        L_0x0053:
            return r0
        L_0x0054:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.zone.e.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        int i0 = ((this.d.i0() + (this.e ? 1 : 0)) << 15) + (this.a.ordinal() << 11) + ((this.b + 32) << 5);
        d dVar = this.c;
        return ((this.g.hashCode() ^ (this.f.ordinal() + (i0 + ((dVar == null ? 7 : dVar.ordinal()) << 2)))) ^ this.h.hashCode()) ^ this.i.hashCode();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TransitionRule[");
        ZoneOffset zoneOffset = this.h;
        ZoneOffset zoneOffset2 = this.i;
        sb.append(zoneOffset.Y(zoneOffset2) > 0 ? "Gap " : "Overlap ");
        sb.append(zoneOffset);
        sb.append(" to ");
        sb.append(zoneOffset2);
        sb.append(", ");
        byte b2 = this.b;
        l lVar = this.a;
        d dVar = this.c;
        if (dVar == null) {
            sb.append(lVar.name());
            sb.append(' ');
            sb.append(b2);
        } else if (b2 == -1) {
            sb.append(dVar.name());
            sb.append(" on or before last day of ");
            sb.append(lVar.name());
        } else if (b2 < 0) {
            sb.append(dVar.name());
            sb.append(" on or before last day minus ");
            sb.append((-b2) - 1);
            sb.append(" of ");
            sb.append(lVar.name());
        } else {
            sb.append(dVar.name());
            sb.append(" on or after ");
            sb.append(lVar.name());
            sb.append(' ');
            sb.append(b2);
        }
        sb.append(" at ");
        sb.append(this.e ? "24:00" : this.d.toString());
        sb.append(" ");
        sb.append(this.f);
        sb.append(", standard offset ");
        sb.append(this.g);
        sb.append(']');
        return sb.toString();
    }
}
