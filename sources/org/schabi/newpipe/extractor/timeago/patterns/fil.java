package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class fil extends PatternsHolder {
    public static final String[] j = {"segundo"};
    public static final String[] k = {"minuto"};
    public static final String[] l = {"oras"};
    public static final String[] m = {"araw"};
    public static final String[] n = {"linggo"};
    public static final String[] o = {"buwan"};
    public static final String[] p = {"taon"};
    public static final fil q = new fil();

    public fil() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static fil getInstance() {
        return q;
    }
}
