package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class es_419 extends PatternsHolder {
    public static final String[] j = {"segundo", "segundos"};
    public static final String[] k = {"minuto", "minutos"};
    public static final String[] l = {"hora", "horas"};
    public static final String[] m = {"día", "días"};
    public static final String[] n = {"semana", "semanas"};
    public static final String[] o = {"mes", "meses"};
    public static final String[] p = {"año", "años"};
    public static final es_419 q = new es_419();

    public es_419() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static es_419 getInstance() {
        return q;
    }
}
