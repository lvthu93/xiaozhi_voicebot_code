package j$.util.concurrent;

import j$.util.Iterator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

final class h extends C0059a implements Iterator, Enumeration, j$.util.Iterator {
    public final /* synthetic */ int k;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public /* synthetic */ h(l[] lVarArr, int i, int i2, ConcurrentHashMap concurrentHashMap, int i3) {
        super(lVarArr, i, i2, concurrentHashMap);
        this.k = i3;
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        switch (this.k) {
            case 0:
                Iterator.CC.$default$forEachRemaining(this, consumer);
                return;
            default:
                Iterator.CC.$default$forEachRemaining(this, consumer);
                return;
        }
    }

    public final Object next() {
        switch (this.k) {
            case 0:
                l lVar = this.b;
                if (lVar != null) {
                    this.j = lVar;
                    a();
                    return lVar.b;
                }
                throw new NoSuchElementException();
            default:
                l lVar2 = this.b;
                if (lVar2 != null) {
                    Object obj = lVar2.c;
                    this.j = lVar2;
                    a();
                    return obj;
                }
                throw new NoSuchElementException();
        }
    }

    public final Object nextElement() {
        switch (this.k) {
            case 0:
                return next();
            default:
                return next();
        }
    }
}
