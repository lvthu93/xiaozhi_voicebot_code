package defpackage;

/* renamed from: h9  reason: default package */
public final class h9 implements Runnable {
    public final /* synthetic */ i9 c;

    public h9(i9 i9Var) {
        this.c = i9Var;
    }

    public final void run() {
        this.c.e.onDisconnected();
    }
}
