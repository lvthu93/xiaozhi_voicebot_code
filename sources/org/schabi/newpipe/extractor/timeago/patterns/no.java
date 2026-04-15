package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class no extends PatternsHolder {
    public static final String[] j = {"sekund", "sekunder"};
    public static final String[] k = {"minutt", "minutter"};
    public static final String[] l = {"time", "timer"};
    public static final String[] m = {"dag", "dager"};
    public static final String[] n = {"uke", "uker"};
    public static final String[] o = {"md."};
    public static final String[] p = {"år"};
    public static final no q = new no();

    public no() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static no getInstance() {
        return q;
    }
}
