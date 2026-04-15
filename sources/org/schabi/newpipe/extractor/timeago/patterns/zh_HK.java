package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class zh_HK extends PatternsHolder {
    public static final String[] j = {"秒前"};
    public static final String[] k = {"分鐘前"};
    public static final String[] l = {"小時前"};
    public static final String[] m = {"天前"};
    public static final String[] n = {"週前"};
    public static final String[] o = {"個月前"};
    public static final String[] p = {"年前"};
    public static final zh_HK q = new zh_HK();

    public zh_HK() {
        super("", j, k, l, m, n, o, p);
    }

    public static zh_HK getInstance() {
        return q;
    }
}
