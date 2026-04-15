package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class bs extends PatternsHolder {
    public static final String[] j = {"sekundi", "sekunde", "sekundu"};
    public static final String[] k = {"minuta", "minute", "minutu"};
    public static final String[] l = {"h", "sat", "sata", "sati"};
    public static final String[] m = {"dan", "dana"};
    public static final String[] n = {"sedm."};
    public static final String[] o = {"mj.", "mjesec", "mjeseca", "mjeseci"};
    public static final String[] p = {"godina", "godine", "godinu"};
    public static final bs q = new bs();

    public bs() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static bs getInstance() {
        return q;
    }
}
