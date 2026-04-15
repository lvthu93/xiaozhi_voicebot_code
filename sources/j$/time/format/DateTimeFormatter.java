package j$.time.format;

import j$.time.DateTimeException;
import j$.time.ZoneId;
import j$.time.chrono.m;
import j$.time.chrono.t;
import j$.time.e;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import j$.time.temporal.i;
import j$.util.Objects;
import java.io.IOException;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

public final class DateTimeFormatter {
    public static final DateTimeFormatter h;
    public static final DateTimeFormatter i;
    public static final DateTimeFormatter j;
    private final C0053f a;
    private final Locale b;
    private final C c;
    private final E d;
    private final Set e = null;
    private final m f;
    private final ZoneId g;

    static {
        w wVar = new w();
        a aVar = a.YEAR;
        F f2 = F.EXCEEDS_PAD;
        wVar.k(aVar, 4, 10, f2);
        wVar.e('-');
        a aVar2 = a.MONTH_OF_YEAR;
        wVar.m(aVar2, 2);
        wVar.e('-');
        a aVar3 = a.DAY_OF_MONTH;
        wVar.m(aVar3, 2);
        E e2 = E.STRICT;
        t tVar = t.d;
        DateTimeFormatter u = wVar.u(e2, tVar);
        h = u;
        w wVar2 = new w();
        wVar2.q();
        wVar2.a(u);
        wVar2.h();
        wVar2.u(e2, tVar);
        w wVar3 = new w();
        wVar3.q();
        wVar3.a(u);
        wVar3.p();
        wVar3.h();
        wVar3.u(e2, tVar);
        w wVar4 = new w();
        a aVar4 = a.HOUR_OF_DAY;
        wVar4.m(aVar4, 2);
        wVar4.e(':');
        a aVar5 = a.MINUTE_OF_HOUR;
        wVar4.m(aVar5, 2);
        wVar4.p();
        wVar4.e(':');
        a aVar6 = a.SECOND_OF_MINUTE;
        wVar4.m(aVar6, 2);
        wVar4.p();
        wVar4.b(a.NANO_OF_SECOND, 0, 9, true);
        DateTimeFormatter u2 = wVar4.u(e2, (t) null);
        w wVar5 = new w();
        wVar5.q();
        wVar5.a(u2);
        wVar5.h();
        wVar5.u(e2, (t) null);
        w wVar6 = new w();
        wVar6.q();
        wVar6.a(u2);
        wVar6.p();
        wVar6.h();
        wVar6.u(e2, (t) null);
        w wVar7 = new w();
        wVar7.q();
        wVar7.a(u);
        wVar7.e('T');
        wVar7.a(u2);
        DateTimeFormatter u3 = wVar7.u(e2, tVar);
        w wVar8 = new w();
        wVar8.q();
        wVar8.a(u3);
        wVar8.s();
        wVar8.h();
        wVar8.t();
        DateTimeFormatter u4 = wVar8.u(e2, tVar);
        i = u4;
        w wVar9 = new w();
        wVar9.a(u4);
        wVar9.p();
        wVar9.e('[');
        wVar9.r();
        wVar9.n();
        wVar9.e(']');
        wVar9.u(e2, tVar);
        w wVar10 = new w();
        wVar10.a(u3);
        wVar10.p();
        wVar10.h();
        wVar10.p();
        wVar10.e('[');
        wVar10.r();
        wVar10.n();
        wVar10.e(']');
        wVar10.u(e2, tVar);
        w wVar11 = new w();
        wVar11.q();
        wVar11.k(aVar, 4, 10, f2);
        wVar11.e('-');
        wVar11.m(a.DAY_OF_YEAR, 3);
        wVar11.p();
        wVar11.h();
        wVar11.u(e2, tVar);
        w wVar12 = new w();
        wVar12.q();
        wVar12.k(i.c, 4, 10, f2);
        wVar12.f("-W");
        wVar12.m(i.b, 2);
        wVar12.e('-');
        a aVar7 = a.DAY_OF_WEEK;
        wVar12.m(aVar7, 1);
        wVar12.p();
        wVar12.h();
        wVar12.u(e2, tVar);
        w wVar13 = new w();
        wVar13.q();
        wVar13.c();
        j = wVar13.u(e2, (t) null);
        w wVar14 = new w();
        wVar14.q();
        wVar14.m(aVar, 4);
        wVar14.m(aVar2, 2);
        wVar14.m(aVar3, 2);
        wVar14.p();
        wVar14.s();
        wVar14.g("+HHMMss", "Z");
        wVar14.t();
        wVar14.u(e2, tVar);
        HashMap hashMap = new HashMap();
        hashMap.put(1L, "Mon");
        hashMap.put(2L, "Tue");
        hashMap.put(3L, "Wed");
        hashMap.put(4L, "Thu");
        hashMap.put(5L, "Fri");
        hashMap.put(6L, "Sat");
        t tVar2 = tVar;
        hashMap.put(7L, "Sun");
        HashMap hashMap2 = new HashMap();
        hashMap2.put(1L, "Jan");
        hashMap2.put(2L, "Feb");
        hashMap2.put(3L, "Mar");
        hashMap2.put(4L, "Apr");
        hashMap2.put(5L, "May");
        hashMap2.put(6L, "Jun");
        hashMap2.put(7L, "Jul");
        hashMap2.put(8L, "Aug");
        hashMap2.put(9L, "Sep");
        hashMap2.put(10L, "Oct");
        hashMap2.put(11L, "Nov");
        hashMap2.put(12L, "Dec");
        w wVar15 = new w();
        wVar15.q();
        wVar15.s();
        wVar15.p();
        wVar15.j(aVar7, hashMap);
        wVar15.f(", ");
        wVar15.o();
        wVar15.k(aVar3, 1, 2, F.NOT_NEGATIVE);
        wVar15.e(' ');
        wVar15.j(aVar2, hashMap2);
        wVar15.e(' ');
        wVar15.m(aVar, 4);
        wVar15.e(' ');
        wVar15.m(aVar4, 2);
        wVar15.e(':');
        wVar15.m(aVar5, 2);
        wVar15.p();
        wVar15.e(':');
        wVar15.m(aVar6, 2);
        wVar15.o();
        wVar15.e(' ');
        wVar15.g("+HHMM", "GMT");
        wVar15.u(E.SMART, tVar2);
    }

