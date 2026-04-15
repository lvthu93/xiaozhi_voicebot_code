package org.schabi.newpipe.extractor.timeago;

import j$.time.temporal.ChronoUnit;
import j$.util.Map;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class PatternsHolder {
    public final String a;
    public final Collection<String> b;
    public final Collection<String> c;
    public final Collection<String> d;
    public final Collection<String> e;
    public final Collection<String> f;
    public final Collection<String> g;
    public final Collection<String> h;
    public final EnumMap i;

    public PatternsHolder() {
        throw null;
    }

    public PatternsHolder(String str, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4, String[] strArr5, String[] strArr6, String[] strArr7) {
        List asList = Arrays.asList(strArr);
        List asList2 = Arrays.asList(strArr2);
        List asList3 = Arrays.asList(strArr3);
        List asList4 = Arrays.asList(strArr4);
        List asList5 = Arrays.asList(strArr5);
        List asList6 = Arrays.asList(strArr6);
        List asList7 = Arrays.asList(strArr7);
        this.i = new EnumMap(ChronoUnit.class);
        this.a = str;
        this.b = asList;
        this.c = asList2;
        this.d = asList3;
        this.e = asList4;
        this.f = asList5;
        this.g = asList6;
        this.h = asList7;
    }

    public final void a(ChronoUnit chronoUnit, String str) {
        ((Map) Map.EL.computeIfAbsent(this.i, chronoUnit, new ug(1))).put(str, 2);
    }

    public java.util.Map<ChronoUnit, Collection<String>> asMap() {
        EnumMap enumMap = new EnumMap(ChronoUnit.class);
        enumMap.put(ChronoUnit.SECONDS, seconds());
        enumMap.put(ChronoUnit.MINUTES, minutes());
        enumMap.put(ChronoUnit.HOURS, hours());
        enumMap.put(ChronoUnit.DAYS, days());
        enumMap.put(ChronoUnit.WEEKS, weeks());
        enumMap.put(ChronoUnit.MONTHS, months());
        enumMap.put(ChronoUnit.YEARS, years());
        return enumMap;
    }

    public Collection<String> days() {
        return this.e;
    }

    public Collection<String> hours() {
        return this.d;
    }

    public Collection<String> minutes() {
        return this.c;
    }

    public Collection<String> months() {
        return this.g;
    }

    public Collection<String> seconds() {
        return this.b;
    }

    public java.util.Map<ChronoUnit, java.util.Map<String, Integer>> specialCases() {
        return this.i;
    }

    public Collection<String> weeks() {
        return this.f;
    }

    public String wordSeparator() {
        return this.a;
    }

    public Collection<String> years() {
        return this.h;
    }
}
