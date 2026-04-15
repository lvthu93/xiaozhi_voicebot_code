package j$.time.chrono;

import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.u;

public enum K implements n {
    BEFORE_BE,
    BE;

    public final int getValue() {
        return ordinal();
    }

    public final u m(q qVar) {
        return p.d(this, qVar);
    }
}
