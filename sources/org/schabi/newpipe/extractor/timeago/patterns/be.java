package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class be extends PatternsHolder {
    public static final String[] j = {"секунд", "секунду", "секунды"};
    public static final String[] k = {"хвілін", "хвіліну", "хвіліны"};
    public static final String[] l = {"гадзін", "гадзіну", "гадзіны"};
    public static final String[] m = {"дзень", "дзён", "дня", "дні"};
    public static final String[] n = {"тыдзень", "тыдня", "тыдні"};
    public static final String[] o = {"месяц", "месяца", "месяцы", "месяцаў"};
    public static final String[] p = {"год", "года", "гады", "гадоў"};
    public static final be q = new be();

    public be() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static be getInstance() {
        return q;
    }
}
