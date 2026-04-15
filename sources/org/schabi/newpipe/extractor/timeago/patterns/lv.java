package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class lv extends PatternsHolder {
    public static final String[] j = {"sekundes", "sekundēm"};
    public static final String[] k = {"minūtes", "minūtēm", "minūtes"};
    public static final String[] l = {"stundas", "stundām"};
    public static final String[] m = {"dienas", "dienām"};
    public static final String[] n = {"nedēļas", "nedēļām"};
    public static final String[] o = {"mēneša", "mēnešiem"};
    public static final String[] p = {"gada", "gadiem"};
    public static final lv q = new lv();

    public lv() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static lv getInstance() {
        return q;
    }
}
