package defpackage;

/* renamed from: ue  reason: default package */
public final class ue implements Runnable {
    public final /* synthetic */ d0 c;

    public ue(d0 d0Var) {
        this.c = d0Var;
    }

    public final void run() {
        this.c.onError(new IllegalStateException("WebSocket not connected"));
    }
}
