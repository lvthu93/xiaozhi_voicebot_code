package defpackage;

/* renamed from: i1  reason: default package */
public final class i1 implements Runnable {
    public final /* synthetic */ d0 c;
    public final /* synthetic */ k1 f;

    public i1(k1 k1Var, s9 s9Var) {
        this.f = k1Var;
        this.c = s9Var;
    }

    public final void run() {
        d0 d0Var = this.c;
        k1 k1Var = this.f;
        k1Var.getClass();
        try {
            d0Var.onSuccess(k1Var.a());
        } catch (Exception e) {
            d0Var.onError(e);
        }
    }
}
