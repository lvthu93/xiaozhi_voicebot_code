package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class si extends PatternsHolder {
    public static final String[] j = {"තත්පර"};
    public static final String[] k = {"මිනිත්තු"};
    public static final String[] l = {"පැය"};
    public static final String[] m = {"දින"};
    public static final String[] n = {"සති"};
    public static final String[] o = {"මාස"};
    public static final String[] p = {"වසර"};
    public static final si q = new si();

    public si() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static si getInstance() {
        return q;
    }
}
