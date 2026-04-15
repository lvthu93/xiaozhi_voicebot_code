package j$.time.format;

/* renamed from: j$.time.format.e  reason: case insensitive filesystem */
final class C0052e implements C0054g {
    private final char a;

    C0052e(char c) {
        this.a = c;
    }

    public final boolean k(z zVar, StringBuilder sb) {
        sb.append(this.a);
        return true;
    }

    public final int l(x xVar, CharSequence charSequence, int i) {
        if (i == charSequence.length()) {
            return ~i;
        }
        char charAt = charSequence.charAt(i);
        char c = this.a;
        return (charAt == c || (!xVar.k() && (Character.toUpperCase(charAt) == Character.toUpperCase(c) || Character.toLowerCase(charAt) == Character.toLowerCase(c)))) ? i + 1 : ~i;
    }

    public final String toString() {
        char c = this.a;
        if (c == '\'') {
            return "''";
        }
        return "'" + c + "'";
    }
}
