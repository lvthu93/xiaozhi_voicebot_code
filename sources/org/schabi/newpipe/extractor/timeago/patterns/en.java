package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class en extends PatternsHolder {
    public static final String[] j = {"second", "seconds", "sec"};
    public static final String[] k = {"minute", "minutes", "min"};
    public static final String[] l = {"hour", "hours", "h"};
    public static final String[] m = {"day", "days", "d"};
    public static final String[] n = {"week", "weeks", "w"};
    public static final String[] o = {"month", "months", "mo"};
    public static final String[] p = {"year", "years", "y"};
    public static final en q = new en();

    public en() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static en getInstance() {
        return q;
    }
}
