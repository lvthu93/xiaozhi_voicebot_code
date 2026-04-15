package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class sq extends PatternsHolder {
    public static final String[] j = {"sekonda", "sekondë"};
    public static final String[] k = {"minuta", "minutë"};
    public static final String[] l = {"orë"};
    public static final String[] m = {"ditë"};
    public static final String[] n = {"javë"};
    public static final String[] o = {"muaj"};
    public static final String[] p = {"vit", "vjet"};
    public static final sq q = new sq();

    public sq() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static sq getInstance() {
        return q;
    }
}
