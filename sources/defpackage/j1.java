package defpackage;

import androidx.core.content.ContextCompat;
import info.dourok.voicebot.java.activities.ActivationActivity;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: j1  reason: default package */
public final class j1 implements Runnable {
    public final /* synthetic */ b0 c;
    public final /* synthetic */ k1 f;

    public j1(k1 k1Var, ActivationActivity.C0028AnonymousClass4 anonymousClass4) {
        this.f = k1Var;
        this.c = anonymousClass4;
    }

    public final void run() {
        b0 b0Var = this.c;
        k1 k1Var = this.f;
        k1Var.getClass();
        try {
            ArrayList arrayList = new ArrayList();
            String[] strArr = k1.d;
            for (int i = 0; i < 4; i++) {
                String str = strArr[i];
                if (ContextCompat.checkSelfPermission(k1Var.a, str) != 0) {
                    arrayList.add(str);
                }
            }
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                if (it.hasNext()) {
                    b0Var.onPermissionRequired((String) it.next());
                    return;
                }
            }
            b0Var.onDeviceInfoReady(k1Var.a());
        } catch (Exception e) {
            b0Var.onError(e.getMessage());
        }
    }
}
