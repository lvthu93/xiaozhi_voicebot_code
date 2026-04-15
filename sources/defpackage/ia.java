package defpackage;

import com.google.android.exoplayer2.scheduler.RequirementsWatcher;

/* renamed from: ia  reason: default package */
public final /* synthetic */ class ia implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ RequirementsWatcher.b f;

    public /* synthetic */ ia(RequirementsWatcher.b bVar, int i) {
        this.c = i;
        this.f = bVar;
    }

    public final void run() {
        int i = this.c;
        RequirementsWatcher.b bVar = this.f;
        switch (i) {
            case 0:
                RequirementsWatcher requirementsWatcher = RequirementsWatcher.this;
                if (requirementsWatcher.g != null && (requirementsWatcher.f & 3) != 0) {
                    requirementsWatcher.a();
                    return;
                }
                return;
            default:
                RequirementsWatcher requirementsWatcher2 = RequirementsWatcher.this;
                if (requirementsWatcher2.g != null) {
                    requirementsWatcher2.a();
                    return;
                }
                return;
        }
    }
}
