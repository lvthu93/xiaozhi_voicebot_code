package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class uk extends PatternsHolder {
    public static final String[] j = {"секунд", "секунди", "секунду"};
    public static final String[] k = {"хвилин", "хвилини", "хвилину"};
    public static final String[] l = {"годин", "години", "годину"};
    public static final String[] m = {"день", "дні", "днів"};
    public static final String[] n = {"тиждень", "тижні"};
    public static final String[] o = {"місяць", "місяці", "місяців"};
    public static final String[] p = {"роки", "років", "рік"};
    public static final uk q = new uk();

    public uk() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static uk getInstance() {
        return q;
    }
}
