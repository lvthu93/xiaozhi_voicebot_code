package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ka extends PatternsHolder {
    public static final String[] j = {"წამის"};
    public static final String[] k = {"წუთის"};
    public static final String[] l = {"საათის"};
    public static final String[] m = {"დღის"};
    public static final String[] n = {"კვირის"};
    public static final String[] o = {"თვის"};
    public static final String[] p = {"წლის"};
    public static final ka q = new ka();

    public ka() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static ka getInstance() {
        return q;
    }
}
