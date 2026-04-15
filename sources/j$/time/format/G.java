package j$.time.format;

public enum G {
    FULL(0),
    FULL_STANDALONE(0),
    SHORT(1),
    SHORT_STANDALONE(1),
    NARROW(1),
    NARROW_STANDALONE(1);
    
    private final int a;

    private G(int i) {
        this.a = i;
    }

    /* access modifiers changed from: package-private */
    public final int k() {
        return this.a;
    }
}
