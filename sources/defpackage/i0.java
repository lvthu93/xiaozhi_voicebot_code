package defpackage;

/* renamed from: i0  reason: default package */
public final class i0 {
    public final String a;
    public final long b;
    public final a c;

    /* renamed from: i0$a */
    public enum a {
        USER,
        SERVER,
        SYSTEM
    }

    public i0(String str, a aVar, long j) {
        this.a = str;
        this.c = aVar;
        this.b = j;
    }

    public final String toString() {
        return "ChatMessage{content='" + this.a + "', type=" + this.c + ", timestamp=" + this.b + '}';
    }
}
