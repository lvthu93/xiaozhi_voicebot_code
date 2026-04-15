package defpackage;

import defpackage.t9;
import java.util.concurrent.ExecutorService;

/* renamed from: i8  reason: default package */
public final class i8 implements Runnable {
    public final /* synthetic */ d0 c;
    public final /* synthetic */ x7 f;

    public i8(x7 x7Var, t9.a aVar) {
        this.f = x7Var;
        this.c = aVar;
    }

    public final void run() {
        d0 d0Var = this.c;
        x7 x7Var = this.f;
        ExecutorService executorService = x7Var.a;
        try {
            x7Var.w();
            if (d0Var != null) {
                executorService.execute(new j8(d0Var));
            }
        } catch (Exception e) {
            if (d0Var != null) {
                executorService.execute(new k8(d0Var, e));
            }
        }
    }
}
