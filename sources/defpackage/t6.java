package defpackage;

import defpackage.w6;

/* renamed from: t6  reason: default package */
public final class t6 implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ float f;
    public final /* synthetic */ w6 g;

    public t6(w6 w6Var, int i, float f2) {
        this.g = w6Var;
        this.c = i;
        this.f = f2;
    }

    public final void run() {
        w6.d dVar = this.g.f;
        if (dVar != null) {
            dVar.onRecordingProgress(this.c, this.f);
        }
    }
}
