package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class de extends PatternsHolder {
    public static final String[] j = {"Sekunde", "Sekunden"};
    public static final String[] k = {"Minute", "Minuten"};
    public static final String[] l = {"Stunde", "Stunden"};
    public static final String[] m = {"Tag", "Tagen"};
    public static final String[] n = {"Woche", "Wochen"};
    public static final String[] o = {"Monat", "Monaten"};
    public static final String[] p = {"Jahr", "Jahren"};
    public static final de q = new de();

    public de() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static de getInstance() {
        return q;
    }
}
