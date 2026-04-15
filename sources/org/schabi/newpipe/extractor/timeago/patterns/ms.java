package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ms extends PatternsHolder {
    public static final String[] j = {"saat"};
    public static final String[] k = {"minit"};
    public static final String[] l = {"jam"};
    public static final String[] m = {"hari"};
    public static final String[] n = {"minggu"};
    public static final String[] o = {"bulan"};
    public static final String[] p = {"tahun"};
    public static final ms q = new ms();

    public ms() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static ms getInstance() {
        return q;
    }
}
