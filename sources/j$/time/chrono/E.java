package j$.time.chrono;

import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.u;

public enum E implements n {
    BEFORE_ROC,
    ROC;

    public final int getValue() {
        return ordinal();
    }

    public final u m(q qVar) {
        return p.d(this, qVar);
    }
}
