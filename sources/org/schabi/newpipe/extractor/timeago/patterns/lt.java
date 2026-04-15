package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class lt extends PatternsHolder {
    public static final String[] j = {"sekundes", "sekundę", "sekundžių"};
    public static final String[] k = {"minutes", "minutę", "minučių"};
    public static final String[] l = {"valandas", "valandą", "valandų"};
    public static final String[] m = {"dienas", "dieną"};
    public static final String[] n = {"savaites", "savaitę"};
    public static final String[] o = {"mėnesius", "mėnesių", "mėnesį"};
    public static final String[] p = {"metus", "metų"};
    public static final lt q = new lt();

    public lt() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static lt getInstance() {
        return q;
    }
}
