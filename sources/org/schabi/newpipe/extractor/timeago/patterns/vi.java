package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class vi extends PatternsHolder {
    public static final String[] j = {"giây"};
    public static final String[] k = {"phút"};
    public static final String[] l = {"giờ", "tiếng"};
    public static final String[] m = {"ngày"};
    public static final String[] n = {"tuần"};
    public static final String[] o = {"tháng"};
    public static final String[] p = {"năm"};
    public static final vi q = new vi();

    public vi() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static vi getInstance() {
        return q;
    }
}
