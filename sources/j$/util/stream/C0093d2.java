package j$.util.stream;

/* renamed from: j$.util.stream.d2  reason: case insensitive filesystem */
final class C0093d2 extends C0098e2 {
    C0093d2() {
    }

    public final void accept(Object obj) {
        this.b++;
    }

    public final Object get() {
        return Long.valueOf(this.b);
    }

    public final void h(Y1 y1) {
        this.b += ((C0098e2) y1).b;
    }
}
