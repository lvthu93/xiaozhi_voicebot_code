package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ro extends PatternsHolder {
    public static final String[] j = {"secunde", "secundă"};
    public static final String[] k = {"minut", "minute"};
    public static final String[] l = {"ore", "oră"};
    public static final String[] m = {"zi", "zile"};
    public static final String[] n = {"săptămâni", "săptămână"};
    public static final String[] o = {"luni", "lună"};
    public static final String[] p = {"an", "ani"};
    public static final ro q = new ro();

    public ro() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static ro getInstance() {
        return q;
    }
}
