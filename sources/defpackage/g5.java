package defpackage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: g5  reason: default package */
public final class g5 implements Runnable {
    public final /* synthetic */ AtomicBoolean c;
    public final /* synthetic */ String f;
    public final /* synthetic */ CountDownLatch g;
    public final /* synthetic */ a5 h;

    public g5(a5 a5Var, AtomicBoolean atomicBoolean, String str, CountDownLatch countDownLatch) {
        this.h = a5Var;
        this.c = atomicBoolean;
        this.f = str;
        this.g = countDownLatch;
    }

    public final void run() {
        AtomicBoolean atomicBoolean = this.c;
        String str = this.f;
        CountDownLatch countDownLatch = this.g;
        a5 a5Var = this.h;
        a5Var.getClass();
        try {
            a5Var.h.b(str);
            atomicBoolean.set(true);
        } catch (Exception unused) {
        } catch (Throwable th) {
            countDownLatch.countDown();
            throw th;
        }
        countDownLatch.countDown();
    }
}
