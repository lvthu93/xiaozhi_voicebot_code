package defpackage;

/* renamed from: re  reason: default package */
public final class re implements Runnable {
    public final /* synthetic */ d0 c;
    public final /* synthetic */ Exception f;

    public re(d0 d0Var, Exception exc) {
        this.c = d0Var;
        this.f = exc;
    }

    public final void run() {
        this.c.onError(this.f);
    }
}
