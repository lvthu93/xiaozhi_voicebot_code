package j$.time.format;

enum r implements C0054g {
    SENSITIVE,
    INSENSITIVE,
    STRICT,
    LENIENT;

    public final boolean k(z zVar, StringBuilder sb) {
        return true;
    }

    public final int l(x xVar, CharSequence charSequence, int i) {
        int ordinal = ordinal();
        if (ordinal == 0) {
            xVar.m(true);
        } else if (ordinal == 1) {
            xVar.m(false);
        } else if (ordinal == 2) {
            xVar.q(true);
        } else if (ordinal == 3) {
            xVar.q(false);
        }
        return i;
    }

    public final String toString() {
        int ordinal = ordinal();
        if (ordinal == 0) {
            return "ParseCaseSensitive(true)";
        }
        if (ordinal == 1) {
            return "ParseCaseSensitive(false)";
        }
        if (ordinal == 2) {
            return "ParseStrict(true)";
        }
        if (ordinal == 3) {
            return "ParseStrict(false)";
        }
        throw new IllegalStateException("Unreachable");
    }
}
