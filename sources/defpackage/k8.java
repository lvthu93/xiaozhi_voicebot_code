package defpackage;

/* renamed from: k8  reason: default package */
public final class k8 implements Runnable {
    public final /* synthetic */ d0 c;
    public final /* synthetic */ Exception f;

    public k8(d0 d0Var, Exception exc) {
        this.c = d0Var;
        this.f = exc;
    }

    public final void run() {
        this.c.onError(this.f);
    }
}
