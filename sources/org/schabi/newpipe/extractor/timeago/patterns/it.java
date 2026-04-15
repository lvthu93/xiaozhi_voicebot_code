package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class it extends PatternsHolder {
    public static final String[] j = {"secondi", "secondo"};
    public static final String[] k = {"minuti", "minuto"};
    public static final String[] l = {"ora", "ore"};
    public static final String[] m = {"giorni", "giorno"};
    public static final String[] n = {"settimana", "settimane"};
    public static final String[] o = {"mese", "mesi"};
    public static final String[] p = {"anni", "anno"};
    public static final it q = new it();

    public it() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static it getInstance() {
        return q;
    }
}
