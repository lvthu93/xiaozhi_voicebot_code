package j$.time.format;

final class n extends o {
    n() {
        super("", (String) null, (o) null, 0);
    }

    private n(String str, String str2, o oVar) {
        super(str, str2, oVar, 0);
    }

    /* access modifiers changed from: protected */
    public final boolean c(char c, char c2) {
        return x.c(c, c2);
    }

    /* access modifiers changed from: protected */
    public final o e(String str, String str2, o oVar) {
        return new n(str, str2, oVar);
    }

    /* access modifiers changed from: protected */
    public final boolean h(CharSequence charSequence, int i, int i2) {
        int length = this.a.length();
        if (length > i2 - i) {
            return false;
        }
        int i3 = 0;
        while (true) {
            int i4 = length - 1;
            if (length <= 0) {
                return true;
            }
            int i5 = i3 + 1;
            int i6 = i + 1;
            if (!x.c(this.a.charAt(i3), charSequence.charAt(i))) {
                return false;
            }
            i = i6;
            length = i4;
            i3 = i5;
        }
    }
}
