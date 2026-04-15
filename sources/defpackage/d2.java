package defpackage;

import java.util.Queue;

/* renamed from: d2  reason: default package */
public final class d2 implements k4 {
    public final String c;
    public final ec f;
    public final Queue<gc> g;

    public d2(ec ecVar, Queue<gc> queue) {
        this.f = ecVar;
        this.c = ecVar.c;
        this.g = queue;
    }

    public final void a(String str) {
        j((Object[]) null);
    }

    public final void b(String str) {
        j(new Object[]{str});
    }

    public final void c(String str, Throwable th) {
        j((Object[]) null);
    }

    public final void d(Object obj, String str) {
        j(new Object[]{obj});
    }

    public final boolean e() {
        return true;
    }

    public final void f(String str, Integer num, Object obj) {
        j(new Object[]{num, obj});
    }

    public final void g(String str, Object obj) {
        j(new Object[]{str, obj});
    }

    public final String getName() {
        return this.c;
    }

    public final void h(String str, Throwable th) {
        j((Object[]) null);
    }

    public final void i(String str) {
        j((Object[]) null);
    }

    public final void j(Object[] objArr) {
        gc gcVar = new gc();
        System.currentTimeMillis();
        gcVar.a = this.f;
        gcVar.b = objArr;
        Thread.currentThread().getName();
        this.g.add(gcVar);
    }
}
