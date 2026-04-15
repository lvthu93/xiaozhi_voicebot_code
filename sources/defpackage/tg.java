package defpackage;

import com.grack.nanojson.JsonObject;
import j$.util.function.Function$CC;
import java.util.function.Function;

/* renamed from: tg  reason: default package */
public final /* synthetic */ class tg implements Function {
    public final /* synthetic */ int a;
    public final /* synthetic */ Class b;

    public /* synthetic */ tg(Class cls, int i) {
        this.a = i;
        this.b = cls;
    }

    public final /* synthetic */ Function andThen(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$andThen(this, function);
            case 1:
                return Function$CC.$default$andThen(this, function);
            default:
                return Function$CC.$default$andThen(this, function);
        }
    }

    public final Object apply(Object obj) {
        int i = this.a;
        Class cls = this.b;
        switch (i) {
            case 0:
                return (JsonObject) cls.cast(obj);
            case 1:
                return (JsonObject) cls.cast(obj);
            default:
                return (String) cls.cast(obj);
        }
    }

    public final /* synthetic */ Function compose(Function function) {
        switch (this.a) {
            case 0:
                return Function$CC.$default$compose(this, function);
            case 1:
                return Function$CC.$default$compose(this, function);
            default:
                return Function$CC.$default$compose(this, function);
        }
    }
}
