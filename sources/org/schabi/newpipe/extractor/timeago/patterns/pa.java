package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class pa extends PatternsHolder {
    public static final String[] j = {"ਸਕਿੰਟ"};
    public static final String[] k = {"ਮਿੰਟ"};
    public static final String[] l = {"ਘੰਟਾ", "ਘੰਟੇ"};
    public static final String[] m = {"ਦਿਨ"};
    public static final String[] n = {"ਹਫ਼ਤਾ", "ਹਫ਼ਤੇ"};
    public static final String[] o = {"ਮਹੀਨਾ", "ਮਹੀਨੇ"};
    public static final String[] p = {"ਸਾਲ"};
    public static final pa q = new pa();

    public pa() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static pa getInstance() {
        return q;
    }
}
