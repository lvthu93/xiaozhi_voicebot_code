package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class gu extends PatternsHolder {
    public static final String[] j = {"સેકંડ"};
    public static final String[] k = {"મિનિટ"};
    public static final String[] l = {"કલાક"};
    public static final String[] m = {"દિવસ"};
    public static final String[] n = {"અઠવાડિયા"};
    public static final String[] o = {"મહિના"};
    public static final String[] p = {"વર્ષ"};
    public static final gu q = new gu();

    public gu() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static gu getInstance() {
        return q;
    }
}
