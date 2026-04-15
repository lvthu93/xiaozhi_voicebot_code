package j$.util.stream;

import j$.util.Optional;

final class O extends P {
    static final J c;
    static final J d;

    static {
        C0134l3 l3Var = C0134l3.REFERENCE;
        c = new J(true, l3Var, Optional.empty(), new L(2), new C0080b(16));
        d = new J(false, l3Var, Optional.empty(), new L(2), new C0080b(16));
    }

    O() {
    }

    public final Object get() {
        if (this.a) {
            return Optional.of(this.b);
        }
        return null;
    }
}
