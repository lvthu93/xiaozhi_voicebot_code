package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class fa extends PatternsHolder {
    public static final String[] j = {"ثانیه"};
    public static final String[] k = {"دقیقه"};
    public static final String[] l = {"ساعت"};
    public static final String[] m = {"روز"};
    public static final String[] n = {"هفته"};
    public static final String[] o = {"ماه"};
    public static final String[] p = {"سال"};
    public static final fa q = new fa();

    public fa() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static fa getInstance() {
        return q;
    }
}
