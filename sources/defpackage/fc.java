package defpackage;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: fc  reason: default package */
public final class fc implements ILoggerFactory {
    public boolean a = false;
    public final HashMap b = new HashMap();
    public final LinkedBlockingQueue<gc> c = new LinkedBlockingQueue<>();

    public final synchronized k4 a(String str) {
        ec ecVar;
        ecVar = (ec) this.b.get(str);
        if (ecVar == null) {
            ecVar = new ec(str, this.c, this.a);
            this.b.put(str, ecVar);
        }
        return ecVar;
    }

    public final void b() {
        this.b.clear();
        this.c.clear();
    }
}
