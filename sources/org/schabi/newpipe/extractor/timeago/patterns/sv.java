package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class sv extends PatternsHolder {
    public static final String[] j = {"sekund", "sekunder"};
    public static final String[] k = {"minut", "minuter"};
    public static final String[] l = {"timmar", "timme"};
    public static final String[] m = {"dag", "dagar"};
    public static final String[] n = {"vecka", "veckor"};
    public static final String[] o = {"månad", "månader"};
    public static final String[] p = {"år"};
    public static final sv q = new sv();

    public sv() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static sv getInstance() {
        return q;
    }
}
