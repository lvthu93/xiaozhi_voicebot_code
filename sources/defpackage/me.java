package defpackage;

/* renamed from: me  reason: default package */
public final class me implements Runnable {
    public final /* synthetic */ d0 c;

    public me(d0 d0Var) {
        this.c = d0Var;
    }

    public final void run() {
        this.c.onError(new IllegalStateException("DeviceInfo is not set"));
    }
}
