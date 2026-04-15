package j$.time.format;

import j$.time.DateTimeException;

final class m implements C0054g {
    private final C0054g a;
    private final int b;
    private final char c;

    m(C0054g gVar, int i, char c2) {
        this.a = gVar;
        this.b = i;
        this.c = c2;
    }

    public final boolean k(z zVar, StringBuilder sb) {
        int length = sb.length();
        if (!this.a.k(zVar, sb)) {
            return false;
        }
        int length2 = sb.length() - length;
        int i = this.b;
        if (length2 <= i) {
            for (int i2 = 0; i2 < i - length2; i2++) {
                sb.insert(length, this.c);
            }
            return true;
        }
        throw new DateTimeException("Cannot print as output of " + length2 + " characters exceeds pad width of " + i);
    }

    public final int l(x xVar, CharSequence charSequence, int i) {
        boolean l = xVar.l();
        if (i > charSequence.length()) {
            throw new IndexOutOfBoundsException();
        } else if (i == charSequence.length()) {
            return ~i;
        } else {
            int i2 = this.b + i;
            if (i2 > charSequence.length()) {
                if (l) {
                    return ~i;
                }
                i2 = charSequence.length();
            }
            int i3 = i;
            while (i3 < i2 && xVar.b(charSequence.charAt(i3), this.c)) {
                i3++;
            }
            int l2 = this.a.l(xVar, charSequence.subSequence(0, i2), i3);
            return (l2 == i2 || !l) ? l2 : ~(i + i3);
        }
    }

    public final String toString() {
        String str;
        char c2 = this.c;
        if (c2 == ' ') {
            str = ")";
        } else {
            str = ",'" + c2 + "')";
        }
        return "Pad(" + this.a + "," + this.b + str;
    }
}
