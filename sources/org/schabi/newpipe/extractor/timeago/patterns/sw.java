package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class sw extends PatternsHolder {
    public static final String[] j = {"sekunde"};
    public static final String[] k = {"dakika"};
    public static final String[] l = {"saa"};
    public static final String[] m = {"siku"};
    public static final String[] n = {"wiki"};
    public static final String[] o = {"Mwezi", "miezi"};
    public static final String[] p = {"Miaka", "Mwaka"};
    public static final sw q = new sw();

    public sw() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static sw getInstance() {
        return q;
    }
}
