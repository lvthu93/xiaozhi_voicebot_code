package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class nl extends PatternsHolder {
    public static final String[] j = {"seconde", "seconden"};
    public static final String[] k = {"minuten", "minuut"};
    public static final String[] l = {"uur"};
    public static final String[] m = {"dag", "dagen"};
    public static final String[] n = {"week", "weken"};
    public static final String[] o = {"maand", "maanden"};
    public static final String[] p = {"jaar"};
    public static final nl q = new nl();

    public nl() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static nl getInstance() {
        return q;
    }
}
