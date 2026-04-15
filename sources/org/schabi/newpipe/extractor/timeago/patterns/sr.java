package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class sr extends PatternsHolder {
    public static final String[] j = {"секунде", "секунди"};
    public static final String[] k = {"минута"};
    public static final String[] l = {"сат", "сата", "сати"};
    public static final String[] m = {"Пре 1 дан", "Пре 2 дана", "Пре 3 дана", "Пре 4 дана", "Пре 5 дана", "Пре 6 дана"};
    public static final String[] n = {"недеље", "недељу"};
    public static final String[] o = {"месец", "месеца", "месеци"};
    public static final String[] p = {"година", "године", "годину"};
    public static final sr q = new sr();

    public sr() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static sr getInstance() {
        return q;
    }
}
