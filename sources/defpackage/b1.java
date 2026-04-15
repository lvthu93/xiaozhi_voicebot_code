package defpackage;

import java.util.concurrent.Executor;

/* renamed from: b1  reason: default package */
public final /* synthetic */ class b1 implements Executor {
    public final /* synthetic */ int a;

    public /* synthetic */ b1(int i) {
        this.a = i;
    }

    public final void execute(Runnable runnable) {
        switch (this.a) {
            case 0:
                runnable.run();
                return;
            case 1:
                runnable.run();
                return;
            case 2:
                runnable.run();
                return;
            default:
                runnable.run();
                return;
        }
    }
}
