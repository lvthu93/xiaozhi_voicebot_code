package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class kk extends PatternsHolder {
    public static final String[] j = {"секунд"};
    public static final String[] k = {"минут"};
    public static final String[] l = {"сағат"};
    public static final String[] m = {"күн"};
    public static final String[] n = {"апта"};
    public static final String[] o = {"ай"};
    public static final String[] p = {"жыл"};
    public static final kk q = new kk();

    public kk() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static kk getInstance() {
        return q;
    }
}
