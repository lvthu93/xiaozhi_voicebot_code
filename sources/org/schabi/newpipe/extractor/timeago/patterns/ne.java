package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ne extends PatternsHolder {
    public static final String[] j = {"सेकेन्ड"};
    public static final String[] k = {"मिनेट"};
    public static final String[] l = {"घन्टा"};
    public static final String[] m = {"दिन"};
    public static final String[] n = {"हप्ता"};
    public static final String[] o = {"महिना"};
    public static final String[] p = {"वर्ष"};
    public static final ne q = new ne();

    public ne() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static ne getInstance() {
        return q;
    }
}
