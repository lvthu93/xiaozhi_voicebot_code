package j$.util.stream;

import j$.util.T;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;

/* renamed from: j$.util.stream.h  reason: case insensitive filesystem */
public final /* synthetic */ class C0110h implements BaseStream {
    public final /* synthetic */ C0115i a;

    private /* synthetic */ C0110h(C0115i iVar) {
        this.a = iVar;
    }

    public static /* synthetic */ BaseStream k(C0115i iVar) {
        if (iVar == null) {
            return null;
        }
        return iVar instanceof C0105g ? ((C0105g) iVar).a : iVar instanceof I ? H.k((I) iVar) : iVar instanceof C0126k0 ? C0121j0.k((C0126k0) iVar) : iVar instanceof C0175u0 ? C0170t0.k((C0175u0) iVar) : iVar instanceof Stream ? C0114h3.k((Stream) iVar) : new C0110h(iVar);
    }

    public final /* synthetic */ void close() {
        this.a.close();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        C0115i iVar = this.a;
        if (obj instanceof C0110h) {
            obj = ((C0110h) obj).a;
        }
        return iVar.equals(obj);
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    public final /* synthetic */ Iterator iterator() {
        return this.a.iterator();
    }

    public final /* synthetic */ BaseStream onClose(Runnable runnable) {
        return k(this.a.onClose(runnable));
    }

    public final /* synthetic */ BaseStream parallel() {
        return k(this.a.parallel());
    }

    public final /* synthetic */ BaseStream sequential() {
        return k(this.a.sequential());
    }

    public final /* synthetic */ Spliterator spliterator() {
        return T.a(this.a.spliterator());
    }

    public final /* synthetic */ BaseStream unordered() {
        return k(this.a.unordered());
    }
}
