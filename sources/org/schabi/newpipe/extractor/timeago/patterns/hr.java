package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class hr extends PatternsHolder {
    public static final String[] j = {"sekunde", "sekundi", "sekundu"};
    public static final String[] k = {"minuta", "minute", "minutu"};
    public static final String[] l = {"sat", "sata", "sati"};
    public static final String[] m = {"dan", "dana"};
    public static final String[] n = {"tjedan", "tjedna"};
    public static final String[] o = {"mjesec", "mjeseca", "mjeseci"};
    public static final String[] p = {"godina", "godine", "godinu"};
    public static final hr q = new hr();

    public hr() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static hr getInstance() {
        return q;
    }
}
