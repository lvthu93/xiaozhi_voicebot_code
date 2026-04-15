package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class zu extends PatternsHolder {
    public static final String[] j = {"amasekhondi", "isekhondi"};
    public static final String[] k = {"amaminithi", "iminithi"};
    public static final String[] l = {"amahora", "ihora"};
    public static final String[] m = {"izinsuku", "usuku"};
    public static final String[] n = {"amaviki", "iviki"};
    public static final String[] o = {"inyanga", "izinyanga"};
    public static final String[] p = {"iminyaka", "unyaka"};
    public static final zu q = new zu();

    public zu() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static zu getInstance() {
        return q;
    }
}
