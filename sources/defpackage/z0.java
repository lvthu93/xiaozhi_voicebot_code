package defpackage;

import android.os.Handler;
import java.util.concurrent.Executor;

/* renamed from: z0  reason: default package */
public final /* synthetic */ class z0 implements Executor {
    public final /* synthetic */ Handler a;

    public /* synthetic */ z0(Handler handler) {
        this.a = handler;
    }

    public final void execute(Runnable runnable) {
        this.a.post(runnable);
    }
}
