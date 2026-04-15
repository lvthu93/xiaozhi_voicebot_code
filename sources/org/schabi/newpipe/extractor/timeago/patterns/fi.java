package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class fi extends PatternsHolder {
    public static final String[] j = {"sekunti", "sekuntia"};
    public static final String[] k = {"minuutti", "minuuttia"};
    public static final String[] l = {"tunti", "tuntia"};
    public static final String[] m = {"päivä", "päivää"};
    public static final String[] n = {"viikko", "viikkoa"};
    public static final String[] o = {"kuukausi", "kuukautta"};
    public static final String[] p = {"vuosi", "vuotta"};
    public static final fi q = new fi();

    public fi() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static fi getInstance() {
        return q;
    }
}
