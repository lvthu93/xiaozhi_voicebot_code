package defpackage;

/* renamed from: qe  reason: default package */
public final class qe implements Runnable {
    public final /* synthetic */ d0 c;

    public qe(d0 d0Var) {
        this.c = d0Var;
    }

    public final void run() {
        this.c.onError(new IllegalStateException("WebSocket URL is not set"));
    }
}
