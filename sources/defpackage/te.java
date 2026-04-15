package defpackage;

import android.util.Log;
import java.util.concurrent.ExecutorService;

/* renamed from: te  reason: default package */
public final class te implements Runnable {
    public final /* synthetic */ byte[] c;
    public final /* synthetic */ d0 f = null;
    public final /* synthetic */ ye g;

    public te(ye yeVar, byte[] bArr) {
        this.g = yeVar;
        this.c = bArr;
    }

    public final void run() {
        byte[] bArr = this.c;
        d0 d0Var = this.f;
        ye yeVar = this.g;
        ExecutorService executorService = yeVar.a;
        try {
            if (yeVar.n != null) {
                if (yeVar.k.get()) {
                    if (yeVar.n.send(cq.q(bArr))) {
                        Log.v("WebSocketProtocol", "Audio sent: " + bArr.length + " bytes");
                        if (d0Var != null) {
                            executorService.execute(new ve(d0Var));
                            return;
                        }
                        return;
                    } else if (d0Var != null) {
                        executorService.execute(new we(d0Var));
                        return;
                    } else {
                        return;
                    }
                }
            }
            if (d0Var != null) {
                executorService.execute(new ue(d0Var));
            }
        } catch (Exception e) {
            if (d0Var != null) {
                executorService.execute(new xe(d0Var, e));
            }
        }
    }
}
