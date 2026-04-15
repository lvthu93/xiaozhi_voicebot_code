package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ca extends PatternsHolder {
    public static final String[] j = {"segon", "segons"};
    public static final String[] k = {"minut", "minuts"};
    public static final String[] l = {"hora", "hores"};
    public static final String[] m = {"dia", "dies"};
    public static final String[] n = {"setmana", "setmanes"};
    public static final String[] o = {"mes", "mesos"};
    public static final String[] p = {"any", "anys"};
    public static final ca q = new ca();

    public ca() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static ca getInstance() {
        return q;
    }
}
