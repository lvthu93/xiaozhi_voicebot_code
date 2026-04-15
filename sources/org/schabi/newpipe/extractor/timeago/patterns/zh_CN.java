package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class zh_CN extends PatternsHolder {
    public static final String[] j = {"秒前"};
    public static final String[] k = {"分钟前"};
    public static final String[] l = {"小时前"};
    public static final String[] m = {"天前"};
    public static final String[] n = {"周前"};
    public static final String[] o = {"个月前"};
    public static final String[] p = {"年前"};
    public static final zh_CN q = new zh_CN();

    public zh_CN() {
        super("", j, k, l, m, n, o, p);
    }

    public static zh_CN getInstance() {
        return q;
    }
}
