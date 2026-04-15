package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class id extends PatternsHolder {
    public static final String[] j = {"detik"};
    public static final String[] k = {"menit"};
    public static final String[] l = {"jam"};
    public static final String[] m = {"hari"};
    public static final String[] n = {"minggu"};
    public static final String[] o = {"bulan"};
    public static final String[] p = {"tahun"};
    public static final id q = new id();

    public id() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static id getInstance() {
        return q;
    }
}
