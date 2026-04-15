package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ar extends PatternsHolder {
    public static final String[] j = {"ثانية", "ثانيتين", "ثوانٍ"};
    public static final String[] k = {"دقائق", "دقيقة", "دقيقتين"};
    public static final String[] l = {"ساعات", "ساعة", "ساعتين"};
    public static final String[] m = {"أيام", "يوم", "يومين"};
    public static final String[] n = {"أسابيع", "أسبوع", "أسبوعين"};
    public static final String[] o = {"أشهر", "شهر", "شهرين", "شهرًا"};
    public static final String[] p = {"سنة", "سنتين", "سنوات"};
    public static final ar q = new ar();

    public ar() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static ar getInstance() {
        return q;
    }
}
