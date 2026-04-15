package defpackage;

/* renamed from: ke  reason: default package */
public final class ke implements Runnable {
    public final /* synthetic */ d0 c;
    public final /* synthetic */ Exception f;

    public ke(d0 d0Var, Exception exc) {
        this.c = d0Var;
        this.f = exc;
    }

    public final void run() {
        this.c.onError(this.f);
    }
}
