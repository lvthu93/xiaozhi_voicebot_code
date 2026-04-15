package j$.time.format;

import j$.time.chrono.m;
import j$.time.chrono.t;
import j$.time.temporal.a;
import j$.time.temporal.q;
import j$.util.concurrent.ConcurrentHashMap;
import java.text.DateFormatSymbols;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

class B {
    private static final ConcurrentHashMap a = new ConcurrentHashMap(16, 0.75f, 2);
    /* access modifiers changed from: private */
    public static final Comparator b = new C0050c();
    private static final B c = new B();
    public static final /* synthetic */ int d = 0;

    B() {
    }

    private static Object b(q qVar, Locale locale) {
        Object obj;
        q qVar2 = qVar;
        Locale locale2 = locale;
        AbstractMap.SimpleImmutableEntry simpleImmutableEntry = new AbstractMap.SimpleImmutableEntry(qVar2, locale2);
        ConcurrentHashMap concurrentHashMap = a;
        Object obj2 = concurrentHashMap.get(simpleImmutableEntry);
        if (obj2 != null) {
            return obj2;
        }
        HashMap hashMap = new HashMap();
        int i = 0;
        if (qVar2 == a.ERA) {
            DateFormatSymbols instance = DateFormatSymbols.getInstance(locale);
            HashMap hashMap2 = new HashMap();
            HashMap hashMap3 = new HashMap();
            String[] eras = instance.getEras();
            while (i < eras.length) {
                if (!eras[i].isEmpty()) {
                    long j = (long) i;
                    hashMap2.put(Long.valueOf(j), eras[i]);
                    hashMap3.put(Long.valueOf(j), c(eras[i]));
                }
                i++;
            }
            if (!hashMap2.isEmpty()) {
                hashMap.put(G.FULL, hashMap2);
                hashMap.put(G.SHORT, hashMap2);
                hashMap.put(G.NARROW, hashMap3);
            }
            obj = new A(hashMap);
        } else if (qVar2 == a.MONTH_OF_YEAR) {
            DateFormatSymbols instance2 = DateFormatSymbols.getInstance(locale);
            j$.lang.a.g(hashMap, instance2, locale2);
            HashMap hashMap4 = new HashMap();
            HashMap hashMap5 = new HashMap();
            String[] months = instance2.getMonths();
            for (int i2 = 0; i2 < months.length; i2++) {
                if (!months[i2].isEmpty()) {
                    long j2 = ((long) i2) + 1;
                    hashMap4.put(Long.valueOf(j2), months[i2]);
                    hashMap5.put(Long.valueOf(j2), c(months[i2]));
                }
            }
            if (!hashMap4.isEmpty()) {
                hashMap.put(G.FULL, hashMap4);
                hashMap.put(G.NARROW, hashMap5);
            }
            HashMap hashMap6 = new HashMap();
            String[] shortMonths = instance2.getShortMonths();
            while (i < shortMonths.length) {
                if (!shortMonths[i].isEmpty()) {
                    hashMap6.put(Long.valueOf(((long) i) + 1), shortMonths[i]);
                }
                i++;
            }
            if (!hashMap6.isEmpty()) {
                hashMap.put(G.SHORT, hashMap6);
            }
            obj = new A(hashMap);
        } else if (qVar2 == a.DAY_OF_WEEK) {
            DateFormatSymbols instance3 = DateFormatSymbols.getInstance(locale);
            HashMap hashMap7 = new HashMap();
            String[] weekdays = instance3.getWeekdays();
            hashMap7.put(1L, weekdays[2]);
            hashMap7.put(2L, weekdays[3]);
            hashMap7.put(3L, weekdays[4]);
            hashMap7.put(4L, weekdays[5]);
            hashMap7.put(5L, weekdays[6]);
            hashMap7.put(6L, weekdays[7]);
            hashMap7.put(7L, weekdays[1]);
            hashMap.put(G.FULL, hashMap7);
            HashMap hashMap8 = new HashMap();
            hashMap8.put(1L, c(weekdays[2]));
            hashMap8.put(2L, c(weekdays[3]));
            hashMap8.put(3L, c(weekdays[4]));
            hashMap8.put(4L, c(weekdays[5]));
            hashMap8.put(5L, c(weekdays[6]));
            hashMap8.put(6L, c(weekdays[7]));
            hashMap8.put(7L, c(weekdays[1]));
            hashMap.put(G.NARROW, hashMap8);
            HashMap hashMap9 = new HashMap();
            String[] shortWeekdays = instance3.getShortWeekdays();
            hashMap9.put(1L, shortWeekdays[2]);
            hashMap9.put(2L, shortWeekdays[3]);
            hashMap9.put(3L, shortWeekdays[4]);
            hashMap9.put(4L, shortWeekdays[5]);
            hashMap9.put(5L, shortWeekdays[6]);
            hashMap9.put(6L, shortWeekdays[7]);
            hashMap9.put(7L, shortWeekdays[1]);
            hashMap.put(G.SHORT, hashMap9);
            obj = new A(hashMap);
        } else if (qVar2 == a.AMPM_OF_DAY) {
            DateFormatSymbols instance4 = DateFormatSymbols.getInstance(locale);
            HashMap hashMap10 = new HashMap();
            HashMap hashMap11 = new HashMap();
            String[] amPmStrings = instance4.getAmPmStrings();
            while (i < amPmStrings.length) {
                if (!amPmStrings[i].isEmpty()) {
                    long j3 = (long) i;
                    hashMap10.put(Long.valueOf(j3), amPmStrings[i]);
                    hashMap11.put(Long.valueOf(j3), c(amPmStrings[i]));
                }
                i++;
            }
            if (!hashMap10.isEmpty()) {
                hashMap.put(G.FULL, hashMap10);
                hashMap.put(G.SHORT, hashMap10);
                hashMap.put(G.NARROW, hashMap11);
            }
            obj = new A(hashMap);
        } else {
            obj = "";
        }
        concurrentHashMap.putIfAbsent(simpleImmutableEntry, obj);
        return concurrentHashMap.get(simpleImmutableEntry);
    }

    private static String c(String str) {
        return str.substring(0, Character.charCount(str.codePointAt(0)));
    }

    static B d() {
        return c;
    }

    public String e(m mVar, q qVar, long j, G g, Locale locale) {
        if (mVar == t.d || !(qVar instanceof a)) {
            return f(qVar, j, g, locale);
        }
        return null;
    }

    public String f(q qVar, long j, G g, Locale locale) {
        Object b2 = b(qVar, locale);
        if (b2 instanceof A) {
            return ((A) b2).a(j, g);
        }
        return null;
    }

    public Iterator g(m mVar, q qVar, G g, Locale locale) {
        if (mVar == t.d || !(qVar instanceof a)) {
            return h(qVar, g, locale);
        }
        return null;
    }

    public Iterator h(q qVar, G g, Locale locale) {
        Object b2 = b(qVar, locale);
        if (b2 instanceof A) {
            return ((A) b2).b(g);
        }
        return null;
    }
}
