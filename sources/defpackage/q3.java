package defpackage;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import org.mozilla.javascript.Hashtable;

/* renamed from: q3  reason: default package */
public final /* synthetic */ class q3 implements Consumer {
    public final void accept(Object obj) {
        ((Hashtable.Entry) obj).clear();
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }
}
