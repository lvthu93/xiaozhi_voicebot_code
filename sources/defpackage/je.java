package defpackage;

/* renamed from: je  reason: default package */
public final class je implements Runnable {
    public final /* synthetic */ d0 c;

    public je(d0 d0Var) {
        this.c = d0Var;
    }

    public final void run() {
        this.c.onError(new RuntimeException("WebSocket send failed"));
    }
}
