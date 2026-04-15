package defpackage;

import defpackage.w6;

/* renamed from: x6  reason: default package */
public final class x6 implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ int f;
    public final /* synthetic */ w6 g;

    public x6(w6 w6Var, int i, int i2) {
        this.g = w6Var;
        this.c = i;
        this.f = i2;
    }

    public final void run() {
        w6.d dVar = this.g.f;
        if (dVar != null) {
            dVar.onPlaybackProgress(this.c, this.f);
        }
    }
}
