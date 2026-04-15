package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ky extends PatternsHolder {
    public static final String[] j = {"секунд"};
    public static final String[] k = {"мүнөт"};
    public static final String[] l = {"саат"};
    public static final String[] m = {"күн"};
    public static final String[] n = {"апта"};
    public static final String[] o = {"ай"};
    public static final String[] p = {"жыл"};
    public static final ky q = new ky();

    public ky() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static ky getInstance() {
        return q;
    }
}
