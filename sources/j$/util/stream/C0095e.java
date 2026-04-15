package j$.util.stream;

/* renamed from: j$.util.stream.e  reason: case insensitive filesystem */
abstract class C0095e {
    protected final int a;
    protected int b;
    protected int c;
    protected long[] d;

    protected C0095e() {
        this.a = 4;
    }

    protected C0095e(int i) {
        if (i >= 0) {
            this.a = Math.max(4, 32 - Integer.numberOfLeadingZeros(i - 1));
            return;
        }
        throw new IllegalArgumentException("Illegal Capacity: " + i);
    }

    public abstract void clear();

    public final long count() {
        int i = this.c;
        return i == 0 ? (long) this.b : this.d[i] + ((long) this.b);
    }
}
