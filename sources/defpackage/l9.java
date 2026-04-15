package defpackage;

/* renamed from: l9  reason: default package */
public final class l9 implements Runnable {
    public final /* synthetic */ String c;
    public final /* synthetic */ i9 f;

    public l9(i9 i9Var, String str) {
        this.f = i9Var;
        this.c = str;
    }

    public final void run() {
        this.f.e.onSessionStarted(this.c);
    }
}
