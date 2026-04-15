package defpackage;

/* renamed from: j9  reason: default package */
public final class j9 implements Runnable {
    public final /* synthetic */ byte[] c;
    public final /* synthetic */ i9 f;

    public j9(ye yeVar, byte[] bArr) {
        this.f = yeVar;
        this.c = bArr;
    }

    public final void run() {
        this.f.e.onIncomingAudio(this.c);
    }
}
