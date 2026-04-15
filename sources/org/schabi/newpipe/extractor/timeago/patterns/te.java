package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class te extends PatternsHolder {
    public static final String[] j = {"సెకను", "సెకన్ల"};
    public static final String[] k = {"నిమిషం", "నిమిషాల"};
    public static final String[] l = {"గంట", "గంటల"};
    public static final String[] m = {"రోజు", "రోజుల"};
    public static final String[] n = {"వారం", "వారాల"};
    public static final String[] o = {"నెల", "నెలల"};
    public static final String[] p = {"సంవత్సరం", "సంవత్సరాల"};
    public static final te q = new te();

    public te() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static te getInstance() {
        return q;
    }
}
