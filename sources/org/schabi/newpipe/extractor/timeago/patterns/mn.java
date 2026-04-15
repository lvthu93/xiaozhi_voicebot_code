package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class mn extends PatternsHolder {
    public static final String[] j = {"секундын"};
    public static final String[] k = {"минутын"};
    public static final String[] l = {"цагийн"};
    public static final String[] m = {"өдрийн"};
    public static final String[] n = {"долоо", "хоногийн"};
    public static final String[] o = {"сарын"};
    public static final String[] p = {"жилийн"};
    public static final mn q = new mn();

    public mn() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static mn getInstance() {
        return q;
    }
}
