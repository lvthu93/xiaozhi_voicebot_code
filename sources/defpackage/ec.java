package defpackage;

import java.lang.reflect.Method;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: ec  reason: default package */
public final class ec implements k4 {
    public final String c;
    public volatile k4 f;
    public Boolean g;
    public Method h;
    public d2 i;
    public final Queue<gc> j;
    public final boolean k;

    public ec(String str, LinkedBlockingQueue linkedBlockingQueue, boolean z) {
        this.c = str;
        this.j = linkedBlockingQueue;
        this.k = z;
    }

    public final void a(String str) {
        j().a(str);
    }

    public final void b(String str) {
        j().b(str);
    }

    public final void c(String str, Throwable th) {
        j().c(str, th);
    }

    public final void d(Object obj, String str) {
        j().d(obj, str);
    }

    public final boolean e() {
        return j().e();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ec.class != obj.getClass() || !this.c.equals(((ec) obj).c)) {
            return false;
        }
        return true;
    }

    public final void f(String str, Integer num, Object obj) {
        j().f(str, num, obj);
    }

    public final void g(String str, Object obj) {
        j().g(str, obj);
    }

    public final String getName() {
        return this.c;
    }

    public final void h(String str, Throwable th) {
        j().h(str, th);
    }

    public final int hashCode() {
        return this.c.hashCode();
    }

    public final void i(String str) {
        j().i(str);
    }

    public final k4 j() {
        if (this.f != null) {
            return this.f;
        }
        if (this.k) {
            return d7.c;
        }
        if (this.i == null) {
            this.i = new d2(this, this.j);
        }
        return this.i;
    }

    public final boolean k() {
        Boolean bool = this.g;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            this.h = this.f.getClass().getMethod("log", new Class[]{m4.class});
            this.g = Boolean.TRUE;
        } catch (NoSuchMethodException unused) {
            this.g = Boolean.FALSE;
        }
        return this.g.booleanValue();
    }
}
