package defpackage;

/* renamed from: ne  reason: default package */
public final class ne implements Runnable {
    public final /* synthetic */ d0 c;
    public final /* synthetic */ InterruptedException f;

    public ne(d0 d0Var, InterruptedException interruptedException) {
        this.c = d0Var;
        this.f = interruptedException;
    }

    public final void run() {
        this.c.onError(this.f);
    }
}
