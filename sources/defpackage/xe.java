package defpackage;

/* renamed from: xe  reason: default package */
public final class xe implements Runnable {
    public final /* synthetic */ d0 c;
    public final /* synthetic */ Exception f;

    public xe(d0 d0Var, Exception exc) {
        this.c = d0Var;
        this.f = exc;
    }

    public final void run() {
        this.c.onError(this.f);
    }
}
