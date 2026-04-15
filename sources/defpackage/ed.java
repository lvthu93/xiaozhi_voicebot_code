package defpackage;

/* renamed from: ed  reason: default package */
public final class ed extends RuntimeException {
    private static final long serialVersionUID = -7466929953374883507L;

    public ed() {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
    }
}
