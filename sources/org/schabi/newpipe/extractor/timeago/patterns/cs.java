package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class cs extends PatternsHolder {
    public static final String[] j = {"sekundami", "sekundou"};
    public static final String[] k = {"minutami", "minutou"};
    public static final String[] l = {"hodinami", "hodinou"};
    public static final String[] m = {"dny", "včera"};
    public static final String[] n = {"týdnem", "týdny"};
    public static final String[] o = {"měsícem", "měsíci"};
    public static final String[] p = {"rokem", "roky", "lety"};
    public static final cs q = new cs();

    public cs() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static cs getInstance() {
        return q;
    }
}
