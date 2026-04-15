package defpackage;

/* renamed from: we  reason: default package */
public final class we implements Runnable {
    public final /* synthetic */ d0 c;

    public we(d0 d0Var) {
        this.c = d0Var;
    }

    public final void run() {
        this.c.onError(new RuntimeException("WebSocket send failed"));
    }
}
