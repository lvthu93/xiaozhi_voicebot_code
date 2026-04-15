package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class el extends PatternsHolder {
    public static final String[] j = {"δευτερόλεπτα", "δευτερόλεπτο"};
    public static final String[] k = {"λεπτά", "λεπτό"};
    public static final String[] l = {"ώρα", "ώρες"};
    public static final String[] m = {"ημέρα", "ημέρες"};
    public static final String[] n = {"εβδομάδα", "εβδομάδες"};
    public static final String[] o = {"μήνα", "μήνες"};
    public static final String[] p = {"χρόνια", "χρόνο"};
    public static final el q = new el();

    public el() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static el getInstance() {
        return q;
    }
}
