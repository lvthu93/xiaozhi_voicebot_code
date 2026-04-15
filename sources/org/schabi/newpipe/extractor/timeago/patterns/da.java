package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class da extends PatternsHolder {
    public static final String[] j = {"sekund", "sekunder"};
    public static final String[] k = {"minut", "minutter"};
    public static final String[] l = {"time", "timer"};
    public static final String[] m = {"dag", "dage"};
    public static final String[] n = {"uge", "uger"};
    public static final String[] o = {"måned", "måneder"};
    public static final String[] p = {"år"};
    public static final da q = new da();

    public da() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static da getInstance() {
        return q;
    }
}
