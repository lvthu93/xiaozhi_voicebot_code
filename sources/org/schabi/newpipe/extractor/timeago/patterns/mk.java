package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class mk extends PatternsHolder {
    public static final String[] j = {"секунда", "секунди"};
    public static final String[] k = {"минута", "минути"};
    public static final String[] l = {"час", "часа"};
    public static final String[] m = {"ден", "дена"};
    public static final String[] n = {"недела", "недели"};
    public static final String[] o = {"месец", "месеци"};
    public static final String[] p = {"година", "години"};
    public static final mk q = new mk();

    public mk() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static mk getInstance() {
        return q;
    }
}
