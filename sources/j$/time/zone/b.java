package j$.time.zone;

import j$.desugar.sun.nio.fs.c;
import j$.time.Duration;
import j$.time.LocalDateTime;
import j$.time.ZoneOffset;
import j$.time.chrono.C0038b;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public final class b implements Comparable, Serializable {
    private static final long serialVersionUID = -6946044323557704546L;
    private final long a;
    private final LocalDateTime b;
    private final ZoneOffset c;
    private final ZoneOffset d;

    b(long j, ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
        this.a = j;
        this.b = LocalDateTime.a0(j, 0, zoneOffset);
        this.c = zoneOffset;
        this.d = zoneOffset2;
    }

    b(LocalDateTime localDateTime, ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
        localDateTime.getClass();
        this.a = C0038b.p(localDateTime, zoneOffset);
        this.b = localDateTime;
        this.c = zoneOffset;
        this.d = zoneOffset2;
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new a((byte) 2, this);
    }

    public final ZoneOffset E() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public final List H() {
        if (Q()) {
            return Collections.emptyList();
        }
        return c.a(new Object[]{this.c, this.d});
    }

    public final long P() {
        return this.a;
    }

    public final boolean Q() {
        return this.d.a0() > this.c.a0();
    }

    /* access modifiers changed from: package-private */
    public final void R(DataOutput dataOutput) {
        a.c(this.a, dataOutput);
        a.d(this.c, dataOutput);
        a.d(this.d, dataOutput);
    }

    public final int compareTo(Object obj) {
        return Long.compare(this.a, ((b) obj).a);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return this.a == bVar.a && this.c.equals(bVar.c) && this.d.equals(bVar.d);
    }

    public final int hashCode() {
        return (this.b.hashCode() ^ this.c.hashCode()) ^ Integer.rotateLeft(this.d.hashCode(), 16);
    }

    public final LocalDateTime k() {
        return this.b.d0((long) (this.d.a0() - this.c.a0()));
    }

    public final LocalDateTime l() {
        return this.b;
    }

    public final Duration m() {
        return Duration.p((long) (this.d.a0() - this.c.a0()));
    }

    public final ZoneOffset p() {
        return this.d;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Transition[");
        sb.append(Q() ? "Gap" : "Overlap");
        sb.append(" at ");
        sb.append(this.b);
        sb.append(this.c);
        sb.append(" to ");
        sb.append(this.d);
        sb.append(']');
        return sb.toString();
    }
}
