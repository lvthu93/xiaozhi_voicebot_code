package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class fr_CA extends PatternsHolder {
    public static final String[] j = {"seconde", "secondes"};
    public static final String[] k = {"minute", "minutes"};
    public static final String[] l = {"heures", "heure"};
    public static final String[] m = {"jour", "jours"};
    public static final String[] n = {"semaine", "semaines"};
    public static final String[] o = {"mois"};
    public static final String[] p = {"an", "ans"};
    public static final fr_CA q = new fr_CA();

    public fr_CA() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static fr_CA getInstance() {
        return q;
    }
}
