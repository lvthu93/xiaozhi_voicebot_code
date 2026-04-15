package defpackage;

import info.dourok.voicebot.java.audio.MusicPlayer;
import info.dourok.voicebot.java.audio.j;
import java.util.concurrent.CountDownLatch;

/* renamed from: e5  reason: default package */
public final class e5 implements Runnable {
    public final /* synthetic */ CountDownLatch c;
    public final /* synthetic */ a5 f;

    public e5(a5 a5Var, CountDownLatch countDownLatch) {
        this.f = a5Var;
        this.c = countDownLatch;
    }

    public final void run() {
        a5 a5Var = this.f;
        if (a5Var.h == null) {
            j jVar = new j();
            a5Var.h = jVar;
            MusicPlayer.m mVar = a5Var.i;
            if (mVar != null) {
                jVar.a = mVar;
            }
        }
        this.c.countDown();
    }
}
