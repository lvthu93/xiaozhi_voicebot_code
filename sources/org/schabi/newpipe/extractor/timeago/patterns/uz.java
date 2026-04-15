package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class uz extends PatternsHolder {
    public static final String[] j = {"soniya"};
    public static final String[] k = {"daqiqa"};
    public static final String[] l = {"soat"};
    public static final String[] m = {"kun"};
    public static final String[] n = {"hafta"};
    public static final String[] o = {"oy"};
    public static final String[] p = {"yil"};
    public static final uz q = new uz();

    public uz() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static uz getInstance() {
        return q;
    }
}
