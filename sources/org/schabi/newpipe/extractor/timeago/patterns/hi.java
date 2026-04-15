package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class hi extends PatternsHolder {
    public static final String[] j = {"सेकंड"};
    public static final String[] k = {"मिनट"};
    public static final String[] l = {"घंटा", "घंटे"};
    public static final String[] m = {"दिन"};
    public static final String[] n = {"सप्ताह", "हफ़्ते"};
    public static final String[] o = {"महीना", "महीने"};
    public static final String[] p = {"वर्ष"};
    public static final hi q = new hi();

    public hi() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static hi getInstance() {
        return q;
    }
}
