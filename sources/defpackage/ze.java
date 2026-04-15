package defpackage;

/* renamed from: ze  reason: default package */
public final class ze implements Runnable {
    public final /* synthetic */ d0 c;

    public ze(d0 d0Var) {
        this.c = d0Var;
    }

    public final void run() {
        this.c.onError(new IllegalStateException("WebSocket not connected"));
    }
}
