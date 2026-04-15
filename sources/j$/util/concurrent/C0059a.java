package j$.util.concurrent;

/* renamed from: j$.util.concurrent.a  reason: case insensitive filesystem */
abstract class C0059a extends q {
    final ConcurrentHashMap i;
    l j;

    C0059a(l[] lVarArr, int i2, int i3, ConcurrentHashMap concurrentHashMap) {
        super(lVarArr, i2, 0, i3);
        this.i = concurrentHashMap;
        a();
    }

    public final boolean hasMoreElements() {
        return this.b != null;
    }

    public final boolean hasNext() {
        return this.b != null;
    }

    public final void remove() {
        l lVar = this.j;
        if (lVar != null) {
            this.j = null;
            this.i.h(lVar.b, (Object) null, (Object) null);
            return;
        }
        throw new IllegalStateException();
    }
}
