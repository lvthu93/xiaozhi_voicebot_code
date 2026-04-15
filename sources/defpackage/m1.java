package defpackage;

/* renamed from: m1  reason: default package */
public enum m1 {
    IDLE("idle"),
    CONNECTING("connecting"),
    LISTENING("listening"),
    SPEAKING("speaking");
    
    public final String c;

    /* access modifiers changed from: public */
    m1(String str) {
        this.c = str;
    }

    public final String toString() {
        return this.c;
    }
}
