package defpackage;

import defpackage.t9;

/* renamed from: se  reason: default package */
public final class se implements Runnable {
    public final /* synthetic */ d0 c;
    public final /* synthetic */ ye f;

    public se(ye yeVar, t9.b bVar) {
        this.f = yeVar;
        this.c = bVar;
    }

    public final void run() {
        d0 d0Var = this.c;
        ye yeVar = this.f;
        yeVar.getClass();
        try {
            yeVar.d.execute(new le(yeVar, new cg()));
            if (d0Var != null) {
                d0Var.onSuccess(null);
            }
        } catch (Exception e) {
            if (d0Var != null) {
                d0Var.onError(e);
            }
        }
    }
}
