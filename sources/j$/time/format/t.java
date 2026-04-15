package j$.time.format;

import j$.time.d;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.q;
import j$.time.temporal.w;
import j$.util.Objects;
import java.util.Calendar;
import java.util.Locale;

final class t extends k {
    private char g;
    private int h;

    t(char c, int i, int i2, int i3) {
        this(c, i, i2, i3, 0);
    }

    t(char c, int i, int i2, int i3, int i4) {
        super((q) null, i2, i3, F.NOT_NEGATIVE, i4);
        this.g = c;
        this.h = i;
    }

    private k g(Locale locale) {
        q qVar;
        TemporalUnit temporalUnit = w.h;
        Objects.requireNonNull(locale, "locale");
        Calendar instance = Calendar.getInstance(new Locale(locale.getLanguage(), locale.getCountry()));
        w g2 = w.g(d.SUNDAY.R((long) (instance.getFirstDayOfWeek() - 1)), instance.getMinimalDaysInFirstWeek());
        char c = this.g;
        if (c == 'W') {
            qVar = g2.i();
        } else if (c == 'Y') {
            q h2 = g2.h();
            int i = this.h;
            if (i == 2) {
                return new q(h2, q.i, this.e);
            }
            return new k(h2, i, 19, i < 4 ? F.NORMAL : F.EXCEEDS_PAD, this.e);
        } else if (c == 'c' || c == 'e') {
            qVar = g2.d();
        } else if (c == 'w') {
            qVar = g2.j();
        } else {
            throw new IllegalStateException("unreachable");
        }
        return new k(qVar, this.b, this.c, F.NOT_NEGATIVE, this.e);
    }

    /* access modifiers changed from: package-private */
    public final k e() {
        return this.e == -1 ? this : new t(this.g, this.h, this.b, this.c, -1);
    }

    /* access modifiers changed from: package-private */
    public final k f(int i) {
        return new t(this.g, this.h, this.b, this.c, this.e + i);
    }

    public final boolean k(z zVar, StringBuilder sb) {
        return g(zVar.c()).k(zVar, sb);
    }

    public final int l(x xVar, CharSequence charSequence, int i) {
        return g(xVar.i()).l(xVar, charSequence, i);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(30);
        sb.append("Localized(");
        char c = this.g;
        if (c == 'Y') {
            int i = this.h;
            if (i == 1) {
                sb.append("WeekBasedYear");
            } else if (i == 2) {
                sb.append("ReducedValue(WeekBasedYear,2,2,2000-01-01)");
            } else {
                sb.append("WeekBasedYear,");
                sb.append(this.h);
                sb.append(",19,");
                sb.append(this.h < 4 ? F.NORMAL : F.EXCEEDS_PAD);
            }
        } else {
            if (c == 'W') {
                sb.append("WeekOfMonth");
            } else if (c == 'c' || c == 'e') {
                sb.append("DayOfWeek");
            } else if (c == 'w') {
                sb.append("WeekOfWeekBasedYear");
            }
            sb.append(",");
            sb.append(this.h);
        }
        sb.append(")");
        return sb.toString();
    }
}
