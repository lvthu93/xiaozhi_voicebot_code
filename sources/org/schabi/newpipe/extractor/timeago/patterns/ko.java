package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ko extends PatternsHolder {
    public static final String[] j = {"초"};
    public static final String[] k = {"분"};
    public static final String[] l = {"시간"};
    public static final String[] m = {"일"};
    public static final String[] n = {"주"};
    public static final String[] o = {"개월"};
    public static final String[] p = {"년"};
    public static final ko q = new ko();

    public ko() {
        super("", j, k, l, m, n, o, p);
    }

    public static ko getInstance() {
        return q;
    }
}
