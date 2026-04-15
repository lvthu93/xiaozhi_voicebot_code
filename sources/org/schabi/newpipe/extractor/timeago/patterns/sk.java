package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class sk extends PatternsHolder {
    public static final String[] j = {"sekundami", "sekundou"};
    public static final String[] k = {"minútami", "minútou"};
    public static final String[] l = {"hodinami", "hodinou"};
    public static final String[] m = {"dňami", "dňom"};
    public static final String[] n = {"týždňami", "týždňom"};
    public static final String[] o = {"mesiacmi", "mesiacom"};
    public static final String[] p = {"rokmi", "rokom"};
    public static final sk q = new sk();

    public sk() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static sk getInstance() {
        return q;
    }
}
