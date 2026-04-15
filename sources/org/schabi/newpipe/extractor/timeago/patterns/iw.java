package org.schabi.newpipe.extractor.timeago.patterns;

import j$.time.temporal.ChronoUnit;
import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class iw extends PatternsHolder {
    public static final String[] j = {"שניות", "שנייה"};
    public static final String[] k = {"דקה", "דקות"};
    public static final String[] l = {"שעה", "שעות"};
    public static final String[] m = {"יום", "ימים"};
    public static final String[] n = {"שבוע", "שבועות"};
    public static final String[] o = {"חודש", "חודשים"};
    public static final String[] p = {"שנה", "שנים"};
    public static final iw q = new iw();

    public iw() {
        super(" ", j, k, l, m, n, o, p);
        a(ChronoUnit.HOURS, "שעתיים");
        a(ChronoUnit.DAYS, "יומיים");
        a(ChronoUnit.WEEKS, "שבועיים");
        a(ChronoUnit.MONTHS, "חודשיים");
        a(ChronoUnit.YEARS, "שנתיים");
    }

    public static iw getInstance() {
        return q;
    }
}
