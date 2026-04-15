package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ml extends PatternsHolder {
    public static final String[] j = {"സെക്കന്റ്", "സെക്കൻഡ്"};
    public static final String[] k = {"മിനിറ്റ്"};
    public static final String[] l = {"മണിക്കൂർ"};
    public static final String[] m = {"ദിവസം"};
    public static final String[] n = {"ആഴ്ച", "ആഴ്‌ച"};
    public static final String[] o = {"മാസം"};
    public static final String[] p = {"വർഷം"};
    public static final ml q = new ml();

    public ml() {
        super("", j, k, l, m, n, o, p);
    }

    public static ml getInstance() {
        return q;
    }
}
