package j$.time.format;

import j$.time.chrono.m;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;

public final /* synthetic */ class p implements Consumer {
    public final /* synthetic */ q a;
    public final /* synthetic */ x b;
    public final /* synthetic */ long c;
    public final /* synthetic */ int d;
    public final /* synthetic */ int e;

    public /* synthetic */ p(q qVar, x xVar, long j, int i, int i2) {
        this.a = qVar;
        this.b = xVar;
        this.c = j;
        this.d = i;
        this.e = i2;
    }

    public final void accept(Object obj) {
        m mVar = (m) obj;
        this.a.d(this.b, this.c, this.d, this.e);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }
}
