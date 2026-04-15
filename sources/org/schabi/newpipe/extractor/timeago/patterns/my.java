package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class my extends PatternsHolder {
    public static final String[] j = {"စက္ကန့်"};
    public static final String[] k = {"မိနစ်"};
    public static final String[] l = {"နာရီ"};
    public static final String[] m = {"ရက်"};
    public static final String[] n = {"ပတ်"};
    public static final String[] o = {"လ"};
    public static final String[] p = {"နှစ်"};
    public static final my q = new my();

    public my() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static my getInstance() {
        return q;
    }
}
