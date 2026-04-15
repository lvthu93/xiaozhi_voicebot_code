package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class en_GB extends PatternsHolder {
    public static final String[] j = {"second", "seconds", "sec"};
    public static final String[] k = {"minute", "minutes", "min"};
    public static final String[] l = {"hour", "hours", "hr"};
    public static final String[] m = {"day", "days"};
    public static final String[] n = {"week", "weeks", "wk"};
    public static final String[] o = {"month", "months", "mo"};
    public static final String[] p = {"year", "years", "yr"};
    public static final en_GB q = new en_GB();

    public en_GB() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static en_GB getInstance() {
        return q;
    }
}
