package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class az extends PatternsHolder {
    public static final String[] j = {"saniyə"};
    public static final String[] k = {"dəqiqə"};
    public static final String[] l = {"saat"};
    public static final String[] m = {"gün"};
    public static final String[] n = {"həftə"};
    public static final String[] o = {"ay"};
    public static final String[] p = {"il"};
    public static final az q = new az();

    public az() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static az getInstance() {
        return q;
    }
}
