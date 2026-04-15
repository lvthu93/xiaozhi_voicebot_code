package defpackage;

import com.google.protobuf.v;
import com.google.protobuf.w;
import java.util.Iterator;
import java.util.Map;

/* renamed from: r4  reason: default package */
public final class r4 implements q4 {
    public final w a(Object obj, Object obj2) {
        w wVar = (w) obj;
        w wVar2 = (w) obj2;
        if (!wVar2.isEmpty()) {
            if (!wVar.c) {
                if (wVar.isEmpty()) {
                    wVar = new w();
                } else {
                    wVar = new w(wVar);
                }
            }
            wVar.b();
            if (!wVar2.isEmpty()) {
                wVar.putAll(wVar2);
            }
        }
        return wVar;
    }

    public final Object b(Object obj) {
        ((w) obj).c = false;
        return obj;
    }

    public final void c(Object obj) {
        ((v) obj).getClass();
    }

    public final w d() {
        w<?, ?> wVar = w.f;
        if (wVar.isEmpty()) {
            return new w();
        }
        return new w(wVar);
    }

    public final w e(Object obj) {
        return (w) obj;
    }

    public final void f(int i, Object obj, Object obj2) {
        w wVar = (w) obj;
        v vVar = (v) obj2;
        if (!wVar.isEmpty()) {
            Iterator it = wVar.entrySet().iterator();
            if (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                entry.getKey();
                entry.getValue();
                vVar.getClass();
                n0.s(i);
                throw null;
            }
        }
    }

    public final boolean g(Object obj) {
        return !((w) obj).c;
    }

    public final w h(Object obj) {
        return (w) obj;
    }
}
