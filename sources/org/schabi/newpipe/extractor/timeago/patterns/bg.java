package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class bg extends PatternsHolder {
    public static final String[] j = {"секунда", "секунди"};
    public static final String[] k = {"минута", "минути"};
    public static final String[] l = {"час", "часа"};
    public static final String[] m = {"ден", "дни"};
    public static final String[] n = {"седмица", "седмици"};
    public static final String[] o = {"месец", "месеца"};
    public static final String[] p = {"година", "години"};
    public static final bg q = new bg();

    public bg() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static bg getInstance() {
        return q;
    }
}
