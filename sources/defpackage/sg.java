package defpackage;

import j$.util.function.Predicate$CC;
import java.util.function.Predicate;

/* renamed from: sg  reason: default package */
public final /* synthetic */ class sg implements Predicate {
    public final /* synthetic */ int a;
    public final /* synthetic */ Class b;

    public /* synthetic */ sg(Class cls, int i) {
        this.a = i;
        this.b = cls;
    }

    public final /* synthetic */ Predicate and(Predicate predicate) {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$and(this, predicate);
            case 1:
                return Predicate$CC.$default$and(this, predicate);
            default:
                return Predicate$CC.$default$and(this, predicate);
        }
    }

    public final /* synthetic */ Predicate negate() {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$negate(this);
            case 1:
                return Predicate$CC.$default$negate(this);
            default:
                return Predicate$CC.$default$negate(this);
        }
    }

    public final /* synthetic */ Predicate or(Predicate predicate) {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$or(this, predicate);
            case 1:
                return Predicate$CC.$default$or(this, predicate);
            default:
                return Predicate$CC.$default$or(this, predicate);
        }
    }

    public final boolean test(Object obj) {
        int i = this.a;
        Class cls = this.b;
        switch (i) {
            case 0:
                return cls.isInstance(obj);
            case 1:
                return cls.isInstance(obj);
            default:
                return cls.isInstance(obj);
        }
    }
}
