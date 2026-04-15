package defpackage;

/* renamed from: pe  reason: default package */
public final class pe implements Runnable {
    public final /* synthetic */ d0 c;

    public pe(d0 d0Var) {
        this.c = d0Var;
    }

    public final void run() {
        this.c.onSuccess(Boolean.TRUE);
    }
}
