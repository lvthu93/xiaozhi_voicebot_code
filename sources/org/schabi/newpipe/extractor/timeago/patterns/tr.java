package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class tr extends PatternsHolder {
    public static final String[] j = {"saniye"};
    public static final String[] k = {"dakika"};
    public static final String[] l = {"saat"};
    public static final String[] m = {"gün"};
    public static final String[] n = {"hafta"};
    public static final String[] o = {"ay"};
    public static final String[] p = {"yıl"};
    public static final tr q = new tr();

    public tr() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static tr getInstance() {
        return q;
    }
}
