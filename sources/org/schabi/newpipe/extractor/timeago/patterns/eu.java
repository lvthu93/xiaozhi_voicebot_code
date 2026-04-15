package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class eu extends PatternsHolder {
    public static final String[] j = {"segundo"};
    public static final String[] k = {"minutu"};
    public static final String[] l = {"ordu", "ordubete"};
    public static final String[] m = {"egun"};
    public static final String[] n = {"aste", "astebete"};
    public static final String[] o = {"hilabete"};
    public static final String[] p = {"urte", "urtebete"};
    public static final eu q = new eu();

    public eu() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static eu getInstance() {
        return q;
    }
}
