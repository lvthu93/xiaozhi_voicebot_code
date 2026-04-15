package defpackage;

import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.b;
import com.google.android.exoplayer2.util.Assertions;

/* renamed from: d9  reason: default package */
public final /* synthetic */ class d9 implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ b f;

    public /* synthetic */ d9(b bVar, int i) {
        this.c = i;
        this.f = bVar;
    }

    public final void run() {
        int i = this.c;
        b bVar = this.f;
        switch (i) {
            case 0:
                bVar.e();
                return;
            default:
                if (!bVar.ap) {
                    ((MediaPeriod.Callback) Assertions.checkNotNull(bVar.u)).onContinueLoadingRequested(bVar);
                    return;
                }
                return;
        }
    }
}
