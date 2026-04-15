package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class af extends PatternsHolder {
    public static final String[] j = {"sekonde", "sekondes"};
    public static final String[] k = {"minute", "minuut"};
    public static final String[] l = {"ure", "uur"};
    public static final String[] m = {"dae", "dag"};
    public static final String[] n = {"week", "weke"};
    public static final String[] o = {"maand", "maande"};
    public static final String[] p = {"jaar"};
    public static final af q = new af();

    public af() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static af getInstance() {
        return q;
    }
}
