package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class pl extends PatternsHolder {
    public static final String[] j = {"sekund", "sekundy", "sekundę"};
    public static final String[] k = {"minut", "minuty", "minutę"};
    public static final String[] l = {"godzin", "godziny", "godzinę"};
    public static final String[] m = {"dni", "dzień"};
    public static final String[] n = {"tydzień", "tygodnie"};
    public static final String[] o = {"miesiąc", "miesiące", "miesięcy"};
    public static final String[] p = {"lat", "lata", "rok"};
    public static final pl q = new pl();

    public pl() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static pl getInstance() {
        return q;
    }
}
