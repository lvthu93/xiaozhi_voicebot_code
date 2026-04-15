package j$.lang;

import j$.time.format.G;
import j$.util.Objects;
import j$.util.concurrent.u;
import j$.util.function.b;
import j$.util.function.d;
import j$.util.function.f;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.BiConsumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public abstract /* synthetic */ class a {
    public static u a(BiConsumer biConsumer, BiConsumer biConsumer2) {
        Objects.requireNonNull(biConsumer2);
        return new u(1, biConsumer, biConsumer2);
    }

    public static b b(DoubleConsumer doubleConsumer, DoubleConsumer doubleConsumer2) {
        Objects.requireNonNull(doubleConsumer2);
        return new b(doubleConsumer, doubleConsumer2);
    }

    public static d c(IntConsumer intConsumer, IntConsumer intConsumer2) {
        Objects.requireNonNull(intConsumer2);
        return new d(intConsumer, intConsumer2);
    }

    public static f d(LongConsumer longConsumer, LongConsumer longConsumer2) {
        Objects.requireNonNull(longConsumer2);
        return new f(longConsumer, longConsumer2);
    }

    public static /* synthetic */ void e(BiConsumer biConsumer, BiConsumer biConsumer2, Object obj, Object obj2) {
        biConsumer.accept(obj, obj2);
        biConsumer2.accept(obj, obj2);
    }

    private static String f(int i, String str, Locale locale) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, locale);
        simpleDateFormat.setTimeZone(timeZone);
        Calendar instance = Calendar.getInstance();
        instance.set(0, i, 0);
        return simpleDateFormat.format(instance.getTime());
    }

    public static void g(HashMap hashMap, DateFormatSymbols dateFormatSymbols, Locale locale) {
        int length = dateFormatSymbols.getMonths().length;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (int i = 1; i < length; i++) {
            String f = f(i, "LLLL", locale);
            long j = (long) i;
            linkedHashMap.put(Long.valueOf(j), f);
            linkedHashMap2.put(Long.valueOf(j), f.substring(0, Character.charCount(f.codePointAt(0))));
            linkedHashMap3.put(Long.valueOf(j), f(i, "LLL", locale));
        }
        if (length > 0) {
            long j2 = (long) length;
            linkedHashMap.put(Long.valueOf(j2), "");
            linkedHashMap2.put(Long.valueOf(j2), "");
            linkedHashMap3.put(Long.valueOf(j2), "");
            hashMap.put(G.FULL_STANDALONE, linkedHashMap);
            hashMap.put(G.NARROW_STANDALONE, linkedHashMap2);
            hashMap.put(G.SHORT_STANDALONE, linkedHashMap3);
        }
    }

    public static /* synthetic */ int h(long j) {
        int i = (int) j;
        if (j == ((long) i)) {
            return i;
        }
        throw new ArithmeticException();
    }

    public static /* synthetic */ long i(long j, long j2) {
        long j3 = j / j2;
        return (j - (j2 * j3) != 0 && (((j ^ j2) >> 63) | 1) < 0) ? j3 - 1 : j3;
    }

    public static /* synthetic */ AbstractMap.SimpleImmutableEntry j(String str, String str2) {
        return new AbstractMap.SimpleImmutableEntry(Objects.requireNonNull(str), Objects.requireNonNull(str2));
    }

    public static /* synthetic */ long k(long j, long j2) {
        long j3 = j % j2;
        if (j3 == 0) {
            return 0;
        }
        return (((j ^ j2) >> 63) | 1) > 0 ? j3 : j3 + j2;
    }

    public static /* synthetic */ long l(long j, long j2) {
        long j3 = j + j2;
        boolean z = true;
        boolean z2 = (j2 ^ j) < 0;
        if ((j ^ j3) < 0) {
            z = false;
        }
        if (z2 || z) {
            return j3;
        }
        throw new ArithmeticException();
    }

    public static /* synthetic */ long m(long j, long j2) {
        int numberOfLeadingZeros = Long.numberOfLeadingZeros(~j2) + Long.numberOfLeadingZeros(j2) + Long.numberOfLeadingZeros(~j) + Long.numberOfLeadingZeros(j);
        if (numberOfLeadingZeros > 65) {
            return j * j2;
        }
        if (numberOfLeadingZeros >= 64) {
            boolean z = true;
            int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
            boolean z2 = i >= 0;
            if (j2 == Long.MIN_VALUE) {
                z = false;
            }
            if (z2 || z) {
                long j3 = j * j2;
                if (i == 0 || j3 / j == j2) {
                    return j3;
                }
            }
        }
        throw new ArithmeticException();
    }

    public static /* synthetic */ long n(long j, long j2) {
        long j3 = j - j2;
        boolean z = true;
        boolean z2 = (j2 ^ j) >= 0;
        if ((j ^ j3) < 0) {
            z = false;
        }
        if (z2 || z) {
            return j3;
        }
        throw new ArithmeticException();
    }
}
