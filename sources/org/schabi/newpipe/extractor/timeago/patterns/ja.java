package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ja extends PatternsHolder {
    public static final String[] j = {"秒前"};
    public static final String[] k = {"分前"};
    public static final String[] l = {"時間前"};
    public static final String[] m = {"日前"};
    public static final String[] n = {"週間前"};
    public static final String[] o = {"か月前"};
    public static final String[] p = {"年前"};
    public static final ja q = new ja();

    public ja() {
        super("", j, k, l, m, n, o, p);
    }

    public static ja getInstance() {
        return q;
    }
}
