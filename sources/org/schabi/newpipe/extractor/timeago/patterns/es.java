package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class es extends PatternsHolder {
    public static final String[] j = {"segundo", "segundos"};
    public static final String[] k = {"minuto", "minutos"};
    public static final String[] l = {"hora", "horas"};
    public static final String[] m = {"día", "días"};
    public static final String[] n = {"semana", "semanas"};
    public static final String[] o = {"mes", "meses"};
    public static final String[] p = {"año", "años"};
    public static final es q = new es();

    public es() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static es getInstance() {
        return q;
    }
}
