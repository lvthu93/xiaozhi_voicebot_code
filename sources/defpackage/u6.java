package defpackage;

/* renamed from: u6  reason: default package */
public final class u6 implements Runnable {
    public final /* synthetic */ w6 c;

    public u6(w6 w6Var) {
        this.c = w6Var;
    }

    public final void run() {
        w6 w6Var = this.c;
        w6Var.q.post(new v6(w6Var));
    }
}
