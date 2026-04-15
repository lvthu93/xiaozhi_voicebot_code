package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class pt_PT extends PatternsHolder {
    public static final String[] j = {"segundo", "segundos"};
    public static final String[] k = {"minuto", "minutos"};
    public static final String[] l = {"hora", "horas"};
    public static final String[] m = {"dia", "dias"};
    public static final String[] n = {"semana", "semanas"};
    public static final String[] o = {"meses", "mês"};
    public static final String[] p = {"ano", "anos"};
    public static final pt_PT q = new pt_PT();

    public pt_PT() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static pt_PT getInstance() {
        return q;
    }
}
