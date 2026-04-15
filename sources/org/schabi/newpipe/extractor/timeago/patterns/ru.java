package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ru extends PatternsHolder {
    public static final String[] j = {"секунд", "секунду", "секунды", "только что"};
    public static final String[] k = {"минут", "минуту", "минуты"};
    public static final String[] l = {"час", "часа", "часов"};
    public static final String[] m = {"день", "дней", "дня"};
    public static final String[] n = {"Неделю", "недели"};
    public static final String[] o = {"месяц", "месяца", "месяцев"};
    public static final String[] p = {"Год", "года", "лет"};
    public static final ru q = new ru();

    public ru() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static ru getInstance() {
        return q;
    }
}
