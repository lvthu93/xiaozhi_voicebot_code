package j$.util.stream;

abstract class O0 implements M0 {
    protected final M0 a;
    protected final M0 b;
    private final long c;

    O0(M0 m0, M0 m02) {
        this.a = m0;
        this.b = m02;
        this.c = m0.count() + m02.count();
    }

    public final M0 a(int i) {
        if (i == 0) {
            return this.a;
        }
        if (i == 1) {
            return this.b;
        }
        throw new IndexOutOfBoundsException();
    }

    public final long count() {
        return this.c;
    }

    public final int n() {
        return 2;
    }
}
