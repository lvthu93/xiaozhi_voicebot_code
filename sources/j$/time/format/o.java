package j$.time.format;

import java.text.ParsePosition;
import java.util.Iterator;
import java.util.Set;

class o {
    protected String a;
    protected String b;
    protected char c;
    protected o d;
    protected o e;

    private o(String str, String str2, o oVar) {
        this.a = str;
        this.b = str2;
        this.d = oVar;
        this.c = str.isEmpty() ? 65535 : this.a.charAt(0);
    }

    /* synthetic */ o(String str, String str2, o oVar, int i) {
        this(str, str2, oVar);
    }

    private boolean b(String str, String str2) {
        int i = 0;
        while (i < str.length() && i < this.a.length() && c(str.charAt(i), this.a.charAt(i))) {
            i++;
        }
        if (i != this.a.length()) {
            o e2 = e(this.a.substring(i), this.b, this.d);
            this.a = str.substring(0, i);
            this.d = e2;
            if (i < str.length()) {
                this.d.e = e(str.substring(i), str2, (o) null);
                this.b = null;
            } else {
                this.b = str2;
            }
            return true;
        } else if (i < str.length()) {
            String substring = str.substring(i);
            for (o oVar = this.d; oVar != null; oVar = oVar.e) {
                if (c(oVar.c, substring.charAt(0))) {
                    return oVar.b(substring, str2);
                }
            }
            o e3 = e(substring, str2, (o) null);
            e3.e = this.d;
            this.d = e3;
            return true;
        } else {
            this.b = str2;
            return true;
        }
    }

    public static o f(x xVar) {
        return xVar.k() ? new o("", (String) null, (o) null) : new n();
    }

    public static o g(Set set, x xVar) {
        o f = f(xVar);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            f.b(str, str);
        }
        return f;
    }

    public final void a(String str, String str2) {
        b(str, str2);
    }

    /* access modifiers changed from: protected */
    public boolean c(char c2, char c3) {
        return c2 == c3;
    }

    public final String d(CharSequence charSequence, ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        int length = charSequence.length();
        if (!h(charSequence, index, length)) {
            return null;
        }
        int length2 = this.a.length() + index;
        o oVar = this.d;
        if (oVar != null && length2 != length) {
            while (true) {
                if (!c(oVar.c, charSequence.charAt(length2))) {
                    oVar = oVar.e;
                    if (oVar == null) {
                        break;
                    }
                } else {
                    parsePosition.setIndex(length2);
                    String d2 = oVar.d(charSequence, parsePosition);
                    if (d2 != null) {
                        return d2;
                    }
                }
            }
        }
        parsePosition.setIndex(length2);
        return this.b;
    }

    /* access modifiers changed from: protected */
    public o e(String str, String str2, o oVar) {
        return new o(str, str2, oVar);
    }

    /* access modifiers changed from: protected */
    public boolean h(CharSequence charSequence, int i, int i2) {
        if (charSequence instanceof String) {
            return ((String) charSequence).startsWith(this.a, i);
        }
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
            if (!c(this.a.charAt(i3), charSequence.charAt(i))) {
                return false;
            }
            i = i6;
            length = i4;
            i3 = i5;
        }
    }
}
