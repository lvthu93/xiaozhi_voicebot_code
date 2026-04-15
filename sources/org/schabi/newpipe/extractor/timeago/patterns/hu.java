package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class hu extends PatternsHolder {
    public static final String[] j = {"másodperce"};
    public static final String[] k = {"perce"};
    public static final String[] l = {"órája"};
    public static final String[] m = {"napja"};
    public static final String[] n = {"hete"};
    public static final String[] o = {"hónapja"};
    public static final String[] p = {"éve"};
    public static final hu q = new hu();

    public hu() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static hu getInstance() {
        return q;
    }
}
