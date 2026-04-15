package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class is extends PatternsHolder {
    public static final String[] j = {"sekúndu", "sekúndum", "second", "seconds"};
    public static final String[] k = {"mínútu", "mínútum", "minute", "minutes"};
    public static final String[] l = {"klukkustund", "klukkustundum", "hour", "hours"};
    public static final String[] m = {"degi", "dögum", "day", "days"};
    public static final String[] n = {"viku", "vikum", "week", "weeks"};
    public static final String[] o = {"mánuði", "mánuðum"};
    public static final String[] p = {"ári", "árum"};
    public static final is q = new is();

    public is() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static is getInstance() {
        return q;
    }
}
