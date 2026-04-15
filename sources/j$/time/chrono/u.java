package j$.time.chrono;

import j$.time.temporal.p;
import j$.time.temporal.q;

public enum u implements n {
    BCE,
    CE;

    public final int getValue() {
        return ordinal();
    }

    public final j$.time.temporal.u m(q qVar) {
        return p.d(this, qVar);
    }
}
