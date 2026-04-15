package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class bn extends PatternsHolder {
    public static final String[] j = {"সেকেন্ড"};
    public static final String[] k = {"মিনিট"};
    public static final String[] l = {"ঘণ্টা"};
    public static final String[] m = {"দিন"};
    public static final String[] n = {"সপ্তাহ"};
    public static final String[] o = {"মাস"};
    public static final String[] p = {"বছর"};
    public static final bn q = new bn();

    public bn() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static bn getInstance() {
        return q;
    }
}
