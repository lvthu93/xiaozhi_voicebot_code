package j$.time.format;

import j$.time.LocalDateTime;
import j$.time.ZoneOffset;
import j$.time.chrono.C0038b;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import java.util.Locale;

final class i implements C0054g {
    i() {
    }

    public final boolean k(z zVar, StringBuilder sb) {
        StringBuilder sb2 = sb;
        Long e = zVar.e(a.INSTANT_SECONDS);
        TemporalAccessor d = zVar.d();
        a aVar = a.NANO_OF_SECOND;
        Long valueOf = d.e(aVar) ? Long.valueOf(zVar.d().E(aVar)) : null;
        int i = 0;
        if (e == null) {
            return false;
        }
        long longValue = e.longValue();
        int Q = aVar.Q(valueOf != null ? valueOf.longValue() : 0);
        if (longValue >= -62167219200L) {
            long j = (longValue - 315569520000L) + 62167219200L;
            long i2 = j$.lang.a.i(j, 315569520000L) + 1;
            LocalDateTime a0 = LocalDateTime.a0(j$.lang.a.k(j, 315569520000L) - 62167219200L, 0, ZoneOffset.UTC);
            if (i2 > 0) {
                sb2.append('+');
                sb2.append(i2);
            }
            sb2.append(a0);
            if (a0.T() == 0) {
                sb2.append(":00");
            }
        } else {
            long j2 = longValue + 62167219200L;
            long j3 = j2 / 315569520000L;
            long j4 = j2 % 315569520000L;
            LocalDateTime a02 = LocalDateTime.a0(j4 - 62167219200L, 0, ZoneOffset.UTC);
            int length = sb.length();
            sb2.append(a02);
            if (a02.T() == 0) {
                sb2.append(":00");
            }
            if (j3 < 0) {
                if (a02.U() == -10000) {
                    sb2.replace(length, length + 2, Long.toString(j3 - 1));
                } else if (j4 == 0) {
                    sb2.insert(length, j3);
                } else {
                    sb2.insert(length + 1, Math.abs(j3));
                }
            }
        }
        if (Q > 0) {
            sb2.append('.');
            int i3 = 100000000;
            while (true) {
                if (Q <= 0 && i % 3 == 0 && i >= -2) {
                    break;
                }
                int i4 = Q / i3;
                sb2.append((char) (i4 + 48));
                Q -= i4 * i3;
                i3 /= 10;
                i++;
            }
        }
        sb2.append('Z');
        return true;
    }

    public final int l(x xVar, CharSequence charSequence, int i) {
        int i2;
        int i3;
        int i4 = i;
        w wVar = new w();
        wVar.a(DateTimeFormatter.h);
        wVar.e('T');
        a aVar = a.HOUR_OF_DAY;
        wVar.m(aVar, 2);
        wVar.e(':');
        a aVar2 = a.MINUTE_OF_HOUR;
        wVar.m(aVar2, 2);
        wVar.e(':');
        a aVar3 = a.SECOND_OF_MINUTE;
        wVar.m(aVar3, 2);
        a aVar4 = a.NANO_OF_SECOND;
        int i5 = 0;
        wVar.b(aVar4, 0, 9, true);
        wVar.e('Z');
        C0053f g = wVar.v(Locale.getDefault()).g();
        x d = xVar.d();
        int l = g.l(d, charSequence, i4);
        if (l < 0) {
            return l;
        }
        long longValue = d.j(a.YEAR).longValue();
        int intValue = d.j(a.MONTH_OF_YEAR).intValue();
        int intValue2 = d.j(a.DAY_OF_MONTH).intValue();
        int intValue3 = d.j(aVar).intValue();
        int intValue4 = d.j(aVar2).intValue();
        Long j = d.j(aVar3);
        Long j2 = d.j(aVar4);
        int intValue5 = j != null ? j.intValue() : 0;
        int intValue6 = j2 != null ? j2.intValue() : 0;
        if (intValue3 == 24 && intValue4 == 0 && intValue5 == 0 && intValue6 == 0) {
            i2 = intValue5;
            i5 = 1;
            i3 = 0;
        } else if (intValue3 == 23 && intValue4 == 59 && intValue5 == 60) {
            xVar.p();
            i3 = intValue3;
            i2 = 59;
        } else {
            i3 = intValue3;
            i2 = intValue5;
        }
        try {
            x xVar2 = xVar;
            int i6 = i;
            return xVar2.o(aVar4, (long) intValue6, i6, xVar2.o(a.INSTANT_SECONDS, j$.lang.a.m(longValue / 10000, 315569520000L) + C0038b.p(LocalDateTime.Y(((int) longValue) % 10000, intValue, intValue2, i3, intValue4, i2).c0((long) i5), ZoneOffset.UTC), i6, l));
        } catch (RuntimeException unused) {
            return ~i4;
        }
    }

    public final String toString() {
        return "Instant()";
    }
}
