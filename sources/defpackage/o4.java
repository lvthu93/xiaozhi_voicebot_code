package defpackage;

import j$.util.function.Consumer$CC;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;
import org.schabi.newpipe.extractor.utils.Pair;

/* renamed from: o4  reason: default package */
public final /* synthetic */ class o4 implements Consumer {
    public final /* synthetic */ int a;
    public final /* synthetic */ ArrayList b;

    public /* synthetic */ o4(ArrayList arrayList, int i) {
        this.a = i;
        this.b = arrayList;
    }

    public final void accept(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        Pair pair = (Pair) entry.getValue();
        int intValue = ((Integer) pair.getFirst()).intValue();
        int i = this.a;
        if (intValue < i) {
            this.b.add(entry);
        } else {
            pair.setFirst(Integer.valueOf(((Integer) pair.getFirst()).intValue() - i));
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }
}
