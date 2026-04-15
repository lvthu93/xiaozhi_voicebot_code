package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class ur extends PatternsHolder {
    public static final String[] j = {"سیکنڈ", "سیکنڈز"};
    public static final String[] k = {"منٹ", "منٹس"};
    public static final String[] l = {"گھنٹہ", "گھنٹے"};
    public static final String[] m = {"دن"};
    public static final String[] n = {"ہفتہ", "ہفتے"};
    public static final String[] o = {"ماہ"};
    public static final String[] p = {"سال"};
    public static final ur q = new ur();

    public ur() {
        super(" ", j, k, l, m, n, o, p);
    }

    public static ur getInstance() {
        return q;
    }
}