    DateTimeFormatter(C0053f fVar, Locale locale, E e2, t tVar) {
        C c2 = C.a;
        this.a = (C0053f) Objects.requireNonNull(fVar, "printerParser");
        this.b = (Locale) Objects.requireNonNull(locale, "locale");
        this.c = (C) Objects.requireNonNull(c2, "decimalStyle");
        this.d = (E) Objects.requireNonNull(e2, "resolverStyle");
        this.f = tVar;
        this.g = null;
    }

    private TemporalAccessor f(CharSequence charSequence) {
        String str;
        ParsePosition parsePosition = new ParsePosition(0);
        Objects.requireNonNull(charSequence, "text");
        Objects.requireNonNull(parsePosition, "position");
        x xVar = new x(this);
        int l = this.a.l(xVar, charSequence, parsePosition.getIndex());
        if (l < 0) {
            parsePosition.setErrorIndex(~l);
            xVar = null;
        } else {
            parsePosition.setIndex(l);
        }
        if (xVar != null && parsePosition.getErrorIndex() < 0 && parsePosition.getIndex() >= charSequence.length()) {
            return xVar.t(this.d, this.e);
        }
        if (charSequence.length() > 64) {
            str = charSequence.subSequence(0, 64).toString() + "...";
        } else {
            str = charSequence.toString();
        }
        if (parsePosition.getErrorIndex() >= 0) {
            String str2 = "Text '" + str + "' could not be parsed at index " + parsePosition.getErrorIndex();
            parsePosition.getErrorIndex();
            throw new DateTimeParseException(str2, charSequence);
        }
        String str3 = "Text '" + str + "' could not be parsed, unparsed text found at index " + parsePosition.getIndex();
        parsePosition.getIndex();
        throw new DateTimeParseException(str3, charSequence);
    }

    public static DateTimeFormatter ofPattern(String str) {
        w wVar = new w();
        wVar.i(str);
        return wVar.v(Locale.getDefault());
    }

    public static DateTimeFormatter ofPattern(String str, Locale locale) {
        w wVar = new w();
        wVar.i(str);
        return wVar.v(locale);
    }

    public final m a() {
        return this.f;
    }

    public final C b() {
        return this.c;
    }

    public final Locale c() {
        return this.b;
    }

    public final ZoneId d() {
        return this.g;
    }

    public final Object e(CharSequence charSequence, e eVar) {
        String str;
        Objects.requireNonNull(charSequence, "text");
        Objects.requireNonNull(eVar, "query");
        try {
            return ((D) f(charSequence)).H(eVar);
        } catch (DateTimeParseException e2) {
            throw e2;
        } catch (RuntimeException e3) {
            if (charSequence.length() > 64) {
                str = charSequence.subSequence(0, 64).toString() + "...";
            } else {
                str = charSequence.toString();
            }
            throw new DateTimeParseException("Text '" + str + "' could not be parsed: " + e3.getMessage(), charSequence, e3);
        }
    }

    public String format(TemporalAccessor temporalAccessor) {
        StringBuilder sb = new StringBuilder(32);
        Objects.requireNonNull(temporalAccessor, "temporal");
        Objects.requireNonNull(sb, "appendable");
        try {
            this.a.k(new z(temporalAccessor, this), sb);
            return sb.toString();
        } catch (IOException e2) {
            throw new DateTimeException(e2.getMessage(), e2);
        }
    }

    /* access modifiers changed from: package-private */
    public final C0053f g() {
        return this.a.a();
    }

    public final String toString() {
        String fVar = this.a.toString();
        return fVar.startsWith("[") ? fVar : fVar.substring(1, fVar.length() - 1);
    }
}
