package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class am extends PatternsHolder {
    public static final String[] j = {"ሰኮንዶች", "ሴኮንድ"};
    public static final String[] k = {"ደቂቃ", "ደቂቃዎች"};
    public static final String[] l = {"ሰዓት", "ሰዓቶች"};
    public static final String[] m = {"ቀን", "ቀኖች"};
    public static final String[] n = {"ሳምንታት", "ሳምንት"};
    public static final String[] o = {"ወራት", "ወር"};
    public static final String[] p = {"ዓመታት", "ዓመት"};
    public static final am q = new am();

    public am() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static am getInstance() {
        return q;
    }
}
