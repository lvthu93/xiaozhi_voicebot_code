package defpackage;

/* renamed from: b8  reason: default package */
public final class b8 implements Runnable {
    public final /* synthetic */ d0 c;
    public final /* synthetic */ boolean f;

    public b8(d0 d0Var, boolean z) {
        this.c = d0Var;
        this.f = z;
    }

    public final void run() {
        this.c.onSuccess(Boolean.valueOf(this.f));
    }
}
