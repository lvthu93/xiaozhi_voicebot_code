package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class et extends PatternsHolder {
    public static final String[] j = {"sekund", "sekundit"};
    public static final String[] k = {"minut", "minutit"};
    public static final String[] l = {"tund", "tundi"};
    public static final String[] m = {"päev", "päeva"};
    public static final String[] n = {"nädal", "nädalat"};
    public static final String[] o = {"kuu", "kuud"};
    public static final String[] p = {"aasta", "aastat"};
    public static final et q = new et();

    public et() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static et getInstance() {
        return q;
    }
}
