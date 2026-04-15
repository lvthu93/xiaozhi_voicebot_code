package j$.util.stream;

import java.util.function.Predicate;

/* renamed from: j$.util.stream.v0  reason: case insensitive filesystem */
final class C0180v0 extends C0199z0 {
    final /* synthetic */ A0 c;
    final /* synthetic */ Predicate d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C0180v0(A0 a0, Predicate predicate) {
        super(a0);
        this.c = a0;
        this.d = predicate;
    }

    public final void accept(Object obj) {
        if (!this.a) {
            boolean test = this.d.test(obj);
            A0 a0 = this.c;
            if (test == a0.a) {
                this.a = true;
                this.b = a0.b;
            }
        }
    }
}
