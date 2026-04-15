package j$.time.format;

import j$.time.DateTimeException;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.temporal.a;
import j$.time.temporal.s;
import j$.time.zone.j;
import java.text.ParsePosition;
import java.util.AbstractMap;
import java.util.Set;

class u implements C0054g {
    private static volatile AbstractMap.SimpleImmutableEntry c;
    private static volatile AbstractMap.SimpleImmutableEntry d;
    private final s a;
    private final String b;

    u(s sVar, String str) {
        this.a = sVar;
        this.b = str;
    }

    private static int b(x xVar, CharSequence charSequence, int i, int i2, l lVar) {
        String upperCase = charSequence.subSequence(i, i2).toString().toUpperCase();
        if (i2 < charSequence.length() && charSequence.charAt(i2) != '0' && !xVar.b(charSequence.charAt(i2), 'Z')) {
            x d2 = xVar.d();
            int l = lVar.l(d2, charSequence, i2);
            if (l < 0) {
                try {
                    if (lVar == l.e) {
                        return ~i;
                    }
                    xVar.n(ZoneId.S(upperCase));
                    return i2;
                } catch (DateTimeException unused) {
                    return ~i;
                }
            } else {
                xVar.n(ZoneId.V(upperCase, ZoneOffset.d0((int) d2.j(a.OFFSET_SECONDS).longValue())));
                return l;
            }
        } else {
            xVar.n(ZoneId.S(upperCase));
            return i2;
        }
    }

    /* access modifiers changed from: protected */
    public o a(x xVar) {
        Set a2 = j.a();
        int size = a2.size();
        AbstractMap.SimpleImmutableEntry simpleImmutableEntry = xVar.k() ? c : d;
        if (simpleImmutableEntry == null || ((Integer) simpleImmutableEntry.getKey()).intValue() != size) {
            synchronized (this) {
                try {
                    AbstractMap.SimpleImmutableEntry simpleImmutableEntry2 = xVar.k() ? c : d;
                    if (simpleImmutableEntry2 == null || ((Integer) simpleImmutableEntry2.getKey()).intValue() != size) {
                        simpleImmutableEntry2 = new AbstractMap.SimpleImmutableEntry(Integer.valueOf(size), o.g(a2, xVar));
                        if (xVar.k()) {
                            c = simpleImmutableEntry2;
                        } else {
                            d = simpleImmutableEntry2;
                        }
                    }
                } catch (Throwable th) {
                    while (true) {
                        throw th;
                    }
                }
            }
        }
        return (o) simpleImmutableEntry.getValue();
    }

    public boolean k(z zVar, StringBuilder sb) {
        ZoneId zoneId = (ZoneId) zVar.f(this.a);
        if (zoneId == null) {
            return false;
        }
        sb.append(zoneId.j());
        return true;
    }

    public final int l(x xVar, CharSequence charSequence, int i) {
        int i2;
        int length = charSequence.length();
        if (i > length) {
            throw new IndexOutOfBoundsException();
        } else if (i == length) {
            return ~i;
        } else {
            char charAt = charSequence.charAt(i);
            if (charAt == '+' || charAt == '-') {
                return b(xVar, charSequence, i, i, l.e);
            }
            int i3 = i + 2;
            if (length >= i3) {
                char charAt2 = charSequence.charAt(i + 1);
                if (xVar.b(charAt, 'U') && xVar.b(charAt2, 'T')) {
                    int i4 = i + 3;
                    return (length < i4 || !xVar.b(charSequence.charAt(i3), 'C')) ? b(xVar, charSequence, i, i3, l.f) : b(xVar, charSequence, i, i4, l.f);
                } else if (xVar.b(charAt, 'G') && length >= (i2 = i + 3) && xVar.b(charAt2, 'M') && xVar.b(charSequence.charAt(i3), 'T')) {
                    int i5 = i + 4;
                    if (length < i5 || !xVar.b(charSequence.charAt(i2), '0')) {
                        return b(xVar, charSequence, i, i2, l.f);
                    }
                    xVar.n(ZoneId.S("GMT0"));
                    return i5;
                }
            }
            o a2 = a(xVar);
            ParsePosition parsePosition = new ParsePosition(i);
            String d2 = a2.d(charSequence, parsePosition);
            if (d2 != null) {
                xVar.n(ZoneId.S(d2));
                return parsePosition.getIndex();
            } else if (!xVar.b(charAt, 'Z')) {
                return ~i;
            } else {
                xVar.n(ZoneOffset.UTC);
                return i + 1;
            }
        }
    }

    public final String toString() {
        return this.b;
    }
}
