package j$.time.temporal;

import j$.time.d;
import j$.util.Objects;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public final class w implements Serializable {
    private static final ConcurrentHashMap g = new ConcurrentHashMap(4, 0.75f, 2);
    public static final TemporalUnit h = i.d;
    private static final long serialVersionUID = -1177360819670808121L;
    private final d a;
    private final int b;
    /* access modifiers changed from: private */
    public final transient q c = v.g(this);
    private final transient q d = v.j(this);
    /* access modifiers changed from: private */
    public final transient q e;
    /* access modifiers changed from: private */
    public final transient q f;

    static {
        new w(d.MONDAY, 4);
        g(d.SUNDAY, 1);
    }

    private w(d dVar, int i) {
        ChronoUnit chronoUnit = ChronoUnit.NANOS;
        this.e = v.n(this);
        this.f = v.i(this);
        Objects.requireNonNull(dVar, "firstDayOfWeek");
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("Minimal number of days is invalid");
        }
        this.a = dVar;
        this.b = i;
    }

    public static w g(d dVar, int i) {
        String str = dVar.toString() + i;
        ConcurrentHashMap concurrentHashMap = g;
        w wVar = (w) concurrentHashMap.get(str);
        if (wVar != null) {
            return wVar;
        }
        concurrentHashMap.putIfAbsent(str, new w(dVar, i));
        return (w) concurrentHashMap.get(str);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        if (this.a != null) {
            int i = this.b;
            if (i < 1 || i > 7) {
                throw new InvalidObjectException("Minimal number of days is invalid");
            }
            return;
        }
        throw new InvalidObjectException("firstDayOfWeek is null");
    }

    private Object readResolve() {
        try {
            return g(this.a, this.b);
        } catch (IllegalArgumentException e2) {
            String message = e2.getMessage();
            throw new InvalidObjectException("Invalid serialized WeekFields: " + message);
        }
    }

    public final q d() {
        return this.c;
    }

    public final d e() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof w) && hashCode() == obj.hashCode();
    }

    public final int f() {
        return this.b;
    }

    public final q h() {
        return this.f;
    }

    public final int hashCode() {
        return (this.a.ordinal() * 7) + this.b;
    }

    public final q i() {
        return this.d;
    }

    public final q j() {
        return this.e;
    }

    public final String toString() {
        return "WeekFields[" + this.a + "," + this.b + "]";
    }
}
