package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class hy extends PatternsHolder {
    public static final String[] j = {"վայրկյան"};
    public static final String[] k = {"րոպե"};
    public static final String[] l = {"ժամ"};
    public static final String[] m = {"օր"};
    public static final String[] n = {"շաբաթ"};
    public static final String[] o = {"ամիս"};
    public static final String[] p = {"տարի"};
    public static final hy q = new hy();

    public hy() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static hy getInstance() {
        return q;
    }
}
