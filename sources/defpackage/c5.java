package defpackage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: c5  reason: default package */
public final class c5 implements Runnable {
    public final /* synthetic */ AtomicReference c;
    public final /* synthetic */ String f;
    public final /* synthetic */ CountDownLatch g;
    public final /* synthetic */ a5 h;

    public c5(a5 a5Var, String str, CountDownLatch countDownLatch, AtomicReference atomicReference) {
        this.h = a5Var;
        this.c = atomicReference;
        this.f = str;
        this.g = countDownLatch;
    }

    public final void run() {
        this.h.getClass();
        this.c.set(oa.b(this.f));
        this.g.countDown();
    }
}
